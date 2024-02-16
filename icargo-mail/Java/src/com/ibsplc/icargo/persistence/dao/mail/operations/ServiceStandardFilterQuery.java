/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 *
 */
public class ServiceStandardFilterQuery extends PageableNativeQuery<MailServiceStandardVO>{

	private MailServiceStandardFilterVO mailServiceStandardFilterVO;

	private String baseQuery;

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	public ServiceStandardFilterQuery(int pageSize,int totalRecords,MailServiceStandardFilterVO mailServiceStandardFilterVO,
			String basequery,MailServiceStandardMapper mapper) throws SystemException {
		super(pageSize,totalRecords,mapper);
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
		log.log(Log.FINE,"\n\n Final query ----------> \n", queryBuilder);
		return queryBuilder.toString();
	}
	
}
