package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailManifestVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** 
 * @author A-1739
 */
@Slf4j
public class MailbagManifestMultiMapper implements MultiMapper<MailManifestVO> {
	/** 
	* Jan 17, 2007, A-1739
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	*/
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("MailbagManifestMultiMapper" + " : " + "map" + " Entering");
		List<MailManifestVO> mailManifests = new ArrayList<MailManifestVO>();
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifests.add(mailManifestVO);
		List<ContainerDetailsVO> containerDetails = new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;
		int segSerialNum = 0;
		String containerNum = null;
		String mailbagId = null;
		String mailClass = null;
		int totalbags = 0;
		double totalWeight = 0;
		int uldbags = 0;
		double uldWeight = 0;
		while (rs.next()) {
			segSerialNum = rs.getInt("SEGSERNUM");
			containerNum = rs.getString("ULDNUM");
			currContainerKey = new StringBuilder().append(segSerialNum).append(containerNum).toString();
			if (!currContainerKey.equals(prevContainerKey)) {
				prevContainerKey = currContainerKey;
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				containerDetailsVO.setContainerNumber(containerNum);
				mailbagVOs = new ArrayList<MailbagVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				populateContainerDetails(rs, containerDetailsVO);
				containerDetails.add(containerDetailsVO);
				mailbagVO = null;
				uldbags = 0;
				uldWeight = 0;
			}
			mailbagId = rs.getString("MALIDR");
			mailClass = rs.getString("MALCLS");
			if (mailbagId != null) {
				currMailbagKey = new StringBuilder().append(currContainerKey).append(mailbagId).toString();
				if (!currMailbagKey.equals(prevMailbagKey)) {
					prevMailbagKey = currMailbagKey;
					mailbagVO = new MailbagVO();
					mailbagVO.setMailbagId(mailbagId);
					mailbagVO.setMailClass(mailClass);
					populateMailbagDetails(rs, mailbagVO);
					mailbagVOs.add(mailbagVO);
					totalbags += 1;
					if (mailbagVO.getWeight() != null) {
						totalWeight += mailbagVO.getWeight().getRoundedDisplayValue().doubleValue();
					}
					uldbags += 1;
					if (mailbagVO.getWeight() != null) {
						uldWeight += mailbagVO.getWeight().getRoundedDisplayValue().doubleValue();
					}

					containerDetailsVO.setTotalBags(uldbags);
					//TODO: Neo to remove unit hardcoding
					containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(uldWeight), BigDecimal.valueOf(0.0), "K"));

				}
			}
		}
		mailManifestVO.setTotalbags(totalbags);
		mailManifestVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalWeight), BigDecimal.valueOf(0.0), "K"));
		log.debug("MailbagManifestMultiMapper" + " : " + "map" + " Exiting");
		return mailManifests;
	}

	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		Quantity wgt = quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(rs.getDouble("MALWGT")), BigDecimal.valueOf(0.0), "K");
		mailbagVO.setWeight(wgt);
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("DSTCOD"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
	}

	
	private void populateContainerDetails(ResultSet rs, ContainerDetailsVO containerDetailsVO) throws SQLException {
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
	}
}
