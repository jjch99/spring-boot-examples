package org.example.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.example.mybatis.entity.Merchant;
import org.example.mybatis.entity.MerchantExample;
import org.example.mybatis.entity.MerchantStatusCount;

public interface MerchantMapper {
    int countByExample(MerchantExample example);

    int deleteByExample(MerchantExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Merchant record);

    int insertSelective(Merchant record);

    List<Merchant> selectByExample(MerchantExample example);

    Merchant selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Merchant record, @Param("example") MerchantExample example);

    int updateByExample(@Param("record") Merchant record, @Param("example") MerchantExample example);

    int updateByPrimaryKeySelective(Merchant record);

    int updateByPrimaryKey(Merchant record);

    /**
     * 查询各状态的商户数量
     * 
     * @return
     */
    List<MerchantStatusCount> selectStatusCount();
}