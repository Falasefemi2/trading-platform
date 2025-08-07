package com.femi.tradingplatform.service;

import com.femi.tradingplatform.dto.TradeRequest;
import com.femi.tradingplatform.dto.TradeResponse;
import com.femi.tradingplatform.exception.TradeNotFoundException;
import com.femi.tradingplatform.model.Trade;
import com.femi.tradingplatform.repository.TradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<TradeResponse> getAllTrades() {
        return tradeRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public TradeResponse saveTrade(TradeRequest request) {
        Trade trade = Trade.builder()
                .symbol(request.getSymbol())
                .quantity(request.getQuantity())
                .build();

        Trade saved = tradeRepository.save(trade);
        return mapToResponse(saved);
    }

    @Transactional
    public TradeResponse updateTrade(Long id, TradeRequest request) {
        Trade trade = tradeRepository.findById(id)
                .orElseThrow(() -> new TradeNotFoundException(id));

        trade.setSymbol(request.getSymbol());
        trade.setQuantity(request.getQuantity());

        Trade updated = tradeRepository.save(trade);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteTrade(Long id) {
        if (!tradeRepository.existsById(id)) {
            throw new TradeNotFoundException(id);
        }
        tradeRepository.deleteById(id);
    }

    private TradeResponse mapToResponse(Trade trade) {
        return TradeResponse.builder()
                .id(trade.getId())
                .symbol(trade.getSymbol())
                .quantity(trade.getQuantity())
                .createdAt(trade.getCreatedAt() != null ? trade.getCreatedAt().format(formatter) : null)
                .updatedAt(trade.getUpdatedAt() != null ? trade.getUpdatedAt().format(formatter) : null)
                .build();
    }
}
