/*
 * PostalAdministrationSession.java Created on June 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-2047
 *
 */
public interface PostalAdministrationSession extends ScreenSession {

    /**
     * @return PostalAdministrationVO
     */
    PostalAdministrationVO getPaVO();
    
    /**
     * @param paVO
     */
    void setPaVO(PostalAdministrationVO paVO);
    
    /**
     * @return Collection<OneTimeVO>
     */
    Collection<OneTimeVO> getOneTimeCategory();
    
    /**
     * @param oneTimeCategory
     */
    void setOneTimeCategory(Collection<OneTimeVO> oneTimeCategory);
    /**
	 * Methods for getting <String,Collection<OneTimeVO>>
	 * @return oneTimeValues
	 */
	 HashMap<String,Collection<OneTimeVO>> getOneTimeValues();
	
	/**
	 * Methods for setting HashMap<String,Collection<OneTimeVO>>
	 * @param oneTimeValues
	 */
	 void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	/**
	 * Methods for removing HashMap<String,Collection<OneTimeVO>>
	 */
	 void removeOneTimeValues();
	 
	 HashMap<String,Collection<PostalAdministrationDetailsVO>> getPostalAdministrationDetailsVOs();

	 void setPostalAdministrationDetailsVOs(HashMap<String,Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs);
	 void removePostalAdministrationDetailsVOs();
	 
	 
}
