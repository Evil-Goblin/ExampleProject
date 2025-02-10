package hello.delivery_mongo.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CoordinatesRequest {
    @DecimalMax(value = "132")
    @DecimalMin(value = "124")
    private BigDecimal longitude;

    @DecimalMax(value = "39")
    @DecimalMin(value = "33")
    private BigDecimal latitude;

    @JsonCreator
    public CoordinatesRequest(double longitude, double latitude) {
        this.longitude = BigDecimal.valueOf(longitude).setScale(5, RoundingMode.HALF_UP);
        this.latitude = BigDecimal.valueOf(latitude).setScale(5, RoundingMode.HALF_UP);
    }

    @JsonIgnore
    public BigDecimal getLongitude() {
        return longitude;
    }

    @JsonIgnore
    public BigDecimal getLatitude() {
        return latitude;
    }
}
