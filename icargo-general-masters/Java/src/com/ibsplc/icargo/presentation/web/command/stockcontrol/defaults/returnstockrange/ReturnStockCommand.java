/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.returnstockrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.MonitorStockSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReturnStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ReturnStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 *
 */
public class ReturnStockCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String BLANK_STRING = "";

	private static final String RELOAD = "Y";//to reload monitor stock screen

	private static final String WARNINGOK = "Y";//if approver has been deleted from the s/m,then,form.getWarningOk()="Y"

	private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK"; 
		
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	private static final String MODE_RANGES = "ranges";
	private static final String MODE_NUMBER_OF_DOCUMENTS  = "numberOfDocuments";
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ReturnStockCommand", "execute");
		ReturnStockForm form = (ReturnStockForm) invocationContext.screenModel;

		ReturnStockSession session = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.returnstockrange");
		log.log(Log.FINE, "\n\n....form.getWarningOk()======================",
				form.getWarningOk());
		MonitorStockSession session2 = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
	 	String companyCode=logonAttributesVO.getCompanyCode();
		Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
		Collection<RangeVO> colRangeVO = new ArrayList<RangeVO>();	
		if(!WARNINGOK.equals(form.getWarningOk())){
			Collection<ErrorVO> errors = null;
			String[] rangeFrom = form.getRangeFrom();
			String[] rangeTo = form.getRangeTo();
			String[] noOfDocs = form.getNoofDocs();

			errors = validateForm(form, session);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_success";
				return;
			}
			if(form.getReturnMode()!=null && form.getReturnMode().equalsIgnoreCase(MODE_NUMBER_OF_DOCUMENTS)){
				RangeVO rangeVO = new RangeVO();
	 			rangeVO.setStartRange(form.getModeRangeFrom());
	 			if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length()>0) {
					rangeVO.setNumberOfDocuments(Long.valueOf(form.getModeNumberOfDocuments()));
				}
	 			/*
	 			 * Call for finding the stock ranges available for a stock holder
	 			 */
	 			RangeFilterVO rangeFilterVO=new RangeFilterVO(); 	
	 			Collection<RangeVO> rangeVOs=null;
	 			rangeFilterVO.setCompanyCode(companyCode);
	 			rangeFilterVO.setStockHolderCode(form.getStockHolder());
	 			rangeFilterVO.setDocumentType(form.getDocType());
	 			rangeFilterVO.setDocumentSubType(form.getSubType());
	 			rangeFilterVO.setStartRange(rangeVO.getStartRange());
	 			rangeFilterVO.setEndRange(rangeVO.getEndRange());
	 			rangeFilterVO.setNumberOfDocuments(form.getModeNumberOfDocuments()); 			
	 			if(form.isManual()){
	 				rangeFilterVO.setManual(form.isManual());
	 			}
	 			 try{
	 				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate(); 				
	 				log.log(Log.FINE, "...rangeFilterVO.........", rangeFilterVO);
	 				rangeVOs = stockControlDefaultsDelegate.findRanges(rangeFilterVO);
	 			} catch(BusinessDelegateException businessDelegateException){ 			
	 				errorVos = handleDelegateException(businessDelegateException);
	 			} 
	 			if(rangeVOs != null && rangeVOs.size()> 0){ 
	 				colRangeVO.addAll(rangeVOs);
	 				int sum=0;
	 				for(RangeVO rangeTemp:rangeVOs){
	 					sum+=rangeTemp.getNumberOfDocuments();
	 				}
	 				form.setTotalNoOfDocs(sum+"");
	 			}else {
	 				  ErrorVO error = new ErrorVO("stockcontrol.defaults.invalidrange");		          
	 		          error.setErrorDisplayType(ErrorDisplayType.ERROR);
	 		          errorVos.add(error); 		          
	 			}
	 			if(errorVos!=null && errorVos.size()>0){
	 				invocationContext.addAllError(errorVos);
	 				invocationContext.target = "screenload_success";
	 				return;
	 			}
			}else{
			int count = 0;
				colRangeVO = session.getCollectionRangeVO();
			for (RangeVO range : colRangeVO) {

				range.setStartRange(rangeFrom[count].toUpperCase());
				range.setEndRange(rangeTo[count].toUpperCase());
				if (!BLANK_STRING.equals(String.valueOf(noOfDocs[count]))) {
					//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also
					range.setNumberOfDocuments(Long.valueOf(noOfDocs[count]));
				}

				count++;

			}
			}
			StockAllocationVO stockAllocationVO = new StockAllocationVO();
			
			//Approver code
			 String approver = "";
                   try{
                  	 approver =new StockControlDefaultsDelegate().
				findApproverCode(getApplicationSession().getLogonVO().getCompanyCode(),
						form.getStockHolder(),form.getDocType(),
						form.getSubType());
                 }catch (BusinessDelegateException businessDelegateException) {				
              	   errors.addAll(handleDelegateException(businessDelegateException));
                 	}
            //Approver code end
			TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TXN_COD_ALLOCATE_STOCK);
			if(privilegeNewVO!=null && PRVLG_RUL_STK_HLDR.equals(privilegeNewVO.getPrivilegeCode())&&
					PRVLG_LVL_STKHLD.equals(privilegeNewVO.getLevelType())&&
					privilegeNewVO.getTypeValue()!=null && 
					privilegeNewVO.getTypeValue().trim().length()>0){
				String[] typeValues = privilegeNewVO.getTypeValue().split(",");
				List<String> privilegedStkHldrList = Arrays.asList(typeValues);
				
				if(!privilegedStkHldrList.contains(form.getStockHolder())&&
						!privilegedStkHldrList.contains(approver)){
					ErrorVO error =  new ErrorVO("stockcontrol.defaults.allocatenotauthorized");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
				}
			}
			try {
				stockAllocationVO.setRanges(colRangeVO);
				stockAllocationVO.setCompanyCode(getApplicationSession()
						.getLogonVO().getCompanyCode());
				stockAllocationVO.setDocumentType(form.getDocType());
				stockAllocationVO.setDocumentSubType(form.getSubType());
				stockAllocationVO.setStockHolderCode(approver);
				stockAllocationVO.setStockControlFor(form.getStockHolder());
				stockAllocationVO.setTransferMode(StockAllocationVO.MODE_RETURN);
				//Added as part of ICRD-79163
				stockAllocationVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));	
				stockAllocationVO.setRemarks(form.getRemarks());
				stockAllocationVO.setAirlineIdentifier(getAirlineIdentifier(session.getPartnerAirline()));
				stockAllocationVO.setAwbPrefix(session2.getAwbPrefix().substring(0,3));
				 // Added by A-2882 for bug 108953 starts
				if (form.getManualCheckFlag() != null
						&& AbstractVO.FLAG_YES.equalsIgnoreCase(form.getManualCheckFlag())) {
					stockAllocationVO.setManual(true);
				}
				// Added by A-2882 for bug 108953 ends
				//Added by A-2881 for ICRD-3082
				stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_RETURN);
				
				 log.log(Log.FINE,
						"\n\n.....................stockAllocationVO*******> ",
						stockAllocationVO);
				session.setStockAllocationVO(stockAllocationVO);
				new StockControlDefaultsDelegate().
						allocateStock(stockAllocationVO);

			} catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);

			}
		}else{//when the approver is deleted from the s/m
			StockAllocationVO stkAllocationVO= session.getStockAllocationVO();
			stkAllocationVO.setApproverDeleted(true);
			form.setWarningOk("N");
			log
					.log(
							Log.FINE,
							"\n\n.......stockAllocationVO when approver has already been deleted from s/m..>>>>>>>>> ",
							stkAllocationVO);
			try {
				new StockControlDefaultsDelegate().
				allocateStock(stkAllocationVO);
			}
			catch (BusinessDelegateException businessDelegateException) {
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);

			}

	}

		if (errorVos != null && errorVos.size() > 0) {
			invocationContext.addAllError(errorVos);
			invocationContext.target = "screenload_success";
			return;
		}
		form.setMode(RELOAD);
		log.exiting("ReturnStockCommand", "execute");
		invocationContext.target = "screenload_success";
	}
	
	private int getAirlineIdentifier(AirlineLovVO partnerAirline) {
		int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(partnerAirline!=null){
			int airlineIdentifier=partnerAirline.getAirlineIdentifier(); 				
			
			return (airlineIdentifier>0)?airlineIdentifier:ownAirlineIdentifier;
		} else {
			return ownAirlineIdentifier;
		}
	}

	/**
	 * To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private long findLong(String range) {
		log.entering("ReturnStockCommand", "findLong");
		char[] sArray = range.toCharArray();
		long base = 1;
		long sNumber = 0;
		for (int i = sArray.length - 1; i >= 0; i--) {
			sNumber += base * calculateBase(sArray[i]);
			int jNum = sArray[i];
			if (jNum > 57) {
				base *= 26;
			} else {
				base *= 10;
			}
		}
		return sNumber;
	}
/**
 * method to calculate base
 * @param yChar
 * @return
 */
	private long calculateBase(char yChar) {
		long xLong = yChar;
		long base = 0;
		try {
			base = Integer.parseInt(BLANK_STRING + yChar);
		} catch (NumberFormatException numberFormatException) {
			base = xLong - 65;
		}
		return base;
	}
	/**
	 * method to validate fields
	 * @param form
	 * @param session
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ReturnStockForm form,
			ReturnStockSession session) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isFromNull = false;
		boolean isDuplicate = false;
		boolean isContinuous = false;
		boolean isEndRange = false;
		boolean isOverlap = false;
		String[] rangeFrom = form.getRangeFrom();
		String[] rangeTo = form.getRangeTo();
		int noOfDocs = 0;
		if(form.getReturnMode()!=null && form.getReturnMode().equalsIgnoreCase(MODE_NUMBER_OF_DOCUMENTS)){
			if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length() > 0){
				noOfDocs = Integer.parseInt(form.getModeNumberOfDocuments());
			}
			/* 
			 *  commented by A-7740 as a part of ICRD-225106
			 *  if(form.getModeRangeFrom() != null && form.getModeRangeFrom().trim().length()==0){
	              error = new ErrorVO("stockcontrol.defaults.returnstock.err.plzenterrangefrom");
	              error.setErrorDisplayType(ErrorDisplayType.ERROR);
	              errors.add(error);    		  
	    	  }*/
	    	  if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length()==0
	    			  || noOfDocs == 0){
	              error = new ErrorVO("stockcontrol.defaults.returnstock.err.plzenternoofdocs");
	              error.setErrorDisplayType(ErrorDisplayType.ERROR);
	              errors.add(error);    		  
	    	  }
		}else{
		for (int i = 0; i < (rangeFrom.length - 1); i++) {
			for (int j = i + 1; j < rangeTo.length; j++) {
				if (BLANK_STRING.equals(rangeFrom[i])
						|| BLANK_STRING.equals(rangeTo[i])
						|| BLANK_STRING.equals(rangeFrom[j])
						|| BLANK_STRING.equals(rangeTo[j])) {
					break;
				} else {

					if (findLong(rangeFrom[i]) == findLong(rangeFrom[j])
							&& findLong(rangeTo[i]) == findLong(rangeTo[j])) {

						isDuplicate = true;
						break;
					}

					if (findLong(rangeTo[i]) == findLong(rangeFrom[j])) {

						isContinuous = true;
						break;
					}

					if (findLong(rangeTo[i]) == findLong(rangeTo[j])) {

						isEndRange = true;
						break;
					}
					if (((findLong(rangeFrom[i]) < findLong(rangeFrom[j]))
							&& (findLong(rangeTo[i]) > findLong(rangeFrom[j])))
							|| ((findLong(rangeTo[j]) < findLong(rangeTo[i]))
							&& (findLong(rangeFrom[i]) < findLong(rangeTo[j])))) {
						isOverlap = true;
						break;
					}
				}

			}
		}

		for (int i = 0; i < rangeFrom.length; i++) {

			if (BLANK_STRING.equals(rangeFrom[i])) {
				isFromNull = true;
				break;
			}
		}
		for (int i = 0; i < rangeTo.length; i++) {

			if (BLANK_STRING.equals(rangeTo[i])) {
				isFromNull = true;
				break;
			}
		}

		if (isFromNull) {
			Object[] obj = { "Mandatory" };
			error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngfrmvalintab", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (isDuplicate) {
			Object[] obj = { "Duplicate" };
			error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (isContinuous) {
			Object[] obj = { "Continuous" };
			error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (isEndRange) {
			Object[] obj = { "Same End Range" };
			error = new ErrorVO("stockcontrol.defaults.endrngcannotbesame", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if (isOverlap) {
			Object[] obj = { "Overlap" };
			error = new ErrorVO("stockcontrol.defaults.overlapingrngcannotbeentered", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		}
		if(errors.size()==0 && rangeFrom!=null){
			populateAvailableRangesForValidation(session);
			StockRangeVO stockRangeVO = session.getStockRangeVO();
			if(stockRangeVO!=null){
				for (int i = 0; i < rangeFrom.length; i++) {
					Collection<RangeVO> rangevos = stockRangeVO.getAvailableRanges();
					for(RangeVO rangeVO : rangevos){
						if(rangeVO.getMasterDocumentNumbers()!=null){
							for(String docnum : rangeVO.getMasterDocumentNumbers()){
								if(((findLong(docnum) > findLong(rangeFrom[i]))
										&& (findLong(docnum) < findLong(rangeTo[i])))
										|| (findLong(docnum) == findLong(rangeFrom[i]))
										||  (findLong(docnum) == findLong(rangeTo[i]))){
									error = new ErrorVO("stockcontrol.defaults.utilisationexists");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break;
								}
							}
						}
					}
				}
			}
		}

		return errors;
	}
	
	/**
	 * getPrivilegeVO()
	 * @param transactionCode
	 * @return TransactionPrivilegeNewVO
	 */
	private TransactionPrivilegeNewVO getPrivilegeVO(
			String transactionCode) {
		log.entering("ListMonitorStockCommand", "getPrivilegeVO");
		List<TransactionPrivilegeNewVO> privilegeList=null;
		try {
			privilegeList = (ArrayList<TransactionPrivilegeNewVO>) 
			TransactionPrivilegeHelper.getAllowededPrivilegesForTransaction(transactionCode);
		} catch (SystemException e) {			
			log.log(Log.SEVERE,e.getMessage());
		}
		log.exiting("ListMonitorStockCommand", "getPrivilegeVO");
		if(privilegeList!=null && !privilegeList.isEmpty()){
			return privilegeList.get(0);
		}
		return null;
	}
	/**
	 * @param returnStockSession
	 */
	private void populateAvailableRangesForValidation(ReturnStockSession returnStockSession) {
		StockRangeVO stkRangeVO = findAvailableRanges(returnStockSession.getStockFilterVO());
		if(stkRangeVO != null && stkRangeVO.getAvailableRanges() != null  && 
				stkRangeVO.getAvailableRanges().size() > 0) {
			if (returnStockSession.getStockRangeVO() != null) {
				returnStockSession.getStockRangeVO().setAvailableRanges(stkRangeVO.getAvailableRanges());
			}
		}
	}
	
	/**
	 * @param stockFilterVO
	 * @return
	 */
	private StockRangeVO findAvailableRanges(StockFilterVO stockFilterVO) {
		StockRangeVO stockRangeVO = null;
		try {
			stockRangeVO = new StockControlDefaultsDelegate()
					.viewRange(stockFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
		}
		return stockRangeVO;
	}
}
