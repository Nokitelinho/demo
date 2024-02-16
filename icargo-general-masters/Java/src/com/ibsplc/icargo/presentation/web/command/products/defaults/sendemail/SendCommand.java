package com.ibsplc.icargo.presentation.web.command.products.defaults.sendemail;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageDespatchDetailsVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.presentation.delegate.products.defaults.ProductDefaultsDelegate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.SendEmailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * ScreenloadProductLovCommdand is for screenload action of productLov
 * @author a-1870
 *
 */
public class SendCommand extends BaseCommand{
	/**
	 * Log
	 */
	private Log log = LogFactory.getLogger("Tracking");
	/**
	 * UNKNOWN_MESSAGE
	 */
    public static final String UNKNOWN_MESSAGE = "UNK";
    /**
	 * STATUS
	 */
    public static final String STATUS = "S";
    /**
     * OUTGOING
     */
    public static final String OUTGOING = "O";
    /**
     * INSERT
     */
    public static final String INSERT = "I";
    
    /**
     * MESSAGE
     */
    public static final String MESSAGE = "M";

	/**
	 *TARGET
	 */
	private static final String TARGET="send_success";
	
	/**
	 *FAILURE_TARGET
	 */
	private static final String FAILURE_TARGET="send_failure";
	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering("SendCommand", "execute");
		SendEmailForm sendEmailForm = 
			(SendEmailForm)invocationContext.screenModel;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
		errors = validateForm(sendEmailForm);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE_TARGET;
			return;
		}
		MessageVO vo=findDetails(sendEmailForm);
		log.log(Log.INFO, "\n\n\n***********mesageVO sent to server****", vo);
		try{
		   new ProductDefaultsDelegate().sendMessageForProduct(vo);
		}catch(BusinessDelegateException businessDelegateException){
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		
		
		sendEmailForm.setEmail("");
		sendEmailForm.setName("");
		sendEmailForm.setFriendEmail("");
		sendEmailForm.setFriendName("");
		ErrorVO error = null;
		Object[] obj = { "Segment" };
		error = new ErrorVO("products.defaults.savesuccessaftersendemail", obj); //saved successfully
		error.setErrorDisplayType(ErrorDisplayType.INFO);
		errors.add(error);
		invocationContext.addAllError(errors);
		invocationContext.target=TARGET;
		log.exiting("SendCommand", "execute");
	}
	/**
	 * 
	 * @param sendEmailForm
	 */
   private MessageVO findDetails(SendEmailForm sendEmailForm){
	   MessageVO messageVO=new MessageVO();
		messageVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		messageVO.setStationCode(
				getApplicationSession().getLogonVO().getStationCode());
		String companyCode=
			getApplicationSession().getLogonVO().getCompanyCode();
		StringBuilder build=new StringBuilder();
		String code=sendEmailForm.getCode();
		log.log(Log.INFO, "\n\n\n***********code from jsp****", code);
		String afterBuild=(build.append(
				companyCode).append("-").append(code)).toString();
		messageVO.setRawMessage(afterBuild);
		messageVO.setOriginalMessage(afterBuild);
		messageVO.setMessageType(UNKNOWN_MESSAGE);
		
		messageVO.setReceiptOrSentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		messageVO.setMessageDirection(OUTGOING);
		messageVO.setStatus(STATUS);
		messageVO.setMessageSource(sendEmailForm.getEmail());
		messageVO.setOperationFlag(INSERT);
		Collection<MessageDespatchDetailsVO> detailsVOs=new 
		ArrayList<MessageDespatchDetailsVO>();
		MessageDespatchDetailsVO detailsVO=new MessageDespatchDetailsVO();
		detailsVO.setOperationFlag(INSERT);
		detailsVO.setAddress(sendEmailForm.getFriendEmail());
		detailsVO.setMode(MESSAGE);
		detailsVOs.add(detailsVO);
		messageVO.setDespatchDetails(detailsVOs);
		return messageVO;
	
  }
	/**
	 * 
	 * @param form
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(SendEmailForm form){
		log.log(Log.FINE,"\n\n\n -----inside-validateForm---->" );
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if("".equals(form.getName())){
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.namecannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getEmail())){
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.emailcannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getFriendName())){
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.friendnamecannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		if("".equals(form.getFriendEmail())){
			ErrorVO error = null;
			error = new ErrorVO(
						"products.defaults.friendemailcannotbenull");
		    error.setErrorDisplayType(ErrorDisplayType.ERROR);
		    errors.add(error);
		}
		return errors;
	}
}
