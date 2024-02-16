package com.ibsplc.neoicargo.stock.mapper.common;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;

/**
 * A parent generic interface that provides common abstract methods for converting between Entity
 * and VO objects
 *
 * @param <E> Entity class object
 * @param <V> VO class object
 */
public interface EntityAbstractMapper<E extends BaseEntity, V extends AbstractVO> {

  E mapVoToEntity(V vo);

  V mapEntityToVo(E entity);
}
