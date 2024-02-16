package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.log.Log;
public class ScreenloadReturnMailCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");


	/**
	 * @param args
	 */
	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException,
			CommandInvocationException {
		LogonAttributes logonAttributes = getLogonAttribute();
		String cmpcod = logonAttributes.getCompanyCode();
	    OutboundModel outboundModel = (OutboundModel)actionContext.getScreenModel();
	
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO = new AreaDelegate().validateAirportCode(
				logonAttributes.getCompanyCode(),
				logonAttributes.getAirportCode());
		String countryCode = airportValidationVO.getCountryCode();
	Collection<PostalAdministrationVO> postalAdministrationVOs = new MailTrackingDefaultsDelegate().findLocalPAs(
			logonAttributes.getCompanyCode(),
			countryCode);
	MailOperationsModelConverter mailOperationsModelConverter = new MailOperationsModelConverter();
	outboundModel.setPostalAdministrations(mailOperationsModelConverter.convertPostalAdministartionVos(postalAdministrationVOs));
	  ResponseVO responseVO = new ResponseVO();
	     List<OutboundModel> results = new ArrayList();
	     results.add(outboundModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);
	     this.log.exiting("ScreenLoadCommand", "execute");

		
	}

}
