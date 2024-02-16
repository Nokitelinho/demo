
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ApproveCommand extends BaseCommand{
	
	private static final String DRAFT = "D";
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
	    if(embargoVo!=null){
		    embargoDetailsVO.setEmbargoReferenceNumber(embargoVo.getEmbargoReferenceNumber());
		    embargoDetailsVO.setEmbargoVersion(embargoVo.getEmbargoVersion());
		    embargoDetailsVO.setCompanyCode(embargoVo.getCompanyCode());
		    
		    errors = validateForm(embargoVo);
		    
		    if (embargoVo.getIsSuspended()) {
		    	embargoDetailsVO.setStatus("S");
	    	}
	    	else{
	    		embargoDetailsVO.setStatus("A");
	    	}
		    embargoDetailsVOs.add(embargoDetailsVO);
	    }
		
		if (errors != null && errors.size() > 0) {			
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}
		int displayPage = 1;
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
		embargoFilterVO.setEmbargoRefNumber(embargoVo.getEmbargoReferenceNumber());
		embargoFilterVO.setCompanyCode(embargoVo.getCompanyCode());
		//embargoFilterVO.setStatus("A");
		embargoFilterVO.setTotalRecordCount(-1);
		embargoFilterVO.setPageNumber(displayPage);
		Page<EmbargoDetailsVO> pg = findEmbargoVos(embargoFilterVO, displayPage);
		if(pg!=null){			
			for(EmbargoDetailsVO embargoVO: pg){
				if("A".equals(embargoVO.getStatus()) || "S".equals(embargoVO.getStatus())){
				embargoVO.setStatus("I");
				embargoDetailsVOs.add(embargoVO);
				}
			}
		}
	    try {		 
	    	
			 embargoDelegate.approveEmbargo(embargoDetailsVOs);
			 ErrorVO error = null;
			 String referenceNoObj=embargoVo.getEmbargoReferenceNumber();
			 maintainEmbargoSession.setIsSaved(referenceNoObj);
			 Object[] obj={referenceNoObj};
			 error = new ErrorVO("embargo.approve",obj);// Saved Successfully
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
			if(!vo.getStatus().equals(DRAFT)  ){
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.cannotapprove", obj);
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
	private Page<EmbargoDetailsVO> findEmbargoVos(EmbargoFilterVO filter, int displayPage){
		Page<EmbargoDetailsVO> detailsVo = null;
		
		
		EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
		try{
			detailsVo = delegate.findEmbargos(filter, displayPage);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
			
		}
		return detailsVo;
	}
}