package com.ibsplc.icargo.presentation.web.session.impl.mail.operations;



import java.util.Collection;
import java.util.HashMap;


import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MainOnHandListSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.business.mail.operations.vo.MailOnHandDetailsVO;

public class MailOnHandListSessionImpl extends AbstractScreenSession implements MainOnHandListSession {

	private static final String SCREEN_ID = "mailtracking.defaults.mailonhandlist";
	private static final String MODULE_NAME = "mail.operations";
	private static final String KEY_ONETIMEVALUES = "oneTimeValues";
	private static final String KEY_INDEXMAP = "indexMap";
	private static final String KEY_SEARCHCONTAINERFILTERVO = "searchContainerFilterVO";
	private static final String KEY_CONTAINERVOS = "MailOnHandDetailsVO";
	
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return MODULE_NAME;
	}
	@Override
	public String getScreenID() {
		// TODO Auto-generated method stub
		return SCREEN_ID;
	}

	
	
	/**
	 * @return Returns the oneTimeValues.
	 */
	public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
		return getAttribute(KEY_ONETIMEVALUES);
	}

	/**
	 * @param oneTimeValues
	 *            The oneTimeValues to set.
	 */
	public void setOneTimeValues(
			HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
		setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
	}
	
	
	
	/**
	 * This method returns the indexMap
	 * @return indexMap - HashMap<String, String>
	 */
	public HashMap<String, String> getIndexMap(){
		return (HashMap<String, String>)getAttribute(KEY_INDEXMAP);
	}
	
	/**
	 * This method is used to set indexMap to the session
	 * @param indexMap - HashMap<String, String>
	 */
	public void setIndexMap(HashMap<String, String> indexMap){
		setAttribute(KEY_INDEXMAP,indexMap);
	}
	
	
	/**
	 * The setter method for SearchContainerFilterVO
	 * @param searchContainerFilterVO
	 */
    public void setSearchContainerFilterVO(
    		SearchContainerFilterVO searchContainerFilterVO) {
    	setAttribute(KEY_SEARCHCONTAINERFILTERVO,searchContainerFilterVO);
    }
    /**
     * The getter method for searchContainerFilterVO
     * @return searchContainerFilterVO
     */
    public SearchContainerFilterVO getSearchContainerFilterVO() {
    	return getAttribute(KEY_SEARCHCONTAINERFILTERVO);
    }
	

	/**
	 * This method is used to set listContainerVOs to the session
	 * @param listContainerVOs - Page<ContainerVO>
	 */
	public void setMailOnHandDetailsVO(Page<MailOnHandDetailsVO> MailOnHandDetailsVO){
		setAttribute(KEY_CONTAINERVOS,(Page<MailOnHandDetailsVO>)MailOnHandDetailsVO);
	}

	/**
	 * This method returns the listContainerVOs
	 * @return listContainerVOs - Page<ContainerVO>
	 */
	public Page<MailOnHandDetailsVO> getMailOnHandDetailsVO(){
		return (Page<MailOnHandDetailsVO>)getAttribute(KEY_CONTAINERVOS);
	}
	
	//
}
