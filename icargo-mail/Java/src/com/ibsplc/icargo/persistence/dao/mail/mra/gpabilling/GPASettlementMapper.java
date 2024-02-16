/**
 * 
 * GPASettlementMapper.java Created on Sep 04,2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

/**
 * @author A-4809
 * 
 *
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class GPASettlementMapper implements MultiMapper<GPASettlementVO>{
	
	private Log log = LogFactory.getLogger("MRA GPABILLING");

	public List<GPASettlementVO> map(ResultSet resultset) throws SQLException {
		log.entering("GPASettlementMapper", "map");
		List<GPASettlementVO> gpaSettlementVOS=new ArrayList<GPASettlementVO>();
		String currSettlementDetailsKey="";
		Map<String, SettlementDetailsVO> settlementDetailsMap =  new HashMap<String, SettlementDetailsVO>();
		GPASettlementVO gpaSettlementVO = null;
		gpaSettlementVO=new GPASettlementVO();
		gpaSettlementVO.setSettlementDetailsVOs(new ArrayList<SettlementDetailsVO>());
		while (resultset.next()) {
		gpaSettlementVO.setCompanyCode(resultset.getString("CMPCOD"));
		//gpaSettlementVO.setSettlementDetailsVOs(new ArrayList<SettlementDetailsVO>());
		gpaSettlementVO.setInvoiceSettlementVOs(new ArrayList<InvoiceSettlementVO>());
		gpaSettlementVO.setSettlementCurrency(resultset.getString("STLCURCOD"));				
		gpaSettlementVO.setGpaCode(resultset.getString("GPACOD"));				
		if(resultset.getDate("STLDAT")!= null){ 
			gpaSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("STLDAT") ));
		}
		gpaSettlementVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
		if(resultset.getTimestamp("LSTUPDTIM")!=null){
			gpaSettlementVO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getTimestamp("LSTUPDTIM")));
		}
		gpaSettlementVO.setSettlementId(resultset.getString("STLREFNUM"));
     if(resultset.getString("STLREFNUM")!=null && resultset.getString("STLREFNUM").trim().length()>0){
		currSettlementDetailsKey = new StringBuilder().append(resultset.getString("GPACOD"))
				.append(resultset.getString("STLREFNUM"))
				.append(resultset.getInt("SEQNUM")).append(resultset.getInt("SERNUM"))
				.append(resultset.getString("CMPCOD")).toString();
       if(!settlementDetailsMap.containsKey(currSettlementDetailsKey)){
		SettlementDetailsVO settlementVO = new SettlementDetailsVO();
		settlementVO.setChequeBank(resultset.getString("BNKNAM"));
		settlementVO.setChequeBranch(resultset.getString("BRNNAM"));
		settlementVO.setChequeNumber(resultset.getString("STLMODNUM"));
		settlementVO.setIsDeleted(resultset.getString("CHQDELFLG"));
		settlementVO.setSerialNumber(resultset.getInt("SERNUM"));
		settlementVO.setInvSerialNumber(resultset.getInt("INVSERNUM"));
		settlementVO.setRemarks(resultset.getString("RMK")); 
		settlementVO.setSettlementId(resultset.getString("STLREFNUM"));
        settlementVO.setSettlementSequenceNumber(resultset.getInt("GPASEQNUM"));   
        settlementVO.setLastUpdatedUser(resultset.getString("LSTUPDUSR"));
		if(resultset.getDate("STLDAT")!= null){
		settlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("STLDAT")));
		}
		if(resultset.getDate("PAYDAT")!=null){
		settlementVO.setChequeDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,resultset.getDate("PAYDAT") ));
		}
		settlementVO.setChequeCurrency(resultset.getString("STLCURCOD"));
		try {
			if(resultset.getString("STLCURCOD")!=null && resultset.getString("STLCURCOD").trim().length()>0){
				Money chequeAmount = CurrencyHelper.getMoney(resultset.getString("STLCURCOD"));
				chequeAmount.setAmount(resultset.getDouble("STLAMT"));
				settlementVO.setChequeAmount(chequeAmount);				 
			} 
		}
		catch (CurrencyException e) {			
			log.log(Log.FINE,  "CurrencyException");
		}
		gpaSettlementVO.getSettlementDetailsVOs().add(settlementVO);					
		settlementDetailsMap.put(currSettlementDetailsKey, settlementVO);
     }
}
	
	}
        gpaSettlementVOS.add(gpaSettlementVO);
		return gpaSettlementVOS;
	}


}
