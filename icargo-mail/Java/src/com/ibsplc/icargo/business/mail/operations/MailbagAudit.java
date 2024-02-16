/*
 * MailbagAudit.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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

import com.ibsplc.icargo.business.mail.operations.vo.MailAuditHistoryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailBagAuditHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagAuditVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.audit.AbstractAudit;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.audit.vo.AuditHistoryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 * @author a-5991
 *
 */
@Entity
@Table(name = "MALAUD")
public class MailbagAudit extends AbstractAudit {
    private MailbagAuditPK mailbagAuditPK;
    /**
     * Additional info
    */
 //  private String additionalInfo;

   /**
 	 * Last update user code
 	 */
 //	private String lastUpdateUser;

 	/**
 	 * Last update date and time
 	 */
 	//private Calendar lastUpdateTime;


 	// private String actionCode;

 	/**
 	 * Updated Time in UTC
 	 */
 	//private Calendar updatedUTCTime;

 	//private String stationCode ;


 	//private String mailClass;

//	private String auditRemark;
    private String mailBagID;

	private Set<MailBagAuditHistory> mailbagAuditHistories;




	/**
     * @return Returns the mailbagAuditPK.
     */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
        @AttributeOverride(name="mailSequenceNumber", column=@Column(name="MALSEQNUM")),
        @AttributeOverride(name="sequenceNumber", column=@Column(name="SERNUM"))
    })
    public MailbagAuditPK getMailbagAuditPK() {
        return mailbagAuditPK;
    }


    /**
     * @param mailbagAuditPK The mailbagAuditPK to set.
     */
    public void setMailbagAuditPK(MailbagAuditPK mailbagAuditPK) {
        this.mailbagAuditPK = mailbagAuditPK;
    }
	/**
	 * @return the mailbagAuditHistory
	 */
    @OneToMany
    @JoinColumns( {
    @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
    @JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable=false, updatable=false),
    @JoinColumn(name = "SERNUM", referencedColumnName = "SERNUM", insertable=false, updatable=false) })
    /**
	 * @return the mailbagAuditHistories
	 */
	public Set<MailBagAuditHistory> getMailbagAuditHistories() {
		return mailbagAuditHistories;
	}
	/**
	 * @param mailbagAuditHistories the mailbagAuditHistories to set
	 */
	public void setMailbagAuditHistories(
			Set<MailBagAuditHistory> mailbagAuditHistories) {
		this.mailbagAuditHistories = mailbagAuditHistories;
	}

	/**
	 * @author A-5991
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
	    	throws SystemException {
	    		try {
	    			EntityManager em = PersistenceController.getEntityManager();
	    			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO("mail.operations"));
	    		}
	    		catch(PersistenceException persistenceException) {
	    			throw new SystemException(persistenceException.getErrorCode());
	    		}
	    	}

	public MailbagAudit() {

    }

   /**
    *	A-5991
    * @param mailbagAuditVO
    */
   private void populatePK(MailbagAuditVO mailbagAuditVO) {
       mailbagAuditPK = new MailbagAuditPK();
       mailbagAuditPK.setCompanyCode(   mailbagAuditVO.getCompanyCode());
       mailbagAuditPK.setMailSequenceNumber(mailbagAuditVO.getMailSequenceNumber());
       /*mailbagAuditPK.setDsn(   mailbagAuditVO.getDsn());
       mailbagAuditPK.setOriginExchangeOffice(   mailbagAuditVO.getOriginExchangeOffice());
       mailbagAuditPK.setDestinationExchangeOffice(   mailbagAuditVO.getDestinationExchangeOffice());
       mailbagAuditPK.setMailSubclass(   mailbagAuditVO.getMailSubclass());
       mailbagAuditPK.setMailCategoryCode(
       	mailbagAuditVO.getMailCategoryCode());
       mailbagAuditPK.setYear(   mailbagAuditVO.getYear());*/

   }

   /**
    *	A-5991
    * @param mailbagAuditVO
    */
   private void populateAttributes(MailbagAuditVO mailbagAuditVO) {
       setActionCode(mailbagAuditVO.getActionCode());
       setAdditionalInfo(mailbagAuditVO.getAdditionalInformation());
       setUpdateUser(mailbagAuditVO.getLastUpdateUser());
       setUpdateTxnTimeUTC(mailbagAuditVO.getTxnTime());
       setStationCode(mailbagAuditVO.getStationCode());
       setMailBagID(mailbagAuditVO.getMailbagId());
       //setLastUpdateTime(mailbagAuditVO.getTxnLocalTime());
     //  setMailClass(mailbagAuditVO.getMailClass());

   }
   /**
    * @param mailbagAuditVO
    * @throws SystemException
    */
   public MailbagAudit(MailbagAuditVO mailbagAuditVO) throws SystemException {
       populatePK(mailbagAuditVO);
       populateAttributes(mailbagAuditVO);
       try {
           PersistenceController.getEntityManager().persist(this);
       } catch(CreateException createException) {
           throw new SystemException(createException.getMessage(),
                   createException);
       }
       //Added by A-5945 for ICRd-119569
      populateHistoryDetails(mailbagAuditVO);
   }
   private void populateHistoryDetails(MailbagAuditVO mailbagAuditVO) throws  SystemException{
   	List<MailBagAuditHistoryVO> historyvos = null;
   	if(MailbagAuditVO.MAL_CMPCOD_UPDATED .equals(mailbagAuditVO.getActionCode())){
   		if(mailbagAuditVO.getAuditHistoryVos() != null){
   			historyvos = new ArrayList<MailBagAuditHistoryVO>();
   			for(AuditHistoryVO auditFieldVO :mailbagAuditVO.getAuditHistoryVos()){
   				auditFieldVO.setActionCode((auditFieldVO.getActionCode().replace("UPDATED", "")).trim());
   				MailBagAuditHistoryVO auditHistory = makeMailBagAuditHistoryVO(mailbagAuditVO);
   				auditHistory.setAuditField(auditFieldVO.getActionCode());
   				//auditHistory.setSerialNumber(getMailbagAuditPK().getSerialNumber());
   				auditHistory.setOldValue(auditFieldVO.getOldValue());
   				auditHistory.setNewValue(auditFieldVO.getNewValue());
   				historyvos.add(auditHistory);
   			}
   }
   	}else if (MailbagAuditVO.MAILBAG_MODIFIED.equals(mailbagAuditVO.getActionCode())){
   		historyvos = new ArrayList<MailBagAuditHistoryVO>();
   		MailBagAuditHistoryVO auditHistory = makeMailBagAuditHistoryVO(mailbagAuditVO);
   		AuditFieldVO fieldVO = mailbagAuditVO.getAuditFields().iterator().next();
   		auditHistory.setAuditField(fieldVO.getFieldName());
   		auditHistory.setOldValue(fieldVO.getOldValue());
		auditHistory.setNewValue(fieldVO.getNewValue());
		historyvos.add(auditHistory);
   	}
   	//Added for ICRD-140584 starts
   	else if(MailbagAuditVO.MAL_SCNDATTIM_UPDATED.equals(mailbagAuditVO.getActionCode())){
   		historyvos = new ArrayList<MailBagAuditHistoryVO>();
   		MailBagAuditHistoryVO auditHistory = makeMailBagAuditHistoryVO(mailbagAuditVO);
   		AuditFieldVO fieldVO = mailbagAuditVO.getAuditFields().iterator().next();
   		auditHistory.setAuditField("Scan Date");
   		auditHistory.setOldValue(fieldVO.getOldValue());
		auditHistory.setNewValue(fieldVO.getNewValue());	
		historyvos.add(auditHistory);
   	}else if((MailbagAuditVO.MAL_AWB_ATTACHED.equals(mailbagAuditVO.getActionCode()) ) || (MailbagAuditVO.MAL_AWB_DEATTACHED.equals(mailbagAuditVO.getActionCode()))){//added by a-7779 for icrd-192536 starts
   		historyvos = new ArrayList<MailBagAuditHistoryVO>();
   		for(AuditFieldVO fieldVO :mailbagAuditVO.getAuditFields()){
   		MailBagAuditHistoryVO auditHistory = makeMailBagAuditHistoryVO(mailbagAuditVO);
	   		//AuditFieldVO fieldVO = ((ArrayList<AuditFieldVO>)mailbagAuditVO.getAuditFields()).iterator().next();
   		auditHistory.setActionCode((mailbagAuditVO.getActionCode().replace("UPDATED", "")).trim()); 
   		auditHistory.setAuditField(fieldVO.getFieldName());
   		auditHistory.setOldValue(fieldVO.getOldValue());
		auditHistory.setNewValue(fieldVO.getNewValue());
		historyvos.add(auditHistory);
   		}
   	}//added by a-7779 for icrd-192536 ends
   	//Added for ICRD-140584 ends
   	//populating child entity
   	if(historyvos  != null && historyvos.size() > 0){
   				populateChildren(historyvos);
   			}
   		
   	}
	private MailBagAuditHistoryVO makeMailBagAuditHistoryVO(MailbagAuditVO mailbagAuditVO){
		MailBagAuditHistoryVO auditHistory = new MailBagAuditHistoryVO(MailbagAuditVO.MOD_NAM,MailbagAuditVO.SUB_MOD_OPERATIONS,MailbagAuditVO.ENTITY_MAILBAG);
		auditHistory.setCompanyCode(mailbagAuditVO.getCompanyCode());
		auditHistory.setDestinationExchangeOffice(mailbagAuditVO.getDestinationExchangeOffice());
		auditHistory.setMailbagId(mailbagAuditVO.getMailbagId());
		auditHistory.setMailSequenceNumber(mailbagAuditVO.getMailSequenceNumber());
		auditHistory.setDsn(mailbagAuditVO.getDsn());
		auditHistory.setOriginExchangeOffice(mailbagAuditVO.getOriginExchangeOffice());
		auditHistory.setYear(mailbagAuditVO.getYear());
		auditHistory.setMailCategoryCode(mailbagAuditVO.getMailCategoryCode());
		auditHistory.setMailSubclass(mailbagAuditVO.getMailSubclass());
		auditHistory.setSerialNumber(this.getMailbagAuditPK().getSequenceNumber());
		auditHistory.setActionCode(mailbagAuditVO.getActionCode());
		auditHistory.setLastUpdateUser(mailbagAuditVO.getLastUpdateUser());
		return auditHistory;
    }
	private void  populateChildren(List<MailBagAuditHistoryVO> historyvos)	throws SystemException{
		for (MailBagAuditHistoryVO historyvo : historyvos) {
			historyvo.setSerialNumber(this.getMailbagAuditPK().getSequenceNumber());
			new MailBagAuditHistory(historyvo);
		}
   }


	/**
	 * @param mailBagID the mailBagID to set
	 */
	public void setMailBagID(String mailBagID) {
		this.mailBagID = mailBagID;
	}


	/**
	 * @return the mailBagID
	 */
	@Column(name = "MALIDR")
	public String getMailBagID() {
		return mailBagID;
	}
	
	/**
	 * 
	 * @param mailAuditHistoryFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MailBagAuditHistoryVO> findMailAuditHistoryDetails(
			MailAuditHistoryFilterVO mailAuditHistoryFilterVO) throws SystemException{
		return constructDAO().findMailAuditHistoryDetails(mailAuditHistoryFilterVO);
	}
	
	/**
	 * 
	 * @param entities
	 * @param isForHistory
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public static HashMap<String,String> findAuditTransactionCodes(Collection<String> entities, boolean isForHistory,
			String companyCode) throws SystemException{
				return constructDAO().findAuditTransactionCodes(entities,isForHistory,companyCode);
			}
}
