/*
 * ListRangesCommand.java Created on Sep 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.allocatenewstock;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.framework.security.privilege.TransactionPrivilegeHelper;
import com.ibsplc.icargo.framework.security.privilege.vo.TransactionPrivilegeNewVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
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
public class ListRangesCommand extends BaseCommand {

	private static final String BLANKSPACE = "";
	
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	
	private static final String TXN_COD_ALLOCATE_STOCK = "ALLOCATE_STOCK";   
	
	private static final String PRVLG_RUL_STK_HLDR = "STK_HLDR_CODE";

	private static final String PRVLG_LVL_STKHLD = "STKHLD";
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListRangesCommand","execute");
    	AllocateNewStockForm allocateNewStockForm = (AllocateNewStockForm) invocationContext.screenModel;
		AllocateNewStockSession session = (AllocateNewStockSession)
							getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.allocatenewstock");
		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		if(allocateNewStockForm.getNumberOfDocs() != null && 
				allocateNewStockForm.getNumberOfDocs().trim().length()>0 &&
				Integer.parseInt(allocateNewStockForm.getNumberOfDocs())==0){
			allocateNewStockForm.setNumberOfDocs(BLANKSPACE);
		}
		errors = validateForm(allocateNewStockForm,session);
		session.setAvailableRangeVos(null);
		String companyCode=logonAttributesVO.getCompanyCode();
		String stockHolderCode=upper(allocateNewStockForm.getStockControlFor());
		String documentType=allocateNewStockForm.getDocType();
		String documentSubType=allocateNewStockForm.getSubType();
		String startRange=allocateNewStockForm.getRangeFrom();
		String endRange=allocateNewStockForm.getRangeTo();
		String numberOfDocuments=allocateNewStockForm.getNumberOfDocs();
		String[] stockRangeFrom=allocateNewStockForm.getStockRangeFrom();
		String[] stockRangeTo=allocateNewStockForm.getStockRangeTo();
		int iterator=0;
		 //Added as part of ICRD-76008		
		if(startRange != null && startRange.trim().length() > 0
        		&& endRange != null && endRange.trim().length() > 0){		
		DocumentTypeDelegate documentTypeDelegate=new DocumentTypeDelegate();
		SharedRangeVO sharedRangeVO=new SharedRangeVO();
		DocumentVO documentVO=new DocumentVO();
		documentVO.setCompanyCode(companyCode);
        documentVO.setDocumentType(documentType);        
        documentVO.setDocumentSubType(documentSubType);
        sharedRangeVO.setFromrange(startRange);
        sharedRangeVO.setToRange(endRange); 
        
	        Collection<SharedRangeVO> sharedRangeVOs=new ArrayList<SharedRangeVO>();
	        sharedRangeVOs.add(sharedRangeVO);
	        documentVO.setRange(sharedRangeVOs);
	        try {
				documentTypeDelegate.validateRange(documentVO);
			} catch (BusinessDelegateException e) {
				ErrorVO error = null;
				error = new ErrorVO("stockcontrol.defaults.invalidalphanumericrange");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
				invocationContext.addAllError(errors);
				allocateNewStockForm.setIsError(DocumentVO.FLAG_YES);
				invocationContext.target = "screenload_failure";				
				return;
				
			}
        }      	
		Collection<RangeVO> retainRangeVOs = session.getAllocatedRangeVos();
		log.log(Log.FINE, "...........The retainRangeVOs.......",
				retainRangeVOs);
		if(retainRangeVOs!=null){
			for(RangeVO tempRange:retainRangeVOs){
			    	tempRange.setStartRange(stockRangeFrom[iterator]);
				    tempRange.setEndRange(stockRangeTo[iterator]);
				
				iterator++;
			}
		}
		log.log(Log.FINE, "...........After The retainRangeVOs.......",
				retainRangeVOs);
		Collection<RangeVO> retainRangeVOsArr =new ArrayList<RangeVO>();
		String[] hopFlag = allocateNewStockForm.getHiddenOpFlag();
		int iteratorForOpFlag=0;
		if(retainRangeVOs!=null){
			for(RangeVO retainrangeVO:retainRangeVOs){
				if(!OPERATION_FLAG_DELETE.equals(hopFlag[iteratorForOpFlag])){
					  log.log(Log.FINE, "........... retainRangeVOsArr.......",
							retainRangeVOsArr.size());
					retainRangeVOsArr.add(retainrangeVO);
					  }
				iteratorForOpFlag++;
			}
		}
	
		log.log(Log.FINE, "...........After The retainRangeVOsArr.......",
				retainRangeVOsArr);
		retainRangeVOs.clear();
		retainRangeVOs.addAll(retainRangeVOsArr);
		log.log(Log.FINE, "...........After Adding The RetainRangeVos.......",
				retainRangeVOs);
		session.setAllocatedRangeVos(null);
		session.setAllocatedRangeVos(retainRangeVOs);
		RangeFilterVO rangeFilterVO=new RangeFilterVO();
		Collection<RangeVO> rangeVOs=null;
		rangeFilterVO.setCompanyCode(companyCode);
		rangeFilterVO.setStockHolderCode(stockHolderCode);
		rangeFilterVO.setDocumentType(documentType);
		rangeFilterVO.setDocumentSubType(documentSubType);
		rangeFilterVO.setStartRange(startRange);
		rangeFilterVO.setEndRange(endRange);
		rangeFilterVO.setNumberOfDocuments(numberOfDocuments);
		rangeFilterVO.setAirlineIdentifier(getAirlineIdentifier(allocateNewStockForm.getAwbPrefix()));
		rangeFilterVO.setManual(allocateNewStockForm.isManual());
		//Privilege check done for ICRD-105821
		//CRQ_ ICRD-105821_Bhaskar_17Apr2015
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
				        findApproverCode(companyCode,stockHolderCode,documentType,
						documentSubType);
                  }catch (BusinessDelegateException businessDelegateException) {				
               	   errors.addAll(handleDelegateException(businessDelegateException));
                  	}
             //Approver code end
			if(!privilegedStkHldrList.contains(allocateNewStockForm.getStockControlFor())&&
					!privilegedStkHldrList.contains(approver)){
				ErrorVO error =  new ErrorVO("stockcontrol.defaults.allocatenotauthorized");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				invocationContext.addAllError(errors);
				allocateNewStockForm.setIsError(DocumentVO.FLAG_YES);
				invocationContext.target = "screenload_failure";
				return;
			}
		}

       try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			log.log(Log.FINE,"...........Inside List Command class.......");
			log.log(Log.FINE, "...........rangeFilterVO.......", rangeFilterVO);
			rangeVOs=stockControlDefaultsDelegate.findRanges(rangeFilterVO);
		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE,"BusinessDelegateException caught..........");
		}

		ErrorVO error = null;
		if(rangeVOs==null||rangeVOs.size()==0){

			Object[] obj = { "Range-VOs is Null" };
			error = new ErrorVO("SKCM_091", obj);
			error.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(error);

		}

		if (errors != null && errors.size() > 0) {
			   session.setAvailableRangeVos(null);
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
		}


		session.setAvailableRangeVos((Collection<RangeVO>)rangeVOs);
		log.exiting("ListRowCommand","execute");
        invocationContext.target = "screenload_success";
    }

    private int getAirlineIdentifier(String awbPrefix) {
    	int ownAirlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			String[] tokens=awbPrefix.split("-");
			return (tokens!=null && tokens.length>2)?Integer.parseInt(tokens[2]):ownAirlineIdentifier;
		}
		return ownAirlineIdentifier;
	}

	private Collection<ErrorVO> validateForm(AllocateNewStockForm form,
					AllocateNewStockSession session) {

				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;

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

				if ("".equals(form.getStockControlFor())||form.getStockControlFor().length()==0){
							Object[] obj = { "Stock Control For" };
							error = new ErrorVO("stockcontrol.defaults.stkcontrolformandatory", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
				}

/* commented by A-7740 as a part of ICRD-225106
				if("".equals(form.getRangeFrom())){
							Object[] obj = { "Range-From" };
							error = new ErrorVO("stockcontrol.defaults.plsenterrangefrmval", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
				}*/
				if("".equals(form.getNumberOfDocs())){
					if("".equals(form.getRangeTo())){
								Object[] obj = { "Range-To" };
								error = new ErrorVO("stockcontrol.defaults.plzenterrangetoval", obj);
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
					}
				}
				if(form.isPartnerAirline() && (form.getAwbPrefix()==null || 
						form.getAwbPrefix().trim().length()==0)){
					error=new ErrorVO("stockcontrol.defaults.allocatestock.partnerairline.ismandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
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
