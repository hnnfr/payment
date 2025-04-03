package com.hnn.payment.mapper;

import com.hnn.payment.dto.MessageDto;
import com.hnn.payment.model.MqMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto messageToDto(MqMessage message);
}