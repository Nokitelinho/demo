package com.ibsplc.icargo.business.xaddons.qf.mail.operations.event;
import java.util.Objects;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.event.Channel;
import com.ibsplc.icargo.framework.event.exceptions.ChannelCreationException;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("mail")
@SubModule("operations")
public class UpdateActualWeightChannel extends Channel {
	
	 public UpdateActualWeightChannel()
	            throws ChannelCreationException
	    {
	        this("UpdateActualWeightChannel");
	    }
	    public UpdateActualWeightChannel(String channel) throws ChannelCreationException{
	        super(channel);
	    }
	@Override
	public void send(EventVO eventVO) throws Throwable {
		ContainerVO containerVO = (ContainerVO) eventVO.getPayload();
		if (Objects.nonNull(containerVO)) {
			despatchUpdateActualWeightRequest(containerVO);
		}
	}

	public void despatchUpdateActualWeightRequest(ContainerVO containerVO)
			throws ProxyException, SystemException {
		despatchRequest("updateActualWeightForMailContainer", containerVO); 
		
	}
}
