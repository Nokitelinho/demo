package com.ibsplc.neoicargo.devops.utils.ecrsync;

import com.ibsplc.neoicargo.devops.utils.ecrsync.parse.BomParser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */
class DoSync {
    String bomFile;
    EcrSync ecrSync;

    public DoSync(String bomFile, EcrSync ecrSync) {
        this.bomFile = bomFile;
        this.ecrSync = ecrSync;
    }

    boolean doIt() throws IOException {
        var parser = new BomParser();
        File file = new File(bomFile);
        var answer = false;
        if (file.canRead()) {
            var bom = parser.parse(file);
            var deployments = bom.getDeployments();
            var metrics = new HashMap<String, Boolean>();
            if (Objects.nonNull(deployments) && deployments.size() > 0) {
                deployments.forEach((key, value) -> {
                    var ie = value.toImageEntry();
                    var result = ecrSync.sync(ie);
                    metrics.put(ie.getImage(), result);
                });
            }
            System.out.printf("#### Sync Status #####%n");
            answer = metrics.entrySet().stream().map(stringBooleanEntry -> {
                        System.out.printf("%s : %s%n", stringBooleanEntry.getKey(), stringBooleanEntry.getValue());
                        return stringBooleanEntry.getValue();
                    }
            ).reduce(true, (a, b) -> a && b);
            //map -> System.out.printf("%s : %s\n",key,value));
        } else {
            System.err.printf("Cannot read file %s %n. Exiting", bomFile);
        }
        return answer;
    }

}
