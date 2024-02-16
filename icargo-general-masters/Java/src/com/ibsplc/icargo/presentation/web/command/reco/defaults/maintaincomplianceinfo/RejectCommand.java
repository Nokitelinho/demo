
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintaincomplianceinfo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo.MaintainComplianceInfoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class RejectCommand extends BaseCommand{
	
	private static final String DRAFT = "D";
	private static final String SUCCESS_MODE = "success";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		EmbargoRulesDelegate embargoDelegate ;
		EmbargoRulesVO embargoVo = null;
	    embargoDelegate = new EmbargoRulesDelegate();
	    MaintainComplianceInfoSession maintainComplianceSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintaincomplianceinfo");
	    MaintainComplianceInfoForm maintainComplianceForm=(MaintainComplianceInfoForm)invocationContext.screenModel;
	    embargoVo = maintainComplianceSession.getEmbargoVo();
	    Collection<ErrorVO> errors = null;
	    Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
	    EmbargoDetailsVO embargoDetailsVO = new EmbargoDetailsVO();
	    if(embargoVo!=null){
		    embargoDetailsVO.setEmbargoReferenceNumber(embargoVo.getEmbargoReferenceNumber());
		    embargoDetailsVO.setEmbargoVersion(embargoVo.getEmbargoVersion());
		    embargoDetailsVO.setCompanyCode(embargoVo.getCompanyCode());
		    embargoDetailsVO.setStatus("R");
		    embargoDetailsVOs.add(embargoDetailsVO);
	    }
		errors = validateForm(embargoVo);
		if (errors != null && errors.size() > 0) {			
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}
	    try {		 
	    	 embargoVo.setStatus("R");
			 embargoDelegate.rejectEmbargo(embargoDetailsVOs);
			 ErrorVO error = null;
			 String referenceNoObj=embargoVo.getEmbargoReferenceNumber();
			 maintainComplianceSession.setIsSaved(referenceNoObj);
			 Object[] obj={referenceNoObj};
			 error = new ErrorVO("compliance.reject",obj);// Saved Successfully
			 error.setErrorDisplayType(ErrorDisplayType.INFO);
			 errors.add(error);
			 invocationContext.addAllError(errors);
			 maintainComplianceForm.setMode(SUCCESS_MODE);
			 maintainComplianceForm.setCanSave("N");
			 maintainComplianceForm.setRefNumber("");
			 invocationContext.target = "screenload_success";
		} catch (BusinessDelegateException ex) {
			Collection<ErrorVO> errors1 = handleDelegateException(ex);			
			invocationContext.addAllError(errors1);
			invocationContext.target = "screenload_failure";
			return;
		}
		
	}
	private Collection<ErrorVO> validateForm(EmbargoRulesVO vo) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		if(vo.getStatus()!= null){
			if(!vo.getStatus().equals(DRAFT)  ){
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.cannotreject", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
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