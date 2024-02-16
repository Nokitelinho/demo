package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TimeZone;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentScreeningDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.RoutingInConsignment;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListScreeningDetailsCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("OPERATIONS MAIL");
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering("ListScreeningDetails", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		MailTrackingDefaultsDelegate mailtrackingdefaultsdelegate = new MailTrackingDefaultsDelegate();
		ConsignmentDocumentVO consignmentDocumentVO = null; 
		ConsignmentScreeningModel screeningmodel = (ConsignmentScreeningModel)actionContext.getScreenModel();
		Collection<ConsignmentScreeningDetail> consignmentScreeningDetail = new ArrayList<>();
		Collection<RoutingInConsignment> routingInConsignment = new ArrayList<>();
		String consignmentNumber = screeningmodel.getConDocNo();
		String companyCode = logonAttributes.getCompanyCode();
		String poaCode = screeningmodel.getPaCode();
		screeningmodel.setLoginUser(logonAttributes.getUserId());
		consignmentDocumentVO = mailtrackingdefaultsdelegate.findConsignmentScreeningDetails(consignmentNumber, companyCode,poaCode);
		
		if(consignmentDocumentVO!=null){
			screeningmodel.setApplicableRegTransportDirection(consignmentDocumentVO.getApplicableRegTransportDirection());
			screeningmodel.setApplicableRegBorderAgencyAuthority(consignmentDocumentVO.getApplicableRegBorderAgencyAuthority());
			screeningmodel.setApplicableRegReferenceID(consignmentDocumentVO.getApplicableRegReferenceID());
			screeningmodel.setApplicableRegFlag(consignmentDocumentVO.getApplicableRegFlag());
			screeningmodel.setCountryCode(consignmentDocumentVO.getCountryCode());
			screeningmodel.setMailCategory(consignmentDocumentVO.getMailCategory());
			if(consignmentDocumentVO.getConsignmentDate()!=null){
				screeningmodel.setConsignDate(consignmentDocumentVO.getConsignmentDate().toDisplayFormat());
			}
			screeningmodel.setConsignmentOrigin(consignmentDocumentVO.getConsignmentOrigin());
			screeningmodel.setConsigmentDest(consignmentDocumentVO.getConsigmentDest());
			screeningmodel.setPaCode(consignmentDocumentVO.getPaCode());
			screeningmodel.setCompanyCode(consignmentDocumentVO.getCompanyCode());
			screeningmodel.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
			screeningmodel.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			screeningmodel.setSecurityStatusCode(consignmentDocumentVO.getSecurityStatusCode());
			screeningmodel.setStatedBags(consignmentDocumentVO.getStatedBags());
			screeningmodel.setStatedWeight(consignmentDocumentVO.getStatedWeight().getRoundedDisplayValue());
			screeningmodel.setAirportCode(logonAttributes.getAirportCode());
			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			TimeZone timezone = sd.getTimeZone();
			screeningmodel.setTimeZone(timezone.getID());
			consignmentScreeningDetail = MailOperationsModelConverter.constructScreeningDetails(consignmentDocumentVO.getConsignementScreeningVOs());
			screeningmodel.setConsignmentScreeningDetail(consignmentScreeningDetail);
			
			routingInConsignment = MailOperationsModelConverter.constructRoutingDetails(consignmentDocumentVO.getRoutingInConsignmentVOs());
			screeningmodel.setRoutingInConsignmentVO(routingInConsignment);
			screeningmodel.setCsgIssuerName(consignmentDocumentVO.getCsgIssuerName());
			screeningmodel.setMstAddionalSecurityInfo(consignmentDocumentVO.getMstAddionalSecurityInfo());
			ResponseVO responseVO = new ResponseVO();
			ArrayList<ConsignmentScreeningModel> results = new ArrayList<>();
			results.add(screeningmodel);
			responseVO.setResults(results);
		    actionContext.setResponseVO(responseVO);
		}else{
			actionContext.addError(new ErrorVO("Consignment does not exist"));
			return;
		}
		
		log.exiting("ListScreeningDetails","execute");
	}
	

}
