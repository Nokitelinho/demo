package com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



/**
 * @author A-2135
 *
 */
public interface ListtempCustomerSession extends ScreenSession {

    public String getScreenID();

    public String getModuleName();   
    


    public ListTempCustomerVO getListTempCustomerDetails();

    public void setListTempCustomerDetails(ListTempCustomerVO listTempCustomerDetails);

    public void removeListTempCustomerDetails();
    
    public Page<TempCustomerVO> getListCustomerRegistration();		
	
	public void setListCustomerRegistration(Page<TempCustomerVO> listCustomerRegistration); 
	
	public void removeListCustomerRegistration(); 
	
    
    public Collection<OneTimeVO> getOneTimeValues();

    public void setOneTimeValues(Collection<OneTimeVO> oneTimeValues);

    public void removeOneTimeValues();
    
    
    public TempCustomerVO getTempCustomerDetails();

    public void setTempCustomerDetails(TempCustomerVO tempCustomerDetails);

    public void removeTempCustomerDetails();
    
    
    public Page<TempCustomerVO> getListtempcustomerregistration();

    public void setListtempcustomerregistration(Page<TempCustomerVO> listtempcustomerregistration);

    public void removeListtempcustomerregistration();
    
    
    public ArrayList<String> getTempIDs();
    
    public void setTempIDs(ArrayList<String> tempIDs);
   
    public String getPageURL();

    public void setPageURL(String pageurl); 
    
    public String getCustCodeFlag();

    public void setCustCodeFlag(String custCodeFlag); 
    
    
    public Page<TempCustomerVO> getTempIdVOLovVOs();

	public void setTempIdVOLovVOs(Page<TempCustomerVO> tempIdVOLovVOs);

	public void removeTempIdVOLovVOs();
	//Added by A-5218 to enable last link in pagination to start
	/**
	 * @return
	 */
	public Integer getTotalRecords();
	/**
	 * @param totalRecords
	 */
	public void setTotalRecords(int totalRecords);
	//Added by A-5218 to enable last link in pagination to end
    
}