/*
 * ProductFilterVO.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import java.util.Collection;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1358
 *
 */
public class ServiceFilterVO extends AbstractVO implements Serializable{

    private static final long serialVersionUID = -8009141297186918206L;

	private String companyCode;

    private Collection<String> serviceCodes;

    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * @return Returns the serviceCodes.
     */
    public Collection<String> getServiceCodes() {
        return serviceCodes;
    }
    /**
     * @param serviceCodes The serviceCodes to set.
     */
    public void setServiceCodes(Collection<String> serviceCodes) {
        this.serviceCodes = serviceCodes;
    }
}
