package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry.SaveActualWeightCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8672	:	08-Feb-2019	:	Draft
 */
public class SaveActualWeightCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail Operations");
	private static final String STNPAR_DEFUNIT_WGT = "station.defaults.unit.weight";
	private static final String MAILBAG_RETURNED_ERR = "mailtracking.defaults.err.mailbagreturned";
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		  log.entering("SaveContentIDCommand","execute");
		  
		  LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		  MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		  MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		  ResponseVO responseVO = new ResponseVO();

		  Collection<Mailbag> selectedMailbags = null;
		  ErrorVO errorVO = null;
		  String actualWeightDefaultUnit=null;

		  ArrayList<ErrorVO> errors = new ArrayList<ErrorVO>();

		  if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null) {

			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");
			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			actualWeightDefaultUnit=findStationParameterValue(STNPAR_DEFUNIT_WGT);
			
			for(Mailbag selectedMailBag : selectedMailbags){
				if(selectedMailBag.getLatestStatus().equals(MailConstantsVO.MAIL_STATUS_RETURNED)){
					Object[] obj = {new StringBuilder(selectedMailBag.getMailbagId())};				
					errors.add(new ErrorVO(MAILBAG_RETURNED_ERR,obj));
					
		 	   		
				} else{
				selectedMailBag.setActualWeightUnit(actualWeightDefaultUnit);
			MailbagVO mailbagVO = MailOperationsModelConverter.constructMailbagVO(selectedMailBag,
					logonAttributes);
			
			mailTrackingDefaultsDelegate.updateActualWeightForMailbag(mailbagVO);
				}
	    	   
	       }}
	       if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}
	       responseVO.setStatus("success");
	       actionContext.setResponseVO(responseVO);
	       
		  log.exiting("SaveContentIDCommand","execute");
		
	}	

	private String findSystemParameterValue(String syspar) throws SystemException, BusinessDelegateException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
				.findSystemParameterByCodes(systemParameters);
		log.log(Log.FINE, " systemParameterMap ", systemParameterMap);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}
	/**
	 * @author A-7779
	 * @param stnPar
	 * @return
	 */
	private String findStationParameterValue(String stnPar){
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		AreaDelegate areaDelegate = new AreaDelegate();
		//String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL); 
		Map stationParameters = null; 
		String stationCode = logonAttributes.getStationCode();	
		Collection<String> parameterCodes = new ArrayList<String>();
		parameterCodes.add(STNPAR_DEFUNIT_WGT);
		try {
			stationParameters = areaDelegate.findStationParametersByCode(logonAttributes.getCompanyCode(), stationCode, parameterCodes);
		} catch (BusinessDelegateException e1) {
			e1.getMessage();
		} 
		return (String)stationParameters.get(STNPAR_DEFUNIT_WGT);
	}
}

