package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsSqlDAO.DeviationMailBagGroupMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

public class DeviationMailbagFilter extends PageableNativeQuery<MailbagVO>{

	private String baseQuery;
	private Query query1;
	private Query query2;
	
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;
	

	public DeviationMailbagFilter(DeviationMailbagMapper deviationMailbagMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),-1,  deviationMailbagMapper);
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}
	
	public DeviationMailbagFilter(DeviationMailBagGroupMapper deviationMailBagGroupMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(),-1,  deviationMailBagGroupMapper);
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public DeviationMailbagFilter(OutboundCarditSummaryMapper outboundCarditSummaryMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) throws SystemException {
		super(mailbagEnquiryFilterVO.getTotalRecords(), outboundCarditSummaryMapper);
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public String getNativeQuery() {
		LogonAttributes logonAttributes = null;
		try {
			logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		} catch (SystemException e) {
			
		}
		LocalDate scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		LocalDate scanToDate = mailbagEnquiryFilterVO.getScanToDate();
		//status E -Outbound Missed , I- Inbound Unplanned
		boolean statusFilterAplied = mailbagEnquiryFilterVO.getCurrentStatus()==null 
				|| mailbagEnquiryFilterVO.getCurrentStatus().trim().length()==0 ? false :true;
		StringBuilder builder = new StringBuilder(baseQuery);
		int index = 0;
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());		
		setParameter(++index, logonAttributes.getCompanyCode());
		setParameter(++index, Integer.parseInt(scanFromDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		setParameter(++index, Integer.parseInt(scanToDate.toStringFormat("yyyyMMdd").substring(0, 8)));
		
		index = constructQuery(builder, mailbagEnquiryFilterVO, index,null);
		setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
		if(!statusFilterAplied || "E".equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			//query 1 here  outbound scan missed
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			/*
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());	
			setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, logonAttributes.getOwnAirlineIdentifier());	
			//commented as part of IASCB-61181
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			//setParameter(++index, logonAttributes.getOwnAirlineIdentifier());	
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport()); */
			
		}
		if(!statusFilterAplied || "I".equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			//query 2 here inbound unplanned
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			/*
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, logonAttributes.getOwnAirlineIdentifier());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport());
			setParameter(++index, mailbagEnquiryFilterVO.getUpliftAirport()); */
		}
		
		builder.append(" ");
		
		if(!statusFilterAplied) {
			builder.append(this.query1.toString());
			builder.append(" UNION ALL ");
			builder.append(this.query2.toString());
		}else if(statusFilterAplied && "E".equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			builder.append(this.query1.toString());
		} else if(statusFilterAplied && "I".equals(mailbagEnquiryFilterVO.getCurrentStatus())) {
			builder.append(this.query2.toString());
		}
		builder.append(" ");
		return builder.toString();
		
	}
	
	private int constructQuery(StringBuilder builder,MailbagEnquiryFilterVO mailbagEnquiryFilterVO, int index, String queryNum) {
		String query  = builder .toString(); 
		StringBuilder addFilterQuery = new StringBuilder("");
		String mailbagId=mailbagEnquiryFilterVO.getMailbagId();
		String consigmentNumber=mailbagEnquiryFilterVO.getConsigmentNumber();
		String originAirportCode = mailbagEnquiryFilterVO.getOriginAirportCode();
		String destinationAirportCode =  mailbagEnquiryFilterVO.getDestinationAirportCode();
		String paoCode = mailbagEnquiryFilterVO.getPacode();
		LocalDate reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
		if (mailbagId != null && mailbagId.trim().length() > 0) {
			addFilterQuery.append(" AND MST.MALIDR= ? ");
			setParameter(++index, mailbagId);
			
		}
		if (consigmentNumber != null
				&& consigmentNumber.trim().length() > 0) {
			addFilterQuery.append("  AND MST.CSGDOCNUM= ? ");
			setParameter(++index, consigmentNumber);
		}
		if (originAirportCode != null && originAirportCode.trim().length() > 0) {
			addFilterQuery.append("  AND MST.ORGCOD= ? ");
			setParameter(++index, originAirportCode);
		}
		if (destinationAirportCode != null && destinationAirportCode.trim().length() > 0) {
			addFilterQuery.append("  AND MST.DSTCOD= ? ");
			setParameter(++index, destinationAirportCode);
		}
		if (paoCode != null && paoCode.trim().length() > 0) {
			addFilterQuery.append("  AND MST.POACOD= ? ");
			setParameter(++index, paoCode);
		}
		if(reqDeliveryTime != null) {
			String rqdDlvTime=reqDeliveryTime.toDisplayFormat("yyyyMMddHHmm");
			if(rqdDlvTime!=null){
				if(rqdDlvTime.contains("0000")){
					addFilterQuery.append(" AND TRUNC(MST.REQDLVTIM) = ?");
				}else{
					addFilterQuery.append(" AND MST.REQDLVTIM = ?");
				}
			setParameter(++index,reqDeliveryTime);
			}
		}
		query  = query.replace("#FILTERCONDITION#", addFilterQuery.toString());
		builder = builder.replace(0, builder.length()-1, query);
		return index;
	}
}
