/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.BillingSiteOverlapMultiMapper.java
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteGPACountriesVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;

/**
 * The Class BillingSiteOverlapMultiMapper.
 *
 * @author A-5219
 */
public class BillingSiteOverlapMultiMapper
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
		BillingSiteGPACountriesVO billingSiteGPACountriesVO=null;
		List<BillingSiteVO> billingSiteVOs = null;
		Map<String, BillingSiteVO> billingSiteVOsMap = new HashMap<String, BillingSiteVO>();
		Map<String, BillingSiteGPACountriesVO> billingSiteGPACountriesVOsMap = new HashMap<String, BillingSiteGPACountriesVO>();
		String companyCode = "";
		String billingSiteCode = "";
		String billingSiteCountryCode="";
		String billingSiteKey = "";
		String billingSiteCountryKey = "";
		String billingSiteCountrySernum="";
		while (resultset.next()) {
			companyCode = resultset.getString("CMPCOD");
			billingSiteCode = resultset.getString("BLGSITCOD");
			billingSiteCountrySernum=resultset.getString("CNTSERNUM");
			billingSiteCountryCode=resultset.getString("BLGSITCNTCOD");
			billingSiteKey = new StringBuilder().append(companyCode).append(billingSiteCode).toString();
			billingSiteCountryKey = new StringBuilder().append(companyCode).append(billingSiteCountryCode).append(billingSiteCountrySernum).toString();
			if(!billingSiteVOsMap.containsKey(billingSiteKey)){
				billingSiteVO = new BillingSiteVO();
				billingSiteVO.setBillingSiteCode(resultset.getString("BLGSITCOD"));
				billingSiteVO.setSerialNumber(resultset.getInt("MSTSERNUM"));
				if(resultset.getDate("FRMDAT")!=null)
					{
					billingSiteVO.setFromDate(new LocalDate("***", Location.NONE, resultset.getDate("FRMDAT")));
					}
				if(resultset.getDate("TOODAT")!=null)
					{
					billingSiteVO.setToDate(new LocalDate("***", Location.NONE, resultset.getDate("TOODAT")));
					}
				billingSiteVO.setBillingSiteGPACountriesVO(new ArrayList<BillingSiteGPACountriesVO>());
				if(resultset.getString("BLGSITCNTCOD")!=null){
					billingSiteGPACountriesVO= new BillingSiteGPACountriesVO();
					billingSiteGPACountriesVO.setBillingSiteCode(resultset.getString("BLGSITCNTCOD"));
					billingSiteGPACountriesVO.setGpaCountry(resultset.getString("CNTCOD"));
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
				
				billingSiteVOsMap.put(billingSiteKey, billingSiteVO);
			}
			
		}

		return new ArrayList<BillingSiteVO>(billingSiteVOsMap.values());
	}
}
