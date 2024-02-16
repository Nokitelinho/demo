package com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.invoker;

import com.ibsplc.neoicargo.awb.dao.AwbDAO;
import com.ibsplc.neoicargo.awb.dao.entity.AWBUserNotification;
import com.ibsplc.neoicargo.awb.dao.entity.Awb;
import com.ibsplc.neoicargo.awb.dao.entity.ShipmentKey;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Invoker;
import com.ibsplc.neoicargo.tracking.component.feature.emailnotification.EmailNotificationFeature;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestoneEvent;
import com.ibsplc.neoicargo.tracking.dao.entity.ShipmentMilestonePlan;
import com.ibsplc.neoicargo.tracking.mapper.TrackingMapper;
import com.ibsplc.neoicargo.tracking.service.TrackingService;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("emailSenderInvoker")
@AllArgsConstructor
@Slf4j
public class EmailSenderInvoker extends Invoker<ShipmentMilestoneEventVO> {

	private final EmailNotificationFeature emailFeature;
	private final TrackingMapper mapper;
	private final TrackingDAO trackingDAO;
	private final AwbDAO awbDAO;
	private final TrackingService trackingService;

	@Override
	public void invoke(ShipmentMilestoneEventVO eventVO) throws BusinessException {
		if (trackingService.isNotificationMilestone(eventVO.getMilestoneCode().getLabel())) {
			var awb = getAwb(eventVO.getShipmentKey());
			var notifications = getUserNotificationEmails(awb.getSerialNumber(), eventVO.getMilestoneCode().getLabel());
			if (!notifications.isEmpty()) {
				var pieces = getMilestoneFullyCompletedPieces(eventVO);
				if (pieces > 0) {
					emailFeature.execute(mapper.constructEmailMilestoneNotificationVO(eventVO, notifications, pieces));
				}
			}
		}
	}

	private Integer getMilestoneFullyCompletedPieces(ShipmentMilestoneEventVO eventVO) {

		var plansPieces = trackingDAO.findPlansByShipmentKeys(List.of(eventVO.getShipmentKey())).stream()
				.filter(plan -> plan.getAirportCode().equals(plan.getAirportCode()))
				.filter(plan -> plan.getMilestoneCode().equals(eventVO.getMilestoneCode().getLabel()))
				.mapToInt(ShipmentMilestonePlan::getPieces).sum();

		var eventsPieces = trackingDAO.findEventsByShipmentKeysAndType(List.of(eventVO.getShipmentKey()), "A").stream()
				.filter(event -> event.getAirportCode().equals(eventVO.getAirportCode()))
				.filter(event -> event.getMilestoneCode().equals(eventVO.getMilestoneCode().getLabel()))
				.mapToInt(ShipmentMilestoneEvent::getPieces).sum();
		log.info("Planned and actual pieces : {} - {}", plansPieces, eventsPieces);

		return eventsPieces >= plansPieces ? eventsPieces : 0;
	}

	private List<AWBUserNotification> getUserNotificationEmails(Long awbSerialNumber, String milestoneCode) {
		return awbDAO.findAwbUserNotificationsByAwbSerialNumber(awbSerialNumber).stream()
				.filter(notification -> notification.getNotificationMilestones().contains(milestoneCode))
				.collect(Collectors.toList());
	}

	private Awb getAwb(String shipmentKey) {
		return awbDAO.findAwbByShipmentKey(new ShipmentKey(shipmentKey.split("-")[0], shipmentKey.split("-")[1]))
				.orElseThrow(() -> new RuntimeException(
						String.format("No AWB found for a shipmentKey : {%s}", shipmentKey)));
	}

}
