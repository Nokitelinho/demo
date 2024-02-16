/*
 * TransactionVO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;


import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-2052
 *
 */
public class TransactionMicroVO extends AbstractVO{

		/**
		 * companyCode
		 */
	    private String companyCode;

	    /**
		 * transactionType
		 */
	    private String transactionType;

	    /**
		 * transactionNature
		 */
	    private String transactionNature;

	    /**
		 * transactionStation
		 */
	    private String transactionStation;

	    /**
		 * transactionDate
		 */
	    private String transactionDate;

	    /**
		 * strTransactionDate to display date in Screen
		 */
	    private String strTransactionDate;

	    /**
		 * transactionTime to display time in Screen
		 */
	    private String transactionTime;

	    /**
		 * transactionRemark
		 */
	    private String transactionRemark;

	    /**
		 * partyType
		 */
	    private String partyType;



	    /**
		 * partyCode
		 */
	    private String fromPartyCode;

	    /**
		 * partyName
		 */
	    private String fromPartyName;

	    /**
		 * partyCode
		 */
	    private String toPartyCode;

	    /**
		 * partyName
		 */
	    private String toPartyName;

	    /**
		 * currOwnerCode
		 */
	    private int  currOwnerCode;

	    private String  operationalFlag;

	    /**
		 * operationalAirlineIdentifier
		 */
	    private int operationalAirlineIdentifier;

	    /**
		 * transactionStatus
		 */
	    private String transactionStatus;

	      /**
		 * uldTransactionDetailsVOs
		 */
	    private ULDTransactionDetailsMicroVO[] ULDTransactionDetailsMicroVOs;

	    /**
		 * accessoryTransactionVOs
		 */
	    private AccessoryTransactionMicroVO[] accessoryTransactionMicroVOs;

	    /**
		 * @return Returns the operationalFlag.
		 */
		public String getOperationalFlag() {
			return operationalFlag;
		}
		/**
		 * @param operationalFlag The operationalFlag to set.
		 */
		public void setOperationalFlag(String operationalFlag) {
			this.operationalFlag = operationalFlag;
		}
		/**
		 * @return Returns the operationalAirlineIdentifier.
		 */
		public int getOperationalAirlineIdentifier() {
			return operationalAirlineIdentifier;
		}
		/**
		 * @param operationalAirlineIdentifier The operationalAirlineIdentifier to set.
		 */
		public void setOperationalAirlineIdentifier(int operationalAirlineIdentifier) {
			this.operationalAirlineIdentifier = operationalAirlineIdentifier;
		}
		/**
		 * @return Returns the accessoryTransactions.
		 */
		public AccessoryTransactionMicroVO[] getAccessoryTransactionMicroVOs() {
			return accessoryTransactionMicroVOs;
		}
		/**
		 * @param accessoryTransactions The accessoryTransactions to set.
		 */
		public void setAccessoryTransactionMicroVOs(AccessoryTransactionMicroVO[] accessoryTransactionMicroVOs) {
			this.accessoryTransactionMicroVOs = accessoryTransactionMicroVOs;
		}
		/**
		 * @return Returns the companyCode.
		 */
		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 * @param companyCode The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		/**
		 * @return Returns the transactionType.
		 */
		public String getTransactionType() {
			return transactionType;
		}
		/**
		 * @param transactionType The transactionType to set.
		 */
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		/**
		 * @return Returns the uldTransactionDetailsVOs.
		 */
		public ULDTransactionDetailsMicroVO[] getULDTransactionDetailsMicroVOs() {
			return ULDTransactionDetailsMicroVOs;
		}
		/**
		 * @param uldTransactionDetailsVOs The uldTransactionDetailsVOs to set.
		 */
		public void setULDTransactionDetailsMicroVOs(ULDTransactionDetailsMicroVO[] ULDTransactionDetailsMicroVOs) {
			this.ULDTransactionDetailsMicroVOs = ULDTransactionDetailsMicroVOs;
		}

		/**
		 * @return Returns the fromPartyCode.
		 */
		public String getFromPartyCode() {
			return fromPartyCode;
		}
		/**
		 * @param fromPartyCode The fromPartyCode to set.
		 */
		public void setFromPartyCode(String fromPartyCode) {
			this.fromPartyCode = fromPartyCode;
		}
		/**
		 * @return Returns the fromPartyName.
		 */
		public String getFromPartyName() {
			return fromPartyName;
		}
		/**
		 * @param fromPartyName The fromPartyName to set.
		 */
		public void setFromPartyName(String fromPartyName) {
			this.fromPartyName = fromPartyName;
		}
		/**
		 * @return Returns the toPartyCode.
		 */
		public String getToPartyCode() {
			return toPartyCode;
		}
		/**
		 * @param toPartyCode The toPartyCode to set.
		 */
		public void setToPartyCode(String toPartyCode) {
			this.toPartyCode = toPartyCode;
		}
		/**
		 * @return Returns the toPartyName.
		 */
		public String getToPartyName() {
			return toPartyName;
		}
		/**
		 * @param toPartyName The toPartyName to set.
		 */
		public void setToPartyName(String toPartyName) {
			this.toPartyName = toPartyName;
		}
		/**
		 * @return Returns the partyType.
		 */
		public String getPartyType() {
			return this.partyType;
		}
		/**
		 * @param partyType The partyType to set.
		 */
		public void setPartyType(String partyType) {
			this.partyType = partyType;
		}
		/**
		 * @return Returns the strTransactionDate.
		 */
		public String getStrTransactionDate() {
			return this.strTransactionDate;
		}
		/**
		 * @param strTransactionDate The strTransactionDate to set.
		 */
		public void setStrTransactionDate(String strTransactionDate) {
			this.strTransactionDate = strTransactionDate;
		}
		/**
		 * @return Returns the transactionDate.
		 */
		public String getTransactionDate() {
			return this.transactionDate;
		}
		/**
		 * @param transactionDate The transactionDate to set.
		 */
		public void setTransactionDate(String transactionDate) {
			this.transactionDate = transactionDate;
		}
		/**
		 * @return Returns the transactionNature.
		 */
		public String getTransactionNature() {
			return this.transactionNature;
		}
		/**
		 * @param transactionNature The transactionNature to set.
		 */
		public void setTransactionNature(String transactionNature) {
			this.transactionNature = transactionNature;
		}
		/**
		 * @return Returns the transactionRemark.
		 */
		public String getTransactionRemark() {
			return this.transactionRemark;
		}
		/**
		 * @param transactionRemark The transactionRemark to set.
		 */
		public void setTransactionRemark(String transactionRemark) {
			this.transactionRemark = transactionRemark;
		}
		/**
		 * @return Returns the transactionStation.
		 */
		public String getTransactionStation() {
			return this.transactionStation;
		}
		/**
		 * @param transactionStation The transactionStation to set.
		 */
		public void setTransactionStation(String transactionStation) {
			this.transactionStation = transactionStation;
		}
		/**
		 * @return Returns the currOwnerCode.
		 */
		public int getCurrOwnerCode() {
			return this.currOwnerCode;
		}
		/**
		 * @param currOwnerCode The currOwnerCode to set.
		 */
		public void setCurrOwnerCode(int currOwnerCode) {
			this.currOwnerCode = currOwnerCode;
		}
		/**
		 * @return Returns the transactionStatus.
		 */
		public String getTransactionStatus() {
			return this.transactionStatus;
		}
		/**
		 * @param transactionStatus The transactionStatus to set.
		 */
		public void setTransactionStatus(String transactionStatus) {
			this.transactionStatus = transactionStatus;
		}
		/**
		 * @return Returns the transactionTime.
		 */
		public String getTransactionTime() {
			return transactionTime;
		}
		/**
		 * @param transactionTime The transactionTime to set.
		 */
		public void setTransactionTime(String transactionTime) {
			this.transactionTime = transactionTime;
		}



	}
