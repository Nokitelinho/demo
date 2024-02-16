/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.persistors.SendResditMessageDetailsPersistor.java
 *
 *	Created by	:	A-10647
 *	Created on	:	21-Mar-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.persistors;



import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ibsplc.icargo.business.mail.operations.MailResditAddress;
import com.ibsplc.icargo.business.mail.operations.ResditController;
import com.ibsplc.icargo.business.mail.operations.feature.savesendresditmessagedetails.SaveSendResditMessageDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Java file :
 * com.ibsplc.icargo.business.mail.operations.feature.saveSendResditMessageDetails.persistors.SendResditMessageDetailsPersistor.java
 *	Version		:	Name	:	Date			:	Updation
 * --------------------------------------------------- 0.1 : A-10647 :
 * 21-Mar-2022 : Draft
 */
public class SendResditMessageDetailsPersistor {
	private static final Log LOGGER = LogFactory
			.getLogger(SaveSendResditMessageDetailsFeatureConstants.MODULE_SUBMODULE);

	/**
	 * Method : SecurityDetailPersistor.persist Added by : A-10647 on 21-Mar-2022
	 * Used for : Parameters : @param consignmentScreeningVO Return type : void
	 * 
	 * @throws SystemException 
	 * @throws FinderException 
	 */

	public void persist(MailResditMessageDetailsVO mailResditMessageDetailsVO) throws SystemException {
		
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		List<String> messageAddressSequenceNumbers = new ArrayList<>();
		Collection<MailResditAddressVO> mailResditAddressVOs = mailResditMessageDetailsVO.getMailResditAddressVO();
		Collection<MailbagVO> selectedMailbags = mailResditMessageDetailsVO.getMailbagVO();
		List<String> selectedResdits=mailResditMessageDetailsVO.getSelectedResdits();
		for(MailResditAddressVO mailResditAddressVO: mailResditAddressVOs) {
			MailResditAddress mailResditAddress = new MailResditAddress(mailResditAddressVO);
		try {

			PersistenceController.getEntityManager().persist(mailResditAddress);
		} catch (CreateException createException) {
			createException.getErrorCode();
	            throw new SystemException(createException.getMessage(), createException);
		}
			messageAddressSequenceNumbers
					.add(Long.toString(mailResditAddress.getMailResditAddressPK().getMessageAddressSequenceNumber()));
	
		}	
		
		String messageAddressSequenceNumber = String.join(",", messageAddressSequenceNumbers);
		for (MailbagVO mailbagVO : selectedMailbags) {
			
			selectedResdits= stampingInMALRDTBasedOnSelectedResdits(selectedResdits, messageAddressSequenceNumber,
					mailbagVO);
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}
	private List<String> stampingInMALRDTBasedOnSelectedResdits(List<String> selectedResdits,
			String messageAddressSequenceNumber, MailbagVO mailbagVO) throws SystemException {
		if ((selectedResdits == null || selectedResdits.isEmpty())
				&& (mailbagVO.getMailbagHistories() != null && !mailbagVO.getMailbagHistories().isEmpty())) {
			Collection<MailbagHistoryVO> mailbagHistories = mailbagVO.getMailbagHistories();
			selectedResdits = new ArrayList<>();
			
			for (MailbagHistoryVO mailbagHistory : mailbagHistories) {
				if (StringUtils.equals(mailbagHistory.getAdditionalInfo(), "true")
						&& mailbagHistory.getEventCode() != null) {
					String eventCode = mailbagHistory.getEventCode();
					new ResditController().flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber,
							eventCode, mailbagVO);
				}
			}

		} else {

			for (String selectedResdit : selectedResdits) {

				new ResditController().flagResditsForSendResditsFromCarditEnquiry(messageAddressSequenceNumber,
						selectedResdit, mailbagVO);

			}
		}
		return selectedResdits;
	}
	
	
	
}
