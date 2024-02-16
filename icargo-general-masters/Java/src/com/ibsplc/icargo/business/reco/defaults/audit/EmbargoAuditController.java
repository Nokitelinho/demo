/*
 * EmbargoAuditController.java Created on Jul 12, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary
 * information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.audit;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoAuditVO;
import com.ibsplc.xibase.server.framework.audit.AuditException;

/**
 * @author A-1358
 * This class routes the audit requests to the appropriate controller class
 */
public class EmbargoAuditController {
    /**
     * This Method audits the Embargo Save and Update method
     * @param embargoAuditVO
     * @throws AuditException
     */
	public void audit(EmbargoAuditVO embargoAuditVO) throws AuditException {

    //	new EmbargoController().audit(embargoAuditVO);

    	//To be reviewed:Check the entity name and route to the correct domain controller
        //for persisting call EmbargoController().audit(auditVo)
    }
}
