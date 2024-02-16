package com.ibsplc.neoicargo.mailmasters.mapper;

import com.ibsplc.icargo.business.shared.area.city.vo.CityVO;
import com.ibsplc.neoicargo.mailmasters.model.*;
import com.ibsplc.neoicargo.mailmasters.vo.*;
import com.ibsplc.neoicargo.masters.area.city.modal.CityModal;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.NullValueMappingStrategy;
import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, uses = {
		MeasureMapper.class, LocalDateMapper.class, EBLMoneyMapper.class })
public interface MailTrackingDefaultsMapper {
	public RdtMasterFilterVO rdtMasterFilterModelToRdtMasterFilterVO(RdtMasterFilterModel rdtMasterFilterModel);

	public MailMasterDataFilterVO mailMasterDataFilterModelToMailMasterDataFilterVO(
			MailMasterDataFilterModel mailMasterDataFilterModel);

	public Collection<PartnerCarrierVO> partnerCarrierModelsToPartnerCarrierVO(
			Collection<PartnerCarrierModel> partnerCarrierModel);

	public OfficeOfExchangeModel officeOfExchangeVOToOfficeOfExchangeModel(OfficeOfExchangeVO officeOfExchangeVO);

	public Collection<OfficeOfExchangeVO> officeOfExchangeModelsToOfficeOfExchangeVO(
			Collection<OfficeOfExchangeModel> officeOfExchangeModel);

	public MailboxIdModel mailboxIdVOToMailboxIdModel(MailboxIdVO mailboxIdVO);

	public Collection<MailRdtMasterModel> mailRdtMasterVOsToMailRdtMasterModel(
			Collection<MailRdtMasterVO> mailRdtMasterVO);

	public Collection<CoTerminusVO> coTerminusModelsToCoTerminusVO(Collection<CoTerminusModel> coTerminusModel);

	public Collection<MailServiceLevelVO> mailServiceLevelModelsToMailServiceLevelVO(
			Collection<MailServiceLevelModel> mailServiceLevelModel);

	public Collection<PartnerCarrierModel> partnerCarrierVOsToPartnerCarrierModel(
			Collection<PartnerCarrierVO> partnerCarrierVO);

	public Collection<IncentiveConfigurationVO> incentiveConfigurationModelsToIncentiveConfigurationVO(
			Collection<IncentiveConfigurationModel> incentiveConfigurationModel);

	public OfficeOfExchangeVO officeOfExchangeModelToOfficeOfExchangeVO(OfficeOfExchangeModel officeOfExchangeModel);

	public CoTerminusFilterVO coTerminusFilterModelToCoTerminusFilterVO(CoTerminusFilterModel coTerminusFilterModel);

	public Collection<MailRdtMasterVO> mailRdtMasterModelsToMailRdtMasterVO(
			Collection<MailRdtMasterModel> mailRdtMasterModel);

	public RoutingIndexVO routingIndexModelToRoutingIndexVO(RoutingIndexModel routingIndexModel);

	public IncentiveConfigurationFilterVO incentiveConfigurationFilterModelToIncentiveConfigurationFilterVO(
			IncentiveConfigurationFilterModel incentiveConfigurationFilterModel);

	public Collection<RoutingIndexModel> routingIndexVOsToRoutingIndexModel(Collection<RoutingIndexVO> routingIndexVO);

	public Collection<RoutingIndexVO> routingIndexModelsToRoutingIndexVO(
			Collection<RoutingIndexModel> routingIndexModel);

	public GPAContractFilterVO gPAContractFilterModelToGPAContractFilterVO(
			GPAContractFilterModel gPAContractFilterModel);

	public Collection<MailHandoverVO> mailHandoverModelsToMailHandoverVO(
			Collection<MailHandoverModel> mailHandoverModel);

	public Collection<OfficeOfExchangeModel> officeOfExchangeVOsToOfficeOfExchangeModel(
			Collection<OfficeOfExchangeVO> officeOfExchangeVO);

	public Collection<CoTerminusModel> coTerminusVOsToCoTerminusModel(Collection<CoTerminusVO> coTerminusVO);

	public PostalAdministrationModel postalAdministrationVOToPostalAdministrationModel(
			PostalAdministrationVO postalAdministrationVO);

	public MailHandoverFilterVO mailHandoverFilterModelToMailHandoverFilterVO(
			MailHandoverFilterModel mailHandoverFilterModel);

	public Page<PostalAdministrationModel> postalAdministrationVOsToPostalAdministrationModel(
			Page<PostalAdministrationVO> postalAdministrationVO);

	public PostalAdministrationDetailsVO postalAdministrationDetailsModelToPostalAdministrationDetailsVO(
			PostalAdministrationDetailsModel postalAdministrationDetailsModel);

	public Page<MailServiceStandardModel> mailServiceStandardVOsToMailServiceStandardModel(
			Page<MailServiceStandardVO> mailServiceStandardVO);

	public Collection<GPAContractModel> gPAContractVOsToGPAContractModel(Collection<GPAContractVO> gPAContractVO);

	public Collection<MLDConfigurationModel> mLDConfigurationVOsToMLDConfigurationModel(
			Collection<MLDConfigurationVO> mLDConfigurationVO);

	public Page<MailHandoverModel> mailHandoverVOsToMailHandoverModel(Page<MailHandoverVO> mailHandoverVO);

	public Page<MailSubClassModel> mailSubClassVOsToMailSubClassModel(Page<MailSubClassVO> mailSubClassVO);

	public Page<MailBoxIdLovModel> mailBoxIdLovVOsToMailBoxIdLovModel(Page<MailBoxIdLovVO> mailBoxIdLovVO);

	public MailbagVO mailbagModelToMailbagVO(MailbagModel mailbagModel);

	public MailboxIdVO mailboxIdModelToMailboxIdVO(MailboxIdModel mailboxIdModel);

	public MailServiceStandardFilterVO mailServiceStandardFilterModelToMailServiceStandardFilterVO(
			MailServiceStandardFilterModel mailServiceStandardFilterModel);

	@Mapping(target = "postalAdministrationDetailsVOs", ignore = true)
	public PostalAdministrationVO postalAdministrationModelToPostalAdministrationVO(
			PostalAdministrationModel postalAdministrationModel);

	public USPSPostalCalendarFilterVO uSPSPostalCalendarFilterModelToUSPSPostalCalendarFilterVO(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel);

	public USPSPostalCalendarModel uSPSPostalCalendarVOToUSPSPostalCalendarModel(
			USPSPostalCalendarVO uSPSPostalCalendarVO);

	public MLDConfigurationFilterVO mLDConfigurationFilterModelToMLDConfigurationFilterVO(
			MLDConfigurationFilterModel mLDConfigurationFilterModel);

	public Collection<GPAContractVO> gPAContractModelsToGPAContractVO(Collection<GPAContractModel> gPAContractModel);

	public Collection<USPSPostalCalendarVO> uSPSPostalCalendarModelsToUSPSPostalCalendarVO(
			Collection<USPSPostalCalendarModel> uSPSPostalCalendarModel);

	public Collection<MailSubClassVO> mailSubClassModelsToMailSubClassVO(
			Collection<MailSubClassModel> mailSubClassModel);

	public Collection<USPSPostalCalendarModel> uSPSPostalCalendarVOsToUSPSPostalCalendarModel(
			Collection<USPSPostalCalendarVO> uSPSPostalCalendarVO);

	public MailRuleConfigVO mailRuleConfigModelToMailRuleConfigVO(MailRuleConfigModel mailRuleConfigModel);

	public Collection<MLDConfigurationVO> mLDConfigurationModelsToMLDConfigurationVO(
			Collection<MLDConfigurationModel> mLDConfigurationModel);

	public Collection<MailSubClassModel> mailSubClassVOsToMailSubClassModel(Collection<MailSubClassVO> mailSubClassVO);

	public Collection<PostalAdministrationModel> postalAdministrationVOsToPostalAdministrationModel(
			Collection<PostalAdministrationVO> postalAdministrationVO);

	public Page<OfficeOfExchangeModel> officeOfExchangeVOsToOfficeOfExchangeModel(
			Page<OfficeOfExchangeVO> officeOfExchangeVO);

	public Collection<MailServiceStandardVO> mailServiceStandardModelsToMailServiceStandardVO(
			Collection<MailServiceStandardModel> mailServiceStandardModel);

	public Collection<IncentiveConfigurationModel> incentiveConfigurationVOsToIncentiveConfigurationModel(
			Collection<IncentiveConfigurationVO> incentiveConfigurationVO);

	public PostalAdministrationDetailsModel postalAdministrationDetailsVOToPostalAdministrationDetailsModel(
			PostalAdministrationDetailsVO postalAdministrationDetailsVO);

	HashMap<String,Collection<PostalAdministrationDetailsVO>> mapPostalAdministrationDetailsVOsToPostalAdministrationDetailsModels(HashMap<String,Collection<PostalAdministrationDetailsModel>> value);

	HashMap<String,Collection<PostalAdministrationDetailsModel>> mapPostalAdministrationDetailsModelsToPostalAdministrationDetailsVOs(HashMap<String,Collection<PostalAdministrationDetailsVO>> value);

	Collection<PostalAdministrationDetailsModel> postalAdministrationDetailsModelsToPostalAdministrationDetailsVOs(Collection<PostalAdministrationDetailsVO> value);

	Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsVOsToPostalAdministrationDetailsModals(Collection<PostalAdministrationDetailsModel> value);

	 MailBoxIdLovModel mailBoxIdLovVOToMailBoxIdLovModel(MailBoxIdLovVO mailBoxIdLovVO);
List<CityVO> cityModelsToCityVO(List<CityModal> cityModal);
	 @Mapping(target ="lastUpdateTime", ignore = true)
	 CityVO cityModelToCityVO (CityModal cityModal);

	@Mapping(target = "postalAdministrationDetailsVOs", ignore = true)
	 com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO postalAdministartionVOMap(
			PostalAdministrationVO postalAdministrationVO) ;
	Collection<com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO> postalAdministrationVOSNeoTopostalAdministrationVOSClassic(Collection<PostalAdministrationVO> paDetails);

	Collection<com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO> mailSubClassVOsNeoToMailSubClassVOsClassic(Collection<MailSubClassVO> subclassDetails);

	Collection<com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO> officeOfExchangeVOSNeoToofficeOfExchangeVOSClassic(Collection<OfficeOfExchangeVO> officeOfExchangeDetails);

	public Collection<MailEventModel> mailEventVOToMailEventModel(
			Collection<MailEventVO> mailEventVOs);
	public MailEventVO mailEventModelToMailEventVO(MailEventModel mailEventModel);
}
