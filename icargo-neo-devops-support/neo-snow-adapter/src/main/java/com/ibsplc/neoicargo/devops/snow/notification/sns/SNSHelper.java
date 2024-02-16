package com.ibsplc.neoicargo.devops.snow.notification.sns;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import javax.validation.Valid;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			May 26, 2021 	  Binu K			First draft
 */

@Component
@Slf4j
public class SNSHelper {

    public static final String DEFAULT_EMAIL_SUB_IDENTIFIER="default";
    public static final String SNS_TOPIC_PFX="neo-icargo-prom-alerts";

    @Autowired
    SnsClient snsClient;

    @Autowired
    @Qualifier("defaultAWSTags")
    HashMap<String,String> defaultAWSTags;



    @Autowired
    @Qualifier("inActiveEmailAlerts")
    List<String> inActiveEmailAlerts;

    @Value("${notify.aws.sns.enabled:true}")
    boolean enabled;


    public static String toTopicName(@Nullable String severity){
        return String.format("%s-%s",SNS_TOPIC_PFX,(severity==null)?DEFAULT_EMAIL_SUB_IDENTIFIER:severity).toLowerCase();
    }

     static List<String> splitCsv(String csvString){
        return (!Objects.isNull(csvString))? Arrays.asList(csvString.split(",")):null;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public boolean shouldAlert(String alertName){
        return isEnabled() && !inActiveEmailAlerts.contains(alertName);
    }



    static Supplier executeIgnoringSNSErrors(Supplier<?> function,Supplier<?> defautlVal){
        return ()-> {
            try {
                return function.get();
            } catch (SnsException snsException) {
                log.error("Could not invoke SNS APIs {}", snsException);
            }
            return defautlVal.get();
        };
    }

    public String lookupOrCreateTopic(String topicName){
        return lookupTopic(topicName).or(()->createTopic(topicName)).orElse(null);
    }





    @SuppressWarnings("unchecked")
    public Optional<String> lookupTopic(String topicName){
            return (Optional<String>) executeIgnoringSNSErrors(()-> {
                ListTopicsRequest request = ListTopicsRequest.builder()
                        .build();

                ListTopicsResponse result = snsClient.listTopics(request);
                return filter(result, topicName).map(Topic::topicArn).or(() -> {
                    Optional<String> answer = Optional.empty();
                    while (StringUtils.hasText(result.nextToken())) {
                        var rqst = ListTopicsRequest.builder().nextToken(result.nextToken())
                                .build();
                        answer= filter(snsClient.listTopics(rqst), topicName).map(Topic::topicArn);
                        if(answer.isPresent()) break;
                    }
                    return answer;
                });
            }, Optional::empty).get();

    }

    @SuppressWarnings("unchecked")
    public Optional<String> createTopic(String topicName){
        CreateTopicRequest.Builder builder = CreateTopicRequest.builder().name(topicName);
        if(!CollectionUtils.isEmpty(defaultAWSTags)){
            var tags = defaultAWSTags.entrySet().stream().map(entry->Tag.builder().key(entry.getKey()).value(entry.getValue()).build()).collect(Collectors.toList());
            builder.tags(tags);
        }
        final var  request = builder.build();
        return (Optional<String>) executeIgnoringSNSErrors(()-> {
            var response = snsClient.createTopic(request);
            return Optional.of(response.topicArn());
        },Optional::empty).get();

    }

    public List<String> subscribeToEmails(String topicArn,List<String> emails){
        return emails.stream().filter(StringUtils::hasText).map(email -> subscribeAnEmail(topicArn,email)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public List<String> filterEmailsWithExistingSubscriptions(String topicArn,List<String> emails){
        return (List<String>) executeIgnoringSNSErrors(()-> {
            var request = ListSubscriptionsByTopicRequest.builder().topicArn(topicArn).build();
            var response = snsClient.listSubscriptionsByTopic(request);
            List<Subscription> subscrnList = new ArrayList<>(response.subscriptions());
            while (StringUtils.hasText(response.nextToken())) {
                request = ListSubscriptionsByTopicRequest.builder().topicArn(topicArn).nextToken(response.nextToken()).build();
                response = snsClient.listSubscriptionsByTopic(request);
                subscrnList.addAll(response.subscriptions());
            }
            log.info("Topic {} has existing subscriptions {}",topicArn,subscrnList);
            return emails.stream().filter(email -> subscrnList.stream().noneMatch(subscription -> subscription.subscriptionArn().contains(email))).collect(Collectors.toList());
        },ArrayList::new).get();
    }


    @SuppressWarnings("unchecked")
    private Optional<String> subscribeAnEmail(String topicArn,String email){
        return (Optional<String>) executeIgnoringSNSErrors(()->{
            SubscribeRequest request = SubscribeRequest.builder()
                    .protocol("email")
                    .endpoint(email)
                    .returnSubscriptionArn(true)
                    .topicArn(topicArn)
                    .build();
            SubscribeResponse result = snsClient.subscribe(request);
            return Optional.ofNullable(result.subscriptionArn());
        }, Optional::empty).get();
    }


    @SuppressWarnings("unchecked")
    public boolean publishToTopic(String topicArn,String subject,String message){
        return (boolean)executeIgnoringSNSErrors(()-> {
            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .subject(subject.substring(0, Math.min(subject.length(), 99))) //SNS has a 100 char limit for subjects
                    .topicArn(topicArn)
                    .build();
            PublishResponse result = snsClient.publish(request);
            return result.sdkHttpResponse().isSuccessful();
        },Boolean.FALSE::booleanValue).get();
    }

    private Optional<Topic> filter(ListTopicsResponse result, String topicName){
        Optional<Topic> answer = Optional.empty();
        if(!CollectionUtils.isEmpty(result.topics())){
            answer = result.topics().stream().filter(topic -> topic.topicArn().contains(topicName)).findFirst();
        }
        return  answer;
    }


    public static void main(String[] args) {
        String s = "i am subject i am subject i am subject";
        var trunc = s.substring(0, Math.min(s.length(), 20));
        System.out.println(trunc);
    }

}
