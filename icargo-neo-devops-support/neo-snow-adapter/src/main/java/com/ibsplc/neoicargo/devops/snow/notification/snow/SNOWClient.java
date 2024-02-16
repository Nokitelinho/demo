package com.ibsplc.neoicargo.devops.snow.notification.snow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibsplc.neoicargo.devops.snow.config.SNOWConfig;
import com.ibsplc.neoicargo.devops.snow.web.model.SNOWRecord;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Base64;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Component
@Slf4j
public class SNOWClient {


    public SNOWClient(SNOWConfig snowConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.snowConfig = snowConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        if(StringUtils.hasText(snowConfig.getInstanceName())) {
            snowURL = String.format("https://%s.service-now.com/api/global/em/jsonv2", snowConfig.getInstanceName());
            isConfigured=true;
        }else{
            log.warn("No snow instance defined as property \"notify.snow.instanceName\". Will NOT post to SNOW");
        }
    }

    SNOWConfig snowConfig;
    RestTemplate restTemplate;
    ObjectMapper objectMapper;
    String snowURL;
    boolean isConfigured;

    private String postTOSnow(String body){
        String authStr = String.format("%s:%s",snowConfig.getBasicAuth().getUserId(),snowConfig.getBasicAuth().getPassword());
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, "Basic " + base64Creds);
        HttpEntity<String> entity = new HttpEntity<String>(body, headers);
        return restTemplate.postForObject(snowURL, entity,String.class);
    }


    @SneakyThrows
    public boolean createEvent(SNOWRecord snowRecord){
        if(isConfigured && StringUtils.hasText(snowURL)) {
            var body = objectMapper.writeValueAsString(snowRecord);
            log.debug("POST {} to {}", body, snowURL);
            var result = postTOSnow(body);
            log.debug("POST to {} got {}", snowURL, result);
            return StringUtils.hasText(result);
        }
        return false;

    }


}
