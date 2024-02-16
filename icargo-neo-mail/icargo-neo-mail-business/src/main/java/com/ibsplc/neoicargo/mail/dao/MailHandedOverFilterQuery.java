package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailHandedOverFilterVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MailHandedOverFilterQuery extends NativeQuery {
    private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
    private MailHandedOverFilterVO mailHandedOverFilterVO;
    private String flightBaseQuery;
    private String carrierBaseQuery;
    private static final  String DATE_FORMAT ="yyyyMMdd";

    public MailHandedOverFilterQuery(MailHandedOverFilterVO	mailHandedOverFilterVO,String flightBaseQuery,String carrierBaseQuery)
             {
        //super();
        super(PersistenceController.getEntityManager().currentSession());
        this.mailHandedOverFilterVO=mailHandedOverFilterVO;
        this.flightBaseQuery=flightBaseQuery;
        this.carrierBaseQuery=carrierBaseQuery;
    }

    @Override
    public String getNativeQuery() {
        log.entering("MailHandedOverFilterQuery", "getNativeQuery");

        int index=0;
        String companyCode=mailHandedOverFilterVO.getCompanyCode();
        //String scanPort=mailHandedOverFilterVO.getScanPort();
        Integer flightCarrierId=mailHandedOverFilterVO.getFlightCarrierId();
        String flightNumber=mailHandedOverFilterVO.getFlightNumber();
        Integer carrierId=mailHandedOverFilterVO.getCarrierId();
        ZonedDateTime fromDate=mailHandedOverFilterVO.getFromDate();
        ZonedDateTime toDate=mailHandedOverFilterVO.getToDate();
        StringBuilder queryBuilder = new StringBuilder();

        if((carrierId != null && carrierId >MailConstantsVO.ZERO)
                && (flightCarrierId==null || flightCarrierId == MailConstantsVO.ZERO)
                && (flightNumber==null || flightNumber.length() == MailConstantsVO.ZERO )){

            queryBuilder = new StringBuilder(carrierBaseQuery);

            //queryBuilder.append(" AND HIS.FLTSEQNUM = 0 ");
            if(carrierId != null && carrierId >0){
                queryBuilder.append(" AND HIS.FLTCARIDR = ? ");
                this.setParameter(++index, carrierId);
            }
            if(companyCode != null && companyCode.length() >0){
                queryBuilder.append(" AND SEG.CMPCOD = ? ");
                this.setParameter(++index, companyCode);
            }
            queryBuilder.append(" AND FLTMST.FLTSTA NOT IN ('TBA','TBC','CAN') ");
//			if(scanPort != null && scanPort.length() >0){
//				queryBuilder.append(" AND HIS.SCNPRT = ? ");
//				this.setParameter(++index, scanPort);
//			}
            if(fromDate != null){
                queryBuilder.append("  AND to_number(to_char(his.scndat, 'YYYYMMDD')) >= ? ");

                this.setParameter(++index,Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            if(toDate != null){
                queryBuilder.append(" AND to_number(to_char(his.scndat, 'YYYYMMDD')) <= ? ");

                this.setParameter(++index,Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }

        }else if(((flightCarrierId != null && flightCarrierId > MailConstantsVO.ZERO)
                && (flightNumber!=null && flightNumber.length() > MailConstantsVO.ZERO)
                && (carrierId==null || carrierId == MailConstantsVO.ZERO ))
                ||((flightCarrierId != null && flightCarrierId > MailConstantsVO.ZERO)
                && (flightNumber!=null && flightNumber.length() > MailConstantsVO.ZERO)
                && (carrierId!=null && carrierId > MailConstantsVO.ZERO ))){

            queryBuilder = new StringBuilder(flightBaseQuery);
            if(flightCarrierId != null && flightCarrierId >0){
                queryBuilder.append(" AND SEG.FLTCARIDR = ? ");
                this.setParameter(++index, flightCarrierId);
            }
            if(carrierId != null && carrierId >0){
                queryBuilder.append(" AND SEG.FLTCARIDR = ? ");
                this.setParameter(++index, carrierId);
            }
            if(flightNumber != null && flightNumber.length() >0){
                queryBuilder.append(" AND SEG.FLTNUM = ? ");
                this.setParameter(++index, flightNumber);
            }
            if(companyCode != null && companyCode.length() >0){
                queryBuilder.append(" AND seg.CMPCOD = ? ");
                this.setParameter(++index, companyCode);
            }
//			if(scanPort != null && scanPort.length() >0){
//				queryBuilder.append(" AND SEG.SCNPRT = ? ");
//				this.setParameter(++index, scanPort);
//			}
            if(fromDate != null){
                queryBuilder.append("  AND to_number(to_char(his.scndat, 'YYYYMMDD')) >= ? ");

                this.setParameter(++index,Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            if(toDate != null){
                queryBuilder.append(" AND to_number(to_char(his.scndat, 'YYYYMMDD')) <= ? ");

                this.setParameter(++index,Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
        }else{
            queryBuilder.append(" ( ");
            queryBuilder.append(flightBaseQuery);
            if(flightCarrierId != null && flightCarrierId >0){
                queryBuilder.append(" AND SEG.FLTCARIDR = ? ");
                this.setParameter(++index, flightCarrierId);
            }
            if(flightNumber != null && flightNumber.length() >0){
                queryBuilder.append(" AND SEG.FLTNUM = ? ");
                this.setParameter(++index, flightNumber);
            }
            if(companyCode != null && companyCode.length() >0){
                queryBuilder.append(" AND SEG.CMPCOD = ? ");
                this.setParameter(++index, companyCode);
            }
//			if(scanPort != null && scanPort.length() >0){
//				queryBuilder.append(" AND SEG.SCNPRT = ? ");
//				this.setParameter(++index, scanPort);
//			}
            if(fromDate != null){
                queryBuilder.append("  AND to_number(to_char(his.scndat, 'YYYYMMDD')) >= ? ");

                this.setParameter(++index,Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            if(toDate != null){
                queryBuilder.append(" AND to_number(to_char(his.scndat, 'YYYYMMDD')) <= ? ");

                this.setParameter(++index,Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            queryBuilder.append(" ) ");
            queryBuilder.append(" UNION ALL ");
            queryBuilder.append(" ( ");
            queryBuilder.append(carrierBaseQuery);
            //Modified as part of Bug ICRD-96791 by A-5526 starts
            queryBuilder.append(" AND HIS.FLTSEQNUM <= 0 ");
            queryBuilder.append(" AND HIS.FLTCARIDR > 0 ");
            //Modified as part of Bug ICRD-96791 by A-5526 ends
            if(carrierId != null && carrierId >0){
                queryBuilder.append(" AND HIS.FLTCARIDR = ? ");
                this.setParameter(++index, carrierId);
            }
            if(companyCode != null && companyCode.length() >0){
                queryBuilder.append(" AND SEG.CMPCOD = ? ");
                this.setParameter(++index, companyCode);
            }
//			if(scanPort != null && scanPort.length() >0){
//				queryBuilder.append(" AND HIS.SCNPRT = ? ");
//				this.setParameter(++index, scanPort);
//			}
            if(fromDate != null){
                queryBuilder.append("  AND to_number(to_char(his.scndat, 'YYYYMMDD')) >= ? ");

                this.setParameter(++index,Integer.parseInt(fromDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            if(toDate != null){
                queryBuilder.append(" AND to_number(to_char(his.scndat, 'YYYYMMDD')) <= ? ");

                this.setParameter(++index,Integer.parseInt(toDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT)).substring(0, 8)));
            }
            queryBuilder.append(" ) ");
        }

        return queryBuilder.toString();
    }

}
