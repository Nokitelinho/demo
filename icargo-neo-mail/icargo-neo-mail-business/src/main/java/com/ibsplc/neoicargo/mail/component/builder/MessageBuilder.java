package com.ibsplc.neoicargo.mail.component.builder;


import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.*;
import com.ibsplc.neoicargo.mail.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j
public class MessageBuilder {

    @Autowired
    private MLDController mldController;

    public void flagMLDForMailAcceptance(MailAcceptanceVO mailAcceptanceVO,
                                         Collection<MailbagVO> mailbags)throws SystemException{
        log.debug("MessageBuilder", "flagMLDForMailAcceptance");
        mldController.flagMLDForMailAcceptance(mailAcceptanceVO, mailbags);
        log.debug("MessageBuilder", "flagMLDForMailAcceptance");

    }
    public void flagMLDForMailOperations(Collection<MailbagVO> mailbagVOs,
                                         String mode)throws SystemException{
        log.debug("MessageBuilder", "flagMLDForMailOperations");
        mldController.flagMLDForMailOperations(mailbagVOs, mode);
        log.debug("MessageBuilder", "flagMLDForMailOperations");

    }
    public void flagMLDForMailReassignOperations(
            Collection<MailbagVO> mailbagVOs, ContainerVO toContainerVO,
            String mode) throws SystemException{
        log.debug("flagMLDForMailReassignOperations");
        mldController.flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,mode);
        if("B".equals(toContainerVO.getType())){
            mldController.flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,MailConstantsVO.MLD_STG);
        }
        mldController.flagMLDForMailReassignOperations(mailbagVOs,toContainerVO,MailConstantsVO.MLD_NST);
        log.debug("flagMLDForMailReassignOperations");

    }
    public void flagMLDForContainerTransfer(Collection<MailbagVO> mailbagVOs,
                                            Collection<ContainerVO> containerVOs,
                                            OperationalFlightVO operationalFlightVO) throws SystemException{
        log.debug("MessageBuilder", "flagMLDForUpliftedMailbags");
        mldController.flagMLDForContainerTransfer(mailbagVOs, containerVOs, operationalFlightVO);
        log.debug("MessageBuilder", "flagMLDForUpliftedMailbags");
    }
    public void flagMLDForMailbagTransfer(Collection<MailbagVO> mailbagVOs,
                                          ContainerVO containerVO, OperationalFlightVO operationalFlightVO) throws SystemException{
        log.debug("MessageBuilder", "flagMLDForUpliftedMailbags");
        if(containerVO.getContainerNumber()!=null){
            mldController.flagMLDForMailbagTransfer(mailbagVOs, containerVO,null,MailConstantsVO.MLD_HND);
        }
        mldController.flagMLDForMailbagTransfer(mailbagVOs, containerVO,null,MailConstantsVO.MLD_TFD);
        if("B".equals(containerVO.getType()))
        {
            mldController.flagMLDForMailReassignOperations(mailbagVOs,containerVO,MailConstantsVO.MLD_STG);
        }
        mldController.flagMLDForMailReassignOperations(mailbagVOs,containerVO,MailConstantsVO.MLD_NST);
        log.debug("MessageBuilder", "flagMLDForUpliftedMailbags");
    }
    public void flagMLDForContainerReassign(
            Collection<ContainerVO> containerVOs, OperationalFlightVO toFlightVO) throws SystemException{
        log.debug("MessageBuilder", "flagMLDForContainerReassign");
        mldController.flagMLDForContainerReassign(containerVOs,toFlightVO);
        log.debug("MessageBuilder", "flagMLDForContainerReassign");

    }
    
    public void flagMLDForMailbagReturn(Collection<MailbagVO> mailbags) throws SystemException{
        log.debug("MessageBuilder", "flagMLDForMailbagReturn");
        mldController.flagMLDForMailbagReturn(mailbags);
        log.debug("MessageBuilder", "flagMLDForMailbagReturn");

    }
    public void flagMLDForMailOperationsInULD(ContainerVO containerVo, String mode)throws SystemException{
        mldController.flagMLDForMailOperationsInULD(containerVo,mode);
    }

    public void flagMLDForUpliftedMailbags(Collection<OperationalFlightVO> operationalFlightVOs) {
        mldController.flagMLDForUpliftedMailbags(operationalFlightVOs);
    }
}








