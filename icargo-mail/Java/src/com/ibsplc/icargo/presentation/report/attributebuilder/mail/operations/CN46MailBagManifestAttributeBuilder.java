package com.ibsplc.icargo.presentation.report.attributebuilder.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class CN46MailBagManifestAttributeBuilder extends AttributeBuilderAdapter {

	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override
	public Vector<String> getReportColumns() {

		Vector<String> columns = new Vector<>();
		columns.add("CONNUM");
		columns.add("POU");
		columns.add("TRANSARPNAM");
		columns.add("MALCTGCOD");
		columns.add("MAILID");
		columns.add("ORGEXGOFC");
		columns.add("DSTEXGOFC");
		columns.add("CSGDAT");
		columns.add("FLTDAT");
		columns.add("DSN");
		columns.add("RSN");
		columns.add("LCCOUNT");
		columns.add("CPCOUNT");
		columns.add("OTHERSCOUNT");
		columns.add("WGT");
		columns.add("MALRMK");
		columns.add("FLTTIME");
		columns.add("PRVFLTNUM");
		columns.add("PRVFLTDAT");
		columns.add("DSTPOACOD");
		columns.add("SELNUM");
		columns.add("FLTNUM");
		columns.add("POANAM");
		columns.add("ARLNAM");
		columns.add("GROUPING");
		return columns;
	}


	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {
		Vector<Vector> reportData = new Vector<>();
		Vector<Object> row = null;
		Object dataRecords = ((ArrayList<Object>)data).get(0);
		MailManifestVO mailManifestVO = (MailManifestVO)dataRecords;
		Collection<ContainerDetailsVO> containerDetailsVOs = mailManifestVO.getContainerDetails();
		String str = "";
		
		for(ContainerDetailsVO containerDtlsVO:containerDetailsVOs){
			
			if(containerDtlsVO.getMailDetails()!=null && containerDtlsVO.getMailDetails().size()>0){
			Collection<MailbagVO> mailbagVOs = containerDtlsVO.getMailDetails();
			
			for(MailbagVO mailbagVO:mailbagVOs){
				
				row = new Vector<>();
				
				if("Y".equals(containerDtlsVO.getPaBuiltFlag())){
					str = new StringBuilder().append(containerDtlsVO.getContainerNumber())
 			       .append(MailConstantsVO.LABEL_SB).toString();
					row.add(str);
				}else{
					row.add(containerDtlsVO.getContainerNumber());
				}
				row.add(containerDtlsVO.getPou());
				row.add(containerDtlsVO.getTransistPort());
				row.add(mailbagVO.getMailCategoryCode());
				row.add(mailbagVO.getMailbagId());
				row.add(mailbagVO.getOriginOfExchangeOffice());
				row.add(mailbagVO.getDestinationOfExchangeOffice());
				row.add(new LocalDate(LocalDate.NO_STATION, Location.NONE, true).toDisplayFormat("yyyy MMM dd").toUpperCase());
				row.add(mailbagVO.getFlightDate()!= null ? mailbagVO.getFlightDate().toDisplayFormat("yyyy MMM dd").toUpperCase():ReportConstants.EMPTY_STRING);
				row.add(mailbagVO.getYear()+mailbagVO.getDsn());
				row.add(mailbagVO.getRsn());
				row.add(mailbagVO.getLcCount());
				row.add(mailbagVO.getCpCount());
				row.add(mailbagVO.getOthersCount());
				row.add(String.valueOf(mailbagVO.getWeight().getRoundedDisplayValue()));
				row.add(mailbagVO.getMailRemarks());
			    row.add(mailbagVO.getFlightDate()!= null ? mailbagVO.getFlightDate().toDisplayTimeOnlyFormat(true) : ReportConstants.EMPTY_STRING);
				row.add(mailbagVO.getFromFightNumber() !=null ? mailbagVO.getFromFightNumber():ReportConstants.EMPTY_STRING);
				row.add(mailbagVO.getFromFlightDate() !=null ? mailbagVO.getFromFlightDate().toDisplayFormat("MMM dd").toUpperCase():ReportConstants.EMPTY_STRING);
				row.add(mailbagVO.getDestinatonPortCode());
				row.add(mailbagVO.getSealNumber() !=null ? mailbagVO.getSealNumber():ReportConstants.EMPTY_STRING);
				
				row.add(containerDtlsVO.getCarrierCode()+containerDtlsVO.getFlightNumber());
				row.add(mailbagVO.getPaDescription() != null ? mailbagVO.getPaDescription():ReportConstants.EMPTY_STRING);
				row.add(mailManifestVO.getAirlineName() != null ? mailManifestVO.getAirlineName():ReportConstants.EMPTY_STRING);
				row.add(mailbagVO.getOriginOfExchangeOffice().concat(mailbagVO.getDestinatonPortCode()).concat(mailbagVO.getMailCategoryCode()));
				reportData.add(row);
				}

			}
			
		}
		return reportData;
	}

	
		
}

