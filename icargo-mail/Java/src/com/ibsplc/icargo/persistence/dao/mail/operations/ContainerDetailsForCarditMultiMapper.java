/*
 * ContainerDetailsForCarditMultiMapper.java Created on Jan 24, 2007.
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1936
 * The Container DetailsForCardit MultiMapper 
 * 
 */
public class ContainerDetailsForCarditMultiMapper  implements MultiMapper<ContainerVO> {


	private static final String RESDIT_DISP_SEP = "/";
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/**
	 * TODO Purpose
	 * Oct 1, 2007, a-1936
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<ContainerVO> map(ResultSet rs) throws SQLException {
		log.entering("CarditMailbagMultiMapper", "map");
		Map<String, String> resditEventMap = initResditEventMap();
		List<ContainerVO> containerVos = new ArrayList<ContainerVO>();
		ContainerVO containerVo = null;
		
		String currentContainerID = null;
		String prevContainerID = null;
		String currentEventId = null;
		String prevEventId = null;
        String event = null;
		String eventPort = null;
		
		StringBuilder eventStringBuilder = null;
		
		while(rs.next()) {			
			currentContainerID = rs.getString("CONNUM")+rs.getString("CDTKEY");
		    
			if(!currentContainerID.equals(prevContainerID)) {
				prevContainerID = currentContainerID;
				containerVo = new ContainerVO();
				containerVo.setContainerNumber(currentContainerID);
				populateContainerDetails(rs, containerVo);
				eventStringBuilder = new StringBuilder();
				containerVos.add(containerVo);
			}
			
			event = rs.getString("RDTEVTCOD");
			if(event != null) {
				eventPort = rs.getString("RDTEVTPRT");
				currentEventId = new StringBuilder(currentContainerID).append(event).
									append(eventPort).toString();
				if(!currentEventId.equals(prevEventId)) {
					prevEventId = currentEventId;
					if(eventStringBuilder.length() > 0) {
						eventStringBuilder.append(RESDIT_DISP_SEP);
					}
					eventStringBuilder = eventStringBuilder.append(resditEventMap.get(event));
					containerVo.setResditEventString(eventStringBuilder.toString());
				}
			}
		}
		log.exiting("CarditMailbagMultiMapper", "map");
		return containerVos;
	}
	
	
	/**
	 * TODO Purpose
	 * Oct 1  2007, a-1936
	 * @return
	 */
	private Map<String, String> initResditEventMap() {
		Map<String, String> resditEventMap = new HashMap<String, String>();	
		resditEventMap.put(MailConstantsVO.RESDIT_RECEIVED, 
				MailConstantsVO.RESDIT_ACRON_RECV);
		resditEventMap.put(MailConstantsVO.RESDIT_ASSIGNED, 
				MailConstantsVO.RESDIT_ACRON_ASG);
		resditEventMap.put(MailConstantsVO.RESDIT_UPLIFTED, 
				MailConstantsVO.RESDIT_ACRON_UPL);
		resditEventMap.put(MailConstantsVO.RESDIT_PENDING, 
				MailConstantsVO.RESDIT_ACRON_PEND);
		resditEventMap.put(MailConstantsVO.RESDIT_RETURNED, 
				MailConstantsVO.RESDIT_ACRON_RET);
		resditEventMap.put(MailConstantsVO.RESDIT_HANDOVER_OFFLINE, 
				MailConstantsVO.RESDIT_ACRON_HND);
		resditEventMap.put(MailConstantsVO.RESDIT_DELIVERED, 
				MailConstantsVO.RESDIT_ACRON_DELV);
		resditEventMap.put(MailConstantsVO.RESDIT_HANDOVER_ONLINE, 
				MailConstantsVO.RESDIT_ACRON_HND_ONL);
		resditEventMap.put(MailConstantsVO.RESDIT_LOADED, 
				MailConstantsVO.RESDIT_ACRON_LOAD);
		return resditEventMap;
	}


	/**
	 * TODO Purpose
	 * Jan 24, 2007, a-1936	
	 * @param rs
	 * @param containerVo
	 * @throws SQLException 
	 */
	private void populateContainerDetails(ResultSet rs, ContainerVO containerVo) 
	throws SQLException {
		log.entering("CarditMailbagMultiMapper", "populateContainerDetails");
		containerVo.setContainerNumber(rs.getString("CONNUM"));
		containerVo.setPaCode(rs.getString("SDRIDR"));
		containerVo.setConsignmentDocumentNumber(rs.getString("CSGDOCNUM"));
		//can be Null
		java.sql.Date consignDate = rs.getDate("CSGCMPDAT");
		if(consignDate != null) {
			containerVo.setConsignmentDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
							rs.getDate("CSGCMPDAT")));
		}
		containerVo.setResditEventPort(rs.getString("RDTEVTPRT"));
		containerVo.setCarditRecipientId(rs.getString("RCTIDR"));
		/*
		 * Added By Karthick V to incorporate the Container Information Details 
		 * 
		 * 
		 */
		containerVo.setCarditKey(rs.getString("CDTKEY"));
		containerVo.setContainerSealNumber(rs.getString("CONSELNUM"));
		containerVo.setMeasurementDimension(rs.getString("MMTDMN"));
		containerVo.setTypeCode(rs.getString("TYPCOD"));
		containerVo.setCodeListResponsibleAgency(rs.getString("NUMCODLSTAGY"));
		containerVo.setTypeCodeListResponsibleAgency(rs.getString("TYPCODLSTAGY"));
		//containerVo.setContainerWeight(rs.getString("NETWGT"));//added by A-7371
		containerVo.setContainerWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("NETWGT"))));
		containerVo.setEquipmentQualifier(rs.getString("EQPQLF"));
		containerVo.setCompanyCode(rs.getString("CMPCOD"));
		log.exiting("CarditMailbagMultiMapper", "populateContainerDetails");			
	}
}
