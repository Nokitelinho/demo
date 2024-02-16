/*
 * DNATAMailTrackingDefaultsDAO.java Created on Jun 25, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

/**
 * 
 * @author A-9619
 * com.ibsplc.icargo.persistence.dao.xaddons.dnata.mail.operations.DNATAMailTrackingDefaultsDAO.java
 */
public interface DNATAMailTrackingDefaultsDAO extends QueryDAO{

	/**
	 * @author A-2037 This method is used to find the History of a Mailbag
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailbagHistoryVO> findMailbagHistories(String companyCode,String mailBagId,
			long mailSequenceNumber) throws SystemException, PersistenceException;  /*modified by A-8149 for ICRD-248207*/
	

	//added by A-9529 for IASCB-44567
    /**
     * This method  is used to find out the Mail Bags in the StorageUnit from in bound react screen
	 * @author a-9529
	 * @param StorageUnit
	 * @return
	 * @throws SystemException
	 */
	Collection<MailbagVO> findMailbagsInStorageUnit(String storageUnit) throws SystemException, PersistenceException;
			
}

