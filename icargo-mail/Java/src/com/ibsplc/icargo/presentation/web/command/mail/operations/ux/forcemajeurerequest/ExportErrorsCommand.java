package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import com.ibsplc.icargo.business.shared.defaults.fileupload.vo.FileUploadErrorLogVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.download.FileDownloadCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ForceMajeureRequestSession;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

public class ExportErrorsCommand extends FileDownloadCommand {

    private static final String MODULE_NAME = "mail.operations";
    private static final String SCREEN_ID = "mail.operations.ux.forcemajeure";
    private static final String CLASS_NAME = "ExportErrorsCommand";
    private static final String ERR_NO_RECORDS = "mailtracking.defaults.forcemajeureRequest.msg.err.norecords";
    private Log LOGGER = LogFactory.getLogger("MAIL OPERATIONS");
    private static final String EXPORT_ERROR_FAILURE = "export_error_failure";

    @Override
    protected StreamInfo[] getStreamInfo(InvocationContext invocationContext) throws CommandInvocationException {
        LOGGER.entering(CLASS_NAME, "execute");
        ForceMajeureRequestSession forceMajeureRequestSession = getScreenSession(MODULE_NAME, SCREEN_ID);
        Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs = forceMajeureRequestSession.getFileUploadErrorLogVOs();
        FileDownloadCommand.StreamInfo[] streamInfo=new FileDownloadCommand.StreamInfo[1];
        streamInfo[0] = new FileStreamInfo("application/csv",generateCsvFile(fileUploadErrorLogVOs));
        return streamInfo;
    }

    @Override
    protected void getStreamInfoError(InvocationContext invocationContext) throws CommandInvocationException {
    	Collection<ErrorVO> errors = new ArrayList<>();
    	ErrorVO error = new ErrorVO(ERR_NO_RECORDS);
		error.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(error);
		invocationContext.addAllError(errors);
        invocationContext.target = EXPORT_ERROR_FAILURE;
    }

    private File generateCsvFile(Collection<FileUploadErrorLogVO> fileUploadErrorLogVOs){
        LOGGER.log(Log.FINE, "Generating CSV **********");
        File downLoadableFile =  new File(new StringBuilder(createTempFolderNew()).append("ExceptionLogDetails.csv").toString());
        try
        {
            PrintWriter writer = new PrintWriter(downLoadableFile);
            writer.append('"');
            writer.append("CMPCOD");
            writer.append('"');
            writer.append(',');
            writer.append('"');
            writer.append("ERRCOD");
            writer.append('"');
            writer.append(',');
            writer.append("LINNUM");
            writer.append(',');
            writer.append('"');
            writer.append("LINCNT");
            writer.append('"');
            writer.append('\r');
            writer.append('\n');

            for (FileUploadErrorLogVO errorLogVO: fileUploadErrorLogVOs) {
                writer.append(errorLogVO.getCompanyCode());
                writer.append(',');
                writer.append(errorLogVO.getErrorCode());
                writer.append(',');
                writer.append(String.valueOf(errorLogVO.getLineNumber()));
                writer.append(',');
                writer.append(errorLogVO.getLineContent()!=null?errorLogVO.getLineContent():"");
                writer.append('\r');
                writer.append('\n');
            }
            writer.flush();
            writer.close();
        } catch( IOException e) {
            LOGGER.log(Log.SEVERE,"IOException caught..........");
        }

        return downLoadableFile;
    }
}
