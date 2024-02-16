package com.ibsplc.neoicargo.mail.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailDiscrepancyVO;
import lombok.extern.slf4j.Slf4j;

/** 
 * @author A-1883Revision History ------------------------------------------------------------------------- Revision 		Date 					Author 		Description -------------------------------------------------------------------------  0.1     		   Jan 19, 2007			  	 a-1883		Created
 */
@Slf4j
public class MailDiscrepancyMapper implements Mapper<MailDiscrepancyVO> {
	/** 
	* @param rs
	* @return
	* @throws SQLException
	*/
	public MailDiscrepancyVO map(ResultSet rs) throws SQLException {
		log.debug("MailDiscrepancyMapper" + " : " + "Map" + " Entering");
		MailDiscrepancyVO mailDiscrepancyVO = new MailDiscrepancyVO();
		mailDiscrepancyVO.setCompanyCode(rs.getString("CMPCOD"));
		mailDiscrepancyVO.setMailIdentifier(rs.getString("MALIDR"));
		mailDiscrepancyVO.setUldNumber(rs.getString("ULDNUM"));
		String acceptedStatus = rs.getString("ACPSTA");
		String arrivalStatus = rs.getString("ARRSTA");
		if (MailDiscrepancyVO.FLAG_YES.equals(acceptedStatus) && MailDiscrepancyVO.FLAG_NO.equals(arrivalStatus)) {
			mailDiscrepancyVO.setDiscrepancyType(MailConstantsVO.MAIL_DISCREPANCY_MISSING);
		} else if (MailDiscrepancyVO.FLAG_NO.equals(acceptedStatus)
				&& MailDiscrepancyVO.FLAG_YES.equals(arrivalStatus)) {
			mailDiscrepancyVO.setDiscrepancyType(MailConstantsVO.MAIL_DISCREPANCY_FOUND);
		}
		return mailDiscrepancyVO;
	}
}
