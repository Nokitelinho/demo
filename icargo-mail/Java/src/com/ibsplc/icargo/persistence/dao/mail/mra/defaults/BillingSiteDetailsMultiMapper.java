/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteDetailsMultiMapper.java
 *
 *	Created by	:	A-5219
 *	Created on	:	19-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2013 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteBankDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * @author A-5219
 *
 */
public class BillingSiteDetailsMultiMapper
implements MultiMapper<BillingSiteVO> {

	/**
    *
    * @param rs
    * @return
    * @throws SQLException
    * @see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
    */
	public List<BillingSiteVO> map(ResultSet resultset)
			throws SQLException {
		BillingSiteVO billingSiteVO=null;
		BillingSiteBankDetailsVO billingSiteBankDetailsVO=null;
		BillingSiteGPACountriesVO billingSiteGPACountriesVO=null;
		List<BillingSiteVO> billingSiteVOs = null;
		Map<String, BillingSiteVO> billingSiteVOsMap = new HashMap<String, BillingSiteVO>();
		Map<String, BillingSiteBankDetailsVO> billingSiteBankDetailsVOsMap = new HashMap<String, BillingSiteBankDetailsVO>();
		Map<String, BillingSiteGPACountriesVO> billingSiteGPACountriesVOsMap = new HashMap<String, BillingSiteGPACountriesVO>();
		String companyCode = "";
		String billingSiteCode = "";
		String billingSiteBankCode="";
		String billingSiteCountryCode="";
		String billingSiteKey = "";
		String billingSiteBankKey = "";
		String billingSiteCountryKey = "";
		String billingSitebankSernum="";
		String billingSiteCountrySernum="";
		while (resultset.next()) {
			companyCode = resultset.getString("CMPCOD");
			billingSiteCode = resultset.getString("BLGSITMSTCOD");
			billingSitebankSernum=resultset.getString("BNKSERNUM");
			billingSiteCountrySernum=resultset.getString("CNTSERNUM");
			billingSiteBankCode=resultset.getString("BLGSITBNKCOD");
			billingSiteCountryCode=resultset.getString("BLGSITCNTCOD");
			billingSiteKey = new StringBuilder().append(companyCode).append(billingSiteCode).toString();
			billingSiteBankKey = new StringBuilder().append(companyCode).append(billingSiteBankCode).append(billingSitebankSernum).toString();
			billingSiteCountryKey = new StringBuilder().append(companyCode).append(billingSiteCountryCode).append(billingSiteCountrySernum).toString();
			if(!billingSiteVOsMap.containsKey(billingSiteKey)){
				billingSiteVO = new BillingSiteVO();
				billingSiteVO.setBillingSiteCode(resultset.getString("BLGSITMSTCOD"));
				billingSiteVO.setBillingSite(resultset.getString("BLGSITNAM"));
				billingSiteVO.setAirlineAddress(resultset.getString("ARLADR"));
				billingSiteVO.setCompanyCode(resultset.getString("CMPCOD"));
				billingSiteVO.setCorrespondenceAddress(resultset.getString("CORADR"));
				billingSiteVO.setDesignator1(resultset.getString("DSGONE"));
				billingSiteVO.setDesignator2(resultset.getString("DSGTWO"));
				billingSiteVO.setSignator1(resultset.getString("SIGONE"));
				billingSiteVO.setSignator2(resultset.getString("SIGTWO"));
				billingSiteVO.setSerialNumber(resultset.getInt("MSTSERNUM"));
				if(resultset.getDate("FRMDAT")!=null)
					{
					billingSiteVO.setFromDate(new LocalDate("***", Location.NONE, resultset.getDate("FRMDAT")));
					}
				if(resultset.getDate("TOODAT")!=null)
					{
					billingSiteVO.setToDate(new LocalDate("***", Location.NONE, resultset.getDate("TOODAT")));
					}
				billingSiteVO.setFreeText(resultset.getString("RMK"));
				billingSiteVO.setBillingSiteBankDetailsVO(new ArrayList<BillingSiteBankDetailsVO>());
				billingSiteVO.setBillingSiteGPACountriesVO(new ArrayList<BillingSiteGPACountriesVO>());
				if(resultset.getString("BLGSITBNKCOD")!=null){
					billingSiteBankDetailsVO = new BillingSiteBankDetailsVO();
					billingSiteBankDetailsVO.setBillingSiteCode(resultset.getString("BLGSITBNKCOD"));
					billingSiteBankDetailsVO.setCurrency(resultset.getString("CURCOD"));
					billingSiteBankDetailsVO.setBankName(resultset.getString("BNKNAM"));
					billingSiteBankDetailsVO.setBranch(resultset.getString("BNKBRN"));
					billingSiteBankDetailsVO.setAccNo(resultset.getString("ACCNUM"));
					billingSiteBankDetailsVO.setCity(resultset.getString("CTYNAM"));
					billingSiteBankDetailsVO.setCountry(resultset.getString("CNTNAM"));
					billingSiteBankDetailsVO.setSwiftCode(resultset.getString("SWTCOD"));
					billingSiteBankDetailsVO.setIbanNo(resultset.getString("IBNNUM"));
					billingSiteBankDetailsVO.setSerialNumber(resultset.getInt("BNKSERNUM"));
					billingSiteVO.getBillingSiteBankDetailsVO().add(billingSiteBankDetailsVO);
					billingSiteBankDetailsVOsMap.put(billingSiteBankKey, billingSiteBankDetailsVO);
				}
				if(resultset.getString("BLGSITCNTCOD")!=null){
					billingSiteGPACountriesVO= new BillingSiteGPACountriesVO();
					billingSiteGPACountriesVO.setBillingSiteCode(resultset.getString("BLGSITCNTCOD"));
					billingSiteGPACountriesVO.setGpaCountry(resultset.getString("CNTCOD"));
					billingSiteGPACountriesVO.setBillingSiteCode(resultset.getString("BLGSITCNTCOD"));
					billingSiteGPACountriesVO.setSerialNumber(resultset.getInt("CNTSERNUM"));
					billingSiteVO.getBillingSiteGPACountriesVO().add(billingSiteGPACountriesVO);
					billingSiteGPACountriesVOsMap.put(billingSiteCountryKey, billingSiteGPACountriesVO);
				}
				
				billingSiteVOsMap.put(billingSiteKey, billingSiteVO);
			}else{
				billingSiteVO = billingSiteVOsMap.get(billingSiteKey);
				if(!billingSiteGPACountriesVOsMap.containsKey(billingSiteCountryKey) && resultset.getString("BLGSITCNTCOD")!=null){
					billingSiteGPACountriesVO= new BillingSiteGPACountriesVO();
					billingSiteGPACountriesVO.setGpaCountry(resultset.getString("CNTCOD"));
					billingSiteGPACountriesVO.setSerialNumber(resultset.getInt("CNTSERNUM"));
					billingSiteVO.getBillingSiteGPACountriesVO().add(billingSiteGPACountriesVO);
					billingSiteGPACountriesVOsMap.put(billingSiteCountryKey, billingSiteGPACountriesVO);
				}
				if(!billingSiteBankDetailsVOsMap.containsKey(billingSiteBankKey) && resultset.getString("BLGSITBNKCOD")!=null){
					billingSiteBankDetailsVO = new BillingSiteBankDetailsVO();
					billingSiteBankDetailsVO.setCurrency(resultset.getString("CURCOD"));
					billingSiteBankDetailsVO.setBankName(resultset.getString("BNKNAM"));
					billingSiteBankDetailsVO.setBranch(resultset.getString("BNKBRN"));
					billingSiteBankDetailsVO.setAccNo(resultset.getString("ACCNUM"));
					billingSiteBankDetailsVO.setCity(resultset.getString("CTYNAM"));
					billingSiteBankDetailsVO.setCountry(resultset.getString("CNTNAM"));
					billingSiteBankDetailsVO.setSwiftCode(resultset.getString("SWTCOD"));
					billingSiteBankDetailsVO.setIbanNo(resultset.getString("IBNNUM"));
					billingSiteBankDetailsVO.setSerialNumber(resultset.getInt("BNKSERNUM"));
					billingSiteVO.getBillingSiteBankDetailsVO().add(billingSiteBankDetailsVO);
					billingSiteBankDetailsVOsMap.put(billingSiteBankKey, billingSiteBankDetailsVO);
				}
				billingSiteVOsMap.put(billingSiteKey, billingSiteVO);
			}
			
		}

		return new ArrayList<BillingSiteVO>(billingSiteVOsMap.values());
	}
}
