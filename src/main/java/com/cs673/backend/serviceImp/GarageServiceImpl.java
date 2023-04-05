package com.cs673.backend.serviceImp;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.repository.GarageRepo;
import com.cs673.backend.repository.ParkForAllRepo;
import com.cs673.backend.service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GarageServiceImpl implements GarageService {
    @Autowired
    private GarageRepo garagerepo;
    @Override
    public Garage findGarageData() {
        return garagerepo.findFirstByOrderByIdAsc();
    }

    @Override
    public void save(Garage garage) {
        garagerepo.save(garage);
    }
}
