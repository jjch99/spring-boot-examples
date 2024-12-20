package org.example.mybatis.service;

import java.util.Random;

import org.example.mybatis.dao.MerchantMapper;
import org.example.mybatis.entity.Merchant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
// import org.mockito.runners.MockitoJUnitRunner;

// @RunWith(MockitoJUnitRunner.class)
public class MerchantServiceTest {

    @Mock
    MerchantMapper merchantMapper;

    @InjectMocks
    MerchantService merchantService;

    @Before
    public void setUp() {
        // 如果只是简单的Mock和InjectMocks，可以直接在类上加注解 @RunWith(MockitoJUnitRunner.class)
        // 如果有更复杂的初始化准备过程，可以通过 @Before+初始化方法 来准备
        // MockitoAnnotations.initMocks(this);
        // ...
    }

    @Test
    public void info() {
        Long id = new Random().nextLong();

        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setMerId("100020003000400");
        merchant.setAddress("Beijing");
        Mockito.when(merchantMapper.selectByPrimaryKey(Mockito.anyLong())).thenReturn(merchant);

        Merchant mer = merchantService.info(id);
        Assert.assertEquals(merchant.getMerId(), mer.getMerId());
    }

}
