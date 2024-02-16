package com.ibsplc.neoicargo.mail.mapper;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.aircraft.vo.AircraftTypeValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineParametersVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.IATACommodityVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.shared.scc.sccsuggestion.vo.SCCSuggestionVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.masters.aircraft.modal.Aircraft;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineParameterModel;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import com.ibsplc.neoicargo.masters.area.city.modal.CityModal;
import com.ibsplc.neoicargo.masters.commodity.modal.CommodityModal;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.masters.onetime.modal.OneTimeModal;
import com.ibsplc.neoicargo.masters.product.modal.ProductModel;
import com.ibsplc.neoicargo.masters.scc.model.SCCSuggestionFilter;
import com.ibsplc.neoicargo.masters.scc.model.SccDetailModel;
import com.ibsplc.neoicargo.masters.scc.model.SccModal;
import com.ibsplc.neoicargo.masters.shared.modal.GeneralMasterGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, uses = {
        MeasureMapper.class, LocalDateMapper.class, EBLMoneyMapper.class })
public interface NeoMastersMapper {

    @Mapping( ignore = true, target = "lastUpdatedTime")
    @Mapping(target = "station", source = "stationCode")
    @Mapping(target = "agentCountry", source = "countryCode")
    @Mapping(target = "validTo", source = "validToDate")
    @Mapping(target = "validFrom", source = "validFromDate")
    AgentVO customerModelToAgentVO(CustomerModel customerModel);

    AircraftTypeValidationVO aircraftTypeValidationVOToAircraft(Aircraft aircraft);

    @Mapping( ignore = true, target = "validFrom")
    @Mapping( ignore = true, target = "validTo")
    @Mapping(source = "airlineParameters", target = "airlineParameterVOs")
    AirlineValidationVO airlineModelToAirlineValidationVO(AirlineModel airlineModel);

    @Mapping( ignore = true, target = "airportParameters")
    AirportValidationVO airportModalToAirportValidationVO(AirportModal airportModal);

    @Mapping( ignore = true, target = "lastUpdateTime")
    CityVO cityModalToCityVO(CityModal cityModal);

    @Mapping( ignore = true, target = "lastUpdateTime")
    @Mapping( ignore = true, target = "airportParameters")
    @Mapping( ignore = true, target = "contactDetails")
    AirportVO airportModalToAirportVO(AirportModal airportModal);

    CommodityValidationVO commodityModalToCommodityValidationVO(CommodityModal commodityModal);

    CommodityVO commodityModalToCommodityVO(CommodityModal commodityModal);


    IATACommodityVO commodityModalToIATACommodityVO(CommodityModal commodityModal);

    @Mapping( ignore = true, target = "lastUpdatedTime")
    CustomerModel customerFilterVOToCustomerModel(CustomerFilterVO customerFilterVO);

    Collection<CustomerVO> customerModelsToCustomerVOs(Collection<CustomerModel> customerModel);

    @Mapping( ignore = true, target = "lastUpdatedTime")
    CustomerVO customerModelToCustomerVO(CustomerModel customerModel);

    Collection<OneTimeVO> oneTimeModalToOneTimeVO(List<OneTimeModal> oneTimeModal);

    GeneralMasterGroupVO generalMasterGroupToGeneralMasterGroupVO(GeneralMasterGroup generalMasterGroup);

    GeneralMasterGroupDetailsVO generalMasterGroupToGeneralMasterGroupDetailsVO(GeneralMasterGroup generalMasterGroup);

    Collection<SCCValidationVO> sccDetailModelToSCCValidationVO(List<SccDetailModel> sccDetailModel);

    @Mapping(target = "configurationParameters", source = "sccSuggestionConfigurationParameters")
    @Mapping(target = "thresholdParameters", source = "sccSuggestionThresholdSettingParameters")
    SCCSuggestionFilter sCCSuggestionVOToSCCSuggestionFilter(SCCSuggestionVO sccSuggestionVO);

    Collection<SCCVO> sccModalsToSCCVOs(List<SccModal> sccModal);

    @Mapping( ignore = true, target = "productPriority")
    @Mapping( ignore = true, target = "endDate")
    @Mapping( ignore = true, target = "startDate")
    ProductVO productModelToProductVO(ProductModel productModel);

    Collection<AirlineParametersVO> airlineParameterModelToAirlineParametersVO (Collection<AirlineParameterModel> airlineParameterModel);

    Collection<CustomerLovVO> customerModelsToCustomerLovVOs(List<CustomerModel> listAllCustomers);

    Collection<ProductValidationVO> productModelsToProductValidationVO(List<ProductModel> productModelList);
    @Mapping( ignore = true, target = "endDate")
    @Mapping( ignore = true, target = "startDate")
    ProductValidationVO productModelToProductValidation(ProductModel productModel);
}
