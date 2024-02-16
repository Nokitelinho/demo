package com.ibsplc.icargo.business.master.defaults.proxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageMIPErrorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * Created by A-8903 on 18-11-2019.
 */
@Module("msgbroker")
@SubModule("message")
public class MsgBrokerMessageProxy extends ProductProxy {


    public Page<MessageMIPErrorVO> findMultipleMIPErrorCodeForLOV(MessageMIPErrorVO errorVO,int displayPage) throws SystemException, ProxyException {
        return despatchRequest("findMultipleMIPErrorCodeForLOV",errorVO,1);
    }


}
