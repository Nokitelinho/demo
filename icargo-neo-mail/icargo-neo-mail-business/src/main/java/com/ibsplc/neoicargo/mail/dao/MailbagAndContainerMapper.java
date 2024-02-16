package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MailUploadVO;

/** 
 * @author A-1885
 */
public class MailbagAndContainerMapper implements Mapper<MailUploadVO> {
	/** 
	* This class is used to map the ResultSet into the MailBagVo
	* @param rs
	* @return 
	* @throws SQLException
	*/
	public MailUploadVO map(ResultSet rs) throws SQLException {
		MailUploadVO mailUploadVO = new MailUploadVO();
		mailUploadVO.setMailTag(rs.getString("MALIDR"));
		mailUploadVO.setContainerNumber(rs.getString("CONNUM"));
		mailUploadVO.setToContainer(rs.getString("ULDNUM"));
		mailUploadVO.setContainerType(rs.getString("CONTYP"));
		mailUploadVO.setContainerPol(rs.getString("ASGPRT"));
		mailUploadVO.setContainerPOU(rs.getString("POU"));
		mailUploadVO.setDestination(rs.getString("DSTCOD"));
		if (MailUploadVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
			mailUploadVO.setArrived(true);
		}
		if (MailUploadVO.FLAG_YES.equals(rs.getString("DLVFLG"))) {
			mailUploadVO.setDeliverd(true);
		}
		if (MailUploadVO.FLAG_YES.equals(rs.getString("ACPFLG"))) {
			mailUploadVO.setAccepted(true);
		}
		return mailUploadVO;
	}
}
