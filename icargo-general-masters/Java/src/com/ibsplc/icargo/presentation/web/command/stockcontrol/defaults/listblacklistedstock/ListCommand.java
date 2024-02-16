/**
 * ListCommand.java Created on Sep 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.stockcontrol.defaults.listblacklistedstock;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.shared.document.vo.SharedRangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.BlacklistStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.document.DocumentTypeDelegate;
import com.ibsplc.icargo.presentation.delegate.stockcontrol.defaults.StockControlDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ListBlackListedStockSession;
import com.ibsplc.icargo.presentation.web.struts.form.stockcontrol.defaults.ListBlackListedStockForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1927
 *
 */

public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1927
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListCommand","execute");
    	ListBlackListedStockForm listBlackListedStockForm = (ListBlackListedStockForm) invocationContext.screenModel;
		ListBlackListedStockSession session = (ListBlackListedStockSession)
							getScreenSession( "stockcontrol.defaults","stockcontrol.defaults.listblacklistedstock");

		ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();

		Collection<ErrorVO> errors = null;
		errors = validateForm(listBlackListedStockForm,session);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "screenload_failure";
			return;
		}
		LocalDate from = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		LocalDate to = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		String companyCode=logonAttributesVO.getCompanyCode();
		String documentType=listBlackListedStockForm.getDocType();
		String documentSubType=listBlackListedStockForm.getSubType();
		String rangeFrom=listBlackListedStockForm.getRangeFrom();
		String rangeTo=listBlackListedStockForm.getRangeTo();

		session.setBlacklistStockVOs(null);

		StockFilterVO stockFilterVO=new StockFilterVO();
		Page<BlacklistStockVO> blacklistStockVOs=null;
		
		//Added by A-5214 as part from the ICRD-20959 starts
		if(!"YES".equals(listBlackListedStockForm.getCountTotalFlag()) && session.getTotalRecords().intValue() != 0){
			stockFilterVO.setTotalRecords(session.getTotalRecords().intValue());
	    }else{
	    	stockFilterVO.setTotalRecords(-1);
	    }
		//Added by A-5214 as part from the ICRD-20959 end
		
		stockFilterVO.setCompanyCode(companyCode);
		stockFilterVO.setAirlineIdentifier(getAirlineIdentifier(listBlackListedStockForm.getAwbPrefix()));
		stockFilterVO.setDocumentType(documentType);
		stockFilterVO.setDocumentSubType(documentSubType);
		stockFilterVO.setRangeFrom(rangeFrom);
		stockFilterVO.setRangeTo(rangeTo);
		if(listBlackListedStockForm.getFromDate() != null && listBlackListedStockForm.getFromDate().trim().length()>0){
		stockFilterVO.setFromDate(from.setDate(listBlackListedStockForm.getFromDate()));
		}
		if(listBlackListedStockForm.getToDate() != null && listBlackListedStockForm.getToDate().trim().length()>0){
		stockFilterVO.setToDate(to.setDate(listBlackListedStockForm.getToDate()));
		}		
		 //Added as part of ICRD-76008
		 if(stockFilterVO.getRangeFrom() != null && stockFilterVO.getRangeFrom().trim().length() > 0
	        		&& stockFilterVO.getRangeTo() != null && stockFilterVO.getRangeTo().trim().length() > 0){
		DocumentTypeDelegate documentTypeDelegate=new DocumentTypeDelegate();
		SharedRangeVO sharedRangeVO=new SharedRangeVO();
		DocumentVO documentVO=new DocumentVO();
        documentVO.setDocumentType(stockFilterVO.getDocumentType());
        documentVO.setCompanyCode(stockFilterVO.getCompanyCode());
        documentVO.setDocumentSubType(stockFilterVO.getDocumentSubType());       
        sharedRangeVO.setFromrange(stockFilterVO.getRangeFrom());
        sharedRangeVO.setToRange(stockFilterVO.getRangeTo());   
       
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
			}
        }
		if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = "screenload_failure";
				return;
		}
	   ErrorVO error = null;
       try{
			StockControlDefaultsDelegate stockControlDefaultsDelegate=new StockControlDefaultsDelegate();
			blacklistStockVOs =stockControlDefaultsDelegate.findBlacklistedStock(stockFilterVO,Integer.parseInt(listBlackListedStockForm.getDisplayPage()));
			//Bug ID : ICRD-12191 - Jeena - Setting airlineIdentifier to BlacklistStockVO before adding the collection of vos to session
			//session.setBlacklistStockVOs(blacklistStockVOs);
			if(blacklistStockVOs == null || blacklistStockVOs.getActualPageSize() == 0 ){ //Added by A-5214 as part from the ICRD-20959
				errors = new ArrayList<ErrorVO>();
				Object[] obj = { "NullBlacklistStockVOs" };
				error = new ErrorVO("stockcontrol.defaults.norecordsfound", obj);
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				
				//End of Bug ID : ICRD-12191 - Jeena - Setting airlineIdentifier to BlacklistStockVO before adding the collection of vos to session
			}else{
				session.setTotalRecords(blacklistStockVOs.getTotalRecordCount());
				session.setBlacklistStockVOs(blacklistStockVOs);
			}
		}
		catch(BusinessDelegateException businessDelegateException){
			log.log(Log.SEVERE, "BusinessDelegateException caught from findBlacklistedStock");
		}		
        listBlackListedStockForm.setListButton("N");
        log.exiting("ListCommand","execute");
        invocationContext.target = "screenload_success";
    }


	private int getAirlineIdentifier(String awbPrefix) {
		int airlineIdentifier=getApplicationSession().getLogonVO().getOwnAirlineIdentifier();
		if(awbPrefix!=null && awbPrefix.trim().length()>0){
			String[] tokens=awbPrefix.split("-");
			return (tokens!=null && tokens.length>2)? Integer.parseInt(tokens[2]):airlineIdentifier;
		}
		return airlineIdentifier;
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

    private Collection<ErrorVO> validateForm(ListBlackListedStockForm form,
					ListBlackListedStockSession session) {

				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				boolean isValid=true;
				if ("".equals(form.getDocType())||form.getDocType().length()==0){

						Object[] obj = { "Document Type" };
						error = new ErrorVO("stockcontrol.defaults.selectdoctype", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
				if(((form.getRangeTo() == null || form.getRangeTo().trim().length()==0) ||
						(form.getRangeTo() == null || form.getRangeTo().trim().length()==0)) && 
						((form.getFromDate() ==null)||form.getFromDate().trim().length()==0 ||
						  form.getToDate()==null ||form.getToDate().trim().length()==0)){
						Object[] obj = { "FromDate" };
							error = new ErrorVO("stockcontrol.defaults.listblacklistedstock.eitherdaterangeorstockrangeismandatory", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
				if (((form.getFromDate() !=null && form.getFromDate().trim().length()>0) ||
						  (form.getToDate()!=null && form.getToDate().trim().length()>0))  &&
						isValid && DateUtilities.isLessThan(form.getToDate(),form.getFromDate(), "dd-MMM-yyyy")){
						isValid = false;
						Object[] obj = { "FromDateExceedsToDate" };
						error = new ErrorVO("stockcontrol.defaults.todateearlierthanfrmdate", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
				if (!"".equals(form.getRangeTo()) && findLong(form.getRangeFrom())>findLong(form.getRangeTo())){

						Object[] obj = { "RangeFrom exceeds" };
						error = new ErrorVO("SKCM_099", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}
				if(form.isPartnerAirline()&&(form.getAwbPrefix()==null || form.getAwbPrefix().trim().length()==0)){					
					error=new ErrorVO("stockcontrol.defaults.allocatestock.partnerairline.ismandatory");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
				
			return errors;
	}



}
