package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailbagFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ListMailbagsinContainersDSNview extends AbstractCommand {
	private static final String OUTBOUND = "O";

	public void execute(ActionContext actionContext)
			throws BusinessDelegateException, CommandInvocationException {
		OutboundModel outboundModel = (OutboundModel) actionContext
				.getScreenModel();
		FlightFilterVO flightFilterVO = null;
		flightFilterVO = new FlightFilterVO();
		Collection<ErrorVO> errors = new ArrayList();
		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList();
		ContainerDetails containerDetails = null;
		if (outboundModel.getContainerInfo() != null) {
			containerDetails = outboundModel.getContainerInfo();
		}
		int mailbagDSNDisplayPage= outboundModel.getMailbagsDSNDisplayPage();
		ContainerDetailsVO containervo = MailOutboundModelConverter
				.constructContainerDetailVO(containerDetails);
		if(outboundModel.getMailbagFilter()!=null) {
			containervo.setAdditionalFilters(populateAdditionalDSNFilter(outboundModel.getMailbagFilter()));
		}
		int displayPage = 1;
		Page<DSNVO> mailbagPagedsnview = null;
		PageResult<DespatchDetails> mailPageResultdsnview = null;
		if (outboundModel.getFlightCarrierFilter().getAssignTo().equals("F")) {

			try {
				mailbagPagedsnview = new MailTrackingDefaultsDelegate()
						.getMailbagsinContainerdsnview(containervo, mailbagDSNDisplayPage);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}

		else {

			try {
				mailbagPagedsnview = new MailTrackingDefaultsDelegate()
						.getMailbagsinCarrierdsnview(containervo, mailbagDSNDisplayPage);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		List<DespatchDetails> mailbagListDSNView = new ArrayList<>();
		if(mailbagPagedsnview!=null) {
		   mailbagListDSNView = MailOutboundModelConverter
				.constructMailbagDetailsDSNView(mailbagPagedsnview);
		} else {
			mailbagPagedsnview = new Page<DSNVO>(new ArrayList<DSNVO>(),1, 1, 0, 0, 0,false,0);
		}
		mailPageResultdsnview = new PageResult<DespatchDetails>(mailbagPagedsnview,
				mailbagListDSNView);
		outboundModel.getContainerInfo().setDsnviewpagelist(
				mailPageResultdsnview);
		// outboundModel.getMailFlight().getContainerPageInfo();
		// HashMap<String,Page<MailbagVO>> map = new
		// HashMap<String,Page<MailbagVO>>();
		// map.put(containerDetail.getContainerNumber(),mailbagPage);
		// outboundModel.setContainerMap(map);
		// Page<ContainerDetail> contPage= new
		// Page(outboundModel.getMailFlight().getContainerPageInfo());
		// outboundModel.getMailFlight().getContainerPageInfo();

		// MailFlight mailflight
		// =MailOutboundModelConverter.constructMailAcceptance(newMailAcceptanceVO,outboundModel.getMailFlight());
		// FlightValidation flightValidation
		// =MailOutboundModelConverter.constructFlightValidation(flightValidationVO,
		// logonAttributes);
		// PageResult<MailFlight> pageList= new
		// PageResult<MailFlight>(mailAcceptanceVOs,mailflights);
		// outboundModel.setMailFlight(mailflight);
		// String flightpk
		// =operationalFlightVO.getCarrierCode()+operationalFlightVO.getFlightNumber()+operationalFlightVO.getFlightSequenceNumber();
		// outboundModel.setFlightPK(flightpk);
		results.add(outboundModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);

	}

	private MailbagEnquiryFilterVO populateAdditionalDSNFilter(MailbagFilter mailbagFilter) {
		MailbagEnquiryFilterVO mailbagEnquiryFilterVO = new MailbagEnquiryFilterVO();
		mailbagEnquiryFilterVO.setDespatchSerialNumber(mailbagFilter.getDespatchSerialNumber());
		mailbagEnquiryFilterVO.setOoe(mailbagFilter.getOoe());
		mailbagEnquiryFilterVO.setDoe(mailbagFilter.getDoe());
		mailbagEnquiryFilterVO.setPacode(mailbagFilter.getPaCode());
		mailbagEnquiryFilterVO.setMailCategoryCode(mailbagFilter.getMailCategoryCode());
		mailbagEnquiryFilterVO.setMailSubclass(mailbagFilter.getMailSubclass());
		mailbagEnquiryFilterVO.setShipmentPrefix(mailbagFilter.getShipmentPrefix());
		mailbagEnquiryFilterVO.setMasterDocumentNumber(mailbagFilter.getMasterDocumentNumber());
		return mailbagEnquiryFilterVO;
	}
}
