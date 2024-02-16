/*
 * RefreshCaptureCN51Command.java Created on DEC 05, 2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturecn51;

import java.util.ArrayList;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureCN51Session;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.CaptureCN51Form;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227 RENO K ABRAHAM
 *
 */
public class RefreshCaptureCN51Command extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	private static final String CLASS_NAME = "CloseCN51DetailsCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturecn51";
	private static final String REFRESH_SUCCESS="refresh_success";
	private static final String REFRESH_FAILURE="refresh_failure";
	private static final String MAILSUBCLASS_CP = "CP";

	private static final String MAILSUBCLASS_LC = "LC";
	
	private static final String MAILSUBCLASS_SAL = "SAL";
	
	private static final String MAILSUBCLASS_ULD = "UL";
	
	private static final String MAILSUBCLASS_SV = "SV";
	
	private static final String MAILSUBCLASS_EMS = "EMS";
	private static final String UNSAVED_DATA = "unsaveddata";

	/**
	 * Execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		CaptureCN51Session captureCN51Session = 
			(CaptureCN51Session) getScreenSession(MODULE_NAME, SCREEN_ID);
		CaptureCN51Form captureCN51Form=(CaptureCN51Form)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		 
	     Money[] totalAmount=null;
	     double lc =0.0;
	     double cp =0.0;
	     double sal=0.0;
	     double uld=0.0;
	     double sv=0.0;
	     double ems=0.0;
	     double wt=0.0;
	     Money moneyChg=null;
		Page<AirlineCN51DetailsVO> mainCN51VOs = null ;
		AirlineCN51SummaryVO airlineCN51SummaryVO = captureCN51Session.getCn51Details();
		if(airlineCN51SummaryVO != null) {
			if(airlineCN51SummaryVO.getCn51DetailsPageVOs()!= null && 
					airlineCN51SummaryVO.getCn51DetailsPageVOs().size() > 0) {
				mainCN51VOs = airlineCN51SummaryVO.getCn51DetailsPageVOs();
			}else {
				mainCN51VOs = new Page<AirlineCN51DetailsVO>(
						new ArrayList<AirlineCN51DetailsVO>(), 0, 0, 0, 0, 0,false);
			}
		}
		captureCN51Session.getCn51Details().setCn51DetailsPageVOs(mainCN51VOs);
		
		if(captureCN51Session.getCn51Details().getCn51DetailsPageVOs()!=null && captureCN51Session.getCn51Details().getCn51DetailsPageVOs().size()>0){
		
			int valsize=captureCN51Session.getCn51Details().getCn51DetailsPageVOs().size();    		
    		log.log(Log.INFO, "size of detVos---in reload..start..", valsize);
			totalAmount=new Money[valsize];
    		Page<AirlineCN51DetailsVO> detVos=captureCN51Session.getCn51Details().getCn51DetailsPageVOs();
    		int siz=detVos.size();
    		log.log(Log.INFO, "size of detVos---in reload..start..", siz);
			totalAmount=new Money[siz];
			try{
				
				moneyChg=CurrencyHelper.getMoney(captureCN51Form.getBlgCurCode());
				moneyChg.setAmount(0.0D);
			
				
			for(int i=0;i<siz;i++){
				log.log(Log.INFO, "detVos", detVos.size());
				if(detVos.get(i).getMailsubclass()!=null){
				if(detVos.get(i).getMailsubclass().equals(MAILSUBCLASS_CP)){
					cp=cp+detVos.get(i).getTotalweight();
				}
				
				
				if(detVos.get(i).getMailsubclass().equals(MAILSUBCLASS_LC)){
					lc=lc+detVos.get(i).getTotalweight();
				}
			
				if(detVos.get(i).getMailsubclass().equals(MAILSUBCLASS_SAL)){
					sal=sal+detVos.get(i).getTotalweight();
				}
				if(detVos.get(i).getMailsubclass().equals(MAILSUBCLASS_ULD)){
					uld=uld+detVos.get(i).getTotalweight();
				}
				if(detVos.get(i).getMailsubclass().equals(MAILSUBCLASS_SV)){
					sv=sv+detVos.get(i).getTotalweight();
				}
				if(MAILSUBCLASS_EMS.equals(detVos.get(i).getMailsubclass())){
					ems=ems+detVos.get(i).getTotalweight();
				}
				}
				if(detVos.get(i).getTotalweight()!=0.0){
				
					log.log(Log.INFO, "total WGT IN RELOAD ", detVos.get(i).getTotalweight());
					wt=wt+detVos.get(i).getTotalweight();
	                  
				}	
				if(detVos.get(i).getTotalamountincontractcurrency()!=null){
					
					log.log(Log.INFO, "total Amount IN RELOAD ", detVos.get(i).getTotalamountincontractcurrency().toString());
					totalAmount[i]=CurrencyHelper.getMoney("USD");
					//totalAmount[i].setAmount(Double.parseDouble(detVos.get(i).getAmount().toString()));
			       	totalAmount[i].setAmount(detVos.get(i).getTotalamountincontractcurrency().getAmount());
					log.log(Log.INFO, "MONEY SET IN FORM IN RELOAD ",
							totalAmount[i].toString());
					moneyChg.plusEquals(detVos.get(i).getTotalamountincontractcurrency());
					 
				}	
				log.log(Log.INFO, "---in reload..end..", detVos);
			}
		}
			catch(CurrencyException currencyException){
				log.log(Log.INFO,"CurrencyException found");
			}
			
				log.log(Log.INFO,"MONEY SET IN FORM IN RELOAD not null" );	
				
				captureCN51Form.setNetCP(cp);
				captureCN51Form.setNetLC(lc);
				captureCN51Form.setNetSal(sal);
				captureCN51Form.setNetUld(uld);
				captureCN51Form.setNetSV(sv);
				captureCN51Form.setNetEMS(ems);
				captureCN51Form.setNetWeight(wt);
				captureCN51Form.setNetChargeMoney(moneyChg);
				log.log(Log.INFO, "screenFlg in refresh command ",
						captureCN51Form.getScreenFlg());
				log.log(Log.INFO,"MONEY SET IN FORM IN RELOAD " );	
			
		}
	if(captureCN51Session.getCn51Details()!=null){
		log.log(Log.INFO, "CaptureCN51Session.getCn51Details() ",
				captureCN51Session.getCn51Details());
		if(captureCN51Session.getCn51Details().getClearanceperiod()!=null){
		captureCN51Form.setClearancePeriod(captureCN51Session.getCn51Details().getClearanceperiod());
		
		
	}
		
		if(captureCN51Session.getCn51Details().getInvoicenumber()!=null){
			captureCN51Form.setInvoiceRefNo(captureCN51Session.getCn51Details().getInvoicenumber());
			
			
		}
		
		
		if(captureCN51Session.getCn51Details().getAirlinecode()!=null){
			captureCN51Form.setAirlineCode(captureCN51Session.getCn51Details().getAirlinecode());
			
			
		}
			
		
	}
	    captureCN51Form.setScreenFlg(UNSAVED_DATA);
		invocationContext.target=REFRESH_SUCCESS;
		log.exiting(CLASS_NAME,"execute");
	}

}
