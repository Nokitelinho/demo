/*
 * InvoiceSummaryMapper.java Created on March 16, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.sql.ResultSet;import java.sql.SQLException;import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;import com.ibsplc.xibase.util.log.Log;import com.ibsplc.xibase.util.log.factory.LogFactory;
/** * @author A-2521 * 
 * Mapper for getting  CN51 invoices */
public class InvoiceSummaryMapper implements Mapper<AirlineCN51SummaryVO> {
	private Log log = LogFactory.getLogger("InvoiceSummaryMapper");		
	 /**	  * @param rs	  * @return airlineCN51SummaryVO	  * @throws SQLException	  */	public AirlineCN51SummaryVO map(ResultSet rs) throws SQLException {		
		AirlineCN51SummaryVO airlineCN51SummaryVO 	= new AirlineCN51SummaryVO();				String blgTyp = rs.getString("INTBLGTYP");		String outward = "Outward";		String inward = "Inward";		String outwardCod = "O";		String inwardCod = "I";				/** added for invoice summary report by clearance period */		airlineCN51SummaryVO.setAirlinecode( rs.getString("ARLCOD" ));		airlineCN51SummaryVO.setAirlineidr( rs.getInt("ARLIDR" ));		airlineCN51SummaryVO.setStrAirlineIdr( String.valueOf(				rs.getInt("ARLIDR")).substring(1));		airlineCN51SummaryVO.setAirlineName( rs.getString("ARLNAM" ));		//Added by A-7794 as part of ICRD-265471		airlineCN51SummaryVO.setCompanycode(rs.getString("CMPCOD"));				if(inwardCod.equals(blgTyp)){						//airlineCN51SummaryVO.setInterlinebillingtype(inward);			//Modified by A-7794 as part of ICRD-265471			airlineCN51SummaryVO.setInterlinebillingtype(blgTyp);						airlineCN51SummaryVO.setTotalAmount(rs.getDouble("TOTAMTCRTCUR" ));							}else if(outwardCod.equals(blgTyp)){						//airlineCN51SummaryVO.setInterlinebillingtype(outward);			//Modified by A-7794 as part of ICRD-265471			airlineCN51SummaryVO.setInterlinebillingtype(blgTyp);							//airlineCN51SummaryVO.setTotalAmount(rs.getDouble("TOTAMTBLGCUR" ));			airlineCN51SummaryVO.setTotalAmount(rs.getDouble("TOTAMTLSTCUR" ));   //Modified by A-7929 as part of ICRD-265471					}					airlineCN51SummaryVO.setInvoicenumber( rs.getString("INVNUM" )); 						/** added for invoice summary report by airline */		airlineCN51SummaryVO.setClearanceperiod( rs.getString("CLRPRD" ));		airlineCN51SummaryVO.setBillingcurrencycode( rs.getString("BLGCURCOD" ));		airlineCN51SummaryVO.setContractCurrencycode( rs.getString("CRTCURCOD" ));		airlineCN51SummaryVO.setInvStatus(rs.getString("INVSTA"));		airlineCN51SummaryVO.setFileName(rs.getString("INTFCDFILNAM"));			log.exiting("InvoiceSummaryMapper", "Map Method");				return airlineCN51SummaryVO;
	}
}
