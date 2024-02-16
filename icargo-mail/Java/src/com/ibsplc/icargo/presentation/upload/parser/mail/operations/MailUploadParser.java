package com.ibsplc.icargo.presentation.upload.parser.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Parser class to parse and process the HHT scanned data
 * @author A-6385
 *
 */
public class MailUploadParser {

	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");

	private static final String SEPARATOR = "~";
	private static final String DATA_DELIMITER = ";";
	private static final String PROCESSPOINT_IN = "IN";
	private static final String PROCESSPOINT_OUT = "OUT";

	/**
	 * Method to construct MailUploadVo from the HHT Scanned Data
	 * @param data
	 * @return
	 */
	public MailUploadVO splitData(String data) {
		String[] splitData = null;

		MailUploadVO mailUploadVO = null;
		splitData = data.split(DATA_DELIMITER);
		log.log(Log.INFO, "Splited data");
		log.log(Log.INFO, "Splited data length:"+splitData.length);

		if(splitData.length >= 43){

		mailUploadVO = new MailUploadVO();

		mailUploadVO.setScanType(splitData[0]);
		mailUploadVO.setCompanyCode(splitData[1]);
		mailUploadVO.setCarrierCode(splitData[2]);
		mailUploadVO.setFlightNumber(splitData[3]);
		if (splitData[4] != null && splitData[4].trim().length() > 0) {
		LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		flightDate.setDate(splitData[4]);
		mailUploadVO.setFlightDate(flightDate);
		}
		if (splitData[5] != null && splitData[5].trim().length() > 0) {
		mailUploadVO.setContainerPOU(splitData[5]);
		} else {
			if (splitData[13].trim().length() == 29) {
			mailUploadVO.setContainerPOU(splitData[13].substring(8, 11));
		}
		}
		mailUploadVO.setContainerNumber(splitData[6]);
		mailUploadVO.setContainerType(splitData[7]);
		mailUploadVO.setDestination(splitData[9]);
		if (splitData[13].trim().length() == 29) {
		mailUploadVO.setContainerPol(splitData[13].substring(2, 5));
		}
		mailUploadVO.setRemarks(splitData[12]);
		mailUploadVO.setMailTag(splitData[13]);
		mailUploadVO.setDateTime(splitData[14]);
		mailUploadVO.setDamageCode(splitData[15]);
		mailUploadVO.setDamageRemarks(splitData[16]);
		mailUploadVO.setOffloadReason(splitData[17]);
		if (splitData[18] != null && splitData[18].trim().length() > 0) {
		mailUploadVO.setReturnCode(splitData[18]);
		}
		if (mailUploadVO.getScanType().equals(
				MailConstantsVO.MAIL_STATUS_RETURNED)
				&& "N".equals(splitData[18])) {

			mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_DAMAGED);

		}

		if (mailUploadVO.getScanType().equals(MailConstantsVO.MAIL_STATUS_DELIVERED) ||
				mailUploadVO.getScanType().equals(MailConstantsVO.MAIL_STATUS_ARRIVED)){
			mailUploadVO.setProcessPoint(PROCESSPOINT_IN);
		} else {
			mailUploadVO.setProcessPoint(PROCESSPOINT_OUT);
		}

		mailUploadVO.setToContainer(splitData[19]);
		mailUploadVO.setToCarrierCode(splitData[20]);
		mailUploadVO.setToFlightNumber(splitData[21]);
		if (splitData[22] != null && splitData[22].trim().length() > 0) {
		LocalDate toFlightDate = new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false);
		toFlightDate.setDate(splitData[22]);

		mailUploadVO.setToFlightDate(toFlightDate);
		}
		if (splitData[5] != null && splitData[5].trim().length() > 0) {
		mailUploadVO.setToPOU(splitData[5]);
		mailUploadVO.setToDestination(splitData[5]);
		} else if (MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(mailUploadVO
				.getScanType())) {
			mailUploadVO.setToPOU(splitData[23]);
			mailUploadVO.setToDestination(splitData[24]);
		} else {
			if (splitData[13].trim().length() == 29) {
			mailUploadVO.setToPOU(splitData[13].substring(8, 11));
			mailUploadVO.setToDestination(splitData[13].substring(8, 11));
		}
		}
		if (mailUploadVO.getMailTag().trim().length() == 29) {
		mailUploadVO.setOrginOE(mailUploadVO.getMailTag().substring(0, 6));
			mailUploadVO.setDestinationOE(mailUploadVO.getMailTag().substring(
					6, 12));
		mailUploadVO.setCategory(splitData[13].substring(12, 13));
		mailUploadVO.setSubClass(splitData[13].substring(13, 15));

			mailUploadVO.setYear(Integer.parseInt(splitData[13].substring(15,
					16)));
		}

		if (splitData[30] != null && splitData[30].trim().length() > 0) {
		mailUploadVO.setDsn(splitData[30]);
		}
		mailUploadVO.setConsignmentDocumentNumber(splitData[31]);
		mailUploadVO.setPaCode(splitData[32]);
		if (splitData[33] != null && splitData[33].trim().length() > 0) {
		mailUploadVO.setTotalBag(Integer.parseInt(splitData[33]));
		}
		if (splitData[34] != null && splitData[34].trim().length() > 0) {
		//mailUploadVO.setTotalWeight(Double.parseDouble(splitData[34]));
			mailUploadVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,
					Double.parseDouble(splitData[34])));//added by A-7371
		}
		if (splitData[35] != null && splitData[35].trim().length() > 0) {
		mailUploadVO.setBags(Integer.parseInt(splitData[35]));
		}

		if (splitData[36] != null && splitData[36].trim().length() > 0) {
		//mailUploadVO.setWeight(Double.parseDouble(splitData[36]));
			mailUploadVO.setWeight(new Measure(UnitConstants.MAIL_WGT,
					Double.parseDouble(splitData[36])));//added by A-7371
		}
		if (splitData[37] != null && splitData[37].trim().length() > 0) {
		mailUploadVO.setIntact(Boolean.parseBoolean(splitData[37]));
		}
		if (splitData[38] != null && splitData[38].trim().length() > 0) {
		mailUploadVO.setSerialNumber(Integer.parseInt(splitData[38]));
		}
		mailUploadVO.setCirCode(splitData[39]);
		if (splitData[40] != null && splitData[40].trim().length() > 0) {
			if ("Y".equals(splitData[40])
					|| MailConstantsVO.MAIL_STATUS_DELIVERED
							.equals(splitData[0])) {
		   mailUploadVO.setDeliverd(true);
			} else {
			mailUploadVO.setDeliverd(false);
		   }
		}
		// Added by A-6385 for ICRD-91729
		if (splitData[41] != null && splitData[41].trim().length() > 0) {
			mailUploadVO.setScanUser(splitData[41]);
		}

		// Setting Date	- Added for ICRD-93567
		if (splitData[14] != null && splitData[14].trim().length() > 0) {
			LocalDate scannedDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			scannedDate.setDate(splitData[14].substring(0, 11));
			scannedDate.setTime(splitData[14].substring(12, 20));
			mailUploadVO.setScannedDate(scannedDate);
		}
		//Added as part of Bug ICRD-94100 starts
		if (splitData[43].length()>2) {
			if(splitData[43].indexOf("*")>0){
				String transferCarrierCode=splitData[43].substring(0, splitData[43].indexOf("*"));
				mailUploadVO.setFromCarrierCode(transferCarrierCode);

		}else{
				mailUploadVO.setFromCarrierCode(splitData[43]);
			}
		}
		 else {
			mailUploadVO.setFromCarrierCode(splitData[43]);
		}
		if(splitData.length>45 && splitData[45] != null && splitData[45].trim().length() >= 3){
			mailUploadVO.setFromPol(splitData[45].substring(0,3));        
		}
		mailUploadVO.setMailSource("OFFLINEUPLOAD");
		}
		return mailUploadVO;
	}
	/**
	 * Method to group the mailbags based on Processpoint, Flightdata and Container
	 * @param mailBatchVOs
	 * @return
	 */
	public HashMap<String, Collection<MailUploadVO>> groupMailBags(Collection<MailUploadVO> mailBatchVOs){

		HashMap<String, Collection<MailUploadVO>> mailMap = new HashMap<String, Collection<MailUploadVO>>();

		if (mailBatchVOs != null && mailBatchVOs.size() > 0) {

			String processPoint = null;
			Collection<MailUploadVO> inMailBatchVOs = new ArrayList<MailUploadVO>();
			Collection<MailUploadVO> outMailBatchVOs = new ArrayList<MailUploadVO>();

			//sort the mailbag vos to IN and OUT
			for (MailUploadVO mailVO : mailBatchVOs) {

				processPoint = mailVO.getProcessPoint();
				if(PROCESSPOINT_IN.equals(processPoint)){
					inMailBatchVOs.add(mailVO);
				} else {
					outMailBatchVOs.add(mailVO);
				}
			}

			String inMailKey = null;
			String outMailKey = null;
			String container = null;
			processPoint = null;

			//Sorting IN mailbags and adding them to HashMap
			for (MailUploadVO inMailVO : inMailBatchVOs) {
				processPoint = inMailVO.getProcessPoint();
				if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(inMailVO.getScanType()) ||
						MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(inMailVO.getScanType())){
					container = inMailVO.getToContainer();
				}else{
					container = inMailVO.getContainerNumber();
				}
				String strInFlightDate = "";
				LocalDate inFlightDate = inMailVO.getFlightDate();
				if(inFlightDate != null){
					strInFlightDate = inFlightDate.toDisplayDateOnlyFormat();
				}
				inMailKey = new StringBuilder(processPoint).append(SEPARATOR)
				.append(inMailVO.getCarrierCode()).append(SEPARATOR)
				.append(inMailVO.getFlightNumber()).append(SEPARATOR)
				.append(strInFlightDate).append(SEPARATOR)
				.append(container).toString();

				Collection<MailUploadVO> mailList = mailMap.get(inMailKey);
				if(mailMap.get(inMailKey) != null){
					mailList.add(inMailVO);
					mailMap.put(inMailKey, mailList);
				} else {
					mailList = new ArrayList<MailUploadVO>();
					mailList.add(inMailVO);
					mailMap.put(inMailKey, mailList);
				}
			}

			container = null;
			processPoint = null;
			//Sorting OUT mailbags and adding them to HashMap
			for (MailUploadVO outMailVO : outMailBatchVOs) {
				processPoint = outMailVO.getProcessPoint();
				if(MailConstantsVO.MAIL_STATUS_TRANSFERRED.equals(outMailVO.getScanType()) ||
						MailConstantsVO.MAIL_STATUS_REASSIGNMAIL.equals(outMailVO.getScanType())){
					container = outMailVO.getToContainer();
				}
				// Added by A-6385 for bug ICRD-93353 - For scenario to offload a container assigned to carrier
				else if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(outMailVO.getScanType())){
					if(outMailVO.getMailTag() != null && outMailVO.getMailTag().trim().length() < 29){
						container = outMailVO.getMailTag();
						outMailVO.setContainerNumber(container);
					}
				}else{
					container = outMailVO.getContainerNumber();
				}
				String strOutFlightDate = "";
				LocalDate outFlightDate = outMailVO.getFlightDate();
				if(outFlightDate != null){
					strOutFlightDate = outFlightDate.toDisplayDateOnlyFormat();
				}
				outMailKey = new StringBuilder(processPoint).append(SEPARATOR)
				.append(outMailVO.getCarrierCode()).append(SEPARATOR)
				.append(outMailVO.getFlightNumber()).append(SEPARATOR)
				.append(strOutFlightDate).append(SEPARATOR)
				.append(container).toString();

				Collection<MailUploadVO> mailList = mailMap.get(outMailKey);
				if(mailMap.get(outMailKey) != null){
					mailList.add(outMailVO);
					mailMap.put(outMailKey, mailList);
				} else {
					mailList = new ArrayList<MailUploadVO>();
					mailList.add(outMailVO);
					mailMap.put(outMailKey, mailList);
				}
			}
		} // mailBatchVOs null check
		return mailMap;
	}
	/**
	 * Method to construct ScannedMailDetailsVO collection for
	 * displaying on the screen
	 * @param mailMap
	 * @param status
	 * @return
	 */
	public ArrayList<ScannedMailDetailsVO> constructScannedMailCollection(
			HashMap<String, Collection<MailUploadVO>> mailMap, String status){

		ArrayList<ScannedMailDetailsVO> scannedMailDetailsVOs = new ArrayList<ScannedMailDetailsVO>();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		MailUploadVO uploadVO = null;
		if(mailMap != null){
			Collection<String> mailKeys = mailMap.keySet();
			for(String mailKey : mailKeys){
				log.log(Log.INFO, ":-----mailKey:"+mailKey);
				ArrayList<MailUploadVO> totalMailList = (ArrayList)mailMap.get(mailKey);
				if(totalMailList.size() > 0){

					// Added for ICRD-93567
					for(MailUploadVO mailUploadVO : totalMailList){
						mailUploadVO.setMailKeyforDisplay(mailKey);
					}

					uploadVO = totalMailList.get(0);
					scannedMailDetailsVO = new ScannedMailDetailsVO();
					scannedMailDetailsVO.setProcessPoint(uploadVO.getProcessPoint());
					scannedMailDetailsVO.setStatus(status);
					scannedMailDetailsVO.setCarrierCode(uploadVO.getCarrierCode());
					scannedMailDetailsVO.setFlightNumber(uploadVO.getFlightNumber());
					scannedMailDetailsVO.setFlightDate(uploadVO.getFlightDate());
					scannedMailDetailsVO.setContainerNumber(uploadVO.getContainerNumber());
					scannedMailDetailsVO.setScannedBags(getMailBagCount(totalMailList));
					scannedMailDetailsVO.setRemarks(mailKey);
					scannedMailDetailsVO.setHasErrors(false);
					scannedMailDetailsVO.setOfflineMailDetails(totalMailList);
					scannedMailDetailsVOs.add(scannedMailDetailsVO);
				}
			}// for
		}// if

		return scannedMailDetailsVOs;
	}
	/**
	 * Method to get the mailbag count
	 * Added for the scenario when a container is added from HHT without adding mailbags
	 * @param mailList
	 * @return
	 */
	public int getMailBagCount(ArrayList<MailUploadVO> mailList){

		int count = 0;
		if(mailList != null && mailList.size() > 0){
			for(MailUploadVO mailvo : mailList){
				// Modified by A-6385 for bug ICRD-93353 - For scenario to offload a container assigned to carrier
				if(mailvo.getMailTag() != null && mailvo.getMailTag().trim().length() == 29){
					count++;
				}
			}
		}
		log.log(Log.INFO, ":-----getMailBagCount:"+count);
		return count;
	}
}