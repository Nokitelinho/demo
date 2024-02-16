
package com.ibsplc.icargo.presentation.web.command.products.defaults.listsubproducts;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.products.defaults.ListSubProductSessionInterface;
import com.ibsplc.icargo.presentation.web.struts.form.products.defaults.ListSubProductForm;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.time.TimeConvertor;


/**
 * @author A-2135
 *
 */
public class ReloadCommand  extends BaseCommand {

    private static final String RELOAD_SUCCESS = "reload_success";
    private static final String MODULE_NAME = "product.defaults";
//    private static final String COMPANY_CODE = "AV";
    private Log log = LogFactory.getLogger("ReloadCommand");
    /*
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ApplicationSessionImpl applicationSession = getApplicationSession();
    	LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
		ListSubProductSessionInterface session = getScreenSession( "product.defaults","products.defaults.listsubproducts");
		ListSubProductForm listSubProductForm = (ListSubProductForm)invocationContext.screenModel;
		String endDate = "";
		String startDate = "";

		if(session.getProductFilterVODetails()!= null){
			if(session.getProductFilterVODetails().getToDate()!=null ){
				log.log(Log.INFO,
						"session.getProductFilterVODetails().getToDate()",
						session.getProductFilterVODetails().getToDate());
				endDate = TimeConvertor.toStringFormat(session.
						getProductFilterVODetails().getToDate().toCalendar(),
						TimeConvertor.CALENDAR_DATE_FORMAT);
			}
			if(session.getProductFilterVODetails().getFromDate()!=null ){
				log.log(Log.INFO,
						"session.getProductFilterVODetails().getFromDate()",
						session.getProductFilterVODetails().getFromDate());
				startDate = TimeConvertor.toStringFormat(session.
				getProductFilterVODetails().getFromDate().toCalendar(),
				TimeConvertor.CALENDAR_DATE_FORMAT);
			}
		}
		if (session.getProductFilterVODetails()!=null &&("listsubproductmode").equals(listSubProductForm.getFromListSubproduct())) {


			listSubProductForm.setEndDate(endDate);
			listSubProductForm.setStartDate(startDate);
			listSubProductForm.setStatus(session.getProductFilterVODetails().getStatus());
			listSubProductForm.setPriority(session.getProductFilterVODetails().getStatus());
			listSubProductForm.setTransportMode(session.getProductFilterVODetails().getTransportMode());
			listSubProductForm.setProductCode(session.getProductFilterVODetails().getProductCode());
			listSubProductForm.setProductName(session.getProductFilterVODetails().getProductName());
			listSubProductForm.setProductScc(session.getProductFilterVODetails().getScc());

    	}
		invocationContext.target = "reload_success";

    }


}
