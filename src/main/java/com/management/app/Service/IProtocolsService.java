package com.management.app.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.management.app.Entity.Protocols;


public interface IProtocolsService {

	public List<Protocols> lisProtocols();

	public Protocols findOne(Long id);
	public void save(Protocols protocols);
	public void delete(Long id);
	public List<Protocols> lastVersionProtocolsbyDevice(Long id, int version);
	public List<Protocols> findVersionProtocolsbyDevice(Long id, int version);
	public int findLastVersion(Long id);
	
	public void importarDesdeExcel(MultipartFile file) throws Exception;
	
	
}
