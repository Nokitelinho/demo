package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OffloadFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

/**
 * Created by A-9529 on 19-08-2020.
 */
public class ULDReceiptOffloadFilterQuery extends NativeQuery {

    private String baseQuery;
    private OffloadFilterVO offloadFilterVO;

    public ULDReceiptOffloadFilterQuery(String baseQuery, OffloadFilterVO offloadFilterVO ) throws SystemException {
        super();
        this.baseQuery = baseQuery;
        this.offloadFilterVO = offloadFilterVO;
    }

    @Override
    public String getNativeQuery() {
        int index = 0 ;
        StringBuilder query = new StringBuilder();
        query.append(" ").append(baseQuery).append(" ");
        this.setParameter(++index, offloadFilterVO.getCompanyCode());
        this.setParameter(++index, offloadFilterVO.getAirportCode());
        this.setParameter(++index, Integer.parseInt(offloadFilterVO.getOffloadFromDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
        this.setParameter(++index, Integer.parseInt(offloadFilterVO.getOffloadToDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
        if (offloadFilterVO.getUldNumber() != null) {
            query.append("AND OFLD.CONNUM = ?");
            this.setParameter(++index, offloadFilterVO.getUldNumber());
        }
        query.append("ORDER BY OFLD.CONNUM");
        return query.toString();
    }

}
