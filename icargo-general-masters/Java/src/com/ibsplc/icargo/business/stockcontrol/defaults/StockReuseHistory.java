package com.ibsplc.icargo.business.stockcontrol.defaults;


import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockReuseHistoryVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@Table(name = "STKRUSHIS")
@Entity
@Staleable
public class StockReuseHistory {

	private static final String CLASS_NAME = "StockReuseHistory";

	private static final Log LOG = LogFactory.getLogger("STOCK");

	/**
	 * @return the stockReusePk
	 */
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "masterDocumentNumber", column = @Column(name = "MSTDOCNUM")),
			@AttributeOverride(name = "documentOwnerId", column = @Column(name = "DOCOWRIDR")),
			@AttributeOverride(name = "duplicateNumber", column = @Column(name = "DUPNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })

	public StockReuseHistoryPK getStockReusePk() {
		return stockReusePk;
	}

	/**
	 * @param stockReusePk the stockReusePk to set
	 */
	private void setStockReusePk(StockReuseHistoryPK stockReusePk) {
		this.stockReusePk = stockReusePk;
	}

	private String countryCode;
	private Calendar captureDate;

	/**
	 * @return the countryCode
	 */
	@Column(name = "CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the captureDate
	 */
	@Column(name = "CAPDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getCaptureDate() {
		return captureDate;
	}

	/**
	 * @param captureDate the captureDate to set
	 */
	public void setCaptureDate(Calendar captureDate) {
		this.captureDate = captureDate;

	}

	private StockReuseHistoryPK stockReusePk;

	public StockReuseHistory(StockReuseHistoryVO stockReuseHistoryVO)  {

 		populatePk(stockReuseHistoryVO);
 		populateAttributes(stockReuseHistoryVO);

	}

	private void populateAttributes(StockReuseHistoryVO stockReuseHistoryVO) {
		setCountryCode(stockReuseHistoryVO.getCountryCode());
 		if(stockReuseHistoryVO.getCaptureDate()!=null) {
		setCaptureDate(stockReuseHistoryVO.getCaptureDate().toCalendar());
 		}
	}

	public StockReuseHistory() {
	
	}

	/**
	 * Populate pk.
	 *
	 * @param stockReuseHistoryVO the stock Reuse HistoryVO
	 * @throws SystemException the system exception
	 */
	private void populatePk(StockReuseHistoryVO stockReuseHistoryVO) {

		StockReuseHistoryPK stockReuseHistoryPK = new StockReuseHistoryPK();


		stockReuseHistoryPK.setCompanyCode ( stockReuseHistoryVO.getCompanyCode());
		stockReuseHistoryPK.setMasterDocumentNumber ( stockReuseHistoryVO.getMasterDocumentNumber());
		stockReuseHistoryPK.setDocumentOwnerId ( stockReuseHistoryVO.getDocumentOwnerId());
		stockReuseHistoryPK.setDuplicateNumber (stockReuseHistoryVO.getDuplicateNumber());
		stockReuseHistoryPK.setSequenceNumber (stockReuseHistoryVO.getSequenceNumber());


	
		setStockReusePk(stockReuseHistoryPK);

	}

	/**
	 * Find.
	 *
	 * @param companyCode          the company code
	 * @param serialNumber         the serial number
	 * @param masterDocumentNumber the master document number
	 * @param documentOwnerId      the document owner id
	 * @param duplicateNumber      the duplicate number
	 * @param sequenceNumber       the sequence number
	 * @param countryCode          the country code
	 * @param captureDate          the capture date
	 * @return the stock reuse history
	 * @throws SystemException the system exception
	 * @throws FinderException the finder exception
	 */
	public static StockReuseHistory find(StockReuseHistoryVO stockReuseHistoryVO) throws SystemException, FinderException {
		LOG.entering(CLASS_NAME, "find");
		EntityManager em = PersistenceController.getEntityManager();
		StockReuseHistoryPK stockReuseHistoryPK = new StockReuseHistoryPK();
		stockReuseHistoryPK.setCompanyCode(stockReuseHistoryVO.getCompanyCode());
		stockReuseHistoryPK.setSerialNumber(stockReuseHistoryVO.getSerialNumber());
		stockReuseHistoryPK.setMasterDocumentNumber(stockReuseHistoryVO.getMasterDocumentNumber());
		stockReuseHistoryPK.setDocumentOwnerId(stockReuseHistoryVO.getDocumentOwnerId());
		stockReuseHistoryPK.setDuplicateNumber(stockReuseHistoryVO.getDuplicateNumber());
		stockReuseHistoryPK.setSequenceNumber(stockReuseHistoryVO.getSequenceNumber());
		LOG.exiting(CLASS_NAME, "find");
		return em.find(StockReuseHistory.class, stockReuseHistoryPK);
		
	}
	
	
	public void remove() throws SystemException {
        try{
            EntityManager entityManager = PersistenceController.
            getEntityManager();
             entityManager.remove(this);
      }catch(RemoveException removeException){
          throw new SystemException(removeException.getErrorCode(),removeException);
      }
  }

}
