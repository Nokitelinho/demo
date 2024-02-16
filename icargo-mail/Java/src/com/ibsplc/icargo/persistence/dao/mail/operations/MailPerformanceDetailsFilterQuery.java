package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailPerformanceDetailsFilterQuery extends NativeQuery {

	 private StringBuilder baseQuery;
	private MailMonitorFilterVO filterVO;
	 private int pageSize;
	 private String type;
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String FIND_SERVICE_FAILURE_DETAILS="mail.operations.findServiceFailureDetails";
	//private static final String FIND_STATION_ONTIME_DETAILS="mail.operations.findStationOntimeDetails";
	private static final String FIND_FORCE_MAJEURE_DETAILS="mail.operations.findForceMajeureMailbagCountDetails";
	
	/**
	 * @throws SystemException
	 */
	public MailPerformanceDetailsFilterQuery(int pageSize, MailbagMapper mapper,MailMonitorFilterVO filterVO,String type,StringBuilder baseQuery) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
		this.pageSize = pageSize;
		this.type=type;
	}
	
	/**
	 * @throws SystemException
	 */
	
	  public String getNativeQuery()
	  {
			log.entering("MailPerformanceMonitorFilterQuery", "Inside the NativeQuery");
			int index =0;
			StringBuilder stringBuilder = new StringBuilder();
		    
			stringBuilder.append(this.baseQuery);
			
		    
		    LocalDate fromDate = filterVO.getFromDate();
			LocalDate toDate = filterVO.getToDate();
			String station = filterVO.getStation();
		    String paCode  =filterVO.getPaCode();
		    String servicelevel = filterVO.getServiceLevel();
		    stringBuilder.append("AND MAL.CMPCOD = ?");
		    setParameter(++index, filterVO.getCompanyCode());	
		    stringBuilder.append(" AND  MAL.ORGCOD = ?");
		    setParameter(++index, filterVO.getStation());	
		    if(fromDate!=null)
			{
		      stringBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.DSPDAT,'YYYYMMDD')) >= ? ");
		      setParameter(++index, Integer.valueOf(Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			}
			
			if(toDate!=null)
			{
		      stringBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.DSPDAT,'YYYYMMDD')) <= ?  ");
		      setParameter(++index, Integer.valueOf(Integer.parseInt(toDate.toStringFormat("yyyyMMdd").substring(0, 8))));
			
		    }
			if(servicelevel!=null)
			{
				stringBuilder.append(" AND MALSRVLVL = ? ");
				setParameter(++index, servicelevel);		
			
			}
			
			if(paCode!=null)
			{
				stringBuilder.append(" AND MAL.POACOD = ? ");
				setParameter(++index, paCode);		
			
			}
				stringBuilder.append(" ) MST ");
		    if (this.type.equals("SERVICE_FAILURE")) {
		    	stringBuilder.append("where MISORGSCAN = 'Y' or  MISDLVSCAN = 'Y'");
			}
		    return stringBuilder.toString();
		}

}
