package com.example.demo.mapper.mysql;

import com.example.demo.entity.MDivision;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DivisionMapper {

  @Select("select * from m_division limit 1")
  MDivision findFirst();
}
