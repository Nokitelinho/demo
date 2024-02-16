package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditDsnEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailCarditModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditdsnenquiry.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	02-Sep-2019		:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("OPERATIONS CARDITDSNENQUIRY");
	private static final String MAL_CLASS = "mailtracking.defaults.mailclass";
	private static final String MAL_CAT = "mailtracking.defaults.mailcategory";
	private static final String MAL_STA = "mailtracking.defaults.mailstatus";
	private static final String MAL_FLT_TYP = "mailtracking.defaults.carditenquiry.flighttype";
	
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
		
		log.entering("ScreenloadCarditDsnEnquiryCommand","execute");
		
		LogonAttributes logonAttributes = 
				(LogonAttributes) getLogonAttribute();
		CarditDsnEnquiryModel carditDSNEnquiryModel=
				(CarditDsnEnquiryModel) actionContext.getScreenModel();
		CarditFilter carditFilter=
				(CarditFilter) carditDSNEnquiryModel.getCarditFilter();
		SharedDefaultsDelegate sharedDefaultsDelegate = 
	    	      new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException e) {
			actionContext.addAllError(handleDelegateException(e));
		}
		
		updateMilitaryClass((Collection<OneTimeVO>) oneTimeValues
				.get(MAL_CLASS));
		carditDSNEnquiryModel.setOneTimeValues(MailCarditModelConverter.constructOneTimeValues(oneTimeValues));
		carditDSNEnquiryModel.setAirportCode(logonAttributes.getAirportCode());
		ResponseVO responseVO = new ResponseVO();
		List<CarditDsnEnquiryModel> results = new ArrayList<CarditDsnEnquiryModel>();
		results.add(carditDSNEnquiryModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		this.log.exiting("ScreenloadCarditDsnEnquiryCommand", "execute");
		
	}
	
	private Collection<String> getOneTimeParameterTypes() {
		
		Collection<String> parameterTypes = new ArrayList();
		parameterTypes.add(MAL_CAT);
		parameterTypes.add(MAL_CLASS);
		parameterTypes.add(MAL_STA);
		// parameterTypes.add("mailtracking.defaults.resditevent");
		parameterTypes.add(MAL_FLT_TYP);
		// parameterTypes.add("mailtracking.defaults.carditenquiry.searchmode");

		return parameterTypes;
	}
	
	private void updateMilitaryClass(Collection<OneTimeVO> mailClasses) {
		
		this.log.exiting("ScreenloadCarditDsnEnquiryCommand", "updateClass");
		if ((mailClasses != null) && (mailClasses.size() > 0)) {
			Collection<Collection<OneTimeVO>> mailClassVOs = new ArrayList();
			String classDesc = null;
			String fildDes = null;
			Collection<OneTimeVO> militaryVOs = new ArrayList();
			for (OneTimeVO mailClassVO : mailClasses) {
				if (mailClassVO.getFieldDescription().equals(classDesc)) {
					militaryVOs.add(mailClassVO);
				} else {
					classDesc = mailClassVO.getFieldDescription();
					militaryVOs = new ArrayList();
					militaryVOs.add(mailClassVO);
				}
				if (militaryVOs.size() > 1) {
					String fieldDesc = ((OneTimeVO) ((ArrayList) militaryVOs).get(0)).getFieldDescription();
					if (!fieldDesc.equals(fildDes)) {
						mailClassVOs.add(militaryVOs);
						fildDes = fieldDesc;
					}
				}
			}
			if (mailClassVOs.size() > 0) {
				for (Collection<OneTimeVO> oneTimeVOs : mailClassVOs) {
					if (oneTimeVOs.size() > 0) {
						mailClasses.removeAll(oneTimeVOs);
						StringBuilder oneTimeVal = new StringBuilder();
						for (OneTimeVO militaryVO : oneTimeVOs) {
							oneTimeVal.append(militaryVO.getFieldValue()).append(",");
						}
						OneTimeVO mailClassVO = (OneTimeVO) oneTimeVOs.iterator().next();
						mailClassVO.setFieldValue(oneTimeVal.substring(0, oneTimeVal.length() - 1).toString());
						mailClasses.add(mailClassVO);
					}
				}
			}
		}
		this.log.exiting("ScreenloadCarditDsnEnquiryCommand", "updateClass");
	}

}
