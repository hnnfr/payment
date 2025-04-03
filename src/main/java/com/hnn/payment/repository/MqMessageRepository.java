package com.hnn.payment.repository;

import com.hnn.payment.model.MqMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MqMessageRepository extends JpaRepository<MqMessage, Long> {
}
