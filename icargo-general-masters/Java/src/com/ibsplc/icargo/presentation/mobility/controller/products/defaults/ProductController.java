/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.products.defaults.ProductController.java
 *
 *	Created by	:	A-7364
 *	Created on	:	13-Jun-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.controller.products.defaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.framework.mobility.ServiceName;
import com.ibsplc.icargo.framework.mobility.controller.AbstractController;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.icargo.framework.web.json.NormalizedResponse;
import com.ibsplc.icargo.framework.web.json.vo.ErrorData;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.presentation.mobility.model.products.defaults.Product;
import com.ibsplc.icargo.presentation.mobility.model.products.defaults.converter.ProductModelConverter;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.controller.products.defaults.ProductController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7364	:	13-Jun-2019	:	Draft
 */

@Controller
@Module("products")
@SubModule("defaults")
@RequestMapping({"products/defaults"})
@ServiceName("ProductController")
public class ProductController extends AbstractController{
	
	private Log log = LogFactory.getLogger("PRODUCTS DEFAULTS");
	private static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	
	@RequestMapping({"/findProducts"}) 
	public @ResponseBody ResponseVO findProducts() throws SystemException {
		ResponseVO responseVO = new ResponseVO();
		ProductFilterVO productFilterVO = new ProductFilterVO();
		Page<ProductVO> productVOs = null;
		Collection<Product> products = new ArrayList<Product>();
		try{
			LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			productFilterVO.setTotalRecords(1000);
			productFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			productVOs = despatchRequest("findProducts", productFilterVO, 1);
			if(productVOs!=null && productVOs.size()>0){
				Product product = null;
				for(ProductVO productVO: productVOs){
					product = ProductModelConverter.convertToProductModel(productVO);
					products.add(product);
				}
				responseVO.setResults(Arrays.asList(NormalizedResponse.createNormalizedResponse(products)));
				responseVO.setStatus(RESPONSE_STATUS_SUCCESS);
				Map<String, List<ErrorData>> errorCodes = new HashMap<String, List<ErrorData>>();
				responseVO.setErrors(errorCodes);
			}
		} catch(SystemException sye){
			log.log(Log.SEVERE, "System Exception Caught");
		} catch (WSBusinessException wbe) {
			Map<String, List<ErrorData>> map = ProductModelConverter
					.getErrorResponseDetailMap(wbe);
			responseVO.setStatus(map.containsKey(ErrorDisplayType.ERROR.name()) ? ErrorDisplayType.ERROR
							.name() : ErrorDisplayType.WARNING.name());
			map.remove(map.containsKey(ErrorDisplayType.ERROR.name()) ? ErrorDisplayType.WARNING
					.name() : ErrorDisplayType.ERROR.name());
			responseVO.setErrors(map);
		}
		return responseVO;
	}

}
