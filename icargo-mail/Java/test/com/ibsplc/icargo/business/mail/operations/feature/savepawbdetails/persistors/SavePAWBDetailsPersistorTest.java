package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.persistors;

 
import static org.mockito.Mockito.doReturn;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.ConsignmentDocument;
import com.ibsplc.icargo.business.mail.operations.ConsignmentDocumentPK;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

public class SavePAWBDetailsPersistorTest extends AbstractFeatureTest{
	

	private SavePAWBDetailsPersistor savePAWBDetailsPersistor;
	private CarditPawbDetailsVO carditPawbDetailsVO;
	private Mailbag mailbagBean;
	private ConsignmentDocument consignmentDocumentEntity;
	private static final String MAILID="FRCDGASGSINAACN24034005000972";

	@Override
	public void setup() throws Exception {
		carditPawbDetailsVO=setUpCarditPawbDetailsVO();
		EntityManagerMock.mockEntityManager();
		mailbagBean = mockBean("MailbagEntity", Mailbag.class);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentNumber("CDG054");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setConsignmentSequenceNumber(2);
		consignmentDocumentEntity = new ConsignmentDocument(consignmentDocumentVO);
		savePAWBDetailsPersistor=spy(new SavePAWBDetailsPersistor());	
		
	}
	
	private CarditPawbDetailsVO setUpCarditPawbDetailsVO() {
		
		
		CarditPawbDetailsVO carditPawbDetailsVo=new CarditPawbDetailsVO();
		carditPawbDetailsVo.setConsigneeCode("USA");
		carditPawbDetailsVo.setShipmentPrefix("134");
		carditPawbDetailsVo.setMasterDocumentNumber("12345678");
		carditPawbDetailsVo.setConsignmentDestination("FRPART");
		carditPawbDetailsVo.setConsignmentOrigin("AEDXBT");
		carditPawbDetailsVo.setShipperCode("JPA");
		
		ShipmentValidationVO shipmentValidationVo=new ShipmentValidationVO();
		shipmentValidationVo.setShipmentPrefix("134");
		shipmentValidationVo.setMasterDocumentNumber("12345678");
		shipmentValidationVo.setOwnerId(1134);
		shipmentValidationVo.setDuplicateNumber(1);
		shipmentValidationVo.setSequenceNumber(1);
		shipmentValidationVo.setCompanyCode("IBS");
		shipmentValidationVo.setStationCode("CDG");
		
		Collection<MailInConsignmentVO> mailInConsigmentVOs=new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO=new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId(MAILID);
		mailInConsignmentVO.setMailSequenceNumber(3691);
		mailInConsigmentVOs.add(mailInConsignmentVO);
		
		carditPawbDetailsVo.setShipmentValidationVO(shipmentValidationVo);
		carditPawbDetailsVo.setMailInConsignmentVOs(mailInConsigmentVOs);
		
		return carditPawbDetailsVo;
	}
	
	@Test
	public void shouldPersistMailBag() throws SystemException, FinderException{
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr(MAILID);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(3691);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentNumber("CDG054");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setConsignmentSequenceNumber(2);
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocument.setAirportCode("FRA");
		doReturn(consignmentDocumentEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class), any(ConsignmentDocumentPK.class));
		consignmentDocument.update(consignmentDocumentVO);
		savePAWBDetailsPersistor.persist(carditPawbDetailsVO);
		
	} 
	
	@Test
	public void shouldNotPersistMailBagShipmentValidationVOIsNull() throws SystemException {
		carditPawbDetailsVO.setShipmentValidationVO(null);
		savePAWBDetailsPersistor.persist(carditPawbDetailsVO);
		
	} 
	
	@Test
	public void shouldNotPersistMailBagMailBagsIsEmpty() throws SystemException {
		carditPawbDetailsVO.setMailInConsignmentVOs(new ArrayList<>());
		savePAWBDetailsPersistor.persist(carditPawbDetailsVO);
		
	} 
	
	@Test
	public void shouldThrowFinderException() throws FinderException, SystemException{
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr(MAILID);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(3691);
		mailbag.setMailbagPK(mailbagPK);
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		savePAWBDetailsPersistor.persist(carditPawbDetailsVO);
		
	} 
	@Test
	public void updateConsignmentDocumentThrowSystemException() throws SystemException, FinderException {
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr(MAILID);
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(3691);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentNumber("CDG054");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setConsignmentSequenceNumber(2);
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocument.setAirportCode("FRA");
		SystemException systemException = new SystemException("system exception");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class), any(ConsignmentDocumentPK.class));
		savePAWBDetailsPersistor.persist(carditPawbDetailsVO);
	} 

}
