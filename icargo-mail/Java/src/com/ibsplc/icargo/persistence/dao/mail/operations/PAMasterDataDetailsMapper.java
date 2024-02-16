/**
 * Java file	: 	com.ibsplc.icargo.persistence.dao.mail.operations.PAMasterDataDetailsMapper.java
 * <p>
 * Created by	:	204082
 * Created on	:	27-Sep-2022
 * <p>
 * Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 * <p>
 * This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.mail.operations;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PAMasterDataDetailsMapper implements Mapper<PostalAdministrationVO> {

    private static final String MAILTRACKING_DEFAULTS = "MAILTRACKING_DEFAULTS";
    private static final Log LOGGER = LogFactory.getLogger(MAILTRACKING_DEFAULTS);

    /**
     * Overriding Method	:	@see com.ibsplc.xibase.server.framework.persistence.query.sql.Mapper#map(java.sql.ResultSet)
     * Added by 			: 204082 on 27-Sep-2022
     * Used for 	:
     * Parameters	:	@param rs
     * Parameters	:	@return PostalAdministrationVO
     * Parameters	:	@throws SQLException
     */
    @Override
    public PostalAdministrationVO map(ResultSet rs) throws SQLException {
        LOGGER.entering(MAILTRACKING_DEFAULTS, "PAMasterDataDetailsMapper");
        PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
        postalAdministrationVO.setPaCode(rs.getString("POACOD"));
        postalAdministrationVO.setPaName(rs.getString("POANAM"));
        postalAdministrationVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, rs.getTimestamp("LSTUPDTIM")));
        LOGGER.log(Log.FINE, "postalAdministrationVO from mapper++++", postalAdministrationVO);
        LOGGER.exiting(MAILTRACKING_DEFAULTS, "PAMasterDataDetailsMapper");
        return postalAdministrationVO;
    }
}
