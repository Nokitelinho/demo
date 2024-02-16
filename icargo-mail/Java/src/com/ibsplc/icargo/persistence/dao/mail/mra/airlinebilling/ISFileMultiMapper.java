/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISChargeAmountMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISInvoiceMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMemoAWBinSubInvoiceMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISSubInvoiceMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageFlightDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageLocationCodeVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageMailDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageQuantityVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageRouteDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.sis.SISXMLMessageUnitPriceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7794
 *
 */
public class ISFileMultiMapper implements MultiMapper<SISMessageVO> {
	
	private Log log = LogFactory.getLogger("MRA ARLBILLING");
	private static final String INVOICE = "invoice";
	private static final String LINEITEM = "LineItem";
	private static final String LINEITEM_DETAIL = "LineItemDetail";
	private static final String DATE_FORMAT_TRANSMISSION_HEADER = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String MRA_MSG_VERSION ="IATA:ISXMLInvoiceV3.2.1";
	private static final String INVTYPE="Invoice";
	private static final String CHG_CATG = "Mail";
	private static final String LOC_ID ="Main";
	private static final String CONT_NAME = "H.M.Lee";
	private String lastBillingType = "";
	private int recordSequenceWithinBatch;
	private static final String DESC ="International Mail Billing- ";
	private static final String REJECTION_MEMO = "R";
	private String lastRejectionMemoKey = "";
	private Object lastAwbKey;
	private static final String CREDIT_MEMO = "T";
	private static final String KGM ="KGM";

	
	private int lineItemNumber;
	private String lastInvoiceNumber = "";
	private String lastBilledAirline = "";
	private Collection<SISInvoiceMessageVO> sisInvoiceMessageVOs;
	private Map<String, SISInvoiceMessageVO> invoiceMap = new HashMap<String, SISInvoiceMessageVO>();
	private int detailNumber;
	private SISInvoiceMessageVO sisInvoiceMessageVO;
	private Map<String, Integer> billinTypeMap = new HashMap<String, Integer>();
	private String billingCurrency = "";
	private Map<String,SISMemoAWBinSubInvoiceMessageVO> sisMemoawbinSubInvoiceVOMap;
	private String lastAirlineIdr;
	private int totalAirlinesCount;
	private int totalMemosCount;
	private int totalAwbsCount;
	private double rate;
	private String mailClass;
	private String mailSubClass;
	private String mailCategory;
	private String mailSubClassGroup;
	private String dsnNumber;
	private int detailCount = 0;
	private String ownAirlineCode;
	
	
	
	@Override
	public List<SISMessageVO> map(ResultSet rs) throws SQLException {
		List<SISMessageVO> sisMessageVOs = new ArrayList<SISMessageVO>();
		SISMessageVO sisMessageVO = new SISMessageVO();
		Date now = new Date();
		LogonAttributes logonAttributes=null;
		try {
			 logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			 ownAirlineCode=logonAttributes.getOwnAirlineCode();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
		}
		sisMessageVO.setTransmissionDateTime(new SimpleDateFormat(
				DATE_FORMAT_TRANSMISSION_HEADER).format(now));
		
		boolean hasRecord = false;
		while (rs.next()) {
			
			populateInvoiceMessageVO(sisMessageVO, rs);
			hasRecord = true;
		}
		if (hasRecord) {
			// Code to handle last Invoice record starts
			if (this.invoiceMap.get(INVOICE) != null) {
				// for last record only.last record is added to the list of invoices.
				this.sisInvoiceMessageVOs.add(this.invoiceMap.get(INVOICE));
				this.invoiceMap.remove(INVOICE);
			}
			sisMessageVO.setSisInvoiceMessageVOs(this.sisInvoiceMessageVOs);
			this.sisInvoiceMessageVOs = populateInvoiceSummary(this.sisInvoiceMessageVOs);
			// Populating TransmissionSummary
			populateTransmissionSummary(sisMessageVO);
			sisMessageVO.setTotalAirlinesCount(this.totalAirlinesCount);
			sisMessageVOs.add(sisMessageVO);
		}
		return sisMessageVOs;
	}

	
	private void populateInvoiceMessageVO(SISMessageVO sisMessageVO,
			ResultSet rs) throws SQLException {
		
		
		
		boolean isNewInvoice = false;
		if (this.sisInvoiceMessageVOs == null) {
			this.sisInvoiceMessageVOs = new ArrayList<SISInvoiceMessageVO>();
		}
		String invNum = rs.getString("INVNUM");
		String billedAirline = rs.getString("BLDARL");
		if (!this.lastInvoiceNumber.equals(invNum) || !this.lastBilledAirline.equals(billedAirline)) {
			isNewInvoice = true;
		}
		if (isNewInvoice) {
			if (this.lastInvoiceNumber.length() > 0) { // on stepping in to new invoice except first invoice
				if (this.invoiceMap.get(INVOICE) != null) {
					this.sisInvoiceMessageVOs.add(this.invoiceMap.get(INVOICE));
					this.invoiceMap.remove(INVOICE);
				}
			}
		this.lineItemNumber = 0;
		this.detailNumber = 0;
		//this.breakdownSerialNumber = 1;
		this.sisInvoiceMessageVO = new SISInvoiceMessageVO();
		this.billinTypeMap = new HashMap<String, Integer>();
		this.billingCurrency = rs.getString("BLGCURCOD");
		//sisMessageVO.setIssuingOrganizationID(rs.getString("BLDARL"));  //NEED TO CONFIRM THE FIELD
		sisMessageVO.setMsgVersion(MRA_MSG_VERSION);
		sisMessageVO.setIssuingOrganizationID(rs.getString("OWNARLPFX"));
		sisMessageVO.setReceivingOrganizationID(rs.getString("BLDARL"));
		//sisMessageVO.setBillingCategory(BLG_CATGRY);
		handleInvoiceHeader(rs);
		sisMemoawbinSubInvoiceVOMap = new HashMap<String,SISMemoAWBinSubInvoiceMessageVO>();
		}
		populateSISSubInvoiceMessageVO(rs);
		populateSISMemoAWBinSubInvoiceMessageVO(rs);

		this.invoiceMap.remove(INVOICE);
		this.invoiceMap.put(INVOICE, this.sisInvoiceMessageVO);
		this.lastInvoiceNumber = invNum;
		this.lastBilledAirline = billedAirline;
	}
	
	/**
	 * Handles Invoice Header Details for every Invoice created.
	 *
	 * @param rs
	 * @throws SQLException
	 */
	private void handleInvoiceHeader(ResultSet rs) throws SQLException {
		log.log(Log.INFO, "inside handleInvoiceHeader ");
		this.sisInvoiceMessageVO.setInvoiceNumber(rs.getString("INVNUM"));
		//this.sisInvoiceMessageVO.setInvoiceType(rs.getString("INVTYP"));
		if (rs.getDate("INVDAT") != null) {
			this.sisInvoiceMessageVO.setInvoiceDate(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, rs.getDate("INVDAT"))
					.toCalendar());
		}
		this.sisInvoiceMessageVO.setSellerOrganizationName1(CONT_NAME);
		this.sisInvoiceMessageVO.setInvoiceType(INVTYPE);
		this.sisInvoiceMessageVO.setSellerOrganizationId(rs.getString("OWNARLPFX"));
		this.sisInvoiceMessageVO.setSellerLocationId(LOC_ID);
		this.sisInvoiceMessageVO.setLocationCode(rs.getString("ORGCOD"));
		this.sisInvoiceMessageVO.setBuyerOrganizationId(rs.getString("ARLTHRNUM"));//modified by for icrd-287379
		this.sisInvoiceMessageVO.setActualBuyerOrganizationId(rs.getString("BLDARL"));
		this.sisInvoiceMessageVO.setBuyerLocationId(LOC_ID);
		this.sisInvoiceMessageVO.setBillingCurrencyCode(rs.getString("BLGCURCOD"));
		this.sisInvoiceMessageVO.setClearanceCurrencyCode(rs
				.getString("BLGCURCOD"));
		this.sisInvoiceMessageVO.setSettlementMonthPeriod(rs
				.getString("CLRPRD"));
		this.sisInvoiceMessageVO.setSettlementMethod(rs.getString("STLMTHD"));	
		this.sisInvoiceMessageVO.setDigitalSignatureFlag(rs.getString("DIGSIGREQ"));
		if(rs.getString("APLRAT") != null){
			this.sisInvoiceMessageVO.setExchangeRateListingToBilling(Double
					.parseDouble(rs.getString("APLRAT")));
			}
		if(!rs.getString("BLDARL").equals(this.lastAirlineIdr)){
			totalAirlinesCount++;
		}
		this.lastAirlineIdr = rs.getString("BLDARL");
	}
	
	private void populateSISSubInvoiceMessageVO(
			 ResultSet rs)
			throws SQLException {

		String billingType = rs.getString("BLGTYP");
		String billedAirline = rs.getString("BLDARL");
		Double mailRate = rs.getDouble("APLRAT");
		String mailClass = rs.getString("MALCLS");
		String mailCategory = rs.getString("MALCTGCOD");
		String mailSubClsGroup = rs.getString("SUBCLSGRP");
		String dsnNumber = rs.getString("DSN");
		String mailSubClass = rs.getString("MALSUBCLS");
		boolean isNewRecord = false;
		Money lineItemChargeAmount = getMoneyObject();
		Money lineItemQuantity = getMoneyObject();
	
		if ((!billingType.equals(this.lastBillingType))
				|| (!this.lastInvoiceNumber.equals(rs.getString("INVNUM")))
				||(!this.lastBilledAirline.equals(billedAirline))
				|| (!this.mailCategory.equals(mailCategory))
				|| (!this.mailSubClass.equals(mailSubClass))
				|| (!this.mailSubClassGroup.equals(mailSubClsGroup))
				|| (!(this.rate == mailRate))
				/*|| (!(this.rate == mailRate))
				|| (!this.mailClass.equals(mailClass)) || (!this.mailCategory.equals(mailCategory)) || (!this.mailSubClass.equals(mailSubClass))
				|| (!this.mailSubClassGroup.equals(mailSubClsGroup))*/
				) {
			isNewRecord = true;
			this.detailNumber = 0;
			
			detailCount = 0;
		}
		Collection<SISSubInvoiceMessageVO> sisSubInvoiceMessageVOs = this.sisInvoiceMessageVO
				.getSisSubInvoiceMessageVOs();

		if (sisSubInvoiceMessageVOs == null) {
			sisSubInvoiceMessageVOs = new ArrayList<SISSubInvoiceMessageVO>();
		}

		if (isNewRecord) {
			
			detailCount++;
			String desc= null;
			SISSubInvoiceMessageVO sisSubInvoiceMessageVO = new SISSubInvoiceMessageVO();
			// LineItemNumber
			this.lineItemNumber++;
			sisSubInvoiceMessageVO.setInvoiceSeqenceNumber(this.lineItemNumber);
			desc = DESC.concat(rs.getString("SUBCLSGRP").concat("-").concat(rs.getString("MALCLS")).concat(" ").concat(rs.getString("ORGCOD")).concat("-").concat(rs.getString("DSTCOD")));
			sisSubInvoiceMessageVO.setDescription(desc);
			if(rs.getDate("FRMDAT") != null){
				sisSubInvoiceMessageVO.setStartDate(rs.getDate("FRMDAT").toString());
			}
			if(rs.getDate("TOODAT") != null){
				sisSubInvoiceMessageVO.setEndDate(rs.getDate("TOODAT").toString());
			}
			//sisSubInvoiceMessageVO.setProductID(this.ownAirlineCode);//IASCB-46305
			
			sisSubInvoiceMessageVO.setChargeCode(CHG_CATG);
			this.billinTypeMap.remove(billingType);
			this.billinTypeMap.put(billingType, this.lineItemNumber);
			this.recordSequenceWithinBatch = 0;
			//this.breakdownSerialNumber =1;
			
			SISXMLMessageQuantityVO quantityVO = new SISXMLMessageQuantityVO();
			Money money = getMoneyObject();
			money.setAmount(rs.getDouble("C66QTY"));
			quantityVO.setValue(money);
			quantityVO.setUomCode(KGM);
			sisSubInvoiceMessageVO.setSubInvoiceQuantityVO(quantityVO);
			
			SISXMLMessageUnitPriceVO unitPriceVO = new SISXMLMessageUnitPriceVO();
			Money money1 = getMoneyObject();
			money1.setAmount(rs.getDouble("APLRAT"));
			unitPriceVO.setValue(money1);
			unitPriceVO.setSf(1);
			sisSubInvoiceMessageVO.setSubInvoiceUnitPriceVO(unitPriceVO);
			//Modified by A-7794 as part of ICRD-287378
			SISChargeAmountMessageVO sisChargeAmountMessageVO = sisSubInvoiceMessageVO.getSisChargeAmountMessageVO();
			sisChargeAmountMessageVO = populateChargeAmountMessageVO(rs, sisChargeAmountMessageVO, LINEITEM);
			sisChargeAmountMessageVO.getChargeAmount();
			sisSubInvoiceMessageVO.setSisChargeAmountMessageVO(sisChargeAmountMessageVO);
			Money totalNetAmount = getMoneyObject();
			totalNetAmount.setAmount(rs.getDouble("BLDAMT"));
			sisSubInvoiceMessageVO.setTotalNetAmount(totalNetAmount);
			//
			Money totalNetAmountinUSD = getMoneyObject("USD");
			totalNetAmountinUSD.setAmount(rs.getDouble("BLDAMT"));
			sisSubInvoiceMessageVO.setTotalNetAmountinUSD(totalNetAmountinUSD);
			sisSubInvoiceMessageVO.setCountofDocuments(detailCount);
			sisSubInvoiceMessageVOs.add(sisSubInvoiceMessageVO);
		}else{
			for(SISSubInvoiceMessageVO vo : sisSubInvoiceMessageVOs){
				if(vo.getInvoiceSeqenceNumber() == this.lineItemNumber){
					
					detailCount++;
					
					
					SISChargeAmountMessageVO sisChargeAmountMessageVO = vo.getSisChargeAmountMessageVO();
					if(null != sisChargeAmountMessageVO){
						lineItemChargeAmount = sisChargeAmountMessageVO.getChargeAmount().plusEquals(rs.getDouble("BLDAMT"));
						sisChargeAmountMessageVO.setChargeAmount(lineItemChargeAmount);
					}
					SISXMLMessageQuantityVO quantityVO = vo.getSubInvoiceQuantityVO();
					if(null != quantityVO){
						lineItemQuantity = quantityVO.getValue().plusEquals(rs.getDouble("C66QTY"));
						quantityVO.setValue(lineItemQuantity);
					}
					if(null != vo.getTotalNetAmountinUSD()){
						Money totalNetAmountinUSD = getMoneyObject("USD");
						totalNetAmountinUSD = vo.getTotalNetAmountinUSD().plusEquals(rs.getDouble("BLDAMT"));
						vo.setTotalNetAmountinUSD(totalNetAmountinUSD);
					}
					if(null != vo.getTotalNetAmount()){
						Money totalNetAmount = getMoneyObject();
						totalNetAmount = vo.getTotalNetAmount().plusEquals(rs.getDouble("BLDAMT"));
						vo.setTotalNetAmount(totalNetAmount);
					}
					vo.setCountofDocuments(detailCount);
				}
		}
			
	}
		this.dsnNumber = dsnNumber;
		this.mailSubClassGroup = mailSubClsGroup;
		this.mailCategory = mailCategory;
		this.mailClass = mailClass;	
		this.mailSubClass = mailSubClass;
		this.rate = mailRate;
		this.lastBillingType = billingType;
		this.sisInvoiceMessageVO.setSisSubInvoiceMessageVOs(sisSubInvoiceMessageVOs);
	}
	
	/**
	 * Method to create Money object.
	 *
	 * @param money
	 * @return
	 * @throws SQLException
	 */
	private Money getMoneyObject() throws SQLException {
		Money money = null;
		try {
			money = CurrencyHelper.getMoney(billingCurrency);
			money.setAmount(0);
		} catch (CurrencyException currencyException) {
			log.log(Log.SEVERE, " CurrencyException : ");
		}
		return money;
	}
	
	private SISChargeAmountMessageVO populateChargeAmountMessageVO(
			ResultSet rs, SISChargeAmountMessageVO sisChargeAmountMessageVO,
			String lineItemType) throws SQLException {
		Double amount = 0.0;
		if(lineItemType.equals(LINEITEM)){
		amount = rs.getDouble("BLDAMT");   //Modified by A-7929 as part of ICRD-265471
		}else if(lineItemType.equals(LINEITEM_DETAIL)){
			amount = rs.getDouble("BLDAMT");  //Modified by A-7929 as part of ICRD-265471
		}
		String billingType = rs.getString("BLGTYP");
		if(CREDIT_MEMO.equals(billingType) && amount >0){
			amount = amount * -1;
		}
		if (amount != null ) {
			if (sisChargeAmountMessageVO == null) {
				sisChargeAmountMessageVO = new SISChargeAmountMessageVO();
			}
			Money money = getMoneyObject();
			money.setAmount(amount);
			sisChargeAmountMessageVO.setChargeAmount(money);
			sisChargeAmountMessageVO
					.setChargeAmountName(generateChargeAmountName(lineItemType));
		}
		return sisChargeAmountMessageVO;
	}
	
	private String generateChargeAmountName(String item) {
		String result = "";
		if (item.equals(LINEITEM)) {
			result = "Weight";
		} else if (item.equals(LINEITEM_DETAIL)) {
			result = "WeightBilled";
		}
		return result;
	}
	/**
	 * Method for populating transaction id in Transaction Header
	 * @return String
	 */
	public String constructTransactionId(){
		String transactionId=null;
		
		return transactionId;
	}
	
	private Collection<SISInvoiceMessageVO> populateInvoiceSummary(
			Collection<SISInvoiceMessageVO> sisInvoiceMessageVOs) throws SQLException {
		Collection<SISInvoiceMessageVO> tempSisInvoiceMessageVOs = sisInvoiceMessageVOs;
		sisInvoiceMessageVOs = new ArrayList<SISInvoiceMessageVO>();

		for (SISInvoiceMessageVO sisInvoiceMessageVO : tempSisInvoiceMessageVOs) {// Iterating Invoice
			Money totalAmount = null;
			Money totalLineItemAmount = null;
			Money totalAddOnChargeAmount = null;
			Money totalAmountInUSD = null;
			try {
				totalAmount = getMoneyObject();
				totalLineItemAmount = getMoneyObject();
				totalAddOnChargeAmount = getMoneyObject();
				totalAmountInUSD = getMoneyObject("USD");
			} catch (SQLException e) {
				log.log(Log.SEVERE, " SQLException : ");
			}
			//sisInvoiceMessageVO = populateSubInvoiceAttributes(sisInvoiceMessageVO);
			// InvoiceSummary-LineItemCount
			sisInvoiceMessageVO.setSisSubnvoiceCount(sisInvoiceMessageVO
					.getSisSubInvoiceMessageVOs().size());
			for (SISSubInvoiceMessageVO sisSubInvoiceMessageVO : sisInvoiceMessageVO
					.getSisSubInvoiceMessageVOs()) { // Iterating LineItem
				if (sisSubInvoiceMessageVO.getTotalNetAmount() != null) {
					Money tempTotalAmount = sisSubInvoiceMessageVO
							.getTotalNetAmount().clone();
					totalAmount = totalAmount.plusEquals(tempTotalAmount
							.getAmount());
				} 
				if(sisSubInvoiceMessageVO.getTotalNetAmountinUSD() != null){
					Money tempTotalAmountinUSD = sisSubInvoiceMessageVO
							.getTotalNetAmountinUSD().clone();
					totalAmountInUSD = totalAmountInUSD.plusEquals(tempTotalAmountinUSD.getAmount());
				}

				if (sisSubInvoiceMessageVO.getTotalAddOnChargeAmount()!= null) {
					Money tempTotalAddOnChargeAmount = sisSubInvoiceMessageVO.getTotalAddOnChargeAmount().clone();
					totalAddOnChargeAmount = totalAddOnChargeAmount.plusEquals(tempTotalAddOnChargeAmount
							.getAmount());
				}
				// For AddonCharge
			}
			// InvoiceSummary-TotalLineItemAmount
			if (totalLineItemAmount != null
					&& totalLineItemAmount.getAmount() != 0) {
				sisInvoiceMessageVO
						.setTotalSubInvoiceAmount(totalLineItemAmount);
			}
			if (totalAmount != null && totalAmount.getAmount() != 0) {
				// InvoiceSummary-TotalAmount
				sisInvoiceMessageVO.setTotalAmount(totalAmount);
				//InvoiceSummary- TotalAddOnChargeAmount
				sisInvoiceMessageVO.setTotalAddOnChargeAmount(totalAddOnChargeAmount); 
			} 
			if(totalAmountInUSD != null && totalAmountInUSD.getAmount() != 0){
				sisInvoiceMessageVO.setTotalAmountInClearanceCurrency(totalAmountInUSD);
			}
			sisInvoiceMessageVOs.add(sisInvoiceMessageVO);
		}
		return sisInvoiceMessageVOs;

	}
	
	
	/**
	 * Method is to populate sub invoice attributes. Method calculates
	 * ChargeAmount,AddOnCharges, TotalNetAmount and DetailCount for sub
	 * invoice or LineItem from LineItemDetails
	 *
	 * @param sisInvoiceMessageVO
	 * @return
	 * @throws SQLException
	 */
	private SISInvoiceMessageVO populateSubInvoiceAttributes(
			SISInvoiceMessageVO sisInvoiceMessageVO) throws SQLException {
		Collection<SISSubInvoiceMessageVO> invoices = new ArrayList<SISSubInvoiceMessageVO>();
		 
		for (SISSubInvoiceMessageVO sisSubInvoiceMessageVO : sisInvoiceMessageVO
				.getSisSubInvoiceMessageVOs()) {
			Money chargeAmount = getMoneyObject();
			// LineItem-ChargeAmount
			if (chargeAmount != null ) {
				SISChargeAmountMessageVO sisChargeAmountMessageVO = sisSubInvoiceMessageVO
						.getSisChargeAmountMessageVO();
				if (sisChargeAmountMessageVO == null) {
					sisChargeAmountMessageVO = new SISChargeAmountMessageVO();
				}
				sisChargeAmountMessageVO.setChargeAmount(chargeAmount);
				sisChargeAmountMessageVO
						.setChargeAmountName(generateChargeAmountName(LINEITEM));
				sisSubInvoiceMessageVO
						.setSisChargeAmountMessageVO(sisChargeAmountMessageVO);
			}
			invoices.add(sisSubInvoiceMessageVO);

		}
		sisInvoiceMessageVO.setSisSubInvoiceMessageVOs(invoices);

		return sisInvoiceMessageVO;
	}
	
	private void populateTransmissionSummary(SISMessageVO sisMessageVO)
	throws SQLException {
			// TransmissionSummary-InvoiceCount
				sisMessageVO.setInvoiceCount(sisMessageVO.getSisInvoiceMessageVOs().size());
					// TransmissionSummary-TotalAmount
				sisMessageVO.setTotalAmount(getTotalInvoiceAmount(sisMessageVO));
			//	sisMessageVO.getTotalAmount().s(this.billingCurrency);
}
	
	private Collection<Money> getTotalInvoiceAmount(SISMessageVO sisMessageVO)
	throws SQLException {
		Collection<Money> totalAmonts = new ArrayList<Money>();
		Money totalAmont = getMoneyObject("USD");
		for (SISInvoiceMessageVO sisInvoiceVO : sisMessageVO
		.getSisInvoiceMessageVOs()) {
			if (sisInvoiceVO.getTotalAmountInClearanceCurrency() != null) {
		Money tempAount = sisInvoiceVO.getTotalAmountInClearanceCurrency().clone();
		totalAmont = totalAmont.plusEquals(tempAount);
			}
		}
		totalAmonts.add(totalAmont);
		return totalAmonts;
}
	
		
			

		
	/**
	 * Method converts Money object to BigDecimal
	 * 
	 * @param value
	 * @return BigDecimal
	 */
	private BigDecimal convertMoneyToBigDecimal(Money value) {
		if(value == null){
			return new BigDecimal(0);
		}
		BigDecimal bigDecimal = new BigDecimal(value.getAmount());
		//bigDecimal = bigDecimal.setScale(3, BigDecimal.ROUND_HALF_UP);		
		return bigDecimal.setScale(3, BigDecimal.ROUND_HALF_EVEN);
	}
	/**
	 * Method to create Money object.
	 *
	 * @param currency
	 * @return
	 * @throws SQLException
	 */
	private Money getMoneyObject(String currency) throws SQLException {

		Money money = null;
		try {
			money = CurrencyHelper.getMoney(currency);

			money.setAmount(0);
		} catch (CurrencyException currencyException) {
			log.log(Log.SEVERE, " CurrencyException : ");
		}
	
		return money;
	}
	
	/**
	 * Method populates SISMemoAWBinSubInvoiceMessageVO for LineItemDetail node
	 * in XML. Each LineItemDetail here is grouped on basis of
	 * lastInvoiceNumber.
	 *
	 * @param rs
	 * @throws SQLException
	 */
	private void populateSISMemoAWBinSubInvoiceMessageVO(ResultSet rs)
			throws SQLException {


		String awbKey = rs.getString("BLDARL")+rs.getString("RECSEQNUM")+rs.getString("INVNUM");
		if(rs.getString("MEMNUM") != null){
			this.totalMemosCount++;
		}
		else{
			this.totalAwbsCount++;
		}
		boolean isNewRecord = false;
		String rejectionMemoKey =rs.getString("BLDARL")+ rs.getString("INVNUM") +  rs.getString("MEMNUM");
		String blgType = rs.getString("BLGTYP");
		if(!(REJECTION_MEMO.equals(blgType))){
			if ( !(awbKey.equals(this.lastAwbKey))) {
				isNewRecord = true;
			}
		}else{
			if(!(rejectionMemoKey.equals(this.lastRejectionMemoKey))){
				isNewRecord = true;
			}
		}
		Collection<SISMemoAWBinSubInvoiceMessageVO> sisMemoAWBinSubInvoiceMessageVOs = this.sisInvoiceMessageVO
				.getSisMemoAWBinSubInvoiceMessageVOs();
		if (sisMemoAWBinSubInvoiceMessageVOs == null) {
			sisMemoAWBinSubInvoiceMessageVOs = new ArrayList<SISMemoAWBinSubInvoiceMessageVO>();
		}

		if (isNewRecord) {
			SISMemoAWBinSubInvoiceMessageVO sisMemoAWBinSubInvoiceMessageVO = new SISMemoAWBinSubInvoiceMessageVO();
			this.detailNumber++;
			sisMemoAWBinSubInvoiceMessageVO.setDetailNumber(this.detailNumber);
			StringBuilder build = new StringBuilder();
			build.append("International Mail Billing-").append(rs.getString("SUBCLSGRP")).append("-").append(rs.getString("MALCLS")).append(" ").append(rs.getString("ORGCOD")).append(rs.getString("DSTCOD")).append("; Minimum Charge");
			sisMemoAWBinSubInvoiceMessageVO.setDescription(build.toString());
			//Modified by A-7794 as part of ICRD-287378
			if(rs.getDate("TOODAT") != null){
			sisMemoAWBinSubInvoiceMessageVO.setEndDate(rs.getDate("TOODAT").toString());
			}
			int linItemNumber = this.billinTypeMap.get(rs.getString("BLGTYP"));
			sisMemoAWBinSubInvoiceMessageVO.setInvoiceSeqenceNumber(linItemNumber);
			if(!this.lastBillingType.equals(rs.getString("BLGTYP"))){
				this.recordSequenceWithinBatch = 0;
			}
			this.recordSequenceWithinBatch++;
			sisMemoAWBinSubInvoiceMessageVO.setRecordSequenceWithinBatch(this.recordSequenceWithinBatch);
			SISXMLMessageQuantityVO quantityVO = new SISXMLMessageQuantityVO();
			Money money = getMoneyObject();
			Double amt =  rs.getDouble("C66QTY");
			money.setAmount(amt);
			quantityVO.setValue(money);
			quantityVO.setUomCode(KGM);
			sisMemoAWBinSubInvoiceMessageVO.setMemoAWBSubInvoiceQuantityVO(quantityVO);
			
			//sisMemoAWBinSubInvoiceMessageVO.setProductID(this.ownAirlineCode);//IASCB-46305
			//sisMemoAWBinSubInvoiceMessageVO.setAgreementID(rs.getString("RATCOD"));//IASCB-46305
			//sisMemoAWBinSubInvoiceMessageVO.setCommitmentID(rs.getString("RATLINIDR"));//IASCB-46305

			SISXMLMessageUnitPriceVO unitPriceVO = new SISXMLMessageUnitPriceVO();
			Money money1 = getMoneyObject();
			money1.setAmount(rs.getDouble("APLRAT"));
			unitPriceVO.setValue(money1);
			unitPriceVO.setSf(1);
			sisMemoAWBinSubInvoiceMessageVO.setMemoAWBSubInvoiceUnitPriceVO(unitPriceVO);
			
			SISChargeAmountMessageVO sisChargeAmountMessageVO = sisMemoAWBinSubInvoiceMessageVO
					.getSisChargeAmountMessageVO();
			sisChargeAmountMessageVO = populateChargeAmountMessageVO(rs,
					sisChargeAmountMessageVO, LINEITEM_DETAIL);
			sisMemoAWBinSubInvoiceMessageVO
			.setSisChargeAmountMessageVO(sisChargeAmountMessageVO);
			
			double netAmount = rs.getDouble("BLDAMT");   //Modified by A-7929 as part of ICRD-265471
			Money moneys = getMoneyObject();
			moneys.setAmount(netAmount);
			sisMemoAWBinSubInvoiceMessageVO.setTotalNetAmount(moneys); 
			
			
			SISXMLMessageMailDetailsVO maildetailsVO = new SISXMLMessageMailDetailsVO();
			maildetailsVO.setMailNo(rs.getString("DSN"));
			maildetailsVO.setMailCtaegory(rs.getString("MALCTGCOD"));
			//Modified by A-7794 as part of ICRD-287378
			maildetailsVO.setMailClass(rs.getString("MALCLS"));
			maildetailsVO.setNoOfBags(rs.getInt("NOMAIL"));
			maildetailsVO.setContainerNo("0");
			maildetailsVO.setBagNo("0");
			if(null != rs.getString("CSGDOCNUM")){
			maildetailsVO.setConsignment(rs.getString("CSGDOCNUM"));
			}else{
				maildetailsVO.setConsignment("0");
			}
			sisMemoAWBinSubInvoiceMessageVO.setMemoAWBSubInvoiceMailDetailsVO(maildetailsVO);
			
			SISXMLMessageFlightDetailsVO flightVO = new SISXMLMessageFlightDetailsVO();
			//Modified by A-7794 as part of ICRD-287378
			if(rs.getString("BLGTYP").equals("O")){
			flightVO.setFlightNo(rs.getString("CARCOD")+ rs.getString("FLTNUM"));
			}else{
			flightVO.setFlightNo(rs.getString("CMPCOD")+ rs.getString("FLTNUM"));
			}
			flightVO.setFlightDateTime(new SimpleDateFormat(DATE_FORMAT_TRANSMISSION_HEADER).format(rs.getDate("FLTDAT")));
			sisMemoAWBinSubInvoiceMessageVO.setMemoAWBSubInvoiceFlightDetailsVO(flightVO);
			
			List<SISXMLMessageRouteDetailsVO> routeVos = new ArrayList<SISXMLMessageRouteDetailsVO>();
			SISXMLMessageRouteDetailsVO routeVO1 = new SISXMLMessageRouteDetailsVO();
			SISXMLMessageLocationCodeVO locationcode1 = new SISXMLMessageLocationCodeVO();
			locationcode1.setValue(rs.getString("ORGCOD"));
			locationcode1.setType("Origin");
			routeVO1.setLocationCode(locationcode1);
			routeVos.add(routeVO1);
			SISXMLMessageRouteDetailsVO routeVO = new SISXMLMessageRouteDetailsVO();
			routeVO.setLocationCode(locationcode1);
			SISXMLMessageLocationCodeVO locationcode = new SISXMLMessageLocationCodeVO();
			locationcode.setValue(rs.getString("DSTCOD"));
			locationcode.setType("Destination");
			routeVO.setLocationCode(locationcode);
			
			routeVos.add(routeVO);
			sisMemoAWBinSubInvoiceMessageVO.setMemoAWBSubInvoiceRouteDetailsVOs(routeVos);
			
			sisMemoAWBinSubInvoiceMessageVOs
			.add(sisMemoAWBinSubInvoiceMessageVO);
			sisMemoawbinSubInvoiceVOMap.put(awbKey,sisMemoAWBinSubInvoiceMessageVO);
		} 
		this.lastAwbKey = awbKey;

		this.sisInvoiceMessageVO
		.setSisMemoAWBinSubInvoiceMessageVOs(sisMemoAWBinSubInvoiceMessageVOs);
	}
	
}
