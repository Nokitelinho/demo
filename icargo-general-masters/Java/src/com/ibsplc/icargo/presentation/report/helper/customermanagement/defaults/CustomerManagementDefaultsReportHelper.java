/*
 * CustomerManagementDefaultsReportHelper.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.helper.customermanagement.defaults;


import java.util.Collection;

import com.ibsplc.icargo.framework.report.helper.Help;
import com.ibsplc.icargo.framework.report.helper.Helper;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-1862
 *
 */
public class CustomerManagementDefaultsReportHelper {

	private Log log = LogFactory.getLogger("CustomerManagementReportHelper");
	
	@Helper(
			{
					@Help(
							reportId = "RPTLST045",
							voNames = {"com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO",
									"com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO"},
							fieldNames = {"fromDate","toDate"
									}
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
	public static String formatDate(Object value, Collection extraInfo) {		
		String date=ReportConstants.EMPTY_STRING;
		if(value!=null){
		LocalDate dates = (LocalDate)value;
		return dates.toDisplayDateOnlyFormat();
		}
		else{
			return date;
		}
	}
	
	@Helper(
			{
					@Help(
							reportId = "RPTLST045",
							voNames = {"com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO",
									"com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeVO"
									},
							fieldNames = {"entryPoints","expiryPeriod"}
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
	public static String formatPeriod(Object value, Collection extraInfo) {
		
		String period=ReportConstants.EMPTY_STRING;
		if(value!=null){
		period=String.valueOf(value);
		return period;
		}
		else{
			return period;
		}
	}

		
}
