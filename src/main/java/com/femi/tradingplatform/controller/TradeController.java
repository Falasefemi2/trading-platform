package com.femi.tradingplatform.controller;

import com.femi.tradingplatform.dto.TradeRequest;
import com.femi.tradingplatform.dto.TradeResponse;
import com.femi.tradingplatform.service.TradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @GetMapping
    public ResponseEntity<List<TradeResponse>> getAllTrades() {
        return ResponseEntity.ok(tradeService.getAllTrades());
    }

    @PostMapping
    public ResponseEntity<TradeResponse> saveTrade(@Valid @RequestBody TradeRequest request) {
        TradeResponse savedTrade = tradeService.saveTrade(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTrade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TradeResponse> updateTrade(
            @PathVariable Long id,
            @Valid @RequestBody TradeRequest request) {
        TradeResponse updatedTrade = tradeService.updateTrade(id, request);
        return ResponseEntity.ok(updatedTrade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }
}
