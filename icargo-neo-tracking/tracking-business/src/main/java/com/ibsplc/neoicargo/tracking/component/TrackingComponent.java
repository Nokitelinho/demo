package com.ibsplc.neoicargo.tracking.component;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.awb.vo.AwbValidationVO;
import com.ibsplc.neoicargo.tracking.component.feature.deleteshipment.DeleteShipmentFeature;
import com.ibsplc.neoicargo.tracking.component.feature.deleteshipmentplan.DeleteShipmentMilestonePlanFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getmilestones.GetMilestonesFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipment.getshipmentlist.GetShipmentListFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipmentactivities.GetShipmentActivitiesFeature;
import com.ibsplc.neoicargo.tracking.component.feature.getshipmentsplits.GetShipmentSplitsFeature;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentevent.SaveShipmentMilestoneEventFeature;
import com.ibsplc.neoicargo.tracking.component.feature.saveshipmentplan.SaveShipmentMilestonePlanFeature;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("trackingComponent")
@RequiredArgsConstructor
public class TrackingComponent {

	private final GetShipmentListFeature getShipmentListFeature;
	private final DeleteShipmentFeature deleteShipmentFeature;
	private final SaveShipmentMilestonePlanFeature saveShipmentMilestonePlanFeature;
	private final DeleteShipmentMilestonePlanFeature deleteShipmentMilestonePlanFeature;
	private final GetShipmentSplitsFeature getShipmentSplitsFeature;
	private final SaveShipmentMilestoneEventFeature saveShipmentMilestoneEventFeature;
	private final GetShipmentActivitiesFeature getShipmentActivitiesFeature;
	private final GetMilestonesFeature getMilestonesFeature;

	public List<ShipmentDetailsVO> getShipments(List<AwbRequestVO> awbs) {
		log.info("Entering get shipments");
		return getShipmentListFeature.perform(awbs);
	}

	public List<SplitVO> getShipmentSplits(AwbRequestVO awb) {
		log.info("Entering getShipmentSplits : {} ", awb);
		return getShipmentSplitsFeature.perform(awb);
	}

	public void deleteShipment(AwbValidationVO awbValidationVO) {
		log.info("Entering delete shipment");
		deleteShipmentFeature.perform(awbValidationVO);
	}

	public void saveTrackingShipmentMilestonePlan(ShipmentMilestonePlanCreatedVO planCreatedVO) {
		log.info("Entering save tracking shipment milestone plan");
		saveShipmentMilestonePlanFeature.execute(planCreatedVO);
	}

	public void deleteTrackingShipmentMilestonePlan(ShipmentMilestonePlanDeletedVO event) {
		log.info("Entering Delete tracking shipment milestone plan");
		deleteShipmentMilestonePlanFeature.perform(event);
	}

	public void saveShipmentMilestoneEvent(ShipmentMilestoneEventVO event) {
		log.info("Entering Save shipment milestone event");
		saveShipmentMilestoneEventFeature.execute(event);
	}

	public List<ShipmentActivityVO> getShipmentActivities(AwbRequestVO awb) {
		log.info("Entering getShipmentActivities : {} ", awb);
		return getShipmentActivitiesFeature.perform(awb);
	}

	public List<MilestoneMasterVO> findAllMilestones() {
		log.info("Entering findAllMilestones ");
		return getMilestonesFeature.getMilestones();
	}
	
	public boolean isNotificationMilestone(String milestoneCode) {
		log.info("Entering isNotificationMilestone ");
		return getMilestonesFeature.isNotificationMilestone(milestoneCode);
	}

	public boolean isActivityViewMilestone(String milestoneCode) {
		log.info("Entering isActivityViewMilestone ");
		return getMilestonesFeature.isActivityViewMilestone(milestoneCode);
	}

}
