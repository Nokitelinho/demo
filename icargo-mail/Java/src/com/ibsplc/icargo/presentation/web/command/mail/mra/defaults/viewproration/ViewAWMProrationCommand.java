/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration.ViewAWMProrationCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7371	:	11-May-2018	:	Draft
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.viewproration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationSurchargeDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRAViewProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAViewProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class ViewAWMProrationCommand extends BaseCommand {
	
	
	private  Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String FROM_SCREEN = "viewawmproration"; 
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String LIST_SUCCESS = "open_awmcharge";
	private static final String SCREENID_VIEW_PRORATION = "mailtracking.mra.defaults.viewproration";
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";


	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering("ViewAWMProrationCommand","execute");	
		MRAViewProrationSession session = getScreenSession(MODULE_NAME,
				SCREENID_VIEW_PRORATION);
		MRAViewProrationForm form = (MRAViewProrationForm) invocationContext.screenModel;
		Map<String, String> results =  new HashMap<String, String>();
		Collection<String> codes = new ArrayList<String>();
      	codes.add(BASE_CURRENCY);
		try {
      		results = new SharedDefaultsDelegate().findSystemParameterByCodes(codes);
      	} catch(BusinessDelegateException businessDelegateException) {
      		handleDelegateException(businessDelegateException); 
      	}
		String baseCurrency = results.get(BASE_CURRENCY);
		Collection<AWMProrationDetailsVO> awmProrationDetailsVOs = null;

		if ((FROM_SCREEN).equals(form.getFromAction())) {
			ProrationFilterVO prorationFilterVO = session
					.getProrationFilterVO();
			//Modified by A-7794 as part of ICRD-267369
			prorationFilterVO.setBaseCurrency(baseCurrency);
			if (prorationFilterVO != null) {
				try{
				awmProrationDetailsVOs = new MailTrackingMRADelegate()
						.viewAWMProrationDetails(prorationFilterVO);
				}catch (BusinessDelegateException e) {
					e.getMessage();
				}
			}

		}
		if(awmProrationDetailsVOs!=null && awmProrationDetailsVOs.size()>0){
		  Money amtinUsd=null;
		  Money amtinXdr=null;
		  Money amtinBase=null;
		  Money amtinCtr=null;
		  
		  
		for(AWMProrationDetailsVO awmProrationDetailsVO:awmProrationDetailsVOs){
			
			try{
				//Modified by A-7794 as part of ICRD-270858
				  amtinUsd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
				  amtinXdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
				  amtinCtr=CurrencyHelper.getMoney(awmProrationDetailsVO.getCurrency());
				  amtinBase=CurrencyHelper.getMoney(baseCurrency);
				  }catch (CurrencyException e) {
						e.getErrorCode();
					}
			  double totalInUsdSum =0;
				double totalInBaseSum =0;
				double totalInSdrSum =0;
				double totalInCtrSum =0;
				
			if(awmProrationDetailsVO.getAwmProrationSurchargeDetailsVO()!=null && awmProrationDetailsVO.getAwmProrationSurchargeDetailsVO().size()>0){
			for(AWMProrationSurchargeDetailsVO awmProrationSurchargeDetailsVO:awmProrationDetailsVO.getAwmProrationSurchargeDetailsVO()){
				totalInUsdSum+=awmProrationSurchargeDetailsVO.getSurProrationAmtInUsd().getAmount();
				totalInBaseSum+=awmProrationSurchargeDetailsVO.getSurProrationAmtInBaseCurr().getAmount();
				totalInSdrSum+=awmProrationSurchargeDetailsVO.getSurProrationAmtInSdr().getAmount();
				totalInCtrSum+=awmProrationSurchargeDetailsVO.getSurProratedAmtInCtrCur().getAmount();
				
				
			}
		}
			 amtinUsd.setAmount(totalInUsdSum);
			 amtinXdr.setAmount(totalInSdrSum);
			 amtinBase.setAmount(totalInBaseSum);;
			 amtinCtr.setAmount(totalInCtrSum);
			
			awmProrationDetailsVO.setSurProratedAmtInCtrCur(amtinCtr);
			awmProrationDetailsVO.setSurProrationAmtInBaseCurr(amtinBase);
			awmProrationDetailsVO.setSurProrationAmtInSdr(amtinXdr);
			awmProrationDetailsVO.setSurProrationAmtInUsd(amtinUsd);
			
			
     }
		double totalAWMProUsd=0;
		double totalAWMProXdr=0;
		double totalAWMProBase=0;
		double totalAWMProCtr=0;
		double totalAWMSurProUsd=0;
		double totalAWMSurProXdr=0;
		double totalAWMSurProBase=0;
		double totalAWMSurProCtr=0;




		
		
		for(AWMProrationDetailsVO awmProrationDetailsVO:awmProrationDetailsVOs){
				
				totalAWMProUsd+=awmProrationDetailsVO.getProrationAmtInUsd().getAmount();
				totalAWMProXdr+=awmProrationDetailsVO.getProrationAmtInSdr().getAmount();
				totalAWMProBase+=awmProrationDetailsVO.getProrationAmtInBaseCurr().getAmount();
				totalAWMProCtr+=awmProrationDetailsVO.getProrationAmtInCtrCurr().getAmount();
				
				totalAWMSurProUsd+=awmProrationDetailsVO.getSurProrationAmtInUsd().getAmount();
				totalAWMSurProXdr+=awmProrationDetailsVO.getSurProrationAmtInSdr().getAmount();
				totalAWMSurProBase+=awmProrationDetailsVO.getSurProrationAmtInBaseCurr().getAmount();
				totalAWMSurProCtr+=awmProrationDetailsVO.getSurProratedAmtInCtrCur().getAmount();
				
			}
			
			form.setTotalAWMProrationAmtInBaseCurr(String.valueOf(totalAWMProBase));
			form.setTotalAWMProrationAmtInCtrCur(String.valueOf(totalAWMProCtr));
			form.setTotalAWMProrationAmtInSdr(String.valueOf(totalAWMProXdr));
			form.setTotalAWMProrationAmtInUsd(String.valueOf(totalAWMProUsd));
			
			form.setTotalAWMSurProrationAmtInBaseCurr(String.valueOf(totalAWMSurProBase));
			form.setTotalAWMSurProrationAmtInCtrCur(String.valueOf(totalAWMSurProCtr));
			form.setTotalAWMSurProrationAmtInSdr(String.valueOf(totalAWMSurProXdr));
			form.setTotalAWMSurProrationAmtInUsd(String.valueOf(totalAWMSurProUsd));
			form.setContractCurrency(amtinCtr.getCurrencyCode());
			form.setBaseCurrency(baseCurrency); 
			
		session.setAWMProrationVOs((ArrayList<AWMProrationDetailsVO>)awmProrationDetailsVOs);   
		
		}
		invocationContext.target=LIST_SUCCESS;
		log.exiting("ViewAWMProrationCommand","execute");

}
	
}
