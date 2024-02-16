/*
 * CancelEmbargoCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1747
 *
 */

public class CancelEmbargoCommand extends BaseCommand{

	
	private static final String CANCELLED = "CANCELLED";
	private static final String ACTIVE = "ACTIVE";
	private static final String SUSPENDED = "SUSPENDED";
	private Log log = LogFactory.getLogger("EMBARGO DEFAULST");

	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command
	 * #execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		EmbargoRulesDelegate embargoRulesDelegate;

		ListEmbargoRulesForm listEmbargoForm =
			(ListEmbargoRulesForm)invocationContext.screenModel;
		ListEmbargoRulesSession session = getScreenSession(
				"reco.defaults", "reco.defaults.listembargo");

		/*ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();

		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();*/

		String[] segment =  listEmbargoForm.getRowId();
		Collection<ErrorVO> errors = null;
		embargoRulesDelegate = new EmbargoRulesDelegate();
		Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
		for(int i=0;i<segment.length;i++){
			int index = segment[i].indexOf('-');
			String refNum = segment[i].substring(0,index);
			int versionIndex = segment[i].indexOf('+');
			String verNum = segment[i].substring(versionIndex+1);
		Page<EmbargoDetailsVO> allSegments
					= session.getEmabrgoDetailVOs();

		EmbargoDetailsVO embargoDetailsVO = new EmbargoDetailsVO();
		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();
		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
		for(EmbargoDetailsVO vo: allSegments){
			if(vo.getEmbargoReferenceNumber().equals(refNum) && vo.getEmbargoVersion()==Integer.parseInt(verNum)){
				embargoDetailsVO  = vo;
				errors = validateForm(embargoDetailsVO);
			    embargoDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			    embargoDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
				embargoDetailsVO.setStatus("C");
					embargoDetailsVOs.add(embargoDetailsVO);
				}
			}
			
		
		}
		if (errors != null && errors.size() > 0) {
			
			invocationContext.addAllError(errors);
			invocationContext.target = "failure";
			return;
		}
		else{
		
			//embargoDetailsVO.setStatus(CANCELLED);
			try{
				 
				embargoRulesDelegate.cancelEmbargo(embargoDetailsVOs);
				
				 invocationContext.target = "success";
			}
			catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				Collection<ErrorVO> errors1 = handleDelegateException(businessDelegateException);
				
				
				invocationContext.addAllError(errors1);
				invocationContext.target = "screenload_failure";
				return;
				
			}
		}
	}

	/**Method to validate form
	 * @param vo
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(EmbargoDetailsVO vo) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(vo.getStatus()!= null){
			if(vo.getStatus().equals(CANCELLED)  ){
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.cancelled", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(vo.getStatus()!= null){
				if(!vo.getStatus().equals(ACTIVE) && !vo.getStatus().equals(SUSPENDED) ){
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.cannotcancel", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}

			if(vo.getEndDate()!= null){
				LocalDate localCurrentDate
					= new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
				Calendar calCurrentdate = vo.getEndDate().toCalendar();
				Calendar calFromdate = localCurrentDate.toCalendar();
				if(calCurrentdate.before(calFromdate)){
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.expired", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}



		return errors;
	}
}
