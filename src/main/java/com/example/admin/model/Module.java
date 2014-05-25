/**
 * 
 */
package com.example.admin.model;

import org.apache.commons.lang3.builder.CompareToBuilder;

/**
 * @author hlw
 * 
 */
public class Module implements Comparable<Module> {
	private Long id;
	private String name;
	private Long parentId = 0l;
	private String description;
	private String url;
	private String tag;
	private String perm;
	private Integer order;
	private Module parent;

	private Module() {
		super();
	}

	public Module(Long id, String name, String perm, Integer order) {
		this();
		this.id = id;
		this.name = name;
		this.order = order;
		this.perm = perm;
	}

	public Module(Long id, String name, String url, String tag, Integer order, Long parentId) {
		this(id, name, null, order);
		this.url = url;
		this.tag = tag;
		this.parentId = parentId;
	}

	public Module(Long id, String name, String url, String tag, String perm,
			Integer order, Long parentId) {
		this(id, name, url, tag, order, parentId);
		this.perm = perm;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Module getParent() {
		return parent;
	}

	public void setParent(Module parent) {
		this.parent = parent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPerm() {
		return perm;
	}

	public void setPerm(String perm) {
		this.perm = perm;
	}

	@Override
	public int compareTo(Module o) {
		return new CompareToBuilder().append(getParentId(), o.getParentId())
				.append(getOrder(), o.getOrder()).toComparison();
	}

}
