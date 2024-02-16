package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailTagDetailsMapper implements Mapper<MailbagVO>{
private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	

	/**
	 *	Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 *	Added by 			: A-7871
	 * 	Used for 	:		ICRD-108366
	 *	Parameters	:	@param rs
	 *	Parameters	:	@return mailbagVO
	 *	Parameters	:	@throws SQLException 
	 */
	public MailbagVO map(ResultSet rs) throws SQLException {
		log.entering("MailTagDetailsMapper", "Resultset");
		MailbagVO mailbagVO = new MailbagVO();
		//if(rs.getString("OPRORG")!=null){
		mailbagVO.setOperatorOrigin(rs.getString("OPRORG"));

	    mailbagVO.setOperatorDestination(rs.getString("OPRDST"));

	    mailbagVO.setType(rs.getString("CSGTYP"));
	    mailbagVO.setReceptacleType(rs.getString("RCPTYP"));
	    mailbagVO.setOrgPaName(rs.getString("POANAM"));
	    mailbagVO.setDstPaName(rs.getString("DSTPOANAM"));
	    mailbagVO.setOoe(rs.getString("ORGEXGOFC"));
	    mailbagVO.setDoe(rs.getString("DSTEXGOFC"));
	    if (rs.getDate("CSGDAT") != null) {
	      mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("CSGDAT")));
	    }
	    mailbagVO.setDestCityDesc(rs.getString("DSTEXGCOD"));
	    mailbagVO.setMailSubclass(rs.getString("MALSUBCLS"));
	    mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(rs.getString("WGT")),0.0,MailConstantsVO.WEIGHTCODE_KILO));//added by A-8353 for ICRD-274933
	    mailbagVO.setOrgCityDesc(rs.getString("EXGCODDES"));
	    mailbagVO.setDespatchSerialNumber(rs.getString("DSN"));
	    mailbagVO.setReceptacleSerialNumber(rs.getString("RSN"));
	    mailbagVO.setMailbagId(rs.getString("MALIDR"));

	    mailbagVO.setSealNumber(rs.getString("SELNUM"));

	    mailbagVO.setPou(rs.getString("POU"));
	    mailbagVO.setPol(rs.getString("POL"));
	    if (rs.getDate("FLTDAT") != null) {
	      mailbagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("FLTDAT")));
	    }
	    mailbagVO.setFlightNumber(rs.getString("FLTNUM"));
	    mailbagVO.setMailCategoryCode(rs.getString("MALCTG"));
	    mailbagVO.setCarrierCode(rs.getString("FLTCARCOD"));
	    mailbagVO.setHighestNumberedReceptacle(rs.getString("HSN"));
	    mailbagVO.setRegisteredOrInsuredIndicator(rs.getString("REGIND"));
		mailbagVO.setYear(rs.getInt("YER"));//added by a-7871 FOR ICRD-262885
	    log.exiting("MailTagDetailsMapper", "Resultset");
	    return mailbagVO;
	}

	
}
