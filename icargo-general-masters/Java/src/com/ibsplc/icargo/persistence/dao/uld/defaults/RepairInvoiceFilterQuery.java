/*
 * RepairInvoiceFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * @author A-1347
 *
 */
public class RepairInvoiceFilterQuery extends NativeQuery {


    /**
     * @throws SystemException
     * @param accessoriesStockConfigFilterVO
     * @param baseQry
     */
    public RepairInvoiceFilterQuery(
		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
		String baseQry) throws SystemException {

    }


  /**
   *
   * @return
   */
    public String getNativeQuery() {
        return null;
    }
}
