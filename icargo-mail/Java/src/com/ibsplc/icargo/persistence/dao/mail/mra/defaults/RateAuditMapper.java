/*
 * RateAuditMapper.java Created on July 17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author a-3108
 * 
 */
public class RateAuditMapper implements Mapper<RateAuditVO>{
	
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String CLASS_NAME = "RateAuditMapper";
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public RateAuditVO map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		RateAuditVO rateAuditVO=new RateAuditVO();
		RateAuditDetailsVO rateAuditDetailsVO = new RateAuditDetailsVO();
		Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
		Double presentWtCharge=0.0;
		Double auditedWtCharge=0.0;
	try{	
		if(rs.getString("CMPCOD")!=null){
			rateAuditVO.setCompanyCode(rs.getString("CMPCOD"));
		}
		if(rs.getString("BLGBAS")!=null){
			rateAuditVO.setBillingBasis(rs.getString("BLGBAS"));
		}
		if(rs.getString("BLGBAS")!=null){
			rateAuditVO.setBillingBasis(rs.getString("BLGBAS"));
		}
		if(rs.getString("CSGDOCNUM")!=null){
			rateAuditVO.setConDocNum(rs.getString("CSGDOCNUM"));
		}
		if(rs.getString("CSGSEQNUM")!=null){
			rateAuditVO.setConSerNum(Integer.parseInt(rs.getString("CSGSEQNUM")));
		}
		
		rateAuditVO.setSerialNumber(rs.getInt("SERNUM"));
			
		
		if(rs.getString("DSN")!=null){
			rateAuditVO.setDsn(rs.getString("DSN"));
		}
		if(rs.getString("DSNDAT")!=null){
			rateAuditVO.setDsnDate(new LocalDate(NO_STATION,
					NONE, rs.getDate("DSNDAT")));
		}
		
		if(rs.getString("POACOD")!=null){
			rateAuditVO.setGpaCode(rs.getString("POACOD"));
		}
		if(rs.getString("MALCTGCOD")!=null){
			rateAuditVO.setCategory(rs.getString("MALCTGCOD"));
		}
		/*if(rs.getString("MALCLS")!=null){
			rateAuditVO.setMalClass(rs.getString("MALCLS"));
		}*/
		if(rs.getString("MALSUBCLS")!=null){
			rateAuditVO.setSubClass(rs.getString("MALSUBCLS"));
			Character malClass=rateAuditVO.getSubClass().charAt(0);
			rateAuditVO.setMalClass(malClass.toString());
			
		}
		
		if(rs.getString("ORGEXGOFC")!=null){
			rateAuditVO.setOrigin(rs.getString("ORGEXGOFC"));
		}
		if(rs.getString("DSTEXGOFC")!=null){
			rateAuditVO.setDestination(rs.getString("DSTEXGOFC"));
		}
		if(rs.getString("TOTPCS")!=null){
			rateAuditVO.setPcs(rs.getString("TOTPCS"));
		}
		if(rs.getString("GRSWGT")!=null){
			rateAuditVO.setGrossWt(rs.getDouble("GRSWGT"));
			rateAuditVO.setUpdWt(rs.getString("GRSWGT"));
		}
		if(rs.getString("RATSTA")!=null){
			rateAuditVO.setDsnStatus(rs.getString("RATSTA"));
		}
		if(rs.getString("APPAUD")!=null){
			rateAuditVO.setApplyAutd(rs.getString("APPAUD"));
		}
		if("Y".equals(rs.getString("APPAUD"))){
    		
			rateAuditVO.setApplyAutd(RateAuditVO.FLAG_YES);
    	}
    	
    	else if("N".equals(rs.getString("APPAUD"))){
    		
    		rateAuditVO.setApplyAutd(RateAuditVO.FLAG_NO);
    	}
		/*if(rs.getString("DISPCY")!=null){
			rateAuditVO.setDiscrepancy(rs.getString("DISPCY"));
		}*/
		if(rs.getString("FLTCARCOD")!=null){
			rateAuditVO.setCarrierCode(rs.getString("FLTCARCOD"));
		}
		if(rs.getString("FLTNUM")!=null){
			rateAuditVO.setFlightNumber(rs.getString("FLTNUM"));
		}
		if(rs.getString("FLTDAT")!=null){
			rateAuditVO.setFlightDate(new LocalDate(NO_STATION,
					NONE, rs.getDate("FLTDAT")));
		}	
		
		if(rs.getString("APLRAT")!=null){
			rateAuditVO.setRate(rs.getDouble("APLRAT"));
		}
		/*if(rs.getString("WGTCHG")!=null){
			rateAuditVO.setPresentWtCharge(rs.getString("WGTCHG"));
		}
		if(rs.getString("UPDWGTCHG")!=null){
			rateAuditVO.setAuditedWtCharge(rs.getString("UPDWGTCHG"));
		}*/
		if(rs.getString("UPDBILLTO")!=null){
			rateAuditVO.setBillTo(rs.getString("UPDBILLTO"));
			rateAuditDetailsVO.setBillTO(rs.getString("UPDBILLTO"));
		}
		if(rs.getString("CTRCURCOD")!=null){
			rateAuditVO.setCurrency(rs.getString("CTRCURCOD"));
			
		}		
		Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
	     if(lstUpdTime != null) {
	    	 rateAuditVO.setLastUpdateTime(
	    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
	     }
	     if(rs.getString("CTRCURCOD")!=null){
		    	
	    	
	    	 Money amount = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
    		 amount.setAmount(rs.getDouble("WGTCHG"));
    		 rateAuditVO.setPresentWtCharge(amount);
    		 rateAuditDetailsVO.setPrsntWgtCharge(amount);
    		 Money amounts = CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
    		 amounts.setAmount(rs.getDouble("UPDWGTCHG"));
    		 rateAuditVO.setAuditedWtCharge(amounts);
    		 rateAuditDetailsVO.setContCurCode(rs.getString("CTRCURCOD"));
    		 rateAuditDetailsVO.setAudtdWgtCharge(amounts);    		 
    		 
    	}
	         rateAuditDetailsVO.setPayFlag("R");
	         rateAuditDetailsVO.setGpaarlBillingFlag(rs.getString("GPAARLBLGFLG"));
	         rateAuditDetailsVO.setFulChg(rs.getDouble("FULCHG"));
	         rateAuditDetailsVO.setRatInd(rs.getString("RATIND"));
	         rateAuditDetailsVOs.add(rateAuditDetailsVO);
	         rateAuditVO.setRateAuditDetails(rateAuditDetailsVOs);
	         
	     if(rateAuditVO.getPresentWtCharge()!=null){
	      presentWtCharge=rateAuditVO.getPresentWtCharge().getAmount();
	     }
	     if(rateAuditVO.getAuditedWtCharge()!=null){
	      auditedWtCharge=rateAuditVO.getAuditedWtCharge().getAmount();
	     }
	     
	     if((presentWtCharge-auditedWtCharge)==0){
	    	 rateAuditVO.setDiscrepancy("N");
	     }
	     else{
	    	 rateAuditVO.setDiscrepancy(String.valueOf(auditedWtCharge-presentWtCharge));
	     }
	     
    }
	     catch(CurrencyException e) {
	        throw new SQLException(e.getErrorCode());
	    }
		return rateAuditVO;
	}

}
