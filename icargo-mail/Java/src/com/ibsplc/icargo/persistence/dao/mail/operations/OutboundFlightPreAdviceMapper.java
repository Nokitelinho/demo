package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.PreAdviceVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OutboundFlightPreAdviceMapper implements Mapper<MailAcceptanceVO> {
	
	private static final Log LOGGER = LogFactory.getLogger("MAIL operations");
	
	@Override
	public MailAcceptanceVO map(ResultSet rs) throws SQLException {
		
    	MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
    	
 			 mailAcceptanceVO.setCompanyCode(rs.getString("CMPCOD"));
 			 mailAcceptanceVO.setCarrierId(rs.getInt("FLTCARIDR"));
 	         mailAcceptanceVO.setFlightNumber(rs.getString("FLTNUM"));
 	         mailAcceptanceVO.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
 		     PreAdviceVO preadvicevo = new PreAdviceVO();
 		   
 		    preadvicevo.setTotalBags(rs.getInt("RCPIDRCNT"));
 		   if(rs.getString("RCPWGT")!=null) {
 	 		     try {
					preadvicevo.setTotalWeight(Measure.addMeasureValues(preadvicevo.getTotalWeight(), new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("RCPWGT")))));
 	 		   } catch (UnitException unitException) {
 	 			 LOGGER.log(Log.INFO,unitException);
				}
 	 		  }
				
 		     mailAcceptanceVO.setPreadvice(preadvicevo);
 		  
		 
		return mailAcceptanceVO;
	}

}
