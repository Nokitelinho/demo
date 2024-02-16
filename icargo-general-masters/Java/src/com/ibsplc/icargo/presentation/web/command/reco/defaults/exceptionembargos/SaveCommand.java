package com.ibsplc.icargo.presentation.web.command.reco.defaults.exceptionembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
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
 * @author A-6843
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SAVE COMMAND");
	/** The Module Name. */
	private static final String MODULE = "reco.defaults";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "reco.defaults.exceptionembargo";
		
	/** The Constant SUCCESS. */
	private static final String SUCCESS = "success";
	
	/** The Constant FAILURE. */
	private static final String FAILURE = "failure";
	
	private static final String SAVED ="reco.defaults.savesuccess";
	private static final String ERROR_INVALID_AWB ="reco.defaults.awbnumbermandatory";
	private static final String ERROR_INVALID_STARTDATE ="reco.defaults.startdatemandatory";
	private static final String ERROR_INVALID_ENDDATE ="reco.defaults.enddatemandatory";
	private static final String ERROR_INVALID_REMARKS ="reco.defaults.remarksmandatory";
	private static final String ERROR_INVALID_DATES="reco.defaults.improperdates";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();
		
		ExceptionEmbargoForm exceptionEmbargoForm =
			(ExceptionEmbargoForm) invocationContext.screenModel;

		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
		ExceptionEmbargoSession exceptionEmbargoSession =
			getScreenSession(MODULE, SCREENID);
		Collection<ErrorVO> errors = null;
		
		String[] operationFlag = exceptionEmbargoForm.getHiddenOpFlag();
		String[] startDate = exceptionEmbargoForm.getStartDate();
		String[] endDate = exceptionEmbargoForm.getEndDate();
		String[] shipmentPrefix = exceptionEmbargoForm.getShipmentPrefix();
		String[] masterDocumentNumber = exceptionEmbargoForm.getMasterDocumentNumber();
		String[] remarks = exceptionEmbargoForm.getRemarks();
		String[] serialNumbers = exceptionEmbargoForm.getSerialNumbers();
		
		Collection<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVOs = new ArrayList<ExceptionEmbargoDetailsVO>();
		Page<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVOPage = null;
		ExceptionEmbargoDetailsVO exceptionEmbargoDetailsVO = null;
		if(exceptionEmbargoSession.getExceptionEmbargoDetails() !=null){
			exceptionEmbargoDetailsVOPage = exceptionEmbargoSession.getExceptionEmbargoDetails();
			exceptionEmbargoDetailsVOPage.clear();
		}
		else{
			List<ExceptionEmbargoDetailsVO> lists=new ArrayList<ExceptionEmbargoDetailsVO>();
			exceptionEmbargoDetailsVOPage=new Page<ExceptionEmbargoDetailsVO>(lists,1,25,lists.size(),0,0,false);

		}
		if(operationFlag!=null){
			for(int i=0; i<operationFlag.length ; i++){
				if((!"NOOP".equals(operationFlag[i]))&&(operationFlag[i].trim().length()>0)){
					 exceptionEmbargoDetailsVO = new ExceptionEmbargoDetailsVO();
					 	LocalDate strDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);
						LocalDate endingDate = new LocalDate(LocalDate.NO_STATION,
								Location.NONE, false);		
						exceptionEmbargoDetailsVO.setCompanyCode(companyCode);
						if(startDate[i] != null && startDate[i].trim().length()>0){		
					strDate.setDate(startDate[i]);	
						exceptionEmbargoDetailsVO.setStartDate(strDate);
					}
						if(endDate[i] != null && endDate[i].trim().length()>0){	
							endingDate.setDate(endDate[i]);	
						exceptionEmbargoDetailsVO.setEndDate(endingDate);
					}	
						if(remarks[i] != null && remarks[i].trim().length()>0){		
						exceptionEmbargoDetailsVO.setRemarks(remarks[i].trim());
					}
						if(shipmentPrefix[i] != null && shipmentPrefix[i].trim().length()>0){		
						exceptionEmbargoDetailsVO.setShipmentPrefix(shipmentPrefix[i].trim());
					}
						if(masterDocumentNumber[i] != null && masterDocumentNumber[i].trim().length()>0){		
						exceptionEmbargoDetailsVO.setMasterDocumentNumber(masterDocumentNumber[i].trim());
					}
						if(operationFlag[i] != null && operationFlag[i].trim().length()>0){		
						exceptionEmbargoDetailsVO.setOperationFlag(operationFlag[i]);
					}
						if(serialNumbers[i] != null && serialNumbers[i].trim().length()>0){		
							exceptionEmbargoDetailsVO.setSerialNumbers(new Integer(serialNumbers[i]).intValue());
						}
					
					exceptionEmbargoDetailsVOs.add(exceptionEmbargoDetailsVO);
						exceptionEmbargoDetailsVOPage.add(exceptionEmbargoDetailsVO);
					
				}
			}
			exceptionEmbargoSession.setExceptionEmbargoDetails(exceptionEmbargoDetailsVOPage);
		}
		errors = validateForm(exceptionEmbargoDetailsVOs);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
		
		try {
			embargoRulesDelegate.saveExceptionEmbargoDetails(exceptionEmbargoDetailsVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			log.log(Log.INFO, "BusinessDelegateException----------->", businessDelegateException.getMessage());
		}
		if(errors != null && errors.size () > 0){
			   invocationContext.addAllError(errors);
			   invocationContext.target = FAILURE;
			   return;
		}else{
			exceptionEmbargoForm.setEndDate(null);
			exceptionEmbargoForm.setEndDateFilter(null);
			exceptionEmbargoForm.setStartDate(null);
			exceptionEmbargoForm.setStartDateFilter(null);
			exceptionEmbargoForm.setMasterDocumentNumber(null);
			exceptionEmbargoForm.setMasterDocumentNumberFilter(null);
			exceptionEmbargoForm.setHiddenOpFlag(null);
			exceptionEmbargoForm.setRemarks(null);
			exceptionEmbargoForm.setShipmentPrefix(null);
			exceptionEmbargoForm.setShipmentPrefixFilter(null);
			exceptionEmbargoForm.setPageNumber(null);
			exceptionEmbargoForm.setDisplayPage(null);
			exceptionEmbargoForm.setNavigationMode(null);
			exceptionEmbargoForm.setApplicableTransactionCodes(null);
			exceptionEmbargoForm.setAwbScribbledText(null);
			exceptionEmbargoForm.setSerialNumbers(null);
			exceptionEmbargoSession.removeExceptionEmbargos();
			exceptionEmbargoSession.removeTotalRecords();
			ErrorVO error = new ErrorVO(SAVED);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);
			invocationContext.addAllError(errors);
		}
		invocationContext.target = SUCCESS;
	}

	/**
	 * @param exceptionEmbargoDetailsVOs
	 * @return
	 */
	private Collection<ErrorVO> validateForm(Collection<ExceptionEmbargoDetailsVO>exceptionEmbargoDetailsVOs) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		for(ExceptionEmbargoDetailsVO exceptionEmbargoDetailsVO : exceptionEmbargoDetailsVOs){
			
			if(!"D".equals(exceptionEmbargoDetailsVO.getOperationFlag())){
				//awb mandatory
				if(exceptionEmbargoDetailsVO.getShipmentPrefix() == null || exceptionEmbargoDetailsVO.getShipmentPrefix().length() == 0 ||
						exceptionEmbargoDetailsVO.getMasterDocumentNumber() == null || exceptionEmbargoDetailsVO.getMasterDocumentNumber().length() == 0){
					errorVO = new ErrorVO(ERROR_INVALID_AWB);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				//start date
				if(exceptionEmbargoDetailsVO.getStartDate() == null){
					errorVO = new ErrorVO(ERROR_INVALID_STARTDATE);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				//end date
				if(exceptionEmbargoDetailsVO.getEndDate() == null){
					errorVO = new ErrorVO(ERROR_INVALID_ENDDATE);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				//remarks
				if(exceptionEmbargoDetailsVO.getRemarks() == null || exceptionEmbargoDetailsVO.getRemarks().length() == 0){
					errorVO = new ErrorVO(ERROR_INVALID_REMARKS);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
				if ((exceptionEmbargoDetailsVO.getStartDate() != null && exceptionEmbargoDetailsVO
						.getEndDate() != null)
						&& (exceptionEmbargoDetailsVO.getStartDate())
								.isGreaterThan(exceptionEmbargoDetailsVO
										.getEndDate())) {
					errorVO = new ErrorVO(ERROR_INVALID_DATES);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}	
		return errors;
	}
}
