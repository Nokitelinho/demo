/*
 * ULDLockVO.java Created on Sep 5, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.vo.lock;

import com.ibsplc.icargo.business.shared.lock.LockConstants;
import com.ibsplc.xibase.server.framework.persistence.lock.LockKeys;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 * @author A-2886
 *
 */
public class ULDLockVO extends LockVO {

	private static final long serialVersionUID = 1L;

	private String uldNumber;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.xibase.server.framework.persistence.lock.LockVO#getEntityName()
	 */
	@Override
	public String getEntityName() {
		return LockConstants.ENTITY_ULDNUM;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.ibsplc.xibase.server.framework.persistence.lock.LockVO#getKeys()
	 */
	@Override
	public LockKeys getKeys() {
		LockKeys keys = new LockKeys();
		keys.addKey("ULD_NUM", getUldNumber());
		return keys;
	}
/**
 *
 * @return
 */
	public String getUldNumber() {
		return uldNumber;
	}
/**
 *
 * @param uldNumber
 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	@Override
	public String getEntityDisplayString() {
		// To be reviewed Auto-generated method stub
		return getUldNumber();
	}
}
