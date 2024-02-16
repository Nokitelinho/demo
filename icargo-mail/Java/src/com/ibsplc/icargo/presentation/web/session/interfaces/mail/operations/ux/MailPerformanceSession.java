package com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.CoTerminusVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailHandoverVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailServiceStandardVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public interface MailPerformanceSession extends ScreenSession{
	 /**
     * @return ArrayList<PartnerCarrierVO>
     */
    ArrayList<CoTerminusVO> getCoTerminusVOs();
    ArrayList<MailRdtMasterVO> getMailRdtMasterVOs();
    
    /**
     * @param partnerCarrierVOs
     */
    void setCoTerminusVOs(ArrayList<CoTerminusVO> coTerminusVOs);

    void setMailRdtMasterVOs(ArrayList<MailRdtMasterVO> mailRdtMasterVOs);

    /**
     * @return ArrayList<PartnerCarrierVO>
     */
    String getAirport();
    
    /**
     * @param partnerCarrierVOs
     */
    void setAirport(String Airport);
    
    public void setServiceLevels(ArrayList<OneTimeVO> serviceLevels);
    public ArrayList<OneTimeVO> getServiceLevels();
    public void setMailClasses(ArrayList<OneTimeVO> mailClasses);
    public ArrayList<OneTimeVO> getMailClasses();
    //Modified by A-7540
    public void setMailServiceStandardVOs(Page<MailServiceStandardVO> mailServiceStandardVOs);
    public Page<MailServiceStandardVO> getMailServiceStandardVOs();
    
    ArrayList<MailServiceStandardVO> getMailServiceStndVOs();
    public void setMailServiceStndVOs(ArrayList<MailServiceStandardVO> mailServiceStandardVOs);
    
    public void setMailServiceStandardVOsToDelete(ArrayList<MailServiceStandardVO> mailServiceStandardVOsToDelete);
    ArrayList<MailServiceStandardVO> getMailServiceStandardVOsToDelete();
    
    public void setResditModes(ArrayList<OneTimeVO> resditModes);
    public void removeResditMode();
    public ArrayList<OneTimeVO> getResditModes() ;
    ArrayList<USPSPostalCalendarVO> getUSPSPostalCalendarVOs();
    public void setUSPSPostalCalendarVOs(ArrayList<USPSPostalCalendarVO> uSPSPostalCalendarVOs);
    public ArrayList<OneTimeVO> getCalendarTypes();
    public void setCalendarTypes(ArrayList<OneTimeVO> calendarTypes);
    public void removeCalendarTypes();
	public Page<MailHandoverVO> getMailHandoverVOs();
    public void setMailHandoverVOs(Page<MailHandoverVO> mailHandoverVOs);
    public void removeMailHandoverVOs();
    //Added by A-7540
    public void removeMailServiceStandardVOs();
    public void setServiceLevel(ArrayList<OneTimeVO> serviceLevels);
    public void removeServiceLevel();
    public ArrayList<OneTimeVO> getServiceLevel();
    public int getTotalRecords() ;
    public void setTotalRecords(int totalRecords) ;
   
    public ArrayList<GPAContractVO> getGPAContractVOs();
    public void setGPAContractVOs(ArrayList<GPAContractVO> gpaContractVOs);
    public void removeGPAContractVOs();
    //Added for ICRD-232361
    public ArrayList<IncentiveConfigurationVO> getIncentiveConfigurationVOs() ;
    public void setIncentiveConfigurationVOs(ArrayList<IncentiveConfigurationVO> incentiveConfigurationVOs);
    public ArrayList<IncentiveConfigurationDetailVO> getIncentiveConfigurationDetailVOs() ;
    public void setIncentiveConfigurationDetailVOs(ArrayList<IncentiveConfigurationDetailVO> incentiveConfigurationDetailVOs);
   
    public ArrayList<OneTimeVO> getRateBreakDownValues();
    public void setRateBreakDownValues(ArrayList<OneTimeVO> RateBreakDownValues);
    public void removeRateBreakDownValues();
    
    public ArrayList<OneTimeVO> getFormulaBasis();
    public void setFormulaBasis(ArrayList<OneTimeVO> formulaBasisValues);
    public void removeFormulaBasis();
    
    public ArrayList<OneTimeVO> getFormulaReference();
    public void setFormulaReference(ArrayList<OneTimeVO> formulaReferenceValues);
    public void removeFormulaReference();
    
    public ArrayList<OneTimeVO> getIncentiveParameters();
    public void setIncentiveParameters(ArrayList<OneTimeVO> incentiveParameters);
    public void removeIncentiveParameters();
    
    public ArrayList<OneTimeVO> getMailServiceLevels();
    public void setMailServiceLevels(ArrayList<OneTimeVO> mailServiceLevels);
    public void removeMailServiceLevels();
    
    
    public ArrayList<OneTimeVO> getMailCategory();
    public void setMailCategory(ArrayList<OneTimeVO> mailCategory);
    public void removeMailCategory();
    
    public List<OneTimeVO> getMailAmot();
    public void setMailAmot(List<OneTimeVO> mailAmot);
    public void removeMailAmot();
    
    public String getPaCode();
    public void setPaCode(String paCode);
    
    public ArrayList<IncentiveConfigurationDetailVO> getParameterVO();
    public void setParameterVO(ArrayList<IncentiveConfigurationDetailVO> parameter);
    
    public ArrayList<String> getFormula();
    public void setFormula(ArrayList<String> formula);
    
    public String getBasis();
    public void setBasis(String basis);
    
    public String getCalendarIndex();
    public void setCalendarIndex(String index);
    
    public USPSPostalCalendarFilterVO getFilterVO();
    public void setFilterVO(USPSPostalCalendarFilterVO vo);
    
    
}
