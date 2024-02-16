package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.BlackListStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListStockRepository extends JpaRepository<BlackListStock, Long> {}
