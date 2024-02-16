/*
 * KafkaTracingUtils.java Created on 03/12/20
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.neoicargo.framework.probe.kafka;

import com.ibsplc.neoicargo.framework.probe.TxProbeContext;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.RecordInterceptor;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static com.ibsplc.neoicargo.framework.probe.TxProbeUtils.TXPROBE_CORRELATION_HEADER;

/**
 * @author jens
 */
@SuppressWarnings({"cast", "rawtypes"})
class KafkaTracingUtils {

    static final Logger logger = LoggerFactory.getLogger(KafkaTracingUtils.class);

    @SneakyThrows
    public static void primeKafkaTemplate(KafkaTemplate<?, ?> kafkaTemplate, TxProbeKafkaAgent agent) {
        ProducerFactory<?, ?> proxy = proxyProducerFactory(kafkaTemplate.getProducerFactory(), agent);
        Field producerFactoryField = KafkaTemplate.class.getDeclaredField("producerFactory");
        producerFactoryField.setAccessible(true);
        producerFactoryField.set(kafkaTemplate, proxy);
    }

    @SneakyThrows
    public static <K, V> void primeListenerContainer(AbstractMessageListenerContainer<K, V> container, TxProbeKafkaAgent agent) {
        Field consumerFactoryField = AbstractMessageListenerContainer.class.getDeclaredField("consumerFactory");
        consumerFactoryField.setAccessible(true);
        ConsumerFactory<?, ?> consumerFactory = (ConsumerFactory<?, ?>) consumerFactoryField.get(container);
        ConsumerFactory<?, ?> proxy = proxyConsumerFactory(consumerFactory, agent);
        consumerFactoryField.set(container, proxy);
        RecordInterceptor<K, V> probeInterceptor = record -> {
            String correlationId = correlationId(record.headers());
            // apply the correlationId to the current thread context
            if (correlationId != null) {
                TxProbeContext.threadContextPut(TXPROBE_CORRELATION_HEADER, correlationId);
                MDC.put("correlationId", correlationId);
            }
            return record;
        };

        Field recordInterceptorField = AbstractMessageListenerContainer.class.getDeclaredField("recordInterceptor");
        recordInterceptorField.setAccessible(true);
        RecordInterceptor<K, V> businessInterceptor = (RecordInterceptor<K, V>) recordInterceptorField.get(container);
        if (businessInterceptor != null) {
            logger.debug("businessRecordInterceptor {}", businessInterceptor);
            container.setRecordInterceptor(new CompoundRecordInterceptor<>(businessInterceptor, probeInterceptor));
        } else
            container.setRecordInterceptor(probeInterceptor);
    }

    static class CompoundRecordInterceptor<K, V> implements RecordInterceptor<K, V> {

        final RecordInterceptor<K, V> businessInterceptor;
        final RecordInterceptor<K, V> probeInterceptor;

        public CompoundRecordInterceptor(RecordInterceptor<K, V> businessInterceptor, RecordInterceptor<K, V> probeInterceptor) {
            this.businessInterceptor = businessInterceptor;
            this.probeInterceptor = probeInterceptor;
        }

        @Override
        public ConsumerRecord<K, V> intercept(ConsumerRecord<K, V> record) {
            try {
                record = probeInterceptor.intercept(record);
            } finally {
                record = businessInterceptor.intercept(record);
            }
            return record;
        }
    }

    public static ProducerFactory<?, ?> proxyProducerFactory(ProducerFactory<?, ?> factory, TxProbeKafkaAgent agent) {
        return new TxProbeProxiedProducerFactory<>(factory, agent);
    }

    public static ConsumerFactory<?, ?> proxyConsumerFactory(ConsumerFactory<?, ?> factory, TxProbeKafkaAgent agent) {
        return new TxProbeProxiedConsumerFactory<>(factory, agent);
    }

    static String correlationId(Headers headers) {
        Header header = headers.lastHeader(TXPROBE_CORRELATION_HEADER);
        if (header == null)
            return null;
        return new String(header.value());
    }

    private static <K, V> Producer<K, V> primeProducer(Producer<K, V> producer, TxProbeKafkaAgent agent) {
        return new TxProbeProxiedProducer<>(producer, agent);
    }

    @SneakyThrows
    private static <K, V> Consumer<K, V> primeConsumer(Consumer<K, V> consumer, TxProbeKafkaAgent agent) {
        return new TxProbeProxiedConsumer<>(consumer, agent);
    }

    /**
     * Proxies the Producer instance to intercept the send operation.
     *
     * @param <K>
     * @param <V>
     */
    static class TxProbeProxiedProducer<K, V> implements Producer<K, V> {

        final Producer<K, V> proxied;
        final TxProbeKafkaAgent agent;

        public TxProbeProxiedProducer(Producer<K, V> proxied, TxProbeKafkaAgent agent) {
            this.proxied = proxied;
            this.agent = agent;
        }

        @Override
        public void initTransactions() {
            proxied.initTransactions();
        }

        @Override
        public void beginTransaction() {
            proxied.beginTransaction();
        }

        @Override
        public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, String consumerGroupId) {
            proxied.sendOffsetsToTransaction(offsets, consumerGroupId);
        }

        @Override
        public void commitTransaction() {
            proxied.commitTransaction();
        }

        @Override
        public void abortTransaction() {
            proxied.abortTransaction();
        }

        @Override
        public Future<RecordMetadata> send(ProducerRecord<K, V> record) {
            return send(record, null);
        }

        @Override
        public Future<RecordMetadata> send(ProducerRecord<K, V> record, Callback callback) {
            String correlationId = TxProbeContext.threadContext(TXPROBE_CORRELATION_HEADER);
            if (correlationId != null)
                record.headers().add(TXPROBE_CORRELATION_HEADER, correlationId.getBytes());
            this.agent.onSend(record);
            TxProbeProducerCallback<K, V> txProbeProducerCallback = new TxProbeProducerCallback<>(callback, this.agent, record);
            return proxied.send(record, txProbeProducerCallback);
        }

        @Override
        public void flush() {
            proxied.flush();
        }

        @Override
        public List<PartitionInfo> partitionsFor(String topic) {
            return proxied.partitionsFor(topic);
        }

        @Override
        public Map<MetricName, ? extends Metric> metrics() {
            return proxied.metrics();
        }

        @Override
        public void close() {
            proxied.close();
        }

        @Override
        public void close(Duration timeout) {
            proxied.close(timeout);
        }

        @Override
        public void close(long timeout, TimeUnit unit) {
            proxied.close(timeout, unit);
        }

        @Override
        public void sendOffsetsToTransaction(Map<TopicPartition, OffsetAndMetadata> offsets, ConsumerGroupMetadata groupMetadata) throws ProducerFencedException {
            proxied.sendOffsetsToTransaction(offsets, groupMetadata);
        }
    }

    /**
     * A producer callback to notify if an error occurs in the send flow.
     *
     * @param <K>
     * @param <V>
     */
    static class TxProbeProducerCallback<K, V> implements Callback {

        final TxProbeKafkaAgent agent;
        final ProducerRecord<K, V> record;
        final Callback next;

        public TxProbeProducerCallback(Callback next, TxProbeKafkaAgent agent, ProducerRecord<K, V> record) {
            this.agent = agent;
            this.record = record;
            this.next = next;
        }

        @Override
        public void onCompletion(RecordMetadata metadata, Exception exception) {
            if (exception != null)
                agent.onSendError(this.record, exception);
            if (this.next != null)
                next.onCompletion(metadata, exception);
        }
    }

    /**
     * A consumer factory proxy to intercept the consumer objects created for probe augmentation.
     *
     * @param <K>
     * @param <V>
     */
    static class TxProbeProxiedConsumerFactory<K, V> implements ConsumerFactory<K, V> {
        final ConsumerFactory<K, V> delegate;
        final TxProbeKafkaAgent agent;

        public TxProbeProxiedConsumerFactory(ConsumerFactory<K, V> delegate, TxProbeKafkaAgent agent) {
            this.delegate = delegate;
            this.agent = agent;
        }

        @Override
        public Consumer<K, V> createConsumer() {
            return primeConsumer(delegate.createConsumer(), this.agent);
        }

        @Override
        public Consumer<K, V> createConsumer(String clientIdSuffix) {
            return primeConsumer(this.delegate.createConsumer(clientIdSuffix), this.agent);
        }

        @Override
        public Consumer<K, V> createConsumer(String groupId, String clientIdSuffix) {
            return primeConsumer(this.delegate.createConsumer(groupId, clientIdSuffix), this.agent);
        }

        @Override
        public Consumer<K, V> createConsumer(String groupId, String clientIdPrefix, String clientIdSuffix, Properties properties) {
            return primeConsumer(this.delegate.createConsumer(groupId, clientIdPrefix, clientIdSuffix, properties), this.agent);
        }

        @Override
        public Map<String, Object> getConfigurationProperties() {
            return this.delegate.getConfigurationProperties();
        }

        @Override
        public Deserializer<K> getKeyDeserializer() {
            return this.delegate.getKeyDeserializer();
        }

        @Override
        public Deserializer<V> getValueDeserializer() {
            return this.delegate.getValueDeserializer();
        }

        @Override
        public Consumer<K, V> createConsumer(String groupId, String clientIdPrefix, String clientIdSuffix) {
            return primeConsumer(this.delegate.createConsumer(groupId, clientIdPrefix, clientIdSuffix), this.agent);
        }

        @Override
        public boolean isAutoCommit() {
            return this.delegate.isAutoCommit();
        }
    }

    /**
     * A producer factory proxy to intercept the producer objects created
     *
     * @param <K>
     * @param <V>
     */
    static class TxProbeProxiedProducerFactory<K, V> implements ProducerFactory<K, V> {

        final ProducerFactory<K, V> delegate;
        final TxProbeKafkaAgent agent;

        public TxProbeProxiedProducerFactory(ProducerFactory<K, V> delegate, TxProbeKafkaAgent agent) {
            this.delegate = delegate;
            this.agent = agent;
        }

        @Override
        public Producer<K, V> createProducer(String txIdPrefix) {
            return primeProducer(delegate.createProducer(txIdPrefix), this.agent);
        }

        @Override
        public boolean transactionCapable() {
            return delegate.transactionCapable();
        }

        @Override
        public void closeProducerFor(String transactionIdSuffix) {
            delegate.closeProducerFor(transactionIdSuffix);
        }

        @Override
        public boolean isProducerPerConsumerPartition() {
            return delegate.isProducerPerConsumerPartition();
        }

        @Override
        public void closeThreadBoundProducer() {
            delegate.closeThreadBoundProducer();
        }

        @Override
        public Producer<K, V> createProducer() {
            return primeProducer(delegate.createProducer(), this.agent);
        }
    }

    /**
     * A delegate consumer to intercept the records received
     * @param <K>
     * @param <V>
     */
    static class TxProbeProxiedConsumer<K, V> implements Consumer<K, V> {

        final Consumer<K, V> delegate;
        final TxProbeKafkaAgent agent;

        public TxProbeProxiedConsumer(Consumer<K, V> delegate, TxProbeKafkaAgent agent) {
            this.agent = agent;
            this.delegate = delegate;
        }

        @Override
        public Set<TopicPartition> assignment() {
            return delegate.assignment();
        }

        @Override
        public Set<String> subscription() {
            return delegate.subscription();
        }

        @Override
        public void subscribe(Collection<String> topics) {
            delegate.subscribe(topics);
        }

        @Override
        public void subscribe(Collection<String> topics, ConsumerRebalanceListener callback) {
            delegate.subscribe(topics, callback);
        }

        @Override
        public void assign(Collection<TopicPartition> partitions) {
            delegate.assign(partitions);
        }

        @Override
        public void subscribe(Pattern pattern, ConsumerRebalanceListener callback) {
            delegate.subscribe(pattern, callback);
        }

        @Override
        public void subscribe(Pattern pattern) {
            delegate.subscribe(pattern);
        }

        @Override
        public void unsubscribe() {
            delegate.unsubscribe();
        }

        @Override
        @Deprecated
        public ConsumerRecords<K, V> poll(long timeout) {
            ConsumerRecords<K, V> answer = delegate.poll(timeout);
            if(!answer.isEmpty())
                answer.forEach(this.agent::onReceive);
            return answer;
        }

        @Override
        public ConsumerRecords<K, V> poll(Duration timeout) {
            ConsumerRecords<K, V> answer = delegate.poll(timeout);
            if(!answer.isEmpty())
                answer.forEach(this.agent::onReceive);
            return answer;
        }

        @Override
        public void commitSync() {
            delegate.commitSync();
        }

        @Override
        public void commitSync(Duration timeout) {
            delegate.commitSync(timeout);
        }

        @Override
        public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets) {
            delegate.commitSync(offsets);
        }

        @Override
        public void commitSync(Map<TopicPartition, OffsetAndMetadata> offsets, Duration timeout) {
            delegate.commitSync(offsets, timeout);
        }

        @Override
        public void commitAsync() {
            delegate.commitAsync();
        }

        @Override
        public void commitAsync(OffsetCommitCallback callback) {
            delegate.commitAsync(callback);
        }

        @Override
        public void commitAsync(Map<TopicPartition, OffsetAndMetadata> offsets, OffsetCommitCallback callback) {
            delegate.commitAsync(offsets, callback);
        }

        @Override
        public void seek(TopicPartition partition, long offset) {
            delegate.seek(partition, offset);
        }

        @Override
        public void seek(TopicPartition partition, OffsetAndMetadata offsetAndMetadata) {
            delegate.seek(partition, offsetAndMetadata);
        }

        @Override
        public void seekToBeginning(Collection<TopicPartition> partitions) {
            delegate.seekToBeginning(partitions);
        }

        @Override
        public void seekToEnd(Collection<TopicPartition> partitions) {
            delegate.seekToEnd(partitions);
        }

        @Override
        public long position(TopicPartition partition) {
            return delegate.position(partition);
        }

        @Override
        public long position(TopicPartition partition, Duration timeout) {
            return delegate.position(partition, timeout);
        }

        @Override
        @Deprecated
        public OffsetAndMetadata committed(TopicPartition partition) {
            return delegate.committed(partition);
        }

        @Override
        @Deprecated
        public OffsetAndMetadata committed(TopicPartition partition, Duration timeout) {
            return delegate.committed(partition, timeout);
        }

        @Override
        public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions) {
            return delegate.committed(partitions);
        }

        @Override
        public Map<TopicPartition, OffsetAndMetadata> committed(Set<TopicPartition> partitions, Duration timeout) {
            return delegate.committed(partitions, timeout);
        }

        @Override
        public Map<MetricName, ? extends Metric> metrics() {
            return delegate.metrics();
        }

        @Override
        public List<PartitionInfo> partitionsFor(String topic) {
            return delegate.partitionsFor(topic);
        }

        @Override
        public List<PartitionInfo> partitionsFor(String topic, Duration timeout) {
            return delegate.partitionsFor(topic, timeout);
        }

        @Override
        public Map<String, List<PartitionInfo>> listTopics() {
            return delegate.listTopics();
        }

        @Override
        public Map<String, List<PartitionInfo>> listTopics(Duration timeout) {
            return delegate.listTopics(timeout);
        }

        @Override
        public Set<TopicPartition> paused() {
            return delegate.paused();
        }

        @Override
        public void pause(Collection<TopicPartition> partitions) {
            delegate.pause(partitions);
        }

        @Override
        public void resume(Collection<TopicPartition> partitions) {
            delegate.resume(partitions);
        }

        @Override
        public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch) {
            return delegate.offsetsForTimes(timestampsToSearch);
        }

        @Override
        public Map<TopicPartition, OffsetAndTimestamp> offsetsForTimes(Map<TopicPartition, Long> timestampsToSearch, Duration timeout) {
            return delegate.offsetsForTimes(timestampsToSearch, timeout);
        }

        @Override
        public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions) {
            return delegate.beginningOffsets(partitions);
        }

        @Override
        public Map<TopicPartition, Long> beginningOffsets(Collection<TopicPartition> partitions, Duration timeout) {
            return delegate.beginningOffsets(partitions, timeout);
        }

        @Override
        public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions) {
            return delegate.endOffsets(partitions);
        }

        @Override
        public Map<TopicPartition, Long> endOffsets(Collection<TopicPartition> partitions, Duration timeout) {
            return delegate.endOffsets(partitions, timeout);
        }

        @Override
        public ConsumerGroupMetadata groupMetadata() {
            return delegate.groupMetadata();
        }

        @Override
        public void enforceRebalance() {
            delegate.enforceRebalance();
        }

        @Override
        public void close() {
            delegate.close();
        }

        @Override
        @Deprecated
        public void close(long timeout, TimeUnit unit) {
            delegate.close(timeout, unit);
        }

        @Override
        public void close(Duration timeout) {
            delegate.close(timeout);
        }

        @Override
        public void wakeup() {
            delegate.wakeup();
        }
    }


}
