/*
 * BagCountMapper.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
*
*
* This class is used to calculate the No of Bags in case of the Barrow
* for the Flight or Destination
*/
public  class BagCountMapper implements Mapper<ContainerVO> {

   /**
    * @author a-1936
    * @param rs
    * @return
    * @throws SQLException
    * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
    */
   public ContainerVO map(ResultSet rs) throws SQLException {
   	ContainerVO containerVO = new ContainerVO();
   	containerVO.setBags(rs.getInt("ACPBAG"));
   	//containerVO.setWeight(rs.getDouble("ACPWGT"));
   	containerVO.setContainerNumber(rs.getString("CONNUM"));
 	containerVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
   	containerVO.setReceivedBags(rs.getInt("RCVBAG"));
   	//containerVO.setReceivedWeight(rs.getDouble("RCVWGT"));
   	containerVO.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT")));//added by A-7371
       containerVO.setWarehouseCode(rs.getString("WHSCOD"));
       containerVO.setLocationCode(rs.getString("LOCCOD"));
       return containerVO;
   }

}
