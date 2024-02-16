/*
 * FindStockDeviationMapper.java Created on Oct 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.stock.vo.ULDStockListVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class FindStockDeviationMapper implements MultiMapper<ULDStockListVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDStockListVO
	 * @throws SQLException
	 */
	public List<ULDStockListVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("FindStockDeviationMapper","Mapppppp");
		ULDStockListVO parentStockListVO = null;
		List<ULDStockListVO> parentCol = new ArrayList<ULDStockListVO>();
		String presPK = "";
		String prevPK = "";
		log.log(Log.INFO, "%presPK%%%  ", presPK);
		while (resultSet.next()) {
			log.log(Log.INFO, "%presPK%%%  ", presPK);
			presPK = new StringBuilder(resultSet.getString("CURARP"))
				.append(resultSet.getString("ULDTYP")).toString();
			log.log(Log.INFO, "%presPK%%%  ", presPK);
			if(!presPK.equals(prevPK)){	
				log.log(Log.INFO, "%%%A%%%%%%%  ", presPK);
				if(parentStockListVO != null){
					log.log(Log.INFO, "%%parentStockListVO%%%%%  ",
							parentStockListVO);
					parentCol.add(parentStockListVO);
				}
				parentStockListVO = new ULDStockListVO();
				parentStockListVO.setStationCode(resultSet.getString("CURARP"));
				parentStockListVO.setUldTypeCode(resultSet.getString("ULDTYP"));
				prevPK = presPK;
			}
			//Notification trigger to be on basis of SystemAvailable (CR QF1199)
			parentStockListVO.setSystemAvailable(parentStockListVO.getSystemAvailable()+resultSet.getInt("SYSAVAILABLE"));
			parentStockListVO.setMaxQty(parentStockListVO.getMaxQty()+resultSet.getInt("MAXQTY"));
			parentStockListVO.setMinQty(parentStockListVO.getMinQty()+resultSet.getInt("MINQTY"));	
			parentStockListVO.setUldGroupCode(resultSet.getString("ULDGRPCOD"));
			parentStockListVO.setUldNature(resultSet.getString("ULDNAT"));
		}
		if(parentStockListVO != null){
			parentCol.add(parentStockListVO);
		}
		log.log(Log.INFO, "%%%%%%%%%%%%%FINAL COL%%%%%%%%%%%%%%", parentCol);
		return parentCol;
	}
}
