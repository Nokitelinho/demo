/*
 * MRAConstantsVO.java Created on Jan 29, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class MRAConstantsVO extends AbstractVO {

	/**
	 * CURRENCY_NZD
	 */
	public static final String CURRENCY_NZD = "NZD";
	/**
	 * CURRENCY_USD
	 */
	public static final String CURRENCY_USD = "USD";
	/**
	 * CURRENCY_XDR
	 */
	public static final String CURRENCY_XDR = "XDR";
	
	/*
	 * Added by Sandeep 
	 */
			
	public static final String FIVE_DAY_RATE = "F";
	
	public static final String INHOUSE_RATE = "I";
	
	public static final String BILLABLE = "BB";
	public static final String ONHOLD ="OH";
	public static final String BILLED = "BD";
	public static final String TOBERERATED ="RR";
	public static final String FINALIZED ="F";
	public static final String DIFFERENCED ="D";
	public static final String WITHDRAWN ="WD";
	public static final String PROFOMABILLED ="PB";
	public static final String BLGTYPE_GPA="GPA";
	//added by A-7531 for icrd-132487
	public static final String TOBEREPORATED ="RP";
	public static final String IMPORT_DATA_TO_MRA_CARDIT="IC";
	
	/*
	 * Added by Sandeep  ends
	 */
	/**
	 * Added by A-7531 for ICRD-132508
	 */
	public static final String SUBSYSTEM ="M";
	
	//Added by A-5218 to enable Last Link in Pagination to START
    public static final String MAILTRACKING_MRA_DENSE_RANK_QUERY=
    	"SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    
    public static final String MAILTRACKING_MRA_SUFFIX_QUERY=") RESULT_TABLE";
    
    public static final String MAILTRACKING_MRA_ROWNUM_QUERY=
    	"SELECT RESULT_TABLE.*, ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM (";
   //Added by A-5218 to enable Last Link in Pagination to end
    //Added by A-6991 for ICRD-137019 Starts
    public static final String CONTRACT_RATE = "C";
    public static final String UPU_RATE = "U";
    //Added by A-6991 for ICRD-137019 Ends
	
	//Added by a-7871 for ICRD-154005 starts
	public static final String BASISCODE_WTCHG = "WTCHG"; 
  public static final String BASISCODE_VALCHG = "VALCHG"; 
  public static final String BASISCODE_SURCHG = "SURCHG";
 //Added by a-7871 for ICRD-154005 ends
	
  public static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
  public static final String KEY_BATCH_STATUS_ONETIME = "mail.mra.receivablemanagement.batchstatus";
  public static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
  public static final String USPS_PERFORMED="mailtracking.mra.gpabilling.uspsperformed";
  public static final String RATE_BASIS="mailtracking.mra.gpabilling.ratebasis";
//Added by A-8527 for IASCB-22915
 public static final String KEY_WGTUNITCOD_ONETIME = "mail.mra.defaults.weightunit";
  public static final String CRA_PAR_FUNPNT_MBG="MBG";
  public static final String MRA_CSG_ROUTING_SOURCE_MANUAL = "MN";
  //Added by A-6991 for ICRD-260357 Starts
 /*   public static final String CONTRACT_RATE = "C";
    public static final String UPU_RATE = "U";*/
    //Added by A-6991 for ICRD-260357 Ends
  public static final String MAILSUBCLASSGROUP_LC = "LC";
  public static final String MAILSUBCLASSGROUP_CP = "CP";
  public static final String MAILSUBCLASSGROUP_EMS = "EMS";
  public static final String MAILSUBCLASSGROUP_SV = "SV";

  //Added by A-8331
  
  public static final String VOIDED = "VD";
  public static final String VOID = "V";
  
  public static final String TRGPNT_PROCESS_MANAGER="P";
  public static final String PROCESS_STATUS_OK="OK";
  public static final String IMPORT_DATA_TO_MRA = "I";	 
public static final String MRA_OB_FINALIZE ="MRAOBF";
  public static final String COMPLETED ="C";
  public static final String FAILD = "F";
  public static final String INV_TYP_FINAL= "F";
  public static final String INV_TYP_PROFOMA= "P";
  public static final String INV_TYP_PASS= "PA";
  public static final String INV_STA_NEW= "N";
  public static final String GPA_BILLING = "GB";
  public static final String INITIATED = "I";
  public static final String BILLING_PARAM_COUNTRYCODE = "CNTCOD";
  public static final String BILLING_PARAM_GPACODE = "GPACOD";
  public static final String INCLUDE = "I";
  public static final String EXCLUDE = "E";
  public static final String SCHEDULER_JOB_GPABLG="JOB:GPABLG";
  public static final String SCHEDULER_JOB_PASSBLG="JOB:PASSBLG";
  public static final String PASS_BILLINGPERIOD_VALIDATION="VALIDATION";
 
  public static final String MAILSOURCE = "M";
  public static final String BATCH_STATUS_NEW = "NW";
  public static final String BATCH_STATUS_DELETE = "DL" ;  
  public static final String MRA_FUNPNT_UPC = "UPC" ;  
  public static final String BATCH_STATUS_PARTIALAPP = "Partially Applied" ;  
  public static final String BATCH_STATUS_APPLIED = "Applied" ; 
  public static final String MRA_FUNPNT_MCB ="MCB";
  public static final String BATCH_STATUS_APPLIED_CODE = "AP" ;  
  public static final String BATCH_STATUS_PARTIALLY_APPLIED_CODE = "PA" ; 
  public static final String BATCH_STATUS_CLEARED = "CL" ; 

}
	