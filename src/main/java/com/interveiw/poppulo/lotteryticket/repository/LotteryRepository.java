package com.interveiw.poppulo.lotteryticket.repository;

import com.interveiw.poppulo.lotteryticket.data.model.LotteryTicket;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LotteryRepository extends CassandraRepository<LotteryTicket, UUID> {

    Optional<List<LotteryTicket>> findByIsStatusChecked(Boolean isStatusChecked);
}
