/*
 * UCMValidator.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.message;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.ULD;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1950
 *
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class UCMValidator {

	private Log log = LogFactory.getLogger("ULD");

	// isAirlineGHA------whether it is an AirlineGHA or OtherAirlineGHA
	private static final boolean ISAIRLINE_GHA = true;

	//is it an Airline or GHA
	private static final boolean IS_GHA = true;

	private static final String OUT = "OUT";

	private static final String IN = "IN";

	/*
	 *Duplicated UCMOUT
	 */
	private static final String E_ONE = "E1";

	/*
	 *Duplicated UCMIN
	 */
	private static final String E_TWO = "E2";
	/*
	 * ULD is not in the System
	 */
	private static final String E_THREE = "E3";

	/*
	 * ULD is NonOperational
	 */
	private static final String E_FOUR = "E4";


	/*
	 * ULD is not int the stationStock and it is in Intransit condition of other station
	 */
	private static final String E_FIVE = "E5";

	/*
	 * ULD is not in station stock.But it is inStation condition of another station stock
	 */
	private static final String E_SIX = "E6";

	/*
	 * ULD is not in Airline Stock.
	 */
	private static final String E_SEVEN = "E7";


	/*
	 * The last movement date  is greater than the current date
	 */
	private static final String E_EIGHT = "E8";

	/*
	 * The error is logged in when there is any mismatch between the UCMIN AND COUNTER UCMOUT
	 * eg: Suppose 1) out message is completely missing for the IN
	 * 			   2) When there are some missing ulds in UCMIN when compared to UCMOUT
	 * 			   3) When there are some extra ulds in UCMIN compared to UCMOUT
	 * 			   4) When a UCMIN against a dup out has struck the system , then E9 is logged in.
	 *
	 */
	private static final String E_NINE = "E9";

	/*
	 * The error is logged in when a UCMOUT is struck after a UCMIN has strucked
	 */
	private static final String E_TWELVE = "E12";

	/*
	 * The error is caused when a UCMIN is going to b saved againt Duplicate UCMOUT
	 */
	private static final String E_THIRTEEN = "E13";

	/*
	 * The error is stacked against a ULD when the login Airline is different from the flight Airline.
	 * For reconciliation , do loan transaction to the flight Airline if uld is ours or of another Airline
	 * which is different from flight airline
	 * if uld is that of flight Airline , do return transaction
	 *
	 */
	private static final String E_FOURTEEN = "E14";

	/*
	 * ErrorCode is attached when the uld is in the station but it is in INTRANSIT status
	 */
	private static final String E_FIFTEEN = "E15";



	/*
	 * Whether the uld is intransit status
	 */
	private static final String INTRANSIT = "Y";

	private static final String NON_OPERATIONAL = "N";

	//Added by A-7359 for ICRD-220123 starts here
	private static final String ULD_NOT_IN_AIRPORT = "uld.defaults.errortype.notinairport";
	private static final String ULD_NOT_IN_AIRPORT_FOR_OAL = "uld.defaults.errortype.notinairportforOAL";
	private static final String ULD_NOT_IN_AIRLINESTOCK_FOR_OAL = "uld.defaults.errortype.notinairlinestockforOAL";
	private static final String ULD_NOT_IN_AIRLINESTOCK = "uld.defaults.errortype.notinairlinestock";
	public static final String INVALID_AIRLINE = "shared.airline.invalidairline";
	public static final String ERROR = "E";
	private Map<String,Collection<String>> errorCollection=new HashMap<String,Collection<String>>();
	private Collection<String> errorSevenOAL= new ArrayList<String>();
	private Collection<String> errorSevenOWN= new ArrayList<String>();
	private Collection<String> errorFiveSixOAL= new ArrayList<String>();
	private Collection<String> errorFiveSixOWN= new ArrayList<String>();
	private Collection<String> parCodes=new ArrayList<String>();
	private Map<String,String> parameterVal=new HashMap<String,String>();
	private static final String ULD_NOT_IN_AIRPORT_VALIDATION = "uld.defaults.notinairportvalidationrequired";
	
	//Added by A-7359 for ICRD-220123 ends here
	/**
	 * @author A-1950
	 *
	 * @param messageVO
	 * @throws SystemException
	 */

	/*
	 *
	 * **? Exception should b raised if UCMDUP error is there.
	 * 		this happens when same UCM is tried to save more than 3 times.
	 */
	/**
	 * Handling Error E1:When the client saves the UCM there shiold be check whether there is any UCM existing
	 * in the system for the same flight.If there is a UCM already existing in the System then for both the UCM
	 * an error E1 is stamped.Now onward system will not allow to send any extra UCM since the
	 * Duplicate UCM exception has to be reconciled.
	 *
	 * */
	/**
	 * Handling Error E2:
	 *
	 * */

	/*
	 * *At what conditions u r throwing the Business errrors....
	 * */

/**
 * @param uldFlightMessageVO
 * @return ULDFlightMessageReconcileVO
 * @throws SystemException
 */
	public ULDFlightMessageReconcileVO validateUCM(ULDFlightMessageReconcileVO uldFlightMessageVO)
	throws SystemException{
		log.entering("UCMValidator","validateUCM");
		//Added by A-7359 for ICRD-220123 starts here
		parCodes.add(ULD_NOT_IN_AIRLINESTOCK);
		parCodes.add(ULD_NOT_IN_AIRLINESTOCK_FOR_OAL);
		parCodes.add(ULD_NOT_IN_AIRPORT);
		parCodes.add(ULD_NOT_IN_AIRPORT_FOR_OAL);
		parCodes.add(ULD_NOT_IN_AIRPORT_VALIDATION);

		
		try {
			parameterVal=(HashMap<String, String>) new SharedDefaultsProxy().findSystemParameterByCodes(parCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage());
		}
		//Added by A-7359 for ICRD-220123 ends here
		//Calling for duplicateOut Check
		//Modifed by A-7359 for ICRD-192413
		if(!("MAN").equalsIgnoreCase(uldFlightMessageVO.getMessageSource())){
		checkForDuplicateOut(uldFlightMessageVO);
		}
	/*
	 * JUST AVOIDED
	 * 	if(!E1.equals(uldFlightMessageVO.getErrorCode())){
			//calling For UCMOUT after UCMIN has Strucked
			checkForOutAfterIn(uldFlightMessageVO);
		}
		*/
		//Calling for the case of UCMIN if duplicated UCMOUT already exists

		log.log(Log.INFO, "%%%%%%%%%%%%%%  uldFlightMessageVO",
				uldFlightMessageVO);
		checkForInIfDuplicateOutExists(uldFlightMessageVO);

		if(!E_THIRTEEN.equals(uldFlightMessageVO.getErrorCode())){
			//calling for Dulicated in Check
			checkForDulicateIn(uldFlightMessageVO);

			if(!E_TWO.equals(uldFlightMessageVO.getErrorCode())){
				/*
				 * This check is for UCM-IN message
				 * Going to check whether OUT is completely missing for the IN
				 */
				checkForUCMInWithUCMOutCompletelyMissing(uldFlightMessageVO);

				if(!E_NINE.equals(uldFlightMessageVO.getErrorCode())){

					/*
					 * This check is for UCM-IN message
					 * compares the IN with the corresponding OUT and finds whether is any ULD is misssing.
					 * if so , E10 wil b stamped
					 */
					checkForAnyMissingULD(uldFlightMessageVO);
					if(!E_NINE.equals(uldFlightMessageVO.getErrorCode())){
						/*
						 * This check is for the UCM-IN
						 * Compares the IN with corresponding OUT and checks whether there is any extra ULD in the IN
						 * If so E11 is stamped.s
						 */
						checkForAnyExtraULD(uldFlightMessageVO);
					}
				}
			}
		}
		/*
		 * ULDLevel errors captured by this method
		 */
		log.log(Log.INFO,"%%%%%%%%%%  going to check for ulderrors");
		if(uldFlightMessageVO.getReconcileDetailsVOs() != null && uldFlightMessageVO.getReconcileDetailsVOs().size() > 0){
			for(ULDFlightMessageReconcileDetailsVO uldVO : uldFlightMessageVO.getReconcileDetailsVOs()){
					if(!uldVO.isToBeAvoidedFromValidationCheck()){
						log.log(Log.INFO, "$$$$$$$$$$$$$$$$    uldVO  ", uldVO);
						checkForULDErrors(uldVO);
						if("U".equalsIgnoreCase(uldFlightMessageVO.getOperationFlag()) &&
								(uldVO.getOperationFlag() == null)){
							if(E_FIFTEEN.equals(uldVO.getErrorCode())){
								uldVO.setErrorCode(null);
							}
						}
					}
			}
		}
		//Added by A-7359 for ICRD-220123 starts here
		errorCollection=checkforErrorCollection();
		if(errorCollection!=null&& errorCollection.size()>0){
			uldFlightMessageVO.setErrorCollection(errorCollection);
		}
		//Added by A-7359 for ICRD-220123 ends here
		log.log(Log.INFO, "%%%%%%%%%%%  uldFlightMessageVO ",
				uldFlightMessageVO);
		return uldFlightMessageVO;
		//new ULDFlightMessageReconcile(uldFlightMessageVO);
	}



/**
 * 	Method		:	UCMValidator.checkforErrorCollection
 *	Added by 	:	A-7359 on 14-Nov-2017
 * 	Used for 	:   ICRD-220123 
 *	Parameters	:	@return 
 *	Return type	: 	Map<String,Collection<String>>
 */
private Map<String, Collection<String>> checkforErrorCollection() {
	log.entering("ULDController","checkforErrorCollection");
	//Modified by A-7359 for ICRD-244811 starts here
	Map<String,Collection<String>> error=new HashMap<String,Collection<String>>();
	if(errorSevenOAL!=null&&errorSevenOAL.size()>0){
		error.put(ULD_NOT_IN_AIRLINESTOCK_FOR_OAL, errorSevenOAL);
	}else if(errorSevenOWN!=null&&errorSevenOWN.size()>0){
		error.put(ULD_NOT_IN_AIRLINESTOCK, errorSevenOWN);
	}else if(errorFiveSixOWN!=null&&errorFiveSixOWN.size()>0){
		error.put(ULD_NOT_IN_AIRPORT, errorFiveSixOWN);
	}else if(errorFiveSixOAL!=null&&errorFiveSixOAL.size()>0){
		error.put(ULD_NOT_IN_AIRPORT_FOR_OAL, errorFiveSixOAL);
	}
	log.log(Log.INFO, "%%%%%%%%%%%  ErrorMap ",
			error);
	log.exiting("ULDController","checkforErrorCollection");
	//Modified by A-7359 for ICRD-244811 ends here
	return error;
}
/**
 *
 * @param uldFlightMessageReconcileVO
 * @throws SystemException
 */
	private void checkForDuplicateOut(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
	throws SystemException{
		log.entering("ULDController","checkForDuplicateOut");
		if(uldFlightMessageReconcileVO.getSequenceNumber() == null){
			boolean isFound = true;
			ULDFlightMessageReconcile messageReconsile = null;
			ULDFlightMessageReconcileVO reconcileVO = null;
			reconcileVO = ULDFlightMessageReconcile.findCounterUCM(uldFlightMessageReconcileVO);
			log.log(Log.INFO, "%%%%%%%%%   reconcileVO", reconcileVO);
			if(reconcileVO != null){
				log.log(Log.INFO, "%%%%%%%%%   isFound", isFound);
				try{
					messageReconsile =
						ULDFlightMessageReconcile.find(reconcileVO);
				}catch(FinderException finderException){
					isFound = false;
				}
				log.log(Log.INFO, "%%%%%%%%%   isFound", isFound);
				if(isFound){
					messageReconsile.setErrorCode(E_ONE);
					uldFlightMessageReconcileVO.setErrorCode(E_ONE);
					log.log(Log.INFO,
							"%%%%%%%%%   uldFlightMessageReconcileVO",
							uldFlightMessageReconcileVO);

				}
			}
		}
	}



	/**
	 * @author A-1950
	 *
	 * @param uldFlightMessageReconcileVO
	 * @throws SystemException
	 * @throws UCMValidationException
	 */
	/*
	 * E13 is locked against a UCMIN when a duplicate UCMOUT already exists
	 */
	private void checkForInIfDuplicateOutExists(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
	throws SystemException {
		if(IN.equals(uldFlightMessageReconcileVO.getMessageType())){

			HashMap<String,String> map = null;
			//Commented by Manaf for INT ULD510
			//List<ULDFlightMessageReconcileDetailsVO> detailsVOs =
				 //new ArrayList<ULDFlightMessageReconcileDetailsVO>();
			FlightFilterMessageVO flightFilterVO = new FlightFilterMessageVO();
			flightFilterVO.setMessageType(OUT);
			flightFilterVO.setCompanyCode(uldFlightMessageReconcileVO.getCompanyCode());
			flightFilterVO.setPointOfUnloading(uldFlightMessageReconcileVO.getAirportCode());
			flightFilterVO.setFlightNumber(uldFlightMessageReconcileVO.getFlightNumber());
			flightFilterVO.setFlightCarrierId(uldFlightMessageReconcileVO.getFlightCarrierIdentifier());
			flightFilterVO.setFlightSequenceNumber(uldFlightMessageReconcileVO.getFlightSequenceNumber());
			//Added by Manaf for INT ULD510 starts
			List<ULDFlightMessageReconcileDetailsVO> detailsVOs = new ArrayList<ULDFlightMessageReconcileDetailsVO>(
					ULDFlightMessageReconcile.listUCMOUTForInOutMismatch(flightFilterVO));
			//Added by Manaf for INT ULD510 ends
			if(detailsVOs.size() > 1){
				map = new HashMap<String,String>();
				//String seqNumber = detailsVOs.get(0).getSequenceNumber();
				for(ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs){
					if(map.containsKey(detailsVO.getAirportCode())){
						if(!map.get(detailsVO.getAirportCode()).equals(detailsVO.getSequenceNumber())){
							uldFlightMessageReconcileVO.setErrorCode(E_THIRTEEN);
							break;
						}
					}else{
						map.put(detailsVO.getAirportCode(),detailsVO.getSequenceNumber());
					}
				}
			}
		}
	}

	/**
	 * @author A-1950
	 *
	 * @param uldFlightMessageReconcileVO
	 * @throws SystemException
	 * @throws UCMValidationException
	 */
	/*
	 * Error E2:
	 * if a duplicate UCMIN is raised error is  stamped.
	 *
	 */
	private void checkForDulicateIn(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
	throws SystemException {
		if(IN.equals(uldFlightMessageReconcileVO.getMessageType())){
			if(uldFlightMessageReconcileVO.getSequenceNumber() == null){
				boolean isFound = true;
				ULDFlightMessageReconcile messageReconsile = null;
				ULDFlightMessageReconcileVO reconcileVO = null;
				reconcileVO = ULDFlightMessageReconcile.findCounterUCM(uldFlightMessageReconcileVO);
				if(reconcileVO != null){
					try{
						messageReconsile =
							ULDFlightMessageReconcile.find(reconcileVO);
					}catch(FinderException finderException){
						isFound = false;
					}
					if(isFound){
						messageReconsile.setErrorCode(E_TWO);
						uldFlightMessageReconcileVO.setErrorCode(E_TWO);
					}
				}
			}
		}
	}

/**
 *
 * @param uldFlightMessageVO
 * @throws SystemException
 */
	private void checkForUCMInWithUCMOutCompletelyMissing(ULDFlightMessageReconcileVO uldFlightMessageVO)
	throws SystemException{
		log.entering("UCMValidator","checkForUCMInWithUCMOutCompletelyMissing");
		 Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs =
			 new ArrayList<ULDFlightMessageReconcileDetailsVO>();

		if(IN.equals(uldFlightMessageVO.getMessageType())){

			FlightFilterMessageVO flightFilterVO = new FlightFilterMessageVO();
			flightFilterVO.setMessageType(OUT);
			flightFilterVO.setCompanyCode(uldFlightMessageVO.getCompanyCode());
			flightFilterVO.setPointOfUnloading(uldFlightMessageVO.getAirportCode());
			flightFilterVO.setFlightNumber(uldFlightMessageVO.getFlightNumber());
			flightFilterVO.setFlightCarrierId(uldFlightMessageVO.getFlightCarrierIdentifier());
			flightFilterVO.setFlightSequenceNumber(uldFlightMessageVO.getFlightSequenceNumber());
			detailsVOs = ULDFlightMessageReconcile.listUCMOUTForInOutMismatch(flightFilterVO);
			log.log(Log.INFO, "%%%%%%%%%% detailsVOs ", detailsVOs);
			if(detailsVOs.size() == 0){
				uldFlightMessageVO.setErrorCode(E_NINE);
				log.log(Log.INFO, "%%%%%%%%%%%  size is 0", uldFlightMessageVO);
			}
		}
	}

	/**
	 * @author A-1950
	 *
	 * @param uldFlightMessageVO
	 * @throws SystemException
	 */
	private void checkForAnyMissingULD(ULDFlightMessageReconcileVO uldFlightMessageVO)
	throws SystemException{
		log.entering("UCMValidator","checkForAnyMissingULD");

		 Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs =
			 new ArrayList<ULDFlightMessageReconcileDetailsVO>();

		if(IN.equals(uldFlightMessageVO.getMessageType())){

			FlightFilterMessageVO flightFilterVO = new FlightFilterMessageVO();
			flightFilterVO.setMessageType(OUT);
			flightFilterVO.setCompanyCode(uldFlightMessageVO.getCompanyCode());
			flightFilterVO.setPointOfUnloading(uldFlightMessageVO.getAirportCode());
			flightFilterVO.setFlightNumber(uldFlightMessageVO.getFlightNumber());
			flightFilterVO.setFlightCarrierId(uldFlightMessageVO.getFlightCarrierIdentifier());
			flightFilterVO.setFlightSequenceNumber(uldFlightMessageVO.getFlightSequenceNumber());
			detailsVOs = ULDFlightMessageReconcile.listUCMOUTForInOutMismatch(flightFilterVO);

			if(detailsVOs.size() > 0){
				Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetailsVOs =
					uldFlightMessageVO.getReconcileDetailsVOs();

				if(uldFlightMessageVO.getReconcileDetailsVOs().size() > 0){

					for(ULDFlightMessageReconcileDetailsVO detailsVO :
						detailsVOs){

						boolean isMatching = false;
						String uldNumber = detailsVO.getUldNumber();
						for(ULDFlightMessageReconcileDetailsVO reconcileDetailsVO : reconcileDetailsVOs){
							if(uldNumber.equals(reconcileDetailsVO.getUldNumber())){
								isMatching = true;
								break;
							}
						}
						if(!isMatching){
							uldFlightMessageVO.setErrorCode(E_NINE);
							break;
						}
					}
			   }
			}
			log.log(Log.INFO,"%%%%%%%%%%%%%%%%%%%%%%   reached heree");
		}
	}

	/**
	 * @author A-1950
	 *
	 * @param uldFlightMessageVO
	 * @throws SystemException
	 */
	private void checkForAnyExtraULD(ULDFlightMessageReconcileVO uldFlightMessageVO)
	throws SystemException{
		log.entering("UCMValidator","checkForAnyExtraULD");
		Collection<ULDFlightMessageReconcileDetailsVO> detailsVOs =
			 new ArrayList<ULDFlightMessageReconcileDetailsVO>();
		if(IN.equals(uldFlightMessageVO.getMessageType())){

			FlightFilterMessageVO flightFilterVO = new FlightFilterMessageVO();
			flightFilterVO.setMessageType(OUT);
			flightFilterVO.setCompanyCode(uldFlightMessageVO.getCompanyCode());
			flightFilterVO.setPointOfUnloading(uldFlightMessageVO.getAirportCode());
			flightFilterVO.setFlightNumber(uldFlightMessageVO.getFlightNumber());
			flightFilterVO.setFlightCarrierId(uldFlightMessageVO.getFlightCarrierIdentifier());
			flightFilterVO.setFlightSequenceNumber(uldFlightMessageVO.getFlightSequenceNumber());

			detailsVOs = ULDFlightMessageReconcile.listUCMOUTForInOutMismatch(flightFilterVO);

			if(detailsVOs.size() > 0){
				if(uldFlightMessageVO.getReconcileDetailsVOs() != null ){
					for(ULDFlightMessageReconcileDetailsVO reconcileDetailsVO :
						uldFlightMessageVO.getReconcileDetailsVOs()){

						boolean isMatching = false;
						String uldNumber = reconcileDetailsVO.getUldNumber();
						for(ULDFlightMessageReconcileDetailsVO detailsVO : detailsVOs){
							if(uldNumber.equals(detailsVO.getUldNumber())){
								isMatching = true;
								break;
							}
						}
						if(!isMatching){
							uldFlightMessageVO.setErrorCode(E_NINE);
							break;
						}
					}
				}
			}
		}
	}
/**
 *
 * @param uldVO
 * @throws SystemException
 */
	private void checkForULDErrors(ULDFlightMessageReconcileDetailsVO uldVO)
	throws SystemException{
		log.log(Log.INFO,"%%%%% Check for ulderrors");
		String uldNumber = null;
		ULD uld = null;
		boolean isFoundInULDMST = true;
		String flightInfo = null;
		String flightDate = null;
		uldNumber = uldVO.getUldNumber();

		LogonAttributes logonAttributes =
				 ContextUtils.getSecurityContext().getLogonAttributesVO();
		//Added by A-7359 for ICRD-220123 starts here
		AirlineValidationVO airlineValidationVO = null;
		try{
			airlineValidationVO = new SharedAirlineProxy()
					.findAirline(
							uldVO.getCompanyCode(),
							logonAttributes
									.getOwnAirlineIdentifier());
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		// own ariline code
		String airlineCode = airlineValidationVO
				.getAlphaCode();
		//Added by A-7359 for ICRD-220123 ends here
		try{
			uld = ULD.find(uldVO.getCompanyCode() , uldNumber);
		}catch(FinderException finderexception){
			isFoundInULDMST = false;
		}
		if(!isFoundInULDMST){
			log.log(Log.INFO,"%%%%##%%%% Setting E3 errorr");
			// if uld is of another airline and flight is the same ,error will be thrown. Change as a part of the bugfix 101302
		/*	String flightCarrierCode = uldVO.getCarrierCode();
			int lCount = uldNumber.length();
			int sCount = flightCarrierCode.length();
			String uldOwnerCode = uldNumber.substring(lCount-sCount , lCount);*/

			
			//Modified by A-7359 for ICRD-220123 starts here
			//if(uldOwnerCode.equals(flightCarrierCode) ||
				//	logonAttributes.getOwnAirlineIdentifier() == uldVO.getFlightCarrierIdentifier()){
				uldVO.setErrorCode(E_THREE);
			//}
		}else{
			if(NON_OPERATIONAL.equals(uld.getOverallStatus())){
				uldVO.setErrorCode(E_FOUR);
			}else{
				//Added by A-7359 for ICRD-220123 starts here
				String arldtl=findAirlineIdentifier(uldVO);
				String[] uldAirlineCodeArray = null;
				String uldAirlineCode="";
				uldAirlineCodeArray=findULDAirlineCode(arldtl);
				if(uldAirlineCodeArray!=null&&uldAirlineCodeArray.length>0){
					 uldAirlineCode=uldAirlineCodeArray[0];
				}
				
				//Added by nisha for bugfix for finding ulds loaned to agent..
				if(uld.getOperationalAirlineIdentifier()!= uldVO.getFlightCarrierIdentifier()
						||( uld.getOperationalAirlineIdentifier() == uldVO.getFlightCarrierIdentifier()
						&& uldVO.getFlightCarrierIdentifier() > 0 && uld.getReleasedTo()!=null
						&& uld.getReleasedTo().trim().length() > 0)
						|| (uldVO.isWetLeasedFlight() && (logonAttributes.getOwnAirlineIdentifier() != uldVO.getFlightCarrierIdentifier()))
						){
					log.log(Log.INFO,
							"%%%%%%%%%%%%   GOING TO CHECK FOR POOLOWNERS",
							uldVO);
						//if(!ULDFlightMessageReconcile.checkForPoolOwners(uldVO)){....need to implement this as part of pool owners CR
					 //Modified by A-7359 for ICRD-220123 starts here
					// Modified as part of bug ICRD-244766
					if(uldAirlineCode.equals(airlineCode)){
						uldVO.setErrorCode(E_SEVEN);
						if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRLINESTOCK))){
					    errorSevenOWN.add(uldVO.getUldNumber());
						}
					} else if (((uldAirlineCode.equals(uldVO.getCarrierCode()) && !(airlineCode
							.equals(uldVO.getCarrierCode()))) || (!uldAirlineCode
							.equals(uldVO.getCarrierCode())))){
						uldVO.setErrorCode(E_SEVEN);
						if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRLINESTOCK_FOR_OAL))){
						 errorSevenOAL.add(uldVO.getUldNumber());
					}
					}
					 //Modified by A-7359 for ICRD-220123 ends here 
					//}
				}else{
					if("OUT".equals(uldVO.getMessageType())){
						LocalDate fltDate = uldVO.getFlightDate();
						flightDate = fltDate.toDisplayDateOnlyFormat();
						flightInfo = uldVO.getCarrierCode()+uldVO.getFlightNumber()+","+flightDate+","+uldVO.getPou(); //Modified as part of bug ICRD-223530
						boolean uldNotInAirportValidation = true;
						if(parameterVal.get(ULD_NOT_IN_AIRPORT_VALIDATION) != null 
								&& parameterVal.get(ULD_NOT_IN_AIRPORT_VALIDATION).equals("N")){
							uldNotInAirportValidation = false;
						}
							if(!uldVO.getAirportCode().equals(uld.getCurrentStation())){
								if(INTRANSIT.equals(uld.getTransitStatus())){
									//Modifed by A-7359 for ICRD-220123 starts here
									if(uldAirlineCode.equals(airlineCode)){
									uldVO.setErrorCode(E_FIVE);
									if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRPORT)) && uldNotInAirportValidation){
										errorFiveSixOWN.add(uldVO.getUldNumber());
									}
								} else if (((uldAirlineCode.equals(uldVO
										.getCarrierCode()) && !(airlineCode
										.equals(uldVO.getCarrierCode()))) || (!uldAirlineCode
										.equals(uldVO.getCarrierCode())))){
										uldVO.setErrorCode(E_FIVE);
										if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRPORT_FOR_OAL)) && uldNotInAirportValidation){
										errorFiveSixOAL.add(uldVO.getUldNumber());
										}
									}
									//Modifed by A-7359 for ICRD-220123 ends here
								}else if (uldVO.isFromReconcileScreen() && flightInfo.equals(uld.getFlightInfo())&& uldVO.getPou().equals(uld.getCurrentStation())){
									log.log(Log.INFO,"%%%%%%%%%%%%   uInsideeee");
								}else{
									//Modifed by A-7359 for ICRD-220123 starts here
									if(uldAirlineCode.equals(airlineCode)){
									uldVO.setErrorCode(E_SIX);
									if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRPORT)) && uldNotInAirportValidation){
										errorFiveSixOWN.add(uldVO.getUldNumber());
									}
								} else if (((uldAirlineCode.equals(uldVO
										.getCarrierCode()) && !(airlineCode
										.equals(uldVO.getCarrierCode()))) || (!uldAirlineCode
										.equals(uldVO.getCarrierCode())))){
										uldVO.setErrorCode(E_SIX);
										if(ERROR.equals(parameterVal.get(ULD_NOT_IN_AIRPORT_FOR_OAL)) && uldNotInAirportValidation){
										errorFiveSixOAL.add(uldVO.getUldNumber());
										}
									}
									//Modifed by A-7359 for ICRD-220123 ends here
									// Modified as part of bug ICRD-244766 ends
								}
							}else{
								if(INTRANSIT.equals(uld.getTransitStatus()) && !flightInfo.equals(uld.getFlightInfo())){
									uldVO.setErrorCode(E_FIFTEEN);
								}
							}
					}
					/*else{
						if(uldVO.getFlightDate().toCalendar()
								.before(uld.getLastMovementDate())){
							uldVO.setErrorCode(E8);
						}
					}*/
				}
			}
		}
	}


	/**
	 * @author A-1950
	 * @param uldReconcileDetailsVO
	 * @throws SystemException
	 */
	 public String reconcileUCMULDError(ULDFlightMessageReconcileDetailsVO uldReconcileDetailsVO)
	    throws SystemException{
		log.entering("UCMValidator","reconcileUCMULDErrorreconcileUCMULDError");
		ULDFlightMessageReconcileDetails uldFlightMessageReconcileDetails =
			ULDFlightMessageReconcileDetails.find(uldReconcileDetailsVO);
		uldReconcileDetailsVO.setErrorCode(null);
		if(!uldReconcileDetailsVO.isToBeAvoidedFromValidationCheck()){
			checkForULDErrors(uldReconcileDetailsVO);
		}
		log.log(Log.INFO, "uld reconcile VOS", uldReconcileDetailsVO);
		ULDFlightMessageReconcileVO uldFlightMessageReconcileVO = new ULDFlightMessageReconcileVO();
		uldFlightMessageReconcileVO.setFlightCarrierIdentifier(uldReconcileDetailsVO.getFlightCarrierIdentifier());
		uldFlightMessageReconcileVO.setFlightNumber(uldReconcileDetailsVO.getFlightNumber());
		uldFlightMessageReconcileVO.setFlightSequenceNumber(uldReconcileDetailsVO.getFlightSequenceNumber());
		uldFlightMessageReconcileVO.setAirportCode(uldReconcileDetailsVO.getAirportCode());
		uldFlightMessageReconcileVO.setCompanyCode(uldReconcileDetailsVO.getCompanyCode());
		uldFlightMessageReconcileVO.setMessageType(uldReconcileDetailsVO.getMessageType());
		uldFlightMessageReconcileVO.setSequenceNumber(uldReconcileDetailsVO.getSequenceNumber());
		String msgSendFlag= null;
		try{
		ULDFlightMessageReconcile uldFlightMessageReconcile =
			ULDFlightMessageReconcile.find(uldFlightMessageReconcileVO);
		if(uldFlightMessageReconcile!=null){
			msgSendFlag = uldFlightMessageReconcile.getMessageSendFlag();
		}
		}catch(FinderException e){
			throw new SystemException(e.getErrorCode());
		}
		if("O".equals(msgSendFlag)){
			if("E15".equals(uldReconcileDetailsVO.getErrorCode())){
				if("E15".equals(uldFlightMessageReconcileDetails.getErrorCode())){
					uldReconcileDetailsVO.setErrorCode("E15");
				}else{
				uldReconcileDetailsVO.setErrorCode(null);
			}
				//uldReconcileDetailsVO.setErrorCode(null);
			}
			if("E5".equals(uldReconcileDetailsVO.getErrorCode())){
				if("E5".equals(uldFlightMessageReconcileDetails.getErrorCode())){
				uldReconcileDetailsVO.setErrorCode("E5");
				}else{
				uldReconcileDetailsVO.setErrorCode("E6");
				}
			}
		}
		log.log(Log.INFO, "new uld reconcile VOS", uldReconcileDetailsVO);
		//		added by nisha for bugfix on 24Mar08 ends
		uldFlightMessageReconcileDetails.update(uldReconcileDetailsVO);
		log.log(Log.INFO, "New Error Code after Revalidating",
				uldReconcileDetailsVO.getErrorCode());
		return uldReconcileDetailsVO.getErrorCode();

	 }
		/**
		 * This method returns a String arldtl which contains 3 parts separated by
		 * ',' Fist part is  the airline identifier,Second part is the twoalphacode and the
		 * thirdone is the threealphacode
		 *
		 * 	Method		:	ULDController.findAirlineIdentifier
		 *	Added by 	:	A-7359 on 13-Nov-2017
		 * 	Used for 	:   ICRD-220123 
		 *	Parameters	:	@param detVO
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 * @throws SystemException 
		 */
		private String findAirlineIdentifier(
				ULDFlightMessageReconcileDetailsVO detVO) throws SystemException {
			String arldtl=null;
			int length = detVO.getUldNumber().length();
			String twoalphacode = detVO.getUldNumber()
					.substring(length - 2);
			String threealphacode = detVO
					.getUldNumber().substring(
							length - 3);
			arldtl = findOwnerCode(detVO.getCompanyCode(), twoalphacode, threealphacode);
			if(arldtl!=null){
				 arldtl=arldtl + "," +twoalphacode+","+threealphacode;
			}
			return arldtl;
		}
		/**
		 * This method returns a String arldtl which contains 2 parts separated by
		 * '~' Fist part is gives the no of characters in ULDOwnerCode And the
		 * SecondPart gives the airline identifier
		 *
		 * A-1950
		 *
		 * @param companyCode
		 * @param twoAlphaCode
		 * @param threeAlphaCode
		 * @return
		 * @throws SystemException
		 */
		public String findOwnerCode(String companyCode, String twoAlphaCode,
				String threeAlphaCode) throws SystemException {
			//Commented as part of ICRD-21184
			//return ULD.findOwnerCode(companyCode, twoAlphaCode, threeAlphaCode);
			//Added as part of ICRD-21184
			log.entering("ULDController", "findOwnerCode");
			AirlineValidationVO airlineValidationVO = null;
			String arldtl = null;
			try {
				airlineValidationVO = new SharedAirlineProxy().validateAlphaCode(companyCode, twoAlphaCode);
				log.log(Log.INFO, "airlineValidationVO: ", airlineValidationVO);
			} catch (ProxyException proxyException) {
				Collection<ErrorVO> errors = proxyException.getErrors();
				if(errors != null && errors.size()>0) {
					for (ErrorVO errorVO : errors) {
						if(errorVO.getErrorCode().equals(INVALID_AIRLINE)) {
							log
									.log(
											Log.INFO,
											"validateAlphaCodes returned null for twoAlphaCode: ",
											twoAlphaCode);
							try {
								airlineValidationVO = new SharedAirlineProxy().validateAlphaCode(companyCode, threeAlphaCode);
							} catch (ProxyException pe) {
								log.log(Log.INFO, "findOwnerCode -> ProxyException");
							}
						}
					}
				}
			}
			if(airlineValidationVO != null) {
				if(airlineValidationVO.getAlphaCode()!=null
						&& airlineValidationVO.getAlphaCode().length()> 0){
					arldtl =   airlineValidationVO.getAlphaCode().length() + "~" + airlineValidationVO.getAirlineIdentifier();
				}
			}
			log.entering("ULDController", "findOwnerCode -> arldtl " + arldtl);
			return arldtl;
		}
		/**
		 * 	Method		:	ULDController.findULDAirlineCode
		 *	Added by 	:	A-7359 on 13-Nov-2017
		 * 	Used for 	:	ICRD-220123 
		 *	Parameters	:	@param arldtl
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		private String[] findULDAirlineCode(String arldtl) {
			String[] finalArray=new String[5];
			if(arldtl != null) {
				String airlineArray[] = arldtl.split(",");
				String airline=airlineArray[0];
				String arldtlArray[]=airline.split("~");
				if ("2".equals(arldtlArray[0])){
					finalArray[0] = airlineArray[1];
				} else {
					finalArray[0] = airlineArray[2];
				}
				finalArray[1]=arldtlArray[1];
			}
			return finalArray;
		}
}
