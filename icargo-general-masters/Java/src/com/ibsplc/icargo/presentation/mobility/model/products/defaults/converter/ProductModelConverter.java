/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.product.defaults.converter.ProductModelConverter.java
 *
 *	Created by	:	A-7364
 *	Created on	:	13-Jun-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.model.products.defaults.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.presentation.mobility.model.products.defaults.Product;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.products.defaults.converter.ProductModelConverter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7364	:	13-Jun-2019	:	Draft
 */
public class ProductModelConverter {
	
	/**
	 * 
	 * 	Method		:	ProductModelConverter.getErrorResponseDetailMap
	 *	Added by 	:	A-7364 on 13-Jun-2019
	 * 	Used for 	:
	 *	Parameters	:	@param exception
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,List<ErrorData>>
	 */
	public static Map<String, List<ErrorData>> getErrorResponseDetailMap(WSBusinessException exception)	throws SystemException {
		Map<String, List<ErrorData>> errorWarnings = new HashMap<String,  List<ErrorData>>();
		if (exception.getErrors() != null && !exception.getErrors().isEmpty()) {
			errorWarnings.put(ErrorDisplayType.ERROR.name(), new ArrayList<ErrorData>());
			errorWarnings.put(ErrorDisplayType.WARNING.name(), new ArrayList<ErrorData>());
			ErrorData errorData = null;
			for (ErrorVO errorVO : exception.getErrors()) {
				errorData = new ErrorData();
				errorData.setCode(errorVO.getErrorCode());
				if (ErrorDisplayType.WARNING.equals(errorVO.getErrorDisplayType()))
					errorWarnings.get(ErrorDisplayType.WARNING.name()).add(errorData);
				else {
					errorWarnings.get(ErrorDisplayType.ERROR.name()).add(errorData);
				}
			}
		}
		return errorWarnings;
	}
	
	/**
	 * 
	 * 	Method		:	ProductModelConverter.convertToProductModel
	 *	Added by 	:	A-7364 on 13-Jun-2019
	 * 	Used for 	:
	 *	Parameters	:	@param productVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Product
	 */
	public static Product convertToProductModel(ProductVO productVO){
		Product product = new Product();
		product.setProductCode(productVO.getProductCode());
		product.setProductName(productVO.getProductName());
		
		return product;
	}
}
