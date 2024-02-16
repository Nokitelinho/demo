package com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ux.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.currency.vo.CurrencyVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageChecklistVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamagePictureVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageRepairDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDamageVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairDetailsListVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.session.ScreenSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.model.UploadFileModel;

public interface MaintainDamageReportSession extends ScreenSession{
    
    
/**
 * 
 * @param ULDDamageVO
 */
void setULDDamageVO(ULDDamageRepairDetailsVO uldDamageVO);	
/**
 * 
 * @return ULDDamageVO
 */		
ULDDamageRepairDetailsVO getULDDamageVO();

/**
 * 
 * @param ULDDamageVO
 */
void setSavedULDDamageVO(ULDDamageRepairDetailsVO uldDamageVO);	
/**
 * 
 * @return ULDDamageVO
 */		
ULDDamageRepairDetailsVO getSavedULDDamageVO();

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

/**
 * @return Returns the currencies.
 */
ArrayList<CurrencyVO> getCurrencies();

/**
 * @param currencies The currencies to set.
 */
void setCurrencies(ArrayList<CurrencyVO> currencies);

/**
 * @return Returns the currencies.
 */
ArrayList<Long> getDmgRefNo();

/**
 * @param currencies The currencies to set.
 */
void setDmgRefNo(ArrayList<Long> dmgRefNo);

/**
 * @return
 */
ArrayList<String> getRefNo();

/**
 * @param currencies The currencies to set.
 */
void setRefNo(ArrayList<String> refNo);

/**
 * @return
 */
public Collection<ULDDamageVO> getULDDamageVOs();
/**
* 
* @param uldDamageVOs
*/
public void setULDDamageVOs(Collection<ULDDamageVO> uldDamageVO);
/**
* @return
*/
public Collection<ULDRepairVO> getULDRepairVOs();
/**
* 
* @param uldRepairVOs
*/
public void setULDRepairVOs(Collection<ULDRepairVO> uldRepairVOs);
/**
* 
* @return
*/
public ArrayList<String> getUldNumbers();
/**
* 
* @param uldNumbers
*/	 
public void setUldNumbers(ArrayList<String> uldNumbers);
/**
* 
* @return
*/
public String getPageURL();
/**
* 
* @param uldNumbers
*/	 
public void setPageURL(String pageurl);
/**
* 
* @param uldDamagePictureVO
*/
public void setULDDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO);
/**
 * 
 * @return
 */
public ULDDamagePictureVO getULDDamagePictureVO();
/**
 * 
 *
 */
public void removeULDDamagePictureVO();
/**
 * 
 * @return
 */
public String getForPic();
/**
 * 
 * @param pageurl
 */
public void setForPic(String pageurl);


/**
 * 
 * @return
 */
public ULDFlightMessageReconcileDetailsVO getULDFlightMessageReconcileDetailsVO();
/**
* 
* @param uldFlightMessageReconcileDetailsVO
*/
public void setULDFlightMessageReconcileDetailsVO(ULDFlightMessageReconcileDetailsVO uldFlightMessageReconcileDetailsVO);


String getUldNumber();

void setUldNumber(String uldNumber);

void removeUldNumber();

String getParentScreenId();

void setParentScreenId(String screenId);

void removeParentScreenId();

/**
 * 
 * @return
 */
public String getDefaultCurrency();
/**
 * 
 * @param currency
 */
public void setDefaultCurrency(String currency);
/**
 * @author a-3093
 * @return
 */
public UldDmgRprFilterVO getUldDmgRprFilterVO();
/**
 * @author a-3093
 * @param uldRepairFilterVO
 */
public void setUldDmgRprFilterVO(UldDmgRprFilterVO uldRepairFilterVO);
/**
 * @author a-3093
 * @return
 */

public Page<ULDRepairDetailsListVO> getULDDamageRepairDetailsVOs();

/**
 * @author a-3093
 * This method sets the ClearanceListingVO in session
 * @param paramCode
 */
public void setULDDamageRepairDetailsVOs(Page<ULDRepairDetailsListVO> paramCode);

/**
 * @author a-3093
 * This method removes the embargodetailsVos in session
 */
public void removeULDDamageRepairDetailsVOs();

/**
 * @return
 */
public Collection<ULDDamageChecklistVO> getULDDamageChecklistVO();
/**
* 
* @param uldDamageChecklistVO
*/
public void setULDDamageChecklistVO(Collection<ULDDamageChecklistVO> uldDamageChecklistVO);

/**
* remove details
*/
public void removeULDDamageChecklistVO();

public void setDamageTotalPoint(int damageTotalPoints);

public void setTotalDamagePoints(int totalDamagePoints);

public int getDamageTotalPoint();

public int getTotalDamagePoints();
/*
* Added by A-3415 for ICRD-113953.
*/
public void setNonOperationalDamageCodes(String nonOperationalDamageCodes);
public String getNonOperationalDamageCodes();

public HashMap<String,Collection<ULDDamageChecklistVO>> getDamageChecklistMap();//Added by A-7924 as part of ICRD-297517
void setDamageChecklistMap(HashMap<String, Collection<ULDDamageChecklistVO>> damageCheckList);//Added by A-7924 as part of ICRD-297517

/*Added by A-7636 as part of ICRD-245031*/
HashMap<String, ArrayList<UploadFileModel>> getDamageImageMap();
void setDamageImageMap(HashMap<String, ArrayList<UploadFileModel>> damageImageMap);
public ArrayList<UploadFileModel> getDamageImageList();
public void setDamageImageList(ArrayList<UploadFileModel> damageImageList);
public ArrayList<String> getRemoveDmgImage();
public void setRemoveDmgImage(ArrayList<String> removeImageIndex);
}
