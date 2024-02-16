/*
 * PopulateCityNameCommand.java Created on Oct 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Rani Rose John
 * Command class for clearing
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Nov 30, 2006 Rani Rose John Initial draft
 */
public class PopulateCityNameCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "PopulateCityNameCommand";

	private static final String POPULATE_SUCCESS = "populate_success";

	//private static final String MODULE_NAME = "mra.defaults";

	//private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainMraProrateFactorsForm maintainProrateFactorsForm = (MaintainMraProrateFactorsForm) invocationContext.screenModel;
		//MaintainMraProrateFactorsSession maintainProrateFactorsSession = (MaintainMraProrateFactorsSession) getScreenSession(
				//MODULE_NAME, SCREEN_ID);
		//Page<ProrateFactorVO> vos = null;
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		if ((maintainProrateFactorsForm.getCityCode() != null && maintainProrateFactorsForm
				.getCityCode().trim().length() > 0)) {
			log.log(Log.INFO, "maintainProrateFactorsForm.getCityCode",
					maintainProrateFactorsForm.getCityCode());
			CityVO cityvo = null;
			Map<String, CityVO> cityMap = null;
			cityMap = populateCityName(maintainProrateFactorsForm
					.getCityCode());
			if (cityMap != null) {
				if (cityMap.get(maintainProrateFactorsForm
						.getCityCode().toUpperCase()) != null) {
					cityvo = cityMap.get(maintainProrateFactorsForm
							.getCityCode().toUpperCase());
					maintainProrateFactorsForm.setCityName(cityvo
							.getCityName().toUpperCase());
					log.log(Log.INFO,
							"maintainProrateFactorsForm.getCityName inside if",
							maintainProrateFactorsForm.getCityName());
					maintainProrateFactorsForm.setErrorStatusFlag("N");
				}
			} else {
				/*ErrorVO errorVO = new ErrorVO(
						"cra.proration.maintainproratefactors.msg.err.invalidcitycode",
						new Object[] { maintainProrateFactorsForm
								.getCityCode().toUpperCase() });
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);*/
				maintainProrateFactorsForm.setErrorStatusFlag("Y");
				maintainProrateFactorsForm.setCityName("");
			}

		}
			
		log.log(Log.INFO,
				"maintainProrateFactorsForm.setCityName before exiting",
				maintainProrateFactorsForm.getCityName());
		invocationContext.target = POPULATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * Method to populate cityvo when citycode is given 
	 * @param originCityCode
	 * @return cityvo
	 */
	private Map<String, CityVO> populateCityName(String originCityCode) {
		// TODO Auto-generated method stub
		//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Map<String, CityVO> cityMap = null;
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<String> codes = new ArrayList<String>();
		codes.add(originCityCode.toUpperCase());
		String companycode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		try {
			cityMap = areaDelegate.validateCityCodes(companycode, codes);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		return cityMap;
	}

}
