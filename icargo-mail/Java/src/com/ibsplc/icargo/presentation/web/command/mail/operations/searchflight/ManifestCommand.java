/*
 * ManifestCommand.java Created on July 17, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.SearchFlightSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.SearchFlightForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-3817
 * 
 */
public class ManifestCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	private static final String SCREEN_ID_ARRIVAL = "mailtracking.defaults.mailarrival";

	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREEN_ID_MANIFEST = "mailtracking.defaults.mailmanifest";

	private static final String SCREENID = "mailtracking.defaults.searchflight";

	private static final String MANIFEST_LIST = "manifest_list";

	private static final String ARRIVAL_LIST = "arrival_list";

	private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";

	private static final String WEIGHT_CODE = "shared.defaults.weightUnitCodes";

	private static final String CATEGORY_CODE = "mailtracking.defaults.mailcategory";

	private static final String CONTAINER_TYPE = "mailtracking.defaults.containertype";

	private static final String MAILSTATUS = "mailtracking.defaults.mailarrivalstatus";

	private static final String CONTAINERTYPES = "mailtracking.defaults.containertype";

	private static final String SYSPAR_MAIL_COMMODITY = "mailtracking.defaults.booking.commodity";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		SearchFlightForm searchFlightForm = (SearchFlightForm) invocationContext.screenModel;
		SearchFlightSession searchFlightSession = getScreenSession(MODULE_NAME,
				SCREENID);
		String selectedRow = searchFlightForm.getSelectedRow();
		OperationalFlightVO operationalFlightVO = (OperationalFlightVO) (searchFlightSession
				.getOperationalFlightVOs()).get(Integer.parseInt(selectedRow));
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		/**
		 * for mail Arrival Screen
		 */
		if (MailConstantsVO.OPERATION_INBOUND.equals(searchFlightForm
				.getFromScreen())) {
			MailArrivalSession mailArrivalSession = getScreenSession(
					MODULE_NAME, SCREEN_ID_ARRIVAL);
			MailArrivalVO mailArrivalVO = null;
			mailArrivalVO = mailArrivalSession.getMailArrivalVO();
			if (mailArrivalVO == null) {
				mailArrivalVO = new MailArrivalVO();
			}
			if (operationalFlightVO.getCarrierCode() != null) {
				mailArrivalVO.setFlightCarrierCode(operationalFlightVO
						.getCarrierCode());
			}
			if (operationalFlightVO.getFlightNumber() != null) {
				mailArrivalVO.setFlightNumber(operationalFlightVO
						.getFlightNumber());
			}
			if (searchFlightForm.getArrivalPort() != null
					&& searchFlightForm.getArrivalPort().trim().length() > 0) {
				mailArrivalVO.setAirportCode(searchFlightForm.getArrivalPort());
			}
			if (operationalFlightVO.getFlightDate() != null) {
				mailArrivalVO.setArrivalDate(operationalFlightVO
						.getFlightDate());
			}
			
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(MAILSTATUS);
			fieldValues.add(CONTAINERTYPES);

			Map<String, Collection<OneTimeVO>> oneTimeData = findOneTimeDescription(
					logonAttributes.getCompanyCode(), fieldValues);
			
			Collection<OneTimeVO> mailStatusVOs = oneTimeData.get(MAILSTATUS);
			Collection<OneTimeVO> containerTypes = oneTimeData
					.get(CONTAINERTYPES);
			mailArrivalSession.setOneTimeMailStatus(mailStatusVOs);
			mailArrivalSession.setOneTimeContainerType(containerTypes);
			
			
			Collection<String> codes = new ArrayList<String>();
			codes.add(SYSPAR_MAIL_COMMODITY);
			Map<String, String> results = getSystemParameters(codes);
			if (results != null && results.size() > 0) {
				String commodityCode = results.get(SYSPAR_MAIL_COMMODITY);
				CommodityValidationVO commodityValidationVO = validateCommodity(
						logonAttributes.getCompanyCode(), commodityCode);
				mailArrivalSession.setMailDensity(String
						.valueOf(commodityValidationVO.getDensityFactor()));
			}

			mailArrivalSession.setMailArrivalVO(mailArrivalVO);
			removeSession(searchFlightSession);
			invocationContext.target = ARRIVAL_LIST;
			return;
		}
		/**
		 * for mail manifest screen
		 */
		if (MailConstantsVO.OPERATION_OUTBOUND.equals(searchFlightForm
				.getFromScreen())) {
			MailManifestSession mailManifestSession = getScreenSession(
					MODULE_NAME, SCREEN_ID_MANIFEST);
			MailManifestVO mailManifestVO = null;
			mailManifestVO = mailManifestSession.getMailManifestVO();
			if (mailManifestVO == null) {
				mailManifestVO = new MailManifestVO();
			}
			if (operationalFlightVO.getCarrierCode() != null) {
				mailManifestVO.setFlightCarrierCode(operationalFlightVO
						.getCarrierCode());
			}
			if (operationalFlightVO.getFlightNumber() != null) {
				mailManifestVO.setFlightNumber(operationalFlightVO
						.getFlightNumber());
			}
			if (searchFlightForm.getDepartingPort() != null
					&& searchFlightForm.getDepartingPort().trim().length() > 0) {
				mailManifestVO.setDepPort(searchFlightForm.getDepartingPort());
			}
			if (operationalFlightVO.getFlightDate() != null) {
				mailManifestVO.setDepDate(operationalFlightVO.getFlightDate());
			}

			/**
			 * Added for Bug
			 */
			Collection<String> parameterTypes = new ArrayList<String>();
			Collection<String> fieldTypes = new ArrayList<String>();

			fieldTypes.add(WEIGHT_CODE);
			fieldTypes.add(CATEGORY_CODE);
			fieldTypes.add(CONTAINER_TYPE);
			parameterTypes.add(STOCK_REQ_PARAMETER);
			Map<String, Collection<OneTimeVO>> oneTimeData = findOneTimeDescription(
					logonAttributes.getCompanyCode(), fieldTypes);
			Map<String, String> parameters = getSystemParameters(parameterTypes);

			mailManifestSession
					.setSystemParameters((HashMap<String, String>) parameters);
			Collection<OneTimeVO> weightCode = oneTimeData.get(WEIGHT_CODE);
			Collection<OneTimeVO> catVOs = oneTimeData.get(CATEGORY_CODE);
			Collection<OneTimeVO> containerTypeVOs = oneTimeData
					.get(CONTAINER_TYPE);
			mailManifestSession.setWeightCodes(weightCode);
			mailManifestSession.setOneTimeCat(catVOs);
			mailManifestSession.setContainerTypes(containerTypeVOs);

			mailManifestSession.setMailManifestVO(mailManifestVO);
			removeSession(searchFlightSession);
			invocationContext.target = MANIFEST_LIST;
			return;
		}
	}

	/**
	 * 
	 * @param session
	 */
	private void removeSession(SearchFlightSession searchFlightSession) {
		searchFlightSession.removeAllAttributes();
	}

	/**
	 * 
	 * @param companyCode
	 * @param parameters
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeDescription(
			String companyCode, Collection<String> parameters) {
		Map<String, Collection<OneTimeVO>> oneTimeData = new HashMap<String, Collection<OneTimeVO>>();
		try {

			oneTimeData = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, parameters);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return oneTimeData;
	}

	/**
	 * 
	 * @param sysParameters
	 * @return
	 */
	private Map<String, String> getSystemParameters(
			Collection<String> sysParameters) {
		Map<String, String> systemParameters = new HashMap<String, String>();
		try {
			systemParameters = new SharedDefaultsDelegate()
					.findSystemParameterByCodes(sysParameters);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,  "BusinessDelegateException");
		}
		return systemParameters;
	}

	/**
	 * @author A-3227 RENO K ABRAHAM
	 * @param companyCode
	 * @param commodityCode
	 * @return
	 * @throws SystemException
	 */
	private CommodityValidationVO validateCommodity(String companyCode,
			String commodityCode) {

		Collection<String> commodites = new ArrayList<String>();
		CommodityValidationVO commodityValidationVO = null;
		if (commodityCode != null && commodityCode.trim().length() > 0) {
			commodites.add(commodityCode);
			Map<String, CommodityValidationVO> densityMap = null;
			CommodityDelegate commodityDelegate = new CommodityDelegate();

			try {
				densityMap = commodityDelegate.validateCommodityCodes(
						companyCode, commodites);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}

			if (densityMap != null && densityMap.size() > 0) {
				commodityValidationVO = densityMap.get(commodityCode);
				// log.log(Log.FINE,
				// "DENSITY:::::::::"+commodityValidationVO.getDensityFactor());
			}
		}
		return commodityValidationVO;
	}
}
