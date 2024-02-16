package com.ibsplc.icargo.persistence.dao.mail.mra.defaults;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
/**
 * @author A-9498
 *
 */
public class BillingScheduleFilterQuery extends NativeQuery {
	private String baseQuery;

	public static final String ACTIVE = "A";
	private BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
	public BillingScheduleFilterQuery(String baseQuery, BillingScheduleFilterVO billingScheduleFilterVO) throws SystemException {
		super();
		this.baseQuery = baseQuery;
		this.billingScheduleFilterVO = billingScheduleFilterVO;
	}

	

	@Override
	public String getNativeQuery(){
		
		StringBuilder stringBuilder = new StringBuilder(baseQuery);
		int index = 1;
		this.setParameter(0, billingScheduleFilterVO.getCompanyCode());  
		
		if(billingScheduleFilterVO.getBillingType()!=null && billingScheduleFilterVO.getBillingType().length()>0){
			
			stringBuilder.append(" AND MST.BLGTYP = ?  ");
			this.setParameter(++index,billingScheduleFilterVO.getBillingType());
			
			}
		if(billingScheduleFilterVO.getBillingPeriod()!=null && billingScheduleFilterVO.getBillingPeriod().length()>0){
			
			stringBuilder.append(" AND MST.BLGPRD = ?  ");
			this.setParameter(++index,billingScheduleFilterVO.getBillingPeriod());
			}
		if(billingScheduleFilterVO.getYear()>0 ){
			
			stringBuilder.append(" AND SUBSTR(MST.PRDNUM,1,4) = ? "); 
			this.setParameter(++index,billingScheduleFilterVO.getYear());
			}
		
		
		return stringBuilder.toString();
	}

}
