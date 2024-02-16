/*
 * AgreementDetailsForReturnTransactionMapper.java Created on Oct 16, 2005
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
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

    /**
     * 
     * @author A-1936
     *
     */
	public class AgreementDetailsForReturnTransactionMapper implements Mapper<ULDAgreementVO> {
		  private Log log=LogFactory.getLogger("ULD_DEFAULTS");
	  
		  /**
	       * @param rs
	       * @return 
	       * @throws SQLException
	       */
	 public ULDAgreementVO map(ResultSet rs) throws SQLException{
		  ULDAgreementVO uldAgreementVo = new ULDAgreementVO();
		  log.entering("INSIDE THE MAPPER","AgreementDetailsForReturnTransactionMapper");
		     uldAgreementVo.setTax(rs.getDouble("TAXAMT")); 
		     uldAgreementVo.setAgreementNumber(rs.getString("AGRMNTNUM"));
		     uldAgreementVo.setDemurrageFrequency(rs.getString("DMRFQY"));
		     uldAgreementVo.setDemurrageRate(rs.getDouble("DMRRAT"));
		     uldAgreementVo.setFreeLoanPeriod(rs.getInt("FRELONPRD")); 
		     uldAgreementVo.setCurrency(rs.getString("CURCOD"));
		    
		return uldAgreementVo;
     }
  }