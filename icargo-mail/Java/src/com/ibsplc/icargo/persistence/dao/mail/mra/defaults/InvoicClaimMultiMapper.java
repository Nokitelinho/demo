/*
 * InvoicClaimMultiMapper.java created on Aug 17, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicLocationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicPackageMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicPriceMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicProductMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.invoicadv.InvoicTransportationMessageVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2518
 * 
 */
public class InvoicClaimMultiMapper implements MultiMapper<InvoicMessageVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "InvoicClaimMultiMapper";

	/* No Payment Record */
	private static final String NPR_CODE = "NPR";

	private static final String NPR_DESCRIPTION = "No Payment Record";

	/* Weight mismatch */
	private static final String WEIGHT_CLAIMCODE = "WXX";

	/*
	 * Rate Value Dispute - Rate mismatch. RVLD and RVHD are subtypes of RVD
	 * claim
	 */
	private static final String RVD_CLAIMCODE = "RVD";

	/* Rate mismatch - Line Haul Dollar Rate */
	private static final String RVLD_CLAIMREASONCODE = "RVLD";

	/* Rate mismatch - Terminal Handling Dollar Rate */
	private static final String RVHD_CLAIMREASONCODE = "RVHD";

	private static final String CONTAINER_TYPE_LOOSE = "LOOSE";

	/**
	 * @param rs
	 * @return List<MailInvoicMasterVO>
	 * @throws SQLException
	 */
	public List<InvoicMessageVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		List<InvoicMessageVO> invoicMessageVos = new ArrayList<InvoicMessageVO>();
		InvoicProductMessageVO productVo = null;
		InvoicMessageVO invoicMessageVo = null;
		InvoicPackageMessageVO packageVo = null;
		InvoicLocationMessageVO locationVo = null;
		InvoicTransportationMessageVO transportationVo = null;
		InvoicPriceMessageVO priceVo = null;
		Map<String, InvoicMessageVO> invoicMessageMap = new HashMap<String, InvoicMessageVO>();
		Collection<InvoicProductMessageVO> productVos = null;
		Collection<InvoicLocationMessageVO> locationVos = null;
		Collection<InvoicPriceMessageVO> priceVos = null;
		Collection<InvoicPackageMessageVO> packageVos = null;
		Collection<InvoicTransportationMessageVO> transportationVos = null;
		String masterKey = null;
		int lineNumber = 0;
		String claimCode = null;
		while (rs.next()) {
			claimCode = rs.getString("CLMCOD");
			if (RVLD_CLAIMREASONCODE.equals(claimCode)
					|| RVHD_CLAIMREASONCODE.equals(claimCode)) {
				claimCode = RVD_CLAIMCODE;
			}
			/*
			 * On a given consignment date, all NPR claims are grouped and will
			 * be sent in a single file. Similarly other claims are also grouped
			 * by consignment date and claim code. So for a particular
			 * consignement date, a maximum of 6 claim files will be generated.
			 */
			masterKey = new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getDate("CSGDAT")).append(claimCode).toString();
			if (!invoicMessageMap.containsKey(masterKey)) {
				invoicMessageVo = new InvoicMessageVO();
				productVos = new ArrayList<InvoicProductMessageVO>();
				locationVos = new ArrayList<InvoicLocationMessageVO>();
				priceVos = new ArrayList<InvoicPriceMessageVO>();
				packageVos = new ArrayList<InvoicPackageMessageVO>();
				transportationVos = new ArrayList<InvoicTransportationMessageVO>();
				lineNumber = 0;
				invoicMessageVo.setCompanyCode(rs.getString("CMPCOD"));
				if (rs.getTimestamp("CSGDAT") != null) {
					invoicMessageVo.setConsignmentCompletionDate(new LocalDate(
							NO_STATION, NONE, rs.getTimestamp("CSGDAT")));
				}
				if (rs.getTimestamp("SCHINVDAT") != null) {
					invoicMessageVo.setScheduleInvoiceDate(new LocalDate(
							NO_STATION, NONE, rs.getTimestamp("SCHINVDAT")));
				}
				invoicMessageVo.setCarrierCode(rs.getString("APHCOD"));
				invoicMessageVo.setCarrierName(rs.getString("ARLNAM"));
				invoicMessageVo.setRecipientID(rs.getString("POACOD"));
				invoicMessageVo.setProductDetails(productVos);
				invoicMessageVo.setLocationDetails(locationVos);
				invoicMessageVo.setPriceDetails(priceVos);
				invoicMessageVo.setPackageDetails(packageVos);
				invoicMessageVo.setTransportationDetails(transportationVos);
				invoicMessageVos.add(invoicMessageVo);
				invoicMessageMap.put(masterKey, invoicMessageVo);
			}
			if (invoicMessageVo != null) {
				/* For LIN Segment */
				++lineNumber;
				// Populating Product details
				productVo = new InvoicProductMessageVO();
				String receptacleId = rs.getString("RCPIDR");
				productVo.setSectorIdentifier(rs.getString("SECIDR"));
				productVo.setLineNumber(lineNumber);
				productVo.setConsignmentDocumentNumber(rs
						.getString("CSGDOCNUM"));
				/*
				 * Populate Mail category code, Mail class code and Mail
				 * subclass code from Receptacle ID
				 */
				productVo.setMailCategoryCode(receptacleId.substring(12, 13));
				productVo.setMailClassCode(receptacleId.substring(13, 14));
				productVo.setMailSubclassCode(receptacleId.substring(13, 15));
				productVo.setClaimReasonCode(rs.getString("CLMCOD"));
				/*
				 * For NPR claim, the description would be 'No Payment Record'.
				 * For other claims no description would be set in the message
				 */
				if (NPR_CODE.equals(rs.getString("CLMCOD"))) {
					productVo.setClaimText(NPR_DESCRIPTION);
				}
				productVo.setReceptacleIdentifier(receptacleId);
				productVos.add(productVo);
				// Populating Location details
				locationVo = new InvoicLocationMessageVO();
				locationVo.setCarrierAssigned(invoicMessageVo.getCarrierCode());
				locationVo.setOriginPort(rs.getString("ORGPRT"));
				locationVo.setDestinationPort(rs.getString("DSTPRT"));
				locationVo
						.setCarrierFinalDestination(rs.getString("CARFINDST"));
				locationVos.add(locationVo);
				// Populating Price details
				priceVo = new InvoicPriceMessageVO();
				/*
				 * For RVLD or RVHD claim types, the claim adjustment code would
				 * be RVD. For other claim types, the claim adjustment code
				 * would be the claim type itself.
				 */
				if ((RVLD_CLAIMREASONCODE.equals(rs.getString("CLMCOD")) || (RVHD_CLAIMREASONCODE
						.equals(rs.getString("CLMCOD"))))) {
					productVo.setClaimAdjustmentCode(RVD_CLAIMCODE);
				} else {
					productVo.setClaimAdjustmentCode(rs.getString("CLMCOD"));
				}
				if (RVLD_CLAIMREASONCODE.equals(rs.getString("CLMCOD"))) {
					priceVo.setLineHaulDollarRate(rs.getDouble("LHLDOLRAT"));
				}
				if (RVHD_CLAIMREASONCODE.equals(rs.getString("CLMCOD"))) {
					priceVo.setTerminalHandlingDollarRate(rs
							.getDouble("THLDOLRAT"));
				}
				priceVos.add(priceVo);
				// Populating Package details
				packageVo = new InvoicPackageMessageVO();
				packageVo.setReceptacleIdentifier(receptacleId);
				if (rs.getString("CNTTYP") != null
						&& !"".equals(rs.getString("CNTTYP").trim())) {
					packageVo.setContainerType(rs.getString("CNTTYP"));
					packageVo.setPackageCount(1);
				} else {
					packageVo.setContainerType(CONTAINER_TYPE_LOOSE);
					packageVo.setPackageCount(1);
				}
				/* For WXX claim, Receptacle weight is picked up */
				if (WEIGHT_CLAIMCODE.equals(rs.getString("CLMCOD"))) {
					packageVo.setContainerWeight(rs.getDouble("WGT"));
				}
				packageVos.add(packageVo);
				// Populating transportaion details
				transportationVo = new InvoicTransportationMessageVO();
				if (rs.getTimestamp("DEPDAT") != null) {
					transportationVo.setDepartureDate(new LocalDate(NO_STATION,
							NONE, rs.getTimestamp("DEPDAT")));
				}
				transportationVos.add(transportationVo);
			}
		}
		log.exiting(CLASS_NAME, "map");
		return invoicMessageVos;
	}
}