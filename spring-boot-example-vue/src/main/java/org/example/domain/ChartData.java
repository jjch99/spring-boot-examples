package org.example.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChartData implements Serializable {

    private List<String> columns;

    private List<Map<String, Object>> rows;

}
