
	/*
	 *  MRAIrregularityForm.java Created on Sep 24, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

	import com.ibsplc.icargo.framework.model.ScreenModel;


	/**
	 * @author A-3229
	 *
	 */
	public class MRAIrregularityForm extends ScreenModel {
		
		
		private static final String BUNDLE = "irregularityresources";
		private static final String PRODUCT = "mail";
		private static final String SUBPRODUCT = "mra.defaults";
		private static final String SCREENID = "mailtracking.mra.defaults.irregularity";
		
		private String fromDate;
		
		private String toDate;
		
		private String irpStatus;
		
		private String offloadStation;
		
		private String origin;
		
		private String destination;
			
		private String effectiveDate;
				
		
		private String[] dsnNumber;
		
		private String[] executionDate;
		
		private String[] mop;
		
		private String[] flightNumber;
		
		private String[] flightDate;
		
		private String[] offloadDate;
		
		private String[] offloadStationCode;
		
		private String[] offloadWeight;
		
		private String[] resFlightNumber;
		               
	    private String[] resFlightDate;
	    
	    private String[] awbOrigin;
	                   
	    private String[] awbDestination;
	                                  
	    private String[] weight;
	                   
	    private String[]  route;   
	    
	    private String[] irregularityStatus;
	                  
	    private String[] freightCharges;
	    
	    private String[] totalCharges;
	                   
	    private String totalFreightCharges;
	    
	    private String total;
	    
	    private String dsn;
	    
	    private String billingBasis;
	    
	    private String csgSequenceNumber;
	    
		private String csgDocumentNumber;
		
		private String poaCode;

	    private String[] selectCheckBox;
	
	    
	   

		/**
		 * @return Returns the SCREENID.
		 */
	    public String getScreenId() {
	        return SCREENID;
	    }

	    /**
		 * @return Returns the PRODUCT.
		 */
	    public String getProduct() {
	        return PRODUCT;
	    }
	    /**
		 * @return Returns the SUBPRODUCT.
		 */
	    public String getSubProduct() {
	        return SUBPRODUCT;
	    }
	    /**
		 * @return Returns the bundle.
		 */
		public String getBundle() {
			return BUNDLE;
		
		}
		/**
		 * @return Returns the awbDestination
		 */
		public String[] getAwbDestination() {
			return awbDestination;
		}
		/**
		 * @param awbDestination the awbDestination to set
		 */
		public void setAwbDestination(String[] awbDestination) {
			this.awbDestination = awbDestination;
		}
		/**
		 * @return Returns the awbNumber
		 */
		public String[] getDsnNumber() {
			return dsnNumber;
		}
		/**
		 * @param dsnNumber the dsnNumber to set
		 */
		public void setDsnNumber(String[] dsnNumber) {
			this.dsnNumber = dsnNumber;
		}
		/**
		 * @return Returns the destination
		 */
		public String getDestination() {
			return destination;
		}
		/**
		 * @param destination the destination to set
		 */
		public void setDestination(String destination) {
			this.destination = destination;
		}
	
		/**
		 * @return Returns the effectiveDate
		 */
		public String getEffectiveDate() {
			return effectiveDate;
		}
		/**
		 * @param effectiveDate the effectiveDate to set
		 */
		public void setEffectiveDate(String effectiveDate) {
			this.effectiveDate = effectiveDate;
		}
		/**
		 * @return Returns the executionDate
		 */
		public String[] getExecutionDate() {
			return executionDate;
		}
		/**
		 * @param executionDate the executionDate to set
		 */
		public void setExecutionDate(String[] executionDate) {
			this.executionDate = executionDate;
		}
		/**
		 * @return Returns the flightDate
		 */
		public String[] getFlightDate() {
			return flightDate;
		}
		/**
		 * @param flightDate the flightDate to set
		 */
		public void setFlightDate(String[] flightDate) {
			this.flightDate = flightDate;
		}
		/**
		 * @return Returns the flightNumber
		 */
		public String[] getFlightNumber() {
			return flightNumber;
		}
		/**
		 * @param flightNumber the flightNumber to set
		 */
		public void setFlightNumber(String[] flightNumber) {
			this.flightNumber = flightNumber;
		}
		/**
		 * @return Returns the freightCharges
		 */
		public String[] getFreightCharges() {
			return freightCharges;
		}
		/**
		 * @param freightCharges the freightCharges to set
		 */
		public void setFreightCharges(String[] freightCharges) {
			this.freightCharges = freightCharges;
		}
		/**
		 * @return Returns the fromDate
		 */
		public String getFromDate() {
			return fromDate;
		}
		/**
		 * @param fromDate the fromDate to set
		 */
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		/**
		 * @return Returns the irpStatus
		 */
		public String getIrpStatus() {
			return irpStatus;
		}
		/**
		 * @param irpStatus the irpStatus to set
		 */
		public void setIrpStatus(String irpStatus) {
			this.irpStatus = irpStatus;
		}
		/**
		 * @return Returns the irregularityStatus
		 */
		public String[] getIrregularityStatus() {
			return irregularityStatus;
		}
		/**
		 * @param irregularityStatus the irregularityStatus to set
		 */
		public void setIrregularityStatus(String[] irregularityStatus) {
			this.irregularityStatus = irregularityStatus;
		}
		/**
		 * @return Returns the mop
		 */
		public String[] getMop() {
			return mop;
		}
		/**
		 * @param mop the mop to set
		 */
		public void setMop(String[] mop) {
			this.mop = mop;
		}
		/**
		 * @return Returns the offloadDate
		 */
		public String[] getOffloadDate() {
			return offloadDate;
		}
		/**
		 * @param offloadDate the offloadDate to set
		 */
		public void setOffloadDate(String[] offloadDate) {
			this.offloadDate = offloadDate;
		}
		/**
		 * @return Returns the offloadStation
		 */
		public String getOffloadStation() {
			return offloadStation;
		}
		/**
		 * @param offloadStation the offloadStation to set
		 */
		public void setOffloadStation(String offloadStation) {
			this.offloadStation = offloadStation;
		}
		/**
		 * @return Returns the offloadStationCode
		 */
		public String[] getOffloadStationCode() {
			return offloadStationCode;
		}
		/**
		 * @param offloadStationCode the offloadStationCode to set
		 */
		public void setOffloadStationCode(String[] offloadStationCode) {
			this.offloadStationCode = offloadStationCode;
		}
		/**
		 * @return Returns the offloadWeight
		 */
		public String[] getOffloadWeight() {
			return offloadWeight;
		}
		/**
		 * @param offloadWeight the offloadWeight to set
		 */
		public void setOffloadWeight(String[] offloadWeight) {
			this.offloadWeight = offloadWeight;
		}
		/**
		 * @return Returns the origin
		 */
		public String getOrigin() {
			return origin;
		}
		/**
		 * @param origin the origin to set
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		/**
		 * @return Returns the resFlightDate
		 */
		public String[] getResFlightDate() {
			return resFlightDate;
		}
		/**
		 * @param resFlightDate the resFlightDate to set
		 */
		public void setResFlightDate(String[] resFlightDate) {
			this.resFlightDate = resFlightDate;
		}
		/**
		 * @return Returns the resFlightNumber
		 */
		public String[] getResFlightNumber() {
			return resFlightNumber;
		}
		/**
		 * @param resFlightNumber the resFlightNumber to set
		 */
		public void setResFlightNumber(String[] resFlightNumber) {
			this.resFlightNumber = resFlightNumber;
		}
		/**
		 * @return Returns the route
		 */
		public String[] getRoute() {
			return route;
		}
		/**
		 * @param route the route to set
		 */
		public void setRoute(String[] route) {
			this.route = route;
		}
	
		/**
		 * @return Returns the toDate
		 */
		public String getToDate() {
			return toDate;
		}
		/**
		 * @param toDate the toDate to set
		 */
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		/**
		 * @return Returns the weight
		 */
		public String[] getWeight() {
			return weight;
		}
		/**
		 * @param weight the weight to set
		 */
		public void setWeight(String[] weight) {
			this.weight = weight;
		}
		/**
		 * @return Returns the totalFreightCharges
		 */
		public String getTotalFreightCharges() {
			return totalFreightCharges;
		}
		/**
		 * @param totalFreightCharges the totalFreightCharges to set
		 */
		public void setTotalFreightCharges(String totalFreightCharges) {
			this.totalFreightCharges = totalFreightCharges;
		}
		/**
		 * @return Returns the awbOrigin
		 */
		public String[] getAwbOrigin() {
			return awbOrigin;
		}
		/**
		 * @param awbOrigin the awbOrigin to set
		 */

		public void setAwbOrigin(String[] awbOrigin) {
			this.awbOrigin = awbOrigin;
		}
		/**
		 * @return Returns the totalCharges
		 */
		public String[] getTotalCharges() {
			return totalCharges;
		}
		/**
		 * @param totalCharges the totalCharges to set
		 */
		public void setTotalCharges(String[] totalCharges) {
			this.totalCharges = totalCharges;
		}

		
		/**
		 * @return Returns the selectCheckBoxr
		 */
		public String[] getSelectCheckBox() {
			return selectCheckBox;
		}
		/**
		 * @param selectCheckBox the selectCheckBox to set
		 */

		public void setSelectCheckBox(String[] selectCheckBox) {
			this.selectCheckBox = selectCheckBox;
		}
		/**
		 * @return Returns the dsn
		 */
		public String getDsn() {
			return dsn;
		}

		/**
		 * @param dsn the dsn to set
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}
		/**
		 * @return Returns the total
		 */
		public String getTotal() {
			return total;
		}

		/**
		 * @param total the total to set
		 */
		public void setTotal(String total) {
			this.total = total;
		}
		/**
		 * @return Returns the billingBasis
		 */
		public String getBillingBasis() {
			return billingBasis;
		}

		/**
		 * @param billingBasis the billingBasis to set
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
		}
		/**
		 * @return Returns the csgDocumentNumber
		 */
		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}

		/**
		 * @param csgDocumentNumber the csgDocumentNumber to set
		 */
		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}
		/**
		 * @return Returns the csgSequenceNumber
		 */
		public String getCsgSequenceNumber() {
			return csgSequenceNumber;
		}
		/**
		 * @param csgSequenceNumber the csgSequenceNumber to set
		 */

		public void setCsgSequenceNumber(String csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}
		/**
		 * @return Returns the poaCode
		 */
		public String getPoaCode() {
			return poaCode;
		}
		/**
		 * @param poaCode the poaCode to set
		 */

		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}
		

	
	
}
