/**
 * 
 */
package com.example.admin.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.example.admin.model.Module;

/**
 * @author hlw
 * 
 */
@Repository
public class ModuleRepository {
	private static List<Module> modules;

	@PostConstruct
	@SuppressWarnings("unused")
	private void init() {
		modules = new ArrayList<Module>();
		modules.add(new Module(1l, "系统管理", "system", 4));
		modules.add(new Module(2l, "用户管理", "/admin/user/page", "user",
				1, 1l));
		modules.add(new Module(3l, "角色管理", "/admin/role/page", "role",
				2, 1l));
		modules.add(new Module(4l, "权限管理", "/admin/perm/page", "perm",
				3, 1l));
		modules.add(new Module(5l, "模块管理", "/admin/module/page",
				"module", "module", 4, 1l));
		modules.add(new Module(6l, "内容管理", "content", 1));
		modules.add(new Module(7l, "栏目管理", "/admin/channel/page",
				"channel", 1, 6l));
		modules.add(new Module(8l, "首页管理", "/admin/home", "home", 2, 6l));
		modules.add(new Module(9l, "统计管理", "statistic", 2));
		modules.add(new Module(10l, "PV统计", "/admin/pv", "pv", 1, 9l));
		modules.add(new Module(11l, "UV统计", "/admin/uv", "uv", 2, 9l));
		modules.add(new Module(12l, "IP统计", "/admin/ip", "ip", 3, 9l));
		modules.add(new Module(13l, "运营管理", "operation", 3));
		modules.add(new Module(14l, "会员管理", "/admin/member/page",
				"member", 1, 13l));
		modules.add(new Module(15l, "外链管理", "/admin/link/page",
				"link", 2, 13l));
		modules.add(new Module(16l, "广告管理", "/admin/adv/page", "adv",
				3, 6l));
		modules.add(new Module(17l, "模板管理", "/admin/tpl/page", "tpl",
				4, 6l));
		modules.add(new Module(18l, "分布统计", "/admin/distribution",
				"distribution", 4, 9l));
		Collections.sort(modules);
		Long parentId;
		for(Module module : modules){
			parentId = module.getParentId();
			if(parentId == 0l){
				continue;
			}
			for(Module m : modules){
				if(parentId.equals(m.getId())){
					module.setParent(m);
					break;
				}
			}
		}
	}

	public int countAll(String name) {
		if (StringUtils.isBlank(name)) {
			return modules.size();
		}
		int total = 0;
		for (Module module : modules) {
			if (module.getName().indexOf(name) != -1) {
				total++;
			}
		}
		return total;
	}

	public List<Module> selectAll(String name, int offset, int limit) {
		List<Module> records;
		if (StringUtils.isNoneBlank(name)) {
			records = new ArrayList<Module>(modules.size());
			for (Module module : modules) {
				if (module.getName().indexOf(name) != -1) {
					records.add(module);
				}
			}
		} else {
			records = modules;
		}
		return records
				.subList(offset, Math.min(offset + limit, records.size()));
	}
}
