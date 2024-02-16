package com.ibsplc.icargo.business.xaddons.qf.mail.operations.feature.saveactualweightcontainer.enrichers;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers.ActualWeightStatusEnricher;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalULDWeighingDetailsVO;
import com.ibsplc.icargo.business.xaddons.qf.mail.operations.proxy.QFOperationsFltHandlingProxy;
import com.ibsplc.icargo.business.xaddons.qf.operations.flthandling.vo.WeighingDetailsVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("QF-mail.operations.feature.saveactualweightcontainer.enrichers.actualweightstatusenricher")
public class ActualWeightStatusEnricherQF extends ActualWeightStatusEnricher {
	private static final Log LOGGER = LogFactory.getLogger(ActualWeightStatusEnricherQF.class.getSimpleName());
	private static final String HHT = "HHT";
	public static final String ACTUAL_WEIGHT_VERIFICATION_ERROR ="mail.operations.err.actualweightreweigh";
	
	@Autowired
	private Proxy proxyInstance;

	@Override
	public void enrich(ContainerVO containervo) throws SystemException {
		LOGGER.entering(ActualWeightStatusEnricherQF.class.getSimpleName(), "enrich");
		 OperationalULDWeighingDetailsVO operationalULDWeighingDetailsVO = new OperationalULDWeighingDetailsVO();
		 operationalULDWeighingDetailsVO.setUldNumber(containervo.getContainerNumber());
		 operationalULDWeighingDetailsVO.setCompanyCode(containervo.getCompanyCode());
		 operationalULDWeighingDetailsVO.setFlightNumber(containervo.getFlightNumber());
		 operationalULDWeighingDetailsVO.setAirportCode(containervo.getAssignedPort());
		 operationalULDWeighingDetailsVO.setFlightCarrierId(containervo.getCarrierId());
		 operationalULDWeighingDetailsVO.setFlightCarrierCode(containervo.getCarrierCode());
		 operationalULDWeighingDetailsVO.setFlightDate(containervo.getFlightDate());
		 operationalULDWeighingDetailsVO.setFlightSequenceNumber(containervo.getFlightSequenceNumber());
		 operationalULDWeighingDetailsVO.setLegSerialNumber(containervo.getLegSerialNumber());
		 operationalULDWeighingDetailsVO.setActualWeight(containervo.getActualWeight());
		 operationalULDWeighingDetailsVO.setSegSerialNumber(containervo.getSegmentSerialNumber());
		 operationalULDWeighingDetailsVO.setPointOfUnLading(containervo.getPou());
		 operationalULDWeighingDetailsVO.setLastUpdateUser(containervo.getLastUpdateUser());
		 operationalULDWeighingDetailsVO.setSource(HHT);
		 OperationalULDWeighingDetailsVO updatedOperationalULDWeighingDetailsVO;
			try {
				updatedOperationalULDWeighingDetailsVO = proxyInstance.get(QFOperationsFltHandlingProxy.class).validateAndEnrichActualWeightForMailContainer(operationalULDWeighingDetailsVO);
			} catch (ProxyException | SystemException e) {
				LOGGER.log(Log.SEVERE,e);
				throw new SystemException(ACTUAL_WEIGHT_VERIFICATION_ERROR);
			}
	
		 WeighingDetailsVO weighingDetailsVO = (WeighingDetailsVO) updatedOperationalULDWeighingDetailsVO.getSpecificData();
		 LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		 String weightStatus ="";
		 if(null!=weighingDetailsVO.getNewWeightStatus())
		 {
		  weightStatus =  new StringBuilder(weighingDetailsVO.getNewWeightStatus()).append("-").append(logonAttributes.getUserId()).toString();
		 }
		 containervo.setActWgtSta(weightStatus);
	}

}
