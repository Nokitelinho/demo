package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.lov;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.IntStream;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ux.FileNameLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListFileNameLovCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA GPABILLING");
    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    private static final String CLASS_NAME = "ListFileNameLovCommand";
    private static final String MAIL_MRA_GPA_BILLING_UX_LOV_FROM_DATE_AND_TO_DATE_MANDATORY = "mail.mra.gpabilling.ux.lov.fromdateandtodatemandatory";
	private static final String MAIL_MRA_GPA_BILLING_UX_LOV_NO_DATA_FOUND = "mail.mra.gpabilling.ux.lov.nodatafound";
	private static final String LOV_LIST = "list";
	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String DATE_FORMAT = "dd-MMM-yyyy";

    @Override
    public void execute(InvocationContext invocationContext) throws CommandInvocationException {
        log.entering(CLASS_NAME, "execute");
        FileNameLovForm fileNameLovForm = (FileNameLovForm) invocationContext.screenModel;
        Page<FileNameLovVO> fileNameLovVOs = null;
        Collection<ErrorVO> errorVOs = null;
        /**
         * Form Validation to be skipped for default LOV invocation
         */
        if (LOV_LIST.equals(fileNameLovForm.getLovAction())) {
            errorVOs = validateForm(fileNameLovForm);
        }

        if (Objects.nonNull(errorVOs) && !errorVOs.isEmpty()) {
            invocationContext.addAllError(errorVOs);
            invocationContext.target = LIST_FAILURE;
            return;
        }
        
		updateFileNameLovForm(fileNameLovForm);
		
        FileNameLovVO fileNameLovVO = constructFileNameLovVO(fileNameLovForm);

        MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();

        try {
            fileNameLovVOs = mailTrackingMRADelegate.findPASSFileNames(fileNameLovVO);
        } catch (BusinessDelegateException businessDelegateException) {
            log.log(Log.FINE, "BusinessDelegateException occurs");
            errorVOs = handleDelegateException(businessDelegateException);
            invocationContext.addAllError(errorVOs);
            invocationContext.target = LIST_FAILURE;
        }
        if (Objects.isNull(fileNameLovVOs) || fileNameLovVOs.isEmpty()) {
            ErrorVO errorVO = new ErrorVO(MAIL_MRA_GPA_BILLING_UX_LOV_NO_DATA_FOUND);
            errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
            if (Objects.isNull(errorVOs)) {
                errorVOs = new ArrayList<>();
            }
            errorVOs.add(errorVO);
            invocationContext.addAllError(errorVOs);
            invocationContext.target = LIST_FAILURE;
            return;
        }

        fileNameLovForm.setFileNameLovPage(fileNameLovVOs);
        invocationContext.target = LIST_SUCCESS;
        log.exiting(CLASS_NAME, "execute");
    }

	private void updateFileNameLovForm(FileNameLovForm fileNameLovForm) {
		if (isNullOrEmpty(fileNameLovForm.getFromDate())) {
			fileNameLovForm.setFromDate(getDateFilter(FROM, 3));
		}
		if (isNullOrEmpty(fileNameLovForm.getToDate())) {
			fileNameLovForm.setToDate(getDateFilter(TO, 3));
		}
		if(Integer.parseInt(fileNameLovForm.getDisplayPage())==0){
			fileNameLovForm.setDisplayPage("1");
		}
		if(Integer.parseInt(fileNameLovForm.getDefaultPageSize()) != 0){
			fileNameLovForm.setDefaultPageSize("10");	
		}
	} 

	private FileNameLovVO constructFileNameLovVO(FileNameLovForm fileNameLovForm) {
		FileNameLovVO fileNameLovVO = new FileNameLovVO();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		fileNameLovVO.setCompanyCode(logonAttributes.getCompanyCode());
		fileNameLovVO.setFromDate(fileNameLovForm.getFromDate());
		fileNameLovVO.setToDate(fileNameLovForm.getToDate());
		fileNameLovVO.setPeriodNumber(fileNameLovForm.getPeriodNumber());
		fileNameLovVO.setPageNumber(Integer.parseInt(fileNameLovForm.getDisplayPage()));
		fileNameLovVO.setDefaultPageSize(Integer.parseInt(fileNameLovForm.getDefaultPageSize()));
		return fileNameLovVO;
	}

    private Collection<ErrorVO> validateForm(FileNameLovForm fileNameLovForm) {
        Collection<ErrorVO> errorVOs = new ArrayList<>();
        if (isNullOrEmpty(fileNameLovForm.getFromDate()) && isNullOrEmpty(fileNameLovForm.getToDate())
                && isNullOrEmpty(fileNameLovForm.getPeriodNumber())) {
            ErrorVO errorVO = new ErrorVO(MAIL_MRA_GPA_BILLING_UX_LOV_FROM_DATE_AND_TO_DATE_MANDATORY);
            errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
            errorVOs.add(errorVO);
        }
        return errorVOs;
    }

    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }
    
	private static String getDateFilter(String dateField, long offset) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		LocalDate initial = LocalDate.now();
		String date = null;
		int dayOfMonth = initial.getDayOfMonth();
		if (IntStream.range(1, 16).anyMatch(day -> day == dayOfMonth)) {
			if (FROM.equals(dateField)) {
				date = initial.minusMonths(offset).withDayOfMonth(1).format(formatter);
			} else {
				date = initial.minusMonths(1).withDayOfMonth(initial.minusMonths(1).lengthOfMonth()).format(formatter);

			}
		} else {
			if (FROM.equals(dateField)) {
				date = initial.minusMonths(offset).withDayOfMonth((initial.lengthOfMonth() / 2) + 1).format(formatter);
			} else {
				date = initial.withDayOfMonth(initial.lengthOfMonth() / 2).format(formatter);
			}
		}
		return date;
	} 

}
