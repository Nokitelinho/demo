package com.ibsplc.icargo.persistence.dao.reco.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-6843
 *
 */
public class ExceptionEmbargosMapper implements
		MultiMapper<ExceptionEmbargoDetailsVO> {

	@Override
	public List<ExceptionEmbargoDetailsVO> map(ResultSet resultSet)
			throws SQLException {

		List<ExceptionEmbargoDetailsVO> exceptionEmbargoVOs= new ArrayList<ExceptionEmbargoDetailsVO>();
		ExceptionEmbargoDetailsVO exceptionEmbargoVO=null;
		 while (resultSet.next()) {
			 exceptionEmbargoVO= new ExceptionEmbargoDetailsVO();
			 exceptionEmbargoVO.setCompanyCode(resultSet.getString("CMPCOD"));
			 exceptionEmbargoVO.setShipmentPrefix(resultSet.getString("SHPPFX"));
			 exceptionEmbargoVO.setMasterDocumentNumber(resultSet.getString("MSTDOCNUM"));
			 exceptionEmbargoVO.setRemarks(resultSet.getString("RMK"));
			 exceptionEmbargoVO.setOperationFlag("N");
			 exceptionEmbargoVO.setSerialNumbers(new Integer(resultSet.getInt("SERNUM")).intValue());
			 
			 if (resultSet.getDate("STRDAT") != null) {
				 exceptionEmbargoVO.setStartDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultSet.getDate("STRDAT")));
			 }
			 if (resultSet.getDate("ENDDAT") != null) {
				 exceptionEmbargoVO.setEndDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, resultSet.getDate("ENDDAT")));
			 }
			
			 exceptionEmbargoVOs.add(exceptionEmbargoVO);
		 }
		return exceptionEmbargoVOs;
	
		
	}

}
