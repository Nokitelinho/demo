package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.AttachAwbDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class DetatchAWBCommand extends AbstractCommand {

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailinbound";
	private static final String ALREADY_ATTACHED = "mailtracking.defaults.attachawb.msg.err.mailbagsalreadyattached";
	private static final String DIFFERENT_ORG_DST = "mailtracking.defaults.attachawb..msg.err.differentairports";
	private static final String ALLOW_NON_STANDARD_AWB = "operations.shipment.allownonstandardawb";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";
	private static final String STOCK_REQ_PARAMETER = "mailtracking.defaults.stockcheckrequired";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String WEIGHT_CODE = "shared.defaults.weightUnitCodes";
	private static final String CATEGORY_CODE = "mailtracking.defaults.mailcategory";
	private static final String CONTAINER_TYPE = "mailtracking.defaults.containertype";
	private static final String CANNOT_BE_DETACHED = "mailtracking.defaults.detachawb.msg.err.cannotbedetached";

	private Log log = LogFactory.getLogger("MAIL OPERATIONS ScreenLoadAttachAwbCommand");

	public void execute(ActionContext actionContext) throws BusinessDelegateException {

		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		OutboundModel outboundModel = (OutboundModel) actionContext.getScreenModel();
		Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
		Collection<ContainerDetailsVO> containerDetVO = new ArrayList<ContainerDetailsVO>();
		ArrayList<MailbagVO> selectedMailbagsVOs = null;
		ArrayList<MailbagVO> mailbagVOs = null;
		ArrayList<DSNVO> dsnVos = null;
		AttachAwbDetails attachAwbDetails = new AttachAwbDetails();
		ContainerDetails containerDetails = null;
		ContainerDetailsVO containerDetailsVO = null;
		List<ErrorVO> errors = null;
		ArrayList<ContainerDetails> containerDetailsCollection = outboundModel.getContainerDetailsCollection();
		ArrayList<DespatchDetails> dsnList = outboundModel.getDespatchDetailsList();
		 ResponseVO responseVO = new ResponseVO();
		  List<OutboundModel> results = new ArrayList<>();
		
		if (containerDetailsCollection != null) {
			containerDetails = containerDetailsCollection.get(0);
			containerDetailsVO = MailOutboundModelConverter.constructContainerDetailsVO(containerDetails,
					logonAttributes);
			containers.add(containerDetailsVO);

			Collection<DSNVO> dsnVOs = MailOutboundModelConverter.constructDSNVOs(dsnList);
			containerDetailsVO.setDsnVOs(dsnVOs);
if (!(containerDetailsVO.getDsnVOs().isEmpty())) {
			Iterator<DSNVO> iterator = containerDetailsVO.getDsnVOs().iterator();
			while (iterator.hasNext()) {
				DSNVO dsnVO = iterator.next();
				String documentNumber = dsnVO.getMasterDocumentNumber();
				if (documentNumber == null || documentNumber.trim().isEmpty()) {
					actionContext.addError(new ErrorVO(CANNOT_BE_DETACHED));
					return;
				}
			}
			}
else {
	containerDetailsVO.setFromDetachAWB("Y");}
			containerDetailsVO.setFromScreen("Outbound");
			
			MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
			try {
				mailTrackingDefaultsDelegate.detachAWBDetails(containerDetailsVO);

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				actionContext.addAllError(errors);
			}
		}
		results.add(outboundModel);
        responseVO.setResults(results);
        responseVO.setStatus("success");
        actionContext.setResponseVO(responseVO);
	}
}
