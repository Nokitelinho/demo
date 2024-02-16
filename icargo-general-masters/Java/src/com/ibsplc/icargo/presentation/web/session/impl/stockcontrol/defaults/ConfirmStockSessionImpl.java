/**
 * 
 */
package com.ibsplc.icargo.presentation.web.session.impl.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineLovVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.MissingStockVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.RangeVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockHolderPriorityVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRequestVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.TransitStockVO;
import com.ibsplc.icargo.framework.session.AbstractScreenSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.stockcontrol.defaults.ConfirmStockSession;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


/**
 * @author A-4443
 *
 */
public class ConfirmStockSessionImpl extends AbstractScreenSession
implements ConfirmStockSession{
	private static final String KEY_SCREEN_ID = "stockcontrol.defaults.confirmstock";
	private static final String KEY_MODULE_NAME = "stockcontrol.defaults";
	private static final String KEY_MAP = "map";
	private static final String KEY_STATUS="status";
	private static final String KEY_DOCTYPE = "docType";
	private static final String KEY_DOCSUBTYPE = "docSubType";
	private static final String KEY_STOCKHOLDERTYPE = "stockHolderType";
	private static final String KEY_PAGESTOCKREQUEST = "pageStockRequestVO";
	private static final String KEY_RANGEVO = "rangeVO";
	private static final String KEY_STOCKHOLDERFOR = "stockHolderFor";
	private static final String KEY_MODE = "mode";
	private static final String KEY_DATA = "data";
	private static final String KEY_CHECK = "check";
	private static final String PRIO_STOCKHOLDER_ANS = "prioritizedstockholder";
	private static final String FILTER_DETAILS = "allocateStockFilterDetails";
	private static final String BUTTONSTATUS_FLAG = "buttonStatusFlag";
	private static final String KEY_STOCKREQUESTVO = "stockRequestVO";
	private static final String KEY_PARTNER_AIRLINES="partner.airlines";
	private static final String KEY_OPERATION = "operation";
	private static final String KEY_TRANSITSTOCKVO = "transitStockVO";
	private static final String KEY_SELECTED_TRANSITSTOCKVO = "selectedTransitStockVO";
	private static final String KEY_MISSINGSTOCKVO = "missingStockVO";
	
	public String getModuleName() {
		// TODO Auto-generated method stub
		return KEY_MODULE_NAME;
	} 

	
	public String getScreenID() {
		// TODO Auto-generated method stub
		return KEY_SCREEN_ID;
	}

	public Collection<OneTimeVO> getStockHolderType() {
		// TODO Auto-generated method stub
		return (Collection<OneTimeVO>)getAttribute(KEY_STOCKHOLDERTYPE);
	}

	public void setStockHolderType(Collection<OneTimeVO> stockHolderType) {
		setAttribute(KEY_STOCKHOLDERTYPE, (ArrayList<OneTimeVO>)stockHolderType);
		
	}

	public void removeStockHolderType() {
		removeAttribute(KEY_STOCKHOLDERTYPE);
		
	}

	public Collection<String> getStockHolderFor() {
		// TODO Auto-generated method stub
		   return (Collection<String>)getAttribute(KEY_STOCKHOLDERFOR);
	}

	public void setStockHolderFor(Collection<String> stockHolderFor) {
		// TODO Auto-generated method stub
		setAttribute(KEY_STOCKHOLDERFOR,(ArrayList<String>)stockHolderFor);
	}

	public void removeStockHolderFor() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_STOCKHOLDERFOR);
	}

	public Collection<RangeVO> getRangeVO() {
		// TODO Auto-generated method stub
		return (Collection<RangeVO>)getAttribute(KEY_RANGEVO);
	}

	public void setRangeVO(Collection<RangeVO> rangeVO) {
		// TODO Auto-generated method stub
		setAttribute(KEY_RANGEVO,(ArrayList<RangeVO>)rangeVO);
	}

	public void removeRangeVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_RANGEVO);
	}

	public Page<StockRequestVO> getPageStockRequestVO() {
		// TODO Auto-generated method stub
		return (Page<StockRequestVO>)getAttribute(KEY_STOCKREQUESTVO);
	}

	public void setPageStockRequestVO(Page<StockRequestVO> pageStockRequestVO) {
		// TODO Auto-generated method stub
		 setAttribute(KEY_PAGESTOCKREQUEST, (Page<StockRequestVO>)pageStockRequestVO);
	}

	public void removePageStockRequestVO() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_PAGESTOCKREQUEST);
	}

	public String getData() {
		// TODO Auto-generated method stub
		 return (String)getAttribute(KEY_DATA);
	}

	public void setData(String data) {
		// TODO Auto-generated method stub
		 setAttribute(KEY_DATA, (String)data);
	}

	public void removeData() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_DATA);
	}

	public String getCheck() {
		// TODO Auto-generated method stub
		 return (String)getAttribute(KEY_CHECK);
	}

	public void setCheck(String check) {
		// TODO Auto-generated method stub
		   setAttribute(KEY_CHECK, (String)check);
	}

	public void removeCheck() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_CHECK);
	}

	public String getDocType() {
		// TODO Auto-generated method stub
		 return (String)getAttribute(KEY_DOCTYPE);
	}

	public void setDocType(String docType) {
		// TODO Auto-generated method stub
		 setAttribute(KEY_DOCTYPE, (String)docType);
	}

	public void removeDocType() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_DOCTYPE);
	}

	public String getDocSubType() {
		// TODO Auto-generated method stub
		 return (String)getAttribute(KEY_DOCSUBTYPE);
	}

	public void setDocSubType(String docSubType) {
		// TODO Auto-generated method stub
		 setAttribute(KEY_DOCSUBTYPE, (String)docSubType);
	}

	public void removeDocSubType() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_DOCSUBTYPE);
	}

	public Collection<OneTimeVO> getStatus() {
		// TODO Auto-generated method stub
		   return (Collection<OneTimeVO>)getAttribute(KEY_STATUS);
	}

	public void setStatus(Collection<OneTimeVO> status) {
		// TODO Auto-generated method stub
		setAttribute(KEY_STATUS, (ArrayList<OneTimeVO>)status);
	}

	public void removeStatus() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_STATUS);
	}

	public HashMap<String, Collection<String>> getMap() {
		// TODO Auto-generated method stub
		 return (HashMap<String,Collection<String>>)getAttribute(KEY_MAP);
	}

	public void setMap(HashMap<String, Collection<String>> map) {
		// TODO Auto-generated method stub
		 setAttribute(KEY_MAP, (HashMap<String,Collection<String>>)map);
	}

	public void removeMap() {
		// TODO Auto-generated method stub
		removeAttribute("KEY_MAP");
	}

	public String getMode() {
		// TODO Auto-generated method stub
		return (String)getAttribute(KEY_MODE);
	}

	public void setMode(String mode) {
		// TODO Auto-generated method stub
		setAttribute(KEY_MODE, (String)mode);
	}

	public void removeMode() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_MODE);
	}

	public Collection<StockHolderPriorityVO> getPrioritizedStockHolders() {
		// TODO Auto-generated method stub
		return (Collection<StockHolderPriorityVO>) getAttribute(PRIO_STOCKHOLDER_ANS);
	}

	public void setPrioritizedStockHolders(
			Collection<StockHolderPriorityVO> prioritizedStockHolders) {
		// TODO Auto-generated method stub
		setAttribute(PRIO_STOCKHOLDER_ANS,
				(ArrayList<StockHolderPriorityVO>) prioritizedStockHolders);
	}

	public StockRequestFilterVO getFilterDetails() {
		// TODO Auto-generated method stub
		 return (StockRequestFilterVO)getAttribute(FILTER_DETAILS);
	}

	public void setFilterDetails(StockRequestFilterVO stockRequestFilterVO) {
		// TODO Auto-generated method stub
		setAttribute(FILTER_DETAILS, (StockRequestFilterVO)stockRequestFilterVO);
	}

	public String getButtonStatusFlag() {
		// TODO Auto-generated method stub
		 return (String)getAttribute(BUTTONSTATUS_FLAG);
	}

	public void setButtonStatusFlag(String buttonStatusFlag) {
		// TODO Auto-generated method stub
		setAttribute(BUTTONSTATUS_FLAG, (String)buttonStatusFlag);
	}

	public StockRequestVO getStockRequestVO() {
		// TODO Auto-generated method stub
		  return (StockRequestVO)getAttribute(KEY_STOCKREQUESTVO);
	}

	public void setStockRequestVO(StockRequestVO stockRequestVO) {
		// TODO Auto-generated method stub
		setAttribute(KEY_STOCKREQUESTVO, (StockRequestVO)stockRequestVO);
	}

	public void setPartnerAirlines(Page<AirlineLovVO> partnerAirlines) {
		// TODO Auto-generated method stub
		setAttribute(KEY_PARTNER_AIRLINES,partnerAirlines);
	}

	public Page<AirlineLovVO> getPartnerAirlines() {
		// TODO Auto-generated method stub
		return getAttribute(KEY_PARTNER_AIRLINES);
	}

	public Collection<OneTimeVO> getOperation() {
		// TODO Auto-generated method stub
		   return (Collection<OneTimeVO>)getAttribute(KEY_OPERATION);
	}

	public void setOperation(Collection<OneTimeVO> status) {
		// TODO Auto-generated method stub
		setAttribute(KEY_OPERATION, (ArrayList<OneTimeVO>)status);
	}

	public void removeOperation() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_OPERATION);
	}


	public Collection<TransitStockVO> getTransitStockVOs() {
		// TODO Auto-generated method stub
		return (Collection<TransitStockVO>)getAttribute(KEY_TRANSITSTOCKVO);
	}


	public void setTransitStockVOs(Collection<TransitStockVO> transitStockVOs) {
		// TODO Auto-generated method stub
		setAttribute(KEY_TRANSITSTOCKVO, (ArrayList<TransitStockVO>)transitStockVOs);
	}


	public void removeTransitStockVOs() {
		// TODO Auto-generated method stub
		removeAttribute(KEY_TRANSITSTOCKVO);
	}
	
	public void setSelectedTransitStockVOs(Collection<TransitStockVO> transitStockVOs) {
		
		setAttribute(KEY_SELECTED_TRANSITSTOCKVO, (ArrayList<TransitStockVO>)transitStockVOs);
	}


	public void removeSelectedTransitStockVOs() {
		
		removeAttribute(KEY_SELECTED_TRANSITSTOCKVO);
	}


	public Collection<TransitStockVO> getSelectedTransitStockVOs() {
		
		return (Collection<TransitStockVO>)getAttribute(KEY_SELECTED_TRANSITSTOCKVO);
	}
	
	
	public void setMissingStockVOs(Collection<MissingStockVO> missingStockVOs) {
		
		setAttribute(KEY_MISSINGSTOCKVO, (ArrayList<MissingStockVO>)missingStockVOs);
	}


	public void removeMissingStockVOs() {
		
		removeAttribute(KEY_MISSINGSTOCKVO);
	}


	public Collection<MissingStockVO> getMissingStockVOs() {
		
		return (Collection<MissingStockVO>)getAttribute(KEY_MISSINGSTOCKVO);
	}
}
