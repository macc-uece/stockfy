package br.com.uece.frameworks.stockfy.service;

import br.com.uece.frameworks.stockfy.util.chart.ChartVO;
import br.com.uece.frameworks.stockfy.util.chart.DataChartVO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Create by Bruno Barbosa - 03/12/2018
 */
@RestController
@RequestMapping("/api/home")
public class HomeService {

    @RequestMapping(value = "/venda-mes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ChartVO vendaMes() {
        ChartVO chartVO = new ChartVO();
        List<String> labels = new LinkedList<>();
        labels.add("1");
        labels.add("2");
        labels.add("3");
        labels.add("4");
        labels.add("5");
        chartVO.setLabels(labels);
        DataChartVO dataChartVO = new DataChartVO();
        dataChartVO.setLabel("titulo teste");
        List<Double> dados = new LinkedList<>();
        dados.add(Double.parseDouble("210.0"));
        dados.add(Double.parseDouble("215.0"));
        dados.add(Double.parseDouble("216.0"));
        dados.add(Double.parseDouble("217.0"));
        dados.add(Double.parseDouble("220.0"));
        dataChartVO.setData(dados);
        chartVO.setDatasets(Collections.singletonList(dataChartVO));
        return chartVO;
    }

    @RequestMapping("/despesa-mes")
    public Map<String, String> despesaMes() {
        return null;
    }

    @RequestMapping("/venda-despesa")
    public Map<String, String> vendaDespesa() {
        return null;
    }

    @RequestMapping("/produto-estabelecimento")
    public Map<String, String> produtoEstabelecimento() {
        return null;
    }

    @RequestMapping("/categoria-venda")
    public Map<String, String> categoriaVenda() {
        return null;
    }
}
