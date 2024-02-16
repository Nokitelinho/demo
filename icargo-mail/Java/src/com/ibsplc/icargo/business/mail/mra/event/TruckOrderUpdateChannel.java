/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.event.TruckOrderUpdateChannel.java
 *
 *	Created by	:	A-8061
 *	Created on	:	Jul 17, 2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.event;



import com.ibsplc.icargo.business.addons.trucking.vo.TruckOrderVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.TruckOrderMailVO;
import com.ibsplc.icargo.framework.event.Channel;
import com.ibsplc.icargo.framework.event.EventMapper;
import com.ibsplc.icargo.framework.event.exceptions.ChannelCreationException;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.event.TruckOrderUpdateChannel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	Jul 17, 2018	:	Draft
 */

@Module("mail")
@SubModule("mra")
public class TruckOrderUpdateChannel extends Channel{

protected Log log = LogFactory.getLogger("MRA:DEFAULTS");
	
	public static final String CHANNEL_NAME = "TruckOrderUpdateChannel";
	public static final String TASKVO_PARAMETER = "param_0";
	public static final String TRK_STATUS_FINAL = "F";
	public static final String TRK_STATUS_REOPEN = "R";
	public static final String TRK_STATUS_CANCEL = "C";
	

	public TruckOrderUpdateChannel(String channel)
			throws ChannelCreationException {
		super(channel);		
	}

	/**
	 * Instantiates a new action workflow channel.
	 *
	 * @throws ChannelCreationException the channel creation exception
	 */
	public TruckOrderUpdateChannel() throws ChannelCreationException{
		this(CHANNEL_NAME);
	}
	
	
	@Override
	protected EventMapper getEventMapper() {	
		return new TruckOrderUpdateMapper();
	}

	@Override
	public void send(EventVO eventVO) throws Throwable {
	
		if(eventVO.getPayload()!=null){
			log.entering(CHANNEL_NAME, "send");
			Object[] args = (Object[]) eventVO.getPayload();
			if(args != null && args.length>0) {
				for(Object obj:args){
					TruckOrderVO truckOrderVO = (TruckOrderVO)obj;
			
					if (TRK_STATUS_FINAL.equals(truckOrderVO.getTruckOrderStatus())
							|| TRK_STATUS_REOPEN.equals(truckOrderVO.getTruckOrderStatus())
							|| TRK_STATUS_CANCEL.equals(truckOrderVO.getTruckOrderStatus())) {
						TruckOrderMailVO truckOrderMailVO = TruckOrderUpdateMapper
								.convertToTruckOrderMailVO(truckOrderVO);
						if (truckOrderMailVO != null) {
							try {
								despatchRequest("updateTruckCost", truckOrderMailVO);

							} catch (Exception e) {
								log.log(Log.SEVERE, "MRA.TruckOrderUpdateChannel method send()", e);
							}
						}
					}
				}
						
			}
		}		
	}	
	
	
}
