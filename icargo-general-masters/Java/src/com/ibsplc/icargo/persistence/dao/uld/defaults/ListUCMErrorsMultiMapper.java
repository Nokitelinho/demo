/*
 * ListUCMErrorsMultiMapper.java Created on Aug 1, 2005
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


import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.PageAwareMultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1883
 *
 */
public class ListUCMErrorsMultiMapper  extends PageAwareMultiMapper<ULDFlightMessageReconcileVO>{
	
	private Log log = LogFactory.getLogger("ULD MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ListCustomerPointsVO
	 * @throws SQLException
	 */
	public List<ULDFlightMessageReconcileVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("ListUCMErrorsMultiMapper","Map");
		String previousKey = "";
		String currentKey = "";
		String currrentChildKey ="";
		String previousChildkey="";
		int choice =0;
		List<ULDFlightMessageReconcileVO> reconcileVOs = new ArrayList<ULDFlightMessageReconcileVO>();
		
		
		ULDFlightMessageReconcileVO reconcileVO = null;
		while(hasNext()){
			currentKey = new StringBuilder(resultSet.getInt("FLTCARIDR"))
			                           .append(resultSet.getString("FLTNUM"))
			                            .append(resultSet.getInt("FLTSEQNUM"))
			                              .append(resultSet.getString("ARPCOD"))
			                              .append(resultSet.getString("MSGTYP"))
			                              .append(resultSet.getString("CMPCOD"))
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
				reconcileVO = new ULDFlightMessageReconcileVO();
				reconcileVO.setCompanyCode(resultSet.getString("CMPCOD"));
				reconcileVO.setFlightCarrierIdentifier(resultSet.getInt("FLTCARIDR"));
				reconcileVO.setFlightNumber(resultSet.getString("FLTNUM"));
				reconcileVO.setFlightSequenceNumber(resultSet.getInt("FLTSEQNUM"));
				reconcileVO.setAirportCode(resultSet.getString("ARPCOD"));
				if(resultSet.getDate("FLTDAT") != null && reconcileVO.getAirportCode() == null) {
					reconcileVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("FLTDAT")));
				}else if(resultSet.getDate("FLTDAT") != null && reconcileVO.getAirportCode() != null){
					reconcileVO.setFlightDate(new LocalDate(reconcileVO.getAirportCode(),
																	Location.ARP,resultSet.getDate("FLTDAT")));
				}
				
				reconcileVO.setMessageType(resultSet.getString("MSGTYP"));
				reconcileVO.setSequenceNumber(resultSet.getString("SEQNUM"));
				reconcileVO.setErrorCode(resultSet.getString("UCMERRCOD"));
				reconcileVO.setCarrierCode(resultSet.getString("ARLCOD"));
				reconcileVO.setMessageSendFlag(resultSet.getString("MSGSNDFLG"));
				// A-5125 cause of ICRD-4664  
				if(resultSet.getDate("ACTDAT")!=null){
				reconcileVO.setActualDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getDate("ACTDAT")));
				}
				
				
				
				
				previousKey = currentKey;
				increment();
			}
			currrentChildKey = new StringBuilder(resultSet.getInt("FLTCARIDR"))
							            .append(resultSet.getString("FLTNUM"))
							            .append(resultSet.getInt("FLTSEQNUM"))
							             .append(resultSet.getString("ARPCOD"))
							              .append(resultSet.getString("MSGTYP"))
							              .append(resultSet.getString("CMPCOD"))
							              .append(resultSet.getInt("SEQNUM"))
							              .append(resultSet.getString("ULDNUM"))
			     		              		.toString();
			if(!currrentChildKey.equals(previousChildkey)){
				if(resultSet.getString("ULDERRCOD") != null ) {
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
		log.exiting("ListUCMErrorsMultiMapper","Map");
		log.log(Log.INFO, "reconcileVOs", reconcileVOs);
		return reconcileVOs;
	}
	
}
