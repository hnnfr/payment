package com.hnn.payment.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class MessageListDto {
    private List<MessageDto> messages;
    private int currentPage;
    private long totalItems;
    private int totalPages;

    public static MessageListDto fromPage(Page<MessageDto> page) {
        MessageListDto dto = new MessageListDto();
        dto.setMessages(page.getContent());
        dto.setCurrentPage(page.getNumber());
        dto.setTotalItems(page.getTotalElements());
        dto.setTotalPages(page.getTotalPages());
        return dto;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
