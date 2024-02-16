/*
 * SqlProbePayload.java Created on 13-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.neoicargo.framework.probe.sql;

import com.ibsplc.neoicargo.framework.probe.ProbePayload;
import com.ibsplc.neoicargo.framework.probe.TxProbeUtils;
import com.ibsplc.neoicargo.framework.probe.sql.spy.agents.SpyContext;

import java.io.Serializable;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			13-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@SuppressWarnings("PMD")
public class SqlProbePayload extends ProbePayload implements SpyContext{

    private Boolean executeResponse = Boolean.TRUE;
    private Integer rowCount = 0;

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.ProbePayload#fieldCount()
     */
    @Override
    public int fieldCount() {
        return super.fieldCount() + 2;
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.framework.probe.ProbePayload#writeTo(java.io.Serializable[][])
     */
    @Override
    public void writeTo(Serializable[][] dupletMap) {
        super.writeTo(dupletMap);
        int x = super.fieldCount() - 1;
        dupletMap[++x][0] = "sqlResponse";
        dupletMap[x][1] = this.executeResponse;
        dupletMap[++x][0] = "sqlRowCount";
        dupletMap[x][1] = this.rowCount;
    }

    @Override
    public void set(String param, Object value) {
        if (value == null)
            return;
        if (SpyContext.EXECUTE_RESPONSE.equals(param) && value instanceof Boolean)
            this.executeResponse = (Boolean) value;
        else if (SpyContext.ROW_COUNT.equals(param) && value instanceof Integer)
            this.rowCount = (Integer) value;
        else if (SpyContext.ERROR.equals(param)) {
            this.setSuccess(false);
            if (value instanceof Throwable)
                this.setError(TxProbeUtils.renderException((Throwable) value));
            else
                this.setError(value.toString());
        }
    }

    public Boolean getExecuteResponse() {
        return executeResponse;
    }

    public void setExecuteResponse(Boolean executeResponse) {
        this.executeResponse = executeResponse;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

}
