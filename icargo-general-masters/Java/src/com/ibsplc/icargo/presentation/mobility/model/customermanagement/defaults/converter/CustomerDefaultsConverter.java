package com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.converter;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.AddressDetailModel;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.CustomerDetailsModel;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.CustomerModel;
import com.ibsplc.icargo.presentation.mobility.model.customermanagement.defaults.CustomerRequestFilterModel;
import com.ibsplc.xibase.util.time.TimeConvertor;

public class CustomerDefaultsConverter {

	/**
	 * 
	 * 	Method		:	CustomerDefaultsConverter.populateCustomerFilterVO
	 *	Added by 	:	A-8761 on 27-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param customerRequestFilterModel
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerFilterVO
	 */
	public static CustomerFilterVO populateCustomerFilterVO(CustomerRequestFilterModel customerRequestFilterModel) {
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCustomerCode(customerRequestFilterModel.getCustomerCode());
		customerFilterVO.setCustomerShortCode(customerRequestFilterModel.getCustomerShortCode());
		customerFilterVO.setCustomerType(customerRequestFilterModel.getCustomerType());

		if (customerRequestFilterModel.getUpdatedAfter() != null) {
			LocalDate updatedAfter = new LocalDate("***", Location.NONE, true);
			updatedAfter.setDateAndTime(customerRequestFilterModel.getUpdatedAfter());
			customerFilterVO.setUpdatedAfter(updatedAfter);
		}

		customerFilterVO.setInternalAccountHolder(customerRequestFilterModel.getInternalAccountHolder());

		customerFilterVO.setHoldingCompany(customerRequestFilterModel.getHoldingCompany());
		customerFilterVO.setHoldingCompanyGroup(customerRequestFilterModel.getHoldingCompanyGroup());

		return customerFilterVO;
	}

	/**
	 * 
	 * 	Method		:	CustomerDefaultsConverter.populateCustomerResponseModel
	 *	Added by 	:	A-8761 on 27-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param customerVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomerDetailsModel
	 */
	public static CustomerDetailsModel populateCustomerResponseModel(Collection<CustomerVO> customerVOs) {

		CustomerDetailsModel customerResponseModel = new CustomerDetailsModel();
		LocalDate currentDate = new LocalDate("***", Location.NONE, true);
		String responseTime = TimeConvertor.toStringFormat(currentDate.toCalendar(), "dd-MMM-yyyy hh:mm:ss");
		customerResponseModel.setResponseSentTime(responseTime);
		customerResponseModel.getCustomerDetails().addAll(populateCustomerModel(customerVOs));
		return customerResponseModel;
	}

	private static ArrayList<CustomerModel> populateCustomerModel(Collection<CustomerVO> customerVOs) {

		ArrayList<CustomerModel> customerModels = new ArrayList<CustomerModel>();

		for (CustomerVO customerVO : customerVOs) {

			CustomerModel customerModel = new CustomerModel();
			if ((customerVO.getCustomerCode() != null) && (customerVO.getCustomerCode().trim().length() > 0))
				customerModel.setCustomerCode(customerVO.getCustomerCode());

			if ((customerVO.getCustomerType() != null) && (customerVO.getCustomerType().trim().length() > 0))
				customerModel.setCustomerType(customerVO.getCustomerType());

			if ((customerVO.getInternalAccountHolder() != null)
					&& (customerVO.getInternalAccountHolder().trim().length() > 0))
				customerModel.setInternalAccountHolder(customerVO.getInternalAccountHolder());

			if ((customerVO.getCustomerName() != null) && (customerVO.getCustomerName().trim().length() > 0))
				customerModel.setCustomerName(customerVO.getCustomerName());

			if (customerVO.getValidFrom() != null) {
				String validFromDate = TimeConvertor.toStringFormat(customerVO.getValidFrom().toCalendar(),
						"dd-MMM-yyyy");
				customerModel.setValidFromDate(validFromDate);
			}

			if (customerVO.getValidTo() != null) {
				String validToDate = TimeConvertor.toStringFormat(customerVO.getValidTo().toCalendar(), "dd-MMM-yyyy");
				customerModel.setValidToDate(validToDate);
			}
			if ((customerVO.getStatus() != null) && (customerVO.getStatus().trim().length() > 0))
				customerModel.setCustomerStatus(customerVO.getStatus());

			if ((customerVO.getCustomerShortCode() != null) && (customerVO.getCustomerShortCode().trim().length() > 0))
				customerModel.setCustomerShortCode(customerVO.getCustomerShortCode());

			AddressDetailModel addressModel = new AddressDetailModel();
			if ((customerVO.getAddress1() != null) && (customerVO.getAddress1().trim().length() > 0))
				addressModel.setAddressOne(customerVO.getAddress1());
			if ((customerVO.getAddress1() != null) && (customerVO.getAddress1().trim().length() > 0))
				addressModel.setAddressTwo(customerVO.getAddress2());
			if ((customerVO.getCity() != null) && (customerVO.getCity().trim().length() > 0))
				addressModel.setCity(customerVO.getCity());
			if ((customerVO.getCountry() != null) && (customerVO.getCountry().trim().length() > 0))
				addressModel.setCountryCode(customerVO.getCountry());
			if ((customerVO.getEmail() != null) && (customerVO.getEmail().trim().length() > 0))
				addressModel.setEmailAddress(customerVO.getEmail());
			if ((customerVO.getFax() != null) && (customerVO.getFax().trim().length() > 0))
				addressModel.setFaxNumber(customerVO.getFax());
			if ((customerVO.getMobile() != null) && (customerVO.getMobile().trim().length() > 0))
				addressModel.setMobileNumber(customerVO.getMobile());
			if ((customerVO.getTelephone() != null) && (customerVO.getTelephone().trim().length() > 0))
				addressModel.setPhoneNumber(customerVO.getTelephone());
			if ((customerVO.getState() != null) && (customerVO.getState().trim().length() > 0))
				addressModel.setState(customerVO.getState());
			if ((customerVO.getZipCode() != null) && (customerVO.getZipCode().trim().length() > 0))
				addressModel.setZipCode(customerVO.getZipCode());
			if ((customerVO.getState() != null) && (customerVO.getState().trim().length() > 0))
				addressModel.setState(customerVO.getState());

			customerModel.setAddressDetails(addressModel);
			customerModels.add(customerModel);
		}

		return customerModels;
	}

}
