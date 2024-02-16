
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class CancelCommand extends BaseCommand{
	
	private static final String CANCELLED = "C";
	private static final String ACTIVE = "A";
	private static final String SUSPENDED = "S";
	private static final String SUCCESS_MODE = "success";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		EmbargoRulesDelegate embargoDelegate ;
		EmbargoRulesVO embargoVo = null;
	    embargoDelegate = new EmbargoRulesDelegate();
	    MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintainembargo");
	    MaintainEmbargoRulesForm maintainEmbargoForm=(MaintainEmbargoRulesForm)invocationContext.screenModel;
	    embargoVo = maintainEmbargoSession.getEmbargoVo();
	    Collection<ErrorVO> errors = null;
	    Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
	    EmbargoDetailsVO embargoDetailsVO = new EmbargoDetailsVO();
	    ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();
		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
	    if(embargoVo!=null){
		    embargoDetailsVO.setEmbargoReferenceNumber(embargoVo.getEmbargoReferenceNumber());
		    embargoDetailsVO.setEmbargoVersion(embargoVo.getEmbargoVersion());
		    embargoDetailsVO.setEmbargoLevel(embargoVo.getEmbargoLevel());
		    embargoDetailsVO.setCompanyCode(embargoVo.getCompanyCode());
		    embargoDetailsVO.setComplianceType(embargoVo.getComplianceType());
		    embargoDetailsVO.setRuleType(embargoVo.getRuleType());
		    embargoDetailsVO.setEmbargoDescription(embargoVo.getEmbargoDescription());
		    embargoDetailsVO.setAirportCode(logonAttributes.getAirportCode());
		    embargoDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
		    embargoDetailsVO.setStatus("C");
		    embargoDetailsVO.setStartDate(embargoVo.getStartDate());
		    embargoDetailsVO.setEndDate(embargoVo.getEndDate());
		    embargoDetailsVO.setDaysOfOperation(embargoVo.getDaysOfOperation());
		    embargoDetailsVO.setDaysOfOperationApplicableOn(embargoVo.getDaysOfOperationApplicableOn());
		    embargoDetailsVO.setDestination(embargoVo.getDestination());
		    embargoDetailsVO.setDestinationType(embargoVo.getDestinationType());		   
		    embargoDetailsVO.setRemarks(embargoVo.getRemarks());
		    embargoDetailsVO.setApplicableTransactions(embargoVo.getApplicableTransactions());
		    embargoDetailsVO.setCategory(embargoVo.getCategory());
		    embargoDetailsVO.setOrigin(embargoVo.getOrigin());
		    embargoDetailsVO.setOriginType(embargoVo.getOriginType());
		    Collection<EmbargoParameterVO> parameterVOs =  embargoVo.getParameters();
		    Set<EmbargoParameterVO> set = new HashSet<EmbargoParameterVO>();		    
		    if(parameterVOs!=null && parameterVOs.size()>0){
		    	set.addAll(parameterVOs);
		    	embargoDetailsVO.setParams(set);
		    }
		    embargoDetailsVO.setRemarks(embargoVo.getRemarks());
		    embargoDetailsVO.setIsSuspended(embargoVo.getIsSuspended());
		    embargoDetailsVO.setViaPoint(embargoVo.getViaPoint());
		    embargoDetailsVO.setViaPointType(embargoVo.getViaPointType());
		    Set<EmbargoGeographicLevelVO> embargoGeographicLevelVOSet = new HashSet<EmbargoGeographicLevelVO>();		    
		    if(embargoVo.getGeographicLevels()!=null && embargoVo.getGeographicLevels().size()>0){
		    	embargoGeographicLevelVOSet.addAll(embargoVo.getGeographicLevels());
		    	 embargoDetailsVO.setGeographicLevels(embargoGeographicLevelVOSet);
		    }
		    embargoDetailsVOs.add(embargoDetailsVO);
	    }
		errors = validateForm(embargoVo);
		if (errors != null && errors.size() > 0) {			
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}
	    try {		 
	    	 embargoVo.setStatus("C");
			 embargoDelegate.cancelEmbargo(embargoDetailsVOs);
			 ErrorVO error = null;
			 String referenceNoObj=embargoVo.getEmbargoReferenceNumber();
			 maintainEmbargoSession.setIsSaved(referenceNoObj);
			 Object[] obj={referenceNoObj};
			 error = new ErrorVO("embargo.cancel",obj);// Saved Successfully
			 error.setErrorDisplayType(ErrorDisplayType.INFO);
			 errors.add(error);
			 invocationContext.addAllError(errors);
			 maintainEmbargoForm.setMode(SUCCESS_MODE);
			 maintainEmbargoForm.setCanSave("N");
			 maintainEmbargoForm.setRefNumber("");
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