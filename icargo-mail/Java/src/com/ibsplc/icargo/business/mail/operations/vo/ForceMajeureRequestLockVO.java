/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.xibase.server.framework.persistence.lock.LockKeys;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 * @author A-5219
 *
 */
public class ForceMajeureRequestLockVO  extends LockVO{
	
	private static final String ENTITY_NAME = "FORMJR";
	  private static final String EMPTY = "";
	  private static final String BLANK = " ";
	  public static final String ACTION_REQFORCEMAJ = "REQFORMJR";
	  public static final String ACTION_FORCEMAJ = "FORMJR";

	  private String companyCode;
	  private String forceLockEntity;
	
	@Override
	public String getEntityDisplayString() {
		StringBuffer localStringBuffer = new StringBuffer(EMPTY);
	    localStringBuffer.append(getCompanyCode()).append(BLANK).append(getForceLockEntity());
	    return localStringBuffer.toString();
	}
	public String getForceLockEntity() {
		return forceLockEntity;
	}
	public void setForceLockEntity(String forceLockEntity) {
		this.forceLockEntity = forceLockEntity;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	@Override
	public String getEntityName() {
		 return ENTITY_NAME;
	}

	@Override
	public LockKeys getKeys() {
		LockKeys localLockKeys = new LockKeys();
	    localLockKeys.addKey("CMPCOD", getCompanyCode());
	    localLockKeys.addKey("REQIDR", getForceLockEntity());
	    return localLockKeys;
	}

}
