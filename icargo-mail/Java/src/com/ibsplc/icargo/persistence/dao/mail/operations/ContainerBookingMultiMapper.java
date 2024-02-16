/*
 * ContainerBookingMultiMapper.java Created on OCT 05, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.BookingTimeVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-3227
 *
 */
public class ContainerBookingMultiMapper implements MultiMapper<ContainerVO>{
	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<ContainerVO> map(ResultSet rs) throws SQLException {
		ContainerVO containerVO=null;
		String key = null;
		HashMap<String,ContainerVO> containerSet = new HashMap<String,ContainerVO>();
		BookingTimeVO bookingTimeVO=null;
		Collection<BookingTimeVO> bookingTimeVOs=null;
		LocalDate bookingLastUpdateTime;
		List<ContainerVO> containerVOs =  new ArrayList<ContainerVO>();
		while(rs.next()){
			key = new StringBuilder()
								.append(rs.getString("CMPCOD")).append("-")
								.append(rs.getString("CONNUM")).append("-")
								.append(rs.getInt("FLTCARIDR")).append("-")
								.append(rs.getString("FLTNUM")).append("-")
								.append(rs.getInt("FLTSEQNUM")).append("-")
								.append(rs.getInt("LEGSERNUM")).append("-")
								.append(rs.getInt("SEGSERNUM")).toString(); 			

			if(!containerSet.containsKey(key)){				
				containerVO = new ContainerVO();
				bookingTimeVOs = new ArrayList<BookingTimeVO>();	
				containerVO.setCompanyCode(rs.getString("CMPCOD"));
				containerVO.setContainerNumber(rs.getString("CONNUM"));
				containerVO.setCarrierId(rs.getInt("FLTCARIDR"));
				containerVO.setFlightNumber(rs.getString("FLTNUM"));
				containerVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				containerVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
				containerVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				if (rs.getTimestamp("BKGLSTUPDTIM") != null &&
						rs.getTimestamp("BKGFLTUPDTIM") != null) {
					Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
					Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
					bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true); 

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
							bookingTimeVO =  new BookingTimeVO();	
							bookingTimeVO.setUbrNumber(rs.getString("UBRNUM"));						
							bookingTimeVO.setBookingLastUpdateTime(bookingLastUpdateTime);
							bookingTimeVO.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
							bookingTimeVOs.add(bookingTimeVO);
						}
					}
				}
				containerVO.setBookingTimeVOs(bookingTimeVOs);
				containerSet.put(key, containerVO);
			}else{
				containerVO = containerSet.get(key); 
				bookingTimeVOs = containerVO.getBookingTimeVOs();
				if(bookingTimeVOs!=null && bookingTimeVOs.size()>0) {
					for(BookingTimeVO bookingTimVo : bookingTimeVOs) {
						if(!bookingTimVo.getUbrNumber().equals(rs.getString("UBRNUM"))) {
							if (rs.getTimestamp("BKGLSTUPDTIM") != null && 
									rs.getTimestamp("BKGFLTUPDTIM") != null) {
								Timestamp bookingUpdateTime = rs.getTimestamp("BKGLSTUPDTIM");
								Timestamp bookingFlightDetailUpdateTime = rs.getTimestamp("BKGFLTUPDTIM");
								bookingLastUpdateTime=new LocalDate(LocalDate.NO_STATION, Location.NONE,true); 

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
										bookingTimVo =  new BookingTimeVO();	
										bookingTimVo.setUbrNumber(rs.getString("UBRNUM"));						
										bookingTimVo.setBookingLastUpdateTime(bookingLastUpdateTime);
										bookingTimVo.setBookingFlightDetailLastUpdTime(bookingLastUpdateTime);
										bookingTimeVOs.add(bookingTimVo);
									}
								}
								break;
							}						
						}
					}					
				}
			}				
		}
		for(ContainerVO contVO : containerSet.values()) {
			containerVOs.add(contVO);
		}
		return containerVOs;
	}
}
