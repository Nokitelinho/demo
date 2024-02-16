package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
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

public class FindContainersinCarrierMapper implements MultiMapper<ContainerDetailsVO> {

	 private Log log= LogFactory.getLogger("MAILOUTBOUND_DEFAULTS");
	 private static final String CLASS_NAME = "FindContainersinCarrierMapper";
    
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
		  DSNVO dsnVO = null;
		  Collection<DSNVO> dsnVOs = null;
		  
		  while (rs.next())
		    {
		      currULDKey = rs.getString("CONNUM");
		      this.log.log(3, new Object[] { "The NEW ParentID is Found to be ", currULDKey });
		      if (!currULDKey.startsWith("TRASH"))
		      {
		        if (!currULDKey.equals(prevULDKey))
		        {
		          containerDetailsVO = new ContainerDetailsVO();
		          populateContainerDetails(containerDetailsVO, rs);
		          containerDetailsVOsList.add(containerDetailsVO);
		          prevULDKey = currULDKey;
		          onwardRoutingVos = new ArrayList();
		          dsnVOs = new ArrayList();
		          prevDSNKey = null;
		         // containerDetailsVO.setOnwardRoutingForSegmentVOs(onwardRoutingVos);
		         // containerDetailsVO.setDsnVOs(dsnVOs);
		        }
		        
	/*	        String dsn = rs.getString("DSN");
		        if ((dsn != null) && (rs.getInt("DSNACPBAG") > 0))
		        {
		          currDSNKey = 
		          
		            currULDKey + rs.getString("DSN") + rs.getString("ORGEXGOFC") + rs.getString("DSTEXGOFC") + rs.getString("MALSUBCLS") + rs.getString("MALCTGCOD") + rs.getInt("YER");
		          
		          this.log.log(3, new Object[] { "currDSNKey ", currDSNKey });
		          if (!currDSNKey.equals(prevDSNKey))
		          {
		            dsnVO = new DSNVO();
		            populateDSNDetails(dsnVO, rs, containerDetailsVO);
		            dsnVOs.add(dsnVO);
		            prevDSNKey = currDSNKey;
		          }
		          else
		          {
		            if (dsnVO != null)
		            {
		              dsnVO.setBags(dsnVO.getBags() + rs.getInt("DSNACPBAG"));
		              dsnVO.setPrevBagCount(dsnVO.getPrevBagCount() + rs.getInt("DSNACPBAG"));
		              try
		              {
		                dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), new Measure("MWT", rs.getDouble("DSNACPWGT"))));
		              }
		              catch (UnitException e)
		              {
		                this.log.log(3, new Object[] { "UnitException", e.getMessage() });
		              }
		              try
		              {
		                dsnVO.setPrevBagWeight(Measure.addMeasureValues(dsnVO.getPrevBagWeight(), new Measure("MWT", rs.getDouble("DSNACPWGT"))));
		              }
		              catch (UnitException e)
		              {
		                this.log.log(3, new Object[] { "UnitException", e.getMessage() });
		              }
		              dsnVO.setStatedBags(dsnVO.getStatedBags() + rs.getInt("DSNSTDBAG"));
		              dsnVO.setPrevStatedBags(dsnVO.getPrevStatedBags() + rs.getInt("DSNSTDBAG"));
		              try
		              {
		                dsnVO.setStatedWeight(Measure.addMeasureValues(dsnVO.getStatedWeight(), new Measure("MWT", rs.getInt("DSNSTDBAG"))));
		              }
		              catch (UnitException e)
		              {
		                this.log.log(3, new Object[] { "UnitException", e.getMessage() });
		              }
		              try
		              {
		                dsnVO.setPrevStatedWeight(Measure.addMeasureValues(dsnVO.getPrevStatedWeight(), new Measure("MWT", rs.getDouble("DSNSTDWGT"))));
		              }
		              catch (UnitException e)
		              {
		                this.log.log(3, new Object[] { "UnitException", e.getMessage() });
		              }
		            }
		            containerDetailsVO.setTotalBags(containerDetailsVO.getTotalBags() + rs.getInt("DSNACPBAG"));
		            try
		            {
		              containerDetailsVO.setTotalWeight(Measure.addMeasureValues(containerDetailsVO.getTotalWeight(), new Measure("MWT", rs.getDouble("DSNACPWGT"))));
		            }
		            catch (UnitException e)
		            {
		              this.log.log(3, new Object[] { "UnitException", e.getMessage() });
		            }
		          }
		        }*/
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
			 containerDetailsVO.setAssignedPort(assignedPort);
			 containerDetailsVO.setPol(assignedPort);
			 containerDetailsVO.setCarrierId(rs.getInt("FLTCARIDR"));
			 containerDetailsVO.setFlightNumber(rs.getString("FLTNUM"));
			 containerDetailsVO.setPou(rs.getString("POU"));
			// LocalDate flightDate = new LocalDate(
	 		//			rs.getString("FLTORG"), Location.ARP, rs.getDate("FLTDAT"));
			// containerDetailsVO.setFlightDate(flightDate);
			containerDetailsVO.setFlightSequenceNumber(rs.getLong("FLTSEQNUM"));
			containerDetailsVO.setSegmentSerialNumber(rs.getInt("SEGSERNUM"));
			containerDetailsVO.setLegSerialNumber(rs.getInt("LEGSERNUM"));
			containerDetailsVO.setLocation(rs.getString("LOCCOD"));
			containerDetailsVO.setWareHouse(rs.getString("WHSCOD"));
			containerDetailsVO.setRemarks(rs.getString("RMK"));
			containerDetailsVO.setTotalBags(rs.getInt("MALCNT"));
			containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT")));
			containerDetailsVO.setDestination(rs.getString("DSTCOD"));
			containerDetailsVO.setPaBuiltFlag(rs.getString("POAFLG"));
			containerDetailsVO.setAcceptedFlag(rs.getString("ACPFLG"));
			containerDetailsVO.setOffloadFlag(rs.getString("OFLFLG"));
			containerDetailsVO.setContainerType(rs.getString("CONTYP"));
			//containerDetailsVO.setArrivedStatus(rs.getString("ARRSTA"));
			containerDetailsVO.setTransferFromCarrier(rs.getString("FRMCARCOD"));
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
			 containerDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
			 if( rs.getTimestamp("REQDLVTIM")!=null) {
			 containerDetailsVO.setMinReqDelveryTime(new LocalDate(LocalDate.NO_STATION, Location.NONE,
					 rs.getTimestamp("REQDLVTIM")));
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
