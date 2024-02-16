/**
 * DNATAMailController.java 29-Jun-2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * 
 */
package com.ibsplc.icargo.business.xaddons.dnata.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations.DNATAMailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-9619
 * com.ibsplc.icargo.business.xaddons.dnata.mail.operations.DNATAMailController.java
 */
public class DNATAMailController extends MailController {

	private static final String MODULE_NAME = "dnatamail.operations";
	private static final Log LOG = LogFactory.getLogger("DNATAMailController");
	
	
	
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
    		LOG.log(Log.SEVERE,"DNATAMailTrackingDefaultsDAO - persistenceException: "+persistenceException);
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
	
	 //added by A-9529 for IASCB-44567
  	/**
  	 * @author a-9529
  	 * @param storageUnit
  	 * @return
  	 * @throws SystemException
  	 */
  	public  static    Collection<MailbagVO> findMailbagsInStorageUnit(String storageUnit)
  			throws SystemException{
  		try {
  			return constructDAO().findMailbagsInStorageUnit(storageUnit);
  		} catch (PersistenceException ex) {
  			throw new SystemException(ex.getMessage(), ex);
  		}
  	}
}

