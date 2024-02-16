package  com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("mail")
@SubModule("operations")
public class MailOperationsProxy extends ProductProxy{

	private static final String FIND_MAIL_SEQUENCE_NUMBER = "findMailBagSequenceNumberFromMailIdr";

	public void saveAndProcessMailBags(ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		 try {
			despatchRequest("saveAndProcessMailBags", scannedMailDetailsVO);
		} catch (ProxyException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, e);
		}
		
	}

	public long findMailSequenceNumber(String mailbagId, String companyCode) throws ProxyException, SystemException {
		return despatchRequest(FIND_MAIL_SEQUENCE_NUMBER, mailbagId, companyCode);
	}

	public Collection<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException, ProxyException {
		return despatchRequest("findCarditMails", carditEnquiryFilterVO, pageNumber);
	}

	public void attachAWBForMail(Collection<MailBookingDetailVO> mailBkgDetailVOs, Collection<MailbagVO> mailbagVOs)
			throws SystemException, ProxyException {
		despatchRequest("attachAWBForMail", mailBkgDetailVOs, mailbagVOs);
	}

	public OfficeOfExchangeVO validateOfficeOfExchange(String companyCode, String officeOfExchange)
			throws SystemException, ProxyException {
		return despatchRequest("validateOfficeOfExchange", companyCode, officeOfExchange);
	}

	public String findNearestAirportOfCity(String companyCode, String exchangeCode)
			throws ProxyException, SystemException {
		return despatchRequest("findNearestAirportOfCity", companyCode, exchangeCode);
	}

	public Collection<FlightValidationVO> validateFlight(FlightFilterVO flightFilterVO)
			throws ProxyException, SystemException {
		return despatchRequest("validateFlight", flightFilterVO);
	}
	
}
