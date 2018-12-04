package br.com.uece.frameworks.stockfy.util.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create by Bruno Barbosa - 03/12/2018
 */
@Getter
@Setter
public class ChartVO {

    private List<String> labels;
    private List<DataChartVO> datasets;
}
