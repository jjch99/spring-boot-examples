package org.example.mybatis.dao;

import org.apache.ibatis.annotations.Mapper;
import org.example.mybatis.entity.Merchant;

@Mapper
public interface MerchantMapper {

    int deleteByPrimaryKey(Long id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    Merchant selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

}