package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailStatusFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
@Slf4j

public class CarditNotAcceptedFilterQuery  extends NativeQuery {


    private String baseQuery;

    private MailStatusFilterVO mailStatusFilterVO;

    /**
     * @throws SystemException
     * @param baseQuery
     * @param mailStatusFilterVO
     */
    public CarditNotAcceptedFilterQuery(String baseQuery,
                                        MailStatusFilterVO mailStatusFilterVO) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.mailStatusFilterVO = mailStatusFilterVO;
    }

    /**
     * TODO Purpose
     * Mar 10, 2008, A-3251
     * @return
     * */
    @Override
    public String getNativeQuery() {
        log.debug("CarditNotAcceptedFilterQuery", "getNativeQuery");

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
            fromdatetemp = datetemp ;
        }
        String fromDateString = String.valueOf(fromdatetemp);
        if(!"".equals(fromDateString.trim()))
        {
            log.debug("mandatory filter field fromDate to Query>>>>>>> ",
                    fromDateString);
            setParameter(++idx,fromDateString);

        }
        datetemp=mailStatusFilterVO.getToDate();
        ZonedDateTime todatetemp=null;
        if (datetemp != null) {
            todatetemp = datetemp ;
        }
        String toDateString = String.valueOf(todatetemp);
        if(!"".equals(toDateString.trim()))
        {
            log.debug("mandatory filter field toomDate to Query>>>>>>> ",
                    toDateString);
            setParameter(++idx,toDateString);
        }
        log.debug( "!!!!!######-->BaseQuery", baseQuery);
        //optional filter fields
        //Carrier code
        if(!"".equals(mailStatusFilterVO.getCarrierCode().trim())&&(mailStatusFilterVO.getCarrierCode()!=null))
        {
            log.debug(  "Carrier code not Null So attaching Carrier id to filter>>>>>>> ",
                            mailStatusFilterVO.getCarrierid());
            queryBuilder.append(" AND CDTTRT.CARIDR = ? ");
            setParameter(++idx,mailStatusFilterVO.getCarrierid());
        }
        //Flight Number

        if(!"".equals(mailStatusFilterVO.getFlightNumber().trim())&&(mailStatusFilterVO.getFlightNumber()!=null))
        {
            log.debug("Flight Number  not Null So attaching  Flight specific details to filter to filter>>>>>>> ");
            queryBuilder.append("  AND CDTTRT.FLTNUM = ? ");
            queryBuilder.append("  AND CDTTRT.CARIDR = ? ");
            //queryBuilder.append("  AND CDTTRT.FLTSEQNUM = ? ");
            setParameter(++idx,mailStatusFilterVO.getFlightNumber().trim());
            setParameter(++idx,mailStatusFilterVO.getFlightCarrierid());
            //setParameter(++idx,mailStatusFilterVO.getFlightSequenceNumber());
        }
        //Airport(POL)
        if(!"".equals(mailStatusFilterVO.getPol().trim())&&(mailStatusFilterVO.getPol()!=null))
        {
            log .debug(  "Airport(POL) not Null So attaching Airport(POL) code to filter>>>>>>> ",
                            mailStatusFilterVO.getPol().trim());
            queryBuilder.append("  AND CDTTRT.ORGCOD= ? ");
            setParameter(++idx,mailStatusFilterVO.getPol().trim());
        }
        //Airport(POU)
        if(!"".equals(mailStatusFilterVO.getPou().trim())&&(mailStatusFilterVO.getPou()!=null))
        {	log.debug( "Airport(POU) not Null So attaching Airport(POU) code to filter>>>>>>> ",
                        mailStatusFilterVO.getPou().trim());
            queryBuilder.append("  AND CDTTRT.DSTCOD= ? ");
            setParameter(++idx,mailStatusFilterVO.getPou().trim());
        }
        //Postal Authority(PA)
        if(!"".equals(mailStatusFilterVO.getPacode().trim())&&(mailStatusFilterVO.getPacode()!=null))
        {
            log.debug(  "Postal Authority(PA) not Null So attaching Postal Authority(PA) code to filter>>>>>>> ",
                            mailStatusFilterVO.getPacode().trim());
            queryBuilder.append(" AND CDTMST.SDRIDR= ? ");
            setParameter(++idx,mailStatusFilterVO.getPacode().trim());
        }


        log.debug( "!!!!!######-->Query Generated After All Filter",
                baseQuery);
        log.debug("CarditNotAcceptedFilterQuery", "getNativeQuery");
        return queryBuilder.toString();
    }


}
