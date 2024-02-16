/* ULDAgreementPageDetailsMultiMapper.java Created on Jan 27, 2020
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
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
//import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
//import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1496
 *
 */
public class ULDAgreementPageDetailsMultiMapper implements MultiMapper<ULDAgreementDetailsVO>{
	  private Log log=LogFactory.getLogger(" ULD_DEFAULTS");

	  private String airportCode = null;
	  public ULDAgreementPageDetailsMultiMapper(String logonAirportCode){
		  airportCode = logonAirportCode;
	  }
   /**
    * @param rs
    * @return List<ULDAgreementDetailsVO>
    * @throws SQLException
    */
    public List<ULDAgreementDetailsVO> map(ResultSet rs) throws SQLException{
    	
    	

    	ULDAgreementVO uldAgreementVO = null;
    	ULDAgreementDetailsVO uldAgreementDetailsVO = null;
    	List <ULDAgreementDetailsVO> uldAgreementDetailsVOs = new ArrayList<ULDAgreementDetailsVO>();
    	
    	
    	String 	presId = null;
    	String prevId = null;
    	StringBuilder sbOne = null;
		while(rs.next()){

		presId = rs.getString("AGR1");

		
		if(!presId.equals(prevId)){
			uldAgreementVO = new ULDAgreementVO();
			log.log(Log.FINE,"Inside if loop") ;
    		uldAgreementVO.setCompanyCode(rs.getString("CMP1"));
    		uldAgreementVO.setAgreementNumber(rs.getString("AGR1"));
    		uldAgreementVO.setTxnType(rs.getString("TXNTYP"));
    		uldAgreementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
    		uldAgreementVO.setLastUpdatedTime(
    				new LocalDate(LocalDate.NO_STATION , Location.NONE , rs.getTimestamp("LSTUPDTIM")));
    		uldAgreementVO.setAgreementStatus(rs.getString("AGRMNTSTA"));
    		Date datOne = rs.getDate("AGRMNTDAT");
			if(datOne != null){
				uldAgreementVO.setAgreementDate(new LocalDate(airportCode,Location.ARP,datOne));
			}
    		uldAgreementVO.setPartyCode(rs.getString("PTYCOD"));
    		uldAgreementVO.setPartyType(rs.getString("PTYTYP"));
    		uldAgreementVO.setPartyName(rs.getString("PTYNAM"));
    		//added as part of ICRD-232684 by A-4393 starts
    		uldAgreementVO.setFromPartyCode(rs.getString("FRMPTYCOD"));
    		uldAgreementVO.setFromPartyType(rs.getString("FRMPTYTYP"));
    		uldAgreementVO.setFromPartyName(rs.getString("FRMPTYNAM"));
    		//added as part of ICRD-232684 by A-4393 ends 
    		uldAgreementVO.setFreeLoanPeriod(rs.getInt("FLP1"));
    		Date datTwo = rs.getDate("FRMDAT1");
			if(datTwo != null){
				uldAgreementVO.setAgreementFromDate(new LocalDate(airportCode,Location.ARP,datTwo));
			}
			Date datThree = rs.getDate("TODAT1");
			if(datThree!= null){
				uldAgreementVO.setAgreementToDate(new LocalDate(airportCode,Location.ARP,datThree));
			}
    		uldAgreementVO.setDemurrageRate(rs.getDouble("RAT1"));
    		uldAgreementVO.setDemurrageFrequency(rs.getString("FQY1"));
    		uldAgreementVO.setTax(rs.getDouble("TAX1"));
    		uldAgreementVO.setCurrency(rs.getString("COD1"));
    		uldAgreementVO.setRemark(rs.getString("RMK1"));
    		/*Date time = rs.getDate("LSTUPDTIM");
    		if(time != null){
				uldAgreementVO.setLastUpdatedTime(new LocalDate(airportCode,Location.ARP,time));				
			}*/
			uldAgreementVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
			uldAgreementDetailsVOs =  new ArrayList<ULDAgreementDetailsVO>();
    		prevId = presId;


    		}
		sbOne = new StringBuilder();
    		if(rs.getString("AGR2") != null){
    			uldAgreementDetailsVO = new ULDAgreementDetailsVO();
    			uldAgreementDetailsVO.setCompanyCode(rs.getString("CMP2"));
    			uldAgreementDetailsVO.setAgreementNumber(rs.getString("AGR2"));
    			uldAgreementDetailsVO.setSequenceNumber(rs.getInt("SEQNUM"));
    			uldAgreementDetailsVO.setUldTypeCode(rs.getString("ULDTYPCOD"));
    			uldAgreementDetailsVO.setStation(rs.getString("ARPCOD"));
    			uldAgreementDetailsVO.setFreeLoanPeriod(rs.getInt("FLP2"));
    			Date datFour = rs.getDate("FRMDAT2");
				if(datFour != null){
					uldAgreementDetailsVO.setAgreementFromDate(new LocalDate(airportCode,Location.ARP,datFour));
				}
				Date datFive = rs.getDate("TODAT2");
				if(datFive != null){
					uldAgreementDetailsVO.setAgreementToDate(new LocalDate(airportCode,Location.ARP,datFive));
				}
				uldAgreementDetailsVO.setDemurrageRate(rs.getDouble("RAT2"));
				uldAgreementDetailsVO.setDemurrageFrequency(rs.getString("FQY2"));
				uldAgreementDetailsVO.setTax(rs.getDouble("TAX2"));
				uldAgreementDetailsVO.setCurrency(rs.getString("COD2"));
				uldAgreementDetailsVO.setRemark(rs.getString("RMK2"));

				uldAgreementDetailsVOs.add(uldAgreementDetailsVO);

		}

	}

	log.exiting("ULDAgreementPageDetailsMultiMapper","map");
	return uldAgreementDetailsVOs;

    }

}
