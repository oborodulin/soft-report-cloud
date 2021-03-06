package com.oborodulin.softreport.domain.model.user;

import java.util.Arrays;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.oborodulin.softreport.domain.common.entity.AuditableEntity;
import com.oborodulin.softreport.domain.model.dic.valuesset.value.Value;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Table(name = User.TABLE_NAME)
public class User extends AuditableEntity<String> { // implements UserDetails {
	private static final long serialVersionUID = 7756405628663026131L;

	/** Наименование таблицы данных доменного объекта (сущности) */
	protected static final String TABLE_NAME = "USERS";

	private final String username;
	private final String password;
	private final String fullname;
	private final String street;
	private final String city;
	private final String state;
	private final String zip;
	private final String phoneNumber;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String codeId() {
		return this.username;
	}
	
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")); }
	 * 
	 * @Override public boolean isAccountNonExpired() { return true; }
	 * 
	 * @Override public boolean isAccountNonLocked() { return true; }
	 * 
	 * @Override public boolean isCredentialsNonExpired() { return true; }
	 * 
	 * @Override public boolean isEnabled() { return true; }
	 */
}
