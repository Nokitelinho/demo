package com.ibsplc.neoicargo.mailmasters.controller;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.MailTrackingDefaultsBI;
import com.ibsplc.neoicargo.mailmasters.exception.MailTrackingBusinessException;
import com.ibsplc.neoicargo.mailmasters.model.*;
import com.ibsplc.neoicargo.mailmasters.service.MailMastersServicesEJB;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import java.util.ArrayList;
import java.util.Collection;
import java.time.ZonedDateTime;
import java.util.Map;

@Service
public class MailMastersController implements MailTrackingDefaultsBI {
	@Autowired
	private MailMastersServicesEJB mailMastersServicesEJB;

	public Page<OfficeOfExchangeModel> findOfficeOfExchangeLov(OfficeOfExchangeModel officeofExchangeModel,
			int pageNumber, int defaultSize) {
		return mailMastersServicesEJB.findOfficeOfExchangeLov(officeofExchangeModel, pageNumber, defaultSize);
	}

	@Override
	public String findPAForMailboxID(String companyCode, String mailboxId, String originOE) {
		return mailMastersServicesEJB.findPAForMailboxID(companyCode, mailboxId,originOE);
	}

	public Page<MailSubClassModel> findMailSubClassCodeLov(String companyCode, String code, String description,
			int pageNumber, int defaultSize) {
		return mailMastersServicesEJB.findMailSubClassCodeLov(companyCode, code, description, pageNumber,
				defaultSize);
	}

	public PostalAdministrationModel findPACode(String companyCode, String paCode) {
		return mailMastersServicesEJB.findPACode(companyCode, paCode);
	}

	public Collection<PartnerCarrierModel> findAllPartnerCarriers(String companyCode, String ownCarrierCode,
			String airportCode) {
		return mailMastersServicesEJB.findAllPartnerCarriers(companyCode, ownCarrierCode, airportCode);
	}

	public Collection<CoTerminusModel> findAllCoTerminusAirports(CoTerminusFilterModel filterModel) {
		return mailMastersServicesEJB.findAllCoTerminusAirports(filterModel);
	}

	public Page<MailServiceStandardModel> listServiceStandardDetails(
			MailServiceStandardFilterModel mailServiceStandardFilterModel, int pageNumber) {
		return mailMastersServicesEJB.listServiceStandardDetails(mailServiceStandardFilterModel, pageNumber);
	}

	public Collection<MailRdtMasterModel> findRdtMasterDetails(RdtMasterFilterModel filterModel) {
		return mailMastersServicesEJB.findRdtMasterDetails(filterModel);
	}

	public boolean validateCoterminusairports(String actualAirport, String eventAirport, String eventCode,
			String paCode, ZonedDateTime dspDate) {
		return mailMastersServicesEJB.validateCoterminusairports(actualAirport, eventAirport, eventCode,
				paCode, dspDate);
	}

	public OfficeOfExchangeModel validateOfficeOfExchange(String companyCode, String officeOfExchange) {
		return mailMastersServicesEJB.validateOfficeOfExchange(companyCode, officeOfExchange);
	}

	public void savePACode(PostalAdministrationModel postalAdministrationModel)
			throws SystemException {
		 mailMastersServicesEJB.savePACode(postalAdministrationModel);
	}

	public void saveOfficeOfExchange(Collection<OfficeOfExchangeModel> officeOfExchangesModel)throws BusinessException{
		 mailMastersServicesEJB.saveOfficeOfExchange(officeOfExchangesModel);
	}

	public void saveMailSubClassCodes(Collection<MailSubClassModel> mailSubClasssModel) throws MailTrackingBusinessException {
		 mailMastersServicesEJB.saveMailSubClassCodes(mailSubClasssModel);
	}

	public Collection<PostalAdministrationModel> findLocalPAs(String companyCode, String countryCode) {
		return mailMastersServicesEJB.findLocalPAs(companyCode, countryCode);
	}

	public Page<PostalAdministrationModel> findPALov(String companyCode, String paCode, String paName, int pageNumber,
			int defaultSize) {
		return mailMastersServicesEJB.findPALov(companyCode, paCode, paName, pageNumber, defaultSize);
	}

	public String findPAForOfficeOfExchange(String companyCode, String officeOfExchange) {
		return mailMastersServicesEJB.findPAForOfficeOfExchange(companyCode, officeOfExchange);
	}

	public Collection<MailSubClassModel> findMailSubClassCodes(String companyCode, String subclassCode) {
		return mailMastersServicesEJB.findMailSubClassCodes(companyCode, subclassCode);
	}

	public Page<OfficeOfExchangeModel> findOfficeOfExchange(String companyCode, String officeOfExchange,
			int pageNumber) {
		return mailMastersServicesEJB.findOfficeOfExchange(companyCode, officeOfExchange, pageNumber);
	}

	public Page<MailBoxIdLovModel> findMailBoxIdLov(String companyCode, String mailboxCode, String mailboxDesc,
			int pageNumber, int defaultSize) {
		return mailMastersServicesEJB.findMailBoxIdLov(companyCode, mailboxCode, mailboxDesc, pageNumber,
				defaultSize);
	}

	public Collection<ArrayList<String>> findCityAndAirportForOE(String companyCode,
			Collection<String> officeOfExchanges) throws SystemException {
		return mailMastersServicesEJB.findCityAndAirportForOE(companyCode, officeOfExchanges);
	}

	public Collection<String> findOfficeOfExchangesForAirport(String companyCode, String airportCode) {
		return mailMastersServicesEJB.findOfficeOfExchangesForAirport(companyCode, airportCode);
	}

	public PostalAdministrationModel findPADetails(String companyCode, String officeOfExchange) {
		return mailMastersServicesEJB.findPADetails(companyCode, officeOfExchange);
	}

	public boolean validateMailSubClass(String companyCode, String subclass) {
		return mailMastersServicesEJB.validateMailSubClass(companyCode, subclass);
	}

	public PostalAdministrationDetailsModel validatePoaDetails(
			PostalAdministrationDetailsModel postalAdministrationDetailsModel) {
		return mailMastersServicesEJB.validatePoaDetails(postalAdministrationDetailsModel);
	}

	public Collection<PostalAdministrationModel> findAllPACodes(GenerateInvoiceFilterVO generateInvoiceFilterVO) {
		return mailMastersServicesEJB.findAllPACodes(generateInvoiceFilterVO);
	}

	public Collection<MLDConfigurationModel> findMLDCongfigurations(
			MLDConfigurationFilterModel mLDConfigurationFilterModel) {
		return mailMastersServicesEJB.findMLDConfigurations(mLDConfigurationFilterModel);
	}

	public void saveMLDConfigurations(Collection<MLDConfigurationModel> mLDConfigurationsModel) throws BusinessException {
		 mailMastersServicesEJB.saveMLDConfigurations(mLDConfigurationsModel);
	}

	public void savePartnerCarriers(Collection<PartnerCarrierModel> partnerCarriersModel){
		 mailMastersServicesEJB.savePartnerCarriers(partnerCarriersModel);
	}

	public void saveCoterminusDetails(Collection<CoTerminusModel> coterminussModel){
		 mailMastersServicesEJB.saveCoterminusDetails(coterminussModel);
	}

	public void saveServiceStandardDetails(Collection<MailServiceStandardModel> mailServiceStandardsModel,
			Collection<MailServiceStandardModel> mailServiceStandardstodeleteModel) {

		 mailMastersServicesEJB.saveServiceStandardDetails(mailServiceStandardsModel,
				mailServiceStandardstodeleteModel);
	}

	public void saveRdtMasterDetails(Collection<MailRdtMasterModel> mailRdtMastersModel){
		 mailMastersServicesEJB.saveRdtMasterDetails(mailRdtMastersModel);
	}

	public Collection<ErrorVO> saveRdtMasterDetailsXls(Collection<MailRdtMasterModel> mailRdtMastersModel){
		return mailMastersServicesEJB.saveRdtMasterDetailsXls(mailRdtMastersModel);
	}

	public Map<String, String> findOfficeOfExchangeForPA(String companyCode, String paCode) {
		return mailMastersServicesEJB.findOfficeOfExchangeForPA(companyCode, paCode);
	}

	public String findAgentCodeForPA(String companyCode, String paCode) {
		return mailMastersServicesEJB.findAgentCodeForPA(companyCode, paCode);
	}

	public Collection<ErrorVO> saveMailServiceLevelDtls(Collection<MailServiceLevelModel> mailServiceLevelsModel) {
		return mailMastersServicesEJB.saveMailServiceLevelDtls(mailServiceLevelsModel);
	}

	public Collection<USPSPostalCalendarModel> listPostalCalendarDetails(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel) {
		return mailMastersServicesEJB.listPostalCalendarDetails(uSPSPostalCalendarFilterModel);
	}

	public void savePostalCalendar(Collection<USPSPostalCalendarModel> uSPSPostalCalendarsModel){
		 mailMastersServicesEJB.savePostalCalendar(uSPSPostalCalendarsModel);
	}

	public void saveContractDetails(Collection<GPAContractModel> gpaContractsModel) throws BusinessException {
		 mailMastersServicesEJB.saveContractDetails(gpaContractsModel);
	}

	public Collection<GPAContractModel> listContractDetails(GPAContractFilterModel gpaContractFilterModel) {
		return mailMastersServicesEJB.listContractDetails(gpaContractFilterModel);
	}

	public void saveMailHandoverDetails(Collection<MailHandoverModel> MailHandoversModel) {
		 mailMastersServicesEJB.saveMailHandoverDetails(MailHandoversModel);
	}

	public Page<MailHandoverModel> findMailHandoverDetails(MailHandoverFilterModel mailHandoverFilterModel,
			int pageNumber) {
		return mailMastersServicesEJB.findMailHandoverDetails(mailHandoverFilterModel, pageNumber);
	}

	public Collection<IncentiveConfigurationModel> findIncentiveConfigurationDetails(
			IncentiveConfigurationFilterModel incentiveConfigurationFilterModel) {
		return mailMastersServicesEJB.findIncentiveConfigurationDetails(incentiveConfigurationFilterModel);
	}

	public void saveIncentiveConfigurationDetails(
			Collection<IncentiveConfigurationModel> incentiveConfigurationsModel) {

		 mailMastersServicesEJB.saveIncentiveConfigurationDetails(incentiveConfigurationsModel);
	}

	public void saveRoutingIndexDetails(Collection<RoutingIndexModel> routingIndexsModel) {

		 mailMastersServicesEJB.saveRoutingIndexDetails(routingIndexsModel);
	}

	public Collection<RoutingIndexModel> findRoutingIndex(RoutingIndexModel routingIndexModel)
			{
		return mailMastersServicesEJB.findRoutingIndex(routingIndexModel);
	}

	public Collection<USPSPostalCalendarModel> validateFrmToDateRange(
			USPSPostalCalendarFilterModel uSPSPostalCalendarFilterModel) {
		return mailMastersServicesEJB.validateFrmToDateRange(uSPSPostalCalendarFilterModel);
	}

	public USPSPostalCalendarModel findInvoicPeriodDetails(
			USPSPostalCalendarFilterModel uspsPostalCalendarFilterModel) {
		return mailMastersServicesEJB.findInvoicPeriodDetails(uspsPostalCalendarFilterModel);
	}

	public MailboxIdModel findMailboxId(MailboxIdModel mailboxIdModel) {
		return mailMastersServicesEJB.findMailboxId(mailboxIdModel);
	}

	public void saveMailboxId(MailboxIdModel mailboxIdModel){
		 mailMastersServicesEJB.saveMailboxId(mailboxIdModel);
	}

	public void saveMailRuleConfiguration(MailRuleConfigModel mailRuleConfigModel)
			{
		 mailMastersServicesEJB.saveMailRuleConfiguration(mailRuleConfigModel);
	}

	public String findMailboxIdFromConfig(MailbagModel mailbagModel) {
		return mailMastersServicesEJB.findMailboxIdFromConfig(mailbagModel);
	}

	public void publishMasterDataForMail(MailMasterDataFilterModel mailMasterDataFilterModel) {
		 mailMastersServicesEJB.publishMasterDataForMail(mailMasterDataFilterModel);
	}

	public Collection<RoutingIndexModel> getPlannedRoutingIndexDetails(RoutingIndexModel routingIndexModel) {
		return mailMastersServicesEJB.getPlannedRoutingIndexDetails(routingIndexModel);
	}

	public Collection<OfficeOfExchangeModel> getExchangeOfficeDetails(String companyCode, String airportCode) {
		return mailMastersServicesEJB.getExchangeOfficeDetails(companyCode, airportCode);
	}
	public Map<String, String> findAirportForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel) {
		return mailMastersServicesEJB.findAirportForOfficeOfExchange(filterModel);
	}
	public String findUpuCodeNameForPA(String companyCode, String paCode) throws PersistenceException {
		return mailMastersServicesEJB.findUpuCodeNameForPA(companyCode,paCode);
	}
	public Map<String, String> findCityForOfficeOfExchange(OfficeOfExchangeFilterModel filterModel) {
		return mailMastersServicesEJB.findCityForOfficeOfExchange( filterModel);
	}
	public String findOfficeOfExchangeDescription(String companyCode, String exchangeCode){
		return mailMastersServicesEJB.findOfficeOfExchangeDescription( companyCode,exchangeCode);
	}
	public Collection<MailEventModel> findMailEvent(MailEventModel mailEventModel) {
		return mailMastersServicesEJB.findMailEvent(mailEventModel);
	}
	public String findPartyIdentifierForPA(String companyCode, String paCode) {
		return mailMastersServicesEJB.findPartyIdentifierForPA(companyCode,paCode);
	}	 
public String getMLDVersion(int carrierIdentifier,String companyCode,String airportCode){
		return mailMastersServicesEJB.getMLDVersion( carrierIdentifier,companyCode,airportCode);
	}
}
