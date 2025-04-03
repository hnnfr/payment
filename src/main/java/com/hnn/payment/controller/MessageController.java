package com.hnn.payment.controller;

import com.hnn.payment.dto.MessageDto;
import com.hnn.payment.dto.MessageListDto;
import com.hnn.payment.mapper.MessageMapper;
import com.hnn.payment.model.MqMessage;
import com.hnn.payment.repository.MqMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MqMessageRepository messageRepository;
    private final MessageMapper messageMapper;

    public MessageController(MqMessageRepository messageRepository,
                             MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    @GetMapping
    public ResponseEntity<MessageListDto> getAllMessages(
            @PageableDefault(size = 20, sort = "receivedTimestamp") Pageable pageable) {

        Page<MqMessage> messagePage = messageRepository.findAll(pageable);
        Page<MessageDto> dtoPage = messagePage.map(messageMapper::messageToDto);

        return ResponseEntity.ok(MessageListDto.fromPage(dtoPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable Long id) {
        return messageRepository.findById(id)
                .map(messageMapper::messageToDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<MessageDto>> searchByContent(
            @RequestParam String content) {

        List<MessageDto> results = messageRepository
                .findByContentContainingIgnoreCase(content)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/correlation/{correlationId}")
    public ResponseEntity<List<MessageDto>> getByCorrelationId(
            @PathVariable String correlationId) {

        List<MessageDto> results = messageRepository
                .findByCorrelationId(correlationId)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<MessageDto>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<MessageDto> results = messageRepository
                .findByReceivedTimestampBetween(start, end)
                .stream()
                .map(messageMapper::messageToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getMessagesCount() {
        return ResponseEntity.ok(messageRepository.count());
    }
}