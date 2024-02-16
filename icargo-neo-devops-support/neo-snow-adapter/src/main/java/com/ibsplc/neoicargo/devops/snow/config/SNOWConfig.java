package com.ibsplc.neoicargo.devops.snow.config;

import com.ibsplc.neoicargo.devops.snow.notification.NotifyConfiguration;
import liquibase.pro.packaged.S;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */


@Getter @Setter @ToString
public class SNOWConfig extends NotifyConfiguration {

    String instanceName;
    BasicAuth basicAuth;
    //Source Name to appear in SNOW
    String sourceName = "prometheus";
    //Node Name to appear in SNOW
    String nodeName = "k8s";

    @Getter @Setter @ToString
    public static class BasicAuth{
        String userId;
        String password;

    }
}
