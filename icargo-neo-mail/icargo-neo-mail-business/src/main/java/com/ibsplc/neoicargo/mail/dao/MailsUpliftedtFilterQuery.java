package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailStatusFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j

public class MailsUpliftedtFilterQuery extends NativeQuery {


    private MailStatusFilterVO mailStatusFilterVO;

    private String baseQuery;
    public MailsUpliftedtFilterQuery(MailStatusFilterVO mailStatusFilterVO,
                                     String baseQuery) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    @Override
    public String getNativeQuery() {
        log.debug("MailsUpliftedtFilterQuery", "getNativeQuery");
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int index = 0;
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

        log.debug( "The from Scan date String", fromScanDateString);
        log.debug( "The toScan date String", toScanDateString);
        String paJoin = null;
        /*
         * The Join conditions to be needed when the pacode is entered as a
         * filter ..
         *
         *
         */
        if (mailStatusFilterVO.getPacode() != null
                && mailStatusFilterVO.getPacode().trim().length() > 0) {
            paJoin = new StringBuilder("  INNER JOIN MALEXGOFCMST EXG ON  ")
                    .append(" MALMST.CMPCOD = EXG.CMPCOD AND ").append(
                            " MALMST.ORGEXGOFC = EXG.EXGOFCCOD ").toString();
            queryBuilder.append(paJoin);
        }

        queryBuilder.append(" WHERE  FLTSEG.CMPCOD = ?  ");
        this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
        queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");

        if (fromScandate != null) {
            queryBuilder
                    .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) >= ?  ");
            this.setParameter(++index, Integer.parseInt(fromScandate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }

        if (toScandate != null) {
            queryBuilder
                    .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) <= ?  ");
            this.setParameter(++index, Integer.parseInt(toScandate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }

        if (mailStatusFilterVO.getFlightNumber() != null
                && mailStatusFilterVO.getFlightNumber().length() > 0) {
            queryBuilder.append(" AND FLTSEG.FLTNUM = ? ");
            this.setParameter(++index, mailStatusFilterVO.getFlightNumber());
        }

        if (mailStatusFilterVO.getFlightCarrierid() > 0) {
            queryBuilder.append(" AND FLTSEG.FLTCARIDR = ? ");
            this.setParameter(++index, mailStatusFilterVO.getFlightCarrierid());
        }

        if (mailStatusFilterVO.getPol() != null
                && mailStatusFilterVO.getPol().trim().length() > 0) {
            queryBuilder.append(" AND FLTSEG.POL = ? ");
            this.setParameter(++index, mailStatusFilterVO.getPol());
        }

        if (mailStatusFilterVO.getPou() != null
                && mailStatusFilterVO.getPou().trim().length() > 0) {
            queryBuilder.append(" AND FLTSEG.POU = ? ");
            this.setParameter(++index, mailStatusFilterVO.getPou());
        }

        if (mailStatusFilterVO.getPacode() != null
                && mailStatusFilterVO.getPacode().trim().length() > 0) {
            queryBuilder.append("    AND EXG.POACOD = ?  ");
            this.setParameter(++index, mailStatusFilterVO.getPacode());
        }

        /*
         * To check if the Flight is Departed . Only Not Departed Flights need
         * to be fetched.
         */
        queryBuilder.append("    AND  OPRLEG.ATD IS NOT  NULL  ");

        /*
         * If all mails that are without cardit has to be needed .
         *
         *
         */

        if (MailConstantsVO.MAIL_UPLIFTED_WITHOUT_CARDIT
                .equals(mailStatusFilterVO.getCurrentStatus())) {
            queryBuilder.append("   AND NOT EXISTS ( ");
            queryBuilder.append("    SELECT 1 ");
            queryBuilder.append("        FROM MALCDTRCP ");
            queryBuilder.append("   WHERE CMPCOD = MALMST.CMPCOD");
            queryBuilder.append("  AND RCPIDR   = MALMST.MALIDR) ");

        }

        /*
         * If all the Mails that are not delivered is needed ..
         *
         *
         */
        if (MailConstantsVO.MAIL_UPLIFTED_NOT_DELIVERED
                .equals(mailStatusFilterVO.getCurrentStatus())) {
            queryBuilder.append("    AND  MALULD.DLVSTA <> 'Y' AND MALULD.ACPSTA ='Y' ");

        }
        return queryBuilder.toString();

    }


}
