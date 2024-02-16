
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailcontainerlist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.SearchContainerFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailContainerListModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.session.interfaces.operations.flthandling.loadplan.MaintainLoadPlanSession;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailContainerListCommand extends AbstractCommand {

	private static final String CLASS_NAME = "MailContainerListCommand";
	
	private static final Log LOG = LogFactory.getLogger(CLASS_NAME);
	private static final String SCREEN_ID_LP = "operations.flthandling.loadplan.maintainloadplan";
	private static final String MODULE_NAME_LP = "operations.flthandling.loadplan";

	
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException {
		LOG.entering(CLASS_NAME, "execute");
		ArrayList<MailContainerListModel> results = new ArrayList<>();
		List<ErrorVO> erros= null;
		Page<ContainerVO>  containerVOPage = null;		
 
		  
		LogonAttributes logonAttributes = getLogonAttribute();
		MailContainerListModel mailContainerListModel = (MailContainerListModel) actionContext.getScreenModel();		
		ContainerFilter filterContainerModel = mailContainerListModel.getContainerFilter();
		ResponseVO responseVO = new ResponseVO();
		SearchContainerFilterVO searchContainerFilterVO = new  SearchContainerFilterVO();
		MaintainLoadPlanSession loadPlanSession = getScreenSession(MODULE_NAME_LP, SCREEN_ID_LP);
		Collection<ContainerVO> mailContainersFromLoadPlan = loadPlanSession.getMailContainerVOs();

		erros=(List<ErrorVO>)updateFilterVO(filterContainerModel, searchContainerFilterVO,logonAttributes);
        if (!erros.isEmpty()){        	
        	actionContext.addAllError(erros);
        	return; 
        }	
		String containerViewParameter = findSystemParameterValue("mail.operations.containercountlimitforlyinglist");
		searchContainerFilterVO.setContainerView(containerViewParameter);
		searchContainerFilterVO.setSource("OPR015");
		
		containerVOPage =findContainers(searchContainerFilterVO,
					filterContainerModel.getDisplayPage());
		removeAlreadyPlannedMailContainersFromLoadPlan(containerVOPage, mailContainersFromLoadPlan);
		if(containerVOPage==null || containerVOPage.isEmpty()){		
			actionContext.addError(new ErrorVO("No data found"));		
			return;
		}else{
			String actualWeightUnit=null;
			try {
				actualWeightUnit=findSystemParameterValue("mail.operations.defaultcaptureunit");
			}  catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
				}
			if(!containerVOPage.isEmpty()){
				for( ContainerVO containerVO : containerVOPage){	
					if(actualWeightUnit!=null){
						containerVO.setActualWeightUnit(actualWeightUnit);
					}
					if(containerVO.getPlannedFlightCarrierCode()!=null && containerVO.getPlannedFlightNum()!=0 && 
							containerVO.getPlannedFlightDate()!=null) {
						String plnfltanddat =new StringBuilder().append(containerVO.getPlannedFlightCarrierCode()).append(" ")
								.append(containerVO.getPlannedFlightNum()).append(" ")
								.append(containerVO.getPlannedFlightDate().toDisplayFormat("dd-MM-yyyy").toUpperCase()).toString();
						containerVO.setPlannedFlightAndDate(plnfltanddat);
					}
				}			
			}
		ArrayList<ContainerDetails> containerList = MailOperationsModelConverter.constructContainer(containerVOPage,logonAttributes); 
		PageResult<ContainerDetails> pageList = new PageResult<>(containerVOPage, containerList);		
		mailContainerListModel.setContainerDetails(pageList);
		results.add(mailContainerListModel);
		responseVO.setResults(results);
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);}
		LOG.exiting("CLASS_NAME","execute");

	}
    private Page<ContainerVO> findContainers(SearchContainerFilterVO searchContainerFilterVO, String displayPage)
    {
    	Page<ContainerVO> containerVOs = null;
      

      int pageNumber = Integer.parseInt(displayPage);
      try
      {
        containerVOs = new MailTrackingDefaultsDelegate().findContainers(searchContainerFilterVO, 
          pageNumber);
      } catch (BusinessDelegateException businessDelegateException) {
        businessDelegateException.getMessageVO().getErrors();
        handleDelegateException(businessDelegateException);
      }
      return containerVOs;
    }
    
    
    
    
    private static String findSystemParameterValue(String syspar) throws BusinessDelegateException {
    	String sysparValue = null;
    	ArrayList<String> systemParameters = new ArrayList<>();
    	systemParameters.add(syspar);
    	Map<String, String> systemParameterMap = new SharedDefaultsDelegate()
    			.findSystemParameterByCodes(systemParameters);
    	if (systemParameterMap != null) {
    		sysparValue = systemParameterMap.get(syspar);
    	}
    	return sysparValue;
    }

    

    private Collection<ErrorVO> updateFilterVO(ContainerFilter filterContainerModel, SearchContainerFilterVO searchContainerFilterVO, LogonAttributes logonAttributes)
    {
      
      Collection<ErrorVO> errorsMail = new ArrayList<>();
      searchContainerFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
     
      if ("ALL".equals(filterContainerModel.getAssignedTo())) {
    	     searchContainerFilterVO.setSearchMode("ALL");
    	     searchContainerFilterVO.setOperationType("O");
    	   }
    	   else if ("DESTN".equals(filterContainerModel.getAssignedTo())) {
    	     searchContainerFilterVO.setSearchMode("DESTN");
    	   } else if ("FLT".equals(filterContainerModel.getAssignedTo())) {
    	     searchContainerFilterVO.setSearchMode("FLT");
    	     searchContainerFilterVO.setOperationType("O");
    	   }else{
    	 	  searchContainerFilterVO.setSearchMode("ALL");
    	   }
        searchContainerFilterVO.setDeparturePort(logonAttributes.getAirportCode());
        
        
      if (filterContainerModel.getSubclassGroup() != null) {
          String subclassGroup = filterContainerModel.getSubclassGroup().trim();
          if (("EMS".equals(subclassGroup)) || 
            ("on".equals(subclassGroup))|| 
            ("true".equals(subclassGroup))){
            searchContainerFilterVO.setSubclassGroup("EMS");}
          else if("OTHERS".equals(subclassGroup)){
        	  searchContainerFilterVO.setSubclassGroup("OTHERS");
          }
          else{
            searchContainerFilterVO.setSubclassGroup("NONEMS"); 	
        }
	  }
        else {
          searchContainerFilterVO.setSubclassGroup("NONEMS");
        }
      
      String destination = filterContainerModel.getDestination();
      if ((destination != null) && (destination.trim().length() > 0) && !destination.equals("null")) {
        searchContainerFilterVO.setFinalDestination(destination);
   
        String companycode=logonAttributes.getCompanyCode();
        findValidateAirportCode(errorsMail,destination,companycode);
      
      }
      
      if("Y".equals(filterContainerModel.getUldFulIndFlag())) {
    	  searchContainerFilterVO.setUldFulIndFlag("Y");
      }
      if(filterContainerModel.getPageSize()!=null){
	  searchContainerFilterVO.setPageSize(Integer.parseInt(filterContainerModel.getPageSize()));} 
      searchContainerFilterVO.setExcATDCapFlights(true);
      if(filterContainerModel.getUnplannedContainers()!=null 
    		  && "true".equals(filterContainerModel.getUnplannedContainers())) {
    	  searchContainerFilterVO.setUnplannedContainers(true);
      }
      return errorsMail;
}   
    
    
   private Collection<ErrorVO> findValidateAirportCode(Collection<ErrorVO> errorsMail, String destination,String companycode){
       List<ErrorVO>errors = new ArrayList<>();
       try {
         new AreaDelegate().validateAirportCode(
           companycode,destination );
       }
       catch (BusinessDelegateException businessDelegateException) {
         errors = handleDelegateException(businessDelegateException);
       }
       if ((errors != null) && !errors.isEmpty()) {
           errorsMail.add(
             new ErrorVO("mail.operations.ux.invalidairport", 
             new Object[] { destination }));
         }

       
    	return errorsMail;
    }
   
	private void removeAlreadyPlannedMailContainersFromLoadPlan(Page<ContainerVO> containerVOPage,
			Collection<ContainerVO> mailContainersFromLoadPlan) {
		if (Objects.nonNull(mailContainersFromLoadPlan) && !mailContainersFromLoadPlan.isEmpty()
				&& Objects.nonNull(containerVOPage)) {
			Collection<ContainerVO> containerVOsToBeRemoved = new ArrayList<>();
			for (ContainerVO containerVO : containerVOPage) {
				if (mailContainersFromLoadPlan.stream().anyMatch(mailContainerFromLoadPlan -> containerVO
						.getContainerNumber().equals(mailContainerFromLoadPlan.getContainerNumber()))) {
					containerVOsToBeRemoved.add(containerVO);
				}
			}
			if (!containerVOsToBeRemoved.isEmpty()) {
				containerVOPage.removeAll(containerVOsToBeRemoved);
			}
		}
	}
}
