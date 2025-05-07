package org.example.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.example.domain.BaseResponse;
import org.example.domain.ChartData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/v-charts-demo")
    public BaseResponse<ChartData> chartsDemo() {

        ChartData chartData = new ChartData();

        List columns = Lists.newArrayList("日期", "A", "B");
        chartData.setColumns(columns);

        List rows = Lists.newArrayList();
        chartData.setRows(rows);

        for (int i = 1; i < 10; i++) {
            Map row = ImmutableMap.builder()
                    .put(columns.get(0), String.format("2月%d日", i))
                    .put(columns.get(1), 1000 + RandomUtils.nextInt(1000))
                    .put(columns.get(2), 1200 + RandomUtils.nextInt(1000))
                    .build();
            rows.add(row);
        }

        return new BaseResponse<>(chartData);
    }

}
