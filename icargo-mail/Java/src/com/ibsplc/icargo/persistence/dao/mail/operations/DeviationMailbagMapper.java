/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author 215166
 *
 */
public class DeviationMailbagMapper implements MultiMapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	List<MailbagVO> mailbagVOs = new ArrayList<>();
	public List<MailbagVO> map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the MailBag DeviationMailbagMapper");
		MailbagVO mailbagVO = null;
		Map<String,MailbagVO> mailBagMap= new HashMap<>();
		StringBuilder key ;
		while (rs.next()) {
			key = new StringBuilder(rs.getString("MALIDR")).append(rs.getLong("MALSEQNUM"));
			if(mailBagMap.containsKey(key.toString())) {
				mailbagVO = mailBagMap.get(key.toString());
				
			}
			mailbagVO = new MailbagVO();
			String airport = rs.getString("SCNPRT");
			mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
			mailbagVO.setMailbagId(rs.getString("MALIDR"));
			mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM")); /* added by A-8149 for ICRD-248207 */
			mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
			mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
			mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
			mailbagVO.setMailClass(rs.getString("MALCLS"));
			mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
			mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
			mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
			mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
			mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
			mailbagVO.setUldNumber(rs.getString("CONNUM"));
			mailbagVO.setYear(rs.getInt("YER"));
			
			if (rs.getDate("CSGDAT") != null ) {
				mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("CSGDAT")));
			}
			String mailStatus = rs.getString("MALSTA");
			mailbagVO.setLatestStatus(mailStatus);
			mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
			mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
			mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			if (rs.getString("FLTNUM") != null && !"0".equals(rs.getString("FLTNUM"))
					&& !"-1".equals(rs.getString("FLTNUM")) && airport != null) {
				if (rs.getDate("FLTDAT") != null) {
					mailbagVO.setFlightDate(new LocalDate(airport, Location.ARP, rs.getDate("FLTDAT")));
				}
			}
			mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
			if (!"RTN".equals(mailStatus)) {
				mailbagVO.setContainerNumber(rs.getString("CONNUM"));
			}
			mailbagVO.setScannedUser(rs.getString("SCNUSR"));
			mailbagVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			mailbagVO.setOperationalStatus(rs.getString("OPRSTA"));
			mailbagVO.setContainerType(rs.getString("CONTYP"));
			mailbagVO.setPou(rs.getString("POU"));
			mailbagVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
			mailbagVO.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
			mailbagVO.setPaCode(rs.getString("POACOD"));
			Measure wgt = new Measure(UnitConstants.MAIL_WGT, rs.getDouble("WGT"));
			mailbagVO.setWeight(wgt);// added by a-8353
			mailbagVO.setFinalDestination(rs.getString("POU"));
			mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
			mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
			mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));
			Measure actualWeight = new Measure(UnitConstants.MAIL_WGT, rs.getDouble("ACTWGT"));
			mailbagVO.setActualWeight(actualWeight);
			if (rs.getTimestamp("LSTUPDTIM") != null) {
				mailbagVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
			}
			mailbagVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));

			mailbagVO.setFromCarrier(rs.getString("FRMCARCOD"));
			if (rs.getString("ARRSTA") != null) {
				mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
			} else {
				if ("ARR".equals(mailStatus)) {
					mailbagVO.setArrivedFlag("Y");
				}
			}
			mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));

			if (rs.getString("INVULDNUM") != null) {
				if ((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM")))
						&& !(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK))) {
					mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
				} else {
					mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
				}
			}
			// done so as that transfer will work in mailbag enquiry when searching with
			// status as ALL
			if (rs.getString("INVCONNUM") != null) {
				mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
			} else {
				mailbagVO.setInventoryContainer(rs.getString("CONNUM"));
				mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
			}

			mailbagVO.setUbrNumber(rs.getString("UBRNUM"));
			Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
			Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
			LocalDate bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);

			if (bookingUpdateTime != null && bookingFlightDetailUpdateTime != null) {
				if (bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
					bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE, bookingUpdateTime);
				} else {
					bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
							bookingFlightDetailUpdateTime);
				}
				if (bookingLastUpdateTime != null) {
					mailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
					mailbagVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
				}
			}
			// Added as part of ICRD-214795 starts
			if (rs.getTimestamp("REQDLVTIM") != null) {
				mailbagVO.setReqDeliveryTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("REQDLVTIM")));
			}

			mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
			mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));
			mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));
			// Added as part of ICRD-211205 ends
			mailbagVO.setOrigin(rs.getString("ORGCOD"));
			mailbagVO.setDestination(rs.getString("DSTCOD"));
			mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
			mailbagVO.setMailDestination(rs.getString("DSTCOD"));

			// Added by A-8464 for ICRD-243079
			mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			mailbagVO.setOnTimeDelivery(rs.getString("ONNTIMDLVFLG"));
			mailbagVO.setPol(rs.getString("POL"));
			mailbagVO.setVol(rs.getDouble("VOL"));
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));
			if("Y".equals(rs.getString("ACP"))){
				mailbagVO.setAccepted("Y");		
			}else{
				mailbagVO.setAccepted("N");
			}
			if(rs.getString("ERRSCNDAT")!=null && rs.getString("ERRSCNDAT").trim().length()>0) {
				if(!"Y".equals(mailbagVO.getAccepted())) {
					mailbagVO.setScannedDate(new LocalDate(
							rs.getString("ERRSCNPRT"), Location.ARP, rs
									.getTimestamp("ERRSCNDAT")));
					mailbagVO.setScannedPort(rs.getString("ERRSCNPRT"));
				}
				//E means Error
				if("N".equals(mailbagVO.getAccepted())) {
				  mailbagVO.setAccepted("E");
				}
			}	
			if(mailbagVO.getErrorCode()==null) {
			  mailbagVO.setErrorCode("");
			}
			StringBuilder error = new StringBuilder(mailbagVO.getErrorCode());
			if ("OBMISSED".equals(rs.getString("ERRORCOD"))) {
				error.append("OBMISSED");
			}
			if ("IBUNPLANED".equals(rs.getString("ERRORCOD"))) {
				error.append("IBUNPLANED");
			}
			mailbagVO.setErrorCode(error.toString());
			log.log(Log.FINEST, "THE MAILBAG VO IS FOUND TO BE ", mailbagVO);
			mailbagVOs.add(mailbagVO);
		}	
		return mailbagVOs;
	}

}
