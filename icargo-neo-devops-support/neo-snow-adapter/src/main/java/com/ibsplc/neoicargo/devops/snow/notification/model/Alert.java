package com.ibsplc.neoicargo.devops.snow.notification.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Getter
@Setter
@ToString
public class Alert {
    String status;
    Map<String,String> labels;
    Map<String,String> annotations;

    String startsAt;
    String endsAt;
    //String generatorURL;
}
