/*
 * ScreenloadCommand.java Created on Jan 3, 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.changebillingstatus;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.ListInterlineBillingEntriesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ChangeBillingStatusSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingEntriesSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ChangeBillingStatusPopupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * @author A-3434
 *
 */
public class  ScreenloadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ChangeStatus ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.changestatus";

	private static final String MODULE_NAME_ARL = "mailtracking.mra.airlinebilling";
	private static final String SCREEN_ID_ARL = "mailtracking.mra.airlinebilling.defaults.listinterlinebillingentries";

	private static final String MODULE_NAME_GPA = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID_GPA = "mailtracking.mra.gpabilling.billingentries.listgpabillingentries";

	/*
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "screen_success";

	private static final String AIRLINE_BILLING_STATUS="mra.airlinebilling.billingstatus";
	private static final String GPA_BILLING_STATUS="mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String COMMA = ",";
	private static final String FROM_INTERLINEBILLING = "fromInterlineBilling";
	private static final String FROM_GPABILLING = "fromGpaBilling";


	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");

    	ChangeBillingStatusPopupForm form=(ChangeBillingStatusPopupForm )invocationContext.screenModel;

 		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
 		ChangeBillingStatusSession popupsession = (ChangeBillingStatusSession) getScreenSession(
 				MODULE_NAME, SCREEN_ID);
 		ListInterlineBillingEntriesSession listInterlineBillingEntriesSession = (ListInterlineBillingEntriesSession) getScreenSession(
 				MODULE_NAME_ARL, SCREEN_ID_ARL);
 		GPABillingEntriesSession gpabillingEntriesSession = (GPABillingEntriesSession) getScreenSession(
 			MODULE_NAME_GPA, SCREEN_ID_GPA);
 		Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs=new ArrayList<DocumentBillingDetailsVO>();

 		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
 		Map<String, Collection<OneTimeVO>> oneTimeValuesNewForAirline = new HashMap <String, Collection<OneTimeVO>>();
 		Map<String, Collection<OneTimeVO>> oneTimeValuesNewForGpa = new HashMap <String, Collection<OneTimeVO>>();
 		Collection<OneTimeVO> oneTimeVos=new ArrayList<OneTimeVO>();
 		Collection<String> parameterTypes = new ArrayList<String>();

 		parameterTypes.add(AIRLINE_BILLING_STATUS);
 		parameterTypes.add(GPA_BILLING_STATUS);

 		ApplicationSessionImpl applicationSession = getApplicationSession();
 		LogonAttributes logonAttributes = applicationSession.getLogonVO();
 		String companyCode = logonAttributes.getCompanyCode();


 		try {
 			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
 					companyCode, parameterTypes);
 			log.log(Log.FINE, " One Time Values---->>", oneTimeValues);

 		} catch (BusinessDelegateException e) {
 			handleDelegateException(e);

 		}

		String popDespatchNumber="";
		String dsns=form.getDespatchNumbers();

		String[] despatchNumbers=dsns.split(COMMA);
    	String[] selectedrows= form.getSelectedrows().split(COMMA);
    	log.log(Log.FINE, "selectedrows.. >>", form.getSelectedrows());
		if(despatchNumbers!=null && despatchNumbers.length>0){
    		StringBuilder sbul=new StringBuilder(despatchNumbers[0]);
    		log.log(Log.INFO, "-log+sbul ", sbul);
			for(int i=1;i<despatchNumbers.length;i++){
    			if(despatchNumbers[i]!=null && despatchNumbers[i].trim().length()>0){
    				log.log(Log.FINE, "despatchNumbers.. >>", despatchNumbers,
							i);
			if(sbul!=null && sbul.length()>0){
    			sbul=sbul.append(",").append(despatchNumbers[i]);
    			log.log(Log.INFO, "if loop---- ", sbul);
    			}
    		else{
        		sbul=sbul.append(despatchNumbers[i]);
    			log.log(Log.INFO, "else loop---- ", sbul);
    		}
    	}


    	}

    		StringBuilder removeComma=new StringBuilder();
        	int len=sbul.length()-1;
    		for(int i=0;i<len+1;i++){
    		removeComma=removeComma.append(sbul.charAt(i));

    		}
    		log.log(Log.INFO, "removedComma-- ", removeComma);
			popDespatchNumber=removeComma.toString();
       		form.setPopupDespatchNumber(popDespatchNumber);
    	}
		log.log(Log.FINE, "FromScreen... >>", form.getFromScreen());
		if(FROM_INTERLINEBILLING.equals(form.getFromScreen())){
    	 documentBillingDetailsVOs = (Collection<DocumentBillingDetailsVO>)listInterlineBillingEntriesSession
		.getDocumentBillingDetailVOs();


    	/*
 		 * Added For removing unwanted onetime values.
 		}*/

 		Collection<OneTimeVO> airlinebillingStatus = oneTimeValues
		.get(AIRLINE_BILLING_STATUS);

 		HashMap<Integer, OneTimeVO> hashmap=new HashMap() ;

 		int i=0;

 		for(OneTimeVO oneTimeVO:airlinebillingStatus){
 			hashmap.put(i, oneTimeVO);


 			if(!(("OB").equals(oneTimeVO.getFieldValue())||("OH").equals(oneTimeVO.getFieldValue()))){

 				hashmap.keySet().remove(i);

 			}
 			i++;
 		}

 		log.log(Log.FINE, " One Time Values after remove---->>", hashmap);
		for(OneTimeVO  onetimeVO:hashmap.values()){
 			oneTimeVos.add(onetimeVO);
 		}
 		oneTimeValuesNewForAirline.put(AIRLINE_BILLING_STATUS,oneTimeVos);

 		popupsession.setBillingStatus((HashMap<String, Collection<OneTimeVO>>) oneTimeValuesNewForAirline);
    	}

    	else if(FROM_GPABILLING.equals(form.getFromScreen())){
    	 documentBillingDetailsVOs =(Collection<DocumentBillingDetailsVO>)gpabillingEntriesSession.getGpaBillingDetails();

    	 /*
  		 * Added For removing unwanted onetime values.
  		*/
        	Collection<OneTimeVO> gpabillingStatus = oneTimeValues
    		.get(GPA_BILLING_STATUS);
        	HashMap<Integer, OneTimeVO> hashmapForGpa=new HashMap() ;

     		int j=0;

     		for(OneTimeVO oneTimeVO:gpabillingStatus){
     			hashmapForGpa.put(j, oneTimeVO);
     		if(!(("BB").equals(oneTimeVO.getFieldValue())||("OH").equals(oneTimeVO.getFieldValue())
     			||("PO").equals(oneTimeVO.getFieldValue())||("WP").equals(oneTimeVO.getFieldValue())
     			||("DB").equals(oneTimeVO.getFieldValue()))){
     	 			
     				hashmapForGpa.keySet().remove(j);

     			}
     			j++;
     		}

     		log.log(Log.FINE, " One Time Values after remove---->>",
					hashmapForGpa);
			for(OneTimeVO  onetimeVO:hashmapForGpa.values()){
     			oneTimeVos.add(onetimeVO);
     		}
     		oneTimeValuesNewForGpa.put(AIRLINE_BILLING_STATUS,oneTimeVos);

     		popupsession.setBillingStatus((HashMap<String, Collection<OneTimeVO>>) oneTimeValuesNewForGpa);


    	}

    	if(documentBillingDetailsVOs!=null){
    		ArrayList<DocumentBillingDetailsVO> documentBillingDetailsVOArraylist = new ArrayList<DocumentBillingDetailsVO>(
    				documentBillingDetailsVOs);

    	Collection<DocumentBillingDetailsVO> newdocumentBillingDetailsVOs=new ArrayList<DocumentBillingDetailsVO>();

    	for(String selectedrow:selectedrows){

    		newdocumentBillingDetailsVOs.add(documentBillingDetailsVOArraylist.get(Integer.parseInt(selectedrow)));
    		form.setBillingStatus(documentBillingDetailsVOArraylist.get(Integer.parseInt(selectedrow)).getBillingStatus());
    		form.setPopupRemarks(documentBillingDetailsVOArraylist.get(Integer.parseInt(selectedrow)).getRemarks());

    	}

    	log
				.log(
						Log.FINE,
						"Inside pop up screenload command..newdocumentBillingDetailsVOs. >>",
						newdocumentBillingDetailsVOs);
		popupsession.setDocumentBillingDetailvoCol(newdocumentBillingDetailsVOs);
    }


 		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");


}

}




