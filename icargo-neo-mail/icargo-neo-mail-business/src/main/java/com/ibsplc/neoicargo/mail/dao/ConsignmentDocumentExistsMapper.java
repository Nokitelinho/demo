package com.ibsplc.neoicargo.mail.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;

/** 
 * @author a-1883
 */
@Slf4j
public class ConsignmentDocumentExistsMapper implements Mapper<ConsignmentDocumentVO> {
	/** 
	* @author A-1883
	* @param resultSet
	* @return
	* @throws SQLException
	*/
	public ConsignmentDocumentVO map(ResultSet resultSet) throws SQLException {
		LocalDate localDateUtil = ContextUtil.getInstance().getBean(LocalDate.class);
		log.debug("ConsignmentDocumentExistsMapper" + " : " + "map" + " Entering");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(resultSet.getString("CMPCOD"));
		consignmentDocumentVO.setConsignmentNumber(resultSet.getString("CSGDOCNUM"));
		consignmentDocumentVO.setPaCode(resultSet.getString("POACOD"));
		consignmentDocumentVO.setConsignmentSequenceNumber(resultSet.getInt("CSGSEQNUM"));
		String senderId = resultSet.getString("SDRIDR");
		if (senderId != null) {
			consignmentDocumentVO.setPaCode(senderId);
		}
		Date csgCmpDat = resultSet.getDate("CSGCMPDAT");
		if (csgCmpDat != null) {
			consignmentDocumentVO.setConsignmentDate(localDateUtil.getLocalDate(null, csgCmpDat));
		}
		log.debug("ConsignmentDocumentExistsMapper" + " : " + "map" + " Exiting");
		return consignmentDocumentVO;
	}
}
