package com.ibsplc.icargo.presentation.mobility.model.uld.defaults.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldAirportLocationModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageChecklistModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageDetailsModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamageModel;
import com.ibsplc.icargo.presentation.mobility.model.uld.defaults.UldDamagePictureModel;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

public class UldDefaultsModelConverter {
	/**
	 * 
	 * 	Method		:	UldDefaultsModelConverter.populateUldDamageChecklistModel
	 *	Added by 	:	A-8761 on 28-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageChecklistVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<UldDamageChecklistModel>
	 */
	public static Collection<UldDamageChecklistModel> populateUldDamageChecklistModel(
			Collection<ULDDamageChecklistVO> uldDamageChecklistVOs) {
		Collection<UldDamageChecklistModel> uldDamageChecklistModels = new ArrayList<UldDamageChecklistModel>();
		for (ULDDamageChecklistVO uldDamageChecklistVO : uldDamageChecklistVOs) {
			UldDamageChecklistModel uldDamageChecklistModel = new UldDamageChecklistModel();
			uldDamageChecklistModel.setNoOfPoints(Integer.valueOf(uldDamageChecklistVO.getNoOfPoints()));
			uldDamageChecklistModel.setDescription(uldDamageChecklistVO.getDescription());
			uldDamageChecklistModel.setSection(uldDamageChecklistVO.getSection());
			uldDamageChecklistModel.setSequenceNumber(uldDamageChecklistVO.getSequenceNumber());
			uldDamageChecklistModels.add(uldDamageChecklistModel);
		}
		return uldDamageChecklistModels;
	}

	/**
	 * 
	 * 	Method		:	UldDefaultsModelConverter.populateUldAirportLocationModels
	 *	Added by 	:	A-8761 on 28-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param uldAirportLocationVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	List<UldAirportLocationModel>
	 */
	public static List<UldAirportLocationModel> populateUldAirportLocationModels(
			Collection<ULDAirportLocationVO> uldAirportLocationVOs) {
		List<UldAirportLocationModel> uldAirportLocationModels = new ArrayList<UldAirportLocationModel>();
		for (ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs) {
			UldAirportLocationModel uldAirportLocationModel = new UldAirportLocationModel();
			uldAirportLocationModel.setFacilitytype(uldAirportLocationVO.getFacilityType());
			uldAirportLocationModel.setLocationCode(uldAirportLocationVO.getFacilityCode());
			uldAirportLocationModel.setLocationDescription(uldAirportLocationVO.getDescription());
			uldAirportLocationModels.add(uldAirportLocationModel);
		}
		return uldAirportLocationModels;
	}

	/**
	 * 
	 * 	Method		:	UldDefaultsModelConverter.populateULDDamageRepairDetailsVO
	 *	Added by 	:	A-8761 on 28-Mar-2019
	 * 	Used for 	:
	 *	Parameters	:	@param uldDamageModel
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ULDDamageRepairDetailsVO
	 */
	public static ULDDamageRepairDetailsVO populateULDDamageRepairDetailsVO(
			UldDamageModel uldDamageModel) throws SystemException {

		LogonAttributes logonAttributes = (LogonAttributes) ContextUtils.getSecurityContext().getLogonAttributesVO();

		ULDDamageRepairDetailsVO uldDamageRepairDetailsVO = new ULDDamageRepairDetailsVO();

		uldDamageRepairDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldDamageRepairDetailsVO.setDamageStatus(uldDamageModel.getDamageStatus());
		uldDamageRepairDetailsVO.setLastUpdatedUser(logonAttributes.getUserId());
		uldDamageRepairDetailsVO.setOverallStatus(uldDamageModel.getOverallStatus());
		uldDamageRepairDetailsVO.setRepairStatus(uldDamageModel.getRepairStatus());
		uldDamageRepairDetailsVO.setUldNumber(uldDamageModel.getUldNumber());

		Collection<ULDDamageVO> uldDamageVOs = new ArrayList<ULDDamageVO>();
		for (UldDamageDetailsModel uldDamageDetailsModel : uldDamageModel.getUldDamageDetails()) {
			ULDDamageVO uldDamageVO = new ULDDamageVO();
			uldDamageVO.setDamageDescription(uldDamageDetailsModel.getDamageDescription());
			uldDamageVO.setDamagePoints(uldDamageDetailsModel.getDamagePoints());
			uldDamageVO.setOperationFlag(uldDamageDetailsModel.getOperationFlag());
			uldDamageVO.setFacilityType(uldDamageDetailsModel.getFacilityType());
			uldDamageVO.setPartyType(uldDamageDetailsModel.getPartyType());
			uldDamageVO.setLocation(uldDamageDetailsModel.getLocation());
			uldDamageVO.setParty(uldDamageDetailsModel.getPartyCode());
			uldDamageVO.setSection(uldDamageDetailsModel.getSection());

			if (uldDamageDetailsModel.getSequenceNumber() != null)
				uldDamageVO.setSequenceNumber(uldDamageDetailsModel.getSequenceNumber().intValue());

			uldDamageVO.setLastUpdateUser(logonAttributes.getUserId());
			uldDamageVO.setReportedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
			uldDamageVO.setSeverity(uldDamageDetailsModel.getSeverity());
			uldDamageVO.setUldStatus(uldDamageDetailsModel.getUldStatus());
			uldDamageVO.setReportedStation(logonAttributes.getAirportCode());

			if ((uldDamageDetailsModel.getUldDamagePictures() != null)
					&& (uldDamageDetailsModel.getUldDamagePictures().size() > 0)) {
				for (UldDamagePictureModel uldDamagePictureModel : uldDamageDetailsModel.getUldDamagePictures()) {
					ULDDamagePictureVO pictureVO = new ULDDamagePictureVO();
					pictureVO.setCompanyCode(logonAttributes.getCompanyCode());
					pictureVO.setOperationFlag("I");
					pictureVO.setUldNumber(uldDamageModel.getUldNumber());
					if (pictureVO.getImage() == null) {
						pictureVO.setImage(new ImageModel());
					}
					pictureVO.getImage().setData(uldDamagePictureModel.getImage());
					if (uldDamageVO.getPictureVOs() == null) {
						uldDamageVO.setPictureVOs(new ArrayList<ULDDamagePictureVO>());
					}
					uldDamageVO.getPictureVOs().add(pictureVO);
					uldDamageVO.setPicturePresent(true);
				}
			}
			uldDamageVOs.add(uldDamageVO);
		}

		if (uldDamageRepairDetailsVO.getUldDamageVOs() == null) {
			uldDamageRepairDetailsVO.setUldDamageVOs(new ArrayList<ULDDamageVO>());
		}
		uldDamageRepairDetailsVO.getUldDamageVOs().addAll(uldDamageVOs);

		return uldDamageRepairDetailsVO;
	}

}
