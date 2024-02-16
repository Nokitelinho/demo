package com.ibsplc.neoicargo.devops.snow.web.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Binu K <binu.kurian@ibsplc.com>
 */
/*
 *  Revision History
 *  Revision 	Date      	      Author			Description
 *  0.1			March 16, 2021 	  Binu K			First draft
 */

@Getter @Setter @ToString
public class SNOWRecord {
    List<SNOWEvent> records;

    public SNOWRecord(){}

    public SNOWRecord(SNOWEvent oneEvent){
        records = new ArrayList<>(1);
        records.add(oneEvent);
    }
}
