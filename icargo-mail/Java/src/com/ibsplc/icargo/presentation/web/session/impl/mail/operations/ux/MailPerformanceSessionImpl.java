package com.ibsplc.icargo.presentation.web.session.impl.mail.operations.ux;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class MailPerformanceSessionImpl extends AbstractScreenSession implements
MailPerformanceSession {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	private static final String CO_TERMINUS_VOS = "coTerminusVOs";
    private static final String RDT_DETAILS_VOS = "mailRdtMasterVOs"; 
	private static final String AIRPORT = "airport";
	private static final String KEY_RESDIT = "resditevent";
	private static final String USPS_CALENDAR="uSPSPostalCalendarVOs";
	private static final String CALENDAR_TYPES="calendarTypes";
	private static final String KEY_SERVICELEVEL = "servicelevel";
	private static final String MAIL_HANDOVER_VOS = "mailHandoverVOs";
	private static final String KEY_TOTALRECORDS =  "totalRecords";
	private static final String SERVICE_LEVEL="serviceLevels";
	private static final String SERVICE_STANDARD_VOS = "mailServiceStandardVOs";
	private static final String SERVICE_STND_VOS = "mailServiceStndVOs";
	private static final String SERVICE_STANDARD_VOS_TODELETE = "mailServiceStandardVOsToDelete";
	private static final String MAIL_CLASS="mailClasses";
	
	private static final String GPACONTRACT_VOS = "gPAContractVOs";
	private static final String INCENTIVECONFIGURATION_VOS = "incentiveConfiguration_VOs";
	private static final String INCENTIVECONFIGURATIONDETAIL_VOS = "incentiveConfigurationDetail_VOs";
	private static final String KEY_RATEBREAKDOWN = "rateBreakDown";
	private static final String KEY_FORMULABASIS = "formulaBasis";
	private static final String KEY_FORMULAREFERENCE = "formulaReference";
	private static final String KEY_INCENTIVE_PARAMETER = "incentiveParameter";
	private static final String KEY_MAILSERVICELEVELS=  "mailServiceLevels";
	private static final String KEY_MAILCATEGORY= "mailCategory";
	private static final String KEY_MAILSUBCLASS= "mailSubClass";
	private static final String KEY_PACODE= "paCode";
	private static final String KEY_PARAMETER= "parameter";
	private static final String KEY_FORMULA= "formula";
	private static final String KEY_BASIS= "basis";
	private static final String KEY_INDEX ="selectedCalendar";
	private static final String KEY_FILTERVO="postalFilterVO";
	private static final String KEY_MAILAMOT="mailAmot";
			
	/**
	 * @return Screen Id
	 */
	public String getScreenID() {
		
		return SCREEN_ID;
	}

	/**
	 * @return Module Name
	 */
	public String getModuleName() {
		
		return MODULE_NAME;
	}
	
	/**
     * @return ArrayList<MailServiceStandardVO>
     */
	public Page<MailServiceStandardVO> getMailServiceStandardVOs() {
		return (Page<MailServiceStandardVO>)getAttribute(SERVICE_STANDARD_VOS);
	}
	
	/**
     * @param mailServiceStandardVOs
     */
	public void setMailServiceStandardVOs(Page<MailServiceStandardVO> mailServiceStandardVOs) {
		setAttribute(SERVICE_STANDARD_VOS,mailServiceStandardVOs);
	}
	
	/**
     * @param mailServiceStandardVOs
     */
	public void setMailServiceStndVOs(ArrayList<MailServiceStandardVO> mailServiceStndVOs) {
		setAttribute(SERVICE_STND_VOS,mailServiceStndVOs);
	}
	
	public ArrayList<MailServiceStandardVO> getMailServiceStndVOs() {
		return (ArrayList<MailServiceStandardVO>)getAttribute(SERVICE_STND_VOS);
	}
	
	public void removeMailServiceStandardVOs(){
		removeAttribute(SERVICE_STANDARD_VOS);
	}
	
	/**
     * @return ArrayList<MailServiceStandardVO>
     */
	public ArrayList<MailServiceStandardVO> getMailServiceStandardVOsToDelete() {
		return (ArrayList<MailServiceStandardVO>)getAttribute(SERVICE_STANDARD_VOS_TODELETE);
	}
	
	/**
     * @param mailServiceStandardVOsToDelete
     */
	public void setMailServiceStandardVOsToDelete(ArrayList<MailServiceStandardVO> mailServiceStandardVOsToDelete) {
		setAttribute(SERVICE_STANDARD_VOS_TODELETE,mailServiceStandardVOsToDelete);
	}
	
	/**
     * @return ArrayList<CoTerminusVO>
     */
	public ArrayList<CoTerminusVO> getCoTerminusVOs() {
		return (ArrayList<CoTerminusVO>)getAttribute(CO_TERMINUS_VOS);
	}
	public ArrayList<MailRdtMasterVO> getMailRdtMasterVOs() {
		return (ArrayList<MailRdtMasterVO>)getAttribute(RDT_DETAILS_VOS);
	}
		public void setMailRdtMasterVOs(ArrayList<MailRdtMasterVO> mailRdtMasterVOs) {
		setAttribute(RDT_DETAILS_VOS,mailRdtMasterVOs);
	}
	/**
     * @param coTerminusVOs
     */
	public void setCoTerminusVOs(ArrayList<CoTerminusVO> coTerminusVOs) {
		setAttribute(CO_TERMINUS_VOS,coTerminusVOs);
	}

	/**
	 * @return String Airport
	 */
	public String getAirport() {
		return (String)getAttribute(AIRPORT);
	}
	
	/**
     * @param Airport
     */
	public void setAirport(String Airport) {
		setAttribute(AIRPORT,Airport);
		
	}	

	public void setServiceLevels(ArrayList<OneTimeVO> serviceLevels) {
		setAttribute(SERVICE_LEVEL, serviceLevels);
	}
	public ArrayList<OneTimeVO> getServiceLevels() {
		return (ArrayList<OneTimeVO>)getAttribute(SERVICE_LEVEL);
	}	

	public void setResditModes(ArrayList<OneTimeVO> resditModes) {
		setAttribute(KEY_RESDIT, resditModes);
	}
	
	public void removeResditMode() {
		removeAttribute(KEY_RESDIT);
	}
	public ArrayList<OneTimeVO> getResditModes() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_RESDIT);
	}
	public ArrayList<USPSPostalCalendarVO> getUSPSPostalCalendarVOs() {
		return (ArrayList<USPSPostalCalendarVO>)getAttribute(USPS_CALENDAR);
	}
	public void setUSPSPostalCalendarVOs(ArrayList<USPSPostalCalendarVO> uSPSPostalCalendarVOs) {
		setAttribute(USPS_CALENDAR,uSPSPostalCalendarVOs);
	}
	 public void setCalendarTypes(ArrayList<OneTimeVO> calendarTypes) {
		setAttribute(CALENDAR_TYPES, calendarTypes);
	}
	 public ArrayList<OneTimeVO> getCalendarTypes() {
			return (ArrayList<OneTimeVO>)getAttribute(CALENDAR_TYPES);
		}
	 public void removeCalendarTypes() {
			removeAttribute(CALENDAR_TYPES);
	}   
    /**
	 * @return Page<MailHandoverVO>
	 */
	public Page<MailHandoverVO> getMailHandoverVOs(){
		return (Page<MailHandoverVO>)getAttribute(MAIL_HANDOVER_VOS);
	}
	/**
     * @param mailHandoverVOs
     */
	public void setMailHandoverVOs(Page<MailHandoverVO> mailHandoverVOs){
		setAttribute(MAIL_HANDOVER_VOS,mailHandoverVOs);
	}
	
	public void removeMailHandoverVOs(){
		removeAttribute(MAIL_HANDOVER_VOS);
	}
	/**
     * @param serviceLevels
     */
	public void setServiceLevel(ArrayList<OneTimeVO> serviceLevels) {
		setAttribute(KEY_SERVICELEVEL, serviceLevels);
	}
	
	public void removeServiceLevel() {
		removeAttribute(KEY_SERVICELEVEL);
	}
	/**
	 * @return ArrayList<OneTimeVO>
	 */
	public ArrayList<OneTimeVO> getServiceLevel() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_SERVICELEVEL);
	}
	/**
	 * @return Integer TotalRecords
	 */
	public int getTotalRecords() {
		return (Integer)getAttribute(KEY_TOTALRECORDS);
	}

	/**
     * @param totalRecords
     */
	public void setTotalRecords(int totalRecords) {
		setAttribute(KEY_TOTALRECORDS, totalRecords);
		
	}

	/**
	 * @return the gpacontractVos
	 */
	 public ArrayList<GPAContractVO> getGPAContractVOs(){
		return (ArrayList<GPAContractVO>)getAttribute(GPACONTRACT_VOS); 
	}
	 
	/**
     * @param gpaContractVOs
     */
	 public void setGPAContractVOs(ArrayList<GPAContractVO> gpaContractVOs){
		 setAttribute(GPACONTRACT_VOS,gpaContractVOs);
	 }
	 public void removeGPAContractVOs(){
		 removeAttribute(GPACONTRACT_VOS);
	 }
	 /**
	 * @return the incentiveconfigurationVOs
	 */
	public ArrayList<IncentiveConfigurationVO> getIncentiveConfigurationVOs() {
		return (ArrayList<IncentiveConfigurationVO>)getAttribute(INCENTIVECONFIGURATION_VOS); 
	}
	/**
     * @param incentiveConfigurationVOs
     */
	 public void setIncentiveConfigurationVOs(ArrayList<IncentiveConfigurationVO> incentiveConfigurationVOs){
		 setAttribute(INCENTIVECONFIGURATION_VOS,incentiveConfigurationVOs);
	 }
	 /**
		 * @return the incentiveconfigurationVOs
		 */
	 public ArrayList<IncentiveConfigurationDetailVO> getIncentiveConfigurationDetailVOs() {
	 
		 return (ArrayList<IncentiveConfigurationDetailVO>)getAttribute(INCENTIVECONFIGURATIONDETAIL_VOS); 
		 
	 }
	 /**
     * @param incentiveConfigurationVOs
     */
     public void setIncentiveConfigurationDetailVOs(
    		ArrayList<IncentiveConfigurationDetailVO> incentiveConfigurationDetailVOs){
    	 setAttribute(INCENTIVECONFIGURATIONDETAIL_VOS,incentiveConfigurationDetailVOs);
     }
    
     /**
     * @param RateBreakDownValues
     */
	 public void setRateBreakDownValues(ArrayList<OneTimeVO> RateBreakDownValues) {
		setAttribute(KEY_RATEBREAKDOWN, RateBreakDownValues);
	 }
	
	 /**
	 * @return ArrayList<OneTimeVO>
	 */
	 public ArrayList<OneTimeVO> getRateBreakDownValues() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_RATEBREAKDOWN);
	 }
	 public void removeRateBreakDownValues(){
		 removeAttribute(KEY_RATEBREAKDOWN);
	 }
	 
	 /**
     * @param formulaBasisValues
     */
	 public void setFormulaBasis(ArrayList<OneTimeVO> formulaBasisValues) {
		setAttribute(KEY_FORMULABASIS, formulaBasisValues);
	 }
	
	 /**
	 * @return ArrayList<OneTimeVO>
	 */
	 public ArrayList<OneTimeVO> getFormulaBasis() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_FORMULABASIS);
	 }
	 public void removeFormulaBasis(){
		 removeAttribute(KEY_FORMULABASIS);
	 }
	    
	 /**
     * @param formulaReference
     */
	 public void setFormulaReference(ArrayList<OneTimeVO> formulaReferenceValues) {
		setAttribute(KEY_FORMULAREFERENCE, formulaReferenceValues);
	 }
	
	 /**
	 * @return ArrayList<OneTimeVO>
	 */
	 public ArrayList<OneTimeVO> getFormulaReference() {
		return (ArrayList<OneTimeVO>)getAttribute(KEY_FORMULAREFERENCE);
	 }
	 public void removeFormulaReference(){
		 removeAttribute(KEY_FORMULAREFERENCE);
	 }
	 /**
	 * @return ArrayList<OneTimeVO>
	 */ 
	 public ArrayList<OneTimeVO> getIncentiveParameters(){
		 return (ArrayList<OneTimeVO>)getAttribute(KEY_INCENTIVE_PARAMETER);
	 }
	 /**
     * @param incentiveParameters
     */
	 public void setIncentiveParameters(ArrayList<OneTimeVO> incentiveParameters){
		 setAttribute(KEY_INCENTIVE_PARAMETER, incentiveParameters);
	 }
	 
	 public void removeIncentiveParameters(){
		 removeAttribute(KEY_INCENTIVE_PARAMETER);
	 }
	 /**
	 * @return ArrayList<OneTimeVO>
	 */ 
     public ArrayList<OneTimeVO> getMailServiceLevels(){
    	 return (ArrayList<OneTimeVO>)getAttribute(KEY_MAILSERVICELEVELS);
     }
     /**
      * @param mailServiceLevels
      */
     public void setMailServiceLevels(ArrayList<OneTimeVO> mailServiceLevels){
    	 setAttribute(KEY_MAILSERVICELEVELS, mailServiceLevels);
     }
     
     public void removeMailServiceLevels(){
    	 removeAttribute(KEY_MAILSERVICELEVELS);
	 }
     /**
 	 * @return ArrayList<OneTimeVO>
 	 */ 
     public ArrayList<OneTimeVO> getMailCategory(){
    	 return (ArrayList<OneTimeVO>)getAttribute(KEY_MAILCATEGORY);
     }
     /**
      * @param mailCategory
      */
     public void setMailCategory(ArrayList<OneTimeVO> mailCategory){
    	 setAttribute(KEY_MAILCATEGORY, mailCategory);
     }
     
     public void removeMailCategory(){
    	 removeAttribute(KEY_MAILCATEGORY);
     }
	 /**
 	 * @return ArrayList<OneTimeVO>
 	 */ 
     public List<OneTimeVO> getMailAmot(){
    	 return (ArrayList<OneTimeVO>)getAttribute(KEY_MAILAMOT);
     }
     /**
      * @param mailAmot
      */
     public void setMailAmot(List<OneTimeVO> mailAmot){
    	 setAttribute(KEY_MAILAMOT,(ArrayList<OneTimeVO>) mailAmot);
     }
     public void removeMailAmot(){
    	 removeAttribute(KEY_MAILAMOT);
     }
   
     /**
   	 * @return ArrayList<OneTimeVO>
   	 */ 
     public String getPaCode(){
    	 return (String)getAttribute(KEY_PACODE);
     }
     public void setPaCode(String paCode){
    	 setAttribute(KEY_PACODE, paCode);
     }
     
     public ArrayList<IncentiveConfigurationDetailVO> getParameterVO(){
    	 return (ArrayList<IncentiveConfigurationDetailVO>)getAttribute(KEY_PARAMETER);
     }
     public void setParameterVO(ArrayList<IncentiveConfigurationDetailVO> parameter){
    	 setAttribute(KEY_PARAMETER, parameter);
     }
     public ArrayList<String> getFormula(){
    	 return (ArrayList<String>)getAttribute(KEY_FORMULA);
     }
     public void setFormula(ArrayList<String> formula){
    	 setAttribute(KEY_FORMULA, formula);
     }
     public String getBasis(){
    	 return (String)getAttribute(KEY_BASIS);
     }
     public void setBasis(String basis){
    	 setAttribute(KEY_BASIS, basis);
     }
     
     public String getCalendarIndex(){
    	 return (String)getAttribute(KEY_INDEX);
     }
     
     public void setCalendarIndex(String index){
    	 setAttribute(KEY_INDEX, index);
     }
     
     public USPSPostalCalendarFilterVO getFilterVO(){
    	 return (USPSPostalCalendarFilterVO)getAttribute(KEY_FILTERVO);
     }
     
     public void setFilterVO(USPSPostalCalendarFilterVO vo){
    	 setAttribute(KEY_FILTERVO, vo);
     }
     public void setMailClasses(ArrayList<OneTimeVO> mailClasses) {
  		setAttribute(MAIL_CLASS, mailClasses);
  	}
  	public ArrayList<OneTimeVO> getMailClasses() {
  		return (ArrayList<OneTimeVO>)getAttribute(MAIL_CLASS);
     }
     
}
