/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-5219
 *
 */
public class CarditDetailsOfMailbagsMapper implements MultiMapper<HashMap<Long,Collection<MailbagHistoryVO>>> {

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<HashMap<Long,Collection<MailbagHistoryVO>>> map(ResultSet rs) throws SQLException {
		HashMap<Long,Collection<MailbagHistoryVO>> historyMap 
		= new HashMap<Long,Collection<MailbagHistoryVO>>();
		Long key;
		while(rs.next()){
				key = rs.getLong("MALSEQNUM");
				String stationCode = rs.getString("STNCOD");
				MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
				mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
				mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
				if (rs.getTimestamp("CDTRCVDAT") != null) {
					mailbagHistoryVO.setScanDate(new LocalDate(stationCode,
							Location.ARP, rs.getTimestamp("CDTRCVDAT")));
					mailbagHistoryVO.setMessageTime(new LocalDate(stationCode,
							Location.ARP, rs.getTimestamp("CDTRCVDAT")));
				}
				mailbagHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
				if(rs.getTimestamp("FLTDAT")!=null)
					{
						mailbagHistoryVO.setFlightDate(new LocalDate(stationCode, Location.ARP, rs.getTimestamp("FLTDAT")));
					}
				mailbagHistoryVO.setCarrierCode(rs.getString("FLTCARCOD"));
				mailbagHistoryVO.setMailStatus(MailConstantsVO.CARDIT_EVENT);
				mailbagHistoryVO.setScannedPort(stationCode);
				mailbagHistoryVO.setPou(rs.getString("POU"));
				mailbagHistoryVO.setScanUser(rs.getString("SCNUSR"));
				if(!historyMap.containsKey(key)){
					Collection<MailbagHistoryVO> historyVOs = new ArrayList<MailbagHistoryVO>();
					historyVOs.add(mailbagHistoryVO);
					historyMap.put(key, historyVOs);
				}else{
					historyMap.get(key).add(mailbagHistoryVO);
				}
		}
		List<HashMap<Long,Collection<MailbagHistoryVO>>> map = 
			new ArrayList<HashMap<Long,Collection<MailbagHistoryVO>>>();
		map.add(historyMap);
		return map;
	}

}
