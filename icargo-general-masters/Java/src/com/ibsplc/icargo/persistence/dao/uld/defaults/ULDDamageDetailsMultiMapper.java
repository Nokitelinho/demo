/*
 * ULDDamageDetailsMultiMapper.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1347
 *
 */

/**
 * This class implements a Mapper<<ULDVO>
 * @author A-1347
 *
 */
public class ULDDamageDetailsMultiMapper implements MultiMapper<ULDDamageRepairDetailsVO> {
	
	private Log log = LogFactory.getLogger("ULD");

    /**
     *
     * @param rs
     * @return List<ULDDamageVO>
     * @throws SQLException
     */
	public List<ULDDamageRepairDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering("ULDDamageDetailsMultiMapper","list");
		String presId = null;
		String prevId = null;
		StringBuilder sbd = null ; 
		
		List<ULDDamageRepairDetailsVO> uldDamageRepairDetailsVOs = 
				new ArrayList<ULDDamageRepairDetailsVO>();
		Collection<ULDDamageVO> damageVOs = null;
		Set<ULDDamageVO> sortedDamageVOs = null;
		ULDDamageVO uldDamageVO = null;
		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = null;
		Collection<ULDRepairVO> uldRepairVOs = null;
		ULDRepairVO uldRepairVO = null;
		Collection<Integer> dmgseqnum = new ArrayList<Integer>();
		Collection<Integer> repairSeqNum = new ArrayList<Integer>();
		
		while(rs.next()){
			sbd = new StringBuilder();
			presId = sbd.append(rs.getString("CMPCOD")).append(rs.getString("ULDNUM")).toString();
			if(!presId.equals(prevId) && presId != null){					
				uldDamageRepairDetailsVO = new ULDDamageRepairDetailsVO();
				uldDamageRepairDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
				uldDamageRepairDetailsVO.setDamageStatus(rs.getString("DMGSTA"));
				uldDamageRepairDetailsVO.setOverallStatus(rs.getString("OVLSTA"));
				uldDamageRepairDetailsVO.setSupervisor(rs.getString("SPR"));
				uldDamageRepairDetailsVO.setInvestigationReport(rs.getString("INVRPT"));
				uldDamageRepairDetailsVO.setRepairStatus(rs.getString("RPRSTA"));
				uldDamageRepairDetailsVO.setLastUpdatedUser(rs.getString("MSTLSTUPDUSR"));
				uldDamageRepairDetailsVO.setImageSequenceNumber(rs.getInt("IMGSEQNUM"));
				if(rs.getTimestamp("MSTLSTUPDTIM") != null){
					uldDamageRepairDetailsVO.setLastUpdatedTime(
							new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("MSTLSTUPDTIM")));
				}
				//uldDamageRepairDetailsVO.setPicture(rs.getBytes(""));
				
				damageVOs = new HashSet<ULDDamageVO>();
				uldRepairVOs = new HashSet<ULDRepairVO>();
				prevId = presId;
			}
			if(rs.getInt("DMGSEQNUM") != 0 && presId != null){
				if(!dmgseqnum.contains(rs.getInt("DMGSEQNUM"))){
					dmgseqnum.add(rs.getInt("DMGSEQNUM"));	
					uldDamageVO = new ULDDamageVO();	
					
					//uldDamageVO.setDamageCode(rs.getString("DMGCOD"));
					uldDamageVO.setPosition(rs.getString("DMGPOS"));
					//Start: Added by A-7636 as part of ICRD-245031
					uldDamageVO.setImageCount(rs.getString("IMGCNT"));
					//End: Added by A-7636 as part of ICRD-245031
					uldDamageVO.setRemarks(rs.getString("RMK"));
					uldDamageVO.setSeverity(rs.getString("DMGSVT"));
					uldDamageVO.setDamageReferenceNumber(rs.getInt("DMGREFNUM"));
					uldDamageVO.setReportedStation(rs.getString("RPTARP"));
					uldDamageVO.setSequenceNumber(rs.getInt("DMGSEQNUM"));
					uldDamageVO.setLastUpdateUser(rs.getString("DMGLSTUPDUSR"));
					if(rs.getString("DMGSEC")!=null){
					uldDamageVO.setSection(rs.getString("DMGSEC"));
					}
					if(rs.getString("DMGDES")!=null){
						uldDamageVO.setDamageDescription(rs.getString("DMGDES"));
					}
					if(rs.getString("DMGPNT")!=null){
						uldDamageVO.setDamagePoints(rs.getString("DMGPNT"));
					}
					if(rs.getTimestamp("DMGLSTUPDTIM") != null){
						uldDamageVO.setLastUpdateTime(
								new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("DMGLSTUPDTIM")));
					}
					Date dat = rs.getDate("CLSDAT");
					if(dat != null && uldDamageVO.getReportedStation() == null){
						uldDamageVO.setRepairDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,dat));
						uldDamageVO.setClosed(true);
					}else if(dat != null && uldDamageVO.getReportedStation() != null){
						uldDamageVO.setRepairDate(new LocalDate(uldDamageVO.getReportedStation(),
																					Location.ARP,dat));
					//	uldDamageVO.setClosed(true);
					}
					Date datTwo = rs.getDate("DMGRPTDAT");
					if(datTwo != null  && uldDamageVO.getReportedStation() == null){
						uldDamageVO.setReportedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("DMGRPTDAT")));
					}else if(datTwo != null  && uldDamageVO.getReportedStation() != null){
						uldDamageVO.setReportedDate(new LocalDate(uldDamageVO.getReportedStation()
																				,Location.ARP,rs.getTimestamp("DMGRPTDAT")));
					}
					if("Y".equalsIgnoreCase(rs.getString("PICFLG"))){
						uldDamageVO.setPicturePresent(true);
					}else{
						uldDamageVO.setPicturePresent(false);
					}
					uldDamageVO.setParty(rs.getString("PTYCOD"));
					uldDamageVO.setPartyType(rs.getString("PTYTYP"));
					if(uldDamageVO.getPartyType()!=null){
						
					
					if(("A").equals(uldDamageVO.getPartyType())){
						uldDamageVO.setAbrPartyType("Airline");
					}else if(("G").equals(uldDamageVO.getPartyType())){
						uldDamageVO.setAbrPartyType("Agent");
					}else if(("O").equals(uldDamageVO.getPartyType())){
						uldDamageVO.setAbrPartyType("Other");
					}
					}
					uldDamageVO.setLocation(rs.getString("LOC"));
					uldDamageVO.setFacilityType(rs.getString("FACTYP"));
					//Added by A-8368 as part of userStory -   IASCB-35533
					uldDamageVO.setDamageNoticePoint(rs.getString("DMGNOTPNT"));
					if(rs.getDate("CLSDAT") != null ){
						uldDamageVO.setUldStatus("YES");
						uldDamageVO.setClosed(true);
					}else{
						uldDamageVO.setUldStatus("NO");
						uldDamageVO.setClosed(false);
					}
					damageVOs.add(uldDamageVO);
				}
			}	
			if(rs.getInt("RPRDMGREFNUM") != 0 && presId != null){
				if(!repairSeqNum.contains(rs.getInt("RPRSEQNUM"))){
					repairSeqNum.add(rs.getInt("RPRSEQNUM"));	
					uldRepairVO = new ULDRepairVO();
					uldRepairVO.setRepairHead(rs.getString("RPRHED"));
					uldRepairVO.setRepairStation(rs.getString("RPRARP"));
					Date dat = rs.getDate("RPRDAT");
					//Modified by A-7359 for ICRD-248447 starts here 
					if(dat != null && uldRepairVO.getRepairStation() == null){
						uldRepairVO.setRepairDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("RPRDAT")));
					}else if(dat != null && uldRepairVO.getRepairStation() != null){
						uldRepairVO.setRepairDate(new LocalDate(uldRepairVO.getRepairStation(),
																					Location.ARP,rs.getTimestamp("RPRDAT")));
					}
					//Modified by A-7359 for ICRD-248447 ends here 
					uldRepairVO.setRepairSequenceNumber(rs.getInt("RPRSEQNUM"));
					uldRepairVO.setInvoiceReferenceNumber(rs.getString("RPRDMGREFNUM"));
					//Added by A-7359 for ICRD-248447 starts here 
					uldRepairVO.setDamageReferenceNumber(rs.getInt("DMGREFNUM"));
					uldRepairVO.setDisplayAmount(rs.getDouble("RPRAMT"));
					//Added by A-7359 for ICRD-248447 ends here 
					uldRepairVO.setAmount(rs.getDouble("RPRAMT"));
					uldRepairVO.setCurrency(rs.getString("DISRPRCUR"));
					uldRepairVO.setRemarks(rs.getString("RPRRMK"));
					uldRepairVOs.add(uldRepairVO);
				}
				
			}
		}
		if(damageVOs != null && damageVOs.size() > 0) {
			sortedDamageVOs = new TreeSet<ULDDamageVO> (new Comparator<ULDDamageVO>() {
				public int compare(ULDDamageVO obj1, ULDDamageVO obj2) {
					if(obj1.getSequenceNumber() > 0 && obj2.getSequenceNumber() > 0) {
						return (int) (obj1.getSequenceNumber() - obj2.getSequenceNumber());
					}
					return 0;
				}
			});
			sortedDamageVOs.addAll(damageVOs);
		}
		if(uldDamageRepairDetailsVO != null){
			log.log(Log.INFO, "!!!!!!!uldDamageRepairDetailsVO",
					uldDamageRepairDetailsVO);
			if(damageVOs != null && damageVOs.size() > 0){
				uldDamageRepairDetailsVO.setUldDamageVOs(new ArrayList<ULDDamageVO>(sortedDamageVOs));
			}
			if(uldRepairVOs != null && uldRepairVOs.size() > 0){
				uldDamageRepairDetailsVO.setUldRepairVOs(new ArrayList<ULDRepairVO>(uldRepairVOs));
			}
			uldDamageRepairDetailsVOs.add(uldDamageRepairDetailsVO);
		}
		log.log(Log.INFO, "!!!uldDamageRepairDetailsVOs",
				uldDamageRepairDetailsVOs);
		return uldDamageRepairDetailsVOs;
	}
}
