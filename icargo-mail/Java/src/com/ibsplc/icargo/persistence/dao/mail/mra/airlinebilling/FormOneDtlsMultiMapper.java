/* FormOneDtlsMultiMapper.java Created on Jul 28,2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;



import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.FormOneVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceInFormOneVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2391
 * 
 */
public class FormOneDtlsMultiMapper implements
		MultiMapper<FormOneVO> {
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "FormOneDtlsMultiMapper";

	/**
	 * @param rs
	 * @return List<ProrationDetailsVO>
	 * @throws SQLException
	 */
	public ArrayList<FormOneVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		FormOneVO formOneVO = new FormOneVO();
		Collection<FormOneVO> formOneVOs = new ArrayList<FormOneVO>();
		Collection<InvoiceInFormOneVO> formOneInvVOs = new ArrayList<InvoiceInFormOneVO>();	
		Map<String, InvoiceInFormOneVO> formOneInvVOMap = new HashMap<String, InvoiceInFormOneVO>();
		String formOneInvVOKey = null;
		InvoiceInFormOneVO formOneInvVO = null;
		int i=0;
		try{
			
			
		while (rs.next()) {
			
			if(i==0){
		
			formOneVO.setBillingCurrency(rs.getString("BLGCURCOD"));
			log.log(Log.INFO, "blg cur code ", rs.getString("BLGCURCOD"));
			if(rs.getString("BLGCURCOD")!=null && rs.getString("BLGCURCOD").trim().length()>0){
				Money totblgamt=CurrencyHelper.getMoney(rs.getString("BLGCURCOD"));
				Money totmiscamt=CurrencyHelper.getMoney(rs.getString("BLGCURCOD"));
				
				totmiscamt.setAmount(rs.getDouble("TOTMISAMTLSTCUR"));     //Modified by A-7929 as part of ICRD-265471
				log.log(Log.INFO, "TOTMISAMTLSTCUR ", totmiscamt.getAmount());
				formOneVO.setMissAmount(totmiscamt);
				log.log(Log.INFO, "TOTMISAMTLSTCUR in vo ", formOneVO.getMissAmount().toString());
				totblgamt.setAmount(rs.getDouble("TOTAMTBLGCUR"));
				log.log(Log.INFO, "TOTAMTBLGCUR ", totblgamt.getAmount());
				formOneVO.setBillingTotalAmt(totblgamt);
				log.log(Log.INFO, "TOTMISAMTLSTCUR in vo", formOneVO.getBillingTotalAmt().toString());
				Timestamp blgTim = rs.getTimestamp("BLGLSTTIM");
			     if(blgTim != null) {
			    	 log.log(Log.INFO, "blgTim ", blgTim);
					formOneVO.setLastUpdateTimeBlg(
			    			
			    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,blgTim));
			     }
				     
			}
			}
			
			
			i++;
			formOneInvVOKey = new StringBuilder().append(
					rs.getString("CMPCOD")).append(rs.getInt("ARLIDR")).append(
					rs.getString("CLRPRD")).append(
							rs.getString("CLSTYP")).append(
									rs.getString("INVNUM")).toString();
			if (!formOneInvVOMap.containsKey(formOneInvVOKey)) {
				
					formOneInvVO = new InvoiceInFormOneVO();
					formOneInvVO.setInvoiceNumber(rs.getString("INVNUM"));
					if(rs.getString("INVDAT")!=null){
						formOneInvVO.setInvoiceDate(new LocalDate(NO_STATION,
								NONE, rs.getDate("INVDAT")));
					}
					/**
					 * @author A-3447
					 */
					formOneInvVO.setClassType(rs.getString("CLSTYP"));
					formOneInvVO.setAirlineCode(rs.getString("ARLCOD"));
					formOneInvVO.setCompanyCode(rs.getString("CMPCOD"));
					formOneInvVO.setAirlineIdentifier(rs.getInt("ARLIDR"));
					formOneInvVO.setClearancePeriod(rs.getString("CLRPRD"));
					formOneInvVO.setIntBlgTyp(rs.getString("INTBLGTYP"));
					
					/**
					 * @author A-3447
					 */
					//formOneInvVO.setc
					formOneInvVO.setLstCurCode(rs.getString("LSTCURCOD"));
					formOneInvVO.setExgRate(rs.getDouble("EXGRATLSTBLGCUR"));
					formOneInvVO.setInvStatus(rs.getString("INVSTA"));
					log.log(Log.INFO, "invstatus in mapper ", rs.getString("INVSTA"));
					formOneInvVO.setFormOneStatus(rs.getString("FRMONESTA"));
					log.log(Log.INFO, "FRMONESTA in mapper ", rs.getString("FRMONESTA"));
					Timestamp lstUpdTime = rs.getTimestamp("LSTUPDTIM");
				     if(lstUpdTime != null) {
				    	 formOneInvVO.setLastUpdateTime(
				    		 new LocalDate(LocalDate.NO_STATION, Location.NONE,lstUpdTime));
				     }
				   
				     
				     
					if(rs.getString("BLGCURCOD")!=null && rs.getString("BLGCURCOD").trim().length()>0){
					Money blgamt=CurrencyHelper.getMoney(rs.getString("BLGCURCOD"));
					blgamt.setAmount(rs.getDouble("TOTBLGAMTLSTCUR"));
					formOneInvVO.setBillingTotalAmt(blgamt);
					
					Money misamt=CurrencyHelper.getMoney(rs.getString("BLGCURCOD"));
					misamt.setAmount(rs.getDouble("INVMISAMT"));
					formOneInvVO.setTotMisAmt(misamt);
					formOneInvVOMap
					.put(formOneInvVOKey, formOneInvVO);
					formOneInvVOs.add(formOneInvVO);
				}
				
				
				
				
				
			}
		}
		}
		catch(CurrencyException e){
			e.getErrorCode();
		}
		
		formOneVO.setInvoiceInFormOneVOs(formOneInvVOs);
		log.log(Log.INFO, "formOneVO in mapper ", formOneVO);
		formOneVOs.add(formOneVO);
		log.exiting(CLASS_NAME, "map");
		return (ArrayList<FormOneVO>) formOneVOs;
	}
}
