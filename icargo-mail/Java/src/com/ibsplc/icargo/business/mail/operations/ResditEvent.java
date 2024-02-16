/*
 * ResditEvent.java Created on Sep 22, 2006
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

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Sep 22, 2006			   a-1883		Created
 */
/**
 * @author A-1883
 */
@Table(name = "MALRDTEVT")
@Entity
@Staleable
public class ResditEvent {
    
    private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
    
    private ResditEventPK resditEventPK;
    
    private String uniqueIdForResdit;
    
    private Calendar lastUpdateTime;
    
    private String paCode;
    
    public ResditEvent() {
    	
    }

    /**
     * @return Returns the resditEventPK.
     */
    @EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
        @AttributeOverride(name = "consignmentDocumentNumber", column = @Column(name = "CSGDOCNUM")),
        @AttributeOverride(name = "eventCode", column = @Column(name = "EVTCOD")),
        @AttributeOverride(name = "eventPort", column = @Column(name = "EVTPRT")),
        @AttributeOverride(name = "messageSequenceNumber", column = @Column(name = "MSGSEQNUM"))
       })
    public ResditEventPK getResditEventPK() {
        return resditEventPK;
    }

    /**
     * @param resditEventPK The resditEventPK to set.
     */
    public void setResditEventPK(ResditEventPK resditEventPK) {
        this.resditEventPK = resditEventPK;
    }

    /**
     * @return Returns the lastUpdateTime.
     */
    @Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
    public Calendar getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime The lastUpdateTime to set.
     */
    public void setLastUpdateTime(Calendar lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * @return Returns the uniqueIdForResdit.
     */
    @Column(name = "RDTREDTIM")
    public String getUniqueIdForResdit() {
        return uniqueIdForResdit;
    }

    /**
     * @param uniqueIdForResdit The uniqueIdForResdit to set.
     */
    public void setUniqueIdForResdit(String uniqueIdForResdit) {
        this.uniqueIdForResdit = uniqueIdForResdit;
    }
    /**
     * @param resditEventPk
     * @return
     * @throws SystemException
     * @throws FinderException
     */
    public static ResditEvent find(ResditEventPK resditEventPk)
        throws SystemException ,FinderException {
        return PersistenceController.getEntityManager().
            find(ResditEvent.class, resditEventPk);
    }
    /**
     * @throws SystemException
     */
   public void remove()throws SystemException{
       log.entering("ResditEvent","remove");
       try {
            PersistenceController.getEntityManager().remove(this);
        } catch (RemoveException exception) {
            throw new SystemException(exception.getMessage(), exception);
        }
        log.exiting("ResditEvent","remove");
   }

	/**
	 * @return Returns the paCode.
	 */
   @Column(name="POACOD")
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}
}
