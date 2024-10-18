package com.kembo.productapi.dto;

import java.math.BigDecimal;

public record ProductResponse(long id, String name, double price) {
}
