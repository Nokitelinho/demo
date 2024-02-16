package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundCarrierMapper implements MultiMapper<MailAcceptanceVO> {
	private Log log = LogFactory.getLogger("MAIL operations");
	private static final String CLASS_NAME = "OutboundCarrierMapper";

	@Override
	public List<MailAcceptanceVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "OutboundCarrierMapper");
		List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		List<MailAcceptanceVO> mailacceptanceVOs = new ArrayList<MailAcceptanceVO>();
		ContainerDetailsVO containerDetailsVO = null; 
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		String currULDNameKey = "";
		String prevULDNameKey = "";
		String currCarrierkey="";
		String preCarrierkey="";
        while(rs.next()){
          currCarrierkey = rs.getString("CMPCOD") + rs.getString("FLTCARCOD") + rs.getString("ASGPRT") +rs.getString("DSTCOD");
          log.log(Log.FINE, "The NEW flight parentID is Found to be ", prevULDNameKey);
          MailAcceptanceVO mailAcceptanceVO =null;
          if (!currCarrierkey.equals(preCarrierkey))
          {
        	 prevULDNameKey="";
        	 mailAcceptanceVO = new MailAcceptanceVO();
 			 mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
 			 mailAcceptanceVO.setCarrierCode(rs.getString("FLTCARCOD"));
 			 mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
 			 mailAcceptanceVO.setDestination(rs.getString("DSTCOD"));
 			 //uplift airport for carriers-to confirm
 			mailAcceptanceVO.setPol(rs.getString("ASGPRT"));
 		     mailacceptanceVOs.add(mailAcceptanceVO);
 		     containerDetailsVOs = new ArrayList<ContainerDetailsVO>();
 		     mailAcceptanceVO.setContainerDetails(containerDetailsVOs);
 		     preCarrierkey = currCarrierkey;
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
 			containerDetailsVOs.add(containerDetailsVO);
 	        	  }
 	        	  } else {
 	 			  containerDetailsVO = new ContainerDetailsVO();
 	 			  containerDetailsVO.setContainerGroup(rs.getString("CONNAM"));
 	   		      containerDetailsVO.setContainercount(Integer.parseInt(rs.getString("CONCNT")));
 	   		      containerDetailsVO.setMailbagcount(Integer.parseInt(rs.getString("MALCNT")));
 	   		  try {
 	   			  containerDetailsVO.setMailbagwt(Measure.addMeasureValues(containerDetailsVO.getMailbagwt(), new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT")))));
 	   		  }catch (UnitException e1) {
 	   						log.log(Log.SEVERE, "UnitException",e1.getMessage());
 	   			}
 	 			  containerDetailsVOs.add(containerDetailsVO);
 	        	  }
 	 			 		}
		 }
        log.exiting(CLASS_NAME, "OutboundCarrierMapper");
        }
		return mailacceptanceVOs;
	}
	


}
