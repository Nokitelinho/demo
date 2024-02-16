/**
 *	Java file	: 	com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.ClaimMailbagDetailsMapper.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Jun 7, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * Java file :
 * com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.ClaimMailbagDetailsMapper.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-4809 : Jun 7,
 * 2019 : Draft
 */
public class ClaimMailbagDetailsMapper implements MultiMapper<ClaimVO> {

	/**
	 * Overriding Method : @see
	 * com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
	 * Added by : A-4809 on Jun 7, 2019 Used for : Parameters : @param arg0
	 * Parameters : @return Parameters : @throws SQLException
	 */
	@Override
	public List<ClaimVO> map(ResultSet rs) throws SQLException {

		List<ClaimVO> claims = new ArrayList<ClaimVO>();
		ClaimVO claimVO = null;
		int currentIteration = 0;
		String messageText = "";
		while (rs.next()) {
			// while(rs.next()){
			do {
				claimVO = new ClaimVO();
				claimVO.setCompanyCode(rs.getString("CMPCOD"));
				claimVO.setCarrierCode(rs.getString("TWOAPHCOD"));
				claimVO.setCarrierName(rs.getString("ARLNAM"));
				claimVO.setConsignmentCompletionDate(rs.getString("CLMSUBDAT"));
				claimVO.setInvoiceNumber(rs.getString("CLMREFNUM"));
				claimVO.setPoaCode(rs.getString("POACOD"));
				claimVO.setAssigneCode(rs.getString("ASCASGCOD"));

				claimVO.setRegionCode(rs.getString("REGCOD"));
				claimVO.setContractNumber(rs.getString("CTRNUM"));

				claimVO.setClaimAmt(rs.getDouble("CLMAMT"));
				claimVO.setInvDate(DateUtilities.format(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getDate("SCHINVDAT")), "yyyyMMddHHmmss"));
				claimVO.setLineCount(rs.getString("TOTMALCNT"));
				claimVO.setTotalClaimAmount(rs.getString("TOTCLMAMT"));
				currentIteration = 1;
			} while (currentIteration < 1);

			if (rs.getString("MSGTXT") != null) {
				messageText = messageText.concat(rs.getString("MSGTXT"));
			}
			claimVO.setMessageText(messageText);
			// }
		}
		claims.add(claimVO);
		return claims;
	}

}
