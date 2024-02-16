package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Validator;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

@FeatureComponent(SavePAWBDetailsFeatureConstants.MANDATORY_FIELDS_IN_PAWB_VALIDATOR)
public class MandatoryFieldsInPawbValidator extends Validator<CarditVO> {
	@Autowired
	@Qualifier("mAilcontroller")
	private MailController mailController;

	@Override
	public void validate(CarditVO carditVO) throws BusinessException, SystemException {
		if(carditVO.getCarditPawbDetailsVO()==null) {
			throw new MailTrackingBusinessException(
					MailTrackingBusinessException.REQUIRED_INFO_FOR_CREATING_PAWB_MISSING);
		}else {
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
			if (carditPawbDetail.getAgentCode() == null
					|| carditPawbDetail.getAgentCode().isEmpty()) {


			isAgent = true;
		}
			if ( carditPawbDetail.getConsigneeAgentCode() == null
					|| carditPawbDetail.getConsigneeAgentCode().isEmpty()) {
			isConsignee = true;
		}
		if (isOrigin || isDestination || isAgent || isConsignee) {
			String exception ="";
			if(isOrigin && isDestination && isAgent && isConsignee) {
				 exception =MailTrackingBusinessException.MANDATORY_FIELDS_IN_PAWB_MISSING;
			}else if(isOrigin && isDestination && isAgent) {
				exception = MailTrackingBusinessException.ORIGIN_DESTINATION_AGENT_IN_PAWB_MISSING;
			}else if(isOrigin && isDestination && isConsignee) {
				exception = MailTrackingBusinessException.ORIGIN_DESTINATION_CONSIGNEE_IN_PAWB_MISSING;	
			}else if(isOrigin  && isAgent && isConsignee) {
				exception = MailTrackingBusinessException.ORIGIN_AGENT_CONSIGNEE_IN_PAWB_MISSING;
			}else if(isDestination && isAgent && isConsignee) {
				exception = MailTrackingBusinessException.DESTINATION_AGENT_CONSIGNEE_IN_PAWB_MISSING;
			}else if(isOrigin  && isAgent) {
				exception = MailTrackingBusinessException.ORIGIN_AGENT_IN_PAWB_MISSING;
			}else if(isOrigin  && isConsignee) {
				exception = MailTrackingBusinessException.ORIGIN_CONSIGNEE_IN_PAWB_MISSING;
			}else if(isOrigin && isDestination) {
				exception = MailTrackingBusinessException.ORIGIN_DESTINATION_IN_PAWB_MISSING;
			}else if(isDestination && isAgent) {
				exception = MailTrackingBusinessException.DESTINATION_AGENT_IN_PAWB_MISSING;
			}else if(isDestination && isConsignee) {
				exception = MailTrackingBusinessException.DESTINATION_CONSIGNEE_IN_PAWB_MISSING;
			}else if(isAgent && isConsignee) {
				exception = MailTrackingBusinessException.AGENT_CONSIGNEE_IN_PAWB_MISSING;
			}else if(isDestination) {
				exception = MailTrackingBusinessException.DESTINATION_IN_PAWB_MISSING;
			}else if(isOrigin) {
				exception = MailTrackingBusinessException.ORGIN_IN_PAWB_MISSING;
			}else if(isAgent) {
				exception = MailTrackingBusinessException.AGENT_IN_PAWB_MISSING;
			}else {
				exception = MailTrackingBusinessException.CONSIGNEE_IN_PAWB_MISSING;
			}
			
			String[] errorData = null;
			ErrorVO errorVO = new ErrorVO(exception);
			errorData = (String[]) errorVO.getErrorData();
				throw new MailTrackingBusinessException(exception, errorData);
			}
		}
	}

}
