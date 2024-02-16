package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailStatusFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class MailsNotUpliftedFilterQuery  extends NativeQuery {


    private MailStatusFilterVO mailStatusFilterVO;

    private String baseQuery;
    public MailsNotUpliftedFilterQuery(MailStatusFilterVO mailStatusFilterVO,
                                       String baseQuery) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    @Override
    public String getNativeQuery() {
        log.debug("MailsNotUpliftedFilterQuery", "getNativeQuery");
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
        log.debug( "The from Scan date String", fromScanDateString);
        log.debug( "The toScan date String", toScanDateString);
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
                    .append(" MALULD.CMPCOD = EXG.CMPCOD AND ").append(
                            " MALULD.ORGEXGOFC = EXG.EXGOFCCOD ").toString();
            queryBuilder.append(paJoin);
        }
        /*
         * The following Possibilites are possible :-
         * 1. Flight Present ( Flag used - isFlightAlone):-
         *     The system should fetch all the mailbags in that flight that are not uplifted.
         * 2.Carrier Present(Flag used- isCarrierAlone)
         *    The  system should fetch all the mailbags that are 1.latest  carrier Assigned
         *                                                       2.Offloaded Mails
         *   Note:-
         *    As if now the system will also fetch all the mails that are in the Inventory at that port
         *    when an arrival happens and the Delivery or Transfer for that Particular Mailbag has not  happened .
         *    Actually these are not actually a Pure  Carrier Acceptance at that Port..
         *    TODO  Later:-
         *    If these inventoy mails are not required Code to be changed Accordingly
         * 3.Both absent (Flag used- isUnion)
         */

        if (isUnion || isFlightAlone) {
            queryBuilder.append("  WHERE    FLTSEG.CMPCOD = ?  ");
            this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
            queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");
            if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
                queryBuilder.append(" AND MALULD.ARRSTA = 'N' ");
            }
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
                this
                        .setParameter(++index, mailStatusFilterVO
                                .getFlightNumber());
            }

            if (mailStatusFilterVO.getFlightCarrierid() > 0) {
                queryBuilder.append(" AND FLTSEG.FLTCARIDR = ? ");
                this.setParameter(++index, mailStatusFilterVO
                        .getFlightCarrierid());
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
             * To check  if the Flight is Departed .
             * Only Not Departed Flights need to be  fetched.
             */
            queryBuilder.append("    AND  OPRLEG.ATD IS  NULL  ");


        }

        if (isUnion || isCarrierAlone) {

            if (isUnion) {
                queryBuilder.append("  UNION ALL");
                queryBuilder
                        .append(" SELECT MALMST.MALIDR, MALULD.ARPCOD AS POL, '' POU, '-1' FLTNUM, ");
                queryBuilder.append(" MALMST.WGT, ");
                queryBuilder.append(" TO_DATE (NULL) AS FLTDAT , (SELECT ( CASE WHEN APHCODUSE = 2  THEN TWOAPHCOD ELSE THRAPHCOD END )  FROM SHRARLMST     WHERE CMPCOD = MALULD.CMPCOD     AND ARLIDR = MALULD.FLTCARIDR)    FLTCARCOD ,");
                queryBuilder.append(" CASE WHEN (CDTMST.CSGDOCNUM) IS NOT NULL THEN 'Y' ELSE 'N' END CDTAVL,MALMST.DSN ");
                queryBuilder.append(" FROM MALARPULDDTL  MALULD ");

                if (mailStatusFilterVO.getPacode() != null
                        && mailStatusFilterVO.getPacode().trim().length() > 0) {
                    queryBuilder.append(paJoin);

                }

            }

            if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
                queryBuilder.append(" INNER JOIN MALMST MALMST ON MALULD.CMPCOD  = MALMST.CMPCOD");
                queryBuilder.append(" AND MALULD.MALSEQNUM = MALMST.MALSEQNUM");
                queryBuilder.append(" AND MALULD.FLTCARIDR  = MALMST.FLTCARIDR");
                queryBuilder.append(" LEFT OUTER JOIN MALCDTMST CDTMST ON MALMST.CMPCOD = CDTMST.CMPCOD AND MALMST.CSGDOCNUM = CDTMST.CSGDOCNUM");
            }

            queryBuilder.append(" WHERE   MALULD.CMPCOD = ?  ");
            this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
            if("Mails not Uplifted".equals(mailStatusFilterVO.getCurrentStatus())){
                queryBuilder.append(" AND MALMST.FLTNUM      =    '-1' ");
            }
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

            if (mailStatusFilterVO.getPol() != null
                    && mailStatusFilterVO.getPol().trim().length() > 0) {
                queryBuilder.append(" AND MALULD.ARPCOD = ? ");
                this.setParameter(++index, mailStatusFilterVO.getPol());
            }
            /*START
             * If POU is given in the filter,
             * 1. Carrier only case, the query should not pick any values.
             * 2. In no carrier - no flight case,
             * 	  the mail in the flight will be taken be fetched by the above written code,
             * 	  So here in carrier related code we dont want to fetch any data,if POU is given.
             */
            if (mailStatusFilterVO.getPou() != null
                    && mailStatusFilterVO.getPou().trim().length() > 0) {
                queryBuilder.append(" AND MALULD.ARPCOD = ? ");
                this.setParameter(++index, "");
            }
            //END

            if (mailStatusFilterVO.getCarrierid() > 0) {
                queryBuilder.append(" AND MALULD.FLTCARIDR = ? ");
                this.setParameter(++index, mailStatusFilterVO.getCarrierid());
            }
            if (mailStatusFilterVO.getPacode() != null
                    && mailStatusFilterVO.getPacode().trim().length() > 0) {
                queryBuilder.append("    AND EXG.POACOD = ?  ");
                this.setParameter(++index, mailStatusFilterVO.getPacode());
            }

        }
        return queryBuilder.toString();

    }



}
