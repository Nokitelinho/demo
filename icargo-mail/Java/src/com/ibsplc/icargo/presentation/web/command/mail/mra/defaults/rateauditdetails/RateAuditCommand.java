/*
 * RateAuditCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.rateauditdetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.RateAuditDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.RateAuditDetailsForm;
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
public class RateAuditCommand  extends BaseCommand {
	
	private Log log = LogFactory.getLogger("RATE AUDITDETAILS RateAuditCommand");
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.rateauditdetails";
	private static final String RATEAUDIT_SUCCESS_INFO = "mailtracking.mra.defaults.rateauditdetails.rateauditedinfo";
	private static final String RECIEVEABLE = "R";
	private static final String PAYABLE = "P";	
	private static final String BILLING_PAR_CHANGED = "P";
	private static final String GROSS_WGT_CHANGED = "W";
	private static final String AUD_WGT_CAHRGE_CHANGED = "C";
	private static final String FINALIZED = "F";
	private static final String CURRENCY_CODE = "NZD";
	private static final String BILLABLE = "BB";
	private static final String OUTWARD_BILLABLE = "OB";
	private static final String UPDATE = "U";
	private static final String INSERT = "I";
	private static final String AIRLINE = "A";
	private static final String GPA = "G";
	private static final String ACTUAL = "A";
	private static final String YES = "Y";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 
		 log.entering("RateAuditCommand", "execute");
		 Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		 MailTrackingMRADelegate delegate=new MailTrackingMRADelegate();
		 RateAuditDetailsForm rateAuditDetailsForm=(RateAuditDetailsForm)invocationContext.screenModel;
		 RateAuditDetailsSession rateAuditDetailsSession=getScreenSession(MODULE,SCREENID);
		 ApplicationSessionImpl applicationSession = getApplicationSession();
		 LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 log.log(Log.INFO, "rateAuditDetailsSession.getParChangeFlag()--->>",
				rateAuditDetailsSession.getParChangeFlag());
		if(rateAuditDetailsSession.getParChangeFlag()!=null&&rateAuditDetailsSession.getParChangeFlag().contains(BILLING_PAR_CHANGED)||rateAuditDetailsSession.getParChangeFlag().contains(GROSS_WGT_CHANGED)||rateAuditDetailsSession.getParChangeFlag().contains(AUD_WGT_CAHRGE_CHANGED)){	
				errors = new ArrayList<ErrorVO>();
				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.mustcomputetot");				
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}

		 RateAuditVO rateAuditVOInSession=new RateAuditVO();
		 rateAuditVOInSession=rateAuditDetailsSession.getRateAuditVO();
		 Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
			rateAuditDetailsVOs = rateAuditVOInSession.getRateAuditDetails();			
			for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
				if(!"T".equals(rateAuditDetailsVO.getPayFlag())){
					if(PAYABLE.equals(rateAuditDetailsVO.getPayFlag())){
						rateAuditDetailsVO.setBillingStatus("IU");
					}
					if(RECIEVEABLE.equals(rateAuditDetailsVO.getPayFlag())){						
						
						if(GPA.equals(rateAuditDetailsVO.getGpaarlBillingFlag())){
						
							PostalAdministrationDetailsVO postalAdministrationDetailsVO = new PostalAdministrationDetailsVO();
							
							postalAdministrationDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							postalAdministrationDetailsVO.setPoaCode(rateAuditDetailsVO.getBillTO());
							postalAdministrationDetailsVO.setParCode("BLGINFO");
							postalAdministrationDetailsVO.setValidFrom(rateAuditDetailsVO.getRecVDate());
							
							PostalAdministrationDetailsVO pADetailsVO = null;
							try {
								pADetailsVO = delegate.validatePoaDetailsForBilling(postalAdministrationDetailsVO);
								 log
										.log(
												Log.INFO,
												"validatePoaDetailsForBilling---[pADetailsVO]-->>",
												pADetailsVO);
							} catch (BusinessDelegateException e) {
								log.log(Log.SEVERE,"validatePoaDetailsForBilling---FAILED-->>");
								e.getMessage();
							}
							if(pADetailsVO==null){
								errors = new ArrayList<ErrorVO>();
		            			Object[] obj = {postalAdministrationDetailsVO.getValidFrom().toDisplayDateOnlyFormat()};
		        				ErrorVO errorVO = new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.gpanotconfigured",obj);
		        				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		        				errors.add(errorVO);
		            			invocationContext.addAllError(errors);
		            			invocationContext.target = SCREENLOAD_SUCCESS;    			
		            			return;
							}
							if(pADetailsVO!=null){
								if(pADetailsVO.getBillingSource()!=null &&  "R".equals(pADetailsVO.getBillingSource())){									
									rateAuditDetailsVO.setBillingStatus("TR");									
								}else if(pADetailsVO.getBillingSource()!=null &&  "B".equals(pADetailsVO.getBillingSource())){									
									rateAuditDetailsVO.setBillingStatus(BILLABLE);									
								}								
							}								
							
						}else{
							rateAuditDetailsVO.setBillingStatus(OUTWARD_BILLABLE);
						}
					
					}
					
				}
			}	 
			 rateAuditVOInSession.setDsnStatus(FINALIZED);
			 rateAuditDetailsForm.setDsnStatus("Rate Audited");			 
			 
			if(rateAuditDetailsSession.getBillToChgFlag()!= null && "Y".equals(rateAuditDetailsSession.getBillToChgFlag())){
				 rateAuditVOInSession.setRaiseCCAFlag("Y");
			}else if(rateAuditVOInSession.getDiscrepancyNo()!=null 
						&& rateAuditVOInSession.getDiscrepancyNo().trim().length()>0 
						&& (!"Y".equals(rateAuditVOInSession.getDiscrepancyNo()))
						&& ("on".equals(rateAuditDetailsForm.getApplyAudit())||"Y".equals(rateAuditDetailsForm.getApplyAudit()))){	
					
			    rateAuditVOInSession.setRaiseCCAFlag("Y");		
			} 
			rateAuditVOInSession.setFromRateAudit("fromrateaudit"); 
			 try {
				    rateAuditVOInSession.setOperationFlag(UPDATE);	
				    rateAuditVOInSession.setSaveToHistoryFlg("Y");
				    rateAuditVOInSession.setCompTotTrigPt("RA");
					delegate.saveRateAuditDetails(rateAuditVOInSession);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}	
		 log.log(Log.INFO, "rateAuditDetailsForm.getDiscrepancy()--->>",
				rateAuditDetailsForm.getDiscrepancy());
		log.log(Log.INFO, "rateAuditDetailsForm.getApplyAudit()--->>",
				rateAuditDetailsForm.getApplyAudit());
		invocationContext.addError(new ErrorVO(RATEAUDIT_SUCCESS_INFO));
		 invocationContext.target = SCREENLOAD_SUCCESS;
		 log.exiting("RateAuditCommand", "execute");
		 
	 }


}
