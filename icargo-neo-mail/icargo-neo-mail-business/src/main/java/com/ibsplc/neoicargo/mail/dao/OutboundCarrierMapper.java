package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
public class OutboundCarrierMapper implements MultiMapper<MailAcceptanceVO> {
	private static final String CLASS_NAME = "OutboundCarrierMapper";

	@Override
	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		MeasureMapper measureMapper = ContextUtil.getInstance().getBean(MeasureMapper.class);
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		log.debug(CLASS_NAME + " : " + "OutboundCarrierMapper" + " Entering");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		List<MailAcceptanceVO> mailacceptanceVOs = new ArrayList<MailAcceptanceVO>();
		ContainerDetailsVO containerDetailsVO = null;
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String currULDNameKey = "";
		String prevULDNameKey = "";
		String currCarrierkey = "";
		String preCarrierkey = "";
		while (rs.next()) {
			currCarrierkey = rs.getString("CMPCOD") + rs.getString("FLTCARCOD") + rs.getString("ASGPRT")
					+ rs.getString("DSTCOD");
			log.debug("" + "The NEW flight parentID is Found to be " + " " + prevULDNameKey);
			MailAcceptanceVO mailAcceptanceVO = null;
			if (!currCarrierkey.equals(preCarrierkey)) {
				prevULDNameKey = "";
				mailAcceptanceVO = new MailAcceptanceVO();
				mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
				mailAcceptanceVO.setCarrierCode(rs.getString("FLTCARCOD"));
				mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
				mailAcceptanceVO.setDestination(rs.getString("DSTCOD"));
				mailAcceptanceVO.setPol(rs.getString("ASGPRT"));
				mailacceptanceVOs.add(mailAcceptanceVO);
				containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
				mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
				preCarrierkey = currCarrierkey;
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
						    if(Objects.nonNull(containerDetailsVO.getMailbagwt())) {
								containerDetailsVO.setMailbagwt(containerDetailsVO.getMailbagwt()
										.add(quantities.getQuantity(Quantities.MAIL_WGT,
												BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),
												BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),"K")));
							} else{
								containerDetailsVO.setMailbagwt(quantities.getQuantity(Quantities.MAIL_WGT,
												BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),
												BigDecimal.valueOf(Double.parseDouble(mailbagWeight[i])),"K"));
							}
							containerDetailsVOs.add(containerDetailsVO);
						}
					} else {
						containerDetailsVO = new ContainerDetailsVO();
						containerDetailsVO.setContainerGroup(rs.getString("CONNAM"));
						containerDetailsVO.setContainercount(Integer.parseInt(rs.getString("CONCNT")));
						containerDetailsVO.setMailbagcount(Integer.parseInt(rs.getString("MALCNT")));
						if(Objects.nonNull(containerDetailsVO.getMailbagwt())) {
							containerDetailsVO.setMailbagwt(containerDetailsVO.getMailbagwt()
									.add(quantities.getQuantity(Quantities.MAIL_WGT,
											BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),
											BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),
											"K"
											)));
						} else{
							containerDetailsVO.setMailbagwt(quantities.getQuantity(Quantities.MAIL_WGT,
											BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),
											BigDecimal.valueOf(Double.parseDouble(rs.getString("WGT"))),
											"K"
									));
						}
						containerDetailsVOs.add(containerDetailsVO);
					}
				}
			}
			log.debug(CLASS_NAME + " : " + "OutboundCarrierMapper" + " Exiting");
		}
		return mailacceptanceVOs;
	}
}
