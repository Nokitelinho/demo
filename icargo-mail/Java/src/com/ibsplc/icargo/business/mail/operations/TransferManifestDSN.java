/*
 * TransferManifestDSN.java Created on June 27, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-3109 This class represents transfer manifest details for transfer
 *         manifest reports.
 */

@Entity
@Table(name = "MALTRFMFTDTL")
@Staleable
public class TransferManifestDSN {

	private TransferManifestDSNPK transferMainfestDSNPK;

	private static final String PRODUCT_NAME = "mail.operations";

	/**
	 * The bagCount
	 */
	private int bagCount;

	/**
	 * The containerNumber
	 */
	private String containerNumber;

	private String dsn;
	
	private String transferStatus;
	/**
	 * @return the dsn
	 */
	@Column(name = "DSNIDR")
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
	 * The weight
	 */
	private double weight;

	@Column(name = "BAGCNT")
	public int getBagCount() {
		return bagCount;
	}

	public void setBagCount(int bagCount) {
		this.bagCount = bagCount;
	}

	@Column(name = "CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	@Column(name = "BAGWGT")
	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	@Column(name = "TRFSTA")
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}

	public TransferManifestDSN() {

	}

	
	/**
	 * The embedded Id for the PK
	 * 
	 * @return
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "transferManifestId", column = @Column(name = "TRFMFTIDR")),
			@AttributeOverride(name = "mailSequenceNumber", column = @Column(name = "MALSEQNUM")) })
	public TransferManifestDSNPK getTransferMainfestDSNPK() {
		return transferMainfestDSNPK;
	}

	public void setTransferMainfestDSNPK(
			TransferManifestDSNPK transferMainfestDSNPK) {
		this.transferMainfestDSNPK = transferMainfestDSNPK;
	}

	/**
	 * @author a-2553 This method is used to populate the Entity
	 * @param onwardRoutingVO
	 * @throws SystemException
	 */
	public TransferManifestDSN(DSNVO dsnVO,
			TransferManifestPK transferManifestPK) throws SystemException {
		populatePK(dsnVO, transferManifestPK);
		populateAttributes(dsnVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}

	}

	/**
	 * @author a-2553 This method is used to populate the PK
	 * @param transferManifestVO
	 * @throws SystemException
	 */
	private void populatePK(DSNVO dsnVO, TransferManifestPK transferManifestPK)
			throws SystemException {

		TransferManifestDSNPK transferManifestDSNPK = new TransferManifestDSNPK();
		transferManifestDSNPK.setCompanyCode(transferManifestPK
				.getCompanyCode());
		transferManifestDSNPK.setTransferManifestId(transferManifestPK
				.getTransferManifestId());
		transferManifestDSNPK.setMailSequenceNumber(dsnVO.getMailSequenceNumber());
		//transferManifestDSNPK.setMailbagId(transferManifestPK.get)
/*		transferManifestDSNPK.setDestinationExchangeOffice(dsnVO
				.getDestinationExchangeOffice());
		transferManifestDSNPK.setDsn(dsnVO.getDsn());
		transferManifestDSNPK.setMailCategoryCode(dsnVO.getMailCategoryCode());
		transferManifestDSNPK.setMailSubclass(dsnVO.getMailSubclass());
		transferManifestDSNPK.setOriginExchangeOffice(dsnVO
				.getOriginExchangeOffice());
		transferManifestDSNPK.setYear(dsnVO.getYear());*/
		this.setTransferMainfestDSNPK(transferManifestDSNPK);
	}

	/**
	 * @author a-2553 This method is used to populate the Attributes
	 * @param dsnVO
	 * @throws SystemException
	 */

	private void populateAttributes(DSNVO dsnVO) throws SystemException {
		this.setBagCount(dsnVO.getBags());
		this.setContainerNumber(dsnVO.getContainerNumber());
		this.setWeight(dsnVO.getWeight().getRoundedSystemValue());//modified by A-7371 for ICRD-133987
		StringBuilder dsn=new StringBuilder();
		dsn.append(dsnVO.getOriginExchangeOffice()).
		append(dsnVO.getDestinationExchangeOffice()).
		append(dsnVO.getMailCategoryCode()).
		append(dsnVO.getMailSubclass()).
		append(dsnVO.getYear()).
		append(dsnVO.getDsn());
		this.setDsn(dsn.toString());
		if("TRAEND".equals(dsnVO.getStatus())){
			this.setTransferStatus("TRFEND");
		}else{
		this.setTransferStatus("TRFINT");
		}
	}
	
	 public static TransferManifestDSN find(TransferManifestDSNPK transferManifestDSNPK)
		      throws FinderException, SystemException {
		      return PersistenceController.getEntityManager().find(TransferManifestDSN.class, transferManifestDSNPK);
		  }

}