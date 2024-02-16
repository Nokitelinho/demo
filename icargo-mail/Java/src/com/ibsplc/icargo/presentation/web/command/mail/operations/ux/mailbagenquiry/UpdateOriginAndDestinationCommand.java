package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagenquiry.UpdateOriginAndDestination.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8353	:	28-Mar-2021	:	Draft
 */
public class UpdateOriginAndDestinationCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail Operations");
	 private static final String CONST_SCREENID = "MTK057";
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		  log.entering("UpdateOriginAndDestinationCommand","execute");
		  LogonAttributes logonAttributes =  getLogonAttribute();
		  MailbagEnquiryModel mailbagEnquiryModel = (MailbagEnquiryModel) actionContext.getScreenModel();
		  MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		  ResponseVO responseVO = new ResponseVO();
		  Collection<Mailbag> selectedMailbags = null;
		  Collection<MailbagVO> mailbagVOs = null;
		  Collection<ErrorVO> errors = new ArrayList<>();
		  AreaDelegate areaDelegate = new AreaDelegate();
		  if (mailbagEnquiryModel != null && mailbagEnquiryModel.getSelectedMailbags() != null 
				  &&!mailbagEnquiryModel.getSelectedMailbags().isEmpty()) {
			log.log(Log.FINE, "mailbagEnquiryModel.getSelectedMailbags() not null");
			selectedMailbags = mailbagEnquiryModel.getSelectedMailbags();
			mailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);
			for(MailbagVO mailbagVO:mailbagVOs){
				int errorFlag=1;
				if(mailbagVO.getMailOrigin()==null||mailbagVO.getMailOrigin().trim().length()==0){
					Object[] obj = {mailbagVO.getMailbagId() };
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.emptyorigin", obj));
					return;
				}
				if(mailbagVO.getMailDestination()==null||mailbagVO.getMailDestination().trim().length()==0){
					Object[] obj = {mailbagVO.getMailbagId() };
					actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.emptydestination", obj));
					return;
				}
				if(mailbagVO.getMailOrigin()!=null&&mailbagVO.getMailOrigin().trim().length()>0){
					try {
						areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), mailbagVO.getMailOrigin().toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {
						errorFlag = 1;
						errors = handleDelegateException(businessDelegateException);
					}
					if (errors != null && !errors.isEmpty() && errorFlag == 1) {
						Object[] obj = { mailbagVO.getMailOrigin().toUpperCase(),mailbagVO.getMailbagId() };
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invalidorigin", obj));
						return;
					}
				}
				if(mailbagVO.getMailDestination()!=null&&mailbagVO.getMailDestination().trim().length()>0){
					try {
						areaDelegate.validateAirportCode(logonAttributes.getCompanyCode(), mailbagVO.getMailDestination().toUpperCase());
					} catch (BusinessDelegateException businessDelegateException) {
						errorFlag = 1;
						errors = handleDelegateException(businessDelegateException);
					}
					if (errors != null && !errors.isEmpty() && errorFlag == 1) {
						Object[] obj = { mailbagVO.getMailDestination().toUpperCase(),mailbagVO.getMailbagId() };
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.invaliddestination", obj));
						return;
					}
				}
				if(mailbagVO.getMailOrigin()!=null&&mailbagVO.getMailOrigin().trim().length()>0
						&&mailbagVO.getMailDestination()!=null&&mailbagVO.getMailDestination().trim().length()>0
						&&mailbagVO.getMailOrigin().equals(mailbagVO.getMailDestination()) ){
						Object[] obj = {mailbagVO.getMailbagId()};
						actionContext.addError(new ErrorVO("mailtracking.defaults.mailbagenquiry.msg.err.sameorigindest", obj));
						return;
				}
				String screenId=CONST_SCREENID;
				if(mailbagEnquiryModel.getNumericalScreenId()!=null){
				screenId= mailbagEnquiryModel.getNumericalScreenId();
				}
				mailbagVO.setMailbagSource(screenId);

				mailbagVO.setTriggerForReImport(MailConstantsVO.ORIGIN_DESTINATION_UPDATE);

			}
			try {
				mailTrackingDefaultsDelegate.updateOriginAndDestinationForMailbag(mailbagVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		   }
	       if (  !errors.isEmpty()) {
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
	       responseVO.setStatus("success");
	       actionContext.setResponseVO(responseVO);
		  log.exiting("UpdateOriginAndDestinationCommand","execute");
	}
}