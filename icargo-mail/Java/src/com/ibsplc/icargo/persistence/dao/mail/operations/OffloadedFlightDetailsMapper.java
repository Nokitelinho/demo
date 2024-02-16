/*
 * OfflodDetailsMapper Created on Oct 11, 2014
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
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
*
*
* This class is used to find the offloded Info for a container
*/
public  class OffloadedFlightDetailsMapper implements Mapper<ContainerVO> {

   /**
    * @author a-3429
    * @param rs
    * @return
    * @throws SQLException
    * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
    */
   public ContainerVO map(ResultSet rs) throws SQLException {
   	ContainerVO containerVO = new ContainerVO();
   	containerVO.setOffloadedDescription(rs.getString("OFFLODDTL"));
   	containerVO.setOffloadCount(rs.getInt("OFFLODCNT"));
    return containerVO;
   }

}
