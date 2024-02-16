package com.ibsplc.icargo.business.uld.defaults.event.mapper;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.junit.Test;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class ULDDemurrageChannelMapperTest extends AbstractFeatureTest {

	private ULDDemurrageChannelMapper uldDemurrageChannelMapper;
	private TransactionVO transactionVO;
	ULDTransactionDetailsVO uldTransactionDetailsVO;
	Collection<ULDTransactionDetailsVO> transactionDetailsVOs;

	@Override
	public void setup() throws Exception {
		uldDemurrageChannelMapper = (ULDDemurrageChannelMapper) ICargoSproutAdapter
				.getBean(ULDDemurrageChannelMapper.class);
		transactionVO = new TransactionVO();
		 transactionDetailsVOs = new ArrayList<>();
		 uldTransactionDetailsVO= new ULDTransactionDetailsVO();
		
	}
	@Test
	public void shouldMapToTransactionVO() throws SystemException{
		ULDTransactionDetailsVO uldTrnDetailsVO = new ULDTransactionDetailsVO();
		uldTransactionDetailsVO.setAgreementNumber("AGN1");
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		BeanHelper.copyProperties(uldTrnDetailsVO,uldTransactionDetailsVO);
		uldDemurrageChannelMapper.mapToTransactionVO(transactionVO);
		assertTrue(Objects.equals("AGN1", uldTrnDetailsVO.getAgreementNumber()));
	}
	@Test
	public void shouldNotPopulateWhenMappingIsNotDone(){
		ULDTransactionDetailsVO uldTrnDetailsVO = new ULDTransactionDetailsVO();
		uldTransactionDetailsVO.setAgreementNumber("AGN1");
		transactionDetailsVOs.add(uldTransactionDetailsVO);
		transactionVO.setUldTransactionDetailsVOs(transactionDetailsVOs);
		uldDemurrageChannelMapper.mapToTransactionVO(transactionVO);
		assertTrue(!Objects.equals("AGN1", uldTrnDetailsVO.getAgreementNumber()));
	}

}
