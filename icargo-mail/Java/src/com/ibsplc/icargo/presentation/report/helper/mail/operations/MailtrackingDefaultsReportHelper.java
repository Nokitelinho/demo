/*
 * MailtrackingDefaultsReportHelper.java Created on July 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.helper.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.report.util.ReportConstants;




/**
 * 
 * @author a-1862
 *
 */
public class MailtrackingDefaultsReportHelper {
	
	
	
	@Helper(
			{
					@Help(
							reportId = "RPTOPR046",
							voNames = {"com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO"},
							fieldNames = {"year"}
					),
					@Help(
							reportId = "RPTOPR046",
							voNames = {"com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO"},
							fieldNames = {"highestNumberedReceptacle"}
					)
			}
	)
	/**
	 * @author A-1862
	 * @return 
	 * @param extraInfo
	 * @param value
	 * 
	 */
	public static String formatRotationFrequency(Object value, Collection extraInfo) {

		String freq="";
		if((value)!=null){
			freq=String.valueOf(value);

		}
		return freq;
	}
	/**
	 * @param value
	 * @param extraInfo
	 * @return
	 */
/*	@Helper(
			{
					@Help(
							reportId = "RPTOPR046",
							voNames = {"com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO"},
							fieldNames = {"statedWeight"}
					)
					
			}
		)
	/**
	 * @author A-3353
	 * @return 
	 * @param extraInfo
	 * @param value
	 * 
	 
	 public static String formatStatedWeight(Object value, Collection extraInfo){
		String wt = "";
		if((value)!=null){
    	 wt = String.valueOf(value);
		}
		 	int len = wt.indexOf(".");
    	String wgt = wt;
    	if(len > 0 && len < 4){
    		wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,wt.length())).toString();
    	}else{
    		wgt = wt.substring(0,len);
    	}
    	String stdwgt = wgt;
    	if(wgt.length() == 3){
    		stdwgt = new StringBuilder("0").append(wgt).toString();
    	}
    	if(wgt.length() == 2){
    		stdwgt = new StringBuilder("00").append(wgt).toString();
    	}
    	if(wgt.length() == 1){
    		stdwgt = new StringBuilder("000").append(wgt).toString();
    	}
		
    	return stdwgt;
    }*/
	
	@Helper(
			{
					@Help(
							reportId = "RPRMTK085",
							voNames = {"com.ibsplc.icargo.business.mail.operations.vo.MailbagVO"},
							fieldNames = {"mailbagId"}
					)
					
			}
		)
		
		
	/**
	 * @author a-2107
	 * @param value
	 * @param extraInfo 
	 * 
	 */

	public static String formatCount(Object value, Collection extraInfo) {		
		String mailId=ReportConstants.EMPTY_STRING;
		if(value!=null){
			mailId=value.toString();
			return mailId;
		}
		else{
			return mailId;
		}
	}
	
	
	
	
}