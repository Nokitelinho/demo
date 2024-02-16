package com.ibsplc.icargo.business.mail.operations.event.aa;

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
 *		0.1		:	A-6245	:	16-Sep-2018	:	Draft
 */
public class ContentIDChannel extends Channel {

	private static final String CONTENTID_CHANNEL = "CONTENTID_CHANNEL";
	private static final String CONTAINER_PARAM = "CONTAINER_PARAM";
	private static final String FLIGHT_PARAM = "FLIGHT_PARAM";
	private static final String CALCULATE_ULD_CONTENT_ID = "calculateULDContentId";

	public ContentIDChannel() throws ChannelCreationException {
		super(CONTENTID_CHANNEL);
	}

	@Override
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.event.Channel#send(com.ibsplc.icargo.framework.event.vo.EventVO)
	 *	Added by 			:	A-6245 on 14-Sep-2018
	 * 	Used for 			:	Send the business vo to perform content ID calculation
	 *	Parameters			:	@param eventVO
	 *	Parameters			:	@throws Throwable
	 */
	public void send(EventVO eventVO) throws Throwable {
		HashMap<String, Object> context = (HashMap<String, Object>) eventVO.getPayload();
		if (context != null)
			despatchRequest(CALCULATE_ULD_CONTENT_ID, context.get(CONTAINER_PARAM), context.get(FLIGHT_PARAM));
	}

}
