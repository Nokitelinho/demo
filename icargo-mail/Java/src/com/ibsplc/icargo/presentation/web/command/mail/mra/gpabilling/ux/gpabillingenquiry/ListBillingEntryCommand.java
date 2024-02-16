/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ListBillingEntryCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Jan 30, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.ConsignmentDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryDetails;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.GPABillingEntryFilter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ListBillingEntryCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * --------------------------------------------------- 0.1 : A-4809 : Jan 30,
 * 2019 : Draft
 */
public class ListBillingEntryCommand extends AbstractCommand{
    private Log log = LogFactory.getLogger("Mail MRA ListBillingEntryCommand");
    
    private static final String STATUS_SUCCESS = "success";
    private static final String NO_DATA_FOUND = "mail.mra.gpabilling.gpabillingenquiry.nodata";
    private static final String FROM_DATE_MANDATORY="mail.mra.gpabilling.gpabillingenquiry.fromdatemandatory";
    private static final String TO_DATE_MANDATORY="mail.mra.gpabilling.gpabillingenquiry.todatemandatory";
    private static final String GREATERDATE="mail.mra.gpabilling.gpabillingenquiry.greaterdate";
    private static final String PALIST_TOAPPLY_CONTRACTRAT="mailtracking.mra.PAlisttoapplycontainerrate";

    
    
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("ListBillingEntryCommand", "execute");
		 LogonAttributes logonAttributes = getLogonAttribute();  
		 String companyCode = logonAttributes.getCompanyCode();
		 GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		 Page<DocumentBillingDetailsVO> documentBillingDetailsVOs =null;
		 Page<ConsignmentDocumentVO> consignmentDocVOs = null;
		 ResponseVO responseVO = new ResponseVO();
		 List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		 Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		 Map<String, String> systemParameters = null;
		 Collection<String> parameterTypes = new ArrayList<String>();
		 parameterTypes.add(PALIST_TOAPPLY_CONTRACTRAT);
		    
		GPABillingEntryFilter gpaBillingEntryFilter = (GPABillingEntryFilter) gpaBillingEnquiryModel
				.getGpaBillingEntryFilter();
		 String fromBillingList =(String)gpaBillingEnquiryModel.getFromBillingList();
		 String fromConsignmentList = (String)gpaBillingEnquiryModel.getFromConsignmentList();
			if(gpaBillingEntryFilter.getFromDate()==null && gpaBillingEntryFilter.getFromDate().isEmpty()){
	        	actionContext.addError(new ErrorVO(FROM_DATE_MANDATORY));
	        	return;			
			}
			if(gpaBillingEntryFilter.getToDate()==null && gpaBillingEntryFilter.getToDate().isEmpty()){
	        	actionContext.addError(new ErrorVO(TO_DATE_MANDATORY));
	        	return;			
			}
		 GPABillingEntriesFilterVO filterVO = new GPABillingEntriesFilterVO();			
		 List<ErrorVO> errors=(List<ErrorVO>)updateFilterVO(gpaBillingEntryFilter, filterVO, logonAttributes);
	        if (errors!=null && !errors.isEmpty()){        	
	        	actionContext.addAllError(errors);
	        	return; 
	        }
	     try{
    	      oneTimeValues = new SharedDefaultsDelegate().findOneTimeValues(companyCode,getOneTimeParameterTypes());
    	      systemParameters = new SharedDefaultsDelegate().findSystemParameterByCodes(parameterTypes);
    	  }catch (BusinessDelegateException e){
    	      actionContext.addAllError(handleDelegateException(e));
    	  }	 
	     
	     /*if((fromBillingList == null||fromBillingList.isEmpty())  
	    	 documentBillingDetailsVOs = listBillingDetails(filterVO, gpaBillingEntryFilter.getDisplayPage()); 
		      	if(documentBillingDetailsVOs==null || documentBillingDetailsVOs.size()==0){
					actionContext.addError(new ErrorVO(NO_DATA_FOUND));		
					return;	
		      	}
		ArrayList<GPABillingEntryDetails> gpaBillingEntryDetails = MailMRAModelConverter
				.constructBillingDetails(documentBillingDetailsVOs, oneTimeValues,systemParameters);
		PageResult<GPABillingEntryDetails> billingDetails = new PageResult<GPABillingEntryDetails>(
				documentBillingDetailsVOs, gpaBillingEntryDetails);
	     }*/
				documentBillingDetailsVOs = listBillingDetails(filterVO, gpaBillingEntryFilter.getDisplayPage()); 
				if(documentBillingDetailsVOs==null || documentBillingDetailsVOs.size()==0){
					actionContext.addError(new ErrorVO(NO_DATA_FOUND));		
					return;	
				}
				ArrayList<GPABillingEntryDetails> gpaBillingEntryDetails= MailMRAModelConverter.constructBillingDetails(documentBillingDetailsVOs,oneTimeValues,systemParameters);
				PageResult<GPABillingEntryDetails> billingDetails = new PageResult<GPABillingEntryDetails>(documentBillingDetailsVOs, gpaBillingEntryDetails);
				ArrayList<ConsignmentDetails> consignmentDetails = MailMRAModelConverter.constructConsginmentDetails(consignmentDocVOs,oneTimeValues);
				PageResult<ConsignmentDetails> csgDetails = new PageResult<ConsignmentDetails>(consignmentDocVOs,consignmentDetails);
				if(billingDetails!=null && billingDetails.getResults().size()>0){
				gpaBillingEnquiryModel.setGpaBillingEntryDetails(billingDetails);
				}
				/*if(csgDetails!=null && csgDetails.getResults().size()>0){
				}*/
				gpaBillingEnquiryModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
				results.add(gpaBillingEnquiryModel);
				responseVO.setResults(results);
				responseVO.setStatus(STATUS_SUCCESS);
				actionContext.setResponseVO(responseVO);	      		
		this.log.exiting("ListBillingEntryCommand", "execute");
	}
/**
	 * Method : ListBillingEntryCommand.listBillingDetails Added by : A-4809 on
	 * Jan 31, 2019 Used for : Parameters : @param filterVO Parameters : @param
	 * displayPage Parameters : @return Return type :
	 * Page<DocumentBillingDetailsVO>
 */
	private Page<DocumentBillingDetailsVO> listBillingDetails(GPABillingEntriesFilterVO filterVO, String displayPage) {
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs =null;
		int pageNumber = Integer.parseInt(displayPage);
		filterVO.setPageNumber(pageNumber);
		try {
			this.log.log(Log.FINE, "To invoke listGPABillingEntries method");
			documentBillingDetailsVOs = new MailTrackingMRADelegate().listGPABillingEntries(filterVO);
			this.log.log(Log.FINE, "after invocation of listGPABillingEntries method");
		} catch (BusinessDelegateException e) {
			this.log.log(Log.SEVERE, e.getMessage());
		}
		return documentBillingDetailsVOs;
	}

/**
	 * Method : ListBillingEntryCommand.updateFilterVO Added by : A-4809 on Jan
	 * 30, 2019 Used for : Parameters : @param gpaBillingEntryFilter Parameters
	 * : @param filterVO Parameters : @param logonAttributes Parameters
	 * : @return Return type : List<ErrorVO>
 */
	private List<ErrorVO> updateFilterVO(GPABillingEntryFilter gpaBillingEntryFilter,
			GPABillingEntriesFilterVO filterVO, LogonAttributes logonAttributes) {
		ArrayList<ErrorVO> errors = new ArrayList<>();
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		try{
			filterVO.setCompanyCode(logonAttributes.getCompanyCode());
			if(gpaBillingEntryFilter.getFromDate()!=null && !gpaBillingEntryFilter.getFromDate().isEmpty()){
	    	  fromDate.setDate(gpaBillingEntryFilter.getFromDate().toUpperCase());
	  		  filterVO.setFromDate(fromDate);
			}
			if(gpaBillingEntryFilter.getToDate()!=null && !gpaBillingEntryFilter.getToDate().isEmpty()){
				toDate.setDate(gpaBillingEntryFilter.getToDate().toUpperCase());
				filterVO.setToDate(toDate);
			}
	        if (fromDate.isGreaterThan(toDate)){
	        	errors.add(new ErrorVO(GREATERDATE));
	        }
			if (gpaBillingEntryFilter.getConsignmentNumber() != null
					&& !gpaBillingEntryFilter.getConsignmentNumber().isEmpty()) {
				StringBuilder csg = new StringBuilder("'").append(gpaBillingEntryFilter.getConsignmentNumber())
						.append("'");
			filterVO.setConDocNumber(csg.toString());
	        }
			filterVO.setBillingStatus(gpaBillingEntryFilter.getBillingStatus());
			filterVO.setGpaCode(gpaBillingEntryFilter.getGpaCode());
			filterVO.setOriginOfficeOfExchange(gpaBillingEntryFilter.getOriginOE());
			filterVO.setOrigin(gpaBillingEntryFilter.getOrigin());
			filterVO.setDestinationOfficeOfExchange(gpaBillingEntryFilter.getDestinationOE());
			filterVO.setDestination(gpaBillingEntryFilter.getDestination());
			filterVO.setMailCategoryCode(gpaBillingEntryFilter.getCategory());
			filterVO.setMailSubclass(gpaBillingEntryFilter.getSubClass());
			filterVO.setYear(gpaBillingEntryFilter.getYear());
			if(gpaBillingEntryFilter.getDsn()!=null && !gpaBillingEntryFilter.getDsn().isEmpty()){
			//	StringBuilder dsn = new StringBuilder("'").append(gpaBillingEntryFilter.getDsn()).append("'");
			filterVO.setDsnNumber(gpaBillingEntryFilter.getDsn()); 
			}
			filterVO.setRsn(gpaBillingEntryFilter.getRsn());
			filterVO.setHni(gpaBillingEntryFilter.getHni());
			filterVO.setRegInd(gpaBillingEntryFilter.getRi());
			filterVO.setMailbagId(gpaBillingEntryFilter.getMailbag());
			filterVO.setIsUSPSPerformed(gpaBillingEntryFilter.getUspsMailPerformance());
			filterVO.setTotalRecordCount(gpaBillingEntryFilter.getTotalRecords());
			filterVO.setDefaultPageSize(gpaBillingEntryFilter.getDefaultPageSize());
			if(gpaBillingEntryFilter.getRateBasis()!=null && !gpaBillingEntryFilter.getRateBasis().isEmpty()){
				if(MRAConstantsVO.CONTRACT_RATE.equals(gpaBillingEntryFilter.getRateBasis())){
					filterVO.setContractRate(gpaBillingEntryFilter.getRateBasis());
				}else if(MRAConstantsVO.UPU_RATE.equals(gpaBillingEntryFilter.getRateBasis())){
					filterVO.setUPURate(gpaBillingEntryFilter.getRateBasis());
				}
			}
			filterVO.setFromCsgGroup(GPABillingEntriesFilterVO.FLAG_NO);
			filterVO.setPaBuilt(gpaBillingEntryFilter.getPaBuilt());
		}catch(Exception e){
			errors.add(new ErrorVO(e.getMessage()));
		}
		return  errors;
	}
/**
	 * Method : ListBillingEntryCommand.getOneTimeParameterTypes Added by :
	 * A-4809 on Feb 6, 2019 Used for : Parameters : @return Return type :
	 * Collection<String>
 */
	  private Collection<String> getOneTimeParameterTypes(){
		    Collection<String> parameterTypes = new ArrayList<String>();
		    parameterTypes.add(MRAConstantsVO.KEY_BILLING_TYPE_ONETIME);
		    parameterTypes.add(MRAConstantsVO.KEY_CATEGORY_ONETIME);
		    parameterTypes.add(MRAConstantsVO.USPS_PERFORMED);
		    parameterTypes.add(MRAConstantsVO.RATE_BASIS);
		  //Added by A-8527 for IASCB-22915
		    parameterTypes.add(MRAConstantsVO.KEY_WGTUNITCOD_ONETIME);
		    return parameterTypes;
		  }
}
