package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadFilterVO;
import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ForceMajeureRequestForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class FileUploadCommand extends BaseCommand {
    private static final String TARGET_SUCCESS = "upload_success";
    private static final String TARGET_FAILURE = "upload_failure";
    private static final String MAX_UPLOAD_FILE_SIZE = "system.defaults.maxuploadfilesize";
    private static final String CLASS_NAME = "FileUploadCommand";
    private static final String EMPTY_STRING = "";
    private static final String TRANSFER_COMPLETED =  "TC";
    private static final String PROCESSED_WITH_ERROR = "PE";
    private static final String PROCESSED_WITH_WARNINGS = "PW";
    private static final String MODULE_NAME = "mail.operations";
    private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
    private Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");
    @Override
    public void execute(InvocationContext invocationContext) throws CommandInvocationException {
        LOGGER.entering(CLASS_NAME, "execute");
        ForceMajeureRequestForm forceMajeureRequestForm = (ForceMajeureRequestForm) invocationContext.screenModel;
        ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(MODULE_NAME,SCREEN_ID);
        ApplicationSessionImpl applicationSession = getApplicationSession();
        Map<String, String> systemParameters = getSystemParameters();
        String paramValue =  null != systemParameters ?systemParameters.get(MAX_UPLOAD_FILE_SIZE) : "0";
        int uploadFileSize = 0;
        if (paramValue != null && !paramValue.isEmpty()) {
            uploadFileSize = Integer.parseInt(paramValue);
        }
        Collection<ErrorVO> errors = validateForm(forceMajeureRequestForm, uploadFileSize);
        if (errors != null && !errors.isEmpty()) {
            invocationContext.addAllError(errors);
            invocationContext.target = TARGET_FAILURE;
            return;
        }
        String fileContent = EMPTY_STRING;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte test[] = new byte[8192];
        InputStream is;
        try {
            is = forceMajeureRequestForm.getFile().getInputStream();
            for(int totalByteSize = 0; (totalByteSize = is.read(test, 0, 8192)) != -1;){
                byteArrayOutputStream.write(test, 0, totalByteSize);
                fileContent = new String(byteArrayOutputStream.toByteArray());
            }
        } catch (IOException e) {
            LOGGER.log(Log.FINE, e);
        }
        FileUploadVO uploadVO = getFileUploadVO(forceMajeureRequestForm, applicationSession, fileContent);

        String uploadStatus = null;
        try {
            SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
            uploadStatus = sharedDefaultsDelegate.uploadFile(uploadVO);

        } catch (BusinessDelegateException businessDelegateException) {
            errors = handleDelegateException(businessDelegateException);
            invocationContext.addAllError(errors);

        }
        forceMajeureRequestForm.setUploadStatus(uploadStatus);
        if (PROCESSED_WITH_ERROR.equals(uploadStatus) || PROCESSED_WITH_WARNINGS.equals(uploadStatus)) {
            FileUploadFilterVO uploadFilterVO = new FileUploadFilterVO();
            uploadFilterVO.setCompanyCode(uploadVO.getCompanyCode());
            uploadFilterVO.setProcessIdentifier(uploadVO.getProcessIdentifier());
            Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs = null;
            SharedDefaultsDelegate defaultsDelegate = new SharedDefaultsDelegate();
            try {
                fileUploadErrorLogVOs = defaultsDelegate.exportFileUploadError(uploadFilterVO);
            } catch (BusinessDelegateException businessDelegateException) {
                errors = handleDelegateException(businessDelegateException);
                invocationContext.addAllError(errors);
            }
            forceMajeureRequestSession.setFileUploadErrorLogVOs(fileUploadErrorLogVOs);
        }
        invocationContext.target = TARGET_SUCCESS;
        LOGGER.exiting(CLASS_NAME, "execute");
    }

    private FileUploadVO getFileUploadVO(ForceMajeureRequestForm forceMajeureRequestForm, ApplicationSessionImpl applicationSession, String fileContent) {
        LogonAttributes logonAttributes = applicationSession.getLogonVO();
        LocalDate uploadTime = new LocalDate(logonAttributes.getStationCode(), Location.STN, true);
        String dateFormat = uploadTime.toDisplayDateOnlyFormat().replaceAll("-", EMPTY_STRING);
        String timeFormat = uploadTime.toDisplayTimeOnlyFormat().replaceAll(":", EMPTY_STRING);
        String processIdr = new StringBuffer().append(forceMajeureRequestForm.getFileType()).append("_").append(logonAttributes.getUserName())
                .append("_").append(dateFormat).append("_").append(timeFormat).toString();
        FileUploadVO uploadVO = new FileUploadVO();
        uploadVO.setCompanyCode(logonAttributes.getCompanyCode());
        uploadVO.setProcessIdentifier(processIdr);
        uploadVO.setRawData(fileContent);
        uploadVO.setFileName(forceMajeureRequestForm.getFile().getFileName());
        uploadVO.setUploadTime(uploadTime);
        uploadVO.setFileType(forceMajeureRequestForm.getFileType());
        uploadVO.setUser(logonAttributes.getUserName());
        uploadVO.setStatusFieldValue(TRANSFER_COMPLETED);
        return uploadVO;
    }

    private Collection<ErrorVO> validateForm(ForceMajeureRequestForm forceMajeureRequestForm, int uploadFileSize) {
        LOGGER.entering(CLASS_NAME,"validateForm");
        Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
        ErrorVO error;
        if(isNullOrEmpty(forceMajeureRequestForm.getFileType())){
            error = new ErrorVO("mail.operations.forcemajeure.err.missingfiletype");
            error.setErrorDisplayType(ErrorDisplayType.ERROR);
            errors.add(error);
        }
        if(isNullOrEmpty(forceMajeureRequestForm.getFile().getFileName())){
            error = new ErrorVO("mail.operations.forcemajeure.err.missingfile");
            error.setErrorDisplayType(ErrorDisplayType.ERROR);
            errors.add(error);
        } else if (forceMajeureRequestForm.getFile().getFileSize() == 0) {
            error = new ErrorVO("mail.operations.forcemajeure.err.validfile");
            error.setErrorDisplayType(ErrorDisplayType.ERROR);
            errors.add(error);
        } else if (uploadFileSize > 0
                && forceMajeureRequestForm.getFile().getFileSize() > uploadFileSize) {
            Object[] obj = { uploadFileSize };
            error = new ErrorVO("mail.operations.forcemajeure.err.filesizelimit",
                    obj);
            error.setErrorDisplayType(ErrorDisplayType.ERROR);
            errors.add(error);
        }
        LOGGER.exiting(CLASS_NAME,"validateForm");
        return errors;
    }

    private Map<String, String> getSystemParameters() {
        LOGGER.entering(CLASS_NAME, "getSystemParameters");
        Collection<String> parameterCodes = new ArrayList<String>(2);
        Map<String, String> systemParameters = null;
        parameterCodes.add(MAX_UPLOAD_FILE_SIZE);
        try {
            systemParameters = new SharedDefaultsDelegate()
                    .findSystemParameterByCodes(parameterCodes);
        } catch (BusinessDelegateException businessDelegateException) {
            LOGGER.log(Log.FINE,"------SYSTEM PARAMETER FETCH EXCEPTION------------");
        }
        return systemParameters;
    }
    private static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s) || s.trim().isEmpty();
    }
}
