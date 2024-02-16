/*
 * ProductDefaultsFilterQuery.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.util.Calendar;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-1358
 *
 */
public class ProductDefaultsFilterQuery extends PageableNativeQuery<ProductVO>{//Added by A-5201 as part for the ICRD-22065

	private ProductFilterVO productFilterVo;

	private String baseQuery;
	/**
	 * Constructor
	 * @param productFilterVo
	 * @param baseQuery
	 * @throws SystemException
	 */
	/*public ProductDefaultsFilterQuery(ProductFilterVO productFilterVo, String baseQuery)
	throws SystemException {
		super();
		this.productFilterVo = productFilterVo;
		this.baseQuery = baseQuery;
	}*/
	
	//Added by A-5201 as part for the ICRD-22065 starts
	public ProductDefaultsFilterQuery(ProductFilterVO productFilterVo,String baseQuery,Mapper<ProductVO> mapper) throws SystemException{
		super(productFilterVo.getTotalRecords(), mapper);
		this.productFilterVo = productFilterVo;
        this.baseQuery = baseQuery;		
	}
	//Added by A-5201 as part for the ICRD-22065 end
	/**
	 * This method is used to create query string
	 * @return String
	 */
	public String getNativeQuery() {
		String companyCode=productFilterVo.getCompanyCode();
		String productName = productFilterVo.getProductName();
		String status = productFilterVo.getStatus();
		String transportMode = productFilterVo.getTransportMode();
		String priority = productFilterVo.getPriority(); 
		String scc = productFilterVo.getScc();
		String isRateDefined="";
		boolean isRate=productFilterVo.getIsRateDefined();
		String productCategory = productFilterVo.getProductCategory();//Added for ICRD-166985 by A-5117
		if(isRate){
			isRateDefined=ProductFilterVO.FLAG_YES;
		}
		else{
			isRateDefined=ProductFilterVO.FLAG_NO;
		}
		Calendar fromDate = null;
		Calendar toDate = null;
		if(productFilterVo.getFromDate()!=null){
			fromDate=(productFilterVo.getFromDate()).toCalendar();
		}
		if(productFilterVo.getToDate()!=null){
			toDate = (productFilterVo.getToDate()).toCalendar();
		}
		StringBuilder stringBuilder = new StringBuilder().append(baseQuery);
		int index = 0;
		if (companyCode != null && companyCode.trim().length()!=0) {
			stringBuilder.append(" AND A.CMPCOD = ? ");
			this.setParameter(++index, companyCode);
		}
		if (productName != null && productName.trim().length()!= 0) {
			stringBuilder.append(" AND UPPER(A.PRDNAM) = ? ");
			this.setParameter(++index, productName.toUpperCase());
		}
		if (status != null && status.trim().length()!= 0 && !(status.equals(ProductFilterVO.FLAG_ALL))){
			stringBuilder.append(" AND A.PRDSTA = ? " );
			this.setParameter(++index, status);
		}		 
		 
		if(toDate != null && fromDate != null){			
			stringBuilder.append(" AND (to_date(A.PRDSTRDAT , 'DD-MM-YYYY') BETWEEN to_date( ? , 'DD-MM-YYYY') AND to_date( ? , 'DD-MM-YYYY')) ");				
			this.setParameter(++index, fromDate );
			this.setParameter(++index, toDate);
			
			stringBuilder.append(" AND (to_date(A.PRDENDDAT , 'DD-MM-YYYY') BETWEEN to_date( ? , 'DD-MM-YYYY') AND to_date( ? , 'DD-MM-YYYY')) ");
			this.setParameter(++index, fromDate );
			this.setParameter(++index, toDate);             
			
		}else{
			if (fromDate != null) {
				stringBuilder.append(" AND (to_date(A.PRDSTRDAT, 'DD-MM-YYYY') = to_date(? , 'DD-MM-YYYY')) ");				
				this.setParameter(++index, fromDate );
			}
			if (toDate != null) {
				stringBuilder.append(" AND (to_date(A.PRDENDDAT, 'DD-MM-YYYY') = to_date(? , 'DD-MM-YYYY')) ");
				this.setParameter(++index, toDate);
			}			
		}		
		
		if (transportMode != null && transportMode.trim().length()!= 0
				&&	!(transportMode.equals(ProductFilterVO.FLAG_ALL))) {
			stringBuilder.append(" AND B.PRDTRAMOD = ? ");
			this.setParameter(++index, transportMode);
		}
		if (priority != null && priority.trim().length()!= 0
				&& !(priority.equals(ProductFilterVO.FLAG_ALL))) {
			stringBuilder.append(" AND C.PRDPRY = ? ");
			this.setParameter(++index, priority);
		}
		if (scc != null && scc.trim().length()!= 0 && !(scc.equals(ProductFilterVO.FLAG_ALL))) {
			stringBuilder.append(" AND UPPER(D.PRDSCC) = ? ");
			this.setParameter(++index, scc);
		}
		if(productCategory != null && productCategory.length() > 0){//Added for ICRD-166985 by A-5117
			stringBuilder.append(" AND  PKG_FRMWRK.Fun_String_Check(A.PRDCTG,?,',') > 0 ");
		      index++; setParameter(index, productCategory);
		}
		if (isRateDefined != null && isRateDefined.trim().length()!= 0
				&& ProductFilterVO.FLAG_YES.equals(isRateDefined)) {
			stringBuilder.append(" AND A.RATDEF = ? ");
			this.setParameter(++index, isRateDefined);
		}		
		stringBuilder.append(")RESULT_TABLE");//Added by A-5201 as part for the ICRD-22065
		return stringBuilder.toString();
	}
}
