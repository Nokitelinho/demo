package com.ibsplc.icargo.presentation.web.command.reco.defaults.exceptionembargos;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSession;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.exceptionembargos.ExceptionEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ExceptionEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * * Command class for listing Exception Embargos.
*
* @author A-6843
*/
public class ListCommand extends BaseCommand{ 
	
	
	/** Logger for Exception Embargos Log. */
	private Log log = LogFactory.getLogger("RECO.DEFAULTS");

	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.exceptionembargo";
	
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";
	
	/** The Constant SUCCESS. */
	private static final String FAILURE = "failure";
	

	/**The Constant ERROR_EMPTY_RECORDS. */
	private static final String ERROR_EMPTY_RECORDS = 
		"reco.defaults.emptyrecords"; 

	/**The Constant ERROR_INVALID_STARTDATE. */
	private static final String ERROR_INVALID_STARTDATE ="reco.defaults.startdatemandatory";
	
	/**The Constant ERROR_INVALID_ENDDATE. */
	private static final String ERROR_INVALID_ENDDATE ="reco.defaults.enddatemandatory";
	
		
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		
    	log.entering("ListCommand","execute "); 
    	ExceptionEmbargoForm exceptionEmbargoForm = (ExceptionEmbargoForm) invocationContext.screenModel;
    	ExceptionEmbargoSession session = (ExceptionEmbargoSession) getScreenSession(MODULE, SCREENID);
		EmbargoRulesDelegate embargoRulesDelegate= new EmbargoRulesDelegate();
		Page<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVO=null;		
		Collection<ErrorVO> errors = null;
		
		ExceptionEmbargoFilterVO exceptionEmbargoFilterVO=populateExceptionEmbargoFilterVO(exceptionEmbargoForm);
		errors=validateForm(exceptionEmbargoFilterVO);
		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		try {
			exceptionEmbargoDetailsVO = embargoRulesDelegate.findExceptionEmbargoDetails(exceptionEmbargoFilterVO);
			if(exceptionEmbargoDetailsVO != null && !exceptionEmbargoDetailsVO.isEmpty()){
				session.setTotalRecords(exceptionEmbargoDetailsVO.getTotalRecordCount());
				session.setExceptionEmbargoDetails(exceptionEmbargoDetailsVO);
			}else{
				ErrorVO error = new ErrorVO(ERROR_EMPTY_RECORDS);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
				session.setExceptionEmbargoDetails(null);
			}
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
		}
		if(errors != null && errors.size () > 0){
			   invocationContext.addAllError(errors);
			   invocationContext.target = FAILURE;
			   return;
		}
		exceptionEmbargoForm.setScreenStatusFlag(ComponentAttributeConstants.COMPONENT_TYPE_DETAIL);
		invocationContext.target = SUCCESS;
    	log.exiting("ListCommand","execute");
	}
	
	/**
	 * @param exceptionEmbargoFilterVO
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ExceptionEmbargoFilterVO exceptionEmbargoFilterVO) {
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		if((exceptionEmbargoFilterVO.getStartDate()==null)&&(exceptionEmbargoFilterVO.getEndDate()!=null)){
			errorVO = new ErrorVO(ERROR_INVALID_STARTDATE);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			error.add(errorVO);
			}
		else if((exceptionEmbargoFilterVO.getStartDate()!=null)&&(exceptionEmbargoFilterVO.getEndDate()==null))
			{
			errorVO = new ErrorVO(ERROR_INVALID_ENDDATE);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			error.add(errorVO);
			}
		return error;
	}
	
	/**
	 * 
	 * @param exceptionEmbargoForm
	 * @return
	 */
	private ExceptionEmbargoFilterVO populateExceptionEmbargoFilterVO(ExceptionEmbargoForm exceptionEmbargoForm){
		ExceptionEmbargoFilterVO exceptionEmbargoFilterVO= new ExceptionEmbargoFilterVO();
		ApplicationSession appSession = (ApplicationSession)getApplicationSession();
		LogonAttributes logonVO = appSession.getLogonVO();
		LocalDate startDate=null;
		LocalDate endDate=null;
		if(exceptionEmbargoForm.getStartDateFilter()!=null && exceptionEmbargoForm.getStartDateFilter().trim().length()>0 )
			{
			startDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			startDate.setDate(exceptionEmbargoForm.getStartDateFilter());
		}
		if(exceptionEmbargoForm.getEndDateFilter()!=null && exceptionEmbargoForm.getEndDateFilter().trim().length()>0)
			{
			endDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			endDate.setDate(exceptionEmbargoForm.getEndDateFilter());
		}
		ExceptionEmbargoSession session = (ExceptionEmbargoSession) getScreenSession(MODULE, SCREENID);
		exceptionEmbargoFilterVO.setStartDate(startDate);
		exceptionEmbargoFilterVO.setShipmentPrefix(exceptionEmbargoForm.getShipmentPrefixFilter());
		exceptionEmbargoFilterVO.setMasterDocumentNumber(exceptionEmbargoForm.getMasterDocumentNumberFilter());
		exceptionEmbargoFilterVO.setEndDate(endDate);
		exceptionEmbargoFilterVO.setCompanyCode(logonVO.getCompanyCode());
		String displayPage = exceptionEmbargoForm.getDisplayPage();
		if(null !=displayPage && displayPage.length()>0){
			exceptionEmbargoFilterVO.setPageNumber(Integer.parseInt(displayPage));
		}
		if(ExceptionEmbargoForm.PAGINATION_MODE_FROM_FILTER.equals(exceptionEmbargoForm.getNavigationMode())) {
			exceptionEmbargoFilterVO.setTotalRecordCount(-1);
		}else if(ExceptionEmbargoForm.PAGINATION_MODE_FROM_NAVIGATION.equals(exceptionEmbargoForm.getNavigationMode())) {
			exceptionEmbargoFilterVO.setTotalRecordCount(session.getTotalRecords());
		}else {
			exceptionEmbargoFilterVO.setTotalRecordCount(-1);
		}
		return exceptionEmbargoFilterVO;

	}
}
		
		