package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundMailbagMapper implements Mapper<MailbagVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @author a-1936 This method is used to set the values from the resultset
	 *         into Mapper
	 * @param rs
	 * @return
	 * @throws SQLException
	 */

	public MailbagVO map(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "Entering the MailBag Mapper");
		MailbagVO mailbagVO = new MailbagVO();
		String airport = rs.getString("SCNPRT");
		mailbagVO.setCompanyCode(rs.getString("CMPCOD"));
		mailbagVO.setMailbagId(rs.getString("MALIDR"));
		mailbagVO.setMailSequenceNumber(rs.getLong("MALSEQNUM")); /*added by A-8149 for ICRD-248207*/
		mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
		mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
		mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		//Added by A-4809 for CR ICMN-2337 Mail Manifest Starts
		mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
		mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
		mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		//Added by A-4809 for CR ICMN-2337 Mail Manifest Ends
		// Added to include the DSN PK
		mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setUldNumber(rs.getString("CONNUM"));
		mailbagVO.setYear(rs.getInt("YER"));
		if (rs.getDate("SCNDAT") != null) {
			mailbagVO.setScannedDate(new LocalDate(airport, Location.ARP, rs.getTimestamp("SCNDAT")));
		}
		String mailStatus = rs.getString("MALSTA");
		mailbagVO.setLatestStatus(mailStatus);
		mailbagVO.setScannedPort(rs.getString("SCNPRT"));
		mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
		mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
		mailbagVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		if (rs.getString("FLTNUM")!=null && !"0".equals(rs.getString("FLTNUM")) && !"-1".equals(rs.getString("FLTNUM"))) {
			if (rs.getDate("FLTDAT") != null) {
				mailbagVO.setFlightDate(new LocalDate(airport, Location.ARP, rs
						.getDate("FLTDAT")));
			}
		}
		mailbagVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		mailbagVO.setCarrierId(rs.getInt("FLTCARIDR"));
		if(!"RTN".equals(mailStatus)){
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
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),rs.getDouble("DSPWGT"),rs.getString("DSPWGTUNT"));
		mailbagVO.setWeight(wgt);//added by a-8353 
		mailbagVO.setDisplayUnit(rs.getString("DSPWGTUNT"));
		mailbagVO.setFinalDestination(rs.getString("POU"));
		mailbagVO.setDamageFlag(rs.getString("DMGFLG"));
		mailbagVO.setTransferFlag(rs.getString("TRAFLG"));
		mailbagVO.setPaBuiltFlag(rs.getString("POAFLG"));
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		/*
		 * Added By Karthick to include the Last Update Time and the Last Update
		 * User as a Part of Optimistic Concurrency Changes
		 *
		 *
		 */
		if (rs.getTimestamp("LSTUPDTIM") != null) {
			mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, rs.getTimestamp("LSTUPDTIM")));
		}
		mailbagVO.setLastUpdateUser(rs.getString("LSTUPDUSR"));

		mailbagVO.setFromCarrier(rs.getString("FRMCARCOD"));
		if(rs.getString("ARRSTA") != null){
			mailbagVO.setArrivedFlag(rs.getString("ARRSTA"));
		}
		else{
			if("ARR".equals(mailStatus)){
				mailbagVO.setArrivedFlag("Y");
			}
		}
		mailbagVO.setDeliveredFlag(rs.getString("DLVSTA"));

		if(rs.getString("INVULDNUM")!=null){
			if((rs.getString("INVULDNUM").equals(rs.getString("INVCONNUM"))) &&
					!(rs.getString("INVULDNUM").startsWith(MailConstantsVO.CONST_BULK)) ){
				mailbagVO.setInventoryContainerType(MailConstantsVO.ULD_TYPE);
			}else {
				mailbagVO.setInventoryContainerType(MailConstantsVO.BULK_TYPE);
			}
		}
		//done so as that transfer will work in mailbag enquiry when searching with status as ALL
		if(rs.getString("INVCONNUM")!=null){
			mailbagVO.setInventoryContainer(rs.getString("INVCONNUM"));
		}
		else{
			mailbagVO.setInventoryContainer(rs.getString("CONNUM"));
			mailbagVO.setInventoryContainerType(rs.getString("CONTYP"));
		}

		 /*
		  * Added By RENO K ABRAHAM for Mail Allocation
		  * UBR number,Currency Code,Mail Rate is taken from MTKDSNULDSEG & MTKDSNCONSEG
		  * CAPBKGMST is joined with these tables for lastUpdateTime of booking.
		  * CAPBKGFLTDTL is joined with CAPBKGMST for lastUpdateTime of Flight booking.
		  */
		 mailbagVO.setUbrNumber(rs.getString("UBRNUM"));
		 Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
		 Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
		 LocalDate bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true);

		 if(bookingUpdateTime != null &&
				 bookingFlightDetailUpdateTime != null) {
			 if(bookingUpdateTime.after(bookingFlightDetailUpdateTime)) {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingUpdateTime);
			 }else {
				 bookingLastUpdateTime = new LocalDate(LocalDate.NO_STATION, Location.NONE,
						 bookingFlightDetailUpdateTime);
			 }
			 if(bookingLastUpdateTime!=null) {
				 mailbagVO.setBookingLastUpdateTime(bookingLastUpdateTime);
				 mailbagVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
			 }
		 }
		 //Added as part of ICRD-214795 starts
			if (rs.getTimestamp("REQDLVTIM") != null) {
				mailbagVO.setReqDeliveryTime(new LocalDate(
						LocalDate.NO_STATION, Location.NONE, rs
								.getTimestamp("REQDLVTIM")));
			}
		//Added as part of ICRD-214795 ends
		//Added as part of ICRD-211205 starts
		mailbagVO.setDocumentNumber(rs.getString("MSTDOCNUM"));	
		mailbagVO.setDocumentOwnerIdr(rs.getInt("DOCOWRIDR"));	
		mailbagVO.setShipmentPrefix(rs.getString("SHPPFX"));	
		mailbagVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		mailbagVO.setSequenceNumber(rs.getInt("SEQNUM"));	
		//Added as part of ICRD-211205 ends
		mailbagVO.setOrigin(rs.getString("ORGCOD"));	
		mailbagVO.setDestination(rs.getString("DSTCOD"));	
		mailbagVO.setMailOrigin(rs.getString("ORGCOD"));	
		mailbagVO.setMailDestination(rs.getString("DSTCOD"));
		mailbagVO.setMailCompanyCode(rs.getString("MALCMPCOD"));
		if(rs.getDouble("VOL")!= 0) {
			mailbagVO.setVol(rs.getDouble("VOL"));//added by A-8353  
		//mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));//added by A-7371
		}
		if(rs.getString("VOLUNT")!=null){
			mailbagVO.setVolUnit(rs.getString("VOLUNT"));//added by A-8353
		}
		if(rs.getString("MRASTA")!=null){
			mailbagVO.setMraStatus(rs.getString("MRASTA"));//added by A-8353
		}
		mailbagVO.setMailRemarks(rs.getString("MALRMK"));
		 log.log(Log.FINEST, "THE MAILBAG VO IS FOUND TO BE ", mailbagVO);
		 mailbagVO.setCarditPresent(rs.getString("CRDPRT"));
		 if(rs.getString("FRMCARCOD")!=null){ 
		 mailbagVO.setTransferFromCarrier(rs.getString("FRMCARCOD"));//added by A-8353 for ICRD-346979
		 }
		 if(rs.getTimestamp("TRPSRVENDTIM") != null){
			 mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
						rs.getTimestamp("TRPSRVENDTIM")));
		 }
		 if(rs.getString("MALSRVLVL")!=null){
				mailbagVO.setMailServiceLevel(rs.getString("MALSRVLVL"));
			}
		 mailbagVO.setAcceptancePostalContainerNumber(rs.getString("ACPPOACONNUM"));
		 mailbagVO.setAcceptanceAirportCode(rs.getString("ACPARPCOD"));
		 mailbagVO.setBellyCartId(rs.getString("BLYCRTIDR"));
		 mailbagVO.setRoutingAvlFlag(rs.getString("RTGAVL"));
		return mailbagVO;

	}

}
