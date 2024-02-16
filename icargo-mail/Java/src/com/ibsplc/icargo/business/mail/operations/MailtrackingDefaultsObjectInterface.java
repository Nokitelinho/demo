/*
 * MailtrackingDefaultsObjectInterface.java Created on July 21, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailResditFileLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.UldResditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public interface MailtrackingDefaultsObjectInterface extends QueryDAO {
	
	/**
	 * findMailResditForEvent
	 * @author A-3227
	 * @param mailbagVO
	 * @param resditEvent
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<MailResdit> findMailResditForEvent(MailbagVO mailbagVO, String resditEvent)
	throws PersistenceException, SystemException;
	
	/**
	 * findMailResdits 
	 * @author A-3227
	 * @param mailResditVO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<MailResdit> findMailResdits(MailResditVO mailResditVO)
	throws PersistenceException, SystemException;
	
	/**
	 * findMailResdit
	 * @author A-3227
	 * @param mailResditVO
	 * @param eventCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<MailResdit> findMailResdit(MailResditVO mailResditVO,String eventCode)
	throws PersistenceException,SystemException;
	
	/**findUldResdit
	 * @author A-3227
	 * @param uldResditVO
	 * @param eventCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<UldResdit> findUldResdit(UldResditVO uldResditVO,String eventCode)
	throws PersistenceException,SystemException;
	
	/**findPendingUldResdit
	 * @author A-3227
	 * @param uldResditVO
	 * @param eventCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<UldResdit> findPendingUldResdit(UldResditVO uldResditVO,String eventCode)
	throws PersistenceException,SystemException;
	/**
	 * findMailResditForResditStatusUpd
	 * @author a-2572
	 * @param controlReferenceNumber
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailResdit> findMailResditForResditStatusUpd(String companyCode,String controlReferenceNumber)
	throws SystemException,PersistenceException;
	
	/**
	 * findULDResditForResditStatusUpd
	 * @author a-2572
	 * @param companyCode
	 * @param controlReferenceNumber
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<UldResdit> findULDResditForResditStatusUpd(String companyCode,String controlReferenceNumber)
	throws SystemException,PersistenceException;
	

	/**
	 * 
	 * @param mailbagHistoryVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 * @author A-2572
	 */
	Collection<MailbagHistory> findMailBagHistory(MailbagHistoryVO mailbagHistoryVO)
	throws SystemException,PersistenceException;
	/**
	 * @author A-5526
	 * @param companyCode
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailResdit> findAllResditDetails(
			String companyCode, long mailbagId)
	throws SystemException,PersistenceException;
	/**
	 * findMailResditFileLog
	 * @author a-2572
	 * @param mailResditFileLogVO
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	Collection<MailResditFileLog> findMailResditFileLog(MailResditFileLogVO mailResditFileLogVO)
	throws SystemException,PersistenceException;
	
	/**
	 * @author a-6986
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public Collection<MailServiceLevel> findServiceLevelDtls(String companyCode)
			throws SystemException,PersistenceException;
}
