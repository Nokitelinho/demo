/*
 * MailbagManifestMultiMapper.java Created on Jan 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Jan 17, 2007			A-1739		Created
 */
public class MailbagManifestMultiMapper implements MultiMapper<MailManifestVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/**
	 * Jan 17, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		log.entering("MailbagManifestMultiMapper", "map");
		
		List<MailManifestVO> mailManifests = new ArrayList<MailManifestVO>();
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifests.add(mailManifestVO);
		List<ContainerDetailsVO> containerDetails = 
			new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;		
		int segSerialNum = 0;
		String containerNum = null;
		String mailbagId = null;
		//Added by A-8438 as part of ICRD-303640 start
		String mailClass=null;
		//Added by A-8438 as part of ICRD-303640 end
		int totalbags = 0;
		double totalWeight = 0;
		int uldbags = 0;
		double uldWeight = 0;
		
		while(rs.next()) {
			segSerialNum = rs.getInt("SEGSERNUM");
			containerNum = rs.getString("ULDNUM");
			
			currContainerKey = new StringBuilder().append(segSerialNum).
								append(containerNum).toString();
			if(!currContainerKey.equals(prevContainerKey)) {
				prevContainerKey = currContainerKey;				
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				containerDetailsVO.setContainerNumber(containerNum);
				mailbagVOs = new ArrayList<MailbagVO>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				populateContainerDetails(rs, containerDetailsVO);				
				containerDetails.add(containerDetailsVO);
				mailbagVO = null;
				uldbags = 0;
				uldWeight = 0;
			}
			
			mailbagId = rs.getString("MALIDR");
			mailClass = rs.getString("MALCLS");
			if(mailbagId != null) {
				currMailbagKey = new StringBuilder().append(currContainerKey).
							append(mailbagId).toString();
				
				if(!currMailbagKey.equals(prevMailbagKey)) {
					prevMailbagKey = currMailbagKey;
					mailbagVO = new MailbagVO();
					mailbagVO.setMailbagId(mailbagId);
					//Added by A-8438 as part of ICRD-303640 start
					mailbagVO.setMailClass(mailClass);
					//Added by A-8438 as part of ICRD-303640 end
					populateMailbagDetails(rs, mailbagVO);
					mailbagVOs.add(mailbagVO);				
					totalbags += 1;
					if(mailbagVO.getWeight()!=null){
					totalWeight += mailbagVO.getWeight().getRoundedDisplayValue();//modified by A-8353
					}
					uldbags  += 1;
					if(mailbagVO.getWeight()!=null){
					uldWeight += mailbagVO.getWeight().getRoundedDisplayValue();//modified by A-8353
					}
					containerDetailsVO.setTotalBags(uldbags );
					//containerDetailsVO.setTotalWeight(uldWeight);
					containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,uldWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
				}
			}
		}
		mailManifestVO.setTotalbags(totalbags);
		mailManifestVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,totalWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371,modified by A-8353
		//mailManifestVO.setTotalWeight(totalWeight);
		
		log.exiting("MailbagManifestMultiMapper", "map");
		return mailManifests;
	}
	
	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO) 
		throws SQLException {
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("MALWGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM);//modified by A-8353 
		mailbagVO.setWeight(wgt);//modified by A-7371
		mailbagVO.setOrigin(rs.getString("ORGCOD"));
		mailbagVO.setDestination(rs.getString("DSTCOD"));
		mailbagVO.setMailClass(rs.getString("MALCLS"));
	}

	private void populateContainerDetails(ResultSet rs, 
			ContainerDetailsVO containerDetailsVO) throws SQLException{
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));	
		containerDetailsVO.setPol(rs.getString("POL"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		
	}

}
