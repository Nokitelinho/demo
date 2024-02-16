/*
 *SubProductDefaultsFilterQuery.java Created on Jun 28, 2005
 *
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *This software is the proprietary information of IBS Software Services (P)Ltd.
 *Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;



import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1883
 * This class is used to generate query for gettting the subproduct
 * based on filter criteria
 */
public class SubProductDefaultsFilterQuery extends PageableNativeQuery<SubProductVO>{

    private ProductFilterVO productFilterVo;

    private String baseQuery;
    /**
     * Constructor
     * @param productFilterVo
     * @param baseQuery
     * @throws SystemException
     */
    public SubProductDefaultsFilterQuery(ProductFilterVO productFilterVo,
    		String baseQuery,Mapper<SubProductVO>subProductMapper)throws SystemException {
      //  super();
    	super(productFilterVo.getTotalRecordsCount(),subProductMapper);
        this.productFilterVo = productFilterVo;
        this.baseQuery = baseQuery;
    }
    /**
     * This method returns the query string
     * @return String
     */
   public String getNativeQuery() {
	   	String companyCode=productFilterVo.getCompanyCode();
    	String productCode = productFilterVo.getProductCode();
		String transportMode = productFilterVo.getTransportMode();
		String priority = productFilterVo.getPriority();
		String scc = productFilterVo.getScc();
		String status = productFilterVo.getStatus();
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
        int index = 0;
        if (companyCode != null && companyCode.trim().length()!=0 ) {
        	stringBuilder.append(" CMPCOD = ? ");
        	this.setParameter(++index,companyCode);
        }
        if (productCode != null && productCode.trim().length()!=0 ) {
        	stringBuilder.append(" AND PRDCOD = ? ");
        	this.setParameter(++index, productCode);
        }
        if (transportMode != null && (transportMode.trim().length()!= 0) &&
        									!(transportMode.equals(ProductFilterVO.FLAG_ALL))) {
        	stringBuilder.append(" AND TRAMOD = ? ");
        	this.setParameter(++index, transportMode);
        }
        if (priority != null && priority.trim().length()!= 0 && !(priority.
        													equals(ProductFilterVO.FLAG_ALL))) {
        	stringBuilder.append(" AND PRYCOD = ? ");
        	this.setParameter(++index, priority);
        }
        if (scc != null && scc.trim().length()!= 0) {
        	stringBuilder.append(" AND UPPER(SCCCOD) = ? ");
        	this.setParameter(++index, scc);
        }
        if (status != null && status.trim().length()!= 0 && !(status.
        													equals(ProductFilterVO.FLAG_ALL))){
        		stringBuilder.append(" AND SUBPRDSTA = ? ");
        		this.setParameter(++index, status);
        }

        stringBuilder.append(" AND LTSVERFLG = 'Y' ");


         return stringBuilder.toString();
    }
}
