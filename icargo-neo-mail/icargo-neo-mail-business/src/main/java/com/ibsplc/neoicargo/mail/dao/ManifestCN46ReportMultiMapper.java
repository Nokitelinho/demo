

package com.ibsplc.neoicargo.mail.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



public class ManifestCN46ReportMultiMapper implements MultiMapper<ConsignmentDocumentVO> {
	private static final Log LOGGER = LogFactory.getLogger("mail.operations");
	private static final String  MANIFEST_CN46_REPORT ="ManifestCN46ReportMultiMapper";
	private static final String MALCTGCOD = "MALCTGCOD";
	private static final String ORGEXGOFC = "ORGEXGOFC";
	private static final String POACOD = "POACOD";
	private static final String DSTEXGOFC = "DSTEXGOFC";
	private static final String ARPCOD = "ARPCOD";
	Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
	LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);


	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "map");
		List<ConsignmentDocumentVO> consignmentDocumentVOs = null;
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		Collection<RoutingInConsignmentVO> routingInConsignmentVOs = null;
		Collection<MailInConsignmentVO> mailInConsignmentVOs = null;
		ConsignmentDocumentVO consignmentDocumentVO = null;
		MailInConsignmentVO mailInConsignmentVO = null;
		RoutingInConsignmentVO routingInConsignmentVO = null;
		String mailCurrentKey = null;
		String mailPreviousKey = null;
		StringBuilder mailKey = null;
		Collection<Integer> routingSerialNumbers = null;
		while (rs.next()) {
			if (consignmentDocumentVOs == null) {
				consignmentDocumentVOs = new ArrayList<>();
			}
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString(MALCTGCOD)).append(
					rs.getString(ORGEXGOFC)).append(rs.getString(POACOD)).toString();
			LOGGER.log(Log.FINE, "CurrentKey : ", currentKey);
			LOGGER.log(Log.FINE, "PreviousKey : ", previousKey);
			if (!currentKey.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				collectConsignmentDetails(consignmentDocumentVO, rs);
				mailInConsignmentVOs = new ArrayList<>();
				routingInConsignmentVOs = new ArrayList<>();
				consignmentDocumentVO
						.setMailInConsignmentcollVOs(mailInConsignmentVOs);
				consignmentDocumentVO
						.setRoutingInConsignmentVOs(routingInConsignmentVOs);
				consignmentDocumentVOs.add(consignmentDocumentVO);
				previousKey = currentKey;
			}
			/* Collecting mail details */
			mailKey = new StringBuilder();
			mailKey.append(rs.getString("DSN")).append(
					rs.getString(ORGEXGOFC))
					.append(rs.getString(DSTEXGOFC)).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString(MALCTGCOD)).append(rs.getInt("YER"))
					.append(rs.getLong("MALSEQNUM"));
			mailCurrentKey = mailKey.toString();
			if (!mailCurrentKey.equals(mailPreviousKey)) {
				mailInConsignmentVO = new MailInConsignmentVO();
				mailInConsignmentVO.setCompanyCode(consignmentDocumentVO
						.getCompanyCode());
				mailInConsignmentVO.setConsignmentNumber(consignmentDocumentVO
						.getConsignmentNumber());
				mailInConsignmentVO
						.setPaCode(consignmentDocumentVO.getPaCode());
				mailInConsignmentVO
						.setConsignmentSequenceNumber(consignmentDocumentVO
								.getConsignmentSequenceNumber());
				collectMailDetails(mailInConsignmentVO, rs);
				mailInConsignmentVOs.add(mailInConsignmentVO);
				mailPreviousKey = mailCurrentKey;
			}
			/* Collecting Routing Details */
			if (rs.getInt("RTGSERNUM") > 0) {
				routingInConsignmentVO = new RoutingInConsignmentVO();
				routingInConsignmentVO.setCompanyCode(consignmentDocumentVO
						.getCompanyCode());
				routingInConsignmentVO
						.setConsignmentNumber(consignmentDocumentVO
								.getConsignmentNumber());
				routingInConsignmentVO.setPaCode(consignmentDocumentVO
						.getPaCode());
				routingInConsignmentVO
						.setConsignmentSequenceNumber(consignmentDocumentVO
								.getConsignmentSequenceNumber());
				collectRoutingDetails(routingInConsignmentVO, rs);
				if (routingSerialNumbers == null) {
					routingSerialNumbers = new ArrayList<>();
				}
				if (!routingSerialNumbers.contains(Integer
						.valueOf(routingInConsignmentVO
								.getRoutingSerialNumber()))) {
					routingSerialNumbers.add(Integer
							.valueOf(routingInConsignmentVO
									.getRoutingSerialNumber()));
					routingInConsignmentVOs.add(routingInConsignmentVO);
				}
			}
		}
		LOGGER.exiting("MANIFEST_CN46_REPORT", "map");

		return consignmentDocumentVOs;
	}

	/**
	 * @param consignmentDocumentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectConsignmentDetails(
			ConsignmentDocumentVO consignmentDocumentVO, ResultSet rs)
			throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT,
				"collectConsignmentDetails");
		consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
		if (rs.getDate("CSGDAT") != null) {
			consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(rs.getString(ARPCOD),  rs.getDate("CSGDAT")));

		}
		consignmentDocumentVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		consignmentDocumentVO.setConsignmentSequenceNumber(rs
				.getInt("CSGSEQNUM"));
		consignmentDocumentVO.setOperation(rs.getString("OPRTYP"));
		consignmentDocumentVO.setPaCode(rs.getString(POACOD));
		consignmentDocumentVO.setRemarks(rs.getString("RMK"));
		consignmentDocumentVO.setStatedBags(rs.getInt("STDBAG"));
		consignmentDocumentVO.setStatedWeight((quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(rs.getDouble("STDWGT")))));

		consignmentDocumentVO.setType(rs.getString("CSGTYP"));
		consignmentDocumentVO.setAirportCode(rs.getString(ARPCOD));
		if(rs.getString(MALCTGCOD).equals("A")) {
			consignmentDocumentVO.setSubType("CN38");	
		}
		if(rs.getString(MALCTGCOD).equals("B")) {
			consignmentDocumentVO.setSubType("CN41");	
		}
		
		if(rs.getString("OPRORG") != null){
		consignmentDocumentVO.setOperatorOrigin(rs.getString("OPRORG"));
		}
		else{
			consignmentDocumentVO.setOperatorOrigin(rs.getString(POACOD));
		}
		consignmentDocumentVO.setOperatorDestination(rs.getString("OPRDST"));
		consignmentDocumentVO.setOoeDescription(rs.getString("ORGEXGOFCDES"));
		consignmentDocumentVO.setDoeDescription(rs.getString(DSTEXGOFC));
		consignmentDocumentVO.setTransportationMeans(rs.getString("TRPMNS"));
		consignmentDocumentVO.setConsignmentPriority(rs.getString("CSGPRI"));
		consignmentDocumentVO.setFlightDetails(rs.getString("FLTDTL"));
		consignmentDocumentVO.setFlightRoute(rs.getString("FLTRUT"));
		if(consignmentDocumentVO.getFlightRoute()==null||consignmentDocumentVO.getFlightRoute().isEmpty()) {
			consignmentDocumentVO.setFlightRoute(rs.getString("POLARPNAM")+"-"+rs.getString("POUARPNAM"));
		}
		if(consignmentDocumentVO.getFlightDetails()==null||consignmentDocumentVO.getFlightDetails().isEmpty()) {
			consignmentDocumentVO.setFlightDetails(rs.getString("FLTOWN")+"-"+ rs.getString("FLTNUM"));
		}
		if (rs.getDate("FSTFLTDEPDAT") != null) {
			consignmentDocumentVO.setFirstFlightDepartureDate(localDateUtil.getLocalDate(rs
					.getString(ARPCOD), rs.getTimestamp("FSTFLTDEPDAT")));

		}
		consignmentDocumentVO.setAirlineCode(rs.getString("ARLCOD"));
		if(consignmentDocumentVO.getAirlineCode()==null || consignmentDocumentVO.getAirlineCode().isEmpty()) {
			consignmentDocumentVO.setAirlineCode(rs.getString("FLTOWN"));
		}
	
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			consignmentDocumentVO.setLastUpdateTime(localDateUtil.getLocalDate(null,rs
					.getTimestamp("LSTUPDTIM")));

		}
		consignmentDocumentVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));
		LOGGER.exiting(MANIFEST_CN46_REPORT,
				"collectConsignmentDetails");
	}

	/**
	 * @param mailInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectMailDetails(MailInConsignmentVO mailInConsignmentVO,
			ResultSet rs) throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "collectMailDetails");
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		mailInConsignmentVO.setDsn(rs.getString("DSN"));
		mailInConsignmentVO.setOriginExchangeOffice(rs.getString(ORGEXGOFC));
		mailInConsignmentVO.setDestinationExchangeOffice(rs
				.getString(DSTEXGOFC));
		//Added to include the MailClass
		mailInConsignmentVO.setMailClass(rs.getString("MALCLS"));
		mailInConsignmentVO.setYear(rs.getInt("YER"));
		mailInConsignmentVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
		mailInConsignmentVO.setMailId(rs.getString("MALIDR"));
		//Added to include  the DSN PK
		mailInConsignmentVO.setMailCategoryCode(rs.getString(MALCTGCOD));
		mailInConsignmentVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailInConsignmentVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailInConsignmentVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailInConsignmentVO.setRegisteredOrInsuredIndicator(rs
				.getString("REGIND"));
		mailInConsignmentVO.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0),BigDecimal.valueOf(rs.getDouble("WGT")),UnitConstants.WEIGHT_UNIT_KILOGRAM));
		mailInConsignmentVO.setUldNumber(rs.getString("ULDNUM"));
		mailInConsignmentVO.setDeclaredValue(rs.getDouble("DCLVAL"));
		mailInConsignmentVO.setCurrencyCode(rs.getString("CURCOD"));
		mailInConsignmentVO.setStatedBags(rs.getInt("BAGCNT"));
		LOGGER.exiting(MANIFEST_CN46_REPORT, "collectMailDetails");
	}

	/**
	 * @param routingInConsignmentVO
	 * @param rs
	 * @throws SQLException
	 */
	private void collectRoutingDetails(
			RoutingInConsignmentVO routingInConsignmentVO, ResultSet rs)
			throws SQLException {
		LOGGER.entering(MANIFEST_CN46_REPORT, "collectRoutingDetails");
		routingInConsignmentVO.setRoutingSerialNumber(rs.getInt("RTGSERNUM"));
		routingInConsignmentVO.setOnwardCarrierCode(rs.getString("FLTCARCOD"));
		routingInConsignmentVO.setOnwardFlightNumber(rs.getString("FLTNUM"));
		if (rs.getDate("FLTDAT") != null) {
			routingInConsignmentVO.setOnwardFlightDate(localDateUtil.getLocalDate(rs
					.getString("POL"),rs.getDate("FLTDAT")));
		}
		routingInConsignmentVO.setOnwardCarrierId(rs.getInt("FLTCARIDR"));
		routingInConsignmentVO.setPou(rs.getString("POU"));
		routingInConsignmentVO.setPol(rs.getString("POL"));
		LOGGER.exiting(MANIFEST_CN46_REPORT, "collectRoutingDetails");
	}
	

}
