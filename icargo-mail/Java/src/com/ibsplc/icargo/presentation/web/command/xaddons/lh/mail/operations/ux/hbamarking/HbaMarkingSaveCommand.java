package com.ibsplc.icargo.presentation.web.command.xaddons.lh.mail.operations.ux.hbamarking;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.xaddons.lh.mail.operations.LhMailOperationsDelegate;
import com.ibsplc.icargo.presentation.web.model.xaddons.lh.mail.operations.HbaMarkingModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.xaddons.lh.mail.operations.ux.hbamarking.HbaMarkingSaveCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	17-Oct-2022	:	Draft
 */
public class HbaMarkingSaveCommand extends AbstractCommand {
	
private static final Log LOGGER = LogFactory.getLogger("OPERATIONS SaveContainerHBADetails");
	

		
	
		public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
			
			LOGGER.entering("HbaMarkingSaveCommand", "execute");
			LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
			ResponseVO responseVO = new ResponseVO();
			List<ErrorVO> errors = new ArrayList<>();
			HbaMarkingModel hbaMarkingModel = null;
			hbaMarkingModel = (HbaMarkingModel) actionContext.getScreenModel();
			LhMailOperationsDelegate lhMailOperationsDelegate = new LhMailOperationsDelegate();
		    HbaMarkingVO hbaMarkingVO = constructHbaMarkingVO(hbaMarkingModel, logonAttributes.getCompanyCode());
			try {
				 lhMailOperationsDelegate.markHba(hbaMarkingVO);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
		    if (errors.size() > 0) {
				actionContext.addAllError(errors);
				return;
			}
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
			LOGGER.exiting("HbaMarkingSaveCommand", "execute");

		}
		
		private  HbaMarkingVO constructHbaMarkingVO(HbaMarkingModel hbaMarkingModel, String companyCode) {
			HbaMarkingVO hbaMarkingVO = new HbaMarkingVO();
			hbaMarkingVO.setHbaPosition(hbaMarkingModel.getPosition());
			hbaMarkingVO.setUldRefNo(hbaMarkingModel.getUldReferenceNumber());
			hbaMarkingVO.setHbaType(hbaMarkingModel.getHbaType());
			hbaMarkingVO.setCompanyCode(companyCode);
			hbaMarkingVO.setOperationFlag(hbaMarkingModel.getOperationFlag());
			hbaMarkingVO.setFlightNumber(hbaMarkingModel.getFlightNumber());
			hbaMarkingVO.setFlightSequenceNumber(hbaMarkingModel.getFlightSequenceNumber());
			hbaMarkingVO.setCarrierId(hbaMarkingModel.getCarrierId());
			hbaMarkingVO.setLegSerialNumber(hbaMarkingModel.getLegSerialNumber());
			hbaMarkingVO.setAssignedPort(hbaMarkingModel.getAssignedPort());  
			hbaMarkingVO.setContainerNumber(hbaMarkingModel.getContainerNumber());
			hbaMarkingVO.setContainerType(hbaMarkingModel.getType());
			return hbaMarkingVO;

		}
	
}
