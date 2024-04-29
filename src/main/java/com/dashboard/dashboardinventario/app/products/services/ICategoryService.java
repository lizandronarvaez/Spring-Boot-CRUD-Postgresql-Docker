package com.dashboard.dashboardinventario.app.products.services;

import java.util.List;

import com.dashboard.dashboardinventario.app.products.models.entity.CategoryEntity;

public interface ICategoryService {
    List<CategoryEntity> findallCategory();
}
