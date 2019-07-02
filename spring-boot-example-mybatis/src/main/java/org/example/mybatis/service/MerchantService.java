package org.example.mybatis.service;

import java.util.List;

import javax.annotation.Resource;

import org.example.mybatis.dao.MerchantMapper;
import org.example.mybatis.entity.Merchant;
import org.example.mybatis.entity.MerchantExample;
import org.example.mybatis.entity.MerchantStatusCount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

@Service
public class MerchantService {

    @Resource
    private MerchantMapper merchantMapper;

    @Transactional
    public void add(Merchant merchant) {
        merchantMapper.insert(merchant);
    }

    public Merchant info(Long id) {
        return merchantMapper.selectByPrimaryKey(id);
    }

    public List<Merchant> list() {
        MerchantExample example = new MerchantExample();
        MerchantExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Byte.valueOf("2"))
                .andIsDelEqualTo(Byte.valueOf("0"));
        example.setOrderByClause("mer_name");
        PageHelper.offsetPage(0, 20);
        return merchantMapper.selectByExample(example);
    }

    public List<MerchantStatusCount> count() {
        return merchantMapper.selectStatusCount();
    }

}
