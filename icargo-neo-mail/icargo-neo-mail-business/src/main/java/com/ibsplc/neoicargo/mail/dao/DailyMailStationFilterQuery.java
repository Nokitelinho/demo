package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.DailyMailStationFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import lombok.extern.slf4j.Slf4j;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


@Slf4j


public class DailyMailStationFilterQuery extends NativeQuery {

    private String baseQuery;
    private DailyMailStationFilterVO filterVO;

    /**
     * @throws SystemException
     * @param baseQuery
     * @param filterVO
     */
    public DailyMailStationFilterQuery(String baseQuery,
                                       DailyMailStationFilterVO filterVO) throws SystemException {
        super(PersistenceController.getEntityManager().currentSession());
        this.baseQuery = baseQuery;
        this.filterVO = filterVO;
    }
    /**
     * TODO Purpose
     * Mar 10, 2008, A-3251
     * @return
     * */
    @Override
    public String getNativeQuery() {
        log.debug("DailyMailStationFilterQuery", "getNativeQuery");
        int index = 0;
        StringBuilder qry = new StringBuilder(baseQuery);
        qry = new StringBuilder(" SELECT MAX(CARCOD) CARCOD, MAX(FLTNUM) FLTNUM, MAX(ULDNUM) ULDNUM, MAX(DEST) DEST, MAX(BAGWGT) BAGWGT, MAX(TOTWGT) TOTWGT, MAX(BAGCNT) BAGCNT, MAX(RMK) RMK FROM  ( ").append(qry);
        //LocalDate flightDate=filterVO.getFilghtDate();
        //Date flightDat=null;
        ZonedDateTime flightFromDate=filterVO.getFlightFromDate();
        ZonedDateTime flightToDate=filterVO.getFlightToDate();
        ZonedDateTime flightFromDat=null;
        ZonedDateTime flightToDat=null;
        if (flightFromDate != null) {
            flightFromDat=flightFromDate;
        }
        if (flightToDate != null) {
            flightToDat=flightToDate;
        }

        String fromDateString = String.valueOf(flightFromDat);
        String toDateString = String.valueOf(flightToDat);
        setParameter(++index, filterVO.getCompanyCode());
        setParameter(++index, flightFromDat.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
        setParameter(++index, flightToDat.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
        setParameter(++index,filterVO.getOrigin());
        if((filterVO.getDestination()!=null)&&(!"".equals(filterVO.getDestination().trim()))){
            //Destination not Null So attaching destination to filter
            qry.append(" AND SUBSTR (MST.DSTEXGOFC, 3, 3) = ? " ) ;
            setParameter(++index, filterVO.getDestination());
        }

        if((filterVO.getFlightNumber()!=null)&&(!"".equals(filterVO.getFlightNumber().trim()))){
            //FlightNumber not Null So attaching Flight specific details to filter
            qry.append(" AND FLTSEG.FLTNUM = ? " ) ;
            qry.append(" AND FLTSEG.FLTCARIDR = ? " ) ;
            setParameter(++index, filterVO.getFlightNumber());
            setParameter(++index, filterVO.getFlightCarrireID());
        }
        /*
         *
         * Added By Karthick V as the part of the ANZ Mail Tracking Bug Fix
         *
         *
         */
        qry.append("  GROUP BY CONMST.FLTCARIDR,FLTSEG.FLTNUM,FLTSEG.FLTSEQNUM,ULDSEG.ULDNUM,CONMST.DSTCOD,ULDSEG.RMK,TYPMST.TAREWGT,ULDMST.TARWGT ");

        /*
         *
         * Added By Karthick V as the part of the ANZ Mail Tracking Bug Fix
         *
         *
         */
/* 	        qry.append(" UNION ALL  SELECT   (SELECT TWOAPHCOD FROM SHRARLMST WHERE CMPCOD = FLTSEG.CMPCOD AND ARLIDR = FLTSEG.FLTCARIDR) CARCOD,FLTSEG.FLTNUM FLTNUM, ");
 					qry.append(" CONSEG.CONNUM ULDNUM, ");
 							qry.append("SUBSTR (MST.DSTEXGOFC, 3, 3) DEST,");
 							qry.append(" CONSEG.ACPWGT BAGWGT,");
 							//qry.append("(  NVL (   TYPMST.TAREWGT, 0)");
 							qry.append(" CONSEG.ACPWGT");
 							qry.append(" TOTWGT,  CONSEG.ACPBAG BAGCNT,");
 							qry.append(" ULDSEG.RMK RMK  FROM MALFLT ASGFLT INNER JOIN MALFLTSEG FLTSEG ON ASGFLT.CMPCOD = FLTSEG.CMPCOD AND ASGFLT.FLTCARIDR = FLTSEG.FLTCARIDR AND ASGFLT.FLTNUM = FLTSEG.FLTNUM AND ASGFLT.FLTSEQNUM = FLTSEG.FLTSEQNUM INNER JOIN MALULDSEG ULDSEG ON FLTSEG.CMPCOD = ULDSEG.CMPCOD AND FLTSEG.FLTCARIDR = ULDSEG.FLTCARIDR AND FLTSEG.FLTNUM = ULDSEG.FLTNUM AND FLTSEG.FLTSEQNUM = ULDSEG.FLTSEQNUM AND FLTSEG.SEGSERNUM = ULDSEG.SEGSERNUM  INNER JOIN MALULDSEGDTL CONSEG ON ULDSEG.CMPCOD= CONSEG.CMPCOD AND ULDSEG.FLTCARIDR=CONSEG.FLTCARIDR AND ULDSEG.FLTNUM=CONSEG.FLTNUM AND ULDSEG.FLTSEQNUM=CONSEG.FLTSEQNUM AND ULDSEG.ULDNUM=CONSEG.ULDNUM AND ULDSEG.SEGSERNUM=CONSEG.SEGSERNUM LEFT OUTER JOIN  MALMST MST ON CONSEG.CMPCOD=MST.CMPCOD AND CONSEG.MALSEQNUM=MST.MALSEQNUM WHERE ULDSEG.CMPCOD = ? AND ASGFLT.FLTDAT BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd') AND ASGFLT.ARPCOD = ? AND CONSEG.ACPWGT<>0 AND CONSEG.ACPBAG<>0");
 			setParameter(++index, filterVO.getCompanyCode());
 			//setParameter(++index, fromDateString);
 			setParameter(++index, fromDateString);
 			setParameter(++index, toDateString);
 			setParameter(++index,filterVO.getOrigin());
 			if((filterVO.getDestination()!=null)&&(!"".equals(filterVO.getDestination().trim()))){
 	 			//Destination not Null So attaching destination to filter to query part 2
 	 			qry.append(" AND  SUBSTR (MST.DSTEXGOFC, 3, 3) = ? " ) ;
 	 		    setParameter(++index, filterVO.getDestination());
 	 			}

 			if((filterVO.getFlightNumber()!=null)&&(!"".equals(filterVO.getFlightNumber().trim()))){
 	 			//FlightNumber not Null So attaching Flight specific details to query filter to part2
 	 			qry.append(" AND FLTSEG.FLTNUM = ? " ) ;
 	 			qry.append(" AND FLTSEG.FLTCARIDR = ? " ) ;
 	 			setParameter(++index, filterVO.getFlightNumber());
 	 			setParameter(++index, filterVO.getFlightCarrireID());
 	 			}*/


        /*
         *
         * Added By Karthick V as the part of the ANZ Mail Tracking Bug Fix
         *
         *
         */
        //qry.append(" GROUP BY CONMST.FLTCARCOD,FLTSEG.FLTNUM,CONSEG.CONNUM,SUBSTR (CONSEG.DSTEXGOFC, 3, 3),ULDSEG.RMK,TYPMST.TAREWGT ");
        //Added as part of ICRD-147983 starts
        qry.append(" UNION ALL  SELECT MAX(INBFLT.FLTCARCOD) CARCOD,FLTSEG.FLTNUM FLTNUM,FLTSEG.FLTSEQNUM FLTSEQNUM,ULDSEG.ULDNUM ULDNUM,CONMST.DSTCOD DEST,SUM ( DSNSEG.RCVWGT) BAGWGT,( COALESCE (COALESCE(ULDMST.TARWGT, TYPMST.TAREWGT), 0) + SUM ( DSNSEG.RCVWGT) ) TOTWGT,     SUM ( DSNSEG.RCVBAG)BAGCNT,     ULDSEG.RMK RMK   FROM MALFLT INBFLT   LEFT OUTER JOIN MALFLTSEG FLTSEG   ON INBFLT.CMPCOD     = FLTSEG.CMPCOD   AND INBFLT.FLTCARIDR = FLTSEG.FLTCARIDR   AND INBFLT.FLTNUM    = FLTSEG.FLTNUM   AND INBFLT.FLTSEQNUM = FLTSEG.FLTSEQNUM   AND INBFLT.ARPCOD = FLTSEG.POU   LEFT OUTER JOIN FLTOPRMST FLTMST   ON FLTSEG.CMPCOD       =FLTMST.CMPCOD   AND FLTSEG.FLTSEQNUM   =FLTMST.FLTSEQNUM   AND FLTSEG.FLTNUM      =FLTMST.FLTNUM   AND FLTSEG.FLTCARIDR   =FLTMST.FLTCARIDR   AND FLTMST.FLTSTA NOT IN ('TBA','TBC','CAN')   INNER JOIN MALULDSEG ULDSEG   ON FLTSEG.CMPCOD     = ULDSEG.CMPCOD   AND FLTSEG.FLTCARIDR = ULDSEG.FLTCARIDR   AND FLTSEG.FLTNUM    = ULDSEG.FLTNUM   AND FLTSEG.FLTSEQNUM = ULDSEG.FLTSEQNUM   AND FLTSEG.SEGSERNUM = ULDSEG.SEGSERNUM   LEFT OUTER JOIN MALULDSEGDTL DSNSEG   ON ULDSEG.CMPCOD     = DSNSEG.CMPCOD   AND ULDSEG.FLTCARIDR = DSNSEG.FLTCARIDR   AND ULDSEG.FLTNUM    = DSNSEG.FLTNUM   AND ULDSEG.FLTSEQNUM = DSNSEG.FLTSEQNUM   AND ULDSEG.SEGSERNUM = DSNSEG.SEGSERNUM   AND ULDSEG.ULDNUM    = DSNSEG.ULDNUM   LEFT OUTER JOIN MALMST MST   ON DSNSEG.CMPCOD    =MST.CMPCOD   AND DSNSEG.MALSEQNUM=MST.MALSEQNUM   INNER JOIN MALFLTCON CONMST   ON DSNSEG.CMPCOD     = CONMST.CMPCOD   AND DSNSEG.FLTCARIDR = CONMST.FLTCARIDR   AND DSNSEG.FLTNUM    = CONMST.FLTNUM   AND DSNSEG.FLTSEQNUM = CONMST.FLTSEQNUM   AND DSNSEG.CONNUM    = CONMST.CONNUM   LEFT OUTER JOIN SHRULDTYPMST TYPMST   ON ULDSEG.CMPCOD                   = TYPMST.CMPCOD   AND CONMST.CONTYP          = 'U' AND (SUBSTR (ULDSEG.ULDNUM, 0, 3)) = TYPMST.ULDTYPCOD   LEFT OUTER  JOIN ULDMST ULDMST   ON MST.CMPCOD                   = ULDMST.CMPCOD    AND MST.CONNUM     = ULDMST.ULDNUM    AND MST.CONTYP ='U'   WHERE ULDSEG.CMPCOD                = ? AND INBFLT.FLTDAT BETWEEN TO_DATE(?, 'yyyy-MM-dd') AND TO_DATE(?, 'yyyy-MM-dd') AND INBFLT.ARPCOD=? AND DSNSEG.RCVWGT<>0 AND DSNSEG.RCVBAG<>0");
        setParameter(++index, filterVO.getCompanyCode());
        //setParameter(++index, fromDateString);
        setParameter(++index, flightFromDat.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
        setParameter(++index, flightToDat.format(DateTimeFormatter.ofPattern(MailConstantsVO.DATE_TIME_FORMAT_WITH_HYPHENS)));
        setParameter(++index,filterVO.getOrigin());
        if((filterVO.getDestination()!=null)&&(!"".equals(filterVO.getDestination().trim()))){
            //Destination not Null So attaching destination to filter to query part 3
            qry.append(" AND  SUBSTR (MST.DSTEXGOFC, 3, 3) = ? " ) ;
            setParameter(++index, filterVO.getDestination());
        }
        if((filterVO.getFlightNumber()!=null)&&(!"".equals(filterVO.getFlightNumber().trim()))){
            //FlightNumber not Null So attaching Flight specific details to query filter to part3
            qry.append(" AND FLTSEG.FLTNUM = ? " ) ;
            qry.append(" AND FLTSEG.FLTCARIDR = ? " ) ;
            setParameter(++index, filterVO.getFlightNumber());
            setParameter(++index, filterVO.getFlightCarrireID());
        }
        qry.append(" GROUP BY INBFLT.FLTCARIDR,FLTSEG.FLTNUM,FLTSEG.FLTSEQNUM,ULDSEG.ULDNUM,CONMST.DSTCOD,ULDSEG.RMK,TYPMST.TAREWGT,ULDMST.TARWGT");
        qry.append("  ) MST GROUP BY CARCOD, FLTNUM, ULDNUM ");
        //Added as part of ICRD-147983 ends
        log.debug( "!!!!!######-->Query Generated After All Filter",
                qry);
        log.debug("DailyMailStationFilterQuery", "getNativeQuery");
        return qry.toString();
    }
}
