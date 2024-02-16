/*
 * SaveCommand.java Created on Aug 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.createstock;


import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.CreateStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.CreateStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1952
 *
 */
public class SaveCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");

	private static final String STOCKHOLDER_CODE="HQ";
	//Added by A-1927 @ NCA on 01-Aug-2007 for NCA Bug Fix starts
	private static final String AWB = "AWB";
	private static final String S = "S";
	private static final String NO_OPERATION = "NOOP";
	//Added by A-1927 @ NCA on 01-Aug-2007 for NCA Bug Fix ends
	/**
	 * The execute method in BaseCommand
	 * @author A-1952
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("SaveCommand","execute");
		Collection<ErrorVO> errorVos= null;
		Collection<ErrorVO> errors = null;
		CreateStockForm form = (CreateStockForm)invocationContext.screenModel;
		CreateStockSession session =(CreateStockSession) getScreenSession( "stockcontrol.defaults",
		                                                     "stockcontrol.defaults.createstock");

       	String[] rangeFrom=form.getRangeFrom();  
		String[] rangeTo=form.getRangeTo();
		String[] noOfDocs=form.getNoOfDocs();
		String[] hiddenOpFlag=form.getHiddenOpFlag();

//	--------	 Added by a2434 for doc format validation --------------
		DocumentTypeDelegate documentTypeDelegate = new DocumentTypeDelegate();
		DocumentVO documentVO = new DocumentVO();
		Collection<SharedRangeVO> newSharedRangeVOs = new ArrayList<SharedRangeVO>();
		Collection<RangeVO> sessionRangeVO = new ArrayList<RangeVO>();
		Collection<RangeVO> colRangeVO = session.getCollectionRangeVO();
		int count=0;
		for(String hiddenFlag : hiddenOpFlag){
			if(OPERATION_FLAG_INSERT.equals(hiddenFlag)){
				SharedRangeVO sharedRangeVO = new SharedRangeVO();
				RangeVO rangeVO = new RangeVO();
				sharedRangeVO.setFromrange(rangeFrom[count]); 
				sharedRangeVO.setToRange(rangeTo[count]);
				newSharedRangeVOs.add(sharedRangeVO);
				rangeVO.setStartRange(rangeFrom[count]);
				rangeVO.setEndRange(rangeTo[count]);
				rangeVO.setOperationFlag(OPERATION_FLAG_INSERT);
				sessionRangeVO.add(rangeVO);
			}
			count++;
		}
		if(sessionRangeVO!=null && sessionRangeVO.size()>0){
			session.setCollectionRangeVO(sessionRangeVO);
		}
		documentVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		documentVO.setDocumentSubType(form.getSubType());
		documentVO.setDocumentType(form.getDocType());
		documentVO.setRange(newSharedRangeVOs);
		log.log(Log.ALL, "", documentVO);
	try{	
	    documentTypeDelegate.validateRange(documentVO);                   	
//	-----------	 Added by a2434 for doc format validation ends----------------	
		Collection<RangeVO> newRangeVOs = new ArrayList<RangeVO>();
		
		count=0;
		//Modified for new implementation of Add/Delete link starts
		if(colRangeVO != null && colRangeVO.size()>0){
			for(RangeVO rangeVO:colRangeVO){
				if(OPERATION_FLAG_DELETE.equals(hiddenOpFlag[count])) {
					log.log(Log.FINE,"<-------  only deletetion taken place  ----> " );
					rangeVO.setOperationFlag(OPERATION_FLAG_DELETE);
					//newRangeVOs.add(rangeVO);
				}
				else if(NO_OPERATION.equals(hiddenOpFlag[count])) {
					log.log(Log.FINE,"<------  insertion and deletetion taken place  ----> " );
				}
				else if(OPERATION_FLAG_UPDATE.equals(hiddenOpFlag[count])) {
					log.log(Log.FINE,"<------  update  ----> " );
					rangeVO.setOperationFlag(OPERATION_FLAG_UPDATE);
					rangeVO.setStartRange(rangeFrom[count]);
					rangeVO.setEndRange(rangeTo[count]);
					if(!"".equals(String.valueOf(noOfDocs[count]))){						
						//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also		 
						rangeVO.setNumberOfDocuments(Long.valueOf(noOfDocs[count]));
					}
					newRangeVOs.add(rangeVO);
				}
				count++;
			}
		}
		count = 0;
		if(hiddenOpFlag != null && hiddenOpFlag.length>0){ 
			for(String hiddenFlag : hiddenOpFlag){
				if(OPERATION_FLAG_INSERT.equals(hiddenFlag)){
					log.log(Log.FINE,"<------  inserted and not deleted  ----> " );
					RangeVO rangeVO = new RangeVO();
					rangeVO.setOperationFlag(OPERATION_FLAG_INSERT);
					rangeVO.setStartRange(rangeFrom[count]);
					rangeVO.setAsciiStartRange(findLong(rangeVO.getStartRange()));//Added by A-7924 as part of ICRD-272427
					rangeVO.setEndRange(rangeTo[count]);
					rangeVO.setAsciiEndRange(findLong(rangeVO.getEndRange()));//Added by A-7924 as part of ICRD-272427
					if(String.valueOf(noOfDocs[count]).trim().length()>0){
						//Edited as part of ICRD-46860 for accepting ALPHANUMERIC formats also
						rangeVO.setNumberOfDocuments(Long.valueOf(noOfDocs[count]));
					}
					newRangeVOs.add(rangeVO);
				}
				count++;
			}
		}
		log.log(Log.FINE,
				"\n\n\n\n--------------------newRangeVOs-------------------",
				newRangeVOs);
		session.setCollectionRangeVO(newRangeVOs);
		//Modified for new implementation of Add/Delete link ends

        StockControlDefaultsDelegate stockControlDefaultsDelegate= new StockControlDefaultsDelegate();
        StockAllocationVO stockAllocationVO=new StockAllocationVO();
		errors = validateForm(form,session);
		if (errors != null && errors.size() > 0) {
			 invocationContext.addAllError(errors);
			 invocationContext.target = "screenload_success";
			 return;
		}

    
    		//			Added by a-2434 for temporal solution for optimistic control
    		StockHolderVO stockHolderVo = stockControlDefaultsDelegate.findStockHolderDetails(getApplicationSession().getLogonVO().getCompanyCode(),"HQ");
    		if(stockHolderVo==null){
    			//This situation comes when Stockholder HQ is not defined in the system
    			ErrorVO errorVo = new ErrorVO("stockcontrol.defaults.stockholderhqisnotdefined");
    			errorVo.setErrorDisplayType(ErrorDisplayType.ERROR);
    			invocationContext.addError(errorVo);
    			invocationContext.target = "screenload_success";
    			return;
    		}
    		stockAllocationVO.setLastUpdateTime(stockHolderVo.getLastUpdateTime());
    		//    		Added by a-2434 for temporal solution for optimistic control ends
    		
			stockAllocationVO.setRanges(newRangeVOs);
			stockAllocationVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
			stockAllocationVO.setNewStockFlag(true);
			stockAllocationVO.setDocumentType(form.getDocType());
			stockAllocationVO.setDocumentSubType(form.getSubType());
			stockAllocationVO.setStockHolderCode(STOCKHOLDER_CODE);
			stockAllocationVO.setManual(form.isManual());
			stockAllocationVO.setTransferMode(StockAllocationVO.MODE_NORMAL); 
			/*
			 * Added by A-2589 for #102543
			 */
			String awbPrefix=form.getAwbPrefix();			
			if(awbPrefix!=null && awbPrefix.trim().length()>2){				
				stockAllocationVO.setAirlineIdentifier(Integer.parseInt(awbPrefix.split("-")[2]));
			}else{
				stockAllocationVO.setAirlineIdentifier(getApplicationSession().getLogonVO().getOwnAirlineIdentifier());
			}
			/*
			 * End - #102543
			 */
			//stockAllocationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
			stockAllocationVO.setLastUpdateUser(getApplicationSession().getLogonVO().getUserId());
			//Added by A-2881 for ICRD-3082
			stockAllocationVO.setTransactionCode(StockAllocationVO.MODE_CREATE);
						
			session.setStockAllocationVO(stockAllocationVO);

			stockControlDefaultsDelegate.allocateStock(session.getStockAllocationVO());

	}catch(BusinessDelegateException businessDelegateException){
//printStackTrrace()();
			errorVos = handleDelegateException(businessDelegateException);
	}
	if (errorVos != null && errorVos.size() > 0) {
	     invocationContext.addAllError(errorVos);
	     invocationContext.target = "screenload_success";
	     return;
   }

	if(errorVos==null || errorVos.size()==0){
			errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
			Object[] obj = { "Save" };
			error = new ErrorVO("createstock.savesuccess", obj);
			error.setErrorDisplayType(ErrorDisplayType.STATUS);
			errors.add(error);
			invocationContext.addAllError(errors);

	}
	log.log(Log.FINE, "\n\n.....................mode...............> ", session.getMode());
	//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix starts
	form.setDocType(AWB);
	form.setSubType(S);
	//Modified by A-1927 @ NRT on 02-Aug-2007 for NCA Bug Fix ends
	form.setManual(false);
	form.setPartnerAirline(false);
	form.setAirlineName("");
	form.setAwbPrefix("");
	session.setCollectionRangeVO(null);
	form.setTotalNoOfDocs("");

	RangeVO row = new RangeVO();
	row.setStartRange("");
	row.setEndRange("");
	row.setNumberOfDocuments(0);
	Collection<RangeVO> rangeVO = new ArrayList<RangeVO>();
	rangeVO.add(row);
	session.setCollectionRangeVO(rangeVO);
	if("popup".equals(session.getMode())){
		log.log(Log.FINE,"\n\n.....................mode popup...............> ");		
		invocationContext.target = "screenloadpopup_success";
	}else{
		invocationContext.target = "screenload_success";
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
			int jNum=sArray[i];
			if (jNum>57) {
				base*=26;
			} else {
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


	private Collection<ErrorVO> validateForm(CreateStockForm form,
       CreateStockSession session) {

      Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
      ErrorVO error = null;
      boolean isFromNull=false;
      boolean isDuplicate=false;
      boolean isContinuous=false;
      boolean isEndRange=false;
      boolean isOverlap=false;
      String[] rangeFrom=form.getRangeFrom();
      String[] rangeTo=form.getRangeTo();
      String[] hiddenOpFlag = form.getHiddenOpFlag();

      if ("".equals(form.getDocType())) {

			Object[] obj = { "DocType" };
			error = new ErrorVO("SKCM041", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
	  }
	  if ("".equals(form.getSubType())) {

			Object[] obj = { "SubType" };
			error = new ErrorVO("SKCM042", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
	  }

      for(int i=0;i<(rangeFrom.length-1);i++){
       for(int j=i+1;j<rangeTo.length;j++){
			if(hiddenOpFlag[i] != null && hiddenOpFlag[j] != null && !hiddenOpFlag[i].equalsIgnoreCase(OPERATION_FLAG_DELETE)
				&& !hiddenOpFlag[j].equalsIgnoreCase(OPERATION_FLAG_DELETE)
				&& !hiddenOpFlag[i].equalsIgnoreCase(NO_OPERATION)
				&& !hiddenOpFlag[j].equalsIgnoreCase(NO_OPERATION)){
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
      }

      for(int i=0;i<rangeFrom.length;i++){
			if(hiddenOpFlag[i] != null && !hiddenOpFlag[i].equalsIgnoreCase(OPERATION_FLAG_DELETE)
					&& !hiddenOpFlag[i].equalsIgnoreCase(NO_OPERATION)){
			       if("".equals(rangeFrom[i])){
			    	   log.log(Log.FINE,
							"\n\n\n\n--------hiddenOpFlag[i]--------",
							hiddenOpFlag, i);
					isFromNull=true;
			           break;
			          }
			}
      }
      for(int i=0;i<rangeTo.length;i++){
			if(hiddenOpFlag[i] != null && !hiddenOpFlag[i].equalsIgnoreCase(OPERATION_FLAG_DELETE)
					&& !hiddenOpFlag[i].equalsIgnoreCase(NO_OPERATION)){
				 if("".equals(rangeTo[i])){
					 log.log(Log.FINE,
							"\n\n\n\n--------hiddenOpFlag[i]--------",
							hiddenOpFlag, i);
					isFromNull=true;
					  break;
				}
			}
      }
      log.log(Log.FINE, "\n\n\n\n--------isFromNull--------", isFromNull);
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

     return errors;
    }
}
