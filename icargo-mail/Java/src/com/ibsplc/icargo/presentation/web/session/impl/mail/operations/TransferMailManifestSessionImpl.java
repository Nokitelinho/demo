/*
 * TransferMailManifestSessionImpl.java Created on Apr02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;

import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferMailManifestSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3353
 *
 */
public class TransferMailManifestSessionImpl extends AbstractScreenSession implements TransferMailManifestSession{

	private static final String SCREEN_ID = "mailtracking.defaults.transfermailmanifest";
	private static final String MODULE_NAME = "mail.operations";

	private static final String KEY_TRANSFERMANIFESTFILTERVO = "transferManifestFilterVO";
	private static final String KEY_TRANSFERMANIFESTVO = "transferManifestVO";
	private static final String KEY_INDEXMAP = "indexMap";

	//Added by A-5220 for ICRD-21098 starts
	public static final String KEY_TOTAL_RECORD_COUNT="mailtracking.defaults.transfermailmanifest.list.totalrecordcount";
	//Added by A-5220 for ICRD-21098 ends

	 /**
     * @return SCREEN_ID - String
     */
	@Override
	public String getScreenID() {
		return SCREEN_ID;
	}

	/**
     * @return MODULE_NAME - String
     */
	@Override
	public String getModuleName() {
		return MODULE_NAME;
	}
	 /**
     * The getter method for TransferManifestFilterVO
     * @return TransferManifestFilterVO
     */ 
	public TransferManifestFilterVO getTransferManifestFilterVO() {
		
		return getAttribute(KEY_TRANSFERMANIFESTFILTERVO);
	}
	/**
     * The getter method for Collection of TransferManifestVO
     * @return Page<TransferManifestVO>
     */
	public Page<TransferManifestVO> getTransferManifestVOs() {
		return(Page<TransferManifestVO>) getAttribute(KEY_TRANSFERMANIFESTVO);
	}
	/**
     * This method is used to remove VOs from the session
     * 
     */
	public void removeTransferManifestVOs() {
		removeAllAttributes();
	}
	/**
     * This method is used to set TransferManifestFilterVO  to the session
     * @param transferManifestFilterVO
     */
	public void setTransferManifestFilterVO(TransferManifestFilterVO transferManifestFilterVO) {
		setAttribute(KEY_TRANSFERMANIFESTFILTERVO, transferManifestFilterVO);
	}
	/**
     * This method is used to set ContainerDetailsVOs to the session
     * @param transferManifestVOs
     */
	public void setTransferManifestVOs(Page<TransferManifestVO> transferManifestVOs) {
		setAttribute(KEY_TRANSFERMANIFESTVO,(Page<TransferManifestVO> )transferManifestVOs);
		
	}
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP, indexMap);
	}
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap(){
    	return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
    }
	

  //Added by A-5220 for ICRD-21098 starts
	public void setTotalRecordCount(int toralRecordCount) {
		// TODO Auto-generated method stub
		setAttribute(KEY_TOTAL_RECORD_COUNT, toralRecordCount);
	}
	public int getTotalRecordCount() {
		// TODO Auto-generated method stub
		return (Integer)getAttribute(KEY_TOTAL_RECORD_COUNT);
	}
	//Added by A-5220 for ICRD-21098 ends

}
