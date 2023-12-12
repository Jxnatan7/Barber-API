package com.haircut.haircutservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(value = "haircut")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Haircut {
    private String id;
    private String name;
    private BigDecimal price;
    private Boolean status;
}
