package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactPointsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2052
 *
 */
public class ListPointRedemptionCommand extends BaseCommand{
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	private Log log = LogFactory.getLogger("ListPointRedemptionCommand");
	
/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException{
		log.entering("ListPointRedemptionCommand","ENTER");
		ListCustomerForm form = (ListCustomerForm)invocationContext.screenModel;
    	String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    	ListCustomerSession session = getScreenSession(MODULENAME,SCREENID);
    	 Collection<CustomerContactPointsVO> vos = new ArrayList<CustomerContactPointsVO>();
    	CustomerMgmntDefaultsDelegate delegate  = new CustomerMgmntDefaultsDelegate();
        String customerCode = session.getCustomerCodes().get(0);
        String customerName = session.getCustomerNames().get(0);
        form.setCustomerCodePointRdmd(customerCode);
        form.setCustomerNamePointRdmd(customerName);
        log.log(Log.FINE,"before setting to delegate-------->>>");
        log.log(Log.FINE, "customerCode-------->>>", customerCode);
		log.log(Log.FINE, "customerName-------->>>", customerName);
			// session.setCharterRefNo(charterRefNo);      
			try {
				vos = delegate.listCustomerContactPoints(companyCode,customerCode);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
//printStackTrrace()();
				handleDelegateException(e);
			}
		log.log(Log.FINE, "getting from delegate-------->>>", vos);
		session.setCustomerContactPointsVOs(vos);
		//double points = 0.0;
		double ptAccruded = 0.0;
		double ptRedeemed = 0.0;
		double points = 0.0;
		double listedPoints = 0.0;
		if(vos!=null && vos.size()>0){
			for(CustomerContactPointsVO vo:vos){
				log.log(Log.FINE, "vo-------->>>", vo);
				points = vo.getPointsAccruded()-vo.getPointsRedeemed();
				ptAccruded = vo.getPointsAccruded();
				ptRedeemed = vo.getPointsRedeemed();
				listedPoints += vo.getPoints();
				log.log(Log.FINE, "vo.getPointsAccruded()--------->>>", vo.getPointsAccruded());
				log.log(Log.FINE, "vo.getPointsRedeemed()--------->>>", vo.getPointsRedeemed());
				log.log(Log.FINE, "points--------->>>", points);
			}
			session.setPointsForValidation(String.valueOf(points));
			session.setListedPoints(String.valueOf(listedPoints));
			form.setPointsAccruded(String.valueOf(ptAccruded));
			form.setPointsRedeemed(String.valueOf(ptRedeemed));
			form.setPointsRedmdTo(String.valueOf(points));
			log.log(Log.FINE, "form.getPointsAccruded--------->>>", form.getPointsAccruded());
			log.log(Log.FINE, "form.getPointsRedeemed--------->>>", form.getPointsRedeemed());
			log.log(Log.FINE, "form.setPointsRedmdTo--------->>>", form.getPointsRedmdTo());
		}else{
			//form.setPointsRedmdTo(session.getPoints());
			form.setPointsAccruded(session.getPointsAccruded());
			form.setPointsRedeemed(session.getPointsRedeemed());
			form.setPointsRedmdTo(session.getPointsRedeemed());
			session.setPointsForValidation(session.getPointsRedeemed());
			session.setListedPoints("0.0");
			log.log(Log.FINE,
					"form.getPointsAccruded@@@@@@@@@@@@@@--------->>>", form.getPointsAccruded());
			log.log(Log.FINE,
					"form.getPointsRedeemed@@@@@@@@@@@@@@@@--------->>>", form.getPointsRedeemed());
			log.log(Log.FINE,
					"form.getPointsRedmdTo@@@@@@@@@@@@@@@@--------->>>", form.getPointsRedmdTo());
		}
		
		log.log(Log.FINE, "form.setPointsRedmdTo(--------->>>", form.getPointsRedmdTo());
		log.exiting("ListPointRedemptionCommand","EXIT");
        invocationContext.target=SCREENLOAD_SUCCESS;
	}
}
