package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.customerpoints;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.ListCustomerPointsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListCustomerPointsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2052
 * 
 */
public class ShowTotalCustomerPointsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ShowTotalCustomerPointsCommand");
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

  /****
   * @param invocationContext
   * @throws CommandInvocationException
   */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ShowTotalCustomerPointsCommand","Enter");
    	ListCustomerPointsForm form = (ListCustomerPointsForm)invocationContext.screenModel;
    	ListCustomerPointsSession session = getScreenSession(MODULENAME,SCREENID);
    	Page<ListCustomerPointsVO> pg = session.getPage();
    	log.log(Log.FINE, "pg------------------>>>>>>", pg);
		double showTotal = 0.0;
    	if(pg !=null && pg.size()>0){
	    	for(ListCustomerPointsVO vo:pg){
		    	double tot = vo.getWeight()+
		    				 vo.getVolume()+
		    				 vo.getRevenue()+
		    				 vo.getYield()+
		    				 vo.getDistance();
	    	//vo.setPoints(tot);
	    	showTotal = tot +showTotal;
	    	log.log(Log.FINE, "vo----------->>>", vo);
	    	}
    	}
    	form.setTotal(String.valueOf(showTotal));
    	log.log(Log.FINE, "String.valueOf(showTotal)---->>>", String.valueOf(showTotal));
		log.log(Log.FINE, "form.getTotal()---->>>", form.getTotal());
		log.exiting("ShowTotalCustomerPointsCommand","Exit");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    }
}
