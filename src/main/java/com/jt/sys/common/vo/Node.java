package com.jt.sys.common.vo;

import java.io.Serializable;

/**
 * VO对象，用于封装相关节点信息，例如一个树节点
 * @author Administrator
 *
 */
public class Node implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7759387094451788303L;
	private Integer id;
	private String name;
	/**记录节点的关系的字段：父节点id*/
	private Integer parentId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", parentId=" + parentId + "]";
	}
	
}
