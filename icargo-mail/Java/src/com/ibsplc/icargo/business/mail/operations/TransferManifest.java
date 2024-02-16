/*
 * TransferManifest.java Created on Jun 27, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.TransferManifestDSN;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-5991 This class represents transfer manifest details for transfer
 *         manifest reports.
 */

@Entity
@Table(name = "MALTRFMFT")
public class TransferManifest {

	private TransferManifestPK transferManifestPK;

	private static final String PRODUCT_NAME = "mail.operations";
	private static final String MODULE = "mail.operations";
	private static final String TRAEND="TRAEND";
	private String airPort;

	private Calendar transferDate;

	private String transferredToCarrierCode;

	private String transferredToFltNumber;

	private String transferredFromFltNumber;

	private Calendar fromFlightDate;

	private Calendar toFlightDate;

	private String transferredFromCarrierCode;

	private Set<TransferManifestDSN> transferManifestDSN;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;
	private long transferredfrmFltSeqNum;
	private int transferredfrmSegSerNum;
	private String transferStatus;

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

	@Column(name = "ARPCOD")
	public String getAirPort() {
		return airPort;
	}

	public void setAirPort(String airPort) {
		this.airPort = airPort;
	}

	@Column(name = "TRFDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Calendar transferDate) {
		this.transferDate = transferDate;
	}

	/**
	 * The embedded Id for the PK
	 * 
	 * @return
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "transferManifestId", column = @Column(name = "TRFMFTIDR")) })
	public TransferManifestPK getTransferMainfestPK() {
		return transferManifestPK;
	}

	public void setTransferMainfestPK(TransferManifestPK transferMainfestPK) {
		this.transferManifestPK = transferMainfestPK;
	}

	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "TRFMFTIDR", referencedColumnName = "TRFMFTIDR", insertable = false, updatable = false) })
	public Set<TransferManifestDSN> getTransferManifestDSN() {
		return transferManifestDSN;
	}

	public void setTransferManifestDSN(
			Set<TransferManifestDSN> transferManifestDSN) {
		this.transferManifestDSN = transferManifestDSN;
	}

	@Column(name = "FRMCARCOD")
	public String getTransferredFromCarrierCode() {
		return transferredFromCarrierCode;
	}

	public void setTransferredFromCarrierCode(String transferredFromCarrierCode) {
		this.transferredFromCarrierCode = transferredFromCarrierCode;
	}

	@Column(name = "ONWCARCOD")
	public String getTransferredToCarrierCode() {
		return transferredToCarrierCode;
	}

	public void setTransferredToCarrierCode(String transferredToCarrierCode) {
		this.transferredToCarrierCode = transferredToCarrierCode;
	}

	@Column(name = "ONWFLTNUM")
	public String getTransferredToFltNumber() {
		return transferredToFltNumber;
	}

	public void setTransferredToFltNumber(String transferredToFltNumber) {
		this.transferredToFltNumber = transferredToFltNumber;
	}

	@Column(name = "FRMFLTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFromFlightDate() {
		return fromFlightDate;
	}

	public void setFromFlightDate(Calendar fromFlightDate) {
		this.fromFlightDate = fromFlightDate;
	}

	@Column(name = "ONWFLTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getToFlightDate() {
		return toFlightDate;
	}

	public void setToFlightDate(Calendar toFlightDate) {
		this.toFlightDate = toFlightDate;
	}

	@Column(name = "FRMFLTNUM")
	public String getTransferredFromFltNumber() {
		return transferredFromFltNumber;
	}

	public void setTransferredFromFltNumber(String transferredFromFltNumber) {
		this.transferredFromFltNumber = transferredFromFltNumber;
	}

	@Column(name = "FRMFLTSEQNUM")
	public long getTransferredfrmFltSeqNum() {
		return transferredfrmFltSeqNum;
	}
	public void setTransferredfrmFltSeqNum(long transferredfrmFltSeqNum) {
		this.transferredfrmFltSeqNum = transferredfrmFltSeqNum;
	}
	@Column(name = "FRMSEGSERNUM")
	public int getTransferredfrmSegSerNum() {
		return transferredfrmSegSerNum;
	}
	public void setTransferredfrmSegSerNum(int transferredfrmSegSerNum) {
		this.transferredfrmSegSerNum = transferredfrmSegSerNum;
	}
	@Column(name = "TRFSTA")
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}

	/**
	 * The default constructor
	 * 
	 */
	public TransferManifest() {
	}

	/**
	 * @author a-5991 This method is used to insert the new record in the Entity
	 * @param transferManifestVO
	 * @throws SystemException
	 */
	public TransferManifest(TransferManifestVO transferManifestVO)
			throws SystemException {
		populatePK(transferManifestVO);
		populateAttributes(transferManifestVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		populateChildren(transferManifestVO);
	}
	/**
	 * 	Method		:	TransferManifest.populateChildren
	 *	Added by 	:	A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param transferManifestVO 
	 *	Return type	: 	void
	 * @throws SystemException 
	 */
	public void populateChildren(TransferManifestVO transferManifestVO) throws SystemException {
		Collection<DSNVO> dsnVOs = null;
		if (transferManifestVO.getDsnVOs()!=null && transferManifestVO.getDsnVOs().size()>0) {			
			dsnVOs = transferManifestVO.getDsnVOs();
			boolean status=false;
			if(TRAEND.equals(transferManifestVO.getStatus())){
				status= true;
			}
			populateDSNs(dsnVOs,status,this.getTransferMainfestPK());
		}
	}
	/**
	 * 	Method		:	TransferManifest.populateDSNs
	 *	Added by 	:	A-4809 on Sep 2, 2016
	 * 	Used for 	:
	 *	Parameters	:	@param dsnVOs
	 *	Parameters	:	@param transferMainfestPK 
	 *	Return type	: 	void
	 * @throws SystemException 
	 */
	public void populateDSNs(Collection<DSNVO> dsnVOs,
			boolean status,TransferManifestPK transferManifestPK) throws SystemException {
		for(DSNVO dsnVO : dsnVOs){			
			if(status){
				dsnVO.setStatus(TRAEND);
			}
			TransferManifestDSN trfManifestDSN = new TransferManifestDSN(dsnVO,transferManifestPK);
			if (getTransferManifestDSN() == null) {
				setTransferManifestDSN(new HashSet<TransferManifestDSN>());
			}
			getTransferManifestDSN().add(trfManifestDSN);
		}
	}

	/**
	 * @author a-5991 This method is used to populate the PK
	 * @param transferManifestVO
	 * @throws SystemException
	 */
	private void populatePK(TransferManifestVO transferManifestVO)
			throws SystemException {

		TransferManifestPK trfManifestPK = new TransferManifestPK();
		trfManifestPK.setCompanyCode(transferManifestVO.getCompanyCode());
		trfManifestPK.setTransferManifestId(transferManifestVO
				.getTransferManifestId());
		this.setTransferMainfestPK(trfManifestPK);
	}

	/**
	 * @author a-5991 This method is used to populate the Attributes
	 * @param transferManifestVO
	 * @throws SystemException
	 */

	private void populateAttributes(TransferManifestVO transferManifestVO)
			throws SystemException {
		this.setAirPort(transferManifestVO.getAirPort());
		if (transferManifestVO.getTransferredDate() != null) {
			this.setTransferDate(transferManifestVO.getTransferredDate()
					.toCalendar());
		}
		this.setTransferredToCarrierCode(transferManifestVO
				.getTransferredToCarrierCode());
		if(transferManifestVO.getTransferredToFltNumber()!= null){
		this.setTransferredToFltNumber(transferManifestVO
				.getTransferredToFltNumber());
		}
		this.setTransferredFromCarrierCode(transferManifestVO
				.getTransferredFromCarCode());
		this.setTransferredFromFltNumber(transferManifestVO
				.getTransferredFromFltNum());
		if (transferManifestVO.getFromFltDat() != null) {
			this.setFromFlightDate(transferManifestVO.getFromFltDat());
		}
		if (transferManifestVO.getToFltDat() != null) {
			this.setToFlightDate(transferManifestVO.getToFltDat());
		}
		if(transferManifestVO.getLastUpdateUser() != null){
		this.setLastUpdateUser(transferManifestVO.getLastUpdateUser());
		}
		this.setTransferredfrmFltSeqNum(transferManifestVO.getTransferredfrmFltSeqNum());
		this.setTransferredfrmSegSerNum(transferManifestVO.getTransferredfrmSegSerNum());
		if(transferManifestVO.getStatus()!= null && transferManifestVO.getStatus().equals(TRAEND)){
				this.setTransferStatus("TRFEND");
			}else{
		this.setTransferStatus("TRFINT");
			}
		}
		
		/**
		    * @author a-1936
		    * This method is used to find the Transfer Manifest for the Different Transactions
		    * @param tranferManifestFilterVo
		    * @return
		    * @throws SystemException
		    * @throws RemoteException
		    */			  
		   public  static Page<TransferManifestVO> findTransferManifest(TransferManifestFilterVO tranferManifestFilterVo)
		   throws SystemException{
			   
			   try{
			   return    constructDAO().findTransferManifest(tranferManifestFilterVo);
			  
			   }catch(PersistenceException ex){
			   throw new SystemException(ex.getErrorCode());
			   }
		   }	
		
		   /**
			 * @author a-2553
			 * Added By Paulson as the  part of  the Air NewZealand CR...
			 * @param operationalFlightVo
			 * @return
			 */
			public static TransferManifestVO generateTransferManifestReport(String companyCode,String transferManifestId)
			 throws SystemException{
				try {
					return constructDAO().generateTransferManifestReport(companyCode,transferManifestId);
				} catch (PersistenceException ex) {
			           throw new SystemException(ex.getMessage(), ex);
			    }
	}
			
			 public static TransferManifest find(TransferManifestPK transferManifestPK)
				      throws FinderException, SystemException {
				      return PersistenceController.getEntityManager().find(TransferManifest.class, transferManifestPK);
				  }

}