package com.ibsplc.icargo.persistence.dao.mail.operations;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.ListManifestDetailsMultiMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	28-Dec-2018		:	Draft
 */
public class ListManifestDetailsMultiMapper implements
					MultiMapper<MailArrivalVO>{
	
	private Log log = LogFactory.getLogger("Mail Operations");

	private static final String CLASS_NAME = "ListManifestDetailsMultiMapper";

	public ArrayList<MailArrivalVO> map(ResultSet rs) throws SQLException {
		
		log.entering(CLASS_NAME, "map");
		ArrayList<MailArrivalVO> mailArrivalVOs=new ArrayList<MailArrivalVO>();
		while (rs.next()) {     
			 MailArrivalVO mailArrivalVO=new MailArrivalVO();
			 String manifestInfo=null;
			 if(null!=rs.getString("CMPCOD")){
				 mailArrivalVO.setFlightCarrierCode(rs.getString("CMPCOD"));
			 }
			 if(null!=rs.getString("FLTNUM")){
				 mailArrivalVO.setFlightNumber(rs.getString("FLTNUM"));
			 }
			 if(0!=rs.getInt("FLTSEQNUM")){
				 mailArrivalVO.setFlightSequenceNumber((rs.getInt("FLTSEQNUM")));
			 }
			 if(0!=rs.getInt("FLTCARIDR")){
				 mailArrivalVO.setCarrierId((rs.getInt("FLTCARIDR")));
			 }
			 if(0!=rs.getInt("SEGSERNUM")){
				 mailArrivalVO.setSegmentSerialNumber(rs.getInt("LEGSERNUM"));
			 }
			 if(null!=rs.getString("LEGORG")){
				 mailArrivalVO.setPol(rs.getString("LEGORG"));
			 }
			 if(null!=rs.getDate("FLTDAT")){
				 mailArrivalVO.setFlightDate(new LocalDate(NO_STATION,
							NONE, rs.getDate("FLTDAT")));
			 }
			 if(null!=rs.getString("CONNUM")){

				 manifestInfo= new StringBuffer().append(rs.getString(MailConstantsVO.CONCOUNT)).append("-")
						 .append(rs.getString("CONNUM")).append("(").append(rs.getInt("MALCOUNT")).append("/") 

						 	.append(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("TOTWGT")).getRoundedDisplayValue()).append(")").toString();
				 mailArrivalVO.setManifestInfo(manifestInfo);
			 }
			 if(null!=rs.getString(MailConstantsVO.CONCOUNT)){
				 mailArrivalVO.setContainerCount(Double.parseDouble(rs.getString(MailConstantsVO.CONCOUNT)));
			 }
			 if(null!=rs.getString("TOTWGT")){
				 mailArrivalVO.setTotalWeight(Double.parseDouble(rs.getString("TOTWGT"))); 
			 }

			 if(null!=rs.getString(MailConstantsVO.CONCOUNT)){
				 mailArrivalVO.setMailCount(Double.parseDouble(rs.getString(MailConstantsVO.CONCOUNT)));
			 }

			 if(null!=rs.getString("MALCOUNT")){
				 mailArrivalVO.setMailCount(Double.parseDouble(rs.getString("MALCOUNT")));

			 }
			 
			 mailArrivalVOs.add(mailArrivalVO); 
			 
		}
		
		return mailArrivalVOs;
		
	}

	}
