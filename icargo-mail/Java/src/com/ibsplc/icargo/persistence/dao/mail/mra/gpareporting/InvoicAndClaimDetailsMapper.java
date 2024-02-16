/**
 *
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicDetailsVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;

/**
 * @author A-5219
 *
 */
public class InvoicAndClaimDetailsMapper implements Mapper<InvoicDetailsVO>{
	
	private static final String INVOIC_DELETED = "Invoic Deleted";
	private static final String PAYMENT_DESC = "PAYDES";
	private static final String PAYMENT_TYPE = "PAYTYP";

	@Override
	public InvoicDetailsVO map(ResultSet rs) throws SQLException {

		InvoicDetailsVO vo = new InvoicDetailsVO();
		if("I".equals(rs.getString("TYPDAT"))){
			vo.setType("I");
			vo.setInvoicID(rs.getString("INVREFNUM"));
			if(rs.getDate("INCRCVDAT")!= null){
				vo.setInvoicRcvDate(new LocalDate
				(LocalDate.NO_STATION,Location.NONE,rs.getDate("INCRCVDAT")));
			}
			if(rs.getDate("RPTPRDFRM")!= null && rs.getDate("RPTPRDTOO")!= null){
				vo.setInvoicPeriod(new StringBuilder(new LocalDate
						(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDFRM")).toDisplayDateOnlyFormat()).append(" - ")
						.append(new LocalDate
						(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDTOO")).toDisplayDateOnlyFormat()).toString());
			}
			try{
			Money amount = CurrencyHelper.getMoney(rs.getString("STLCUR"));
			amount.setAmount(rs.getDouble("INVAMT"));
			vo.setInvoicamount(amount);
			}catch(CurrencyException currencyException){
	        	currencyException.getErrorCode();
	        }
			StringBuilder invoicPayMentType=new StringBuilder();
			if(rs.getString(PAYMENT_DESC) != null && rs.getString(PAYMENT_DESC).contains(INVOIC_DELETED)){
				invoicPayMentType.append(rs.getString(PAYMENT_DESC));
			}else{
			invoicPayMentType.append(rs.getString(PAYMENT_DESC)).append(" (").append(rs.getString(PAYMENT_TYPE)).append(")");
			}
			vo.setInvoicPayType(invoicPayMentType.toString());                         
			String amountType= "";
			if("I".equals(rs.getString("INCTYP"))){
				if("01".equals(rs.getString("PAYTYP")) || "1".equals(rs.getString("PAYTYP")) ||
						"03".equals(rs.getString("PAYTYP")) || "3".equals(rs.getString("PAYTYP")) ||
						"05".equals(rs.getString("PAYTYP")) || "5".equals(rs.getString("PAYTYP")) ||
						"09".equals(rs.getString("PAYTYP")) || "9".equals(rs.getString("PAYTYP")) ||
						"04".equals(rs.getString("PAYTYP"))){
					amountType = "CREDIT";
				}else{ 
					amountType = "DEBIT";
				}
			}else{
				if("09".equals(rs.getString("PAYTYP")) || "9".equals(rs.getString("PAYTYP")) ||
						"04".equals(rs.getString("PAYTYP")) || "4".equals(rs.getString("PAYTYP")) ||
						"37".equals(rs.getString("PAYTYP"))){
							amountType = "CREDIT";
				}else{ 
					amountType = "DEBIT";
				}
			}
			vo.setPayType(amountType);
			vo.setClaimReason(rs.getString("CLMTYP"));
		}else{
			vo.setType("C");
			vo.setClaimRefNumber(rs.getString("INVREFNUM"));
			if(rs.getDate("INCRCVDAT")!= null){
				vo.setClaimRcvDate(new LocalDate
				(LocalDate.NO_STATION,Location.NONE,rs.getDate("INCRCVDAT")));
			}
			if(rs.getDate("RPTPRDFRM")!= null && rs.getDate("RPTPRDTOO")!= null){
				vo.setClaimPeriod(new StringBuilder(new LocalDate
						(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDFRM")).toDisplayDateOnlyFormat()).append(" - ")
						.append(new LocalDate
						(LocalDate.NO_STATION,Location.NONE,rs.getDate("RPTPRDTOO")).toDisplayDateOnlyFormat()).toString());
			}
			try{
			Money amount = CurrencyHelper.getMoney(rs.getString("STLCUR"));
			amount.setAmount(rs.getDouble("INVAMT"));
			vo.setClaimamount(amount);
			}catch(CurrencyException currencyException){
	        	currencyException.getErrorCode();
	        }
			vo.setInvoicPayType(rs.getString("PAYTYP"));
			vo.setClaimReason(rs.getString("CLMTYP"));
		}
		return vo;

	}

}
