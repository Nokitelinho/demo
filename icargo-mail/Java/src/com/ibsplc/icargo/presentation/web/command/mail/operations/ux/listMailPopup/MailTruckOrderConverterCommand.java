/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.MailTruckOrderConverterCommand.java
 *
 *	Created by	:	A-7378
 *	Created on	:	25-Jun-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderMailBagVO;
import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.addons.trucking.TruckingOrderSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.ListMailPopupSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.ListMailbagPopupForm;

import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.listMailPopup.MailTruckOrderConverterCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8215	:	19-Aug-2018	:	Draft
 */
public class MailTruckOrderConverterCommand extends BaseCommand{
	
	private static final String ULD_AS_BULK = "BULK";
	private Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	private ErrorVO error = null;
	private static final String TRUCK_MODULE = "addons.trucking";
	private static final String TRUCK_SCREENID =	"addons.trucking.order";
	private static final String SUCCESS="truckOrdernavigateSuccess";
	private static final String FAILURE="truckOrdernavigateFailure";
	private static final String YES="YES";
    private Log log = LogFactory.getLogger("OPERATIONS FLTHANDLING");
    /*
     * The Module Name
     */
    private static final String MODULE_NAME = "mail.operations";
    /*
     * The ScreenID for Acceptance Checkin screen
     */
    private static final String SCREEN_ID = "mail.operations.ux.listmailbagpopup";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		ListMailbagPopupForm listMailbagPopupForm = (ListMailbagPopupForm) invocationContext.screenModel;
		ListMailPopupSession listMailPopupSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		TruckingOrderSession truckingOrderSession = getScreenSession(TRUCK_MODULE, TRUCK_SCREENID);																			
		TruckOrderVO existingTruckOrderVO = truckingOrderSession.getTruckOrderDetailsVO();
		ArrayList<TruckOrderMailBagVO> existingTruckOrderMailBagVos=  (ArrayList<TruckOrderMailBagVO>) existingTruckOrderVO.getTruckOrderMailBagVOs();
		
		Page<MailbagVO> lyingMailbagVOs=listMailPopupSession.getLyingMailbagVOs();
		Page<MailbagVO> carditsVOs=listMailPopupSession.getCarditMailbagVOsCollection();
		ArrayList<TruckOrderMailBagVO> truckOrderMailBagVosToAdd= new ArrayList<TruckOrderMailBagVO>();
		ArrayList<TruckOrderMailBagVO> truckOrderMailBagVosToRemove= new ArrayList<TruckOrderMailBagVO>();
		ArrayList<MailbagVO> selectedMailbagVOs= new ArrayList<MailbagVO>();
		boolean uldAlreadyExists =false;
		
		if(listMailbagPopupForm.getLyingListRowId() != null){
			for (String lyingList : listMailbagPopupForm.getLyingListRowId()) {
				if(lyingList.trim().length()>0){
				int index =Integer.parseInt(lyingList);
				selectedMailbagVOs.add(lyingMailbagVOs.get(index));
				}
			}
			
		}
		
		if(listMailbagPopupForm.getCarditRowId() != null){
			for (String carditRowId : listMailbagPopupForm.getCarditRowId()) {
				if(carditRowId.trim().length()>0){
				int index =Integer.parseInt(carditRowId);
				selectedMailbagVOs.add(carditsVOs.get(index));
				}
			}
			
		}
		
		ArrayList<MailbagVO> selectedMailbagVOsToAdd= new ArrayList<MailbagVO>();
		ArrayList<MailbagVO> selectedMailbagVOsToRemove= new ArrayList<MailbagVO>();
		for (MailbagVO mailbagVO : selectedMailbagVOs) {
			if(mailbagVO.getUldNumber()==null || (mailbagVO.getUldNumber()!=null && mailbagVO.getUldNumber().contains(ULD_AS_BULK) ) ){
				if(selectedMailbagVOsToAdd.size()>0){
					MailbagVO emptyMailbagVO=selectedMailbagVOsToAdd.get(0);
						//Modified by A-8372 as part of IASCB-48104
						if(mailbagVO.getCount() > 0){
							emptyMailbagVO.setCount(emptyMailbagVO.getCount() + mailbagVO.getCount());
						}else{
							emptyMailbagVO.setCount(emptyMailbagVO.getCount() + 1);
						}
					try {
						emptyMailbagVO.setWeight(Measure.addMeasureValues(emptyMailbagVO.getWeight(), mailbagVO.getWeight()));
						emptyMailbagVO.setVolume(Measure.addMeasureValues(emptyMailbagVO.getVolume(), mailbagVO.getVolume()));
						//Modified by A-8372 as part of IASCB-48104 ends
					} catch (UnitException e) {
						log.log(Log.FINE, "Caught UnitException MailTruckOrderConverterCommand", e);
					}
				}else{
					
					mailbagVO.setCount(1);
					mailbagVO.setUldNumber(ULD_AS_BULK);
					selectedMailbagVOsToAdd.add(mailbagVO);
				}
				selectedMailbagVOsToRemove.add(mailbagVO);
				
			}
		}
		if(selectedMailbagVOsToRemove.size()>0)
		selectedMailbagVOs.removeAll(selectedMailbagVOsToRemove);
		if(selectedMailbagVOsToAdd.size()>0)
		selectedMailbagVOs.addAll(selectedMailbagVOsToAdd);
		
		Money	money ;
		try {
			money = CurrencyHelper.getMoney(existingTruckOrderVO.getCurrencyCode());
		} catch (CurrencyException e) {
			money = null;
		}
		
		for (MailbagVO mailbagVO : selectedMailbagVOs) {
		uldAlreadyExists=  false;  //Added by A-8372 as part of IASCB-47831
		if(existingTruckOrderMailBagVos!=null){
			for (TruckOrderMailBagVO truckOrderMailBag : existingTruckOrderMailBagVos) {
				if(!truckOrderMailBag.OPERATION_FLAG_DELETE.equals(truckOrderMailBag.getOperationalFlag())){
				if(truckOrderMailBag.getMailBagUldNumber().equals(mailbagVO.getUldNumber()) || (ULD_AS_BULK.equals(truckOrderMailBag.getMailBagUldNumber()) && mailbagVO.getUldNumber()==null) ){
					uldAlreadyExists=  true;	
					//Modified by A-8372 as part of IASCB-48104
					if(mailbagVO.getCount()>0){
						truckOrderMailBag.setMailBagCount(truckOrderMailBag.getMailBagCount()+mailbagVO.getCount());
					}else{
						truckOrderMailBag.setMailBagCount(truckOrderMailBag.getMailBagCount()+1);
					}
					//Modified by A-8372 as part of IASCB-48104 ends
					try {
						truckOrderMailBag.setWeight(Measure.addMeasureValues(truckOrderMailBag.getWeight(), mailbagVO.getWeight()));
						truckOrderMailBag.setVolume(Measure.addMeasureValues(truckOrderMailBag.getVolume(), mailbagVO.getVolume()));
					} catch (UnitException e) {
						log.log(Log.FINE, "Caught UnitException MailTruckOrderConverterCommand", e);
					}
					truckOrderMailBag.setAddedFromCardit(true);
				
				}
		
			}
		  }
		}
		
			if(!uldAlreadyExists){
				TruckOrderMailBagVO truckOrderMailBag  = new TruckOrderMailBagVO();
				
					truckOrderMailBag.setCompanyCode(mailbagVO.getCompanyCode());
					if(mailbagVO.getUldNumber() != null && !mailbagVO.getUldNumber().isEmpty() ){
						
					truckOrderMailBag.setMailBagUldNumber(mailbagVO.getUldNumber());
					}else{
						truckOrderMailBag.setMailBagUldNumber(ULD_AS_BULK);	
					} 
					truckOrderMailBag.setWeight(mailbagVO.getWeight());
					truckOrderMailBag.setVolume(mailbagVO.getVolume());
					if(mailbagVO.getCount()>0){
						truckOrderMailBag.setMailBagCount(mailbagVO.getCount());
					}else{
						truckOrderMailBag.setMailBagCount(1);
					}
					truckOrderMailBag.setTruckCharge(money.clone());
					truckOrderMailBag.setTruckOrderNumber(existingTruckOrderVO.getTruckOrderNumber());
					truckOrderMailBag.setPou(mailbagVO.getPou());
					truckOrderMailBag.setAddedFromCardit(true);
					truckOrderMailBag.setFlightCarrierIdr(mailbagVO.getCarrierId());
					truckOrderMailBag.setFlightNumber(mailbagVO.getFlightNumber());
					truckOrderMailBag.setFlightSequenceNumber((int)mailbagVO.getFlightSequenceNumber());
					//Modified by A-8372 as part of IASCB-47831
					if(existingTruckOrderMailBagVos == null){
						existingTruckOrderMailBagVos = new ArrayList<>();
					}
					existingTruckOrderMailBagVos.add(truckOrderMailBag);
					//Modified by A-8372 as part of IASCB-47831 ends
			}
			
		}
		
		existingTruckOrderVO.setTruckOrderMailBagVOs(existingTruckOrderMailBagVos);
		truckingOrderSession.setTruckOrderDetailsVO(existingTruckOrderVO);

		listMailbagPopupForm.setOkForScreenClose(YES);
		invocationContext.target = SUCCESS;
		
	}
	
	


}
