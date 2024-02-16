package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DSNVO;
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
import java.util.Objects;

/** 
 * @author A-1739
 */
@Slf4j
public class MailAWBManifestMultiMapper implements MultiMapper<MailManifestVO> {
	private static final String MASTERDOCUMENTNUMBER="MSTDOCNUM";
	private static final String SHIPMENTPREFIX="SHPPFX";
	private static final String CSGDOCNUM="CSGDOCNUM";
	/** 
	* Jan 17, 2007, A-1739
	* @param rs
	* @return
	* @throws SQLException
	* @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	*/
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug("MailAWBManifestMultiMapper" + " : " + "map" + " Entering");
		MailManifestVO mailManifestVO = new MailManifestVO();
		List<MailManifestVO> mailManifests = new ArrayList<MailManifestVO>();
		mailManifests.add(mailManifestVO);
		Collection<ContainerDetailsVO> containerDetails = new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		DSNVO dsnVO = null;
		Collection<DSNVO> despatches = null;
		String containerNum = null;
		int segSerialNum = 0;
		int docOwnerId = 0;
		String masterDocNum = null;
		String shipmentPrefix = null;
		int duplicateNum = 0;
		int sequenceNum = 0;
		String docOwnerCode = null;
		String shipmentCode = null;
		String shipmentDescription = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		String currDespatchKey = null;
		String prevDespatchKey = null;
		int uldTotalbags = 0;
		double uldTotalWeight = 0;
		int totalbags = 0;
		double totalWeight = 0;
		String origin = null;
		String destination = null;
		Collection<MailbagVO> mailbags=null;
		MailbagVO mailbagVO=null;
		while (rs.next()) {
			containerNum = rs.getString("ULDNUM");
			segSerialNum = rs.getInt("SEGSERNUM");
			currContainerKey = new StringBuilder().append(containerNum).append(segSerialNum).toString();
			if (!currContainerKey.equals(prevContainerKey)) {
				uldTotalbags = 0;
				uldTotalWeight = 0;
				dsnVO = null;
				prevContainerKey = currContainerKey;
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setContainerNumber(containerNum);
				containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				populateContainerDetailsVO(containerDetailsVO, rs);
				containerDetails.add(containerDetailsVO);
				despatches = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(despatches);
				mailbags=new ArrayList<>();
				containerDetailsVO.setMailDetails(mailbags);
				containerDetailsVO.setContour(rs.getString("ULDCTR"));
				prevDespatchKey = null;
			} else {
				containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + rs.getInt("CONBAGCNT"));
				//TODO: Hardcoding to be removed for unit
				try {
					containerDetailsVO.setTotalWeight(containerDetailsVO.getTotalWeight()
							.add(quantities.getQuantity(Quantities.MAIL_WGT,
									BigDecimal.valueOf(0.0), BigDecimal.valueOf(rs.getDouble("CONBAGWGT")),
									"K")));
				} finally {
				}
			}
			docOwnerId = rs.getInt("DOCOWRIDR");
			masterDocNum = rs.getString("MSTDOCNUM");
			shipmentPrefix = rs.getString("SHPPFX");
			duplicateNum = rs.getInt("DUPNUM");
			sequenceNum = rs.getInt("SEQNUM");
			docOwnerCode = rs.getString("DOCOWRCOD");
			shipmentCode = rs.getString("SCCCOD");
			shipmentDescription = rs.getString("SHPDES");
			origin = rs.getString("ORG");
			destination = rs.getString("DST");
			currDespatchKey = new StringBuilder().append(currContainerKey).append(docOwnerId).append(masterDocNum)
					.append(duplicateNum).append(sequenceNum).append(docOwnerCode).append(origin).append(destination)
					.toString();
			log.debug("" + "\n\n currDespatchKey  " + " " + currDespatchKey);
			log.debug("" + "\n\n prevDespatchKey  " + " " + prevDespatchKey);
			if (!currDespatchKey.equals(prevDespatchKey)) {
				prevDespatchKey = currDespatchKey;
				dsnVO = new DSNVO();
				if ((docOwnerId != 0 && masterDocNum != null && masterDocNum.length() > 0)) {
					dsnVO.setDocumentOwnerIdentifier(docOwnerId);
					dsnVO.setMasterDocumentNumber(masterDocNum);
					dsnVO.setDuplicateNumber(duplicateNum);
					dsnVO.setSequenceNumber(sequenceNum);
					dsnVO.setDocumentOwnerCode(shipmentPrefix);
					dsnVO.setShipmentCode(shipmentCode);
					dsnVO.setShipmentDescription(shipmentDescription);
				}
				populateDSNDetails(rs, dsnVO);
				despatches.add(dsnVO);
				totalbags += dsnVO.getBags();
				totalWeight += dsnVO.getWeight().getValue().doubleValue();
			}
			else {
				dsnVO.setBags(dsnVO.getBags() + rs.getInt("ACPBAG"));
				try {
					dsnVO.setWeight(dsnVO.getWeight().add(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),
							BigDecimal.valueOf(rs.getDouble("ACPWGT")),"K")));
				}finally {
				}
			}
			mailbagVO=new MailbagVO();
			populateMailbagDetails(rs,mailbagVO);
			mailbags.add(mailbagVO);
			try {
				if (Objects.nonNull(containerDetailsVO.getNetWeight())) {
			containerDetailsVO.setNetWeight(containerDetailsVO.getNetWeight().add(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("NETWGT")),"K")));
			}else{
					containerDetailsVO.setNetWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("NETWGT")),"K"));
				}
			}
				finally{

			}
			//containerDetailsVO.setTotalBags(uldTotalbags);
			//containerDetailsVO.setTotalWeight(uldTotalWeight);
		}

		mailManifestVO.setTotalbags(totalbags);
		//TODO: Hardcoding to be removed for unit
		mailManifestVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(totalWeight), BigDecimal.valueOf(totalWeight), "K"));
		log.debug("MailAWBManifestMultiMapper" + " : " + "mailManifests--" + mailManifests + " Exiting");
		return mailManifests;
	}

	/** 
	* TODO Purpose Jan 17, 2007, A-1739
	* @param rs
	* @param dsnVO
	* @throws SQLException
	*/
	private void populateDSNDetails(ResultSet rs, DSNVO dsnVO) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		dsnVO.setOrigin(rs.getString("ORG"));
		dsnVO.setDestination(rs.getString("DST"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		//TODO: Hardcoding to be removed for unit
		dsnVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(rs.getDouble("ACPWGT")), "K"));
		dsnVO.setStatedBags(rs.getInt("STDBAG"));
		dsnVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(rs.getDouble("STDWGT")),"K"));
		dsnVO.setShipmentCount(rs.getInt("SHPPCS"));
		dsnVO.setShipmentWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(rs.getDouble("SHPWGT")), "K"));
		dsnVO.setShipmentVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("SHPVOL"))));
		dsnVO.setAcceptedVolume(
				quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("ACPVOL"))));
	}
	/** 
	* TODO Purpose Jan 17, 2007, A-1739
	* @param containerDetailsVO
	* @param rs
	*/
	private void populateContainerDetailsVO(ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setTotalBags(rs.getInt("CONBAGCNT"));
		//TODO: Hardcoding to be removed for unit
		containerDetailsVO.setTotalWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
				BigDecimal.valueOf(rs.getDouble("CONBAGWGT")), "K"));
		if ("U".equals(rs.getString("CONTYP"))) {
			containerDetailsVO.setTareWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),
					BigDecimal.valueOf(rs.getDouble("TARWGT")), "K"));
		}
	}
	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO)throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setOrigin(rs.getString("ORG"));
		mailbagVO.setDestination(rs.getString("DST"));
		mailbagVO.setWeight(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("MALWGT")),"K"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		StringBuilder awbNumber=new StringBuilder();
		if(Objects.nonNull(rs.getString(MASTERDOCUMENTNUMBER)) && Objects.nonNull(rs.getString(SHIPMENTPREFIX))) {
			awbNumber.append(rs.getString(SHIPMENTPREFIX)).append("-").append(rs.getString(MASTERDOCUMENTNUMBER));
		}
		mailbagVO.setAwbNumber(awbNumber.toString());
		mailbagVO.setConsignmentNumber(!CSGDOCNUM.isEmpty()?CSGDOCNUM:"");
		mailbagVO.setVolume(quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("MALVOL")),"K"));
	}
}

