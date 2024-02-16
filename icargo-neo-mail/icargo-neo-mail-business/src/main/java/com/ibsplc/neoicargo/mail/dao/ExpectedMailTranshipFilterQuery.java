package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailStatusFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
@Slf4j
public class ExpectedMailTranshipFilterQuery extends NativeQuery {



    private MailStatusFilterVO mailStatusFilterVO;

    private String baseQuery;
    public ExpectedMailTranshipFilterQuery(MailStatusFilterVO mailStatusFilterVO,
                                           String baseQuery) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    @Override
    public String getNativeQuery() {
        log.debug("ExpectedMailTranshipFilterQuery", "getNativeQuery");
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int index = 0;
        boolean isUnion = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
                .getFlightCarrierid() == 0);
        boolean isCarrierAlone = (mailStatusFilterVO.getCarrierid() > 0 && mailStatusFilterVO
                .getFlightCarrierid()==0);
        boolean isFlightAlone = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
                .getFlightCarrierid()>0);
        ZonedDateTime fromScandate = mailStatusFilterVO.getFromDate();
        ZonedDateTime fromSqlDate = null;
        if (fromScandate != null) {
            fromSqlDate = fromScandate;
        }
        String fromScanDateString = String.valueOf(fromSqlDate);

        ZonedDateTime toScandate = mailStatusFilterVO.getToDate();
        ZonedDateTime toSqlDate = null;
        if (toScandate != null) {
            toSqlDate = toScandate;
        }
        String toScanDateString = String.valueOf(toSqlDate);
        log.debug( "The Flag isUnion is", isUnion);
        log.debug( "The isCarrierAlone is", isCarrierAlone);
        log.debug( "isFlightAlone is", isFlightAlone);
        log.debug("The from Scan date String", fromScanDateString);
        log.debug("The toScan date String", toScanDateString);
        log.debug( "The Mail status Filter Vo is ", mailStatusFilterVO);
        String paJoin = null;
        /*
         * The Join conditions to be needed when the pacode is entered as a filter ..
         *
         *
         */
        if (mailStatusFilterVO.getPacode() != null
                && mailStatusFilterVO.getPacode().trim().length() > 0) {
            paJoin = new StringBuilder("  INNER JOIN MALEXGOFCMST EXG ON  ")
                    .append(" CDTTRT.CMPCOD = EXG.CMPCOD AND ").append(
                            " CDTRCP.ORGEXGOFF = EXG.EXGOFCCOD ").toString();
            queryBuilder.append(paJoin);
        }
        queryBuilder.append("  WHERE    CDTTRT.CMPCOD = ?  ");
        this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
        if (mailStatusFilterVO.getPol() != null
                && mailStatusFilterVO.getPol().trim().length() > 0) {
            queryBuilder.append(" AND CDTTRT.ORGCOD  = ? ");
            this.setParameter(++index, mailStatusFilterVO.getPol());
        }
        if (mailStatusFilterVO.getPou() != null
                && mailStatusFilterVO.getPou().trim().length() > 0) {
            queryBuilder.append(" AND CDTTRT.DSTCOD = ? ");
            this.setParameter(++index, mailStatusFilterVO.getPou());
        }
        if (mailStatusFilterVO.getPacode() != null
                && mailStatusFilterVO.getPacode().trim().length() > 0) {
            queryBuilder.append("    AND EXG.POACOD = ?  ");
            this.setParameter(++index, mailStatusFilterVO.getPacode());
        }
        if (mailStatusFilterVO.getFlightNumber() != null
                && mailStatusFilterVO.getFlightNumber().length() > 0) {
            queryBuilder.append(" AND CDTTRT.FLTNUM = ? ");
            this.setParameter(++index, mailStatusFilterVO.getFlightNumber());
        }

        if (mailStatusFilterVO.getCarrierCode()!=null && mailStatusFilterVO.getCarrierCode().trim().length()>0) {
            queryBuilder.append(" AND CDTTRT.CARCOD = ? ");
            this.setParameter(++index, mailStatusFilterVO.getCarrierCode());
        }
        if (fromScandate != null) {
            queryBuilder
                    .append(" AND TO_NUMBER(TO_CHAR(TRUNC(CDTTRT.DEPTIM),'YYYYMMDD')) >= ?  ");
            this.setParameter(++index, Integer.parseInt(fromSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        if (toScandate != null) {
            queryBuilder
                    .append(" AND TO_NUMBER(TO_CHAR(TRUNC(CDTTRT.DEPTIM),'YYYYMMDD')) <= ?  ");
            this.setParameter(++index, Integer.parseInt(toSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        queryBuilder
                .append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN')");
        return queryBuilder.toString();

    }


}
