/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.billingentries;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingEntriesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class VoidMailbagsCommand extends BaseCommand{

	private Log log = LogFactory.getLogger(" VoidMailbagsCommand");
    private static final String CLASS_NAME = "VoidMailbagsCommand";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String ACTION = "void_success";
	private static final String ACTION_WARN = "confirm_void";
	private static final String VOID_TRIGGER_POINT = "VD";
	private static final String SCREENID = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";
	private static final String SCREEN_ID="MRA001";

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");

		GPABillingEntriesForm gpaBillingEntriesForm=(GPABillingEntriesForm)invocationContext.screenModel;
		GPABillingEntriesSession gpaSession =(GPABillingEntriesSession)getScreenSession(MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors =  new ArrayList<ErrorVO>();
		ErrorVO errorVO=null;
		Page<DocumentBillingDetailsVO> documentBillingDetailsVOs = gpaSession.getGpaBillingDetails();
		Collection<DocumentBillingDetailsVO> selectedVOs = gpaSession.getSelectedVoidMailbags();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();

		String[] rowId=gpaBillingEntriesForm.getCheck();
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

		if((gpaSession.getSelectedVoidMailbags() == null || gpaSession.getSelectedVoidMailbags().isEmpty())
				&& selectedVOs != null && !selectedVOs.isEmpty()){
			gpaSession.setSelectedVoidMailbags(selectedVOs);
			gpaSession.setSelectedRows(rowId);
			Object[] obj = new Object[1];
			obj[0] = String.valueOf(selectedVOs.size());
			errorVO=new ErrorVO("mailtracking.mra.gpabilling.billingentries.voidmailbagsconfirm",obj);
			errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
   			invocationContext.target=ACTION_WARN;
			return;
		}

		try {
			delegate.voidMailbags(selectedVOs);
		} catch (BusinessDelegateException e) {
			errors=handleDelegateException(e);
			gpaSession.setSelectedVoidMailbags(null);
			gpaSession.setSelectedRows(null);
		}

		if (errors != null && errors.size() > 0) {
			 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
		}else{
			gpaSession.setSelectedVoidMailbags(null);
			gpaSession.setSelectedRows(null);
			errorVO=new ErrorVO("mailtracking.mra.gpabilling.voidedmailbagssuccesfully");
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
		}
		invocationContext.target = ACTION;
		log.exiting(CLASS_NAME, "execute");

	}

}
