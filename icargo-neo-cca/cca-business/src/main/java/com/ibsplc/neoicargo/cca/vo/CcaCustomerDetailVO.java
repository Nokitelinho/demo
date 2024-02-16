package com.ibsplc.neoicargo.cca.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CcaCustomerDetailVO extends AbstractVO {

    private static final long serialVersionUID = 1L;

    private Long serialNumber;

    private CustomerType customerType;

    private String customerName;

    private String stationCode;

    @JsonProperty("city_code")
    private String cityCode;

    private String countryCode;

    private String accountNumber;

    private String iataCode;

    private String cassIndicator;

    private String customerTypeCode;

    private String billingCurrencyCode;

}