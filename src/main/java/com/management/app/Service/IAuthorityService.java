package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Authority;


public interface IAuthorityService {

	public List<Authority> lisAuthorities();

	public Authority findOne(Long id);
	public void save(Authority authority);
	public void delete(Long id);
}
