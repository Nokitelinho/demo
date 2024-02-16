package com.ibsplc.neoicargo.tracking.service;

import java.util.List;

import com.ibsplc.neoicargo.awb.vo.AwbRequestVO;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentActivityVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanCreatedVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanDeletedVO;
import com.ibsplc.neoicargo.tracking.vo.SplitVO;

public interface TrackingService {

	List<ShipmentDetailsVO> getShipments(List<AwbRequestVO> awbs);

	List<SplitVO> getShipmentSplits(AwbRequestVO awb);

	void saveShipmentMilestonePlans(ShipmentMilestonePlanCreatedVO planCreatedVO);

	void deleteShipmentMilestonePlans(ShipmentMilestonePlanDeletedVO event);

	void saveShipmentMilestoneEvent(ShipmentMilestoneEventVO shipmentMilestoneEventVO);

	List<ShipmentActivityVO> getShipmentActivities(AwbRequestVO awbRequestVO);

	List<MilestoneMasterVO> findAllMilestones();

	boolean isNotificationMilestone(String milestoneCode);

	boolean isActivityViewMilestone(String milestoneCode);
	
}
