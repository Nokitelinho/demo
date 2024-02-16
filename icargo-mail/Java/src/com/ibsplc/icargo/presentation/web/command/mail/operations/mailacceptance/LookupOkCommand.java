/*
 * LookupOkCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.text.TextFormatter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchConsignmentForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 * Revision History
 * Version      	Date      	    Author        		Description
 *
 *  0.1         Jul 1 2016  	    A-5991                Coding
 */
public class LookupOkCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET = "save_success";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.searchconsignment";
   private static final String SCREEN_ID_AXP = "mailtracking.defaults.mailacceptance";
   private static final int  WEIGHT_DIVISION_FACTOR = 10;
   private static final double  MINIMUM_VOLUME = 0.01D;
   private static final double  NO_VOLUME = 0.00D;
   private static final String BLANK = "";
   private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("LookupOkCommand","execute-");

    	SearchConsignmentForm carditEnquiryForm =
    		(SearchConsignmentForm)invocationContext.screenModel;
    	SearchConsignmentSession carditEnquirySession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	MailAcceptanceSession mailAcceptanceSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID_AXP);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		AreaDelegate areaDelegate = new AreaDelegate();
		Map stationParameters = null; 
	    	String stationCode = logonAttributes.getStationCode();
    	String companyCode=logonAttributes.getCompanyCode();
    	try {
			stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
		} catch (BusinessDelegateException e1) {
			
			e1.getMessage();
		}
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();

		Collection<MailbagVO> mailbagVOsForListing = carditEnquirySession.getMailBagVOsForListing();
		
		Collection<MailbagVO> newMailbagVOsForListing = new ArrayList<MailbagVO>();
		
//		if (mailbagVOsForListing != null && mailbagVOsForListing.size()>0) {
//			newMailbagVOsForListing.addAll(mailbagVOsForListing);
//		}
		
		Collection<MailbagVO> mailbagVOs = carditEnquirySession.getMailbagVOsCollection();
		
		
		Collection<MailbagVO> newMailbagVOs = containerDetailsVO.getMailDetails();
		ArrayList<DespatchDetailsVO> newDespatchVOs = new ArrayList<DespatchDetailsVO>(containerDetailsVO.getDesptachDetailsVOs());
		String[] primaryKey = carditEnquiryForm.getSelectMail();
     	   int cnt=0;
		   int count = 0;
	       int primaryKeyLen = 0;
	       if(primaryKey != null) {
	    	   primaryKeyLen = primaryKey.length;
	       }
	       double vol = 0.0D;
	       
//	       if (mailbagVOsForListing != null && mailbagVOsForListing.size()>0) {
//	    	   for (MailbagVO mailbagvo : mailbagVOsForListing){
//	    		   if("N".equals(mailbagvo.getInList())){
//	    			   newMailbagVOsForListing.remove(mailbagvo); 
//	    		   }
//	    	   }
//	       }
	       /*
	        * To collect the selected rows + Rows in "inList" Collection
	        */
	       if (mailbagVOs != null && mailbagVOs.size() != 0) {
				ArrayList<MailbagVO> mailbagVOArraylist = new ArrayList<MailbagVO>(mailbagVOs);
				if(primaryKey != null && 
						primaryKey.length > 0 && 
						!"".equals(primaryKey[0])) {
					for(String selectedrow:primaryKey){
						MailbagVO mailbagVO = mailbagVOArraylist.get(Integer.parseInt(selectedrow));
						if(mailbagVO != null && 
								!MailConstantsVO.FLAG_YES.equalsIgnoreCase(mailbagVO.getInList()) && 
								!MailConstantsVO.FLAG_YES.equals(mailbagVO.getAccepted())){
							mailbagVO.setInList(MailConstantsVO.FLAG_YES);
							MailbagVO newMailInList = new MailbagVO();
							try {
								BeanHelper.copyProperties(newMailInList,mailbagVO);
							} catch (SystemException e) {
								log.log(Log.FINE, "BeanHelper.copyProperties Failed !!!");
							}
							if (newMailbagVOsForListing != null && newMailbagVOsForListing.size()> 0 ) {
								newMailbagVOsForListing.add(newMailInList); 
							}else{
								newMailbagVOsForListing = new ArrayList<MailbagVO>();
								newMailbagVOsForListing.add(newMailInList); 
							}	
						}
					}
				}
//	    	   for (MailbagVO mailbagVO : mailbagVOs) {
//	    		   if ((cnt < primaryKeyLen) &&( String.valueOf(count)).
//	    				   equalsIgnoreCase(primaryKey[cnt].trim())) {
//	    			   if (mailbagVOsForListing != null && mailbagVOsForListing.size()> 0 ) {
//	    				   if(mailbagVO.getInList ()== null ){
//	    					   newMailbagVOsForListing.add(mailbagVO); 
//	    					   mailbagVO.setInList("Y");
//	    				   }
//	    			   }
//	    			   else{
//	    				   newMailbagVOsForListing = new ArrayList<MailbagVO>();
//	    				   newMailbagVOsForListing.add(mailbagVO); 
//	    			   }
//	    		   }
//	    	   }
	    	   if (newMailbagVOsForListing != null && newMailbagVOsForListing.size() > 0) {
	    		   for (MailbagVO mailbagVO : newMailbagVOsForListing) {

	    			   if(mailbagVO.getReceptacleSerialNumber()!=null && mailbagVO.getReceptacleSerialNumber().length()>0){

	    				   mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
	    				   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
	    				   mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
	    				   mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
	    				   mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
	    				   mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
	    				   mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
	    				   mailbagVO.setDamageFlag("N");
	    				   mailbagVO.setArrivedFlag("N");
	    				   mailbagVO.setDeliveredFlag("N"); 
	    				   mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
	    				   mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	    				   mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
	    				   mailbagVO.setContainerType(containerDetailsVO.getContainerType());
	    				   mailbagVO.setPou(containerDetailsVO.getPou());
	    				   mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	    				   mailbagVO.setOperationalFlag("I");
	    				   double roundedDisplayWeight=0;
	    				   if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(mailbagVO.getWeight().getDisplayUnit())){
	    					   roundedDisplayWeight=round(mailbagVO.getWeight().getDisplayValue(),1); 
	    					}
	    					else {
	    						roundedDisplayWeight=round(mailbagVO.getWeight().getDisplayValue(),0);
	    					}   
	    				  /* String wt = String.valueOf(mailbagVO.getWeight().getDisplayValue());
							log.log(Log.FINE, "wt ...in command", wt);
							int len = wt.indexOf(".");
							String wgt = wt;
							if(len > 0){
								wgt = new StringBuilder(wt.substring(0,len)).append(wt.substring(len+1,len+2)).toString();
							}else{
								wgt = new StringBuilder(wt).append("0").toString();            
							}   
							String stdwgt = wgt;
							if(wgt.length() == 3){
								stdwgt = new StringBuilder("0").append(wgt).toString();  
							}
							if(wgt.length() == 2){
								stdwgt = new StringBuilder("00").append(wgt).toString();
							}
							if(wgt.length() == 1){
								stdwgt = new StringBuilder("000").append(wgt).toString();
							}*/
	    				   //mailbagVO.setStrWeight(weightFormatter(mailbagVO.getWeight()));      
							//added by A-7371
							if(mailbagVO.getStrWeight()==null){
							mailbagVO.setStrWeight(new Measure(UnitConstants.MAIL_WGT,0.0,roundedDisplayWeight,mailbagVO.getWeight().getDisplayUnit()));  //Added by A-7550                    
							}     
							mailbagVO.setWeight(mailbagVO.getStrWeight());  
							
	    					/*if (!BLANK.equals(carditEnquiryForm.getDensity())){	
	    						if(mailbagVO.getStrWeight()!=null){//added by A-7371
	    					  // vol =mailbagVO.getStrWeight().getSystemValue()/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(carditEnquiryForm.getDensity()));	
	    					   vol =mailbagVO.getStrWeight().getRoundedSystemValue()/(WEIGHT_DIVISION_FACTOR * Double.parseDouble(carditEnquiryForm.getDensity()));	//Added by A-7550
	    						}
		    				   if(NO_VOLUME != vol) {
		    					   if(MINIMUM_VOLUME > vol) {
		    						   vol = MINIMUM_VOLUME;
		    					   }else {
		    						   vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
		    					   }
		    				   }
	    				   }*/
							String stationVolumeUnit=null;    
	    			   if (!BLANK.equals(carditEnquiryForm.getDensity())){	
							double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailbagVO.getStrWeight().getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,mailbagVO.getStrWeight().getDisplayValue());
							double ActVol=(weightForVol/Double.parseDouble(carditEnquiryForm.getDensity()));
							 stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
							vol=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
	    			   }
	    			   if(NO_VOLUME != vol) {
    					   if(MINIMUM_VOLUME > vol) {
    						   vol = MINIMUM_VOLUME;
    					   }else {
    						   vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
    					   }
    				   }
	    				  
	    				  // mailbagVO.setVolume(vol);
	    				   mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,0,vol,stationVolumeUnit)); //Added by A-7550

	    				   /*
	    				    * Added By RENO K ABRAHAM : ANZ BUG : 37646
	    				    * As a part of performance Upgrade
	    				    * START.
	    				    */
	    				   mailbagVO.setDisplayLabel("N");   //Modified by a-7871 for ICRD-218762
	    				   //END

	    				   if (newMailbagVOs != null && newMailbagVOs.size() != 0) {
	    					   boolean isDuplicate = false;
	    					   for (MailbagVO alreadyMailbagVO : newMailbagVOs) {
	    						   if(alreadyMailbagVO.getMailbagId()!=null
	    								   && alreadyMailbagVO.getMailbagId().equals(mailbagVO.getMailbagId())){
	    							   isDuplicate = true;
	    						   }
	    					   }
	    					   if(!isDuplicate){
	    						   newMailbagVOs.add(mailbagVO);
	    					   }
	    				   }else{
	    					   newMailbagVOs = new ArrayList<MailbagVO>();
	    					   newMailbagVOs.add(mailbagVO);
	    				   }
	    			   }
	    			   else{
	    				   DespatchDetailsVO despatchDetailsVO = new DespatchDetailsVO();
	    				   despatchDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	    				   despatchDetailsVO.setContainerNumber(containerDetailsVO.getContainerNumber());
	    				   despatchDetailsVO.setCarrierId(containerDetailsVO.getCarrierId());
	    				   despatchDetailsVO.setFlightNumber(containerDetailsVO.getFlightNumber());
	    				   despatchDetailsVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
	    				   despatchDetailsVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
	    				   despatchDetailsVO.setUldNumber(containerDetailsVO.getContainerNumber());
	    				   despatchDetailsVO.setContainerType(containerDetailsVO.getContainerType());
	    				   despatchDetailsVO.setPou(containerDetailsVO.getPou());
	    				   despatchDetailsVO.setStatedBags(mailbagVO.getAcceptedBags());//todo
	    				   despatchDetailsVO.setStatedWeight(mailbagVO.getAcceptedWeight());//todo

	    				  // vol =despatchDetailsVO.getStatedWeight()/Double.parseDouble(carditEnquiryForm.getDensity());
	    				  vol =despatchDetailsVO.getStatedWeight().getRoundedSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity());//added by A550
	    				   if(NO_VOLUME != vol) {
	    					   if(MINIMUM_VOLUME > vol) {
	    						   vol = MINIMUM_VOLUME;
	    					   }else {
	    						   vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
	    					   }
	    				   }
	    				   despatchDetailsVO.setStatedVolume(new Measure(UnitConstants.MAIL_WGT, vol)); //Added by A-7550

	    				   despatchDetailsVO.setOperationalFlag("I");
	    				   despatchDetailsVO.setOriginOfficeOfExchange(mailbagVO.getOoe());
	    				   despatchDetailsVO.setDestinationOfficeOfExchange(mailbagVO.getDoe());
	    				   despatchDetailsVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
	    				   despatchDetailsVO.setMailClass(mailbagVO.getMailClass());
	    				   despatchDetailsVO.setMailSubclass(mailbagVO.getMailSubclass());
	    				   despatchDetailsVO.setYear(mailbagVO.getYear());
	    				   despatchDetailsVO.setAcceptedBags(mailbagVO.getAcceptedBags());//todo
	    				   despatchDetailsVO.setAcceptedWeight(mailbagVO.getAcceptedWeight());//todo

	    				   /*
	    				    * Added By RENO K ABRAHAM
	    				    * As a part of performance Upgrade
	    				    * START.
	    				    */
	    				   despatchDetailsVO.setDisplayLabel("Y");
	    				   //END
	    				  // vol =despatchDetailsVO.getAcceptedWeight()/Double.parseDouble(carditEnquiryForm.getDensity());
	    				   vol =despatchDetailsVO.getAcceptedWeight().getSystemValue()/Double.parseDouble(carditEnquiryForm.getDensity());//added by A-7371
	    				   if(NO_VOLUME != vol) {
	    					   if(MINIMUM_VOLUME > vol) {
	    						   vol = MINIMUM_VOLUME;
	    					   }else {
	    						   vol = Double.parseDouble(TextFormatter.formatDouble(vol , 2));
	    					   }
	    				   }
	    				   //despatchDetailsVO.setAcceptedVolume(vol);
	    				   despatchDetailsVO.setAcceptedVolume(new Measure(UnitConstants.VOLUME, vol)); //Added by A-7550
	    				   despatchDetailsVO.setConsignmentDate(mailbagVO.getConsignmentDate());
	    				   despatchDetailsVO.setConsignmentNumber(mailbagVO.getConsignmentNumber());
	    				   despatchDetailsVO.setPaCode(mailbagVO.getPaCode());
	    				   despatchDetailsVO.setDsn(mailbagVO.getDespatchSerialNumber());
	    				   despatchDetailsVO.setAirportCode(logonAttributes.getAirportCode());
	    				   despatchDetailsVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false));
	    				   despatchDetailsVO.setCarrierCode(containerDetailsVO.getCarrierCode());

	    				   if (newDespatchVOs != null && newDespatchVOs.size() != 0) {
	    					   boolean isDuplicate = false;
	    					   for (DespatchDetailsVO  alreadyDespatchVO: newDespatchVOs) {
	    						   String alreadypk = alreadyDespatchVO.getOriginOfficeOfExchange()
	    						   +alreadyDespatchVO.getDestinationOfficeOfExchange()
	    						   +alreadyDespatchVO.getMailCategoryCode()
	    						   +alreadyDespatchVO.getMailSubclass()
	    						   +alreadyDespatchVO.getDsn()
	    						   +alreadyDespatchVO.getYear();
	    						   String mainpk = mailbagVO.getOoe()
	    						   +mailbagVO.getDoe()
	    						   +mailbagVO.getMailCategoryCode()
	    						   +mailbagVO.getMailSubclass()
	    						   +mailbagVO.getDespatchSerialNumber()
	    						   +mailbagVO.getYear();

	    						   if(alreadypk!=null
	    								   && alreadypk.equals(mainpk)){
	    							   isDuplicate = true;
	    						   }
	    					   }
	    					   if(!isDuplicate){
	    						   newDespatchVOs.add(despatchDetailsVO);
	    					   }
	    				   }else{
	    					   newDespatchVOs = new ArrayList<DespatchDetailsVO>();
	    					   newDespatchVOs.add(despatchDetailsVO);
	    				   }
	    			   }
	    			   cnt++;
	    		   }
	    		   count++;
	    	   }
	       }



		containerDetailsVO.setMailDetails(newMailbagVOs);
		containerDetailsVO.setDesptachDetailsVOs(newDespatchVOs);
		mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
		carditEnquiryForm.setLookupClose("CLOSE");
    	invocationContext.target = TARGET;
    	log.exiting("LookupOkCommand","execute");

   }

    /**
     * @author a-2107
     * @param weight
     * @return
     */

    private String weightFormatter(Double weight) {
    	String weightString = String.valueOf(weight);
        String weights[] = weightString.split("[.]");
        StringBuilder flatWeight = new StringBuilder(weights[0]).append(weights[1]);
        if (flatWeight.length() >= 4) {
            return flatWeight.substring(0, 4);
        }  else {
            StringBuilder zeros = new StringBuilder();
            int zerosRequired = 4 - flatWeight.length();
            for(int i = 0; i < zerosRequired; i++) {
                zeros.append("0");
            }
            return zeros.append(flatWeight).toString();
        }
    }
    private Collection<String> getStationParameterCodes()
	  {
	    Collection stationParameterCodes = new ArrayList();
	    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
	    return stationParameterCodes;
  }	
   private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
		UnitConversionNewVO unitConversionVO= null;
		try {
			unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		double convertedValue = unitConversionVO.getToValue();
		return convertedValue;
	}
   /**
	 * Added by :A-8353 
	 * @param val
	 * @param places
	 * @return
	  */
   	private double round(double val, int places) {
		long factor = (long) Math.pow(10, places);
		val = val * factor;
		long tmp = Math.round(val);
		return (double) tmp / factor;
	} 

}
