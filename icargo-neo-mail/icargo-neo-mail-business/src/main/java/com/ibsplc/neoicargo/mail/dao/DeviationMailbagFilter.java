package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagEnquiryFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.persistence.query.Query;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DeviationMailbagFilter extends PageableNativeQuery<MailbagVO> {
	@Autowired
	private ContextUtil contextUtil;
	private String baseQuery;
	private Query query1;
	private Query query2;
	private MailbagEnquiryFilterVO mailbagEnquiryFilterVO;

	public DeviationMailbagFilter(DeviationMailbagMapper deviationMailbagMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), -1, deviationMailbagMapper, PersistenceController.getEntityManager().currentSession());
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public DeviationMailbagFilter(MailOperationsSqlDAO.DeviationMailBagGroupMapper deviationMailBagGroupMapper,
                                  MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), -1, deviationMailBagGroupMapper,PersistenceController.getEntityManager().currentSession());
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public DeviationMailbagFilter(OutboundCarditSummaryMapper outboundCarditSummaryMapper,
			MailbagEnquiryFilterVO mailbagEnquiryFilterVO, StringBuilder rankQuery, Query query1, Query query2) {
		super(mailbagEnquiryFilterVO.getTotalRecords(), outboundCarditSummaryMapper,PersistenceController.getEntityManager().currentSession());
		this.baseQuery = rankQuery.toString();
		this.query1 = query1;
		this.query2 = query2;
		this.mailbagEnquiryFilterVO = mailbagEnquiryFilterVO;
	}

	public String getNativeQuery() {
		LoginProfile logonAttributes = null;
		try {
			//logonAttributes = ContextUtil.getSecurityContext().getLogonAttributesVO();
			ContextUtil contextUtil = ContextUtil.getInstance();
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {

		}
		ZonedDateTime scanFromDate = mailbagEnquiryFilterVO.getScanFromDate();
		ZonedDateTime scanToDate = mailbagEnquiryFilterVO.getScanToDate();
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
		setParameter(++index, Integer.parseInt(scanFromDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));
		setParameter(++index, Integer.parseInt(scanToDate.format(DateTimeFormatter.ofPattern(MailConstantsVO.YYYY_MM_DD)).substring(0, 8)));

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
		ZonedDateTime reqDeliveryTime=mailbagEnquiryFilterVO.getReqDeliveryTime();
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
			String rqdDlvTime=reqDeliveryTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
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
		builder = builder.replace(0, builder.length(), query);
		return index;
	}
}
