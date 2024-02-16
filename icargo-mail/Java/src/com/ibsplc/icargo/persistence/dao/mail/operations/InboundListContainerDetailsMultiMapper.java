package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListContainerDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
public class InboundListContainerDetailsMultiMapper implements 
				MultiMapper<ContainerDetailsVO> {

	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String CLASS_NAME = "InboundListContainerDetailsMultiMapper";
	
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
		
		log.entering(CLASS_NAME, "map");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		while (rs.next()) {    
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();   
			if(null!=rs.getString("CONNUM")){        
			String assignedPort = rs.getString("ASGPRT");   
			containerDetailsVO.setCompanyCode(rs.getString("CMPCOD")); 
			containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
			containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			containerDetailsVO.setContainerNumber(rs.getString("CONNUM")); 
			containerDetailsVO.setContainerGroup(rs.getString("ULDNUM"));
			containerDetailsVO.setLegSerialNumber((rs.getInt("LEGSERNUM")));
			containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			containerDetailsVO.setPou(rs.getString("POU"));
			containerDetailsVO.setPol(rs.getString("ASGPRT"));
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
			containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
			containerDetailsVO.setContainerType(rs.getString("CONTYP"));
			containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
			containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));

			Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
			if(lstUpdateTime != null) {
				containerDetailsVO.setLastUpdateTime(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
						lstUpdateTime));
			}
			Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
			if (uldLastUpdateTime != null) {
				containerDetailsVO
						.setUldLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, uldLastUpdateTime));
			}
			containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
			containerDetailsVO.setPaCode(rs.getString("POACOD"));
			if (rs.getTimestamp("ASGDATUTC") != null) {
				 containerDetailsVO.setAssignedDate(new LocalDate(assignedPort, Location.ARP, rs.getTimestamp("ASGDATUTC")));
			 }
			
			containerDetailsVO.setLocation(rs.getString("LOCCOD"));
			containerDetailsVO.setRemarks(rs.getString("RMK"));
			containerDetailsVO.setAssignedUser(rs.getString("USRCOD"));
			containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));
			containerDetailsVO.setReceivedBags(rs.getInt("RCVBAG"));
			containerDetailsVO.setMailbagcount(rs.getInt("ACPBAG"));
			containerDetailsVO.setIntact(rs.getString("INTFLG"));
			containerDetailsVO.setDeliveredStatus(rs.getString("DLVFLG"));	
			containerDetailsVO.setActWgtSta(rs.getString("ACTWGTSTA"));
			if(0!=rs.getDouble("WGT"))
				containerDetailsVO.setTotalWeight(
						new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
			if(0!=rs.getDouble("RCVWGT"))
				containerDetailsVO.setReceivedWeight(
						new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCVWGT"))); 
			if(0!=rs.getDouble("ACPWGT"))
				containerDetailsVO.setMailbagwt(
						new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));
			String onwardFlight ="";
			if(null!=rs.getString("ONWFLTCARCOD"))
				onwardFlight=new StringBuffer().append(onwardFlight)
							.append(rs.getString("ONWFLTCARCOD")).toString();
			if(null!=rs.getString("ONWFLTNUM"))
				onwardFlight=new StringBuffer().append(onwardFlight)
							.append(rs.getString("ONWFLTNUM")).toString();
			if(null!=rs.getString("ONWFLTDAT"))
				onwardFlight=new StringBuffer().append(onwardFlight).append(" ")
							.append(rs.getString("ONWFLTDAT")).toString();
			if(null!=rs.getString("RTGPOU"))
				onwardFlight=new StringBuffer().append(onwardFlight).append(" to ")
							.append(rs.getString("RTGPOU")).toString();
			if(!"".equals(onwardFlight))
				containerDetailsVO.setOnwardFlights(onwardFlight);
			if(0!=rs.getDouble("ACTULDWGT"))
				containerDetailsVO.setActualWeight(
						new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACTULDWGT")));
			containerDetailsVO.setAssignedPort(rs.getString("ASGPRT"));
			containerDetailsVOsList.add(containerDetailsVO);
			
			//Added by A-8464 for ICRD-328502 begins  
            //Modified by A-7540 for ICRD-350010
			int mailDiffDestCnt=rs.getInt("DESTCOUNT");
			if(mailDiffDestCnt==1){
				if(rs.getString("MALDST").equals(rs.getString("POU"))){
				containerDetailsVO.setContainerPureTransfer("TERMINATING");
			}
				else{
				containerDetailsVO.setContainerPureTransfer("PURE TRANSFER");
				}
			}
			else{
				containerDetailsVO.setContainerPureTransfer("MIXED");
			}
			//Added by A-8464 for ICRD-328502 ends
			//Added by A-8893 for ICRD-337334 begins
            /*Issue mentioned here is handled using in difference count above, and status should not change based on delivered count
			if(containerDetailsVO.getContainerPureTransfer().equals("PURE TRANSFER") || containerDetailsVO.getContainerPureTransfer()=="PURE TRANSFER"){
			int deliveredMailCount=rs.getInt("DLVCNT");

			   if(deliveredMailCount>0 && deliveredMailCount==containerDetailsVO.getTotalBags()){
				containerDetailsVO.setContainerPureTransfer("TERMINATING");
			   }
			   else if(deliveredMailCount>0 && deliveredMailCount<containerDetailsVO.getTotalBags()){
				containerDetailsVO.setContainerPureTransfer("MIXED");
			   }

			 //Added by A-8893 for ICRD-337334 ends
			}*/
            if(rs.getTimestamp("REQDLVTIM")!=null) {
				 containerDetailsVO.setMinReqDelveryTime(new LocalDate(LocalDate.NO_STATION,	Location.NONE, 
							rs.getTimestamp("REQDLVTIM")));
			 }
		  }
		 }
		
		return containerDetailsVOsList;
	}
}
