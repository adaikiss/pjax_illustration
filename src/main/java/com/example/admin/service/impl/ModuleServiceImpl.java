/**
 * 
 */
package com.example.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.admin.dao.ModuleRepository;
import com.example.admin.model.Module;
import com.example.admin.service.ModuleService;
import com.example.spring.support.page.Page;
import com.example.spring.support.page.PageRequest;

/**
 * @author hlw
 * 
 */
@Service
public class ModuleServiceImpl implements ModuleService {

	@Autowired
	private ModuleRepository moduleRepository;

	@Override
	public Page<Module> findAll(String name, PageRequest pageRequest) {

		int total = moduleRepository.countAll(name);
		if (total == 0) {
			return new Page<Module>(new ArrayList<Module>(0), pageRequest,
					total);
		}
		pageRequest.confirm(total);
		List<Module> content = moduleRepository.selectAll(name,
				pageRequest.getOffset(), pageRequest.getPageSize());
		return new Page<Module>(content, pageRequest, total);
	}

}
