package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;
/**
 * 
 * @author a-1496
 *
 */
public class CustomerMgmntPersistenceConstants {

	/**
	 *  Module Name
	 */
	public static final String MODULE_NAME =
		"customermanagement.defaults";
	
	/**
	 * Finds all loyalty programmes
	 */
	/*public static final String FIND_ALL_LOYALTY_PROGRAMMES = 
		"customermanagement.defaults.findAllLoyaltyProgrammes";*/

	/**
	 * Lists loyalty programmes based on Filter
	 */
	public static final String LIST_LOYALTY_PROGRAMMES = 
		"customermanagement.defaults.listLoyaltyProgramme";
	
	/**
	 * Lists loyalty Attribute details
	 */
	public static final String LIST_LOYALTY_ATTRIBUTE_DETAILS = 
		"customermanagement.defaults.listLoyaltyAttributeDetails";
	/**
	 * Validates parameter code and values
	 */
	public static final String VALIDATE_PARAMETER = 
		"customermanagement.defaults.validateParameter";
	/**
	 * Checks Customers or groups attached to Loyalty programme
	 */
	public static final String CHECK_CUSTOMER_ATTACHED = 
		"customermanagement.defaults.checkCustomerAttached";
	/**
	 * Checks Loyalty Programme attatched to 
	 */
	public static final String CHECK_LOYALTY_PROGRAMME_ATTACHED = 
		"customermanagement.defaults.checkLoyaltyProgrammeAttached";
	/**
	 * Lists All Loyalty Programmes based on Filter 
	 */
	public static final String LIST_ALL_LOYALTY_PROGRAMMES = 
		"customermanagement.defaults.listAllLoyaltyProgrammes";
	/**
	 * Finds Customer Group if any attached 
	 */
	public static final String FIND_CUSTOMER_GROUP = 
		"customermanagement.defaults.findCustomerGorup";
	/**
	 * Finds attached loyalty programmes to Customer/Group
	 */
	public static final String FIND_ATTACHED_LOYALTY_PROGRAMMES = 
		"customermanagement.defaults.findAttachedLoyaltyProgrammes";
	/**
	 * Lists Loyalty Points For Awb based on filter
	 */
	public static final String LIST_LOYALTY_POINTS_FOR_AWB = 
		"customermanagement.defaults.listLoyaltyPointsForAwb";
	
	/**
	 * Finds Parameter Description Details
	 */
	public static final String FIND_PARAMETER_DETAILS = 
		"customermanagement.defaults.findParameterDetails";
	/**
	 * Finds Parameter unit code
	 */
	public static final String FIND_PARAMETER_UNIT = 
		"customermanagement.defaults.findParameterUnit";
	/**
	 * @param String
	 */
	public static final String VALIDATE_CUSTOMER_FOR_PTS_REDEMPTION = 
		"customermanagement.defaults.validateCustomerForPointsRedemption";
	/**
	 * Returns Running Loyalty programmes that is not expired
	 */
	public static final String RUNNING_LOYALTY_PROGRAMME_LOV = 
		"customermanagement.defaults.runningLoyaltyProgrammeLOV";
	
	
	
	/**
	 * 
	 */
	public static final String LIST_CUSTOMER_GROUP = 
		"customermanagement.defaults.listCustomerGroup";
	/**
	 * 
	 */
	
	public static final String LIST_CUSTOMER_GROUPLOV = 
		"customermanagement.defaults.customerGroupLOV";
	/**
	 * 
	 */
	public static final String LIST_TEMP_CUSTOMER = 
		"customermanagement.defaults.listTempCustomer";
	/**
	 * 
	 */
	public static final String LIST_TEMP_CUSTOMER_DETAILS = 
		"customermanagement.defaults.listTempCustomerDetails";
	/**
	 * 
	 */
	public static final String LIST_GROUP_LOYALTY_PGM = 
		"customermanagement.defaults.listGroupLoyaltyPgm";
	/**
	 * 
	 */
	public static final String CHECK_FOR_CUSTOMER=
		"customermanagement.defaults.checkForCustomer";
    /**
     * 
     */
	public static final String LIST_LOYALTYPGM_CUSTOMERS=
		"customermanagement.defaults.listLoyaltyPgmToCustomers";
	/**
	 * 
	 */
	public static final String LIST_POINTS_ACCUMULATED=
		"customermanagement.defaults.listPointAccumulated";
	/**
	 * 
	 */
	public static final String SHOW_POINTS =
		"customermanagement.defaults.showPoints";
	/**
	 * 
	 */
	public static final String FIND_ENTRY_POINTS =
		"customermanagement.defaults.findEntryPoints";
	/**
	 * @param String
	 */
	public static final String CHECK_FOR_CUSTOMERLOYALTY =
		"customermanagement.defaults.checkForCustomerLoyalty";
	/**
	 * 
	 */
	public static final String CHECK_FOR_SERVICES =
		"customermanagement.defaults.checkforservices";
	/**
	 * 
	 */
	public static final String LIST_CUSTOMER_SERVICES=
		"customermanagement.defaults.listcustomerservices";
	/**
	 * @param String
	 */
	public static final String CUSTOMER_SERVICES_LOV=
		"customermanagement.defaults.customerserviceslov";
	
	//Added by A-5175 for icrd-20959 starts
    public static final String CUSTOMERMANAGEMENT_DEFAULTS_DENSE_RANK_QUERY = "SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    
    public static final String CUSTOMERMANAGEMENT_DEFAULTS_SUFFIX_QUERY = ") RESULT_TABLE";
    
    public static final String CUSTOMERMANAGEMENT_DEFAULTS_ROWNUM_QUERY = "SELECT RESULT_TABLE.*,ROWNUM AS RANK FROM (";
    //Added by A-5175 for icrd-20959 ends
	
}
