package com.ibsplc.neoicargo.stock.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import java.util.Collection;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AirlineValidationModel extends BaseModel {

  private static final long serialVersionUID = 1L;

  private int airlineIdentifier;
  private int awbCheckDigit;
  private int routingMaxCon;
  private int routingDistOff;
  private int routeUpdateFreq;

  private boolean dynamicRouting;
  private boolean routingCnrRegion;
  private boolean routingCnrOff;

  private String companyCode;
  private String alphaCode;
  private String numericCode;
  private String baseCurrencyCode;
  private String cfgHub;
  private String defaultWeightUnit;
  private String defaultVolumeUnit;
  private String defaultDimUnit;
  private String nationality;
  private String airlineName;
  private String billingAddress;
  private String billingEmail;
  private String dummyCarrier;
  private String handledCarrierImpFlag;
  private String handledCarrierExpFlag;
  private String isPartnerAirline;
  private String airlineStatus;
  private String threeAlphaCode;

  private LocalDate validFrom;
  private LocalDate validTo;

  private Set<AirlineValidityDetailsModel> airlineValidityDetails;

  private Collection<AirlineParametersModel> airlineParameters;
  private Collection<AirlineValidationModel> airlineValidations;
}
