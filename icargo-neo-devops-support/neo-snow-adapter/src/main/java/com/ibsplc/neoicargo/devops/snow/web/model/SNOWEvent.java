package com.ibsplc.neoicargo.devops.snow.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */
@Getter @Setter @ToString
public class SNOWEvent {

    public enum SnowAlterManagerSeverities{

        critical("critical",1),
        error("error",2),
        warning("warning",3),
        info("info",4);

        int snowLevel;
        String promLevel;

        SnowAlterManagerSeverities(String promLevel,int snowLevel) {
            this.promLevel = promLevel;
            this.snowLevel = snowLevel;
        }


        public String getPrometheusLevel(){
            return promLevel;
        }

        public int getSnowLevel(){
            return snowLevel;
        }

        public static Optional<SnowAlterManagerSeverities> toSnowAlterManagerSeverities(String promAlertLevel){
            return Arrays.stream(SnowAlterManagerSeverities.values()).filter(s -> s.promLevel.equalsIgnoreCase(promAlertLevel)).findFirst();
        }

    }

    String source;
    String event_class;
    String resource;
    String node;
    String metric_name;
    String type;
    int severity;
    String description;
    @JsonIgnore
    Map<String, ? super Object> additional_attribs;
    String additional_info;

    public <T> void addAnAdditionalInfo(String key, T val){
        if(StringUtils.hasText(key) && !ObjectUtils.isEmpty(val)) {
            if (Objects.isNull(additional_attribs)) {
                additional_attribs = new HashMap<>(5);
            }
            additional_attribs.put(key, val);
        }
    }

    @SneakyThrows
    public void buildAdditionalInfo(ObjectMapper objectMapper){
        if(!CollectionUtils.isEmpty(additional_attribs)) {
            additional_info = objectMapper.writeValueAsString(additional_attribs);
        }
    }

}
