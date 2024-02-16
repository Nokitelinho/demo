/*
 * SaveCommand.java Created on July 1, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MaintainConsignmentModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class SaveCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/**
	 * This method overrides the execute method of BaseComand class
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(ActionContext actionContext) throws CommandInvocationException {

		log.entering("SaveCommand", "execute");
		MaintainConsignmentModel maintainConsignmentModel = (MaintainConsignmentModel) actionContext.getScreenModel();

		
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ArrayList results = new ArrayList();
		ResponseVO responseVO = new ResponseVO();
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if (oneTimes != null) {
			List<String> sortedOnetimes;
			Collection<OneTimeVO> catVOs = oneTimes.get("mailtracking.defaults.mailcategory");
			Collection<OneTimeVO> hniVOs = oneTimes.get("mailtracking.defaults.highestnumbermail");
			Collection<OneTimeVO> rsnVOs = oneTimes.get("mailtracking.defaults.registeredorinsuredcode");
			Collection<OneTimeVO> mailClassVOs = oneTimes.get("mailtracking.defaults.mailclass");
			Collection<OneTimeVO> typeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.type");
			// Added as part of CRQ ICRD-103713 by A-5526
			Collection<OneTimeVO> subTypeVOs = oneTimes.get("mailtracking.defaults.consignmentdocument.subtype");
			
			if (hniVOs != null && !hniVOs.isEmpty()) {
				sortedOnetimes = new ArrayList<String>();
				for (OneTimeVO hniVo : hniVOs) {
					sortedOnetimes.add(hniVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);

				int i = 0;
				for (OneTimeVO hniVo : hniVOs) {
					hniVo.setFieldValue(sortedOnetimes.get(i++));
				}
			}
			if (rsnVOs != null && !rsnVOs.isEmpty()) {
				sortedOnetimes = new ArrayList<String>();
				for (OneTimeVO riVo : rsnVOs) {
					sortedOnetimes.add(riVo.getFieldValue());
				}
				Collections.sort(sortedOnetimes);

				int i = 0;
				for (OneTimeVO riVo : rsnVOs) {
					riVo.setFieldValue(sortedOnetimes.get(i++));
				}
			}
			maintainConsignmentModel.setOneTimeCat(catVOs);
			maintainConsignmentModel.setOneTimeRSN(rsnVOs);
			maintainConsignmentModel.setOneTimeHNI(hniVOs);
			maintainConsignmentModel.setOneTimeMailClass(mailClassVOs);
			maintainConsignmentModel.setOneTimeType(typeVOs);
			// Added as part of CRQ ICRD-103713 by A-5526
			maintainConsignmentModel.setOneTimeSubType(subTypeVOs);
		}

		
		maintainConsignmentModel.setConsignment(null);
		// Added for Unit Component
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		maintainConsignmentModel.setWeightRoundingVO(unitRoundingVO);
		setUnitComponent(logonAttributes.getStationCode(), maintainConsignmentModel);

		maintainConsignmentModel.setSelectMail(null);		
		maintainConsignmentModel.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		results.add(maintainConsignmentModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);
		
		log.exiting("SaveCommand", "execute");
	}

	
	/**
	 * A-3251
	 * 
	 * @param stationCode
	 * @param mailAcceptanceSession
	 * @return
	 */
	private void setUnitComponent(String stationCode, MaintainConsignmentModel maintainConsignmentModel) {
		UnitRoundingVO unitRoundingVO = null;
		try {
			log.log(Log.FINE, "station code is ----------->>", stationCode);
			unitRoundingVO = UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.MAIL_WGT,
					UnitConstants.WEIGHT_MAIL_UNIT);
			log.log(Log.FINE, "unit vo for wt--in session---", unitRoundingVO);
			maintainConsignmentModel.setWeightRoundingVO(unitRoundingVO);

		} catch (UnitException unitException) {
			unitException.getErrorCode();
		}
	}

	/**
	 * This method will be invoked at the time of screen load
	 * 
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		// Collection<ErrorVO> errors = null;
		try {
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add("mailtracking.defaults.registeredorinsuredcode");
			fieldValues.add("mailtracking.defaults.mailcategory");
			fieldValues.add("mailtracking.defaults.highestnumbermail");
			fieldValues.add("mailtracking.defaults.mailclass");
			fieldValues.add("mailtracking.defaults.consignmentdocument.type");
			// Added as part of CRQ ICRD-103713 by A-5526
			fieldValues.add("mailtracking.defaults.consignmentdocument.subtype");
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode, fieldValues);
		} catch (BusinessDelegateException businessDelegateException) {
			// errors = handleDelegateException(businessDelegateException);
			handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	
}
