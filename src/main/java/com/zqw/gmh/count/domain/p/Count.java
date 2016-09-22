package com.zqw.gmh.count.domain.p;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="count")
@JsonIgnoreProperties(value={"countType","inoutType"})
/*@JsonIgnoreProperties(value={"b","hibernateLazyInitializer","handler","fieldHandler"})*/
public class Count implements Serializable{
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="id",name="countTypeId",nullable=false)
	private CountType countType = new CountType();
	
	@Column
	private Double money;
	
	@Column(length=50)
	private String username;
	
	/*@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false,fetch=FetchType.LAZY)
	@JoinColumn(referencedColumnName="id",name="inoutTypeId",nullable=false)
	private InoutType inoutType = new InoutType();*/
	
	@Column
	private Timestamp datetime;
	
	@Transient
	private Integer inoutTypeName;
	@Transient
	private String countTypeName;
	@Transient
	private Long countTypeId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CountType getCountType() {
		return countType;
	}

	public void setCountType(CountType countType) {
		this.countType = countType;
	}

	

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/*public InoutType getInoutType() {
		return inoutType;
	}

	public void setInoutType(InoutType inoutType) {
		this.inoutType = inoutType;
	}*/

	public Timestamp getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	public Integer getInoutTypeName() {
		return inoutTypeName;
	}

	public void setInoutTypeName(Integer inoutTypeName) {
		this.inoutTypeName = inoutTypeName;
	}

	public String getCountTypeName() {
		return countTypeName;
	}

	public void setCountTypeName(String countTypeName) {
		this.countTypeName = countTypeName;
	}

	public Long getCountTypeId() {
		return countTypeId;
	}

	public void setCountTypeId(Long countTypeId) {
		this.countTypeId = countTypeId;
	}
	
	
}
