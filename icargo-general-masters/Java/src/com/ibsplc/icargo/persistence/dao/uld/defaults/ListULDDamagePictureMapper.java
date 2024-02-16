/*
 * ListULDDamagePictureMapper.java Created on Apr 17, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.uld.defaults;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2883
 * This mapper is used for listing of ULD Damage Picture
 */
public class ListULDDamagePictureMapper implements Mapper<ULDDamagePictureVO> {
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	private boolean isOracleDatasource;
	private static final String DMGPIC = "DMGPIC";
	
	public ListULDDamagePictureMapper(boolean isOracleDatasource) {
		this.isOracleDatasource = isOracleDatasource;
	}
	/**
	 * @param resultSet
	 * @return ULDdamagePictureVO
	 * @throws SQLException
	 */
	
public ULDDamagePictureVO map(ResultSet resultSet) throws SQLException {
		log.entering("Mapper","ListULDDamagePictureMapper");
		ULDDamagePictureVO ulddamagePictureVO = new ULDDamagePictureVO();
		ImageModel imageModel = new ImageModel();
		byte[] imageData = null;
		imageModel.setContentType("image/gif");
		if (isOracleDatasource) {
			Blob blob=resultSet.getBlob(DMGPIC);
			if(Objects.nonNull(blob)){
				ulddamagePictureVO.setUldPicture(blob);
				int sizeOFBlob = (int) blob.length();
				long startIndex = 1;
				imageData = blob.getBytes(startIndex, sizeOFBlob);
				ulddamagePictureVO.setUldPictureByte(imageData);
				imageModel.setSize(sizeOFBlob);
				imageModel.setData(imageData);
				ulddamagePictureVO.setImage(imageModel);
			 }
		}else {
			if(Objects.nonNull(resultSet.getBytes(DMGPIC))){
				imageData = resultSet.getBytes(DMGPIC);
				ulddamagePictureVO.setUldPictureByte(imageData);
				int sizeOFByteArray = imageData.length;
				imageModel.setData(imageData);
				imageModel.setSize(sizeOFByteArray);
				ulddamagePictureVO.setImage(imageModel);
		       }
		    }
			ulddamagePictureVO.setImageSequenceNumber(resultSet.getInt("IMGSEQNUM"));
			ulddamagePictureVO.setSequenceNumber(resultSet.getInt("SEQNUM"));
			ulddamagePictureVO.setUldNumber(resultSet.getString("ULDNUM"));
			ulddamagePictureVO.setFileName(resultSet.getString("PICFILNAM"));
			log.exiting("Mapper","ListULDDamagePictureMapper");
        	
			return ulddamagePictureVO;
		
	}
}
