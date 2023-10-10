package com.kkkj.yorijori_be.Repository;

import com.kkkj.yorijori_be.Entity.SpecialDayFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface SpecialDayFoodRepository extends JpaRepository<SpecialDayFoodEntity, LocalDate> {
}
