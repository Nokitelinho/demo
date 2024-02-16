package com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.UpdateMultipleULDDetailsSession;

public class UpdateMultipleULDDetailsSessionImpl extends AbstractScreenSession
implements UpdateMultipleULDDetailsSession{
	
	private static final String MODULE = "uld.defaults";
	/*
	 * Key for oneTimeValues
	 */
	private static final String KEY_ONETIMEVALUES = "uld_defaults_maintaindmg_onetimevalues";

	private static final String SCREENID = "uld.defaults.updatemultipleulddetails";
	private static final String KEY_ULDDDAMAGEVOS = "uldDamageVOS";
	private static final String KEY_SELULDDMGVO="seluldDamageVO";
	private static final String KEY_REPAIRPORT="reportedAirport";
	private static final String KEY_REPDATE="reportedDate";
	private static final String KEY_DMGDESC="dmgDescription";
	private static final String KEY_DMGPIC="uldDamagePicVO";

	/**
	 * @return ScreenID
	 */
	public String getScreenID() {
		return SCREENID;
	}

	/**
	 * Method to get ProductName
	 *
	 * @return ProductName
	 */
	public String getModuleName() {
		return MODULE;
	}
	
	/** (non-Javadoc)
     * @return oneTimeValues(HashMap<String, Collection<OneTimeVO>>)
     */
    public HashMap<String, Collection<OneTimeVO>> getOneTimeValues() {
        return getAttribute(KEY_ONETIMEVALUES);
    }


    /** (non-Javadoc)
     * @see com.ibsplc.icargo.presentation.web.session.operations.interfaces.AcceptanceSessionInterface
     * #setOneTimeValues(java.util.HashMap<java.lang.String, com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO>)
     * @param oneTimeValues
     */
    public void setOneTimeValues(
    		HashMap<String, Collection<OneTimeVO>> oneTimeValues) {
    	setAttribute(KEY_ONETIMEVALUES, oneTimeValues);
    }
    
	

	
	
	  public void setULDDamageRepairDetailsVOs(Collection<ULDDamageRepairDetailsVO> uldVOs){
	 	   setAttribute(KEY_ULDDDAMAGEVOS, (ArrayList<ULDDamageRepairDetailsVO>)uldVOs);
	    }
	  

		
		public Collection<ULDDamageRepairDetailsVO> getULDDamageRepairDetailsVOs() {
			 return (Collection<ULDDamageRepairDetailsVO>) getAttribute(KEY_ULDDDAMAGEVOS);
		}
		
	
		 
		
			public ULDDamageRepairDetailsVO getSelectedDamageRepairDetailsVO(){
				return (ULDDamageRepairDetailsVO)getAttribute(KEY_SELULDDMGVO);
			}
			
			public void setSelectedDamageRepairDetailsVO(ULDDamageRepairDetailsVO uLDDamageRepairDetailsVO){
				setAttribute(KEY_SELULDDMGVO,uLDDamageRepairDetailsVO);
			}
			
			
			public void setReportedAirport(String airport) {
			    setAttribute(KEY_REPAIRPORT,airport);
			}

			public String getReportedAirport(){
				return getAttribute(KEY_REPAIRPORT);
			}

			public void setReportedDate(LocalDate date){
				setAttribute(KEY_REPDATE,date);
			}

			public LocalDate getReportedDate(){
				return getAttribute(KEY_REPDATE);
			}
			public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO(){
				return getAttribute(KEY_DMGDESC);
			}
			public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> uldDamageChecklistVO){
				setAttribute(KEY_DMGDESC,(ArrayList<ULDDamageChecklistVO>)uldDamageChecklistVO);
			}
			public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO)
			  {
			    setAttribute("KEY_DMGPIC", uldDamagePictureVO);
			  }
			  public ULDDamagePictureVO getULDDamagePictureVO()
			  {
			    return (ULDDamagePictureVO)getAttribute("KEY_DMGPIC");
			}
}
