
package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
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

public class ApproveCommand extends BaseCommand{
	
	private static final String DRAFT = "DRAFT";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		EmbargoRulesDelegate embargoRulesDelegate;
		ListEmbargoRulesForm listEmbargoForm =
			(ListEmbargoRulesForm)invocationContext.screenModel;
	    ListEmbargoRulesSession session = getScreenSession(
				"reco.defaults", "reco.defaults.listembargo");
	    String[] segment =  listEmbargoForm.getRowId();
		Collection<ErrorVO> errors = null;
	    Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
	    ArrayList<EmbargoDetailsVO> selectedDraftfEmbargos = new ArrayList<EmbargoDetailsVO>();
	    embargoRulesDelegate = new EmbargoRulesDelegate();
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
				if(DRAFT.equals(vo.getStatus())){
					selectedDraftfEmbargos.add(vo);
				}  
				embargoDetailsVO  = vo;
				errors = validateForm(embargoDetailsVO);
				if (vo.getIsSuspended()) {
			    	embargoDetailsVO.setStatus("S");
		    	}
		    	else{
		    		embargoDetailsVO.setStatus("A");
		    	}
				embargoDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			    embargoDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
					break;
				}
			}
			embargoDetailsVOs.add(embargoDetailsVO);
	    }
	    if(selectedDraftfEmbargos!=null && selectedDraftfEmbargos.size()>0){
	    errors = checkDuplicateDraft(selectedDraftfEmbargos);
	    }
		if (errors != null && errors.size() > 0) {
			
			invocationContext.addAllError(errors);
			invocationContext.target = "failure";
			return;
		}
	    try {		 
	    	//embargoDetailsVO.setStatus("A");
	    	
	    	int displayPage = 1;
			EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
			Collection<EmbargoDetailsVO> embargoDetailsTempVOs = new ArrayList<EmbargoDetailsVO>();
			for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
			embargoFilterVO.setEmbargoRefNumber(embargoDetailsVO.getEmbargoReferenceNumber());
			embargoFilterVO.setCompanyCode(embargoDetailsVO.getCompanyCode());
			//embargoFilterVO.setStatus("A");
			embargoFilterVO.setTotalRecordCount(-1);
			embargoFilterVO.setPageNumber(displayPage);
			Page<EmbargoDetailsVO> pg = findEmbargoVos(embargoFilterVO, displayPage);
			if(pg!=null){			
				for(EmbargoDetailsVO embargoVO: pg){
					if("A".equals(embargoVO.getStatus()) || "S".equals(embargoVO.getStatus())){
						embargoVO.setStatus("I");
						embargoDetailsTempVOs.add(embargoVO);
					}
				}
			}
			}
			if(embargoDetailsTempVOs!=null && embargoDetailsTempVOs.size()>0)
				{
				embargoDetailsVOs.addAll(embargoDetailsTempVOs);
				}
	    	embargoRulesDelegate.approveEmbargo(embargoDetailsVOs);
			 invocationContext.target = "success";
		} catch (BusinessDelegateException ex) {
			Collection<ErrorVO> errors1 = handleDelegateException(ex);			
			invocationContext.addAllError(errors1);
			invocationContext.target = "screenload_failure";
			return;
		}		
	}
	private Collection<ErrorVO> validateForm(EmbargoDetailsVO vo) {
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
	private Collection<ErrorVO> checkDuplicateDraft(ArrayList<EmbargoDetailsVO> selectedDraftfEmbargos){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		String[] refNumArray = new String[selectedDraftfEmbargos.size()];
		int i= 0;
		for(EmbargoDetailsVO vo : selectedDraftfEmbargos){
			refNumArray[i] = vo.getEmbargoReferenceNumber();
			i++;
		}
		for(int j = 0; j < refNumArray.length; j++){
		    for(int k = 0; k < refNumArray.length; k++){
		    	if(k!= j){
		        if (refNumArray[k] .equals( refNumArray[j])){
		        	Object[] obj = { refNumArray[k] };
					error = new ErrorVO("reco.defaults.duplicatedraftpresent", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
	return errors;
	}
		    	}
		    }
		}
	return errors;
	}
}
