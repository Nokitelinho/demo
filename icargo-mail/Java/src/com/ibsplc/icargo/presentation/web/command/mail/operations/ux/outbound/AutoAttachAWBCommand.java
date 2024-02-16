package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class AutoAttachAWBCommand extends AbstractCommand {
	   private static final String OUTBOUND = "O";
	   private static final String PAWBASSCONENAB ="PAWBASSCONENAB";
		private static final String PAWBPARMVALYES ="YES";
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
	CommandInvocationException {
OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
FlightFilterVO flightFilterVO = null;
flightFilterVO = new FlightFilterVO();
List<ErrorVO> errorVOs = null;
LogonAttributes logonAttributes = getLogonAttribute();
ResponseVO responseVO = new ResponseVO();
List<OutboundModel> results = new ArrayList();

MailManifestVO mailManifestVO = new MailManifestVO();

ArrayList<DSNVO> dsnVOs =null;
Collection<ContainerDetailsVO> resultVOs = new ArrayList();
OperationalFlightVO operationalFlightVO = MailOutboundModelConverter.constructOperationalFlightVO(outboundModel.getMailAcceptance(),logonAttributes);

MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = 
new MailTrackingDefaultsDelegate();
try
{
	mailManifestVO = mailTrackingDefaultsDelegate.findContainersInFlightForManifest(operationalFlightVO);
}
catch (BusinessDelegateException businessDelegateException)
{
	errorVOs = handleDelegateException(businessDelegateException);
}





Collection<ContainerDetailsVO> containerDetails = mailManifestVO.getContainerDetails();
if ((containerDetails == null) || (containerDetails.size() == 0))
{
	actionContext.addError(new ErrorVO("mailtracking.defaults.attachawb.msg.err.nomailstoattach"));
	return;
}

else{
  try
  {
					 
    resultVOs = mailTrackingDefaultsDelegate.autoAttachAWBDetails(containerDetails, operationalFlightVO);
  }
  catch (BusinessDelegateException businessDelegateException)
  {
	  errorVOs = handleDelegateException(businessDelegateException);
  }
  if ((errorVOs != null) && (errorVOs.size() > 0))
  {
	  actionContext.addAllError(errorVOs);
      return;
  }
}

results.add(outboundModel);
responseVO.setResults(results);
responseVO.setStatus("success");
actionContext.setResponseVO(responseVO);
}
	
	
private boolean checkForPAWBEnabledConsignment(DSNVO dsnVO,LogonAttributes logonAttributes) throws BusinessDelegateException{
	Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOs;
	 boolean pawbFlag =false;
					String paCode =  dsnVO.getPaCode();
					PostalAdministrationVO postalAdministrationVO;
					postalAdministrationVO = new MailTrackingDefaultsDelegate().findPACode(logonAttributes.getCompanyCode(),paCode);
				    postalAdministrationDetailsVOs = postalAdministrationVO.getPostalAdministrationDetailsVOs().get("INVINFO");
							for (PostalAdministrationDetailsVO postalAdministrationDetailsVO : postalAdministrationDetailsVOs) {
								
								if (postalAdministrationDetailsVO != null && postalAdministrationDetailsVO.getParCode()!= null && PAWBASSCONENAB.equals(postalAdministrationDetailsVO.getParCode()) && postalAdministrationDetailsVO.getParameterValue() != null && PAWBPARMVALYES.equalsIgnoreCase(postalAdministrationDetailsVO.getParameterValue())) {
									pawbFlag= true;
									
								}
							}
							return pawbFlag;
							
							
						
				}
	

	
}
	