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
public class MailsDeliveredFilterQuery extends NativeQuery {


    private String baseQuery;

    private MailStatusFilterVO mailStatusFilterVO;


    /**
     * @throws SystemException
     * @param baseQuery
     * @param mailStatusFilterVO
     */
    public MailsDeliveredFilterQuery(String baseQuery,
                                     MailStatusFilterVO mailStatusFilterVO) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    /**
     * TODO Purpose
     * Mar 11, 2008, A-3251
     * @return
     * */
    @Override
    public String getNativeQuery() {
        log.debug("MailsDeliveredFilterQuery", "getNativeQuery");

        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        int idx = 0;
        //mandatory filter fields

        log.debug("mandatory filter field Company code to Query>>>>>>> ",
                mailStatusFilterVO.getCompanyCode());
        setParameter(++idx,mailStatusFilterVO.getCompanyCode());
        //Date Range
        ZonedDateTime datetemp=mailStatusFilterVO.getFromDate();
        ZonedDateTime fromdatetemp=null;
        if (datetemp != null) {
            fromdatetemp = datetemp;
        }
        String fromDateString = String.valueOf(fromdatetemp);
        if(!"".equals(fromDateString.trim()))
        {
            log.debug( "mandatory filter field fromDate to Query>>>>>>> ",
                    fromDateString);
            setParameter(++idx, Integer.parseInt(fromdatetemp.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        datetemp=mailStatusFilterVO.getToDate();
        ZonedDateTime todatetemp=null;
        if (datetemp != null) {
            todatetemp = datetemp;
        }
        String toDateString = String.valueOf(todatetemp);
        if(!"".equals(toDateString.trim()))
        {
            log.debug( "mandatory filter field toomDate to Query>>>>>>> ",
                    toDateString);
            setParameter(++idx, Integer.parseInt(todatetemp.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        log.debug( "!!!!!######-->BaseQuery", baseQuery);
        //optional filter fields
        //Carrier code
        if(!"".equals(mailStatusFilterVO.getCarrierCode().trim())&&(mailStatusFilterVO.getCarrierCode()!=null))
        {
            log
                    .debug("Carrier code not Null So attaching Carrier id to filter>>>>>>> ",
                            mailStatusFilterVO.getCarrierid());
            queryBuilder.append("AND ULDSEG.FLTCARIDR=?");
            setParameter(++idx,mailStatusFilterVO.getCarrierid());
        }
        //Flight Number

        if(!"".equals(mailStatusFilterVO.getFlightNumber().trim())&&(mailStatusFilterVO.getFlightNumber()!=null))
        {
            log.debug("Flight Number  not Null So attaching  Flight specific details to filter to filter>>>>>>> ");
            queryBuilder.append("AND ULDSEG.FLTNUM=? ");
            queryBuilder.append("AND ULDSEG.FLTCARIDR=?");
            setParameter(++idx,mailStatusFilterVO.getFlightNumber().trim());
            setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
        }
        //Airport(POL)
        if(!"".equals(mailStatusFilterVO.getPol().trim())&&(mailStatusFilterVO.getPol()!=null))
        {
            log
                    .debug("Airport(POL) not Null So attaching Airport(POL) code to filter>>>>>>> ",
                            mailStatusFilterVO.getPol().trim());
            queryBuilder.append("AND ULDSEG.SCNPRT=?");
            setParameter(++idx,mailStatusFilterVO.getPol().trim());
        }
        //Airport(POU)
        if(!"".equals(mailStatusFilterVO.getPou().trim())&&(mailStatusFilterVO.getPou()!=null))
        {	log
                .debug( "Airport(POU) not Null So attaching Airport(POU) code to filter>>>>>>> ",
                        mailStatusFilterVO.getPou().trim());
            queryBuilder.append("AND FLTSEG.POU=?");
            setParameter(++idx,mailStatusFilterVO.getPou().trim());
        }
        //Postal Authority(PA)
        if(!"".equals(mailStatusFilterVO.getPacode().trim())&&(mailStatusFilterVO.getPacode()!=null))
        {
            log
                    .debug("Postal Authority(PA) not Null So attaching Postal Authority(PA) code to filter>>>>>>> ",
                            mailStatusFilterVO.getPacode().trim());
            queryBuilder.append("AND MALMST.POACOD=?");
            setParameter(++idx,mailStatusFilterVO.getPacode().trim());
        }


        log.debug( "!!!!!######-->Query Generated After All Filter",
                baseQuery);
        log.debug("MailsDeliveredFilterQuery", "getNativeQuery");
        return queryBuilder.toString();
    }

}
