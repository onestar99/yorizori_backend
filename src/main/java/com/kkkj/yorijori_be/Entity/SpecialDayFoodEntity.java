package com.kkkj.yorijori_be.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@ToString
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_day_food")
public class SpecialDayFoodEntity {

    @CreatedDate
    @Id
    @Column(name = "special_day", updatable = false)
    private LocalDate specialDay;

    @Column(name = "day_name")
    private String dayName;

    @Column(name = "day_food")
    private String dayFood;

}
