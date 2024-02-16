/*
 * ProrateCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.manualproration;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ManualProrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ManualProrationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class ProrateCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS MANUAL PRORATION");
	
	private static final String CLASS_NAME = "ProrateCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.manualproration";
	
	
	private static final String PRORATE_SUCCESS = "prorate_success";
	

	private static final String AIRLINE="A";
	private static final String GPA="G";
	private static final String RETENTION="T";
	private static final String PAYABLE="P";
	private static final String RECEIVABLE="R";
	private static final String PAYFLAG_ONETIME = "mailtracking.mra.defaults.payflag";

	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		ManualProrationSession  manualProrationSession = (ManualProrationSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		ManualProrationForm manualProrationForm=(ManualProrationForm)invocationContext.screenModel;
//		COMMENTED BY INDU FOR 44354
		/*			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		String[] weightForPri=manualProrationForm.getWeightForPri();
		Double updWght = 0.0;
		Double updWghtCharge = 0.0;
		updWght = Double.parseDouble(weightForPri[0]);
		Collection<ProrationDetailsVO> prorationDetailsVOs = manualProrationSession.getProrationDetailVOs();
		
		Collection<ProrationDetailsVO> updatedprorationDetailsVOs = null;


	Money amountincc=null;
    	Money amountinbas=null;
    	Money amountinusd=null;
    	Money amountinsdr=null;
    	int trecCount =0;
	
    	for( ProrationDetailsVO prorationDetailsVO : prorationDetailsVOs){		

			prorationDetailsVO.setWeight(updWght);
			updWghtCharge = updWght * prorationDetailsVO.getRate();
			
			try {
				amountincc=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
				amountincc.setAmount(updWghtCharge);				
				
				amountinbas=CurrencyHelper.getMoney(manualProrationSession.getBaseCurrency());
				amountinbas.setAmount(updWghtCharge);
				
				amountinusd=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
				amountinusd.setAmount(updWghtCharge);				
				
				amountinsdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
				amountinsdr.setAmount(updWghtCharge);
				
				
				if(prorationDetailsVO.getPayableFlag()!= null && !RETENTION.equals(prorationDetailsVO.getPayableFlag())){
					
					prorationDetailsVO.setProratedAmtInCtrCur(amountincc);
					
					prorationDetailsVO.setProrationAmtInBaseCurr(amountinbas);
					
					prorationDetailsVO.setProrationAmtInUsd(amountinusd);
					
					prorationDetailsVO.setProrationAmtInSdr(amountinsdr);
				}else if(prorationDetailsVO.getPayableFlag()!= null && RETENTION.equals(prorationDetailsVO.getPayableFlag())){					  
					trecCount = trecCount+1;				
					if(trecCount==1){
					
						prorationDetailsVO.setProratedAmtInCtrCur(amountincc);
						
						prorationDetailsVO.setProrationAmtInBaseCurr(amountinbas);
						
						prorationDetailsVO.setProrationAmtInUsd(amountinusd);
						
						prorationDetailsVO.setProrationAmtInSdr(amountinsdr);
					}
				}			
				
			} catch (CurrencyException e) {
				// TODO Auto-generated catch block
//printStackTrrace()();
			}			
			
		}
    	
   	if(trecCount>1){	
    		try {
				updatedprorationDetailsVOs	=	mailTrackingMRADelegate.findProrationForRetensionRecords(prorationDetailsVOs );
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "\n\n\n\n\nPRORATION FAILED ---->>>!!!!!!!!\n\n\n");
			}    		
    		
    	}
    	if(updatedprorationDetailsVOs!=null){
    		manualProrationSession.setProrationDetailVOs(updatedprorationDetailsVOs);	
			log.log(Log.SEVERE, "\n\n\n\n\nupdatedprorationDetailsVOs ---->>>"+updatedprorationDetailsVOs);
    	}else{
    		manualProrationSession.setProrationDetailVOs(prorationDetailsVOs);
			log.log(Log.SEVERE, "\n\n\n\n\nprorationDetailsVOs before CONVERSION---->>>"+prorationDetailsVOs);
    	}*/
    	
    	
    	Collection<ProrationDetailsVO> prorationDataVOs = manualProrationSession.getProrationDetailVOs();
    	Collection<ProrationDetailsVO> updProrationDataVOs=null;
    	
    	
    	if(prorationDataVOs!=null && prorationDataVOs.size()>0){
    		
				updProrationDataVOs = findConvertedChargesForManualProration(prorationDataVOs,manualProrationForm.getOldWt());
		
    		if(updProrationDataVOs!=null && updProrationDataVOs.size()>0){
    			manualProrationSession.setProrationDetailVOs(updProrationDataVOs);
    		}
    	}
    	
    	 prorationDataVOs = manualProrationSession.getProrationDetailVOs();
    	 
   // 	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
//    	Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
//		manualProrationSession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
		Collection<ProrationDetailsVO> primaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
		Collection<ProrationDetailsVO> secondaryDetailsVOs=new ArrayList<ProrationDetailsVO>();
		if(prorationDataVOs!=null){
			for(ProrationDetailsVO vo:prorationDataVOs){
				if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
					if(!(RETENTION.equals(vo.getPayableFlag()))){
						primaryDetailsVOs.add(vo);
					}
					
				}
				if(GPA.equals(vo.getGpaarlBillingFlag())){	
					if(!(RECEIVABLE.equals(vo.getPayableFlag()))){
						primaryDetailsVOs.add(vo);
					}
					
				}
				if(AIRLINE.equals(vo.getGpaarlBillingFlag())){
					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
					}
					
				}
				if(GPA.equals(vo.getGpaarlBillingFlag())){
					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
					}
					
				}
				if(("".equals(vo.getGpaarlBillingFlag()))||(vo.getGpaarlBillingFlag()==null) ){
					
					if(RETENTION.equals(vo.getPayableFlag())){
						secondaryDetailsVOs.add(vo);
						primaryDetailsVOs.add(vo);
					}
					
				}
			}
			
			
		}
		
		
//------------- To group  primary retention records for multi sector flight
		
        
        ArrayList<ProrationDetailsVO> priTrecs =new ArrayList<ProrationDetailsVO>();
       
        ArrayList<ProrationDetailsVO> newProrationVOs =new ArrayList<ProrationDetailsVO>();
    
		Money totalTrecInUsd = null;
		Money totalTrecInBase = null;
		Money totalTrecInSdr = null;
		Money totalTrecInCtr = null;
		
		try {
			totalTrecInUsd = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_USD);
			totalTrecInUsd.setAmount(0.0);
			totalTrecInBase = CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInBase.setAmount(0.0);
			totalTrecInSdr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_XDR);
			totalTrecInSdr.setAmount(0.0);
			totalTrecInCtr=CurrencyHelper.getMoney(MRAConstantsVO.CURRENCY_NZD);
			totalTrecInCtr.setAmount(0.0);

		} catch (CurrencyException e) {
			e.getErrorCode();
		}
		
        for(ProrationDetailsVO priProrationVO : secondaryDetailsVOs){
        	if("T".equals(priProrationVO.getPayableFlag())){
        		priTrecs.add(priProrationVO);	
        		
        	}
        }
        ArrayList<ProrationDetailsVO> priTrecsToremove =new ArrayList<ProrationDetailsVO>();     
     
		String tOrgin ="";
		String tDestn ="";
		int profactor=0;
		int siz=0;
		if(priTrecs!=null && priTrecs.size() >1){
			tOrgin = priTrecs.get(0).getSectorFrom();
			tDestn = priTrecs.get(0).getSectorTo();
			profactor= priTrecs.get(0).getProrationFactor();
			newProrationVOs.add( priTrecs.get(0));
			siz=priTrecs.size()-1;
			for(int i =0;i<siz;i++){
				String secto =  priTrecs.get(i).getSectorTo();
				String nxtsecfrm = priTrecs.get(i+1).getSectorFrom();						
				if(secto.equals(nxtsecfrm)){
					priTrecsToremove.add(priTrecs.get(i));
					priTrecsToremove.add(priTrecs.get(i+1));					
					tDestn = priTrecs.get(i+1).getSectorTo();
					profactor=profactor+priTrecs.get(i+1).getProrationFactor();		
				
					totalTrecInUsd.setAmount((priTrecs.get(i).getProrationAmtInUsd().getAmount())+(priTrecs.get(i+1).getProrationAmtInUsd().getAmount()));
					totalTrecInBase.setAmount((priTrecs.get(i).getProrationAmtInBaseCurr().getAmount())+(priTrecs.get(i+1).getProrationAmtInBaseCurr().getAmount()));
					totalTrecInSdr.setAmount((priTrecs.get(i).getProrationAmtInSdr().getAmount())+(priTrecs.get(i+1).getProrationAmtInSdr().getAmount()));
					totalTrecInCtr.setAmount((priTrecs.get(i).getProratedAmtInCtrCur().getAmount())+(priTrecs.get(i+1).getProratedAmtInCtrCur().getAmount()));
				}	
			}
		}
	
		ProrationDetailsVO priProrationVO = null;
		
		if(priTrecsToremove!=null && priTrecsToremove.size()>0){
			
			primaryDetailsVOs.removeAll(priTrecsToremove);
			
			for(ProrationDetailsVO newProrationVO :newProrationVOs){
				
				priProrationVO =  new ProrationDetailsVO();						
				
				priProrationVO.setOperationFlag("N");						
				priProrationVO.setSectorFrom(tOrgin);
				priProrationVO.setSectorTo(tDestn);
				priProrationVO.setProrationFactor(profactor);
				priProrationVO.setPayableFlag("T");
				priProrationVO.setProrationPercentage(100);
				priProrationVO.setProratPercentage("100");
				priProrationVO.setNumberOfPieces(newProrationVO.getNumberOfPieces());
				priProrationVO.setWeight(newProrationVO.getWeight());
				priProrationVO.setProrationType(newProrationVO.getProrationType());
				priProrationVO.setCarrierCode(newProrationVO.getCarrierCode());
				priProrationVO.setProrationAmtInUsd(totalTrecInUsd);
				priProrationVO.setProrationAmtInBaseCurr(totalTrecInBase);
				priProrationVO.setProrationAmtInSdr(totalTrecInSdr);
				priProrationVO.setProratedAmtInCtrCur(totalTrecInCtr);
				priProrationVO.setSectorStatus(newProrationVO.getSectorStatus());
				priProrationVO.setGpaarlBillingFlag("A");
				primaryDetailsVOs.add(priProrationVO);
			}					
		}
		//---------------------END 	 multi sector flight
		
		
		
		manualProrationSession.setPrimaryProrationVOs(primaryDetailsVOs);		
		manualProrationSession.setSecondaryProrationVOs(secondaryDetailsVOs);    	
    	
    	
		manualProrationForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
		manualProrationForm.setProrated("Y");
		
		invocationContext.target = PRORATE_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
	
	
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(PAYFLAG_ONETIME);
		
		
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}
	
	
	/**
	 * @author A-2391
	 * @param ProrationDetailsVOs
	 */
   public Collection<ProrationDetailsVO> findConvertedChargesForManualProration(Collection<ProrationDetailsVO> prorationDetailsVOs,
		   double oldWt)  {
		log.entering(CLASS_NAME, "findConvertedChargesForManualProration-");
		log.log(Log.INFO, "oldwt in conversion ", oldWt);
			/*
		 * for converting the changed weight charge into USD,XDR,NSD ------------------------------>>
		 */
			for(ProrationDetailsVO prorationDetailsVO : prorationDetailsVOs){

			
				Double wt=prorationDetailsVO.getWeight();
				
				Double amtInUSD =  prorationDetailsVO.getProrationAmtInUsd().getAmount();
				Double amtPerKgUsd =amtInUSD/oldWt;
				
				Double amtInSDR =  prorationDetailsVO.getProrationAmtInSdr().getAmount();
				Double amtPerKgSDR =amtInSDR/oldWt;
				
				Double amtInBase =  prorationDetailsVO.getProrationAmtInBaseCurr().getAmount();
				Double amtPerKgBase =amtInBase/oldWt;
				
				Double amtInCC =  prorationDetailsVO.getProratedAmtInCtrCur().getAmount();
				Double amtPerKgCC =amtInCC/oldWt;
			   
			    prorationDetailsVO.getProrationAmtInUsd().setAmount( amtPerKgUsd*wt);
			    prorationDetailsVO.getProrationAmtInSdr().setAmount( amtPerKgSDR*wt);
			    prorationDetailsVO.getProrationAmtInBaseCurr().setAmount( amtPerKgBase*wt);
			    prorationDetailsVO.getProratedAmtInCtrCur().setAmount( amtPerKgCC*wt);
			
			}
			log.log(Log.INFO, "PRORATION VOS AFTER CONVERSION ",
					prorationDetailsVOs);
			log.exiting(CLASS_NAME, "findConvertedChargesForManualProration");
			return prorationDetailsVOs;

}
}


