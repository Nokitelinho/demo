package com.ibsplc.icargo.services.xaddons.ey.uld.defaults;

import com.ibsplc.icargo.business.msgbroker.message.vo.xaddons.ey.uld.defaults.CPMBulkFlightDetailsVO;
import com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYUldController;
import com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYUldDefaultsBI;
import com.ibsplc.icargo.services.uld.defaults.ULDDefaultsServicesEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Collection;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.ey.uld.defaults.EYUldDefaultsServicesEJB.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */
public class EYUldDefaultsServicesEJB extends ULDDefaultsServicesEJB implements EYUldDefaultsBI {
    private static final String MODULE_NAME = "EYULD.DEFAULTS.EJB";
    private static final Log LOG = LogFactory.getLogger(MODULE_NAME);

	public void saveCPMBulkDetails(Collection<CPMBulkFlightDetailsVO> cpmBulkFlightDetailsVOs) throws SystemException {
        try {
            new EYUldController().saveCPMBulkDetails(cpmBulkFlightDetailsVOs);
        } catch (CreateException e) {
            LOG.log(Log.SEVERE, "CreateException", e);
            throw new SystemException(e.getErrorCode());
        }
    }
}
