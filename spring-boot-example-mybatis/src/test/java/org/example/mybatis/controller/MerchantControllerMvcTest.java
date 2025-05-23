package org.example.mybatis.controller;

import org.example.mybatis.entity.Merchant;
import org.example.mybatis.service.MerchantService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Auto-configured Spring MVC tests
 * https://docs.spring.io/spring-boot/3.5/reference/testing/spring-boot-applications.html#testing.spring-boot-applications.spring-mvc-tests
 */
@RunWith(SpringRunner.class)
@WebMvcTest(MerchantController.class)
public class MerchantControllerMvcTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MerchantService merchantService;

    @Test
    public void testInfo() throws Exception {
        Merchant merchant = new Merchant();
        BDDMockito.given(merchantService.info(100L)).willReturn(merchant);
        this.mvc.perform(MockMvcRequestBuilders.get("/merchant/info").param("id", "1")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

}
