		package edu.mum.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import edu.mum.validation.EmptyOrSize;
import edu.mum.validation.NullMinNumber;

@Entity
@Table(name = "USERS")
 public class User implements Serializable  {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public interface Details extends Default {};
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long id = null;

//    @NotEmpty 
//    @Size(min=5, max=19, message="{Size}")
	@EmptyOrSize(min=5, max=19, message="{EmptyOrSize}")
    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

	@EmptyOrSize(min=5, max=19, message="{EmptyOrSize}")
    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Email(message="{email}", groups={Details.class})
    @Column(name = "EMAIL", nullable = false)
    private String email;

    @NullMinNumber(message="{NullMinNumber}", value = 8, groups={Details.class})
    @Column(name = "RANK", nullable = false)
    private Integer ranking = 0;

    @Column(name = "IS_ADMIN", nullable = false)
    private boolean admin = false;

	@OneToOne(fetch=FetchType.LAZY,  cascade = CascadeType.ALL) 
	@JoinColumn(name="userId") 
	private UserCredentials userCredentials;

    @Valid
	@OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE}, mappedBy="user")
	private List<Address> addresses = new ArrayList<Address>();


	@OneToMany(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private Set<Item> boughtItems = new HashSet<Item>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRanking() {
		return ranking;
	}

	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Item> getBoughtItems() {
		return boughtItems;
	}

	public void setBoughtItems(Set<Item> boughtItems) {
		this.boughtItems = boughtItems;
	}
	
	public void addBoughtItem(Item boughtItem) {
		this.boughtItems.add(boughtItem);
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
		address.setUser(this);
	}
}