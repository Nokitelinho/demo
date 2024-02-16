
/*
 * NavigateCommand.java Created on Sep 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNRoutingSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;

import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 *
 */
public class NavigateCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA PRORATION");
	/**
	 * Strings for SCREEN_ID and MODULE_NAME
	 */
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/*
	 * String for CLASS_NAME
	 */
	private static final String CLASS_NAME="NavigateCommand";


	///for dispatch enquiry

	private static final String SCREENID_DISPATCHENQ = "mailtracking.mra.defaults.despatchenquiry";
	private static final String  TODISPATCHENQUIRY="todispatchenquiry";

	//for despatch routing
	private static final String SCREEN_ID_ROUTING = "mailtracking.mra.defaults.despatchrouting";
	private static final String TOROUTING="todespatchrouting";

	//for manual proartion
	private static final String SCREEN_ID_MANUALPRO = "mailtracking.mra.defaults.manualproration";
	private static final String TOMANUALPRORATION="tomanualproration";
	//Added as part of ICRD-341226
	private static final String SCREENID_DSN_POPUP = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String MODULE = "mailtracking.mra.defaults";


	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {


		ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
		ListProrationExceptionsSession listExceptionSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		ProrationExceptionVO prorationExceptionVO=null;
		MailTrackingMRADelegate mailTrackingMRADelegate=new MailTrackingMRADelegate();
		DSNPopUpVO popUpVO = null;
		DSNPopUpSession dsnPopUpSession = getScreenSession(MODULE,
				SCREENID_DSN_POPUP);
		listExceptionSession.setFromScreenFlag("Y");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(listExceptionForm.getRowId()!=null){
			int index=0;
			Collection<ProrationExceptionVO> prorateExceptionsFromSession =
				(Collection<ProrationExceptionVO>)listExceptionSession.getProrationExceptionVOs();
			if(prorateExceptionsFromSession!=null && prorateExceptionsFromSession.size()>0){

				for(ProrationExceptionVO proExceptionVO:prorateExceptionsFromSession){

					if(Integer.parseInt(
							listExceptionForm.getRowId()[0])==index){
						log.log(Log.FINE,
								"Selected exceptionExceptionVO frm VOs>>",
								proExceptionVO);
						prorationExceptionVO=new ProrationExceptionVO();
						prorationExceptionVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
						prorationExceptionVO.setMailbagId(proExceptionVO.getMailbagId());
						prorationExceptionVO.setDispatchNo(proExceptionVO.getDispatchNo());
						prorationExceptionVO.setFlightDate(proExceptionVO.getFlightDate());
						prorationExceptionVO.setConsDocNo(proExceptionVO.getConsDocNo());
						prorationExceptionVO.setConsSeqNo(proExceptionVO.getConsSeqNo());
						prorationExceptionVO.setPoaCode(proExceptionVO.getPoaCode());
						prorationExceptionVO.setDate(proExceptionVO.getDate());

					}
					index++;
				}
			}
			if(prorationExceptionVO !=null){
				if("dispatchenquiry".equals(listExceptionForm.getClickedButton())){
					DespatchEnqSession despatchEnqSession=getScreenSession(MODULE_NAME,SCREENID_DISPATCHENQ);
					popUpVO=new DSNPopUpVO();
					popUpVO.setCompanyCode(prorationExceptionVO.getCompanyCode());
					popUpVO.setBlgBasis(prorationExceptionVO.getMailbagId());
					popUpVO.setDsn(prorationExceptionVO.getDispatchNo());
					popUpVO.setDsnDate(prorationExceptionVO.getFlightDate().toDisplayDateOnlyFormat());
					popUpVO.setCsgdocnum(prorationExceptionVO.getConsDocNo());
					popUpVO.setCsgseqnum(prorationExceptionVO.getConsSeqNo());
					popUpVO.setGpaCode(prorationExceptionVO.getPoaCode());
					despatchEnqSession.setDispatchFilterVO(popUpVO);
					
					invocationContext.target = TODISPATCHENQUIRY;
				}
				else if("routing".equals(listExceptionForm.getClickedButton())){
					SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
					LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			    	String companyCode = logonAttributes.getCompanyCode();
					Map<String, Collection<OneTimeVO>> hashMap = 
			    		      new HashMap<String, Collection<OneTimeVO>>();
			    	 Collection<String> oneTimeList = new ArrayList();
			    	 oneTimeList.add("mailtracking.defaults.agreementtype");
			    	 try
			    	    {
			    	      hashMap = defaultsDelegate.findOneTimeValues(companyCode, 
			    	        oneTimeList);
			    	    }
			    	    catch (BusinessDelegateException localBusinessDelegateException3)
			    	    {
			    	      this.log.log(7, "onetime fetch exception");
			    	    }
			    	Collection<OneTimeVO> agreementTypes = (Collection<OneTimeVO>)hashMap.get("mailtracking.defaults.agreementtype");
					DSNRoutingSession  dsnRoutingSession =
						(DSNRoutingSession)getScreenSession(MODULE_NAME, SCREEN_ID_ROUTING);

					DSNRoutingFilterVO dsnRoutingFilterVO = new DSNRoutingFilterVO();
					dsnRoutingFilterVO.setCompanyCode(prorationExceptionVO.getCompanyCode());
					dsnRoutingFilterVO.setBillingBasis(prorationExceptionVO.getMailbagId());
					dsnRoutingFilterVO.setDsn(prorationExceptionVO.getDispatchNo());
					dsnRoutingFilterVO.setDsnDate(prorationExceptionVO.getDate());
					dsnRoutingFilterVO.setCsgDocumentNumber(prorationExceptionVO.getConsDocNo());
					dsnRoutingFilterVO.setCsgSequenceNumber(prorationExceptionVO.getConsSeqNo());
					dsnRoutingFilterVO.setPoaCode(prorationExceptionVO.getPoaCode());
					dsnRoutingSession.setDSNRoutingFilterVO(dsnRoutingFilterVO);
					dsnRoutingSession.setAgreementTypes((ArrayList<OneTimeVO>)agreementTypes);
					 //Added as part of ICRD-341226 starts
					Page<DSNPopUpVO> despatchLovVOs = null;
					DSNPopUpVO dsnPopUpVO = new DSNPopUpVO();
					try {
						String dsnNumber = prorationExceptionVO.getMailbagId();
						String dsnDate="";
						int pageNumber = 1;
						despatchLovVOs = mailTrackingMRADelegate.findDsnSelectLov(companyCode, dsnNumber, dsnDate, pageNumber);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if (despatchLovVOs != null && despatchLovVOs.size() > 0) {
						
						if (despatchLovVOs.size() == 1) {
							for (DSNPopUpVO dsnPopUp : despatchLovVOs) {
								try {
									BeanHelper.copyProperties(dsnPopUpVO, dsnPopUp);
								} catch (SystemException e) {
									
								}
							}
							dsnPopUpSession.setSelectedDespatchDetails(dsnPopUpVO);
							
						}
					}
			      //Added as part of ICRD-341226 ends
					invocationContext.target = TOROUTING;
				}
				else if("manualproration".equals(listExceptionForm.getClickedButton())){
					ManualProrationSession manualProrationSession = (ManualProrationSession) getScreenSession(
							MODULE_NAME, SCREEN_ID_MANUALPRO);

					/*popUpVO=new DSNPopUpVO();
					popUpVO.setCompanyCode(prorationExceptionVO.getCompanyCode());
					popUpVO.setBlgBasis(prorationExceptionVO.getDispatchNo());
					popUpVO.setDsn(prorationExceptionVO.getDispatchNo());
					popUpVO.setDsnDate(prorationExceptionVO.getFlightDate().toDisplayDateOnlyFormat());
					popUpVO.setCsgdocnum(prorationExceptionVO.getConsDocNo());
					popUpVO.setCsgseqnum(prorationExceptionVO.getConsSeqNo());
					popUpVO.setGpaCode(prorationExceptionVO.getPoaCode());*/
					ProrationFilterVO prorationFilterVO = new ProrationFilterVO();
					prorationFilterVO.setCompanyCode(prorationExceptionVO.getCompanyCode());
					prorationFilterVO.setBillingBasis(prorationExceptionVO.getMailbagId());
					prorationFilterVO.setDespatchSerialNumber(prorationExceptionVO.getDispatchNo());
					prorationFilterVO.setConsigneeDocumentNumber(prorationExceptionVO.getConsDocNo());
					prorationFilterVO.setConsigneeSequenceNumber(prorationExceptionVO.getConsSeqNo());
					prorationFilterVO.setPoaCode(prorationExceptionVO.getPoaCode());
					
					manualProrationSession.setProrationFilterVO(prorationFilterVO);
					log.log(Log.INFO, "prorationFilterVO------>",
							manualProrationSession.getProrationFilterVO());
					invocationContext.target = TOMANUALPRORATION;
				}
			}
		}
	}

}
