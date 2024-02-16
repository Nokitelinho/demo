package com.ibsplc.icargo.persistence.dao.mail.operations;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListFlightDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	25-Sep-2018		:	Draft
 */
public class ListFlightDetailsMultiMapper implements
			MultiMapper<MailArrivalVO>{
	
	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String CLASS_NAME = "ListFlightDetailsMultiMapper";

	public ArrayList<MailArrivalVO> map(ResultSet rs) throws SQLException {
		
		log.entering(CLASS_NAME, "map");
		 ArrayList<MailArrivalVO> mailArrivalVOs=new ArrayList<MailArrivalVO>();
		 while (rs.next()) {     
			 MailArrivalVO mailArrivalVO=new MailArrivalVO();
			 //String manifestInfo=null;
			 String recievedInfo=null;
			 if(null!=rs.getString("CMPCOD")){
				 mailArrivalVO.setCompanyCode(rs.getString("CMPCOD"));
			 }
			 if(null!=rs.getString("FLTNUM")){
				 mailArrivalVO.setFlightNumber(rs.getString("FLTNUM"));
			 }
			 if(0!=rs.getInt("FLTSEQNUM")){
				 mailArrivalVO.setFlightSequenceNumber((rs.getInt("FLTSEQNUM")));
			 }
			 if(0!=rs.getInt("FLTCARIDR")){
				 mailArrivalVO.setCarrierId((rs.getInt("FLTCARIDR")));
			 }
			 if(null!=rs.getString("FLTCARCOD")){
				 mailArrivalVO.setFlightCarrierCode((rs.getString("FLTCARCOD")));
			 }
			 if(null!=rs.getString("LEGDST")){
				 mailArrivalVO.setAirportCode(rs.getString("LEGDST"));  
			 }
			 if(null!=rs.getString("FLTSTA")){
				 mailArrivalVO.setFlightStatus(rs.getString("FLTSTA"));
			 }
			 if(null!=rs.getString("FLTTYP")){
				 mailArrivalVO.setFlightStatus(rs.getString("FLTTYP"));
			 }
			 if(null!=rs.getString("FLTROU")){
				 mailArrivalVO.setRoute(rs.getString("FLTROU"));
			 }
			 if(0!=rs.getInt("LEGSERNUM")){
				 mailArrivalVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			 }
			 if(null!=rs.getString("LEGORG")){
				 mailArrivalVO.setPol(rs.getString("LEGORG"));
			 }
			 if(null!=rs.getDate("STA")){
				 mailArrivalVO.setFlightDate(new LocalDate(NO_STATION,
							NONE, rs.getTimestamp("STA")));
				 mailArrivalVO.setArrivalTimeType(" (S)");
				 mailArrivalVO.setArrivalDate(mailArrivalVO.getFlightDate());
			 }
			 if(null!=rs.getDate("ETA")){
				 mailArrivalVO.setFlightDate(new LocalDate(NO_STATION,
							NONE, rs.getTimestamp("ETA")));
				 mailArrivalVO.setArrivalTimeType(" (E)");
			 }
			 if(null!=rs.getDate("ATA")){
				 mailArrivalVO.setFlightDate(new LocalDate(NO_STATION,
							NONE, rs.getTimestamp("ATA")));
				 mailArrivalVO.setArrivalTimeType(" (A)");
			 }
			 if(null!=rs.getString("ACRTYP")){
				 mailArrivalVO.setAircraftType(rs.getString("ACRTYP"));
			 }
			 if(null!=rs.getString("ARVGTE")){
				 mailArrivalVO.setGateInfo(rs.getString("ARVGTE"));   
			 }
			 if(null!=rs.getString("IMPCLSFLG")){
				 mailArrivalVO.setOperationalStatus(rs.getString("IMPCLSFLG")); 
			 }
			 else{
				 if(rs.getString("TOTACPBAG")!=null && rs.getString("TOTRCVBAG")!=null){
					 if(Integer.parseInt(rs.getString("TOTACPBAG"))+Integer.parseInt(rs.getString("TOTRCVBAG"))>0){
						mailArrivalVO.setOperationalStatus("O");
					 }
				 }
				 else if(rs.getString("ULDCHK")!=null){  
						mailArrivalVO.setOperationalStatus("O"); 
				 }
				 else
					 mailArrivalVO.setOperationalStatus("N");
			 }
			/* if(null!=rs.getString("CONNAM")){
				 manifestInfo= new StringBuffer().append(rs.getString("CONCNT")).append("-")
						 .append(rs.getString("CONNAM")).append("(").append(rs.getString("MALCNT")).append("/")
						 	.append(rs.getString("TOTWGT")).append(")").toString();
				 mailArrivalVO.setManifestInfo(manifestInfo);
			 }*/
			 if(null!=rs.getString("FLTSEQNUM")){
				 recievedInfo= new StringBuffer().append(rs.getString("TOTACPBAG")!=null?rs.getString("TOTACPBAG"):'0').append("/") 
					 .append(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TOTACPWGT")).getRoundedDisplayValue()).append("-")
					 	.append(rs.getString("TOTRCVBAG")!=null?rs.getString("TOTRCVBAG"):'0').append("/")
					 		.append(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TOTRCVWGT")).getRoundedDisplayValue()).toString();
			 mailArrivalVO.setRecievedInfo(recievedInfo);
		 }
			 if(null!=rs.getString("ONLARPPAR")){
				 mailArrivalVO.setOnlineAirportParam(rs.getString("ONLARPPAR"));
			 }
			 mailArrivalVOs.add(mailArrivalVO); 
			 
		 }
		return mailArrivalVOs; 
		
	}
	
}
