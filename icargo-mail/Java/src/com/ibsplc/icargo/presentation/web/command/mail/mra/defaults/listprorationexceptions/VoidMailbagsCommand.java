/**
 *
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
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
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String ACTION = "void_success";
	private static final String ACTION_WARN = "confirm_void";
	private static final String VOID_TRIGGER_POINT = "VD";
	private static final String SCREENID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String SCREEN_ID="MRA062";

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");

		ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
		ListProrationExceptionsSession listExceptionSession = getScreenSession(
				MODULE_NAME, SCREENID);
		Collection<ErrorVO> errors =  new ArrayList<ErrorVO>();
		Page<ProrationExceptionVO> prorationExceptionVOs = listExceptionSession.getProrationExceptionVOs();

		ErrorVO errorVO=null;
		Collection<DocumentBillingDetailsVO> selectedVOs = listExceptionSession.getSelectedVoidMailbags();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();

		String[] selectedRows = null;
		LocalDate currentDate = new LocalDate("***", Location.NONE, true);
		
		if(listExceptionForm.getSelectedRows() != null && listExceptionForm.getSelectedRows().trim().length() > 0 ){
			selectedRows = listExceptionForm.getRowId();
		}


		if(selectedRows != null && prorationExceptionVOs != null && !prorationExceptionVOs.isEmpty() && selectedVOs ==null){
			selectedVOs = new ArrayList<DocumentBillingDetailsVO>();
			for(int i=0; i < selectedRows.length; i++){

				DocumentBillingDetailsVO vo = new DocumentBillingDetailsVO();
				ProrationExceptionVO proExceptionVO = prorationExceptionVOs.get(Integer.parseInt(selectedRows[i]));
				vo.setCompanyCode(proExceptionVO.getCompanyCode());
				vo.setMailSequenceNumber(proExceptionVO.getMailSequenceNumber());
				vo.setScreenID(SCREEN_ID);
				vo.setTriggerPoint(VOID_TRIGGER_POINT);
				vo.setLastUpdatedUser(getApplicationSession().getLogonVO().getUserId());
				vo.setLastUpdatedTime(currentDate);
				selectedVOs.add(vo);
			}
		}

		if((listExceptionSession.getSelectedVoidMailbags() == null || listExceptionSession.getSelectedVoidMailbags().isEmpty())
				&& selectedVOs != null && !selectedVOs.isEmpty()){
			listExceptionSession.setSelectedVoidMailbags(selectedVOs);
			listExceptionSession.setSelectedRows(selectedRows);
			listExceptionSession.setProrationExceptionVOs(prorationExceptionVOs);
			Object[] obj = new Object[1];
			obj[0] = String.valueOf(selectedVOs.size());
			errorVO=new ErrorVO("mailtracking.mra.defaults.listexceptions.voidmailbagsconfirm",obj);
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
			listExceptionSession.setSelectedVoidMailbags(null);
			listExceptionSession.setSelectedRows(null);
		}

		if (errors != null && errors.size() > 0) {
			 log.log(Log.FINE,"!!!inside errors!= null");
			invocationContext.addAllError(errors);
		}else{
			listExceptionSession.setSelectedVoidMailbags(null);
			listExceptionSession.setSelectedRows(null);
			errorVO=new ErrorVO("mailtracking.mra.defaults.listexceptions.voidedmailbagssuccesfully");
			errorVO.setErrorDisplayType(ErrorDisplayType.STATUS);
   			errors.add(errorVO);
   			invocationContext.addAllError(errors);
		}
		invocationContext.target = ACTION;
		log.exiting(CLASS_NAME, "execute");

	}

}
