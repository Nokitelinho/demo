
package com.ibsplc.neoicargo.mail.util;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.aircraft.vo.AircraftTypeValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityFilterVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.IATACommodityVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyConvertorVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.dgr.vo.DGRIncompatibilityFilterVO;
import com.ibsplc.icargo.business.shared.dgr.vo.DGRThresholdFilterVO;
import com.ibsplc.icargo.business.shared.dgr.vo.DGRThresholdMasterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.shared.scc.sccsuggestion.vo.SCCSuggestionVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.exception.SharedProxyException;
import com.ibsplc.neoicargo.mail.mapper.NeoMastersMapper;
import com.ibsplc.neoicargo.common.types.Pair;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.ParameterService;
import com.ibsplc.neoicargo.masters.ParameterType;
import com.ibsplc.neoicargo.masters.aircraft.AircraftComponent;
import com.ibsplc.neoicargo.masters.aircraft.modal.Aircraft;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineParameterModel;
import com.ibsplc.neoicargo.masters.area.airport.AirportBusinessException;
import com.ibsplc.neoicargo.masters.area.airport.AirportComponent;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportModal;
import com.ibsplc.neoicargo.masters.area.airport.modal.AirportParameterModel;
import com.ibsplc.neoicargo.masters.area.city.CityComponent;
import com.ibsplc.neoicargo.masters.area.city.modal.CityModal;
import com.ibsplc.neoicargo.masters.area.station.StationComponent;
import com.ibsplc.neoicargo.masters.area.station.modal.StationDetailsModel;
import com.ibsplc.neoicargo.masters.commodity.CommodityBusinessException;
import com.ibsplc.neoicargo.masters.commodity.CommodityComponent;
import com.ibsplc.neoicargo.masters.commodity.modal.CommodityModal;
import com.ibsplc.neoicargo.masters.currency.CurrencyComponent;
import com.ibsplc.neoicargo.masters.currency.exceptions.ExchangeRateNotFoundException;
import com.ibsplc.neoicargo.masters.currency.modal.ExchangeRateModal;
import com.ibsplc.neoicargo.masters.customer.CustomerBusinessException;
import com.ibsplc.neoicargo.masters.customer.CustomerComponent;
import com.ibsplc.neoicargo.masters.customer.modal.CustomerModel;
import com.ibsplc.neoicargo.masters.onetime.OneTimeBusinessException;
import com.ibsplc.neoicargo.masters.onetime.OneTimeComponent;
import com.ibsplc.neoicargo.masters.onetime.modal.OneTimeFilterModal;
import com.ibsplc.neoicargo.masters.onetime.modal.OneTimeModal;
import com.ibsplc.neoicargo.masters.product.ProductComponent;
import com.ibsplc.neoicargo.masters.product.modal.ProductFilterModal;
import com.ibsplc.neoicargo.masters.product.modal.ProductModel;
import com.ibsplc.neoicargo.masters.scc.SccComponent;
import com.ibsplc.neoicargo.masters.scc.model.SccDetailModel;
import com.ibsplc.neoicargo.masters.scc.model.SccFilterModel;
import com.ibsplc.neoicargo.masters.scc.model.SccModal;
import com.ibsplc.neoicargo.masters.shared.SharedComponent;
import com.ibsplc.neoicargo.masters.shared.modal.GeneralMasterGroup;
import com.ibsplc.neoicargo.masters.shared.modal.GeneralMasterGroupWrapper;
import com.ibsplc.neoicargo.masters.uld.ULDComponent;
import com.ibsplc.neoicargo.masters.uld.modal.ULDModal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 203651 - sumitlr - 19th November 2022.
 * This class will be used to make NEO Master API calls.
 * Earlier those calls were routed to Classic Master via ACL.
 * As these Master APIs are already present in NEO, we will leverage the Neo Master API Calls.
 * Creating methods so any class in existing refactored code can use the below methods to make call to NEO Master APIs.
 * The method names have been kept same as the ones used in calling Classic Alpha PROXY classes.
 */

@Component("neoMastersProxyCallUtils")
public class NeoMastersServiceUtils {

    @Autowired
    private CustomerComponent customerComponent;
    @Autowired
    private NeoMastersMapper neoMastersMapper;
    @Autowired
    private AircraftComponent aircraftComponent;
    @Autowired
    private AirlineWebComponent airlineWebComponent;
    @Autowired
    private AirportComponent airportComponent;
    @Autowired
    private CityComponent cityComponent;
    @Autowired
    private StationComponent stationComponent;
    @Autowired
    private CommodityComponent commodityComponent;
    @Autowired
    private CurrencyComponent currencyComponent;
    @Autowired
    private OneTimeComponent oneTimeComponent;
    @Autowired
    private SharedComponent sharedComponent;
    @Autowired
    private SccComponent sccComponent;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private ULDComponent uldComponent;
    @Autowired
    private ProductComponent productComponent;

    @Autowired
    private SharedDefaultsProxy defaultsProxy;




    public AgentVO findAgentDetails(String companyCode, String agentCode) throws CustomerBusinessException {
        AgentVO agentVO = null;
        if(agentCode!=null && !agentCode.trim().isEmpty()){
            List<CustomerModel> customerModels = customerComponent.getCustomerDetails(agentCode);
            if(customerModels!=null && !customerModels.isEmpty()){
                //Getting 1st Object as in return we need to return only single AgentVO.
                agentVO = neoMastersMapper.customerModelToAgentVO(customerModels.get(0));
            }
        }
        return agentVO;

    }
    
    public Collection<AgentVO> findAgentForIATA(String iataCode) throws CustomerBusinessException {

        Collection<AgentVO> agentVOs  = null;
        if(iataCode!=null && !iataCode.trim().isEmpty()){
            CustomerModel customerModel = customerComponent.validateIataAgentCode(iataCode);
            if(customerModel!=null){
                agentVOs  = new ArrayList<>();
                agentVOs.add(neoMastersMapper.customerModelToAgentVO(customerModel));
            }
        }
        return agentVOs;

    }

    public Map<String, AircraftTypeValidationVO> validateAircraftTypeCodes(Collection<String> aircraftTypeCodes){

        if(aircraftTypeCodes!=null && !aircraftTypeCodes.isEmpty()){
            Set<String> aircraftTypeCodesSet =  aircraftTypeCodes.stream().collect(Collectors.toSet());
            List<Aircraft> aircrafts = aircraftComponent.listAircraft(aircraftTypeCodesSet);
            if(aircrafts!=null && !aircrafts.isEmpty()){
                Map<String, AircraftTypeValidationVO> aircraftTypeValidationVOMap = new HashMap<>();
                aircrafts.forEach(aircraft -> aircraftTypeValidationVOMap.put(aircraft.getAircraftType(), neoMastersMapper.aircraftTypeValidationVOToAircraft(aircraft)));
                return aircraftTypeValidationVOMap;
            }
        }

        return  null;
    }

    public AirlineValidationVO validateAlphaCode(String companyCode, String alphaCode)
            throws BusinessException {

        AirlineValidationVO airlineValidationVO = null;
        if(alphaCode!=null && !alphaCode.trim().isEmpty()){
            List<String> alphaCodeList = new ArrayList<>();
            alphaCodeList.add(alphaCode);
            List<AirlineModel> airlineModelList = airlineWebComponent.validateAlphaCode(alphaCodeList);
            if(airlineModelList!=null && !airlineModelList.isEmpty()){
                //Getting 1st Object as in return we need to return only single AirlineValidationVO.
                airlineValidationVO = neoMastersMapper.airlineModelToAirlineValidationVO(airlineModelList.get(0));
            }
        }

        return airlineValidationVO;
    }

    public Map<String, AirlineValidationVO> validateAlphaCodes(String companyCode, Set<String> alphaCodes)
            throws BusinessException {
        if(alphaCodes!=null && !alphaCodes.isEmpty()){
            List<String> alphaCodesList = alphaCodes.stream().collect(Collectors.toList());
            List<AirlineModel> airlineModelList = airlineWebComponent.validateAlphaCode(alphaCodesList);
            if(airlineModelList!=null && !airlineModelList.isEmpty()){
                Map<String, AirlineValidationVO> airlineValidationVOMap =  new HashMap<>();
                airlineModelList.forEach(airlineModel -> airlineValidationVOMap.put(airlineModel.getAlphaCode(),neoMastersMapper.airlineModelToAirlineValidationVO(airlineModel)));
                return airlineValidationVOMap;
            }
        }

        return null;
    }

    public Map<String, AirportValidationVO> validateAirportCodes(String companyCode, Collection<String> airportCodes)
            throws AirportBusinessException {

        if(airportCodes!=null && !airportCodes.isEmpty()){
            Set<String> airportCodesList = airportCodes.stream().collect(Collectors.toSet());
            List<AirportModal> airportModalList = airportComponent.validateAirports(airportCodesList);
            if(airportModalList!=null && !airportModalList.isEmpty()){
                Map<String, AirportValidationVO> airportValidationVOMap =  new HashMap<>();
                airportModalList.forEach(airportModal -> airportValidationVOMap.put(airportModal.getAirportCode(),neoMastersMapper.airportModalToAirportValidationVO(airportModal)));
                return airportValidationVOMap;
            }
        }

        return null;
    }

    public AirportValidationVO validateAirportCode(String companyCode, String airportCode)
            throws AirportBusinessException {

        AirportValidationVO airportValidationVO = null;
        if(airportCode!=null && !airportCode.trim().isEmpty()){
            AirportModal airportModal = airportComponent.validateAirport(airportCode);
            if(airportModal!=null){
                airportValidationVO = neoMastersMapper.airportModalToAirportValidationVO(airportModal);
            }
        }

        return airportValidationVO;
    }

    //Also to be used for SharedAreaProxy.findAirportParametersByCode
    public Map<String, String> findParametersForAirport(String companyCode, String airportCode, Collection<String> parameters){

        if(parameters!=null && !parameters.isEmpty()){
            Set<String> parametersList = parameters.stream().collect(Collectors.toSet());
            Map<String, String> airportParameterCodeParameterValueMap = new HashMap<>();
            Set<AirportParameterModel> airportParametersModelSet = airportComponent.findAirportParametersByCode(airportCode, parametersList);
            if(airportParametersModelSet!=null && !airportParametersModelSet.isEmpty()){
                airportParametersModelSet.forEach(airportParameterModel -> airportParameterCodeParameterValueMap.put(airportParameterModel.getParameterCode(), airportParameterModel.getParameterCode()));
                return airportParameterCodeParameterValueMap;
            }
        }

        return null;
    }

    public Map<String, CityVO> validateCityCodes(String companyCode, Collection<String> cityCodes){

        if(cityCodes!=null && !cityCodes.isEmpty()){
            List<String> cityCodesList = cityCodes.stream().collect(Collectors.toList());
            List<CityModal> citiesList = cityComponent.findCities(cityCodesList);
            if(citiesList!=null && !citiesList.isEmpty()){
                Map<String, CityVO> cityVOMap = new HashMap<>();
                citiesList.forEach(cityModal -> cityVOMap.put(cityModal.getCityCode(), neoMastersMapper.cityModalToCityVO(cityModal)));
                return cityVOMap;
            }
        }


        return null;
    }

    public Map<String, String> findStationParametersByCode(String companyCode, String airportCode, Collection<String> stationCodes)
            throws BusinessException {

        Map<String, String> stationParameterMap = null;
        if(stationCodes!=null && !stationCodes.isEmpty()){
            stationParameterMap = new HashMap<>();
            for (String stationCode : stationCodes) {
                if(stationCode!=null && !stationCode.trim().isEmpty()){
                    StationDetailsModel stationDetailsModel = new StationDetailsModel();
                    stationDetailsModel.setCompanyCode(companyCode);
                    stationDetailsModel.setStationCode(airportCode);
                    stationDetailsModel.setStationParameterCode(stationCode);
                    List<Pair> stationParameterList = stationComponent.getStationParameter(stationDetailsModel);
                    if(stationParameterList!=null && !stationParameterList.isEmpty()){
                        Pair stationParameter = stationParameterList.get(0);
                        stationParameterMap.put((String) stationParameter.getName(), (String) stationParameter.getValue());
                    }
                }
            }
        }

        return stationParameterMap;
    }

    //sumitlr : 23rd November 2022. Using AirportComponent.validateAirport(String airportCode) calls same API.
    public AirportVO findAirportDetails(String companyCode, String airportCode)
            throws AirportBusinessException {

        AirportVO airportVO = null;
        if(airportCode!=null && !airportCode.trim().isEmpty()){
            AirportModal airportModal = airportComponent.validateAirport(airportCode);
            if(airportModal!=null){
                airportVO = neoMastersMapper.airportModalToAirportVO(airportModal);
            }
        }

        return airportVO;
    }

    //sumitlr : 23rd November 2022. Using AirportComponent.vavalidateAirports(List<String> airportCodes) calls same API.
    public Map<String, AirportValidationVO> validateAWBOrgDestRoutingAirports(String companyCode, Collection<String> airportCodes)
            throws AirportBusinessException {

        if(airportCodes!=null && !airportCodes.isEmpty()){
            Set<String> airportCodesList = airportCodes.stream().collect(Collectors.toSet());
            List<AirportModal> airportModalList = airportComponent.validateAirports(airportCodesList);
            if (airportModalList!=null && !airportModalList.isEmpty()) {
                Map<String, AirportValidationVO> airportCodeMap = new HashMap<>();
                airportModalList.forEach(airportModal -> airportCodeMap.put(airportModal.getAirportCode(), neoMastersMapper.airportModalToAirportValidationVO(airportModal)));
                return airportCodeMap;
            }
        }

        return null;
    }

    //sumitlr : 23rd November 2022. Using CommodityComponent.findCommodities(List<String> commodityCodes) calls same API.
    public HashMap<String, CommodityValidationVO> validateCommodityCodes(String companyCode, Collection<String> commodities)
            throws CommodityBusinessException {

        if(commodities!=null && !commodities.isEmpty()){
            List<String> commoditiesList = commodities.stream().collect(Collectors.toList());
            List<CommodityModal> commodityModalList = commodityComponent.findCommodities(commoditiesList);
            if(commodityModalList!=null && !commodityModalList.isEmpty()){
                HashMap<String, CommodityValidationVO> commodityModalHashMap = new HashMap<>();
                commodityModalList.forEach(commodityModal -> commodityModalHashMap.put(commodityModal.getCommodityCode(),  neoMastersMapper.commodityModalToCommodityValidationVO(commodityModal)));
                return commodityModalHashMap;
            }
        }
        
        return null;
    }

    public Collection<CommodityVO> findIATACommodity(String companyCode, CommodityFilterVO commodityFilterVO)
            throws CommodityBusinessException {

        Collection<CommodityVO> commodityVOCollection = null;
        if(commodityFilterVO!=null){
            if(commodityFilterVO.getCommodityCode()!=null && !commodityFilterVO.getCommodityCode().trim().isEmpty()){
                CommodityModal iataCommodity = getCommodityModal(commodityFilterVO.getCommodityCode());
                if(iataCommodity!=null){
                    commodityVOCollection = new ArrayList<>();
                    commodityVOCollection.add(neoMastersMapper.commodityModalToCommodityVO(iataCommodity));
                }
            }
        }

        return commodityVOCollection;
    }

    public Collection<IATACommodityVO> validateIataCommodity(String companyCode, String commodityItemNumber)
            throws CommodityBusinessException {
        Collection<IATACommodityVO> iataCommodityCollection = null;
        if(commodityItemNumber!=null){
            CommodityModal iataCommodity = getCommodityModal(commodityItemNumber);
            if(iataCommodity!=null){
                iataCommodityCollection = new ArrayList<>();
                iataCommodityCollection.add(neoMastersMapper.commodityModalToIATACommodityVO(iataCommodity));
            }
        }

        return iataCommodityCollection;
    }

    private CommodityModal getCommodityModal(String  commodityCode) throws CommodityBusinessException {
        return commodityComponent.findIATACommodity(commodityCode);
    }

    public double findConversionRate(CurrencyConvertorVO currencyConvertorVO) throws ExchangeRateNotFoundException {

        double conversionFactor = 0;
        if(currencyConvertorVO!=null){
            ExchangeRateModal exchangeRateModal = currencyComponent.findCurrencyExchangeRate(currencyConvertorVO.getFromCurrencyCode(), currencyConvertorVO.getToCurrencyCode(), currencyConvertorVO.getRatePickUpDate().toStringFormat("YYYY-MM-dd"), currencyConvertorVO.getRatingBasisType());
            if(exchangeRateModal!=null){
                conversionFactor = exchangeRateModal.getConversionFactor();
            }
        }

        return conversionFactor;
    }

    public Collection<CustomerVO> validateCustomerName(CustomerFilterVO customerFilterVO)
            throws CustomerBusinessException {

        if(customerFilterVO!=null){
            List<CustomerModel> customerModelList = customerComponent.listAllCustomers(neoMastersMapper.customerFilterVOToCustomerModel(customerFilterVO));
            if(customerModelList!=null && !customerModelList.isEmpty()){
                return neoMastersMapper.customerModelsToCustomerVOs(customerModelList);
            }
        }

        return null;
    }

    public Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> oneTimeList)
            throws OneTimeBusinessException {

        if(oneTimeList!=null && !oneTimeList.isEmpty()){
            OneTimeFilterModal oneTimeFilterModal = new OneTimeFilterModal();
            List<String> oneTimeCodesList = oneTimeList.stream().collect(Collectors.toList());
            oneTimeFilterModal.setOneTimeCodes(oneTimeCodesList);
            Map<String, List<OneTimeModal>> oneTimeModalMap = oneTimeComponent.findOneTimeValues(oneTimeFilterModal);
            if(oneTimeModalMap!=null && !oneTimeModalMap.isEmpty()){
                Map<String, Collection<OneTimeVO>> oneTimeVOMap = new HashMap<>();
                oneTimeModalMap.forEach((oneTimeCode, oneTimeModalList) -> oneTimeVOMap.put(oneTimeCode, neoMastersMapper.oneTimeModalToOneTimeVO(oneTimeModalList)));
                return oneTimeVOMap;
            }
        }

        return null;
    }

    public void validateDGRIncompatibilities(Collection<DGRIncompatibilityFilterVO> dgrIncompatibilityFilterVOs,
                                             String validationLevel, String companyCode){

        //TODO : sumitlr : 20th November 2022.
        //Unable to find DGRComponent
        //dgrComponent.validateDGRIncompatibilities;


    }

    public Collection<DGRThresholdMasterVO> fetchDGThresholdDetails(DGRThresholdFilterVO dgrThresholdFilterVO){

        //TODO : sumitlr : 20th November 2022.
        //Unable to find DGRComponent
        //dgrComponent.findDGRThresholdDetails;
        return null;
    }

    //TODO : sumitlr : 20th November 2022. Confirm the logic if more than 1 GeneralMasterGroupDetailsVO comes as input.
    //Confirm if Output of this API is GroupName
    //SharedGeneralMasterGroupingProxy.findGroupsDetails & SharedGeneralMasterGroupingProductProxy.findGroupsDetails will call this method.
    public Collection<GeneralMasterGroupVO> findGroupsDetails(Collection<GeneralMasterGroupDetailsVO> generalMasterGroupDetailsVOs){

        Collection<GeneralMasterGroupVO> generalMasterGroupVOCollection = null;
        if(generalMasterGroupDetailsVOs!=null && !generalMasterGroupDetailsVOs.isEmpty()){
            generalMasterGroupVOCollection = new ArrayList<>();
            for (GeneralMasterGroupDetailsVO generalMasterGroupDetailsVO : generalMasterGroupDetailsVOs) {
                if(generalMasterGroupDetailsVO!=null){
                    Collection<String> groupNamesOfGroupTypeCollection = sharedComponent.findGroupNamesofGroupType(generalMasterGroupDetailsVO.getGroupType(), generalMasterGroupDetailsVO.getGroupCategory());
                    if(groupNamesOfGroupTypeCollection!=null && !groupNamesOfGroupTypeCollection.isEmpty()){
                        for (String groupNameOfGroupType : groupNamesOfGroupTypeCollection) {
                            if(groupNameOfGroupType!=null && !groupNameOfGroupType.trim().isEmpty()){
                                GeneralMasterGroupVO generalMasterGroupVO = new GeneralMasterGroupVO();
                                generalMasterGroupVO.setGroupName(groupNameOfGroupType);
                                generalMasterGroupVOCollection.add(generalMasterGroupVO);
                            }
                        }
                    }
                }
            }
        }

        return generalMasterGroupVOCollection;
    }

    public Collection<GeneralMasterGroupVO> findGroupNamesofGroupEntity(GeneralMasterGroupFilterVO generalMasterGroupFilterVO){

        Collection<GeneralMasterGroupVO> generalMasterGroupVOCollection = null;
        if(generalMasterGroupFilterVO!=null){
            GeneralMasterGroupWrapper generalMasterGroupWrapper = sharedComponent.findGroupNamesofGroupEntity(generalMasterGroupFilterVO.getGroupCategory(),generalMasterGroupFilterVO.getGroupEntity());
            if(generalMasterGroupWrapper!=null){
                List<GeneralMasterGroup> generalMasterGroupList = generalMasterGroupWrapper.getGeneralMasterGroups();
                if(generalMasterGroupList!=null && !generalMasterGroupList.isEmpty()){
                    generalMasterGroupVOCollection = new ArrayList<>();
                    for(GeneralMasterGroup generalMasterGroup : generalMasterGroupList){
                        if(generalMasterGroup!=null){
                            GeneralMasterGroupVO generalMasterGroupVO = neoMastersMapper.generalMasterGroupToGeneralMasterGroupVO(generalMasterGroup);
                            if(generalMasterGroupVO!=null){
                                generalMasterGroupVOCollection.add(generalMasterGroupVO);
                            }
                        }
                    }
                }
            }
        }

        return generalMasterGroupVOCollection;
    }


    public Collection<String> findGroupNamesOfEntity(GeneralMasterGroupFilterVO generalMasterGroupFilterVO){

        Collection<String> groupNames = null;
        if(generalMasterGroupFilterVO!=null){
            GeneralMasterGroupWrapper generalMasterGroupWrapper = sharedComponent.findGroupNamesofGroupEntity(generalMasterGroupFilterVO.getGroupCategory(), generalMasterGroupFilterVO.getGroupEntity());
            if(generalMasterGroupWrapper!=null){
                List<GeneralMasterGroup> generalMasterGroupList = generalMasterGroupWrapper.getGeneralMasterGroups();
                if(generalMasterGroupList!=null && !generalMasterGroupList.isEmpty()){
                    groupNames = new ArrayList<>();
                    for(GeneralMasterGroup generalMasterGroup : generalMasterGroupList){
                        if(generalMasterGroup!=null){
                            GeneralMasterGroupVO generalMasterGroupVO = neoMastersMapper.generalMasterGroupToGeneralMasterGroupVO(generalMasterGroup);
                            if(generalMasterGroupVO!=null){
                                groupNames.add(generalMasterGroupVO.getGroupName());
                            }
                        }
                    }
                }
            }
        }

        return groupNames;
    }

    public Collection<GeneralMasterGroupDetailsVO> findGroupNamesforElements(GeneralMasterGroupFilterVO generalMasterGroupFilterVO){

        Collection<GeneralMasterGroupDetailsVO> generalMasterGroupDetailsVOs = null;
        if(generalMasterGroupFilterVO!=null){
            GeneralMasterGroupWrapper generalMasterGroupWrapper = sharedComponent.findGroupNamesofGroupEntity(generalMasterGroupFilterVO.getGroupCategory(), generalMasterGroupFilterVO.getGroupEntity());
            if(generalMasterGroupWrapper!=null){
                List<GeneralMasterGroup> generalMasterGroupList = generalMasterGroupWrapper.getGeneralMasterGroups();
                if(generalMasterGroupList!=null && !generalMasterGroupList.isEmpty()){
                    generalMasterGroupDetailsVOs = new ArrayList<>();
                    for(GeneralMasterGroup generalMasterGroup : generalMasterGroupList){
                        if(generalMasterGroup!=null){
                            generalMasterGroupDetailsVOs.add(neoMastersMapper.generalMasterGroupToGeneralMasterGroupDetailsVO(generalMasterGroup));
                        }
                    }
                }
            }
        }

        return generalMasterGroupDetailsVOs;
    }

    public Collection<SCCValidationVO> validateSCCCodes(String companyCode, Collection<String> sccCodes){

        if(sccCodes!=null && !sccCodes.isEmpty()){
            SccFilterModel sccFilterModel = new SccFilterModel();
            List<String> sccCodeList = sccCodes.stream().collect(Collectors.toList());
            sccFilterModel.setSccCode(sccCodeList);
            List<SccDetailModel> sccDetailModelList = sccComponent.findSccs(sccFilterModel);
            if(sccDetailModelList!=null && !sccDetailModelList.isEmpty()){
                return neoMastersMapper.sccDetailModelToSCCValidationVO(sccDetailModelList);
            }
        }

        return null;
    }

    public Collection<SCCVO> suggestSCCs(SCCSuggestionVO sccSuggestionVO){

        if(sccSuggestionVO!=null){
            List<SccModal> sccModalList = sharedComponent.suggestSccs(neoMastersMapper.sCCSuggestionVOToSCCSuggestionFilter(sccSuggestionVO));
            if(sccModalList!=null && !sccModalList.isEmpty()){
                return neoMastersMapper.sccModalsToSCCVOs(sccModalList);
            }
        }

        return null;
    }

    public SCCSuggestionVO suggestSCCForACC(SCCSuggestionVO sccSuggestionVO){

        sccSuggestionVO.setSccVos(suggestSCCs(sccSuggestionVO));

        //sumitlr : 21st November 2022. Returning same SCCSuggestionVO as "suggestionVO.getScreeningRouteValidationVos()" is being used
        //It is being used in SuggestSCCEnricher.enrich method.
        return sccSuggestionVO;
    }

    public List<ULDModal> findULDDetails(Set<String> uldCodes) {
        if (Objects.isNull(uldCodes) || uldCodes.isEmpty()) {
            return List.of();
        }
        return uldComponent.findULDDetails(uldCodes);
    }

    public ProductVO findProductDetails(String companyCode, String productCode){

        ProductVO productVO = null;
        if(productCode!=null && !productCode.trim().isEmpty()){
            ProductFilterModal productFilterModal = new ProductFilterModal();
            productFilterModal.setProductCode(productCode);
            List<ProductModel> productModelList = productComponent.findProducts(productFilterModal);
            if(productModelList!=null && !productModelList.isEmpty()){
                //Getting 1st Object as in return we need to return only single ProductVO.
                productVO = neoMastersMapper.productModelToProductVO(productModelList.get(0));
            }
        }


        return productVO;
    }

    public AirlineValidationVO validateNumericCode(String companyCode, String numericCode) throws BusinessException {

        AirlineValidationVO airlineValidationVO = null;
        if(numericCode!=null && !numericCode.trim().isEmpty()){
            AirlineModel airlineModel = airlineWebComponent.validateNumericCode(String.valueOf(numericCode));
            if(airlineModel!=null){
                airlineValidationVO = neoMastersMapper.airlineModelToAirlineValidationVO(airlineModel);
            }
        }

        return airlineValidationVO;
    }

    //1. Use AirlineModel validateNumericCode(String numericCode) and use isPartnerAirline
    //2. Then invoke this for airline params Set<AirlineParameterModel>
    // findAirlineParametersByCode(int airlineIdentifier, List<String> parameterCodes)
    public AirlineVO findAirlineParametersByCode(int airlineIdentifier, List<String> parameterCodes)  {

        AirlineVO airlineVO = null;
        Set<AirlineParameterModel> airlineParametersByCode = airlineWebComponent.findAirlineParametersByCode(airlineIdentifier, parameterCodes);
        if(airlineParametersByCode!=null && !airlineParametersByCode.isEmpty()){
            airlineVO.setAirlineParameterVOs(neoMastersMapper.airlineParameterModelToAirlineParametersVO(airlineParametersByCode));
        }

        return airlineVO;
    }

    public List<Pair> findSystemParameters(List<String> parameterCodes) throws BusinessException{
        List<Pair> pair = null;
        if(parameterCodes != null){
            pair = sharedComponent.findSystemParameters(parameterCodes);
        }
        return pair;
    }

    public String getDefaultCurrencyForStation(String stationCode) {
        return Optional.ofNullable(stationComponent.findStationDetails(stationCode))
                .map(StationDetailsModel::getStationCurrency)
                .orElse(null);
    }

    public Map<String,String> findSystemParameterByCodes(Collection<String> codes) throws BusinessException {
        var proxy= ContextUtil.getInstance().getBean(SharedDefaultsProxy.class);
        return proxy.findSystemParameterByCodes(codes);
    }

    public  List<Pair>  findStationParametersByCode(String companyCode, String stationCode,String parameterCode) throws BusinessException {
        StationDetailsModel stationDetailsModel=new StationDetailsModel();
        stationDetailsModel.setCompanyCode(companyCode);
        stationDetailsModel.setStationCode(stationCode);
        stationDetailsModel.setStationParameterCode(parameterCode);
        try {
            return stationComponent.getStationParameter(stationDetailsModel);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }

    }
    public AirlineValidationVO findAirline(String companyCode, int carrierId) throws SharedProxyException {
        AirlineModel airlineModel=new AirlineModel();
        airlineModel.setAirlineIdentifier(carrierId);
        List<AirlineModel> airlineModels = airlineWebComponent.findAirlineValidityDetails(List.of(airlineModel));
        airlineModel= airlineModels.get(0);
        return   neoMastersMapper.airlineModelToAirlineValidationVO(airlineModel);
    }

    public String findSystemParameterValue(String syspar)  {
        try {
            return sharedComponent.findSystemParameterByCode(syspar);
        } catch (BusinessException e) {
            return null;
        }
    }

    public Collection<CustomerLovVO> findCustomers(CustomerFilterVO customerFilterVO) {
        try {
           return neoMastersMapper.customerModelsToCustomerLovVOs(customerComponent.listAllCustomers(neoMastersMapper.customerFilterVOToCustomerModel(customerFilterVO)));
        } catch (CustomerBusinessException e) {
            return Collections.emptyList();
        }
    }

    public Collection<ProductValidationVO> findProductsByName(String companyCode, String product) {
        ProductVO productVO = null;
        if(product!=null && !product.trim().isEmpty()){
            ProductFilterModal productFilterModal = new ProductFilterModal();
            productFilterModal.setProductName(product);
            List<ProductModel> productModelList = productComponent.findProducts(productFilterModal);
            if(productModelList!=null && !productModelList.isEmpty()){
                //Getting 1st Object as in return we need to return only single ProductVO.
                return neoMastersMapper.productModelsToProductValidationVO(productModelList);
            }
        }

        return Collections.emptyList();


    }

    public Collection<CustomerVO> getAllCustomerDetails(CustomerFilterVO customerFilterVO) {
        try {
            return neoMastersMapper.customerModelsToCustomerVOs(customerComponent.listAllCustomers(neoMastersMapper.customerFilterVOToCustomerModel(customerFilterVO)));
        } catch (CustomerBusinessException e) {
            return Collections.emptyList();
        }
    }

    public Map<String, String> findAirportParametersByCode(String companyCode, String airport, Collection<String> parCodes) {
        Set<String> parcodes = new HashSet<>();
        parcodes.addAll(parCodes);
        return  airportComponent.findAirportParametersByCode(airport,parcodes).stream()
                .collect(Collectors.toMap(airportParameterModel ->  airportParameterModel.getParameterCode(),
                        airportParameterModel->  airportParameterModel.getParameterValue()));
    }
}
