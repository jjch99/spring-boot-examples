package org.example.mybatis.service;

import org.example.mybatis.dao.MerchantMapper;
import org.example.mybatis.entity.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantService {

    @Autowired
    MerchantMapper merchantMapper;

    @Transactional
    public void add(Merchant merchant) {
        merchantMapper.insert(merchant);
    }

    @Transactional
    public Merchant info(Long id) {
        return merchantMapper.selectByPrimaryKey(id);
    }

}
