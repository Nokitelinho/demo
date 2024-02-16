/*
 * CarrierAcceptanceDetailsMultimapper.java Created on May 29, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRouteForSegmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1936
 * This class is used to map the ResultSet into Vos...
 */
public class CarrierAcceptanceDetailsMultimapper implements MultiMapper<ContainerDetailsVO> {

	private Log log= LogFactory.getLogger("MAILTRACKING_DEFAULTS");
 
	/**
	 * The Map method that can be used to construct the List containing the 
	 * ContainerDetailsVo
	 * @author a-1936
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public List<ContainerDetailsVO>  map(ResultSet rs) throws SQLException {
		 List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<ContainerDetailsVO>();
		 ContainerDetailsVO containerDetailsVO = null; 
		 String currULDKey = "";
		 String prevULDKey = "";
		 String currDSNKey = null;
		 String prevDSNKey = null;
		 Collection<OnwardRouteForSegmentVO> onwardRoutingVos = null;
		 DSNVO dsnVO = null;
		 Collection<DSNVO> dsnVOs = null;
		
		 while (rs.next()){
			 /*
			  * Added By Karthick V 
			  * Only the Container Number is part of the Parent Pk 
			  * since  the Container Number cannot repeat for the 
			  * Particular Origin -Destination Pair... 
			  */
			 currULDKey = new StringBuilder(rs.getString("CONNUM")).toString();
			 log.log(Log.FINE, "The NEW ParentID is Found to be ", currULDKey);
			/*
			   * First check if  the container is a Trash Container 
			   */
			 if(currULDKey.startsWith(MailConstantsVO.BULK_TRASH)){
				 continue;
			 }

			 if(!currULDKey.equals(prevULDKey)) {
				  containerDetailsVO = new ContainerDetailsVO();
				  populateContainerDetails(containerDetailsVO, rs);
				  containerDetailsVOsList.add(containerDetailsVO);
				  prevULDKey = currULDKey;
				  onwardRoutingVos = new ArrayList<OnwardRouteForSegmentVO>();
				  dsnVOs = new ArrayList<DSNVO>();				
				  prevDSNKey = null;				
				  containerDetailsVO.setOnwardRoutingForSegmentVOs(onwardRoutingVos);
				  containerDetailsVO.setDsnVOs(dsnVOs);
			 }
			String dsn = rs.getString("DSN");
			/*
			 * 
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
					 dsnVO = new DSNVO();
					 populateDSNDetails(dsnVO, rs,containerDetailsVO);
					 dsnVOs.add(dsnVO);
					 prevDSNKey = currDSNKey;
				  }        else
			        {
			          if (dsnVO != null) {
			            dsnVO.setBags(dsnVO.getBags() + rs.getInt("DSNACPBAG"));
			            dsnVO.setPrevBagCount(dsnVO.getPrevBagCount() + rs.getInt("DSNACPBAG"));
			           // dsnVO.setWeight(dsnVO.getWeight() + rs.getDouble("DSNACPWGT"));
			            try {
							dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
						}//added by A-7371
			           // dsnVO.setPrevBagWeight(dsnVO.getPrevBagWeight() + rs.getDouble("DSNACPWGT"));
			            try {
							dsnVO.setPrevBagWeight(Measure.addMeasureValues(dsnVO.getPrevBagWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
						}//added by A-7371
			            dsnVO.setStatedBags(dsnVO.getStatedBags() + rs.getInt("DSNSTDBAG"));
			            dsnVO.setPrevStatedBags(dsnVO.getPrevStatedBags() + rs.getInt("DSNSTDBAG"));
			            //dsnVO.setStatedWeight(dsnVO.getStatedWeight() + rs.getDouble("DSNSTDWGT"));
			            try {
							dsnVO.setStatedWeight(Measure.addMeasureValues(dsnVO.getStatedWeight(),  new Measure(UnitConstants.MAIL_WGT,rs.getInt("DSNSTDBAG"))));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
						}//added by A-7371
			            //dsnVO.setPrevStatedWeight(dsnVO.getPrevStatedWeight() + rs.getDouble("DSNSTDWGT"));
			            try {
							dsnVO.setPrevStatedWeight(Measure.addMeasureValues(dsnVO.getPrevStatedWeight(), new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT"))));
						} catch (UnitException e) {
							// TODO Auto-generated catch block
							log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
						}//added by A-7371
			          }
			          containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + rs.getInt("DSNACPBAG"));
			         // containerDetailsVO.setTotalWeight(containerDetailsVO.getTotalWeight() + rs.getDouble("DSNACPWGT"));
			          try {
						containerDetailsVO.setTotalWeight(Measure.addMeasureValues(containerDetailsVO.getTotalWeight(),  new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT"))));
					} catch (UnitException e) {
						// TODO Auto-generated catch block
						log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
					}//added by A-7371
				  }
			}
		 }
		return containerDetailsVOsList;
	}

	/**
	 * 
	 * @param dsnVO
	 * @param rs
	 * @param containerDetailsVo
	 * @throws SQLException
	 */
	private void populateDSNDetails(DSNVO dsnVO, ResultSet rs,ContainerDetailsVO containerDetailsVo)
			throws SQLException {
		   dsnVO.setCompanyCode(rs.getString("CMPCOD"));
		   dsnVO.setDsn(rs.getString("DSN"));
		   dsnVO.setOriginExchangeOffice(rs.getString("ORGEXGOFC"));
		   dsnVO.setDestinationExchangeOffice(rs
				.getString("DSTEXGOFC"));
		   dsnVO.setMailClass(rs.getString("MALCLS"));
		   //Added to include the DSN PK 
		   dsnVO.setMailSubclass(rs.getString("MALSUBCLS"));
		   dsnVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
		   dsnVO.setYear(rs.getInt("YER"));
		   dsnVO.setBags(rs.getInt("DSNACPBAG"));
		   dsnVO.setPrevBagCount(rs.getInt("DSNACPBAG"));
		   //dsnVO.setWeight(rs.getDouble("DSNACPWGT"));
		   dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));//added by A-7371
		   //dsnVO.setPrevBagWeight(rs.getDouble("DSNACPWGT"));
		   dsnVO.setPrevBagWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNACPWGT")));//added by A-7371
		   dsnVO.setStatedBags(rs.getInt("DSNSTDBAG"));
		   dsnVO.setPrevStatedBags(rs.getInt("DSNSTDBAG"));
		   //dsnVO.setStatedWeight(rs.getDouble("DSNSTDWGT"));
		   dsnVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT")));//added by A-7371
		   //dsnVO.setPrevStatedWeight(rs.getDouble("DSNSTDWGT"));
		   dsnVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("DSNSTDWGT")));//added by A-7371
		   dsnVO.setPltEnableFlag(rs.getString("PLTENBFLG"));
		   //Added By Karthick To sum the Accepted Bags and Weights of the Container..
			  /*
			  * In case of the uld the Bag Count and the Bag Weight is fetched directly from the 
			  * MTKULDSEG ,In case of BULK say the 
			  * Containers Total Bag counts must be summation of all the DSNs acpBags and AcpWgt to the 
			  * Container ...Table (MTKDSNCONSEG)
			  * 
			  * 
			  */
		   /*
		    * Below Code Commented for MTK411
		    * For carrier even if there is mailbags n weight , container level - both displayed as zero
		    * This bug occurs only when 1 mailbag is accepted and then listed.
		    */
		 //if(MailConstantsVO.BULK_TYPE.equals(rs.getString("CONTYP"))){
		   containerDetailsVo.setTotalBags(containerDetailsVo.getTotalBags()+dsnVO.getBags());
		   //containerDetailsVo.setTotalWeight(containerDetailsVo.getTotalWeight()+dsnVO.getWeight());
		   try {
			containerDetailsVo.setTotalWeight(Measure.addMeasureValues(containerDetailsVo.getTotalWeight(), dsnVO.getWeight()));
		} catch (UnitException e) {
			// TODO Auto-generated catch block
			log.log(Log.FINE,"UnitException",e.getMessage());//added by A-7371
		}//added by A-7371
		// }
	}

	/**
	 * A-1936
	 * 
	 * @param containerDetailsVO
	 * @param rs
	 * @throws SQLException
	 */
	private void populateContainerDetails(
			ContainerDetailsVO containerDetailsVO,ResultSet rs)
			throws SQLException {
		containerDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		containerDetailsVO.setContainerNumber(rs.getString("CONNUM"));
		containerDetailsVO.setPol(rs.getString("ASGPRT"));
		containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
		containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
		containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
		containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
		containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
		containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
		containerDetailsVO.setLocation(rs.getString("LOCCOD"));
		containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
		containerDetailsVO.setRemarks(rs.getString("RMK"));
		containerDetailsVO.setContainerJnyId(rs.getString("CONJRNIDR"));
		containerDetailsVO.setPaCode(rs.getString("POACOD"));
		/*
		 * Below Code Commented for MTK411
		 * for carrier even if there is mailbags n weight , container level - both displayed as zero
		 * This bug occurs only when 1 mailbag is accepted and then listed.
		 */
//		if(MailConstantsVO.ULD_TYPE.equals(rs.getString("CONTYP"))){
//			containerDetailsVO.setTotalBags(rs.getInt("ULDBAGCNT"));
//			containerDetailsVO.setTotalWeight(rs.getDouble("ULDBAGWGT"));
//		}
		containerDetailsVO.setDestination(rs.getString("DSTCOD"));
		containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
		containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
		containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
		containerDetailsVO.setContainerType(rs.getString("CONTYP"));
		containerDetailsVO.setTransferFromCarrier(rs.getString("FRMCARCOD"));
		containerDetailsVO.setTransitFlag(rs.getString("TRNFLG"));
		Timestamp lstUpdateTime = rs.getTimestamp("CONLSTUPDTIM");
		if(lstUpdateTime != null) {
			containerDetailsVO.setLastUpdateTime(
				new LocalDate(LocalDate.NO_STATION, Location.NONE, lstUpdateTime));
		}
		Timestamp uldLastUpdateTime = rs.getTimestamp("ULDLSTUPDTIM");
		if(uldLastUpdateTime != null) {
			containerDetailsVO.setUldLastUpdateTime(
				new LocalDate(LocalDate.NO_STATION, 
					Location.NONE, uldLastUpdateTime));
		}
		containerDetailsVO.setContentId(rs.getString("CNTIDR"));//Added as part of ICRD-239331
	}
}
