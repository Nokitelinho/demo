/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.SaveSendResditMessageDetailsFeature.java
 *
 *	Created by	:	A-10647
 *	Created on	:	21-Mar-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails;

import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.persistors.SendResditMessageDetailsPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.SaveSendResditMessageDetailsFeature.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	21-Mar-2022	:	Draft
 */
@FeatureComponent(SaveSendResditMessageDetailsFeatureConstants.SAVE_SEND_RESDIT_MESSAGE_DETAILS_FEATURE)
@Feature(exception = BusinessException.class)
public class SaveSendResditMessageDetailsFeature extends AbstractFeature<MailResditMessageDetailsVO> {
	private static final Log LOGGER = LogFactory.getLogger(SaveSendResditMessageDetailsFeatureConstants.MODULE_SUBMODULE);


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#fetchFeatureConfig(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added by 			: A-10647 on 21-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return 
	 */
	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailResditMessageDetailsVO mailResditMessageDetailsVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	
	}

	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeature#perform(com.ibsplc.xibase.server.framework.vo.AbstractVO)
	 *	Added by 			: A-10647 on 21-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws BusinessException 
	 */
	@Override
	protected Void perform(MailResditMessageDetailsVO mailResditMessageDetailsVO ) throws SystemException, BusinessException {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		
			new SendResditMessageDetailsPersistor().persist(mailResditMessageDetailsVO);
			

		return null;
	}



 
}
