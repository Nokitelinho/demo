package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Calendar;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;

/**
 * @author A-7871
 * @version	0.1, Nov 07, 2017
 * 
 *
 */
public class RateLineAuditVO extends AuditVO{
	
	 /**
	    * Module name
	    */
	    public static final String AUDIT_MODULENAME = "mail";
	    /**
	     * submodule name
	     */
	    public static final String AUDIT_SUBMODULENAME = "mra";
	    /**
	     * Entity name  mailtracking.mra.gpareporting.GPAReportingDetails
	     */
	    public static final String AUDIT_ENTITY = "mail.mra.UPURateline";
	
	  
	private String rateCardID;
	private String origin;
	private String destination;
	private Calendar lastUpdateTime;
	private String lastUpdateUser;
	private Calendar lastUpdateDate;
	private int rateLineSerNum;
	private String level;
	 
	  public LocalDate getLastUpdateDate() {
		return (LocalDate) lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public RateLineAuditVO(String moduleName, String subModuleName,String entityName) {
			super(moduleName, subModuleName, entityName);
			
		}

	/**
	 * @return the lastUpdateTime
	 */
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param time the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar time) {
		this.lastUpdateTime = time;
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
	 * @return the rateCardID
	 */
	public String getRateCardID() {
		return rateCardID;
	}
	/**
	 * @param rateCardID the rateCardID to set
	 */
	public void setRateCardID(String rateCardID) {
		this.rateCardID = rateCardID;
	}
	/**
	 * @return the origin
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
	 * @return the destination
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
	 * 	Getter for rateLineSerNum 
	 *	Added by : A-5219 on 26-Oct-2020
	 * 	Used for :
	 */
	public int getRateLineSerNum() {
		return rateLineSerNum;
	}

	/**
	 *  @param rateLineSerNum the rateLineSerNum to set
	 * 	Setter for rateLineSerNum 
	 *	Added by : A-5219 on 26-Oct-2020
	 * 	Used for :
	 */
	public void setRateLineSerNum(int rateLineSerNum) {
		this.rateLineSerNum = rateLineSerNum;
	}

	/**
	 * 	Getter for level 
	 *	Added by : A-5219 on 26-Oct-2020
	 * 	Used for :
	 */
	public String getLevel() {
		return level;
	}

	/**
	 *  @param level the level to set
	 * 	Setter for level 
	 *	Added by : A-5219 on 26-Oct-2020
	 * 	Used for :
	 */
	public void setLevel(String level) {
		this.level = level;
	}


}
