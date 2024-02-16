package com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo;

import com.ibsplc.xibase.server.framework.persistence.lock.LockKeys;
import com.ibsplc.xibase.server.framework.persistence.lock.TransactionLockVO;

public class MRASettlementBatchLockVO extends  TransactionLockVO  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String ENTITY_NAME  = "MRABTHSTL";
	
	private static final String ENTITY_DISPLAY_NAME  = "MRA SETTLEMENT BATCH";

	private String batchId;
    
    private int batchseqnum;
    
    private String companyCode;
    
    public MRASettlementBatchLockVO(String action) {
    	super(action);
    }
   

	@Override
	public LockKeys getKeys() {
		LockKeys keys = new LockKeys();
        keys.addKey("BATCHID", getBatchId());
        keys.addKey("BATCHSEQNUM", getBatchseqnum());     
        return keys;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public int getBatchseqnum() {
		return batchseqnum;
	}

	public void setBatchseqnum(int batchseqnum) {
		this.batchseqnum = batchseqnum;
	}
	@Override
	public String getCompanyCode() {
		return companyCode;
	}
	@Override
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}


	@Override
	public String getEntityName() {
		return ENTITY_NAME;
	}


	@Override
	public String getEntityDisplayString() {
		return ENTITY_DISPLAY_NAME;
	}
	
	@Override
	public boolean equals(Object other) {
		return (other != null) && (hashCode() == other.hashCode());
	}
	
	@Override
	public int hashCode() {

		return new StringBuffer(companyCode).
				append(batchId).
				append(ENTITY_NAME).
				append(ENTITY_DISPLAY_NAME).
				toString().hashCode();
	}

}
