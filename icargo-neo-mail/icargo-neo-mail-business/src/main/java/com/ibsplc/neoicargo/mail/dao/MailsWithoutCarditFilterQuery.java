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
public class MailsWithoutCarditFilterQuery extends NativeQuery{


    private String baseQuery;

    private MailStatusFilterVO mailStatusFilterVO;


    /**
     * @throws SystemException
     * @param baseQuery
     * @param mailStatusFilterVO
     */
    public MailsWithoutCarditFilterQuery(String baseQuery,
                                         MailStatusFilterVO mailStatusFilterVO) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    /**
     *
     * Mar 11, 2008, A-2553
     * @return
     * */
    @Override
    public String getNativeQuery() {
        log.debug("MailsWithoutCarditFilterQuery", "getNativeQuery");

        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int idx = 0;
        if(mailStatusFilterVO.getPacode() != null && mailStatusFilterVO.getPacode().length()>0){
            queryBuilder.append(" INNER JOIN MALEXGOFCMST EXG ")
                    .append(" ON EXG.CMPCOD = MST.CMPCOD ")
                    .append(" AND EXG.EXGOFCCOD = MST.ORGEXGOFC ")
                    .append(" AND EXG.POACOD =  ? ");
            setParameter(++idx,mailStatusFilterVO.getPacode());
        }
        queryBuilder.append(" WHERE MST.CMPCOD =?");
        setParameter(++idx,mailStatusFilterVO.getCompanyCode());
        queryBuilder.append(" AND FLT.FLTSTA NOT IN ('TBA','TBC','CAN') ");
        if(mailStatusFilterVO.getFromDate()!=null){
            queryBuilder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MST.SCNDAT),'YYYYMMDD')) >= ?");
            ZonedDateTime dt = mailStatusFilterVO.getFromDate();
            ZonedDateTime d= dt;
           // String dat = String.valueOf(d);
            setParameter(++idx,Integer.parseInt(d.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        if(mailStatusFilterVO.getToDate()!=null){
            queryBuilder.append(" AND TO_NUMBER(TO_CHAR(TRUNC(MST.SCNDAT),'YYYYMMDD')) <= ?");
            ZonedDateTime dt = mailStatusFilterVO.getToDate();
            ZonedDateTime d= dt;
           // String dat = String.valueOf(d);
            setParameter(++idx,Integer.parseInt(d.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        if(mailStatusFilterVO.getFlightCarrierid()>0){
            queryBuilder.append(" AND MST.FLTCARIDR = ?");
            setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
        }
        if(mailStatusFilterVO.getCarrierid()>0){
            queryBuilder.append(" AND MST.FLTCARIDR = ?");
            setParameter(++idx,mailStatusFilterVO.getCarrierid());
        }
        if(mailStatusFilterVO.getFlightNumber() !=null && mailStatusFilterVO.getFlightNumber().length()>0){
            queryBuilder.append(" AND MST.FLTNUM = ?");
            setParameter(++idx,mailStatusFilterVO.getFlightNumber());
        }
        if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0){
            queryBuilder.append(" AND MST.SCNPRT = ?");
            setParameter(++idx,mailStatusFilterVO.getPol());
        }
        if(mailStatusFilterVO.getPou() != null && mailStatusFilterVO.getPou().length()>0){
            queryBuilder.append(" AND MST.POU = ?");
            setParameter(++idx,mailStatusFilterVO.getPou());
        }
        queryBuilder.append(" AND MST.MALSTA <> 'RTN' ");
        queryBuilder.append(" AND MST.MALSTA <> 'TRA' ");
        queryBuilder.append(" AND MST.FLTSEQNUM <> 0 ");
        queryBuilder.append(" AND NOT EXISTS ")
                .append(" (SELECT 1 FROM MALCDTRCP ")
                .append(" WHERE MALCDTRCP.CMPCOD = MST.CMPCOD ")
                .append(" AND MALCDTRCP.RCPIDR = MST.MALIDR) ");
        queryBuilder.append(" ORDER BY HIS.FLTCARCOD,HIS.FLTNUM, HIS.FLTDAT, MST.MALIDR ");
        log.debug("MailsWithoutCarditFilterQuery", "getNativeQuery");
        return queryBuilder.toString();
    }


}
