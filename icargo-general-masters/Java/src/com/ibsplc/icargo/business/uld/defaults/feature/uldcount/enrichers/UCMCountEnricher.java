/**
 * Java file	: 	com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers.UCMCountEnricher
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.feature.uldcount.enrichers;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.uld.defaults.ULDController;
import com.ibsplc.icargo.business.uld.defaults.feature.uldcount.ULDCountFeatureConstants;
import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.bean.BeanConversionConfigVO;
import com.ibsplc.icargo.framework.bean.BeanConversionHelper;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
@FeatureComponent("ULDCountFeature.UCMCountEnricher") 
public class UCMCountEnricher extends Enricher<ULDFlightMessageReconcileVO>{
 
	@Autowired
	private static final  Log LOGGER = LogFactory.getLogger(UCMCountEnricher.class.getSimpleName());
	int ucmReportedCount=0; 
	int manifestedUldCount=0;
	int manualUldCount=0;
	int baggageUldCount=0;
	
	/** 
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.Enricher#enrich(java.lang.Object)
	 *	Added on 			: 05-Jul-2022
	 * 	Used for 	:    used as an enricher for getting UCM Reported Count
	 *	Parameters	:	@param uldFlightMessageReconcileVO
	 *	Parameters	:	@throws SystemException  
	 */
	@Override
	public void enrich(ULDFlightMessageReconcileVO uldFlightMessageReconcileVO) throws SystemException {
		   
		LOGGER.entering(this.getClass().getSimpleName(),"UCMTotalCountEnricher");
		   
		    FlightFilterMessageVO filterVO;
		    BeanConversionConfigVO configVO = new BeanConversionConfigVO();
		  
	        if(Objects.nonNull(uldFlightMessageReconcileVO.getReconcileDetailsVOs())){
	            uldFlightMessageReconcileVO.getReconcileDetailsVOs()
	            .stream()
	            .filter(reconcileDetailsVO-> Objects.equals(ULDCountFeatureConstants.STATUS, reconcileDetailsVO.getUldStatus()))
	            .forEach(uldFlightMessageReconcileDetailsVO->ucmReportedCount++);
                uldFlightMessageReconcileVO.setUcmReportedCount(ucmReportedCount);
	   }
	        if(Objects.nonNull(uldFlightMessageReconcileVO.getReconcileDetailsVOs())){
	        	manualUldCount=
	        	(int) uldFlightMessageReconcileVO.getReconcileDetailsVOs()
	            .stream()
	            .filter(reconcileDetailsVO-> Objects.equals(ULDCountFeatureConstants.STATUS_MAN, reconcileDetailsVO.getUldSource()))
	            .count();
                uldFlightMessageReconcileVO.setManualUldCount(manualUldCount);
	   }
	       
	        @SuppressWarnings("unchecked")
			Collection<UldManifestVO> manifestedVOs= (Collection<UldManifestVO>)ContextUtils.getTxBusinessParameter(ULDCountFeatureConstants.MANIFEST);
	        if(Objects.nonNull(manifestedVOs)) {
	        	manifestedUldCount = (int)manifestedVOs.stream().count();
			} 
  
	        configVO.setFrom(ULDFlightMessageReconcileVO.class); 
	        filterVO = BeanConversionHelper.getInstance().convert(uldFlightMessageReconcileVO, configVO, FlightFilterMessageVO.class);
	        
	        ULDFlightMessageReconcileVO reconcileVO = ((ULDController) ICargoSproutAdapter.getBean("ULDController")).listUCMMessage(filterVO);
	       
	       if(Objects.nonNull(reconcileVO)) { 
	    	    baggageUldCount = (int)reconcileVO.getReconcileDetailsVOs()
	    		   	.stream()
	    		   	.filter(reconcileDetailsVO->Objects.equals(ULDCountFeatureConstants.BAGGAGE, reconcileDetailsVO.getContent()))
	    		   	.count(); 
	       }   
	       
	       int totalUldCount = manifestedUldCount+baggageUldCount+manualUldCount;
	       uldFlightMessageReconcileVO.setTotalUldCount(totalUldCount);
	       
	} 
} 
 