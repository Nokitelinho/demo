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

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-5219
 *
 */
public class MailbagHistoryMultiMapper implements MultiMapper<HashMap<Long,Collection<MailbagHistoryVO>>> {
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
			String scannedPort = rs.getString("SCNPRT");
			MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
			mailbagHistoryVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagHistoryVO.setMailbagId(rs.getString("MALIDR"));
	
			
			if (rs.getTimestamp("SCNDAT") != null) {
				mailbagHistoryVO.setScanDate(new LocalDate(scannedPort,	Location.ARP, rs.getTimestamp("SCNDAT")));
			}
			//Added by A-5945 for ICRD-118215 starts
			mailbagHistoryVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
			mailbagHistoryVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
			mailbagHistoryVO.setDsn(rs.getString("DSN"));
			mailbagHistoryVO.setHistorySequenceNumber(rs.getInt("MALHISIDR"));
			mailbagHistoryVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailbagHistoryVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailbagHistoryVO.setYear(rs.getInt("YER"));
			//Added by A-5945 for ICRD-118215 ends
			
			//Added by a-7540 as a part of ICRD-197419 by a-7540
			mailbagHistoryVO.setMailRemarks(rs.getString("MALRMK"));
			
			mailbagHistoryVO.setMailStatus(rs.getString("MALSTA"));
			String mailStatus = rs.getString("MALSTA");
			mailbagHistoryVO.setScannedPort(scannedPort);
			mailbagHistoryVO.setFlightNumber(rs.getString("FLTNUM"));
			mailbagHistoryVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			mailbagHistoryVO.setContainerType(rs.getString("CONTYP"));
			mailbagHistoryVO.setCarrierCode(rs.getString("FLTCARCOD"));
	
			if (rs.getTimestamp("FLTDAT") != null) {
				mailbagHistoryVO.setFlightDate(new LocalDate(scannedPort, Location.ARP, rs.getTimestamp("FLTDAT")));
			}
			if(!"RTN".equals(mailStatus)){
				mailbagHistoryVO.setContainerNumber(rs.getString("CONNUM"));
			}
			mailbagHistoryVO.setPou(rs.getString("POU"));
			mailbagHistoryVO.setScanUser(rs.getString("SCNUSR"));
	
			if (rs.getTimestamp("UTCSCNDAT") != null) {
				mailbagHistoryVO.setUtcScanDate(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
						rs.getTimestamp("UTCSCNDAT")));
			}
	
			if (rs.getTimestamp("MSGTIM") != null) {
				mailbagHistoryVO.setMessageTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
						rs.getTimestamp("MSGTIM")));
			}
			mailbagHistoryVO.setPaBuiltFlag(rs.getString("POAULDFLG"));
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
