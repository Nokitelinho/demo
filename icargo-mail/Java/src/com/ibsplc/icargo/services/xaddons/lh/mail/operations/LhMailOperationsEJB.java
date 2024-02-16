/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.lh.mail.operations.MailOperationsServicesEJB.java
 *
 *	Created by	:	203168
 *	Created on	:	20-Sep-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.services.xaddons.lh.mail.operations;

import java.rmi.RemoteException;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.LhMailOperationsBI;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.LhXaddonMailController;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.DespatchDetailsFilterVO;
import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.services.xaddons.lh.mail.operations.LhMailOperationsEJB.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	20-Sep-2022	:	Draft
 */
public class LhMailOperationsEJB extends AbstractFacadeEJB
implements LhMailOperationsBI{


	private static final Log LOG = LogFactory.getLogger("mail.operations");



	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.xaddons.lh.mail.operations.LhMailOperationsBI#markHba(com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO)
	 *	Added by 			: 203168 on 20-Sep-2022
	 * 	Used for 	:
	 *	Parameters	:	@param com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO
	 */
	
	@Override
	public void markHba(HbaMarkingVO hbaMarkingVO) throws SystemException, RemoteException {
		LOG.entering("LhMailOperationsEJB", "markHba");
		 new LhXaddonMailController().markHba(hbaMarkingVO);
	}


	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.xaddons.lh.mail.operations.LhMailOperationsBI#findHbaDetails(com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO)
	 *	Added by 			: 203168 on 20-Sep-2022
	 * 	Used for 	:
	 *	Parameters	:	@param com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO
	 */

	@Override
	public HbaMarkingVO findHbaDetails(HbaMarkingVO hbaMarkingVO) throws SystemException, RemoteException {
		return new LhXaddonMailController().findHbaDetails(hbaMarkingVO);
	}


	@Override
	public Collection<DespatchDetailsVO> findDespatchDetails(DespatchDetailsFilterVO despatchDetailsFilter)
			throws SystemException, RemoteException {
			 return new LhXaddonMailController().findDespatchDetails(despatchDetailsFilter);
		}
	}
