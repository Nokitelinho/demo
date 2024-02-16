/*
 * DNATAMailbag.java Created on Jun 25, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.dnata.mail.operations;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations.DNATAMailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-9619
 * com.ibsplc.icargo.business.xaddons.dnata.mail.operations.DNATAMailbag.java
 * This view is created for DNATA specific operations
 */
@Table(name = "MALMST_VW_DNATA")
@Entity
public class DNATAMailbag extends Mailbag{
	
	private static final Log LOG = LogFactory.getLogger("DNATAMailbag");
	private static final String MODULE_NAME = "dnatamail.operations";
	
	private String screeningUser;

	//added by A-9529 for IASCB-44567
    private String storageUnit;

    @Column(name="STGUNT")
    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }

    @Column(name="SCRUSR")
 	public String getScreeningUser() {
		return screeningUser; 
	}
	public void setScreeningUser(String screeningUser) { 
		this.screeningUser = screeningUser;
	}
    
	 /**
	 * @author A-2037
	 * This method is used to find the History of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 */
    public static Collection<MailbagHistoryVO> findMailbagHistories
    (String companyCode,String mailBagId,long mailSequenceNumber) throws SystemException{  /*modified by A-8149 for ICRD-248207*/
    	Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
    	try{
    		   mailbagHistoryVOs.addAll(constructDAO().findMailbagHistories(companyCode,mailBagId,mailSequenceNumber));
    	}
    	catch(PersistenceException persistenceException){
    		LOG.log(Log.SEVERE,"findMailbagHistories - PersistenceException: "+persistenceException);
    		throw new SystemException(persistenceException.getErrorCode());
    	}
       return mailbagHistoryVOs;
    }

    /**
     * @author A-9619
     * This method returns the Instance of the DAO
     * @return
     * @throws SystemException
     */
    private static DNATAMailTrackingDefaultsDAO constructDAO()
    throws SystemException {
    	try {
    		EntityManager em = PersistenceController.getEntityManager();
    		return DNATAMailTrackingDefaultsDAO.class.cast(em.
    				getQueryDAO(MODULE_NAME));
    	}
    	catch(PersistenceException persistenceException) {
    		LOG.log(Log.SEVERE,"checkReTriggerMessage - SystemException 3 !!!!!"+persistenceException);
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    
    @Override
	public void populateAttributes(MailbagVO mailbagVO) {
		super.populateAttributes(mailbagVO);
		setScreeningUser(mailbagVO.getScreeningUser());
	    setStorageUnit(MailConstantsVO.MLD_DLV.equals(
             mailbagVO.getLatestStatus())?"":mailbagVO.getStorageUnit()); //added by A-9529 for IASCB-44567
	}
    
    @Override
    public void updateArrivalDetails(MailbagVO mailbagVO) throws SystemException {
    	super.updateArrivalDetails(mailbagVO);
    	//added by A-9529 for IASCB-44567
        setStorageUnit(MailConstantsVO.MLD_DLV.equals(
                mailbagVO.getLatestStatus())?"":mailbagVO.getStorageUnit()); 
    }
    
    @Override
    public void updateReturnDetails(MailbagVO mailbagVO) throws SystemException {
    	super.updateReturnDetails(mailbagVO);
		setScreeningUser(mailbagVO.getScreeningUser());
    }
    
    
    /**
     *	A-1739
     * @param toFlightVO
     * @param containerVO
     * @param toFlightSegmentSerialNum
     * @throws SystemException
     */
    @Override
    public void updateFlightForReassign(OperationalFlightVO toFlightVO,
            ContainerVO containerVO, int toFlightSegmentSerialNum,boolean isRDTUpdateReq)
        throws SystemException {
    	
    	super.updateFlightForReassign(toFlightVO, containerVO, toFlightSegmentSerialNum, isRDTUpdateReq);
    	if(containerVO.getScreeningUser()!=null) { //Added by A-9498 as part of IASCB-44577
             setScreeningUser(containerVO.getScreeningUser());
         }
    }
    
    @Override
    public void setScreeningUser(MailbagVO mailbagVo)
			throws FinderException, SystemException {
		setScreeningUser(mailbagVo.getScreeningUser());//Added by A-9498 as part of IASCB-44577 
	}
}
