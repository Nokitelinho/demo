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

public class ExpectedMailCarditFilterQuery   extends NativeQuery  {


    private MailStatusFilterVO mailStatusFilterVO;
    private static final  String DATE_FORMAT ="yyyyMMdd";
    /**
     *
     */
    public ExpectedMailCarditFilterQuery(MailStatusFilterVO mailStatusFilterVO) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());

        this.mailStatusFilterVO=mailStatusFilterVO;

    }
    public String getNativeQuery() {
        log.debug("CarditEnquiryFilterQuery", "getNativeQuery");

        StringBuilder queryBuilder = new StringBuilder();
        StringBuilder filterQuery = new StringBuilder();
        ZonedDateTime fromDate=mailStatusFilterVO.getFromDate();
        ZonedDateTime toDate =mailStatusFilterVO.getToDate();
        //LocalDate flightDate=mailStatusFilterVO.getFlightDate();
        String flightNumber=mailStatusFilterVO.getFlightNumber();
        String pol=mailStatusFilterVO.getPol();
        String pou=mailStatusFilterVO.getPou();
        String pacode=mailStatusFilterVO.getPacode();
        Integer flightCarrierIdr=mailStatusFilterVO.getFlightCarrierid();
        Integer carrierIdr=mailStatusFilterVO.getCarrierid();
        //Integer legSerialNumber=mailStatusFilterVO.getLegSerialNumber();
        //Long flightSequenceNumber=mailStatusFilterVO.getFlightSequenceNumber();

        int index = 0;

        queryBuilder.append(" SELECT RTG.POL,RTG.POU,RTG.FLTNUM,MALMST.MALIDR,MALMST.WGT,RTG.FLTDAT,")
                .append(" RTG.FLTCARCOD FLTCARCOD, CASE WHEN (CDTMST.CSGDOCNUM) IS NOT NULL ")
                .append(" THEN 'Y' ELSE 'N' END CDTAVL,MALMST.DSN ")
                .append(" FROM MALCSGMST CSGMST ")
                .append(" INNER JOIN MALCSGDTL MST ")
                .append("ON CSGMST.CMPCOD  = MST.CMPCOD AND CSGMST.CSGDOCNUM  = MST.CSGDOCNUM ")
                .append(" AND CSGMST.CSGSEQNUM  = MST.CSGSEQNUM ")
                .append(" AND NOT EXISTS (SELECT 1 FROM MALMST MALMST WHERE MALMST.CMPCOD = MST.CMPCOD ")
                .append(" AND MALMST.MALSEQNUM = MST.MALSEQNUM AND MALMST.POACOD = MST.POACOD AND MALMST.MALSTA <> 'NEW')")
                .append(" INNER JOIN MALMST MALMST ")
                .append(" ON MALMST.CMPCOD = MST.CMPCOD ")
                .append(" AND MALMST.MALSEQNUM = MST.MALSEQNUM AND MALMST.POACOD = MST.POACOD AND MALMST.MALSTA = 'NEW'")
                .append(" INNER JOIN MALCSGRTG RTG ")
                .append(" ON MST.CMPCOD  = RTG.CMPCOD AND MST.CSGDOCNUM = RTG.CSGDOCNUM ")
                .append(" AND MST.CSGSEQNUM = RTG.CSGSEQNUM ");


        if (mailStatusFilterVO.getCurrentStatus().equals(MailConstantsVO.EXPECTED_MAIL_CARDIT)) {
            queryBuilder.append(" INNER JOIN MALCDTMST CDTMST ");
        }else{
            queryBuilder.append(" LEFT OUTER JOIN MALCDTMST CDTMST ");
        }
        queryBuilder.append(" ON MST.CMPCOD = CDTMST.CMPCOD AND MST.CSGDOCNUM = CDTMST.CSGDOCNUM ");

        filterQuery
                .append(" WHERE MST.CMPCOD   = ? ");
        this.setParameter(++index, mailStatusFilterVO.getCompanyCode());
        if (fromDate != null) {
            filterQuery

                    .append(" AND to_number(to_char(csgmst.csgdat, 'YYYYMMDD')) >= ? ");


            this.setParameter(++index, Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }

        if (toDate != null) {
            filterQuery

                    .append("AND to_number(to_char(csgmst.csgdat, 'YYYYMMDD')) <= ? ");

            this.setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }

        if(pacode !=null && pacode.length() > 0){
            filterQuery.append(" AND CSGMST.POACOD = ? ");
            this.setParameter(++index, pacode);
        }

        if(carrierIdr != null && carrierIdr > 0){
            filterQuery.append(" AND RTG.FLTCARIDR = ? ");
            this.setParameter(++index, carrierIdr);
        }

        if(pol !=null && pol.length() > 0){
            filterQuery.append(" AND SUBSTR(MALMST.MALIDR,3,3) = ? ");
            this.setParameter(++index, pol);
        }

        if(pou !=null && pou.length() > 0){
            filterQuery.append(" AND SUBSTR(MALMST.MALIDR,9,3) = ? ");
            this.setParameter(++index, pou);
        }
        if(flightNumber != null && flightNumber.length() > 0 && flightCarrierIdr != null && flightCarrierIdr > 0){
            filterQuery .append(" AND RTG.FLTCARIDR = ?  ")
                    .append(" AND RTG.FLTNUM = ? ");
            this.setParameter(++index, flightCarrierIdr);
            this.setParameter(++index, flightNumber);
        }
        int caridr = carrierIdr!=null ? Integer.parseInt(carrierIdr.toString()):0;
        if(caridr!=0){
            filterQuery .append(" AND RTG.FLTCARIDR = ?  ");
            this.setParameter(++index, caridr);
        }
        queryBuilder.append(filterQuery);

        return queryBuilder.toString();
    }

}
