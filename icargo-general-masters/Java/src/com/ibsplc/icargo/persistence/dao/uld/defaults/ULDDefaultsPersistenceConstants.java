/*
 * ULDDefaultsPersistenceConstants.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject tos license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.util.regex.Pattern;

/**
 *
 * @author A-1940
 *
 */
public class ULDDefaultsPersistenceConstants {
	private ULDDefaultsPersistenceConstants(){
		
	}
	/**
	 *  Module Name
	 */
	public static final String MODULE_NAME = "uld.defaults";

	/**
	 *
	 */
	public static final String ULD_FIND_ULDACCSTK_DETAILS = "uld.defaults.stock.findAccessoriesStockDetails";

	/**
	 *
	 */
	public static final String ULD_FIND_ULDACCSTK_LIST = "uld.defaults.stock.findAccessoriesStockList";

	/**
	 *
	 */
	public static final String ULD_FIND_ULDMVTDTL = "uld.defaults.stock.findULDMovementHistory";

	/**
	 *
	 */
	public static final String ULD_DEFAULTS_FINDULDDETAILS = "uld.defaults.findULDDetails";

	/**
	 *
	 */
	public static final String CHECK_DUPLICATE_MANUFACTURER_NUMBER = "uld.defaults.checkDuplicateManufacturerNumber";
	
	public static final String DUMMY_ULD_MOVEMENT_PRESENT = "uld.defaults.DummyULDMovementPresent";

	/**
	 *
	 */
	public static final String CHECK_ULD_IN_TRANSACTION = "uld.defaults.checkULDInTransaction";

     /**
      * used in listing uld agreement details
      */
	public static final String ULD_DEFAULTS_FIND_ULD_AGMNT_DTLS = "uld.defaults.finduldagreementdetails";
	/**
     * used in listing uld agreement details
     */
	//Added by A-8445 as a part of IASCB-28460 Starts
	public static final String ULD_DEFAULTS_FIND_ULD_AGMNT_FILTER_DTLS = "uld.defaults.finduldagreementfilterdetails";
	//Added by A-8445 as a part of IASCB-28460 Ends
   /**
    *
    */
	public static final String ULD_DEFAULTS_FIND_ULD_LIST = "uld.defaults.finduldlist";

     /**
	 * 
	 */
	public static final String ULD_DEFAULTS_FIND_ULD_INVENTORY_LIST = "uld.defaults.findUldInventoryList";

     /**
      * For Listing ULD Agreement
      */

	public static final String LIST_ULDAGREEMENT = "uld.defaults.listuldagreement";

     /**
      * For ULDAgreementLOV
      */
	public static final String POPULATE_ULDAGREEMENTLOV = "uld.defaults.populateULDAgreementLOV";

     /**
      * For checking the duplication of ULD Agreement
      */

	public static final String ULDAGREEMENT_ALREADY_EXISTS = "uld.defaults.uldagreementalreadyexists";

     /**
      * For listing ULD Damage details
      */
	public static final String FIND_ULDDAMAGEDETAILS = "uld.defaults.findulddamagedetails";

	/**
	 *
	 */
	public static final String FIND_CURRENTULDDETAILS = "uld.defaults.misc.findCurrentULDDetails";

	/**
	 *
	 */
	public static final String FIND_ULDDAMAGE_LIST = "uld.defaults.misc.findulddamagelist";

	/**
	 * For listing ULD Repair Details
	 */
	public static final String LIST_ULDREPAIRDETAILS = "uld.defaults.misc.listuldrepairdetails";

	/**
	 *  For listing uld Invoice
	 */
	public static final String LIST_ULD_CHARGING_INVOICE = "uld.defaults.listuldinvoice";

	/**
	 * For listing uld Invoice Details
	 */
	public static final String VIEW_ULD_CHARGING_INVOICE_DETAILS = "uld.defaults.viewuldtxncharginginvoicedetails";

	/**
	 *
	 */
	public static final String LOANED_ULD_ALREADY_LOANED = "uld.defaults.loaneduld.loaned";

	/**
	 *
	 */
	public static final String BORROWED_ULD_ALREADY_BORROWED = "uld.defaults.borroweduld.borrowed";

	/**
	 *
	 */
	public static final String BORROWED_ULD_LOANEDTO_SAMEPARTY = "uld.defaults.borroweduld.loanedtosameparty";

	/**
	 *
	 */
	public static final String LOANED_ULD_BORROWEDFROM_SAMEPARTY = "uld.defaults.loaneduld.borrowedfromsameparty";

	/**
	 * For listing all damageRefernceNumbers
	 */
	public static final String FIND_ULDDAMAGEDREFERENCENUMBER_LOV = "uld.defaults.misc.findulddamagedreferencenumberlov";

	/**
	 * For listing uld transaction
	 */
	public static final String LIST_ULD_TRANSACTIONS = "uld.defaults.listuldtransactions";

	/**
	 *
	 */
	public static final String LIST__TRANSACTIONS = "uld.defaults.listtransactions";

	/**
	 * For Invoice RefNumber Lov
	 */
	public static final String FIND_INVOICE_REFERENCENUM_LOV = "uld.defaults.findinvoicerefnumberlov";

	/**
	 * For Listing of accessory details
	 */
	public static final String LIST_ACCESSORY_TRANSACTIONS = "uld.defaults.listaccessorytransactions";

	/**
	 * For Listing of Repair Invoice details
	 */
	public static final String FIND_REPAIR_INVOICE_DETAILS = "uld.defaults.findrepairinvoicedetails";

	/**
	 * Used in Listing of Repair Invoice details for getting Invoice Details
	 */
	public static final String FIND_INVOICE_DETAILS_FOR_REPAIR = "uld.defaults.findinvoicedetailsforrepair";

	/**
	 * used in listing the ULDAgreementsForReturnTransaction
	 */
	public static final String FIND_ULDAGMNTS_FOR_RETURNTXN = "uld.defaults.finduldagreementsforreturntransaction";

	/**
	 * This method is used to Monitor ULD stock
	 */
	public static final String FIND_ULD_STOCK_LIST = "uld.defaults.finduldstocklist";

	public static final String FIND_ULDSTOCKLIST_FOR_NOTIFICATION = "uld.defaults.finduldstocklistfornotification";
	
	public static final String FIND_FLIGHTS_MISSING_UCM = "uld.defaults.findUCMMissingFlights"; 

	/**
	 * This method is used to list the StationULds
	 */
	public static final String FIND_STATION_ULDS = "uld.defaults.findstationulds";

	/**
	 * This method is used to list the Generic agreements
	 */
	public static final String FIND_ULDAGMNTS_GENERIC_FOR_RET_TXN = "uld.defaults.findgenericagreement";

	/**
	 * This method is used to Validate ULD
	 */
	public static final String VALIDATE_ULD = "uld.defaults.validateULD";

	/**
	 *
	 */
	public static final String CHECK_FOR_INVOICE = "uld.defaults.checkForInvoice";

	/**
	 *
	 */
	public static final String LIST_ULD_DISCREPANCY = "uld.defaults.listulddiscrepancy";
	/**
	 * Added by A-7636 as part of ICRD-245031 
	 */
	public static final String REMOVE_DAMAGE_IMAGES = "uld.defaults.removeDamageImages";

/**
 *
 */
	public static final String CHECK_FOR_DISCREPANCY = "uld.defaults.checkfordiscrepancy";

	/**
	 *
	 */
	public static final String FIND_AIRLINE_CODE = "uld.defaults.findAirlineCode";

	/**
	 *
	 */
	public static final String FIND_TRANSACTIONS = "uld.defaults.findtransactionsformucreconciliation";

	/**
	 *
	 */
	public static final String FIND_CARRIER_CODE = "uld.defaults.findcarriercode";

	/**
	 *
	 */
	public static final String LIST_AIRLINE_IDENTIFIERS = "uld.defaults.getairlineidentifiers";

	/**
	 *
	 */
	public static final String FIND_CURRENT_LOCATION = "uld.defaults.findcurrentlocation";
	/**
	 *
	 */
	public static final String FIND_LOCATION_FACILITY= "uld.defaults.findlocationforfacility";

	/**
	 *
	 */
	public static final String POPULATE_CURRENT_LOCATION = "uld.defaults.populatecurrentlocation";

	/**
	 *
	 */
	public static final String GENERATE_UCMMESSAGEVO = "uld.defaults.generateucmmessagevo";

	/**
	 *
	 */
	public static final String VALIDATE_ULDS = "uld.defaults.validateULDs";

	/**
	 *
	 */
	public static final String FIND_ULDAGMTS_FOR_ALL_PTY = "uld.defaults.findagreementforallparty";

	/**
	 *
	 */
	public static final String ALLPARTYCODE = "ALL";

	/**
	 *
	 */
	// Added by Preet for AirNZ 517 --starts
	public static final String ALLPARTYTYPE = "L";

	public static final String ALL_AIRLINE = "ALL AIRLINES";

	public static final String ALL_AGENT = "ALL AGENTS";
	
	public static final String ALL_CUSTOMERS = "ALL CUSTOMERS"; //Added for ICRD-323967

	public static final String AIRLINE = "A";

	public static final String AGENT = "G";

	public static final String OTHER = "O";

	public static final String CUSTOMERS = "C"; //Added for ICRD-323967

	public static final String ALL_OTHER = "ALL OTHERS";

	//Added by Preet for AirNZ 517 --ends



    /**
     *
     */
	public static final String VALIDATEULDFLIGHTMESSAGEDETAILS = "uld.defaults.validateULDFlightMessageDetails";

    /**
     *
     */
	public static final String GET_UCM_FLIGHTDETAILS = "uld.defaults.getUCMFlightDetails";

    /**
     *
     */
	public static final String LIST_UKD_AIRPORTLOCATION = "uld.defaults.listAirportLocation";

    /**
     *
     */
	public static final String FIND_DEFAULTFLAG = "uld.defaults.findDefaultFlag";

    /**
     *
     */
	public static final String CHECK_ULD_LOCATION = "uld.defaults.checkForULDLocation";

    /**
     *
     */
	public static final String LIST_UCM_MESSAGE = "uld.defaults.listUCMMessage";

    /**
     *
     */
	public static final String LIST_UCM_OUTMESSAGE = "uld.defaults.listUCMOUTMessage";

    /**
     *
     */
	public static final String LIST_UCM_INMESSAGE = "uld.defaults.listUCMINMessage";

    /**
     *
     */
	public static final String LIST_UCM_ERRORS = "uld.defaults.listUCMErrors";

    /**
     *
     */
	public static final String LIST_ULD_ERRORS = "uld.defaults.listUldErrors";

    /**
     *
     */
	public static final String LIST_UCMS_FOR_COMPARISON = "uld.defaults.listucmsforcomparison";
	
	
	 /**
    *
    */
	public static final String LIST_UCMS_FOR_FLIGHT_MVT = "uld.defaults.listucmsforflightmovement";
	

    /**
     *
     */
	public static final String FIND_UCM_NO_LOV = "uld.defaults.finducmnolov";

    /**
     *
     */
	public static final String FIND_COUNTER_UCM = "uld.defaults.findcounterucm";

    /**
     *
     */
	public static final String LIST_UCMINOUT_MESSAGE = "uld.defaults.listUCMINOUTMessage";

    /**
     *
     */
	public static final String LIST_UCMOUT_FORMISMATCH = "uld.defaults.listUCMOUTForInOutMismatch";

    /**
     *
     */
	public static final String LIST_ULD_FORSCM = "uld.defaults.getUldDetailsForSCM";

    /**
     *
     */
	public static final String LIST_SCM_MESSAGE = "uld.defaults.listSCMMessage";

    /**
     *
     */
	public static final String LIST_ULDERRORS_INSCM = "uld.defaults.listULDErrorsInSCM";

    /**
     *
     */
	public static final String CHECK_FOR_UCM_INOUT = "uld.defaults.checkforucminout";

    /**
     *
     */
	public static final String FIND_INMESSAGE_AIRPORTS = "uld.defaults.findInMesssageAirports";

    /**
     *
     */
	public static final String SEND_SCM_MESSAGE = "uld.defaults.sendSCMMessage";

    /**
     *
     */
	public static final String LIST_ULD_POOLOWNER = "uld.defaults.listULDPoolOwner";

    /**
     *
     */
	public static final String LIST_SCM_MESSAGE_LOV = "uld.defaults.listSCMMessageLOV";

    /**
     *
     */
	public static final String FIND_AIRLINEID_FORSCM = "uld.defaults.findAirlineIdentifierForSCM";

    /**
     *
     */
	public static final String FIND_MONITOR_ULD_STOCK_SCM_ULDS = "uld.defaults.findULDsForSCMFromMonitorULDStock";

    /**
     *
     */
	public static final String CHECK_FOR_POOL_OWNERS = "uld.defaults.checkforpoolowners";

    /**
     * This method finds all ulds for hht
     */
	public static final String FIND_ULDS_FOR_HHT = "uld.defaults.finduldlistforhht";

    /**
     * to find the owner airline details for an airline
     */
	public static final String FIND_OWNERCODE = "uld.defaults.findownercode";

    /**
     * to find all ulds in the systemstock
     */
    public static final String FIND_ULDS = "uld.defaults.findulds";

    /**
     * To list all Flights for which the UCM has to be sent manually
     */
	public static final String FIND_EXCEPTION_FLIGHTS = "uld.defaults.findexceptionflights";

    /**
     * to find all missing ulds
     */
	public static final String FIND_MISSING_ULDS = "uld.defaults.findmissingulds";

    /**
     *
     */
    public static final String FIND_ULDCOUNTS_FOR_MOVEMENT =
    	"uld.defaults.finduldcountsformovement";


    /**
     *
     */
	public static final String MONITOR_ULD_FOR_GROUP = "uld.defaults.monitorulsforuldgroup";

	/**
	 *
	 */
	public static final String LIST_ULD_HISTORY = "uld.defaults.listuldhistory";

	/* Added by A-2412 on 18th Oct for Editable CRN cr */
    public static final String CHECK_DUP_CRN = "uld.defaults.checkforduplicatecrn";
    //ends
    /* Added by A-3459 on 26th Sep for checking Single Duplicate CRN  */
    public static final String CHECK_SINGLE_DUP_CRN=  "uld.defaults.checkforsingleduplicatecrn";
    //ends
    // Added by A-2667 on 5th Nov for duplicate Facility Code Check
    public static final String CHECK_DUP_FACCOD =
    	"uld.defaults.checkforduplicatefacilitycod";
    // Ends

    //Added by Sreekumar S for ANACR 1471 on 28th Nov
    /**
     * To fetch the Airline Code based on the Login User.
     */
    public static final String GET_AIRLINECODE = "uld.defaults.getairlinecode";


    //Added by Sreekumar S for ANACR 1478 on 4thDec
    /**
     * To fetch the Current Airport Code based on the ULD Number and Company Code.
     */
    public static final String FIND_CURRENTARP = "uld.defaults.findcurrentairport";

//  Added by Tarun for ANACR 1479 on 6th Dec
    /**
     * To fetch the Handled Carrier.
     */
    public static final String FIND_HANDLED_CARRIER = "uld.defaults.findhandledcarrier";

    //  Added by Sreekumar S for AirNZ CR431 on 17Mar08
    /**
     * To fetch the Stock status for HHT
     */
    public static final String FIND_STOCK_STATUS_FOR_HHT = "uld.defaults.findstockstatusforhht";

    //  Added by Sreekumar S for AirNZ CR434 on 17Mar08
    /**
     *
     */
    public static final String POPULATE_LOCATION_LOV = "uld.defaults.populatelocationlov";

    //Added by Tarun on AirNZ CRQ312 on 25Mar08
    public static final String FIND_ULDDAMAGEDREPAIRETAILS = "uld.defaults.findulddamagerepairdetails";

    // Added by Preet for AirNZ CRQ519 on 26th Mar08
    public static final String ULD_FIND_ULDINTMVTDTL = "uld.defaults.findintuldmvthistory";



    public static final String FIND_POL_LOCATION = "uld.defaults.findpollocation";

    /**
     *
     */
    public static final String FIND_ULD_AUDIT_ENQUIRY_DETAILS = "uld.defaults.findauditenquirydetails";

    /**
     * @author A-1950
     */
    public static final String FIND_ULD_STOCK_DEVIATION = "uld.defaults.finduldstockdeviation";

    /**
     * @author A-1950
     */
    public static final String FIND_ULD_ACC_STOCK_DEPLETION = "uld.defaults.stock.findAccessoriesStockList";

    /**
     *
     */
    public static final String FIND_ULD_DIS_FOR_ALT = "uld.defaults.findulddiscrepanciesforalert";

    /**
     * @author a-2883
     */
    public static final String FIND_ULD_DMG_PIC_DETAILS = "uld.defaults.findulddamagepictures";
    /**
     * @author A-2667
     */
    public static final String FIND_CPM_ULD_DETAILS = "uld.defaults.findcpmulddetails";

    /**
     * @author A-3459
     */
	public static final String LIST_ULD_DAMAGECHECKLISTMASTER = "uld.defaults.listULDDamageChecklistMaster";

	  /**
     * @author a-2883
     */
    public static final String FIND_ULD_INVENTORY_DETAILS = "uld.defaults.finduldinventorydetails";

    /**
     * @author A-1950
     */
    public static final String FIND_DWELL_TIMES = "uld.defaults.finddwelltimes";

    /**
     * @author A-2408
     */
    public static final String CHECK_POOL_OWNERS = "uld.defaults.checkpoolowners";


    /**
     * @author a-3429
     */
    public static final String FIND_ULD_HISTORY_COUNTS="uld.defaults.finduldhistorycounts";
    
    /**
     * @author a-3093
     * for QF1022
     */

	public static final String LIST_ULDDAMAGEREPAIRDETAILS = "uld.defaults.listdamagerepairdetails";
	
	public static final String FINDULDACTIONHISTORY = "uld.defaults.findULDActionHistory";
	
	/**
     * @author A-3459
     */
	public static final String FIND_MISSING_UCMS = "uld.defaults.missingucmlist";
	 /**
	 * For MUC RefNumber Lov
	 */
	public static final String FIND_MUC_REFERENCENUM_LOV = "uld.defaults.findmucrefnumberlov";
	
	public static final String FIND_MUC_AUDIT_DETAILS = "uld.defaults.findmucauditdetails";
	
	public static final String CHECK_ULD_INUSE = "uld.defaults.checkuldinuse";
	
	public static final String UPDATE_SCM_FOR_PENDING_ULDS = "uld.defaults.updatescmstatusforpendingulds";
	
	/**
	 *added by a-3045
	 */
	public static final String FIND_TRANSACTION_HISTORY = "uld.defaults.findtransactionhistory";
	
	public static final String LIST__LOANBORROWENQUIRY = "uld.defaults.listloanborrowenquiry";
	
	public static final String LIST_LOANDEMURAGEENQ = "uld.defaults.listloandemurageenq";

	public static final String LIST_LOANBORROWENQUIRYWITHAGRDTL = "uld.defaults.listloanborrowenquirywithagrdtl";

	/**
	 *added by a-3459
	 */
	public static final String ULD_DEFAULTS_FIND_ULD_SCMVALIDATION = "uld.defaults.findscmvalidationlist";
	
	public static final String ULD_DEFAULTS_FIND_ULD_SCMVALIDATION_PART2 = "uld.defaults.findscmvalidationlist.part2";//added by A-5844 for ICRD-138696
	
	public static final String UPDATE_ULD_DISCREPANCY = "uld.defaults.updateulddiscrepancy";
	
	/**
    * added by a-3278
    */
	public static final String ULDS_FOR_SCM = "uld.defaults.findULDsForSCM";

	/**
    * added by a-3278
    */
	public static final String ULD_DEFAULTS_UPDATESCMSTATUSFORULD = "uld.defaults.updatescmstatusforuld";
	/**
    * added by a-2052
    */
	public static final String LIST_ULD_SERVICEABILITY_CODEMASTER = "uld.defaults.listuldserviceabilitymaster";
	/**
    *added by a-2052
    */
	public static final String CHECK_ULD_SERVICEABILITY = "uld.defaults.checkForuldserviceability";
	// Added by A-3268 for Bug 102024
	public static final String LIST__ULDDTLSFORDEMURRAGE = "uld.defaults.ulddetailsfordemurrage";
	
	public static final String FIND_FLIGHT_ARRIVAL = "uld.defaults.findflightarrival";
	public static final String FIND_UCM_IN_STATUS = "uld.defaults.finducmin";
	
	  //Added by A-5214 to enable Last Link in Pagination to start
    public static final String ULD_DEFAULTS_DENSE_RANK_QUERY=
    	"SELECT RESULT_TABLE.* ,DENSE_RANK() OVER ( ORDER BY ";
    public static final String ULD_DEFAULTS_ROWNUM_RANK_QUERY=
    	"SELECT RESULT_TABLE.* ,ROW_NUMBER() OVER (ORDER BY null) AS RANK FROM(";
    public static final String ULD_DEFAULTS_SUFFIX_QUERY=") RESULT_TABLE";
    //Added by A-5214 to enable Last Link in Pagination to end
	
	public static final String FIND_ESTIMATED_ULD_STOCK_LIST = "uld.defaults.findestimateduldstocklist";
	public static final String FIND_EXCESS_STOCK_AIRPORT_LIST = "uld.defaults.findexcessstockairportlist";
	public static final String FIND_ALL_ULD_FLIGHT_MSG_REC_DTLS="uld.defaults.message.findAllUldFltMsgRecDtls";
	public static final String FIND_TRANSIT_STATE_ULDS_FOR_UCM = "uld.defaults.message.findULDsInTransitStatus";
	public static final String FIND_LAST_TRANSACTIONSFOR_ULD = "uld.defaults.findLastTransactionsForUld";
	//Added by A-6994 to enable total no: of record fetched in Pagination 
	public static final String ULD_DEFAULT_ROWNUM_RANK_QUERY = "SELECT RESULT_TABLE.* ,ROW_NUMBER() OVER (ORDER BY null) AS RANK FROM(";
    public static final String ULD_DEFAULT_ROWNUM_SUFFIX_QUERY= ") RESULT_TABLE";
  //Added by A-6994 to enable print button 
    public static final String ULD_DEFAULT_LIST_SUFFIX_QUERY= ") ";
	public static final String DATEFORMAT = "yyyy-MM-dd";
	public static final String DATEFORMAT_TIME = "yyyy-MM-dd HH:mm:ss";



//merge solved by A-7794   

    /**
     * Added By A-7794 as part of ICRD-208677
     */
    public static final String FIND_OPERATIONAL_ULD_AUDIT_DETAILS= "uld.defaults.findOprULDAuditDetails";
    public static final String FIND_OPERATIONAL_MAILULD_AUDIT_DETAILS = "uld.defaults.findOprAndMaiULDAuditDetails";
    //Added by A-7794 as part of ICRD-224604
    public static final String FIND_OPERATIONAL_MAILULD_AUDIT_DETAILS_FOR_OAL_CARRIER = "uld.defaults.findOprAndMaiULDAuditDetailsForOALCarrier";
    //Added by A-7359 for ICRD-192413
    public static final String FIND_lATESTUCM_FROMALL_SOURCES= "uld.defaults.findlatestUCMsFromAllSources";
    
    public static final String VALIDATE_ULD_AIRPORT_LOCATION = "uld.defaults.validateUldAirportLocation";
	
    /**
    *
    */
	public static final String DUPLICATE_ULD_POOLOWNER = "uld.defaults.findDuplicatePoolOwnerConfig";
	
	public static final String FIND_AIRPORTS_FOR_SCM_JOB="uld.defaults.findairportsforscmjob";
	public static final String FLAG_YES="Y";
	public static final Pattern CURDAT= Pattern.compile("CURDAT");
	
	
}
