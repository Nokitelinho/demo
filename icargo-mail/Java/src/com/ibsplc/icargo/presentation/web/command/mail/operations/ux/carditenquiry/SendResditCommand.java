package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.carditenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.CarditEnquiryModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SendResditCommand  extends AbstractCommand{
	   private static final String UNPROCESSED_RESDIT="mailtracking.defaults.carditenquiry.msg.err.unprocessedresdits";
	   public static final String CONST_RESDIT_STATUS_UNPROCESSED = "UP"; 
	   public static final String CONST_MESSAGE_INFO_SEND_SUCCESS = "mailtracking.defaults.carditenquiry.msg.info.sendsuccessfully";
	   
	private Log log = LogFactory.getLogger("OPERATIONS CARDITENQUIRY NEO");
	@Override
	public void execute(ActionContext actionContext)
	    throws BusinessDelegateException {
		this.log.entering("SendResditCommand", "execute");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes = getLogonAttribute();
		CarditEnquiryModel carditEnquiryModel = (CarditEnquiryModel)actionContext.getScreenModel();
		this.log.log(5, new Object[] { "OOE ---> ", carditEnquiryModel.getOoe() });		
		this.log.log(5, new Object[] { "DOE ---> ", carditEnquiryModel.getDoe()});
		CarditEnquiryFilterVO carditEnquiryFilterVO = null;
		 List<CarditEnquiryModel> results = new ArrayList();
		 ResponseVO responseVO = new ResponseVO();
		Collection<Mailbag> selectedMailbags = carditEnquiryModel.getSelectedMailbags();
    	Collection<MailbagVO> selectedMailbagVOs = MailOperationsModelConverter.constructMailbagVOs(selectedMailbags, logonAttributes);
    	

    	CarditEnquiryVO carditEnquiryVO = new CarditEnquiryVO();
    //	carditEnquiryVO.setUnsendResditEvent(carditEnquiryForm.getResdit());
    	carditEnquiryVO.setSearchMode(MailConstantsVO.CARDITENQ_MODE_DOC);

     	
    	
    	Collection<MailbagHistoryVO> mailbagHistoryVOs=null;
    	
    	if(selectedMailbagVOs!=null && selectedMailbagVOs.size() > 0){
    		for(MailbagVO mailbagVO : selectedMailbagVOs){
    			boolean processFlag=false;
    			mailbagHistoryVOs=mailbagVO.getMailbagHistories();
    			if(mailbagHistoryVOs!=null && mailbagHistoryVOs.size() > 0){
    				for(MailbagHistoryVO mailbagHistoryVO :mailbagHistoryVOs){
    					if(!CONST_RESDIT_STATUS_UNPROCESSED.equals(mailbagHistoryVO.getMailStatus())){
    						processFlag=true;
    					}
    				}
    			}
    			else{
    				
    				ErrorVO error = new ErrorVO(UNPROCESSED_RESDIT);
					actionContext.addError(error);
					return;
    				
    			}
    			if(!processFlag){
    				
    				ErrorVO error = new ErrorVO(UNPROCESSED_RESDIT);
					errors.add(error);
					actionContext.addError(error);
					return;
    				
    			}
    		}
    	}
    		carditEnquiryVO.setMailbagVos(selectedMailbagVOs);
    		carditEnquiryVO.setContainerVos(null);
    		carditEnquiryVO.setCompanyCode(logonAttributes.getCompanyCode());
    		carditEnquiryVO.setResditEventPort(logonAttributes.getAirportCode());
    		
  
    	log.log(Log.INFO, "carditEnquiryVO....in SendResditCommand....",
				carditEnquiryVO);
		try{
    	     new MailTrackingDefaultsDelegate().sendResdit(carditEnquiryVO);

    	}catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		 ErrorVO error = new ErrorVO(CONST_MESSAGE_INFO_SEND_SUCCESS);
		 error.setErrorDisplayType(ErrorDisplayType.INFO);
         actionContext.addError(error);

		 results.add(carditEnquiryModel);
	     responseVO.setResults(results);
	     actionContext.setResponseVO(responseVO);

	}

}
