/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.persistors.HBADetailsPersistor.java
 *
 *	Created by	:	203168
 *	Created on	:	17-Oct-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.persistors;



import com.ibsplc.icargo.business.xaddons.lh.mail.operations.ContainerHBADetails;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.ContainerHBADetailsPK;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeatureConstants;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.persistors.HBADetailsPersistor.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	17-Oct-2022	:	Draft
 */
public class HBADetailsPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveHBADetailsFeatureConstants.MODULE_SUBMODULE);


	/**
	 * 	Method		:	HBADetailsPersistor.persist
	 *	Added by 	:	203168 on 17-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param HbaMarkingVO 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws FinderException 
	 */

	public void persist(HbaMarkingVO hbaMarkingVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		if (HbaMarkingVO.OPERATION_FLAG_INSERT
				.equals(hbaMarkingVO.getOperationFlag())) {
			newContainerHBADetails(hbaMarkingVO);
		}
	if (HbaMarkingVO.OPERATION_FLAG_UPDATE
			.equals(hbaMarkingVO.getOperationFlag())) {
		ContainerHBADetails  containerHBADetails= null;
		ContainerHBADetailsPK containerHBADetailsPK = new ContainerHBADetailsPK();			
		containerHBADetailsPK.setCompanyCode(hbaMarkingVO.getCompanyCode());
		containerHBADetailsPK.setUldReferenceNo(hbaMarkingVO.getUldRefNo());
		try {
			containerHBADetails = ContainerHBADetails.find(containerHBADetailsPK);
			containerHBADetails.update(hbaMarkingVO);
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		
	}
	if (HbaMarkingVO.OPERATION_FLAG_DELETE
			.equals(hbaMarkingVO.getOperationFlag())) {
		ContainerHBADetails  containerHBADetails= null;
		ContainerHBADetailsPK containerHBADetailsPK = new ContainerHBADetailsPK();			
		containerHBADetailsPK.setCompanyCode(hbaMarkingVO.getCompanyCode());
		containerHBADetailsPK.setUldReferenceNo(hbaMarkingVO.getUldRefNo());
		try {
			containerHBADetails = ContainerHBADetails.find(containerHBADetailsPK);
			if(containerHBADetails!=null){
				containerHBADetails.remove();
			}
		} catch (FinderException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
		
	}
	
	/**
	 *
	 * @return
	 * @throws SystemException
	 */
	private void newContainerHBADetails(HbaMarkingVO hbaMarkingVO)throws SystemException{
		ContainerHBADetails containerHBADetails = new ContainerHBADetails(hbaMarkingVO);
		try {

			PersistenceController.getEntityManager().persist(containerHBADetails);
		} catch (CreateException createException) {
			createException.getErrorCode();
	            throw new SystemException(createException.getMessage(), createException);
		}
	}
	
}
