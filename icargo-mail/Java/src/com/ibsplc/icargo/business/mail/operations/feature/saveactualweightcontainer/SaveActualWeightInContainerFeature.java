package com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer;


import java.util.Collection;
import java.util.Objects;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.enrichers.ActualWeightStatusEnricher;
import com.ibsplc.icargo.business.mail.operations.feature.saveactualweightcontainer.persistors.SaveActualWeighInContainerPersistor;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedULDProductProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeFilterVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@FeatureComponent(SaveActualWeightInContainerFeatureConstants.SAVE_ACTUAL_WEIGHT_CONTAINER)
@Feature(exception = MailTrackingBusinessException.class)
public class SaveActualWeightInContainerFeature extends AbstractFeature<ContainerVO> {

	Proxy proxy;
	@Override
	protected FeatureConfigVO fetchFeatureConfig(ContainerVO arg0) {
		return getBECConfigurationDetails();
	}
	
	private FeatureConfigVO getBECConfigurationDetails() {
		return new FeatureConfigVO();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected ContainerVO perform(ContainerVO containervo) throws SystemException, BusinessException {
	        ActualWeightStatusEnricher enricher = (ActualWeightStatusEnricher) SpringAdapter.getInstance()
	                .getBean(SaveActualWeightInContainerFeatureConstants.ACTUAL_WEIGHT_STATUS_ENRICHER);
	        preInvokeFindULDTypes(containervo);
	        enricher.enrich(containervo);
	        return	 new SaveActualWeighInContainerPersistor().persist(containervo);
	}
	private void preInvokeFindULDTypes(ContainerVO containerVO) throws BusinessException, SystemException{
		Collection<ULDTypeVO> uldTypes = null;
			ULDTypeFilterVO uldTypeFilterVO = populateULDTypeFilterVO(containerVO);
			uldTypes = Proxy.getInstance().get(SharedULDProductProxy.class).findULDTypes(uldTypeFilterVO);
		filterAndSetToContext(uldTypes);
	}
	private void filterAndSetToContext(Collection<ULDTypeVO> uldTypes){
		ULDTypeVO uldTypeVO = null;
		if (Objects.nonNull(uldTypes) && !uldTypes.isEmpty()) {
			uldTypeVO = uldTypes.iterator().next();
		}
		setToContext(SaveActualWeightInContainerFeatureConstants.ULDTYPES_FROM_FINDULDTYPES, uldTypeVO);
	}
	private ULDTypeFilterVO populateULDTypeFilterVO(ContainerVO containerVO) {
		ULDTypeFilterVO uldTypeFilterVO = new ULDTypeFilterVO();
		uldTypeFilterVO.setCompanyCode(containerVO.getCompanyCode());
		uldTypeFilterVO.setUldTypeCode(containerVO.getContainerNumber().substring(0, 3));
		return uldTypeFilterVO;
	}

}
