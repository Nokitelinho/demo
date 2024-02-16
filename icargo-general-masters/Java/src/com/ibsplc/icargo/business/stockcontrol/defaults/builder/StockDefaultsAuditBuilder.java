/**
 * StockDefaultsAuditBuilder.java Created on May 05, 2015
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.builder;

import java.rmi.RemoteException;

import com.ibsplc.icargo.business.shared.audit.vo.SharedEntityAuditVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAllocationVO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class StockDefaultsAuditBuilder.
 */
/**
 * @author a-5249
 */
public class StockDefaultsAuditBuilder extends AbstractActionBuilder {

	/** The Constant log. */
	private static final Log log = LogFactory
			.getLogger("SHARED STOCKCONTROL AUDIT");

	/** The audit vo. */
	private SharedEntityAuditVO auditVO;

	/**
	 * Gets the audit vo.
	 * 
	 * @return the audit vo
	 */
	public SharedEntityAuditVO getAuditVO() {
		return new SharedEntityAuditVO(SharedEntityAuditVO.MODULE_NAME,
				SharedEntityAuditVO.SUB_MODULE, SharedEntityAuditVO.AUDIT_NAME);
	}

	/**
	 * Audit for stock utilisation.
	 * 
	 * @param stockAllocationVO
	 *            the stock allocation vo
	 * @throws SystemException
	 *             the system exception
	 * @throws RemoteException
	 *             the remote exception
	 */
	public void auditForStockUtilisation(StockAllocationVO stockAllocationVO)
			throws SystemException, RemoteException {
		log.entering("StockDefaultsAuditBuilder", "auditForStockUtilisation");
		auditVO = getAuditVO();
		auditVO.setCompanyCode(stockAllocationVO.getCompanyCode());
		auditVO.setEntityCode(SharedEntityAuditVO.ENT_STKRNGUTL);
		auditVO.setReferenceOne(stockAllocationVO.getStockHolderCode());
		auditVO.setReferenceTwo(stockAllocationVO.getDocumentType());
		auditVO.setReferenceThree(stockAllocationVO.getDocumentSubType());
		auditVO.setUserId(stockAllocationVO.getLastUpdateUser());
		auditVO.setTxnTime(stockAllocationVO.getLastUpdateTime());
		auditVO.setTxnLocalTime(stockAllocationVO
				.getLastUpdateTimeForStockReq());
		RangeVO rngVO = null;
		for (RangeVO rng : stockAllocationVO.getRanges()) {
			rngVO = rng;
		}
		auditVO.setAdditionalInformation(new StringBuilder(rngVO
				.getStartRange()).append("-").append(rngVO.getEndRange())
				.append("-").append(rngVO.getNumberOfDocuments()).toString());
		if (StockAllocationVO.OPERATION_FLAG_INSERT.equals(stockAllocationVO
				.getOperationFlag())) {
			auditVO.setActionCode(SharedEntityAuditVO.AUDIT_ACTION_STKRNGUTL_CTD);
		} else {
			auditVO.setActionCode(SharedEntityAuditVO.AUDIT_ACTION_STKRNGUTL_UPD);
		}
		auditVO.setAuditRemarks(new StringBuilder().append("StockControlFor:")
				.append(stockAllocationVO.getStockControlFor())
				.append(",Arlidr:")
				.append(stockAllocationVO.getAirlineIdentifier()).toString());
		AuditUtils.performAudit(auditVO);

		log.exiting("StockDefaultsAuditBuilder", "auditForStockUtilisation");
	}

}