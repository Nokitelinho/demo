
package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
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

public class RejectCommand extends BaseCommand{
	
	private static final String DRAFT = "DRAFT";
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		EmbargoRulesDelegate embargoRulesDelegate;
		ListEmbargoRulesForm listEmbargoForm =
			(ListEmbargoRulesForm)invocationContext.screenModel;
	    ListEmbargoRulesSession session = getScreenSession(
				"reco.defaults", "reco.defaults.listembargo");
	    String[] segment =  listEmbargoForm.getRowId();
	    embargoRulesDelegate = new EmbargoRulesDelegate();
	    Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
	    Collection<ErrorVO> errors = null;
	    for(int i=0;i<segment.length;i++){
			int index = segment[i].indexOf('-');
			String refNum = segment[i].substring(0,index);
			int versionIndex = segment[i].indexOf('+');
			String verNum = segment[i].substring(versionIndex+1);
		Page<EmbargoDetailsVO> allSegments
					= session.getEmabrgoDetailVOs();

		EmbargoDetailsVO embargoDetailsVO = new EmbargoDetailsVO();
			
		for(EmbargoDetailsVO vo: allSegments){
			if(vo.getEmbargoReferenceNumber().equals(refNum) && vo.getEmbargoVersion()==Integer.parseInt(verNum)){
				embargoDetailsVO  = vo;
				errors = validateForm(embargoDetailsVO);
				embargoDetailsVO.setStatus("R");
					embargoDetailsVOs.add(embargoDetailsVO);
				}
			}
			
		
	    }
		if (errors != null && errors.size() > 0) {
			
			invocationContext.addAllError(errors);
			invocationContext.target = "failure";
			return;
		}
	    try {		 
	    	//embargoDetailsVO.setStatus("R");
	    	embargoRulesDelegate.rejectEmbargo(embargoDetailsVOs);
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