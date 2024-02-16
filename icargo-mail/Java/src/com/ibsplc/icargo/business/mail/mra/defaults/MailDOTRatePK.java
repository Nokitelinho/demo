/*
 * MailDOTRatePK.java Created on Aug 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;

/**
 * @author A-2408
 *
 */
/**
 * @author A-2408
 *
 */
public class MailDOTRatePK implements Serializable{

	private String companyCode;
	
	private String sectorOrigin;
	
	private String sectorDestination;
	
	private Double circleMiles; 
	
	private String rateCode;
	/**
	 * 
	 */
	public MailDOTRatePK(){
		
	}
	 
  
/**
 * @param companyCode
 * @param sectorOrigin
 * @param sectorDestination
 * @param circleMiles
 * @param rateCode
 */
public MailDOTRatePK(String companyCode,String sectorOrigin,String sectorDestination,Double circleMiles,String rateCode) {
       super();
       this.companyCode = companyCode;
       this.sectorOrigin = sectorOrigin;
       this.sectorDestination = sectorDestination;
       this.circleMiles = circleMiles;
       this.rateCode = rateCode;
       
   }

   /**
    * @param other
    * @return boolean
    */
	public boolean equals(Object other) {
		if(other != null ){
			return (hashCode() == other.hashCode());
		}else {
			return false;
		}
	}

	/**
	 * @return int
	 */
	public int hashCode() {
		return new StringBuffer(companyCode).
				append(sectorOrigin).
				append(sectorDestination).
				append(circleMiles).
				append(rateCode).
				toString().hashCode();
	}

	/**
	 * @return String
	 */
	public String toString(){
		return new StringBuffer().append("companyCode=")
						.append(companyCode)
						.append("\nsectorOrigin=")
						.append(sectorOrigin)
						.append("\nsectorDestination=")
						.append(sectorDestination)
						.append("\ncircleMiles=")
						.append(circleMiles)
						.append("\nrateCode=")
						.append(rateCode)
						.toString() ;
	}


	/**
	 * @return Returns the circleMiles.
	 */
	public Double getCircleMiles() {
		return circleMiles;
	}


	/**
	 * @param circleMiles The circleMiles to set.
	 */
	public void setCircleMiles(Double circleMiles) {
		this.circleMiles = circleMiles;
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
	 * @return Returns the rateCode.
	 */
	public String getRateCode() {
		return rateCode;
	}


	/**
	 * @param rateCode The rateCode to set.
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}


	/**
	 * @return Returns the sectorDestination.
	 */
	public String getSectorDestination() {
		return sectorDestination;
	}


	/**
	 * @param sectorDestination The sectorDestination to set.
	 */
	public void setSectorDestination(String sectorDestination) {
		this.sectorDestination = sectorDestination;
	}


	/**
	 * @return Returns the sectorOrigin.
	 */
	public String getSectorOrigin() {
		return sectorOrigin;
	}


	/**
	 * @param sectorOrigin The sectorOrigin to set.
	 */
	public void setSectorOrigin(String sectorOrigin) {
		this.sectorOrigin = sectorOrigin;
	}
	
	
}
