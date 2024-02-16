
/*
 * DSNRouting.java Created on Sep 03, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3229
 *
 */

@Entity
@Table(name = "MALMRARTG")
@Staleable
public class DSNRouting {



	private static final String CLASS_NAME = "DSNRouting";
	private static final String MODULE_NAME = "mail.mra.defaults";

	private DSNRoutingPK dsnRoutingPk;

	private Calendar flightDate;
	private int flightCarrierID;
	private String flightNumber;
	private String flightCarrierCode;
	private String pol;
	private String pou;
	private String agreementType;
	private int nopieces;		
	private double weight;
	//Commented as part of MRA revamp
	//private int legsernum;
	//added for bug ICRD-2170
	private long  flightSequenceNumber;
	
	//Added by A-7794 as part of MRA Revam
	
	private int segmentSerialNum;
	private String lastUpdatedUser;
	private Calendar lastUpdatedTime;
	
	private String flightType;
	
	
	private String blockSpaceType;//added for ICRD-239039
	private String bsaReference;   //added for ICRD-239039
	
	private String interfacedFlag;
	
	private String source;//Added by Manish for IASCB-40970
	
	
	
	
	
	/**
	 * @return the segmentSerialNum
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNum() {
		return segmentSerialNum;
	}

	/**
	 * @param segmentSerialNum the segmentSerialNum to set
	 */
	public void setSegmentSerialNum(int segmentSerialNum) {
		this.segmentSerialNum = segmentSerialNum;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * 
	 * @log
	 */

	public static Log returnLogger() {
		return LogFactory.getLogger("MRA DSNROUTING");
	}

	/**
	 * @return Returns the flightCarrierID.
	 */
	@Column(name = "FLTCARIDR")
	public int getFlightCarrierID() {
		return flightCarrierID;
	}
	/**
	 * @return Returns the flightDate.
	 */
	@Column(name = "FLTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getFlightDate() {
		return flightDate;
	}
	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}
	/**
	 * @return Returns the pol.
	 */
	@Column(name="POL")
	public String getPol() {
		return pol;
	}


	/**
	 * @return Returns the pou.
	 */
	@Column(name="POU")
	public String getPou() {
		return pou;
	}

	/**
	 * @return the agreementType
	 */
	@Column(name="AGRTYP")
	public String getAgreementType() {
		return agreementType;
	}

	/**
	 * @param flightCarrierID The flightCarrierID to set.
	 */
	public void setFlightCarrierID(int flightCarrierID) {
		this.flightCarrierID = flightCarrierID;
	}

	/**
	 * @param agreementType The agreementType to set.
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
	/**
	 * @param flightCarrierCode The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}
	/**
	 * @param flightDate The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @param pou The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "mailSequenceNumebr", column = @Column(name = "MALSEQNUM")),
		@AttributeOverride(name = "routingSerialNum", column = @Column(name = "RTGSERNUM"))})
		public DSNRoutingPK getDSNRoutingPK() {
		return dsnRoutingPk;
	}
	/**
	 * @param dsnRoutingPk The dsnRoutingPk to set.
	 */
	public void setDSNRoutingPK(DSNRoutingPK dsnRoutingPk) {
		this.dsnRoutingPk = dsnRoutingPk;
	}
	/**
	 * default constructor
	 */
	public DSNRouting() {

	}


	/**
	 * 
	 * @param dsnRoutingVO
	 * @throws SystemException
	 */
	public DSNRouting(DSNRoutingVO dsnRoutingVO)throws SystemException{
		returnLogger().entering(CLASS_NAME,"DSNRouting-Constructor");
		populatePK(dsnRoutingVO);
		populateAttributes(dsnRoutingVO);

		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 

		returnLogger().exiting(CLASS_NAME,"MRABillingDetails-Constructor");
	}


	/**
	 * This method constructs
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO()
	throws PersistenceException, SystemException{
		Log log = LogFactory.getLogger("MRA BILLING");
		log.entering("MRABillingMaster","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRADefaultsDAO.class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}
	/**
	 * @param mraBillingDetailsVO
	 */
	private void populatePK(DSNRoutingVO dsnRoutingVO) {

		returnLogger().entering(CLASS_NAME,"populatePK");

		DSNRoutingPK dsnRoutingPK=new DSNRoutingPK(
				dsnRoutingVO.getCompanyCode(),dsnRoutingVO.getMailSequenceNumber(),dsnRoutingVO.getRoutingSerialNumber()
		);

		this.setDSNRoutingPK(dsnRoutingPK);

		returnLogger().exiting(CLASS_NAME,"populatePK");
	}


	/**
	 * @param mraBillingDetailsVO
	 */
	private void populateAttributes(DSNRoutingVO dsnRoutingVO) {

		returnLogger().entering(CLASS_NAME,"populateAttributes");

		this.setFlightCarrierCode(dsnRoutingVO.getFlightCarrierCode());
		this.setFlightCarrierID(dsnRoutingVO.getFlightCarrierId());
		this.setFlightDate(dsnRoutingVO.getDepartureDate());
		this.setFlightNumber(dsnRoutingVO.getFlightNumber());
		this.setPol(dsnRoutingVO.getPol());
		this.setPou(dsnRoutingVO.getPou());
		this.setNopieces(dsnRoutingVO.getNopieces());
		this.setWeight(dsnRoutingVO.getWeight());
		this.setAgreementType(dsnRoutingVO.getAgreementType());
		this.setFlightSequenceNumber(dsnRoutingVO.getFlightSeqnum());
		//Added by A-7794 as part of MRA revamp activity
		this.setSegmentSerialNum(dsnRoutingVO.getSegmentSerialNumber());
		this.setLastUpdatedTime(dsnRoutingVO.getLastUpdateTime());
		this.setLastUpdatedUser(dsnRoutingVO.getLastUpdateUser());
		this.setFlightType(dsnRoutingVO.getFlightType());
		this.setBlockSpaceType(dsnRoutingVO.getBlockSpaceType());
		this.setBsaReference(dsnRoutingVO.getBsaReference());
		this.setSource(MRAConstantsVO.MRA_CSG_ROUTING_SOURCE_MANUAL);//Added by Manish for IASCB-40970
		if(dsnRoutingVO.getInterfacedFlag()!=null){
			this.setInterfacedFlag(dsnRoutingVO.getInterfacedFlag());
		}else{
			this.setInterfacedFlag("N");
		}
		returnLogger().exiting(CLASS_NAME,"populateAttributes");

	}


	/**
	 * @param routingSerNumber
	 * @param csgSeqNum
	 * @param csgDocumentNumber
	 * @param billingBasis
	 * @param companyCode
	 * @param poaCode
	 * @return
	 * @throws SystemException
	 */
	public static DSNRouting find(String companyCode,long mailSeqNumber,int routingSerNumber)throws SystemException,FinderException{
		returnLogger().entering(CLASS_NAME,"find");
		DSNRouting dsnRouting=null;
		DSNRoutingPK dsnRoutingPK=new DSNRoutingPK(companyCode,mailSeqNumber,routingSerNumber);
		try {
			dsnRouting=PersistenceController.getEntityManager().find(
					DSNRouting.class,dsnRoutingPK);
		} catch (FinderException e) {		
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"find");
		return dsnRouting;

	}


	/**
	 * 
	 * @param dsnRoutingVO
	 * @throws SystemException
	 */
	public void update(DSNRoutingVO dsnRoutingVO)
	throws SystemException{
		returnLogger().entering(CLASS_NAME,"update");
		populateAttributes(dsnRoutingVO);		
		returnLogger().exiting(CLASS_NAME,"update");		
	}


	/**
	 * 
	 * @throws SystemException
	 */
	public void remove()throws SystemException{
		returnLogger().entering(CLASS_NAME,"remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} catch (OptimisticConcurrencyException e) {			
			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
		returnLogger().exiting(CLASS_NAME,"remove");
	}


	/**
	 * @author a-3229
	 * @param dsnRoutingFilterVO
	 * @return DSNRoutingVO
	 * @throws SystemException	
	 */
	public static  Collection<DSNRoutingVO> findDSNRoutingDetails(DSNRoutingFilterVO dsnRoutingFilterVO) 
	throws SystemException {
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("DSNRouting","entity");
			return constructDAO().findDSNRoutingDetails(dsnRoutingFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @return the nopieces
	 */
	@Column(name = "STDBAG")
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
	@Column(name = "STDWGT")
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
	 * @return the flightSequenceNumber
	 */
	@Column(name = "FLTSEQNUM")
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber the flightSequenceNumber to set
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * 	Method		:	DSNRouting.updateFlightSegment
	 *	Added by 	:	a-4809 on Oct 22, 2014
	 * 	Used for 	:	ICRD-68924,TK specific
	 * to update flight segment details for SAP CR
	 *	Parameters	:	@param dsnRoutingVO
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public static void updateFlightSegment(DSNRoutingVO dsnRoutingVO)
			throws SystemException{
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("DSNRouting","updateFlightSegment");
			constructDAO().updateFlightSegment(dsnRoutingVO);
			log.exiting("DSNRouting","updateFlightSegment"); 
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

	/**
	 * 	Getter for flightType 
	 *	Added by : a-8061 on 27-Jul-2018
	 * 	Used for :
	 */
	@Column(name = "FLTTYP")
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

	public static String validateBSA(DSNRoutingVO dsnRoutingVO)throws SystemException {
		
		
		try {
			Log log = LogFactory.getLogger("MRA DEFAULTS");
			log.entering("DSNRouting","isValidBSA");
			return constructDAO().validateBSA(dsnRoutingVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		
	}
	/**
	 * 
	 * @return blockSpaceType
	 */
	@Column(name = "BLKSPCTYP")
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
	@Column(name = "BSAREF")
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
	@Column(name = "INTFCDFLG")
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
	 * 	Method		:	DSNRouting.getSource
	 *	Added by 	:	A-5219 on 07-Apr-2020
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	@Column(name = "SEGSRC")
	public String getSource(){
		return source;
	}
	
	/**
	 * 
	 * 	Method		:	DSNRouting.setSource
	 *	Added by 	:	A-5219 on 07-Apr-2020
	 * 	Used for 	:
	 *	Parameters	:	@param source 
	 *	Return type	: 	void
	 */
	public void setSource(String source){
		this.source = source;
	}



}


