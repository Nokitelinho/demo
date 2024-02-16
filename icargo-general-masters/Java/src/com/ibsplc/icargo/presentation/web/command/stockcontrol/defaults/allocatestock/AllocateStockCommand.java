/*
 * AllocateStockCommand.java Created on Sep 9, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatestock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1870
 *
 */
public class AllocateStockCommand extends BaseCommand {
	private static final String NEW = "New";
	private Log log = LogFactory.getLogger("STOCK CONTROL");
	
    private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK"; 
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";

	private static final String AWB_PREFIX_NOT_MAPPED = "stockcontrol.defaults.maintainstockrequest.awbprefixnotmapped";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	AllocateStockSession session= getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatestock");
		AllocateStockForm allocateStockForm=(AllocateStockForm)invocationContext.screenModel;
		handleUpdate(session,allocateStockForm);
		Collection<RangeVO> rangeVOs=session.getRangeVO();
		Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String[] chk=allocateStockForm.getCheckBox();
		StockAllocationVO stockAllocationVO=new StockAllocationVO();
		StockRequestVO stkRequestVO = null;
		for(StockRequestVO stockRequestVO:pageStockRequestVO){

			if(chk[0].equals(stockRequestVO.getRequestRefNumber())){
			stockAllocationVO.setDocumentType(stockRequestVO.getDocumentType());
			stockAllocationVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
			stockAllocationVO.setStockControlFor(allocateStockForm.getStockControlFor());
			stockAllocationVO.setStockHolderCode(stockRequestVO.getStockHolderCode());
			stockAllocationVO.setManual(stockRequestVO.isManual());
			}
		}
		stockAllocationVO.setRemarks(allocateStockForm.getRemarks());
		stockAllocationVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockAllocationVO.setRanges(rangeVOs);
		stockAllocationVO.setNewStockFlag(false);
		stockAllocationVO.setAllocate(true);
		stockAllocationVO.setTransferMode(StockAllocationVO.MODE_NORMAL);	
		stockAllocationVO.setAirlineIdentifier(getAirlineIdentifier(allocateStockForm.getAwbPrefix()));
		stockAllocationVO.setAwbPrefix(allocateStockForm.getAwbPrefix().substring(0,3));
		for(StockRequestVO requestVO:pageStockRequestVO){
			if(chk[0].equals(requestVO.getRequestRefNumber())){
				stockAllocationVO.setRequestRefNumber(requestVO.getRequestRefNumber());
				stockAllocationVO.setAllocatedStock(requestVO.getAllocatedStock());
				stockAllocationVO.setLastUpdateTime(requestVO.getLastStockHolderUpdateTime());
				stockAllocationVO.setLastUpdateTimeForStockReq(requestVO.getLastUpdateDate());
			}
		}
		
		StockHolderVO stockHolderVO = null;
		boolean isequal = false;
		try{
			stockHolderVO =  new StockControlDefaultsDelegate().findStockHolderDetails(
					getApplicationSession().getLogonVO().getCompanyCode()
					,upper(stockAllocationVO.getStockHolderCode()));
		}catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE,"BusinessDelegateException caught..........");
		}
		for(StockVO stockHold : stockHolderVO.getStock()){
			if(stockHold.getAirlineIdentifier() == (stockAllocationVO.getAirlineIdentifier())){
			isequal = true;
			break;			
			}
		}
		if(!isequal){
			ErrorVO error=new ErrorVO(AWB_PREFIX_NOT_MAPPED);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);
			invocationContext.target = "screenload_failure";
			return;
		}
		TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TXN_COD_ALLOCATE_STOCK);
		if(privilegeNewVO!=null && PRVLG_RUL_STK_HLDR.equals(privilegeNewVO.getPrivilegeCode())&&
				PRVLG_LVL_STKHLD.equals(privilegeNewVO.getLevelType())&&
				privilegeNewVO.getTypeValue()!=null && 
				privilegeNewVO.getTypeValue().trim().length()>0){
			String[] typeValues = privilegeNewVO.getTypeValue().split(",");
			List<String> privilegedStkHldrList = Arrays.asList(typeValues);
			//Approver code
			 String approver = "";
                   try{
                  	 approver =new StockControlDefaultsDelegate().
				        findApproverCode(stockAllocationVO.getCompanyCode(),stockAllocationVO.getStockHolderCode(),stockAllocationVO.getDocumentType(),
				        		stockAllocationVO.getDocumentSubType());
                 }catch (BusinessDelegateException businessDelegateException) {				
              	   errors.addAll(handleDelegateException(businessDelegateException));
                 	}
            //Approver code end
			if(!privilegedStkHldrList.contains(stockAllocationVO.getStockHolderCode())&&
					!privilegedStkHldrList.contains(approver)){
				ErrorVO error =  new ErrorVO("stockcontrol.defaults.allocatenotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
			}
		}
		try{
			 RangeFilterVO rangeFilterVO=new RangeFilterVO();
			 StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			if("N".equals(session.getMode())){
				if(session.getRangeVO()==null || session.getRangeVO().size()==0 ){
					for(StockRequestVO stockRequestVO:pageStockRequestVO){
						if(chk[0].equals(stockRequestVO.getRequestRefNumber())){
						rangeFilterVO.setCompanyCode(stockRequestVO.getCompanyCode());
						rangeFilterVO.setDocumentType(stockRequestVO.getDocumentType());
						rangeFilterVO.setDocumentSubType(stockRequestVO.getDocumentSubType());
						rangeFilterVO.setNumberOfDocuments(String.valueOf(stockRequestVO.getApprovedStock()-stockRequestVO.getAllocatedStock()));
						rangeFilterVO.setStockHolderCode(allocateStockForm.getStockControlFor());
						rangeFilterVO.setStartRange("0");
						rangeFilterVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
						}
					}
					Collection<RangeVO> rangeVOS=stockControlDefaultsDelegate.findRanges(rangeFilterVO);
					stockAllocationVO.setRanges(rangeVOS);
					//Added as part of ICRD-46860 starts
					session.setRangeVO(rangeVOS);
					stockAllocationVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));	
					session.setStockAllocationVO(stockAllocationVO);
					//Added as part of ICRD-46860 ends
				}
			}
			long allocatedQuantity = getAllocatedQuantity(stockAllocationVO);

			errors = validateForm(allocateStockForm,session,allocatedQuantity);
			 if (errors != null && errors.size() > 0) {

					invocationContext.addAllError(errors);
					invocationContext.target = "reject_failure";
					return;
				}
			 stkRequestVO = session.getStockRequestVO();
			 
			 stockAllocationVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());
			/*if(stkRequestVO != null){
				stockAllocationVO.setLastUpdateTime(stkRequestVO.getLastStockHolderUpdateTime());
				stockAllocationVO.setLastUpdateTimeForStockReq(stkRequestVO.getLastUpdateDate());
			}*/


			log
					.log(
							Log.FINE,
							"\n\n\n\n-----------------allocateStockForm.getStockControlFor()-----------",
							allocateStockForm.getStockControlFor());
			//Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_ALLOCATE);

		/*//Added by Aswin Starts
			String privileageCode=stockControlDefaultsDelegate.findPrivileage(allocateStockForm.getStockControlFor());
			boolean hasPrivilege = false;
			try{




				SecurityAgent agent = SecurityAgent.getInstance();
				hasPrivilege = agent.checkPrivilegeForAction(privileageCode);


			}catch(SystemException ex){

			}if(hasPrivilege){


			stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
			}else{



				invocationContext.addError(new ErrorVO("stockcontrol.defaults.donthaveprivileage"));


			}
			// Added by Aswin Ends
*/
stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
			 /*StockRequestVO vo=new StockRequestVO();
			 for(StockRequestVO requestVO:pageStockRequestVO){
					if(chk[0].equals(requestVO.getRequestRefNumber())){
						vo.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
						vo.setAllocatedStock(requestVO.getAllocatedStock());
						vo.setRequestRefNumber(requestVO.getRequestRefNumber());
			            stockControlDefaultsDelegate.allocateStockRequest(vo);
					}
			 }*/
		}catch(BusinessDelegateException businessDelegateException){
			Object[] obj = { "stockHolderCode" };
		  	ErrorVO error = new ErrorVO("stockcontrol.defaults.stocknotfound", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			invocationContext.target = "reject_failure";
			return;
		}
		invocationContext.target = "allocate_success";
    }
    private String upper(String input){
		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
    }
    private int getAirlineIdentifier(String awbPrefix) {
		String[] tokens=awbPrefix.split("-");
		if(tokens.length>2){
			return Integer.parseInt(tokens[2]);
		}
		return getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
	}
private Collection<ErrorVO> validateForm(AllocateStockForm allocateStockForm,
		AllocateStockSession session,long allocatedQuantity){
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		Page<StockRequestVO> pageStockRequestVO=session.getPageStockRequestVO();
		String[] chk=allocateStockForm.getCheckBox();
		long allocatedStock=0;
		long persistedApprovedStock=0;
		long approvedStock=0;
			for(StockRequestVO StockRequestvo:pageStockRequestVO){
				if(chk[0].equals(StockRequestvo.getRequestRefNumber())){
					 allocatedStock=StockRequestvo.getAllocatedStock();
					 persistedApprovedStock=StockRequestvo.getPersistedApprovedStock();
					 approvedStock = StockRequestvo.getApprovedStock();
					 if(persistedApprovedStock<=allocatedStock){
					 	if(approvedStock > persistedApprovedStock){
								error = new ErrorVO("stockcontrol.defaults.approvenotdone");
						 		error.setErrorDisplayType(ErrorDisplayType.ERROR);
						 		errors.add(error);
						 		break;
						 	}else{
						 		error = new ErrorVO("stockcontrol.defaults.approvedstknotlessthanallocatedstk");
						 		error.setErrorDisplayType(ErrorDisplayType.ERROR);
						 		errors.add(error);
						 		break;
						 	}
					 }
					 if(NEW.equals(StockRequestvo.getStatus())){

							error = new ErrorVO("stockcontrol.defaults.stkreqofstatusnewcantbeallocated");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break;
					}
					long tobeAllocatedQty = persistedApprovedStock - allocatedStock;
					if(allocatedQuantity > tobeAllocatedQty){
						error = new ErrorVO("stockcontrol.defaults.allocatestockgreater");
				 		error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 		errors.add(error);
				 		break;
					}
				}

			}

		return errors;
	}
private void handleUpdate(AllocateStockSession session,AllocateStockForm allocateStockForm){
	Page<StockRequestVO> StockRequestVOs=session.getPageStockRequestVO();
	String[] approvedStock=allocateStockForm.getApprovedStock();
	String[] allocatedStock=allocateStockForm.getAllocatedStock();
	int i=-1;
	for(StockRequestVO stockReqvo:StockRequestVOs){
		i++;
		if(!"".equals(approvedStock[i])){

			stockReqvo.setApprovedStock(new Integer(approvedStock[i]).longValue());

		}
		if(!"".equals(allocatedStock[i])){

			stockReqvo.setAllocatedStock(new Integer(allocatedStock[i]).longValue());

		}
	}
	session.setPageStockRequestVO(StockRequestVOs);

	}

private long getAllocatedQuantity(StockAllocationVO stockAllocationVO){
	long allocatedQty=0;
	if(stockAllocationVO.getRanges()!=null && stockAllocationVO.getRanges().size() !=0){
		for(RangeVO rangeVO : stockAllocationVO.getRanges()){
			allocatedQty += difference(rangeVO.getStartRange(),rangeVO.getEndRange());
		}
	}
	return allocatedQty;
}
/**
 * Method for calculating the base of number
 * @param input
 * @return base
 */
private  long calculateBase(char input){
	long inside=input;
	long base=0;
	try{
		base=Integer.parseInt(""+input);
	}catch(NumberFormatException numberFormatException){
		base=inside-65;
	}
	return base;
}

/** To get the numeric value of the string
 *
 * @param range
 * @return Numeric value
 */
private long findLong(String range){
	char[] sArray=range.toCharArray();
	long base=1;
	long sNumber=0;
	for(int i=sArray.length-1;i>=0;i--){
		sNumber+=base*calculateBase(sArray[i]);
		int index=sArray[i];
		if (index>57) {
			base*=26;
		} else {
			base*=10;
		}
	}
	return sNumber;
}

/** To find the difference between range from and range to value
 *
 * @param rangeFrom Range from value
 * @param rangeTo Range to value
 * @return Difference between the ranges
 */
private int difference(String rangeFrom,String rangeTo){

	long difference=findLong(rangeTo)-findLong(rangeFrom);
	difference++;
	return (int)difference;
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

}
