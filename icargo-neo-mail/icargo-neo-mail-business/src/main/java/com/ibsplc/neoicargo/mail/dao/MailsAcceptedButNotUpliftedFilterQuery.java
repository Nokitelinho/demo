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
public class MailsAcceptedButNotUpliftedFilterQuery  extends NativeQuery {


    private MailStatusFilterVO mailStatusFilterVO;

    private String baseQuery;



    public MailsAcceptedButNotUpliftedFilterQuery(
            MailStatusFilterVO mailStatusFilterVO, String baseQuery)
            throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    @Override
    public String getNativeQuery() {
        log.debug("MailsAcceptedButNotUpliftedFilterQuery", "getNativeQuery");
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int index = 0;
        boolean isUnion = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
                .getFlightCarrierid() == 0);
        boolean isCarrierAlone = (mailStatusFilterVO.getCarrierid() > 0 && mailStatusFilterVO
                .getFlightCarrierid() == 0);
        boolean isFlightAlone = (mailStatusFilterVO.getCarrierid() == 0 && mailStatusFilterVO
                .getFlightCarrierid() > 0);



        log.debug( "The Flag isUnion is ", isUnion);
        log.debug("The isCarrierAlone is", isCarrierAlone);
        log.debug( "isFlightAlone is", isFlightAlone);
        log.debug( "The Mail status Filter Vo is ", mailStatusFilterVO);
        String paJoin = null;
        /*
         * The Join conditions to be needed when the pacode is entered as a filter ..
         *
         *
         */
        //Added by A-5945 for ICRD-103361 starts
        ZonedDateTime fromScandate = mailStatusFilterVO.getFromDate();
        ZonedDateTime fromSqlDate = null;
        if (fromScandate != null) {
            fromSqlDate = fromScandate;
        }
       // String fromScanDateString = String.valueOf(fromSqlDate);

        ZonedDateTime toScandate = mailStatusFilterVO.getToDate();
        ZonedDateTime toSqlDate = null;
        if (toScandate != null) {
            toSqlDate = toScandate;
        }
       // String toScanDateString = String.valueOf(toSqlDate);

        //Added by A-5945 for ICRD-103361 ends
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

            queryBuilder.append(" AND OPRMST.FLTSTA NOT IN ('TBA','TBC','CAN') ");

            if (fromSqlDate != null) {
                queryBuilder
                        .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) >= ?  ");
                this.setParameter(++index, Integer.parseInt(fromSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
            }

            if (toSqlDate != null) {
                queryBuilder
                        .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) <= ?  ");
                this.setParameter(++index, Integer.parseInt(toSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
            }

            if (mailStatusFilterVO.getFlightNumber() != null
                    && mailStatusFilterVO.getFlightNumber().length() > 0) {
                queryBuilder.append(" AND FLTSEG.FLTNUM = ? ");
                this
                        .setParameter(++index, mailStatusFilterVO
                                .getFlightNumber());
            }

            if (mailStatusFilterVO.getFlightDate() != null) {
                queryBuilder
                        .append(" AND ASGFLT.FLTDAT = ?");
                this.setParameter(++index, mailStatusFilterVO.getFlightDate());
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

			/*if (mailStatusFilterVO.getMailCategoryCode() != null
					&& mailStatusFilterVO.getMailCategoryCode().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.MALCTGCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getMailCategoryCode());
			}*/

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
            String acqContainer = MailConstantsVO.CONST_BULK_ACQ_ARP.concat("%");
            String arrContainer = MailConstantsVO.CONST_BULK_ARR_ARP.concat("%");
            queryBuilder.append(" AND MALULD.CONNUM not like ? ");
            this.setParameter(++index,acqContainer);
            queryBuilder.append(" AND MALULD.CONNUM not like ? ");
            this.setParameter(++index, arrContainer);

        }

        if (isUnion || isCarrierAlone) {

            if (isUnion) {
                queryBuilder.append("  UNION ALL");
                queryBuilder
                        .append(" SELECT MALULD.ARPCOD AS POL, MALMST.POU, '-1' FLTNUM, ");
                queryBuilder.append(" MALMST.MALIDR, MALMST.WGT, ");
                queryBuilder
                        .append(" TO_DATE (NULL) AS FLTDAT , (SELECT (CASE WHEN APHCODUSE = 2  THEN TWOAPHCOD ELSE THRAPHCOD END )  FROM SHRARLMST     WHERE CMPCOD = MALULD.CMPCOD     AND ARLIDR = MALULD.FLTCARIDR)    FLTCARCOD , ");
                queryBuilder.append(" MALULD.CONNUM, TO_DATE (NULL) AS STD, '' LEGSTA, '' FLTROU ");
                queryBuilder.append(" FROM MALARPULDDTL  MALULD ");
                queryBuilder.append("INNER JOIN MALMST MALMST ON MALMST.CMPCOD            = MALULD.CMPCOD AND MALMST.FLTCARIDR        = MALULD.FLTCARIDR AND MALMST.MALSEQNUM        = MALULD.MALSEQNUM ");

                if (mailStatusFilterVO.getPacode() != null
                        && mailStatusFilterVO.getPacode().trim().length() > 0) {
                    queryBuilder.append(paJoin);

                }

            }

            queryBuilder.append(" WHERE   MALULD.CMPCOD = ?  ");
            this.setParameter(++index, mailStatusFilterVO.getCompanyCode());

            if (fromSqlDate != null) {
                queryBuilder
                        .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) >= ?  ");
                this.setParameter(++index, Integer.parseInt(fromSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
            }

            if (toSqlDate != null) {
                queryBuilder
                        .append(" AND TO_NUMBER(TO_CHAR(TRUNC(MALULD.SCNDAT),'YYYYMMDD')) <= ?  ");
                this.setParameter(++index, Integer.parseInt(toSqlDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
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
			/*if (mailStatusFilterVO.getPou() != null
					&& mailStatusFilterVO.getPou().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.ARPCOD = ? ");
				this.setParameter(++index, "");
			}*/
            //END

			/*if (mailStatusFilterVO.getMailCategoryCode() != null
					&& mailStatusFilterVO.getMailCategoryCode().trim().length() > 0) {
				queryBuilder.append(" AND MALULD.MALCTGCOD = ? ");
				this.setParameter(++index, mailStatusFilterVO.getMailCategoryCode());
			}*/

            if (mailStatusFilterVO.getCarrierid() > 0) {
                queryBuilder.append(" AND MALULD.FLTCARIDR = ? ");
                this.setParameter(++index, mailStatusFilterVO.getCarrierid());
            }
            if (mailStatusFilterVO.getPacode() != null
                    && mailStatusFilterVO.getPacode().trim().length() > 0) {
                queryBuilder.append("    AND EXG.POACOD = ?  ");
                this.setParameter(++index, mailStatusFilterVO.getPacode());
            }

            //added by A-8149 for ICRD-261476 starts
            if (mailStatusFilterVO.getPou() != null
                    && mailStatusFilterVO.getPou().trim().length() > 0) {
                queryBuilder.append(" AND MALMST.POU  = ? ");
                this.setParameter(++index, mailStatusFilterVO.getPou());
            }
            //added by A-8149 for ICRD-261476 ends

            String acqContainer = MailConstantsVO.CONST_BULK_ACQ_ARP.concat("%");
            String arrContainer = MailConstantsVO.CONST_BULK_ARR_ARP.concat("%");
            queryBuilder.append(" AND MALULD.CONNUM not like ? ");
            this.setParameter(++index,acqContainer);
            queryBuilder.append(" AND MALULD.CONNUM not like ? ");
            this.setParameter(++index, arrContainer);

        }
        //queryBuilder.append(" order by MALULD.MALIDR ");
        return queryBuilder.toString();

    }


}
