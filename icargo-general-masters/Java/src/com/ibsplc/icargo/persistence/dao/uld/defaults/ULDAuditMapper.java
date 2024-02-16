/*
 * ULDAuditMapper.java Created on Sept 18, 2017
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Objects;

import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDAuditVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-7794
 *
 */
public class ULDAuditMapper implements Mapper<OperationalULDAuditVO>{

	@Override
	public OperationalULDAuditVO map(ResultSet result) throws SQLException {
		OperationalULDAuditVO operationalULDAuditVO = new OperationalULDAuditVO(null,null,null);
		
		if(result.getTimestamp("UPDTXNTIM") != null){
			operationalULDAuditVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION , Location.NONE , result.getTimestamp("UPDTXNTIM")));
		}
		operationalULDAuditVO.setStatus(result.getString("STA"));
		operationalULDAuditVO.setCarrierId(result.getInt("FLTCARIDR"));
		//Modified as part of ICRD-226069; 
		//In the case of carrier no need to show flight Number
		if(!Objects.equals("-1", result.getString("FLTNUM"))){
		operationalULDAuditVO.setFlightNumber(result.getString("FLTNUM"));
		}else{
			operationalULDAuditVO.setFlightNumber("");
		}
		operationalULDAuditVO.setFlightTailNumber(result.getString("TALNUM"));
		Date datOne = result.getDate("FLTDAT");
		if(datOne != null && operationalULDAuditVO.getAirport() == null){
			operationalULDAuditVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,result.getDate("FLTDAT")));
		}else if(datOne != null && operationalULDAuditVO.getAirport() != null){
			operationalULDAuditVO.setFlightDate(new LocalDate(operationalULDAuditVO.getAirport(),Location.ARP,result.getDate("FLTDAT")));
		}
		operationalULDAuditVO.setFlightCarrierCode(result.getString("FLTOWN"));
		operationalULDAuditVO.setRemarks(result.getString("AUDRMK"));			
		return operationalULDAuditVO;
	}
	

}
