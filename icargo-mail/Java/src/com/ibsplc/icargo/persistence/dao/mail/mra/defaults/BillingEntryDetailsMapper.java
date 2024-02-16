package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
/**
 * 
 * @author A-2147
 *
 */
public class BillingEntryDetailsMapper implements Mapper<MRABillingDetailsVO> {
	/**
	 * 
	 */
	public 	 MRABillingDetailsVO map(ResultSet rs) throws SQLException {
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		billingDetailsVO.setCompanyCode(rs.getString("CMPCOD"));
		billingDetailsVO.setBillingBasis(rs.getString("MALIDR"));//A-8164 for TK 4.8
		billingDetailsVO.setCsgDocumentNumber(rs.getString("CSGDOCNUM"));
		billingDetailsVO.setCsgSeqNumber(rs.getInt("CSGSEQNUM"));
		billingDetailsVO.setPoaCode(rs.getString("POACOD"));
		billingDetailsVO.setContractCurrCode(rs.getString("CTRCURCOD"));
		billingDetailsVO.setSequenceNumber(rs.getInt("SERNUM"));
		billingDetailsVO.setWgtCharge(rs.getDouble("WGTCHG"));
		billingDetailsVO.setGpaCountryCode(rs.getString("CNTCOD"));
		billingDetailsVO.setValCharges(rs.getDouble("VALCHGCTR"));//5219
		if(rs.getTimestamp("CSGDAT")!=null)
		{
		billingDetailsVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("CSGDAT")));
		}
		billingDetailsVO.setConsignmentOrigin(rs.getString("CSGORG"));
		billingDetailsVO.setConsignmentDestination(rs.getString("CSGDST"));
		//Added for CRQ 12578
		if("Y".equals(rs.getString("RATINCSRVTAXFLG"))){
			billingDetailsVO.setTaxIncludedInRateFlag(true);
		}
		else{
			billingDetailsVO.setTaxIncludedInRateFlag(false);
		}
		billingDetailsVO.setSegFrom(rs.getString("SECFRM"));
		billingDetailsVO.setSegTo(rs.getString("SECTOO"));		
		billingDetailsVO.setUpdBillTo(rs.getString("UPDBILLTO"));
		//Added by A-4809 for ICRD-161909...Starts
		billingDetailsVO.setMailbagOrigin(rs.getString("ORG"));
		billingDetailsVO.setMailbagDestination(rs.getString("DST"));
		//Added by A-4809 for ICRD-161909... Ends
		//Added for Bug ICRD-19263 starts
		billingDetailsVO.setPaymentFlag(rs.getString("PAYFLG"));	
		if(billingDetailsVO.getContractCurrCode()!=null && billingDetailsVO.getContractCurrCode().trim().length()>0){
			try {
				Money netAmount= CurrencyHelper.getMoney(rs.getString("CTRCURCOD"));
				netAmount.setAmount(rs.getDouble("NETAMT"));
				billingDetailsVO.setNetAmount(netAmount);
				
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block				
			}
			
		}
		//Added for Bug ICRD-19263 ends
		billingDetailsVO.setSurCharge(rs.getDouble("UPDOTHCHG"));//Added by A-7871 for ICRD-154005
		if(rs.getTimestamp("RCVDAT")!=null){
		billingDetailsVO.setRecieveDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getTimestamp("RCVDAT")));
		}
		if(rs.getInt("SEQNUMINT")!=0){
			billingDetailsVO.setSeqNumInt(rs.getInt("SEQNUMINT"));
			}
		if(rs.getLong("MALSEQNUM")!=0){
			billingDetailsVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
			}
		if(rs.getString("ORGCNTCOD")!=null)
		{
			billingDetailsVO.setOrgCountryCode(rs.getString("ORGCNTCOD"));
		}
		return billingDetailsVO;
		
	}

}
