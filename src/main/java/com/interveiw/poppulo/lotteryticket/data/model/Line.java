package com.interveiw.poppulo.lotteryticket.data.model;

import com.interveiw.poppulo.lotteryticket.Validations.Number;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@ToString
@UserDefinedType
public class Line {

    @Number
    @NotEmpty(message = "numbers cannot be empty")
    @NotNull
    private String numbers;

    @NotEmpty(message = "result cannot be empty, initially result is set to -1")
    @NotNull
    private Integer result;

    public Line() {
        // This is default value
        this.result = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Line)) return false;
        Line line = (Line) o;
        return getNumbers().equals(line.getNumbers()) &&
                getResult().equals(line.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumbers(), getResult());
    }
}
