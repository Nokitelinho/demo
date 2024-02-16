package com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.QueryDAO;

/**
 * @author A-10543
 *
 */
public interface LhXaddonsMailOperationsDAO extends QueryDAO {
	
	/**Added by A-10543 for Airmail Portlet
	 * @param despatchDetailsFilter
	 * @return
	 * @throws SystemException
	 */
	public Collection<DespatchDetailsVO> findDespatchDetails(DespatchDetailsFilterVO despatchDetailsFilter)
			throws SystemException;
}
