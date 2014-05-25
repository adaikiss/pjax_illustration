/**
 * 
 */
package com.example.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.admin.model.Module;
import com.example.admin.service.ModuleService;
import com.example.spring.support.page.Page;
import com.example.spring.support.page.PageRequest;
import com.example.spring.support.web.OptionalPathVariable;

/**
 * @author hlw
 * 
 */
@Controller
@RequestMapping("/admin/module")
public class ModuleController {
	@Autowired
	private ModuleService moduleService;

	@RequestMapping(value = { "/page", "/page/{page}" })
	public String page(
			@OptionalPathVariable(defaultValue = "1") int page,
			@RequestParam(value = "page.size", defaultValue = "5") int size,
			@RequestHeader(value = "X-PJAX", defaultValue = "false") boolean pjax,
			Model model, HttpServletRequest request) {
		String name = ServletRequestUtils.getStringParameter(request, "name",
				null);
		Page<Module> pagination = moduleService.findAll(name, new PageRequest(
				page, size));
		model.addAttribute("pagination", pagination);
		if (!pjax) {
			model.addAttribute("name", name);
		}
		return pjax ? "/admin/fragment/module.page" : "/admin/module.page";
	}
}
