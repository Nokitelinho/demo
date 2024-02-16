package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.neoicargo.mail.vo.PreAdviceVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OutboundFlightMapper implements MultiMapper<MailAcceptanceVO> {
	private static final String CLASS_NAME = "OutboundFlightMapper";

	@Override
	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug(CLASS_NAME + " : " + "listMailFlifgts" + " Entering");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		List<MailAcceptanceVO> mailacceptanceVOs = new ArrayList<MailAcceptanceVO>();
		ContainerDetailsVO containerDetailsVO = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String currULDNameKey = "";
		String prevULDNameKey = "";
		String currFlightkey = "";
		String preFlightkey = "";
		while (rs.next()) {
			currFlightkey = rs.getString("CMPCOD") + rs.getInt("FLTCARIDR") + rs.getString("FLTNUM")
					+ rs.getLong("FLTSEQNUM") + rs.getInt("LEGSERNUM");
			log.debug("" + "The NEW flight parentID is Found to be " + " " + prevULDNameKey);
			MailAcceptanceVO mailAcceptanceVO = null;
			if (!currFlightkey.equals(preFlightkey)) {
				prevULDNameKey = "";
				mailAcceptanceVO = new MailAcceptanceVO();
				mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
				mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
				mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));
				mailAcceptanceVO.setPol(rs.getString("LEGORG"));
				mailAcceptanceVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				ZonedDateTime flightDepartureDate = localDateUtil.getLocalDate(rs.getString("FLTORG"),
						rs.getTimestamp("DEPTIM"));
				mailAcceptanceVO.setFlightDepartureDate(flightDepartureDate);
				ZonedDateTime flightDate = localDateUtil.getLocalDate(null, rs.getTimestamp("FLTDAT"));
				mailAcceptanceVO.setFlightDate(flightDate);
				mailAcceptanceVO.setFlightRoute(rs.getString("FLTROU"));
				mailAcceptanceVO.setFlightStatus(rs.getString("FLTSTA"));
				mailAcceptanceVO.setFlightOperationalStatus(rs.getString("EXPCLSFLG"));
				mailAcceptanceVO.setFlightOrigin(rs.getString("FLTORG"));
				mailAcceptanceVO.setFlightDestination(rs.getString("FLTDST"));
				mailAcceptanceVO.setFlightType(rs.getString("FLTTYP"));
				mailAcceptanceVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				mailAcceptanceVO.setAircraftType(rs.getString("ACRTYP"));
				mailAcceptanceVO.setFlightDateDesc(rs.getString("FLTDATPREFIX"));
				mailAcceptanceVO.setDepartureGate(rs.getString("DEPGTE"));
				PreAdviceVO preadvicevo = new PreAdviceVO();
				mailAcceptanceVO.setDCSinfo(rs.getString("DCSSTA"));
				mailAcceptanceVO.setDCSregectionReason(rs.getString("DCSREJRSN"));
				mailAcceptanceVO.setPreadvice(preadvicevo);
				ZonedDateTime std = localDateUtil.getLocalDate(rs.getString("LEGORG"), rs.getTimestamp("STD"));
				mailAcceptanceVO.setStd(std);
				mailacceptanceVOs.add(mailAcceptanceVO);
				containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
				mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
				preFlightkey = currFlightkey;
			}
			if (rs.getString("CONNAM") != null) {
				String containers = rs.getString("CONNAM");
				String[] containerCount = rs.getString("CONCNT").split(",");
				String[] mailCount = rs.getString("MALCNT").split(",");
				String[] mailbagWeight = rs.getString("WGT").split(",");
				if (containers.contains(",")) {
					String[] containerSplit = containers.split(",");
					for (int i = 0; i < containerSplit.length; i++) {
						containerDetailsVO = new ContainerDetailsVO();
						containerDetailsVO.setContainerGroup(containerSplit[i]);
						containerDetailsVO.setContainercount(Integer.parseInt(containerCount[i]));
						containerDetailsVO.setMailbagcount(Integer.parseInt(mailCount[i]));
						String dspwgtunt = rs.getString("DSPWGTUNT");
//						Quantity mailBagWeight = Objects.nonNull(dspwgtunt)?(quantities.getQuantity(Quantities.MAIL_WGT,
//								BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),
//								BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),dspwgtunt)):
//								quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO);
						//TODO: checking weight with 2 par
//						Quantity mailBagWeight = quantities.getQuantity(Quantities.MAIL_WGT,
//								BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),
//								BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),"K");
						Quantity mailBagWeight = quantities.getQuantity(Quantities.MAIL_WGT,
								BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])));
						if (Objects.nonNull(containerDetailsVO.getMailbagwt())) {
							containerDetailsVO.setMailbagwt(containerDetailsVO.getMailbagwt().add(mailBagWeight));
						} else {
							containerDetailsVO.setMailbagwt(mailBagWeight);
						}

						containerDetailsVOs.add(containerDetailsVO);
					}
				} else {
					containerDetailsVO = new ContainerDetailsVO();
					containerDetailsVO.setContainerGroup(rs.getString("CONNAM"));
					containerDetailsVO.setContainercount(Integer.parseInt(rs.getString("CONCNT")));
					containerDetailsVO.setMailbagcount(Integer.parseInt(rs.getString("MALCNT")));
					String dspwgtunt = rs.getString("DSPWGTUNT");
					Quantity mailBagWeight = Objects.nonNull(dspwgtunt)?(quantities.getQuantity(Quantities.MAIL_WGT,
							BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),
							BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),dspwgtunt)):
							quantities.getQuantity(Quantities.MAIL_WGT,BigDecimal.ZERO);
					if (Objects.nonNull(containerDetailsVO.getMailbagwt())) {
						containerDetailsVO.setMailbagwt(containerDetailsVO.getMailbagwt().add(mailBagWeight));
					} else {
						containerDetailsVO.setMailbagwt(mailBagWeight);
					}

					containerDetailsVOs.add(containerDetailsVO);
				}

			}
			log.debug(CLASS_NAME + " : " + "listMailFlifgts" + " Exiting");
		}
		return mailacceptanceVOs;
	}
}
