/*
 * ListSCMMultiMapper.java Created on Dec 19, 2005
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

import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 *
 */
public class ListSCMMultiMapper implements MultiMapper<ULDSCMReconcileVO>{
	
	private Log log = LogFactory.getLogger("ULD MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ListCustomerPointsVO
	 * @throws SQLException
	 */
	public List<ULDSCMReconcileVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("ListSCMMultiMapper","Map");
		String previousKey = "";
		String currentKey = "";
		String currrentChildKey ="";
		String previousChildkey="";
		int choice =0;
		 List<ULDSCMReconcileVO> reconcileVOs = new ArrayList<ULDSCMReconcileVO>();
		
		
		ULDSCMReconcileVO reconcileVO = null;
		while(resultSet.next()){
			currentKey = new StringBuilder(resultSet.getString("CMPCOD"))
			                              .append(resultSet.getString("ARPCOD"))
			                              .append(resultSet.getInt("ARLIDR"))			                              
			                              .append(resultSet.getInt("SEQNUM"))
			                              .toString();
			
			log.log(Log.FINE, "Current Key -->", currentKey);
			if(!currentKey.equals(previousKey)){
				log.log(Log.FINE, "Previous Key -->", previousKey);
				if(choice==1) {
					choice=0;
					reconcileVO.setHasUldErrors(true);
				}
				if(reconcileVO != null) {
					reconcileVOs.add(reconcileVO);
				}
				reconcileVO = new ULDSCMReconcileVO();
				reconcileVO.setCompanyCode(resultSet.getString("CMPCOD"));
				reconcileVO.setAirportCode(resultSet.getString("ARPCOD"));
				reconcileVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
				
				if(resultSet.getDate("STKCHKDAT") != null && reconcileVO.getAirportCode() == null) {
					reconcileVO.setStockCheckDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("STKCHKDAT")));
				}else if(resultSet.getDate("STKCHKDAT") != null && reconcileVO.getAirportCode() != null){
					reconcileVO.setStockCheckDate(new LocalDate(reconcileVO.getAirportCode(),
																	Location.ARP,resultSet.getTimestamp("STKCHKDAT")));
				}
				reconcileVO.setSequenceNumber(resultSet.getString("SEQNUM"));
				reconcileVO.setMessageSendFlag(resultSet.getString("MSGSNDFLG"));
				
				if(previousKey.trim().length() > 0){
					//increment();
				}
				previousKey = currentKey;
			}
			currrentChildKey = new StringBuilder(resultSet.getString("CMPCOD"))
								            .append(resultSet.getString("ARPCOD"))
								            .append(resultSet.getInt("ARLIDR"))		
								            .append(resultSet.getInt("SEQNUM"))
							                 .append(resultSet.getString("ULDNUM"))
			     		              		.toString();
			if(!currrentChildKey.equals(previousChildkey)){
				if(resultSet.getString("ERRCOD") != null ) {
					choice =1;
				}
				previousChildkey = currrentChildKey;
			}
			
		}
		if(reconcileVO != null) {
			if(choice==1) {
				reconcileVO.setHasUldErrors(true);
			}
			
			reconcileVOs.add(reconcileVO);
			
		}
		log.exiting("ListSCMMultiMapper","Map");
		log.log(Log.INFO, "reconcileVOs", reconcileVOs);
		return reconcileVOs;
	}
	
}
