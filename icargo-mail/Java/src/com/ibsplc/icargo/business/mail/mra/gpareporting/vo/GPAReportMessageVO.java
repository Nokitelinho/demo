/*
 * GPAReportMessageVO.java Created on March 9, 2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1945
 *
 */
public class GPAReportMessageVO extends AbstractVO {

    private String stationCode;

    private String companyCode;

    private String message;

    public static final String GPR_MSG_STD = "IFCSTD";

    public static final String GPR_MSG_TYPE = "GPR";

    public static final String GPR_MSG_VERSION = "1";

    public static final String GPR_DESPATCH_MODE_FTP = "FTP";

    public static final String PARTY_TYPE_AIRLINE = "AR";

    /**
     *
     * @return
     */
    public String getStationCode() {
        return stationCode;
    }

    /**
     *
     * @param stationCode
     */
    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    /**
     *
     * @return
     */
    public String getCompanyCode() {
        return companyCode;
    }

    /**
     *
     * @param companyCode
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
