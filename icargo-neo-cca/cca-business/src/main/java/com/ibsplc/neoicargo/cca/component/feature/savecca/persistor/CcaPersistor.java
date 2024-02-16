package com.ibsplc.neoicargo.cca.component.feature.savecca.persistor;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.dao.entity.CcaWorkflow;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaCustomerDetailMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaTaxMapper;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.INSERT_OPERATION;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.UPDATE_OPERATION;

@Slf4j
@Component
@AllArgsConstructor
public class CcaPersistor {

    private final CcaDao ccaDao;
    private final CcaMasterMapper ccaMasterMapper;
    private final QuantityMapper quantityMapper;
    private final CcaAwbDetailMapper ccaAwbDetailMapper;
    private final CcaCustomerDetailMapper ccaCustomerDetailMapper;
    private final CcaTaxMapper taxMapper;
    private final ContextUtil contextUtil;

    public CCAMasterVO persist(CCAMasterVO ccaMasterVO) {
        log.info("CcaPersistor.persist INVOKED");
        var loginProfile = contextUtil.callerLoginProfile();
        ccaDao.findCCAMaster(ccaMasterMapper.ccaMasterVOToCcaFilterVO(ccaMasterVO))
                .ifPresentOrElse(ccaMaster -> {
                            log.info("Existing CCA Found! Updating operation has been triggered");
                            ccaMasterVO.setOperation(UPDATE_OPERATION);
                            if (ccaMasterVO.getCcaStatus().equals(CcaStatus.A)
                                    && !ccaMaster.getCcaStatus().equals(CcaStatus.A)) {
                                //If status changed to Approve -> add 'Approve' step
                                if (ccaMaster.getCcaStatus().equals(CcaStatus.R)) {
                                    //If old status was Rejected, remove 'Rejected' step
                                    ccaMaster.getCcaWorkflow().stream()
                                            .filter(s -> s.getCcaStatus().equals(CcaStatus.R))
                                            .findFirst()
                                            .ifPresent(rStep -> ccaMaster.getCcaWorkflow().remove(rStep));
                                }
                                ccaMaster.getCcaWorkflow()
                                        .add(
                                                buildCcaWorkflow(
                                                        ccaMaster,
                                                        loginProfile.getUserId(),
                                                        Optional.ofNullable(ccaMaster.getCcaAssignee())
                                                                .orElse(buildUsername(loginProfile)),
                                                        CcaStatus.A
                                                )
                                        );
                            } else if (ccaMasterVO.getCcaStatus().equals(CcaStatus.R)
                                    && !ccaMaster.getCcaStatus().equals(CcaStatus.R)) {
                                ccaMaster.getCcaWorkflow()
                                        .add(
                                                buildCcaWorkflow(
                                                        ccaMaster,
                                                        loginProfile.getUserId(),
                                                        Optional.ofNullable(ccaMaster.getCcaAssignee())
                                                                .orElse(buildUsername(loginProfile)),
                                                        CcaStatus.R
                                                )
                                        );
                            } else if (ccaMasterVO.getCcaStatus().equals(CcaStatus.S)
                                    && !ccaMaster.getCcaStatus().equals(CcaStatus.S)) {
                                ccaMaster.getCcaWorkflow()
                                        .add(
                                                buildCcaWorkflow(
                                                        ccaMaster,
                                                        loginProfile.getUserId(),
                                                        Optional.ofNullable(ccaMaster.getCcaAssignee())
                                                                .orElse(buildUsername(loginProfile)),
                                                        CcaStatus.S
                                                )
                                        );
                            }
                            ccaMaster.update(
                                    ccaMasterVO, quantityMapper, ccaAwbDetailMapper, ccaCustomerDetailMapper, taxMapper
                            );
                        },
                        () -> {
                            ccaMasterVO.setOperation(INSERT_OPERATION);
                            final var newCcaMaster = ccaMasterMapper.constructCCAMasterFromVo(ccaMasterVO);
                            newCcaMaster.setCcaAssignee(loginProfile.getFirstName() + " " + loginProfile.getLastName());

                            //create 'New' workflow step
                            newCcaMaster.setCcaWorkflow(
                                    new HashSet<>(Set.of(
                                            buildCcaWorkflow(
                                                    newCcaMaster,
                                                    loginProfile.getUserId(),
                                                    buildUsername(loginProfile),
                                                    CcaStatus.N
                                            )
                                    ))
                            );

                            if (CcaStatus.A.equals(ccaMasterVO.getCcaStatus())) {
                                newCcaMaster.getCcaWorkflow()
                                        .add(
                                                buildCcaWorkflow(
                                                        newCcaMaster,
                                                        loginProfile.getUserId(),
                                                        buildUsername(loginProfile),
                                                        CcaStatus.A
                                                )
                                        );
                            }

                            log.info("New CcaMaster has been created - " + newCcaMaster);
                            newCcaMaster.getCcaAwb().forEach(ccaawb -> ccaawb.setCcaMaster(newCcaMaster));
                            ccaDao.saveCCA(newCcaMaster);
                        });
        return ccaMasterVO;
    }

    public static CcaWorkflow buildCcaWorkflow(CcaMaster ccaMaster, String userId, String username, CcaStatus ccaStatus) {
        CcaWorkflow ccaWorkflow = new CcaWorkflow();
        ccaWorkflow.setCcaMaster(ccaMaster);
        ccaWorkflow.setUserId(userId);
        ccaWorkflow.setUsername(username);
        ccaWorkflow.setCcaStatus(ccaStatus);
        ccaWorkflow.setRequestedDate(LocalDateTime.now());
        return ccaWorkflow;
    }

    public static String buildUsername(LoginProfile loginProfile) {
        if (Objects.isNull(loginProfile.getLastName())) {
            return loginProfile.getFirstName();
        }
        return loginProfile.getFirstName() + " " + loginProfile.getLastName();
    }

}
