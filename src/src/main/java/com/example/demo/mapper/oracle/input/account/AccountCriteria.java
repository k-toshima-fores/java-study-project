package com.example.demo.mapper.oracle.input.account;

import com.example.demo.mapper.oracle.input.Criteria;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountCriteria extends Criteria {
  private String name;
}
