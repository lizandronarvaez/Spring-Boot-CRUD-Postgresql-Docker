package com.dashboard.dashboardinventario.app.clients.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthErrorResponse {
    private Integer status;
    private Object message;
}
