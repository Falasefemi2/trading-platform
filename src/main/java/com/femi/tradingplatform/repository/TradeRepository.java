package com.femi.tradingplatform.repository;

import com.femi.tradingplatform.model.Trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    // Find trades by exact symbol
    List<Trade> findBySymbolIgnoreCase(String symbol);

    // Find trades for a symbol with quantity greater than a value
    List<Trade> findBySymbolIgnoreCaseAndQuantityGreaterThan(String symbol, int quantity);

    // Paginated query for trades
    Page<Trade> findAllBySymbolIgnoreCase(String symbol, Pageable pageable);

    // Custom JPQL query (example: partial match)
    @Query("SELECT t FROM Trade t WHERE LOWER(t.symbol) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Trade> searchBySymbolKeyword(String keyword);

    // Optional wrapper for safety
    Optional<Trade> findFirstBySymbolIgnoreCase(String symbol);
}
