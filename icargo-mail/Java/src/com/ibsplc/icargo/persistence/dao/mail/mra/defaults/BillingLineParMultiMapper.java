/*
 * BillingLineParMultiMapper.java created on Mar 06, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * @author A-2398
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Mar 6, 2007			  A-2398		Created
 */
public class BillingLineParMultiMapper implements MultiMapper<BillingLineVO> {

	  private Log log = LogFactory.getLogger("MAILTRACKING MRA BILLINGLINE");
	  /**
		 * If Billing Line Valid End date is 
		 * earlier than current date, 
		 * its Status is - Expired.
		 */
	 
	  /**
	     * @author A-2398
	     * @param rs
	     * @return List<BillingLineVO>
	     * @throws SQLException
	     */
  public List<BillingLineVO> map(ResultSet rs) throws SQLException {
  	 log.entering("BillingLineMultiMapper", "map");
  	 
  	 List<BillingLineVO> blgLineDetails =
           new ArrayList<BillingLineVO>();
  		 BillingLineVO billingLineVO = null;
  		 String blgLineKey = null;
  		 String blgLinePrev = null;
  		 
  		 while(rs.next()) {
  			 blgLineKey = new StringBuilder().append(rs.getString("BLGMTXCOD"))
  			 								.append(rs.getString("CMPCOD"))
  			 								.append(rs.getInt("BLGLINSEQNUM")).toString();
  			 if(!blgLineKey.equals(blgLinePrev)) {
  				 blgLinePrev = blgLineKey;
  				 billingLineVO = new BillingLineVO();
  				 blgLineDetails.add(billingLineVO);
  				 billingLineVO.setBillingLineParameters(
  	                        new ArrayList<BillingLineParameterVO>());
  				billingLineVO.setBillingMatrixId(rs.getString("BLGMTXCOD"));
  				billingLineVO.setCompanyCode(rs.getString("CMPCOD"));
  				 billingLineVO.setBillingLineSequenceNumber(rs.getInt("BLGLINSEQNUM"));
  				 billingLineVO.setLastUpdatedUser(rs.getString("LSTUPDUSR"));
  				if(rs.getTimestamp("LSTUPDTIM")!=null){
  					billingLineVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("LSTUPDTIM")));
  		        }
  	            }
  	            if(rs.getInt("BLGLINSEQNUM") > 0) {
  	               BillingLineParameterVO blgLineParameterVO =
  	                        new BillingLineParameterVO();
  	               
  	                billingLineVO.getBillingLineParameters()
  	                        .add(blgLineParameterVO);
  	                
  	                blgLineParameterVO
  	                        .setParameterCode(rs.getString("PARCOD"));
  	                blgLineParameterVO.setParameterValue(rs.getString("PARVAL"));
  	                blgLineParameterVO.setExcludeFlag(rs.getString("EXCFLG"));
  	                
  	            }
  	        }
  	        log.exiting("BillingLineParameterMultiMapper", "map");
  	        return blgLineDetails;
  	    }
  	}