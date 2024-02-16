package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ParameterUxLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OkParameterCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String INCENTIVE_PARAMETERS = "mail.operations.incentiveparameters" ;
	
	
	private static final String SUBCLASS_NOT_EXISTS = "mailtracking.defaults.ux.mailperformance.msg.err.subclassNotExists";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("OkParameterLovCommand", "execute");
		
		ParameterUxLovForm parameterUxLovForm = (ParameterUxLovForm) invocationContext.screenModel;
	
		StringBuilder  parameter = new StringBuilder();
		StringBuilder  displayParameter = new StringBuilder();
		StringBuilder  excludeincludeValue = new StringBuilder();
		String[] exclude = null;
		MailPerformanceSession mailPerformanceSession = getScreenSession(MODULE_NAME,SCREEN_ID);
				
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 
		 Collection<MailSubClassVO> mailSubClassVOs = null;
		 LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		 Collection<IncentiveConfigurationDetailVO> detailsVO = new ArrayList<IncentiveConfigurationDetailVO>();
		
		 String excludeInclude = parameterUxLovForm.getExcludeIncludeFlag();
		 exclude = excludeInclude.split(",");
		 for(int j=0;j<parameterUxLovForm.getSelectCheckBox().length;j++){
			 
			
			 
			 
			 if(parameterUxLovForm.getSelectCheckBox().length>0 
					 && parameterUxLovForm.getSelectCheckBox()[j]!= null
					 && (parameterUxLovForm.getParameterValue()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])]!= null
					 	&& parameterUxLovForm.getParameterValue()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])].length()>0 )){
				 
				 IncentiveConfigurationDetailVO detailVO = new IncentiveConfigurationDetailVO();
				 detailVO.setDisIncParameterCode(parameterUxLovForm.getParameterCode()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])].trim());
				 detailVO.setDisIncParameterValue(parameterUxLovForm.getParameterValue()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])].trim());
				 detailVO.setExcludeFlag(exclude[j].trim());
				 detailsVO.add(detailVO);
				 
				 parameter = parameter.append(parameterUxLovForm.getParameterCode()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])])
						 				.append(" : ")
						 				.append(parameterUxLovForm.getParameterValue()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])])
						 				.append("\r\n");
				 excludeincludeValue = excludeincludeValue.append(exclude[j].trim())
						          .append(",");
				 
				 Map<String, Collection<OneTimeVO>> oneTimes = null;
				 oneTimes =  findOneTimeDescription(logonAttributes.getCompanyCode(), INCENTIVE_PARAMETERS);
						 
				
				 
				Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
				oneTimeVOs= oneTimes.get(INCENTIVE_PARAMETERS);
				String paramDesc = null;
								
				for(OneTimeVO oneTimeVO: oneTimeVOs){

					if(parameterUxLovForm.getParameterCode()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])]
							.equals(oneTimeVO.getFieldValue())){
						paramDesc = oneTimeVO.getFieldDescription();
						break;
					}
				}
				
				
				displayParameter = displayParameter.append(paramDesc.toUpperCase())
												   .append(" : ")
												   .append(parameterUxLovForm.getParameterValue()[Integer.parseInt(parameterUxLovForm.getSelectCheckBox()[j])])
									 			   .append(" (")
									 			   .append("Y".equals(exclude[j]) ? "I" : "E")
									 			   .append(")")
									 			   .append("\r\n");
									 				
									 			  
			 }
			
		 }
		 if(detailsVO != null && detailsVO.size()>0 ){
			 	mailPerformanceSession.setParameterVO((ArrayList<IncentiveConfigurationDetailVO>)detailsVO);
		 }
		for(int i=0;i<parameterUxLovForm.getParameterCode().length;i++){
			
			
			 
			if(("SCL".equals(parameterUxLovForm.getParameterCode()[i]))
					&& (parameterUxLovForm.getParameterValue()[i] != null 
							&& parameterUxLovForm.getParameterValue()[i].length() >0 )){
				
				    try {
				    	mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(
				    			logonAttributes.getCompanyCode(),parameterUxLovForm.getParameterValue()[i]);
				    	
						if(mailSubClassVOs == null || mailSubClassVOs.size()==0) {
				    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				    		ErrorVO error = new ErrorVO(SUBCLASS_NOT_EXISTS);
				    		errors.add(error);
							
				    	}
					}catch(BusinessDelegateException businessDelegateException) {
				    	handleDelegateException(businessDelegateException);
				    }
			}
		}
		
		 
		
		 if(errors != null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = FAILURE;
			    return;

		 }else{
			 
			 parameterUxLovForm.setParameter(parameter.toString());
			 parameterUxLovForm.setDisplayParameter(displayParameter.toString());
			 parameterUxLovForm.setExcludeIncludeFlag(excludeincludeValue.toString().substring(0, excludeincludeValue.length() - 1));
			
			 if("Y".equals(parameterUxLovForm.getServiceResponseFlag())){
				 parameterUxLovForm.setTxtFieldName("disIncParameter");
				 parameterUxLovForm.setDispTxtFieldName("srvDisplayParameter");
			 	 parameterUxLovForm.setExcludeTxtFieldName("excFlag");
			 }else if("N".equals(parameterUxLovForm.getServiceResponseFlag())){
				  parameterUxLovForm.setTxtFieldName("disIncNonSrvParameter");
			 	  parameterUxLovForm.setExcludeTxtFieldName("excNonSrvFlag");
			 	  parameterUxLovForm.setDispTxtFieldName("nonSrvDisplayParameter");
		 	}else{
				 parameterUxLovForm.setTxtFieldName("disIncBothSrvParameter");
			     parameterUxLovForm.setExcludeTxtFieldName("excBothSrvFlag");
			     parameterUxLovForm.setDispTxtFieldName("bothSrvDisplayParameter");
		 	}
			 
			 invocationContext.target = SUCCESS;
			 return;
		 }
	}
	
	/**
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param oneTimeCode
	 *	Parameters	:	@return
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */

	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(
			String companyCode, String oneTimeCode) {
		log.entering("MailController", "findOneTimeDescription");
		Map<String, Collection<OneTimeVO>> oneTimes = null;

		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(oneTimeCode);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,
					fieldValues);
		}catch (BusinessDelegateException localBusinessDelegateException3) {
			this.log.log(7, "onetime fetch exception");
		}

		log.exiting("MailController", "findOneTimeDescription");
		return oneTimes;
	}
}
