/**
 * 	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.bs.mail.operations.MailBookingMapper.java
 */
package com.ibsplc.icargo.persistence.dao.xaddons.bs.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.bs.mail.operations.MailBookingMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	24-Aug-2017	:	Draft
 */
public class MailBookedFlightDetailsMapper implements MultiMapper<MailBookingDetailVO>   {
	
	public List<MailBookingDetailVO> map(ResultSet rs) throws SQLException {
		
		Collection<MailBookingDetailVO> mailBookingFlightDetailVOs = null;
		mailBookingFlightDetailVOs = new ArrayList<MailBookingDetailVO>();
		MailBookingDetailVO  mailBookingFlightDetailVO = null;
		while(rs.next()){
			
			//mailBookingFlightDetailVOs = new ArrayList<MailBookingDetailVO>();
			mailBookingFlightDetailVO = new MailBookingDetailVO();
			mailBookingFlightDetailVO.setCompanyCode(rs.getString("CMPCOD"));
			mailBookingFlightDetailVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailBookingFlightDetailVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
			mailBookingFlightDetailVO.setOrigin(rs.getString("FLTORG"));
			mailBookingFlightDetailVO.setDestination(rs.getString("FLTDST"));
			mailBookingFlightDetailVO.setBookingCarrierCode(rs.getString("FLTCARCOD"));
			mailBookingFlightDetailVO.setBookingFlightNumber(rs.getString("FLTNUM"));
			if(rs.getDate("FLTDAT")!=null){
				mailBookingFlightDetailVO.setBookingFlightDate(new LocalDate(rs.getString("FLTORG"),Location.STN,rs.getTimestamp("FLTDAT")));	
			}
			
			mailBookingFlightDetailVO.setBookedPieces(rs.getInt("NUMPCS"));
			mailBookingFlightDetailVO.setBookedWeight(rs.getDouble("WGT"));
			
			//a-8061 added fro ICRD-229572 begin

			mailBookingFlightDetailVO.setBookingFlightCarrierid(rs.getInt("FLTCARIDR"));
			mailBookingFlightDetailVO.setBookingFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			mailBookingFlightDetailVO.setSegementserialNumber(rs.getInt("SEGSERNUM"));
			
			//a-8061 added fro ICRD-229572 end
			
			
			
			mailBookingFlightDetailVOs.add(mailBookingFlightDetailVO);
			}
		return (List<MailBookingDetailVO>) mailBookingFlightDetailVOs;
		}
}
