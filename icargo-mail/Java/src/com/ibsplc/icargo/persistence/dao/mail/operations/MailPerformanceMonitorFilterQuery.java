package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailPerformanceMonitorFilterQuery  extends PageableNativeQuery<MailbagVO> {
	 private StringBuilder baseQuery;
	
	private MailMonitorFilterVO filterVO;
	private int pageSize;
	private String type;
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	public MailPerformanceMonitorFilterQuery(int pageSize, MailbagMapper mapper,MailMonitorFilterVO filterVO,String type,StringBuilder baseQuery) throws SystemException {
	    super(filterVO.getPageSize(),-1,mapper);
		this.baseQuery = baseQuery;
		this.filterVO = filterVO;
		this.pageSize = pageSize;
		this.type=type;
	}
	@Override
	public String getNativeQuery() {
		log.entering("MailPerformanceMonitorFilterQuery", "Inside the NativeQuery");
		int index =0;
		StringBuilder stringBuilder = new StringBuilder();
	    stringBuilder.append(this.baseQuery);
	    
	    
	    LocalDate fromDate = filterVO.getFromDate();
		LocalDate toDate = filterVO.getToDate();
		String station = filterVO.getStation();
	    String paCode  =filterVO.getPaCode();
	    String servicelevel = filterVO.getServiceLevel();
	    if (!this.type.equals("APPROVED_MAILBAGS")){
	    	stringBuilder.append(" AND (MAL.SCNWVDFLG = 'N' OR MAL.SCNWVDFLG IS NULL)");
	    }
	    stringBuilder.append(" AND MAL.CMPCOD = ?");
	    setParameter(++index, filterVO.getCompanyCode());	
	    stringBuilder.append(" AND  MAL.ORGCOD = ?");
	    setParameter(++index, filterVO.getStation());	
	    if(fromDate!=null)
		{
	      stringBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.DSPDAT,'YYYYMMDD')) >= ?");
	      setParameter(++index, Integer.valueOf(Integer.parseInt(fromDate.toStringFormat("yyyyMMdd").substring(0, 8))));
		}
		
		if(toDate!=null)
		{
	      stringBuilder.append(" AND TO_NUMBER(TO_CHAR(MAL.DSPDAT,'YYYYMMDD')) <= ? ");
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
	  //  stringBuilder.append(")");
	    if ((this.type.equals("MISSING_ORIGIN_SCAN")) || (this.type.equals("MISSING_DESTINATION_SCAN")) || (this.type.equals("MISSING_BOTH_SCAN"))) {
	      if (this.type.equals("MISSING_ORIGIN_SCAN")) {
	        stringBuilder.append("WHERE MISORGSCAN ='Y' AND  MISDLVSCAN='N'");
	      } else if (this.type.equals("MISSING_DESTINATION_SCAN")) {
	        stringBuilder.append("WHERE MISORGSCAN ='N' AND MISDLVSCAN='Y'");
	      } else {
	        stringBuilder.append("WHERE MISORGSCAN ='Y' AND MISDLVSCAN='Y'");
	      }
	    }
	    if ((this.type.equals("ON_TIME_MAILBAGS")) || (this.type.equals("DELAYED_MAILBAGS"))) {
	      if (this.type.equals("DELAYED_MAILBAGS")) {
	        stringBuilder.append("WHERE ONNTIMDLVFLG = 'N' ");
	      } else if (this.type.equals("ON_TIME_MAILBAGS")) {
	        stringBuilder.append("WHERE ONNTIMDLVFLG = 'Y' ");
	      }
	    }
	    if (this.type.equals("APPROVED_MAILBAGS") || this.type.equals("REJECTED_MAILBAGS") || this.type.equals("RAISED_MAILBAGS") ) {
	    	if (type.equals("APPROVED_MAILBAGS")) {
	    		stringBuilder.append(" WHERE FORMJRSTA='APR'");
			}
			else if (type.equals("REJECTED_MAILBAGS")) {
				stringBuilder.append(" WHERE FORMJRSTA='REJ' ");
			}
			else{
				stringBuilder.append(" WHERE FORMJRSTA='REQ' ");
			}
		}
	    return stringBuilder.toString();
	}

}
