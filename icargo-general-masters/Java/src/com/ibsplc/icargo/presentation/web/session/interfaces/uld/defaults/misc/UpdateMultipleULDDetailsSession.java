package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.icargo.framework.util.time.LocalDate;

public interface UpdateMultipleULDDetailsSession extends ScreenSession { 
    
 
/**
 * Method to get the hashmap of one time values
 * @return HashMap<String, Collection<OneTimeVO>>
 */
HashMap<String, Collection<OneTimeVO>> getOneTimeValues();

/**
 * Method to set the one time values
 * @param oneTimeValues
 */
void setOneTimeValues(HashMap<String, Collection<OneTimeVO>> oneTimeValues);



public void setULDDamageRepairDetailsVOs(Collection<ULDDamageRepairDetailsVO> uldVO);

public Collection<ULDDamageRepairDetailsVO> getULDDamageRepairDetailsVOs();







public ULDDamageRepairDetailsVO getSelectedDamageRepairDetailsVO();

public void setSelectedDamageRepairDetailsVO(ULDDamageRepairDetailsVO uLDDamageRepairDetailsVO);

public void setReportedAirport(String airport);

public String getReportedAirport();

public void setReportedDate(LocalDate date);

public LocalDate getReportedDate();
public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO();
public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> uldDamageChecklistVO);

public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO);
public ULDDamagePictureVO getULDDamagePictureVO();
}