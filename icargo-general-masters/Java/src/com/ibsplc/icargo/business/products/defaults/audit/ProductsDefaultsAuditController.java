/*
 * ProductsDefaultsAuditController.java Created on Aug 23, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.audit;



import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.xibase.server.framework.audit.AuditException;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1366
 *
 */
public class ProductsDefaultsAuditController {
	private Log log = LogFactory.getLogger("PRODUCT DEFAULT CONTROLLER");
    /**
     * Method audit
     * @param productsDefaultsAuditVO
     * @throws AuditException
     * @return
     */
    public void audit(ProductsDefaultsAuditVO productsDefaultsAuditVO) throws AuditException {
    	log.log(Log.FINE,"((((((((ProductsDefaultsAuditController)))))))))))");
    	//try{
           /* if(ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME.equals(productsDefaultsAuditVO.getAuditName()) ) {
                new ProductDefaultsController().auditProductsDefaults(productsDefaultsAuditVO);
            }*/
           // new ProductDefaultsController().auditProductsDefaults(productsDefaultsAuditVO);
       // }catch(SystemException systemException){
        	//throw new AuditException(systemException.getError().getErrorCode());
      //  }

    }
}
