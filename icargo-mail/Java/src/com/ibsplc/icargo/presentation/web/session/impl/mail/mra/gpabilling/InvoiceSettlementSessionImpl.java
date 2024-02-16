/*
 * InvoiceSettlementSessionImpl.java Created on Mar 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.mra.gpabilling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementHistoryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-2408
 *
 */
public class InvoiceSettlementSessionImpl extends AbstractScreenSession implements InvoiceSettlementSession {

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.invoicesettlement";

	private static final String KEY_INV_SETTLE_VOS="invoicesettlementvos";

	private static final String KEY_INV_SETTLE_HISTORY_VOS="invoicesettlementhistoryvos";

	private static final String KEY_ONETIME_VOS="onetimevalues";
	
	private static final String KEY_GPA_SETTLE_VO="gpasettlementvo";
	private static final String KEY_GPA_SETTLE_VOS="gpasettlementvos";
	private static final String KEY_SELECTED_GPA_SETTLE_VOS="selectedgpasettlementvos";
	private static final String KEY_SELECTED_GPA_SETTLE_VO="selectedgpasettlementvo";
	private static final String KEY_INV_SETTLE_FILTERVO="invoicesettlementfiltervo";
	private static final String KEY_FROMSAVE="fromSave";
	private static final String LIST_INV_COLLECTION="invoicesettlementdetailvos";

	


	/**
	 *
	 */
	public InvoiceSettlementSessionImpl() {
		super();

	}
	/**
	 * @return
	 */
	@Override
	public String getScreenID() {

		return SCREENID;
	}

	/**
	 * @return
	 */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}


	/**

	 *

	 * @return

	 */

	public HashMap<String, Collection<OneTimeVO>> getOneTimeVOs(){

		return getAttribute(KEY_ONETIME_VOS);

	}
	/**

	 *

	 * @param oneTimeVOs

	 */

	public void setOneTimeVOs(HashMap<String, Collection<OneTimeVO>> oneTimeVOs){

		setAttribute(KEY_ONETIME_VOS, oneTimeVOs);

	}
	/**

	 *

	 *remove onetimes

	 */

	public void removeOneTimeVOs() {

		removeAttribute(KEY_ONETIME_VOS);

	}

	/**
	 * @return
	 */
	public ArrayList<InvoiceSettlementVO> getInvoiceSettlementVOs(){
		return getAttribute(KEY_INV_SETTLE_VOS);
	}
	/**
	 * @param invsettlementvos
	 */
	public void setInvoiceSettlementVOs(ArrayList<InvoiceSettlementVO> invsettlementvos){
		setAttribute(KEY_INV_SETTLE_VOS,invsettlementvos);
	}
	/**
	 * 
	 */
	public void removeInvoiceSettlementVOs(){
		removeAttribute(KEY_INV_SETTLE_VOS);
	}
	/**
	 * @return
	 */
	public ArrayList<InvoiceSettlementHistoryVO> getInvoiceSettlementHistoryVOs(){
		return getAttribute(KEY_INV_SETTLE_HISTORY_VOS);
	}
	/**
	 * @param invsettlementvos
	 */
	public void setInvoiceSettlementHistoryVOs(ArrayList<InvoiceSettlementHistoryVO> invsettlementvos){
		setAttribute(KEY_INV_SETTLE_HISTORY_VOS,invsettlementvos);
	}
	/**
	 * 
	 */
	public void removeInvoiceSettlementHistoryVOs(){
		removeAttribute(KEY_INV_SETTLE_HISTORY_VOS);
	}

	public Page<GPASettlementVO> getGPASettlementVO() {

		return getAttribute(KEY_GPA_SETTLE_VO);
	}
	public void setGPASettlementVO(Page<GPASettlementVO> gpaSettlementVO) {
		setAttribute(KEY_GPA_SETTLE_VO, gpaSettlementVO);
		
	}
	public Page<GPASettlementVO> getGPASettlementVOs() {

		return getAttribute(KEY_GPA_SETTLE_VOS);
	}
	public void setGPASettlementVOs(Page<GPASettlementVO> gpaSettlementVOs) {
		setAttribute(KEY_GPA_SETTLE_VOS, gpaSettlementVOs);
		
	}	
	public Page<GPASettlementVO> getSelectedGPASettlementVOs() {
		
		return getAttribute(KEY_SELECTED_GPA_SETTLE_VOS);
	}
	
	public void setSelectedGPASettlementVOs(
			Page<GPASettlementVO> selectedGPASettlementVOs) {
		setAttribute(KEY_SELECTED_GPA_SETTLE_VOS, selectedGPASettlementVOs);
		
	}
	
	public void setInvoiceSettlementFilterVO(
			InvoiceSettlementFilterVO invoiceSettlementFilterVO) {
		setAttribute(KEY_INV_SETTLE_FILTERVO,invoiceSettlementFilterVO);
		
	}
	
	public InvoiceSettlementFilterVO getInvoiceSettlementFilterVO() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_INV_SETTLE_FILTERVO);
	}
	@Override
	public GPASettlementVO getSelectedGPASettlementVO() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_SELECTED_GPA_SETTLE_VO);
	}
	@Override
	public void setSelectedGPASettlementVO(GPASettlementVO gpaSettlementVO) {
		setAttribute(KEY_SELECTED_GPA_SETTLE_VO, gpaSettlementVO);
	}
	@Override
	public String getFromSave() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_FROMSAVE);
	}
	@Override
	public void setFromSave(String status) {
		setAttribute(KEY_FROMSAVE,status);
		
	}
	/**
	 *	Added by 			: A-7371 on 30-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 */

	public Page<InvoiceSettlementVO> getInvoiceSettlementDetailVOs() {
		
		return (Page<InvoiceSettlementVO>)getAttribute(LIST_INV_COLLECTION);
	}

	/**
	 *	Added by 			: A-7371 on 30-Apr-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invoiceSettlementDetailVOs 
	 */
	
	public void setInvoiceSettlementDetailVOs(
			Page<InvoiceSettlementVO> invoiceSettlementDetailVOs) {
		setAttribute(LIST_INV_COLLECTION,(Page<InvoiceSettlementVO>)invoiceSettlementDetailVOs);
		
	}
	
}
