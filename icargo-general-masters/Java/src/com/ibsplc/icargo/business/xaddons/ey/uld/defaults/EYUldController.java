package com.ibsplc.icargo.business.xaddons.ey.uld.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.xaddons.ey.uld.defaults.CPMBulkFlightDetailsVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ey.uld.defaults.EYUldController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7765	:	22-Jul-2020	    :	Draft
 */
@Module("eyuld")
@SubModule("defaults")
public class EYUldController {

	private static final String MODULE_NAME = "XADDONS.EYULD.DEFAULTS";
	private static final String CLASS_NAME = "EYUldController";
	private static final Log LOG = LogFactory.getLogger(MODULE_NAME);

	public void saveCPMBulkDetails(Collection<CPMBulkFlightDetailsVO> cpmBulkFlightDetailsVOs)
            throws SystemException, CreateException {
		LOG.entering(CLASS_NAME, "saveCPMBulkDetails");
		saveOrModifyCpmBulkDetails(cpmBulkFlightDetailsVOs);
		LOG.exiting(CLASS_NAME, "saveCPMBulkDetails");
	}

    /**
     * <p>Saves the bulk details from CPM message to table or update if already present.<p/>
     * 	Method		:	EYUldController.saveOrModifyCpmBulkDetails
     *	Added by 	:	A-7765 on 22-Jul-2020
     * 	Used for 	:   IASCB-61445
     *	@param cpmBulkFlightDetailsVOs Bulk information from message
     *	@return void
     */
	private void saveOrModifyCpmBulkDetails(Collection<CPMBulkFlightDetailsVO> cpmBulkFlightDetailsVOs)
            throws SystemException, CreateException {
		if (cpmBulkFlightDetailsVOs != null && !cpmBulkFlightDetailsVOs.isEmpty()) {
			EYCPMMessageDetailPK eyCPMMessageDetailPK = constructEYCPMMessageDetailPK(
					cpmBulkFlightDetailsVOs.iterator().next());
			for (CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO : cpmBulkFlightDetailsVOs) {
				eyCPMMessageDetailPK.setStationCode(cpmBulkFlightDetailsVO.getStationCode());
				eyCPMMessageDetailPK.setCompartmentSection(cpmBulkFlightDetailsVO.getCompartmentSection());
				eyCPMMessageDetailPK.setContent(cpmBulkFlightDetailsVO.getBulkContent());
				try {
					EYCPMMessageDetail eyCPMMessageDetail = EYCPMMessageDetail.find(eyCPMMessageDetailPK);
 					eyCPMMessageDetail.remove();
				} catch (FinderException e) {
					LOG.log(Log.SEVERE, "FinderException", e);
				}
				if (cpmBulkFlightDetailsVO.getBulkContent() != null) {
                    new EYCPMMessageDetail(cpmBulkFlightDetailsVO);
                }
			}
		}
	}

    /**
     * 	Method		:	EYUldController.constructEYCPMMessageDetailPK
     *	Added by 	:	A-7765 on 22-Jul-2020
     * 	Used for 	:   IASCB-61445
     *	@param cpmBulkFlightDetailsVO
     *	@return EYCPMMessageDetailPK
     */
	private EYCPMMessageDetailPK constructEYCPMMessageDetailPK(CPMBulkFlightDetailsVO cpmBulkFlightDetailsVO) {
		EYCPMMessageDetailPK eyCPMMessageDetailPK = new EYCPMMessageDetailPK();
		eyCPMMessageDetailPK.setCompanyCode(cpmBulkFlightDetailsVO.getCompanyCode());
		eyCPMMessageDetailPK.setFlightCarrierId(cpmBulkFlightDetailsVO.getFlightCarrierID());
		eyCPMMessageDetailPK.setFlightSequenceNumber(cpmBulkFlightDetailsVO.getFlightSequenceNumber());
		eyCPMMessageDetailPK.setFlightNumber(cpmBulkFlightDetailsVO.getFlightNumber());
		eyCPMMessageDetailPK.setLegSerialNumber(cpmBulkFlightDetailsVO.getLegSerialNumber());
		return eyCPMMessageDetailPK;
	}
}
