/*
 * SaveCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.AllocateNewStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.AllocateNewStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1927
 *
 */
public class SaveCommand extends BaseCommand {

	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix starts

	private static final String AWB = "AWB";

	private static final String S = "S";

	//Added by A-1927 @ NCA on 02-Aug-2007 for NCA Bug Fix ends


	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String BLANKSPACE = "";
	
	private static final String NO_OPERATION = "NOOP";

	private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK"; 
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	private static final String AWB_PREFIX_NOT_MAPPED = "stockcontrol.defaults.maintainstockrequest.awbprefixnotmapped";
	private static final String ERROR_INVALID_STOCK_HOLDER_CODE = "stockcontrol.defaults.invalidstockholder";
	private static final String SCREEN_LOAD_FAILURE = "screenload_failure";
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("SaveCommand","**execute");
			AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
			AllocateNewStockSession session = (AllocateNewStockSession)
							getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");
			StockAllocationVO stockAllocationVO=new StockAllocationVO();
			
			Collection<ErrorVO> errors = null;
			Collection<RangeVO> rangeVOs =session.getAllocatedRangeVos();
			RangeVO rangeStock =new RangeVO();
			Collection<RangeVO> resetRangeVOs = new ArrayList<RangeVO>();

			String[] allocRangeFrom=allocateNewStockForm.getStockRangeFrom();
			String[] allocRangeTo=allocateNewStockForm.getStockRangeTo();
			String[] hiddenOpFlag=allocateNewStockForm.getHiddenOpFlag();
			
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
			StockRequestVO stock =new StockRequestVO();
			
			int iterator=0;
			rangeStock.setStartRange(BLANKSPACE);
			rangeStock.setEndRange(BLANKSPACE);
			rangeStock.setNumberOfDocuments(0);
			resetRangeVOs.add(rangeStock);

			Collection<RangeVO> rangesForSave = new ArrayList<RangeVO>();
			
			if(rangeVOs!=null){
				for(RangeVO rangeVO:rangeVOs){
					if(OPERATION_FLAG_DELETE.equals(hiddenOpFlag[iterator])) {
						log.log(Log.FINE,"<------  only deletetion taken place  ----> " );
						rangeVO.setOperationFlag(OPERATION_FLAG_DELETE);
						//rangesForSave.add(rangeVO);
					}
					else if(NO_OPERATION.equals(hiddenOpFlag[iterator])) {
						log.log(Log.FINE,"<------  insertion and deletetion taken place  ----> " );
					}
					else if(OPERATION_FLAG_UPDATE.equals(hiddenOpFlag[iterator])) {
						log.log(Log.FINE,"<------  update  ----> " );
						rangeVO.setOperationFlag(OPERATION_FLAG_UPDATE);
						rangeVO.setStartRange(allocRangeFrom[iterator]);
						rangeVO.setEndRange(allocRangeTo[iterator]);
						rangeVO.setNumberOfDocuments(difference(rangeVO.getStartRange(),rangeVO.getEndRange()));
						rangesForSave.add(rangeVO);    					
					}
					iterator++;
				}
			}
			iterator = 0;
			if(hiddenOpFlag != null && hiddenOpFlag.length>0){
				for(String hiddenFlag : hiddenOpFlag){
					if(OPERATION_FLAG_INSERT.equals(hiddenFlag)){
						log.log(Log.FINE,"<------  inserted and not deleted  ----> " );
						RangeVO rangeVO = new RangeVO();
						rangeVO.setOperationFlag(OPERATION_FLAG_INSERT);
						if(allocRangeFrom[iterator].length() > 0 && allocRangeTo[iterator].length() > 0){
						rangeVO.setStartRange(allocRangeFrom[iterator]);
						rangeVO.setEndRange(allocRangeTo[iterator]);
						rangeVO.setNumberOfDocuments(difference(rangeVO.getStartRange(),rangeVO.getEndRange()));
						rangesForSave.add(rangeVO);  
						}
					} 
					iterator++;
				}
			}	
			session.setAllocatedRangeVos(rangesForSave);			
			errors = validateForm(allocateNewStockForm,session);
			if (errors != null && errors.size() > 0) {
					allocateNewStockForm.setIsError("Y");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
			}
			String companyCode=logonAttributesVO.getCompanyCode();
			String stockHolderCode=upper(allocateNewStockForm.getStockHolderCode());
			String stockHolderType=allocateNewStockForm.getStockHolderType();
			String stockControlFor=allocateNewStockForm.getStockControlFor();
			String documentType=allocateNewStockForm.getDocType();
			String documentSubType=allocateNewStockForm.getSubType();
			boolean isManual=allocateNewStockForm.isManual();
			boolean isNewStockFlag=false;
			RangeFilterVO rangeFilterVO=new RangeFilterVO();
			rangeFilterVO.setCompanyCode(companyCode);
			rangeFilterVO.setStockHolderCode(stockControlFor);
			rangeFilterVO.setDocumentType(documentType);
			rangeFilterVO.setDocumentSubType(documentSubType);
			rangeFilterVO.setAirlineIdentifier(getAirlineIdentifier(allocateNewStockForm.getAwbPrefix()));
			rangeFilterVO.setManual(allocateNewStockForm.isManual());
			StockHolderVO stockHolderVO = null;
			boolean isequal = false;
			try{
				stockHolderVO =  new StockControlDefaultsDelegate().findStockHolderDetails(
						getApplicationSession().getLogonVO().getCompanyCode()
						,upper(allocateNewStockForm.getStockHolderCode()));
			}catch(BusinessDelegateException businessDelegateException){
				log.log(Log.SEVERE,"BusinessDelegateException caught..........");
			}
			if (Objects.isNull(stockHolderVO)) {
				Object[] errorObj = new Object[1];
				errorObj[0] = upper(allocateNewStockForm.getStockHolderCode());
				ErrorVO errorVO = new ErrorVO(ERROR_INVALID_STOCK_HOLDER_CODE, errorObj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(errorVO);
				invocationContext.target = SCREEN_LOAD_FAILURE;
				return;
			}			
			for(StockVO stockHold : stockHolderVO.getStock()){
				if(stockHold.getAirlineIdentifier()==(rangeFilterVO.getAirlineIdentifier())){
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
			errors=validateStockAvailabilityCheck(rangeFilterVO,allocateNewStockForm);
			if (errors != null && errors.size() > 0) {
				allocateNewStockForm.setIsError("Y");
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
		     } 
			Collection<RangeVO> ranges=session.getAllocatedRangeVos();


			stock.setCompanyCode(companyCode);
			stock.setStockHolderCode(stockHolderCode);
			stock.setStockHolderType(stockHolderType);
			stock.setDocumentType(documentType);
			stock.setDocumentSubType(documentSubType);
			stockAllocationVO.setCompanyCode(companyCode);
			stockAllocationVO.setStockHolderCode(stockHolderCode);
			stockAllocationVO.setStockControlFor(stockControlFor);
			stockAllocationVO.setDocumentType(documentType);
			stockAllocationVO.setDocumentSubType(documentSubType);
			stockAllocationVO.setManual(isManual);
			stockAllocationVO.setNewStockFlag(isNewStockFlag);
			stockAllocationVO.setTransferMode(StockAllocationVO.MODE_NORMAL);
			stockAllocationVO.setAirlineIdentifier(getAirlineIdentifier(allocateNewStockForm.getAwbPrefix()));
			stockAllocationVO.setRanges(ranges);
			stockAllocationVO.setAwbPrefix(allocateNewStockForm.getAwbPrefix().substring(0,3));
//Calendar lastUpdateDate =Calendar.getInstance(logonAttributesVO.getTimeZone());
			//stockAllocationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			stockAllocationVO.setLastUpdateUser(logonAttributesVO.getUserId());
			//Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_ALLOCATE);			
			Collection<ErrorVO> errorVos= null;
			Collection<String> stockHolderCodes=new ArrayList<String>();
			stockHolderCodes.add(stockHolderCode);
			String actualApprover = "";
			try{
				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
				log.log(Log.FINE,"...........Inside Controller.......");

				//				Added by a-2434 for temporal solution for optimistic control
	    		StockHolderVO stockHolderVo = stockControlDefaultsDelegate.findStockHolderDetails(getApplicationSession().getLogonVO().getCompanyCode(),stockHolderCode);
	    		if(stockHolderVo != null) {
	    			stockAllocationVO.setLastUpdateTime(stockHolderVo.getLastUpdateTime());
	    		}
	    		//    		Added by a-2434 for temporal solution for optimistic control ends
				
				if (stockHolderType!=null && stockHolderType.trim().length()!=0){

					stockControlDefaultsDelegate.validateStockHolderTypeCode(stock);

				}else{

					stockControlDefaultsDelegate.validateStockHolders(companyCode,stockHolderCodes);
				}
				actualApprover = stockControlDefaultsDelegate.findApproverCode
				(companyCode,stockHolderCode,documentType,documentSubType);
				if(actualApprover == null){
					ErrorVO error = null;
					error = new ErrorVO("stockcontrol.defaults.approvernotfound");
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					allocateNewStockForm.setIsError("Y");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
				}else{
					if(!upper(actualApprover).trim().equals(upper(stockControlFor.trim()))){
						ErrorVO error = null;
						error = new ErrorVO("stockcontrol.defaults.invalidapprover");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						allocateNewStockForm.setIsError("Y");
						invocationContext.addAllError(errors);
						invocationContext.target = "screenload_failure";
						return;
					}
				}
			}
			catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);
			}

			if(stockAllocationVO != null && (stockAllocationVO.getRanges() == null
					|| stockAllocationVO.getRanges().size()==0)){
				ErrorVO error = null;
				error = new ErrorVO("stockcontrol.defaults.allocatenewstock.err.plzspecifyranges");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				allocateNewStockForm.setIsError("Y");
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
			}else if(stockAllocationVO.getRanges().size()==1){
				ArrayList<RangeVO> rangeVOsArr = (ArrayList<RangeVO>)stockAllocationVO.getRanges();
				RangeVO rangeVO = rangeVOsArr.get(0);
				if((rangeVO.getStartRange() == null || rangeVO.getStartRange().trim().length()==0) ||
						(rangeVO.getEndRange() == null || rangeVO.getEndRange().trim().length()==0)){
					ErrorVO error = null;
					error = new ErrorVO("stockcontrol.defaults.allocatenewstock.err.plzspecifyranges");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					allocateNewStockForm.setIsError("Y");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
				}
			}
			//Privilege check done for ICRD-105821
			//CRQ_ ICRD-105821_Bhaskar_17Apr2015
			TransactionPrivilegeNewVO privilegeNewVO = getPrivilegeVO(TXN_COD_ALLOCATE_STOCK);
			if(privilegeNewVO!=null && PRVLG_RUL_STK_HLDR.equals(privilegeNewVO.getPrivilegeCode())&&
					PRVLG_LVL_STKHLD.equals(privilegeNewVO.getLevelType())&&
					privilegeNewVO.getTypeValue()!=null && 
					privilegeNewVO.getTypeValue().trim().length()>0){
				String[] typeValues = privilegeNewVO.getTypeValue().split(",");
				List<String> privilegedStkHldrList = Arrays.asList(typeValues);
				if(!privilegedStkHldrList.contains(stockAllocationVO.getStockHolderCode())&&
						!privilegedStkHldrList.contains(actualApprover)){
					ErrorVO error =  new ErrorVO("stockcontrol.defaults.allocatenotauthorized");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					allocateNewStockForm.setIsError("Y");
					invocationContext.addAllError(errors);
					invocationContext.target = "screenload_failure";
					return;
				}
			}
			
			if (errorVos != null && errorVos.size() > 0) {
				allocateNewStockForm.setIsError("Y");
				invocationContext.addAllError(errorVos);
				invocationContext.target = "screenload_failure";
				return;
			}
			try{
					StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
					log.log(Log.FINE,"...........Inside Allocate New Stock Controller.......");
/*//					Added by Aswin Starts	
					String privileageCode=stockControlDefaultsDelegate.findPrivileage(allocateNewStockForm.getStockControlFor());
					boolean hasPrivilege = false;
					try{
						
						log.log(Log.FINE,"\n\n\n\n-----------------hasPrivilege1111-----------"+hasPrivilege);
						
						log.log(Log.FINE,"\n\n\n\n-----------------PrivilegeCode-----------"+privileageCode);
						SecurityAgent agent = SecurityAgent.getInstance();
						hasPrivilege = agent.checkPrivilegeForAction(privileageCode);
						log.log(Log.FINE,"\n\n\n\n-----------------hasPrivilege2222-----------"+hasPrivilege);
						
					}catch(SystemException ex){
						log.log(Log.FINE,"\n\n\n\n-----------------hasPrivilege3333 -----------"+hasPrivilege);
					}if(hasPrivilege){
						
						log.log(Log.FINE,"\n\n\n\n-----------------Inside Allocate Stock-----------");
					stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
					}else{
						
						log.log(Log.FINE,"\n\n\n\n-----------------Inside Else Loop of Allocate Stock-----------");
						
						invocationContext.addError(new ErrorVO("stockcontrol.defaults.donthaveprivileage"));
						
				 		
					}
					// Added by Aswin Ends
					 * 
*/				    //Added as part of ICRD-79163
					stockAllocationVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));	
					session.setStockAllocationVO(stockAllocationVO);					
					stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
					
				}
			catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);
				log.log(Log.FINE, "..........errorVos --allocateStock",
						errorVos);
				for(ErrorVO errorVo:errorVos){
					log.log(Log.FINE, " ErrorCode ", errorVo.getErrorCode());
				}
			}
			if (errorVos != null && errorVos.size() > 0) {
				allocateNewStockForm.setIsError("Y");
				invocationContext.addAllError(errorVos);
				invocationContext.target = "screenload_failure";
				return;
			}
			if(errorVos==null || errorVos.size()==0){
				//Added as part of ICRD-46860
				allocateNewStockForm.setStatusFlag("success");
				ErrorVO error = null;
				Object[] obj = { "Save" };
				error = new ErrorVO("stockcontrol.defaults.savesuccessful", obj);// Saved Successfully
				error.setErrorDisplayType(ErrorDisplayType.STATUS);
				errors.add(error);
				invocationContext.addAllError(errors);

			}

			allocateNewStockForm.setStockHolderCode("");
			allocateNewStockForm.setStockHolderType("");
			allocateNewStockForm.setStockControlFor("");
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix starts
			allocateNewStockForm.setDocType(AWB);
			allocateNewStockForm.setSubType(S);
			//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix ends
			allocateNewStockForm.setRangeFrom("");
			allocateNewStockForm.setRangeTo("");
			allocateNewStockForm.setNumberOfDocs("");
			allocateNewStockForm.setManual(false);
			allocateNewStockForm.setAllocatedTotalnoofDocs("");
			//Commented as part of ICRD-46860
			//Modified for BUG_ICRD-81938 starts
			session.setPrintReportRangeVos(session.getAllocatedRangeVos());
			session.setAllocatedRangeVos(resetRangeVOs);
		    //Modified for BUG_ICRD-81938 Ends
			session.setAvailableRangeVos(null);
			allocateNewStockForm.setReportGenerateMode("ReportGenerateModeOn");

		log.exiting("SaveCommand","execute");
		invocationContext.target = "screenload_success";
    }
	/**
	 * Validate stock availability check.
	 * this method check whether the stock is present in the stock holder with respective to the manual flag
	 * @param rangeFilterVO the range filter vo
	 * @param allocateNewStockForm the allocate new stock form
	 * @return the collection
	 */
	private Collection<ErrorVO> validateStockAvailabilityCheck(RangeFilterVO rangeFilterVO,
			AllocateNewStockForm allocateNewStockForm) {
		Collection<RangeVO> rangeVOs = new ArrayList<RangeVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		int index = 0;
		for (String numberOfDocs : allocateNewStockForm.getNoOfDocs()) {
			if (Long.parseLong(numberOfDocs) != 0 && !RangeFilterVO.OPERATION_FLAG_DELETE.equals(allocateNewStockForm.getHiddenOpFlag()[index])) {
				rangeFilterVO.setStartRange(allocateNewStockForm
						.getStockRangeFrom()[index]);
				rangeFilterVO.setEndRange(allocateNewStockForm
						.getStockRangeTo()[index]);
				rangeFilterVO.setNumberOfDocuments(numberOfDocs);
				try {
					StockControlDefaultsDelegate stockControlDefaultsDelegate = new StockControlDefaultsDelegate();
					rangeVOs = stockControlDefaultsDelegate
							.findRanges(rangeFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				ErrorVO error = null;
				if (rangeVOs.size() == 0) {
					error = new ErrorVO("stockcontrol.defaults.invalidrange");
					// errors.add(error);
				} else {
					long numDocs = 0;
					for (RangeVO rangeVo : rangeVOs) {
						numDocs = numDocs + rangeVo.getNumberOfDocuments();
					}
					if (Long.parseLong(numberOfDocs) != numDocs) {// if the given range has both manual and neutral then number of docs comes in equal
						error = new ErrorVO(
								"stockcontrol.defaults.invalidrange");
					}
				}
				if (error != null) {
					errors.add(error);
					break;
				}
			}
			index++;
		}
		return errors;
    }

    /**
     * @author A-2589
     * @param awbPrefix
     * @return airlineIdentifier
     * 
     */
    private int getAirlineIdentifier(String awbPrefix) {
    	int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			String[] tokens=awbPrefix.split("-");
			return (tokens!=null && tokens.length>2)?Integer.parseInt(tokens[2]):ownAirlineIdentifier;
		}
		return ownAirlineIdentifier;
	}

	/** To get the numeric value of the string
	 *
	 * @param range
	 * @return Numeric value
	 */
	private  long findLong(String range){
			log.log(Log.FINE,"...........Entering ascii conversion.......");
			char[] sArray=range.toCharArray();
			long base=1;
			long sNumber=0;
			for(int i=sArray.length-1;i>=0;i--){
				sNumber+=base*calculateBase(sArray[i]);
				int count=sArray[i];
				if (count>57) {
					base*=26;
				} else {
					base*=10;
				}
			}
			return sNumber;
	}

	private  long calculateBase(char str){
			long formatStr=str;
			long base=0;
			try{
				base=Integer.parseInt(""+str);
			}catch(NumberFormatException numberFormatException){
				base=formatStr-65;
			}
			return base;
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

    private Collection<ErrorVO> validateForm(AllocateNewStockForm form,
				AllocateNewStockSession session) {

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			boolean isCheckFromNull=false;
			boolean isCheckToNull=false;
			boolean isCheckDuplicate=false;
			boolean isCheckFlow=false;
			boolean isCheckTo=false;
			boolean isCheckOverlap=false;
			boolean isCheckRange=false;
			String[] allocRangeFrom=form.getStockRangeFrom();
			String[] allocRangeTo=form.getStockRangeTo();
			String[] hiddenOpFlag=form.getHiddenOpFlag();
			for(int i=0;i<(allocRangeFrom.length-1);i++){
				if (!hiddenOpFlag[i].equalsIgnoreCase(OPERATION_FLAG_DELETE)){
				
					for(int j=i+1;j<allocRangeFrom.length;j++){
				
						if (!hiddenOpFlag[j].equalsIgnoreCase(OPERATION_FLAG_DELETE)){
						log.log(Log.FINE, "<------  hiddenOpFlag[i]  ----> ",
								hiddenOpFlag, i);
					log.log(Log.FINE, "<------  hiddenOpFlag[j]  ----> ",
							hiddenOpFlag, j);
					if("".equals(allocRangeFrom[i]) || "".equals(allocRangeTo[i]) || "".equals(allocRangeFrom[j]) || "".equals(allocRangeTo[j])) {
						break;
					} else{

						if(findLong(allocRangeFrom[i])==findLong(allocRangeFrom[j])
							&&	findLong(allocRangeTo[i])==findLong(allocRangeTo[j])){

								isCheckDuplicate=true;
								break;
						}

						if(findLong(allocRangeTo[i])==findLong(allocRangeFrom[j])){

								isCheckFlow=true;
								break;
						}

						if(findLong(allocRangeTo[i])==findLong(allocRangeTo[j])){

								isCheckTo=true;
								break;
						}
						/*if(findLong(allocRangeFrom[i])<=findLong(allocRangeFrom[j])
								&& findLong(allocRangeTo[i])>findLong(allocRangeTo[j])){

								checkOverlap=true;
								break;
						}*/

						if(	(
					        	findLong(allocRangeFrom[i])==
					        	findLong(allocRangeFrom[j])
					        	) || ((
					        	findLong(allocRangeFrom[i])<
					        	findLong(allocRangeFrom[j])
					        	)
					           &&
					           (
					        	findLong(allocRangeTo[i])>
					        	findLong(allocRangeFrom[j])
					        	))
					           ||((
					    		   findLong(allocRangeTo[j])<
					    		   findLong(allocRangeTo[i])
					    		   )
					           &&(
					    		   findLong(allocRangeFrom[i])<
					    		   findLong(allocRangeTo[j]))
					           	)
					        	){

							   isCheckOverlap=true;
					           break;
					         }

					}
				}


				}
			}
			}

			for(int i=0;i<allocRangeFrom.length;i++){

				if(!((allocRangeFrom[i]==null || allocRangeFrom[i].trim().length()==0) &&
						(allocRangeTo[i]==null || allocRangeTo[i].trim().length()==0))){
					if(allocRangeFrom[i]==null || allocRangeFrom[i].trim().length()==0){
						isCheckFromNull=true;
						break;
					}else if(allocRangeTo[i]==null || allocRangeTo[i].trim().length()==0){
						isCheckToNull=true;
						break;
					}

				}
				if(findLong(allocRangeFrom[i])>findLong(allocRangeTo[i])){

						isCheckRange=true;
						break;
				}
			}

			if ("".equals(form.getDocType())||form.getDocType().length()==0){

						Object[] obj = { "Document Type" };
						error = new ErrorVO("stockcontrol.defaults.selectdoctype", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}


			if ("".equals(form.getSubType())||form.getSubType().length()==0){

						Object[] obj = { "Document Sub Type" };
						error = new ErrorVO("stockcontrol.defaults.selectdocsubtype", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}

			if ("".equals(form.getStockHolderCode())||form.getStockHolderCode().length()==0){

						Object[] obj = { "StockHolderCode" };
						error = new ErrorVO("stockcontrol.defaults.plsenterstkhldval", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
			}

			if(isCheckFromNull){
						Object[] obj = { "Allocated-Range-From" };
						error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngfrmvalintab", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}

			if(isCheckToNull){
				Object[] obj = { "Allocated-Range-To" };
				error = new ErrorVO("stockcontrol.defaults.allocatenewstock.err.plzenterrangeto", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				return errors;
			}

			if(isCheckDuplicate){
						Object[] obj = { "Allocated-Range-Duplicate" };
						error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}

			if(isCheckFlow){
						Object[] obj = { "Allocated-Range-Flow" };
						error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}

			if(isCheckTo){
						Object[] obj = { "Allocated-Range-To" };
						error = new ErrorVO("stockcontrol.defaults.endrngcannotbesame", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}

			if(isCheckOverlap){
						Object[] obj = { "Allocated-Range-Overlap" };
						error = new ErrorVO("stockcontrol.defaults.overlapingrngcannotbeentered", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}
			if(isCheckRange){
						Object[] obj = { "Range From Exceeds" };
						error = new ErrorVO("SKCM_099", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						return errors;
			}




		return errors;
	}

	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
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
