package org.tobiaszpietryga.charter.dto;

import java.util.Date;
import java.util.Objects;

import org.tobiaszpietryga.charter.model.Transaction;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardDto {
    int firstMonthPoints;
    int secondMonthPoints;
    int thirdMonthPoints;
    int totalPoints;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        RewardDto rewardDto = (RewardDto) o;
        return firstMonthPoints == rewardDto.firstMonthPoints &&
                secondMonthPoints == rewardDto.secondMonthPoints &&
                thirdMonthPoints == rewardDto.thirdMonthPoints &&
                totalPoints == rewardDto.totalPoints;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstMonthPoints, secondMonthPoints, thirdMonthPoints, totalPoints);
    }
}
