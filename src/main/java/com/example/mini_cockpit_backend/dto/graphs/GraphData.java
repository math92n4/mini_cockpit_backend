package com.example.mini_cockpit_backend.dto.graphs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GraphData {

    private List<SalesPrModel> salesPrModel;
    private List<SalesPrMonth> salesPrMonth;

}
