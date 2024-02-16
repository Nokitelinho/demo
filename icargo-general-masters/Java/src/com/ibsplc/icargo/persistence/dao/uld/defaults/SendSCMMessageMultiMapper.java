/*
 * SendSCMMessageMultiMapper.java Created on Aug 1, 2005
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
import java.util.Collection;
import java.util.List;



import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDSCMReconcileDetailsVO;
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
public class SendSCMMessageMultiMapper  implements MultiMapper<ULDSCMReconcileVO>{
	
	private Log log = LogFactory.getLogger("ULD MANAGEMENT");
	/**
	 * @param resultSet
	 * @return ListCustomerPointsVO
	 * @throws SQLException
	 */
	public List<ULDSCMReconcileVO> map(ResultSet resultSet) 
	throws SQLException {
		log.entering("SendSCMMessageMultiMapper","Map");
		String previousKey = "";
		String currentKey = "";
		String currrentChildKey ="";
		String previousChildkey="";
		int choice =0;
		List<ULDSCMReconcileVO> reconcileVOs = new ArrayList<ULDSCMReconcileVO>();
		
		
		ULDSCMReconcileVO reconcileVO = null;
		Collection<ULDSCMReconcileDetailsVO> sightedreconcileDetailsVOs = null;
		Collection<ULDSCMReconcileDetailsVO> nonsightedreconcileDetailsVOs = null;
		ULDSCMReconcileDetailsVO uLDSCMReconcileDetailsVO =null;
		
		while(resultSet.next()) {
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
				
				if(resultSet.getDate("STKCHKDAT") != null) {
					reconcileVO.setStockCheckDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultSet.getTimestamp("STKCHKDAT")));
				}
				
				reconcileVO.setAirportCode(resultSet.getString("ARPCOD"));
				reconcileVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
				
				reconcileVO.setSequenceNumber(resultSet.getString("SEQNUM"));
				reconcileVO.setMessageSendFlag(resultSet.getString("MSGSNDFLG"));
							
				sightedreconcileDetailsVOs = new ArrayList<ULDSCMReconcileDetailsVO>();
				nonsightedreconcileDetailsVOs = new ArrayList<ULDSCMReconcileDetailsVO>();
				previousKey = currentKey;
				
			}

			currrentChildKey = new StringBuilder(resultSet.getString("CMPCOD"))
								            .append(resultSet.getString("ARPCOD"))
								             .append(resultSet.getInt("ARLIDR"))
								            .append(resultSet.getInt("SEQNUM"))
							                 .append(resultSet.getString("ULDNUM"))
			     		              		.toString();
			log.log(Log.FINE, "currrentChildKey------->>", currrentChildKey);
			log.log(Log.FINE, "previousChildkey------->>", previousChildkey);
			if(!currrentChildKey.equals(previousChildkey)){
				if(resultSet.getString("ERRCOD") != null ) {
					choice =1;
				}
				uLDSCMReconcileDetailsVO = new ULDSCMReconcileDetailsVO();
				uLDSCMReconcileDetailsVO.setAirportCode(resultSet.getString("ARPCOD"));
				uLDSCMReconcileDetailsVO.setAirlineIdentifier(resultSet.getInt("ARLIDR"));
				uLDSCMReconcileDetailsVO.setCompanyCode(resultSet.getString("CMPCOD"));
				uLDSCMReconcileDetailsVO.setSequenceNumber(resultSet.getString("SEQNUM"));
				uLDSCMReconcileDetailsVO.setUldNumber(resultSet.getString("ULDNUM"));
				log.log(Log.FINE,
						"uLDSCMReconcileDetailsVO.getUldNumber()------->>",
						uLDSCMReconcileDetailsVO.getUldNumber());
				uLDSCMReconcileDetailsVO.setErrorCode(resultSet.getString("ERRCOD"));
				if ("M".equals(resultSet.getString("DISCOD"))) {
					uLDSCMReconcileDetailsVO
							.setMissingDiscrepancyCaptured(true);
				}
				String uldStatus=resultSet.getString("ULDSTKSTA");
				if("F".equals(uldStatus)|| "N".equals(uldStatus)){
					sightedreconcileDetailsVOs.add(uLDSCMReconcileDetailsVO);
				}else if(!"M".equals(uldStatus)){
					nonsightedreconcileDetailsVOs.add(uLDSCMReconcileDetailsVO);
				}
				previousChildkey = currrentChildKey;
			}
			
		}
		if(reconcileVO != null) {
			if(choice==1) {
				reconcileVO.setHasUldErrors(true);
			}
			if(sightedreconcileDetailsVOs != null && sightedreconcileDetailsVOs.size()>0) {
				reconcileVO.setReconcileDetailsVOs(sightedreconcileDetailsVOs);
			}else if(nonsightedreconcileDetailsVOs!=null && nonsightedreconcileDetailsVOs.size()>0){
				reconcileVO.setReconcileDetailsVOs(nonsightedreconcileDetailsVOs);
			}
			
			reconcileVOs.add(reconcileVO);
			
		}
		log.exiting("SendSCMMessageMultiMapper","Map");
		log.log(Log.INFO, "reconcileVOs", reconcileVOs);
		return reconcileVOs;
	}
	
}
