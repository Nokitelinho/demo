/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.sq;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.HandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMRDVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.util.template.TemplateRenderingException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5219
 *
 */
public class MailUploadController extends
		com.ibsplc.icargo.business.mail.operations.MailUploadController {
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");
	
	/**
	 *
	 * @param mailMRDMessageVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailHHTBusniessException
	 */
	public Collection<ErrorVO> handleMRDMessage(MailMRDVO mailMRDMessageVO)
			throws  RemoteException,SystemException,MailTrackingBusinessException, ForceAcceptanceException, TemplateRenderingException{
						Collection<ErrorVO> allErrorVOs = new ArrayList<>();
				Collection<OperationalFlightVO> opFltVO = null;
		if(mailMRDMessageVO.getHandovers()!=null && mailMRDMessageVO.getHandovers().get("valid_handovers")!=null){
			for(HandoverVO handover : mailMRDMessageVO.getHandovers().get("valid_handovers")){
					List<String> mailbags = handover.getMailId();
					if(mailbags!=null && !mailbags.isEmpty()){
						for(String mailBag : mailbags){
									if("POD".equals(handover.getHandOverType())) {
									
								if(mailMRDMessageVO.getHandoverErrors()==null || mailMRDMessageVO.getHandoverErrors().isEmpty()){
						handover.setCompanyCode(mailMRDMessageVO.getCompanyCode());
						    constructAndValidateMailBags(mailMRDMessageVO, handover, mailBag);
						    
											allErrorVOs= handleMessageForMRDPOD(mailMRDMessageVO,opFltVO, handover, mailBag);
						}else
							{
							allErrorVOs.addAll(mailMRDMessageVO.getHandoverErrors());
						}
					}
								else{
									allErrorVOs.addAll(handleMessageForMRDPOC(mailMRDMessageVO, handover, mailBag));
							  }
							}
						}
				}
				}
				if(mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS)!=null 
						&& !mailMRDMessageVO.getHandovers().get(MailConstantsVO.INV_HANDOVERS).isEmpty()){
					
					allErrorVOs =handleInvalidHandover(mailMRDMessageVO);
		}
		return allErrorVOs;
	}
	
		private void constructAndValidateMailBags(MailMRDVO mailMRDMessageVO, Collection<OperationalFlightVO> opFltVO,
			HandoverVO handover, String mailBag) throws SystemException {
		if (opFltVO != null) {
			for (OperationalFlightVO opFltVo : opFltVO) {
				handover.setFlightDate(opFltVo.getFlightDate());
				constructAndValidateMailBags(mailMRDMessageVO, handover, mailBag);
			}
		}
	}
	
	/**
	 *
	 * @param mailMRDMessageVO
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws MailTrackingBusinessException
	 * @throws TemplateRenderingException 
	 * @throws ForceAcceptanceException 
	 */
	public Collection<ErrorVO> handleMRDHO22Message(MailMRDVO mailMRDMessageVO)
	throws RemoteException, SystemException,MailTrackingBusinessException, ForceAcceptanceException, TemplateRenderingException{
		return handleMRDMessage(mailMRDMessageVO);
	}


}
