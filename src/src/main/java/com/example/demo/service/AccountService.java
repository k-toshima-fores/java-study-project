package com.example.demo.service;

import com.example.demo.controller.input.AccountListInputForm;
import com.example.demo.entity.TAccount;
import com.example.demo.mapper.mysql.DivisionMapper;
import com.example.demo.mapper.oracle.AccountMapper;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Transactional
public class AccountService {
  private final AccountMapper accountMapper;
  private final DivisionMapper divisionMapper;

  public List<TAccount> list(AccountListInputForm param){
    return null;
  }
}
