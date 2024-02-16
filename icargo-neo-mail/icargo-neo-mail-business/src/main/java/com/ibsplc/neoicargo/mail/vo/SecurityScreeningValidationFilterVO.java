package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.msgbroker.config.handling.vo.MessageAddressFilterVO;

@Setter
@Getter
public class SecurityScreeningValidationFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String originAirport;
	private String originCountry;
	private String destinationAirport;
	private String destinationCountry;
	private String transactionAirport;
	private String transactionCountry;
	private String securityStatusCode;
	private String applicableTransaction;
	private String flightType;
	private String originAirportCountryGroup;
	private String destAirportCountryGroup;
	private String txnAirportCountryGroup;
	private String txnAirportGroup;
	private String securityStatusCodeGroup;
	private String mailbagId;
	private boolean appRegValReq;
	private String appRegOriginCountryGroup;
	private String appRegDestCountryGroup;
	private String subClass;
	private String appRegFlg;
	private String appRegDestArp;
	private boolean transferInTxn;
	private boolean securityValNotReq;
	private String securityValNotRequired;
	private String appRegTransistCountryGroup;
	private String appRegTransistCountry;
	private String transistAirport;

	@Override
	public int hashCode() {
		return SecurityScreeningValidationFilterVO.this.toString().hashCode();
	}

	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(150);
		sbul.append(this.companyCode).append('-').append(this.originAirport).append('-').append(this.originCountry)
				.append('-').append(this.destinationAirport).append('-').append(this.destinationCountry).append('-')
				.append(this.transactionAirport).append('-').append(this.transactionCountry).append('-')
				.append(this.securityStatusCode).append('-').append(this.applicableTransaction).append('-')
				.append(this.flightType).append('-').append(this.originAirportCountryGroup).append('-')
				.append(this.destAirportCountryGroup).append('-').append(this.txnAirportCountryGroup).append('-')
				.append(this.txnAirportGroup).append('-').append(this.securityStatusCodeGroup).append('-')
				.append(this.appRegOriginCountryGroup).append('-').append(this.appRegDestCountryGroup).append('-')
				.append(this.subClass).append('-').append(this.appRegFlg).append('-').append(this.appRegDestArp)
				.append(this.appRegTransistCountryGroup).append(this.appRegTransistCountry).append(transistAirport)
				.append(securityValNotRequired);
		return sbul.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof SecurityScreeningValidationFilterVO) {
			return obj.hashCode() == SecurityScreeningValidationFilterVO.this.hashCode();
		} else {
			return false;
		}
	}
}
