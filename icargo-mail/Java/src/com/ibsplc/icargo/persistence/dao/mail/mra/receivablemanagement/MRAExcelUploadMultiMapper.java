package com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DateUtil;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MRAExcelUploadMultiMapper implements MultiMapper<MRABatchUploadedVO>{
	
	private static final Log LOG = LogFactory.getLogger("MRA:RCVMNG");
	
	public List<MRABatchUploadedVO> map(ResultSet rs) throws SQLException {
		List<MRABatchUploadedVO> excelVOCollection=new ArrayList<>();
		java.text.DateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date payDate = null;
		while(rs.next()){
			MRABatchUploadedVO excelVO=	new MRABatchUploadedVO();
			excelVO.setCompanyCode(rs.getString("CMPCOD"));
			excelVO.setFileName(rs.getString("FILNAM"));
			excelVO.setSerialNumber(rs.getLong("SERNUM"));
			excelVO.setMailIdr(rs.getString("MALIDR"));
			String mailDate = rs.getString("MALDAT");
			String payDateFromExcel = rs.getString("PAYDAT");
			if(mailDate!=null){
				try {
					date = formatter.parse(mailDate);
				} catch (ParseException e) {
					LOG.log(Log.INFO, e);
					date = DateUtil.getJavaDate(Double.parseDouble(mailDate));
				}
				if(date!=null){
					LocalDate d =new LocalDate( LocalDate.NO_STATION, Location.NONE,true);
					d.setTime(date);
					excelVO.setMailDate(d);
				}
			}
			excelVO.setConsignmentDocNum(rs.getString("CSGDOCNUM"));
			if(payDateFromExcel!=null){
				try {
					payDate = formatter.parse(payDateFromExcel);
				} catch (ParseException e) {
					LOG.log(Log.INFO, e);
					payDate = DateUtil.getJavaDate(Double.parseDouble(payDateFromExcel));
				}
			if(payDate!=null){
					LocalDate paymentdate =new LocalDate( LocalDate.NO_STATION, Location.NONE,true);
					paymentdate.setTime(payDate);
					excelVO.setPayDate(paymentdate);
				}
			}
			double amount = 0;
			try{
				amount = rs.getDouble("PAYAMT");
				excelVO.setPayAmount(amount);
			}catch(SQLException | NumberFormatException e){
				LOG.log(Log.INFO, e);
				excelVO.setPayAmount(0);
			}
			excelVO.setCurrencyCode(rs.getString("CURCOD"));
			excelVO.setPaCode(rs.getString("GPACOD"));
			excelVOCollection.add(excelVO);
		}
		return excelVOCollection;
	}

}
