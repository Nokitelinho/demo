/*
 * TransferMailManifestSession.java Created on Apr02, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3353
 *
 */
public interface TransferMailManifestSession extends ScreenSession {
	/**
     * This method is used to set TransferManifestVOs to the session
     * @param Collection<TransferManifestVO>
     */
	public void setTransferManifestVOs(Page<TransferManifestVO> transferManifestVOs);
	 /**
     * The getter method for Collection<TransferManifestVO>
     * @return Collection<TransferManifestVO>
     */
	public Page<TransferManifestVO> getTransferManifestVOs();
	/**
     * This method is used to set TransferManifestFilterVO  to the session
     * @param TransferManifestFilterVO
     */
	public void setTransferManifestFilterVO(TransferManifestFilterVO transferManifestFilterVO);
	 /**
     * The getter method for TransferManifestFilterVO
     * @return TransferManifestFilterVO
     */
	public TransferManifestFilterVO getTransferManifestFilterVO();
	/**
     * This method is used to remove VOs from the session
     * 
     */
	public void removeTransferManifestVOs();
	
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();

    //Added by A-5220 for ICRD-21098 starts
	void setTotalRecordCount(int toralRecordCount);
	int getTotalRecordCount();
	//Added by A-5220 for ICRD-21098 ends
}
