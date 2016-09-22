package com.zqw.gmh.count.domain.p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="countType")
public class CountType  implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	@Column(length=50)
	private String name;
	@OneToMany(mappedBy="countType",cascade = { CascadeType.REFRESH, CascadeType.PERSIST,     
            CascadeType.MERGE, CascadeType.REMOVE },fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Count> counts = new ArrayList<Count>();
	
	@Column
	private Integer intout;//-1支出 1收入
	
	@Transient
	private Long sumCost;
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
	public List<Count> getCounts() {
		return counts;
	}
	public void setCounts(List<Count> counts) {
		this.counts = counts;
	}
	public Integer getIntout() {
		return intout;
	}
	public void setIntout(Integer intout) {
		this.intout = intout;
	}
	public Long getSumCost() {
		return sumCost;
	}
	public void setSumCost(Long sumCost) {
		this.sumCost = sumCost;
	}
	public CountType() {
		super();
	}
	public CountType(String name, Long sumCost) {
		super();
		this.name = name;
		this.sumCost = sumCost;
	}
	
	
}
