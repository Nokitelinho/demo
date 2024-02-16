/*
 * MailSLAMultiMapper.java created on Mar 06, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class MailSLAMultiMapper implements MultiMapper<MailSLAVO> {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA MAILSLAMULTIMAPPER");
	
	/**
	 * @return List<MailSLAVO>
	 * @throws SQLException
	 * @param rs
	 */
	public List<MailSLAVO> map(ResultSet rs) throws SQLException {
		HashMap<String, MailSLAVO> mailSLAVoMap = new
			HashMap<String, MailSLAVO>();
		HashMap<String, MailSLADetailsVO> mailSLADetailsVoMap = new 
			HashMap<String, MailSLADetailsVO>();			
	
	MailSLAVO mailSLAVo = null; 
	MailSLADetailsVO mailSLADetailsVo = null;
	
	Collection<MailSLAVO> mailSLAVosCollection = null;
	try {
		while (rs.next()) {
			String parentId = null;
			String mailSLADetailsId = null;
			parentId = rs.getString("CMPCOD") + rs.getString("SLAIDR");
			if (rs.getString("SERNUM") != null) {
				mailSLADetailsId = parentId+ rs.getString("SERNUM");
			}
			if (!mailSLAVoMap.containsKey(parentId)) {
				log.log(Log.INFO, "New PARENT ID ");
				mailSLAVo = new MailSLAVO();
				mailSLAVo.setSlaId(rs.getString("SLAIDR"));
				mailSLAVo.setCompanyCode(rs.getString("CMPCOD"));
				mailSLAVo.setCurrency(rs.getString("CURCOD"));
				mailSLAVo.setDescription(rs.getString("SLADSC"));
								
				mailSLAVo.setMailSLADetailsVos(new ArrayList<MailSLADetailsVO>());				
				mailSLAVoMap.put(parentId, mailSLAVo);
				
			}
			else {
				log.log(Log.INFO, "Existing PARENT ID ");
				mailSLAVo = mailSLAVoMap.get(parentId);
			}
			if (mailSLADetailsId != null) {
				if (!mailSLADetailsVoMap.containsKey(mailSLADetailsId)
						&& rs.getString("SERNUM") != null) {
					log.log(Log.INFO, "new mailSLADetailsId");
					mailSLADetailsVo = new MailSLADetailsVO();
					
					mailSLADetailsVo.setSerialNumber(rs.getInt("SERNUM"));
					mailSLADetailsVo.setSlaId(rs.getString("SLAIDR"));
					mailSLADetailsVo.setCompanyCode(rs.getString("CMPCOD"));
					mailSLADetailsVo.setMailCategory(rs.getString("MALCTGCOD"));
					mailSLADetailsVo.setServiceTime(rs.getInt("SRVTIM"));
					mailSLADetailsVo.setAlertTime(rs.getInt("ALRTIM"));
					mailSLADetailsVo.setChaserTime(rs.getInt("CHSTIM"));
					mailSLADetailsVo.setChaserFrequency(rs.getInt("CHSFRQ"));
					mailSLADetailsVo.setMaxNumberOfChasers(rs.getInt("MAXNUMCHS"));
					mailSLADetailsVo.setClaimRate(rs.getDouble("CLMRAT"));
					
					mailSLAVo.getMailSLADetailsVos().add(mailSLADetailsVo);
					mailSLADetailsVoMap.put(mailSLADetailsId,	mailSLADetailsVo);
					log.log(Log.INFO, "new mailSLADetailsId");
					
				}else{
					log.log(Log.INFO, "existing MailSLAMultiMapper");
					
				}
			}
		}
	}catch (SQLException sqlException) {
		sqlException.getErrorCode();
	}
	mailSLAVosCollection = new ArrayList<MailSLAVO>(mailSLAVoMap.values());
	return new ArrayList<MailSLAVO>(mailSLAVosCollection);
	}

}
