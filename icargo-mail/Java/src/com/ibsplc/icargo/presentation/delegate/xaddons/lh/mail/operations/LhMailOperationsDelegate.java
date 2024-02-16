/**
 *	Java file	: 	com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.LhMailOperationsDelegate.java
 *
 *	Created by	:	203168
 *	Created on	:	06-Oct-2022
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.xaddons.lh.mail.operations;


import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.delegate.xaddons.bs.mail.operations.LhMailOperationsDelegate.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	06-Oct-2022	:	Draft
 */

@Module("lhmail")
@SubModule("operations")
public class LhMailOperationsDelegate extends BusinessDelegate{
	
	
	/**
	 * 
	 * 	Method		:	LhMailOperationsDelegate.markHba
	 *	Added by 	:	203168 on 06-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param HbaMarkingVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	HbaMarkingVO
	 * @throws BusinessDelegateException 
	 */
	public void markHba(HbaMarkingVO hbaMarkingVO ) throws BusinessDelegateException {
		 despatchRequest("markHba",hbaMarkingVO);
	}
	
	/**
	 * 
	 * 	Method		:	LhMailOperationsDelegate.findHbaDetails
	 *	Added by 	:	203168 on 06-Oct-2022
	 * 	Used for 	:
	 *	Parameters	:	@param HbaMarkingVO
	 *	Parameters	:	@return 
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	HbaMarkingVO
	 * @throws BusinessDelegateException 
	 */
    public HbaMarkingVO findHbaDetails(HbaMarkingVO hbaMarkingVO) throws BusinessDelegateException {
    	return despatchRequest("findHbaDetails",hbaMarkingVO);
    }




}
