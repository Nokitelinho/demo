package com.ibsplc.neoicargo.stock.mapper.common;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/**
 * A parent generic interface that provides common abstract methods for converting between Model, VO
 * and Filter objects
 *
 * @param <M> Model class object
 * @param <V> VO class object
 */
public interface FilterAbstractMapper<M extends BaseModel, V extends AbstractVO, F> {

  F mapModelToFilter(M model);

  F mapVoToFilter(V vo);
}
