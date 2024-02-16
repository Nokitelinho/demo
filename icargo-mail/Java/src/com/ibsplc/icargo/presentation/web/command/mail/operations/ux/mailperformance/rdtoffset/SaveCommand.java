package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.rdtoffset;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



public class SaveCommand extends BaseCommand {
	
	private static final String SUCCESS = "save_success";
	private static final String FAILURE = "save_failure";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	private static final String ARP_EMPTY = 
				"mailtracking.defaults.ux.mailperformance.msg.err.arpEmpty";
	
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
		
		ArrayList<MailRdtMasterVO> mailRdtMasterVOs = mailPerformanceSession.getMailRdtMasterVOs();
		log.log(Log.FINE, "\n\n CoterminusVOS from session =============> \n\n",mailRdtMasterVOs);
    	if (mailRdtMasterVOs == null) {
    		mailRdtMasterVOs = new ArrayList<MailRdtMasterVO>();
		}
    	
    	if(null!=mailPerformanceForm.getRdtPacode()&&("").equals(mailPerformanceForm.getRdtPacode())) {
	    	log.log(Log.FINE, "\n\n GPA_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	mailPerformanceForm.setStatusFlag("Save_fail");
	  /*  	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);*/
	    	errors.add(error);
	    }
    	
    	mailRdtMasterVOs = updateMailPerformanceVOs(mailRdtMasterVOs,
    			mailPerformanceForm,logonAttributes);
    	
	
		
		if(mailRdtMasterVOs != null && mailRdtMasterVOs.size()>0){
			for(MailRdtMasterVO mailRdtMasterVO:mailRdtMasterVOs){
				if(null==mailRdtMasterVO.getAirportCodes()||("").equals(mailRdtMasterVO.getAirportCodes())){
					ErrorVO error = new ErrorVO(ARP_EMPTY);
					/*error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);*/
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);
				}
			}
		}
		
		if(errors.size()==0){
			if(mailRdtMasterVOs != null && mailRdtMasterVOs.size()>0){
				for(MailRdtMasterVO mailRdtMasterVO:mailRdtMasterVOs){
					String[] airportCodes=null;
					if(mailRdtMasterVO.getAirportCodes()!=null){
					airportCodes=mailRdtMasterVO.getAirportCodes().split(",");
					try{
						for(String code:airportCodes){
			    		new AreaDelegate().validateAirportCode
			    					(logonAttributes.getCompanyCode(),
			    							code.toUpperCase().trim());
						}
			    	}catch(BusinessDelegateException businessDelegateException){
			    		errors = handleDelegateException(businessDelegateException);
			    		invocationContext.addAllError(errors);
			    		mailPerformanceForm.setScreenFlag("rdtRadioBtn");
			    		mailPerformanceForm.setStatusFlag("Save_fail");
						invocationContext.target = FAILURE;
				    	return;
			    	}
			    	}
				}
			}
			}
			if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				mailPerformanceSession.setMailRdtMasterVOs(mailRdtMasterVOs);
				mailPerformanceForm.setScreenFlag("rdtRadioBtn");
				invocationContext.target = FAILURE;
		    	return;
			}
			
			mailPerformanceSession.setMailRdtMasterVOs(mailRdtMasterVOs);
			
			
			try {
				new MailTrackingDefaultsDelegate().saveRdtMasterDetails(mailRdtMasterVOs);
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			
			if(errors != null && errors.size()>0) {
				log.log(Log.FINE, "\n\n <============= ERROR !!!  =============> \n\n",errors);
				invocationContext.addAllError(errors);	
				mailPerformanceForm.setScreenFlag("rdtRadioBtn");
				mailPerformanceForm.setStatusFlag("Save_fail");
				invocationContext.target = FAILURE;
		    	return;
			}
			
			if(mailRdtMasterVOs != null && mailRdtMasterVOs.size()>0){
				log.log(Log.FINE, "\n\n <============= SAVE SUCCESS !!!  =============> \n\n");
				ErrorVO error = new ErrorVO(SAVE_SUCCESS);
				errors.add(error);
				mailPerformanceForm.setStatusFlag("Save_success");
				mailPerformanceForm.setScreenFlag("rdtRadioBtn");
				invocationContext.addAllError(errors);
			}
			mailPerformanceForm.setRdtAirport("");
			mailPerformanceSession.setMailRdtMasterVOs(null);
			mailPerformanceSession.setAirport(null);
			mailPerformanceForm.setScreenFlag("rdtRadioBtn");
			mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = SUCCESS;	
		
	}
	
	  private ArrayList<MailRdtMasterVO> updateMailPerformanceVOs(ArrayList<MailRdtMasterVO> mailRdtMasterVOs , MailPerformanceForm mailPerformanceForm,
	    		LogonAttributes logonAttributes){
		  
		  log.entering("SaveCommand","updateMailSubClassVOs");
		  mailRdtMasterVOs=new ArrayList<MailRdtMasterVO>();
		  
		  for (int i = 0; i < mailPerformanceForm.getRdtOperationFlag().length; i++) {  
				if (!"NOOP".equals(mailPerformanceForm.getRdtOperationFlag()[i])) {
					
					if(mailPerformanceForm.getAirportCodes()[i]!=null && !mailPerformanceForm.getAirportCodes()[i].isEmpty()){     
						MailRdtMasterVO mailRdtMasterVO = new MailRdtMasterVO();
						mailRdtMasterVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailRdtMasterVO.setGpaCode(mailPerformanceForm.getRdtPacode());
					mailRdtMasterVO.setAirportCodes(mailPerformanceForm.getAirportCodes()[i]);
					mailRdtMasterVO.setOperationFlag(mailPerformanceForm.getRdtOperationFlag()[i]);
						mailRdtMasterVO.setMailType("I");
					if(mailPerformanceForm.getRdtOffset()[i]!=0){
						mailRdtMasterVO.setRdtOffset(mailPerformanceForm.getRdtOffset()[i]);
					}    
					    
						mailRdtMasterVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
	    			if(mailPerformanceForm.getSeqnum()[i]!=0)
	    				mailRdtMasterVO.setSeqnum(mailPerformanceForm.getSeqnum()[i]);	
		    			
		    			mailRdtMasterVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
	    			mailRdtMasterVOs.add(mailRdtMasterVO);
	    			
					}    
					
				}
				
			}
		  
		  
	    	
	    	log.exiting("SaveCommand","updateMailSubClassVOs");
 	    	
	    	return (ArrayList<MailRdtMasterVO>)mailRdtMasterVOs;
	  }

}
