package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.junit.Test;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.HAWBVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class SavePostalHAWBInvokerTest extends AbstractFeatureTest {

	private SavePostalHAWBInvoker spy;
	private CarditVO carditVO;
	private OperationsShipmentProxy operationsShipmentProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private ShipmentValidationVO shipmentValidationVO;
	private Collection<HAWBVO> hawVOs;
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private ShipmentDetailVO shipmentDetailVO;
	private static final String MSTDOCNUM = "FRCDGADEFRAAACA11001101110011";
	private CarditVO setCarditVO() {
		CarditVO carditVo = new CarditVO();
		carditVo.setCompanyCode("IBS");
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		Collection<MailInConsignmentVO> mailInConsigmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailClass("C");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditPawbDetailsVO.setShipmentDetailVO(setShipmentDetailVO());
		carditVo.setCarditPawbDetailsVO(carditPawbDetailsVO);
		return carditVo;
	}

	private ShipmentValidationVO setShipmentValidationVO() {
		ShipmentValidationVO shipmentValidationVo = new ShipmentValidationVO();
		shipmentValidationVo.setShipmentPrefix("134");
		shipmentValidationVo.setMasterDocumentNumber("12345678");
		shipmentValidationVo.setOwnerId(1134);
		shipmentValidationVo.setDuplicateNumber(1);
		shipmentValidationVo.setSequenceNumber(1);
		shipmentValidationVo.setCompanyCode("IBS");
		shipmentValidationVo.setStationCode("CDG");
		shipmentValidationVo.setOwnerCode("aa");
		return shipmentValidationVo;
	}

	private ShipmentDetailVO setShipmentDetailVO() {
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setShipmentPrefix("134");
		shipmentDetailVO.setMasterDocumentNumber("12345678");
		shipmentDetailVO.setOwnerId(1134);
		shipmentDetailVO.setDuplicateNumber(1);
		shipmentDetailVO.setSequenceNumber(1);
		shipmentDetailVO.setCompanyCode("IBS");
		shipmentDetailVO.setStationCode("CDG");
		shipmentDetailVO.setOwnerCode("aa");
		return shipmentDetailVO;
	}

	private Collection<HAWBVO> constructHAWBVOs() {
		Collection<HAWBVO> hawbs = new ArrayList<>();
		for (MailInConsignmentVO mailInConsignmentVO : carditVO.getCarditPawbDetailsVO().getMailInConsignmentVOs()) {
			HAWBVO hawbVO = new HAWBVO();
			hawbVO.setCompanyCode(shipmentValidationVO.getCompanyCode());
			hawbVO.setDocumentNumber(shipmentValidationVO.getMasterDocumentNumber());
			hawbVO.setDuplicateNumber(shipmentValidationVO.getDuplicateNumber());
			hawbVO.setHawbNumber(mailInConsignmentVO.getMailId());
			hawbVO.setOwnerId(shipmentValidationVO.getOwnerId());
			hawbVO.setOwnerCode(shipmentValidationVO.getOwnerCode());
			hawbVO.setOperationFlag(AbstractVO.OPERATION_FLAG_INSERT);
			hawbVO.setOrigin(shipmentValidationVO.getOrigin());
			hawbVO.setDestination(shipmentValidationVO.getDestination());
			hawbVO.setStatedPieces(1);
			hawbVO.setStatedWeight(mailInConsignmentVO.getStatedWeight());
			hawbVO.setShipperCode(shipmentValidationVO.getShipperCode());
			hawbVO.setConsigneeCode(shipmentValidationVO.getConsigneeCode());
			hawbVO.setConsigneeName(shipmentValidationVO.getConsigneeName());
			hawbs.add(hawbVO);
		}
		return hawbs;
	}

	@Override
	public void setup() throws Exception {
		operationsShipmentProxy = mockProxy(OperationsShipmentProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		carditVO = setCarditVO();
		shipmentValidationVO = setShipmentValidationVO();
		hawVOs = constructHAWBVOs();
		EntityManagerMock.mockEntityManager();
		spy = (SavePostalHAWBInvoker) ICargoSproutAdapter
				.getBean(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_HAWB_INVOKER);
		 shipmentDetailVO = setShipmentDetailVO();
	}

	@Test
	public void shouldAddToContextAfterSavePostalHAWB() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		OfficeOfExchangeCache officeOfExchangeCache;
		shipmentValidationVOs.add(shipmentValidationVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		shipmentDetailVOs.add(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		Collection<ShipmentValidationVO> updatedshipmentValidationVOs = new ArrayList<>();
		ShipmentValidationVO shipmentValidation = new ShipmentValidationVO();
		shipmentValidation.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110011");
		updatedshipmentValidationVOs.add(shipmentValidation);
		carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(updatedshipmentValidationVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);

	}
	
	@Test
	public void shouldThrowFinderException() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		shipmentDetailVOs.add(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		Collection<ShipmentValidationVO> updatedshipmentValidationVOs = new ArrayList<>();
		ShipmentValidationVO shipmentValidation = new ShipmentValidationVO();
		shipmentValidation.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110011");
		updatedshipmentValidationVOs.add(shipmentValidation);
		carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(updatedshipmentValidationVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);

	}
	


	@Test
	public void shouldNotAddToContextIfMailbagsIsNull() throws SystemException, BusinessException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		carditVO.getCarditPawbDetailsVO().setMailInConsignmentVOs(null);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		spy.invoke(carditVO);

	}

	@Test
	public void shouldNotAddToContextIfMailbagsIsEmpty() throws SystemException, BusinessException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		carditVO.getCarditPawbDetailsVO().setMailInConsignmentVOs(new ArrayList<>());
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		spy.invoke(carditVO);

	}

	@Test
	public void shouldNotAddToContextIfShipmentValidationVoIsNull() throws SystemException, BusinessException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(null);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		spy.invoke(carditVO);

	}
	@Test
	public void invoke_WithExistingHawbs() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<HAWBSummaryVO>hawbSummaryVOs = new ArrayList<>();
		HAWBSummaryVO hawbSummaryVO = new HAWBSummaryVO();
		hawbSummaryVO.setCompanyCode("IBS");
		hawbSummaryVO.setMasterDocumentNumber(MSTDOCNUM);
		hawbSummaryVO.setDuplicateNumber(2);
		hawbSummaryVO.setOwnerId(134);
		hawbSummaryVO.setSequenceNumber(32);
		hawbSummaryVO.setDocumentNumber("FRCDGADEFRAAACA11001101110011");
		hawbSummaryVOs.add(hawbSummaryVO);
		shipmentDetailVO.setHouses(hawbSummaryVOs);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		shipmentDetailVOs.add(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		Collection<ShipmentValidationVO> updatedshipmentValidationVOs = new ArrayList<>();
		ShipmentValidationVO shipmentValidation = new ShipmentValidationVO();
		shipmentValidation.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110011");
		updatedshipmentValidationVOs.add(shipmentValidation);
		carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(updatedshipmentValidationVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);
	}
	@Test(expected = SystemException.class)
	public void invoke_WithMailNoneMatch() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		OfficeOfExchangeCache officeOfExchangeCache;
		shipmentValidationVOs.add(shipmentValidationVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<HAWBSummaryVO>hawbSummaryVOs = new ArrayList<>();
		HAWBSummaryVO hawbSummaryVO = new HAWBSummaryVO();
		hawbSummaryVO.setCompanyCode("IBS");
		hawbSummaryVO.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110021");
		hawbSummaryVO.setDuplicateNumber(2);
		hawbSummaryVO.setOwnerId(134);
		hawbSummaryVO.setSequenceNumber(32);
		hawbSummaryVO.setDocumentNumber("FRCDGADEFRAAACA11001101110021");
		hawbSummaryVOs.add(hawbSummaryVO);
		shipmentDetailVO.setHouses(hawbSummaryVOs);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		shipmentDetailVOs.add(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		carditVO.setCarditType("CANCEL"); 
		Collection<ShipmentValidationVO> updatedshipmentValidationVOs = new ArrayList<>();
		ShipmentValidationVO shipmentValidation = new ShipmentValidationVO();
		shipmentValidation.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110011");
		updatedshipmentValidationVOs.add(shipmentValidation);
		carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(updatedshipmentValidationVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		SystemException exception = new SystemException("");
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenThrow(ProxyException.class);
		spy.invoke(carditVO);
	}
	@Test
	public void invoke_WithEmptyHawbs() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
	    OfficeOfExchangeCache officeOfExchangeCache;
		shipmentValidationVOs.add(shipmentValidationVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<HAWBSummaryVO>hawbSummaryVOs = new ArrayList<>();
		shipmentDetailVO.setHouses(hawbSummaryVOs);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);
	}
	@Test
	public void constructHouseAwbToBeUpdated_ThrowFinderException() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		shipmentValidationVOs.add(shipmentValidationVO);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		Collection<HAWBSummaryVO>hawbSummaryVOs = new ArrayList<>();
		HAWBSummaryVO hawbSummaryVO = new HAWBSummaryVO();
		hawbSummaryVO.setCompanyCode("IBS");
		hawbSummaryVO.setMasterDocumentNumber(MSTDOCNUM);
		hawbSummaryVO.setDuplicateNumber(2);
		hawbSummaryVO.setOwnerId(134);
		hawbSummaryVO.setSequenceNumber(32);
		hawbSummaryVO.setDocumentNumber("FRCDGADEFRAAACA11001101110011");
		hawbSummaryVOs.add(hawbSummaryVO);
		shipmentDetailVO.setHouses(hawbSummaryVOs);
		shipmentDetailVOs.add(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		Collection<ShipmentValidationVO> updatedshipmentValidationVOs = new ArrayList<>();
		ShipmentValidationVO shipmentValidation = new ShipmentValidationVO();
		shipmentValidation.setMasterDocumentNumber("FRCDGADEFRAAACA11001101110011");
		updatedshipmentValidationVOs.add(shipmentValidation);
		carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(updatedshipmentValidationVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);
	}
	@Test
	public void invoke_WithAwbDeleted() throws SystemException, BusinessException, FinderException {
		Collection<ShipmentValidationVO> shipmentValidationVOs = new ArrayList<>();
		OfficeOfExchangeCache officeOfExchangeCache;
		shipmentValidationVOs.add(shipmentValidationVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("AV");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("AV");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		officeOfExchangeCache = spy(OfficeOfExchangeCache.class);
		doReturn(officeOfExchangeCache).when(CacheFactory.getInstance()).getCache(OfficeOfExchangeCache.ENTITY_NAME);
		doReturn(shipmentValidationVOs).when(operationsShipmentProxy).saveHAWBDetails(shipmentValidationVO, hawVOs,
				true);
		carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		Collection<HAWBSummaryVO>hawbSummaryVOs = new ArrayList<>();
		shipmentDetailVO.setHouses(hawbSummaryVOs);
		Collection<ShipmentDetailVO>shipmentDetailVOs = new ArrayList();
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVO);
		carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetailVOs);
		HashMap<String, Collection<OneTimeVO>> oneTimes= new HashMap();
		Collection<OneTimeVO> mailClassFieldValues= new ArrayList<>();
		OneTimeVO mailClassValue = new OneTimeVO();
		mailClassValue.setFieldValue("C");
		mailClassValue.setFieldDescription("Parcels");
		mailClassFieldValues.add(mailClassValue);
		oneTimes.put("mailtracking.defaults.mailclass", mailClassFieldValues);
		when(sharedDefaultsProxy.findOneTimeValues(any(String.class),anyCollectionOf(String.class))).thenReturn(oneTimes);
		spy.invoke(carditVO);
	}
}
