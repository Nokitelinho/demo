package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.validators;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.orchestration.Validator;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import org.springframework.stereotype.Component;

@Component("mandatoryfieldsinpawbvalidator")
public class MandatoryFieldsInPawbValidator extends Validator<CarditVO> {
//	@Autowired
//	@Qualifier("mailController")
//	private MailController mailController;

	@Override
	public void validate(CarditVO carditVO) throws BusinessException {
		if (carditVO.getCarditPawbDetailsVO() == null) {
			throw new MailOperationsBusinessException(
					MailOperationsBusinessException.REQUIRED_INFO_FOR_CREATING_PAWB_MISSING);
		} else {
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
			boolean isOrigin = false;
			boolean isDestination = false;
			boolean isAgent = false;
			boolean isConsignee = false;
			if (carditPawbDetail.getConsignmentOriginAirport() == null) {
				isOrigin = true;
			}
			if (carditPawbDetail.getConsignmentDestinationAirport() == null) {
				isDestination = true;
			}
			if (carditPawbDetail.getAgentCode() == null || carditPawbDetail.getAgentCode().isEmpty()) {
				isAgent = true;
			}
			if (carditPawbDetail.getConsigneeAgentCode() == null
					|| carditPawbDetail.getConsigneeAgentCode().isEmpty()) {
				isConsignee = true;
			}
			if (isOrigin || isDestination || isAgent || isConsignee) {
				String exception = "";
				if (isOrigin && isDestination && isAgent && isConsignee) {
					exception = MailOperationsBusinessException.MANDATORY_FIELDS_IN_PAWB_MISSING;
				} else if (isOrigin && isDestination && isAgent) {
					exception = MailOperationsBusinessException.ORIGIN_DESTINATION_AGENT_IN_PAWB_MISSING;
				} else if (isOrigin && isDestination && isConsignee) {
					exception = MailOperationsBusinessException.ORIGIN_DESTINATION_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isOrigin && isAgent && isConsignee) {
					exception = MailOperationsBusinessException.ORIGIN_AGENT_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isDestination && isAgent && isConsignee) {
					exception = MailOperationsBusinessException.DESTINATION_AGENT_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isOrigin && isAgent) {
					exception = MailOperationsBusinessException.ORIGIN_AGENT_IN_PAWB_MISSING;
				} else if (isOrigin && isConsignee) {
					exception = MailOperationsBusinessException.ORIGIN_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isOrigin && isDestination) {
					exception = MailOperationsBusinessException.ORIGIN_DESTINATION_IN_PAWB_MISSING;
				} else if (isDestination && isAgent) {
					exception = MailOperationsBusinessException.DESTINATION_AGENT_IN_PAWB_MISSING;
				} else if (isDestination && isConsignee) {
					exception = MailOperationsBusinessException.DESTINATION_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isAgent && isConsignee) {
					exception = MailOperationsBusinessException.AGENT_CONSIGNEE_IN_PAWB_MISSING;
				} else if (isDestination) {
					exception = MailOperationsBusinessException.DESTINATION_IN_PAWB_MISSING;
				} else if (isOrigin) {
					exception = MailOperationsBusinessException.ORGIN_IN_PAWB_MISSING;
				} else if (isAgent) {
					exception = MailOperationsBusinessException.AGENT_IN_PAWB_MISSING;
				} else {
					exception = MailOperationsBusinessException.CONSIGNEE_IN_PAWB_MISSING;
				}
				String[] errorData = null;
				ErrorVO errorVO = new ErrorVO(exception);
				errorData = (String[]) errorVO.getErrorData();
				if(errorData!=null)
					throw new MailOperationsBusinessException(exception,errorData);//to do getting NULL in errorData
			}
		}
	}
}
