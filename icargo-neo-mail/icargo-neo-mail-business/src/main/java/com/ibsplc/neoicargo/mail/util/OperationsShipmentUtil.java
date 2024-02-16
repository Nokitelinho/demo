package com.ibsplc.neoicargo.mail.util;

import com.ibsplc.icargo.business.operations.shipment.vo.*;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.awb.AWBAPI;
import com.ibsplc.neoicargo.awb.OperationsShipmentBI;
import com.ibsplc.neoicargo.awb.exception.ShipmentBusinessException;
import com.ibsplc.neoicargo.awb.modal.AWBIndexModel;
import com.ibsplc.neoicargo.awb.modal.AWBValidationModel;
import com.ibsplc.neoicargo.awb.model.ShipmentDetailFilterModel;
import com.ibsplc.neoicargo.awb.model.ShipmentDetailModel;
import com.ibsplc.neoicargo.awb.model.ShipmentFilterModel;
import com.ibsplc.neoicargo.awb.model.ShipmentValidationModel;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClient;
import com.ibsplc.neoicargo.framework.tenant.jaxrs.client.RegisterJAXRSClients;

import com.ibsplc.neoicargo.mail.mapper.OperationsShipmentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Component
@RegisterJAXRSClients(value={@RegisterJAXRSClient(clazz = OperationsShipmentBI.class, targetService = "neo-awb-business"),
        @RegisterJAXRSClient(clazz = AWBAPI.class, targetService = "neo-awb-business", name = "awb-api")})
public class OperationsShipmentUtil {

    @Autowired
    private OperationsShipmentBI operationsShipmentBI;

    @Autowired
    private OperationsShipmentMapper operationsShipmentMapper;

    @Autowired
    private AWBAPI awbapi;

    public ShipmentValidationVO saveShipmentDetails(ShipmentDetailVO shipmentDetailVO) {
        try {
            ShipmentDetailModel shipmentDetailModel = operationsShipmentMapper
                    .shipmentDetailVOtoShipmentDetailModel(shipmentDetailVO);
            shipmentDetailModel.setLastUpdateTime(new LocalDate("***", Location.NONE,true));
           ShipmentValidationModel shipmentValidationModel =  operationsShipmentBI.saveShipmentDetails(shipmentDetailModel);
           return operationsShipmentMapper.shipmentValidationModeltoShipmentValidationVo(shipmentValidationModel);
        } catch (ShipmentBusinessException e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<ShipmentVO> findShipments(ShipmentFilterVO shipmentFilterVO) {
        ShipmentFilterModel shipmentFilterModel =  operationsShipmentMapper
                .shipmentFilterVOtoShipmentFilterModel(shipmentFilterVO);
        try {
            return operationsShipmentMapper.shipmentModelsTOShipmentVOs(
                    operationsShipmentBI.findAllShipments(shipmentFilterModel));
        }catch(ServiceException e){
                throw new RuntimeException(e);
            }
    }

    public ShipmentDetailVO findShipmentDetails(ShipmentDetailFilterVO shipmentDetailFilterVO) {
        ShipmentDetailFilterModel shipmentFilterModel =  operationsShipmentMapper
                .shipmentDetailFilterVOtoShipmentDetailFilterModel(shipmentDetailFilterVO);
        try {
            return operationsShipmentMapper.shipmentDetailModelToShipmentDetailVO(
                    operationsShipmentBI.findShipmentDetails(shipmentFilterModel));
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        } catch (CurrencyException e) {
            throw new RuntimeException(e);
        }
     catch (Exception e) {
            throw new RuntimeException(e);
    }
    }
    public Collection<String> deleteAWB(ShipmentValidationVO shipmentValidationVO, String source)
            throws BusinessException {
        try {
            return operationsShipmentBI.deleteAWB(operationsShipmentMapper.shipmentValidationVOtoShipmentValidationModel(shipmentValidationVO), source);
        }
        catch (ShipmentBusinessException e) {
            throw new RuntimeException(e);
        }catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }
    public Collection<ShipmentValidationVO> saveHAWBDetails(ShipmentValidationVO shipmentValidationVO,
                                                            Collection<HAWBVO> hawbs, boolean isConsolStatusTobeChanged) {
        Collection<ShipmentValidationModel> shipmentValidationModels = new ArrayList<>();
        try {
            shipmentValidationModels = operationsShipmentBI.saveHAWBDetails(operationsShipmentMapper.shipmentValidationVOtoShipmentValidationModel(shipmentValidationVO),
                    operationsShipmentMapper.hAWBVOsTOHAWBModel(hawbs),
                    isConsolStatusTobeChanged);
            return operationsShipmentMapper.shipmentValidationModelstoShipmentValidationVOs(shipmentValidationModels);
        } catch (ShipmentBusinessException e) {
            throw new RuntimeException(e);
        }
    }

    public List<AWBIndexModel> validateShipmentDetails(String awb) {
        AWBValidationModel awbValidationModel = new AWBValidationModel();
        awbValidationModel.retrieveAll(true);
        try {
            return awbapi.validateAWB(awb, awbValidationModel);
        } catch (BusinessException e) {
            throw new RuntimeException(e);
        }
    }
}
