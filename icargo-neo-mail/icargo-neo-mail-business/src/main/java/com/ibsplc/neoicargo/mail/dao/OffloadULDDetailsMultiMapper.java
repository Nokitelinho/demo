package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.time.ZonedDateTime;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadShipmentVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadULDDetailsVO;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.icargo.framework.util.time.Location;

/** 
 * Created by A-9529 on 22-08-2020.
 */
public class OffloadULDDetailsMultiMapper implements MultiMapper<OffloadULDDetailsVO> {
	private static final String MSTDOCNUM = "MSTDOCNUM";
	private static final String LEGORG = "LEGORG";
	private static final String FLTNUM = "FLTNUM";
	private static final String FLTCARIDR = "FLTCARIDR";
	private static final String FLTSEQNUM = "FLTSEQNUM";
	private static final String CMPCOD = "CMPCOD";
	private static final String ULDNUM = "ULDNUM";
	private static final String LEGSERNUM = "LEGSERNUM";
	private static final String RCVHNDFLG = "RCVHNDFLG";

	public List<OffloadULDDetailsVO> map(ResultSet rs) throws SQLException {
		OffloadULDDetailsVO offloadULDDetailsVO = null;
		OffloadShipmentVO offloadShipmentVO = null;
		HashMap<String, OffloadULDDetailsVO> offloadULDDetailsMap = new HashMap<>();
		HashMap<String, OffloadShipmentVO> offloadShipmentMap = new HashMap<>();
		String uldPK = null;
		String shipmentPK = null;
		while (rs.next()) {
			uldPK = rs.getString(CMPCOD) + rs.getString(FLTNUM) + rs.getString(FLTCARIDR) + rs.getString(FLTSEQNUM)
					+ rs.getString(ULDNUM) + rs.getString(LEGORG);
			if (offloadULDDetailsMap.containsKey(uldPK)) {
				offloadULDDetailsVO = offloadULDDetailsMap.get(uldPK);
				this.setSccCode(rs, offloadULDDetailsVO);
			} else {
				offloadULDDetailsVO = new OffloadULDDetailsVO();
				offloadULDDetailsVO.setCompanyCode(rs.getString(CMPCOD));
				offloadULDDetailsVO.setAirportCode(rs.getString(LEGORG));
				offloadULDDetailsVO.setFlightNumber(rs.getString(FLTNUM));
				offloadULDDetailsVO.setCarrierId(rs.getInt(FLTCARIDR));
				offloadULDDetailsVO.setFlightSequenceNumber((long) rs.getInt(FLTSEQNUM));
				offloadULDDetailsVO.setUldNumber(rs.getString(ULDNUM));
				offloadULDDetailsVO.setLegSerialNumber(rs.getInt(LEGSERNUM));
				offloadULDDetailsVO.setRampReceiveHandover("Y".equals(rs.getString(RCVHNDFLG)));
				this.setCarrierCodeForULD(rs, offloadULDDetailsVO);
				this.setFlightTimes(rs, offloadULDDetailsVO);
				this.setSccCode(rs, offloadULDDetailsVO);
				offloadULDDetailsMap.put(uldPK, offloadULDDetailsVO);
			}
			if (rs.getString(MSTDOCNUM) != null) {
				shipmentPK = rs.getString(CMPCOD) + rs.getString(LEGORG) + rs.getString(FLTCARIDR)
						+ rs.getString(FLTNUM) + rs.getString(FLTSEQNUM) + rs.getString(ULDNUM)
						+ rs.getString(MSTDOCNUM) + rs.getString("DOCOWRIDR") + rs.getString("SEQNUM")
						+ rs.getString("DUPNUM");
				if (offloadShipmentMap.containsKey(shipmentPK)) {
					offloadShipmentVO = offloadShipmentMap.get(shipmentPK);
				} else {
					offloadShipmentVO = new OffloadShipmentVO();
					this.setOffloadStatus(rs, offloadShipmentVO);
					offloadShipmentVO.setOwnerId(rs.getInt("DOCOWRIDR"));
					offloadShipmentVO.setDuplicateNumber(rs.getInt("DUPNUM"));
					offloadShipmentVO.setSequenceNumber(rs.getInt("SEQNUM"));
					offloadShipmentVO.setShipmentPrefix(rs.getString("SHPPFX"));
					offloadShipmentVO.setScc(rs.getString("SCCCOD"));
					offloadShipmentVO.setBlockCount(rs.getInt("BLKCNT"));
					offloadShipmentMap.put(shipmentPK, offloadShipmentVO);
				}
				if (offloadULDDetailsVO.getOffloadShipmentVOs() == null) {
					offloadULDDetailsVO.setOffloadShipmentVOs(new ArrayList<OffloadShipmentVO>());
				}
				offloadULDDetailsVO.getOffloadShipmentVOs().add(offloadShipmentVO);
			}
		}
		return new ArrayList<>(offloadULDDetailsMap.values());
	}

	private void setCarrierCodeForULD(ResultSet rs, OffloadULDDetailsVO offloadULDDetailsVO) throws SQLException {
		if (rs.getInt("APHCODUSE") == 2) {
			offloadULDDetailsVO.setCarrierCode(rs.getString("TWOAPHCOD"));
		} else {
			offloadULDDetailsVO.setCarrierCode(rs.getString("THRAPHCOD"));
		}
	}

	private void setOffloadStatus(ResultSet rs, OffloadShipmentVO offloadShipmentVO) throws SQLException {
		int uldOffloadShipmentPieces = rs.getInt("OFLPCS");
		int uldAvailableShipmentPieces = rs.getInt("ULDSHPPCS");
		if (uldAvailableShipmentPieces == 0) {
			offloadShipmentVO.setReassignmentStatus("COMPLETED");
		} else if (uldOffloadShipmentPieces > uldAvailableShipmentPieces) {
			offloadShipmentVO.setReassignmentStatus("INPROGRESS");
		} else {
			offloadShipmentVO.setReassignmentStatus((String) null);
		}
	}

	private void setSccCode(ResultSet rs, OffloadULDDetailsVO offloadULDDetailsVO) throws SQLException {
		String sccCode = rs.getString("SCCCOD");
		if (offloadULDDetailsVO.getScc() != null) {
			this.setForMultipleSccCodes(sccCode, offloadULDDetailsVO);
		} else {
			offloadULDDetailsVO.setScc(sccCode);
		}
	}

	private void setFlightTimes(ResultSet rs, OffloadULDDetailsVO offloadULDDetailsVO) throws SQLException {
		if (rs.getTimestamp("ATD") != null) {
			if (rs.getString(LEGORG) != null) {
				offloadULDDetailsVO.setAtd(new com.ibsplc.icargo.framework.util.time.LocalDate(rs.getString(LEGORG),
						Location.ARP, rs.getTimestamp("ATD")));
			} else {
				offloadULDDetailsVO.setAtd(new com.ibsplc.icargo.framework.util.time.LocalDate("***", Location.NONE,
						rs.getTimestamp("ATA")));
			}
		}
		if (rs.getTimestamp("ETD") != null) {
			if (rs.getString(LEGORG) != null) {
				offloadULDDetailsVO.setEtd(new com.ibsplc.icargo.framework.util.time.LocalDate(rs.getString(LEGORG),
						Location.ARP, rs.getTimestamp("ETD")));
			} else {
				offloadULDDetailsVO.setEtd(new com.ibsplc.icargo.framework.util.time.LocalDate("***", Location.NONE,
						rs.getTimestamp("ETD")));
			}
		}
		if (rs.getTimestamp("STD") != null) {
			if (rs.getString(LEGORG) != null) {
				offloadULDDetailsVO.setStd(new com.ibsplc.icargo.framework.util.time.LocalDate(rs.getString(LEGORG),
						Location.ARP, rs.getTimestamp("STD")));
			} else {
				offloadULDDetailsVO.setStd(new com.ibsplc.icargo.framework.util.time.LocalDate("***", Location.NONE,
						rs.getTimestamp("STD")));
			}
		}
	}

	private void setForMultipleSccCodes(String sccCode, OffloadULDDetailsVO offloadULDDetailsVO) {
		if (sccCode != null && !sccCode.isEmpty()) {
			if (sccCode.length() > 1) {
				String[] var6 = sccCode.split(",");
				int var5 = var6.length;
				for (int var4 = 0; var4 < var5; ++var4) {
					String scc = var6[var4];
					if (!offloadULDDetailsVO.getScc().contains(scc)) {
						offloadULDDetailsVO.setScc(offloadULDDetailsVO.getScc() + "," + scc);
					}
				}
			} else if (sccCode.length() == 1) {
				offloadULDDetailsVO.setScc(offloadULDDetailsVO.getScc() + "," + sccCode);
			}
		}
	}
}
