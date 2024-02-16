package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailbagDSNMapper implements Mapper<DSNVO> {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	@Override
	public DSNVO map(ResultSet rs) throws SQLException {
		DSNVO dsnVO = new DSNVO();
		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		dsnVO.setBags(rs.getInt("DSNCNT"));
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		dsnVO.setPaCode(rs.getString("POACOD"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
	//	despatchDetailsVO.setd	
	//	despatchDetailsVO.setDeliveredBags(rs.getInt("DSNCNT"));
			try {
				dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getDeliveredWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNWGT"))));
			}catch (UnitException e1) {
				log.log(Log.SEVERE, "UnitException",e1.getMessage());
		    }
			
		    return dsnVO;
	}

}
