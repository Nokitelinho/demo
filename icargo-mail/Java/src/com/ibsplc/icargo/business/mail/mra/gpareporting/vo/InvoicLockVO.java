/**
 * 
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.persistence.lock.LockKeys;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;

/**
 * @author A-5219
 *
 */
public class InvoicLockVO extends LockVO{
	
	  private static final String ENTITY_NAME = "GPAINVLCK";
	  private static final String EMPTY = "";
	  private static final String BLANK = " ";
	  public static final String ACTION_INVOICLOCK = "INVPRCLCK";
	  public static final String ACTION_GPAINVOICLOCK = "GPAINVLCK";

	  private String companyCode;
	  private String forceLockEntity;
	  
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
		    localLockKeys.addKey("SERNUM", getForceLockEntity());
		    return localLockKeys;
		}

}
