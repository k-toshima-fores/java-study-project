package com.example.demo.service;

import com.example.demo.entity.MDivision;
import com.example.demo.entity.TAccount;
import com.example.demo.mapper.mysql.DivisionMapper;
import com.example.demo.mapper.oracle.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class GreetingService {
  private final AccountMapper accountMapper;
  private final DivisionMapper divisionMapper;

  public TAccount findAccountById(Long id){
    return accountMapper.findById(id);
  }

  public MDivision findFiresDivision(){
    return divisionMapper.findFirst();
  }
}
