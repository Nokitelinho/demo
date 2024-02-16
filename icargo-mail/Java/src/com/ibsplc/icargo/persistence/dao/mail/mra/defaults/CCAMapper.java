/* CCAMapper.java Created on July 15,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

/**
 * @author A-3447
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3447
 * CCAMapper
 * implements Mapper<CCAdetailsVO>
 */

public class CCAMapper implements Mapper<CCAdetailsVO> {
	/**
	 * class name
	 */
	private static final String CLASS_NAME = "CCADetailsMapper";

	/**
	 * Logger
	 */

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");

	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public CCAdetailsVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "Mapper");

		CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();
		if (rs.getDate("BILLTO") != null) {
			cCAdetailsVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("BILLTO"))
					.toDisplayDateOnlyFormat());
		}
		if (rs.getDate("BILFRM") != null) {
			cCAdetailsVO.setBillingPeriodFrom(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("BILFRM"))
					.toDisplayDateOnlyFormat());
		}
		if (rs.getDate("ISSDAT") != null) {
			cCAdetailsVO.setIssueDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getDate("ISSDAT"))
					.toDisplayDateOnlyFormat());
		}
		cCAdetailsVO.setIssuingParty(rs.getString("ISSPARTY"));
		cCAdetailsVO.setAirlineCode(rs.getString("ARLCOD"));
		cCAdetailsVO.setLocation(rs.getString("LOCCOD"));
		cCAdetailsVO.setCcaType(rs.getString("CCATYP"));
		cCAdetailsVO.setOrigin(rs.getString("ORGCOD"));
		cCAdetailsVO.setDestination(rs.getString("DSTCOD"));
		cCAdetailsVO.setCategory(rs.getString("CATCOD"));
		cCAdetailsVO.setSubClass(rs.getString("SUBCLS"));
		cCAdetailsVO.setGrossWeight(rs.getDouble("GRSWGT"));
//		cCAdetailsVO.setChgGrossWeight(rs.getDouble("CHGWGT"));
		cCAdetailsVO.setDestination(rs.getString("DSTCOD"));
//		cCAdetailsVO.setDueArl(rs.getDouble("DUEARL"));
//		cCAdetailsVO.setDuePostDbt(rs.getDouble("DUEPSTDBT"));
		cCAdetailsVO.setGpaCode(rs.getString("GPACOD"));
		cCAdetailsVO.setGpaName(rs.getString("GPANAM"));
		cCAdetailsVO.setRevGrossWeight(rs.getDouble("REVGRSWGT"));
//		cCAdetailsVO.setRevChgGrossWeight(rs.getDouble("REVCHGWGT"));
		cCAdetailsVO.setRevDStCode(rs.getString("REVDSTCOD"));
//		cCAdetailsVO.setRevDueArl(rs.getDouble("REVDUEARL"));
		cCAdetailsVO.setRevDuePostDbt(rs.getDouble("REVDUEPSTDBT"));
		cCAdetailsVO.setRevGpaCode(rs.getString("REVGPACOD"));
		cCAdetailsVO.setRevGpaName(rs.getString("REVGPANAM"));
		cCAdetailsVO.setCcaRemark(rs.getString("CCARMK"));
		cCAdetailsVO.setRevOrgCode(rs.getString("REVORGCOD"));
		cCAdetailsVO.setRevDStCode(rs.getString("REVDSTCOD"));
		cCAdetailsVO.setGrossWeightChangeInd(rs.getString("GRSWGTIND"));
		/**
		 * for cca details using dsn
		 * 
		 */
		cCAdetailsVO.setCcaRefNumber(rs.getString("CCAREFNUM"));
		cCAdetailsVO.setBillingBasis(rs.getString("BLGBAS"));
		cCAdetailsVO.setSerialNumber(rs.getInt("SERNUM"));
		cCAdetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		//Added by A-7540
		cCAdetailsVO.setAutoMca(rs.getString("AUTMCA"));
		log.log(Log.FINE, "Vos Returned From Mapper", cCAdetailsVO);
		return cCAdetailsVO;
	}

}
