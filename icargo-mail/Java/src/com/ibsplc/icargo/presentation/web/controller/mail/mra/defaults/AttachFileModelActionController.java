/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.defaults.AttachFileModelActionController.java
 *
 *	Created by	:	A-4809
 *	Created on	:	01-Nov-2021
 *
 *  Copyright 2021 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.controller.mail.mra.defaults;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController;
import com.ibsplc.icargo.framework.web.spring.resource.ICargoResourceBundle;
import com.ibsplc.icargo.presentation.web.model.mail.mra.defaults.AttachFileModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.controller.mail.mra.defaults.AttachFileModelActionController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	01-Nov-2021	:	Draft
 */
@Controller
@RequestMapping("mail/mra/defaults/attachfile")

public class AttachFileModelActionController extends AbstractActionController<AttachFileModel>{
    @Resource(name="attachFileResourceBundle")
    ICargoResourceBundle attachFileResourceBundle;

	@RequestMapping("/screenload")
	public @ResponseBody ResponseVO fetchDetailsOnScreenload(@RequestBody AttachFileModel attachFileModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.attachfile.fetchdetailsonscreenload",attachFileModel);
	}
	@RequestMapping("/excelupload")
	public @ResponseBody ResponseVO fetchDetailsOnList(@RequestBody AttachFileModel attachFileModel)
		throws BusinessDelegateException, SystemException{
		return performAction("mail.mra.defaults.ux.attachfile.fetchdetailsonexcelupload",attachFileModel);
	}
    
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.spring.controller.AbstractActionController#getResourceBundle()
	 *	Added by 			: A-4809 on 01-Nov-2021
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */
	@Override
	public ICargoResourceBundle getResourceBundle() {
		// TODO Auto-generated method stub
		return attachFileResourceBundle;
	}

}
