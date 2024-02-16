package com.ibsplc.icargo.business.master.defaults;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.master.defaults.orchestration.MasterTypeOrchestrator;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedAgentProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.master.defaults.proxy.TariffOthersProxy;
import com.ibsplc.icargo.business.shared.agent.vo.AgentFilterVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportFilterVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.business.shared.scc.SCCBusinessException;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovFilterVO;
import com.ibsplc.icargo.business.tariff.others.vo.ChargeHeadLovVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;


/**
 * @author a-4801
 */
@Module("master")
@SubModule("defaults")
public class MasterController {
	
	
	public List<String> findCodes(String masterType) throws SystemException{
		
		List<AirportVO> mastersVos = null;
		List<String> mastersList = new ArrayList<String>();
		Collection<ChargeHeadLovVO> chargeVOs=null;
		List<AgentVO> agentVOs = null;
		Collection<CustomerVO> customerVOs=null;
		if("AIRPORT".equalsIgnoreCase(masterType)){
			AirportFilterVO filterVO=new AirportFilterVO();
			mastersVos=new SharedAreaProxy().findAirports(filterVO);
		}else if("OTHERCHARGE".equalsIgnoreCase(masterType)){
			ChargeHeadLovFilterVO chargeHeadLovFilterVO= new ChargeHeadLovFilterVO();
			try {
			chargeVOs=new  TariffOthersProxy().findAllChargeHeads(chargeHeadLovFilterVO);
			} catch (ProxyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if("AGENT".equalsIgnoreCase(masterType)){
			AgentFilterVO agentFilterVO= new AgentFilterVO();
			agentVOs=new SharedAgentProxy().findAgentMasters(agentFilterVO);
		}else if("CUSTOMER".equalsIgnoreCase(masterType)){
			CustomerFilterVO filterVO=new CustomerFilterVO();
			customerVOs=new SharedCustomerProxy().findCustomerMasters(filterVO);
		}
		if(mastersVos!=null){
			for(AirportVO airportVo:mastersVos){
				mastersList.add(airportVo.getAirportCode());
			}
		}
		if(chargeVOs!=null){
			for(ChargeHeadLovVO chargeHeadLovVO:chargeVOs){
				mastersList.add(chargeHeadLovVO.getChargeHeadCode());
			}
		}
		if(agentVOs!=null){
			for(AgentVO agentVO:agentVOs){
				mastersList.add(agentVO.getAgentCode());
			}
		}
		if(customerVOs!=null){
			for(CustomerVO customerVO:customerVOs){
				mastersList.add(customerVO.getCustomerCode());
			}
		}
		
		return mastersList;
	}
	
public List<SuggestResponseVO> findCodes(SuggestRequestVO requestVO) throws SystemException{
		
		try {
		return new MasterTypeOrchestrator().despatch(requestVO);
		} catch (SCCBusinessException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		} catch (ProxyException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		} catch (RemoteException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		} catch (ServiceNotAccessibleException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		}
	}
}
