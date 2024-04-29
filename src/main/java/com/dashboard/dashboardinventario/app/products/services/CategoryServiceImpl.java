package com.dashboard.dashboardinventario.app.products.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dashboard.dashboardinventario.app.products.models.entity.CategoryEntity;
import com.dashboard.dashboardinventario.app.products.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService{

    private final CategoryRepository categoryRepository;
    @Override
    public List<CategoryEntity> findallCategory() {
       return (List<CategoryEntity>) categoryRepository.findAll();
    }
    
}
