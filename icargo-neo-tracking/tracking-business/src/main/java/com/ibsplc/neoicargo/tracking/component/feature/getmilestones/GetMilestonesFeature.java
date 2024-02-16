package com.ibsplc.neoicargo.tracking.component.feature.getmilestones;

import java.util.List;

import org.springframework.stereotype.Component;


import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;
import com.ibsplc.neoicargo.tracking.vo.MilestoneMasterVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetMilestonesFeature {

	private final TrackingDAO trackingDAO;
	private final TrackingEntityMapper trackingEntityMapper;

    
    private final ContextUtil contextUtil;

	public List<MilestoneMasterVO> getMilestones() {
		log.info("Entering getMilestones");
		
		return trackingEntityMapper.constructMilestoneVOs(trackingDAO.findAllMilestones());

	}
	public boolean isNotificationMilestone(String milestoneCode) {
		log.info("Entering isNotificationMilestone");
		return getMilestones().stream().filter(MilestoneMasterVO::isEmailNotificationFlag)
				.filter(milestone -> milestoneCode.equals(milestone.getMilestoneCode())).findFirst().isPresent();
	}
	public boolean isActivityViewMilestone(String milestoneCode) {
		log.info("Entering isActivityViewMilestone");
		return getMilestones().stream().filter(MilestoneMasterVO::isActivityViewFlag)
				.filter(milestone -> milestoneCode.equals(milestone.getMilestoneCode())).findFirst().isPresent();
	}
}
