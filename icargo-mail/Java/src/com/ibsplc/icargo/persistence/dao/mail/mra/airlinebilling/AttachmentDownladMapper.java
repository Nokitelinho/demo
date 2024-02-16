/*
 * AttachmentDownladMapper created on Oct 29, 2018 
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.SisSupportingDocumentDetailsVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Mapper class for downloading single attachment.
 * 
 * @author a-8061
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  October 29, 2018				A-8061 		 Created
 */
public class AttachmentDownladMapper  implements Mapper<SisSupportingDocumentDetailsVO>{
	
	private Log log = LogFactory.getLogger("AttachmentDownladMapper");
	
	
	public SisSupportingDocumentDetailsVO map(ResultSet rs) throws SQLException {
		log.entering("AttachmentDownladMapper", "map");
		SisSupportingDocumentDetailsVO sisSupportingDocumentDetailsVO = new SisSupportingDocumentDetailsVO();
		sisSupportingDocumentDetailsVO.setFilename(rs.getString("FILNAM"));		
		sisSupportingDocumentDetailsVO.setFileData(rs.getBytes("FILDAT"));
		return sisSupportingDocumentDetailsVO;
	}

}
