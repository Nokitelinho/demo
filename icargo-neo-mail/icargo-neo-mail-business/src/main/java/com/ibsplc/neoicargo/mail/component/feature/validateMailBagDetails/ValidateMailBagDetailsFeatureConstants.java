package com.ibsplc.neoicargo.mail.component.feature.validateMailBagDetails;

import org.springframework.stereotype.Component;

@Component
public final class ValidateMailBagDetailsFeatureConstants {


	private ValidateMailBagDetailsFeatureConstants() {
	}

	/** 
	* System Parameters
	*/
	public static final String DOMESTICMRA_USPS = "mailtracking.domesticmra.usps";
	public static final String USPS_INTERNATIONAL_PA ="mailtracking.defaults.uspsinternationalpa";
	public static final String SYSPAR_IMPORT_HANDL_VALIDATION = "operations.flthandling.enableatdcapturevalildationforimporthandling";
	public static final String EMBARGO_VALIDATION_REQUIRED = "mail.operation.embargovalidationrequired";
	public static final String DEFAULTCOMMODITYCODE_SYSPARAM = "mailtracking.defaults.booking.commodity";
	public static final String DEST_FOR_CDT_MISSING_DOM_MAL = "mail.operation.destinationforcarditmissingdomesticmailbag";
	public static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	/**
	* Airport Parameters
	*/
	public static final String SHARED_ARPPAR_ONLARP = "operations.flthandling.isonlinehandledairport";

	/**
	 * Errors
	 */
	public static final String LAT_VIOLATED_ERR = "mailtracking.defaults.err.latvalidation";
	public static final String LAT_VIOLATED_WAR = "mailtracking.defaults.war.latvalidation";
	/**
	 * CONTEXT OBJECTS
	 */
	public static final String LATEST_CONTAINER_ASSIGNMENT_VO = "LATEST_CONTAINER_ASSIGNMENT_VO";
	}
