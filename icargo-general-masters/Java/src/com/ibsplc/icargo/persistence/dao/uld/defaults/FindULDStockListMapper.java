/*
 * FindULDStockListMapper.java Created on Oct 5, 2005
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
public class FindULDStockListMapper implements MultiMapper<ULDStockListVO>{
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ULDStockListVO
	 * @throws SQLException
	 */
	public List<ULDStockListVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("FindULDStockListMapper","Map");
		ULDStockListVO parentStockListVO = null;
		List<ULDStockListVO> parentCol = new ArrayList<ULDStockListVO>();
		ULDStockListVO childStockListVO = null;
		String presPK = "";
		String prevPK = "";
		while(resultSet.next()){		
			presPK = new StringBuilder(
					resultSet.getString("ARLCOD"))
				.append(resultSet.getString("ARPCOD"))
				.append(resultSet.getString("ULDGRPCOD")).toString();
			if(!presPK.equals(prevPK)){	
				if(parentStockListVO != null){

					parentCol.add(parentStockListVO);
					/* added by a-3278 for 52218 on 30Jun09( to add the last parent)
					 * The last parent wont be added by the main loop
					 * */
					//increment();
					//a-3278 ends
				}
				parentStockListVO = new ULDStockListVO();
				parentStockListVO.setStationCode(resultSet.getString("ARPCOD"));
				parentStockListVO.setLevelValue(resultSet.getString("ARPCOD"));
				parentStockListVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
				parentStockListVO.setAirlineCode(resultSet.getString("ARLCOD"));
				parentStockListVO.setUldGroupCode(resultSet.getString("ULDGRPCOD"));
				parentStockListVO.setUldStockLists(new ArrayList<ULDStockListVO>());
				prevPK = presPK;
				/* commented by a-3278 for 52218 on 30Jun09( to add the last parent)
				 * The last parent wont be added by the main loop
	 	 		 * */
				//increment();
				//a-3278 ends
				
			}


			childStockListVO = new ULDStockListVO();
			
			childStockListVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
			childStockListVO.setAirlineCode(resultSet.getString("ARLCOD"));
			childStockListVO.setUldTypeCode(resultSet.getString("ULDTYP"));
			childStockListVO.setUldGroupCode(resultSet.getString("ULDGRPCOD"));
			childStockListVO.setUldNature(resultSet.getString("ULDNAT"));
			//commented by a-3278 since available field is removed from the screen
			//childStockListVO.setAvailable(resultSet.getInt("ACTUALAV"));
			//a-3278 ends
			childStockListVO.setSystemAvailable(resultSet.getInt("AVASPERSYSTEM"));
			//added by a-3278 for CR QF1199 on 08Apr09
			childStockListVO.setBalance(resultSet.getInt("BALANCE"));
			//a-3278 ends
			childStockListVO.setOff(resultSet.getInt("OFFARP"));
			//commented by a-3278 since damaged field is removed from the screen
			//childStockListVO.setDamaged(resultSet.getInt("DAMAGED"));
			//a-3278 ends
			childStockListVO.setNonOperational(resultSet.getInt("NONOPR"));
			childStockListVO.setInStock(resultSet.getInt("INSTOCK"));
			//commented by a-3045 on 25 Nov 08 starts
			//childStockListVO.setInFlight(resultSet.getInt("INFLIGHT"));
			//commented by a-3045 on 25 Nov 08 ends
			childStockListVO.setMaxQty(resultSet.getInt("MAXQTY"));
			childStockListVO.setMinQty(resultSet.getInt("MINQTY"));
			childStockListVO.setLevelValue(resultSet.getString("ARPCOD"));
			childStockListVO.setStationCode(resultSet.getString("ARPCOD"));			
			


			parentStockListVO.getUldStockLists().add(childStockListVO);
			//commented by a-3278 since available field is removed from the screen
			//parentStockListVO.setAvailable(parentStockListVO.getAvailable()+resultSet.getInt("ACTUALAV"));
			//a-3278 ends
			parentStockListVO.setSystemAvailable(parentStockListVO.getSystemAvailable()+resultSet.getInt("AVASPERSYSTEM"));
			//added by a-3278 for CR QF1199 on 08Apr09
			parentStockListVO.setBalance(parentStockListVO.getBalance()+resultSet.getInt("BALANCE"));
			//a-3278 ends
			//commented by a-3278 since damaged field is removed from the screen
			//parentStockListVO.setDamaged(parentStockListVO.getDamaged()+resultSet.getInt("DAMAGED"));
			//a-3278 ends
			parentStockListVO.setNonOperational(parentStockListVO.getNonOperational()+resultSet.getInt("NONOPR"));
			parentStockListVO.setInStock(parentStockListVO.getInStock()+resultSet.getInt("INSTOCK"));
			//commented by a-3045 on 25 Nov 08 starts
			//parentStockListVO.setInFlight(parentStockListVO.getInFlight()+resultSet.getInt("INFLIGHT"));
			//commented by a-3045 on 25 Nov 08 ends
			parentStockListVO.setOff(parentStockListVO.getOff()+resultSet.getInt("OFFARP"));
			parentStockListVO.setMaxQty(parentStockListVO.getMaxQty()+resultSet.getInt("MAXQTY"));
			parentStockListVO.setMinQty(parentStockListVO.getMinQty()+resultSet.getInt("MINQTY"));	
		}
		if(parentStockListVO != null){
			parentCol.add(parentStockListVO);
			/* added by a-3278 for 52218 on 30Jun09( to add the last parent)
			 * The last parent wont be added by the main loop
			 * */
			//increment();
			//a-3278 ends
		}
		log.log(Log.INFO, "%%%%%%%%%%%%%FINAL COL%%%%%%%%%%%%%%", parentCol);
		return parentCol;
	}
}
