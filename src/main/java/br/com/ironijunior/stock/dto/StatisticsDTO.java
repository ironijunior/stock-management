package br.com.ironijunior.stock.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import br.com.ironijunior.stock.model.enumeration.StatisticsTimeEnum;
import lombok.Getter;
import lombok.Setter;

public class StatisticsDTO implements Serializable {

    @Getter
    @Setter
    private ZonedDateTime requestTimestamp;
    
    @Getter
    @Setter
    private StatisticsTimeEnum range;
    
    @Getter
    @Setter
    private List<StockDTO> topAvailableProducts;
    
    @Getter
    @Setter
    private List<ProductSaleDTO> topSellingProducts;

    public StatisticsDTO() {
    }

    public StatisticsDTO(ZonedDateTime requestTimestamp, StatisticsTimeEnum range, List<StockDTO> topAvailableProducts, List<ProductSaleDTO> topSellingProducts) {
        this.requestTimestamp = requestTimestamp;
        this.range = range;
        this.topAvailableProducts = topAvailableProducts;
        this.topSellingProducts = topSellingProducts;
    }

}
