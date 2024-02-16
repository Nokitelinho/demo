package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundFlightMapper implements MultiMapper<MailAcceptanceVO> {
	private Log log = LogFactory.getLogger("MAIL operations");
	private static final String CLASS_NAME = "OutboundFlightMapper";

	@Override
	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "listMailFlifgts");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		List<MailAcceptanceVO> mailacceptanceVOs = new ArrayList<MailAcceptanceVO>();
		ContainerDetailsVO containerDetailsVO = null; 
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String currULDNameKey = "";
		String prevULDNameKey = "";
		String currFlightkey="";
		String preFlightkey="";
        while(rs.next()){	
          currFlightkey = rs.getString("CMPCOD") + rs.getInt("FLTCARIDR") + rs.getString("FLTNUM") + rs.getLong("FLTSEQNUM") + rs.getInt("LEGSERNUM");
          log.log(Log.FINE, "The NEW flight parentID is Found to be ", prevULDNameKey);	
          MailAcceptanceVO mailAcceptanceVO =null;
          if (!currFlightkey.equals(preFlightkey))
          {
        	  prevULDNameKey="";
        	 mailAcceptanceVO = new MailAcceptanceVO();
 			 mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
 			 mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
 	         mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));
 	         //uplift airport to confirm
 	         mailAcceptanceVO.setPol(rs.getString("LEGORG"));
 	         mailAcceptanceVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
			 //modified by A-7815 as part of IASCB-47327
 		     LocalDate flightDepartureDate = new LocalDate(
 					rs.getString("FLTORG"), Location.ARP,rs.getTimestamp("DEPTIM") );
 		     mailAcceptanceVO.setFlightDepartureDate(flightDepartureDate);
 		     LocalDate flightDate = new LocalDate(
 		    		LocalDate.NO_STATION, Location.NONE,rs.getTimestamp("FLTDAT") );
 		     mailAcceptanceVO.setFlightDate(flightDate);
 		     mailAcceptanceVO.setFlightRoute(rs.getString("FLTROU"));
 		     mailAcceptanceVO.setFlightStatus(rs.getString("FLTSTA"));
 		     mailAcceptanceVO.setFlightOperationalStatus(rs.getString("EXPCLSFLG"));
 		     mailAcceptanceVO.setFlightOrigin(rs.getString("FLTORG"));
 		     mailAcceptanceVO.setFlightDestination(rs.getString("FLTDST"));
 		     mailAcceptanceVO.setFlightType(rs.getString("FLTTYP"));
 		     mailAcceptanceVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
 		     mailAcceptanceVO.setAircraftType(rs.getString("ACRTYP"));
 		     mailAcceptanceVO.setFlightDateDesc(rs.getString("FLTDATPREFIX"));
 		     mailAcceptanceVO.setDepartureGate(rs.getString("DEPGTE"));
 		     PreAdviceVO preadvicevo = new PreAdviceVO();
 		   
 		    mailAcceptanceVO.setDCSinfo(rs.getString("DCSSTA"));
 		    mailAcceptanceVO.setDCSregectionReason(rs.getString("DCSREJRSN"));
				
 		     mailAcceptanceVO.setPreadvice(preadvicevo);
 		    LocalDate std = new LocalDate(
					rs.getString("LEGORG"), Location.ARP,rs.getTimestamp("STD") );
 		    mailAcceptanceVO.setStd(std);
 		     mailacceptanceVOs.add(mailAcceptanceVO);
 		     containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
 		     mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
 		     preFlightkey = currFlightkey;
			 
		 }
          if(rs.getString("CONNAM")!=null) {
 	     	  String containers = rs.getString("CONNAM");
        	  String[] containerCount = rs.getString("CONCNT").split(",");
        	  String[] mailCount = rs.getString("MALCNT").split(",");
        	  String[]  mailbagWeight = rs.getString("WGT").split(",");
        	  if(containers.contains(",")) {
        	  String[] containerSplit = containers.split(",");
        	  for(int i=0;i<containerSplit.length;i++){
 			containerDetailsVO = new ContainerDetailsVO();
        		  containerDetailsVO.setContainerGroup(containerSplit[i]);
        		  containerDetailsVO.setContainercount(Integer.parseInt(containerCount[i]));
        		  containerDetailsVO.setMailbagcount(Integer.parseInt(mailCount[i]));
        		  try {
        			  containerDetailsVO.setMailbagwt(Measure.addMeasureValues(containerDetailsVO.getMailbagwt(), new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailbagWeight[i]))));
        		  }catch (UnitException e1) {
        						log.log(Log.SEVERE, "UnitException",e1.getMessage());
        			}
     			  //populateContainerDetails(containerDetailsVO, rs,mailAcceptanceVO);
 			containerDetailsVOs.add(containerDetailsVO);
        	  }
        	  } else {
 			  containerDetailsVO = new ContainerDetailsVO();
 			  containerDetailsVO.setContainerGroup(rs.getString("CONNAM"));
   		      containerDetailsVO.setContainercount(Integer.parseInt(rs.getString("CONCNT")));
   		      containerDetailsVO.setMailbagcount(Integer.parseInt(rs.getString("MALCNT")));
   		      //containerDetailsVO.setReceptacleCount(Integer.parseInt(rs.getString("RCPIDRCNT")));
   		  try {
   			  containerDetailsVO.setMailbagwt(Measure.addMeasureValues(containerDetailsVO.getMailbagwt(), new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT")))));
   			 // containerDetailsVO.setReceptacleWeight(Measure.addMeasureValues(containerDetailsVO.getReceptacleWeight(), new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("RCPWGT")))));
   		  }catch (UnitException e1) {
   						log.log(Log.SEVERE, "UnitException",e1.getMessage());
   			}
 			  containerDetailsVOs.add(containerDetailsVO);
        	  }
 			//prevULDNameKey = currULDNameKey;
 			
 		//	 log.log(Log.FINE, "The NEW container parentID is Found to be ", currULDNameKey);
		//	  containerDetailsVO = new ContainerDetailsVO();
		//	  populateContainerDetails(containerDetailsVO, rs);
		//	  containerDetailsVOsList.add(containerDetailsVO);
			 
	   //  mailAcceptanceVO.setContainerDetails(containerDetailsVOsList);
	  //  } 
 		}
 		//mailacceptanceVOs.add(mailAcceptanceVO);
        log.exiting(CLASS_NAME, "listMailFlifgts");
        }
		return mailacceptanceVOs;
	}
	
	/*private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO,ResultSet rs,MailAcceptanceVO mailAcceptanceVO)
			throws SQLException {
		   rs.getString("CONNAM");
		  containerDetailsVO.setContainerGroup(rs.getString("CONNAM"));
		  containerDetailsVO.setContainercount(rs.getInt("CONCNT"));
		  containerDetailsVO.setMailbagcount(rs.getInt("MALCNT"));
		  containerDetailsVO.setReceptacleCount(rs.getInt("RCPIDRCNT"));
		  try {
			  containerDetailsVO.setMailbagwt(Measure.addMeasureValues(containerDetailsVO.getMailbagwt(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"))));
			  containerDetailsVO.setReceptacleWeight(Measure.addMeasureValues(containerDetailsVO.getReceptacleWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCPWGT"))));
		  }catch (UnitException e1) {
						log.log(Log.SEVERE, "UnitException",e1.getMessage());
			}
	}*/

}
