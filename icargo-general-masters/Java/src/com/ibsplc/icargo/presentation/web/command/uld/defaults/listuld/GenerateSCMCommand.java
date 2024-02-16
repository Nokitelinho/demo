/* GenerateSCMCommand.java Created on Jul 07,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.listuld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDListVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListULDSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.GenerateSCMSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2408
 */ 
public class GenerateSCMCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SendCommand");

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.listuld";
	
	private static final String MODULE_SCM = "uld.defaults";

	/**
	 * Screen Id of Generate SCM
	 */
	private static final String SCREENID_SCM = "uld.defaults.generatescm";

	private static final String SEND_SUCCESS = "send_success";
	
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";	

	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("GenerateSCMCommand", "execute");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		GenerateSCMSession generateSCMSession = (GenerateSCMSession) getScreenSession(
				MODULE_SCM, SCREENID_SCM);
		ListULDForm listULDForm = (ListULDForm) invocationContext.screenModel;
		ListULDSession listULDSession = 
							(ListULDSession)getScreenSession(MODULE,SCREENID);
		Page<ULDListVO> uldListVO = listULDSession.getListDisplayPage();
		Map<String, Collection<OneTimeVO>> oneTimeCollection = fetchScreenLoadDetails(logonAttributes.getCompanyCode());
		if(listULDForm.getSelectedRows() != null &&
    			listULDForm.getSelectedRows().length > 0) {
    		String selectedRows[] = listULDForm.getSelectedRows();
    		ArrayList<ULDVO> uldVOs =new ArrayList<ULDVO>();
    		
    		if(selectedRows!=null && selectedRows.length>0){
    			for(int i=0;i<selectedRows.length;i++){
	    			ULDVO uldVO = new ULDVO();
	    			ULDListVO uldListvo = uldListVO.get(Integer.parseInt(selectedRows[i]));
	    			uldVO.setCompanyCode(uldListvo.getCompanyCode());
	    			uldVO.setUldNumber(uldListvo.getUldNumber());
	    			//Added by nisha on 01Aug08 for bugfix starts
	    			// Added by a-3278 for bug 20381 on 03Oct08 starts
					ArrayList<OneTimeVO> facTypes = (ArrayList<OneTimeVO>) oneTimeCollection
							.get(FACILITY_TYPE);
					if (facTypes != null && facTypes.size() > 0) {
						for (OneTimeVO oneTimeVO : facTypes) {
							if (uldListvo.getFacilityType() != null
									&& uldListvo.getFacilityType().equals(
											oneTimeVO.getFieldValue())) {
								uldVO.setFacilityType(oneTimeVO
										.getFieldDescription());
							}
						}
					}
					// uldVO.setFacilityType(uldListvo.getFacilityType());
					// a-3278 ends
	    			uldVO.setLocation(uldListvo.getLocation());
	    			//ends	    			
	    			uldVO.setScmStatusFlag(ULDVO.SCM_SYSTEM_STOCK);
	    			uldVOs.add(uldVO);
	    			
    			}
    			Page<ULDVO> uldPage = new Page<ULDVO>(uldVOs,0,0,0,0,0,false);
    			log.log(Log.INFO, "ULD Vos in generate SCM-->", uldPage);
				generateSCMSession.setSystemStock(uldPage);
    			generateSCMSession.setChangedSystemStock(null);
    		}
		}else{
			//added by a-3045 for bug 24468 starts
			ULDListFilterVO uldListFilterVO = new ULDListFilterVO();				
			generateSCMSession.setULDListFilterVO(listULDSession.getListFilterVO());
		}
		//added by a-3045 for bug 24468 ends
		invocationContext.target = SEND_SUCCESS;
	}

	/**
	 * @author a-3278
	 * for bug 20381 on 03Oct08
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchScreenLoadDetails(
			String companyCode) {
		Map<String, Collection<OneTimeVO>> hashMap = new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(FACILITY_TYPE);
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);

		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		return hashMap;
	}

}
