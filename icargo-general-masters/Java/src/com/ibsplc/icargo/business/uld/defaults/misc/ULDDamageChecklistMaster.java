/*
 * ULDDamageChecklistMaster.java Created on May 5, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc;

import java.util.Calendar;
import java.util.Collection;
import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3459
 *
 */

@Table(name = "ULDDMGCHKLST")
@Entity
public class ULDDamageChecklistMaster {
	
	private Log log=LogFactory.getLogger("ULD DAMAGE CHECKLIST MASTER"); 
	private static final String MODULE = "uld.defaults";
	
	private ULDDamageChecklistMasterPK damageChecklistMasterPK ;
	
	private String description;
	private int noOfPoints;
	
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	/**
	 * @return the description
	 */
	@Column(name="DESCRPTIN")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the noOfPoints
	 */
	@Column(name="PNT")
	public int getNoOfPoints() {
		return noOfPoints;
	}
	/**
	 * @param noOfPoints the noOfPoints to set
	 */
	public void setNoOfPoints(int noOfPoints) {
		this.noOfPoints = noOfPoints;
	}
	/**
	 * @return Returns the damageChecklistMasterPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
	    @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
	    @AttributeOverride(name="section", column=@Column(name="SEC")),
	    @AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}) 
	    
	    public ULDDamageChecklistMasterPK getDamageChecklistMasterPK() {
		return this.damageChecklistMasterPK;
	}
	/**
	 * @param damageChecklistMasterPK The damageChecklistMasterPK to set.
	 */
	public void setDamageChecklistMasterPK(ULDDamageChecklistMasterPK damageChecklistMasterPK) {
		this.damageChecklistMasterPK = damageChecklistMasterPK;
	}
	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	 @Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	
	/**
	 * 
	 *
	 */
	public ULDDamageChecklistMaster() {
		
	}
	/**
	 * 
	 * @param damageChecklistVO
	 * @throws SystemException
	 */
	
	public ULDDamageChecklistMaster(ULDDamageChecklistVO damageChecklistVO)
	throws SystemException{
		log.entering("ULDDamageChecklistMaster","ULDDamageChecklistMaster");
		populatePK(damageChecklistVO);
		populateAttributes(damageChecklistVO);
		try{
			  PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());	
		}
	}	
	
	/**
	 * 
	 * @param companyCode
	 * @param section
	 * @param seqeunceNumber
	 * @param damageChecklistVO
	 * 
	 */
	public void populatePK(ULDDamageChecklistVO damageChecklistVO) {
		log.entering("ULDDamageChecklistMaster","populatePK");
		ULDDamageChecklistMasterPK pk = new ULDDamageChecklistMasterPK();
		pk.setCompanyCode(damageChecklistVO.getCompanyCode());
		pk.setSection(damageChecklistVO.getSection());
		pk.setSequenceNumber(damageChecklistVO.getSequenceNumber());
		log.log(Log.FINE, " Section :", pk.getSection());
		this.setDamageChecklistMasterPK(pk);
		log.exiting(MODULE, "populatePK");
	}
	/**
	 * 
	 * @param damageChecklistVO
	 * @throws SystemException 
	 */
	public void populateAttributes(ULDDamageChecklistVO damageChecklistVO)throws SystemException {
		
		log.entering("ULDDamageChecklistMaster","populateAttributes");
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		this.setDescription(damageChecklistVO.getDescription() );
		this.setNoOfPoints(damageChecklistVO.getNoOfPoints());
		this.setLastUpdatedUser(logon.getUserId());
		if(damageChecklistVO.getLastUpdatedTime() != null){
			this.setLastUpdatedTime(damageChecklistVO.getLastUpdatedTime());		
		}
	
		
		
		log.exiting(MODULE, "populateAttributes");
	}
	
	/**
	 * 
	 * @param damageChecklistVO
	 * @throws SystemException 
	 */
	public void update(ULDDamageChecklistVO damageChecklistVO)
	throws SystemException{
		log.entering("ULDDamageChecklistMaster","update");
		populateAttributes(damageChecklistVO);	
	}
	
	/**
	 * 
	 * @param companyCode
	 * @param section
	 * @param seqeunceNumber
	 * @return 
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ULDDamageChecklistMaster find(String companyCode, String section ,String seqeunceNumber)
	throws SystemException ,FinderException{
		ULDDamageChecklistMasterPK pk = new ULDDamageChecklistMasterPK();
		pk.setSection(section);
		pk.setCompanyCode(companyCode);
		pk.setSequenceNumber(seqeunceNumber);
		EntityManager entityManager = PersistenceController.getEntityManager();
		return entityManager.find(ULDDamageChecklistMaster.class,
				pk);
	}
	
	/**
     * method to remove BO
     *
     * @throws SystemException
     */
    public void remove() throws SystemException {
    	log.entering("ULDDamageChecklistMaster","remove");
    	EntityManager em = PersistenceController.getEntityManager();
    	try{
    		em.remove(this);
    	}catch(RemoveException removeException){
    		throw new SystemException(removeException.getErrorCode());
    	}
	}
    
    /**
	  * 
	  * @return
	  * @throws SystemException
	  */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getMessage());
		}
	}
	/**
	 * @param companyCode
	 * @param section
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDDamageChecklistVO> listULDDamageChecklistMaster(String companyCode,String section)
    throws SystemException{
		try {
            return constructDAO().listULDDamageChecklistMaster(companyCode,section);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
	}
}
