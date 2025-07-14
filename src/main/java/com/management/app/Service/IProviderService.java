package com.management.app.Service;

import java.util.List;

import com.management.app.Entity.Provider;


public interface IProviderService {

	public List<Provider> lisProvider();

	public Provider findOne(Long id);
	public void save(Provider provider);
	public void delete(Long id);
}
