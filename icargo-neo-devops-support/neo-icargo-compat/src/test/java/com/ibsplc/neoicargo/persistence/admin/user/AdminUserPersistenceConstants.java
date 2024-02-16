/*
 * AdminUserPersistenceConstants.java Created on Jun 2, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.persistence.admin.user;

/**
 * @author A-1954
 *
 */

public interface AdminUserPersistenceConstants {

	/**
	 * Static Constant for MODULE_NAME
	 */
	public static final String MODULE_NAME = "admin.user";
	
	/*
	 * Static Constant for mapping query in xml for finding the userdetails
	 */
	public static final String ADMIN_USER_FINDUSERDETAILSLOV_QUERY1 = "admin.user.findUserDetailslov_query1";
	/**
	 * Static Constant for mapping query in xml for finding the userdetails
	 */
	public static final String ADMIN_USER_FINDUSERDETAILSLOV_QUERY2 = "admin.user.findUserDetailslov_query2";
	/**
	 * Added by A-5497 for ICRD-78944 
	 * Static Constant for mapping query in xml for finding the userdetails
	 */
	public static final String ADMIN_USER_FINDUSERDETAILSLOV_QUERY3 = "admin.user.findUserDetailslov_query3";
	
	
	/**
	 * Static Constant for mapping query in xml
	 */
	public static final String ADMIN_USER_FINDUSERDETAILS = "admin.user.findUserDetails";
	
	public static final String ADMIN_USER_LIST_USERS = "admin.user.listUsers";
	public static final String ADMIN_USER_FINDUSERPRIVILEGES = "admin.user.findUserPrivileges";
	public static final String ADMIN_USER_FIND_USER_PARAMETERS = "admin.user.findUserParameters";
	
	// Added by Bejoy {starts}
	/**
	 * Static Constant for mapping query in xml
	 */
	public static final String ADMIN_USER_GETUSERDETAILSFORLOGON = "admin.user.getUserDetailsForLogon";
	// Added by Bejoy {ends}
	/**
	 * Static Constant for mapping query in xml for finding the userdetails
	 */
	public static final String ADMIN_USER_FINDUSERDETAILSLOV_BASE = "admin.user.findUserDetailslov_base";
	/**
	 * Static Constant for mapping query in xml for finding the userdetails
	 */
	public static final String ADMIN_USER_FINDUSERDETAILSLOV_NEXT = "admin.user.findUserDetailslov_next";
	/**
	 * Static Constant for mapping query in xml for the validation of users
	 */
	public static final String ADMIN_USER_VALIDATEUSERS = "admin.user.validateusers";
	
	public static final String ADMIN_USER_VALIDATEMSGHEADER="admin.user.validatemessageheader";

	// Added by Sinoob starts
	/**
	 *
	 */
	public static final String ADMIN_USER_FINDSPECIFICSTAFF = "admin.user.findSpecificStaff";
	/**
	 *
	 */
	public static final String ADMIN_USER_FINDSPECIFICSTAFFONLINE = "admin.user.findSpecificStaffOnline";
	//public static final String ADMIN_USER_FINDUSERSINROLEGROUPONLINE = "admin.user.findUsersInRoleGroupOnline";
	/**
	 *
	 */
	public static final String ADMIN_USER_FINDUSERSINROLEGROUP = "admin.user.findUsersInRoleGroup";
	/**
	 *
	 */
	public static final String ADMIN_USER_FINDROLEGROUPONLINE = "admin.user.findRoleGroupOnline";
	/**
	 *
	 */
	public static final String ADMIN_USER_FINDROLEGROUP = "admin.user.findRoleGroup";

	// Added by Sinoob ends

	/**
	 *
	 */
	public static final String ADMIN_USER_VALIDATEUSERSWITOUTROLEGRP = "admin.user.validateuserswitoutrolegrp";

	/**
	 *
	 */
	public static final String ADMIN_USER_VALIDATEROLEGROUP = "admin.user.validateRoleGroup";
	/**
	 *
	 */
	public static final String AIRLINE_USER = "ARL";

	public static final String GHA_USER = "GHA";

	public static final String OWN_GSA_USER = "OGS";

	public static final String OTHER_GSA_USER = "TGS";

	public static final String GHA_GSA_USER = "GHS";

	public static final String PORTAL_USER = "P";
	public static final String SYSTEM_USER = "S";
	public static final String NORMAL_USER = "N";
	public static final String HHT_USER = "H";
	/**
	 * Static Constant for mapping query in xml for the validation of user
	 */
	public static final String ADMIN_USER_VALIDATEUSER="admin.user.validateuser";


	public static final String ADMIN_USER_USERENQUIRY="admin.user.findUserEnquiryDetails";


	public static final String GRACE_LOGIN = "G";
	public static final String NORMAL_LOGIN = "N";

	public static final String ADMIN_USER_FINDALLPWD_FORUSR = "admin.user.findAllPasswordsForUser";

	public static final String ADMIN_USER_CHK_PWDHISTORY = "admin.user.checkPasswordHistory";

	public static final String 	ADMIN_USER_IS_PWDGUESSABLE= "admin.user.isPasswordGuessable";

	public static final String 	ADMIN_USER_FIND_MAXSEQNUM= "admin.user.findMaxSequenceNumber";

	public static final String 	ADMIN_USER_FIND_PWDRYLTRH= "admin.user.findPasswordRecycleThreshold";

	public static final String ADMIN_USER_FINDPASSWORDPOLICY =	"admin.user.findPasswordPolicy";

	public static final String ADMIN_USER_FINDGUESSABLEPASSWORDS =	"admin.user.findGuessablePasswords";

	public static final String ADMIN_USER_FINDINACTIVEUSERS =	"admin.user.findInactiveUsers";

	public static final String ADMIN_USER_FINDUSERALLOWABLESTATION_PAGES =	"admin.user.findUserAllowableStationsPages";

	public static final String ADMIN_USER_VALIDATECASHIERS = "admin.user.validatecashiers";

	public static final String ADMIN_USER_UPDATEPROCEDURE = "admin.user.udapteUserDetails";
	
	//Added by A-5223 for ICRD-23107 starts
	public static final String ADMIN_USER_DENSE_RANK_QUERY =
		"SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY ";

	public static final String ADMIN_USER_ROWNUM_QUERY =
		"SELECT RESULT_TABLE.* , ROW_NUMBER () OVER (ORDER BY NULL) AS RANK FROM ( ";

	public static final String ADMIN_USER_SUFFIX_QUERY = " )RESULT_TABLE";
	//Added by A-5223 for ICRD-23107 ends
	
	//ICRD-57144 changes begins here
	/*
	 * The constant value used to identify the query to get user parameter values.
	 * 
	 * Modified By : A-6042
	 * Modified Date: 03/04/2014
	 * CR : ICRD-57144
	 */
	public static final String ADMIN_USER_FIND_USER_PARAMETERS_VALUE_MAP = "admin.user.findUserParametersValueMap";
	////ICRD-57144 changes ends here
	
	
	public static final String ADMIN_USER_FIND_EXHAUSTED_LOGIN = "admin.user.findExhaustedlogindetails";
	
	
	public static final String ADMIN_USER_FIND_USER_FAVOURITES = "admin.user.findUserFavourites";
	
	public static final String ADMIN_USER_FIND_USER_PARAMETERS_LIST = "admin.user.findUserParametersList";
	
	public static final String ADMIN_USER_FIND_USER_OFFICES_LIST = "admin.user.findUserOffices";
	
	public static final String ADMIN_USER_FIND_USER_STATIONS = "admin.user.findUserStations";
	
	public static final String ADMIN_USER_FIND_PORTAL_USER_DEATILS_FOR_LOGON = "admin.user.getPortalUserDetailsForLogon";
	
	public static final String ADMIN_USER_FIND_PORTAL_USER_DEATILS = "admin.user.getPortalUserDetails";
	// Added as part of ICRD-204467
	public static final String  ADMIN_USER_FIND_HANDLING_AREA_USERS = "admin.user.findHandlingAreaUsers";
	
	public static final String ADMIN_USER_FIND_USER_FOR_CUSTOMER = "admin.user.findPortalUserForMappedCustomer";
	
	public static final String ADMIN_USER_VALIDATE_HANDLING_AREA_USER = "admin.user.validateHandlingAreaUsers";

	public static final String ADMIN_USER_USERENQUIRY_FOR_DEF_ROL="admin.user.findUserEnquiryDetailsForDefRole";
	public static final String ADMIN_USER_USERENQUIRY_FOR_ADL_ROL="admin.user.findUserEnquiryDetailsForAdlRole";
	public static final String ADMIN_USER_USERENQUIRY_DEF_ROL_DETAILS = "admin.user.findDefRolesForUser";	
	public static final String ADMIN_USER_USERENQUIRY_ADL_ROL_DETAILS = "admin.user.findAdlRolesForUser";
	
	public static final String POSTGRES_ADMIN_USER_GETUSERDETAILSFORLOGON = "postgres.admin.user.getUserDetailsForLogon";
	public static final String POSTGRES_ADMIN_USER_FIND_USER_STATIONS = "postgres.admin.user.findUserStations";
	public static final String ADMIN_USER_EMAIL_DETAILS = "admin.user.findRoleGroupUserEmailDetails";
}
