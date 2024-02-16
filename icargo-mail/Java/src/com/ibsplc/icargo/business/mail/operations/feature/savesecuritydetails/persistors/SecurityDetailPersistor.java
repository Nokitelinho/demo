/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSecurityDetails.persistors.SecurityDetailPersistor.java
 *
 *	Created by	:	A-10647
 *	Created on	:	21-Mar-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.persistors;



import com.ibsplc.icargo.business.mail.operations.ConsignmentScreeningDetails;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSecurityDetails.persistors.SecurityDetailPersistor.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	21-Mar-2022	:	Draft
 */
public class SecurityDetailPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveSecurityDetailsFeatureConstants.MODULE_SUBMODULE);


	/**
	 * 	Method		:	SecurityDetailPersistor.persist
	 *	Added by 	:	A-10647 on 21-Mar-2022
	 * 	Used for 	:
	 *	Parameters	:	@param consignmentScreeningVO 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws FinderException 
	 */

	public void persist(ConsignmentScreeningVO consignmentScreeningVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		new ConsignmentScreeningDetails(consignmentScreeningVO);
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
		
	}
	
}
