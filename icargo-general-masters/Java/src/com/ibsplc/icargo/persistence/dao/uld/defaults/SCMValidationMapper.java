/*
 * SCMValidationMapper.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.uld.defaults.message.vo.SCMValidationVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3459
 *
 */
public class SCMValidationMapper implements Mapper<SCMValidationVO> {
	
	private Log log = LogFactory.getLogger("ULD_DEFAULTS");
	
	/**
	    * @param rs
	    * @return ULDListVO
	    * @throws SQLException
	    */
		public SCMValidationVO map(ResultSet rs) throws SQLException{
			log.entering("INSIDE THE MAPPER","map(ResultSet rs)");
						
			SCMValidationVO validationVO =new SCMValidationVO();
			/*if(rs.getInt("TYP") == 0 && rs.getInt("COUNT") >0){
				validationVO.setNotSighted(rs.getString("COUNT"));
			}*/
			//else{
				validationVO.setCompanyCode(rs.getString("CMPCOD")); 
				validationVO.setUldNumber(rs.getString("ULDNUM"));
				validationVO.setLocation(rs.getString("LOC"));
				validationVO.setAirportCode(rs.getString("CURARP"));
				validationVO.setFacilityType(rs.getString("FACTYP"));
				validationVO.setScmFlag(rs.getString("SCMFLG"));
				validationVO.setPrevMissingFlag(rs.getString("PREVMISSING"));
				if(rs.getDate("SCMDAT") != null) {
					validationVO.setScmdate(new LocalDate(validationVO.getAirportCode(),Location.ARP ,rs.getDate("SCMDAT")));
				}
				validationVO.setTotal(rs.getString("COUNT"));
				validationVO.setNotSighted(rs.getString("MSNCOUNT"));
				//Added by A-7131 for ICRD-213319 starts
				String lastMovementDtls = rs.getString("LSTMVTDTL");
				if (lastMovementDtls != null && lastMovementDtls.trim().length() > 0) {
					String[] mvtDtls = lastMovementDtls.split("~");
					if (mvtDtls != null) {
						if (mvtDtls.length > 2) {
							if (mvtDtls[0] != null && mvtDtls[1] != null && mvtDtls[2] != null
									&& mvtDtls[0].trim().length() > 0 
									&& mvtDtls[1].trim().length() > 0 && mvtDtls[2].trim().length() > 0) {
							validationVO.setFlightDetails(mvtDtls[0] + " " + mvtDtls[1] + "~" + mvtDtls[2]);
							} else {
								validationVO.setFlightDetails("");
							}
						}
						if (mvtDtls.length > 4) {
							if (mvtDtls[3] != null && mvtDtls[4] != null && mvtDtls[3].trim().length() > 0 
									&& mvtDtls[4].trim().length() > 0) {
								validationVO.setFlightSegment(mvtDtls[3] + " - " + mvtDtls[4]);
							} else {
								validationVO.setFlightSegment("");
							}
						}
						if (mvtDtls.length > 5) {
							if (mvtDtls[5] != null && mvtDtls[5].trim().length() > 0) {
								validationVO.setRemarks(mvtDtls[5]);
							} else {
								validationVO.setRemarks("");
							}							
						}
					}
				}
				if (ULDVO.SCM_MISSING_STOCK.equals(rs.getString("DISCOD"))) {
					validationVO.setMissingDiscrepancyCaptured(true);
				}
				//Added by A-7131 for ICRD-213319 ends
			//}
			
			log.log(Log.INFO, "THe SCMValidationVO details are", validationVO);
			log.exiting("SCMValidationMapper","SCMValidationMapper");
			return validationVO;
			
		}

}
