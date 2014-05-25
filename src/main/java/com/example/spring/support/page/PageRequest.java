package com.example.spring.support.page;

import java.util.List;

public class PageRequest {
	public static final int DEFAULT_SIZE = 10;
	private int page;
	private final int size;
	private List<Sort> sort;

	public PageRequest() {
		this(1);
	}

	public PageRequest(int page) {
		this(page, DEFAULT_SIZE);
	}

	public PageRequest(int page, int size) {
		super();
		if (page <= 0) {
			page = 1;
		}
		this.page = page;
		if (size < 0) {
			size = DEFAULT_SIZE;
		}
		this.size = size;
	}

	public int getPageSize() {
		return size;
	}

	int getPageNumber() {
		return page;
	}

	public int getOffset() {
		return (page - 1) * size;
	}

	/**
	 * 当offset大于或等于total时，将page置为1
	 * 
	 * @param total
	 */
	public void confirm(int total) {
		if (getOffset() >= total) {
			page = 1;
		}
	}

	public String getSortText() {
		if(null == sort){
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < sort.size(); i++) {
			result.append(sort.get(i).toString());
			if (i != sort.size() - 1) {
				result.append(", ");
			}
		}
		return result.toString();
	}

	public List<Sort> getSort() {
		return sort;
	}

	public void setSort(List<Sort> sort) {
		this.sort = sort;
	}

	public static final class Sort {
		private String property;
		private String order = ORDER_ASC;
		public static final String ORDER_ASC = "asc";
		public static final String ORDER_DESC = "desc";

		public Sort(String property) {
			this.property = property;
		}

		public Sort(String property, String order) {
			this.property = property;
			this.setOrder(order);
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		public String getOrder() {
			return order;
		}

		public void setOrder(String order) {
			if (ORDER_DESC.equalsIgnoreCase(order)) {
				this.order = ORDER_DESC;
			} else {
				this.order = ORDER_ASC;
			}
		}

		@Override
		public String toString() {
			return property + " " + order;
		}
	}
}
