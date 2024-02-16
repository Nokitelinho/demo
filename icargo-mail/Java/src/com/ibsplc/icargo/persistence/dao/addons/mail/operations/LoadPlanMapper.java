package com.ibsplc.icargo.persistence.dao.addons.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class LoadPlanMapper implements MultiMapper<MailBookingDetailVO> {
	private static final String PLNFLT = "PLNFLT";
	private static final String FLTCARIDR = "FLTCARIDR";
	private static final String FLTSEQNUM = "FLTSEQNUM";
	private static final String ORGCOD = "ORGCOD";
	private static final String DSTCOD = "DSTCOD";
	private static final String CMPCOD = "CMPCOD";

	@Override
	public List<MailBookingDetailVO> map(ResultSet rs) throws SQLException {
		Collection<MailBookingDetailVO> loadPlanDetailVOs = new ArrayList<>();
		HashMap<String, MailBookingDetailVO> mailBookingMap = new HashMap<>();
		String key = null;
		MailBookingDetailVO bookedFlight = null;
		Collection<MailBookingDetailVO> bookedFlights = null;
		
		while(rs.next()){
			key = new StringBuilder().append(rs.getString("AWBNUM")).toString();

			if (mailBookingMap.containsKey(key)) {
				bookedFlight = new MailBookingDetailVO();
				bookedFlight.setPlannedFlight(rs.getString(PLNFLT));
				bookedFlight.setBookingCarrierCode(rs.getString(CMPCOD));
				bookedFlight.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
				bookedFlight.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
				bookedFlight.setOrigin(rs.getString(ORGCOD));
				bookedFlight.setDestination(rs.getString(DSTCOD));
				if (bookedFlights != null && !bookedFlights.isEmpty()) {
					bookedFlights.add(bookedFlight);
				}
				
			} else {
				bookedFlights = new ArrayList<>();
				bookedFlight = new MailBookingDetailVO();

				bookedFlight.setPlannedFlight(rs.getString(PLNFLT));
				bookedFlight.setBookingCarrierCode(rs.getString(CMPCOD));
				bookedFlight.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
				bookedFlight.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
				bookedFlight.setOrigin(rs.getString(ORGCOD));
				bookedFlight.setDestination(rs.getString(DSTCOD));

				bookedFlights.add(bookedFlight);
				MailBookingDetailVO mailBookingFlightDetailVO = new MailBookingDetailVO();

				mailBookingFlightDetailVO.setBookedFlights(bookedFlights);
				mailBookingFlightDetailVO.setSplitBooking(false);

				populateLoadPlanBookingDetailVO(mailBookingFlightDetailVO, rs);
				loadPlanDetailVOs.add(mailBookingFlightDetailVO);
				mailBookingMap.put(key, mailBookingFlightDetailVO);
			}
		}
		return (List<MailBookingDetailVO>) loadPlanDetailVOs;
	}
		
		private MailBookingDetailVO populateLoadPlanBookingDetailVO(MailBookingDetailVO mailBookingFlightDetailVO, ResultSet rs) throws SQLException {
			mailBookingFlightDetailVO.setAwbNumber(rs.getString("AWBNUM"));
			mailBookingFlightDetailVO.setStandardPieces(rs.getString("STDPCS"));
			mailBookingFlightDetailVO.setStandardWeight(rs.getString("STDWGT"));
			mailBookingFlightDetailVO.setVolume(rs.getString("GRSVOL"));
			mailBookingFlightDetailVO.setOrigin(rs.getString(ORGCOD));
			mailBookingFlightDetailVO.setDestination(rs.getString(DSTCOD));
			mailBookingFlightDetailVO.setPol(rs.getString("SEGORG"));
			mailBookingFlightDetailVO.setPou(rs.getString("SEGDST"));
			mailBookingFlightDetailVO.setScc(rs.getString("SCCCOD"));
			mailBookingFlightDetailVO.setShipmentStatus(rs.getString("SHPSTA"));
			mailBookingFlightDetailVO.setPlannedWeight(rs.getString("PLNWGT"));
			mailBookingFlightDetailVO.setPlannedPieces(rs.getString("PLNPCS"));
			mailBookingFlightDetailVO.setPlannedFlight(rs.getString(PLNFLT));
			if(rs.getDate("PLNFLTDAT")!=null){
				mailBookingFlightDetailVO.setFlightDate(new LocalDate(rs.getString("SEGORG"),Location.STN,rs.getTimestamp("PLNFLTDAT")));	
			}
			mailBookingFlightDetailVO.setShipmentDescription(rs.getString("SHPDES"));
			mailBookingFlightDetailVO.setBookingFlightSequenceNumber(rs.getInt(FLTSEQNUM));
			mailBookingFlightDetailVO.setBookingFlightCarrierid(rs.getInt(FLTCARIDR));
			mailBookingFlightDetailVO.setCompanyCode(rs.getString(CMPCOD));
			mailBookingFlightDetailVO.setDuplicateNumber(rs.getInt("DUPNUM"));
			mailBookingFlightDetailVO.setOwnerId(rs.getInt("DOCOWNIDR"));
			mailBookingFlightDetailVO.setSequenceNumber(rs.getInt("SEQNUM"));
			
			return mailBookingFlightDetailVO;
		}
		
		}

