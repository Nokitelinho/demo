package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

/** 
 * @author A-1883
 */
@Setter
@Getter
@Slf4j
@IdClass(ResditEventPK.class)
@Table(name = "MALRDTEVT")
@Entity
public class ResditEvent extends BaseEntity implements Serializable {

	@Id
	@Transient
	private String companyCode;
	@Id
	@Column(name = "CSGDOCNUM")
	private String consignmentDocumentNumber;
	@Id
	@Column(name = "EVTCOD")
	private String eventCode;
	@Id
	@Column(name = "EVTPRT")
	private String eventPort;
	@Id
	@Column(name = "MSGSEQNUM")
	private long messageSequenceNumber;

	@Column(name = "RDTREDTIM")
	private String uniqueIdForResdit;
	@Column(name = "POACOD")
	private String paCode;

	/**
	* @param resditEventPk
	* @return
	* @throws SystemException
	* @throws FinderException
	*/
	public static ResditEvent find(ResditEventPK resditEventPk) throws FinderException {
		return PersistenceController.getEntityManager().find(ResditEvent.class, resditEventPk);
	}

	/** 
	* @throws SystemException
	*/
	public void remove() {
		log.debug("ResditEvent" + " : " + "remove" + " Entering");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception.getMessage(), exception);
		}
		log.debug("ResditEvent" + " : " + "remove" + " Exiting");
	}
}
