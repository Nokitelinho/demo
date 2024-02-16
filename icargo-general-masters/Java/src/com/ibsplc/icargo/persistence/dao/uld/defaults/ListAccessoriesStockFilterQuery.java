/*
 * ListAccessoriesStockFilterQuery.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigFilterVO;
import com.ibsplc.icargo.business.uld.defaults.stock.vo.AccessoriesStockConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */
public class ListAccessoriesStockFilterQuery extends PageableNativeQuery<AccessoriesStockConfigVO> {

	private AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO;
	private String query;
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
    /**
     * @throws SystemException
     * @param accessoriesStockConfigFilterVO
     * @param baseQry
     */
    public ListAccessoriesStockFilterQuery(
		AccessoriesStockConfigFilterVO accessoriesStockConfigFilterVO,
		String baseQry,AccessoriesStockMapper accessoriesStockMapper) throws SystemException {
    	super(accessoriesStockConfigFilterVO.getTotalRecordsCount(),accessoriesStockMapper);
    	this.accessoriesStockConfigFilterVO = accessoriesStockConfigFilterVO;
    	this.query = baseQry;
    	
    }

  /**
   *
   * @return
   */
    public String getNativeQuery() {
    	log.entering("ListAccessoriesStockFilterQuery","getNativeQuery");
        StringBuilder stringBuilder = new StringBuilder().append(query);
        String accessoryCode = accessoriesStockConfigFilterVO.getAccessoryCode();
    	int airlineIdentifier = accessoriesStockConfigFilterVO.getAirlineIdentifier();
    	String stationCode = accessoriesStockConfigFilterVO.getStationCode();
    	String companyCode = accessoriesStockConfigFilterVO.getCompanyCode();
        int index = 0;
        this.setParameter(++index,companyCode);
        if(accessoryCode != null && accessoryCode.trim().length() > 0){
        	stringBuilder.append(" AND ULD.ACCCOD = ?");
        	this.setParameter(++index,accessoryCode);
        }
        if(airlineIdentifier > 0){
        	stringBuilder.append(" AND ULD.ARLIDR =?");
        	this.setParameter(++index,airlineIdentifier );
        }
        if(stationCode != null && stationCode .trim().length() > 0){
        	stringBuilder.append(" AND ULD.ARPCOD = ?");
        	this.setParameter(++index,stationCode );
        }
//        stringBuilder.append(" AND SHR.ARLIDR(+) = ULD.ARLIDR");
        stringBuilder.append(" ORDER BY ARPCOD ");
        log.log(Log.FINE, "stringBuilder-->", stringBuilder.toString());
		log.exiting("ListAccessoriesStockFilterQuery","getNativeQuery");
    	return stringBuilder.toString();
    }
}
