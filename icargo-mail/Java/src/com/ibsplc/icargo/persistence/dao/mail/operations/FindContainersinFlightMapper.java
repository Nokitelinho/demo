package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class FindContainersinFlightMapper implements MultiMapper<ContainerDetailsVO> {

	 private Log log= LogFactory.getLogger("MAILOUTBOUND_DEFAULTS");
	 private static final String CLASS_NAME = "FindFlightDetailsMapper";
     
	 /**
	  * The Map method that can be used to construct the List containing the 
	  * ContainerDetailsVo
	  * @author a-1936
	  * @param rs
	  * @return
	  * @throws SQLException
	  */
	  @Override
	  public  List<ContainerDetailsVO>  map(ResultSet rs) throws SQLException {
		  List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		  ContainerDetailsVO containerDetailsVO = null; 
		  String currULDKey = "";
		  String prevULDKey = "";
		  String currOnwardKey = "";
		  String prevOnwardKey = "";
		  String pou = null;
		  String flightNumber = null;
		  String carrierCode = null;
		  StringBuilder onwardFlightBuilder = null;
		  String currDSNKey = null;
		  String prevDSNKey = null;
		  Collection<OnwardRouteForSegmentVO> onwardRoutingVos = null;
		  MailbagVO mailbagVO = null;
		  Collection<MailbagVO> mailbagsVOs = null;
		  
		  while(rs.next()){
			
			 currULDKey = new StringBuilder(rs.getString("CONNUM")).toString();
			 log.log(Log.FINE, "The NEW ParentID is Found to be ", currULDKey);
			
			if (MailConstantsVO.FLAG_NO.equals(rs.getString("ACPFLG"))
					&& MailConstantsVO.FLAG_YES.equals(rs.getString("ARRSTA"))) {
				continue;
			}
			
			if(currULDKey.startsWith(MailConstantsVO.BULK_TRASH)){
				continue;
			}
		 	if(!currULDKey.equals(prevULDKey)) {
				  containerDetailsVO = new ContainerDetailsVO();
				  populateContainerDetails(containerDetailsVO, rs);
				  containerDetailsVOsList.add(containerDetailsVO);
				  prevULDKey = currULDKey;
				  onwardRoutingVos = new ArrayList<OnwardRouteForSegmentVO>();
				  onwardFlightBuilder = new StringBuilder();
				  mailbagsVOs = new ArrayList<MailbagVO>();				
				  containerDetailsVO.setOnwardRoutingForSegmentVOs(onwardRoutingVos);
				  containerDetailsVO.setMailDetails(mailbagsVOs);
			}
			/**
			 * 
			 *  For each RoutingSerialNum create a String like EK
			 *  1303-30/6/2006-DXB,EK 1305-30/6/2006-TRV (FLTNUM-FLTDAT-POU)
			 *  Collect all  the Routings for the Segment...
			 */
			if(rs.getInt("RTGSERNUM") > 0) {
				 currOnwardKey = new StringBuffer(currULDKey).append(
						rs.getString("RTGSERNUM")).toString();
				if(!currOnwardKey.equals(prevOnwardKey)) {
					flightNumber = rs.getString("ONWFLTNUM");
					pou = rs.getString("RTGPOU");
					carrierCode = rs.getString("ONWFLTCARCOD");

					OnwardRouteForSegmentVO onwardRouteForSegmentVO = new OnwardRouteForSegmentVO();
					onwardRouteForSegmentVO.setOnwardCarrierCode(carrierCode);
					onwardRouteForSegmentVO.setOnwardCarrierId(rs
							.getInt("ONWFLTCARIDR"));

					if (rs.getString("ONWFLTDAT") != null) {
						LocalDate localdate = new LocalDate(rs
								.getString("ASGPRT"), Location.ARP, false);
						localdate.setDate(rs.getString("ONWFLTDAT"));

						onwardRouteForSegmentVO.setOnwardFlightDate(localdate);
					}
					onwardRouteForSegmentVO.setOnwardFlightNumber(flightNumber);
					onwardRouteForSegmentVO.setPou(pou);
					onwardRouteForSegmentVO.setRoutingSerialNumber(rs
							.getInt("RTGSERNUM"));

					onwardRoutingVos.add(onwardRouteForSegmentVO);
					/*
					 * iF FlightNumber,FlightDate ,Pou exists For each
					 * RoutingSerialNum create a String like EK
					 * 1303-30/6/2006-DXB
					 */
					if (rs.getString("ONWFLTDAT") != null
							&& flightNumber != null
							&& flightNumber.trim().length() > 0
							&& carrierCode != null
							&& carrierCode.trim().length() > 0 && pou != null
							&& pou.trim().length() > 0) {

						if(onwardFlightBuilder.length() > 0) {
							onwardFlightBuilder.append(", ");
						}
						
						onwardFlightBuilder.append(carrierCode).append("-")
								.append(flightNumber).append("-").append(
										rs.getString("ONWFLTDAT")).append("-")
								.append(pou);
						
						containerDetailsVO.setRoute(onwardFlightBuilder.toString());
					}
					prevOnwardKey = currOnwardKey;
				}
			}
			String dsn = rs.getString("DSN");
			 /*
			  * 
			  * avoid all dsns which are having 0 accepted bags, ie., they are
			  * newly arrived only. but not accepted since there is no need to display the
			  * DSNS which are newly Arrived as they are not actually not Accepted at 
			  * the Particular Port 
			  */
			if(dsn != null && (rs.getInt("DSNACPBAG") > 0)) {
				 currDSNKey = new StringBuilder().
				   append(currULDKey)
				   .append(rs.getString("DSN"))
				   .append(rs.getString("ORGEXGOFC"))
				   .append(rs.getString("DSTEXGOFC")).append(
				   rs.getString("MALSUBCLS"))
				   .append(rs.getString("MALCTGCOD"))
				   .append(rs.getInt("YER")).toString();
				 log.log(Log.FINE, "currDSNKey ", currDSNKey);
				if(!currDSNKey.equals(prevDSNKey)) {
					mailbagVO = new MailbagVO();
					populateMailBagDetails(mailbagVO, rs,containerDetailsVO);
					mailbagsVOs.add(mailbagVO);
					prevDSNKey = currDSNKey;
				 }else{
					
				 }
			}
		}
		return containerDetailsVOsList;
	}
	  
	  private void populateContainerDetails(
				ContainerDetailsVO containerDetailsVO,ResultSet rs)
				throws SQLException {
		     String assignedPort = rs.getString("ASGPRT");
			 containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
			 containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
			 containerDetailsVO.setPou(rs.getString("POU"));
			 containerDetailsVO.setAssignedPort(assignedPort);
			 containerDetailsVO.setPol(rs.getString("ASGPRT"));
			 containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
			 containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
			 containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			 if (rs.getDate("FLTDAT") != null) {
				  containerDetailsVO.setFlightDate(new LocalDate(assignedPort, 
		            Location.ARP, rs.getDate("FLTDAT")));
		     }
			 containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			 containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			 containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			 containerDetailsVO.setLocation(rs.getString("LOCCOD"));
			 containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
			 containerDetailsVO.setRemarks(rs.getString("RMK"));
			  containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));
			  //containerDetailsVO.setTotalWeight(rs.getDouble("ULDBAGWGT"));
			  containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));//added by A-7371
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
			containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
			containerDetailsVO.setContainerType(rs.getString("CONTYP"));
			containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
			containerDetailsVO.setTransferFromCarrier(rs.getString("ULDFRMCARCOD"));
			containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
			containerDetailsVO.setPaCode(rs.getString("POACOD"));
			containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
			containerDetailsVO.setActualWeight(new Measure(UnitConstants.MAIL_WGT, rs.getDouble("ACTULDWGT")));
			containerDetailsVO.setUldFulIndFlag(rs.getString("ULDFULIND"));
			containerDetailsVO.setTransactionCode(rs.getString("TXNCOD")); 
			containerDetailsVO.setUldReferenceNo(rs.getLong("ULDREFNUM"));
			if(rs.getString("CNTIDR")!=null){
			    containerDetailsVO.setContentId(rs.getString("CNTIDR"));
			 }
			Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
			if(lstUpdateTime != null) {
				containerDetailsVO.setLastUpdateTime(
					new LocalDate(LocalDate.NO_STATION, Location.NONE,
						lstUpdateTime));
			}
			Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
			if(uldLastUpdateTime != null) {
				containerDetailsVO.setUldLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE,
							uldLastUpdateTime));
			}
			
			 if (rs.getTimestamp("ASGDATUTC") != null) {
				 containerDetailsVO.setAssignedDate(new LocalDate(assignedPort, Location.ARP, rs.getTimestamp("ASGDATUTC")));
			 }
			 containerDetailsVO.setAssignedUser(rs.getString("USRCOD"));
			
			    Timestamp uldLastUpdTim = rs.getTimestamp("ULDLSTUPDTIM");
			    if (uldLastUpdTim != null) {
			    	containerDetailsVO.setUldLastUpdateTime(new LocalDate("***",  Location.NONE, uldLastUpdTim));
			    }
			    if(rs.getTimestamp("REQDLVTIM")!=null) {
			    containerDetailsVO.setMinReqDelveryTime(new LocalDate(assignedPort, Location.ARP, rs.getTimestamp("REQDLVTIM")));
			    }
			    Money amount;		 	 
				 try {
						amount = CurrencyHelper.getMoney(rs.getString("BASCURCOD"));
						amount.setAmount(rs.getDouble("PROCHG"));   
						containerDetailsVO.setProvosionalCharge(amount);						
					} catch (CurrencyException e) {
						log.log(Log.INFO, e);
					}
				 if(containerDetailsVO.getTotalBags()!=rs.getInt("RATEDCOUNT")) {
					 containerDetailsVO.setRateAvailforallMailbags("N"); 
				 }
				 else {
					 containerDetailsVO.setRateAvailforallMailbags("Y");  
				 }
				 containerDetailsVO.setBaseCurrency(rs.getString("BASCURCOD"));
	       
		}

	  private void populateMailBagDetails(MailbagVO mailVO, ResultSet rs,ContainerDetailsVO containerDetailsVo)
				throws SQLException {
		  mailVO.setCompanyCode(rs.getString("CMPCOD"));
		  mailVO.setDespatchSerialNumber(rs.getString("DSN"));
		  mailVO.setOoe(rs.getString("ORGEXGOFC"));
		  mailVO.setDoe(rs.getString("DSTEXGOFC"));
		  mailVO.setMailClass(rs.getString("MALCLS"));
		  mailVO.setMailSubclass(rs.getString("MALSUBCLS"));
		  mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		  mailVO.setYear(rs.getInt("YER"));
		  mailVO.setMailbagId(rs.getString("MALIDR"));
		  mailVO.setVolume(new Measure(UnitConstants.VOLUME,rs.getDouble("VOL")));
		  mailVO.setConsignmentNumber(rs.getString("CSGDOCNUM"));
		  mailVO.setSealNumber(rs.getString("SELNUM"));
		  mailVO.setDamageFlag(rs.getString("DMGFLG"));
		  mailVO.setShipmentPrefix(rs.getString("SHPPFX"));
		  mailVO.setDocumentNumber(rs.getString("MSTDOCNUM"));
		
			
		}

}
