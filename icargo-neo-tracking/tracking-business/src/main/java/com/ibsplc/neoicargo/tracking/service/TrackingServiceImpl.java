package com.ibsplc.neoicargo.tracking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.framework.core.lang.notation.BusinessService;
import com.ibsplc.neoicargo.tracking.component.TrackingComponent;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;

import lombok.RequiredArgsConstructor;

@Component("trackingService")
@BusinessService
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {

	private final TrackingComponent trackingComponent;

	@Override
	public List<ShipmentDetailsVO> getShipments(List<AwbRequestVO> awbs) {
		return trackingComponent.getShipments(awbs);
	}

	@Override
	public List<SplitVO> getShipmentSplits(AwbRequestVO awb) {
		return trackingComponent.getShipmentSplits(awb);
	}

	@Override
	public void saveShipmentMilestonePlans(ShipmentMilestonePlanCreatedVO plan) {
		trackingComponent.saveTrackingShipmentMilestonePlan(plan);
	}

	@Override
	public void deleteShipmentMilestonePlans(ShipmentMilestonePlanDeletedVO event) {
		trackingComponent.deleteTrackingShipmentMilestonePlan(event);
	}

	@Override
	public void saveShipmentMilestoneEvent(ShipmentMilestoneEventVO event) {
		trackingComponent.saveShipmentMilestoneEvent(event);
	}

	@Override
	public List<ShipmentActivityVO> getShipmentActivities(AwbRequestVO awb) {
		return trackingComponent.getShipmentActivities(awb);
	}

	@Override
	public List<MilestoneMasterVO> findAllMilestones() {
		return trackingComponent.findAllMilestones();
	}

	@Override
	public boolean isActivityViewMilestone(String milestoneCode) {
		return trackingComponent.isActivityViewMilestone(milestoneCode);
	}

	@Override
	public boolean isNotificationMilestone(String milestoneCode) {
		return trackingComponent.isNotificationMilestone(milestoneCode);
	}
	
}
