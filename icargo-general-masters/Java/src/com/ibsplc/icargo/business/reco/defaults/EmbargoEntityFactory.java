/*
 * EmbargoEntityFactory.java Created on Jul 13, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.reco.defaults;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * This Class has methods that validates a
 * particular Entity against its values
 *
 * @author A-1894
 *
 */

public class EmbargoEntityFactory {

	private static EmbargoEntityFactory factory = null;

	private static final String SCC = "SCC";
	private static final String COMMODITY = "COM";
	private static final String CARRIER = "CAR";
	private static final String PAYMENTTYPE = "PAYTYP";

	private EmbargoEntityFactory() {

	}

	/**
	 * Factory method for Entity Factory
	 *
	 * @return EmbargoEntityFactory
	 */
	public static EmbargoEntityFactory getEntityFactory() {

		if (factory == null){
			factory = new EmbargoEntityFactory();
		}
		return factory;
	}

	/**
	 *
	 * This method validates a Particular Entity against its Values
	 *
	 * @param companyCode
	 * @param entityName
	 * @param collection
	 * @return
	 * @throws EmbargoBusinessException
	 * @throws SystemException
	 */

	public static boolean validateParamterCodes(String companyCode,
			String entityName, Collection collection)
			throws EmbargoRulesBusinessException, SystemException {

		String entity = null;
		String method = null;

		try {

			if (entityName.equalsIgnoreCase(SCC)) {
				entity = "com.ibsplc.icargo.business.shared.scc.SCC";
				method = "validateSCCCodes";
			} else if (entityName.equalsIgnoreCase(COMMODITY)) {
				entity = "com.ibsplc.icargo.business.shared.commodity.Commodity";
				method = "validateCommodityCodes";
			}else if(entityName.equalsIgnoreCase(CARRIER)){
				entity = "com.ibsplc.icargo.business.shared.airline.Airline";
				method = "validateAlphaCodes";
			}else if(entityName.equalsIgnoreCase(PAYMENTTYPE)){
				//To be implemented after the completion of paymenttype master
				entity = null;
				method = null;
			}

			if(entity != null) {
				Class entityClass = Class.forName(entity);
				entityClass.getMethod(method, String.class, Collection.class)
						.invoke(null, companyCode, collection);
			}
		} catch (ClassNotFoundException e) {
			throw new SystemException(e.getMessage(),e);
		} catch (SecurityException e) {
			throw new SystemException(e.getMessage(),e);
		} catch (NoSuchMethodException e) {
			throw new SystemException(e.getMessage(),e);
		} catch (IllegalArgumentException e) {
			throw new SystemException(e.getMessage(),e);
		} catch (IllegalAccessException e) {
			throw new SystemException(e.getMessage(),e);
		} catch (InvocationTargetException e) {
			throw new EmbargoRulesBusinessException((BusinessException)e.getCause());
		}
		return true;
	}

}
