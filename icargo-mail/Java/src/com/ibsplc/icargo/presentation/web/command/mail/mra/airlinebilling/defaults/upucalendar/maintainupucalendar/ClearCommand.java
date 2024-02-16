

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;

/**
public class ClearCommand  extends BaseCommand {

	/*
	 * The module name
	 */
    private static final String MODULE_NAME = "mailtracking.mra";
    /*
	 * The screen id
	 */
    private static final String SCREENID =
    /* For setting the Target action*/
    private static final String CLEAR_SUCCESS = "clear_success";
    private static final String CLASS_NAME = "ClearCommand";
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    		Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
    		UPUCalendarForm upuCalendarForm = (UPUCalendarForm)invocationContext.screenModel;
			invocationContext.target = CLEAR_SUCCESS;
	}
}