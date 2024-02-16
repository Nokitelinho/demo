package com.ibsplc.neoicargo.mail.mapper;

import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationReportVO;

import com.ibsplc.neoicargo.mail.vo.MailHandedOverFilterVO;

import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;
import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.mail.model.*;
import com.ibsplc.neoicargo.mail.vo.*;
import com.ibsplc.neoicargo.mailmasters.model.MailboxIdModel;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, uses = {
		MeasureMapper.class, LocalDateMapper.class, EBLMoneyMapper.class })
public interface MailOperationsMapper {
	Page<MailTransitModel> mailTransitVOsToMailTransitModel(Page<MailTransitVO> mailTransitVO);

	ArrayList<MailbagModel> mailbagVOsToMailbagModel(ArrayList<MailbagVO> mailbagVO);

	 ArriveAndImportMailVO arriveAndImportMailModelToArriveAndImportMailVO(
			ArriveAndImportMailModel arriveAndImportMailModel);

	 Collection<MailMonitorSummaryModel> mailMonitorSummaryVOsToMailMonitorSummaryModel(
			Collection<MailMonitorSummaryVO> mailMonitorSummaryVO);

	 Page<MailOnHandDetailsModel> mailOnHandDetailsVOsToMailOnHandDetailsModel(
			Page<MailOnHandDetailsVO> mailOnHandDetailsVO);

	 SearchContainerFilterVO searchContainerFilterModelToSearchContainerFilterVO(
			SearchContainerFilterModel searchContainerFilterModel);

	 Collection<MailDiscrepancyModel> mailDiscrepancyVOsToMailDiscrepancyModel(
			Collection<MailDiscrepancyVO> mailDiscrepancyVO);

	 EDIInterchangeVO eDIInterchangeModelToEDIInterchangeVO(EDIInterchangeModel eDIInterchangeModel);

	CarditEnquiryFilterVO carditEnquiryFilterModelToCarditEnquiryFilterVO(
			CarditEnquiryFilterModel carditEnquiryFilterModel);

	Collection<OperationalFlightModel> operationalFlightVOsToOperationalFlightModel(
			Collection<OperationalFlightVO> operationalFlightVO);

	MailInConsignmentVO mailInConsignmentModelToMailInConsignmentVO(
			MailInConsignmentModel mailInConsignmentModel);

	Map<String, ContainerAssignmentModel> containerAssignmentVOsToContainerAssignmentModel(
			Map<String, ContainerAssignmentVO> containerAssignmentVO);

	 ConsignmentFilterVO consignmentFilterModelToConsignmentFilterVO(
			ConsignmentFilterModel consignmentFilterModel);

	Collection<MLDMasterVO> mLDMasterModelsToMLDMasterVO(Collection<MLDMasterModel> mLDMasterModel);

	Collection<MLDMasterModel> mLDMasterVOsToMLDMasterModels(Collection<MLDMasterVO> MLDMasterVOs);

	List<MailArrivalModel> mailArrivalVOsToMailArrivalModel(List<MailArrivalVO> mailArrivalVO);

	Collection<MailHistoryRemarksModel> mailHistoryRemarksVOsToMailHistoryRemarksModel(
			Collection<MailHistoryRemarksVO> mailHistoryRemarksVO);

	Page<ContainerModel> containerVOsToContainerModel(Page<ContainerVO> containerVO);

	MailManifestVO mailManifestModelToMailManifestVO(MailManifestModel mailManifestModel);

	MailArrivalFilterVO mailArrivalFilterModelToMailArrivalFilterVO(
			MailArrivalFilterModel mailArrivalFilterModel);

	 SecurityScreeningValidationFilterVO securityScreeningValidationFilterModelToSecurityScreeningValidationFilterVO(
			SecurityScreeningValidationFilterModel securityScreeningValidationFilterModel);

	 MailAcceptanceVO mailAcceptanceModelToMailAcceptanceVO(MailAcceptanceModel mailAcceptanceModel);

	 MailMonitorFilterVO mailMonitorFilterModelToMailMonitorFilterVO(
			MailMonitorFilterModel mailMonitorFilterModel);

	 Collection<ConsignmentDocumentVO> consignmentDocumentModelsToConsignmentDocumentVO(
			Collection<ConsignmentDocumentModel> consignmentDocumentModel);

	 MailbagInULDForSegmentModel mailbagInULDForSegmentVOToMailbagInULDForSegmentModel(
			MailbagInULDForSegmentVO mailbagInULDForSegmentVO);

	 Collection<ResditEventModel> resditEventVOsToResditEventModel(Collection<ResditEventVO> resditEventVO);

	 Collection<DespatchDetailsModel> despatchDetailsVOsToDespatchDetailsModel(
			Collection<DespatchDetailsVO> despatchDetailsVO);

	 OperationalFlightModel operationalFlightVOToOperationalFlightModel(OperationalFlightVO operationalFlightVO);

	 TransferManifestModel transferManifestVOToTransferManifestModel(TransferManifestVO transferManifestVO);

	 PreAdviceModel preAdviceVOToPreAdviceModel(PreAdviceVO preAdviceVO);

	 MailbagModel mailbagVOToMailbagModel(MailbagVO mailbagVO);

	 Page<TransferManifestModel> transferManifestVOsToTransferManifestModel(
			Page<TransferManifestVO> transferManifestVO);

	 ContainerVO containerModelToContainerVO(ContainerModel containerModel);

	 MailArrivalVO mailArrivalModelToMailArrivalVO(MailArrivalModel mailArrivalModel);

	 ConsignmentDocumentVO consignmentDocumentModelToConsignmentDocumentVO(
			ConsignmentDocumentModel consignmentDocumentModel);

	 Collection<ForceMajeureRequestVO> forceMajeureRequestModelsToForceMajeureRequestVO(
			Collection<ForceMajeureRequestModel> forceMajeureRequestModel);

	 MailAuditHistoryFilterVO mailAuditHistoryFilterModelToMailAuditHistoryFilterVO(
			MailAuditHistoryFilterModel mailAuditHistoryFilterModel);

	 OffloadModel offloadVOToOffloadModel(OffloadVO offloadVO);

	 Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerModelsToFlightLoadPlanContainerVO(
			Collection<FlightLoadPlanContainerModel> flightLoadPlanContainerModel);

	 Collection<MailbagHistoryVO> mailbagHistoryModelsToMailbagHistoryVO(
			Collection<MailbagHistoryModel> mailbagHistoryModel);

	 MailScreeningFilterVO mailScreeningFilterModelToMailScreeningFilterVO(
			MailScreeningFilterModel mailScreeningFilterModel);

	 Collection<DamagedMailbagModel> damagedMailbagVOsToDamagedMailbagModel(
			Collection<DamagedMailbagVO> damagedMailbagVO);

	 Collection<ScannedMailDetailsModel> scannedMailDetailsVOsToScannedMailDetailsModel(
			Collection<ScannedMailDetailsVO> scannedMailDetailsVO);

	 MailMRDVO mailMRDModelToMailMRDVO(MailMRDModel mailMRDModel);

	 CarditEnquiryVO carditEnquiryModelToCarditEnquiryVO(CarditEnquiryModel carditEnquiryModel);

	 ScannedMailDetailsVO scannedMailDetailsModelToScannedMailDetailsVO(
			ScannedMailDetailsModel scannedMailDetailsModel);

	 Page<MailAcceptanceModel> mailAcceptanceVOsToMailAcceptanceModel(Page<MailAcceptanceVO> mailAcceptanceVO);

	 Collection<SecurityScreeningValidationModel> securityScreeningValidationVOsToSecurityScreeningValidationModel(
			Collection<SecurityScreeningValidationVO> securityScreeningValidationVO);

	 Collection<MailAcceptanceModel> mailAcceptanceVOsToMailAcceptanceModel(
			Collection<MailAcceptanceVO> mailAcceptanceVO);

	 ScannedMailDetailsModel scannedMailDetailsVOToScannedMailDetailsModel(
			ScannedMailDetailsVO scannedMailDetailsVO);

	 AutoAttachAWBJobScheduleVO autoAttachAWBJobScheduleModelToAutoAttachAWBJobScheduleVO(
			AutoAttachAWBJobScheduleModel autoAttachAWBJobScheduleModel);

	 ContainerModel containerVOToContainerModel(ContainerVO containerVO);

	 MailTransitFilterVO mailTransitFilterModelToMailTransitFilterVO(
			MailTransitFilterModel mailTransitFilterModel);

	 Collection<DSNVO> dSNModelsToDSNVO(Collection<DSNModel> dSNModel);

	 ContainerAssignmentModel containerAssignmentVOToContainerAssignmentModel(
			ContainerAssignmentVO containerAssignmentVO);

	 Collection<MailBagAuditHistoryModel> mailBagAuditHistoryVOsToMailBagAuditHistoryModel(
			Collection<MailBagAuditHistoryVO> mailBagAuditHistoryVO);

	 Collection<MailbagHistoryModel> mailbagHistoryVOsToMailbagHistoryModel(
			Collection<MailbagHistoryVO> mailbagHistoryVO);

	 MailbagVO mailbagModelToMailbagVO(MailbagModel mailbagModel);

	 MailInboundAutoAttachAWBJobScheduleVO mailInboundAutoAttachAWBJobScheduleModelToMailInboundAutoAttachAWBJobScheduleVO(
			MailInboundAutoAttachAWBJobScheduleModel mailInboundAutoAttachAWBJobScheduleModel);

	 Collection<ConsignmentScreeningVO> consignmentScreeningModelsToConsignmentScreeningVO(
			Collection<ConsignmentScreeningModel> consignmentScreeningModel);

	 Collection<ScannedMailDetailsVO> scannedMailDetailsModelsToScannedMailDetailsVO(
			Collection<ScannedMailDetailsModel> scannedMailDetailsModel);

	 MailbagEnquiryFilterVO mailbagEnquiryFilterModelToMailbagEnquiryFilterVO(
			MailbagEnquiryFilterModel mailbagEnquiryFilterModel);

	 SecurityScreeningValidationModel securityScreeningValidationVOToSecurityScreeningValidationModel(
			SecurityScreeningValidationVO securityScreeningValidationVO);

	 Map<String, Collection<MLDMasterModel>> mLDMasterVOsToMLDMasterModel(
			Map<String, Collection<MLDMasterVO>> mLDMasterVO);

	 Collection<MailArrivalVO> mailArrivalModelsToMailArrivalVO(Collection<MailArrivalModel> mailArrivalModel);

	 ForceMajeureRequestFilterVO forceMajeureRequestFilterModelToForceMajeureRequestFilterVO(
			ForceMajeureRequestFilterModel forceMajeureRequestFilterModel);

	 HbaMarkingVO hbaMarkingModelToHbaMarkingVO(HbaMarkingModel hbaMarkingModel);

	 Collection<MailUploadModel> mailUploadVOsToMailUploadModel(Collection<MailUploadVO> mailUploadVO);

	 Collection<ConsignmentDocumentModel> consignmentDocumentVOsToConsignmentDocumentModel(
			Collection<ConsignmentDocumentVO> consignmentDocumentVO);

	 Collection<DSNModel> dSNVOsToDSNModel(Collection<DSNVO> dSNVO);

	 Collection<MailUploadVO> mailUploadModelsToMailUploadVO(Collection<MailUploadModel> mailUploadModel);

	 MailAuditFilterVO mailAuditFilterModelToMailAuditFilterVO(MailAuditFilterModel mailAuditFilterModel);

	DSNEnquiryFilterVO dSNEnquiryFilterModelToDSNEnquiryFilterVO(DSNEnquiryFilterModel dSNEnquiryFilterModel);

	Collection<MailBookingDetailVO> mailBookingDetailModelsToMailBookingDetailVO(
			Collection<MailBookingDetailModel> mailBookingDetailModel);

	 Collection<FlightLoadPlanContainerModel> flightLoadPlanContainerVOsToFlightLoadPlanContainerModel(
			Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVO);

	 TransferManifestVO transferManifestModelToTransferManifestVO(TransferManifestModel transferManifestModel);

	 Collection<ContainerDetailsVO> containerDetailsModelsToContainerDetailsVO(
			Collection<ContainerDetailsModel> containerDetailsModel);

	 Page<DespatchDetailsModel> despatchDetailsVOsToDespatchDetailsModel(
			Page<DespatchDetailsVO> despatchDetailsVO);

	 OffloadVO offloadModelToOffloadVO(OffloadModel offloadModel);

	 AWBDetailModel aWBDetailVOToAWBDetailModel(AWBDetailVO aWBDetailVO);

	 OffloadFilterVO offloadFilterModelToOffloadFilterVO(OffloadFilterModel offloadFilterModel);

	 ConsignmentDocumentModel consignmentDocumentVOToConsignmentDocumentModel(
			ConsignmentDocumentVO consignmentDocumentVO);

	 TransferManifestFilterVO transferManifestFilterModelToTransferManifestFilterVO(
			TransferManifestFilterModel transferManifestFilterModel);

	 Page<DSNModel> dSNVOsToDSNModel(Page<DSNVO> dSNVO);

	 MailManifestModel mailManifestVOToMailManifestModel(MailManifestVO mailManifestVO);

	 ContainerDetailsVO containerDetailsModelToContainerDetailsVO(ContainerDetailsModel containerDetailsModel);

	@Mapping(target = "containerDetails", expression = "java(containerDetailsModelsInboundToContainerDetailsVO(mailArrivalModel.getContainerDetails(), measureMapper))")
	MailArrivalVO mailArrivalModelInboundToMailArrivalVO(MailArrivalModel mailArrivalModel, @Context MeasureMapper measureMapper);

	@IterableMapping(qualifiedByName = "containerDetailsModelInboundToContainerDetailsVO")
	@Named("containerDetailsModelsInboundToContainerDetailsVO")
	Collection<ContainerDetailsVO> containerDetailsModelsInboundToContainerDetailsVO(
			Collection<ContainerDetailsModel> containerDetailsModel, @Context MeasureMapper measureMapper);

	@Mapping(target = "receivedWeight", ignore = true)
	@Mapping(target = "deliveredWeight", ignore = true)
	@Mapping(target = "tareWeight", ignore = true)
	@Mapping(target = "mailbagwt", ignore = true)
	@Mapping(target = "totalWeight", ignore = true)
	@Mapping(target = "receptacleWeight", ignore = true)
	@Mapping(target = "actualWeight", ignore = true)
	@Named("containerDetailsModelInboundToContainerDetailsVO")
	ContainerDetailsVO containerDetailsModelInboundToContainerDetailsVO(ContainerDetailsModel containerDetailsModel, @Context MeasureMapper measureMapper);

	 DestinationFilterVO destinationFilterModelToDestinationFilterVO(
			DestinationFilterModel destinationFilterModel);

	AWBFilterVO aWBFilterModelToAWBFilterVO(AWBFilterModel aWBFilterModel);

	Collection<ContainerModel> containerVOsToContainerModel(Collection<ContainerVO> containerVO);

	Collection<OperationalFlightVO> operationalFlightModelsToOperationalFlightVO(
			Collection<OperationalFlightModel> operationalFlightModel);

	Collection<MailbagVO> mailbagModelsToMailbagVO(Collection<MailbagModel> mailbagModel);

	MailInConsignmentModel mailInConsignmentVOToMailInConsignmentModel(MailInConsignmentVO mailInConsignmentVO);

	Page<ContainerDetailsModel> containerDetailsVOsToContainerDetailsModel(
			Page<ContainerDetailsVO> containerDetailsVO);

	AWBDetailVO aWBDetailModelToAWBDetailVO(AWBDetailModel aWBDetailModel);

	Collection<MailScanDetailVO> mailScanDetailModelsToMailScanDetailVO(
			Collection<MailScanDetailModel> mailScanDetailModel);

	Collection<DespatchDetailsVO> despatchDetailsModelsToDespatchDetailsVO(
			Collection<DespatchDetailsModel> despatchDetailsModel);

	OperationalFlightVO operationalFlightModelToOperationalFlightVO(
			OperationalFlightModel operationalFlightModel);

	MailArrivalModel mailArrivalVOToMailArrivalModel(MailArrivalVO mailArrivalVO);

	Collection<MailResditVO> mailResditModelsToMailResditVO(Collection<MailResditModel> mailResditModel);

	Collection<ResditEventVO> resditEventModelsToResditEventVO(Collection<ResditEventModel> resditEventModel);

	Page<MailbagModel> mailbagVOsToMailbagModel(Page<MailbagVO> mailbagVO);

	Collection<MailbagModel> mailbagVOsToMailbagModels(Collection<MailbagVO> mailbagVO);

	Collection<ForceMajeureRequestModel> forceMajeureRequestVOsToForceMajeureRequestModel(
			Collection<ForceMajeureRequestVO> forceMajeureRequestVO);

	Page<ForceMajeureRequestModel> forceMajeureRequestVOsToForceMajeureRequestModel(
			Page<ForceMajeureRequestVO> forceMajeureRequestVO);

	Collection<MailWebserviceVO> mailWebserviceModelsToMailWebserviceVO(
			Collection<MailWebserviceModel> mailWebserviceModel);

	Collection<ContainerDetailsModel> containerDetailsVOsToContainerDetailsModel(
			Collection<ContainerDetailsVO> containerDetailsVO);

	MailFlightSummaryVO mailFlightSummaryModelToMailFlightSummaryVO(
			MailFlightSummaryModel mailFlightSummaryModel);

	List<MailUploadModel> mailUploadVOsToMailUploadModel(List<MailUploadVO> mailUploadVO);

	MailUploadVO mailUploadModelToMailUploadVO(MailUploadModel mailUploadModel);

	Collection<ContainerVO> containerModelsToContainerVO(Collection<ContainerModel> containerModel);

	MailbagVO copyMailbagVO(MailbagVO mailbagVO);
	ScannedMailDetailsVO copyScannedMailDetailsVO(ScannedMailDetailsVO scannedMailDetailsVO);
	ContainerVO copyContainerVO(ContainerVO containerVO);
	ContainerDetailsVO copyContainerDetailsVO(ContainerDetailsVO containerDetailsVO);
	Collection<FlightSegmentSummaryVO> copyFlightSegVOs(Collection<FlightSegmentSummaryVO> fligtSegVOs);
	HashMap<String,Collection<HandoverVO>> copyHandoverVOHashMap(HashMap<String,Collection<HandoverModel>> HandoverModelsHashMaps);
	Collection<HandoverVO> copyHandoverVOs(Collection<HandoverModel> copyHandoverVOs);
	DespatchDetailsVO copyDespatchDetailsVO(DespatchDetailsVO despatchDetailsVO);
	DSNVO copyDSNVO(DSNVO dSNVO);
	Page<MailDetailVO> copyToMailDetailVOPage(Page<MailDetailVO> mailDtlPages);
	//To be moved to another class
	ResditMessageVO copyResditMessageVO(ResditMessageVO resditMessageVO);
	ConsignmentDocumentVO copyConsignmentDocumentVO(ConsignmentDocumentVO consignmentDocumentVO);
	Page<MailInConsignmentVO> copyPageMailInConsignmentVO(Page<MailInConsignmentVO> page);

	MailAcceptanceModel mailAcceptanceVOToMailAcceptanceModel(MailAcceptanceVO mailAcceptanceVO);

    PostalAdministrationVO postalAdministrationModelToPostalAdministrationVO(com.ibsplc.neoicargo.mailmasters.model.PostalAdministrationModel paCode);

	List<ContainerDetailsModel> containerDetailsVOsToContainerDetailsModels(List<ContainerDetailsVO> containerDetailsVOsList);

	HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsModelToPostalAdministrationDetailsVO(HashMap<String,
			Collection<com.ibsplc.neoicargo.mailmasters.model.PostalAdministrationDetailsModel>> postalAdministrationDetailsMap);
	Collection<PostalAdministrationDetailsVO> postalAdministrationDetailsModelToPostalAdministrationDetailsVOCollection
			(Collection<com.ibsplc.neoicargo.mailmasters.model.PostalAdministrationDetailsModel> value);

	OfficeOfExchangeVO officeOfExchangeModelToOfficeOfExchangeVO(com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeModel officeOfExchangeModel);

    Collection<PartnerCarrierVO> partnerCarrierModelstoPartnerCarrierVos(Collection<com.ibsplc.neoicargo.mailmasters.model.PartnerCarrierModel> allPartnerCarriers);

    Page<OfficeOfExchangeVO> officeOfExchangeModelsToOfficeOfExchangeVOs(Page<com.ibsplc.neoicargo.mailmasters.model.OfficeOfExchangeModel> officeOfExchangeModel);
    
    DSNModel dsnVoTodsnModel(DSNVO dsnvo);

	ULDForSegmentVO ULDForSegmentVO(ULDForSegmentVO uldForSegmentVO);
	List<TransferManifestModel> transferManifestVOToTransferManifestModels(List<TransferManifestVO> transferManifestVO);
  MailStatusFilterVO mailStatusFilterModelToMailStatusFilterVO(MailStatusFilterModel mailStatusFilterModel);

	Collection<MailStatusModel> mailStatusVOsToMailStatusModel( Collection<MailStatusVO> mailStatusVO);

	MailStatusModel mailStatusVOToMailStatusModel (MailStatusVO mailStatusVO);
	DailyMailStationFilterVO dailyMailStationFilterModelToDailyMailStationFilterVO(DailyMailStationFilterModel filterModel);
	Collection<DailyMailStationReportModel> dailyMailStationReportVOsToDailyMailStationReportModel(Collection<com.ibsplc.neoicargo.mail.vo.DailyMailStationReportVO> dailyMailStationReportVO);
	DailyMailStationReportModel dailyMailStationReportVOToDailyMailStationReportModel(com.ibsplc.neoicargo.mail.vo.DailyMailStationReportVO dailyMailStationReportVO);


	MailHandedOverFilterVO mailHandedOverFilterModelToMailHandedOverFilterVO(
			MailHandedOverFilterModel mailHandedOverFilterModel);
	Collection<MailHandedOverModel> mailHandedOverFilterVOsToMailHandedOverModel(
			Collection<MailHandedOverVO> mailHandedOverVO);


    MailAcceptanceVO copyMailAcceptanceVO(MailAcceptanceVO mailAcceptanceVO);
	DamageMailFilterVO damageMailFilterModelToDamageMailFilterVO(DamageMailFilterModel damageMailReportFiltermodel);
	OperationalFlightVO mailArrivalVOTOOperationalFlightVO(MailArrivalVO mailArrivalVO);
	MailboxIdVO mailboxIdModelToMailboxIdVO(MailboxIdModel mailboxIdModel);
	MailboxIdModel mailboxIdVOToMailboxIdModel(MailboxIdVO mailboxIdVo);
	Collection<MailEventVO> mailEventModelToMailEventVOs(Collection<com.ibsplc.neoicargo.mailmasters.model.MailEventModel> mailEventModels);


    MailArrivalVO copymailArrivalVO(MailArrivalVO mailArrivalVO);

	Collection<ContainerDetailsVO> CopyContainerDetails(Collection<ContainerDetailsVO> containerDetails);

	Collection<MailbagVO> copyMailbagVOS(Collection<MailbagVO> mailbagVOS);
	Collection<MLDConfigurationVO> mLDConfigurationModelstoMLDConfigurationVOs(Collection<com.ibsplc.neoicargo.mailmasters.model.MLDConfigurationModel> mldCongfigurations);
	com.ibsplc.neoicargo.mailmasters.model.MLDConfigurationFilterModel mLDConfigurationFilterVOtoMLDConfigurationFilterModel(MLDConfigurationFilterVO mLDConfigurationFilterVO);
	Map<String,Collection<MailbagModel>>mailbagVOMapToMailbagModelMap(Map<String,Collection<MailbagVO>> mailbagVOs);
	MailMasterDataFilterVO mailMasterDataFilterModelToMailMasterDataFilterVO(MailMasterDataFilterModel mailMasterDataFilterModel);
}
