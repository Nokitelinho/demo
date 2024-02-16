package com.ibsplc.neoicargo.awb.component.feature.saveawb.persistor;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.awb.mapper.AwbEntityMapper;
import com.ibsplc.neoicargo.awb.vo.AwbVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component("saveAwbPersistor")
@RequiredArgsConstructor
public class SaveAwbPersistor {

    private final AwbEntityMapper awbEntityMapper;
    private final AwbDAO awbDao;

    public void persist(AwbVO awbVO) {
        var possibleAwb = awbDao.findAwbByShipmentKey(
                new ShipmentKey(awbVO.getShipmentPrefix(),
                        awbVO.getMasterDocumentNumber()));
        if (possibleAwb.isEmpty()) {
            var awb = awbEntityMapper.constructAwb(awbVO);
            log.info("Creating AWB... {}-{}", awb.getShipmentKey().getShipmentPrefix(),
                    awb.getShipmentKey().getMasterDocumentNumber());
            Optional.ofNullable(awb.getAwbContactDetails()).ifPresent(details -> details.setAwb(awb));
            awbDao.saveAwb(awb);
            log.info("AWB Serial Number {}-{}:{}", awb.getShipmentKey().getShipmentPrefix(),
                    awb.getShipmentKey().getMasterDocumentNumber(), awb.getSerialNumber());
        } else {
            var awb = awbEntityMapper.mergeAwb(awbVO, possibleAwb.get());
            Optional.ofNullable(awb.getAwbContactDetails()).ifPresent(details -> details.setAwb(awb));
            awbDao.saveAwb(awb);
        }
    }

}