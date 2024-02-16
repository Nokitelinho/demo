/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeature.java
 *
 *	Created by	:	203168
 *	Created on	:	17-Oct-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails;

import java.util.ArrayList;

import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.persistors.HBADetailsPersistor;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.savehbaDetails.SaveHBADetailsFeature.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	17-oct-2022	:	Draft
 */
@FeatureComponent(SaveHBADetailsFeatureConstants.SAVE_HBA_DETAILS_FEATURE)
@Feature(exception = BusinessException.class)
public class SaveHBADetailsFeature extends AbstractFeature<HbaMarkingVO> {
	private static final Log LOGGER = LogFactory.getLogger(SaveHBADetailsFeatureConstants.MODULE_SUBMODULE);


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#fetchFeatureConfig(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added by 			: 203168 on 17-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return 
	 */
	@Override
	protected FeatureConfigVO fetchFeatureConfig(HbaMarkingVO hbaMarkingVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added by 			: 203168 on 17-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException 
	 */
	@Override
	protected Void perform(HbaMarkingVO hbaMarkingVO) throws SystemException, BusinessException {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
			new HBADetailsPersistor().persist(hbaMarkingVO);
		return null;
	}
 
}
