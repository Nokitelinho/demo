/*
 * StockControlDefaultsAuditController.java Created on Jul 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.audit;

import com.ibsplc.icargo.business.stockcontrol.defaults.StockController;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * @author A-1358
 *
 */
public class StockControlDefaultsAuditController {

    /**
     *
     * @param auditVo
     * @throws AuditException
     */
    public void audit(AuditVO auditVo) throws AuditException{
    	try{
            /*if(StockHolderVO.STOCKHOLDER_AUDIT_ENTITYNAME.equals(auditVo.getAuditName()) ) {
                new StockController().auditStockHolder(auditVo);
            }
           else if(StockRequestVO.STOCKREQUEST_AUDIT_ENTITYNAME.equals(auditVo.getAuditName())){
            	new StockController().auditStockRequest(auditVo);
            }
           else if(StockAllocationVO.ALLOCATERANGE_AUDIT_ENTITYNAME.equals(auditVo.getAuditName())){
        	   new StockController().auditAllocateStock(auditVo);
           }
           else if(BlacklistStockVO.STOCKHOLDER_AUDIT_ENTITYNAME.equals(auditVo.getAuditName())){
        	   new StockController().auditBlacklistStock(auditVo);
           }*/
           new StockController().auditBlacklistStock(auditVo);

        }catch(SystemException systemException){
        	throw new AuditException(systemException.getError().getErrorCode());
        }
    }
}
