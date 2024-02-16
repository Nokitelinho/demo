/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 * 
 */
public class MailServiceStandardMapper implements Mapper<MailServiceStandardVO> {

	private Log log = LogFactory.getLogger("MailServiceStandardMapper");

	public MailServiceStandardVO map(ResultSet rs) throws SQLException {
		
			MailServiceStandardVO mailServiceStandardVO = new MailServiceStandardVO();
			mailServiceStandardVO.setCompanyCode(rs.getString("CMPCOD"));
			mailServiceStandardVO.setGpaCode(rs.getString("GPACOD"));
			mailServiceStandardVO.setOriginCode(rs.getString("ORGCOD"));
			mailServiceStandardVO.setDestinationCode(rs.getString("DSTCOD"));
			mailServiceStandardVO.setServicelevel(rs.getString("SRVLVL"));
			mailServiceStandardVO.setScanWaived(rs.getString("SCNWVDFLG"));
			mailServiceStandardVO.setServicestandard(rs.getString("SRVSTD"));
			mailServiceStandardVO.setContractid(rs.getString("CTRIDR"));
	
			log.log(Log.FINE,
					"\n\n mailServiceStandardVO listed !! ----------> ",
					mailServiceStandardVO.getGpaCode()
							+" "+ mailServiceStandardVO.getOriginCode()
							+" "+ mailServiceStandardVO.getDestinationCode()
							+" "+ mailServiceStandardVO.getServicelevel()
							+" "+ mailServiceStandardVO.getServicestandard()
							+" "+ mailServiceStandardVO.getScanWaived()
							+" "+ mailServiceStandardVO.getContractid());
			
		if(mailServiceStandardVO!=null){
			mailServiceStandardVO.setOperationFlag("U");
		}

		return mailServiceStandardVO;

	}

}
