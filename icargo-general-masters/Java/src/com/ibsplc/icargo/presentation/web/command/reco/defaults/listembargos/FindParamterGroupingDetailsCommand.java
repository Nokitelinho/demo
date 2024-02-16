package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class FindParamterGroupingDetailsCommand extends BaseCommand{

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes
			    			= applSessionImpl.getLogonVO();
		ListEmbargoRulesForm listForm = (ListEmbargoRulesForm)invocationContext.screenModel;
		ListEmbargoRulesSession session = getScreenSession("reco.defaults", "reco.defaults.listembargo");
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		Collection<String> groupDetails = null;
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		generalMasterGroupFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		generalMasterGroupFilterVO.setGroupType(listForm.getGroupType());
		generalMasterGroupFilterVO.setGroupName(listForm.getGroupName());
		generalMasterGroupFilterVO.setGroupCategory("EMB");
		try {
			groupDetails = generalMasterGroupingDelegate.findGroupElementsByName(generalMasterGroupFilterVO);
			session.setGroupDetails(groupDetails);
		} catch (BusinessDelegateException e) {
			e.printStackTrace();
			invocationContext.target = "faliure";
		}
		
		invocationContext.target = "success";
	}
}	
