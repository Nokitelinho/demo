/*
 * ListMailDamageCommand.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import static com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History Revision Date Author Description 0.1 Jun 07, 2018 A-2257
 * First draft
 */

public class ListMailDamageCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("ListMailDamageCommand");

	/**
	 * 
	 */
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		log.entering("ListMailDamageCommand", "execute");

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();

		MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();

		Collection<Mailbag> selectedMailbags = null;
		ArrayList<MailbagVO> mailbagVOs = null;
		
		Collection<DamagedMailbagVO> damagedMailbagVOs = null;
		Collection<DamagedMailbag> damagedMailbags = null;
		
		ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();	
		ArrayList results = new ArrayList();	

		if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");

			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			log.log(Log.FINE, "selectedMailbags --------->>", selectedMailbags);

			mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags,logonAttributes);
			AirportValidationVO airportValidationVO = new AirportValidationVO();
			airportValidationVO = new AreaDelegate().validateAirportCode(
					logonAttributes.getCompanyCode(),
					logonAttributes.getAirportCode());
			String countryCode = airportValidationVO.getCountryCode();
		Collection<PostalAdministrationVO> postalAdministrationVOs = new MailTrackingDefaultsDelegate().findLocalPAs(
				logonAttributes.getCompanyCode(),
				countryCode);
		MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
		mailbagEnquiryModel.setPostalAdministrations(mailOperationsModelConverter.convertPostalAdministartionVos(postalAdministrationVOs));	
			
			for(MailbagVO mailbagVO : mailbagVOs){
				if(MAIL_STATUS_CAP_NOT_ACCEPTED.equalsIgnoreCase(mailbagVO.getLatestStatus())) {
		    		ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.capturedbutnotaccepted");	
		    		actionContext.addError(errorVO);
		    		return;
				}else{
					try {
						damagedMailbagVOs = mailTrackingDefaultsDelegate.findMailbagDamages(mailbagVO.getCompanyCode(),mailbagVO.getMailbagId());
					} catch (BusinessDelegateException businessDelegateException) {
						errors = (ArrayList) handleDelegateException(businessDelegateException);
					}
				}
				
				if(damagedMailbagVOs!=null && damagedMailbagVOs.size()>0){
					
					damagedMailbags = MailOperationsModelConverter.constructDamagedMailbagsDetails(damagedMailbagVOs, logonAttributes);
					mailbagEnquiryModel.setDamagedMailbags(damagedMailbags);
					results.add(mailbagEnquiryModel);
					responseVO.setResults(results);
				}
			}

			

		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError(errors);
			return;
		}

		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		log.exiting("ListMailDamageCommand", "execute");

	}
	

}
