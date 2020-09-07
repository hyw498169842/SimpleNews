package com.java.huangyuwei.covid.bean;

import java.io.Serializable;

public class ResearcherItemBean implements Serializable {
	private static ResearcherItemBean instance;
	private String iconUrl;
	private String name;
	private String affiliation;
	private String position;
	private String email;
	private String bio;
	private String edu;
	private String work;
	private String indices;
	private String tags;

	public static ResearcherItemBean getInstance() {
		if(instance == null) {
			instance = new ResearcherItemBean();
		}
		return instance;
	}

	private ResearcherItemBean() {}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getIndices() {
		return indices;
	}

	public void setIndices(String indices) {
		this.indices = indices;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
