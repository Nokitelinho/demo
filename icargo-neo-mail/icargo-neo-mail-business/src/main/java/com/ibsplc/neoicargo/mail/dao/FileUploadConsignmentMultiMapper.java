package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/** 
 * @author A-7794
 */
@Slf4j
public class FileUploadConsignmentMultiMapper implements MultiMapper<ConsignmentDocumentVO> {
	Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
	ConsignmentDocumentVO consignmentDocumentVO;
	Collection<MailInConsignmentVO> mailVOs = null;

	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		Quantities quantities = ContextUtil.getInstance().getBean(Quantities.class);
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		int count = 0;
		while (rs.next()) {
			if (consignmentDocumentVOs == null) {
				consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
			}
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(rs.getString("POACOD"))
					.append(rs.getString("REF003")).toString();
			log.debug("" + "CurrentKey : " + " " + currentKey);
			log.debug("" + "PreviousKey : " + " " + previousKey);
			if (!currentKey.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				MailInConsignmentVO mailVO = new MailInConsignmentVO();
				mailVOs = new ArrayList<MailInConsignmentVO>();
				count = 0;
				ZonedDateTime scanDate = null;
				if (rs.getDate("DSPDAT") != null) {
					scanDate = localDateUtil.getLocalDate(null, rs.getDate("DSPDAT"));
				}
				mailVO.setMailId(rs.getString("MALIDR"));
				mailVO.setConsignmentDate(scanDate);
				mailVO.setCompanyCode(rs.getString("CMPCOD"));
				mailVO.setPaCode(rs.getString("POACOD"));
				mailVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				mailVO.setOriginExchangeOffice(rs.getString("ORGNOFCEXGCOD"));
				mailVO.setDestinationExchangeOffice(rs.getString("DSTNOFCEXGCOD"));
				mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
				if (rs.getString("ORGAPRCOD") != null) {
					mailVO.setMailOrigin(rs.getString("ORGAPRCOD"));
				} else {
					mailVO.setMailOrigin(rs.getString("ORGCITYCOD"));
				}
				if (rs.getString("DSTARPCOD") != null) {
					mailVO.setMailDestination(rs.getString("DSTARPCOD"));
				} else {
					mailVO.setMailDestination(rs.getString("DSTCITYCOD"));
				}
				mailVO.setDsn(rs.getString("REF001"));
				if (rs.getString("REF002") != null) {
					String reqDlvryDate = rs.getString("REF002");
					ZonedDateTime excelDate = localDateUtil.getLocalDate(null, false);
					java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
					try {
						Date parseDate = formatter.parse(reqDlvryDate);
						//excelDate = LocalDate.withTime(excelDate, parseDate);
						//TODO: Below code to be verified in Neo
						excelDate = new LocalDate().getLocalDate(null, parseDate);
					} catch (ParseException e) {
						e.getMessage();
					}
					mailVO.setReqDeliveryTime(excelDate);
				}
				mailVOs.add(mailVO);
				ZonedDateTime currentDate = localDateUtil.getLocalDate(null, false);
				consignmentDocumentVO.setCarrierCode(rs.getString("REF003"));
				consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
				consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
				consignmentDocumentVO.setConsignmentDate(currentDate);
				consignmentDocumentVO.setMailInConsignment(mailVOs);
				count++;
				consignmentDocumentVO.setStatedBags(count);
				consignmentDocumentVO
						.setStatedWeight(quantities.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0)));
				consignmentDocumentVO.setConsignmentPriority("N");
				consignmentDocumentVOs.add(consignmentDocumentVO);
				previousKey = currentKey;
			} else {
				for (ConsignmentDocumentVO consVO : consignmentDocumentVOs) {
					if (consVO.equals(consignmentDocumentVO)) {
						MailInConsignmentVO mailVO = new MailInConsignmentVO();
						ZonedDateTime scanDate = null;
						if (rs.getDate("DSPDAT") != null) {
							scanDate = localDateUtil.getLocalDate(null, rs.getDate("DSPDAT"));
						}
						mailVO.setMailId(rs.getString("MALIDR"));
						mailVO.setConsignmentDate(scanDate);
						mailVO.setCompanyCode(rs.getString("CMPCOD"));
						mailVO.setPaCode(rs.getString("POACOD"));
						mailVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
						mailVO.setOriginExchangeOffice(rs.getString("ORGNOFCEXGCOD"));
						mailVO.setDestinationExchangeOffice(rs.getString("DSTNOFCEXGCOD"));
						mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
						if (rs.getString("ORGAPRCOD") != null) {
							mailVO.setMailOrigin(rs.getString("ORGAPRCOD"));
						} else {
							mailVO.setMailOrigin(rs.getString("ORGCITYCOD"));
						}
						if (rs.getString("DSTARPCOD") != null) {
							mailVO.setMailDestination(rs.getString("DSTARPCOD"));
						} else {
							mailVO.setMailDestination(rs.getString("DSTCITYCOD"));
						}
						mailVO.setDsn(rs.getString("REF001"));
						if (rs.getString("REF002") != null) {
							String reqDlvryDate = rs.getString("REF002");
							ZonedDateTime excelDate = localDateUtil.getLocalDate(null, false);
							java.text.DateFormat formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
							Date parseDate;
							try {
								parseDate = formatter.parse(reqDlvryDate);
								//excelDate = LocalDate.withTime(excelDate, parseDate);
								//TODO: Below code to be verified in Neo
								excelDate = new LocalDate().getLocalDate(null, parseDate);
							} catch (ParseException e) {
								e.getMessage();
							}
							mailVO.setReqDeliveryTime(excelDate);
						}
						this.mailVOs.add(mailVO);
						consVO.setMailInConsignment(mailVOs);
						count++;
						consVO.setStatedBags(count);
					}
				}
			}
		}
		return (List<ConsignmentDocumentVO>) consignmentDocumentVOs;
	}
}
