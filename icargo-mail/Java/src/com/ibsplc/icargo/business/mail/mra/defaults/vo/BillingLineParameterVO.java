/*
 * BillingLineParameterVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-2280
 *
 */
public class BillingLineParameterVO extends AbstractVO {
	/**
	 * Parcod for ORIGIN_REGION
	 */
	public static final String ORIGIN_REGION = "ORGREG";
	/**
	 * Parcod for ORIGIN_COUNTRY
	 */
	public static final String ORIGIN_COUNTRY = "ORGCNT";
	/**
	 * Parcod for ORIGIN_CITY
	 */
	public static final String ORIGIN_CITY = "ORGCTY";
	/**
	 * Parcod for DESTINATION_REGION
	 */
	public static final String DESTINATION_REGION = "DSTREG";
	/**
	 * Parcod for DESTINATION_COUNTRY
	 */
	public static final String DESTINATION_COUNTRY = "DSTCNT";
	/**
	 * Parcod for DESTINATION_CITY
	 */
	public static final String DESTINATION_CITY = "DSTCTY";
	/**
	 * Parcod for UPLIFT_COUNTRY
	 */
	public static final String UPLIFT_COUNTRY = "UPLCNT";
	/**
	 * Parcod for UPLIFT_CITY
	 */
	public static final String UPLIFT_CITY = "UPLCTY";
	/**
	 * Parcod for DISCHARGE_COUNTRY
	 */
	public static final String DISCHARGE_COUNTRY = "DISCNT";
	/**
	 * Parcod for DISCHARGE_CITY
	 */
	public static final String DISCHARGE_CITY = "DISCTY";
	/**
	 * Parcod for FLIGHT_NUMBER
	 */
	public static final String FLIGHT_NUMBER = "FLTNUM";
	/**
	 * Parcod for ULD_TYPE
	 */
	public static final String ULD_TYPE = "ULDTYP";
	/**
	 * Parcod for CATEGORY
	 */
	public static final String CATEGORY = "CAT";
	/**
	 * Parcod for CLASS
	 */
	public static final String CLASS = "CLS";
	/**
	 * Parcod for SUB_CLASS
	 */
	public static final String SUB_CLASS = "SUBCLS";
	/**
	 * Parcod for TRASFERED_BY
	 */
	public static final String TRASFERED_BY = "TRANSFEREDBY";
	/**
	 * Parcod for AGENT
	 */
	public static final String AGENT = "AGT";//Added by a-7531 for icrd-224979

	//Added by A-7540
	public static final String ORIGIN_AIRPORT = "ORGARPCOD";
	public static final String DESTINATION_AIRPORT = "DSTARPCOD";
	public static final String VIA_POINT = "VIAPNT";
	public static final String MAIL_SERVICE_LVL = "MALSRVLVL";
	public static final String POAFLG = "POAFLG";
	
    private String parameterCode;
    private String parameterValue;
    private String excludeFlag;
    private String parameterDesc;

	/**
	 * @return Returns the excludeFlag.
	 */
	public String getExcludeFlag() {
		return excludeFlag;
	}
	/**
	 * @param excludeFlag The excludeFlag to set.
	 */
	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
	}
	/**
	 * @return Returns the parameterCode.
	 */
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 * @param parameterCode The parameterCode to set.
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * @return Returns the parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue The parameterValue to set.
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getParameterDesc() {
		return parameterDesc;
	}
	public void setParameterDesc(String parameterDesc) {
		this.parameterDesc = parameterDesc;
	}


}
