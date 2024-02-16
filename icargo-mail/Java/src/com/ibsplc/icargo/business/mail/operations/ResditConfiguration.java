/*
 * ResditConfiguration.java Created on Feb 1, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.ResditConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditTransactionDetailVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 1, 2007			A-1739		Created
 */
@Entity
@Table(name="MALRDTCFG")
public class ResditConfiguration {

	private static final String MAILTRACKING_DEFAULTS = "mailtracking.defaults";
	
	private static final String MODULENAME = "mail.operations";
	
	private Log log = LogFactory.getLogger("MODULENAME");
	
	private ResditConfigurationPK resditConfigPK;
    
	private String receivedResditFlag;
    
	private String assignedResditFlag;
    
	private String upliftedResditFlag;
	
	private String handedOverResditFlag;
    
	private String loadedResditFlag;
	
	private String handedOverReceivedFlag;
	
	private String deliveredResditFlag;
	
	private String returnedResditFlag;
	
	private String readyForDeliveryResditFlag; 
	private String transportationCompletedResditFlag; 
	private String arrivedResditFlag; 
	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	/**
	 * For Hibernate Constructor.newInstance
	 *
	 */
	public ResditConfiguration() {
		
	}
	
	/**
	 * For persistence
	 * @param resditConfigVO
	 * @throws SystemException 
	 */
	public ResditConfiguration(ResditConfigurationPK resditConfigPK, 
			ResditTransactionDetailVO transactionVO) throws SystemException {
		log.entering("ResditConfiguration", "<init>");
		this.resditConfigPK = resditConfigPK;
		populateAttributes(transactionVO);		
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch(CreateException exception) {
			throw new SystemException(exception.getErrorCode(), exception);
		}
		log.exiting("ResditConfiguration", "<init>");
	}


	/**
	 * TODO Purpose
	 * Feb 1, 2007, A-1739
	 * @param resditConfigVO
	 */
	private void populateAttributes(ResditTransactionDetailVO transactionVO) {
		setReceivedResditFlag(transactionVO.getReceivedResditFlag());
		setAssignedResditFlag(transactionVO.getAssignedResditFlag());
		setHandedOverResditFlag(transactionVO.getHandedOverResditFlag());
		setUpliftedResditFlag(transactionVO.getUpliftedResditFlag());
		setLoadedResditFlag(transactionVO.getLoadedResditFlag());
		setHandedOverReceivedFlag(
				transactionVO.getHandedOverReceivedResditFlag());
		setDeliveredResditFlag(transactionVO.getDeliveredResditFlag());
		setReturnedResditFlag(transactionVO.getReturnedResditFlag());
		setReadyForDeliveryResditFlag(transactionVO.getReadyForDeliveryFlag());
		setTransportationCompletedResditFlag(transactionVO.getTransportationCompletedResditFlag());
		setArrivedResditFlag(transactionVO.getArrivedResditFlag());
		setLastUpdateUser(transactionVO.getLastUpdateUser());
	}

	/**
	 * @return Returns the assignedResditFlag.
	 */
	@Column(name="ASGRDTFLG")
	public String getAssignedResditFlag() {
		return assignedResditFlag;
	}

	/**
	 * @param assignedResditFlag The assignedResditFlag to set.
	 */	
	public void setAssignedResditFlag(String assignedResditFlag) {
		this.assignedResditFlag = assignedResditFlag;
	}

	/**
	 * @return Returns the departedResditFlag.
	 */
	@Column(name="DEPRDTFLG")
	public String getLoadedResditFlag() {
		return loadedResditFlag;
	}

	/**
	 * @param departedResditFlag The departedResditFlag to set.
	 */
	public void setLoadedResditFlag(String departedResditFlag) {
		this.loadedResditFlag = departedResditFlag;
	}

	/**
	 * @return Returns the handedOverResditFlag.
	 */
	@Column(name="HNDOVRFLG")
	public String getHandedOverResditFlag() {
		return handedOverResditFlag;
	}

	/**
	 * @param handedOverResditFlag The handedOverResditFlag to set.
	 */
	public void setHandedOverResditFlag(String handedOverResditFlag) {
		this.handedOverResditFlag = handedOverResditFlag;
	}

	/**
	 * @return Returns the receivedResditFlag.
	 */
	@Column(name="RCVRDTFLG")
	public String getReceivedResditFlag() {
		return receivedResditFlag;
	}

	/**
	 * @param receivedResditFlag The receivedResditFlag to set.
	 */
	public void setReceivedResditFlag(String receivedResditFlag) {
		this.receivedResditFlag = receivedResditFlag;
	}
	
  	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */

	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the resditConfigPK.
	 */
	@EmbeddedId 
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="carrierId", column=@Column(name="CARIDR")),
		@AttributeOverride(name="transactionId", column=@Column(name="TXNCOD"))
		}
	)
	public ResditConfigurationPK getResditConfigPK() {
		return resditConfigPK;
	}

	/**
	 * @param resditConfigPK The resditConfigPK to set.
	 */
	public void setResditConfigPK(ResditConfigurationPK resditConfigPK) {
		this.resditConfigPK = resditConfigPK;
	}

	/**
	 * @return Returns the upliftedResditFlag.
	 */
	@Column(name="UPLRDTFLG")
	public String getUpliftedResditFlag() {
		return upliftedResditFlag;
	}

	/**
	 * @param upliftedResditFlag The upliftedResditFlag to set.
	 */
	public void setUpliftedResditFlag(String upliftedResditFlag) {
		this.upliftedResditFlag = upliftedResditFlag;
	}

	/**
	 * @return the deliveredResditFlag
	 */
	@Column(name="DLVRDTFLG")
	public String getDeliveredResditFlag() {
		return deliveredResditFlag;
	}

	/**
	 * @param deliveredResditFlag the deliveredResditFlag to set
	 */
	public void setDeliveredResditFlag(String deliveredResditFlag) {
		this.deliveredResditFlag = deliveredResditFlag;
	}

	/**
	 * @return the returnedResditFlag
	 */
	@Column(name="RETRDTFLG")
	public String getReturnedResditFlag() {
		return returnedResditFlag;
	}

	/**
	 * @param returnedResditFlag the returnedResditFlag to set
	 */
	public void setReturnedResditFlag(String returnedResditFlag) {
		this.returnedResditFlag = returnedResditFlag;
	}
	/**
	 * 	Getter for readyForDeliveryResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */	
	@Column(name="RDYDLVRDTFLG")
	public String getReadyForDeliveryResditFlag() {
		return readyForDeliveryResditFlag;
	}
	/**
	 *  @param readyForDeliveryResditFlag the readyForDeliveryResditFlag to set
	 * 	Setter for readyForDeliveryResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setReadyForDeliveryResditFlag(String readyForDeliveryResditFlag) {
		this.readyForDeliveryResditFlag = readyForDeliveryResditFlag;
	}
	/**
	 * 	Getter for transportationCompletedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	@Column(name="TRTCPLRDTFLG")
	public String getTransportationCompletedResditFlag() {
		return transportationCompletedResditFlag;
	}
	/**
	 *  @param transportationCompletedResditFlag the transportationCompletedResditFlag to set
	 * 	Setter for transportationCompletedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setTransportationCompletedResditFlag(
			String transportationCompletedResditFlag) {
		this.transportationCompletedResditFlag = transportationCompletedResditFlag;
	}
	/**
	 * 	Getter for arrivedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	@Column(name="ARRRDTFLG")
	public String getArrivedResditFlag() {
		return arrivedResditFlag;
	}
	/**
	 *  @param arrivedResditFlag the arrivedResditFlag to set
	 * 	Setter for arrivedResditFlag 
	 *	Added by : A-5201 on 13-Oct-2014
	 * 	Used for :
	 */
	public void setArrivedResditFlag(String arrivedResditFlag) {
		this.arrivedResditFlag = arrivedResditFlag;
	}
	
	public static ResditConfigurationVO findResditConfigurationForAirline(
			String companyCode, int carrierId) throws SystemException {		
		try {
			return constructDAO().findResditConfurationForAirline(
					companyCode, carrierId);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getErrorCode(), exception);
		}
	}
	
	private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
        try {
            return MailTrackingDefaultsDAO.class.cast(
            		PersistenceController.getEntityManager().getQueryDAO(
            				MODULENAME));
        } catch(PersistenceException exception) {
            throw new SystemException("No dao impl found", exception);
        }
    }

	/**
	 * TODO Purpose
	 * Feb 1, 2007, A-1739
	 * @param resditConfigurationPK
	 * @return
	 * @throws SystemException 
	 * @throws FinderException 
	 */
	public static ResditConfiguration find(
			ResditConfigurationPK resditConfigurationPK) 
	throws FinderException, SystemException {		
		return PersistenceController.getEntityManager().find(
				ResditConfiguration.class, resditConfigurationPK);
	}

	/**
	 * TODO Purpose
	 * Feb 1, 2007, A-1739
	 * @param txnDetailsVO
	 */
	public void update(ResditTransactionDetailVO txnDetailsVO) {
		log.entering("ResditConfiguration", "update");
		populateAttributes(txnDetailsVO);
		setLastUpdateTime(txnDetailsVO.getLastUpdateTime());
		log.exiting("ResditConfiguration", "update");
		
	}

	/**
	 * @return Returns the handedOverReceivedFlag.
	 */
	@Column(name="HNDOVRRCVFLG")
	public String getHandedOverReceivedFlag() {
		return handedOverReceivedFlag;
	}

	/**
	 * @param handedOverReceivedFlag The handedOverReceivedFlag to set.
	 */
	public void setHandedOverReceivedFlag(String handedOverReceivedFlag) {
		this.handedOverReceivedFlag = handedOverReceivedFlag;
	}

	/**
	 * TODO Purpose
	 * Feb 5, 2007, A-1739
	 * @param companyCode
	 * @param carrierId
	 * @param txnId
	 * @return
	 * @throws SystemException 
	 */
	public static ResditTransactionDetailVO findResditConfigurationForTxn(
			String companyCode, int carrierId, String txnId) 
	throws SystemException {
		try {
			return constructDAO().findResditConfurationForTxn(
					companyCode, carrierId, txnId);
		} catch(PersistenceException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}
}
