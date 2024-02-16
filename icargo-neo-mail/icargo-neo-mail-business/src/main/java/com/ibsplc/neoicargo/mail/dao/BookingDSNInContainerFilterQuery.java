package com.ibsplc.neoicargo.mail.dao;

import com.ibsplc.neoicargo.mail.vo.ContainerVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.sql.NativeQuery;

import java.util.Collection;

/** 
 * @author A-3227 RENO K ABRAHAM
 */
public class BookingDSNInContainerFilterQuery extends NativeQuery {
	private String baseQuery;
	private Collection<ContainerVO> containerVOs;
	private static final String HYPHEN = "-";

	public BookingDSNInContainerFilterQuery(Collection<ContainerVO> containerVOs, String baseQuery) {
		super(PersistenceController.getEntityManager().currentSession());
		this.baseQuery = baseQuery;
		this.containerVOs = containerVOs;
	}

	@Override
	public String getNativeQuery() {
		StringBuilder builder = new StringBuilder(baseQuery);
		int index = 0;
		String key;
		builder.append(" WHERE ")
				.append(" MST.CMPCOD || '-' || MST.CONNUM || '-' || ")
				.append(" MST.FLTCARIDR || '-' || MST.FLTNUM || '-' || " )
				.append(" MST.FLTSEQNUM || '-' || MST.SEGSERNUM IN ( ");
		boolean isFirstEntry = true;
		for(ContainerVO containerVO : containerVOs){
			key = new StringBuilder()
					.append(containerVO.getCompanyCode()).append(HYPHEN)
					.append(containerVO.getContainerNumber()).append(HYPHEN)
					.append(containerVO.getCarrierId()).append(HYPHEN)
					.append(containerVO.getFlightNumber()).append(HYPHEN)
					.append(containerVO.getFlightSequenceNumber()).append(HYPHEN)
					.append(containerVO.getSegmentSerialNumber()).toString();
			if(isFirstEntry){
				builder.append(" ? ");
				isFirstEntry = false;
			} else{
				builder.append(", ? ");
			}

			this.setParameter(++index, key);
		}
		builder.append(" ) ");
		return builder.toString();
	}
}
