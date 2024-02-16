/*
 * StockControlAuditMDB.java Created on Jul 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.audit;



import com.ibsplc.xibase.server.framework.audit.AuditMDB;


//import com.ibsplc.icargo.business.stockcontrol.audit.proxy.StockControlDefaultsAuditProxy;


/**
 * @author A-1358
 *
 */
public class StockControlAuditMDB extends AuditMDB {

    //private static final String SUBSYSTEM_DEFAULTS = "stockcontrol.defaults";
    private static final String FRONTCONTROLLER_ACTION_NAME = "audit";

	/**
	 * This method will be called from audit framework MDB onMessage() method
	 *
	 * @param moduleName
	 * @param auditVO
	 * Annotataion to be used
	 * @Override
	 */
	/*public void audit(String moduleName, AuditVO auditVo)
			throws AuditException {
	    try {
	        if (SUBSYSTEM_DEFAULTS.equalsIgnoreCase(moduleName)) {
	            new StockControlDefaultsAuditProxy().audit(auditVo);
	        }
	    }catch(RemoteException exception){
	        throw new AuditException(
	               SystemException.SERVICE_NOT_ACCESSIBLE, exception);
	    }
	}*/

	/**
	 * This method returns the action for the audit request.
	 * It is also the implementation of the method in AuditMDB
	 * @param moduleName
	 * @return String - returns the action name
	 */
    protected String getAction(String moduleName) {
    	return FRONTCONTROLLER_ACTION_NAME;
    }
}
