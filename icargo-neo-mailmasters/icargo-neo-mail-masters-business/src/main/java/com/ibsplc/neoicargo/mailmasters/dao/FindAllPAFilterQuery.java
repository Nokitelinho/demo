package com.ibsplc.neoicargo.mailmasters.dao;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;

public class FindAllPAFilterQuery extends NativeQuery {

	private GenerateInvoiceFilterVO generateInvoiceFilterVO;
	private String baseQuery;
	int index;

	public FindAllPAFilterQuery(GenerateInvoiceFilterVO generateInvoiceFilterVO,
								String baseQuery) throws SystemException {
		super(PersistenceController.getEntityManager().currentSession());
		this.generateInvoiceFilterVO = generateInvoiceFilterVO;
		this.baseQuery = baseQuery;
		this.index = 0;
	}

	public String getNativeQuery() {
		StringBuilder builder = new StringBuilder(baseQuery);

		this.setParameter(++index, generateInvoiceFilterVO.getCompanyCode());
		this.setParameter(++index, generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayDateOnlyFormat());
		this.setParameter(++index, generateInvoiceFilterVO.getBillingPeriodTo().toDisplayDateOnlyFormat());

		if (generateInvoiceFilterVO.getGpaCode() != null && generateInvoiceFilterVO.getGpaCode().trim().length() > 0) {
			builder.append("AND MST.POACOD= ?");
			this.setParameter(++index, generateInvoiceFilterVO.getGpaCode());
		}
		if (generateInvoiceFilterVO.getCountryCode() != null && generateInvoiceFilterVO.getCountryCode().trim().length() > 0) {
			builder.append("AND MST.CNTCOD= ?");
			this.setParameter(++index, generateInvoiceFilterVO.getCountryCode());
		}
		if (generateInvoiceFilterVO.getBillingFrequency() != null && generateInvoiceFilterVO.getBillingFrequency().trim().length() > 0) {
			builder.append(" AND COALESCE(DTL.BLGFRQ,?)  =?");
			this.setParameter(++index, generateInvoiceFilterVO.getBillingFrequency());
			this.setParameter(++index, generateInvoiceFilterVO.getBillingFrequency());
		}
		if (MRAConstantsVO.INV_TYP_PASS.equals(generateInvoiceFilterVO.getInvoiceType())) {
			builder.append(" AND DTL.PARVAL IS NOT NULL ");
		}

		if (generateInvoiceFilterVO.getParamsList() != null && !generateInvoiceFilterVO.getParamsList().isEmpty()) {
			setParameters(builder);
		}

		builder.append("GROUP BY MST.CMPCOD,MST.POACOD,MST.POANAM,MST.POAADR,MST.AUTEMLREQ,MST.CNTCOD,MST.EMLADR,MST.SECEMLADRONE,MST.SECEMLADRTWO,MST.STLCURCOD,MST.PROINVREQ");

		return builder.toString();
	}


	private void setParameters(StringBuilder builder) {

		for (BillingParameterVO billingParameterVO : generateInvoiceFilterVO.getParamsList()) {
			if (MRAConstantsVO.INCLUDE.equals(billingParameterVO.getExcludeFlag())) {
				if (MRAConstantsVO.BILLING_PARAM_COUNTRYCODE.equals(billingParameterVO.getParamterCode())) {
					builder.append("AND MST.CNTCOD= ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				} else if (MRAConstantsVO.BILLING_PARAM_GPACODE.equals(billingParameterVO.getParamterCode())) {
					builder.append("AND MST.POACOD= ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}
			} else if (MRAConstantsVO.EXCLUDE.equals(billingParameterVO.getExcludeFlag())) {
				if (MRAConstantsVO.BILLING_PARAM_COUNTRYCODE.equals(billingParameterVO.getParamterCode())) {
					builder.append("AND MST.CNTCOD <> ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				} else if (MRAConstantsVO.BILLING_PARAM_GPACODE.equals(billingParameterVO.getParamterCode())) {
					builder.append("AND MST.POACOD <> ?");
					this.setParameter(++index, billingParameterVO.getParameterValue());
				}
			}
		}

	}
}
