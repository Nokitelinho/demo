/*
	* DSNRoutingVO.java Created on Sep 1, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	 package com.ibsplc.icargo.business.mail.mra.defaults.vo;

	import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;

	/**
	 * @author A-3229
	 *
	 */
	 public class DSNRoutingVO extends AbstractVO {

		private String companyCode;
		
		private long mailSequenceNumber;

		private String billingBasis;
		
		private int routingSerialNumber;
		
		private int csgSequenceNumber;
		
		private String csgDocumentNumber;
		
		private String poaCode;
		
		private String dsn;

		private LocalDate dsnDate;

		private String flightCarrierCode;

		private int flightCarrierId;

		private String flightNumber;

		private LocalDate departureDate;

		private String pol;

		private String pou;

		private String billingStatus;

		private String agreementType;
		private String blockSpaceType;
		private String bsaReference;
		
		private String operationFlag;
		
		private int nopieces;
		
		private double weight;
		
		private int acctualnopieces;
		
		private double acctualweight;
		
		private String ownairlinecode;
		
		private int legsernum;
		
		private String triggerPoint;
		
		 private LocalDate lastUpdateTime;
		 private String lastUpdateUser;	
	
		 public static final String INVALID_PRORATEEXCEPTION="X";
		 //added for bug ICRD-2170
		 private long flightSeqnum;
		 
		 private String route;
		 
		 private String mailbagId;
		 
		 private int segmentSerialNumber;
		 
		 private String flightType;
		 
		 private String interfacedFlag;
		 
		 // added for IASCB-22920 by A-9002		 
		 private double displayWgt;	 
	     private String displayWgtUnit;
	     private String source;
	 	private String transferPA;
		private String transferAirline;
		 
	    public double getDisplayWgt() {
			return displayWgt;
		}

		public void setDisplayWgt(double displayWgt) {
			this.displayWgt = displayWgt;
		}

		public String getDisplayWgtUnit() {
			return displayWgtUnit;
		}
        public void setDisplayWgtUnit(String displayWgtUnit) {
			this.displayWgtUnit = displayWgtUnit;
		}

		 //Ends
		/**
		 * @return the segmentSerialNumber
		 */
		public int getSegmentSerialNumber() {
			return segmentSerialNumber;
		}




		/**
		 * @param segmentSerialNumber the segmentSerialNumber to set
		 */
		public void setSegmentSerialNumber(int segmentSerialNumber) {
			this.segmentSerialNumber = segmentSerialNumber;
		}




		/**
		 * @return the lastUpdateTime
		 */
		public LocalDate getLastUpdateTime() {
			return lastUpdateTime;
		}




		/**
		 * @param lastUpdateTime the lastUpdateTime to set
		 */
		public void setLastUpdateTime(LocalDate lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}




		/**
		 * @return the lastUpdateUser
		 */
		public String getLastUpdateUser() {
			return lastUpdateUser;
		}




		/**
		 * @param lastUpdateUser the lastUpdateUser to set
		 */
		public void setLastUpdateUser(String lastUpdateUser) {
			this.lastUpdateUser = lastUpdateUser;
		}




		/**
		 * @return the triggerPoint
		 */
		public String getTriggerPoint() {
			return triggerPoint;
		}




		/**
		 * @param triggerPoint the triggerPoint to set
		 */
		public void setTriggerPoint(String triggerPoint) {
			this.triggerPoint = triggerPoint;
		}




		public DSNRoutingVO(){

		}




		/**
		 * @return Returns the dsn.
		 */
		public String getDsn() {
			return dsn;
		}



		/**
		 * @param dsn The dsn to set.
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}




		/**
		 * @return Returns the dsn.
		 */

		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 *
		 * @param companyCode
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}



		/**
		 * @return Returns the dsnDate.
		 */
		public LocalDate getDsnDate() {
			return dsnDate;
		}



		/**
		 * @param dsnDate The dsnDate to set.
		 */
		public void setDsnDate(LocalDate dsnDate) {
			this.dsnDate = dsnDate;
		}



		/**
		 * @return Returns the billingBasis.
		 */

		public String getBillingBasis() {
			return billingBasis;
		}


		/**
		 * @param billingBasis The billingBasis to set.
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
		}


		/**
		 * @return Returns the flightNumber.
		 */
		public String getFlightNumber() {
			return flightNumber;
		}

		/**
		 * @param flightNumber The flightNumber to set.
		 */

		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}

		/**
		 * @return Returns the poaCode.
		 */

		public String getPoaCode() {
			return poaCode;
		}


		/**
		 * @param poaCode The poaCode to set.
		 */
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}

		/**
		 * @return Returns the pol.
		 */

		public String getPol() {
			return pol;
		}

		/**
		 * @param pol The pol to set.
		 */

		public void setPol(String pol) {
			this.pol = pol;
		}


		/**
		 * @return Returns the pou.
		 */

		public String getPou() {
			return pou;
		}

		/**
		 * @param pou The pou to set.
		 */

		public void setPou(String pou) {
			this.pou = pou;
		}
		/**
		 * @return Returns the agreementType.
		 */
		public String getAgreementType() {
			return agreementType;
		}
		/**
		 * @param agreementType The agreementType to set.
		 */
		public void setAgreementType(String agreementType) {
			this.agreementType = agreementType;
		}

		


		/**
		 * @return Returns the operationFlag.
		 */
		public String getOperationFlag() {
			return operationFlag;
		}

		/**
		 * @param operationFlag
		 *            The operationFlag to set.
		 */
		public void setOperationFlag(String operationFlag) {
			this.operationFlag = operationFlag;
		}


		/**
		 * @return Returns the departureDate.
		 */

		public LocalDate getDepartureDate() {
			return departureDate;
		}


		/**
		 * @param departureDate
		 *            The departureDate to set.
		 */

		public void setDepartureDate(LocalDate departureDate) {
			this.departureDate = departureDate;
		}


		/**
		 * @return Returns the flightCarrierCode.
		 */

		public String getFlightCarrierCode() {
			return flightCarrierCode;
		}


		/**
		 * @param flightCarrierCode
		 *            The flightCarrierCode to set.
		 */

		public void setFlightCarrierCode(String flightCarrierCode) {
			this.flightCarrierCode = flightCarrierCode;
		}


		/**
		 * @return Returns the flightCarrierId.
		 */

		public int getFlightCarrierId() {
			return flightCarrierId;
		}

		/**
		 * @param flightCarrierId
		 *            The flightCarrierId to set.
		 */


		public void setFlightCarrierId(int flightCarrierId) {
			this.flightCarrierId = flightCarrierId;
		}


		/**
		 * @return Returns the routingSerialNumber.
		 */

		public int getRoutingSerialNumber() {
			return routingSerialNumber;
		}

		/**
		 * @param routingSerialNumber
		 *            The routingSerialNumber to set.
		 */


		public void setRoutingSerialNumber(int routingSerialNumber) {
			this.routingSerialNumber = routingSerialNumber;
		}


		/**
		 * @return Returns the csgDocumentNumber.
		 */

		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}

		/**
		 * @param csgDocumentNumber
		 *            The csgDocumentNumber to set.
		 */


		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}

		/**
		 * @return Returns the csgSequenceNumber.
		 */


		public int getCsgSequenceNumber() {
			return csgSequenceNumber;
		}


		/**
		 * @param csgSequenceNumber
		 *            The csgSequenceNumber to set.
		 */

		public void setCsgSequenceNumber(int csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}




		/**
		 * @return the nopieces
		 */
		public int getNopieces() {
			return nopieces;
		}




		/**
		 * @param nopieces the nopieces to set
		 */
		public void setNopieces(int nopieces) {
			this.nopieces = nopieces;
		}




		/**
		 * @return the weight
		 */
		public double getWeight() {
			return weight;
		}




		/**
		 * @param weight the weight to set
		 */
		public void setWeight(double weight) {
			this.weight = weight;
		}




		/**
		 * @return the acctualnopieces
		 */
		public int getAcctualnopieces() {
			return acctualnopieces;
		}




		/**
		 * @param acctualnopieces the acctualnopieces to set
		 */
		public void setAcctualnopieces(int acctualnopieces) {
			this.acctualnopieces = acctualnopieces;
		}




		/**
		 * @return the acctualweight
		 */
		public double getAcctualweight() {
			return acctualweight;
		}




		/**
		 * @param acctualweight the acctualweight to set
		 */
		public void setAcctualweight(double acctualweight) {
			this.acctualweight = acctualweight;
		}




		/**
		 * @return the ownairlinecode
		 */
		public String getOwnairlinecode() {
			return ownairlinecode;
		}




		/**
		 * @param ownairlinecode the ownairlinecode to set
		 */
		public void setOwnairlinecode(String ownairlinecode) {
			this.ownairlinecode = ownairlinecode;
		}




		/**
		 * @return the legsernum
		 */
		public int getLegsernum() {
			return legsernum;
		}




		/**
		 * @param legsernum the legsernum to set
		 */
		public void setLegsernum(int legsernum) {
			this.legsernum = legsernum;
		}




		/**
		 * @return the flightSeqnum
		 */
		public long getFlightSeqnum() {
			return flightSeqnum;
		}




		/**
		 * @param flightSeqnum the flightSeqnum to set
		 */
		public void setFlightSeqnum(long flightSeqnum) {
			this.flightSeqnum = flightSeqnum;
		}




		/**
		 * @param route the route to set
		 */
		public void setRoute(String route) {
			this.route = route;
		}




		/**
		 * @return the route
		 */
		public String getRoute() {
			return route;
		}
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}

		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}




		public String getMailbagId() {
			return mailbagId;
		}




		public void setMailbagId(String mailbagId) {
			this.mailbagId = mailbagId;
		}




		/**
		 * 	Getter for flightType 
		 *	Added by : a-8061 on 27-Jul-2018
		 * 	Used for :
		 */
		public String getFlightType() {
			return flightType;
		}




		/**
		 *  @param flightType the flightType to set
		 * 	Setter for flightType 
		 *	Added by : a-8061 on 27-Jul-2018
		 * 	Used for :
		 */
		public void setFlightType(String flightType) {
			this.flightType = flightType;
		}








		/**
		 * 
		 * @return blockSpaceType
		 */
		public String getBlockSpaceType() {
			return blockSpaceType;
		}



		/**
		 * 
		 * @param blockSpaceType
		 */
		public void setBlockSpaceType(String blockSpaceType) {
			this.blockSpaceType = blockSpaceType;
		}



		/**
		 * 
		 * @return bsaReference
		 */
		public String getBsaReference() {
			return bsaReference;
		}



		/**
		 * 
		 * @param bsaReference
		 */
		public void setBsaReference(String bsaReference) {
			this.bsaReference = bsaReference;
		}




		/**
		 * 	Getter for interfacedFlag 
		 *	Added by : a-8061 on 22-Aug-2018
		 * 	Used for :
		 */
		public String getInterfacedFlag() {
			return interfacedFlag;
		}




		/**
		 *  @param interfacedFlag the interfacedFlag to set
		 * 	Setter for interfacedFlag 
		 *	Added by : a-8061 on 22-Aug-2018
		 * 	Used for :
		 */
		public void setInterfacedFlag(String interfacedFlag) {
			this.interfacedFlag = interfacedFlag;
		}
		
		/**
		 * 
		 * 	Method		:	DSNRoutingVO.geSource
		 *	Added by 	:	A-5219 on 02-Apr-2020
		 * 	Used for 	:
		 *	Parameters	:	@return 
		 *	Return type	: 	String
		 */
		public String getSource(){
			return source;
		}
		
		/**
		 * 
		 * 	Method		:	DSNRoutingVO.setSource
		 *	Added by 	:	A-5219 on 02-Apr-2020
		 * 	Used for 	:
		 *	Parameters	:	@param source 
		 *	Return type	: 	void
		 */
		public void setSource(String source){
			this.source = source;
		}

		/**
		 * 	Getter for transferPA 
		 *	Added by : A-8061 on 06-Jan-2021
		 * 	Used for :
		 */
		public String getTransferPA() {
			return transferPA;
		}

		/**
		 *  @param transferPA the transferPA to set
		 * 	Setter for transferPA 
		 *	Added by : A-8061 on 06-Jan-2021
		 * 	Used for :
		 */
		public void setTransferPA(String transferPA) {
			this.transferPA = transferPA;
		}

		/**
		 * 	Getter for transferAirline 
		 *	Added by : A-8061 on 06-Jan-2021
		 * 	Used for :
		 */
		public String getTransferAirline() {
			return transferAirline;
		}

		/**
		 *  @param transferAirline the transferAirline to set
		 * 	Setter for transferAirline 
		 *	Added by : A-8061 on 06-Jan-2021
		 * 	Used for :
		 */
		public void setTransferAirline(String transferAirline) {
			this.transferAirline = transferAirline;
		}




		public String getBillingStatus() {
			return billingStatus;
		}




		public void setBillingStatus(String billingStatus) {
			this.billingStatus = billingStatus;
		}

		
	}
