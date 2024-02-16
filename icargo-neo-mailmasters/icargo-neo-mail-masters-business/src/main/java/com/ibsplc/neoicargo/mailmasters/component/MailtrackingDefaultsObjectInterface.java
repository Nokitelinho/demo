package com.ibsplc.neoicargo.mailmasters.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

import java.util.Collection;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
public interface MailtrackingDefaultsObjectInterface extends QueryDAO {
	/** 
	* @author a-6986
	* @param companyCode
	* @return
	* @throws SystemException
	* @throws PersistenceException
	*/
	public Collection<MailServiceLevel> findServiceLevelDtls(String companyCode)
			throws SystemException, PersistenceException;
}
