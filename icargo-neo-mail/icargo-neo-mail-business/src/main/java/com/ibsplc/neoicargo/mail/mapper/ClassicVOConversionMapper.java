package com.ibsplc.neoicargo.mail.mapper;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.currency.helper.EBLMoneyMapper;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.ResditEventVO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL, uses = {
		MeasureMapper.class, LocalDateMapper.class, EBLMoneyMapper.class })
public interface ClassicVOConversionMapper {

	//To be moved to another class-Method to be deprecated after Neo messagebroker integration
	Collection<ResditEventVO> copyResditEventVO_classic(
			Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> resditEventVOs);
	Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> copyResditEvent_classic(
			Collection<ResditEventVO> resditEventVOs);
	FlightValidationVO copyFlightValidationVO(FlightValidationVO flightValidationVO);
	Collection<MailbagVO> copyMailBagVOs_classic(
			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> classicMailbagVO);
}
