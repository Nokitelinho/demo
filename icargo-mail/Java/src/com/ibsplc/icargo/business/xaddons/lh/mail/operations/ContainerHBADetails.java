/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Aug 5, 2016
 *
 *  Copyright 2016 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations;



import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailBoxId.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Aug 5, 2016	:	Draft
 */
@Entity
@Table(name = "MALFLTCONHBADTL")
@Staleable
public class ContainerHBADetails {
	
	
	private String hbapos;
	private String hbatyp;
	private ContainerHBADetailsPK containerHBADetailsPK;
	
	@Column(name = "HBAPOS")
	public String getHbapos() {
		return hbapos;
	}

	public void setHbapos(String hbapos) {
		this.hbapos = hbapos;
	}

	@Column(name = "HBATYP")
	public String getHbatyp() {
		return hbatyp;
	}

	public void setHbatyp(String hbatyp) {
		this.hbatyp = hbatyp;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "uldReferenceNo", column = @Column(name = "ULDREFNUM")) 
	})
	public ContainerHBADetailsPK getContainerHBADetailsPK() {
		return containerHBADetailsPK;
	}

	public void setContainerHBADetailsPK(ContainerHBADetailsPK containerHBADetailsPK) {
		this.containerHBADetailsPK = containerHBADetailsPK;
	}

	/**
	 * Empty Constructor
	 *
	 */
	
	
	public ContainerHBADetails() {

	}
	

	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	public ContainerHBADetails(HbaMarkingVO hbaMarkingVO)throws SystemException{

		populatePK(hbaMarkingVO);
	    populateAttributes(hbaMarkingVO);
	
	}
	
	
	private void populatePK(HbaMarkingVO hbaMarkingVO) {
		
			ContainerHBADetailsPK hbaDetailsPK = new ContainerHBADetailsPK();
			hbaDetailsPK.setCompanyCode(hbaMarkingVO.getCompanyCode());
			hbaDetailsPK.setUldReferenceNo(hbaMarkingVO.getUldRefNo());
			this.containerHBADetailsPK=hbaDetailsPK;
		
	}
	
	

	private void populateAttributes(HbaMarkingVO hbaMarkingVO) {
		setHbapos(hbaMarkingVO.getHbaPosition());
		setHbatyp(hbaMarkingVO.getHbaType());
		
	}




	/**
     * This method is used to find the Instance of the Entity
     * @param ContainerHBADetailsPK
     * @param
     * @param
     * @param
     * @return
     * @throws SystemException
     * @throws FinderException
     */
	public static ContainerHBADetails find(ContainerHBADetailsPK containerHBADetailsPK) throws SystemException,
			FinderException {
		
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(ContainerHBADetails.class, containerHBADetailsPK);
	}
	
	
	public void update(HbaMarkingVO hbaMarkingVO)
			throws SystemException, FinderException {
		populateAttributes(hbaMarkingVO);
	}
	/**
	 * @author A-8353
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);		}
	}
	
}
