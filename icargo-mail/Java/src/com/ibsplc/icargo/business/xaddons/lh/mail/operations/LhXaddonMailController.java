/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.LhXaddonMailController.java
 *
 *	Created by	:	203168
 *	Created on	:	17-Oct-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.lh.mail.operations;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeature;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.feature.savehbadetails.SaveHBADetailsFeatureConstants;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.persistence.dao.xaddons.lh.mail.operations.LhXaddonsMailOperationsDAO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.bs.mail.operations.LhXaddonMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	17-Oct-2022	:	Draft
 */
@Module("lhmail")
@SubModule("operations")
public class LhXaddonMailController extends MailController{

	private static final Log LOGGER = LogFactory.getLogger("mail.operations");
	   

	/**
	 *
	 * 	Method		:	LhXaddonMailController.saveMailBookingDetails
	 *	Added by 	:	203168
	 * 	Used for 	:
	 *	Parameters	:	@param hbaMarkingVO
	 *	Return type	: 	void

	 * @throws SystemException
	 * @throws BusinessException 
	 */
	public void markHba(HbaMarkingVO hbaMarkingVO) throws  SystemException {
		LOGGER.entering("HbaMarkingVO", "markHba");

		SaveHBADetailsFeature saveHbaDetailsFeature = (SaveHBADetailsFeature) SpringAdapter.getInstance().getBean(SaveHBADetailsFeatureConstants.SAVE_HBA_DETAILS_FEATURE);
		try {
			saveHbaDetailsFeature.execute(hbaMarkingVO);
			Proxy.getInstance().get(MailOperationsProxy.class).updateIntFlgAsNForMailBagsInConatiner(hbaMarkingVO);
		} catch (BusinessException e) {
			LOGGER.log(Log.SEVERE,e);
		}	
		
			
	}
	
	public  HbaMarkingVO findHbaDetails(HbaMarkingVO hbaMarkingVO) throws  SystemException {
		LOGGER.entering("HbaMarkingVO", "markHba");
	ContainerHBADetails  containerHBADetails= null;
	ContainerHBADetailsPK containerHBADetailsPK = new ContainerHBADetailsPK();
	containerHBADetailsPK.setCompanyCode(hbaMarkingVO.getCompanyCode());
	containerHBADetailsPK.setUldReferenceNo(hbaMarkingVO.getUldRefNo());
	try {
		 containerHBADetails = ContainerHBADetails.find(containerHBADetailsPK);
		 hbaMarkingVO.setHbaType(containerHBADetails.getHbatyp());
		 hbaMarkingVO.setHbaPosition(containerHBADetails.getHbapos());
		 return hbaMarkingVO;
		 
	} catch (FinderException e) {
		LOGGER.log(Log.SEVERE,e);
		return hbaMarkingVO;
	}
				
	}
	
	public Collection<DespatchDetailsVO> findDespatchDetails(DespatchDetailsFilterVO despatchDetailsFilter)
			throws SystemException {
		LOGGER.entering("LhXaddonMailController", "findDespatchDetails");
		Collection<DespatchDetailsVO> returnDespatchDetails = null;

		returnDespatchDetails = constructDAO().findDespatchDetails(despatchDetailsFilter);
		LOGGER.exiting("LhXaddonMailController", "findDespatchDetails");
		return returnDespatchDetails;
	}
	private static LhXaddonsMailOperationsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return LhXaddonsMailOperationsDAO.class.cast(em.getQueryDAO("mail.operations"));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),persistenceException);
		}
	}

}
