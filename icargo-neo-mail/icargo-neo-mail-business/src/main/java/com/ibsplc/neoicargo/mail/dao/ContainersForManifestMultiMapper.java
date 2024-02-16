package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/** 
 * @author a-1936
 */
@Slf4j
public class ContainersForManifestMultiMapper implements MultiMapper<ContainerDetailsVO> {
	private static final String BULKCONNUM = "BULKCONNUM";

	/** 
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	*/
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		List<ContainerDetailsVO> containerDetails = null;
		Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		String currContainerKey = null;
		String prevContainerKey = null;
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> dsnVOs = null;
		String dsnKey = null;
		String currDSNKey = null;
		String prevDSNKey = null;
		containerDetails = new ArrayList<ContainerDetailsVO>();
		String route = null;
		Collection<String> onwardRoutes = new ArrayList<String>();
		String routeKey = null;
		while (rs.next()) {
			currContainerKey = new StringBuilder().append(rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					.append(rs.getString("FLTNUM")).append(rs.getLong("FLTSEQNUM")).append(rs.getInt("SEGSERNUM"))
					.append(rs.getString(BULKCONNUM)).toString();
			route = rs.getString("ONWRTGSTR");
			if (route != null) {
				routeKey = currContainerKey + route;
			}
			if (MailConstantsVO.FLAG_NO.equals(rs.getString("ACPFLG"))
					&& MailConstantsVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
				continue;
			}
			if (rs.getString(BULKCONNUM) != null && rs.getString(BULKCONNUM).startsWith(MailConstantsVO.CONST_BULK)
					&& rs.getInt("ACPBAG") == 0) {
				continue;
			}
			if (!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				if (routeKey != null && !(onwardRoutes.contains(routeKey))) {
					containerDetailsVO.setRoute(rs.getString("ONWRTGSTR"));
					onwardRoutes.add(routeKey);
				}
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				containerDetails.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
			} else {
				if (routeKey != null && !(onwardRoutes.contains(routeKey))) {
					if (containerDetailsVO.getRoute() != null) {
						containerDetailsVO.setRoute(containerDetailsVO.getRoute().concat(",").concat(route));
						onwardRoutes.add(routeKey);
					}
				}
			}
			dsnKey = new StringBuilder().append(rs.getString("DSN")).append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(rs.getString("MALSUBCLS"))
					.append(rs.getString("MALCTGCOD")).append(rs.getInt("YER")).toString();
			currDSNKey = new StringBuilder().append(currContainerKey).append(dsnKey).toString();
			if (rs.getString("DSN") != null && rs.getInt("ACPBAG") > 0) {
				if (dsnKey != null && dsnKey.trim().length() > 0 && !(csgDocForDSN.containsKey(dsnKey))) {
					ArrayList<String> csgDetails = new ArrayList<String>();
					if ((rs.getString("CSGDOCNUM") != null && rs.getString("CSGDOCNUM").length() > 0)
							&& (rs.getString("POACOD") != null && rs.getString("POACOD").length() > 0)) {
						csgDetails.add(rs.getString("CSGDOCNUM"));
						csgDetails.add(rs.getString("POACOD"));
						csgDocForDSN.put(dsnKey, csgDetails);
					}
				}
				if (!currDSNKey.equals(prevDSNKey)) {
					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs);
					dsnVO.setContainerType(containerDetailsVO.getContainerType());
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
				} else {
					if (rs.getString(BULKCONNUM) != null) {
						dsnVO.getDsnContainers().add(rs.getString(BULKCONNUM));
					}
					dsnVO.setBags(dsnVO.getBags() + rs.getInt("ACPBAG"));
					try {
						dsnVO.setWeight(dsnVO.getWeight().add(quantities
								.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT")))));
					} finally {
					}
					if (rs.getString("CSGDOCNUM") != null && rs.getString("CSGDOCNUM").length() > 0) {
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if (rs.getString("POACOD") != null && rs.getString("POACOD").length() > 0) {
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
				}
			}
		}
		autoSuggestConsignmentForDSN(containerDetails, csgDocForDSN);
		return containerDetails;
	}

	/** 
	* @param containerDetails
	* @param csgDocForDSN
	*/
	private void autoSuggestConsignmentForDSN(List<ContainerDetailsVO> containerDetails,
			Map<String, ArrayList<String>> csgDocForDSN) {
		if (containerDetails != null) {
			String dsnKey = null;
			for (ContainerDetailsVO cntDetails : containerDetails) {
				for (DSNVO dsnVO : cntDetails.getDsnVOs()) {
					if (dsnVO.getCsgDocNum() == null
							|| (dsnVO.getCsgDocNum() != null && dsnVO.getCsgDocNum().length() == 0)) {
						dsnKey = new StringBuilder().append(dsnVO.getDsn()).append(dsnVO.getOriginExchangeOffice())
								.append(dsnVO.getDestinationExchangeOffice()).append(dsnVO.getMailSubclass())
								.append(dsnVO.getMailCategoryCode()).append(String.valueOf(dsnVO.getYear())).toString();
						if (csgDocForDSN != null && csgDocForDSN.size() > 0 && csgDocForDSN.containsKey(dsnKey)) {
							ArrayList<String> csgDetails = csgDocForDSN.get(dsnKey);
							if (csgDetails != null) {
								dsnVO.setCsgDocNum(csgDetails.get(0));
								dsnVO.setPaCode(csgDetails.get(1));
							}
						}
					}
				}
			}
		}
	}

	/** 
	* A-1739
	* @param dsnVO
	* @param rs
	* @throws SQLException
	*/
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String categoryCode = null;
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setOrigin(rs.getString("ORGCOD"));
		dsnVO.setDestination(rs.getString("DSTCOD"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		categoryCode = rs.getString("MALCTGCOD");
		dsnVO.setMailCategoryCode(categoryCode);
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPWGT"))));
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		dsnVO.setCsgSeqNum(rs.getInt("CSGSEQNUM"));
		if (rs.getDate("CSGDAT") != null) {
			dsnVO.setConsignmentDate(localDateUtil.getLocalDate(null, rs.getDate("CSGDAT")));
		}
		if (rs.getDate("ACPDAT") != null) {
			dsnVO.setAcceptedDate(localDateUtil.getLocalDate(null, rs.getDate("ACPDAT")));
		}
		dsnVO.setRemarks(rs.getString("REMARKS"));
		dsnVO.setPaCode(rs.getString("POACOD"));
		if (rs.getTimestamp("DSNULDSEGTIM") != null) {
			dsnVO.setDsnUldSegLastUpdateTime(localDateUtil.getLocalDate(null, rs.getTimestamp("DSNULDSEGTIM")));
		}
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsnContainers(new ArrayList<String>());
		if (rs.getString("CONNUM") != null) {
			dsnVO.getDsnContainers().add(rs.getString("CONNUM"));
			dsnVO.setContainerNumber(rs.getString("CONNUM"));
		}
		if (rs.getString("RTGAVL") != null && "Y".equals(rs.getString("RTGAVL"))) {
			dsnVO.setRoutingAvl(rs.getString("RTGAVL"));
		}
	}

	/** 
	* A-1739
	* @param containerDetailsVO
	* @param rs
	* @throws SQLException
	*/
	private void populateContainerDetails(ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setContainerNumber(rs.getString(BULKCONNUM));
		containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		containerDetailsVO.setTotalWeight(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("BAGWGT"))));
		if (rs.getString(BULKCONNUM) != null && !rs.getString(BULKCONNUM).startsWith(MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		}
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
	}
}
