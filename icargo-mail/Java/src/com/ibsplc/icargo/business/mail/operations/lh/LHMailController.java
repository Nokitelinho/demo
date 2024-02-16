package com.ibsplc.icargo.business.mail.operations.lh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.MailController;

import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class LHMailController
		extends com.ibsplc.icargo.business.mail.operations.MailController {
	private static final Log LOGGER = LogFactory.getLogger("LHMailController");
	private static final String MODULENAME = "mail.operations";
	public static final String CARDIT_TYPE = "ACP";
	private static final String KEY_CARDIT_AWB =
			"KEY_CARDIT_AWB";
	private static final String PAWB_END_RANGE="mailtracking.defaults.pawbendrange";
	@Override
	public void createPAWBForConsignment(int noOfDays) throws SystemException {
		
		Collection<CarditPawbDetailsVO>	carditPawbDetailsVOs = null;
		Collection <CarditVO>  carditVOs = null;
		carditPawbDetailsVOs = findMailbagsForPAWBCreation(noOfDays);
		carditVOs =  constructCarditVO(carditPawbDetailsVOs);
		boolean pawbCountryValidation =false;
		boolean awbrangelimit = false;
		for(CarditVO carditVO:carditVOs){

			try {
				
				if(carditVO.getCarditPawbDetailsVO()!=null) {
				new MailController().findFieldsOfCarditPawb(carditVO);
				pawbCountryValidation = new MailController().findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
				findMstDocNumAndCallExcecute(pawbCountryValidation, carditVO,awbrangelimit);
				}
			} catch (SystemException | BusinessException exce) {
				LOGGER.log(Log.INFO, exce);
			}
				
		}
	}



	private void findMstDocNumAndCallExcecute(boolean pawbCountryValidation, CarditVO carditVO, boolean awbrangelimit)
			throws SystemException, BusinessException {
		AWBDetailVO aWBDetailVO = null;
		if(pawbCountryValidation) {
			aWBDetailVO = findMstDocNumForAWBDetails(carditVO);
			awbrangelimit = updateCarditBasedOnAWBFromInternalStock(aWBDetailVO, carditVO,awbrangelimit);
			if(awbrangelimit){
		SavePAWBDetailsFeature	savePAWBDetailsFeature = (SavePAWBDetailsFeature) SpringAdapter.getInstance()
					.getBean("mail.operations.savepawbdetails.savePAWBDetailsFeature");
		savePAWBDetailsFeature.execute(carditVO);
		}
	}
	}



	private boolean updateCarditBasedOnAWBFromInternalStock(AWBDetailVO aWBDetailVO, CarditVO carditVO, boolean awbrangelimit)
			throws SystemException {
		if(aWBDetailVO != null && aWBDetailVO.getMasterDocumentNumber() != null){
			carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
			carditVO.getCarditPawbDetailsVO().setMasterDocumentNumber(aWBDetailVO.getMasterDocumentNumber());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next().setMailBagDocumentOwnerIdr(aWBDetailVO.getOwnerId());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next().setMailDuplicateNumber(aWBDetailVO.getDuplicateNumber());
			carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs().iterator().next().setSequenceNumberOfMailbag(aWBDetailVO.getSequenceNumber());
			awbrangelimit = true;
		}
		
		else{
			
			awbrangelimit = findNextAWBNumberFromInternalStock(carditVO,awbrangelimit);
		}
		return awbrangelimit;
	}

	

	private Collection<CarditPawbDetailsVO> findMailbagsForPAWBCreation(
			int noOfDays) throws SystemException {
			Collection<CarditPawbDetailsVO> carditPawbDetails = null;
			carditPawbDetails = constructDAO().findMailbagsForPAWBCreation(noOfDays);
			return carditPawbDetails;
	}
	
    	
	private Collection<CarditVO> constructCarditVO(Collection<CarditPawbDetailsVO> carditPawbDetailsVOs) throws SystemException {
		CarditVO carditVO = null;
		Collection<CarditVO> carditVOs = new ArrayList<>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		for(CarditPawbDetailsVO carditPawbDetailsVO:carditPawbDetailsVOs){
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
      
	private static MailTrackingDefaultsDAO constructDAO()
    	throws SystemException {
    		try {
    			EntityManager em = PersistenceController.getEntityManager();
    			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULENAME));
    		} catch (PersistenceException persistenceException) {
				LOGGER.log(Log.INFO, persistenceException);
    			throw new SystemException(persistenceException.getErrorCode());
    		}
    	}
	
	public AWBDetailVO findMstDocNumForAWBDetails(CarditVO carditVO) throws SystemException {
		try {
			return constructDAO().findMstDocNumForAWBDetails(carditVO);
		} catch(PersistenceException exception) {
			LOGGER.log(Log.INFO, exception);
			throw new SystemException(exception.getErrorCode());
		}
	}
	
	


	private boolean findNextAWBNumberFromInternalStock(CarditVO carditVO, boolean awbrangelimit) throws SystemException {
		int pawbEndRangeVal = 0;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PAWB_END_RANGE);

		HashMap<String, String> systemParameterMap = null;
		systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameters);
		if(systemParameterMap != null && systemParameterMap.get(PAWB_END_RANGE)!= null){
			String pawbEndRangeValStr = systemParameterMap.get(PAWB_END_RANGE);
			pawbEndRangeVal = Integer.parseInt(pawbEndRangeValStr);
			Criterion criterion = KeyUtils.getCriterion(carditVO
					.getCompanyCode(), KEY_CARDIT_AWB);
			String key = KeyUtilInstance.getInstance().getKey(criterion);
			StringBuilder keyBuilder = new StringBuilder(7);
			if(key!= null && key.trim().length()!= 0){
				int keyLength = key.length();
				for (int count = 0; count < 7 - keyLength; count++) {
					keyBuilder.append("0");
				}
				keyBuilder.append(key);
			String appendedKey = new StringBuilder().append(keyBuilder)
					.toString();
				
			int keynum = Integer.parseInt(appendedKey);
				if(keynum <= pawbEndRangeVal) {
					StringBuilder awbNumber = new StringBuilder();
					String awb = appendedKey;
					int checkDigit= keynum%7;
					awbNumber.append(awb).append(checkDigit);
					carditVO.getCarditPawbDetailsVO().setMasterDocumentNumber(awbNumber.toString());
					awbrangelimit = true;
					
				}
			}
			}
		return awbrangelimit;
		
	}

}