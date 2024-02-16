package com.ibsplc.icargo.business.mail.operations.lh;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy;
public class LHMailUploadController
		extends com.ibsplc.icargo.business.mail.operations.MailUploadController {
	private static final Log LOGGER = LogFactory.getLogger("LHMailUploadController");
	public void saveScreeningDetails (ScannedMailDetailsVO scannedMailDetailsVO) throws FinderException, SystemException{
       if((MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(scannedMailDetailsVO.getProcessPoint()) || MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(scannedMailDetailsVO.getProcessPoint())
    		   ||MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(scannedMailDetailsVO.getProcessPoint())
    		   || (MailConstantsVO.CONTAINER_STATUS_TRANSFER.equals(scannedMailDetailsVO.getContainerProcessPoint())&& !MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getProcessPoint())))
    		   &&scannedMailDetailsVO.getMailDetails()!=null&&!scannedMailDetailsVO.getMailDetails().isEmpty()){
    	  
    	  for(MailbagVO mailbagVo:scannedMailDetailsVO.getMailDetails()){
    		  
    			addSecurityDetails(scannedMailDetailsVO, mailbagVo);
    	  }
	}
	}

	private void addSecurityDetails(ScannedMailDetailsVO scannedMailDetailsVO, MailbagVO mailbagVo)
			throws FinderException, SystemException {
		String mailBagId = mailbagVo.getMailbagId()!=null&&mailbagVo.getMailbagId().trim().length()>0?mailbagVo.getMailbagId():null;
		String companyCode = scannedMailDetailsVO.getCompanyCode();
		long malseqnum=0;
  	  
 		  try {
		 	 malseqnum= mailbagVo.getMailSequenceNumber() == 0
					      ? Mailbag.findMailBagSequenceNumberFromMailIdr(mailBagId,companyCode)
					      : mailbagVo.getMailSequenceNumber();
			} catch (SystemException e1) {
				LOGGER.log(Log.INFO, e1);
			}
		 	  
  	  
  	   
  	   if(scannedMailDetailsVO.isScreeningPresent()){

        	findMailbag(scannedMailDetailsVO, companyCode, malseqnum);
         }
  	  
   
saveScreeningValues(scannedMailDetailsVO, mailBagId, companyCode, malseqnum);
	}

	private void saveScreeningValues(ScannedMailDetailsVO scannedMailDetailsVO, String mailBagId, String companyCode,
			 long seqnum) {
		ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
     	int raCount = 0;
     	
     	UserVO userVO = null;
		String raid = null;
		String country = null;
     	if(scannedMailDetailsVO.getConsignmentScreeningVos()!=null){
		 consignmentScreeningVos =
				scannedMailDetailsVO.getConsignmentScreeningVos();
        
     	}
    	try {
			
    		raCount = Mailbag.findScreeningDetails(mailBagId, companyCode);
				userVO = Proxy.getInstance().get(AdminUserProxy.class).findUserDetails(companyCode, scannedMailDetailsVO.getScannedUser());
				
			if(userVO!=null) {
				raid =userVO.getUserParameterVOs()
						.stream()
						.filter(param -> param.getParameterCode().equals("admin.user.raid"))
				        .map(parval -> parval.getParameterValue())
				        .collect(Collectors.toList())
				        .get(0);
				country = userVO.getUserParameterVOs()
						.stream()
						.filter(param -> param.getParameterCode().equals("admin.user.country"))
				        .map(parval -> parval.getParameterValue())
				       .collect(Collectors.toList())
				       .get(0);
				}
    		
    		
    		
    		if(raCount== 0){
    			
    			if(scannedMailDetailsVO.isScreeningPresent()){
					checkRAFormat(raid, consignmentScreeningVO, companyCode, scannedMailDetailsVO);
			consignmentScreeningVos.add(consignmentScreeningVO);

    			}
    		}
    		
		
       		consignmentScreeningVos.forEach(consignmentScreeningVo->consignmentScreeningVo.setMalseqnum(seqnum));
       		
       		 new MailController().saveSecurityDetails(consignmentScreeningVos);   
		} catch (SystemException | ProxyException e) {
			LOGGER.log(Log.INFO, e);
		}
	}

	private void findMailbag(ScannedMailDetailsVO scannedMailDetailsVO, String companyCode,
			 long seqnum) throws FinderException, SystemException {
		MailbagPK mailBagPK = new MailbagPK();
		Mailbag mailBag = null;
		mailBagPK.setCompanyCode(companyCode);
		mailBagPK.setMailSequenceNumber(seqnum);
		mailBag = Mailbag.find(mailBagPK);
		if (mailBag != null&&scannedMailDetailsVO.isScreeningPresent()) {
			mailBag.setSecurityStatusCode(MailConstantsVO.SECURITY_STATUS_CODE_SPX);
		}
		
	}
	
	@Override
	public void saveScreeningConsginorDetails(Map<String, Object> contTransferMap) {
        ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();

       Collection<MailbagVO> mailbagVOs = new ArrayList<>();
        Collection<ContainerDetailsVO> containerDetailsCollection = null;
      
        TransferManifestVO transferManifestVO = new TransferManifestVO();
        
        if (contTransferMap!=null && contTransferMap.get(MailConstantsVO.CONST_CONTAINER_DETAILS) != null) {
        	
        	containerDetailsCollection = (Collection<ContainerDetailsVO>) contTransferMap
					.get(MailConstantsVO.CONST_CONTAINER_DETAILS);
        	
        	
        	transferManifestVO.setDsnVOs(new ArrayList<DSNVO>());
			for (ContainerDetailsVO container : containerDetailsCollection) {
				if (container.getDsnVOs() != null
						&& !container.getDsnVOs().isEmpty()) {
					for(DSNVO dsnVO:container.getDsnVOs()){
						 MailbagVO mailbagVO = new MailbagVO();
					      mailbagVO.setMailSequenceNumber(dsnVO.getMailSequenceNumber()); 
					      mailbagVO.setMailbagId(dsnVO.getMailbagId());
					      scannedMailDetailsVO.setScannedUser(dsnVO.getScannedUser());
					      scannedMailDetailsVO.setCompanyCode(dsnVO.getCompanyCode());
					      mailbagVOs.add(mailbagVO);
						
					}
					 
				}
	
				}
			scannedMailDetailsVO.setContainerProcessPoint(MailConstantsVO.CONTAINER_STATUS_TRANSFER);
			scannedMailDetailsVO.setMailDetails(mailbagVOs);
	        try {
				saveScreeningDetails(scannedMailDetailsVO);
			} catch (FinderException | SystemException e) {
				LOGGER.log(Log.INFO, e);
			} 
			}
		
        	
        	
        }
	public void checkRAFormat(String raid,ConsignmentScreeningVO consignmentScreeningVO, String companyCode, ScannedMailDetailsVO scannedMailDetailsVO) {
		if (raid != null) {
			consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
			consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
			consignmentScreeningVO.setCompanyCode(companyCode);
			consignmentScreeningVO.setSource(MailConstantsVO.SOURCE_HHT);
			consignmentScreeningVO.setAgentType(MailConstantsVO.RA_ISSUING);
			consignmentScreeningVO.setScreeningLocation(scannedMailDetailsVO.getAirportCode());
			if (raid.contains("/")) {
				String[] consignmentContractRefNumber = raid.split("/");
				setRADetails(consignmentScreeningVO, consignmentContractRefNumber);
			} else {
				consignmentScreeningVO.setAgentID(raid);
			}
		}
	}
	private ConsignmentScreeningVO setRADetails(ConsignmentScreeningVO consignmentScreeningVO,
			String[] consignmentContractRefNumber) {
		if (consignmentContractRefNumber.length > 0 
				&& consignmentContractRefNumber[0] != null
				&& consignmentContractRefNumber[0].length() == 2) {
			consignmentScreeningVO.setIsoCountryCode(consignmentContractRefNumber[0]);
		}
		if (consignmentContractRefNumber.length > 2) {
			consignmentScreeningVO.setAgentID(consignmentContractRefNumber[2]);
		}
		if (consignmentContractRefNumber.length > 3) {
			consignmentScreeningVO.setExpiryDate(consignmentContractRefNumber[3]);
		}
		return consignmentScreeningVO;
	}
        

}