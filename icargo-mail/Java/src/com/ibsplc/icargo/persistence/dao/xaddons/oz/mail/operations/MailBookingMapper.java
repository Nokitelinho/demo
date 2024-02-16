/**
 * 	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookingMapper.java
 */
package com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.xaddons.oz.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.xaddons.oz.mail.operations.MailBookingMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	24-Aug-2017	:	Draft
 */
public class MailBookingMapper implements MultiMapper<MailBookingDetailVO>   {
	
	public List<MailBookingDetailVO> map(ResultSet rs) throws SQLException {
		
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();
		HashMap<String,MailBookingDetailVO> mailBookingMap = new HashMap<String,MailBookingDetailVO>();
		String firstFlightDetails="";
		String newFlight="";
		String key =null;
		while(rs.next()){
		key=new StringBuilder().append(rs.getString("SHPPFX")).append(",")
					.append(rs.getString("MSTDOCNUM")).toString();
			
		if(mailBookingMap.containsKey(key))
		{
			MailBookingDetailVO mailBookingNewVO=null;
			mailBookingNewVO=mailBookingMap.get(key);
	        //String flightNum=mailBookingNewVO.getBookingFlightNumber();
			String carrierCode=mailBookingNewVO.getBookingCarrierCode();
	        String firstDate=mailBookingNewVO.getBookingFlightDate().toDisplayFormat("dd-MMM-yyyy").toString();
	        String secondDate=new LocalDate(rs.getString("STNCOD"),Location.STN,rs.getTimestamp("FLTDAT")).toDisplayFormat("dd-MMM-yyyy").toString();
	        String nextFlightNum=rs.getString("FLTNUM");
	        if(carrierCode!=null && carrierCode.trim().length()>0){
	        	if(mailBookingNewVO.getBookingFlightDate()!=null&&DateUtilities.isGreaterThan(firstDate, secondDate, "dd-MMM-yyyy")){
	        		mailBookingNewVO.setBookingFlightDate(new LocalDate(rs.getString("STNCOD"),Location.STN,rs.getTimestamp("FLTDAT")));
	        		mailBookingNewVO.setBookingFlightNumber(rs.getString("FLTNUM"));
	        		mailBookingNewVO.setBookingCarrierCode(rs.getString("FLTCARCOD"));
	        		mailBookingNewVO.setBookingFlightCarrierid(rs.getInt("FLTCARIDR"));
	        		mailBookingNewVO.setBookingFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
	        		
	        	}
	        	// String newFlight=new StringBuilder().append(flightNum).append(" ").append(firstDate).append(",").append(rs.getString("FLTCARCOD")).append(rs.getString("FLTNUM")).append(" ").append(secondDate).toString();
	        	if(nextFlightNum!=null && nextFlightNum.trim().length()>0){//added by A-7371 as part of ICRD-249374
	        		newFlight=new StringBuilder().append(mailBookingNewVO.getSelectedFlightNumber()).append(",").append(nextFlightNum).append(" ").append(secondDate).append(" ").append(rs.getString("FLTCARCOD")).append(" ").append(rs.getInt("FLTCARIDR")).append(" ").append(rs.getInt("FLTSEQNUM")).toString();
	        	}else{
			     newFlight=new StringBuilder().append(mailBookingNewVO.getSelectedFlightNumber()).append(",").append(" ").append(secondDate).append(" ").append(rs.getString("FLTCARCOD")).append(" ").append(rs.getInt("FLTCARIDR")).append(" ").append(rs.getInt("FLTSEQNUM")).toString();

	        	}
	        	mailBookingNewVO.setSelectedFlightNumber(newFlight);

	        }
			
		}
		else
		{
			
			
			MailBookingDetailVO  mailBookingFlightDetailVO = new MailBookingDetailVO();
			mailBookingFlightDetailVO.setCompanyCode(rs.getString("CMPCOD"));
			mailBookingFlightDetailVO.setShipmentPrefix(rs.getString("SHPPFX"));
			mailBookingFlightDetailVO.setOwnerId(rs.getInt("DOCOWRIDR"));
			mailBookingFlightDetailVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailBookingFlightDetailVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
			mailBookingFlightDetailVO.setSequenceNumber(rs.getInt("SEQNUM"));//added by a-7779 for icrd-231589
			mailBookingFlightDetailVO.setAwbOrgin(rs.getString("ORGCOD"));
			mailBookingFlightDetailVO.setAwbDestination(rs.getString("DSTCOD"));
			mailBookingFlightDetailVO.setBookingCarrierCode(rs.getString("FLTCARCOD"));
			mailBookingFlightDetailVO.setBookingFlightCarrierid(rs.getInt("FLTCARIDR"));
			mailBookingFlightDetailVO.setBookingFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			mailBookingFlightDetailVO.setBookingFlightNumber(rs.getString("FLTNUM"));
			//mailBookingFlightDetailVO.setSelectedFlightNumber(mailBookingFlightDetailVO.getBookingFlightNumber());
			if(rs.getDate("FLTDAT")!=null){
				mailBookingFlightDetailVO.setBookingFlightDate(new LocalDate(rs.getString("STNCOD"),Location.STN,rs.getTimestamp("FLTDAT")));	
				mailBookingFlightDetailVO.setSelectedFlightDate(mailBookingFlightDetailVO.getBookingFlightDate());
			}
			mailBookingFlightDetailVO.setAgentCode(rs.getString("AGTCOD"));
			mailBookingFlightDetailVO.setMailScc(rs.getString("SCCCOD"));
			if(rs.getDate("SHPDAT")!=null){
				mailBookingFlightDetailVO.setShipmentDate(new LocalDate(rs.getString("STNCOD"),Location.STN,rs.getTimestamp("SHPDAT")));	
			}
			mailBookingFlightDetailVO.setBookedPieces(rs.getInt("TOTNUMPCS"));
			mailBookingFlightDetailVO.setBookedWeight(rs.getDouble("TOTWGT"));
			mailBookingFlightDetailVO.setBookedVolume(rs.getDouble("TOTVOL"));
			mailBookingFlightDetailVO.setBookingStatus(rs.getString("BKGSTA"));
			mailBookingFlightDetailVO.setShipmentStatus(rs.getString("SHPSTA"));
			mailBookingFlightDetailVO.setBookingStation(rs.getString("STNCOD"));
			if(rs.getDate("BKGDAT")!=null){
				mailBookingFlightDetailVO.setBookingDate(new LocalDate(rs.getString("STNCOD"),Location.STN,rs.getTimestamp("BKGDAT")));	
			}
			mailBookingFlightDetailVO.setRemarks(rs.getString("SHPRMK"));
			mailBookingFlightDetailVO.setStatedPieces(rs.getInt("STDPCS"));
			//String newFlightNum=new StringBuilder().append(mailBookingFlightDetailVO.getBookingFlightNumber()).append(" ").append(mailBookingFlightDetailVO.getBookingFlightDate().toDisplayFormat("dd-MMM-yyyy")).toString();
			if(mailBookingFlightDetailVO.getBookingFlightNumber()!=null && mailBookingFlightDetailVO.getBookingFlightNumber().trim().length()>0){
			 firstFlightDetails=new StringBuilder().append(mailBookingFlightDetailVO.getBookingFlightNumber()).append(" ").append(mailBookingFlightDetailVO.getBookingFlightDate().toDisplayFormat("dd-MMM-yyyy")).append(" ").append(rs.getString("FLTCARCOD")).append(" ").append(rs.getInt("FLTCARIDR")).append(" ").append(rs.getInt("FLTSEQNUM")).toString();
			}else{
				 firstFlightDetails=new StringBuilder().append(" ").append(mailBookingFlightDetailVO.getBookingFlightDate().toDisplayFormat("dd-MMM-yyyy")).append(" ").append(rs.getString("FLTCARCOD")).append(" ").append(rs.getInt("FLTCARIDR")).append(" ").append(rs.getInt("FLTSEQNUM")).toString();
			}
			
			
			mailBookingFlightDetailVO.setSelectedFlightNumber(firstFlightDetails);
			mailBookingDetailVOs.add(mailBookingFlightDetailVO);
			mailBookingMap.put(key, mailBookingFlightDetailVO);
			}
			}
		return (List<MailBookingDetailVO>) mailBookingDetailVOs;
		}
}
