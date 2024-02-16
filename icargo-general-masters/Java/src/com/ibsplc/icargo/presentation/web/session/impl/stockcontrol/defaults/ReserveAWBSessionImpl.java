/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ReserveAWBSession;

/**
 * @author A-1747
 *
 */
public class ReserveAWBSessionImpl extends AbstractScreenSession 
implements ReserveAWBSession {
	
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.cto.reservestock";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_AWB="awbvalues";
	private static final String KEY_DATE="expirydate";
	private static final String KEY_AWBVO="reserveawbvo";
	private static final String KEY_AWBVOS="reserveawbvos";
	private static final String KEY_AWBLIST="awbnumbers";
	private static final String KEY_AIRLINE="airline";
	private static final String KEY_DOC="doctype";
	private static final String KEY_VO="documentvo";
	private static final String KEY_RESERVE="reserveddocs";
	private static final String KEY_REJECT="rejecteddocs";
	private static final String KEY_REORDER="reorderlevel";
	
	
	/**
	 * @return String
	 */
	public String getScreenID(){
        return KEY_SCREEN_ID;
    }

    /**
     * This method returns the MODULE name for the List Product screen
     */
    public String getModuleName(){
        return KEY_MODULE_NAME;
    } 
    /**
	 * @return Collection<String>
	 */
    public Collection<String> getAWBTypes() {
    	return ((Collection<String>)getAttribute(KEY_AWB));
    }
    
    /**
	 * This method is used to set the priority in session
	 * @param document
	 */
	public void setAWBTypes(Collection<String>  document) {
	    setAttribute(KEY_AWB, (ArrayList<String>)document);
	}
	/**
	 * @return void
	 */
	public void removeAWBTypes(){
		removeAttribute(KEY_AWB);
	}  
	/**
	 * @return void
	 */
	public String getExpiryDate() {
    	return ((String)getAttribute(KEY_DATE));
    }
    
    /**
	 * This method is used to set the priority in session
	 * @param document
	 */
	public void setExpiryDate(String  document) {
	    setAttribute(KEY_DATE, (String)document);
	}
	/**
	 * @return void
	 */
	public void removeExpiryDate(){
		removeAttribute(KEY_DATE);
	}  
	/**
	 * @return ReserveAWBVO
	 */
	public ReserveAWBVO getReserveAWBVO() {
		return ((ReserveAWBVO)getAttribute(KEY_AWBVO));		
	} 
    
    /**
	 * This method is used to set the priority in session
	 * @param reserveAWBVO
	 */
	public void setReserveAWBVO(ReserveAWBVO  reserveAWBVO){
		setAttribute(KEY_AWBVO, (ReserveAWBVO)reserveAWBVO);		
	}
	/**
	 * @return void
	 */
	public void removeReserveAWBVO() {
		removeAttribute(KEY_AWBVO);		
	}
	/**
	 * @return Collection<String>
	 */
	public Collection<String> getAWBs() {
		return ((Collection<String>)getAttribute(KEY_AWBLIST));		
	} 
    
    /**
	 * This method is used to set the priority in session
	 * @param awb
	 */
	public void setAWBs(Collection<String>  awb){
		setAttribute(KEY_AWBLIST, (ArrayList<String>)awb);		
	}
	/**
	 * @return void
	 */
	public void removeAWBs() {
		removeAttribute(KEY_AWBLIST);		
	}
	/**
	 * @return String
	 */
	public String getAirline() {
		return ((String)getAttribute(KEY_AIRLINE));	
	}
	/**
	 * @param airline
	 */
	public void setAirline(String airline) {
		setAttribute(KEY_AIRLINE, (String)airline);			
	}
	/**
	 * @return void
	 */
	public void removeAirline() {
		removeAttribute(KEY_AIRLINE);
	}
	/**
	 * @return String
	 */
	public String getDocType() {
		return ((String)getAttribute(KEY_DOC));		
	}	
	/**
	 * @param doctype
	 */
	public void setDocType(String doctype) {
		setAttribute(KEY_DOC, (String)doctype);
	}
	/**
	 * @return void
	 */
	public void removeDocType() {
		removeAttribute(KEY_DOC);
	}
	/**
	 * @return ReserveAWBVO
	 */
	public ReserveAWBVO getReserveAWBVOs() {
		return ((ReserveAWBVO)getAttribute(KEY_AWBVOS));		
	} 
    
    /**
	 * This method is used to set the priority in session
	 * @param reserveAWBVO
	 */
	public void setReserveAWBVOs(ReserveAWBVO  reserveAWBVO){
		setAttribute(KEY_AWBVOS, (ReserveAWBVO)reserveAWBVO);		
	}
	/**
	 * @return void
	 */
	public void removeReserveAWBVOs() {
		removeAttribute(KEY_AWBVOS);		
	}
	/**
	 * @return Collection<DocumentVO>
	 */
	public Collection<DocumentVO>  getDocumentVO() {
		 return (Collection<DocumentVO>)getAttribute(KEY_VO);
		
	}

	/**
	 * Method used to set indexMap to session
	 * @param stockFilterVO
	 */
	public void setDocumentVO(Collection<DocumentVO>  stockFilterVO) {
		setAttribute(KEY_VO, (ArrayList<DocumentVO>)stockFilterVO);
		
	}
	/**
    * This method removes the indexMAp in session
    */
	public void removeDocumentVO() {
		removeAttribute(KEY_VO);
		
	}
	/**
	 * @return String
	 */
	public String getReservedDoc() {
		return ((String)getAttribute(KEY_RESERVE));		
	}
	/**
	 * @param doctype
	 */
	public void setReservedDoc(String doctype) {
		setAttribute(KEY_RESERVE, (String)doctype);
	}	
	/**
	 * @return void
	 */
	public void removeReservedDoc() {
		removeAttribute(KEY_RESERVE);
	}
	/**
	 * @return String
	 */
	public String getRejectedDoc() {
		return ((String)getAttribute(KEY_REJECT));		
	}	
	/**
	 * @param doctype
	 */
	public void setRejectedDoc(String doctype) {
		setAttribute(KEY_REJECT, (String)doctype);
	}	
	/**
	 * @return void
	 */
	public void removeRejectedDoc() {
		removeAttribute(KEY_REJECT);
	}
	
	/**
	 * @return String
	 */
	public String getReorderLevel() {
		return ((String)getAttribute(KEY_REORDER));		
	}	
	/**
	 * @param reorderlevel
	 */
	public void setReorderLevel(String reorderlevel) {
		setAttribute(KEY_REORDER, (String)reorderlevel);
	}	
	/**
	 * @return void
	 */
	public void removeReorderLevel() {
		removeAttribute(KEY_REORDER);
	}
	
}
