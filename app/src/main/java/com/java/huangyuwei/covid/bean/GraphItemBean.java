package com.java.huangyuwei.covid.bean;

public class GraphItemBean {
	private static GraphItemBean instance;
	private double hot;
	private String title;
	private String description;
	private String properties;
	private String relations;

	private GraphItemBean() {}

	public static GraphItemBean getInstance() {
		if(instance == null) {
			instance = new GraphItemBean();
		}
		return instance;
	}

	public double getHot() {
		return hot;
	}

	public void setHot(double hot) {
		this.hot = hot;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
	}

	public String getRelations() {
		return relations;
	}

	public void setRelations(String relations) {
		this.relations = relations;
	}
}
