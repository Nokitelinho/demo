
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.defaults.AWMProrationDetailsMapper.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7371	:	11-May-2018	:	Draft
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationSurchargeDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class AWMProrationDetailsMapper implements MultiMapper<AWMProrationDetailsVO> {

	private ProrationFilterVO prorationFilterVO;
	private static final String CLASS_NAME = "AWMProrationDetailsMapper";

	private Log log = LogFactory.getLogger("MRA:DEFAULTS");
	
	
	/**
	 *@author A-7794 
	 */
	public AWMProrationDetailsMapper(ProrationFilterVO prorationFilterVO) {
		this.prorationFilterVO = prorationFilterVO;
	}


	public List<AWMProrationDetailsVO> map(ResultSet rs) throws SQLException {
		log.entering(CLASS_NAME, "map");
		HashMap<String,AWMProrationDetailsVO> awmProrationMap = new HashMap<String,AWMProrationDetailsVO>();
		HashMap<String,AWMProrationSurchargeDetailsVO> awmSurProrationMap = new HashMap<String,AWMProrationSurchargeDetailsVO>();
		String malChgKey = null;
		String surChgKey = null;
		String chargeCode;
        List<AWMProrationDetailsVO> aWMProrationDetailsVOs=new ArrayList<AWMProrationDetailsVO>();
		while(rs.next()) {
			malChgKey=new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getString("SECFRM"))
					.append(rs.getString("SECTOO")).toString();
					
			surChgKey=new StringBuilder().append(rs.getString("CMPCOD"))
					.append(rs.getString("CHGCOD"))
					.append(rs.getString("SERNUM")).toString();
			
			if(awmProrationMap.containsKey(malChgKey)){
				AWMProrationDetailsVO awmProrationDetailsVO=null;
				awmProrationDetailsVO=awmProrationMap.get(malChgKey);

				if(!awmSurProrationMap.containsKey(surChgKey)){
					chargeCode=null;
					 chargeCode =rs.getString("CHGCOD");
					 
					 if(chargeCode!=null &&!"".equals(chargeCode) &&chargeCode.trim().length()>0){
						 AWMProrationSurchargeDetailsVO aWMProrationSurchargeDetailsVO=new AWMProrationSurchargeDetailsVO();
							 aWMProrationSurchargeDetailsVO.setChargeHead(rs.getString("CHGCOD"));
							 aWMProrationSurchargeDetailsVO.setSector((new StringBuilder().append(rs.getString("SECFRM"))
									 .append("-").append(rs.getString("SECTOO"))).toString());
							 if(rs.getString("CTRCURCOD") != null){
								 aWMProrationSurchargeDetailsVO.setCurrency(rs.getString("CTRCURCOD"));
								}
							 try{
									Money surAmtInUsd =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
									Money surAmtInXdr =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
									//Modified by A-7794 as part of ICRD-267369
									Money surAmtInBas =  CurrencyHelper.getMoney(prorationFilterVO.getBaseCurrency());
									Money surAmtInCtr =  CurrencyHelper.getMoney(aWMProrationSurchargeDetailsVO.getCurrency());
									
									surAmtInUsd.setAmount(rs.getDouble("SURCHGVALUSD"));
									surAmtInXdr.setAmount(rs.getDouble("SURCHGVALSDR"));
									surAmtInBas.setAmount(rs.getDouble("SURCHGVALBAS"));
									surAmtInCtr.setAmount(rs.getDouble("SURCHGVAL"));
							
									aWMProrationSurchargeDetailsVO.setSurProratedAmtInCtrCur(surAmtInCtr);
									aWMProrationSurchargeDetailsVO.setSurProrationAmtInBaseCurr(surAmtInBas);;
									aWMProrationSurchargeDetailsVO.setSurProrationAmtInSdr(surAmtInXdr);
									aWMProrationSurchargeDetailsVO.setSurProrationAmtInUsd(surAmtInUsd);
								}catch(CurrencyException currencyException){
									log.log(Log.FINE, currencyException.getErrorCode());
								}
							 awmProrationDetailsVO.getAwmProrationSurchargeDetailsVO().add(aWMProrationSurchargeDetailsVO);
							 awmSurProrationMap.put(surChgKey, aWMProrationSurchargeDetailsVO);
					 }


				}
				
				}else{

					AWMProrationDetailsVO aWMProrationDetailsVO=new AWMProrationDetailsVO();
						aWMProrationDetailsVO.setCarrierCode(rs.getString("FLTCARCOD"));
						aWMProrationDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
						aWMProrationDetailsVO.setPoaCode(rs.getString("POACOD"));
						aWMProrationDetailsVO.setSectorFrom(rs.getString("SECFRM"));
						aWMProrationDetailsVO.setSectorTo(rs.getString("SECTOO"));
						aWMProrationDetailsVO.setNumberOfPieces(rs.getInt("TOTPCS"));
						//Modified by A-7794 as part of ICRD-267355
						aWMProrationDetailsVO.setWeight(rs.getDouble("UPDGRSWGT"));
						aWMProrationDetailsVO.setProrationType(rs.getString("PROTYP"));
						aWMProrationDetailsVO.setProrationPercentage(rs.getInt("PROPRC"));
						aWMProrationDetailsVO.setSectorStatus(rs.getString("SECSTA"));

						
						
						if(rs.getString("CTRCURCOD") != null){
							aWMProrationDetailsVO.setCurrency(rs.getString("CTRCURCOD"));
						}
		                
						try{
							Money amtInUsd =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
							Money amtInXdr =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
							//Modified by A-7794 as part of ICRD-267369
							Money amtInBas =  CurrencyHelper.getMoney(prorationFilterVO.getBaseCurrency());
							Money amtInCtr =  CurrencyHelper.getMoney(aWMProrationDetailsVO.getCurrency());
							
							amtInUsd.setAmount(rs.getDouble("MALCHGVALUSD"));
							amtInXdr.setAmount(rs.getDouble("MALCHGVALXDR"));
							amtInBas.setAmount(rs.getDouble("MALCHGVALBAS"));
							amtInCtr.setAmount(rs.getDouble("MALCHGVAL"));
						
							aWMProrationDetailsVO.setProrationAmtInBaseCurr(amtInBas);
							aWMProrationDetailsVO.setProrationAmtInSdr(amtInXdr);
							aWMProrationDetailsVO.setProrationAmtInUsd(amtInUsd);
							aWMProrationDetailsVO.setProrationAmtInCtrCurr(amtInCtr);
						}catch(CurrencyException currencyException){
							log.log(Log.FINE, currencyException.getErrorCode());
						}
						
						 chargeCode=null;
						 chargeCode =rs.getString("CHGCOD");
						 
						 if(chargeCode!=null &&!"".equals(chargeCode) &&chargeCode.trim().length()>0){
							 AWMProrationSurchargeDetailsVO aWMProrationSurchargeDetailsVO=new AWMProrationSurchargeDetailsVO();
							 Collection<AWMProrationSurchargeDetailsVO> aWMProrationSurchargeDetailsVOs=new ArrayList<AWMProrationSurchargeDetailsVO>();
								 aWMProrationSurchargeDetailsVO.setChargeHead(rs.getString("CHGCOD"));
								 aWMProrationSurchargeDetailsVO.setSector((new StringBuilder().append(aWMProrationDetailsVO.getSectorFrom()).append("-")
										 .append(aWMProrationDetailsVO.getSectorTo())).toString());
								 try{
										Money surAmtInUsd =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
										Money surAmtInXdr =  CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
										//Modified by A-7794 as part of ICRD-267369
										Money surAmtInBas =  CurrencyHelper.getMoney(prorationFilterVO.getBaseCurrency());
										Money surAmtInCtr =  CurrencyHelper.getMoney(aWMProrationDetailsVO.getCurrency());
										 
										surAmtInUsd.setAmount(rs.getDouble("SURCHGVALUSD"));
										surAmtInXdr.setAmount(rs.getDouble("SURCHGVALSDR"));
										surAmtInBas.setAmount(rs.getDouble("SURCHGVALBAS"));
										surAmtInCtr.setAmount(rs.getDouble("SURCHGVAL"));

										aWMProrationSurchargeDetailsVO.setSurProratedAmtInCtrCur(surAmtInCtr);
										aWMProrationSurchargeDetailsVO.setSurProrationAmtInBaseCurr(surAmtInBas);;
										aWMProrationSurchargeDetailsVO.setSurProrationAmtInSdr(surAmtInXdr);
										aWMProrationSurchargeDetailsVO.setSurProrationAmtInUsd(surAmtInUsd);
									}catch(CurrencyException currencyException){
										log.log(Log.FINE, currencyException.getErrorCode());
									}
								 
								 aWMProrationSurchargeDetailsVOs.add(aWMProrationSurchargeDetailsVO);
							 aWMProrationDetailsVO.setAwmProrationSurchargeDetailsVO(aWMProrationSurchargeDetailsVOs);
							 awmSurProrationMap.put(surChgKey, aWMProrationSurchargeDetailsVO);
					}

							aWMProrationDetailsVOs.add(aWMProrationDetailsVO);
							awmProrationMap.put(malChgKey, aWMProrationDetailsVO);

				
			}

}
		return  aWMProrationDetailsVOs; 
}
}
