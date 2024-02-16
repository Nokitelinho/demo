/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.servicestandard;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
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
import com.ibsplc.xibase.server.framework.vo.ErrorDisplaySubtype;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 *
 */
public class SaveCommand extends BaseCommand {

	private static final String SUCCESS = "servicestandard_save_success";
	private static final String FAILURE = "servicestandard_save_failure";
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String SERVICELEVEL_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.servicelevelempty";
	private static final String SERVICESTANDARD_EMPTY = "mailtracking.defaults.ux.mailperformance.msg.err.servicestandardempty";
	private static final String SAVE_SUCCESS = 
			"mailtracking.defaults.ux.mailperformance.msg.info.savesuccess";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "\n\n in the service standard save command-----> \n\n");
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<MailServiceStandardVO> mailServiceStandardVOs = mailPerformanceSession.getMailServiceStndVOs();
		ArrayList<MailServiceStandardVO> mailServiceStandardVOstodelete = mailPerformanceSession.getMailServiceStandardVOsToDelete();
		log.log(Log.FINE, "\n\n ServiceStandardVOs from session ===========> \n\n",mailServiceStandardVOs);
		if(mailServiceStandardVOs==null){
			mailServiceStandardVOs=new ArrayList<MailServiceStandardVO>();
		}
		
		if(null!=mailPerformanceForm.getServiceStandardsPacode()&&("").equals(mailPerformanceForm.getServiceStandardsPacode())) {
	    	log.log(Log.FINE, "\n\n GPA_EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	mailPerformanceForm.setStatusFlag("Save_fail");
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);
	    }
		
		mailServiceStandardVOs = updateMailPerformanceVOs(mailServiceStandardVOs,
    			mailPerformanceForm,logonAttributes);
		
		if(mailServiceStandardVOs!=null && mailServiceStandardVOs.size()>0){
			for(MailServiceStandardVO mailServiceStandardVO : mailServiceStandardVOs){
				if(mailServiceStandardVO.getServicelevel()==null || ("").equals(mailServiceStandardVO.getServicelevel())){
					ErrorVO error = new ErrorVO(SERVICELEVEL_EMPTY);
					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);
					break;
				}
				else if(mailServiceStandardVO.getServicestandard()==null || ("").equals(mailServiceStandardVO.getServicestandard())){
					ErrorVO error = new ErrorVO(SERVICESTANDARD_EMPTY);
					error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
					error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
					mailPerformanceForm.setStatusFlag("Save_fail");
					errors.add(error);
					break;
				}
			}
			
		}
		
		if(errors.size()==0){
			if(mailServiceStandardVOs != null && mailServiceStandardVOs.size()>0){
				for(MailServiceStandardVO mailServiceStandardVO:mailServiceStandardVOs){
					String[] airportCodes=null;
					if(mailServiceStandardVO.getOriginCode()!=null){
						airportCodes=mailServiceStandardVO.getOriginCode().split(",");
						try{
							for(String code:airportCodes){
								new AreaDelegate().validateAirportCode
			    						(logonAttributes.getCompanyCode(),
			    								code.toUpperCase().trim());
							}
						}catch(BusinessDelegateException businessDelegateException){
							errors = handleDelegateException(businessDelegateException);
							invocationContext.addAllError(errors);
							mailPerformanceForm.setScreenFlag("serviceStandards");
							mailPerformanceForm.setStatusFlag("Save_fail");
							invocationContext.target = FAILURE;
							return;
						}
					}
					if(mailServiceStandardVO.getOriginCode()!=null){
						airportCodes=mailServiceStandardVO.getDestinationCode().split(",");
						try{
							for(String code:airportCodes){
				    		new AreaDelegate().validateAirportCode
				    					(logonAttributes.getCompanyCode(),
				    							code.toUpperCase().trim());
							}
				    	}catch(BusinessDelegateException businessDelegateException){
				    		errors = handleDelegateException(businessDelegateException);
				    		invocationContext.addAllError(errors);
				    		mailPerformanceForm.setScreenFlag("serviceStandards");
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
			mailPerformanceSession.removeMailServiceStandardVOs();
			mailPerformanceSession.setMailServiceStndVOs(mailServiceStandardVOs);
			mailPerformanceForm.setScreenFlag("serviceStandards");
			invocationContext.target = FAILURE;
	    	return;
		}
		
		mailPerformanceSession.setMailServiceStndVOs(mailServiceStandardVOs);
		try {
			new MailTrackingDefaultsDelegate().saveServiceStandardDetails(mailServiceStandardVOs,mailServiceStandardVOstodelete);
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(mailServiceStandardVOs != null && mailServiceStandardVOs.size()>0){
			log.log(Log.FINE, "\n\n <========== SAVE SUCCESS !!!  ===========> \n\n");
			ErrorVO error = new ErrorVO(SAVE_SUCCESS);
			errors.add(error);
			mailPerformanceForm.setStatusFlag("Save_success");
			mailPerformanceForm.setScreenFlag("serviceStandards");
			invocationContext.addAllError(errors);
		}
		
		mailPerformanceSession.setMailServiceStandardVOs(null);
		mailPerformanceSession.setMailServiceStndVOs(null);
		mailPerformanceForm.setServiceStandardsPacode("");
		mailPerformanceForm.setServiceStandardsOrigin("");
		mailPerformanceForm.setServiceStandardsDestination("");
		mailPerformanceForm.setServiceStandard("");
		mailPerformanceForm.setContractId("");
		mailPerformanceForm.setScanningWaived(null);
		mailPerformanceForm.setScreenFlag("serviceStandards");
		mailPerformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		invocationContext.target = SUCCESS;	
		
	}
	
	private ArrayList<MailServiceStandardVO> updateMailPerformanceVOs(
			ArrayList<MailServiceStandardVO> mailServiceStandardVOs,
			MailPerformanceForm mailPerformanceForm,
			LogonAttributes logonAttributes) {
		
		log.entering("SaveCommand","updateMailSubClassVOs");
		String[] oprflags = mailPerformanceForm.getServiceStdoperationFlag();
		Collection<MailServiceStandardVO> newMailServiceStandardVOs = new ArrayList<MailServiceStandardVO>();
		
		int maxsize=mailPerformanceForm.getOriginCode().length;
    	String[] scanWaivedFlag=new String[maxsize];
    	if(mailPerformanceForm.getScanWaived()!=null){
    		String[] counter=new String[maxsize];
    		int size1=mailPerformanceForm.getScanWaived().length;
    		int cnt;
    		for(cnt=0;cnt<size1;cnt++){
    			counter[cnt]=mailPerformanceForm.getScanWaived()[cnt];
    		}
    		for(int i=0;i<size1;i++){
    			scanWaivedFlag[Integer.parseInt(counter[i])]="Y";
    		}
    		for(int i=0;i<maxsize;i++){
    			if(scanWaivedFlag[i]==null||!scanWaivedFlag[i].equals("Y")){
    				scanWaivedFlag[i]="N";
    			}
    		}
    	
    	}
    	
    	int size = 0;
    	if(mailServiceStandardVOs != null && mailServiceStandardVOs.size() > 0){
			   size = mailServiceStandardVOs.size();
    	}
		for(int index=0; index<oprflags.length;index++){
			if(index >= size){
				if(!"NOOP".equals(oprflags[index])){
					MailServiceStandardVO mailServiceStandardVO = new MailServiceStandardVO();
					mailServiceStandardVO.setCompanyCode(logonAttributes.getCompanyCode());
					mailServiceStandardVO.setGpaCode(mailPerformanceForm.getServiceStandardsPacode());
					mailServiceStandardVO.setOriginCode(mailPerformanceForm.getOriginCode()[index]);
					mailServiceStandardVO.setDestinationCode(mailPerformanceForm.getDestinationCode()[index]);
					mailServiceStandardVO.setServicelevel(mailPerformanceForm.getServicelevel()[index]);
					mailServiceStandardVO.setServicestandard(mailPerformanceForm.getServicestandard()[index]);
					mailServiceStandardVO.setContractid(mailPerformanceForm.getContractid()[index]);
					mailServiceStandardVO.setOperationFlag(mailPerformanceForm.getServiceStdoperationFlag()[index]);
					mailServiceStandardVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
					mailServiceStandardVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
	    			if(mailPerformanceForm.getScanWaived()!=null){
	    				mailServiceStandardVO.setScanWaived(scanWaivedFlag[index]);
	    			}
	    			else{
	    				mailServiceStandardVO.setScanWaived("N");
	    			}
	    			
	    			newMailServiceStandardVOs.add(mailServiceStandardVO);
				}
			}else{
				int count = 0;
				if(mailServiceStandardVOs != null && mailServiceStandardVOs.size() > 0){
				   for(MailServiceStandardVO mailServiceStandardVO:mailServiceStandardVOs){
					   if(count == index){
						   if(!"NOOP".equals(oprflags[index])){
							   mailServiceStandardVO.setCompanyCode(logonAttributes.getCompanyCode());
								mailServiceStandardVO.setGpaCode(mailPerformanceForm.getServiceStandardsPacode());
								mailServiceStandardVO.setOriginCode(mailPerformanceForm.getOriginCode()[index]);
								mailServiceStandardVO.setDestinationCode(mailPerformanceForm.getDestinationCode()[index]);
								mailServiceStandardVO.setServicelevel(mailPerformanceForm.getServicelevel()[index]);
								mailServiceStandardVO.setServicestandard(mailPerformanceForm.getServicestandard()[index]);
								mailServiceStandardVO.setContractid(mailPerformanceForm.getContractid()[index]);
								mailServiceStandardVO.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());
								mailServiceStandardVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
							   if(mailPerformanceForm.getScanWaived()!=null){
				    				mailServiceStandardVO.setScanWaived(scanWaivedFlag[index]);
				    			}
				    			else{
				    				mailServiceStandardVO.setScanWaived("N");
				    			}
								   mailServiceStandardVO.setOperationFlag(oprflags[index]);
							   

							   newMailServiceStandardVOs.add(mailServiceStandardVO);
						   }
					   }
					   count++;
				   }
				}
			}
		}
    	
    	log.exiting("SaveCommand","updateMailSubClassVOs");
    	
    	return (ArrayList<MailServiceStandardVO>)newMailServiceStandardVOs;
		
	}
	
	
}
