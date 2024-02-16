package com.ibsplc.icargo.presentation.web.model.mail.operations.common;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;

public class MailCarditModelConverter {
	public static Map<String,Collection<OneTime>> constructOneTimeValues(Map<String,Collection<OneTimeVO>> oneTimeValues){
		HashMap<String,Collection<OneTime>> oneTimeValuesMap = new HashMap<String,Collection<OneTime>>();
		for(Map.Entry<String,Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()){
			ArrayList<OneTime> oneTimes= new ArrayList<OneTime>();
			for(OneTimeVO oneTimeVO : oneTimeValue.getValue()){
				OneTime oneTime =  new OneTime();
				oneTime.setFieldType(oneTimeVO.getFieldType());
				oneTime.setFieldValue(oneTimeVO.getFieldValue());
				oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
				oneTimes.add(oneTime);
			}
			oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
		}
		return oneTimeValuesMap;
	}
	
	public static List<Mailbag> constructMailbag(Collection<MailbagVO> mailbags)
	  {
		List<Mailbag> localArrayList = new ArrayList<Mailbag>();
	    Mailbag mailbag= null;
	    if ((mailbags != null) && (mailbags.size() > 0))
	    {
	      Iterator localIterator = mailbags.iterator();
	      while (localIterator.hasNext()) {
	     // {
	    	 mailbag= new Mailbag();
	    	 MailbagVO mailbagvo= (MailbagVO)localIterator.next();
	    	 mailbag.setMailbagId(mailbagvo.getMailbagId());
	    	 mailbag.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
	    	 mailbag.setMailCategoryCode(mailbagvo.getMailCategoryCode());
	    	 mailbag.setOoe(mailbagvo.getOoe());
	    	 mailbag.setDoe(mailbagvo.getDoe());
	    	 mailbag.setMailSubclass(mailbagvo.getMailSubclass());
	    	 mailbag.setYear(mailbagvo.getYear());
	    	 mailbag.setDespatchSerialNumber(mailbagvo.getDespatchSerialNumber());
	    	 mailbag.setReceptacleSerialNumber(mailbagvo.getReceptacleSerialNumber());
	    	 mailbag.setHighestNumberedReceptacle(mailbagvo.getHighestNumberedReceptacle());
	    	 mailbag.setRegisteredOrInsuredIndicator(mailbagvo.getRegisteredOrInsuredIndicator());
	    	 mailbag.setFlightNumber(mailbagvo.getFlightNumber());
	    	 mailbag.setFlightDate(mailbagvo.getFlightDate()!=null?mailbagvo.getFlightDate().toDisplayDateOnlyFormat().toString():null);
	    	 mailbag.setWeight(mailbagvo.getWeight());
	    	 mailbag.setConsignmentNumber(mailbagvo.getConsignmentNumber());
	    	 mailbag.setConsignmentDate(mailbagvo.getConsignmentDate()!=null?mailbagvo.getConsignmentDate().toDisplayDateOnlyFormat().toString():null);
	    	 //mailbag.setReqDeliveryTime(mailbagvo.getReqDeliveryTime());
	    	 mailbag.setUldNumber(mailbagvo.getUldNumber());
	    	 mailbag.setAccepted(mailbagvo.getAccepted());
	    	 mailbag.setShipmentPrefix(mailbagvo.getShipmentPrefix());
	    	 mailbag.setMasterDocumentNumber(mailbagvo.getDocumentNumber());
	    	 mailbag.setCarrierCode(mailbagvo.getCarrierCode());
	    	 //Added by A-8164 starts
	    	 mailbag.setDocumentOwnerIdr(mailbagvo.getDocumentOwnerIdr());
	    	 mailbag.setDuplicateNumber(mailbagvo.getDuplicateNumber());
	    	 mailbag.setMailClass(mailbagvo.getMailClass());
	    	 mailbag.setPaCode(mailbagvo.getPaCode());
	    	 mailbag.setVolume(mailbagvo.getVolume());  
	    	//Added by A-8164 ends 
	      //  localUldManifestVO = constructUldManifestVO(localULDManifest, paramLogonAttributes);
	         localArrayList.add(mailbag);
	      }
	    }
	    return localArrayList;
	  }
	
	public static List<Mailbag> constructCarditGroupedMailbag(Collection<MailbagVO> mailbags)
	  {
		List<Mailbag> localArrayList = new ArrayList<Mailbag>();
	    Mailbag mailbag= null;
	    if ((mailbags != null) && (mailbags.size() > 0))
	    {
	      Iterator localIterator = mailbags.iterator();
	      while (localIterator.hasNext()) {
	     // {
	    	 mailbag= new Mailbag();
	    	 MailbagVO mailbagvo= (MailbagVO)localIterator.next();
	    	 mailbag.setDestinationCode(mailbagvo.getDestCityDesc());
	    	 mailbag.setCount(mailbagvo.getCount());
	    	 mailbag.setWeight(mailbagvo.getWeight());
	    	 mailbag.setAcceptedBagCount(mailbagvo.getAcceptedBags());
	      //  localUldManifestVO = constructUldManifestVO(localULDManifest, paramLogonAttributes);
	         localArrayList.add(mailbag);
	      }
	    }
	    return localArrayList;
	  }
	
	public static Mailbag constructCarditSummary(MailbagVO mailbagvo) {
		Mailbag mailbag= new Mailbag();
		mailbag.setCount(mailbagvo.getCount());
   	    mailbag.setWeight(mailbagvo.getWeight());
		return mailbag;
		
	}
	
	
	

}
