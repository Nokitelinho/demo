package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.mail.vo.MailArrivalFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
//TODO: FIle to be removed in java- after moving out the methods- deprecated entity
/** 
 * @author a-1303
 */
@Setter
@Getter
@Entity
@Table(name = "MTKINBFLT")
public class InboundFlight {
	private static final String MODULE_NAME = "mail.operations";
	@EmbeddedId
	@AttributeOverrides({ @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD  ")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")) })
	private InboundFlightPK inboundFlightPK;
	@Column(name = "FLTCARCOD")
	private String carrierCode;
	@Column(name = "FLTDAT")
	private LocalDateTime flightDate;
	@Column(name = "CLSFLG")
	private String closedFlag;
	/** 
	* @return Returns the flightStatus.
	*/
	public String getClosedFlag() {
		return closedFlag;
	}

	/** 
	* @return Returns the carrierCode.
	*/
	public String getCarrierCode() {
		return carrierCode;
	}

	/** 
	* @return Returns the flightDate.
	*/
	public LocalDateTime getFlightDate() {
		return flightDate;
	}

	/** 
	* @return Returns the inboundFlightPk.
	*/
	public InboundFlightPK getInboundFlightPK() {
		return inboundFlightPK;
	}

	/** 
	* @param operationalFlightVO
	* @return
	*/
	public static MailArrivalFilterVO constructMailArrivalFilterVO(OperationalFlightVO operationalFlightVO) {
		MailArrivalFilterVO filterVO = new MailArrivalFilterVO();
		filterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		filterVO.setCarrierId(operationalFlightVO.getCarrierId());
		filterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		filterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		filterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		filterVO.setFlightDate(operationalFlightVO.getFlightDate());
		filterVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		filterVO.setPou(operationalFlightVO.getPou());
		filterVO.setMailStatus(MailConstantsVO.MAIL_STATUS_ALL);
		return filterVO;
	}

	public void setFlightDate(ZonedDateTime flightDate) {
		if (Objects.nonNull(flightDate)) {
			this.flightDate = flightDate.toLocalDateTime();
		}
	}

}
