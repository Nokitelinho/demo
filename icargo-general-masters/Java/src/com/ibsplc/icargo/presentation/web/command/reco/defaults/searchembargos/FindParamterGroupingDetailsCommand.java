package com.ibsplc.icargo.presentation.web.command.reco.defaults.searchembargos;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

public class FindParamterGroupingDetailsCommand extends BaseCommand{
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes
			    			= applSessionImpl.getLogonVO();
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		Collection<String> groupDetails = null;
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		generalMasterGroupFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		generalMasterGroupFilterVO.setGroupType(searchEmbargoForm.getGroupType());
		generalMasterGroupFilterVO.setGroupName(searchEmbargoForm.getGroupName());
		generalMasterGroupFilterVO.setGroupCategory("EMB");
		try {
			groupDetails = generalMasterGroupingDelegate.findGroupElementsByName(generalMasterGroupFilterVO);
			searchEmbargoSession.setGroupDetails(groupDetails);
		} catch (BusinessDelegateException e) {
			e.printStackTrace();
			invocationContext.target = "faliure";
		}
		
		invocationContext.target = "success";
	}
}	
