/*
 * MRAFlownDAO.java Created on Dec 14, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.mra.flown;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.flown.vo.DSNForFlownSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailRevenueVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.MailBagForFlownSegmentVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

/**
 * This Class is for MRAFlownDAO
 * @author A-2338
 *
 */

public interface MRAFlownDAO {


    /**
     * This method is for findFlownMailMags
     * @param flownMailFilterVO Collection <MailBagForFlownSegmentVO>
     * @return
     */
    public Collection<MailBagForFlownSegmentVO> findFlownMailMags(
    		FlownMailFilterVO flownMailFilterVO) throws SystemException ,PersistenceException;

    /**
     * This method is for findFlownDSNs
     * @param flownMailFilterVO Collection <DSNForFlownSegmentVO>
     * @return
     */
    public Collection<DSNForFlownSegmentVO> findFlownDSNs(
    		FlownMailFilterVO flownMailFilterVO) throws SystemException ,PersistenceException;


    /**
     * This method is for findFlownMailExceptions
     * @param flownMailFilterVO Collection <FlownMailExceptionVO>
     * @return
     */
    public Collection<FlownMailExceptionVO> findFlownMailExceptions(
            FlownMailFilterVO flownMailFilterVO) throws SystemException;
    /**
     * This method is for findFlownMailExceptions
     * @param flownMailFilterVO Collection <FlownMailExceptionVO>
     * @return
     */
    public Collection<FlownMailExceptionVO> findFlownMailExceptionsforprint(
            FlownMailFilterVO flownMailFilterVO) throws SystemException;
    
    /**
     * This method is for findFlownMailExceptions
     * @param flownMailFilterVO Collection <FlownMailExceptionVO>
     * @return
     */
    public Collection<FlownMailExceptionVO> findFlownMailExceptionsforprintDetails(
            FlownMailFilterVO flownMailFilterVO) throws SystemException;
    /**
     * This method is for closeFlight
     * @param flownMailFilterVO
     */
    public void closeFlight(FlownMailFilterVO flownMailFilterVO)
    		throws SystemException ,PersistenceException;


    /**
     * This method is for Processing Flight Segment
     * 
     * Mar 26, 2007, A-2401
     * @param flownMailFilterVO
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public String processFlight(FlownMailFilterVO flownMailFilterVO)
    throws SystemException ,PersistenceException;
    /**
     * This method is for finding segmentdetails
     * @param flownMailFilterVO
     */
    public Collection<FlownMailSegmentVO> findFlightDetails(
    		FlownMailFilterVO flownMailFilterVO)throws SystemException ,PersistenceException;
    
    
    /**
     * @author A-2449
     * @param flownMailFilterVO
     * @return Collection<FlownMailSegmentVO>
     */
    public Collection<FlownMailSegmentVO> findListOfFlightsForReport(
    		FlownMailFilterVO flownMailFilterVO) throws SystemException;
    
    /**
     * @author A-2449
     * @param flownMailFilterVO
     * @return Collection<FlownMailSegmentVO>
     */
    public Collection<FlownMailSegmentVO> findListOfFlownMailsForReport(
    		FlownMailFilterVO flownMailFilterVO) throws SystemException;
    
    /***
     * @author A-2270
     * @param flownMailFilterVO
     * @return
     * @throws SystemException
     */
    public Collection<FlownMailRevenueVO> generateMailRevenueReportDetails(FlownMailFilterVO flownMailFilterVO)
    throws SystemException;
    
    /**
     * 	Method		:	MRAFlownDAO.importArrivedMailstoMRA
     *	Added by 	:	A-4809 on Oct 12, 2015
     * 	Used for 	:
     *	Parameters	:	@param companyCode
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	void
     */
    public void importArrivedMailstoMRA(String companyCode) throws SystemException;
    
    /**
     * @author A-7794 as part of ICRD-232299
     * @param companyCode
     * @param startDate
     * @param endDate
     * @throws SystemException
     */
    public void forceImportScannedMailbags(String companyCode,String startDate,String endDate) throws SystemException;
}