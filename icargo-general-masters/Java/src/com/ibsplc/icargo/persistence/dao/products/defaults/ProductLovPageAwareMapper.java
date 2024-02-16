/*
 * ProductLovPageAwareMapper.java Created on Aug 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * Class created for finding product lov
 * @author a-1885
 *
 */
public class ProductLovPageAwareMapper implements
MultiMapper<ProductLovVO> {

	private Log log = LogFactory.getLogger("PRODUCT LOV PAGE AWARE MAPPER");

	public List<ProductLovVO> map(ResultSet resultSet)throws
	SQLException{
		List<ProductLovVO> productList = null;
		ProductLovVO lovVo = null;
		//Set<String> productScc= null;
		Set<String> productTransportMode = null;
		Set<String> productPriority = null;
		String productLovPk = " ";
		while(resultSet.next()){
			String productLovPkCreate = resultSet.getString("PRDCOD");
				if(!productLovPk.equals(productLovPkCreate)){
					if(lovVo!=null){
						if(productList==null){
							productList = new ArrayList<ProductLovVO>();
						}
						productList.add(lovVo);
						lovVo = null;
						//productScc = null;
						productTransportMode = null;
						productPriority = null;
						//increment();
					}
					lovVo = new ProductLovVO();
					lovVo.setProductCode(resultSet.getString("PRDCOD"));
					lovVo.setProductName(resultSet.getString("PRDNAM"));
					if(resultSet.getDate("PRDSTRDAT")!=null){
						lovVo.setStartDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE,resultSet.getDate("PRDSTRDAT")));
					}
					if(resultSet.getDate("PRDENDDAT")!=null){
						lovVo.setEndDate(new LocalDate(LocalDate.NO_STATION,
								Location.NONE,resultSet.getDate("PRDENDDAT")));
					}
					//productScc = new HashSet<String>();
					productTransportMode = new HashSet<String>();
					productPriority = new HashSet<String>();
					//productScc.add(resultSet.getString("PRDSCC"));
					productTransportMode.add(resultSet.getString("PRDTRAMOD"));
					productPriority.add(resultSet.getString("PRDPRY"));
					//lovVo.setProductScc(productScc);
					lovVo.setProductTransportMode(productTransportMode);
					lovVo.setProductPriority(productPriority);
				}
				else{
					if(lovVo.getProductScc()==null){
						lovVo.setProductScc(new HashSet<String>());
					}
					lovVo.getProductScc().add(resultSet.getString
						("PRDSCC"));
					if(lovVo.getProductTransportMode()==null){
						lovVo.setProductTransportMode(new HashSet<String>());
					}
					lovVo.getProductTransportMode().add
					(resultSet.getString("PRDTRAMOD"));
					if(lovVo.getProductPriority()==null){
						lovVo.setProductPriority(new HashSet<String>());
					}
					lovVo.getProductPriority().add
					(resultSet.getString("PRDPRY"));

				}
				productLovPk = productLovPkCreate;
			}
			if(lovVo!=null){
				if(productList==null){
					productList = new ArrayList<ProductLovVO>();
				}
				productList.add(lovVo);
				//increment();
			}
		return productList;
	}
}