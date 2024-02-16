package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.xibase.server.framework.persistence.query.sql.MultiMapper;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Slf4j

public  class TransferManifestConsignmentDetail implements MultiMapper<ConsignmentDocumentVO> {
		private static final String CLASS_NAME = "TransferManifestConsignmentDetail";

		@Override
		public List<ConsignmentDocumentVO> map(ResultSet resultSet) throws SQLException {

			log.debug(CLASS_NAME, "map");
			List<ConsignmentDocumentVO> consignmentDocumentVOs = new ArrayList<>();
			ConsignmentDocumentVO consignmentDocumentVO = null;
			while(resultSet.next()) {
				consignmentDocumentVO = new ConsignmentDocumentVO();
				consignmentDocumentVO.setCompanyCode(resultSet.getString("CMPCOD"));
				consignmentDocumentVO.setConsignmentNumber(resultSet.getString("CSGDOCNUM"));
				consignmentDocumentVO.setPaCode(resultSet.getString("POACOD"));
				consignmentDocumentVOs.add(consignmentDocumentVO);
			}
			return consignmentDocumentVOs;

		}
	}