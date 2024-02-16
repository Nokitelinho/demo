package com.ibsplc.icargo.business.xaddons.ey.uld.defaults;

import com.ibsplc.icargo.business.msgbroker.message.vo.xaddons.ey.uld.defaults.CPMBulkFlightDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.ULDDefaultsBI;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

import java.rmi.RemoteException;
import java.util.Collection;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYUldDefaultsBI.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */
public interface EYUldDefaultsBI extends ULDDefaultsBI {

	public void saveCPMBulkDetails(Collection<CPMBulkFlightDetailsVO> cpmBulkFlightDetailsVOs)
			throws SystemException, RemoteException;
}
