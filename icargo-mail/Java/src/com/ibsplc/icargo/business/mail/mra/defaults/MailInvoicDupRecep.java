/*
 * MailInvoicDupRecep.java Created on Sep 17, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicDupRecepVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVDUPRCP")
@Staleable
@Deprecated
public class MailInvoicDupRecep {
	private MailInvoicDupRecepPK mailInvoicDupRecepPK;

	private String originalInvoicRef;
	
	private String duplicateInvoicRef;

	/**
	 * 
	 */
	public MailInvoicDupRecep(){
		
	}
	/**
	 * @param dupRecepVO
	 * @throws SystemException
	 */
	public MailInvoicDupRecep(MailInvoicDupRecepVO dupRecepVO)
	throws SystemException{
		MailInvoicDupRecepPK dupRecepPK = new MailInvoicDupRecepPK();
		dupRecepPK.setCompanyCode(dupRecepVO.getCompanyCode());
		dupRecepPK.setRecepticleIdentifier(dupRecepVO.getRecepticleIdentifier());
		dupRecepPK.setSectorDestination(dupRecepVO.getSectorDestination());
		dupRecepPK.setSectorOrigin(dupRecepVO.getSectorOrigin());
		this.setMailInvoicDupRecepPK(dupRecepPK);
		this.setOriginalInvoicRef(dupRecepVO.getOriginalInvoicRef());
		this.setDuplicateInvoicRef(dupRecepVO.getDuplicateInvoicRef());
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}
	
	/**
	 * @return Returns the duplicateInvoicRef.
	 */
	@Column(name="DUPINFREF")
	public String getDuplicateInvoicRef() {
		return duplicateInvoicRef;
	}

	/**
	 * @param duplicateInvoicRef The duplicateInvoicRef to set.
	 */
	public void setDuplicateInvoicRef(String duplicateInvoicRef) {
		this.duplicateInvoicRef = duplicateInvoicRef;
	}

	/**
	 * @return Returns the mailInvoicDupRecepPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="recepticleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicDupRecepPK getMailInvoicDupRecepPK() {
		return mailInvoicDupRecepPK;
	}

	/**
	 * @param mailInvoicDupRecepPK The mailInvoicDupRecepPK to set.
	 */
	public void setMailInvoicDupRecepPK(MailInvoicDupRecepPK mailInvoicDupRecepPK) {
		this.mailInvoicDupRecepPK = mailInvoicDupRecepPK;
	}

	/**
	 * @return Returns the originalInvoicRef.
	 */
	@Column(name="ORGINFREF")
	public String getOriginalInvoicRef() {
		return originalInvoicRef;
	}

	/**
	 * @param originalInvoicRef The originalInvoicRef to set.
	 */
	public void setOriginalInvoicRef(String originalInvoicRef) {
		this.originalInvoicRef = originalInvoicRef;
	}
	
	/**
	 * @param vo
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailInvoicDupRecep find(MailInvoicDupRecepVO vo)throws
	SystemException,FinderException{
		MailInvoicDupRecepPK pktofind = new MailInvoicDupRecepPK();
		pktofind.setCompanyCode(vo.getCompanyCode());
		pktofind.setRecepticleIdentifier(vo.getRecepticleIdentifier());
		pktofind.setSectorDestination(vo.getSectorDestination());
		pktofind.setSectorOrigin(vo.getSectorOrigin());
		
		return PersistenceController.getEntityManager().find(
				MailInvoicDupRecep.class, pktofind);
	}
	/**
	 * @param vo
	 */
	public  void update(MailInvoicDupRecepVO vo){
		this.setOriginalInvoicRef(vo.getOriginalInvoicRef());
		this.setDuplicateInvoicRef(vo.getDuplicateInvoicRef());
	}


}