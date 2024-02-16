
package com.ibsplc.icargo.business.mail.mra.wsproxy.mapper;

//import java.util.Collection;

//import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.framework.services.jaxws.proxy.mapper.TypeMapper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
//import com.ibsplc.xibase.util.log.Log;
//import com.ibsplc.xibase.util.log.factory.LogFactory;
//import com.ke.xsd.mra.revenueinterface.IfMail;
//import com.ke.xsd.mra.revenueinterface.IfMailType;
//import com.ke.xsd.mra.revenueinterface.Response;


public class MailFlightRevenueMapper  implements TypeMapper {

	@Override
	public Object[] mapParameters(Object... parameters) throws SystemException {
		
	/*	Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs = (Collection<FlightRevenueInterfaceVO>)parameters[0];
		IfMailType ifMailType = new IfMailType();
		IfMail ifMail =null;

		for(FlightRevenueInterfaceVO flightRevenueInterfaceVO : flightRevenueInterfaceVOs){
			 ifMail = new IfMail();
			 
			 if(flightRevenueInterfaceVO!=null){
						
					ifMail.setAcctDt(flightRevenueInterfaceVO.getAccountDate());
					ifMail.setMailN(flightRevenueInterfaceVO.getMailNumber());
					ifMail.setRgnC(flightRevenueInterfaceVO.getRegionCode());
					ifMail.setBrnC(flightRevenueInterfaceVO.getBranchCode());
					ifMail.setMailC(flightRevenueInterfaceVO.getMailCategory());
					ifMail.setMailTypeX(flightRevenueInterfaceVO.getSubClassGroup());
					ifMail.setSttlC(flightRevenueInterfaceVO.getSettlement());
					ifMail.setOrgC(flightRevenueInterfaceVO.getMailOrigin());
					ifMail.setDstC(flightRevenueInterfaceVO.getMailDestination());
					ifMail.setAdjC(flightRevenueInterfaceVO.getAdjustCode());
					ifMail.setMailW(flightRevenueInterfaceVO.getMailWeight());
					ifMail.setRevCyC(flightRevenueInterfaceVO.getCurrency());
					ifMail.setLinC(flightRevenueInterfaceVO.getFlightLineCode());
					ifMail.setHlN(flightRevenueInterfaceVO.gethLNumber());
					ifMail.setPtnM(flightRevenueInterfaceVO.getRateAmount());
					ifMail.setPtnFmM(flightRevenueInterfaceVO.getRateAmountInUSD());
					ifMail.setCtrlN(flightRevenueInterfaceVO.getrSN());
					ifMail.setBillBrnC(flightRevenueInterfaceVO.getBillingBranch());
					ifMail.setCarrTypeC(flightRevenueInterfaceVO.getCarrTypeC());
					ifMail.setMfstNoFlt(flightRevenueInterfaceVO.getFlightNumber());
					ifMail.setMfstDtFlt(flightRevenueInterfaceVO.getFlightDate());
					ifMail.setMfstCdDprt(flightRevenueInterfaceVO.getFlightOrigin());
					ifMail.setMfstCdArry(flightRevenueInterfaceVO.getFlightDestination());
					ifMail.setAwbFltD(flightRevenueInterfaceVO.getFirstFlightDate());
					ifMail.setErpSeq(flightRevenueInterfaceVO.getSerialNumber());
					ifMail.setIfDErp(flightRevenueInterfaceVO.getInterfaceDate());
					
			 }
		ifMailType.getIfMail().add(ifMail);
		
		}
		writeLog(ifMailType);
		return new Object[]{ifMailType};*/
		return null;
		
	}

	@Override
	public <T> T mapResult(T arg0) throws SystemException {

		/*Response response = (Response) arg0; 
		return (T)response;*/
		return null;
		
	}
	
	/**
	 * This method is added to log the WS request because ws log was not coming properly in DC server logs
	 * @param ifMailType
	 */
	/*private void writeLog(IfMailType ifMailType) {
		if(ifMailType != null && ifMailType.getIfMail() != null && ifMailType.getIfMail().size() > 0) {
			Log log = LogFactory.getLogger("MAIL FLTREV INTERFACE");
			int logLevel = Log.SEVERE; // change if required
			log.log(logLevel, "<IfMailType>");
			for (IfMail ifmail : ifMailType.getIfMail()) {
				log.log(logLevel, "        <IfMail>");
				log.log(logLevel, "                <AcctDt>", ifmail.getAcctDt(), "</AcctDt>");
				log.log(logLevel, "                <MailN>", ifmail.getMailN(), "</MailN>");
				log.log(logLevel, "                <RgnC>", ifmail.getRgnC(), "</RgnC>");
				log.log(logLevel, "                <BrnC>", ifmail.getBrnC(), "</BrnC>");
				log.log(logLevel, "                <MailC>", ifmail.getMailC(), "</MailC>");
				log.log(logLevel, "                <MailTypeX>", ifmail.getMailTypeX(), "</MailTypeX>");
				log.log(logLevel, "                <SttlC>", ifmail.getSttlC(), "</SttlC>");
				log.log(logLevel, "                <OrgC>", ifmail.getOrgC(), "</OrgC>");
				log.log(logLevel, "                <DstC>", ifmail.getDstC(), "</DstC>");
				log.log(logLevel, "                <AdjC>", ifmail.getAdjC(), "</AdjC>");
				log.log(logLevel, "                <MailW>", ifmail.getMailW(), "</MailW>");
				log.log(logLevel, "                <RevCyC>", ifmail.getRevCyC(), "</RevCyC>");
				log.log(logLevel, "                <LinC>", ifmail.getLinC(), "</LinC>");
				log.log(logLevel, "                <HlN>", ifmail.getHlN(), "</HlN>");
				log.log(logLevel, "                <PtnM>", ifmail.getPtnM(), "</PtnM>");
				log.log(logLevel, "                <PtnFmM>", ifmail.getPtnFmM(), "</PtnFmM>");
				log.log(logLevel, "                <CtrlN>", ifmail.getCtrlN(), "</CtrlN>");
				log.log(logLevel, "                <BillBrnC>", ifmail.getBillBrnC(), "</BillBrnC>");
				log.log(logLevel, "                <CarrTypeC>", ifmail.getCarrTypeC(), "</CarrTypeC>");
				log.log(logLevel, "                <MfstNoFlt>", ifmail.getMfstNoFlt(), "</MfstNoFlt>");
				log.log(logLevel, "                <MfstDtFlt>", ifmail.getMfstDtFlt(), "</MfstDtFlt>");
				log.log(logLevel, "                <MfstCdDprt>", ifmail.getMfstCdDprt(), "</MfstCdDprt>");
				log.log(logLevel, "                <MfstCdArry>", ifmail.getMfstCdArry(), "</MfstCdArry>");
				log.log(logLevel, "                <AwbFltD>", ifmail.getAwbFltD(), "</AwbFltD>");
				log.log(logLevel, "                <ErpSeq>", ifmail.getErpSeq(), "</ErpSeq>");
				log.log(logLevel, "                <IfDErp>", ifmail.getIfDErp(), "</IfDErp>");
				log.log(logLevel, "        </IfMail>");
			}
			log.log(logLevel, "</IfMailType>");
		}
	}*/

}
