/*
 * GPABlgDtlsCommand.java created on Jul 10, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2391
 *
 */
public class GPABlgDtlsCommand extends BaseCommand{
	private  Log log = LogFactory.getLogger("MRA DEFAULTS DespatchEnquiry");

	private static final String CLASS_NAME = "GPABlgDtlsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";

	private static final String SCREEN_SUCCESS = "screenload_success";
	
	private static final String NO_RESULTS = "mailtracking.mra.defaults.despatchenquiry.nogparesultsfound";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String DSNLEVEL_IMPORT_TOMRA="mailtracking.defaults.DsnLevelImportToMRA";
	

	
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		DSNPopUpSession popUpSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		DSNPopUpVO popUpVO = null;
		popUpVO = popUpSession.getSelectedDespatchDetails();
		if(popUpVO == null) {
			popUpVO = despatchEnqSession.getDispatchFilterVO();
		}
		if(popUpVO != null) {
			despatchEnquiryForm.setDespatchNum(popUpVO.getBlgBasis());
			despatchEnquiryForm.setDsnFilterDate(popUpVO.getDsnDate());
		}		
		
		if(despatchEnquiryForm.getListed()!=null  && 
				despatchEnquiryForm.getListed().trim().length()>0 &&
				("N".equals(despatchEnquiryForm.getListed())) && 
				!("".equals(despatchEnquiryForm.getListed()))){
			 despatchEnquiryForm.setListed("N");
			invocationContext.target=LIST_FAILURE;
			return;
		}else{
			MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
			ArrayList<GPABillingDetailsVO> gPABillingDetailsVOs=null;
			try {
				gPABillingDetailsVOs=new ArrayList<GPABillingDetailsVO>(delegate.findGPABillingDetails(popUpVO));


			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
			if(gPABillingDetailsVOs!=null && gPABillingDetailsVOs.size()>0){
				checkDespatchSystemParameter(gPABillingDetailsVOs);      //Added for ICRD-147813
				//popUpSession.removeSelectedDespatchDetails();
				int siz=gPABillingDetailsVOs.size();
				for(int i=0;i<siz;i++){
					GPABillingDetailsVO vo=new GPABillingDetailsVO();
					vo=gPABillingDetailsVOs.get(i);
					if(vo.getBillingStatus()!=null){
						if( !("").equals(vo.getBillingStatus()) &&(vo.getBillingStatus().trim().length()>0 )) {
							vo.setBillingStatus(getBlgStatusDesc(vo.getBillingStatus(),despatchEnqSession));
						}
					}
					if(vo.getInvStatus()!=null){
						if( !("").equals(vo.getInvStatus()) &&(vo.getInvStatus().trim().length()>0 )) {
							vo.setInvStatus(getInvStatusDesc(vo.getInvStatus(),despatchEnqSession));
						}
					}
					gPABillingDetailsVOs.set(i,vo);
				}
				despatchEnqSession.setGPABillingDtls(gPABillingDetailsVOs);
				GPABillingDetailsVO gPABillingDetailsVO=new GPABillingDetailsVO();
				gPABillingDetailsVO=gPABillingDetailsVOs.get(0);
				despatchEnquiryForm.setGpaCode(gPABillingDetailsVO.getGpaCode());
				despatchEnquiryForm.setGpaName(gPABillingDetailsVO.getGpaName());
				despatchEnquiryForm.setCurrency(gPABillingDetailsVO.getCurrencyCode());
				despatchEnquiryForm.setRemarks(gPABillingDetailsVO.getRemarks());
				despatchEnquiryForm.setFinYear(String.valueOf(gPABillingDetailsVO.getYear()));
				despatchEnquiryForm.setListed("Y");
				invocationContext.target=SCREEN_SUCCESS;
			}
			else{
				if(errors!=null){
					//popUpSession.removeSelectedDespatchDetails();
					ErrorVO err=new ErrorVO(NO_RESULTS);
					despatchEnqSession.removeGPABillingDtls();
					despatchEnquiryForm.setListed("ERROR");
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
					invocationContext.addAllError(errors);
					invocationContext.target=LIST_FAILURE;
				}
			}		
		}
		log.exiting(CLASS_NAME,"execute");
	}
	private String getBlgStatusDesc(String val,
			DespatchEnqSession session) {
		String desc = null;
		if(session.getOneTimeVOs()!=null) {
			Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
			"mailtracking.mra.gpabilling.gpabillingstatus");
			for (OneTimeVO oneTimeVO : oneTimeVOs) {
				if (oneTimeVO.getFieldValue().equalsIgnoreCase(val)) {
					desc = oneTimeVO.getFieldDescription();
				}
			}
		}
		return desc;

	}
	private String getInvStatusDesc(String val,
			DespatchEnqSession session) {
		String desc = null;
		if(session.getOneTimeVOs()!=null) {
			Collection<OneTimeVO> oneTimeVOs = session.getOneTimeVOs().get(
			"mra.gpabilling.invoicestatus");
			for (OneTimeVO oneTimeVO : oneTimeVOs) {
				if (oneTimeVO.getFieldValue().equalsIgnoreCase(val)) {
					desc = oneTimeVO.getFieldDescription();
				}
			}
		}
		return desc;

	}
	
	/**Checking DSNLEVEL_IMPORT_TOMRA 
	 * Added for ICRD-147813
	 * @param gPABillingDetailsVOs
	 */
	private void checkDespatchSystemParameter(ArrayList<GPABillingDetailsVO> gPABillingDetailsVOs){
		Collection<String> parameterCodes = new ArrayList<String>();
	      HashMap<String, String> systemParameterCodes = new HashMap<String, String>();
	      Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	      parameterCodes.add(DSNLEVEL_IMPORT_TOMRA);
	      try {
	                  systemParameterCodes = (HashMap<String, String>) (new SharedDefaultsDelegate().findSystemParameterByCodes(parameterCodes));   
	            }catch(BusinessDelegateException ex){
	                  errors.addAll(handleDelegateException(ex));
	            }
	            String fileGenerationDateSysPar = systemParameterCodes.get(DSNLEVEL_IMPORT_TOMRA);
	            if("Y".equals(fileGenerationDateSysPar)){
	            	for(GPABillingDetailsVO gpaBillingDetailsVO : gPABillingDetailsVOs){
	            		gpaBillingDetailsVO.setDespatchId(gpaBillingDetailsVO.getDsn());
	            	}
	            }else if("N".equals(fileGenerationDateSysPar)){
	            	for(GPABillingDetailsVO gpaBillingDetailsVO : gPABillingDetailsVOs){
	            		gpaBillingDetailsVO.setDespatchId(gpaBillingDetailsVO.getBillingBase().toString());
		       	      }
	            }
	      
	     
	}
}
