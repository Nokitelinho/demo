/* SendSCMCommand.java Created on Aug 01,2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P)Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.scmreconcile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMMessageFilterVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.msgbroker.message.ListMessageSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.SCMReconcileSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.SCMReconcileForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class SendSCMCommand extends BaseCommand {

	private static final String MODULE = "uld.defaults";

	/**
	 * Screen Id of UCM Error logs
	 */
	private static final String SCREENID = "uld.defaults.scmreconcile";
	private static final String CRLF = "\r\n";
	private static final String SEND_SUCCESS = "send_success";
	
	private static final String SEND_FAILED = "send_success";

	private Log log = LogFactory.getLogger("UCM_MESSAGING");

	private static final String MSGMODULE_NAME = "msgbroker.message";
	
	private static final String MSGSCREEN_ID = "msgbroker.message.listmessages";
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		/**
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		//Commented by Manaf for INT ULD510
		//LogonAttributes logonAttributes = applicationSession.getLogonVO();
		//String compCode = logonAttributes.getCompanyCode();

		SCMReconcileForm scmReconcileForm = (SCMReconcileForm) invocationContext.screenModel;
		SCMReconcileSession scmReconcileSession = getScreenSession(MODULE,
				SCREENID);
		ListMessageSession msgsession = 
			getScreenSession( MSGMODULE_NAME,MSGSCREEN_ID);
		Page<ULDSCMReconcileVO> scmReconcileVOs = scmReconcileSession
				.getSCMReconcileVOs();
		log.log(Log.FINE, "selectedReconcileVO ----------------->",
				scmReconcileForm.getSelectedSCMErrorLog());
		ULDSCMReconcileVO selectedReconcileVO = scmReconcileVOs.get(Integer
				.parseInt(scmReconcileForm.getSelectedSCMErrorLog()[0]));
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "selectedReconcileVO ----------------->",
				selectedReconcileVO);
		if ("S".equals(selectedReconcileVO.getMessageSendFlag())) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.messaging.scmreconcile.msg.err.msgalreadysend");
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = SEND_FAILED;
			return;
		}
		SCMMessageFilterVO scmFilterVO = new SCMMessageFilterVO();
		scmFilterVO.setAirportCode(selectedReconcileVO.getAirportCode());
		scmFilterVO.setCompanyCode(selectedReconcileVO.getCompanyCode());
		scmFilterVO
				.setStockControlDate(selectedReconcileVO.getStockCheckDate());
		log.log(Log.FINE, "\n\n\nAirline Identifier---------------->",
				selectedReconcileVO.getAirlineIdentifier());
		scmFilterVO.setFlightCarrierIdentifier(selectedReconcileVO
				.getAirlineIdentifier());
		scmFilterVO.setSequenceNumber(selectedReconcileVO.getSequenceNumber());
		scmFilterVO.setPageNumber(1);
		scmReconcileForm.setRowIndex("");
		log.log(Log.FINE, "Filter vo to server-=--------------->", scmFilterVO);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();		
		//changes by nisha starts
		Collection<MessageVO> msgVOs=null;
		Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs = msgsession.getDespatchDetails();
		try {
			msgVOs = delegate.sendSCMMessage(scmFilterVO,additionaldespatchDetailsVOs);
			if(msgVOs!=null && msgVOs.size()>0){
				ArrayList<MessageVO> msgeVOs =new ArrayList<MessageVO>(msgVOs);
				log.log(Log.INFO, "encode VO from server-->", msgeVOs.size());
				log.log(Log.INFO, "encode VO from server-->", msgeVOs.get(0));
				//added A-6344 for ICRD-100244 start
				/*Collection<MessageDespatchDetailsVO> additionaldespatchDetailsVOs = msgsession.getDespatchDetails();
				Collection<MessageDespatchDetailsVO> despatchDetailsVOs = new ArrayList<MessageDespatchDetailsVO>();
				if(additionaldespatchDetailsVOs!=null && additionaldespatchDetailsVOs.size() > 0){
					despatchDetailsVOs.addAll(additionaldespatchDetailsVOs);
				}*/
				//added A-6344 for ICRD-100244 start
				msgsession.setMessage(msgeVOs.get(0));
				msgsession.removeDespatchDetails();
				msgsession.removeMessageVO();
				scmReconcileForm.setMsgFlag("TRUE");
				// Added by Preet on 09Dec08 for bug 29461 starts
				ULDSCMReconcileVO scmMsgRecVO = new ULDSCMReconcileVO();
				scmMsgRecVO.setCompanyCode(scmFilterVO.getCompanyCode());
				scmMsgRecVO.setAirlineIdentifier(scmFilterVO.getFlightCarrierIdentifier());
				scmMsgRecVO.setAirportCode(scmFilterVO.getAirportCode());
				scmMsgRecVO.setSequenceNumber(scmFilterVO.getSequenceNumber());
				scmMsgRecVO.setStockCheckDate(selectedReconcileVO.getStockCheckDate());
				msgsession.setSCMReconcileVO(scmMsgRecVO);
				// Added by Preet on 09Dec08 for bug 29461 ends
				MessageVO messageVO = msgsession.getMessage();
				/*try{
						populateMessageVos(messageVO,despatchDetailsVOs);
				}
				catch(BusinessDelegateException ex){
					ex.getMessage();
					ErrorVO errVO=new ErrorVO("uld.defaults.messaging.generatescm.MessageSendingFailed");
					errors.add(errVO);
					invocationContext.addAllError(errors);
					invocationContext.target = SEND_FAILED;
					return;
				}*/
				
				// to do....set the raw msg in form
			}
		} catch (BusinessDelegateException ex) {
			ex.getMessage();
			errors = handleDelegateException(ex);
		}
		
//		changes by nisha ends
		invocationContext.addAllError(errors);
		invocationContext.target = SEND_SUCCESS;
	}

	private void populateMessageVos(MessageVO messageVO,
			Collection<MessageDespatchDetailsVO> despatchDetailsVOs)
			throws BusinessDelegateException {
		if (messageVO.getRawMessage().endsWith(CRLF)) {
			messageVO.setOriginalMessage(messageVO.getRawMessage());
		} else {
			messageVO
					.setRawMessage(new StringBuilder(messageVO.getRawMessage())
							.append(CRLF).toString());
			messageVO.setOriginalMessage(new StringBuilder(messageVO
					.getRawMessage()).append(CRLF).toString());
		}
		Collection<MessageVO> msgVos = new ArrayList<MessageVO>();
		Collection<MessageDespatchDetailsVO> removeDespatchDetailsVOs = new ArrayList<MessageDespatchDetailsVO>();
		log.log(Log.INFO, "despatch details from session=---->",
				despatchDetailsVOs);
		if (despatchDetailsVOs != null) {
			for (MessageDespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
				if (despatchDetailsVO.getOperationFlag() != null
						&& "D".equals(despatchDetailsVO.getOperationFlag())) {
					removeDespatchDetailsVOs.add(despatchDetailsVO);
				}
			}
			despatchDetailsVOs.removeAll(removeDespatchDetailsVOs);
			log.log(Log.INFO,
					"despatch details after updation=--------------------------------->",
					despatchDetailsVOs);
			messageVO.setDespatchDetails(despatchDetailsVOs);
		}
		msgVos.add(messageVO);
		ULDDefaultsDelegate uldDelegate = new ULDDefaultsDelegate();
		uldDelegate.sendMessageWithEnvelopeEncoding(msgVos);
	}
}
