/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.document.vo.DocumentVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReserveAWBVO;
import com.ibsplc.icargo.framework.session.ScreenSession;

/**
 * @author A-1747
 *
 */
public interface ReserveAWBSession extends ScreenSession{
	/**
	 * @return Collection<String>
	 * @param 
	 */
	public Collection<String> getAWBTypes() ;    
    /**
	 * This method is used to set the priority in session
	 * @param document
	 */
	public void setAWBTypes(Collection<String>  document);
	/**
	 * @return void
	 * @param 
	 */
	public void removeAWBTypes();
	/**
	 * @return String
	 * @param 
	 */
	public String getExpiryDate() ;
    
    /**
	 * This method is used to set the priority in session
	 * @param document
	 */
	public void setExpiryDate(String  document);
	/**
	 * @return void
	 */
	public void removeExpiryDate();
	/**
	 * @return ReserveAWBVO
	 * @param 
	 */
	public ReserveAWBVO getReserveAWBVO() ;    
    /**
	 * This method is used to set the reserveAWBVO in session
	 * @param reserveAWBVO
	 */
	public void setReserveAWBVO(ReserveAWBVO  reserveAWBVO);
	/**
	 * @return void
	 */
	public void removeReserveAWBVO();
	/**
	 * @return ReserveAWBVO
	 * @param 
	 */
	public ReserveAWBVO getReserveAWBVOs() ;    
    /**
	 * This method is used to set the priority in session
	 * @param reserveAWBVO
	 */
	public void setReserveAWBVOs(ReserveAWBVO  reserveAWBVO);
	/**
	 * @return void
	 */
	public void removeReserveAWBVOs();
	/**
	 * @return Collection<String>
	 * @param 
	 */
	public Collection<String> getAWBs();
	/**
	 * @return void
	 * @param awbs
	 */
	public void setAWBs(Collection<String> awbs);
	/**
	 * @return void
	 * @param 
	 */
	public void removeAWBs();
	/**
	 * @return String
	 * @param 
	 */
	public String getAirline();	
	/**
	 * @return void
	 * @param airline
	 */
	public void setAirline(String airline);	
	/**
	 * @return void
	 * @param
	 */
	public void removeAirline();
	/**
	 * @return void
	 * @param
	 */
	
	public String getDocType();
	/**
	 * @return void
	 * @param doctype
	 */
	public void setDocType(String doctype);	
	/**
	 * @return void
	 * @param
	 */
	public void removeDocType();
	/**
	 * @return Collection<DocumentVO>
	 * @param 
	 */
	
	public Collection<DocumentVO>  getDocumentVO();

	/**
	 * Method used to set indexMap to session
	 * @param stockFilterVO
	 */
	public void setDocumentVO(Collection<DocumentVO>  stockFilterVO);
	/**
     * This method removes the indexMAp in session
     */
	 void removeDocumentVO();
	 /**
	 * @return void
	 * @param
	 */
	 String getReservedDoc();
	 /**
	 * @return void
	 * @param doctype
	 */
	 void setReservedDoc(String doctype);
	 /**
	 * @return void
	 * @param
	 */
	 void removeReservedDoc();
	 /**
	 * @return String
	 * @param
	 */
	 String getRejectedDoc();
	 /**
	 * @return void
	 * @param doctype
	 */
	 void setRejectedDoc(String doctype);
	 /**
	 * @return void
	 * @param
	 */
	 void removeRejectedDoc();
	 /**
	 * @return String
	 * @param
	 */
	
	 String getReorderLevel();
	 /**
	 * @return void
	 * @param reorderlevel
	 */
	 void setReorderLevel(String reorderlevel);
	 /**
	 * @return void
	 * @param
	 */
	 void removeReorderLevel();

}
