package com.ibsplc.neoicargo.devops.utils.ecrsync.parse;

import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			June 03, 2021 	  Binu K			First draft
 */

public class BomParser {

    ObjectMapper objectMapper;
    public BomParser() {
        objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    public Bom parse(File file) throws IOException {
        return objectMapper.readValue(file,Bom.class);
    }




}
