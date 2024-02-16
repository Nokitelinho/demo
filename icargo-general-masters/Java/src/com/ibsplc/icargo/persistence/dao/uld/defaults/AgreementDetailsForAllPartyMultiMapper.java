/*
 * AgreementDetailsForAllPartyMultiMapper.java Created on Oct 16, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

    /**
     * 
     * @author A-2048
     *
     */
	public class AgreementDetailsForAllPartyMultiMapper implements MultiMapper<ULDAgreementVO> {
		  private Log log=LogFactory.getLogger("ULD_DEFAULTS");
	  
		  /**
	       * @param rs
	       * @return 
	       * @throws SQLException
	       */
	 public List<ULDAgreementVO> map(ResultSet rs) throws SQLException{
		 log.entering("INSIDE THE MAPPER","AgreementDetailsForAllPartyMultiMapper"); 
		 
		  List<ULDAgreementVO> uLDAgreementVOs = new ArrayList<ULDAgreementVO>();
		  ULDAgreementVO uldAgreementVo = new ULDAgreementVO();
		  Collection <ULDAgreementDetailsVO> detailVOs = null;
		  ULDAgreementDetailsVO detailVO = null;
		  String 	presId ="";
	      String prevId = ""; 
	    	
	      String childPresId = "";
		  String childPrevId = "";
		  
		  while (rs.next()) {
			  presId =new StringBuffer(rs.getString("AGRCMPCOD"))
						              .append(rs.getString("AGRAGRMNTNUM"))
						              .toString();
			  if(!presId.equals(prevId)){
				  uldAgreementVo = new ULDAgreementVO();
				  
				  uldAgreementVo.setCompanyCode(rs.getString("AGRCMPCOD"));
				  
				  uldAgreementVo.setTax(rs.getDouble("AGRTAXAMT")); 
			      uldAgreementVo.setAgreementNumber(rs.getString("AGRAGRMNTNUM"));
			      uldAgreementVo.setDemurrageFrequency(rs.getString("AGRDMRFQY"));
			      uldAgreementVo.setDemurrageRate(rs.getDouble("AGRDMRRAT"));
			      uldAgreementVo.setFreeLoanPeriod(rs.getInt("AGRFRELONPRD")); 
			      uldAgreementVo.setCurrency(rs.getString("AGRCURRENCY"));
			      if(rs.getDate("PARFRMDAT") != null) {
			    	  uldAgreementVo.setAgreementFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PARFRMDAT")));
	    		  }
			      if(rs.getDate("PARTOODAT") != null) {
			    	  uldAgreementVo.setAgreementToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("PARTOODAT")));
	    		  }
			      prevId = presId ;
			      detailVOs = new HashSet<ULDAgreementDetailsVO>();
			  }
			  childPresId = new StringBuffer(rs.getString("AGRCMPCOD"))
					              .append(rs.getString("AGRAGRMNTNUM"))
					              .append(rs.getInt("SEQNUM"))
					              .toString();
			  if(!childPresId.equalsIgnoreCase(childPrevId)	) {
				  if(detailVO != null) {
					  detailVOs.add(detailVO);
				  }
				  detailVO = new ULDAgreementDetailsVO(); 
				  detailVO.setCompanyCode(rs.getString("AGRCMPCOD"));
				  detailVO.setSequenceNumber(rs.getInt("SEQNUM"));
				  detailVO.setTax(rs.getDouble("TAXAMT")); 
				  detailVO.setAgreementNumber(rs.getString("AGRAGRMNTNUM"));
				  detailVO.setDemurrageFrequency(rs.getString("DMRFQY"));
				  detailVO.setDemurrageRate(rs.getDouble("DMRRAT"));
				  detailVO.setFreeLoanPeriod(rs.getInt("FRELONPRD")); 
				  detailVO.setCurrency(rs.getString("CURCOD"));
				  if(rs.getDate("CHDFRMDAT") != null) {
					  detailVO.setAgreementFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CHDFRMDAT")));
	    		  }
			      if(rs.getDate("CHDTOODAT") != null) {
			    	  detailVO.setAgreementToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("CHDTOODAT")));
	    		  }
			      childPrevId = childPresId;
			  }
			  
		  }
		  if(uldAgreementVo != null) {
			  if(detailVO != null) {
				  detailVOs.add(detailVO);  
			  }
			  if(detailVOs != null && detailVOs.size() >0 ) {
				  uldAgreementVo.setUldAgreementDetailVOs(new ArrayList<ULDAgreementDetailsVO> (detailVOs));  
			  }
			  uLDAgreementVOs.add(uldAgreementVo);
			  
		  }
		    
               		  
		     
		     
		    
		return uLDAgreementVOs;
     }
  }