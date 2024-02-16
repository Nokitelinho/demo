package com.ibsplc.neoicargo.stock.dao.repository;

import com.ibsplc.neoicargo.stock.dao.entity.StockAgent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockAgentRepository extends JpaRepository<StockAgent, Long> {

  Optional<StockAgent> findByCompanyCodeAndAgentCode(String companyCode, String agentCode);
}
