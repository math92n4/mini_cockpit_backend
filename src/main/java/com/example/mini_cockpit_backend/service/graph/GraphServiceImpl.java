package com.example.mini_cockpit_backend.service.graph;

import com.example.mini_cockpit_backend.dto.graphs.GraphData;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrModel;
import com.example.mini_cockpit_backend.dto.graphs.SalesPrMonth;
import com.example.mini_cockpit_backend.service.model.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GraphServiceImpl implements GraphService{

    private final JdbcTemplate jdbcTemplate;
    private final ModelService modelService;

    @Override
    public List<SalesPrModel> getSalesPrModel() {

        String sql = "SELECT model.model_description, COUNT(*) as count FROM ivsr " +
                "INNER JOIN model ON model.id = ivsr.model_id " +
                "GROUP BY model_description ORDER BY count DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SalesPrModel dto = new SalesPrModel();
            dto.setModelDescription(rs.getString("model_description"));
            dto.setCount(rs.getInt("count"));
            return dto;
        });
    }

    @Override
    public List<SalesPrMonth> getSalesPrMonth() {
        String sql = "SELECT DATE_FORMAT(purchase_agreement_date, '%Y-%m') AS month, " +
                "COUNT(*) AS count FROM ivsr " +
                "WHERE purchase_agreement_date BETWEEN '2024-01-01' AND '2024-12-31' " +
                "GROUP BY DATE_FORMAT(purchase_agreement_date, '%Y-%m') " +
                "ORDER BY month";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            SalesPrMonth dto = new SalesPrMonth();
            dto.setMonth(rs.getString("month"));
            dto.setCount(rs.getInt("count"));
            return dto;
        });
    }

    @Override
    public GraphData getGraphData() {
        List<SalesPrMonth> salesPrMonth = getSalesPrMonth();
        List<SalesPrModel> salesPrModels = getSalesPrModel();
        return new GraphData(salesPrModels, salesPrMonth);
    }
}
