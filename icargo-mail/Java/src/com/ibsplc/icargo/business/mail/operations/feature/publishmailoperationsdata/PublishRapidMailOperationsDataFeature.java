package com.ibsplc.icargo.business.mail.operations.feature.publishmailoperationsdata;


import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailOperationDataFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.PublishRapidMailOperationsDataFeatureVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateConfigVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateFilterVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.publishRapidMailOperationsDataFeature")
@Feature(exception = BusinessException.class)
public class PublishRapidMailOperationsDataFeature extends AbstractFeature<PublishRapidMailOperationsDataFeatureVO>{
	
	private static final Log LOGGER = LogFactory.getLogger(PublishRapidMailOperationsDataFeature.class.getCanonicalName());
	private static final String MTK_MAL_RAPID_RPT="MTKMALRAPIDRPT";
	private static final String FRMDAT = "FRMDAT";
	private static final String TOODAT = "TOODAT";
	private static final String TRIGGER_POINTS = "TRGPNT";
	private static final String TOLERANCE = "TOLERANCE";
	private static final String POACOD = "POACOD";
	private static final String CARRIER_CODE = "CARCOD";
	private static final String ORG_OF_MAL_BAGS = "ORGMALBAG";

	@SuppressWarnings("unchecked")
	@Override 
	protected Void perform(PublishRapidMailOperationsDataFeatureVO publishRapidMailOperationsDataFeatureVO)throws SystemException, BusinessException {
		LOGGER.entering(this.getClass().getSimpleName(), "publishIRawShipmnentsinCsv");
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		Collection<FileGenerateFilterVO>screenFilters=getScreenFilters(publishRapidMailOperationsDataFeatureVO.getMailOperationDataFilterVO(),logonAttributes);
		FileGenerateVO fileGenerateVO=new FileGenerateVO();
		FileGenerateConfigVO fileGenerateConfigVO=new FileGenerateConfigVO();
		fileGenerateConfigVO.setScreenFilters(screenFilters);
		fileGenerateConfigVO.setCompanyCode(publishRapidMailOperationsDataFeatureVO.getMailOperationDataFilterVO().getCompanyCode());
		fileGenerateConfigVO.setFileType(MTK_MAL_RAPID_RPT);
		fileGenerateConfigVO.setGeneratorType("CSV-generator");
		LocalDate uploadTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		fileGenerateVO.setStatus("I");
		fileGenerateVO.setAppliedConfiguration(fileGenerateConfigVO);
		fileGenerateVO.setFileType(fileGenerateConfigVO.getFileType());
		fileGenerateVO.setCompanyCode(publishRapidMailOperationsDataFeatureVO.getMailOperationDataFilterVO().getCompanyCode());
		fileGenerateVO.setStationCode(logonAttributes.getStationCode());
		fileGenerateVO.setReconstructConfig(true);
		fileGenerateVO.setUploadStartTime(uploadTime);
		fileGenerateVO.setUploadEndTime(uploadTime);
		fileGenerateVO.setLastUpdatedTime(uploadTime);
		fileGenerateVO.setLastUpdatedUser(logonAttributes.getUserId());
		doGenerate(fileGenerateVO);
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		return null;
   
		 

	}
	
	private Collection<FileGenerateFilterVO> getScreenFilters(MailOperationDataFilterVO mailOperationDataFilterVO,LogonAttributes logonAttributes){
		Collection<FileGenerateFilterVO>screenFilters=new ArrayList<>();
		LocalDate currentDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP, true);
		FileGenerateFilterVO filter=getFileGenerateFilterVO(FRMDAT,(currentDate.addDays(mailOperationDataFilterVO.getNoOfDaysToConsider()*-1)).toDisplayFormat());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(TOODAT,currentDate.toDisplayFormat());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(TRIGGER_POINTS,mailOperationDataFilterVO.getTriggerPoints());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(TOLERANCE,mailOperationDataFilterVO.getTolerance().toString());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(POACOD,mailOperationDataFilterVO.getPostalAuthorityCode());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(CARRIER_CODE,mailOperationDataFilterVO.getCarrierCode());
		screenFilters.add(filter);
		filter = getFileGenerateFilterVO(ORG_OF_MAL_BAGS,mailOperationDataFilterVO.getMailbagOrigin());
		screenFilters.add(filter);
		return screenFilters;
	}
	private  FileGenerateFilterVO getFileGenerateFilterVO(String filterCode,String filterValue){
		FileGenerateFilterVO fileGenerateFilterVO=new FileGenerateFilterVO();
		fileGenerateFilterVO.setFilterCode(filterCode);
		fileGenerateFilterVO.setFilterValue(filterValue);
		return fileGenerateFilterVO;
	}

	private void doGenerate(FileGenerateVO fileGenVO) throws SystemException {
			Proxy.getInstance().get(SharedDefaultsProxy.class).doGenerate(fileGenVO);
	}

	@Override
	protected FeatureConfigVO fetchFeatureConfig(PublishRapidMailOperationsDataFeatureVO publishRapidMailOperationsDataFeatureVO) {
		return new  FeatureConfigVO();
	}


}