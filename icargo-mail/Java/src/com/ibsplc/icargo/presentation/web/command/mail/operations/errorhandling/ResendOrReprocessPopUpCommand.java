package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailTrackingErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ErrorHandlingPopUpForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class ResendOrReprocessPopUpCommand extends BaseCommand{

	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL  OPERATIONS");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";

	/**
	 * The ScreenID 
	 */
	private static final String SCREEN_ID = "mailtracking.defaults.errorhandligpopup";

	/**
	 * Target mappings for succes 
	 */
	private static final String PROCESS_SUCCESS = "openpopup_success";
	
	 private static final String MAILCATEGORY = "mailtracking.defaults.mailcategory";
	 private static final String MAIL_HNI = "mailtracking.defaults.highestnumbermail";
	 private static final String MAIL_RI = "mailtracking.defaults.registeredorinsuredcode";
	 private static final String MAIL_CMPCOD = "mailtracking.defaults.companycode";
	 private static final String NO_OF_CHAR_ALLOWED_FOR_MAILTAG = "mailtracking.defaults.noofcharsallowedformailtag";
	 private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps";
	   private static final String SYSTEM_WEIGHT_UNIT="system.defaults.unit.weight";
	 private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";
	 private Map<String,String> exchangeOfficeMap;	
	
   public void execute(InvocationContext invocationContext)
	                         throws CommandInvocationException {
	   
	   log.entering("ResendOrReprocessPopupCommand", "execute");		
	   ErrorHandlingPopUpForm errorHandlingPopUpForm = (ErrorHandlingPopUpForm) invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		
		String txnId=errorHandlingPopUpForm.getSelectedtxnid();
		String DEST_FLT="-1"; 
		log.log(Log.FINE, "trxnid",txnId);
		
		 String[] txnIds = null;    
	   
	     if(errorHandlingPopUpForm.getSelectedtxnid()!=null &&
	    		 errorHandlingPopUpForm.getSelectedtxnid().trim().length()>0){
	    	 txnIds = errorHandlingPopUpForm.getSelectedtxnid().split(",");
	    	
	     }
	     
	     errorHandlingPopUpForm.setTotalViewRecords(String.valueOf(txnIds.length));
	     errorHandlingPopUpForm.setLastPopupPageNum(errorHandlingPopUpForm.getTotalViewRecords());
		List<String> sortedOnetimes ;
		MailTrackingErrorHandlingSession mailTrackingErrorHandlingSession= (MailTrackingErrorHandlingSession) getScreenSession(
				MODULE_NAME, SCREEN_ID)	;
		mailTrackingErrorHandlingSession.setTxnids(txnIds);
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(companyCode);
		if(oneTimes!=null){
			Collection<OneTimeVO> catVOs = oneTimes.get(MAILCATEGORY);
			Collection<OneTimeVO> hniVOs = oneTimes.get(MAIL_HNI);
			Collection<OneTimeVO> riVOs = oneTimes.get(MAIL_RI);
			Collection<OneTimeVO> cmpcodVos = oneTimes.get(MAIL_CMPCOD);
			mailTrackingErrorHandlingSession.setOneTimeCat(catVOs);
			mailTrackingErrorHandlingSession.setOneTimeCompanyCode(cmpcodVos);
			
			
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
			if(riVOs!=null && !riVOs.isEmpty()){
				sortedOnetimes= new ArrayList<String>();
				for(OneTimeVO riVo: riVOs){
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);
			
			
			int i=0;
			for(OneTimeVO riVo: riVOs){
				riVo.setFieldValue(sortedOnetimes.get(i++));
			}
			}
			mailTrackingErrorHandlingSession.setOneTimeHni(hniVOs);
			mailTrackingErrorHandlingSession.setOneTimeRi(riVOs);
		}	
		
		// for(String txnIdSelected : txnIds){        
		Object[] txndetail = getTxnParameters(companyCode,txnIds[0]);
		log.log(Log.FINE, "size of object array",txndetail!=null?txndetail.length:0);
		log.log(Log.FINE, "object array",txndetail);
		MailUploadVO mailUplVO = new MailUploadVO();
		if(txndetail!=null && txndetail.length >0) {
		for(Object obj : txndetail){			
			ArrayList<MailUploadVO> mailBagVOs=null;
			if (obj instanceof Collection)
			{
				mailBagVOs =(ArrayList<MailUploadVO>)obj;
				log.log(Log.FINE, "object mails", mailBagVOs);
			}else if(obj instanceof MailUploadVO){
				mailUplVO= (MailUploadVO)obj;
				mailBagVOs = new ArrayList<MailUploadVO>();
				mailBagVOs.add(mailUplVO);
			}
					
			if (obj instanceof String)
			{
				String scannedport=(String)obj;
				log.log(Log.FINE, "object mails", scannedport);          
			}
		
			if(mailBagVOs!=null && mailBagVOs.size()>0)         
			{
			for (MailUploadVO mailUploadVO : mailBagVOs) {
				String function = mailUploadVO.getScanType();
				errorHandlingPopUpForm.setSelectedtxnid(txnIds[0]);    
			errorHandlingPopUpForm.setFunctionType(function);
			log.log(Log.FINE, "function",function);
					if (("DLV").equals(function) || ("OFL").equals(function) || ("RTN").equals(function) || ("DMG").equals(function)) {
				log.log(Log.FINE, " in side if looop",function);
				errorHandlingPopUpForm.setFlightCarrierCode(null);
				errorHandlingPopUpForm.setFlightNumber(null);
				errorHandlingPopUpForm.setDestination(null);
				errorHandlingPopUpForm.setFlightDate(null);
				errorHandlingPopUpForm.setContainer(null);
				errorHandlingPopUpForm.setPou(null);
				
				log.log(Log.FINE, " exiting if looop",function);	
//Added 'ACP' function type also as part of ICRD-156832
					} else if (("ARR").equals(function) || ("EXP").equals(function) || ("ACP").equals(function)) {
						if (mailUploadVO.getMailCompanyCode() != null && mailUploadVO.getMailCompanyCode().trim().length() > 0) {
							errorHandlingPopUpForm.setMailCompanyCode(mailUploadVO.getMailCompanyCode());

			}
					errorHandlingPopUpForm.setPou(null);
				}
					if (mailUploadVO.getCarrierCode() != null && mailUploadVO.getCarrierCode().trim().length() > 0) {
						errorHandlingPopUpForm.setFlightCarrierCode(mailUploadVO.getCarrierCode());
					}
					if (mailUploadVO.getFlightNumber() != null && mailUploadVO.getFlightNumber().trim().length() > 0 && !mailUploadVO.getFlightNumber().equals(DEST_FLT)) {
						errorHandlingPopUpForm.setFlightNumber(mailUploadVO.getFlightNumber());
					}
					
					 //Added by a-4810 for icrd-84794
					if (("ACP").equals(function)) {
						if (mailUploadVO.getDestination() != null && mailUploadVO.getDestination().trim().length() > 0) {
							errorHandlingPopUpForm.setDestination(mailUploadVO.getDestination());
			         	}  
						if (mailUploadVO.getToPOU() != null && mailUploadVO.getToPOU().trim().length() > 0) {
							errorHandlingPopUpForm.setPou(mailUploadVO.getContainerPOU());
						}
					}   
               if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(function))        
					{
						errorHandlingPopUpForm.setPou(mailUploadVO.getScannedPort());    
					}
					if (("ACP").equals(function) || ("OFL").equals(function) || ("ARR").equals(function) || ("EXP").equals(function)) {
						if (mailUploadVO.getContainerNumber() != null && mailUploadVO.getContainerNumber().trim().length() > 0) {
							errorHandlingPopUpForm.setContainer(mailUploadVO.getContainerNumber());
					  }
					}
					if (mailUploadVO.getFlightDate() != null) {
						errorHandlingPopUpForm.setFlightDate(mailUploadVO.getFlightDate().toDisplayDateOnlyFormat());
			}
					if (mailUploadVO.getContainerType().trim().length() > 0 && ("B").equals(mailUploadVO.getContainerType())) {
				errorHandlingPopUpForm.setBulk("Y");
//Added by A-5945 for ICRD-141814
			errorHandlingPopUpForm.setBarrowCheck(true);     
			
					} else {
			errorHandlingPopUpForm.setBulk("N"); 
//Added by A-5945 for ICRD-141814
			errorHandlingPopUpForm.setBarrowCheck(false); 
					}   
//Added by A-5945 for ICRD-88905 starts
					if (("").equals(mailUploadVO.getContainerNumber()) && mailUploadVO.getContainerNumber().trim().length() == 0) {
						try {
							if(mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().length()<29 && !isValidMailtag(mailUploadVO.getMailTag().length())){
							 errorHandlingPopUpForm.setContainer(mailUploadVO.getMailTag());
}
						} catch (BusinessDelegateException e) {
							e.getMessage();
					}
					}
					if(("OFL").equals(function)){
						try {
							if(mailUploadVO.getMailTag()!=null && mailUploadVO.getMailTag().length()!=29 &&
									!isValidMailtag(mailUploadVO.getMailTag().length())){
								mailUploadVO.setMailTag("");										
							}
						} catch (BusinessDelegateException e) {
							e.getMessage();
						}
					}
					//Added by A-7540 
					String weightUnit="";
			    	String mailWgt="";
			    	Measure mailWt=null;
			    	Map stationParameters = null;
			    	 AreaDelegate areaDelegate = new AreaDelegate(); 
					try {
						 stationParameters = areaDelegate.findStationParametersByCode(companyCode, logonAttributes.getAirportCode(), getStationParameterCodes());
						 weightUnit=(String)stationParameters.get(STNPAR_DEFUNIT_WGT);
					} catch ( BusinessDelegateException businessDelegateException) {
						businessDelegateException.getMessage();
		  			}
					/*try {
						weightUnit = findSystemParameterValue(SYSTEM_WEIGHT_UNIT);
					} catch (BusinessDelegateException e) {
						e.getMessage();
					}*/
						String systemParameterValue=null;
						MailbagVO mailbagVO=null;
						String mailbagId = mailUploadVO.getMailTag();
						try {
							
							systemParameterValue = findSystemParameterValue(USPS_DOMESTIC_PA);
						} catch (BusinessDelegateException e) {
							log.log(Log.SEVERE, "System Exception Caught");
						} 
						try{
							mailbagVO =new MailTrackingDefaultsDelegate().findMailDetailsForMailTag(
									companyCode,mailUploadVO.getMailTag().toUpperCase()) ;
				  		}catch (BusinessDelegateException businessDelegateException) {
			  				handleDelegateException(businessDelegateException);
			  			}
						if(mailbagVO!=null&&mailbagVO.getMailbagId()!=null&&
								!mailbagVO.getMailbagId().isEmpty()){
							errorHandlingPopUpForm.setMailBag(mailbagVO.getMailbagId());
							errorHandlingPopUpForm.setOoe(mailbagVO.getOoe());
							errorHandlingPopUpForm.setGpaCode(mailbagVO.getPaCode());
							errorHandlingPopUpForm.setDoe(mailbagVO.getDoe());
							errorHandlingPopUpForm.setCategory(mailbagVO.getMailCategoryCode());
							errorHandlingPopUpForm.setSubclass(mailbagVO.getMailSubclass());
							errorHandlingPopUpForm.setYear(String.valueOf(mailbagVO.getYear()));
							errorHandlingPopUpForm.setDsn(mailbagVO.getDespatchSerialNumber());
							errorHandlingPopUpForm.setRsn(mailbagVO.getReceptacleSerialNumber());
							errorHandlingPopUpForm.setHni(mailbagVO.getHighestNumberedReceptacle());
							errorHandlingPopUpForm.setRi(mailbagVO.getRegisteredOrInsuredIndicator());
						double covertedWeight=0.0;
						if(mailbagVO.getMailbagId().length()==29){
    					 covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit,Double.parseDouble(mailbagId.substring(25,29)));
						}
						else if(mailbagVO.getMailbagId().length()==12){
							covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit,Double.parseDouble(mailbagId.substring(10,12)));
						}							/*double conDisplayWeight=0;
    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
    						 conDisplayWeight=round(covertedWeight,1); 
    					}
    					else {
    						conDisplayWeight=round(covertedWeight,0);
    					} */
    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
						mailWgt=String.valueOf(mailWt.getSystemValue());
						errorHandlingPopUpForm.setWeightMeasure(mailWt);	
						errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
						}
					else if (mailUploadVO.getMailTag() != null && mailUploadVO.getMailTag().trim().length() == 29) {
						String mailBagId = mailUploadVO.getMailTag();
						//added by A-7540
						errorHandlingPopUpForm.setMailBag(mailBagId);
						errorHandlingPopUpForm.setOoe(mailBagId.substring(0, 6));
						errorHandlingPopUpForm.setDoe(mailBagId.substring(6, 12));
						errorHandlingPopUpForm.setCategory(mailBagId.substring(12, 13));
						errorHandlingPopUpForm.setSubclass(mailBagId.substring(13, 15));
						errorHandlingPopUpForm.setYear((mailBagId.substring(15, 16)));
						errorHandlingPopUpForm.setDsn(mailBagId.substring(16, 20));
						errorHandlingPopUpForm.setRsn(mailBagId.substring(20, 23));
						errorHandlingPopUpForm.setHni(mailBagId.substring(23, 24));
						errorHandlingPopUpForm.setRi(mailBagId.substring(24, 25));
						double covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"H",weightUnit,Double.parseDouble(mailBagId.substring(25,29)));
							/*double conDisplayWeight=0;
	    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
	    						 conDisplayWeight=round(covertedWeight,1); 
	    					}
	    					else {
	    						conDisplayWeight=round(covertedWeight,0);
	    					} */
	    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
							mailWgt=String.valueOf(mailWt.getSystemValue());
							errorHandlingPopUpForm.setWeightMeasure(mailWt);	
							errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
							}
					else if(mailbagId.length()==10||mailbagId.length()==12){
						String routIndex=mailbagId.substring(4,8).toUpperCase();
						String org=null;
						String dest=null;
						String mailOrigin="";
						String mailDest="";
						Collection<RoutingIndexVO> routingIndexVOs=new ArrayList<RoutingIndexVO>();
						RoutingIndexVO routingIndexFilterVO=new RoutingIndexVO() ;
						routingIndexFilterVO.setRoutingIndex(routIndex);
						routingIndexFilterVO.setCompanyCode(companyCode);
						routingIndexFilterVO.setScannedDate(mailUploadVO.getScannedDate());
						try {
							routingIndexVOs=new MailTrackingDefaultsDelegate().findRoutingIndex(routingIndexFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							handleDelegateException(businessDelegateException);
						}
						exchangeOfficeMap=new HashMap<String,String>();
						if(routingIndexVOs.size()>0){
						for(RoutingIndexVO routingIndexVO:routingIndexVOs){
						if(routingIndexVO!=null&&routingIndexVO.getRoutingIndex()!=null){
							 org=routingIndexVO.getOrigin();
							 dest=routingIndexVO.getDestination();
							try {
								exchangeOfficeMap=new MailTrackingDefaultsDelegate().findOfficeOfExchangeForPA(companyCode,findSystemParameterValue(USPS_DOMESTIC_PA));
							} catch (BusinessDelegateException businessDelegateException) {
								handleDelegateException(businessDelegateException);;
							} 
							if(exchangeOfficeMap!=null && !exchangeOfficeMap.isEmpty()){
					    		if(exchangeOfficeMap.containsKey(org)){		
					    			 mailOrigin = exchangeOfficeMap.get(org);
								     errorHandlingPopUpForm.setOoe(mailOrigin.toUpperCase());
					    		}
					    		if(exchangeOfficeMap.containsKey(dest)){
					    			mailDest = exchangeOfficeMap.get(dest);
					    		}
                                    errorHandlingPopUpForm.setDoe(mailDest.toUpperCase());
					    		}
							String category="B";
							errorHandlingPopUpForm.setCategory(category.toUpperCase());
							String mailClass=mailbagId.substring(3,4);
							String subClass = mailClass+"X";
							errorHandlingPopUpForm.setSubclass(subClass.toUpperCase());
							int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
							String lastDigitOfYear = String.valueOf(lastTwoDigits).substring(1,2);
							String year=lastDigitOfYear;
							errorHandlingPopUpForm.setYear(year);
							String companycode=logonAttributes.getCompanyCode();
							MailbagVO newMailbagVO=new MailbagVO();
							newMailbagVO.setCompanyCode(companycode);
							newMailbagVO.setYear(Integer.parseInt(errorHandlingPopUpForm.getYear()));
							try {
								newMailbagVO =new MailTrackingDefaultsDelegate().findDsnAndRsnForMailbag(newMailbagVO);
							} catch (BusinessDelegateException businessDelegateException) {
								handleDelegateException(businessDelegateException);
							}
							if(newMailbagVO.getDespatchSerialNumber()!=null){
								String dsn=newMailbagVO.getDespatchSerialNumber();	
								errorHandlingPopUpForm.setDsn(dsn.toUpperCase());
								}
								if(newMailbagVO.getReceptacleSerialNumber()!=null){
								String rsn=newMailbagVO.getReceptacleSerialNumber();
								errorHandlingPopUpForm.setRsn(rsn.toUpperCase());
								}
								String hni="9";
								errorHandlingPopUpForm.setHni(hni.toUpperCase());
								String ri="9";
								errorHandlingPopUpForm.setRi(ri.toUpperCase());
								if(mailbagId.length()==12){
									double covertedWeight =unitConvertion(UnitConstants.MAIL_WGT,"L",weightUnit,Double.parseDouble(mailbagId.substring(10,12)));
									/*double conDisplayWeight=0;
			    					if(UnitConstants.WEIGHT_UNIT_KILOGRAM.equals(weightUnit)){
			    						 conDisplayWeight=round(covertedWeight,1); 
			    					}
			    					else {
			    						conDisplayWeight=round(covertedWeight,0);
			    					} */
			    					mailWt=new Measure(UnitConstants.MAIL_WGT,covertedWeight); 
									mailWgt=String.valueOf(mailWt.getSystemValue());
									errorHandlingPopUpForm.setWeightMeasure(mailWt);	
									errorHandlingPopUpForm.setWeight(String.valueOf(mailWgt));
								}
						  }
						  /*else{
							    errorHandlingPopUpForm.setInvalidMailbagId(true);
						     }*/
						  }
						}
						/*else{
						    errorHandlingPopUpForm.setInvalidMailbagId(true);
					     }*/
						errorHandlingPopUpForm.setMailBag(mailUploadVO.getMailTag());
						
						
					}
					
						else {
						errorHandlingPopUpForm.setOoe("");
						if(mailUploadVO.getMailTag().length()==0){
							errorHandlingPopUpForm.setMailBag("");	
						}
				errorHandlingPopUpForm.setDoe("");
				errorHandlingPopUpForm.setCategory("");
				errorHandlingPopUpForm.setSubclass("");

				errorHandlingPopUpForm.setYear("");
				errorHandlingPopUpForm.setDsn("");
				errorHandlingPopUpForm.setRsn("");
				errorHandlingPopUpForm.setHni("");
				errorHandlingPopUpForm.setRi("");
				errorHandlingPopUpForm.setWeight("");
						
					}
					//Added by A-5945 for ICRD-113473 starts
					if(mailUploadVO.getFromCarrierCode()!=null && mailUploadVO.getFromCarrierCode().trim().length()>0){
						errorHandlingPopUpForm.setTransferCarrier(mailUploadVO.getFromCarrierCode());
					}	
//Added by A-5945 for ICRD-113473 ends
					mailTrackingErrorHandlingSession.setScannedDetails(mailUploadVO);
				}
				

			}  

		}
		}

		log.exiting("ResendOrReprocessPopupCommand", "execute");    
		invocationContext.target = PROCESS_SUCCESS;
	
}

   private  Object[] getTxnParameters(String companyCode,String txnId) {
		
	   Object[] txndetails1 = null; 
		try {
						 			
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
			txndetails1 = mailTrackingDefaultsDelegate.getTxnParameters(
					companyCode, txnId);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage(); 
		}
		return txndetails1;
	}

private  Map<String, Collection<OneTimeVO>> findOneTimeDescription(
		String companyCode) {
	Map<String, Collection<OneTimeVO>> oneTimes1 = null;
	Collection<ErrorVO> errors = null;
	try{
		Collection<String> fieldValues = new ArrayList<String>();
		
		fieldValues.add(MAILCATEGORY);
		fieldValues.add(MAIL_HNI);
		fieldValues.add(MAIL_RI);
		fieldValues.add(MAIL_CMPCOD);
		
			oneTimes1 = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, fieldValues);
	}catch(BusinessDelegateException businessDelegateException){
		errors = handleDelegateException(businessDelegateException);
	}
	return oneTimes1;
}


private String findSystemParameterValue(String syspar)
		throws  BusinessDelegateException {
			String sysparValue = null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add(syspar);
			Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(systemParameters);
			log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
			if (systemParameterMap != null) {
				sysparValue = systemParameterMap.get(syspar);
			}
			return sysparValue;
		}


private boolean isValidMailtag(int mailtagLength) throws BusinessDelegateException
{
	boolean valid=false;
	String systemParameterValue=null;
	
	systemParameterValue = findSystemParameterValue(NO_OF_CHAR_ALLOWED_FOR_MAILTAG);//modified by a-7871 for ICRD-218529
	
	if(systemParameterValue!=null && !systemParameterValue.equals("NA"))
	{
	 String[] systemParameterVal = systemParameterValue.split(","); 
        for (String a : systemParameterVal) 
        {
        	if(Integer.valueOf(a)==mailtagLength)
        	{
        		valid=true;
        		break;
        	}
        }
	}
	return valid;
					}
				/**
				 * 
				 * @param unitType
				 * @param fromUnit
				 * @param toUnit
				 * @param fromValue
				 * @return
				 */
				private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
					UnitConversionNewVO unitConversionVO= null;
					try {
						unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
					} catch (UnitException e) {
						e.getMessage();
					}
					double convertedValue = unitConversionVO.getToValue();
					return convertedValue;
					} 
				/**
				 * 
				 * @param weight
				 * @return
				 */
				private String weightFormatter(Double weight) {
					String weightString=String.valueOf(weight.intValue());	// added by A-8353 for ICRD-274933
				    String weights[] = weightString.split("[.]");
				    StringBuilder flatWeight = new StringBuilder(weights[0]);
				    /*if(!"0".equals(weights[1])){    
				    	flatWeight.append(weights[1]);//commented by A-8353 for ICRD-274933
				    }*/
				    if (flatWeight.length() >= 4) {
				        return flatWeight.substring(0, 4);
				    }
					StringBuilder zeros = new StringBuilder();
					int zerosRequired = 4 - flatWeight.length();
					for(int i = 0; i < zerosRequired; i++) {
					    zeros.append("0");
					}
					return zeros.append(flatWeight).toString();
}
				private Collection getStationParameterCodes()
			    {
			        Collection stationParameterCodes = new ArrayList();
			        stationParameterCodes.add(STNPAR_DEFUNIT_WGT);
			        return stationParameterCodes;
}
}