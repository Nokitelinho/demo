/*
 * EmbargoJobSchedulerVO.java Created on Sep 20, 2013
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.reco.defaults.vo;

import java.util.HashMap;

import com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO;

/**
 * The Class EmbargoJobSchedulerVO.
 *
 * @author A-5160
 */
public class EmbargoJobSchedulerVO extends JobScheduleVO {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /** The company code. */
    private String companyCode;
    
    /** The map. */
    private static HashMap map;

    static 
    {
        map = new HashMap();
        map.put("COMPANY_CODE", Integer.valueOf(1));
    }
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getIndex(java.lang.String)
	 */
	public int getIndex(String s)
    {
        return ((Integer)map.get(s)).intValue();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getPropertyCount()
     */
    public int getPropertyCount()
    {
        return map.size();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#getValue(int)
     */
    public String getValue(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            {
            return companyCode;
            }
        default: {
            return null;
        }
        }
    	
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.jobscheduler.vo.JobScheduleVO#setValue(int, java.lang.String)
     */
    public void setValue(int i, String s)
    {
        switch(i)
        {
        case 1: // '\001'
            {
            setCompanyCode(s);
            break;
            }
        default:
        }
    }

    /**
     * Gets the company code.
     *
     * @return the company code
     */
    public String getCompanyCode()
    {
        return companyCode;
    }

    /**
     * Sets the company code.
     *
     * @param s the new company code
     */
    public void setCompanyCode(String s)
    {
        companyCode = s;
    }

   
}