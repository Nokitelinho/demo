
package  com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.FlightOperationsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil M N	:	23-Sep-2021	:	Draft
 */
@Module("flight")
@SubModule("operation")
public class FlightOperationsProxy extends ProductProxy {


	/**
	 * 
	 * 	Method		:	FlightOperationsProxy.validateFlightForAirport
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param flightFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<FlightValidationVO>
	 */
	public Collection<FlightValidationVO> validateFlightForAirport(
			FlightFilterVO flightFilterVO) throws SystemException {

		try {
			return despatchRequest("validateFlightForAirport", flightFilterVO);
		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, proxyException);
		}
	}
	
/**
 * 
 * 	Method		:	FlightOperationsProxy.findFlightSegments
 *	Added by 	:	Ashil M N on 23-Sep-2021
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param airlineId
 *	Parameters	:	@param flightNumber
 *	Parameters	:	@param flightSequenceNumber
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	Collection<FlightSegmentSummaryVO>
 */
	public Collection<FlightSegmentSummaryVO> findFlightSegments(
			String companyCode, int airlineId, String flightNumber,
			long flightSequenceNumber) throws SystemException {
		try {
			if(flightNumber!=null && flightNumber.trim().length()>0){
			return despatchRequest("findFlightSegments", companyCode,
					airlineId, flightNumber, flightSequenceNumber);
			}else{
				return new ArrayList();
			}
		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, proxyException);
		}
		}
}
