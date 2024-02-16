package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;


public class MailBagsForPABuiltContainerMapper implements Mapper<MailbagVO>{ 

	
	
	public MailbagVO  map(ResultSet rs)
			 throws SQLException{
				MailbagVO mailBagVo = new MailbagVO();
				mailBagVo.setCompanyCode(rs.getString("CMPCOD"));
				mailBagVo.setMailbagId(rs.getString("MALIDR"));
				mailBagVo.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				mailBagVo.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
				mailBagVo.setContainerNumber(rs.getString("CONNUM"));    			
				mailBagVo.setPou(rs.getString("POU"));
				mailBagVo.setDoe(rs.getString("DSTEXGOFC"));
				mailBagVo.setOoe(rs.getString("ORGEXGOFC"));
				mailBagVo.setMailCategoryCode(rs.getString("MALCTG"));
				mailBagVo.setMailSubclass(rs.getString("MALSUBCLS")); 
				mailBagVo.setOrigin(rs.getString("ORGCOD"));
				mailBagVo.setDestination(rs.getString("DSTCOD"));
				mailBagVo.setConsignmentNumber(rs.getString("CSGDOCNUM"));
				mailBagVo.setConsignmentSequenceNumber(rs.getInt("CSGSEQNUM"));
				mailBagVo.setFlightNumber(rs.getString("FLTNUM"));
				mailBagVo.setFlightSequenceNumber(rs.getInt("FLTSEQNUM"));
				mailBagVo.setCarrierId(rs.getInt("FLTCARIDR"));
				mailBagVo.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
				mailBagVo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
				mailBagVo.setContainerJourneyId((rs.getString("MALJNRIDR")));
				return mailBagVo;
			}
}
