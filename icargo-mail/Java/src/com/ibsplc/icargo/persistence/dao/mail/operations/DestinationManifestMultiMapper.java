/*
 * DestinationManifestMultiMapper.java Created on Jan 17, 2007
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

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;   
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

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
public class DestinationManifestMultiMapper implements
		MultiMapper<MailManifestVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	/**
	 * TODO Purpose
	 * Jan 17, 2007, A-1739
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		log.entering("DestinationManifestMultiMapper", "map");

		MailManifestVO mailManifestVO = new MailManifestVO();
		List<MailManifestVO> mailManifestVOs = new ArrayList<MailManifestVO>();
		mailManifestVOs.add(mailManifestVO);
		Collection<ContainerDetailsVO> containerDetails = 
			new ArrayList<ContainerDetailsVO>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		MailSummaryVO mailSummaryVO = null;
		Collection<MailSummaryVO> mailSummaryVOs = null;
		
		String currContainerKey = null;
		String prevContainerKey = null;
		String currDestnKey = null;
		String prevDestnKey = null;
		
		String containerNum = null;
		int segSerialNum = 0;
		String destination = null;
		String categoryCode = null;
		String mailClass = null;
		String origin	= null;
		
		int sumbags = 0;
		double sumWeight = 0;
		int totalbags = 0;
		double totalWeight = 0;
		
		while(rs.next()) {
			containerNum = rs.getString("ULDNUM");
			segSerialNum = rs.getInt("SEGSERNUM");
			
			currContainerKey = new StringBuilder().append(containerNum).
										append(segSerialNum).toString();
			if(!currContainerKey.equals(prevContainerKey)) {
				prevContainerKey = currContainerKey;
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setContainerNumber(containerNum);
				containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				populateContainerDetailsVO(containerDetailsVO, rs);
				//totalbags += containerDetailsVO.getTotalBags();
				//totalWeight += containerDetailsVO.getTotalWeight();
				//totalWeight += containerDetailsVO.getTotalWeight().getRoundedDisplayValue();//added by A-7371
				containerDetails.add(containerDetailsVO);
				mailSummaryVOs = new ArrayList<MailSummaryVO>();
				containerDetailsVO.setMailSummaryVOs(mailSummaryVOs);
				mailSummaryVO = null;
				prevDestnKey = null;
			}
			
			String doe = rs.getString("DSTEXGOFC");
			destination = rs.getString("DSTCTY");
			origin		= rs.getString("ORGCTY");//Added as part of ICRD-113230
			if(doe != null && doe.length() > 0 && 
					destination != null && destination.length() > 0) {
//				destination = doe.substring(2,5);
				categoryCode = rs.getString("MALCTGCOD");
				mailClass = rs.getString("MALCLS");
				
				for(int cls=0;cls < MailConstantsVO.MILITARY_CLASS.length;cls++){
					if(MailConstantsVO.MILITARY_CLASS[cls].equals(mailClass)){
						categoryCode = "M";
					}
				}
				
				currDestnKey = new StringBuilder().append(currContainerKey).
									append(destination).
									append(categoryCode).
									append(origin).toString();//Added as part of ICRD-113230
				if(!currDestnKey.equals(prevDestnKey)) {
					sumbags = 0;
					sumWeight = 0;
					prevDestnKey = currDestnKey;
					mailSummaryVO = new MailSummaryVO();
					mailSummaryVO.setDestination(destination);
					mailSummaryVO.setMailCategory(categoryCode);			
					mailSummaryVO.setOrigin(origin);//Added as part of ICRD-113230
					mailSummaryVOs.add(mailSummaryVO);
				}
				if(mailSummaryVO != null) {
					sumbags += rs.getInt("ACPBAG");
					sumWeight += rs.getDouble("ACPWGT");
					mailSummaryVO.setOriginPA(rs.getString("ORGPOA"));
					mailSummaryVO.setDestinationPA(rs.getString("DSTPOA"));
					mailSummaryVO.setBagCount(sumbags);
					//mailSummaryVO.setTotalWeight(sumWeight);
					mailSummaryVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,sumWeight,0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));//added by A-7371
					
					containerDetailsVO.setTotalBags(rs.getInt("ACPBAG")+containerDetailsVO.getTotalBags());
					containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")+containerDetailsVO.getTotalWeight().getSystemValue(),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
					totalbags += rs.getInt("ACPBAG");
					totalWeight +=rs.getDouble("ACPWGT");
				}
			}
		}
		mailManifestVO.setTotalbags(totalbags);
		Measure totalWt=new Measure(UnitConstants.MAIL_WGT,0.0,totalWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM);//added by A-7371
		mailManifestVO.setTotalWeight(totalWt);
		//mailManifestVO.setTotalWeight(totalWeight);
		log.exiting("DestinationManifestMultiMapper", "map");
		return mailManifestVOs;
	}

	/**
	 * TODO Purpose
	 * Jan 18, 2007, A-1739
	 * @param containerDetailsVO
	 * @param rs 
	 * @throws SQLException 
	 */
	private void populateContainerDetailsVO(
			ContainerDetailsVO containerDetailsVO, ResultSet rs) throws SQLException {
		//containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0,0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		
	}

}
