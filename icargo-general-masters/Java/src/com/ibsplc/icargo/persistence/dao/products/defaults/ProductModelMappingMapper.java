package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

public class ProductModelMappingMapper implements Mapper<ProductModelMappingVO>{

	public ProductModelMappingVO map(ResultSet rs) throws SQLException {
		
		ProductModelMappingVO productModelMappingVO = null;
		
		productModelMappingVO =  new ProductModelMappingVO();
		productModelMappingVO.setCompanyCode(rs.getString("CMPCOD"));//
		productModelMappingVO.setCommodityGroup(rs.getString("COMGRP"));
		productModelMappingVO.setCommodityName(rs.getString("COMNAM"));
		productModelMappingVO.setProductName(rs.getString("PRDNAM"));
		productModelMappingVO.setIscommodityValidForBookingChannelEDI(rs.getString("COMEDI"));
		productModelMappingVO.setIscommodityValidForBookingChannelEbooking(rs.getString("COMPOR"));
		productModelMappingVO.setIscommodityAvailableInSoCo(rs.getString("COMSOC"));
		productModelMappingVO.setProductPriority(rs.getString("PRDPRY"));
		productModelMappingVO.setProductGroup(rs.getString("PRDGRP"));
		productModelMappingVO.setAttributeName(rs.getString("ATRNAM"));
		productModelMappingVO.setProductDescriptionPriority(rs.getInt("ATRPRY"));
		productModelMappingVO.setIsProductValidForBookingChannelEDI(rs.getString("PRDEDI"));
		productModelMappingVO.setIsProductValidForBookingChannelEbooking(rs.getString("PRDBKG"));
		productModelMappingVO.setIsProductAvailableInSoCo(rs.getString("PRDSOCO"));
		productModelMappingVO.setRecProductGroup(rs.getString("RECPRD"));
		productModelMappingVO.setRecProductPriority(rs.getString("RECPRY"));
		productModelMappingVO.setCommodityGroupDescription(rs.getString("COMGRPDES"));
		productModelMappingVO.setCommodityDescription(rs.getString("COMDES"));
		productModelMappingVO.setProductGroupDescription(rs.getString("PRDGRPDES"));
		if(rs.getDate("PRDSTRDAT")!=null){
			productModelMappingVO.setProductStartDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("PRDSTRDAT")));
		}
		if(rs.getDate("PRDENDDAT")!=null){
			productModelMappingVO.setProductEndDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("PRDENDDAT")));
		}
		productModelMappingVO.setPossibleBookingType(rs.getString("POSBKGTYP"));
		if((rs.getString("CONSOL")!=null)&&(rs.getString("CONSOL").trim().length()>0)){
		productModelMappingVO.setConsolShipment(rs.getString("CONSOL"));
		}
		if((rs.getString("UPSPRDNAM")!=null)&&(rs.getString("UPSPRDNAM").trim().length()>0)){
			productModelMappingVO.setUpsellingProductCodes(rs.getString("UPSPRDNAM"));
		}
		
		
		
		
		
		return productModelMappingVO;
		
	}

}
