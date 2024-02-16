package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.listinvoice;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.download.FileDownloadCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.ListGPABillingInvoiceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class DownloadPASSFileCommand extends FileDownloadCommand{

	private static final Log log = LogFactory.getLogger("MRA GPABILLING");
    private static final String CLASS_NAME = "DownloadPASSFileCommand";
    private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
    private static final String SCREEN_ID = "mailtracking.mra.gpabilling.listgpabillinginvoice";
    private static final String FILE_TYPE = "MAL_MRA_GPAPASS_FIL";
    private static final String PASS = "PASS_";
    private static final String DOWNLOAD_SUCCESS = "download_success";
    private static final String DOWNLOAD_FAILURE = "download_failure";
    private static final StreamInfo[] NO_FILES = {};
    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private static final String MAIL_MRA_GPABILLING_LIST_GPABILLING_INVOICE_ERR_FILE_NAME_MANDATORY = "mail.mra.gpabilling.listgpabillinginvoice.err.filenamemandatory";
    private static final String MAIL_MRA_GPABILLING_LIST_GPABILLING_INVOICE_ERR_NO_DATA_FOUND = "mail.mra.gpabilling.listgpabillinginvoice.err.nodatafound";

    @Override
	protected StreamInfo[] getStreamInfo(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		ListGPABillingInvoiceForm listGPABillingInvoiceForm = (ListGPABillingInvoiceForm) invocationContext.screenModel;
		ListGPABillingInvoiceSession listGPABillingInvoiceSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		Collection<CN51SummaryVO> cN51SummaryVOs = listGPABillingInvoiceSession.getCN51SummaryVOs();
		List<CN51SummaryVO> cN51SummaryVOList = new ArrayList<>(cN51SummaryVOs);
		CN51SummaryVO cn51SummaryVO = cN51SummaryVOList
				.get(Integer.parseInt(listGPABillingInvoiceForm.getSelectedRow()));

		Collection<ErrorVO> errorVOs;
		String companyCode = cn51SummaryVO.getCompanyCode();
		String fileName = cn51SummaryVO.getPassFileName();
		if (isNullAndEmpty(fileName)) {
			errorVOs = getErrorVOS(MAIL_MRA_GPABILLING_LIST_GPABILLING_INVOICE_ERR_FILE_NAME_MANDATORY);
			invocationContext.addAllError(errorVOs);
			invocationContext.target = DOWNLOAD_FAILURE;
			return NO_FILES;
		}
		
		FileGenerateVO fileGenerateVO = constructFileGenerateVO(companyCode, fileName);
		Collection<FileGenerateVO> fileGenerateVOs;
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		try {
			fileGenerateVOs = sharedDefaultsDelegate.findGeneratedFilesFromFileName(fileGenerateVO);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "BusinessDelegateException occurs");
			errorVOs = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errorVOs);
			invocationContext.target = DOWNLOAD_FAILURE;
			return NO_FILES;
		}
		
		if (Objects.isNull(fileGenerateVOs) || fileGenerateVOs.isEmpty()) {
			errorVOs = getErrorVOS(MAIL_MRA_GPABILLING_LIST_GPABILLING_INVOICE_ERR_NO_DATA_FOUND);
			invocationContext.addAllError(errorVOs);
			invocationContext.target = DOWNLOAD_FAILURE;
			return NO_FILES;
		}
		StreamInfo[] arrayOfStreamInfo = getStreamInfos(fileGenerateVOs);
		if (fileGenerateVOs.size() > 1) {
			LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
			StringBuilder zipFileName = new StringBuilder(PASS);
			zipFileName.append(currentDate.toDisplayFormat(DATE_TIME_FORMAT));
			setZipFile(true);
			setZipFileName(zipFileName.toString());
		}
		invocationContext.target = DOWNLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

		return arrayOfStreamInfo;
	}

    private FileGenerateVO constructFileGenerateVO(String companyCode, String fileName) {
            FileGenerateVO fileGenerateVO = new FileGenerateVO();
            fileGenerateVO.setCompanyCode(companyCode);
            fileGenerateVO.setFileType(FILE_TYPE);
            fileGenerateVO.setFileName(fileName);
            fileGenerateVO.setCallForDownload(true);
        return fileGenerateVO;
    }

    private StreamInfo[] getStreamInfos(Collection <FileGenerateVO> fileGenerateVOs) {
        String contentType = "application/CSV";
        int i = 0;
        FileDownloadCommand.StreamInfo[] arrayOfStreamInfo = new FileDownloadCommand.StreamInfo[fileGenerateVOs.size()];
        for(FileGenerateVO fileGenerateVO:fileGenerateVOs) {
            File fileToDownload = new File(fileGenerateVO.getFileName());
            arrayOfStreamInfo[i] = new FileStreamInfo(contentType, fileToDownload);
            try {
                fileToDownload.deleteOnExit();
            } catch (SecurityException securityException) {
                log.log(Log.SEVERE, "Unable to delete file");
            }
            i++;
        }
        return arrayOfStreamInfo;
    }

    private Collection<ErrorVO> getErrorVOS(String errorCode) {
        Collection<ErrorVO> errorVOs = new ArrayList<>();
        ErrorVO errorVO = new ErrorVO(errorCode);
        errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
        errorVOs.add(errorVO);
        return errorVOs;
    }

    private boolean isNullAndEmpty(String s) {
        return Objects.isNull(s)|| s.trim().isEmpty();
    }

    @Override
    protected void getStreamInfoError(InvocationContext invocationContext) throws CommandInvocationException {
        invocationContext.target = DOWNLOAD_FAILURE;
    }
}
