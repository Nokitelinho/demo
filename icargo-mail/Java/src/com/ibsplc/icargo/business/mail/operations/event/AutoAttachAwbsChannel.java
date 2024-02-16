package com.ibsplc.icargo.business.mail.operations.event;

import java.util.HashMap;

import com.ibsplc.icargo.framework.event.Channel;
import com.ibsplc.icargo.framework.event.exceptions.ChannelCreationException;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

@Module("mail")
@SubModule("operations")
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.event.aa.ContentIDChannel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8353	:	13-Aug-2019	:	Draft
 */

public class AutoAttachAwbsChannel extends Channel {
	private static final String COLL_CONTAINERDETAILSVO_PARM = "COLL_CONTAINERDETAILSVO_PARM";
	private static final String OPERATIONALFLIGHTVO_PARM = "OPERATIONALFLIGHTVO_PARM";
	private static final String AUTOATTACH_AWBDETAILS = "autoAttachAWBDetails";
	private static final String AUTOATTACH_AWBDETAILS_CHANNEL = "AUTOATTACHMAILAWBs_CHANNEL";
	private static final String MODE_OF_AUTO_ATTACH_AWB = "MODE_OF_AUTO_ATTACH_AWB";
	private static final String AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM = "AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM";
	private static final String JOB = "JOB";
	private static final String CREATE_AUTO_ATTACH_AWB_JOB_SCHEDULE = "createAutoAttachAWBJobSchedule";

	public AutoAttachAwbsChannel() throws ChannelCreationException {
		super(AUTOATTACH_AWBDETAILS_CHANNEL);
	
	}

	@Override
	/**
	  *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.Channel#send(com.ibsplc.icargo.framework.event.vo.EventVO)
	 *	Added by 			:	A-8353 on 13-Aug-2018
	 * 	Used for 			:	Send the business vo to perform mailbag attachment to flight
	 *	Parameters			:	@param eventVO
	 *	Parameters			:	@throws Throwable
	 */
	public void send(EventVO eventVO) throws Throwable {
		if(eventVO.getPayload()!=null){
			HashMap<String, Object> context = (HashMap<String, Object>) eventVO.getPayload();
			if (context != null) {
				if(!JOB.equals(context.get(MODE_OF_AUTO_ATTACH_AWB))) {
			despatchRequest(AUTOATTACH_AWBDETAILS, context.get(COLL_CONTAINERDETAILSVO_PARM),context.get(OPERATIONALFLIGHTVO_PARM));
				}else{
					despatchRequest(CREATE_AUTO_ATTACH_AWB_JOB_SCHEDULE,context.get(AUTO_ATTACH_AWB_JOBSCHEDULEVO_PARM));
				}
			}
		}
		
	}
	

}
