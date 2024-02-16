package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

public class CN46MailManifestMultiMapper implements MultiMapper<MailManifestVO> {

	private static final String REGIND = "REGIND";
	private static final String HSN = "HSN";
	
	public List<MailManifestVO> map(ResultSet rs) throws SQLException {
		
		List<MailManifestVO> mailManifests = new ArrayList<>();
		MailManifestVO mailManifestVO = new MailManifestVO();
		mailManifests.add(mailManifestVO);
		List<ContainerDetailsVO> containerDetails = 
			new ArrayList<>();
		mailManifestVO.setContainerDetails(containerDetails);
		ContainerDetailsVO containerDetailsVO = null;
		MailbagVO mailbagVO = null;
		Collection<MailbagVO> mailbagVOs = null;
		String currContainerKey = null;
		String prevContainerKey = null;
		String currMailbagKey = null;
		String prevMailbagKey = null;		
		int segSerialNum = 0;
		String containerNum = null;
		String mailbagId = null;
		String mailClass=null;
		int totalbags = 0;
		double totalWeight = 0;
		int uldbags = 0;
		double uldWeight = 0;
		
		while(rs.next()) {
			segSerialNum = rs.getInt("SEGSERNUM");
			containerNum = rs.getString("CONNUM");
			
			currContainerKey = new StringBuilder().append(segSerialNum).
								append(containerNum).toString();
			if(!currContainerKey.equals(prevContainerKey)) {
				prevContainerKey = currContainerKey;				
				containerDetailsVO = new ContainerDetailsVO();
				containerDetailsVO.setSegmentSerialNumber(segSerialNum);
				containerDetailsVO.setContainerNumber(containerNum);
				mailbagVOs = new ArrayList<>();
				containerDetailsVO.setMailDetails(mailbagVOs);
				populateContainerDetails(rs, containerDetailsVO);				
				containerDetails.add(containerDetailsVO);
				uldbags = 0;
				uldWeight = 0;
			}
			
			mailbagId = rs.getString("MALIDR");
			mailClass = rs.getString("MALCLS");
			if(mailbagId != null) {
				currMailbagKey = new StringBuilder().append(currContainerKey).
							append(mailbagId).toString();
				
				if(!currMailbagKey.equals(prevMailbagKey)) {
					prevMailbagKey = currMailbagKey;
					mailbagVO = new MailbagVO();
					mailbagVO.setMailbagId(mailbagId);
					mailbagVO.setMailClass(mailClass);
					populateMailbagDetails(rs, mailbagVO);
					mailbagVOs.add(mailbagVO);				
					totalbags += 1;
					if(mailbagVO.getWeight()!=null){
					totalWeight += mailbagVO.getWeight().getRoundedDisplayValue();
					}
					uldbags  += 1;
					if(mailbagVO.getWeight()!=null){
					uldWeight += mailbagVO.getWeight().getRoundedDisplayValue();
					}
					containerDetailsVO.setTotalBags(uldbags );
					containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,uldWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));
					}
			}
			if(rs.getString("ARLNAM") !=null) {
				mailManifestVO.setAirlineName(rs.getString("TWOAPHCOD")+", "+(rs.getString("ARLNAM")));
			}
			
		}
		mailManifestVO.setTotalbags(totalbags);
		mailManifestVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,0.0,totalWeight,UnitConstants.WEIGHT_UNIT_KILOGRAM));
		
		return mailManifests;
	}
	
	private void populateMailbagDetails(ResultSet rs, MailbagVO mailbagVO) 
		throws SQLException {
		
		Measure wgt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"),0.0,UnitConstants.WEIGHT_UNIT_KILOGRAM);
		mailbagVO.setWeight(wgt);
		mailbagVO.setMailClass(rs.getString("MALCLS"));
		mailbagVO.setOriginOfExchangeOffice(rs.getString("ORGEXGOFC"));
		mailbagVO.setDestinationOfExchangeOffice(rs.getString("DSTEXGOFC"));
		if(rs.getTimestamp("FLTDAT") !=null) {
		mailbagVO.setFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("FLTDAT")));
		}
		mailbagVO.setYear(rs.getInt("YER"));
		mailbagVO.setDsn(rs.getString("DSN"));
		mailbagVO.setRsn(rs.getString("RSN"));
		if(rs.getString(HSN) !=null && rs.getString(REGIND) !=null) {
			mailbagVO.setMailRemarks(rs.getString(HSN).concat(", ").concat(rs.getString(REGIND)));
		}else if(rs.getString(HSN) !=null) {
			mailbagVO.setMailRemarks(rs.getString(HSN));
		}else {
			if (rs.getString(REGIND) != null) {
				mailbagVO.setMailRemarks(rs.getString(REGIND));
			}
		}
		if(rs.getString("PRVFLTNUM") !=null) {
		mailbagVO.setFromFightNumber(rs.getString("PRVFLTNUM"));
		}
		if(rs.getTimestamp("PRVFLTDAT") !=null) {
		mailbagVO.setFromFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("PRVFLTDAT")));
		}
		mailbagVO.setLcCount(rs.getInt("LCCOUNT"));
		mailbagVO.setCpCount(rs.getInt("CPCOUNT"));
		mailbagVO.setOthersCount(rs.getInt("OTHERSCOUNT"));
		mailbagVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		mailbagVO.setDestinatonPortCode(rs.getString("DSTPOACOD"));
		mailbagVO.setSealNumber(rs.getString("SELNUM"));
		if(rs.getString("POANAM") !=null) {
			mailbagVO.setPaDescription(rs.getString("POANAM"));
		}
	}

	private void populateContainerDetails(ResultSet rs, 
			ContainerDetailsVO containerDetailsVO) throws SQLException{
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setPou(rs.getString("POU")+", "+rs.getString("POUARPNAM"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setTransistPort(rs.getString("ASGPRT")+", "+rs.getString("TRANSARPNAM"));
	}

}
