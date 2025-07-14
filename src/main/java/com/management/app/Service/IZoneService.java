package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import com.management.app.Entity.Zone;


public interface IZoneService {

	public List<Zone> listZones();

	public Zone findOne(Long id);
	public void save(Zone zone);
	public void delete(Long id);
	public List<String> GroupbyMainZone(Date fecha1, Date fecha2);
}
