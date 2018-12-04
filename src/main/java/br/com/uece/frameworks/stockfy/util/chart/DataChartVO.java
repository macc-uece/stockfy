package br.com.uece.frameworks.stockfy.util.chart;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create by Bruno Barbosa - 03/12/2018
 */
@Getter
@Setter
public class DataChartVO {

    private String label;
    private List<Double> data;
}
