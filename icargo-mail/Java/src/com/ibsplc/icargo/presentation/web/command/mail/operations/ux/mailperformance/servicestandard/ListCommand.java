/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.servicestandard;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplaySubtype;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 *
 */
public class ListCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String SUCCESS = "servicestandard_list_success";
	private static final String FAILURE = "servicestandard_list_failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY = 
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
		private static final String NO_RESULT = "mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the list command of service standard----------> \n\n");
		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformaceSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    
	    Page<MailServiceStandardVO> mailServiceStandardVOs = null;
	    ArrayList<MailServiceStandardVO>  mailServiceStandardVOsToDelete = new ArrayList<MailServiceStandardVO>(); 
	    MailServiceStandardVO mailServiceStandardVOTmp=null;
	    
	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    mailPerformanceForm.setStatusFlag("");
	    
	    if(null!=mailPerformanceForm.getServiceStandardsPacode() && ("").equals(mailPerformanceForm.getServiceStandardsPacode())) {
	    	log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
			error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setStatusFlag("List_fail");
	    	mailPerformaceSession.setMailServiceStandardVOs(null);
	    	mailPerformaceSession.setMailServiceStndVOs(null);
	    	mailPerformanceForm.setScreenFlag("serviceStandards");
	    	mailPerformanceForm.setScreenStatusFlag
						(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target = FAILURE;
	    	return;
	    }
	    
	    try {
	    	MailServiceStandardFilterVO mailServiceStandardFilterVO  =new MailServiceStandardFilterVO();
	    	mailServiceStandardFilterVO.setCompanyCode(companyCode);
	    	//Added by A-7540
	    	int displayPage=Integer.parseInt(mailPerformanceForm.getDisplayPage());
	    	mailServiceStandardFilterVO.setPageNumber(displayPage);	    
	    	mailServiceStandardFilterVO.setTotalRecords(-1);
	    	if(mailPerformanceForm.getDefaultPageSize()!=null && mailPerformanceForm.getDefaultPageSize().trim().length()>0){
	    		mailServiceStandardFilterVO.setDefaultPageSize(Integer.parseInt(mailPerformanceForm.getDefaultPageSize()));
	    	}
	    	mailServiceStandardFilterVO.setGpaCode(mailPerformanceForm.getServiceStandardsPacode());
	    	mailServiceStandardFilterVO.setOrginCode(mailPerformanceForm.getServiceStandardsOrigin());
	    	mailServiceStandardFilterVO.setDestCode(mailPerformanceForm.getServiceStandardsDestination());
	    	mailServiceStandardFilterVO.setServLevel(mailPerformanceForm.getServiceLevel());
	    	mailServiceStandardFilterVO.setServiceStandard(mailPerformanceForm.getServiceStandard());
	    	mailServiceStandardFilterVO.setContractId(mailPerformanceForm.getContractId());
	    	if(mailPerformanceForm.getScanningWaived()!=null){
	    		mailServiceStandardFilterVO.setScanWaived(mailPerformanceForm.getScanningWaived());
	    	}	
	    	else{
	    		mailServiceStandardFilterVO.setScanWaived("N");
	    	}
	    	
	    	mailServiceStandardVOs = delegate.listServiceStandardDetails(mailServiceStandardFilterVO,displayPage);
	    	
	    	log.log(Log.FINE, "\n\n mailServiceStandardVOs after find=========>",
	    			mailServiceStandardVOs);
	    	
			if(mailServiceStandardVOs == null || mailServiceStandardVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no record found********** \n\n");
				ErrorVO error = new ErrorVO(NO_RESULT);
				error.setErrorDisplayType(ErrorDisplayType.ERROR_WARNING_GROUP);
				error.setErrorDisplaySubtype(ErrorDisplaySubtype.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				/*mailPerformaceSession.setMailServiceStandardVOs(null);*/
				mailPerformanceForm.setScreenFlag("serviceStandards");
				mailPerformanceForm.setStatusFlag("List_fail_NoRecords");
				mailPerformanceForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    }catch(BusinessDelegateException businessDelegateException) {
	    	
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    
	    ArrayList<MailServiceStandardVO> mailServiceStndVOs=new ArrayList<MailServiceStandardVO>();
	    
	    mailServiceStndVOs.addAll(mailServiceStandardVOs);
	   if(mailServiceStandardVOs != null && mailServiceStandardVOs.size()>0){
	    	mailPerformaceSession.setTotalRecords(mailServiceStandardVOs.size());
		}
		
		mailPerformanceForm.setHandoverPage(mailServiceStandardVOs);
	    populateServiceStandardDetails(mailServiceStandardVOs,mailPerformanceForm);
	    mailPerformaceSession.setMailServiceStandardVOs(mailServiceStandardVOs);
	    mailPerformaceSession.setMailServiceStndVOs(mailServiceStndVOs);
	    
  
	    for(MailServiceStandardVO mailServiceStandardVO: mailServiceStandardVOs){
	    	mailServiceStandardVOTmp= new MailServiceStandardVO();
	    try {
			BeanHelper.copyProperties(mailServiceStandardVOTmp,mailServiceStandardVO);
		} catch (SystemException systemException) {
			log.log(Log.WARNING, " systemException");
		}
	    	mailServiceStandardVOsToDelete.add(mailServiceStandardVOTmp);
	    }

	    mailPerformaceSession.setMailServiceStandardVOsToDelete(mailServiceStandardVOsToDelete);

	    mailPerformanceForm.setScreenFlag("serviceStandards");
	    mailPerformanceForm.setStatusFlag("List_success");
	    mailPerformanceForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;
	    
	}

	private void populateServiceStandardDetails(Page<MailServiceStandardVO> mailServiceStandardVOs, MailPerformanceForm mailPerformanceForm){
		
		int index=0;
		int size=mailServiceStandardVOs.size();
		String[] OriginCodes=new String[size];
		String[] DestCodes=new String[size];
		String[] serviceLevels=new String[size];
		String[] serviceStandards=new String[size];
		String[] contractIds=new String[size];
		String[] scanWaviedflags=new String[size];
		String[] oprFlags=new String[size];
		for(MailServiceStandardVO mailServiceStandardVO : mailServiceStandardVOs){
			OriginCodes[index]=mailServiceStandardVO.getOriginCode();
			DestCodes[index]=mailServiceStandardVO.getDestinationCode();
			serviceLevels[index]=mailServiceStandardVO.getServicelevel();
			serviceStandards[index]=mailServiceStandardVO.getServicestandard();
			contractIds[index]=mailServiceStandardVO.getContractid();
			scanWaviedflags[index]=mailServiceStandardVO.getScanWaived();
			oprFlags[index]=mailServiceStandardVO.getOperationFlag();
			index++;
		}
		mailPerformanceForm.setOriginCode(OriginCodes);
		mailPerformanceForm.setDestinationCode(DestCodes);
		mailPerformanceForm.setServicelevel(serviceLevels);
		mailPerformanceForm.setServicestandard(serviceStandards);
		mailPerformanceForm.setScanWaived(scanWaviedflags);
		mailPerformanceForm.setContractid(contractIds);
		mailPerformanceForm.setServiceStdoperationFlag(oprFlags);
	}
	
}
