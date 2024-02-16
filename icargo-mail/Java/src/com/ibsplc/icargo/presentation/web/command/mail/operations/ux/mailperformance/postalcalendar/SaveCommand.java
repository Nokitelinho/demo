package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplaySubtype;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.postalcalendar.SaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	03-Jul-2018	:	Draft
 */
public class SaveCommand extends BaseCommand {
	
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private static final String BLANK = "";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String CALTYPE_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.caltypeempty";
	private static final String PERIOD_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.periodempty";
	private static final String FROM_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.fromempty";
	private static final String TO_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.toempty";
	private static final String DATE_ERROR =
			"mailtracking.defaults.ux.mailperformance.msg.err.dateerror";
	private static final String PERIOD_INCORRECT=
			"mailtracking.defaults.ux.mailperformance.msg.err.periodincorrect";
	private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command
	 *							#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 	: A-8164 on 03-Jul-2018
	 * 	Used for 	: ICRD-236925
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the save command----------> \n\n");
		
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		mailPerformanceForm.setStatusFlag("");
		
		ArrayList<USPSPostalCalendarVO> uspsPostalCalendarVOs = 
				mailPerformanceSession.getUSPSPostalCalendarVOs();
		log.log(Log.FINE, "\n\n USPSPostalCalendarVOs from session =============> \n\n",uspsPostalCalendarVOs);
    	if (uspsPostalCalendarVOs == null) {
    		uspsPostalCalendarVOs = new ArrayList<USPSPostalCalendarVO>();
		}
    	 
    	if(null!=mailPerformanceForm.getCalPacode()&&BLANK.equals(mailPerformanceForm.getCalPacode())) {  
	    	log.log(Log.FINE, "\n\n GPA_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	mailPerformanceForm.setStatusFlag("Save_fail");
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);  
	    }
    	
    	 if(null!=mailPerformanceForm.getFilterCalender()&&BLANK.equals(mailPerformanceForm.getFilterCalender())) {
 	    	log.log(Log.FINE, "\n\n CALENDAR TYPE EMPTY =============> \n\n");
 	    	ErrorVO error = new ErrorVO(CALTYPE_EMPTY);
 	    	mailPerformanceForm.setStatusFlag("Save_fail");
 	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
 			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
 	    	errors.add(error);
    	 }
    	 if(errors != null && errors.size()>0){//Added by A-8164 for ICRD-273748
				invocationContext.addAllError(errors);
				mailPerformanceSession.setUSPSPostalCalendarVOs(uspsPostalCalendarVOs);
				mailPerformanceForm.setScreenFlag("postCalender");
				invocationContext.target = FAILURE;
		    	return;
			}
    	 uspsPostalCalendarVOs = updateMailPerformanceVOs(uspsPostalCalendarVOs,
     			mailPerformanceForm,logonAttributes);
    	 
    	 if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size()>0){
 			for(USPSPostalCalendarVO uSPSPostalCalendarVO:uspsPostalCalendarVOs){
 				if(uSPSPostalCalendarVO.getPeriods()==null||
 						BLANK.equals(uSPSPostalCalendarVO.getPeriods())){
 					ErrorVO error = new ErrorVO(PERIOD_EMPTY);
 					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
 					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
 					mailPerformanceForm.setStatusFlag("Save_fail");
 					errors.add(error);
 				}
 			}
 		}
    	 LocalDate fromDate=new LocalDate
 				(LocalDate.NO_STATION,Location.NONE,false);
    	 LocalDate toDate=new LocalDate
  				(LocalDate.NO_STATION,Location.NONE,false);
    	 
    	 if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size()>0){
  			for(USPSPostalCalendarVO uSPSPostalCalendarVO:uspsPostalCalendarVOs){
  				if(uSPSPostalCalendarVO.getPeriods()==null||
  						BLANK.equals(uSPSPostalCalendarVO.getFromDates())){
  					ErrorVO error = new ErrorVO(FROM_EMPTY);
  					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
  					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					errors.add(error);
  				}
  				if(uSPSPostalCalendarVO.getToDates()==null||
  						BLANK.equals(uSPSPostalCalendarVO.getToDates())){
  					ErrorVO error = new ErrorVO(TO_EMPTY);
  					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
  					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					errors.add(error);
  				}
  				if(uSPSPostalCalendarVO.getFromDates()!=null){					 //Added by A-8399 as part of ICRD-302610
  				fromDate.setDate(uSPSPostalCalendarVO.getFromDates());
  				}
  				if(uSPSPostalCalendarVO.getToDates()!=null){					   //Added by A-8399 as part of ICRD-302610
  				toDate.setDate(uSPSPostalCalendarVO.getToDates());
  				}
  				if(toDate.isLesserThan(fromDate)){
  					ErrorVO error = new ErrorVO(DATE_ERROR);
  					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
  					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
  					mailPerformanceForm.setStatusFlag("Save_fail");
  					errors.add(error);
  				}
  			}
  		}
    	 
    	 if(errors.size()==0){
 			if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size()>0){
 				for(USPSPostalCalendarVO uSPSPostalCalendarVO:uspsPostalCalendarVOs){
 					if(uSPSPostalCalendarVO.getPeriods()!=null){				 //Added by A-8399 as part of ICRD-302610
 					if(9<uSPSPostalCalendarVO.getPeriods().length()){ 
 						ErrorVO error = new ErrorVO(PERIOD_INCORRECT);
 	 					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
 	 					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
 	 					mailPerformanceForm.setStatusFlag("Save_fail");
 	 					errors.add(error);
 					}
 				}
 			}
    	 }
    	 }
    	 if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				mailPerformanceSession.setUSPSPostalCalendarVOs(uspsPostalCalendarVOs);
				mailPerformanceForm.setScreenFlag("postCalender");
				invocationContext.target = FAILURE;
		    	return;
			}
			
    	 mailPerformanceSession.setUSPSPostalCalendarVOs(uspsPostalCalendarVOs);
    	 
    	 try {
				new MailTrackingDefaultsDelegate().savePostalCalendar(uspsPostalCalendarVOs);
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
    	 if(errors != null && errors.size()>0) {
				log.log(Log.FINE, "\n\n <============= ERROR !!!  =============> \n\n",errors);
				invocationContext.addAllError(errors);	
				mailPerformanceForm.setScreenFlag("postCalender");
				mailPerformanceForm.setStatusFlag("Save_fail");
				invocationContext.target = FAILURE;
		    	return;
			}
			
			if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size()>0){
				log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
				ErrorVO error = new ErrorVO(SAVE_SUCCESS);
				errors.add(error);
				mailPerformanceForm.setStatusFlag("Save_success");
				mailPerformanceForm.setScreenFlag("postCalender");
				invocationContext.addAllError(errors);
			}
			mailPerformanceForm.setCalPacode("");
			mailPerformanceSession.setUSPSPostalCalendarVOs(null);
			mailPerformanceForm.setCalValidFrom("");
			mailPerformanceForm.setCalValidTo("");
			mailPerformanceForm.setScreenFlag("postCalender");
			mailPerformanceForm.setPostalCalendarAction("");
			mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SUCCESS;	
		
			
	}
	/**
	 * 
	 * 	Method		:	SaveCommand.updateMailPerformanceVOs
	 *	Added by 	:	A-8164 on 03-Jul-2018
	 * 	Used for 	:	ICRD-236925
	 *	Parameters	:	@param uspsPostalCalendarVOs
	 *	Parameters	:	@param mailPerformanceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	ArrayList<USPSPostalCalendarVO>
	 */
	private ArrayList<USPSPostalCalendarVO> updateMailPerformanceVOs
			(ArrayList<USPSPostalCalendarVO> uspsPostalCalendarVOs , MailPerformanceForm mailPerformanceForm,
    		LogonAttributes logonAttributes){
		
		String[] oprflags = mailPerformanceForm.getCalOperationFlags();  
		int size = 0;
    	if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size() > 0){
			   size = uspsPostalCalendarVOs.size();
	   	}
    	Collection<USPSPostalCalendarVO> newUSPSPostalCalendarVOs = new ArrayList<USPSPostalCalendarVO>();
    	for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					USPSPostalCalendarVO uSPSPostalCalendarVO =new USPSPostalCalendarVO();
					uSPSPostalCalendarVO.setCompanyCode(logonAttributes.getCompanyCode());
					uSPSPostalCalendarVO.setGpacod(mailPerformanceForm.getCalPacode());
					uSPSPostalCalendarVO.setFilterCalender(mailPerformanceForm.getFilterCalender());
					uSPSPostalCalendarVO.setOperationFlag(mailPerformanceForm.getCalOperationFlags()[index]); 
					int temp=index;										//Added by A-8399 as part of ICRD-302610
					index=oprflags.length-(index+2); 					  //Added by A-8399 as part of ICRD-302610
					if(((null!=mailPerformanceForm.getPeriods()[index]))&&
							(0<mailPerformanceForm.getPeriods()[index].length())) {
								uSPSPostalCalendarVO.setPeriods(
										mailPerformanceForm.getPeriods()[index]);
					}
					if(((null!=mailPerformanceForm.getFromDates()[index]))&& 
							(0<mailPerformanceForm.getFromDates()[index].length())){
								uSPSPostalCalendarVO.setFromDates(
										mailPerformanceForm.getFromDates()[index]);
					}
					if(((null!=mailPerformanceForm.getToDates()[index]))&&
							(0<mailPerformanceForm.getToDates()[index].length())) {
					uSPSPostalCalendarVO.setToDates(mailPerformanceForm.getToDates()[index]);
					}
					
					
					if("C".equals(mailPerformanceForm.getFilterCalender())){
						if(((null!=mailPerformanceForm.getCgrSubmissions()[index]))&&
								(0<mailPerformanceForm.getCgrSubmissions()[index].length())){
									uSPSPostalCalendarVO.setCgrSubmissions(
											mailPerformanceForm.getCgrSubmissions()[index]);
						}
						if(((null!=mailPerformanceForm.getCgrDeleteCutOffs()[index]))&&
								(0<mailPerformanceForm.getCgrDeleteCutOffs()[index].length())){
									uSPSPostalCalendarVO.setCgrDeleteCutOffs(
											mailPerformanceForm.getCgrDeleteCutOffs()[index]);
						}
						if(((null!=mailPerformanceForm.getFromDates()[index]))&&
								(0<mailPerformanceForm.getFromDates()[index].length())){
									uSPSPostalCalendarVO.setFromDates(
											mailPerformanceForm.getFromDates()[index]);
						}
						if(((null!=mailPerformanceForm.getCutWeek1s()[index]))&&
								(0<mailPerformanceForm.getCutWeek1s()[index].length())){
									uSPSPostalCalendarVO.setCutWeek1s(
											mailPerformanceForm.getCutWeek1s()[index]);
						}
						if(((null!=mailPerformanceForm.getCutWeek2s()[index]))&&
								(0<mailPerformanceForm.getCutWeek2s()[index].length())){
									uSPSPostalCalendarVO.setCutWeek2s(
											mailPerformanceForm.getCutWeek2s()[index]);
						}
						if(((null!=mailPerformanceForm.getCutWeek3s()[index]))&&
								(0<mailPerformanceForm.getCutWeek3s()[index].length())){
									uSPSPostalCalendarVO.setCutWeek3s(
											mailPerformanceForm.getCutWeek3s()[index]);
						}
						if(((null!=mailPerformanceForm.getCutWeek4s()[index]))&&
								(0<mailPerformanceForm.getCutWeek4s()[index].length())){
									uSPSPostalCalendarVO.setCutWeek4s(
											mailPerformanceForm.getCutWeek4s()[index]);
						}
						if(((null!=mailPerformanceForm.getCutWeek5s()[index]))&&
								(0<mailPerformanceForm.getCutWeek5s()[index].length())){
									uSPSPostalCalendarVO.setCutWeek5s(
											mailPerformanceForm.getCutWeek5s()[index]);
						}
					}
					
					if("I".equals(mailPerformanceForm.getFilterCalender())){	  		  		
						if(((null!=mailPerformanceForm.getPaymEffectiveDates()[index]))&&
								(0<mailPerformanceForm.getPaymEffectiveDates()[index].length())){
									uSPSPostalCalendarVO.setPaymEffectiveDates(
										mailPerformanceForm.getPaymEffectiveDates()[index]);
						}
						if(((null!=mailPerformanceForm.getIncCalcDates()[index]))&&
								(0<mailPerformanceForm.getIncCalcDates()[index].length())){
									uSPSPostalCalendarVO.setIncCalcDates(
											mailPerformanceForm.getIncCalcDates()[index]);
						}
						if(((null!=mailPerformanceForm.getIncEffectiveDates()[index]))&&
								(0<mailPerformanceForm.getIncEffectiveDates()[index].length())){
									uSPSPostalCalendarVO.setIncEffectiveDates(
											mailPerformanceForm.getIncEffectiveDates()[index]);
						}
						if(((null!=mailPerformanceForm.getIncRecvDates()[index]))&&
								(0<mailPerformanceForm.getIncRecvDates()[index].length())){
									uSPSPostalCalendarVO.setIncRecvDates(
											mailPerformanceForm.getIncRecvDates()[index]);
						}
						//Added by A-8527 for ICRD-262451 Starts
						if(((null!=mailPerformanceForm.getClmGenDate()[index]))&&
								(0<mailPerformanceForm.getClmGenDate()[index].length())){
									uSPSPostalCalendarVO.setClmGenDate(
											mailPerformanceForm.getClmGenDate()[index]);
						}
						//Added by A-8527 for ICRD-262451 Ends
					}  
					
					if(mailPerformanceForm.getCalSeqnum()[index]!=0){
						uSPSPostalCalendarVO.setCalSeqnum(0);				 //Modified by A-8399 as part of ICRD-302610
					}
					uSPSPostalCalendarVO.setLstUpdUsr(logonAttributes.getUserId().toUpperCase());
					uSPSPostalCalendarVO.setLstUpdTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
					newUSPSPostalCalendarVOs.add(uSPSPostalCalendarVO);
					index=temp;									   //Added by A-8399 as part of ICRD-302610
				}
			}else{
				int count = 0;
				if(uspsPostalCalendarVOs != null && uspsPostalCalendarVOs.size() > 0){
				   for(USPSPostalCalendarVO uSPSPostalCalendarVO:uspsPostalCalendarVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   uSPSPostalCalendarVO.setCompanyCode(logonAttributes.getCompanyCode());
								uSPSPostalCalendarVO.setGpacod(mailPerformanceForm.getCalPacode());
								uSPSPostalCalendarVO.setFilterCalender(mailPerformanceForm.getFilterCalender());
								if("N".equals(oprflags[index])){
									uSPSPostalCalendarVO.setOperationFlag(null);
								   }else{
									   uSPSPostalCalendarVO.setOperationFlag(oprflags[index]);
								   }   

									//Commented by A-8399 as part of ICRD-302610
								/*if(((null!=mailPerformanceForm.getPeriods()[index]))&&
										(0<mailPerformanceForm.getPeriods()[index].length())) {
											uSPSPostalCalendarVO.setPeriods(
													mailPerformanceForm.getPeriods()[index]);
								}
								if(((null!=mailPerformanceForm.getFromDates()[index]))&& 
										(0<mailPerformanceForm.getFromDates()[index].length())){
											uSPSPostalCalendarVO.setFromDates(
													mailPerformanceForm.getFromDates()[index]);
								}
								if(((null!=mailPerformanceForm.getToDates()[index]))&&
										(0<mailPerformanceForm.getToDates()[index].length())) {
											uSPSPostalCalendarVO.setToDates(
													mailPerformanceForm.getToDates()[index]);
								}
								
								if("C".equals(mailPerformanceForm.getFilterCalender())){
									if(((null!=mailPerformanceForm.getCgrSubmissions()[index]))&&
											(0<mailPerformanceForm.getCgrSubmissions()[index].length())){
												uSPSPostalCalendarVO.setCgrSubmissions(
														mailPerformanceForm.getCgrSubmissions()[index]);
									}
									if(((null!=mailPerformanceForm.getCgrDeleteCutOffs()[index]))&&
											(0<mailPerformanceForm.getCgrDeleteCutOffs()[index].length())){
												uSPSPostalCalendarVO.setCgrDeleteCutOffs(
														mailPerformanceForm.getCgrDeleteCutOffs()[index]);
									}
									if(((null!=mailPerformanceForm.getFromDates()[index]))&&
											(0<mailPerformanceForm.getFromDates()[index].length())){
												uSPSPostalCalendarVO.setFromDates(
														mailPerformanceForm.getFromDates()[index]);
									}
									if(((null!=mailPerformanceForm.getCutWeek1s()[index]))&&
											(0<mailPerformanceForm.getCutWeek1s()[index].length())){
												uSPSPostalCalendarVO.setCutWeek1s(
														mailPerformanceForm.getCutWeek1s()[index]);
									}
									if(((null!=mailPerformanceForm.getCutWeek2s()[index]))&&
											(0<mailPerformanceForm.getCutWeek2s()[index].length())){
												uSPSPostalCalendarVO.setCutWeek2s(
														mailPerformanceForm.getCutWeek2s()[index]);
									}
									if(((null!=mailPerformanceForm.getCutWeek3s()[index]))&&
											(0<mailPerformanceForm.getCutWeek3s()[index].length())){
												uSPSPostalCalendarVO.setCutWeek3s(
														mailPerformanceForm.getCutWeek3s()[index]);
									}
									if(((null!=mailPerformanceForm.getCutWeek4s()[index]))&&
											(0<mailPerformanceForm.getCutWeek4s()[index].length())){
												uSPSPostalCalendarVO.setCutWeek4s(
														mailPerformanceForm.getCutWeek4s()[index]);
									}
									if(((null!=mailPerformanceForm.getCutWeek5s()[index]))&&
											(0<mailPerformanceForm.getCutWeek5s()[index].length())){
												uSPSPostalCalendarVO.setCutWeek5s(
														mailPerformanceForm.getCutWeek5s()[index]);
									}
								}
								if("I".equals(mailPerformanceForm.getFilterCalender())){              
									if(((null!=mailPerformanceForm.getPaymEffectiveDates()[index]))&&
											(0<mailPerformanceForm.getPaymEffectiveDates()[index].length())){
												uSPSPostalCalendarVO.setPaymEffectiveDates(
													mailPerformanceForm.getPaymEffectiveDates()[index]);
									}
									if(((null!=mailPerformanceForm.getIncCalcDates()[index]))&&
											(0<mailPerformanceForm.getIncCalcDates()[index].length())){
												uSPSPostalCalendarVO.setIncCalcDates(
														mailPerformanceForm.getIncCalcDates()[index]);
									}
									if(((null!=mailPerformanceForm.getIncEffectiveDates()[index]))&&
											(0<mailPerformanceForm.getIncEffectiveDates()[index].length())){
												uSPSPostalCalendarVO.setIncEffectiveDates(
														mailPerformanceForm.getIncEffectiveDates()[index]);
									}
									if(((null!=mailPerformanceForm.getIncRecvDates()[index]))&&
											(0<mailPerformanceForm.getIncRecvDates()[index].length())){
												uSPSPostalCalendarVO.setIncRecvDates(
														mailPerformanceForm.getIncRecvDates()[index]); 
									}
								} 
								
								if(mailPerformanceForm.getCalSeqnum()[index]!=0){
									uSPSPostalCalendarVO.setCalSeqnum(mailPerformanceForm.getCalSeqnum()[index]);	
								}
								uSPSPostalCalendarVO.setLstUpdUsr(logonAttributes.getUserId().toUpperCase());
								uSPSPostalCalendarVO.setLstUpdTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));*/
								newUSPSPostalCalendarVOs.add(uSPSPostalCalendarVO);
						   }
					   }
					   count++;
				   }
				}
			}
    	}
    	return (ArrayList<USPSPostalCalendarVO>)newUSPSPostalCalendarVOs;
	}
    	
}
