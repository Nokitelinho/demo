/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.UpdateBillingStatusCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Mar 11, 2019
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

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterConfigRuleVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
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
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.UpdateBillingStatusCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Mar 11, 2019	:	Draft
 */
public class UpdateBillingStatusCommand extends AbstractCommand{
	private Log log = LogFactory.getLogger("MAIL MRA GPABILLING");
	private static final String STATUS_SUCCESS = "success";
	private static final String CRA_PAR_INVGRP="INVGRP";
	private static final String PALIST_TOAPPLY_CONTRACTRAT="mailtracking.mra.PAlisttoapplycontainerrate";
	 
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("UpdateBillingStatusCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		 String companyCode = logonAttributes.getCompanyCode();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
		ResponseVO responseVO = new ResponseVO();
		List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		 Map<String, String> systemParameters = null;
		 Collection<String> parameterTypes = new ArrayList<String>();
		 parameterTypes.add(PALIST_TOAPPLY_CONTRACTRAT);
		Collection<ConsignmentDetails> selectedBilling = gpaBillingEnquiryModel.getSelectedConsignmentDetails();
		GPABillingEntryFilter gpaBillingEntryFilter = (GPABillingEntryFilter)gpaBillingEnquiryModel.getGpaBillingEntryFilter();
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs =null;
		Page<ConsignmentDocumentVO> consignmentDocVOs = null;
		Collection<ConsignmentDocumentVO> selectedConsignmentDocumentVOs =null;
		Collection<CRAParameterVO> craParameterVOs = null;
		String groupParameter = null;
		craParameterVOs =findCRAParameterDetails(companyCode, CRA_PAR_INVGRP);
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
		if(selectedBilling!=null && !selectedBilling.isEmpty()){
		 selectedConsignmentDocumentVOs = MailMRAModelConverter.constructConsignmentDocumentVOs(selectedBilling, logonAttributes,oneTimeValues);
		}else if(gpaBillingEntryFilter!=null){  
			filterVO.setFromCsgGroup(GPABillingEntriesFilterVO.FLAG_NO); 
			documentBillingDetailsVOs = listBillingDetails(filterVO, gpaBillingEntryFilter.getDisplayPage());
			ArrayList<GPABillingEntryDetails> gpaBillingEntryDetails= MailMRAModelConverter.constructBillingDetails(documentBillingDetailsVOs,oneTimeValues,systemParameters);
			PageResult<GPABillingEntryDetails> billingDetails = new PageResult<GPABillingEntryDetails>(documentBillingDetailsVOs, gpaBillingEntryDetails);
			if(billingDetails!=null && billingDetails.getResults().size()>0){
			gpaBillingEnquiryModel.setGpaBillingEntryDetails(billingDetails);
			}
			results.add(gpaBillingEnquiryModel); 
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);	  
		}  
		if(selectedConsignmentDocumentVOs!=null && !selectedConsignmentDocumentVOs.isEmpty()){
			groupParameter =getGroupParameter(craParameterVOs);
			consignmentDocVOs = listConsignmentDetails(filterVO,gpaBillingEntryFilter.getDisplayPage());
			StringBuilder csgNO =null;
			//StringBuilder dsnNO = null;
			StringBuilder rate = null;
			StringBuilder gpa =null;
			StringBuilder ooe = null;
			StringBuilder doe =null;
			StringBuilder cat =null;
			StringBuilder subcls =null;
			StringBuilder permet =null;
			StringBuilder ctrcurcod =null;
			StringBuilder mcaind =null;
			filterVO.setMcaIndicator(GPABillingEntriesFilterVO.FLAG_NO);
			for(ConsignmentDocumentVO doc : selectedConsignmentDocumentVOs){
				if(doc.getConsignmentNumber()!=null){
					if(csgNO == null){
					csgNO = new StringBuilder("'").append(doc.getConsignmentNumber()).append("'");
					}else{
						csgNO.append(",'").append(doc.getConsignmentNumber()).append("'");
					} 
				} 
/*				if(doc.getDsnNumber()!=null){
					if(dsnNO == null){
					dsnNO = new StringBuilder("'").append(doc.getDsnNumber()).append("'");
					}else{
						dsnNO.append(",'").append(doc.getDsnNumber()).append("'"); 
					} 
				}*/
				if(doc.getRate()!=0.0){
					String rateVal = Double.toString(doc.getRate());
					int length = rateVal.length();
					if('0'==(rateVal.charAt(length-1))){
						rateVal = rateVal.substring(0, length-2);
					}	
					int newlength = rateVal.length();
					if('.'==(rateVal.charAt(newlength-1))){
						rateVal = rateVal.substring(0, newlength-2);
					}					
					if(rate==null){
						rate = new StringBuilder(rateVal);
					}else{
						rate.append(",").append(rateVal);
					}
				}
				if(groupParameter!=null){
					if(groupParameter.contains("GPA")){
						if(doc.getPaCode()!=null){
							if(gpa == null){
								gpa = new StringBuilder("'").append(doc.getPaCode()).append("'");
							}else{
								gpa.append(",'").append(doc.getPaCode()).append("'"); 
							} 							
						}
					}else if(doc.getPaCode()!=null){
						gpa = new StringBuilder("'").append(doc.getPaCode()).append("'");
					}
				  if(groupParameter.contains("OOE")){
					  if(doc.getOriginOfficeOfExchange()!=null){
							if(ooe == null){
								ooe = new StringBuilder("'").append(doc.getOriginOfficeOfExchange()).append("'");
							}else{
								ooe.append(",'").append(doc.getOriginOfficeOfExchange()).append("'"); 
							} 	
					  }
				  }else if(doc.getOriginOfficeOfExchange()!=null){
							ooe = new StringBuilder("'").append(doc.getOriginOfficeOfExchange()).append("'");
				  }
				 if(groupParameter.contains("DOE")){
					  if(doc.getDestinationOfficeOfExchange()!=null){
							if(doe == null){
								doe = new StringBuilder("'").append(doc.getDestinationOfficeOfExchange()).append("'");
							}else{
								doe.append(",'").append(doc.getDestinationOfficeOfExchange()).append("'"); 
							} 	
					  }
				 }else if(doc.getDestinationOfficeOfExchange()!=null){
							doe = new StringBuilder("'").append(doc.getDestinationOfficeOfExchange()).append("'");
				 }
				if(groupParameter.contains("CAT")){
					  if(doc.getMailCategory()!=null && !"ALL".equals(doc.getMailCategory())){
							if(cat == null){
								cat = new StringBuilder("'").append(doc.getMailCategory()).append("'");
							}else{
								cat.append(",'").append(doc.getMailCategory()).append("'"); 
							} 	
					  }					
				} else  if(doc.getMailCategory()!=null && !"ALL".equals(doc.getMailCategory())){
					cat = new StringBuilder("'").append(doc.getMailCategory()).append("'");
				}
				if(groupParameter.contains("SUBCLS")){
					if(doc.getMailSubClass()!=null){
						if(subcls == null){
							subcls = new StringBuilder("'").append(doc.getMailSubClass()).append("'");
						}else{
							subcls.append(",'").append(doc.getMailSubClass()).append("'"); 
						} 	
				  }
				}else if(doc.getMailSubClass()!=null){
						subcls = new StringBuilder("'").append(doc.getMailSubClass()).append("'");
				}
				if(groupParameter.contains("PERMET")){
					if(doc.getIsUspsPerformed()!=null){
						if(permet == null){
							permet = new StringBuilder("'").append(doc.getIsUspsPerformed()).append("'");
						}else{
							permet.append(",'").append(doc.getIsUspsPerformed()).append("'"); 
						} 	
				  }					
				}else 	if(doc.getIsUspsPerformed()!=null){
					permet = new StringBuilder("'").append(doc.getIsUspsPerformed()).append("'");
				}
				if(groupParameter.contains("CTRCURCOD")){
					  if(doc.getCurrency()!=null){
							if(ctrcurcod == null){
								ctrcurcod = new StringBuilder("'").append(doc.getCurrency()).append("'");
							}else{
								ctrcurcod
								.append(",'").append(doc.getCurrency()).append("'"); 
							} 	
					  }
				  }	else if(doc.getCurrency()!=null){
					  ctrcurcod = new StringBuilder("'").append(doc.getCurrency()).append("'"); 
				  }				
			  if(groupParameter.contains("MCAIND")){
				if(doc.getMcaNumber()!=null){
					if(mcaind == null){
						mcaind = new StringBuilder("'").append(doc.getMcaNumber()).append("'");
					}else{
						mcaind.append(",'").append(doc.getMcaNumber()).append("'"); 
					} 					
				}else{
					filterVO.setMcaIndicator(GPABillingEntriesFilterVO.FLAG_YES);
				}
			  }else if(doc.getMcaNumber()!=null){
				  mcaind = new StringBuilder("'").append(doc.getMcaNumber()).append("'");
			  }

				}
			}
			if(csgNO!=null){
				filterVO.setConDocNumber(csgNO.toString());
			}
/*			if(dsnNO!=null){ 
				filterVO.setDsnNumber(dsnNO.toString());
			}*/
			if(rate!=null){
				filterVO.setRateFilter(rate.toString());
			}
			if(gpa!=null){
				filterVO.setGpaCode(gpa.toString());
			}
			if(ooe!=null){
				filterVO.setOriginOfficeOfExchange(ooe.toString());
			}
			if(doe!=null){
				filterVO.setDestinationOfficeOfExchange(doe.toString());
			}
			if(cat!=null){
				filterVO.setMailCategoryCode(cat.toString());
			}else{
				filterVO.setMailCategoryCode(null);
			}
			if(subcls!=null){
				filterVO.setMailSubclass(subcls.toString());
			}
			if(permet!=null){
				filterVO.setIsUSPSPerformed(permet.toString());
			}else{
				filterVO.setIsUSPSPerformed(null);
			}
			if(ctrcurcod!=null){
				filterVO.setCurrencyCode(ctrcurcod.toString());
			}
			if(mcaind!=null){
				filterVO.setMcaNumber(mcaind.toString());
			}
			if(!((groupParameter.contains("CTRCURCOD"))||(groupParameter.contains("PERMET"))||(groupParameter.contains("SUBCLS"))||(groupParameter.contains("CAT"))||(groupParameter.contains("MCAIND"))))
			{
				filterVO.setCurrencyCode(null);
				filterVO.setMailCategoryCode(null);
				filterVO.setMailSubclass(null);
				filterVO.setIsUSPSPerformed(null);
				filterVO.setMcaIndicator(null);
			}
			filterVO.setFromCsgGroup(GPABillingEntriesFilterVO.FLAG_YES);
			
			documentBillingDetailsVOs = listBillingDetails(filterVO, "1");
			ArrayList<GPABillingEntryDetails> gpaBillingEntryDetails= MailMRAModelConverter.constructBillingDetails(documentBillingDetailsVOs,oneTimeValues,systemParameters);
			PageResult<GPABillingEntryDetails> billingDetails = new PageResult<GPABillingEntryDetails>(documentBillingDetailsVOs, gpaBillingEntryDetails);
			ArrayList<ConsignmentDetails> consignmentDetails = MailMRAModelConverter.constructConsginmentDetails(consignmentDocVOs,oneTimeValues);
			PageResult<ConsignmentDetails> csgDetails = new PageResult<ConsignmentDetails>(consignmentDocVOs,consignmentDetails);
			if(billingDetails!=null && billingDetails.getResults().size()>0){
			gpaBillingEnquiryModel.setGpaBillingEntryDetails(billingDetails);
			}
			if(csgDetails!=null && csgDetails.getResults().size()>0){
			gpaBillingEnquiryModel.setConsignmentDetails(csgDetails);
			}
			results.add(gpaBillingEnquiryModel); 
			responseVO.setResults(results);
			responseVO.setStatus(STATUS_SUCCESS);
			actionContext.setResponseVO(responseVO);	      		
		}
		
		this.log.exiting("ListBillingEntryCommand", "execute");
	}
	private String getGroupParameter(Collection<CRAParameterVO> craParameterVOs) {
		StringBuilder groupString = null;
		if(craParameterVOs!=null && !craParameterVOs.isEmpty()){
			for(CRAParameterVO parameterVO :craParameterVOs){
			Collection<CRAParameterConfigRuleVO> cRAParameterConfigRuleVOs = parameterVO.getCRAParameterConfigRuleVOs();
			if(cRAParameterConfigRuleVOs!=null && !cRAParameterConfigRuleVOs.isEmpty()){
				for(CRAParameterConfigRuleVO ruleVO : cRAParameterConfigRuleVOs){
					if(MRAConstantsVO.CRA_PAR_FUNPNT_MBG.equals(ruleVO.getFunctionPoint())){
					if(CRAParameterConfigRuleVO.FLAG_YES.equals(ruleVO.getInclude())){
						if(groupString != null){
						groupString.append(",").append(ruleVO.getConditionCode());
						}else{
						groupString = new StringBuilder(ruleVO.getConditionCode());
						}
					}
				 }
				} 
			}
			}
		}
		//groupString.replace(start, end, str);
		return groupString.toString().replace(",CSGDOCNUM", "");
	}
	/**
	 * 	Method		:	UpdateBillingStatusCommand.findCRAParameterDetails
	 *	Added by 	:	A-4809 on Mar 12, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode,craParInvgrp
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<CRAParameterVO>
	 */
	private Collection<CRAParameterVO> findCRAParameterDetails(String companyCode, String craParInvgrp) {
		Collection<CRAParameterVO> craParameterVOs = null;
		try{
			this.log.log(Log.FINE, "To invoke findCRAParameterDetails method");
			craParameterVOs = new MailTrackingMRADelegate().findCRAParameterDetails(companyCode,craParInvgrp);
			this.log.log(Log.FINE, "after invocation of findCRAParameterDetails method");
		}catch(BusinessDelegateException e) {
			this.log.log(Log.SEVERE, e.getMessage());  
		}
		return craParameterVOs;
	}
	/**
	 * 	Method		:	UpdateBillingStatusCommand.listConsignmentDetails
	 *	Added by 	:	A-4809 on Mar 12, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@param displayPage
	 *	Parameters	:	@return 
	 *	Return type	: 	Page<ConsignmentDocumentVO>
	 */
    private Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO filterVO, String displayPage) {
    	Page<ConsignmentDocumentVO> docVOs = null;
    	if(displayPage==null || displayPage.isEmpty()){
    		displayPage ="1"; 
    	}
    	int pageNumber = Integer.parseInt(displayPage);
    	filterVO.setPageNumber(pageNumber);
    	try {
    		this.log.log(Log.FINE, "To invoke listConsignmentDetails method");
			docVOs = new MailTrackingMRADelegate().listConsignmentDetails(filterVO);
			this.log.log(Log.FINE, "after invocation of listConsignmentDetails method");
		} catch (BusinessDelegateException e) {
			this.log.log(Log.SEVERE, e.getMessage());  
		}
		return docVOs;
	}	
	/**
	 * 	Method		:	UpdateBillingStatusCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-4809 on Mar 11, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
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
	/**
	 * 	Method		:	UpdateBillingStatusCommand.updateFilterVO
	 *	Added by 	:	A-4809 on Jan 30, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param gpaBillingEntryFilter
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	List<ErrorVO>
	 */
		private List<ErrorVO> updateFilterVO(GPABillingEntryFilter gpaBillingEntryFilter,GPABillingEntriesFilterVO filterVO, LogonAttributes logonAttributes) {
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
				//filterVO.setConDocNumber(gpaBillingEntryFilter.getConsignmentNumber());
		        if(gpaBillingEntryFilter.getConsignmentNumber()!=null && !gpaBillingEntryFilter.getConsignmentNumber().isEmpty()){
		        	StringBuilder csg = new StringBuilder("'").append(gpaBillingEntryFilter.getConsignmentNumber()).append("'");
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
				//filterVO.setDsnNumber(gpaBillingEntryFilter.getDsn());
				if(gpaBillingEntryFilter.getDsn()!=null && !gpaBillingEntryFilter.getDsn().isEmpty()){
					StringBuilder dsn = new StringBuilder("'").append(gpaBillingEntryFilter.getDsn()).append("'");
				filterVO.setDsnNumber(dsn.toString()); 
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
 * 	Method		:	UpdateBillingStatusCommand.listBillingDetails
 *	Added by 	:	A-4809 on Mar 11, 2019
 * 	Used for 	:
 *	Parameters	:	@param filterVO
 *	Parameters	:	@param displayPage
 *	Parameters	:	@return 
 *	Return type	: 	Page<DocumentBillingDetailsVO>
 */
		private Page<DocumentBillingDetailsVO> listBillingDetails(GPABillingEntriesFilterVO filterVO, String displayPage) {
			int pageNumber =0;
			if (displayPage!=null && !displayPage.isEmpty()){
				 pageNumber = Integer.parseInt(displayPage);
			}
			else{
				pageNumber = 1;
			}
			Page<DocumentBillingDetailsVO> documentBillingDetailsVOs =null;
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
}
