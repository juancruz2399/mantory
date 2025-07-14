package com.management.app.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.management.app.Entity.Resume;


public interface IResumeService {

	public List<Resume> listResumes();

	public Resume findOne(Long id);
	public void save(Resume resume);
	public void delete(Long id);
	
	public Resume findHVbyDevice(Long id);
	public void importarHvDesdeExcel(MultipartFile file) throws Exception;

}
