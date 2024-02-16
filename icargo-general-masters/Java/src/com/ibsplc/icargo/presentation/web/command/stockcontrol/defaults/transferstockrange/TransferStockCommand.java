/*
 * TransferStockCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.transferstockrange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockDepleteFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.TransferStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.TransferStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 *
 */
public class TransferStockCommand extends BaseCommand {
	
	private static final String MODE_RANGES = "ranges";
	
	private static final String MODE_NUMBER_OF_DOCUMENTS  = "numberOfDocuments";
	private static final String INVOICE="INVOICE";

	private Log log = LogFactory.getLogger("STOCKCONTROL");
	
    private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK"; 
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	
	private static final int RANGE_LENGTH = 7;
	
	private static final String ZERO = "0";
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		TransferStockForm form = (TransferStockForm) invocationContext.screenModel;

		TransferStockSession session = getScreenSession("stockcontrol.defaults",
		  								"stockcontrol.defaults.transferstockrange");
		MonitorStockSession session2 = getScreenSession("stockcontrol.defaults",
				"stockcontrol.defaults.monitorstock");
		StockAllocationVO stockAllocationVO = new StockAllocationVO();
 		Collection<ErrorVO> errors = null;
 		Collection<RangeVO> colRangeVO = new ArrayList<RangeVO>();	
 		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
 		String companyCode=logonAttributesVO.getCompanyCode();
 		Collection<ErrorVO> errorVos = new ArrayList<ErrorVO>();
		errors = validateForm(form,session); 
		if (errors != null && errors.size() > 0) {
			 invocationContext.addAllError(errors);
			 invocationContext.target = "screenload_success";
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
				findApproverCode(companyCode,form.getTransferFrom(),form.getDocType(),
						form.getSubType());
                  }catch (BusinessDelegateException businessDelegateException) {				
               	   errors.addAll(handleDelegateException(businessDelegateException));
                  	}
             //Approver code end
			if(!privilegedStkHldrList.contains(form.getTransferFrom())&&
					!privilegedStkHldrList.contains(approver)){
				ErrorVO error =  new ErrorVO("stockcontrol.defaults.allocatenotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);				
				invocationContext.target = "screenload_success";
				return;
			}
		}
 		if(form.getTransferMode() != null && form.getTransferMode().equalsIgnoreCase(MODE_RANGES)){
 	 		String[] rangeFrom=form.getRangeFrom();
 			String[] rangeTo=form.getRangeTo();
 			String[] noOfDocs=form.getNoofDocs();

 			colRangeVO = session.getCollectionRangeVO();
 			int count=0;
 			for(RangeVO range:colRangeVO){

 				range.setStartRange(rangeFrom[count].toUpperCase());
 				range.setEndRange(rangeTo[count].toUpperCase());
 				if(noOfDocs[count] != null && noOfDocs[count].trim().length()>0) {
 					//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also
					range.setNumberOfDocuments(Long.valueOf(noOfDocs[count]));
				}
 				count++;

 			} 			
 		}else{
 			RangeVO rangeVO = new RangeVO();
 			rangeVO.setStartRange(form.getModeRangeFrom());
 			if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length()>0) {
 				//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also
				rangeVO.setNumberOfDocuments(Long.valueOf(form.getModeNumberOfDocuments()));
			}
 			/*
 			 * Call for finding the stock ranges available for a stock holder
 			 */
 			RangeFilterVO rangeFilterVO=new RangeFilterVO(); 	
 			Collection<RangeVO> rangeVOs=null;
 			rangeFilterVO.setCompanyCode(companyCode);
 			rangeFilterVO.setStockHolderCode(form.getTransferFrom());
 			rangeFilterVO.setDocumentType(form.getDocType());
 			rangeFilterVO.setDocumentSubType(form.getSubType());
 			rangeFilterVO.setStartRange(rangeVO.getStartRange());
 			rangeFilterVO.setEndRange(rangeVO.getEndRange());
 			rangeFilterVO.setNumberOfDocuments(form.getModeNumberOfDocuments()); 			
 			//Added as part of ICRD-88160
 			if(form.isManual()){
 				rangeFilterVO.setManual(form.isManual());
 			}
 			rangeFilterVO.setAirlineIdentifier(getAirlineIdentifier(session.getPartnerAirline())); 
 			 try{
 				StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate(); 				
 				log.log(Log.FINE, "...rangeFilterVO.........", rangeFilterVO);
 				rangeVOs = stockControlDefaultsDelegate.findRanges(rangeFilterVO);
 				
 			} catch(BusinessDelegateException businessDelegateException){ 			
 				errorVos = handleDelegateException(businessDelegateException);
 			} 
 			
 			if(rangeVOs != null && rangeVOs.size()> 0){ 
 				/**
 				 * Commented as part of ICRD-84747 by A-6386
 				 * The behaviour from transfer stock is implemented same as that of allocate stock screen
 				 */
 				//Added By A-5247 for BUG_ICRD-83834 starts
 				/*boolean isInRange=false;
 				long startRange=0L;
 				long endRange=0L;
 				if(INVOICE.equals(form.getDocType())){
 					 String alpha = getSeparatedValues("[0-9]",rangeVO.getStartRange()); 
 					 startRange = Long.parseLong(getSeparatedValues("[a-zA-Z]",rangeVO.getStartRange())); 
 					 endRange=startRange+Long.parseLong(form.getModeNumberOfDocuments())-1;
 					for(RangeVO transferRangeVO:rangeVOs){
 	 					
 	 					String transferStartAlpha=getSeparatedValues("[0-9]",transferRangeVO.getStartRange()); 
 	 					String transferEndAlpha=getSeparatedValues("[0-9]",transferRangeVO.getEndRange()); 
 						if(alpha.equals(transferStartAlpha) && alpha.equals(transferEndAlpha)){
 							long transferStartRange=Long.parseLong(getSeparatedValues("[a-zA-Z]",transferRangeVO.getStartRange())); 
 							long transferEndRange=Long.parseLong(getSeparatedValues("[a-zA-Z]",transferRangeVO.getEndRange())); 
 							if (startRange >= transferStartRange
 									&& endRange <= transferEndRange) {
 								isInRange=true;
 							}
 						}
 	 				}
 				}else{
 				 startRange=Long.parseLong(rangeVO.getStartRange());					
					
					if(rangeVO.getEndRange()!=null && rangeVO.getEndRange().trim().length() !=0){
					  endRange=Long.parseLong(rangeVO.getEndRange());
					}else{
						endRange=startRange+Long.parseLong(form.getModeNumberOfDocuments())-1;
					}
 				for(RangeVO transferRangeVO:rangeVOs){
 					long transferStartRange=Long.parseLong(transferRangeVO.getStartRange());
 					long transferEndRange=Long.parseLong(transferRangeVO.getEndRange());
					if (startRange >= transferStartRange
							&& endRange <= transferEndRange) {
						isInRange=true;
					}
 				}
 				}
 				if(isInRange){*/
 					colRangeVO.addAll(rangeVOs);
 				/*}else{
 					ErrorVO error = new ErrorVO("stockcontrol.defaults.invalidrange");		          
 	 		          error.setErrorDisplayType(ErrorDisplayType.ERROR);
 	 		          errorVos.add(error); 		
 				}//Added By A-5247 for BUG_ICRD-83834 Ends
 			} */
 					
 			} else {
 				  ErrorVO error = new ErrorVO("stockcontrol.defaults.invalidrange");		          
 		          error.setErrorDisplayType(ErrorDisplayType.ERROR);
 		          errorVos.add(error); 		          
 				
 			}
 			
 			if(errorVos!=null && errorVos.size()>0){
 				invocationContext.addAllError(errorVos);
 				invocationContext.target = "screenload_success";
 				return;
 			}
 			
 		}
 				
		Collection<String> codes = new ArrayList<String>();
		codes.add(upper(form.getTransferTo()));
		Collection<String> stockHolderCodes = new ArrayList<String>();
		stockHolderCodes.add(upper(form.getTransferTo()));
		stockHolderCodes.add(form.getTransferFrom());
		ArrayList<StockHolderPriorityVO> stockHolderPriorityVOList = null;
		try{
			 log.log(Log.FINE,"\n\n<-----------Calling validateStockHolders-------------------------------->");
			new StockControlDefaultsDelegate().validateStockHolders(
					getApplicationSession().getLogonVO().getCompanyCode(),codes);
			 log.log(Log.FINE,"\n\n<-----------Calling findPriorities-------------------------------->");
			 stockHolderPriorityVOList = (ArrayList<StockHolderPriorityVO>)
					new StockControlDefaultsDelegate().
						findPriorities(getApplicationSession().getLogonVO().getCompanyCode(),
								stockHolderCodes);

		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
		}
		if(errorVos!=null && errorVos.size()>0){
			invocationContext.addAllError(errorVos);
			invocationContext.target = "screenload_success";
			return;
		}
		if(stockHolderPriorityVOList!=null && stockHolderPriorityVOList.size()>0){
			StockHolderPriorityVO  transferTo =
					(StockHolderPriorityVO)stockHolderPriorityVOList.get(0);
			StockHolderPriorityVO  transferFrom =
				(StockHolderPriorityVO)stockHolderPriorityVOList.get(1);
			if(!( transferTo.getPriority() == transferFrom.getPriority()) ){
				 log.log(Log.FINE,"<-------------Priority Not equal-------------------------------->");
				 ErrorVO error = null;
				 errors = new ArrayList<ErrorVO>();
				 Object[] obj = { "Priority" };
				 error = new ErrorVO("stockcontrol.defaults.prioritymismatch", obj);
				 error.setErrorDisplayType(ErrorDisplayType.ERROR);
				 errors.add(error);
				 invocationContext.addAllError(errors);
				 invocationContext.target = "screenload_success";
				 return;
			}
		}

		stockAllocationVO.setRanges(colRangeVO);
		stockAllocationVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		stockAllocationVO.setDocumentType(form.getDocType());
		stockAllocationVO.setDocumentSubType(form.getSubType());
		stockAllocationVO.setStockHolderCode(upper(form.getTransferTo()));
		stockAllocationVO.setStockControlFor(form.getTransferFrom());
		stockAllocationVO.setRemarks(form.getRemarks());
		stockAllocationVO.setTransferMode(StockAllocationVO.MODE_TRANSFER);
		
		stockAllocationVO.setExecutionDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		//log.log(Log.FINE,"The Manual flag isssss>******"+session.getStockRangeVO().isManual());
		//Added by A-2882 for bug 108953 starts
		stockAllocationVO.setManual(session.getStockFilterVO().isManual());
		//Added by A-2882 for bug 108953 ends
        //Added by A-2881 for ICRD-3082
		stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_TRANSFER);
		
		stockAllocationVO.setAirlineIdentifier(getAirlineIdentifier(session.getPartnerAirline()));
		stockAllocationVO.setAwbPrefix(session2.getAwbPrefix().substring(0,3));
		StockDepleteFilterVO stockDepleteFilterVO=new StockDepleteFilterVO();
	 	  stockDepleteFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
	 	  stockDepleteFilterVO.setAirlineId(getAirlineIdentifier(session.getPartnerAirline()));
	 	  //Added as part of ICRD-46860 starts
	 	  session.setStockAllocationVO(stockAllocationVO);
	 	  //Added as part of ICRD-46860 ends
	 	  StockControlDefaultsDelegate  stockControlDefaultsDelegate=new  StockControlDefaultsDelegate(); 
	 	  
	    try{
	    	 log
					.log(
							Log.FINE,
							"<-------------stockAllocationVO-------------------------------->",
							stockAllocationVO);
	    	 //Commented by A-5153
			//stockControlDefaultsDelegate.autoStockDeplete(stockDepleteFilterVO);
	    	 stockControlDefaultsDelegate.allocateStock(stockAllocationVO);
		}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
				errorVos = handleDelegateException(businessDelegateException);
		}

		if(errorVos!=null && errorVos.size()>0){
			invocationContext.addAllError(errorVos);
			invocationContext.target = "screenload_success";
			return;
		}
		form.setMode("Y");

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
	 * method to convert to upper case
	 * @param input
	 *
	 */
	private String upper(String input){

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return "";
		}
	}


		/** To get the numeric value of the string
		 *
		 * @param range
		 * @return Numeric value
		 */
		private  long findLong(String range){
			char[] sArray=range.toCharArray();
			long base=1;
			long sNumber=0;
			for(int i=sArray.length-1;i>=0;i--){
				sNumber+=base*calculateBase(sArray[i]);
				int num=sArray[i];
				if (num>57){
					base*=26;
				}
				else{
					base*=10;
				}
			}
			return sNumber;
		}

		private  long calculateBase(char yChar){
			long xLong=yChar;
			long base=0;
			try{
				base=Integer.parseInt(""+yChar);
			}catch(NumberFormatException numberFormatException){
				base=xLong-65;
			}
			return base;
	}

   private Collection<ErrorVO> validateForm(TransferStockForm form,
       TransferStockSession session) {

      Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
      ErrorVO error = null;
      boolean isFromNull=false;
      boolean isDuplicate=false;
      boolean isContinuous=false;
      boolean isEndRange=false;
      boolean isOverlap=false;
      String[] rangeFrom=form.getRangeFrom();
      String[] rangeTo=form.getRangeTo();
      int noOfDocs = 0;
      if(form.getTransferMode() != null && form.getTransferMode().equalsIgnoreCase(MODE_NUMBER_OF_DOCUMENTS)) {
		if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length() > 0){
			noOfDocs = Integer.parseInt(form.getModeNumberOfDocuments());
		}
      }
     
      if(form.getTransferMode() != null && form.getTransferMode().equalsIgnoreCase(MODE_RANGES)){
          for(int i=0;i<(rangeFrom.length-1);i++){
              for(int j=i+1;j<rangeTo.length;j++){
               if("".equals(rangeFrom[i]) || "".equals(rangeTo[i]) || "".equals(rangeFrom[j]) || "".equals(rangeTo[j])) {
				break;
			} else{

                if(findLong(rangeFrom[i])==findLong(rangeFrom[j])
                 && findLong(rangeTo[i])==findLong(rangeTo[j])){

                  isDuplicate=true;
                  break;
                }

                if(findLong(rangeTo[i])==findLong(rangeFrom[j])){

                  isContinuous=true;
                  break;
                }

                if(findLong(rangeTo[i])==findLong(rangeTo[j])){

                  isEndRange=true;
                  break;
                }
       		 if(
       				((
       				findLong(rangeFrom[i])<
       				findLong(rangeFrom[j])
       				)
       				&&
       				(
       				findLong(rangeTo[i])>
       				findLong(rangeFrom[j])
       				))
       				||((
       				   findLong(rangeTo[j])<
       				   findLong(rangeTo[i])
       				   )
       				&&(
       				   findLong(rangeFrom[i])<
       				   findLong(rangeTo[j]))
       					)
               	){

                  isOverlap=true;
                  break;
                }
               }


              }
             }

             for(int i=0;i<rangeFrom.length;i++){

              if("".equals(rangeFrom[i])){
               isFromNull=true;
               break;
              }
             }
             for(int i=0;i<rangeTo.length;i++){

       		 if("".equals(rangeTo[i])){
       		  isFromNull=true;
       		  break;
       		 }
             }

             if(isFromNull){
                Object[] obj = { "Mandatory" };
                error = new ErrorVO("stockcontrol.defaults.plsenterallocatedrngfrmvalintab", obj);
                error.setErrorDisplayType(ErrorDisplayType.ERROR);
                errors.add(error);
             }

             if(isDuplicate){
                Object[] obj = { "Duplicate" };
                error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
                error.setErrorDisplayType(ErrorDisplayType.ERROR);
                errors.add(error);
             }

             if(isContinuous){
                Object[] obj = { "Continuous" };
                error = new ErrorVO("stockcontrol.defaults.duplirngcannotbeentered", obj);
                error.setErrorDisplayType(ErrorDisplayType.ERROR);
                errors.add(error);
             }

             if(isEndRange){
                Object[] obj = { "Same End Range" };
                error = new ErrorVO("stockcontrol.defaults.endrngcannotbesame", obj);
                error.setErrorDisplayType(ErrorDisplayType.ERROR);
                errors.add(error);
             }

             if(isOverlap){
                Object[] obj = { "Overlap" };
                error = new ErrorVO("stockcontrol.defaults.overlapingrngcannotbeentered", obj);
                error.setErrorDisplayType(ErrorDisplayType.ERROR);
                errors.add(error);
             }    	  
      }else{
    	  /*
    	   *  commented by A-7740 as a part of ICRD-225106
    	   *  if(form.getModeRangeFrom() != null && form.getModeRangeFrom().trim().length()==0){
              Object[] obj = { "RangeFrom" };
              error = new ErrorVO("stockcontrol.defaults.tranferstock.err.plzenterrangefrom", obj);
              error.setErrorDisplayType(ErrorDisplayType.ERROR);
              errors.add(error);    		  
    	  }*/
    	  if(form.getModeNumberOfDocuments() != null && form.getModeNumberOfDocuments().trim().length()==0
    			  || noOfDocs == 0){
              Object[] obj = { "NumberOfDocuments" };
              error = new ErrorVO("stockcontrol.defaults.tranferstock.err.plzenternoofdocs", obj);
              error.setErrorDisplayType(ErrorDisplayType.ERROR);
              errors.add(error);    		  
    	  }
      }
      if(form.getTransferFrom().equalsIgnoreCase(form.getTransferTo())){
    	  Object[] obj = { "Same Transfer From - Transfer To" };
          error = new ErrorVO("stockcontrol.defaults.transferfromtransfertocannotbesame", obj);
          error.setErrorDisplayType(ErrorDisplayType.ERROR);
          errors.add(error);
	  }
      form.setManual(session.getStockFilterVO().isManual());
      if(errors.size()==0 && rangeFrom!=null){
    	  populateAvailableRangesForValidation(session);
			StockRangeVO stockRangeVO = session.getStockRangeVO();
			if(stockRangeVO!=null){
				for (int i = 0; i < rangeFrom.length; i++) {
					Collection<RangeVO> rangevos = stockRangeVO.getAvailableRanges();
					for(RangeVO rangeVO : rangevos){
						if(rangeVO.getMasterDocumentNumbers()!=null){
							for(String docnum : rangeVO.getMasterDocumentNumbers()){
							 if(rangeFrom[i].length()>0){	
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
		}

     return errors;
    }
   /**
    * @author A-4777
    * @param regexp
    * @param value
    * @return
    */
   private String getSeparatedValues(String regexp,String value) {
				
		return value.replaceAll(regexp, "");		

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
	 * @param sessiontransfer
	 */
	private void populateAvailableRangesForValidation(TransferStockSession sessiontransfer) {
		StockRangeVO stkRangeVO = findAvailableRanges(sessiontransfer.getStockFilterVO());
		if(stkRangeVO != null && stkRangeVO.getAvailableRanges() != null  && 
				stkRangeVO.getAvailableRanges().size() > 0) {
			int startRangeCount = 0;
			int endRangeCount = 0;
			for(RangeVO rangeVo : stkRangeVO.getAvailableRanges()){
				startRangeCount =0;
				endRangeCount = 0;
				if(DocumentValidationVO.DOC_TYP_AWB.equals(sessiontransfer.getStockFilterVO().getDocumentType())) {
					if(rangeVo.getStartRange().length() < RANGE_LENGTH){
						startRangeCount = RANGE_LENGTH - rangeVo.getStartRange().length();
						for(int i = 0 ; i < startRangeCount ; i++){
							rangeVo.setStartRange(ZERO + rangeVo.getStartRange());
						}
					
					}
					if(rangeVo.getEndRange().length() < RANGE_LENGTH ){
						endRangeCount = RANGE_LENGTH - rangeVo.getEndRange().length();
						for(int i = 0 ; i < endRangeCount ; i++){
							rangeVo.setEndRange(ZERO + rangeVo.getEndRange());
						}
					}
				}
			}
			if (sessiontransfer.getStockRangeVO() != null) {
				sessiontransfer.getStockRangeVO().setAvailableRanges(stkRangeVO.getAvailableRanges());
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
