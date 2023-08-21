package com.kkkj.yorijori_be.Repository.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipDetailEntity;
import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipDetailRepository extends JpaRepository<TipDetailEntity, String> {

}
