package cn.edu.nenu.acm.oj.entitybeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * @author Winguse
 */
@Entity
@Table(name = "system_config", catalog = "nenuoj")
@DynamicInsert
@DynamicUpdate
@NamedQueries({
    @NamedQuery(name = "SystemConfig.findAll", query = "SELECT s FROM SystemConfig s"),
    @NamedQuery(name = "SystemConfig.findById", query = "SELECT s FROM SystemConfig s WHERE s.id = :id"),
    @NamedQuery(name = "SystemConfig.findByAttribute", query = "SELECT s FROM SystemConfig s WHERE s.attribute = :attribute"),
    @NamedQuery(name = "SystemConfig.findByLastUpdateTime", query = "SELECT s FROM SystemConfig s WHERE s.lastUpdateTime = :lastUpdateTime")})
public class SystemConfig implements java.io.Serializable {

	private static final long serialVersionUID = 4793595277145140978L;
	private Integer id;
	private String attribute;
	private Serializable value;
	private Date lastUpdateTime;

	public SystemConfig() {
	}

	public SystemConfig(String attribute, Date lastUpdateTime) {
		this.attribute = attribute;
		this.lastUpdateTime = lastUpdateTime;
	}

	public SystemConfig(String attribute, Serializable value, Date lastUpdateTime) {
		this.attribute = attribute;
		this.value = value;
		this.lastUpdateTime = lastUpdateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "attribute", nullable = false, length = 128)
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Column(name = "value")
	public Serializable getValue() {
		return this.value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}
	
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_time", nullable = false, length = 19)
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
