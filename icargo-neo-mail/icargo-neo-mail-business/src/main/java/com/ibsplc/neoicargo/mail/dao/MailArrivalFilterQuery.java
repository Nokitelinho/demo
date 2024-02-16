package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailArrivalFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;

/**
 * @author A-1739
 */
@Slf4j
public class MailArrivalFilterQuery extends NativeQuery {
    private MailArrivalFilterVO mailArrivalFilterVO;
    private String baseQuery;

    /**
     * @throws SystemException
     */
    public MailArrivalFilterQuery(MailArrivalFilterVO mailArrivalFilterVO, String baseQuery) {
        super(PersistenceController.getEntityManager().currentSession());
        this.mailArrivalFilterVO = mailArrivalFilterVO;
        this.baseQuery = baseQuery;
    }
    @Override
    public String getNativeQuery() {
        log.debug(this.getClass().getSimpleName() + " : " + "getNativeQuery" + " Entering");
        String mailStatus = mailArrivalFilterVO.getMailStatus();
        String paCode = mailArrivalFilterVO.getPaCode();
        int nextCarrierId = mailArrivalFilterVO.getNextCarrierId();
        String nextCarrierCode = mailArrivalFilterVO.getNextCarrierCode();

        StringBuilder queryBuilder = new StringBuilder(baseQuery);

        if (!MailConstantsVO.MAIL_STATUS_ALL.equals(mailStatus)) {
            queryBuilder.append(" INNER JOIN SHRCTYMST CTY ON ").
                    append(" CTY.CMPCOD = MALMST.CMPCOD ").
                    append(" AND CTY.CTYCOD =  (SELECT CTYCOD FROM MALEXGOFCMST WHERE CMPCOD = MALMST.CMPCOD AND EXGOFCCOD = MALMST.DSTEXGOFC) ");
        }
        if (MailConstantsVO.MAIL_TERMINATING.equals(mailStatus)) {
            if (paCode != null) {
                queryBuilder.append(" LEFT OUTER JOIN MALEXGOFCMST EXGOFC ON ").
                        append(" EXGOFC.EXGOFCCOD = MALMST.DSTEXGOFC ");
            }
        } else if (MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus)) {
            if (nextCarrierId > 0 || (nextCarrierCode != null &&
                    !nextCarrierCode.trim().isEmpty())) {
                queryBuilder.append(" LEFT OUTER JOIN MALTRFMFTDTL MFTDSN ").
                        append(" ON MFTDSN.MALSEQNUM = MALMST.MALSEQNUM ").
                        append(" AND MFTDSN.CMPCOD = MALMST.CMPCOD").
                        append(" INNER JOIN MALTRFMFT TRFMFT ON ").
                        append(" TRFMFT.TRFMFTIDR   = MFTDSN.TRFMFTIDR");
            }
        }
        queryBuilder.append(" WHERE FLT.CMPCOD = ? ").
                append(" AND FLT.FLTCARIDR = ? ").
                append(" AND FLT.FLTNUM = ? ").
                append(" AND FLT.FLTSEQNUM = ? ").
                append(" AND FLT.POU = ? ");
        int idx = 0;
        setParameter(++idx, MailConstantsVO.MAIL_STATUS_ARRIVED);
        setParameter(++idx, mailArrivalFilterVO.getPou());
        /*
         * Added By Karthick V as the  part of the  Bug Fix in the Query
         *
         */
        setParameter(++idx, mailArrivalFilterVO.getPou());
        setParameter(++idx, mailArrivalFilterVO.getCompanyCode());
        setParameter(++idx, mailArrivalFilterVO.getCarrierId());
        setParameter(++idx, mailArrivalFilterVO.getFlightNumber());
        setParameter(++idx, mailArrivalFilterVO.getFlightSequenceNumber());
        setParameter(++idx, mailArrivalFilterVO.getPou());
        if (MailConstantsVO.MAIL_TERMINATING.equals(mailStatus)) {
            queryBuilder.append(" AND CTY.SRVARPCOD = ? ");
            setParameter(++idx, mailArrivalFilterVO.getPou());
        } else if (MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus)) {
            queryBuilder.append(" AND CTY.SRVARPCOD <> ? ");
            setParameter(++idx, mailArrivalFilterVO.getPou());
        }
        if (MailConstantsVO.MAIL_TERMINATING.equals(mailStatus) || MailConstantsVO.MAIL_STATUS_ALL.equals(mailStatus)) {
            if (paCode != null && !paCode.trim().isEmpty()) {
                queryBuilder.append(" AND EXGOFC.POACOD = ? ");
                setParameter(++idx, mailArrivalFilterVO.getPaCode());
            }
        }
        if (nextCarrierCode != null && !nextCarrierCode.trim().isEmpty()
                && (MailConstantsVO.MAIL_TRANSHIP.equals(mailStatus))) {
            queryBuilder.append(" AND TRFMFT.ARPCOD = ? ").
                    append(" AND TRFMFT.ONWCARCOD = ? ");
            setParameter(++idx, mailArrivalFilterVO.getPou());
            /*
             * Note:-
             * NCA Doesnt have the Schedules of the NH in their System where Querying with the Airline ID
             * will return no  results .so Use the Carrier Code as well ...
             */
            setParameter(++idx, mailArrivalFilterVO.getNextCarrierCode());
        }
        queryBuilder.append(" ORDER BY ULD.ULDNUM, ULD.SEGSERNUM,").
                append(" MALMST.DSN, MALMST.ORGEXGOFC, MALMST.DSTEXGOFC, ").
                append(" MALMST.MALSUBCLS, MALMST.MALCTG, MALMST.YER ,MALMST.MALIDR");

        log.debug(this.getClass().getSimpleName() + " : " + "getNativeQuery" + " Exiting");
        return queryBuilder.toString();
    }
}
