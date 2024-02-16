package com.ibsplc.neoicargo.awb.component.feature.getawbcontactdetails;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbContactDetailsVO;
import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.ws.rs.NotFoundException;
import java.util.Objects;

import static com.ibsplc.neoicargo.tracking.exception.TrackingErrors.AWB_NOT_FOUND;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetAwbContactDetailsFeature {

    private final AwbDAO awbDAO;
    private final AwbEntityMapper mapper;

    public AwbContactDetailsVO perform(AwbRequestVO awbRequestVO) throws NotFoundException {
        var shipmentKey = new ShipmentKey(awbRequestVO.getShipmentPrefix(), awbRequestVO.getMasterDocumentNumber());
        var awb = awbDAO.findAwbByShipmentKey(shipmentKey)
                .orElseThrow(() -> new NotFoundException(AWB_NOT_FOUND.getErrorMessage()));

        if (Objects.isNull(awb.getAwbContactDetails())) {
            log.info("No AWB personal information found for the shipment key and serial number : {}, {}", awb.getShipmentKey(), awb.getSerialNumber());
            return null;
        }

        return mapper.constructAwbContactDetailsVO(awb.getAwbContactDetails());
    }

}
