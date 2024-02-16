package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.UldResditVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

import java.util.Collection;
import java.util.List;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
public interface MailOperationsObjectInterface extends QueryDAO {
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
	* @author A-5526
	* @param companyCode
	* @param mailbagId
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	Collection<MailResdit> findAllResditDetails(String companyCode, long mailbagId)
			throws SystemException, PersistenceException;
	/**
	 * findUldResdit
	 * @author A-3227
	 * @param uldResditVO
	 * @param eventCode
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	List<UldResdit> findUldResdit(UldResditVO uldResditVO, String eventCode)
			throws PersistenceException, SystemException;
	List<MailResdit> findMailResdits(MailResditVO mailResditVO)
			throws PersistenceException, SystemException;

}
