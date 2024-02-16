
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.ux.billingScheduleMaster;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingParameterDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.BillingScheduleDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.BillingScheduleMasterModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;



/**
 * @author A-9498
 *
 */
public class ValidateCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MRA MAIL");
	private static final String SUCCESS_MESSAGE = "Saved Successfully";
	private static final String SUCCESS = "success";
	private static final String  ERR_DUPLICATE_SCHEDULE = "mail.mra.defaults.ux.billingScheduleMaster.error.duplicate";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		this.log.entering("SaveCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		BillingScheduleMasterModel billingScheduleMasterModel = (BillingScheduleMasterModel) actionContext
				.getScreenModel(); // Model
		List<BillingScheduleDetails> mailBillingList = billingScheduleMasterModel.getBillingScheduleDetailList();

		if (!mailBillingList.isEmpty()) {
			
			List<BillingScheduleDetails>  deleteList = mailBillingList.stream().filter(blgSchDet->OPERATION_FLAG_DELETE.equals(blgSchDet.get__opFlag())).collect(Collectors.toList());
			Map <String,String> deleteMap=null;
			
			if(deleteList!=null && !deleteList.isEmpty()){
			 deleteMap = deleteList.stream().collect(Collectors.toMap(BillingScheduleDetails::getPeriodNumber,BillingScheduleDetails::getPeriodNumber));
			}
			validate(actionContext,mailBillingList,logonAttributes,deleteMap);
		}

		ResponseVO responseVO = new ResponseVO();
		ArrayList<BillingScheduleMasterModel> results = new ArrayList<>();
		results.add(billingScheduleMasterModel);
		responseVO.setResults(results);
		responseVO.setStatus(SUCCESS);
		actionContext.setResponseVO(responseVO);
		log.exiting("SaveCommand", "execute");
	}
	
	private void checkForDuplicate(ActionContext actionContext,List<BillingScheduleDetails> mailBillingList ){
		
		List<BillingScheduleDetails> insertList = mailBillingList.stream().filter(blgSchDet->OPERATION_FLAG_INSERT.equals(blgSchDet.get__opFlag())).collect(Collectors.toList());
		Set<String> insertSet=null;
		if(insertList!=null && !insertList.isEmpty()){
			insertSet = 	insertList.stream().map(BillingScheduleDetails::getPeriodNumber).collect(Collectors.toSet());
			if(insertSet!=null && !insertSet.isEmpty() && insertSet.size()!=insertList.size()){
				actionContext
				.addError(new ErrorVO(ERR_DUPLICATE_SCHEDULE));
				return;
			}
		
		}
		
		
	}
	
	private void validate(ActionContext actionContext,List<BillingScheduleDetails> mailBillingList,LogonAttributes logonAttributes,Map <String,String> deleteMap){
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		boolean isduplicate = false;
		List<ErrorVO> errorVOs = null;
		
		checkForDuplicate(actionContext, mailBillingList);
		
		for (BillingScheduleDetails billingScheduleModel : mailBillingList) {
			if (billingScheduleModel.get__opFlag()!=null && billingScheduleModel.get__opFlag().equals("I")) {  
				BillingScheduleDetailsVO billingScheduleDetailsVO = MailMRAModelConverter
						.constructBillingScheduleDetailsModels(billingScheduleModel);
				billingScheduleDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				try {
					isduplicate = mailTrackingMRADelegate.validateBillingSchedulemaster(billingScheduleDetailsVO);
					if (isduplicate && (deleteMap==null || !deleteMap.containsKey(billingScheduleDetailsVO.getPeriodNumber())  ) ) {
						actionContext
								.addError(new ErrorVO(ERR_DUPLICATE_SCHEDULE));
						return;
					}
					if (billingScheduleModel.getParametersList() != null
							&& !billingScheduleModel.getParametersList().isEmpty()) {
						Collection<String> countryCodes = new ArrayList<>();
						BillingParameterDetails country = billingScheduleModel.getParametersList().stream()
								.filter(param -> param.getParameterName().equals("Country Code")).findFirst()
								.orElse(null);
						if (country != null) {
							countryCodes.add(country.getParameterValue());
							new AreaDelegate().validateCountryCodes(
									getApplicationSession().getLogonVO().getCompanyCode(), countryCodes);

						}

						BillingParameterDetails gpaCode = billingScheduleModel.getParametersList().stream()
								.filter(param -> param.getParameterName().equals("GPA CODE")).findFirst().orElse(null);
						if (gpaCode != null) {
							PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
							postalAdministrationVO = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(), gpaCode.getParameterValue().toUpperCase());
							log.log(Log.FINE, "postalAdministrationVO", postalAdministrationVO);
							if (postalAdministrationVO == null) {
								actionContext.addError(new ErrorVO("Enter valid GPA code for Period no: "
										+ billingScheduleModel.getPeriodNumber()));
								return;
							}
						}
					}
				} catch (BusinessDelegateException businessDelegateException) {
					// handleDelegateException(businessDelegateException);
					errorVOs = handleDelegateException(businessDelegateException);
					if (errorVOs.iterator().hasNext()) {
						if (("shared.area.invalidcountry").equals(errorVOs.iterator().next().getErrorCode())) {
							actionContext.addError(new ErrorVO(
									"Enter valid country code for Period no: " + billingScheduleModel.getPeriodNumber()));
						} else {
							actionContext.addAllError(errorVOs);
						}
					}
					return;
				}
			}
		}
		
	}
	
	

	
	
}
