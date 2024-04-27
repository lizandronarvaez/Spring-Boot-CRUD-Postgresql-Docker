package com.dashboard.dashboardinventario.app.products.validation;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthErrorResponse {
    Integer status;
    String message;
}
