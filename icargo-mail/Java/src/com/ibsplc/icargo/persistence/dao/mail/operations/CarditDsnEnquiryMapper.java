package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.CarditDsnEnquiryMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	21-Oct-2019		:	Draft
 */
public class CarditDsnEnquiryMapper  implements MultiMapper<DSNVO>  {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	
	public ArrayList<DSNVO> map(ResultSet rs) throws SQLException {
		log.entering("CarditDsnEnquiryMapper", "map");
		
		ArrayList<DSNVO> dsnVos=
				new ArrayList<>();
		while(rs.next()) {
			DSNVO dsnvo=new DSNVO();
			dsnvo.setFlightNumber(rs.getString("FLTNUM"));
			if(rs.getTimestamp("FLTDAT")!=null)
				dsnvo.setFlightDate(
						new LocalDate(LocalDate.NO_STATION,Location.NONE, rs.getTimestamp("FLTDAT")));
			dsnvo.setCarrierCode(rs.getString("CARCOD"));
			dsnvo.setOriginExchangeOffice(rs.getString("ORGEXGOFF")); 
			dsnvo.setDestinationExchangeOffice(rs.getString("DSTEXGOFF"));   
			dsnvo.setYear(rs.getInt("DSPYER"));
			dsnvo.setDsn(rs.getString("DSPSRLNUM"));
			dsnvo.setMailCategoryCode(rs.getString("MALCTGCOD"));
			dsnvo.setMailSubclass(rs.getString("MALSUBCLS"));
			dsnvo.setBags(rs.getInt("MALCNT"));
			dsnvo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("RCPWGT")));
			dsnvo.setCompanyCode(rs.getString("CMPCOD"));
			dsnvo.setShipmentCode(rs.getString("SHPPFX")); 
			dsnvo.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
			dsnvo.setCsgDocNum(rs.getString("CSGDOCNUM")); 
			if(rs.getTimestamp("CSGDAT")!=null)
				dsnvo.setConsignmentDate(
						new LocalDate(LocalDate.NO_STATION,Location.NONE, rs.getTimestamp("CSGDAT")));
			dsnvo.setPaCode(rs.getString("POACOD"));
			dsnvo.setContainerNumber(rs.getString("CONNUM"));
			dsnvo.setAcceptanceStatus(rs.getString("ACPSTA"));
			if(rs.getTimestamp("REQDLVTIM")!=null)
				dsnvo.setReqDeliveryTime(
						new LocalDate(LocalDate.NO_STATION,Location.NONE, rs.getTimestamp("REQDLVTIM")));
			
			dsnVos.add(dsnvo);
		}
		
		return dsnVos;
		
	}

}
