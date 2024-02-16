package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
//import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
//import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitConversionNewVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
public class PopulateVolumeCommand extends BaseCommand{
	   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	      
	   private static final String CLASS_NAME = "PopulateVolumeCommand";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";

	   private static final String TARGET_SUCCESS = "success";
	   private static final String STNPAR_DEFUNIT_VOL = "station.defaults.unit.volume";
	  
		public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			log.entering(CLASS_NAME,"execute");
			MailAcceptanceForm mailAcceptanceForm = 
	    		(MailAcceptanceForm)invocationContext.screenModel;
	    	MailAcceptanceSession mailAcceptanceSession = getScreenSession(MODULE_NAME,SCREEN_ID);
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			AreaDelegate areaDelegate = new AreaDelegate();
			Map stationParameters = null; 
 	    	String stationCode = logonAttributes.getStationCode();
	    	String companyCode=logonAttributes.getCompanyCode();
	    	try {
				stationParameters = areaDelegate.findStationParametersByCode(companyCode, stationCode, getStationParameterCodes());
			} catch (BusinessDelegateException e1) {
				
				e1.getMessage();
			}
		//	MailbagVO currentMailVO = new MailbagVO();
			MailbagVO unModifiedMailVO = new MailbagVO();
			MailbagVO sessionVO = mailAcceptanceSession.getUnmodifiedMailDetail();
			//MailAcceptanceVO mailAcceptanceVO = mailAcceptanceSession.getMailAcceptanceVO();
			try{
				BeanHelper.copyProperties(unModifiedMailVO,sessionVO);
			}catch(SystemException exception){
				log.log(Log.FINE, "exception-", exception.getMessage());
			}
			 int index=0;
			ContainerDetailsVO containerVO = new ContainerDetailsVO(); 
			containerVO = mailAcceptanceSession.getContainerDetailsVO();
	    	double weightForVol=unitConvertion(UnitConstants.MAIL_WGT,mailAcceptanceForm.getMailWtMeasure()[index].getDisplayUnit(),UnitConstants.WEIGHT_UNIT_KILOGRAM,mailAcceptanceForm.getMailWtMeasure()[index].getDisplayValue());
			double ActVol=(weightForVol/Double.parseDouble(mailAcceptanceForm.getDensity()));
			String stationVolumeUnit = (String)stationParameters.get(STNPAR_DEFUNIT_VOL);
			double stationVolume=unitConvertion(UnitConstants.VOLUME,UnitConstants.VOLUME_UNIT_CUBIC_METERS,stationVolumeUnit,ActVol);
			 mailAcceptanceForm.setVol(String.valueOf(stationVolume));
			 invocationContext.target = TARGET_SUCCESS;		 	
		       	
		    	log.exiting("CLASS_NAME","execute");
		}
		
		
		
		private double unitConvertion(String unitType,String fromUnit,String toUnit,double fromValue){
			UnitConversionNewVO unitConversionVO= null;
			try {
				unitConversionVO=UnitFormatter.getUnitConversionForToUnit(unitType,fromUnit,toUnit, fromValue);
			} catch (UnitException e) {
				// TODO Auto-generated catch block
				e.getMessage();
			}
			double convertedValue = unitConversionVO.getToValue();
			return convertedValue;
		}
	private Collection<String> getStationParameterCodes()
		  {
		    Collection stationParameterCodes = new ArrayList();
		    stationParameterCodes.add(STNPAR_DEFUNIT_VOL);
		    return stationParameterCodes;
        }		
}
