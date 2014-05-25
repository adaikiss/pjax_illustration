package com.example.admin.service;

import com.example.admin.model.Module;
import com.example.spring.support.page.Page;
import com.example.spring.support.page.PageRequest;

public interface ModuleService {
	Page<Module> findAll(String name, PageRequest pageRequest);
}
