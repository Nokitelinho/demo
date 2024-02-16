package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author SAP15
 */
@Slf4j
public class MailResditDetailsMultiMapper implements MultiMapper<HashMap<String, Collection<MailResditVO>>> {
	private static final String CLASS_NAME = "MailResditDetailsMultiMapper";

	/** 
	*/
	public List<HashMap<String, Collection<MailResditVO>>> map(ResultSet rs) throws SQLException {
		List<HashMap<String, String>> statusMaps = new ArrayList<HashMap<String, String>>();
		log.debug(CLASS_NAME + " : " + "map" + " Entering");
		HashMap<String, Collection<MailResditVO>> resditDetailsMap = new HashMap<String, Collection<MailResditVO>>();
		Collection<MailResditVO> mailResditVOs = null;
		StringBuilder keyBuilder = null;
		MailResditVO mailResditVO = null;
		while (rs.next()) {
			keyBuilder = new StringBuilder(rs.getString("MALIDR")).append(rs.getString("EVTPRT"))
					.append(rs.getString("EVTCOD"));
			mailResditVOs = resditDetailsMap.get(keyBuilder.toString());
			if (mailResditVOs == null) {
				mailResditVOs = new ArrayList<MailResditVO>();
				resditDetailsMap.put(keyBuilder.toString(), mailResditVOs);
			}
			mailResditVO = new MailResditVO();
			mailResditVO.setCarrierId(rs.getInt("FLTCARIDR"));
			mailResditVO.setFlightNumber(rs.getString("FLTNUM"));
			mailResditVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			mailResditVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			mailResditVO.setCompanyCode(rs.getString("CMPCOD"));
			mailResditVO.setMailId(rs.getString("MALIDR"));
			mailResditVO.setEventAirport(rs.getString("EVTPRT"));
			mailResditVO.setEventCode(rs.getString("EVTCOD"));
			mailResditVO.setResditSequenceNum(rs.getInt("SEQNUM"));
			mailResditVO.setResditSentFlag(rs.getString("RDTSND"));
			mailResditVO.setProcessedStatus(rs.getString("PROSTA"));
			mailResditVOs.add(mailResditVO);
		}
		List<HashMap<String, Collection<MailResditVO>>> map = new ArrayList<HashMap<String, Collection<MailResditVO>>>();
		map.add(resditDetailsMap);
		return map;
	}
}
