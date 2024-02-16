package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

// TODO: Auto-generated Javadoc
/**
 * The Class ProductODMappingMapper.
 * @author A-6858
 */
public class ProductODMappingMapper implements Mapper<ProductODMappingVO> {

	/* (non-Javadoc)
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 */
	public ProductODMappingVO map(ResultSet rs) throws SQLException {

		ProductODMappingVO productODMappingVO = null;
		productODMappingVO = new ProductODMappingVO();
		productODMappingVO.setCompanyCode(rs.getString("CMPCOD"));
		productODMappingVO.setProductName(rs.getString("PRDNAM"));
		productODMappingVO.setOrigin(rs.getString("ORGCOD"));
		productODMappingVO.setDestination(rs.getString("DSTCOD"));

		return productODMappingVO;
	}

}
