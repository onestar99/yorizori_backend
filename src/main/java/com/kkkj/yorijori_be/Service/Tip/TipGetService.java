package com.kkkj.yorijori_be.Service.Tip;

import com.kkkj.yorijori_be.Entity.Tip.TipEntity;
import com.kkkj.yorijori_be.Repository.Tip.TipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TipGetService {

    private final TipRepository tipRepository;

    public Page<TipEntity> getTipPaging(int pageNo, int pageSize, String sortBy){

        // 페이지 인스턴스 생성
//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return tipRepository.findAll(pageable);

    }
}
