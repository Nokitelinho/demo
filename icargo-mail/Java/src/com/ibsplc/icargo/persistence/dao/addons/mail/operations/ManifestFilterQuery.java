package com.ibsplc.icargo.persistence.dao.addons.mail.operations;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;

public class ManifestFilterQuery extends PageableNativeQuery<MailBookingDetailVO>{
	
	private MailBookingFilterVO mailBookingFilterVO;
	private String baseQuery;
	
	public ManifestFilterQuery(ManifestMapper mapper,
			MailBookingFilterVO mailBookingFilterVO, String baseQuery) throws SystemException {
		super(mailBookingFilterVO.getPageSize(),mailBookingFilterVO.getTotalRecordsCount(), mapper);
		this.mailBookingFilterVO = mailBookingFilterVO;
		this.baseQuery = baseQuery;
	}
	
	@Override
	public String getNativeQuery() {
		
		int index = 0;
		setParameter(++index, mailBookingFilterVO.getManifestDateFrom().toDisplayDateOnlyFormat());
		setParameter(++index, mailBookingFilterVO.getManifestDateTo().toDisplayDateOnlyFormat());
		setParameter(++index, mailBookingFilterVO.getPou());
		
		StringBuilder queryString = (new StringBuilder()).append(baseQuery);
		
		if(!StringUtils.isBlank(mailBookingFilterVO.getMailScc())){
			 validateMailScc(queryString);     
		}else {  
			validateMailSccFromSyspar(queryString);
		}
		
		if(!StringUtils.isBlank(mailBookingFilterVO.getMasterDocumentNumber())){
			queryString.append("AND SHPMST.MSTDOCNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getMasterDocumentNumber());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getShipmentPrefix())){
			queryString.append("AND SHPMST.SHPPFX = ? ");
			setParameter(++index, mailBookingFilterVO.getShipmentPrefix());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getFlightNumber())){
			queryString.append("AND SEGSHP.FLTNUM = ? ");
			setParameter(++index, mailBookingFilterVO.getFlightNumber());
		}
		if(mailBookingFilterVO.getFlightDate()!=null){
			queryString.append("AND TO_NUMBER(TO_CHAR(TRUNC(OPRLEG.STA), 'YYYYMMDD')) = TO_NUMBER(TO_CHAR(TO_DATE(?), 'YYYYMMDD')) ");
			setParameter(++index, mailBookingFilterVO.getFlightDate().toDisplayDateOnlyFormat());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getOrigin())){
			queryString.append("AND SHPMST.ORGCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getOrigin());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getDestination())){
			queryString.append("AND SHPMST.DSTCOD = ? ");
			setParameter(++index, mailBookingFilterVO.getDestination());
		}
		if(!StringUtils.isBlank(mailBookingFilterVO.getPol())){
			queryString.append("AND SEG.POL = ? ");
			setParameter(++index, mailBookingFilterVO.getPol());
		}
		queryString.append(MailConstantsVO.MAIL_OPERATIONS_SUFFIX_QUERY);
		return queryString.toString();
	}
	
	private StringBuilder validateMailScc(StringBuilder queryString) {
		if (mailBookingFilterVO.getMailScc().contains(",")) {
			String[] sccs = mailBookingFilterVO.getMailScc().split(",");
			int count = 0;
			if (sccs != null){
				count = sccs.length;
			}	

			queryString.append("AND ( ");
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < count; i++) {
				sb.append("SHPMST.SCCCOD like ");
				sb.append("'%");
				sb.append(sccs[i]);
				sb.append("%'");
				if (count > i + 1) {
					sb.append(" or ");
				}
			}
			sb.append(")");
			queryString.append(sb.toString());
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append("AND SHPMST.SCCCOD like '%");
			sb.append(mailBookingFilterVO.getMailScc());
			sb.append("%'");
			queryString.append(sb.toString());
		}
		return queryString;
	}

	private StringBuilder validateMailSccFromSyspar(StringBuilder queryString) {
		if (mailBookingFilterVO.getMailSccFromSyspar().contains(",")){
			
			String[] sccs = mailBookingFilterVO.getMailSccFromSyspar().split(","); 
			int count=0;
			if (sccs!=null ){
				count=sccs.length;  
			}
			queryString.append("AND ( ");        
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<count;i++){
				sb.append("SHPMST.SCCCOD like ");
				sb.append("'%");
				sb.append(sccs[i]); 
				sb.append("%'"); 
				if(count>i+1){
					sb.append(" or ");       
				}     
			}
			sb.append(")");      
			queryString.append(sb.toString());     
			}
		else { 
			StringBuilder sb = new StringBuilder();
			sb.append("AND SHPMST.SCCCOD like '%");  
			sb.append(mailBookingFilterVO.getMailSccFromSyspar()); 
			sb.append("%'");      
			queryString.append(sb.toString());
			}
		return queryString;
	}
}
