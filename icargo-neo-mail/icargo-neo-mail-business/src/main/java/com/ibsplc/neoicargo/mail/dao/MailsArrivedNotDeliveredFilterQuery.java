package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailStatusFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
public class MailsArrivedNotDeliveredFilterQuery extends NativeQuery {



    private String baseQuery;

    private MailStatusFilterVO mailStatusFilterVO;


    /**
     * @throws SystemException
     * @param baseQuery
     * @param mailStatusFilterVO
     */
    public MailsArrivedNotDeliveredFilterQuery(String baseQuery,
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
            queryBuilder.append(" LEFT OUTER JOIN MALEXGOFCMST EXG ");
            queryBuilder.append(" ON EXG.CMPCOD = MAL.CMPCOD")
                    .append(" AND EXG.EXGOFCCOD = MAL.ORGEXGOFC")
                    .append(" AND EXG.EXGOFCCOD = MAL.ORGEXGOFC");
        }
        if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0
                || mailStatusFilterVO.getPou() !=null && mailStatusFilterVO.getPou().length()>0){
            queryBuilder.append(" LEFT OUTER JOIN MALFLTSEG ASG ");
            queryBuilder.append(" ON ASG.CMPCOD = ULD.CMPCOD")
                    .append(" AND ASG.FLTCARIDR = ULD.FLTCARIDR")
                    .append(" AND ASG.FLTNUM = ULD.FLTNUM")
                    .append(" AND ASG.FLTSEQNUM = ULD.FLTSEQNUM")
                    .append(" AND ASG.SEGSERNUM = ULD.SEGSERNUM");
        }

        queryBuilder.append(" WHERE MAL.CMPCOD =?");
        setParameter(++idx,mailStatusFilterVO.getCompanyCode());

        queryBuilder.append(" AND ULD.ARRSTA = 'Y'")
                .append(" AND MAL.MALSTA = 'ARR'")
                .append(" AND ULD.DLVSTA <> 'Y'");

        if(mailStatusFilterVO.getPacode() != null && mailStatusFilterVO.getPacode().length()>0){
            queryBuilder.append(" AND EXG.POACOD = ?");
            setParameter(++idx,mailStatusFilterVO.getPacode());
        }
        if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0
                || mailStatusFilterVO.getPou() !=null && mailStatusFilterVO.getPou().length()>0){

            if(mailStatusFilterVO.getPol() != null && mailStatusFilterVO.getPol().length()>0){
                queryBuilder.append(" AND ASG.POL =?");
                setParameter(++idx,mailStatusFilterVO.getPol());
            }
            if(mailStatusFilterVO.getPou() != null && mailStatusFilterVO.getPou().length()>0){
                queryBuilder.append(" AND ASG.POU = ?");
                setParameter(++idx,mailStatusFilterVO.getPou());
            }
        }
        if(mailStatusFilterVO.getFromDate()!=null){
			
            queryBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.SCNDAT,'YYYYMMDD')) >= ? ");
            ZonedDateTime dt = mailStatusFilterVO.getFromDate();
            ZonedDateTime d= dt;
            setParameter(++idx,Integer.parseInt(d.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
		
        if(mailStatusFilterVO.getToDate()!=null){
          
            queryBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.SCNDAT,'YYYYMMDD')) <= ?");
            ZonedDateTime dt = mailStatusFilterVO.getToDate();
            ZonedDateTime d= dt;
            setParameter(++idx,Integer.parseInt(d.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
		
        if(mailStatusFilterVO.getFlightCarrierid()>0){
            queryBuilder.append(" AND MAL.FLTCARIDR = ?");
            setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
        }
        if(mailStatusFilterVO.getCarrierid()>0){
            queryBuilder.append(" AND MAL.FLTCARIDR = ?");
            setParameter(++idx,mailStatusFilterVO.getCarrierid());
        }
        if(mailStatusFilterVO.getFlightNumber() !=null && mailStatusFilterVO.getFlightNumber().length()>0){
            queryBuilder.append(" AND MAL.FLTNUM = ?");
            setParameter(++idx,mailStatusFilterVO.getFlightNumber());
        }
        queryBuilder.append(" ) a ");

        log.debug("MailsWithoutCarditFilterQuery", "getNativeQuery");
        return queryBuilder.toString();
    }

}
