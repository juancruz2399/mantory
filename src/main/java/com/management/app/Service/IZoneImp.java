package com.management.app.Service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.app.Dao.IZoneDao;
import com.management.app.Entity.Zone;

@Service
public class IZoneImp implements IZoneService{

	@Autowired
	IZoneDao ZoneDao;
	
	@Override
	public List<Zone> listZones() {
		// TODO Auto-generated method stub
		return (List<Zone>)ZoneDao.findAll();
	}

	@Override
	public Zone findOne(Long id) {
		// TODO Auto-generated method stub
		return ZoneDao.findById(id).orElse(null);
	}

	@Override
	public void save(Zone zone) {
		// TODO Auto-generated method stub
		ZoneDao.save(zone);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		ZoneDao.delete(findOne(id));
	}

	@Override
	public List<String> GroupbyMainZone(Date fecha1, Date fecha2) {
		// TODO Auto-generated method stub
		return ZoneDao.groupmainbyzone(fecha1, fecha2);
	}

}
