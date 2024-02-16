package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailTransitVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

public class MailTransitFilterQuery extends  PageableNativeQuery<MailTransitVO>{
	
	 String baseQuery;
	 private MailTransitFilterVO mailTransitFilterVO;

	 private boolean isFlightFilterPresent(MailTransitFilterVO mailTransitFilterVO) {
		 return (mailTransitFilterVO.getFlightNumber() != null && mailTransitFilterVO.getFlightNumber().trim().length() > 0 && mailTransitFilterVO.getFlightDate() != null); 
			
		}
	public MailTransitFilterQuery(MailTransitMapper mailTransitMapper ,MailTransitFilterVO mailTransitFilterVO, String baseQuery) throws SystemException {
		 
		 super(mailTransitFilterVO.getPageSize(),mailTransitFilterVO.getTotalRecordsCount(),mailTransitMapper);
		 this.baseQuery = baseQuery;
	     this.mailTransitFilterVO = mailTransitFilterVO;
		 
	}
	
	@Override
	 public String getNativeQuery() {
	        int index=0;
	        StringBuilder builder = new StringBuilder(baseQuery);
	        setParameter(++index, mailTransitFilterVO.getAirportCode());
	        
	        
	        if(mailTransitFilterVO.getFlightNumber()!=null && mailTransitFilterVO.getFlightNumber().trim().length() > 0) {
				builder.append("AND fltleg.fltnum =  ?");
				setParameter(++index,mailTransitFilterVO.getFlightNumber());
				}		
	        
	        if(null!=mailTransitFilterVO.getFlightDate()){
				builder.append(" AND to_number(TO_CHAR(fltleg.sta, 'YYYYMMDD')) = ? ");
			    setParameter(++index, Integer.parseInt(mailTransitFilterVO.getFlightDate().toStringFormat(MailConstantsVO.YYYY_MM_DD).substring(0, 8)));
			}
	        if(null!=mailTransitFilterVO.getFlightFromDate()&& null!=mailTransitFilterVO.getFlightToDate() && !isFlightFilterPresent(mailTransitFilterVO)) {
	        	builder.append(" AND FLTLEG.STA >= ? ");
				setParameter(++index, mailTransitFilterVO.getFlightFromDate());			
			
			
				builder.append(" AND FLTLEG.STA <= ? ");
				setParameter(++index, mailTransitFilterVO.getFlightToDate());		
	        }
	        else {
				if(null!=mailTransitFilterVO.getFlightFromDate()&& !isFlightFilterPresent(mailTransitFilterVO))
				{
	        	builder.append(" AND FLTLEG.STA >= ? ");
				setParameter(++index, mailTransitFilterVO.getFlightFromDate());			
				}
				if(null!=mailTransitFilterVO.getFlightToDate()&& !isFlightFilterPresent(mailTransitFilterVO))
				{
				builder.append(" AND FLTLEG.STA <= ? ");
				setParameter(++index, mailTransitFilterVO.getFlightToDate());		
				}
			}
	        builder.append(") tmp group by fltcarcod ,dstcod,cmpcod ");
	        builder.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);

	        return builder.toString();
	    }

}
