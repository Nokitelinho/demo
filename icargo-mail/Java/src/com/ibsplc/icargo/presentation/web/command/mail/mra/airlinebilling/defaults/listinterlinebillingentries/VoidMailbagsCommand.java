/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.listinterlinebillingentries.VoidMailbagsCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	17-Oct-2019	:	Draft
 */
public class VoidMailbagsCommand extends BaseCommand{

	private Log log = LogFactory.getLogger(" VoidMailbagsCommand");
    private static final String CLASS_NAME = "VoidMailbagsCommand";
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String ACTION = "void_success";
	private static final String ACTION_WARN = "confirm_void";
	private static final String VOID_TRIGGER_POINT = "VD";
	private static final String SCREENID = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";
	private static final String SCREEN_ID="MRA047";
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-5219 on 17-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");

		ListInterlineBillingEntriesForm form = (ListInterlineBillingEntriesForm) invocationContext.screenModel;
		ListInterlineBillingEntriesSession session = (ListInterlineBillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID);
		
		Collection<ErrorVO> errors =  new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = session.getDocumentBillingDetailVOs();
		Collection<DocumentBillingDetailsVO> selectedVOs = session.getSelectedVoidMailbags();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();

		String[] rowId=form.getCheck();
		LocalDate currentDate = new LocalDate("***", Location.NONE, true);


		if(rowId != null && documentBillingDetailsVOs != null && selectedVOs == null){
			log.log(Log.INFO, "rowid length-->", rowId.length);
			selectedVOs = new ArrayList<DocumentBillingDetailsVO>();
			try {
				for(int i=0; i < rowId.length; i++){
					DocumentBillingDetailsVO vo = documentBillingDetailsVOs.get(Integer.parseInt(rowId[i]));
					vo.setScreenID(SCREEN_ID);
					vo.setTriggerPoint(VOID_TRIGGER_POINT);
					vo.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
					vo.setLastUpdatedTime(currentDate);
					selectedVOs.add(documentBillingDetailsVOs.get(Integer.parseInt(rowId[i])));
				}
			} catch (NumberFormatException e) {
				log.log(Log.SEVERE, "execption",e.getMessage());
			}

		}

		if((session.getSelectedVoidMailbags() == null || session.getSelectedVoidMailbags().isEmpty())
				&& selectedVOs != null && !selectedVOs.isEmpty()){
			session.setSelectedVoidMailbags(selectedVOs);
			session.setSelectedRow(rowId.toString());
			Object[] obj = new Object[1];
			obj[0] = String.valueOf(selectedVOs.size());
			errorVO=new ErrorVO("mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries.voidmailbagsconfirm",obj);
			errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
   			invocationContext.target=ACTION_WARN;
			return;
		}

		try {
			delegate.voidMailbags(selectedVOs);
			session.setSelectedVoidMailbags(null);
			session.setSelectedRow(null);
		} catch (BusinessDelegateException e) {
			errors=handleDelegateException(e);
			session.setSelectedVoidMailbags(null);
			session.setSelectedRow(null);
		}

		if (errors != null && errors.size() > 0) {
			 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
		}
		invocationContext.target = ACTION;
		log.exiting(CLASS_NAME, "execute");

	}

}
