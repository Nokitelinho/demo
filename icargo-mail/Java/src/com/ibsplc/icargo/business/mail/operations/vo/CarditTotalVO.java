/*
 * CarditTotalVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * TODO Add the purpose of this class
 *
 * @author A-5991
 *
 */
/*
 *  Revision History
 *--------------------------------------------------------------------------
 *  Revision 	Date      	           		   Author			Description
 * -------------------------------------------------------------------------
 *  0.1			Jun 30, 2016				   A-5991			First Draft
 */
public class CarditTotalVO extends AbstractVO {

    /**
     * Number of receptacles
     * If value is null default value is set as -1
     */
    private String numberOfReceptacles;
   
    /**
     * Weight of receptacles
     * If value is null default value is set as -1
     */
   // private String weightOfReceptacles;
    private Measure weightOfReceptacles;//added by A-7371
    
    private String mailClassCode;
   // private String totalWeight;
    private Measure totalWeight;//added by A-7371

/**
 * 
 * @return weightOfReceptacles
 */
    public Measure getWeightOfReceptacles() {
		return weightOfReceptacles;
	}
/**
 * 
 * @param weightOfReceptacles
 */
	public void setWeightOfReceptacles(Measure weightOfReceptacles) {
		this.weightOfReceptacles = weightOfReceptacles;
	}
/**
 * 
 * @return totalWeight
 */
	public Measure getTotalWeight() {
		return totalWeight;
	}
/**
 * 
 * @param totalWeight
 */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}

    /**
     * @return Returns the mailClassCode.
     */
    public String getMailClassCode() {
        return mailClassCode;
    }

    /**
     * @param mailClassCode The mailClassCode to set.
     */
    public void setMailClassCode(String mailClassCode) {
        this.mailClassCode = mailClassCode;
    }

    /**
     * @return Returns the numberOfReceptacles.
     */
    public String getNumberOfReceptacles() {
        return numberOfReceptacles;
    }

    /**
     * @param numberOfReceptacles The numberOfReceptacles to set.
     */
    public void setNumberOfReceptacles(String numberOfReceptacles) {
        this.numberOfReceptacles = numberOfReceptacles;
    }

    /**
     * @return Returns the weightOfReceptacles.
     */
    /*public String getWeightOfReceptacles() {
        return weightOfReceptacles;
    }

    *//**
     * @param weightOfReceptacles The weightOfReceptacles to set.
     *//*
    public void setWeightOfReceptacles(String weightOfReceptacles) {
        this.weightOfReceptacles = weightOfReceptacles;
    }
*/
	/**
	 * @return Returns the totalWeight.
	 */
	/*public String getTotalWeight() {
		return totalWeight;
	}

	*//**
	 * @param totalWeight The totalWeight to set.
	 *//*
	public void setTotalWeight(String totalWeight) {
		this.totalWeight = totalWeight;
	}*/
}
