package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;

import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class MRAGpaBatchSettlmentMapper implements MultiMapper<InvoiceSettlementVO>{
	
	private static final Log LOG = LogFactory.getLogger("MRA:RCVMNG");
	private static final String CURCOD ="STLCURCOD";

	
	@Override
	public List<InvoiceSettlementVO> map(ResultSet rs) throws SQLException {
		List<InvoiceSettlementVO> invoiceSettlemnetVOs=new ArrayList<>();
		while (rs.next()) {
			InvoiceSettlementVO invoiceSettlementVO=new InvoiceSettlementVO();

			invoiceSettlementVO.setCompanyCode(rs.getString("CMPCOD"));
			invoiceSettlementVO.setContractCurrencyCode(rs.getString("CURCOD"));
			invoiceSettlementVO.setDestnCode(rs.getString("DSTCOD"));
			invoiceSettlementVO.setSettlementFileName(rs.getString("FILNAM"));
			invoiceSettlementVO.setSettlementFileType(rs.getString("FILTYP"));
			invoiceSettlementVO.setFlownSector(rs.getString("FLNSEC"));
			invoiceSettlementVO.setInvoiceNumber(rs.getString("INVNUM"));
			invoiceSettlementVO.setSettlementId(rs.getString("BTHSTLIDR"));
			invoiceSettlementVO.setBatchSeqNumber(rs.getLong("BTHSTLSEQNUM"));
			invoiceSettlementVO.setFromBatchSettlementJob(true);
			invoiceSettlementVO.setSettlementDate(new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false));
			Money mailchg = null;
			Money netamount = null;
			Money stlamt = null;
			Money surchg=null;
			Money tax=null;
			Money settledamt=null;
			Money dueamt=null;
			Money revnetamt=null;
			Money actualbilled=null;
			if(rs.getString(CURCOD)!=null){
				try{
					mailchg = CurrencyHelper.getMoney(rs.getString(CURCOD));
					netamount=CurrencyHelper.getMoney(rs.getString(CURCOD));
					stlamt=CurrencyHelper.getMoney(rs.getString(CURCOD));
					surchg=CurrencyHelper.getMoney(rs.getString(CURCOD));
					tax=CurrencyHelper.getMoney(rs.getString(CURCOD));
					settledamt=CurrencyHelper.getMoney(rs.getString(CURCOD));
					dueamt=CurrencyHelper.getMoney(rs.getString(CURCOD));
					revnetamt = CurrencyHelper.getMoney(rs.getString(CURCOD));
					actualbilled=CurrencyHelper.getMoney(rs.getString(CURCOD));
				mailchg.setAmount(rs.getDouble("MALCHG"));
				netamount.setAmount(rs.getDouble("NETAMT"));
				stlamt.setAmount(rs.getDouble("STLAMT"));
				surchg.setAmount(rs.getDouble("SURCHG"));
				tax.setAmount(rs.getDouble("TAX"));
				settledamt.setAmount(rs.getDouble("C66STLAMT"));
				dueamt.setAmount(rs.getDouble("C66DUEAMT"));
				revnetamt.setAmount(rs.getDouble("REVNETAMT"));
				} catch (CurrencyException e) {
					LOG.log(Log.SEVERE, e);
				}
			

			}
			if(actualbilled != null && revnetamt != null && rs.getDouble("REVNETAMT")!=0){
				double amount=netamount.getAmount()+revnetamt.getAmount();
				actualbilled.setAmount(amount);
				invoiceSettlementVO.setActualBilled(actualbilled);
			}else{
				invoiceSettlementVO.setActualBilled(actualbilled);
			}
			invoiceSettlementVO.setMailCharge(mailchg);
			invoiceSettlementVO.setNetAmount(netamount);
			invoiceSettlementVO.setSettlemetAmt(stlamt);
			invoiceSettlementVO.setSurCharge(surchg);
			invoiceSettlementVO.setAmountAlreadySettled(settledamt);
			invoiceSettlementVO.setDueAmount(dueamt);
			invoiceSettlementVO.setMcaNumber(revnetamt);
			invoiceSettlementVO.setMailRate(rs.getDouble("MALRAT"));
			invoiceSettlementVO.setMailsequenceNum(rs.getLong("MALSEQNUM"));
			invoiceSettlementVO.setGpaCode(rs.getString("POACOD"));
			invoiceSettlementVO.setSettlementCurrencyCode(rs.getString(CURCOD));
			invoiceSettlementVO.setRemarks(rs.getString("STLRMKS"));
			invoiceSettlementVO.setTax(tax);

			Measure strWt=new Measure(UnitConstants.MAIL_WGT,rs.getDouble("WGT"));
			invoiceSettlementVO.setWgt(strWt);


			invoiceSettlementVO.setMailbagID(rs.getString("MALIDR"));
			invoiceSettlementVO.setMailsequenceNum(rs.getLong("MALSEQNUM"));
			invoiceSettlementVO.setProcessIdentifier(rs.getString("PRCIDR"));
			invoiceSettlementVO.setSummaryGpa(rs.getString("C51GPA"));
			invoiceSettlementVO.setSummaryInvoiceNumber(rs.getString("C51INVNUM"));
			invoiceSettlementVO.setInvSerialNumber(rs.getInt("INVSERNUM"));
			invoiceSettlementVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
			invoiceSettlementVO.setTolerancePercentage(rs.getDouble("STLTOLPER"));
			invoiceSettlementVO.setSettlementValue(rs.getDouble("STLTOLVAL"));
			invoiceSettlementVO.setSettlementMaxValue(rs.getDouble("STLTOLMAXVAL"));
			invoiceSettlementVO.setSettlementLevel(rs.getString("STLLVL"));
			invoiceSettlementVO.setTotalBatchAmountApplied(rs.getDouble("BTHTOTAMT"));

			invoiceSettlemnetVOs.add(invoiceSettlementVO);
		}

		return invoiceSettlemnetVOs;
	}

}
