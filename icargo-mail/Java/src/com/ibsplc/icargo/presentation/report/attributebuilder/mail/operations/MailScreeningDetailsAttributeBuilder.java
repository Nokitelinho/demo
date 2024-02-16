package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;

public class MailScreeningDetailsAttributeBuilder extends AttributeBuilderAdapter {

	@Override
	public Vector<String> getReportColumns() {

		Vector<String> columns = new Vector<>();
		columns.add("CONSTACOD");
		columns.add("CSGDOCNUM");
		columns.add("MALCTG");
		columns.add("CSGORG");
		columns.add("CSGDST");
		columns.add("POU");
		columns.add("SECSTACOD");
		columns.add("SCRAPLAUT");
		columns.add("SECSCRMTHCOD");
		columns.add("SECRSNCOD");
		columns.add("SECSTAPTY");
		columns.add("SECSTADAT");
		columns.add("SECSTATIM");
		columns.add("CONSTATUSCODEVAL");
		columns.add("ADLSECINF");

		return columns;
	}

	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		List<MailbagVO> mailbagVO = (ArrayList<MailbagVO>) data;
		Vector<Vector> reportData = new Vector<>();
		MailbagVO mailbagVo = mailbagVO.get(0);

		if (mailbagVo != null) {
			Vector<Object> row = new Vector<>();
			String constatusCode = null;
			String consignmentNumber = null;
			String origin = null;
			String destination = null;
			String pou = null;
			String securityStatusCode = null;
			String securityAuthSM = null;
			String screeningMethodSM = null;
			String screeningReasonSE = null;
			String secStaParty = null;
			String date = null;
			String time = null;
			String addSecurityInfo = null;
			String category = "MAIL";
			String constatusCodeVal = null;

			consignmentNumber = mailbagVo.getMailbagId();
			origin = mailbagVo.getOrigin();
			destination = mailbagVo.getDestination();
			pou = mailbagVo.getPou();

			securityStatusCode = mailbagVo.getSecurityStatusCode();

			List<ConsignmentScreeningVO> consignmentScreeningVoCS = mailbagVo.getConsignmentScreeningVO().stream()
					.filter(value -> value.getScreenDetailType().equals(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR))
					.collect(Collectors.toList());
			List<ConsignmentScreeningVO> consignmentScreeningVoSM = mailbagVo.getConsignmentScreeningVO().stream()
					.filter(value -> value.getScreenDetailType().equals(MailConstantsVO.SECURITY_REASON_CODE_SCREENING))
					.collect(Collectors.toList());

			List<ConsignmentScreeningVO> consignmentVos = consignmentScreeningVoCS.stream()
					.filter(value -> !(value.getAgentType().equals(MailConstantsVO.RA_ACCEPTING)||(value.getAgentType().equals(MailConstantsVO.REGULATED_CARRIER))))
					.collect(Collectors.toList()); 

			ConsignmentScreeningVO consignmentVo = new ConsignmentScreeningVO();
			ConsignmentScreeningVO screeningVo = new ConsignmentScreeningVO();
			if (!consignmentVos.isEmpty() && consignmentVos != null) {
				consignmentVo = consignmentVos.get(0);
			}

			if (!consignmentScreeningVoSM.isEmpty() && consignmentScreeningVoSM != null) {
				screeningVo = consignmentScreeningVoSM.get(0);
			}

			if ((consignmentVo.getAgentType() != null)
					&& (consignmentVo.getAgentType().equals(MailConstantsVO.ACCOUNT_CONSIGNOR)
					|| consignmentVo.getAgentType().equals(MailConstantsVO.KNOWN_CONSIGNOR)
					|| consignmentVo.getAgentType().equals(MailConstantsVO.RA_ISSUING))) {
				constatusCode = consignmentVo.getAgentID();
			}

			int count = 0;
			StringBuilder sb = new StringBuilder();
			for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreeningVoCS) {
				if ((count < 1) && consignmentScreeningVO.getAgentType() != null
						&& consignmentScreeningVO.getAgentType().equals(MailConstantsVO.REGULATED_CARRIER)) {
					if(("FLTCLS").equals(consignmentScreeningVO.getSource())){    
						constatusCodeVal=sb.append(consignmentScreeningVO.getIsoCountryCode() + "/").append("ACC3" + "/").append(consignmentScreeningVO.getAgentID() + ",").toString();
					}
					
					count++;
				}

			}
			for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreeningVoCS) {  
				if ((count < 2) && consignmentScreeningVO.getAgentType() != null
						&& consignmentScreeningVO.getAgentType().equals(MailConstantsVO.RA_ACCEPTING)) {
					constatusCodeVal = sb.append(consignmentScreeningVO.getAgentID() + ",").toString();
					count++;
				}

			}
			StringBuilder ref = new StringBuilder();
			for (ConsignmentScreeningVO consignmentScreeningVOSm : consignmentScreeningVoSM) {
				if (consignmentScreeningVOSm.getScreeningMethodCode() != null) {
					String methodVals = ref.append(consignmentScreeningVOSm.getScreeningMethodCode() + ",").toString();
					screeningMethodSM = methodVals.substring(0, methodVals.length() - 1);
				}
			}
			if(constatusCodeVal!=null){
			constatusCodeVal = constatusCodeVal.substring(0, constatusCodeVal.length() - 1);
			}
			securityAuthSM = screeningVo.getScreeningAuthority();
			secStaParty = screeningVo.getSecurityStatusParty();

			if (screeningVo.getSecurityStatusDate() != null) {
				boolean excludeSeconds = true;				
				date = screeningVo.getSecurityStatusDate().toDisplayFormat("ddMMMYY");
				time = screeningVo.getSecurityStatusDate().toDisplayTimeOnlyFormat(excludeSeconds);
				addSecurityInfo = screeningVo.getAdditionalSecurityInfo();

			}

			row.add(constatusCode);
			row.add(consignmentNumber);
			row.add(category);
			row.add(origin);
			row.add(destination);
			row.add(pou);
			row.add(securityStatusCode);
			row.add(securityAuthSM);
			row.add(screeningMethodSM);
			row.add(screeningReasonSE);
			row.add(secStaParty);
			row.add(date);
			row.add(time);
			row.add(constatusCodeVal);
			row.add(addSecurityInfo);

			reportData.add(row);
		}
		return reportData;
	}
}
