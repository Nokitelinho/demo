package com.ibsplc.neoicargo.mail.dao;


import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.DamageMailFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.Timestamp;
@Slf4j

public class DamageMailReportFilterQuery  extends NativeQuery {
    private String baseQuery;
    private DamageMailFilterVO damageMailFilterVO;
    public DamageMailReportFilterQuery(DamageMailFilterVO damageMailFilterVO,
                                       String baseQuery) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.damageMailFilterVO = damageMailFilterVO;
        this.baseQuery = baseQuery;
    }

    public String getNativeQuery() {
        log.debug("INSIDE THE FILTER QUERY", "getNativeQuery()");
        String airport = damageMailFilterVO.getAirport().toUpperCase();
        String companyCode = damageMailFilterVO.getCompanyCode();
        Integer airlineId = damageMailFilterVO.getAirlineId();
        String damageCode = damageMailFilterVO.getDamageCode();
        ZonedDateTime fromdate = damageMailFilterVO.getFromDate();
        ZonedDateTime toDate = damageMailFilterVO.getToDate();
        //added by A-5844 for ICRD-67196 starts
        String flightCarrierCode=damageMailFilterVO.getFlightCarrierCode();
        String flightNumber=damageMailFilterVO.getFlightNumber();
        ZonedDateTime flightDate=damageMailFilterVO.getFlightDate();
        String flightOrigin=damageMailFilterVO.getFlightOrigin();
        String flightDestination=damageMailFilterVO.getFlightDestination();
        String gpaCode=damageMailFilterVO.getGpaCode();
        String originOE=damageMailFilterVO.getOriginOE();
        String destinationOE=damageMailFilterVO.getDestinationOE();
        String subClassGroup=damageMailFilterVO.getSubClassGroup();
        String subClassCode=damageMailFilterVO.getSubClassCode();
        StringBuilder builder = new StringBuilder(baseQuery);
        int index = 0;


        builder.append(" WHERE ");

        if (companyCode != null) {
            builder.append(" DMG.CMPCOD = ? ");
            this.setParameter(++index, companyCode);
        }

        if (fromdate != null) {
            builder.append(" AND TO_NUMBER(TO_CHAR(DMG.DMGDAT,'YYYYMMDD')) >= ? ");
            this.setParameter(++index, Integer.parseInt(fromdate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }
        if (toDate != null) {
            builder.append(" AND TO_NUMBER(TO_CHAR(DMG.DMGDAT,'YYYYMMDD')) <= ? ");
            this.setParameter(++index, Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
        }

        if (airport != null && airport.length() > 0) {
            builder.append(" AND DMG.ARPCOD = ? ");
            this.setParameter(++index, airport);
        }

        if (damageCode != null && damageCode.length() > 0) {
            builder.append(" AND DMG.DMGCOD = ? ");
            this.setParameter(++index, damageCode);
        }

        if (flightCarrierCode != null && flightCarrierCode.trim().length() > 0) {
            builder.append(" AND ARL.TWOAPHCOD = ? ");
            this.setParameter(++index, flightCarrierCode);
        }
        if (flightNumber != null && flightNumber.trim().length() > 0) {
            builder.append(" AND MST.FLTNUM = ? ");
            this.setParameter(++index, flightNumber);
        }
        if (flightDate != null ) {
            builder.append(" AND OPRMST.FLTDAT = ? ");
            this.setParameter(++index,  Timestamp.valueOf(damageMailFilterVO.getFlightDate().toLocalDateTime()));
        }

        if (flightOrigin != null && flightOrigin.trim().length() > 0) {
            builder.append(" AND OPRMST.FLTORG = ? ");
            this.setParameter(++index, flightOrigin);
        }
        if (flightDestination != null && flightDestination.trim().length() > 0) {
            builder.append(" AND OPRMST.FLTDST = ? ");
            this.setParameter(++index, flightDestination);
        }

        if (gpaCode != null && gpaCode.trim().length() > 0) {
            builder.append(" AND MST.POACOD = ? ");
            this.setParameter(++index, gpaCode);
        }
        if (originOE != null && originOE.trim().length() > 0) {
            builder.append(" AND MST.ORGEXGOFC = ? ");
            this.setParameter(++index, originOE);
        }
        if (destinationOE != null && destinationOE.trim().length() > 0) {
            builder.append(" AND MST.DSTEXGOFC = ? ");
            this.setParameter(++index, destinationOE);
        }
        if (subClassGroup != null && subClassGroup.trim().length() > 0) {
            builder.append(" AND CLSMST.SUBCLSGRP = ? ");
            this.setParameter(++index, subClassGroup);
        }
        if (subClassCode != null && subClassCode.trim().length() > 0) {
            builder.append(" AND CLSMST.SUBCLSCOD = ? ");
            this.setParameter(++index, subClassCode);
        }
        builder.append(" ORDER BY MALIDR ");
        return builder.toString();

    }


}
