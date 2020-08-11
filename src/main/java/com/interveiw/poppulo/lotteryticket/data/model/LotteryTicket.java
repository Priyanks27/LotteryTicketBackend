package com.interveiw.poppulo.lotteryticket.data.model;

import com.interveiw.poppulo.lotteryticket.Validations.Number;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table
@AllArgsConstructor
@ToString
@Builder
public class LotteryTicket {

    @PrimaryKey
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

    @NotNull
    @NotEmpty(message = "")
    @Number
    private List<Line> lines;

    @NotNull
    @NotEmpty
    @Indexed
    private Boolean isStatusChecked; // cannot amend ticket if this is true

    public LotteryTicket() {
        this.id = UUID.randomUUID();
        this.lines = new ArrayList<>();
        this.isStatusChecked = false;
    }
}
