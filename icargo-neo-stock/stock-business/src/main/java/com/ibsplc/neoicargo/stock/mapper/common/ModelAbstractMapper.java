package com.ibsplc.neoicargo.stock.mapper.common;

import com.ibsplc.neoicargo.common.modal.BaseModel;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/**
 * A parent generic interface that provides common abstract methods for converting between Model and
 * VO objects
 *
 * @param <M> Model class object
 * @param <V> VO class object
 */
public interface ModelAbstractMapper<M extends BaseModel, V extends AbstractVO> {

  M mapVoToModel(V vo);

  V mapModelToVo(M model);
}
