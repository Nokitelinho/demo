package com.ibsplc.neoicargo.mail.lh.component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.SavePAWBDetailsFeature;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.vo.AWBDetailVO;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.CarditTotalVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import com.ibsplc.neoicargo.mail.dao.MailOperationsDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class LHMailController extends MailController {
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	private static final String MODULENAME = "mail.operations";
	public static final String CARDIT_TYPE = "ACP";
	private static final String KEY_CARDIT_AWB = "KEY_CARDIT_AWB";
	private static final String PAWB_END_RANGE = "mailtracking.defaults.pawbendrange";
//TODO: Override commented- refer classic
	//@Override
	public void createPAWBForConsignment(int noOfDays) {
		Collection<CarditPawbDetailsVO> carditPawbDetailsVOs = null;
		Collection<CarditVO> carditVOs = null;
		carditPawbDetailsVOs = findMailbagsForPAWBCreation(noOfDays);
		carditVOs = constructCarditVO(carditPawbDetailsVOs);
		boolean pawbCountryValidation = false;
		for (CarditVO carditVO : carditVOs) {
			try {
				if (carditVO.getCarditPawbDetailsVO() != null) {
					new MailController().findFieldsOfCarditPawb(carditVO);
					pawbCountryValidation = new MailController().findPawbCountryValidation(carditVO,
							carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
					findMstDocNumAndCallExcecute(pawbCountryValidation, carditVO);
				}
			} catch (BusinessException exce) {
				log.info("Exception :", exce);
			}
		}
	}

	private void findMstDocNumAndCallExcecute(boolean pawbCountryValidation, CarditVO carditVO)
			throws BusinessException {
		AWBDetailVO aWBDetailVO = null;
		if (pawbCountryValidation) {
			aWBDetailVO = findMstDocNumForAWBDetails(carditVO);
			updateCarditBasedOnAWBFromInternalStock(aWBDetailVO, carditVO);
			SavePAWBDetailsFeature savePAWBDetailsFeature = ContextUtil.getInstance()
					.getBean(SavePAWBDetailsFeature.class);
			savePAWBDetailsFeature.execute(carditVO);
		}
	}

	private void updateCarditBasedOnAWBFromInternalStock(AWBDetailVO aWBDetailVO, CarditVO carditVO) {
		if (aWBDetailVO != null && aWBDetailVO.getMasterDocumentNumber() != null) {
			carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
			carditVO.getCarditPawbDetailsVO().setMasterDocumentNumber(aWBDetailVO.getMasterDocumentNumber());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next()
					.setMailBagDocumentOwnerIdr(aWBDetailVO.getOwnerId());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next()
					.setMailDuplicateNumber(aWBDetailVO.getDuplicateNumber());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next()
					.setSequenceNumberOfMailbag(aWBDetailVO.getSequenceNumber());
		} else {
			findNextAWBNumberFromInternalStock(carditVO);
		}
	}

	private Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(int noOfDays) {
		Collection<CarditPawbDetailsVO> carditPawbDetails = null;
		carditPawbDetails = constructDAO().findMailbagsForPAWBCreation(noOfDays);
		return carditPawbDetails;
	}

	private Collection<CarditVO> constructCarditVO(Collection<CarditPawbDetailsVO> carditPawbDetailsVOs) {
		CarditVO carditVO = null;
		Collection<CarditVO> carditVOs = new ArrayList<>();
		LoginProfile logonAttributes = contextUtil.callerLoginProfile();
		for (CarditPawbDetailsVO carditPawbDetailsVO : carditPawbDetailsVOs) {
			carditVO = new CarditVO();
			carditVO.setCompanyCode(logonAttributes.getCompanyCode());
			carditVO.setCarditType(CARDIT_TYPE);
			carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
			CarditTotalVO carditTotalVO = new CarditTotalVO();
			String numberOfReceptacles = Integer.toString(carditPawbDetailsVO.getTotalPieces());
			carditTotalVO.setNumberOfReceptacles(numberOfReceptacles);
			carditTotalVO.setWeightOfReceptacles(carditPawbDetailsVO.getTotalWeight());
			Collection<CarditTotalVO> totalsInformation = new ArrayList<>();
			totalsInformation.add(carditTotalVO);
			carditVO.setTotalsInformation(totalsInformation);
			carditVO.setConsignementScreeningVOs(carditPawbDetailsVO.getConsignmentScreeningVOs());
			carditVOs.add(carditVO);
		}
		return carditVOs;
	}

	private static MailOperationsDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailOperationsDAO.class.cast(em.getQueryDAO(MODULENAME));
		} catch (PersistenceException persistenceException) {
			log.info("Exception :", persistenceException);
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO) {
		try {
			return constructDAO().findMstDocNumForAWBDetails(carditVO);
		} catch (PersistenceException exception) {
			log.info("Exception :", exception);
			throw new SystemException(exception.getErrorCode());
		}
	}

	private void findNextAWBNumberFromInternalStock(CarditVO carditVO) {
		int pawbEndRangeVal = 0;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PAWB_END_RANGE);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			log.error(e.getMessage());
		}
		if (systemParameterMap != null && systemParameterMap.get(PAWB_END_RANGE) != null) {
			String pawbEndRangeValStr = systemParameterMap.get(PAWB_END_RANGE);
			pawbEndRangeVal = Integer.parseInt(pawbEndRangeValStr);
			//TODO: Neo to correct key
			//String key = KeyUtilInstance.getInstance().getKey();
			String key = "";
			if (key != null && key.trim().length() != 0) {
				int keynum = Integer.parseInt(key);
				if (keynum <= pawbEndRangeVal) {
					StringBuilder awbNumber = new StringBuilder();
					String awb = key;
					int checkDigit = keynum % 7;
					awbNumber.append(awb).append(checkDigit);
					carditVO.getCarditPawbDetailsVO().setMasterDocumentNumber(awbNumber.toString());
				}
			}
		}
	}
}
