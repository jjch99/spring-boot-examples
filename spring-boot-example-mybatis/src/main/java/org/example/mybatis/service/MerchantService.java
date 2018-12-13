package org.example.mybatis.service;

import java.util.List;

import org.example.mybatis.dao.MerchantMapper;
import org.example.mybatis.entity.Merchant;
import org.example.mybatis.entity.MerchantExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

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

    public List<Merchant> list() {
        MerchantExample example = new MerchantExample();
        example.createCriteria().andStatusEqualTo(Byte.valueOf("2"));
        PageHelper.offsetPage(0, 20);
        return merchantMapper.selectByExample(example);
    }

}
