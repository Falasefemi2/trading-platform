package com.femi.tradingplatform.service;

import com.femi.tradingplatform.dto.TradeRequest;
import com.femi.tradingplatform.dto.TradeResponse;
import com.femi.tradingplatform.exception.TradeNotFoundException;
import com.femi.tradingplatform.model.Trade;
import com.femi.tradingplatform.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TradeServiceTest {

    @Mock
    TradeRepository tradeRepository;

    @InjectMocks
    TradeService tradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTrades_ShouldReturnListOfTradeResponses() {
        Trade trade1 = Trade.builder()
                .id(1L)
                .symbol("AAPL")
                .quantity(100)
                .createdAt(LocalDateTime.now())
                .build();

        Trade trade2 = Trade.builder()
                .id(2L)
                .symbol("SMSNG")
                .quantity(300)
                .createdAt(LocalDateTime.now())
                .build();

        when(tradeRepository.findAll()).thenReturn(Arrays.asList(trade1, trade2));

        List<TradeResponse> result = tradeService.getAllTrades();

        assertEquals(2, result.size());
        assertEquals("AAPL", result.get(0).getSymbol());
        assertEquals("SMSNG", result.get(1).getSymbol());

        verify(tradeRepository).findAll();
    }

    @Test
    void saveTrade_ShouldReturnTradeResponse() {
        TradeRequest request = new TradeRequest();
        request.setSymbol("APPLE");
        request.setQuantity(20);

        Trade savedTrade = Trade.builder()
                .id(1L)
                .symbol("APPLE")
                .quantity(20)
                .createdAt(LocalDateTime.now())
                .build();

        when(tradeRepository.save(any(Trade.class))).thenReturn(savedTrade);

        TradeResponse result = tradeService.saveTrade(request);

        assertNotNull(result);
        assertEquals("APPLE", result.getSymbol());
        assertEquals(20, result.getQuantity());
    }

    @Test
    void updateTrade_ShouldReturnUpdatedTradeResponse() {
        Long id = 1L;
        Trade existingTrade = Trade.builder()
                .id(id)
                .symbol("APPLE")
                .quantity(20)
                .createdAt(LocalDateTime.now())
                .build();

        TradeRequest updateRequest = new TradeRequest();
        updateRequest.setSymbol("apple");
        updateRequest.setQuantity(30);

        when(tradeRepository.findById(id)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(existingTrade)).thenReturn(existingTrade);

        TradeResponse result = tradeService.updateTrade(id, updateRequest);

        assertEquals("apple", result.getSymbol());
        assertEquals(30, result.getQuantity());
        verify(tradeRepository).findById(id);
        verify(tradeRepository).save(existingTrade);
    }

    @Test
    void updateTrade_ShouldThrowWhenTradeNotFound() {
        Long id = 1L;
        TradeRequest updateRequest = new TradeRequest();
        updateRequest.setSymbol("apple");
        updateRequest.setQuantity(30);

        when(tradeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(TradeNotFoundException.class, () -> tradeService.updateTrade(id, updateRequest));
    }

    @Test
    void deleteTrade_ShouldDeleteWhenExists() {
        Long id = 1L;
        when(tradeRepository.existsById(id)).thenReturn(true);

        tradeService.deleteTrade(id);

        verify(tradeRepository).deleteById(id);
    }

    @Test
    void deleteTrade_ShouldThrowWhenNotExists() {
        Long id = 1L;
        when(tradeRepository.existsById(id)).thenReturn(false);

        assertThrows(TradeNotFoundException.class, () -> tradeService.deleteTrade(id));
        verify(tradeRepository, never()).deleteById(id);
    }
}
