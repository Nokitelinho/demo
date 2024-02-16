package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.businessrules.client.util.RuleClient;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.exception.CcaBusinessException;
import com.ibsplc.neoicargo.cca.exception.CcaErrors;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.util.awb.AwbParameterConfig;
import com.ibsplc.neoicargo.cca.util.awb.Parameters.Parameter;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaCustomerDetailVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorType;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.country.CountryComponent;
import com.ibsplc.neoicargo.masters.country.modal.CountryModal;
import com.ibsplc.neoicargo.masters.currency.CurrencyComponent;
import com.ibsplc.neoicargo.masters.currency.exceptions.ExchangeRateNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import static com.ibsplc.neoicargo.cca.exception.CcaErrors.constructErrorVO;

@Component
@Slf4j
@AllArgsConstructor
public class CcfeeCalculatorEnricher extends Enricher<CCAMasterVO> {

    private final AirportComponent airportComponent;
    private final CountryComponent countryComponent;
    private final CurrencyComponent currencyComponent;
    private final AirportUtil airportUtil;
    private final AwbParameterConfig parameterConfig;
    private final RuleClient client;
    private final CustomerComponent customerComponent;
    public static final double ONE_PERCENT = .01;
    public static final String CCFEE_FORMULA = "CCFee";
    public static final Set<String> FREIGHT_CC_PAYMENT_TYPES = Set.of("CC", "CP");
    public static final String CC_COLLECTOR = "CC";
    @Override
    public void enrich(CCAMasterVO ccaMasterVO) throws BusinessException {
        var ccaAwbVO = ccaMasterVO.getRevisedShipmentVO();
        if (FREIGHT_CC_PAYMENT_TYPES.contains(ccaAwbVO.getPayType()) && airportUtil.isLastOwnFlownRoute(ccaAwbVO) && !CcaConstants.FLAG_Y.equalsIgnoreCase(getCCFeeDueGhaFlag(ccaAwbVO))) {
            log.info("CcfeeCalculatorEnricher -> Invoked");
            CountryModal countryModal = getDestinationCountryDetails(ccaAwbVO);

            /* find minimum ccfee in destination currency --> minimum ccfee currency to destination currency
             * find calculated ccfee in destination currency --> awb currency to destination currency
             * compare the values and calculate ccfee
             */
            var exchangeRateBasis = parameterConfig.getAwbParameter("EXGRATBAS");
            var exchangeRateDate = parameterConfig.getAwbParameter("EXGRATDATBAS");
            log.info("parameterConfig Invoked");
            var ratePickupDate = "";

            if ("A".equals(exchangeRateDate.getParameterValue())) {
                ratePickupDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            var ccFeeMinInDestinationCurrency =
                    getCcFeeMinInDestinationCurrency(countryModal, exchangeRateBasis, ratePickupDate);
            /** Code For Formula Configuration for CCFEE **/
            double ccFeeSum = getCcFeeSum(ccaAwbVO);
            double calculatedCCfeeConversionFactor = getFromCurrency(ccaAwbVO.getCurrency(), countryModal.getCurrencyCode(),
                    exchangeRateBasis.getParameterValue(), ratePickupDate);

            double ccFeeCalculatedInDestinationCurrency =
                    (ccFeeSum) * ONE_PERCENT * countryModal.getCcfeePercentage() * calculatedCCfeeConversionFactor;
            setCcFee(ccaAwbVO, countryModal, ccFeeMinInDestinationCurrency, ccFeeCalculatedInDestinationCurrency);
            if (countryModal.getMinimumCCfee() != 0.0) {
                validateExchangeRate(ccaAwbVO, exchangeRateBasis);
            }
            log.info("CcfeeCalculatorEnricher -> exited");
        }
    }

    private void setCcFee(CcaAwbVO ccaAwbVO,
                          CountryModal countryModal,
                          double ccFeeMinInDestinationCurrency,
                          double ccFeeCalculatedInDestinationCurrency) {
        if (ccFeeMinInDestinationCurrency != 0.0) {
            if (ccFeeCalculatedInDestinationCurrency > ccFeeMinInDestinationCurrency) {
                ccaAwbVO.setCcfee(ccFeeCalculatedInDestinationCurrency);
                ccaAwbVO.setCcfeeCurrency(countryModal.getCurrencyCode());
            } else {
                ccaAwbVO.setCcfee(countryModal.getMinimumCCfee());
                ccaAwbVO.setCcfeeCurrency(countryModal.getMinimumCCfeeCurrency());
            }
        } else {
            ccaAwbVO.setCcfee(ccFeeCalculatedInDestinationCurrency);
            ccaAwbVO.setCcfeeCurrency(countryModal.getCurrencyCode());
        }
    }

    private double getCcFeeSum(CcaAwbVO ccaAwbVO) throws BusinessException {
        var actionsCCFee = client.fireRule(CCFEE_FORMULA, ccaAwbVO, false);
        if (Objects.nonNull(actionsCCFee)) {
            return actionsCCFee.stream()
                    .filter(f -> Objects.nonNull(f.getParams()))
                    .mapToDouble(m -> m.getParams().stream()
                            .findFirst()
                            .map(outputParameter -> Double.parseDouble(outputParameter.getValue()))
                            .orElse(0.0))
                    .sum();
        } else {
            return 0.0;
        }
    }

    private double getCcFeeMinInDestinationCurrency(CountryModal countryModal,
                                                    Parameter exchangeRateBasis,
                                                    String ratePickupDate) throws CcaBusinessException {
        if (countryModal.getMinimumCCfeeCurrency() != null) {
            double minimumCCfeeConversionFactor =
                    getFromCurrency(countryModal.getMinimumCCfeeCurrency(), countryModal.getCurrencyCode(),
                    exchangeRateBasis.getParameterValue(), ratePickupDate);
            return countryModal.getMinimumCCfee() * minimumCCfeeConversionFactor;
        } else {
            return 0;
        }
    }

    private CountryModal getDestinationCountryDetails(CcaAwbVO ccaAwbVO) throws AirportBusinessException {
        var airportModelDst = airportComponent.validateAirport(ccaAwbVO.getDestination());
        return countryComponent.findCountry(airportModelDst.getCountryCode());
    }

    private void validateExchangeRate(CcaAwbVO revisedShipmentVO,
                                      Parameter exchangeRateBasis) throws CcaBusinessException {
        log.info("parameterConfig Invoked");
        String ratePickupDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        getFromCurrency(
                getBillingCurrencyCode(revisedShipmentVO),
                revisedShipmentVO.getCcfeeCurrency(),
                exchangeRateBasis.getParameterValue(),
                ratePickupDate
        );
    }

    private String getBillingCurrencyCode(CcaAwbVO revisedShipmentVO) {
        return revisedShipmentVO.getCcaCustomerDetails().stream()
            .filter(ccaCustomerDetailVO -> ccaCustomerDetailVO.getCustomerType().equals(CustomerType.I))
            .findFirst()
            .map(CcaCustomerDetailVO::getBillingCurrencyCode)
            .orElse("");
    }

    private double getFromCurrency(String fromCurrency, String toCurrency,
                                   String exchangeRateBasis, String ratePickupDate) throws CcaBusinessException {
        if (!fromCurrency.equals(toCurrency)) {
            try {
                var exchangeRateModal = currencyComponent.findCurrencyExchangeRate(
                        fromCurrency, toCurrency, ratePickupDate, exchangeRateBasis);
                return exchangeRateModal.getConversionFactor();
            } catch (ExchangeRateNotFoundException e) {
                log.error("Cannot find exchange rate", e);
                throw new CcaBusinessException(
                        constructErrorVO(
                                CcaErrors.NEO_CCA_018.getErrorCode(),
                                CcaErrors.NEO_CCA_018.getErrorMessage(),
                                ErrorType.ERROR,
                                new String[]{fromCurrency, toCurrency}
                        )
                );
            }
        } else {
            return 1;
        }
    }

    //function to get inbound customer  ccfeeduegha flag of  cc type customer
    private String getCCFeeDueGhaFlag(CcaAwbVO ccaAwbVO) throws CustomerBusinessException {
        CustomerModel customerModel = new CustomerModel();
        customerModel.setCustomerCode(ccaAwbVO.getInboundCustomerCode());
        customerModel.setCustomerType(CC_COLLECTOR);
        customerModel.setAirportCode(ccaAwbVO.getDestination());
        CustomerModel ccCollector = null;
        String ccfeeDueGhaFlag =    CcaConstants.FLAG_N;
        List<CustomerModel> listccCollector = customerComponent.validateCCCollector(customerModel);
        if (!listccCollector.isEmpty()) {
            Optional<CustomerModel> listCutmodel = listccCollector.stream().findFirst();
            if (listCutmodel.isPresent()) {
                ccCollector = listCutmodel.get();
            }
            if (Objects.nonNull(ccCollector) && Objects.nonNull(ccCollector.getCcfeeDueGhaFlag())) {
                ccfeeDueGhaFlag = ccCollector.getCcfeeDueGhaFlag();
            }
        }
        return ccfeeDueGhaFlag;
    }
}
