package com.ibsplc.neoicargo.devops.snow.notification.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibsplc.neoicargo.devops.snow.web.model.SNOWEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Getter @Setter @ToString
public class AlertManagerNotification {
    public static final String ALERTNAME = "alertname";
    protected static final String SEVERITY = "severity";
    protected static final String NAMESPACE = "namespace";
    protected static final String JOB = "job";

    private static final Pattern TARGET_PATTERN = Pattern.compile(".*/(?<target>.*)-podmonitor-.*");


    private static Optional<Map<String,String>> labels(List<Alert> alerts){
        if(!CollectionUtils.isEmpty(alerts)){
            //Get one
            Alert alert = alerts.get(0);
            var labels = alert.getLabels();
            if (!CollectionUtils.isEmpty(labels)) {
                return Optional.of(labels);
            }
        }
        return Optional.empty();
    }


     static String namespace(List<Alert> alerts){
        return labels(alerts).flatMap(labels->
            labels.entrySet().stream().filter(e -> NAMESPACE.equals(e.getKey())).findFirst().map(Map.Entry::getValue)).orElse("undefined");
    }

    static String target(List<Alert> alerts){
        return labels(alerts).flatMap(labels->
                labels.entrySet().stream().filter(e -> JOB.equals(e.getKey())).findFirst().map(Map.Entry::getValue))
                .map(value->{
                    var matcher = TARGET_PATTERN.matcher(value);
                    if (matcher.matches()) {
                        return matcher.group("target");
                    }
                    return "undefined";
                }).orElse("undefined");
    }


     static String resourceName(List<Alert> alerts) {
        return labels(alerts).map(labels->{
            var filtered = labels.entrySet().stream().
                    filter(e -> !AlertManagerNotification.ALERTNAME.equals(e.getKey()) && !SEVERITY.equalsIgnoreCase(e.getKey()))
                    .map(e -> {
                        var result = e;
                        if (JOB.equalsIgnoreCase(e.getKey())) {
                            var matcher = TARGET_PATTERN.matcher(e.getValue());
                            if (matcher.matches()) {
                                result = Map.entry("target", matcher.group("target"));
                            }
                        }
                        return result;
                    })
                    .collect(Collectors.toSet());
            return new HashSet<>(filtered).toString();
        }).orElse("undefined");

    }

     static String promSeverityLevel(List<Alert> alerts) {
        return labels(alerts).map(labels-> {
            var found = labels.entrySet().stream().filter(e -> SEVERITY.equals(e.getKey())).map(Map.Entry::getValue).findFirst();
            return found.orElse(SNOWEvent.SnowAlterManagerSeverities.info.getPrometheusLevel());
        }).orElse(SNOWEvent.SnowAlterManagerSeverities.info.getPrometheusLevel());

    }

    static String alertName(Map<String,String> commonLabels){
        String resource = "default";
        if(!CollectionUtils.isEmpty(commonLabels)){
            var name = commonLabels.get(ALERTNAME);
            if(StringUtils.hasText(name)){
                resource = name;
            }
        }
        return resource;
    }

    static String description(List<Alert> alerts) {
        String desc = "undefined";
        if (!CollectionUtils.isEmpty(alerts)) {
            Alert alert = alerts.get(0);
            var annotations = alert.getAnnotations();
            if(!CollectionUtils.isEmpty(annotations)){
                var found = annotations.entrySet().stream().filter(e-> "message".equals(e.getKey())).map(Map.Entry::getValue).findFirst();
                if(found.isPresent()){
                    desc = found.get();
                }
            }
        }
        return desc;
    }


    public enum Status{
       resolved,
        firing;

    }

    String version;
    String groupKey;
    int truncatedAlerts;
    Status status;
    String receiver;
    Map<String,String> groupLabels;
    Map<String,String> commonLabels;
    Map<String,String> commonAnnotations;
    //String externalURL;
    List<Alert> alerts;



    @JsonIgnore
    public String getDescription(){
        return description(this.alerts);
    }

    @JsonIgnore
    public String getAlertName(){
        return alertName(this.commonLabels);
    }

    @JsonIgnore
    public String getResourceName(){
        return resourceName(this.alerts);
    }

    @JsonIgnore
    public String getTarget(){
        return target(this.alerts);
    }

    @JsonIgnore
    public String getNamespace(){
        return namespace(this.alerts);
    }

    @JsonIgnore
    public String getPrometheusSeverityLevel(){
        return promSeverityLevel(this.alerts);
    }

}
