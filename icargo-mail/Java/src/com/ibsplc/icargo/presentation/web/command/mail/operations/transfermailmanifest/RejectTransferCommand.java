package com.ibsplc.icargo.presentation.web.command.mail.operations.transfermailmanifest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.TransferMailManifestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class RejectTransferCommand extends BaseCommand {
	 private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * declaring
	    * TARGET , MODULE & SCREENID
	    */
	   private static final String TRANSFER_NOT_POSSIBLE= "mailtracking.defaults.err.transfernotpossible";
	   private static final String TARGET_SUCCESS= "reject_success";
	   private static final String TARGET_FAILURE = "reject_failure";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	   /**
	    * @param invocationContext
	    * @throws CommandInvocationException
	    */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		TransferMailManifestForm transferMailManifestForm = 
	    		(TransferMailManifestForm)invocationContext.screenModel;
				
			TransferMailManifestSession transferMailManifestSession = 
				getScreenSession(MODULE_NAME,SCREEN_ID);
			Collection<TransferManifestVO> transferMftVOs = new ArrayList<TransferManifestVO>();
			Collection<TransferManifestVO> transferManifestVOs  = transferMailManifestSession.getTransferManifestVOs();
			Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
			for(TransferManifestVO trfManifestVO : transferManifestVOs){
				transferMftVOs.add(trfManifestVO);
			}
			String select = transferMailManifestForm.getSelectMail();
		    if ((select != null) && (select.trim().length() > 0))
		    {

			      String[] childKey = select.split("_");
			      for (int i = 0; i < childKey.length; i++)
			      {
				TransferManifestVO transferManifestVO = ((ArrayList<TransferManifestVO>)(transferMftVOs)).get(Integer.parseInt(childKey[i]));
				if (transferManifestVO.getTransferStatus()!=null &&(transferManifestVO.getTransferStatus().equals("Transfer Ended")||transferManifestVO.getTransferStatus().equals("Transfer Rejected"))) {
					ErrorVO errorVO = new ErrorVO(
							TRANSFER_NOT_POSSIBLE);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
				}
				
				if (formErrors != null && !formErrors.isEmpty()) {
					invocationContext.addAllError(formErrors);
					invocationContext.target = TARGET_FAILURE;
					return;
				}
				List<TransferManifestVO> transferManifestVOList=new ArrayList<>();
				try {
					transferManifestVOList = new MailTrackingDefaultsDelegate().findTransferManifestDetailsForTransfer(transferManifestVO.getCompanyCode(),transferManifestVO.getTransferManifestId());
				} catch (BusinessDelegateException e) {
					handleDelegateException(e);
				}
			      
				for(TransferManifestVO transferManifest:transferManifestVOList){
				 try {
					new MailTrackingDefaultsDelegate().rejectTransferFromManifest(transferManifest)	;
				} catch (BusinessDelegateException e) {
					e.getMessage();
				}
				}
			      }
		      }
		      

				invocationContext.target = TARGET_SUCCESS;
	}
	}