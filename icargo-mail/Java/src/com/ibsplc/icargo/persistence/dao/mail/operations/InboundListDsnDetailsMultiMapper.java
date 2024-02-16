package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.InboundListDsnDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	29-Dec-2018		:	Draft
 */
public class InboundListDsnDetailsMultiMapper implements 
			MultiMapper<DSNVO>{
	
	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String CLASS_NAME = "InboundListDsnDetailsMultiMapper";
	
	public List<DSNVO> map(ResultSet rs) throws SQLException {
		
		log.entering(CLASS_NAME, "map");
		
		List<DSNVO> dsnvos = new ArrayList<DSNVO>();
		while (rs.next()) {
			DSNVO dsnvo = new DSNVO();
			if(rs.getString("DSN")!=null){
			dsnvo.setDsn(rs.getString("DSN"));    
			dsnvo.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
			dsnvo.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
			dsnvo.setMailClass(rs.getString("MALCLS"));
			dsnvo.setMailSubclass(rs.getString("MALSUBCLS"));
			dsnvo.setMailCategoryCode(rs.getString("MALCTGCOD"));
			dsnvo.setYear(rs.getInt("YER")); 
			dsnvo.setBags(rs.getInt("DSNACPCNT"));
			dsnvo.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));
			dsnvo.setReceivedBags(rs.getInt("DSNRCVCNT"));
			dsnvo.setReceivedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNRCVWGT")));
			dsnvo.setDeliveredBags(rs.getInt("DSNACPCNT"));
			dsnvo.setDeliveredWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));
			dsnvo.setRemarks(rs.getString("RMK"));
			dsnvo.setContainerNumber(rs.getString("CSGDOCNUM"));
			dsnvo.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
			//Added by A-7540
			dsnvo.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
			dsnvo.setOrigin(rs.getString("ORGCOD"));
			dsnvo.setDestination(rs.getString("DSTCOD"));  
			dsnvo.setPaCode(rs.getString("POACOD")); 
			dsnvo.setPltEnableFlag(rs.getString("PLTENBFLG"));
			dsnvo.setRoutingAvl(rs.getString("RTGAVL"));
			dsnvos.add(dsnvo);
			}
		}
		
		return dsnvos;
	}

}
