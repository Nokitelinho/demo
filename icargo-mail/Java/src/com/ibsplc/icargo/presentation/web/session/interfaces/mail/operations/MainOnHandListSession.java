package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface MainOnHandListSession extends ScreenSession {

	
	
	 /**
	 * @return Returns the oneTimeValues.
	 */
	HashMap<String,Collection<OneTimeVO>> getOneTimeValues();

	/**
	 * @param oneTimeValues The oneTimeValues to set.
	 */

	void setOneTimeValues(HashMap<String,Collection<OneTimeVO>> oneTimeValues);
	
	
	
	
	//
	
	
	/**
	 * The setter method for searchContainerFilterVO
	 * @param searchContainerFilterVO
	 */
	public void setSearchContainerFilterVO(
    		SearchContainerFilterVO searchContainerFilterVO);
    /**
     * The getter method for SearchContainerFilterVO
     * @return SearchContainerFilterVO
     */
    public SearchContainerFilterVO getSearchContainerFilterVO();
	
	/**
	 * This method is used to set ContainerVOs to the session
	 * @param containervos - Page<ContainerVO>
	 */
	public void setMailOnHandDetailsVO(Page<MailOnHandDetailsVO> MailOnHandDetailsVO);

	/**
	 * This method returns the ContainerVOs
	 * @return CONTAINERVOS - Page<ContainerVO>
	 */
	public Page<MailOnHandDetailsVO> getMailOnHandDetailsVO();
	
	/**
	 * The setter method for indexMap
	 * @param indexMap
	 */
	public void setIndexMap(
			HashMap<String, String> indexMap);
    /**
     * The getter method for indexMap
     * @return indexMap
     */
    public HashMap<String, String> getIndexMap();
	
}
