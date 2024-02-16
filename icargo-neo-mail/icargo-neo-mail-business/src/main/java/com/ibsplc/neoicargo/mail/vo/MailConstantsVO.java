/*
 * MailConstantsVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

import java.time.format.DateTimeFormatter;

/**
 * This class contains the various possible values the mailStatus can take.
 * @author a-3109
 *
 */
public class MailConstantsVO extends AbstractVO {
    /**
     * The accepted status
     */
	public static final String MAIL_STATUS_ACCEPTED="ACP";
	public static final String MLD_MSG_ERR_INVALID_FLIGHT = "mailtracking.defaults.mld.invalidflight";
	public static final String CONTAINER_STATUS_PREASSIGN="PREASSIGN_CONTAINER";
	public static final String CONTAINER_STATUS_REASSIGN="CONTAINER_REASSIGN";
	public static final String CONTAINER_STATUS_TRANSFER="CONTAINER_TRANSFER";
	public static final String CONTAINER_STATUS_ARRIVAL="CONTAINER_ARRIVAL";
	public static final String CONTAINER_STATUS_DELIVERY="CONTAINER_DELIVERY";
	 /**
	  * The Constant tat has to be used in Messaging For the PartyType Airline
	  */
	 public static final String MSG_PARTYTYPE_ARL="AR";
	 public static final String MLD_MSG_ERR_INVALID_CATEGORY = "mailtracking.defaults.mld.invalidcategory";
	 public static final String MLD_MSG_ERR_CANT_DELIVER_PORT = "mailtracking.defaults.mld.cantdeliverport";
	/**
	 * The Arrived status
	 */
	 public static final String RESDIT_SEND_STATUS="RDT";
	 public static final String MAILBAG_ASSIGNMENT_IN="ASG_IN";
	 public static final String MAILBAG_ASSIGNMENT_OUT="ASG_OUT";
	public static final String MLD="MLD";
//Added as part of CRQ ICRD-93584 by A-5526 starts
	public static final String IMP="IMP";
	public static final String MLD_REC="REC";
	public static final String MLD_ALL="ALL";
	public static final String MLD_UPL="UPL";
	public static final String MLD_HND="HND";
	public static final String MLD_DLV="DLV";
	//Added as part of bug ICRD-225164 by A-5526
	public static final String MLD_FRESH_ALL="FRESHALL";
	public static final String MLD_MODE_FLIGHT="F";
	public static final String MLD_MODE_CARRIER="H";
	public static final String MLD_MODE_POSTAL_AUTHORITY="G";
	//Added as part of CRQ ICRD-120878 by A-5526
	public static final String MLD_BARCODETYPE_FOR_ULD_LEVEL="U";
	public static final String MLD_BARCODETYPE_FOR_MAIL_LEVEL="R";
	public static final String MLD_OPERATION_INBOUND_MODE="INB";
	public static final String MLD_OPERATION_OUTBOUND_MODE="OUB";
	 //Added as part of CRQ ICRD-93584 by A-5526 ends
	public static final String WS="WS";
	public static final String MAIL_STATUS_ARRIVED="ARR";
	public static final String MAIL_STATUS_UPLIFTED="UPL";
	public static final String MAIL_STATUS_DELETED="DEL";
	/**
	 * The Arrived status
	 */
	public static final String MAIL_STATUS_RECEIVED = "REC";
	/**
	 * The delivered status	 */
	public static final String MAIL_STATUS_DELIVERED="DLV";
	/**
	 * The status notDelivered
	 */
	public static final String MAIL_STATUS_NOTDELIVERED="NDL";
	/**
	 * The status offloaded
	 */
	public static final String MAIL_STATUS_OFFLOADED="OFL";
	/**
	 * The status notuplifted
	 */
	public static final String MAIL_STATUS_NOTUPLIFTED="NUP";
	/**
	 * The status Returned
	 */
	public static final String MAIL_STATUS_RETURNED="RTN";
	/**
	 * The status notArrived
	 */
	public static final String MAIL_STATUS_NOTARRIVED="NAR";
    /**
     * The Status Damaged
     */
    public static final String MAIL_STATUS_DAMAGED ="DMG";
	/**
	 * The status Captured But Not Accpeted
	 */
	public static final String MAIL_STATUS_CAP_NOT_ACCEPTED="CAP";
	public static final String MAIL_STATUS_HND="HND";
	/**
	 * The status New
	 */
	 public static final String MAIL_STATUS_NEW ="NEW";

    /**
     * The Status Transferred ...
     */
    public static final String MAIL_STATUS_TRANSFERRED ="TRA";

    /**
     * The Status Assigned ...
     */
    public static final String MAIL_STATUS_ASSIGNED ="ASG";

    /**
     * The Status Reassign mail
     */
    public static final String MAIL_STATUS_REASSIGNMAIL ="RSGM";

    /**
     * The Status Reassign despatch
     */
    public static final String MAIL_STATUS_REASSIGNDESPATCH ="RSGD";

	/* RESDIT Constants */
       /**
        * The ReceivedResdit
        */
       public static final String RESDIT_RECEIVED = "74";
       /**
        * The AssignedResdit
        */
        public static final String RESDIT_ASSIGNED = "6";
        /**
         * The UpliftedResdit
         */
        public static final String RESDIT_UPLIFTED = "24";
        /**
         * The PendingResdit
         */
        public static final String RESDIT_PENDING= "57";
        /**
         * The ResditDelivered
         */
        public static final String RESDIT_DELIVERED= "21";

        /**
		 * When mail is transferred from one flight to another flight of the same
		 * carrier
		 */
		public static final String RESDIT_HANDOVER_ONLINE = "41";

		/**
         * The HandedOverResdit
         */
        public static final String RESDIT_HANDOVER_OFFLINE = "42";

		public static final String RESDIT_HANDOVER_RECEIVED = "43";
        /**
         * The ReturnedResdit
         */
        public static final String RESDIT_RETURNED = "82";

        /**
         * Also called loaded/uplifted/departed
         */
        public static final String RESDIT_LOADED = "48";



        /**
         * This represents a uld
         */
        public static final String ULD_TYPE = "U";

        /**
         * This constant used to represent BULK
         */
        public static final String BULK_TYPE = "B";
        /**
         * The Constant for ALL
         */
        public static final String ALL="ALL";

        /**
         * Constant for flight information of  destination assignment
         */
        public static final int DESTN_FLT = -1;

        /**
         * The Flight Status To Be Action
         */
        public static final String FLIGHT_STATUS_TBA="TBA";


		public static final String CN37_CATEGORY_C = "C";

		public static final String CN37_CATEGORY_D = "D";
		//Added as part of CRQ ICRD-103713 by A-5526 starts


		public static final String CONSIGNMENT_TYPE_CN37 = "CN37";
		public static final String CONSIGNMENT_TYPE_CN46 = "CN46";
		public static final String CONSIGNMENT_TYPE_CN47 = "CN47";

        /**
         * The Flight Status Closed
         */
        public static final String FLIGHT_STATUS_CLOSED="C";
        /**
         * The Flight Status Open
         */
        public static final String FLIGHT_STATUS_OPEN="O";
        /**
         * The Flight Status Departed
         */
        public static final String FLIGHT_STATUS_DEPARTED="DEP";
        /**
         * The Flight Status Reconciled
         */
        public static final String FLIGHT_STATUS_RECONCILED="R";
        /**
         * The Flight Status Finalized
         */
        public static final String FLIGHT_STATUS_FINALIZED="F";
        /**
         * The Bulk
         */
        public  static final String CONST_BULK = "BULK";
        /**
         * The Seperator
         */
        public static final String SEPARATOR = "-";

        /**
         * The Offload Type For Container
         */
        public static final String OFFLOAD_CONTAINER="U";
        /**
         * The Offload Type For DSN
         */
        public static final String OFFLOAD_DSN="D";
        /**
         * The Offload Type For MailBag
         */
        public static final String OFFLOAD_MAILBAG="M";

        /**
         * The Offload Type Full
         */
        public static final String OFFLOADTYPE_FULL="F";
        /**
         * The Offload Type Partial
         */
        public static final String OFFLOADTYPE_PARTIAL="P";
        /**
         * The Operation Type Inbound
         */
        public static final String OPERATION_INBOUND = "I";
        /**
         * The OperationType OutBound
         */
        public static final String OPERATION_OUTBOUND = "O";
        /**
         * The FlagYes
         */
        public static final String FLAG_YES="Y";
        /**
         * The FlagScanned
         */
        public static final String FLAG_SCANNED="S";

        /**
         * The value One
         */
        public static final int ONE=1;
        /**
         * The Value Zero
         */
        public static final int ZERO=0;
         /**
          * The ReturnStatus Full
          */
        public static final String RETURN_STATUS_FULL = "F";
        /**
         * The Return Status Partial
         */
        public static final String RETURN_STATUS_PARTIAL = "P";
        /**
         * The Star Symbol
         */
        public static final String STAR = "*";
        /**
         * The Percentage Sign
         */
        public static final String PERCENTAGE  = "%";

        /**
         * The FlightStatus Open
         */
        public static final String AUDIT_FLIGHT_CLOSED="FLTCLOS";
        /**
         * The FlightStatus Closed
         */
        public static final String AUDIT_FLIGHT_OPENED="FLTOPN";
        /**
         * The Audit Constants
         */
        public static final String AUDIT_REASSIGN_MAILBAG="RSGNMAIL";
        /**
         * The Audit Constants
         */
        public static final String AUDIT_OFFLOAD_MAIL="OFFLMAIL";

        /**
         * The Audit Constants
         */
        public static final String AUDIT_OFFLOAD_CONTAINER="OFFLCON";


        /**
         * The Audit Constants
         */
        public static final String AUDIT_REASSIGNDESPATCHES="RSGNDSN";

        /**
         * The Audit Constants
         */
        public static final String AUDIT_RETURNDESPATCHES="RTNDSN";

        /**
         * The Audit Constants
         */
        public static final String AUDIT_AWB_ATTACHED ="AWBATTACH";

		public static final String RESDIT_EVENT_SENT = "S";

		public static final String AUD_CREAT_OFFL_CON = "CONCREATOFL";

		public static final String AUD_CREAT_RSGN_CON = "CONCREATRSGN";

		public static final String AUD_CREAT_ASGN_CON = "CONCREATASGN";

		public static final String AUD_CON_UPD = "CONUPD";

		public static final String AUD_CON_REM = "CONREM";

		public static final String EXCEPT_WARN = "EXPWARN";

		public static final String AUDIT_FLT_CREAT = "FLTCREAT";

		public static final String AUDIT_CONACP = "CONACP";

		public static final String AUDIT_DSN_ACP = "DSNACP";

		public static final String AUDIT_MAILACP = "MALACP";

		public static final String AUDIT_OFFLOADESPATCHES = "OFFLDSN";

		public static final String AUDIT_MAILRTN = "MALRTN";

		public static final String AUDIT_REASSIGN_CONTAINER = "RSGNCON";

		public static final String AUDIT_MALARRV = "MALARRV";

		public static final String EXCEPT_FATAL = "EXPERR";

		public static final String INCORRECT_DLV =
		"Airport for delivery is not the current port";

		public static final String DUPLICATE_MAIL_ERR = "Duplicate Mailbag";

		public static final String ERMSG_HANDOVER_RETURN =
			"Handedover mailbag cannot be returned";

		public static final String ERR_MSG_DUPLICATE_RETURN =
			"Mailbag already returned";

		public static final String ERR_MSG_NEW_ULD_ASG =
			"The container is already accepted to another open flight";

		public static final String DUPLICATE_ARRIVAL =
			"Mailbag already arrived";

		public static final String AUDIT_DSN_TRA = "DSNTRA";

		public static final String AUD_CON_TRA = "CONTRA";

		public static final String SEARCH_MODE_FLIGHT = "FLT";

		public static final String SEARCH_MODE_DESTN = "DESTN";

		public static final String SEARCH_MODE_ALL = "ALL";

        public static final String DUMMY_SUBCLS = "_";

        public static final String DOCUMENT_TYPE = "AWB";

        public static final String DOCUMENT_SUBTYPE = "L";

        public static final String MAIL_DOCUMENT_SUBTYPE = "M";

        public static final String MAIL_SERVICE_CARGO_CLASS = "F";

        public static final String MANIFEST_MAILBAG = "MAILMF";

        public static final String MANIFEST_DSN_MAILBAG = "DSNMBMF";

        public static final String MANIFEST_AWB = "AWBMF";

        public static final String MANIFEST_DESTCAT = "DCAATMF";

        public static final String WEIGHT_CODE = "K";

        public static final String BULK_TRASH = "TRASH";
        /**
         * Mail Discrepancy Type --Found
         */
        public static final String MAIL_DISCREPANCY_FOUND= "Found";
        /**
         * Mail Discrepancy Type --Missing
         */
        public static final String MAIL_DISCREPANCY_MISSING = "Missing";

		public static final String MAIL_TERMINATING = "MAILTERM";

		public static final String MAIL_TRANSHIP = "MAILTRA";

		public static final String MAIL_STATUS_ALL = "ALL";
		/**
		 * Added to include the Message type for  the Cardit
		 */
		public static final String MESSAGETYPE_CARDIT="CARDIT";

		/* RESDIT Acronym Constants */
	       /**
        * The ReceivedResdit
        */
       public static final String RESDIT_ACRON_RECV = "RC";
       /**
        * The AssignedResdit
        */
        public static final String RESDIT_ACRON_ASG = "AS";
        /**
         * The UpliftedResdit
         */
        public static final String RESDIT_ACRON_UPL = "UP";
        /**
         * The PendingResdit
         */
        public static final String RESDIT_ACRON_PEND = "PD";
        /**
         * The ResditDelivered
         */
        public static final String RESDIT_ACRON_DELV = "DL";
        /**
         * The HandedOverResdit
         */
        public static final String RESDIT_ACRON_HND = "HO";
        /**
         * The ReturnedResdit
         */
        public static final String RESDIT_ACRON_RET = "RT";

        public static final String RESDIT_ACRON_HND_ONL = "HL";

        public static final String RESDIT_ACRON_LOAD = "LD";

        /* Constants for Cardit Enquirty filght ttype */

        public static final String FLIGHT_TYP_CARDIT = "C";

        public static final String FLIGHT_TYP_OPR = "O";

        public static final String CARDITENQ_MODE_DOC = "C";

        public static final String CARDITENQ_MODE_DESP = "D";

        public static final String CARDITENQ_MODE_MAL = "M";

        /**
         * Mail Commodity Parameter Code
         */
        public static final String BOOKING_MAIL_COMMODITY_PARAMETER =
			"mailtracking.defaults.booking.commodity";

        public static final String RESDIT_CONFIG_CHECK =
        	"mailtracking.defaults.resditconfiguration.checkneeded";
        /**
         * Mail CARDIT CONSIGNMENT FILTER
         * Consignment details population will be filtered with the
         * values given in this System Parameter.
         */
        public static final String SYSPAR_CARDIT_CONSIGNMENT_FILTER =
			"mailtracking.defaults.cardit.consignmentfilter";

        /*
         * Transaction IDs for Resdit Configuration
         */
		public static final String TXN_ACP = "1";

		public static final String TXN_ASG = "2";

		public static final String TXN_DEP = "3";

		public static final String TXN_ARR = "4";

		public static final String TXN_TSFR = "5";

		public static final String TXN_RET = "6";

		/**
		 * Whether to check for NCA's business
		 */
		public static final String NCA_RESDIT_PROC =
			"mailtracking.defaults.resdit.ncaspecificcheckneeded";

		/**
		 * Time before departure for flagging RCVD Resdit
		 */
		public static final String RESDIT_RCVD_CONFTIM =
			"mailtracking.defaults.receivedresdit.configuredtime";

		public static final String MALCLS_SEP = ",";
		/* Category code for CN38 */
		public static final String MAIL_CATEGORY_AIR = "A";
		/* Category code for CN41 */
		public static final String MAIL_CATEGORY_SAL = "B";

		public static final String [] MILITARY_CLASS = {"1","2","3","4","5","6"};
		/**
		 * Civilian Letters code
		 */
		public static final String LETTERS_CODE = "U";
		/**
		 * Civilian Parcels code
		 */
		public static final String PARCELS_CODE = "C";
		/**
		 * Civilian Express Mail code
		 */
		public static final String EXPRESSMAIL_CODE = "E";

		public static final String CN38_REPORT_ID = "RPTOPR050";

		public static final String CN41_REPORT_ID = "RPTOPR051";

		public static final String AV7_REPORT_ID = "RPTOPR052";
		/**
		 * FPO Codes of AV7 - Military Mails
		 */
		public static final String [] APO_CODES = {"1" , "3" ,"5"};

		public static final String [] FPO_CODES = {"2" , "4" ,"6"};

		public static final String APO_LETTER_CODE = "1A" ;

		public static final String[] APO_PARCEL_CODES = { "1B" ,"5B" } ;

		public static final String [] FPO_LETTER_CODES = {"2A","2H" };

		public static final String[] FPO_PARCEL_CODES = { "2B" ,"2I","6B" } ;

		public static final String AUTO_PROCESS_NEEDED = "mailtracking.defaults.carditsave.autoprocessneeded";

		public static final String CN38_CATEGORY = "A";

		public static final String CN41_CATEGORY = "B";

		public static final String CONSIGNMENT_TYPE_CN38 = "CN38";

		public static final String CONSIGNMENT_TYPE_CN41 = "CN41";

		public static final String CARDIT_EVENT = "CDT";

		public static final String RESDIT_XX = "XX";

		public static final String XX_RESDIT_EVENT_CODE = "42";

		public static final String ULD_INTEGRATION_ENABLED = "mailtracking.defaults.uldintegrationneeded";
		public static final String FLIGHT_CLOSURE_ENABLED = "mailtracking.defaults.flightclosureneeded";
		public static final String UPLOAD_RECORDS_COUNT = "mailtracking.defaults.scanmailupload.noofrecordstouploadataatime";
		public static final String REALTIME_UPLOAD_REQUIRED = "mailtracking.defaults.scanmailupload.isrealtimeuploadrequired";
		public static final String EXPORT = "E";

		public static final String IMPORT = "I";

		public static final String SEND_FWB_NEEDED = "mailtracking.defaults.flightclosure.sendfwbneeded";

		public static final String ATTACH_AWB_SCC_CODE =
			"mailtracking.defaults.awbsccformail";

		public static final String DEFAULT_CUSTOMER_CODE = "mailtracking.defaults.defaultcustomercodeformail";

		public static final String PEAK_CARR = "KZ";

		/*
		 *
		 * Added By Karthick V to include the status for the Mail Revenue Accounting that  can be used to import  the
		 * data from the Mail operations .
		 */
		public static final String MRA_STATUS_NEW="N";

		public static final String MRA_STATUS_UPDATE="U";


		public static final String  MAILTRACKING_MONITORSLA="mailtracking.defaults.monitorsla";

//		/* Stock holder parameter for GHA */
//		 public static final String STOCK_HOLDER_PARAMETER =
//				"stockcontrol.defaults.defaultstockholdercodeforcto";
		/* Stock holder parameter for GHA */
		 public static final String STOCK_HOLDER_PARAMETER =
				"stock.defaults.defaultstockholdercodeforcto";

		/* Mail Stock holder parameter For the Station */
		public static final String 	STATION_STOCK_HOLDER_PARAMETER =
			 "mailtracking.defaults.defaultstockholderformail";

		/* Default Mail Stock holder parameter  , system parameter*/
		public static final String 	DEFAULT_STOCK_HOLDER_PARAMETER =
			 "mailtracking.defaults.defaultstockholdercode";

		/* Agent code for booking thru mail  , system parameter
		 * 10th April,2009  : Bug 44852 for AirNZ by Roopak
		 * */
		public static final String 	DEFAULT_AGENT_CODE_FOR_BOOKING =
			 "mailtracking.defaults.agentcodeforbooking";

		/**
		 * Added By Karthick V for  the Active Flag Indicator Changes
		 *
		 */
		public static final String FLAG_ACTIVE="A";

		public static final String FLAG_INACTIVE="I";


		//Added By Karthick V Ends

		public static final String FLAG_UPD_OPRULD =
				"mailtracking.defaults.saveoperationalulds";
		public static final String MAIL_AWB_SOURCE = "MAL";

		public static final String ARPULD_KEYSEP = "~";

		public static final String RESDIT_ATD_REQD = "mailtracking.defaults.isatdrequiredinresdit";

		public static final String CONSIGNMENT_TYPE_AV7 = "AV7";

		public static final String CONSIGN_REPORT_SEP = " - ";

		public static final String MIL_POST_APO = "APO";

		public static final String MIL_POST_FPO = "FPO";

		public static final String INVENTORY_ENABLED_FLAG =
			"mailtracking.defaults.inventoryenabled";

		public static final String MIL_MAL_CAT = "M";

		public static final String LABEL_SB = "(SB)";

		//Added for Mail Status report By Roopak - 10 March 2008 ANZ CR 297
		public static final String EXPECTED_MAIL = "Expected Mail";
		public static final String EXPECTED_MAIL_CARDIT = "Expected Mail through CARDIT";
		public static final String EXPECTED_MAIL_ALLOCATION = "Expected mail through Allocation";
		public static final String EXPECTED_MAIL_TRANSHIPS = "Expected Tranships through CARDIT";
		public static final String MAIL_WITHOUT_CARDIT = "Mails without CARDIT";
		public static final String MAIL_NOT_UPLIFTED = "Mails not Uplifted";
		public static final String MAIL_UPLIFTED_WITHOUT_CARDIT = "Mails Uplifted without CARDIT";
		public static final String MAIL_CARDIT_NOT_POSSESSED = "CARDIT Received but not scanned for Possession";
		public static final String MAIL_UPLIFTED_NOT_DELIVERED = "Mail Uplifted but not Delivered";
		public static final String MAIL_ARRIVED_NOT_DELIVERED = "Mail Arrived But Not Delivered";
		public static final String MAIL_DELIVERED = "Mail Delivered";
		public static final String MAIL_DELIVERED_WITHOUT_CARDIT = "Mail Delivered without CARDIT";
		public static final String OUT_FLT_NOT_FINALISED = "Outbound Flights Not Finalised";
		public static final String INB_FLT_NOT_FINALISED = "Inbound Flights Not Finalised";
		public static final String MAIL_SCANNED_INVALID_DESTINATIONS = "Mail Scanned To Invalid Destinations";
		public static final String OFFLOAD_MANIFEST = "Offload Manifest";
		//added for CR QF1543 on 10Nov10
		public static final String MAIL_ACCEPTED_NOT_UPLIFTED = "Accepted but not uplifted";
		public static final String MAILS_FOR_TRANSHIPMENT_FLIGHT = "mail.operations.mailsfortranshipmentflight";
		//QF1543 ends
		/*
		 * Added By  Karthick V as the part of the ANZ Mail Tracking CR
		 *
		 *
		 */
		public static final String CONST_CONTAINER = "CONT";
		public static final String CONST_CONTAINER_DETAILS = "CONTDTL";
		 public static final String RESTRICTED_PAS_FOR_DEVIATION_RESDITS = "mail.operations.restrictedPAsfordeviationresdits";
	        public static final String RDEVIATION_RESTRICTED_PAS_WITHOUT_CONSIGNMENT = "mail.operations.deviationrestrictedPAswithoutconsignments";

		/*
		 * This System Parameter is usedd to determine if system should allow mails to be imported to MRA or not
		 *
		 */

		public static final String IMPORTMAILS_TO_MRA_SYSPAR="mailtracking.defaults.importmailstomra";

		public static final String CONSIGNMENT_ROUTING_NEEDED_FOR_EXPORT_CLOSEFLIGHT =
			"mailtracking.defaults.consignmentroutingrequiredforexportcloseflight";

		public static final String CONSIGNMENTROUTING_NEEDED_FOR_IMPORT_CLOSEFLIGHT =
			"mailtracking.defaults.consignmentroutingrequiredforimportcloseflight";
		/*
		 * This System Parameter is usedd to determine if the xx Resdits has to be Proceesed for the System
		 *
		 *
		 */
		public static final String  XXRESDIT_REQUIRED_SYSPAR="mailtracking.defaults.resdit.xxresditneeded";

		/*
		 * Added By Karthick V as the part of the Air New Zealand Mail Tracking CR 404..
		 * On 10-Apr-2009, Nemil told to replace 'NC' by 'PP' for DWH impact.
		 */
	    public static final String  PAYMENT_TYPE_AWB="PP";

	    public static final String   SHIPMENTDESCRIPTION_FORAWB="mailtracking.defaults.shipmentdescriptionforawb";

	    /*
		 * Added By Paulson for Inbound acceptance logic - MTK552
		 */
	    public static final String MAIL_TXNCOD_ASG = "ASG";
		public static final String MAIL_TXNCOD_ARR = "ARR";


		//Added by paulson for Air NZ 432 (HHT)
		public static final String HHT_ACP = "ACP";
		public static final String HHT_ARR = "ARR";
		public static final String HHT_RET = "RTN";
		public static final String HHT_OFL = "OFL";
		public static final String HHT_TRA = "TRA";
		public static final String HHT_REASGMAIL = "REASGMAIL";
		public static final String HHT_REASGDSN = "REASGDSN";

		/**
		 * BULK Arrived at Airport
		 */
		public static final String CONST_BULK_ARR_ARP = "BULK-ARR";


		/**
		 * Added for MRA
		 *  - Property Deprecated
		 */
		@Deprecated
		public static final int NEG_VAL = -1;


		/**
		 * The FlagNO
		 */
		public static final String FLAG_NO="N";

		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : ORIGINAL
		 * -(First Transmission)
		 */
		public static final String CDT_TYP_ORIGINAL = "9";
		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : CONFIRM
		 * -(Confirmation of information from RESDIT)
		 */
		public static final String CDT_TYP_CONFIRM = "6";
		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : UPDATE
		 * -(Replacement Of Previous CARDIT)
		 */
		public static final String CDT_TYP_UPDATE = "4";
		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : FINAL
		 * -(Definitive: Last Transmission)
		 */
		public static final String CDT_TYP_FINAL = "47";
		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : CANCEL
		 * -(Cancellation)
		 */
		public static final String CDT_TYP_CANCEL = "1";
		/**
		 * @author
		 * @since
		 * M39
		 * @since
		 * CARDIT TYPE : CORRECTION
		 * -(Definitive: Last Transmission)
		 */
		public static final String CDT_TYP_CORRECTION = "5";

		/**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * M39
		 * @since
		 * Belly Cart ID used in cardit
		 */
		public static final String CDT_BLY_CART_ID = "TE";

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
		 * IHR
		 * Added by Sandeep
		 */

		 public static final String INHOUSE_RATE = "I";

		 /*
		  * FDR
		  *  Added by Sandeep
		  */
		 public static final String FIVEDAY_RATE = "F";

		 /**
		  * MINIMUM_VOLUME = 0.01D
		  */
		 public static final double  MINIMUM_VOLUME = 0.01D;
		 /**
		  * NO_VOLUME = 0.00D
		  */
		 public static final double  NO_VOLUME = 0.00D;

		 /**
		  * PAGE SIZE = 25
		  */
		 public static final int MAX_PAGE_LIMIT =150;


		 public static final String  UPLOAD_EXCEPT_ALREADY_RETURNED = "mailtracking.defaults.mailbagalreadyreturned";
		 public static final String  UPLOAD_EXCEPT_ALREADY_DELIVERED = "mailtracking.defaults.mailbagalreadydelivered";
		 public static final String  UPLOAD_EXCEPT_ALREADY_OFFLOADED = "mailtracking.defaults.mailbagalreadyoffloaded";
		 public static final String  UPLOAD_EXCEPT_MAILBAG_DOESNOT_EXISTS = "mailtracking.defaults.mailbagdoesnotexists";
		 public static final String  UPLOAD_EXCEPT_MAILBAG_NOT_IN_AIRPORT = "mailtracking.defaults.mailbagdoesnotexistinairport";
		 public static final String  UPLOAD_EXCEPT_MAILBAG_CANNOT_BE_DELIVERED = "mailtracking.defaults.mailbagcannotbedelivered";
		 public static final String  UPLOAD_EXCEPT_FLIGHT_DEPARTED = "mailtracking.defaults.warn.flightdeparted";
		 public static final String  UPLOAD_EXCEPT_FLIGHT_CLOSED = "mailtracking.defaults.err.flightclosed";
		 public static final String  UPLOAD_EXCEPT_NO_FLIGHT_DETAILS = "mailtracking.defaults.noflightdetails";
		 public static final String  UPLOAD_EXCEPT_MAILBAG_NOT_ACP_TO_FLT = "mailtracking.defaults.mailbagnotacceptedtoflight";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_NOT_ACP_TO_FLT = "mailtracking.defaults.containernotacceptedtoflight";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_OFFLOAD_FLT_NOT_CLOSED = "mailtracking.defaults.flightnotclosedforoffloading";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_ALREADY_OFFLOADED = "mailtracking.defaults.containeralreadyoffloaded";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_NOT_IN_FLIGHT = "mailtracking.defaults.containernotinflight";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_NOT_IN_CARRIER = "mailtracking.defaults.containernotincarrier";
		 public static final String  UPLOAD_EXCEPT_MAILBAG_ALREADY_TRANSFFERED = "mailtracking.defaults.mailbagalreadytransffered";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_NOT_ACCEPTED_TO_SYSTEM = "mailtracking.defaults.containernotacceptedtosystem";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_NOT_IN_AIRPORT = "mailtracking.defaults.containerdoesnotexistinairport";
		 public static final String  UPLOAD_EXCEPT_CONTAINER_ALREADY_EXIST_IN_SAME_FLIGHT = "mailtracking.defaults.containeralreadyexistinsameflight";
		 public static final String  UPLOAD_EXCEPT_CONT_ALRDY_EXST_IN_SAME_FLT_WIT_DIFF_CONTYPE = "mailtracking.defaults.containeralreadyexistinsameflightwithdiffcontainertype";
		 public static final String  BULK_IN_FLIGHT_NOT_CLOSED = "mailtracking.defaults.flightforbulknotclosed";
		 public static final String  BULK_ALREADY_ASSIGNED_TOCARRIER = "mailtracking.defaults.bulkalreadyassignedtocarrier";
		 /**
		  * The ErrorCode For Container Already Assigned to Same Flight
		  */
		 public static final String ALREADY_ASSIGNED_TOSAMEFLIGHT = "mailtracking.defaults.sameflight";
		 public static final String CON_ASSIGNEDTO_DIFFFLT =
			 "mailtracking.defaults.uldalreadyassignedtoflgt";
        /**
		 * Added for OF 1258
		 */
		 public static final String EXC_ACCEPTED="A";
		 public static final String EXC_NOTACCEPTED="N";
		 public static final String EXC_FINALISE="F";
		 public static final String EXC_RECONCILE="R";
		 public static final String OPERATION_TRANSSHIPMENT="T";


		 /**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * CR : QF 1322 (ULD Acquittal at non scannable ports)
		 * @since
		  * ULD ACQUITTAL ENABLED SYSTEM PARAMETER
		 */
		 public static final String ULD_ACQUITTAL_ENABLED="mailtracking.defaults.uldacquittalenabled";

		 /**
		 * @author A-3227 RENO K ABRAHAM
		 * @since
		 * CR : QF 1322 ULD Acquittal at non scannable ports)
		 * @since
		  * ULD ACQUITTAL GRACE PERIOD SYSTEM PARAMETER
		 */
		 public static final String ULD_ACQUITTAL_GRACE_PERIOD="mailtracking.defaults.uldacquittalgraceperiod";

		 public static final String DEFAULT_CARRIER_LEVEL_TYPE_COUNTRY = "Co";
		 public static final String DEFAULT_CARRIER_LEVEL_TYPE_CITY = "Ci";


		 public static final String INVOIC_CLAIM_CODE_WEIGHT_MISMATCH = "WXX";
		 public static final String INVOIC_CLAIM_CODE_ORIGIN_DESTN_MISMATCH = "ODC";
		 public static final String INVOIC_CLAIM_CODE_NO_PAY_RECORD = "NPR";
		 public static final String INVOIC_CLAIM_CODE_RATE_MISMATCH = "RVX";
		 public static final String MANUAL_DELIVERY_REQ = "mailtracking.defaults.additionaldeliveryoperationrequired";

		 public static final String TRANSFER_FROM_REASSIGN = "TRANSFER_FROM_REASSIGN";


		 public static final String USPOST = "US101";

		 public static final String ARP_ALL="XXX";
		 public static final String POACOD_ALL="XXX";
		 public static final String CARDIT_STATUS_ORIGINAL = "CDTORG";
		 public static final String CARDIT_STATUS_UPDATE = "CDTUPD";
		 public static final String CARDIT_STATUS_CANCELLATION = "CDTCNC";
		 public static final String CARDIT_STATUS_FINAL = "CDTFNL";
		 public static final String CARDIT_STATUS_CORRECTION_ADDED = "CDTCRTADD";
		 public static final String CARDIT_STATUS_CONFIRMATION = "CDTCNF";
		 public static final String CARDIT_STATUS_CORRECTION_DELETED= "CDTCRTDEL";
		 public static final String CARDIT_STATUS_CONFIRMATION_DELETED= "CDTCNFDEL";

		 public static final String CARDIT_STATUS_CARDIT_V20_V11= "CDT";

		 /**
		  * Added by A-2135 For QF CR 1517
		  */
		 public static final String RESDIT_EVENT_GENERATED = "G";

		 //CRQ_QF1540_SowmyaK_22Oct10
		 //commented by RENO to reduce the Container Number Length.
		 //Inorder to accomodate in Taurus without propagating any change for them
		 //public static final String CONST_BULK_ACQ_ARP = "BULK-ACQ";
		 public static final String CONST_BULK_ACQ_ARP = "ACQ";
		 public static final String STATUS_ACQUITTED = "A";
		 public static final String STATUS_PENDING = "P";
		 //CRQ_QF1540_SowmyaK_22Oct10 ends

		 // Added for QFCTO starts
		 public static final String MWB_AWB_SOURCE = "MWB";
		 // Added for QFCTO ends

		//Added by A-5220 for ICRD-21098 starts
			public static final String MAIL_OPERATIONS_DENSE_RANK_QUERY =
				"SELECT RESULT_TABLE.* , DENSE_RANK() OVER ( ORDER BY ";

			public static final String MAIL_OPERATIONS_ROWNUM_QUERY =
				"SELECT RESULT_TABLE.* ,  ROW_NUMBER () OVER (ORDER BY NULL)  AS RANK FROM ( ";

			public static final String MAIL_OPERATIONS_SUFFIX_QUERY = " )RESULT_TABLE";
			//Added by A-5220 for ICRD-21098 ends


			//Added for ICRD-63167 moving Cardit Resdit from QF to Base Starts
			public static final String AU_POST = "AU101";
	        /**
	         * The Resdit Ready for Delivery
	         */
	        public static final String RESDIT_READYFOR_DELIVERY= "23";
	        public static final String SYSPAR_USPS_ENHMNT = "mailtracking.defaults.enableuspsresditenhancements";
			//ICRD-63167 ENDS
			public static final String MAIL_STATUS_EXPORT= "EXP";
//added for ICRD-85233
	        public static final String RESDIT_ARRIVED= "40";
	        //added for ICRD-80366
	        public static final String RESDIT_TRANSPORT_COMPLETED = "14";
	        /**
	         * The minimum flight number length
	         */
	        public static final int MIN_FLIGHT_NUMBER_LENGTH = 4;

	        public static final String FRM_JOB = "JOB";
	        public static final String SCAN = "SCAN";//Added for ICRD-156218
	        public static final String MTKMALUPLJOB = "MTKMALUPLJOB";//Added for ICRD-156218
	        public static final String MAIL_ARRIVAL_NEEDED = "mailtracking.defaults.arrivalrequiredforonlineflights";
	        public static final String IS_RESDITMESSAGING_ENABLED =
				"mailtracking.defaults.resdit.isresditmessagingenabled";
	        //added for ICRD-117307
	        public static final String RETURN_NOT_POSSIBLE_AT_IMPORT="mailtracking.defaults.returnpossibleonlyatacceptanceport";
	        public static final String MAIL_RESDIT_TRIGGER = "MAL_RDT_BDR_JOB";
	        //Added by A-5945 for ICRD-129920
	        public static final String STATION_GROUPING_FOR_RESIDIT_REQUIRED = "mailtracking.defaults.stationgroupingrequiredforresdit";
	       // Added by A-5945 for ICRD-135116
	         public static final String CARDIT_ONCE_CANCELLED= "mailtracking.defaults.cardit.onceCancelled";
	         public static final String MAILBAG_ACTIVE_UNDER_ANOTHERCONSIGNMENT ="mailtracking.defaults.cardit.mailbagactiveunderanotherConsignment";
	         public static final String MAILBAG_IS_AWB_ATTACHED ="mailtracking.defaults.cardit.awbisalreadyattachedtothemailbag";
	         public static final String CONSIGNMENT_HAVING_AWB_ATTACHED_MAILBAGS ="mailtracking.defaults.cardit.consignmenthavingawbattachedmailbags";
	         public static final String WRONG_POL_ENTERED = "mailtracking.defaults.wrongpol";

//merge solved by A-7794

	        //Added by A-7794 as part of ICRD-208677
	         public static final String MAIL_CONST = "mail";
	         public static final String MAIL_ULD_ASSIGNED = "Mail ULD Assigned";
	         public static final String FLT_CLOSED = "Mail Flight Closed";
	         public static final String MAIL_ULD_ARRIVED = "Mail ULD Arrived";
	         public static final String MAIL_ULD_OFFLOAD = "Mail ULD Offload";
	         public static final String MAIL_ULD_REASSIGN = "Mail ULD Reassigned";
	         public static final String MAIL_ULD_TRANSF = "Mail ULD Transferred";
	         public static final String DESTN_FLT_STR = "-1";
	         public static final String M49_1_1 ="1.1 M49";
	         public static final String RESDIT_PENDING_M49 ="59";
	         public static final String CHANGE_SCAN_TIME ="CHGSCNTIM";//Added For ICRD-140584
	       //Added by A-6991 for ICRD-77772 Starts
	         public static final String UCM_TRIGGERED_FOR = "uld.defaults.ucmtrigger";
	         public static final String UCM_ULD_SOURCE_MAIL = "M";

	       //Added by A-6991 for ICRD-77772 Ends
	         //Added by A-7794 as part of ICRD-223754
	         public static final String UPU_CODE = "UPUCOD";
	         public static final String MAIL_STATUS_AWB_BOOKED="BKD";
	         public static final String MAIL_STATUS_AWB_CANCELLED="CAN";
	         public static final String SYS_PARA_TRIGGER_FOR_MRAIMPORT="mailtracking.mra.triggerforimport";
	         public static final String MAIL_STATUS_TRANSFER_MAIL="TRA_MAL";
	         public static final String MAIL_STATUS_TRANSFER_CONTAINER="TRA_CON";
	         public static final String CARGO_OPS_DEFAULT_OFFLOAD_REASON= "UNKNOWN";
	         public static final String CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE= "62";
	         public static final String MAIL_SOURCE_MAWB_OFFLOAD = "OPSOFL";
	         public static final String MEASUREUNITQUALIFIER_POUND="LBR";
	         public static final String MEASUREUNITQUALIFIER_KILO="KGM";
	         public static final String WEIGHTCODE_POUND="L";
	         public static final String WEIGHTCODE_KILO="K";
	         public static final String MAIL_SOURCE_MAWB_OFFLOAD_JX = "OPSOFL_JX";



		       //Added for CRQ ICRD-135130 by A-8061 starts
	         public static final String MLD_STG="STG";
	         public static final String MLD_NST="NST";
	         public static final String MLD_RCF="RCF";
	         public static final String MLD_TFD="TFD";
	         public static final String MLD_RCT="RCT";
	         public static final String MLD_RET="RET";
	       //Added for CRQ ICRD-135130 by A-8061 end

	         public static final String IS_COTERMINUS_CONFIGURED="mailtracking.defaults.iscoterminusconfigured";
	         //added by A-7371 as part of ICRD-240340
	         public static final String MALTYP_DOMESTIC="D";
	         public static final String MALTYP_INTERNATIONAL="I";
	         public static final String CONTAINER_POU_DIFFERENT_FROM_DEST ="mailtracking.defaults.containerpoudifferentfromdestination";
	         public static final String EMBARGO_VALIDATION ="mailtracking.defaults.warning.embargovalidation";
	         public static final String LAT_VIOLATED_WAR = "mailtracking.defaults.warning.latvalidation";
	         public static final String NOT_COTERMINUS_AIRPORT="mailtracking.defaults.warning.notcoterminusairport";
	         public static final String INVALID_ACCEPTANCE_AIRPORT="mailtracking.defaults.warning.invalidacceptanceairport";
	         public static final String CONTAINER_REASSIGN_WARNING="mailtracking.defaults.warning.containerressaign";
	         public static final String CONTAINER_REASSIGN_ERROR="mailtracking.defaults.error.containerressaign" ;
	         //Added by A-4809
	         public static final String CARDIT_PROCESS="CDTPRC";
	         public static final String DELVRY_SCAN="DLVSCN";
	         public static final String EXCEL_UPLOAD="EXLUPL";
	         public static final String RESDIT="RESDIT";
	         public static final String PROCESS_MANAGER="PRCMGR";
	         public static final String MAIL_RETURN="MALRTN";
	         public static final String MAIL_TRANSFER="MALTRF";

	         public static final String FORCE_MAJEURE_REQUEST = "FMR";
	         public static final String DUMMY_DATE_FOR_FMR ="01-JAN-1990";
	         public static final String OK_STATUS ="OK";
	         public static final String FORCE_MAJEURE ="FM";
	         public static final String INITIATED_STATUS ="I";
	         public static final String MAIL_SUBSYSTEM ="M";
	         public static final String COMPLETED ="C";
	         public static final String FAILED ="F";
	         public static final String FORCE_MAJEURE_UPDATED =" updated";
	         public static final String FORCE_MAJEURE_CREATED =" created";
	         public static final String FORCE_MAJEURE_CANNOT_UPDATE ="Force Majeure request not updated ";
	         public static final String FORCE_MAJEURE_CANNOT_CREATE ="Force Majeure request not created ";
	         public static final String FORCE_MAJEURE_ID ="Force Majeure request ID: ";

	        //Added by A-7540
	         public static final String MAIL_SRC_RESDIT = "RESDIT";
	         public static final String USPS_INTERNATIONAL_PA="mailtracking.defaults.uspsinternationalpa";

	         public static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	         public static final String GPA_REBILL = "GR";	
	         
//*Added as part of ICRD-229584 
	          public static final String MAILBAG_NOT_ARRIVED = "The mailbag is not arrived"; 
	          public static final String MAIL_AWB_PRODUCT =
	      			"mail.operations.productCode";
	          
	          public static final String FLAG_OTHERS="X";
	          public static final String FLAG_NOT_AVAILABLE="NA";
	          public static final String PARAMETER_TYPE_DEFAULT_STATION_UNIT_WEIGHT = "station.defaults.unit.weight";
	          
	          public static final String MAILBAG_CSG_DOESNOT_EXIST ="mailtracking.defaults.cardit.consignmentdoesnotexist";
	          public static final String CARRIER_PRE_QUALIFIER = "10";
	      	  public static final String ANDROID_MSG_ERR_INVALID_DESTINATION = "mailtracking.defaults.invaliddestination";
              public static final String ULD_INCOMPATIBLEAIRCRAFT="shared.uld.aircraftincompatible";
              public static final String ALL_RETURN_ENABLED_PA="All";
	          
           //Added by A-8488 as part of ICRD-134563
	        public static final String MLD_VERSION_TWO= "2";
	        public static final String SYSPAR_FAIL_ATTACHEDA_AWB ="mailtracking.defaults.carditsave.failAlreadyAttached";
	          
	        public static final String MAIL_OUTBOUND_SCRIDR="MTK060";
	        public static final String MAIL_INBOUND_SCRIDR="MTK064";
			public static final String MODE_OF_DWS_SYNC_FOR_MAIL = "mail.operations.modeofdwssyncformail";
			public static final String MODE_OF_DWS_SYNC_REAL_TIME = "R";
			public static final int FLIGHT_NUMBER_LENGTH = 5;
			public static final String PAD_DIGIT = "0";
			public static final String MAIL_LAT_OFFSET_CONFIGURATION_TYPE = "MLT";
			public static final String MAIL_LAT_OFFSET_PARAMETER_CODE = "DOMFLT";
			public static final String MAIL_LAT_OFFSET_PARAMETER_VALUE = "yes";
			public static final String MAIL_LAT_OFFSET_CONFIGURATION_RULE_HOURS = "Hrs";
			public static final String MAIL_LAT_OFFSET_CONFIGURATION_RULE_MINUTES = "Min";
			public static final String MAIL_MLD_ENABLED_SEND="mail.operations.mldsendingrequired";
			public static final String CUSTOMER_TYPE_GPA = "GPA";
			public static final String CUSTOMS_POSTAL_AUTHORITY_CODE_PRFCOD = "CUSPOACOD";
			public static final String MAIL_SOURCE_HHT_MAILINBOUND = "SCAN:HHT045"; ////added by A-9529 for IASCB-44567
			public static final String MAIL_SOURCE_HHT_MAILOUTBOUND = "SCAN:HHT046"; ////added by A-9529 for IASCB-61108
			public static final String MAIL_STORAGE_UNIT_NOT_FOUND_ERR_CODE = "operations.mailtracking.defaults.storageunitnotfound";
	          
			public static final String SYNC_ACTUAL_WEIGHT_TO_DWS_FUNCTION_POINTS = "mail.operations.syncactualweighttodwsfunctionpoints";
	        public static final String FLIGHT_DEPARTURE="DEP"  ;
	        public static final String FLIGHT_ARRIVAL="ARV"  ;

	        public static final String INVALID_DELIVERY_PORT="mail.operation.error.invaliddeliveryport" ;

	        public static final String READY_FOR_DELIVERY ="RFD";

	        public static final String FORCE_ACCEPTANCE ="FORCEACP";
	        public static final String AUTO_ATTACH_AWB_MINIMUM_DURATION ="mail.operations.autoattachawb.minimumduration";

	        public static final String TRANSFER_OUT="TRA_OUT";
	        public static final String TRANSFER_IN="TRA_IN";
            public static final String MAIL_STATUS_HNDRCV="HDR";
            public static final String HNDOVR_CARRIER="Handover Carrier :";	 
            public static final String TRANSFER_REJECTED ="TRR";
	        public static final String TRANSFER_INITIATED ="TRI";   
			public static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	        public static final String SCREEN = "SCREEN";
	        public static final String REASON_CODE_CS = "CS";
	        public static final String REASON_CODE_SM = "SM";
	        public static final String DOM_MAILBAG_DEF_DSNVAL="0001";
	        public static final String DOM_MAILBAG_DEF_RSNVAL="001";
	        public static final String PA_BUILT_ADD_INFO="SB";

	        
	        public static final String RESDIT_STAMPING_PRECHECK_ENABLED = "mail.operations.resditstampingprecheckenabled";
	        public static final String MESSAGE_ENABLED_PARTIAL = "P";
	        public static final String MESSAGE_ENABLED_EXCLUDE = "E";
	        public static final String ONLOAD_MESSAGE = "SCAN:ONLOAD";
	        public static final String CONSIGNMENT_SUMMARYTYPE_CN46 = "CNSummary46";
	        public static final String YYYYMMDD="yyyymmdd";
	        public static final String YYYY_MM_DD="yyyyMMdd";
	        public static final String TROLLY_EXCEPTION ="ProxyException--generate trolley number excep";
	        public static final String MRD = "MRD";
            public static final String KEYCONDITION ="getKeyCondition";
            public static final String INV_HANDOVERS="invalid_handovers";
            public static final String INVALID_MAILBAG="invalid_mailbag";
            public static final String CONCOUNT="CONCOUNT";
            public static final String CONTAINER_ACCEPTANCE = "Accepted";
            public static final String CONTAINER_ASSIGNMENT = "Assigned";
            public static final String CONTAINER_OFFLOAD = "Offloaded";
            public static final String CONTAINER_DELETED = "Deleted";
            public static final String CONTAINER_UNASSIGNED = "Unassigned";
            public static final String CONTAINER_REASSIGN = "Reassigned";
            public static final String CONTAINER_UPDATION = "Updated";
            public static final String CONTAINER_ARRIVED = "Arrived";
            public static final String CONTAINER_DELIVERED = "Delivered";
            public static final String CONTAINER_TRANSFER = "Transferred";
            public static final String MAIL_ULD_TRANSFER = "Container Transfered";
            public static final String IPC_MRD = "IPC MRD";
            public static final String CONTAINER_ACQUITED = "Acquited";
            public static final String CONTAINER_RETAINED = "Retained";
            public static final String CONTAINER_NUMBER = "CONNUM";
			public static final String DSN_VIEW = "DSN_VIEW";
            public static final String CONTAINER_JOURNEY_ID= "J";
            public static final String RESDIT_LOST="49";
            public static final String RESDIT_FOUND="37";
			public static final String YES = "Yes";
	        public static final String CARDITDOM = "CARDITDOM";
	        public static final String ORIGIN_DESTINATION_UPDATE = "ORGDSTUPD";
			public static final String MAILOUTBOUND = "MailOutbound";
			public static final String MAIL_OPERATIONS_ATD_MISSING = "mail.operations.atd.missing";
			public static final String EXPORT_FLIGHT_CLOSED ="FLIGHT CLOSED FOR EXPORT MAIL OPERATIONS";
			public static final String IMPORT_FLIGHT_CLOSED ="FLIGHT CLOSED FOR IMPORT MAIL OPERATIONS";
			public static final String EXPORT_FLIGHT_REOPEN ="FLIGHT REOPENED FOR EXPORT MAIL OPERATIONS";
			public static final String IMPORT_FLIGHT_REOPEN ="FLIGHT REOPENED FOR IMPORT MAIL OPERATIONS";
			public static final String REQ_TRIGGERPOINT = "REQ_TRIGGERPOINT";
			public static final String WARNING ="W";
			public static final String INFO ="I";
			public static final String ERROR ="E";
			public static final String EXPFLTFIN_ACPMAL="EXPFLTFIN_ACPMAL";
			public static final String MAL_FUL_IND="FUL";
			public static final String MLD_VERSION1="1";
			public static final String MLD_VERSION2="2";
			
			public static final String SECURITY_REASON_CODE_SCREENING ="SM";
			public static final String SECURITY_REASON_CODE_CONSIGNOR ="CS";
			public static final String SCREEN_LEVEL_VALUE ="M";
			public static final String RA_ACCEPTING="RA";
			public static final String RA_ISSUING="RI";
			public static final String SOURCE_HHT="HHT";
			public static final String RESULT="P";
			public static final String ACCOUNT_CONSIGNOR="AC";
			public static final String KNOWN_CONSIGNOR="KC";
			public static final String REGULATED_CARRIER="RC";
            public static final String SECURITY_STATUS_CODE_SPX="SPX";
            public static final String NON_EU_INDICATOR="N";
			public static final String EU_INDICATOR="E";
			public static final String GREEN = "GRN";
	        public static final String BLUE = "BLU";
	        public static final String EU = "EU";
			public static final String MAILOUTBOUND_SCREEN = "Outbound";
			public static final String ACCEPTANCE_FLAG = "Y";
			public static final String FROMDETACHAWB = "Y";
			
			public static final String MAILBAG_LEVEL_TRANSACTION="M";
			public static final String ULD_LEVEL_TRANSACTION ="U";
			public static final String SECURITY_WARNING_VALIDATION= "mail.operations.warning.securityvalidationwarning";
			public static final String SECURITY_ERROR_VALIDATION= "mail.operations.warning.securityvalidationerror";
			public static final String ORG_COUNTRY="ORGCNT";
			public static final String DEST_COUNTRY="DSTCNT";
			public static final String TXN_COUNTRY="TXNCNT";
			public static final String TXN_ARP="TXNARP";
			public static final String SCC_GRP="SCCGRP";
			public static final String SECURITY_STATUS_CODE_NSC="NSC";
            public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
            public static final String MAIL_OPERATION_RESDIT_GROUPING_PARAMETERS = "mail.operations.resditgroupingparameters";
            public static final String RDTEVT = "RDTEVT";
            public static final String HDLCAR = "HDLCAR";
            public static final String STATION = "STN";
            public static final String POSTAL_AUTHORITY_CODE = "POACOD";
	         public static final String SYS_PARA_TRIGGER_FOR_MRA_PROVISIONAL_RATE_IMPORT="mailtracking.defaults.importsmailstomra";	
              public static final String APPLICABLE_REGULATION_ERROR="mail.operations.applicableregulationerror";
			public static final String AUSTRALIA = "AU";
			public static final String AUSTRALIAN_CUSTOMS = "ACS";
			public static final String CAN = "CAN";
			public static final String EXML = "EXML"; 
			public static final String SENDER_ID_LEADING_VALUE = "Sender ID:";
			public static final String SENDER_RECIPIENT_SEPERATOR = "|";
			public static final String RECIPIENT_ID_LEADING_VALUE = "Recipient ID:";
			public static final String MAIL_CONTROLLER_BEAN = "mAilcontroller";
            public static final String MLD_VERSION0="0";
            public static final String CARDIT_TYPE_ACP = "ACP";
	        public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYYMMDDHHMM =
			DateTimeFormatter.ofPattern("yyyyMMddHHmm");
			public static final String DATE_TIME_FORMAT="yyyyMMddHHmm";


	        public static final DateTimeFormatter DATE_TIME_FORMATTER_YYYY_MM_DD =
			DateTimeFormatter.ofPattern("yyyy-MM-dd");

			public static  final DateTimeFormatter DISPLAY_DATE_ONLY_FORMAT  = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	        public static final String DATE_TIME_FORMAT_WITH_HYPHENS="yyyy-MM-dd";
	        public static final String DATE_TIME_FORMAT_WITH_HYPHENS_DD_MMM_YYYYY="dd-MMM-yyyy";

}

