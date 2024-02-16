/*
 * CaptureGPAReportSession.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 *  
 * @author A-2257
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 21, 2007			a-2257		Created
 */
public interface CaptureGPAReportSession extends ScreenSession {
	/**
     * 
     * @return GPAReportingFilterVO
     */
	public GPAReportingFilterVO getGPAReportingFilterVO();
	
	/**
	 * 
	 * @param gpaReportingFilterVO
	 */
	public void setGPAReportingFilterVO(GPAReportingFilterVO gpaReportingFilterVO);
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 20, 2006, A-2257
	 */
	public void removeGPAReportingFilterVO();
	
//	/**
//	 * 
//	 * TODO Purpose
//	 * Nov 21, 2006, A-2257
//	 * @return Collection<GPAReportingDetailsVO>
//	 */
//	public Collection<GPAReportingDetailsVO> getGPAReportingDetailsVOs();
//	
//	/**
//	 * 
//	 * TODO Purpose
//	 * Nov 21, 2006, A-2257
//	 * @param gpaReportingDetailsVOs
//	 */
//	public void setGPAReportingDetailsVOs(Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs);
//	
//	/**
//	 * 
//	 * TODO Purpose
//	 * Nov 21, 2006, A-2257
//	 */
//	public void removeGPAReportingDetailsVOs();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 21, 2006, A-2257
	 * @return Page<GPAReportingDetailsVO>
	 */
	public Page<GPAReportingDetailsVO> getGPAReportingDetailsPage();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 21, 2006, A-2257
	 * @param gpaReportingDetailsPage
	 */
	public void setGPAReportingDetailsPage(Page<GPAReportingDetailsVO> gpaReportingDetailsPage);
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 21, 2006, A-2257
	 */
	public void removeGPAReportingDetailsPage();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 21, 2006, A-2257
	 * @return Collection<DocumentInInvoiceVO>
	 */
	public Collection<GPAReportingDetailsVO> getModifiedGPAReportingDetailsVOs();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 21, 2006, A-2257
	 * @param awbVOs
	 */
	public void setModifiedGPAReportingDetailsVOs(Collection<GPAReportingDetailsVO> modifiedDetailsVOs);
	/**
	 * 
	 * TODO Purpose
	 * Nov 28, 2006, A-2257
	 */
	
	public void removeModifiedGPAReportingDetailsVOs();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 28, 2006, A-2257
	 * @return GPAReportingDetailsVO
	 */
	public GPAReportingDetailsVO getSelectedGPAReportingDetailsVO();
	
	/**
	 * 
	 * TODO Purpose
	 * Nov 28, 2006, A-2257
	 * @param gpaReportingDetailsVO
	 */
	public void setSelectedGPAReportingDetailsVO(GPAReportingDetailsVO gpaReportingDetailsVO);
	/**
	 * 
	 * TODO Purpose
	 * Nov 28, 2006, A-2257
	 */
	public void removeSelectedGPAReportingDetailsVO();
	
	/**
	 * 
	 * TODO Purpose
	 * Feb 23, 2007, a-2257
	 * @param indexmap
	 */
	public void setIndexMap(HashMap <String, String> indexmap);
	/**
	 * 
	 * TODO Purpose
	 * Feb 23, 2007, a-2257
	 * @return
	 */
	public HashMap <String, String> getIndexMap();
	/**
	 * 
	 * TODO Purpose
	 * Feb 23, 2007, a-2257
	 * @param key
	 */
	public void removeIndexMap(String key);
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 16, 2007, a-2257
	 * @return
	 */
	
	public ArrayList<OneTimeVO> getMailStatus();
	/**
	 * 
	 * TODO Purpose
	 * Mar 16, 2007, a-2257
	 * @param mailStatus
	 */
	public void setMailStatus(ArrayList<OneTimeVO> mailStatus);
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @return
	 */
	public ArrayList<OneTimeVO> getMailCategory();
	/**
	 * 
	 * TODO Purpose
	 * Apr 9, 2007, a-2257
	 * @param mailCategory
	 */
	public void setMailCategory(ArrayList<OneTimeVO> mailCategory);
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @param highestNum
	 */
	public void setHeighestNum(ArrayList<OneTimeVO> highestNum);
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @return
	 */
	public ArrayList<OneTimeVO> getHeighestNum();
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @param regOrInsInd
	 */
	public void setRegOrInsInd(ArrayList<OneTimeVO> regOrInsInd);
	/**
	 * 
	 * TODO Purpose
	 * Apr 17, 2007, a-2257
	 * @return
	 */
	public ArrayList<OneTimeVO> getRegOrInsInd();
	/**
	 * 
	 * TODO Purpose
	 * Apr 4, 2007, a-2257
	 * @return
	 */
	public String getParentScreenFlag();
	/**
	 * 
	 * TODO Purpose
	 * Apr 4, 2007, a-2257
	 * @param parentScreenFlag
	 */
	public void setParentScreenFlag(String parentScreenFlag) ;
	/**
	 * 
	 * TODO Purpose
	 * Apr 4, 2007, a-2257
	 */
	public void removeParentScreenFlag();
	 /**
     * @param WeightRoundingVO WeightRoundingVO
     */
    public void setWeightRoundingVO(UnitRoundingVO unitRoundingVO);
    /**
     * @return KEY_WEIGHTROUNDINGVO WeightRoundingVO
     */
    public UnitRoundingVO getWeightRoundingVO() ;
    /**
     * @param key
     */
    public void removeWeightRoundingVO(String key) ;
	
}

