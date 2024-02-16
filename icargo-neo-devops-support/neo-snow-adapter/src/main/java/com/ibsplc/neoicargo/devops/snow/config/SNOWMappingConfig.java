package com.ibsplc.neoicargo.devops.snow.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 17, 2021 	  Binu K			First draft
 */

@Getter @Setter @ToString
public class SNOWMappingConfig {

    Map<String,String> typeMappings = new HashMap<>();

    public String snowTypeMapping(String alertName){
        return typeMappings.getOrDefault(alertName, typeMappings.get("default"));
    }
}
