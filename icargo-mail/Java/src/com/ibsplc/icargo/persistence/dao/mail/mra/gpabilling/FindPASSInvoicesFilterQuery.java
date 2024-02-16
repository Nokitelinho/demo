package com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

public class FindPASSInvoicesFilterQuery extends NativeQuery {

	private GeneratePASSFilterVO generatePASSFilterVO;
	private String baseQuery;
	int index;
	
	public FindPASSInvoicesFilterQuery(GeneratePASSFilterVO generatePASSFilterVO,
			String baseQuery) throws SystemException {
		super();
		this.generatePASSFilterVO = generatePASSFilterVO;
		this.baseQuery = baseQuery;
		this.index=0;
	}

	public String getNativeQuery() {
		StringBuilder builder = new StringBuilder(baseQuery);

		this.setParameter(++index, generatePASSFilterVO.getCompanyCode());
		this.setParameter(++index, generatePASSFilterVO.getPeriodNumber());
		this.setParameter(++index, generatePASSFilterVO.getBillingPeriodFrom());
		this.setParameter(++index, generatePASSFilterVO.getBillingPeriodTo());
		if(generatePASSFilterVO.getGpaCode()!=null && !generatePASSFilterVO.getGpaCode().isEmpty()){
			builder.append(" AND SMY.GPACOD = ?");
			this.setParameter(++index, generatePASSFilterVO.getGpaCode());
		}
		if(generatePASSFilterVO.isAddNew() && generatePASSFilterVO.getFileName()!=null && generatePASSFilterVO.getFileName().trim().length()>0){
			builder.append(" AND(SMY.INTFCDFILNAM LIKE '%").append(generatePASSFilterVO.getFileName()).append("%' OR SMY.INTFCDFILNAM IS NULL)");
		}
		else if (generatePASSFilterVO.getFileName()!=null && generatePASSFilterVO.getFileName().trim().length()>0){
			builder.append(" AND SMY.INTFCDFILNAM LIKE '%").append(generatePASSFilterVO.getFileName()).append("%' "); 
		}else{
			builder.append(" AND SMY.INTFCDFILNAM IS NULL ");
		}
		if(generatePASSFilterVO.getParamsList()!=null && !generatePASSFilterVO.getParamsList().isEmpty()){
			setParameters(builder);
		}
		
		if(generatePASSFilterVO.getCountry()!=null && generatePASSFilterVO.getCountry().trim().length()>0){
			builder.append("AND POAMST.CNTCOD= ?");
			this.setParameter(++index, generatePASSFilterVO.getCountry());
		}
		
		builder.append("GROUP BY SMY.PRDNUM,C66.BRHOFC,SMY.CMPCOD");

		return builder.toString();
	}


	private void setParameters(StringBuilder builder) {
		
		for(BillingParameterVO billingParameterVO:generatePASSFilterVO.getParamsList()){
			if(MRAConstantsVO.INCLUDE.equals(billingParameterVO.getExcludeFlag())){
				if(MRAConstantsVO.BILLING_PARAM_COUNTRYCODE.equals(billingParameterVO.getParamterCode())){
					builder.append("AND POAMST.CNTCOD= ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}else if(MRAConstantsVO.BILLING_PARAM_GPACODE.equals(billingParameterVO.getParamterCode())){
					builder.append("AND POAMST.POACOD= ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}
			}else if(MRAConstantsVO.EXCLUDE.equals(billingParameterVO.getExcludeFlag())){
				if(MRAConstantsVO.BILLING_PARAM_COUNTRYCODE.equals(billingParameterVO.getParamterCode())){
					builder.append("AND POAMST.CNTCOD <> ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}else if(MRAConstantsVO.BILLING_PARAM_GPACODE.equals(billingParameterVO.getParamterCode())){
					builder.append("AND POAMST.POACOD <> ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}
			}
		}
		
	}

}