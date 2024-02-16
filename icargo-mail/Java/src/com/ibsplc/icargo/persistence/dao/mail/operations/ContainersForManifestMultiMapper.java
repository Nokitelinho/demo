/*
 * ContainersForManifestMultiMapper.java Created on Feb 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-1936
 * 
 * 
 */
public class ContainersForManifestMultiMapper implements
		MultiMapper<ContainerDetailsVO> {

	private static final String BULKCONNUM ="BULKCONNUM" ;
	// The Logger
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	/**
	 * @param rs
	 * @return
	 * @throws SQLException
	 * @see com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper#map(java.sql.ResultSet)
	 */
	public List<ContainerDetailsVO> map(ResultSet rs) throws SQLException {
 		 List<ContainerDetailsVO> containerDetails = null;
 		 Map<String, ArrayList<String>> csgDocForDSN = new HashMap<String, ArrayList<String>>();
		 String currContainerKey = null;
		 String prevContainerKey = null;
		 ContainerDetailsVO containerDetailsVO = null;
		 DSNVO dsnVO = null;
		 Collection<DSNVO> dsnVOs = null;
		 String dsnKey = null;
		 String currDSNKey = null;
		 String prevDSNKey = null;
		 containerDetails = new ArrayList<ContainerDetailsVO>();
		 String route = null;
		 Collection<String> onwardRoutes= new ArrayList<String>();
		 String routeKey = null;

		while (rs.next()) {
			 currContainerKey = new StringBuilder().append(
					  rs.getString("CMPCOD")).append(rs.getInt("FLTCARIDR"))
					  .append(rs.getString("FLTNUM"))
					  .append(rs.getLong("FLTSEQNUM"))
					  .append(rs.getInt("SEGSERNUM"))
					  .append(rs.getString(BULKCONNUM))
					  .toString();

			route = rs.getString("ONWRTGSTR");
			if(route!=null){
				routeKey=currContainerKey+route;
			}
			
            //log.log(Log.FINE, "currContainerKey ", currContainerKey);
			if (MailConstantsVO.FLAG_NO.equals(rs.getString("ACPFLG"))
					&& MailConstantsVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
				continue;
			}

			/*
			 * To avoid bulks added at the arrival port from appearing in the
			 * outbound manifest. Check for bulk and whether or not the accepted
			 * bags are greater than 0. Accepted bags for ULDs can be zero
			 * because it is possible to accept empty ULDs to the flight. Also
			 * ULDs appearing in manifest can be controlled byu the
			 * accepted/arrival flags. But since Bulks added at arrival donot
			 * have entryin the CONMST, the flag s/m cannot be used for them
			 */

			if (rs.getString(BULKCONNUM) != null && rs.getString(BULKCONNUM).startsWith(MailConstantsVO.CONST_BULK)
					&& rs.getInt("ACPBAG") == 0) {
				continue;
			}

			if(!currContainerKey.equals(prevContainerKey)) {
				containerDetailsVO = new ContainerDetailsVO();
				populateContainerDetails(containerDetailsVO, rs);
				if (routeKey != null && !(onwardRoutes.contains(routeKey))) {
					containerDetailsVO.setRoute(rs.getString("ONWRTGSTR"));
					onwardRoutes.add(routeKey);
				}
				dsnVOs = new ArrayList<DSNVO>();
				containerDetailsVO.setDsnVOs(dsnVOs);
				containerDetails.add(containerDetailsVO);
				prevContainerKey = currContainerKey;
			} else {
				if (routeKey != null && !(onwardRoutes.contains(routeKey))) {
					if (containerDetailsVO.getRoute() != null) {
						containerDetailsVO.setRoute(containerDetailsVO
								.getRoute().concat(",").concat(route));
						onwardRoutes.add(routeKey);
					}

				}
			}
			dsnKey = new StringBuilder().append(rs.getString("DSN"))
					.append(rs.getString("ORGEXGOFC"))
					.append(rs.getString("DSTEXGOFC")).append(
							rs.getString("MALSUBCLS")).append(
							rs.getString("MALCTGCOD")).append(rs.getInt("YER"))
					.toString();
			currDSNKey = new StringBuilder().append(currContainerKey)
					.append(dsnKey).toString();

			//log.log(Log.FINE, "CurrDSNKey ", currDSNKey);
			if (rs.getString("DSN") != null && rs.getInt("ACPBAG") > 0) {
				if(dsnKey != null && dsnKey.trim().length() > 0 && 
						!(csgDocForDSN.containsKey(dsnKey))){
					ArrayList<String> csgDetails = new ArrayList<String>();
					if((rs.getString("CSGDOCNUM") != null && 
							rs.getString("CSGDOCNUM").length() > 0) &&
							(rs.getString("POACOD")!=null && 
									rs.getString("POACOD").length() > 0)){
						csgDetails.add(rs.getString("CSGDOCNUM"));
						csgDetails.add(rs.getString("POACOD"));
						//CONSTRUCTING THE MAP FOR DSN & CSG DETAILS
						csgDocForDSN.put(dsnKey, csgDetails);
					}
				}
				if (!currDSNKey.equals(prevDSNKey)) {
					dsnVO = new DSNVO();
					populateDSNDetails(dsnVO, rs);
					dsnVO.setContainerType(containerDetailsVO
							.getContainerType());
					dsnVOs.add(dsnVO);
					prevDSNKey = currDSNKey;
				} else {
					if (rs.getString(BULKCONNUM) != null) {
						dsnVO.getDsnContainers()
								.add(rs.getString(BULKCONNUM));
					}
					dsnVO.setBags(dsnVO.getBags()+rs.getInt("ACPBAG"));
					//dsnVO.setWeight(dsnVO.getWeight()+rs.getDouble("ACPWGT"));
					try {
						dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(),new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT"))));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						log.log(Log.SEVERE,"UnitException",e.getMessage());
					}//added by A-7371
					if(rs.getString("CSGDOCNUM") != null && 
							rs.getString("CSGDOCNUM").length() > 0){
						dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
					}
					if(rs.getString("POACOD")!=null && 
							rs.getString("POACOD").length() > 0){
						dsnVO.setPaCode(rs.getString("POACOD"));
					}
				}

			}
		}
		//SUGGESTING CONSIGNMENT DOC NUMBER AND POACOD FOR SIMILAR DSNS
		//STARTS
		autoSuggestConsignmentForDSN(containerDetails,csgDocForDSN);
		//END
		return containerDetails;
	}
	
	/**
	 * @param containerDetails
	 * @param csgDocForDSN
	 */
	private void autoSuggestConsignmentForDSN(List<ContainerDetailsVO> containerDetails,
			Map<String, ArrayList<String>> csgDocForDSN ){
		if(containerDetails != null){
			String dsnKey = null;
			for(ContainerDetailsVO cntDetails : containerDetails){
				for(DSNVO dsnVO : cntDetails.getDsnVOs()){
					if(dsnVO.getCsgDocNum() == null || 
							(dsnVO.getCsgDocNum() != null && dsnVO.getCsgDocNum().length() == 0)){
						dsnKey = new StringBuilder().append(dsnVO.getDsn())
						.append(dsnVO.getOriginExchangeOffice())
						.append(dsnVO.getDestinationExchangeOffice())
						.append(dsnVO.getMailSubclass())
						.append(dsnVO.getMailCategoryCode())
						.append(String.valueOf(dsnVO.getYear()))
						.toString();
						if(csgDocForDSN != null && 
								csgDocForDSN.size() > 0 && 
								csgDocForDSN.containsKey(dsnKey)){
							ArrayList<String> csgDetails = csgDocForDSN.get(dsnKey);
							if(csgDetails != null){
								dsnVO.setCsgDocNum(csgDetails.get(0));
								dsnVO.setPaCode(csgDetails.get(1));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * A-1739
	 * 
	 * @param dsnVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs)
			throws SQLException {
		String categoryCode = null;
		dsnVO.setDsn(rs.getString("DSN"));
		dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		dsnVO.setDestinationExchangeOffice(rs.getString("DSTEXGOFC"));
		//Added for bug ICRD-15418
		dsnVO.setOrigin(rs.getString("ORGCOD"));
		dsnVO.setDestination(rs.getString("DSTCOD"));
		// Added to include the DSN PK..
		
		dsnVO.setMailClass(rs.getString("MALCLS"));
		dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		categoryCode = rs.getString("MALCTGCOD");
//		for(int cls=0;cls < MailConstantsVO.MILITARY_CLASS.length;cls++){
//			if(MailConstantsVO.MILITARY_CLASS[cls].equals(rs.getString("MALCLS"))){
//				categoryCode = "M";
//			}
//		}
		dsnVO.setMailCategoryCode(categoryCode);
		dsnVO.setYear(rs.getInt("YER"));
		dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		dsnVO.setBags(rs.getInt("ACPBAG"));
		//dsnVO.setWeight(rs.getDouble("ACPWGT"));
		dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("ACPWGT")));//added by A-7371
		// AWB
		dsnVO.setDocumentOwnerCode(rs.getString("DOCOWRCOD"));
		dsnVO.setDocumentOwnerIdentifier(rs.getInt("DOCOWRIDR"));
		dsnVO.setDuplicateNumber(rs.getInt("DUPNUM"));
		dsnVO.setSequenceNumber(rs.getInt("SEQNUM"));
		dsnVO.setMasterDocumentNumber(rs.getString("MSTDOCNUM"));
		
		//change for bug 50584
		dsnVO.setCsgDocNum(rs.getString("CSGDOCNUM"));
		//added for cr ICRD-2878
		dsnVO.setCsgSeqNum(rs.getInt("CSGSEQNUM"));
		if(rs.getDate("CSGDAT") != null){
		dsnVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CSGDAT")));
		}
		if(rs.getDate("ACPDAT") != null){
			dsnVO.setAcceptedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("ACPDAT")));
		}
		dsnVO.setRemarks(rs.getString("REMARKS"));
		dsnVO.setPaCode(rs.getString("POACOD"));
		
		if (rs.getTimestamp("DSNULDSEGTIM") != null) {
			dsnVO.setDsnUldSegLastUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs
							.getTimestamp("DSNULDSEGTIM")));
		}

		dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		dsnVO.setDsnContainers(new ArrayList<String>());
		if (rs.getString("CONNUM") != null) {
			dsnVO.getDsnContainers().add(rs.getString("CONNUM"));
			dsnVO.setContainerNumber(rs.getString("CONNUM"));
		}
		if(rs.getString("RTGAVL")!=null && "Y".equals(rs.getString("RTGAVL"))){
			dsnVO.setRoutingAvl(rs.getString("RTGAVL"));
		}

	}

	/**
	 * A-1739
	 * 
	 * @param containerDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO, ResultSet rs)
			throws SQLException {

		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setPou(rs.getString("POU"));
		containerDetailsVO.setPol(rs.getString("POL"));
		/*
		 * Added By Karthick V to include the Destination Code that has to be
		 * shown in the User Interface
		 */
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setContainerNumber(rs.getString(BULKCONNUM));
		containerDetailsVO.setTotalBags(rs.getInt("BAGCNT"));
		//containerDetailsVO.setTotalWeight(rs.getDouble("BAGWGT"));
		containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("BAGWGT")));//added by A-7371
		if (rs.getString(BULKCONNUM) != null && !rs.getString(BULKCONNUM).startsWith(MailConstantsVO.CONST_BULK)) {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		}
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));

	}

}
