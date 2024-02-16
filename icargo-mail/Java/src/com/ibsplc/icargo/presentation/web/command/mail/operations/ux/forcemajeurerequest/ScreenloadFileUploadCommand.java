package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.forcemajeurerequest;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;

public class ScreenloadFileUploadCommand extends BaseCommand {
    private static final String SCREEN_LOAD_SUCCESS = "screenload_success";

    @Override
    public void execute(InvocationContext invocationContext) throws CommandInvocationException {
        invocationContext.target = SCREEN_LOAD_SUCCESS;
    }
}
