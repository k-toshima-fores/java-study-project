package com.example.demo.mapper.oracle;

import com.example.demo.entity.TAccount;
import com.example.demo.mapper.oracle.input.account.AccountCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AccountMapper {
  @Select("select * from t_account where id = #{id}")
  TAccount findById(@Param("id") Long id);

  @Select("select * from t_account where name like ")
  List<TAccount> findByQuery(AccountCriteria criteria);
}
