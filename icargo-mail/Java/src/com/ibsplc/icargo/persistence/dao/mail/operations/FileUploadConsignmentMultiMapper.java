/**
 * 
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-7794
 *
 */
public class FileUploadConsignmentMultiMapper implements MultiMapper<ConsignmentDocumentVO>  {
	private Log log = LogFactory.getLogger("mail.operations"); 
	Collection<ConsignmentDocumentVO> consignmentDocumentVOs = null;
	ConsignmentDocumentVO consignmentDocumentVO ;
	Collection<MailInConsignmentVO> mailVOs = null;

	public List<ConsignmentDocumentVO> map(ResultSet rs) throws SQLException {
		String currentKey = null;
		String previousKey = null;
		StringBuilder stringBuilder = null;
		int count =0;

		while (rs.next()) {
			if (consignmentDocumentVOs == null) {
				consignmentDocumentVOs = new ArrayList<ConsignmentDocumentVO>();
			}
			stringBuilder = new StringBuilder();
			currentKey = stringBuilder.append(rs.getString("CMPCOD")).append(
					rs.getString("POACOD")).append(rs.getString("REF003")).toString();
			log.log(Log.FINE, "CurrentKey : ", currentKey);
			log.log(Log.FINE, "PreviousKey : ", previousKey);
			if (!currentKey.equals(previousKey)) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				MailInConsignmentVO mailVO = new MailInConsignmentVO();
				mailVOs = new ArrayList<MailInConsignmentVO>();
				count =0;
				LocalDate scanDate = null;
				if(rs.getDate("DSPDAT") != null){
					scanDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("DSPDAT"));
				}
				//Data from MALMST put into MailInConsignmentVO
				mailVO.setMailId(rs.getString("MALIDR"));
				mailVO.setConsignmentDate(scanDate);
				mailVO.setCompanyCode(rs.getString("CMPCOD"));
				mailVO.setPaCode(rs.getString("POACOD"));
				mailVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
				mailVO.setOriginExchangeOffice(rs.getString("ORGNOFCEXGCOD"));
				mailVO.setDestinationExchangeOffice(rs.getString("DSTNOFCEXGCOD"));
				mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
				if(rs.getString("ORGAPRCOD") != null){
					mailVO.setMailOrigin(rs.getString("ORGAPRCOD"));
				}else{
					mailVO.setMailOrigin(rs.getString("ORGCITYCOD"));	
				}
				if(rs.getString("DSTARPCOD") != null){
					mailVO.setMailDestination(rs.getString("DSTARPCOD"));	
				}else{
					mailVO.setMailDestination(rs.getString("DSTCITYCOD"));	
				}
				mailVO.setDsn(rs.getString("REF001"));//MailBag Number from SHRGENEXTTAB; for validation purpose
				if(rs.getString("REF002") != null){
					String reqDlvryDate = rs.getString("REF002");
				LocalDate excelDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
				java.text.DateFormat  formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
				 try {
					 Date parseDate = formatter.parse(reqDlvryDate);
					 excelDate.setTime(parseDate);
				} catch (ParseException e) {
					e.getMessage();
				} 
				 mailVO.setReqDeliveryTime(excelDate);// scanDate of mailbag from excel; for validation purpose
				}
				
				mailVOs.add(mailVO);
				//Data from SHRGENEXTTAB put into ConsignmentDocumentVO
				LocalDate currentDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
				consignmentDocumentVO.setCarrierCode(rs.getString("REF003")); //CarrierCode
				consignmentDocumentVO.setCompanyCode(rs.getString("CMPCOD"));
				consignmentDocumentVO.setPaCode(rs.getString("POACOD"));
				consignmentDocumentVO.setConsignmentDate(currentDate);
				consignmentDocumentVO.setMailInConsignment(mailVOs);
				count++;
				consignmentDocumentVO.setStatedBags(count);
				consignmentDocumentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,0.0));
				consignmentDocumentVO.setConsignmentPriority("N");
				consignmentDocumentVOs.add(consignmentDocumentVO);
				previousKey = currentKey;
			}else{
				for(ConsignmentDocumentVO consVO: consignmentDocumentVOs){
					if(consVO.equals(consignmentDocumentVO)){
						MailInConsignmentVO mailVO = new MailInConsignmentVO();
						LocalDate scanDate = null;
						if(rs.getDate("DSPDAT") != null){
							scanDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE,rs.getDate("DSPDAT"));
						}
						//Data from MALMST put into MailInConsignmentVO
						mailVO.setMailId(rs.getString("MALIDR")); 
						mailVO.setConsignmentDate(scanDate);
						mailVO.setCompanyCode(rs.getString("CMPCOD"));
						mailVO.setPaCode(rs.getString("POACOD"));
						mailVO.setMailSequenceNumber(rs.getLong("MALSEQNUM"));
						mailVO.setOriginExchangeOffice(rs.getString("ORGNOFCEXGCOD"));
						mailVO.setDestinationExchangeOffice(rs.getString("DSTNOFCEXGCOD"));
						mailVO.setMailCategoryCode(rs.getString("MALCTGCOD"));
						if(rs.getString("ORGAPRCOD") != null){
							mailVO.setMailOrigin(rs.getString("ORGAPRCOD"));
						}else{
							mailVO.setMailOrigin(rs.getString("ORGCITYCOD"));	
						}
						if(rs.getString("DSTARPCOD") != null){
							mailVO.setMailDestination(rs.getString("DSTARPCOD"));	
						}else{
							mailVO.setMailDestination(rs.getString("DSTCITYCOD"));	
						}
						mailVO.setDsn(rs.getString("REF001"));//MailBag Number from SHRGENEXTTAB; for validation purpose
						if(rs.getString("REF002") != null){
							String reqDlvryDate = rs.getString("REF002");
							LocalDate excelDate =  new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
							java.text.DateFormat  formatter = new java.text.SimpleDateFormat("dd-MM-yyyy");
							Date parseDate;
							try {
								parseDate = formatter.parse(reqDlvryDate);
								excelDate.setTime(parseDate);
							} catch (ParseException e) {
								e.getMessage();
							}
							mailVO.setReqDeliveryTime(excelDate);
						}
						// scanDate of mailbag from excel; for validation purpose
						this.mailVOs.add(mailVO);
						consVO.setMailInConsignment(mailVOs);
						count++;
						consVO.setStatedBags(count);
					}
				}
			}

		}
		return (List<ConsignmentDocumentVO>) consignmentDocumentVOs;
	}

}
