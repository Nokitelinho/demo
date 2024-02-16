package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DeleteEmptyContainerCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("DELETE CONTAINER");
	private  static final String SUCCESS_MESSAGE="mail.operations.err.deletesuccess";
	private static final String SUCCESS="success";	
	private  static final String ERROR_MESSAGE="mail.operations.cannotdeletecontainers";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		log.log(Log.FINE, "<------------DELETE CONTAINER ------------> ");
		OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
		ContainerDetails containerDetails=new ContainerDetails();
		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList();
		List<ErrorVO> errorVOs = null;
		if(outboundModel.getContainerInfo()!=null) {
			containerDetails=outboundModel.getContainerInfo();
		}
		
		
		if(containerDetails.getBags()>0){
			ErrorVO error = new ErrorVO(ERROR_MESSAGE,new Object[]{containerDetails.getContainerNumber()});      
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(error);
			return;
		}
		
		ContainerDetailsVO containerDetailsVO = MailOutboundModelConverter.constructContainerDetailVO(containerDetails);
		if (containerDetails.getPol() != null && containerDetails.getPol().trim().length() > 0 && containerDetails.getFlightDate() != null
				&& containerDetails.getFlightDate().trim().length() > 0) {
		LocalDate flightDate = new LocalDate(containerDetails.getPol(), Location.ARP,false);
		flightDate.setDate(containerDetails.getFlightDate());
		containerDetailsVO.setFlightDate(flightDate);
		}
		try{
			 new MailTrackingDefaultsDelegate().deleteEmptyContainer(containerDetailsVO);
		}catch(BusinessDelegateException businessDelegateException){
			errorVOs = handleDelegateException(businessDelegateException);
			actionContext.addAllError(errorVOs);
			return;
		}
		
		
		results.add(outboundModel);
		responseVO.setResults(results);
		responseVO.setStatus(SUCCESS);
		ErrorVO error = new ErrorVO(SUCCESS_MESSAGE);      
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		actionContext.addError(error);    
		actionContext.setResponseVO(responseVO);
	
		 
	}
	
}