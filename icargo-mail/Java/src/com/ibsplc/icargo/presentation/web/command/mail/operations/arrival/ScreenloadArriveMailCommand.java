/*
 * ScreenloadArriveMailCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ScreenloadArriveMailCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "screenload_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";

   /** The Constant DAMAGE_CODES. */
   private static final String DAMAGE_CODES = "mailtracking.defaults.return.reasoncode";
   private static final String MAILCATEGORY = "mailtracking.defaults.mailcategory";
   private static final String MAILHNI = "mailtracking.defaults.highestnumbermail";
   private static final String MAILRI = "mailtracking.defaults.registeredorinsuredcode";
   private static final String MAILCLASS = "mailtracking.defaults.mailclass";
   private static final String CONTAINERTYPE = "mailtracking.defaults.containertype";
   private static final String MAILCMPCOD_CODES = "mailtracking.defaults.companycode";
   private static final String ULD_TYPE = "U";	//Added for ICRD-128804
   private static final String SYSPAR_DEFUNIT_WEIGHT= "mail.operations.defaultcaptureunit";
   private static final String STNPAR_DEFUNIT_WEIGHT = "station.defaults.unit.weight";


	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ScreenloadArriveMailCommand","execute");

    	MailArrivalForm mailArrivalForm =
    		(MailArrivalForm)invocationContext.screenModel;
    	MailArrivalSession mailArrivalSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	mailArrivalForm.setOperationalStatus("");
    	mailArrivalSession.setMessageStatus("");

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		//Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		mailArrivalSession.setWeightRoundingVO(unitRoundingVO);
		setUnitComponent(logonAttributes.getStationCode(),mailArrivalSession);
		boolean isCheckRequired = false;
		mailArrivalForm.setHiddenScanDate((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)).toDisplayDateOnlyFormat().toUpperCase());
		mailArrivalForm.setHiddenScanTime(
				((new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true))
				.toDisplayTimeOnlyFormat()).substring(0,5));


		// To set POL
		 FlightValidationVO flightValidationVO = mailArrivalSession.getFlightValidationVO();
		 String route = flightValidationVO.getFlightRoute();
		 String[] routeArr = route.split("-");
		 boolean isRoundRobin = false;
		 if(routeArr != null && routeArr.length >0){
			 if(routeArr[0].equals(routeArr[routeArr.length-1]))
				 {
				 isRoundRobin = true;
		 }
		 }
		//String pol = "";
		 ArrayList<String> pols = new ArrayList<String>();
		 for(String airport: routeArr){
			 if(!airport.equals(logonAttributes.getAirportCode())){
				 pols.add(airport);
			 }else if (isRoundRobin && pols.isEmpty()){
				 pols.add(airport);
			 }
			 else{
				 break;
			 }
		 }
		 if(!pols.isEmpty())
			 {
			 Collections.reverse(pols);
			 }
		//mailArrivalForm.setPol(pol);

		MailArrivalVO mailArrivalVO = mailArrivalSession.getMailArrivalVO();
		Collection<ContainerDetailsVO> contDetailsVOs = mailArrivalVO.getContainerDetails();
		String[] selected = mailArrivalForm.getSelectContainer();
		String selectedMails = selected[0];
		String[] primaryKey = selectedMails.split(",");

		int cnt=0;
	    int count = 0;
        int primaryKeyLen = primaryKey.length;
        String commodityCode = mailArrivalSession.getMailCommidityCode();
        Collection<String> commodites = new ArrayList<String>();
    	if(commodityCode!=null && commodityCode.trim().length()>0) {
			commodites.add(commodityCode);
			Map<String,CommodityValidationVO> densityMap = null;
			CommodityDelegate  commodityDelegate = new CommodityDelegate();
			try {
				densityMap = commodityDelegate.validateCommodityCodes(logonAttributes.getCompanyCode(), commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				//e.getMessage();
			}
			if(densityMap !=null && densityMap.size()>0){
				CommodityValidationVO commodityValidationVO = densityMap.get(commodityCode);
				log.log(Log.FINE, "DENSITY-----:", commodityValidationVO.getDensityFactor());
				mailArrivalForm.setDensity(String.valueOf(commodityValidationVO.getDensityFactor()));
			}
			 log.log(Log.FINE, "*******DEnsity Factor******", mailArrivalForm.getDensity());
		}
    	
    	Map systemParameters = null;  
        SharedDefaultsDelegate sharedDelegate =new SharedDefaultsDelegate();
        try {
        	systemParameters=sharedDelegate.findSystemParameterByCodes(getSystemParameterCodes());
        } catch (BusinessDelegateException e) {
               e.getMessage();
        }//added by A-7540 for ICRD-274933 ends

        AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {			
			e1.getMessage();
		}
    	if ((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT)!=null){
    		mailArrivalForm.setDefWeightUnit((String)systemParameters.get(SYSPAR_DEFUNIT_WEIGHT));
    		}
    		else{
    			mailArrivalForm.setDefWeightUnit((String)stationParameters.get(STNPAR_DEFUNIT_WEIGHT));
    		}  //added by A-7540 for ICRD-274933
        Collection<ContainerDetailsVO> containerDetailsVOs = new ArrayList<ContainerDetailsVO>();

        try{

        	if(!"NA".equals(primaryKey[0])){

        		if (contDetailsVOs != null && contDetailsVOs.size() > 0) {

        			for (ContainerDetailsVO contDetailsVO : contDetailsVOs) {
						ContainerDetailsVO newContDetailsVO = new ContainerDetailsVO();
						BeanHelper.copyProperties(newContDetailsVO, contDetailsVO);
						String primaryKeyFromVO = String.valueOf(count);

						if (cnt < primaryKeyLen && primaryKeyFromVO.trim().equalsIgnoreCase(
								primaryKey[cnt].trim())) {

							if (!"I".equals(newContDetailsVO.getOperationFlag())) {
								newContDetailsVO.setOperationFlag("U");
							}
							Collection<MailbagVO> mailbagVOs = newContDetailsVO.getMailDetails();

							if (mailbagVOs != null && mailbagVOs.size() != 0) {

								for (MailbagVO mailbagVO : mailbagVOs) {
									/*String mailId = mailbagVO.getMailbagId();

									if (mailId != null) {
										
										double displayWt=Double.parseDouble(mailId.substring(
												mailId.length() - 4, mailId.length()));
										Measure strWt=new Measure(UnitConstants.MAIL_WGT,displayWt/10);
										mailbagVO.setStrWeight(strWt);//added by A-7371
									}*/
									//Modified as part of ICRD-260245
									if (mailbagVO.getWeight() != null) {
										mailbagVO.setStrWeight(mailbagVO.getWeight());
									}
									mailbagVO.setUndoArrivalFlag(null);
									/*
									 * Added By RENO K ABRAHAM : ANZ BUG : 37646
									 * As a part of performance Upgrade START.
									 */
									if (mailbagVO.getOperationalFlag() == null || !("I").equals(
											mailbagVO.getOperationalFlag())) {
										mailbagVO.setDisplayLabel("Y");
									}
									// END
									if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getAcceptanceFlag())&&mailbagVO.getMailCompanyCode()!=null && mailbagVO.getMailCompanyCode().trim().length()>0){
										StringBuilder mailCmpCode=new StringBuilder();
										mailCmpCode.append("MALCMPCOD").append("-").append(mailbagVO.getMailCompanyCode());
										mailbagVO.setAccepted(mailCmpCode.toString());
									}
								}
							}
							Collection<DespatchDetailsVO> despatchDetailsVOs = newContDetailsVO
									.getDesptachDetailsVOs();

							if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {

								for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
									/*
									 * Added By RENO K ABRAHAM : ANZ BUG : 37646
									 * As a part of performance Upgrade START.
									 */
									if (despatchDetailsVO.getOperationalFlag() == null || !("I")
											.equals(despatchDetailsVO.getOperationalFlag())) {
										despatchDetailsVO.setDisplayLabel("Y");
									}
									// END
								}
							}
							containerDetailsVOs.add(newContDetailsVO);
							cnt++;
						}
						count++;
					}
					mailArrivalForm.setIsContainerValidFlag(ContainerDetailsVO.FLAG_YES);
				}

       		mailArrivalSession.setContainerDetailsVOs(containerDetailsVOs);
       		ContainerDetailsVO containerDetailsVO = ((ArrayList<ContainerDetailsVO>)containerDetailsVOs).get(0);
       		mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
       		if(!"B".equals(containerDetailsVO.getContainerType()) && (containerDetailsVO.getAcceptedFlag() == null ||  containerDetailsVO.getAcceptedFlag().trim().length() == 0)){
       			Collections.reverse(pols);
       			mailArrivalSession.setPols(pols);
       			isCheckRequired = true;
       		mailArrivalForm.setPol(containerDetailsVO.getPol());
       		}else
       			{
       			mailArrivalForm.setPol(containerDetailsVO.getPol());
       			}
       		mailArrivalForm.setContainerNo(containerDetailsVO.getContainerNumber());
//Added by A-5945	for ICRD-130512
       		mailArrivalForm.setContainerType(containerDetailsVO.getContainerType());

       		//Added for ICRD-128804 starts
       		if(ULD_TYPE.equalsIgnoreCase(containerDetailsVO.getContainerType())){
       		mailArrivalForm.setBarrowCheck(false);
       		}else{
       		mailArrivalForm.setBarrowCheck(true);
       		}
       		//Added for ICRD-128804 starts ends
       		mailArrivalForm.setDisableFlag("Y");
       }else{
    	   ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
    	   containerDetailsVO.setContainerNumber("");
    	   containerDetailsVO.setOperationFlag("I");
    	   mailArrivalSession.setContainerDetailsVOs(containerDetailsVOs);
           mailArrivalSession.setContainerDetailsVO(containerDetailsVO);
           mailArrivalForm.setContainerNo("");
           mailArrivalForm.setDisableFlag("");
         //Added for ICRD-128804 starts
           mailArrivalForm.setBarrowCheck(false);
           mailArrivalForm.setContainerType(ULD_TYPE);
          //Added for ICRD-128804 ends
       }
        }catch (SystemException e) {
			e.getMessage();
		}

       log.log(Log.FINE, "*******containerDetailsVOs******", containerDetailsVOs.size());
	/*
        * Getting OneTime values
        */
       Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (oneTimes != null) {
		   List<String> sortedOnetimes ;
		   Collection<OneTimeVO> catVOs = oneTimes.get(MAILCATEGORY);
		   Collection<OneTimeVO> hniVOs = oneTimes.get(MAILHNI);
		   Collection<OneTimeVO> rsnVOs = oneTimes.get(MAILRI);
		   Collection<OneTimeVO> mailClassVOs = oneTimes.get(MAILCLASS);
		   Collection<OneTimeVO> containerTypeVOs = oneTimes.get(CONTAINERTYPE);
			Collection<OneTimeVO> damageCodes = oneTimes.get(DAMAGE_CODES);
			Collection<OneTimeVO> mailCompanyCodes = oneTimes.get(MAILCMPCOD_CODES);
		   Collection<OneTimeVO> newTypeVOs = new ArrayList<OneTimeVO>();

			if (containerTypeVOs != null && containerTypeVOs.size() > 0) {

				for (OneTimeVO oneTimeVO : containerTypeVOs) {

					if ("U".equals(oneTimeVO.getFieldValue())) {
						newTypeVOs.add(oneTimeVO);
					}
				}

				for (OneTimeVO oneTimeVO : containerTypeVOs) {

					if (!"U".equals(oneTimeVO.getFieldValue())) {
						newTypeVOs.add(oneTimeVO);
					}
				}
			}
			mailArrivalSession.setOneTimeDamageCodes(damageCodes);
		   if(hniVOs!=null && !hniVOs.isEmpty()){
			   sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO hniVo: hniVOs){
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);


			int i=0;
			for(OneTimeVO hniVo: hniVOs){
				hniVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			if(rsnVOs!=null && !rsnVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: rsnVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);


			int i=0;
			for(OneTimeVO riVo: rsnVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
		   mailArrivalSession.setOneTimeCat(catVOs);
		   mailArrivalSession.setOneTimeRSN(rsnVOs);
		   mailArrivalSession.setOneTimeHNI(hniVOs);
		   mailArrivalSession.setOneTimeMailClass(mailClassVOs);
		   mailArrivalSession.setOneTimeContainerType(newTypeVOs);
		   mailArrivalSession.setOneTimeCompanyCode(mailCompanyCodes);
		}
	    log.log(Log.FINE, "*******containerDetailsVO******", mailArrivalSession.getContainerDetailsVO());
	    mailArrivalSession.setPols(pols);
	invocationContext.target = TARGET;


      log.exiting("ScreenloadArriveMailCommand","execute");

   }


	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;

		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(MAILRI);
			fieldValues.add(MAILCATEGORY);
			fieldValues.add(MAILHNI);
			fieldValues.add(MAILCLASS);
			fieldValues.add(CONTAINERTYPE);
			fieldValues.add(DAMAGE_CODES);
			fieldValues.add(MAILCMPCOD_CODES);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * A-3251
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return
	 */
	private void setUnitComponent(String stationCode,
			MailArrivalSession mailArrivalSession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.WEIGHT);
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			mailArrivalSession.setWeightRoundingVO(unitRoundingVO);

		   }catch(UnitException unitException) {
				unitException.getErrorCode();
		   }

	}
    
	
	/**
	 * 
	 * @return
	 */
    private Collection<String> getSystemParameterCodes(){
             Collection<String> systemParameterCodes = new ArrayList<String>();
               systemParameterCodes.add(SYSPAR_DEFUNIT_WEIGHT);
               return systemParameterCodes;
      }
    private Collection<String> getStationParameterCodes(){
        Collection<String> systemParameterCodes = new ArrayList<String>();
          systemParameterCodes.add(STNPAR_DEFUNIT_WEIGHT);
          
          return systemParameterCodes;
	}

}
