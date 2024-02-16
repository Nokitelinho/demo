/*
 * ListContainerActionController.java Created on Jul 17, 2017
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.controller.mail.operations;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ListContainerModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jul 17, 2018	     A-5437			First draft
 * 0.2		Sep 28,	2018		 a-7779			New method addition
 * 0.3		Oct 5,	2018		 A-7929		    New method addition 
*/

@Controller
@RequestMapping("mail/operations/containerenquiry")
public class ListContainerActionController extends AbstractActionController<ListContainerModel>{


	@Resource(name="listContainerResourceBundle")
	ICargoResourceBundle listContainerResourceBundle;
	
	/**
	 * 
	 * @author A-5437
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO screenloadListContainer(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.screenload",listContainer);
	}
	
	/**
	 * 
	 * @author A-5437
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/list")
	public @ResponseBody ResponseVO listListContainer(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.listcontainer",listContainer);
	}
	
	/**
	 * 
	 * @author A-5437
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/validateContainerForReassign")
	public @ResponseBody ResponseVO validateContainerForReassign(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.validateContainerForReassign",listContainer);
	}
	
	/**
	 * 
	 * @author A-5437
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/unassignContainer")
	public @ResponseBody ResponseVO unassignContainer(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.unassignContainer",listContainer);
	}
	
	/**
	 * 
	 * 	Method		:	ListContainerActionController.reassignContainer
	 *	Added by 	:	a-7779 on 26-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param reassignContainer
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/reassignContainer")
	public @ResponseBody ResponseVO reassignContainer(@RequestBody ListContainerModel listContainer)
			throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.reassignContainer",listContainer);
	}
	/**
	 * 
	 * @author A-7929
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/validateContainerForTransfer")
	public @ResponseBody ResponseVO validateContainerTransferCommand(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.validateContainerTransferCommand",listContainer);
	}
	/**
	 * 
	 * @author A-7929
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/transferContainerSave")
	public @ResponseBody ResponseVO TransferContainerSaveCommand(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.transferContainerSave",listContainer);
	}
	/**
      *
	 * @author A-7929
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/contentIdSave")
	public @ResponseBody ResponseVO ContentIdSaveCommand(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.contentidSave",listContainer);
	}
	
	/**
    *
	* @author A-8672
	 * @param ListContainerModel
	 * @return
	 * @throws BusinessDelegateException
	 * @throws SystemException
	 */
	@RequestMapping("/saveActualWeight")
	public @ResponseBody ResponseVO SaveActualWeightCommand(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.saveActualWeight",listContainer);
	}
	
	/**
      *
      * @author A-5437
      * @return
      * @see com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController#getResourceBundle()
     */
	@Override
	public ICargoResourceBundle getResourceBundle() {
		return listContainerResourceBundle;
	}
	
	/**
	 * 
	 * 	Method		:	ListContainerActionController.OffloadContainerCommand
	 *	Added by 	:	A-8164 on 22-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param listContainer
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/offloadContainer")
	public @ResponseBody ResponseVO OffloadContainerCommand(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.offloadcontainer",listContainer);
	}
	/**
	 * 
	 * 	Method		:	ListContainerActionController.releaseContainer
	 *	Added by 	:	A-8893 on 14-Jan-2021
	 * 	Used for 	:
	 *	Parameters	:	@param listContainer
	 *	Parameters	:	@return
	 *	Parameters	:	@throws BusinessDelegateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ResponseVO
	 */
	@RequestMapping("/releaseContainer")
	public @ResponseBody ResponseVO releaseContainer(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.releaseContainer",listContainer);
	}
	
	@RequestMapping("/markUnmarkIndicator")
	public @ResponseBody ResponseVO markUnmarkIndicator(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.markUnmarkIndicator",listContainer);
	}
	
	@RequestMapping("/validateContainerForMarkingHba")
	public @ResponseBody ResponseVO validateContainerForMarkingHba(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.validateContainerForMarkingHba",listContainer);
	}
	@RequestMapping("/checkCloseFlight")
	public @ResponseBody ResponseVO checkCloseFlight(@RequestBody ListContainerModel listContainer)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.operations.ux.listcontainer.checkCloseFlight",listContainer);
	}
}
