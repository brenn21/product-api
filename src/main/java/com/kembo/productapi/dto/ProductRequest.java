package com.kembo.productapi.dto;

import java.math.BigDecimal;

public record ProductRequest(long id, String name, double price) {
}
