/*
 * LoyaltyPointsForAwbMultiMapper.java Created on Jan 26, 2006
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.customermanagement.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
//	Modified by : A-5175 on 24-Oct-2012	 for icrd-22065
public class LoyaltyPointsForAwbMultiMapper  implements MultiMapper<ListCustomerPointsVO>{
	
	private Log log = LogFactory.getLogger("CUSTOMER MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ListCustomerPointsVO
	 * @throws SQLException
	 */
	public List<ListCustomerPointsVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("LoyaltyPointsForAwbMultiMapper","Map");
		String previousKey = "";
		String currentKey = "";
		List<ListCustomerPointsVO> listCustomerPointsVOs = null;
		Collection<String> loyaltyProgrammes = null;
		StringBuilder buffer = null;
		ListCustomerPointsVO listCustomerPointsVO = null;
		while(resultSet.next()){
			buffer = new StringBuilder(resultSet.getString("MSTAWBNUM"));
			currentKey = buffer.toString();
			log.log(Log.FINE, "Current Key -->", currentKey);
			if(!currentKey.equals(previousKey)){
				log.log(Log.FINE, "Previous Key -->", previousKey);
				listCustomerPointsVO = new ListCustomerPointsVO();
				listCustomerPointsVO.setCompanyCode(resultSet.getString("CMPCOD"));
				listCustomerPointsVO.setCustomerCode(resultSet.getString("CUSCOD"));
				listCustomerPointsVO.setDocumentOwnerIdentifier(resultSet.getInt("DOCOWRIDR"));
				listCustomerPointsVO.setMasterAwbNumber(currentKey);
				listCustomerPointsVO.setAwbNumber(resultSet.getString("AWBNUM"));
				Timestamp time = resultSet.getTimestamp("LSTUPDDAT");
				if(time != null){
					listCustomerPointsVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,time));  
				}
				loyaltyProgrammes = new ArrayList<String>();
				listCustomerPointsVO.setLoyaltyProgrammes(loyaltyProgrammes);
				if(listCustomerPointsVOs == null){
					listCustomerPointsVOs = new ArrayList<ListCustomerPointsVO>(); 
				}
				listCustomerPointsVOs.add(listCustomerPointsVO);
				previousKey = currentKey;
				//increment();
			}
			collectAttributePionts(listCustomerPointsVO,resultSet);
			String loyaltyProgrammeCode = resultSet.getString("LTYPRGCOD");
			if(!(loyaltyProgrammes.contains(loyaltyProgrammeCode)) ){
			 loyaltyProgrammes.add(loyaltyProgrammeCode);
			}
		}
		log.exiting("LoyaltyPointsForAwbMultiMapper","Map");
		return listCustomerPointsVOs;
	}
	/**
	 * @param listCustomerPointsVO
	 * @param resultSet
	 * @throws SQLException
	 */
	private void collectAttributePionts(ListCustomerPointsVO listCustomerPointsVO,
			ResultSet resultSet)throws SQLException {
		log.entering("LoyaltyPointsForAwbMultiMapper","collectAttributePionts");
		String attribute = resultSet.getString("LTYATR");
		double points = resultSet.getDouble("PTSARD");
		if(ListCustomerPointsVO.DISTANCE_ATTRIBUTE.equals(attribute)){
			listCustomerPointsVO.setDistance(listCustomerPointsVO.getDistance()+points);
		}else if(ListCustomerPointsVO.REVENUE_ATTRIBUTE.equals(attribute)){
			listCustomerPointsVO.setRevenue(listCustomerPointsVO.getRevenue()+points);
		}else if(ListCustomerPointsVO.VOLUME_ATTRIBUTE.equals(attribute)){
			listCustomerPointsVO.setVolume(listCustomerPointsVO.getVolume()+points);
		}else if(ListCustomerPointsVO.WEIGHT_ATTRIBUTE.equals(attribute)){
			listCustomerPointsVO.setWeight(listCustomerPointsVO.getWeight()+points);
		}else if(ListCustomerPointsVO.YIELD_ATTRIBUTE.equals(attribute)){
			listCustomerPointsVO.setYield(listCustomerPointsVO.getYield()+points);
		}
		log.entering("LoyaltyPointsForAwbMultiMapper","collectAttributePionts");
	}
}
