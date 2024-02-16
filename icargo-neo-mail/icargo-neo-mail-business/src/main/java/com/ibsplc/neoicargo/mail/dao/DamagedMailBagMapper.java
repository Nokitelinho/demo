package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.DamagedMailbagVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author A-2037
 */
public class DamagedMailBagMapper implements Mapper<DamagedMailbagVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public DamagedMailbagVO map(ResultSet rs) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String airportCode = rs.getString("ARPCOD");
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		damagedMailbagVO.setAirportCode(rs.getString("ARPCOD"));
		damagedMailbagVO.setDamageCode(rs.getString("DMGCOD"));
		if (rs.getTimestamp("DMGDAT") != null) {
			damagedMailbagVO.setDamageDate(localDateUtil.getLocalDate(airportCode, rs.getTimestamp("DMGDAT")));
		}
		damagedMailbagVO.setDamageDescription(rs.getString("DMGDES"));
		damagedMailbagVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		damagedMailbagVO.setDsn(rs.getString("DSN"));
		damagedMailbagVO.setMailbagId(rs.getString("MALIDR"));
		damagedMailbagVO.setMailClass(rs.getString("MALCLS"));
		damagedMailbagVO.setOperationType(rs.getString("OPRTYP"));
		damagedMailbagVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		damagedMailbagVO.setRemarks(rs.getString("RMK"));
		damagedMailbagVO.setUserCode(rs.getString("USRCOD"));
		damagedMailbagVO.setYear(rs.getInt("YER"));
		damagedMailbagVO.setReturnedFlag(rs.getString("RTNFLG"));
		damagedMailbagVO.setPaCode(rs.getString("POACOD"));
		return damagedMailbagVO;
	}
}
