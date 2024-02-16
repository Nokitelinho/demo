package com.ibsplc.neoicargo.tracking.dao.impl.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ibsplc.neoicargo.tracking.dao.entity.Milestone;

import java.util.List;

public interface MilestoneRepository extends CrudRepository<Milestone,Long> {
    List<Milestone> findAll();
}
