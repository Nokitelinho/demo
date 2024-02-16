package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceStandardFilterVO;
import com.ibsplc.neoicargo.mailmasters.vo.MailServiceStandardVO;

/** 
 * @author A-8149
 */
public class ServiceStandardFilterQuery extends PageableNativeQuery<MailServiceStandardVO> {
	private MailServiceStandardFilterVO mailServiceStandardFilterVO;
	private String baseQuery;

	public ServiceStandardFilterQuery(int pageSize, int totalRecords,
			MailServiceStandardFilterVO mailServiceStandardFilterVO, String basequery,
			MailServiceStandardMapper mapper) {
		super(pageSize, totalRecords, mapper, PersistenceController.getEntityManager().currentSession());
		this.mailServiceStandardFilterVO = mailServiceStandardFilterVO;
		this.baseQuery = basequery;
	}


	public String getNativeQuery() {

		int index=0;
		StringBuilder queryBuilder = new StringBuilder(baseQuery);
		String companyCode=mailServiceStandardFilterVO.getCompanyCode();
		String gpaCode=mailServiceStandardFilterVO.getGpaCode();
		this.setParameter(++index, gpaCode);
		this.setParameter(++index, companyCode);

		if(mailServiceStandardFilterVO.getOrginCode()!=null && mailServiceStandardFilterVO.getOrginCode().length()>0){
			queryBuilder.append(" AND ORGCOD=?");
			this.setParameter(++index, mailServiceStandardFilterVO.getOrginCode());
		}
		if(mailServiceStandardFilterVO.getDestCode()!=null && mailServiceStandardFilterVO.getDestCode().length()>0){
			queryBuilder.append(" AND DSTCOD=?");
			this.setParameter(++index, mailServiceStandardFilterVO.getDestCode());
		}
		if(mailServiceStandardFilterVO.getServLevel()!=null && mailServiceStandardFilterVO.getServLevel().length()>0){
			queryBuilder.append(" AND SRVLVL=?");
			this.setParameter(++index, mailServiceStandardFilterVO.getServLevel());
		}
		if(mailServiceStandardFilterVO.getScanWaived()!=null && ("Y").equals(mailServiceStandardFilterVO.getScanWaived())){
			queryBuilder.append(" AND SCNWVDFLG=?");
			this.setParameter(++index, mailServiceStandardFilterVO.getScanWaived());
		}
		if(mailServiceStandardFilterVO.getServiceStandard()!=null && mailServiceStandardFilterVO.getServiceStandard().length()>0){
			queryBuilder.append(" AND SRVSTD=?");
			this.setParameter(++index, Integer.parseInt(mailServiceStandardFilterVO.getServiceStandard()));
		}
		if(mailServiceStandardFilterVO.getContractId()!=null && mailServiceStandardFilterVO.getContractId().length()>0){
			queryBuilder.append(" AND CTRIDR=?");
			this.setParameter(++index, mailServiceStandardFilterVO.getContractId());
		}
		return queryBuilder.toString();
	}
}
