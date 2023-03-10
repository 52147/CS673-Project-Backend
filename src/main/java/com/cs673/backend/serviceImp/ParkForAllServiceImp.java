package com.cs673.backend.serviceImp;


import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkForAllRepo;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.DTO.AllData;
import com.cs673.backend.service.ParkForAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ParkForAllServiceImp implements  ParkForAllService{
    @Autowired
    private ParkForAllRepo parkForAllRepo;
//    public parkInfo findParkInfByParkNum(int parkNum) {
//        return parkForAllRepo.findParkInfoByParkNum(parkNum);
//    }
    @Override
    public void save(ParkInfo parkinfo) {
        Date exit = new Date();
        Date entrance = parkinfo.getEntrance();
        long totalParkingTime = calTimeDiffInMinutes(entrance, exit);
        BigDecimal parkingFee = calParkingFee(totalParkingTime);

        ParkForAll parkforall = new ParkForAll();
        parkforall.setCardNum(parkinfo.getCardNum());
        parkforall.setParkNum(parkinfo.getParkNum());
        parkforall.setPlate(parkinfo.getPlate());
        parkforall.setEntrance(entrance);
        parkforall.setExit(exit);
        parkforall.setTotalParkingTime(totalParkingTime);
        parkforall.setParkingFee(parkingFee);
        parkForAllRepo.save(parkforall);
    }
    //@Override
    //public AllData findById(int id) {return parkForAllRepo.findById(id);}

    @Override
    public List<ParkForAll> findAllParkInForAll(){return parkForAllRepo.findAll();}

    @Override
    public Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id){return parkForAllRepo.findAllByOrderByIdDesc(id);}

    public long calTimeDiffInMinutes(Date entrance, Date exit) {
        long timeDiffMillis = exit.getTime() - entrance.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis);
        return minutes;
    }

    public BigDecimal calParkingFee(long minutes) {
        return new BigDecimal(minutes).divide(new BigDecimal(60), RoundingMode.DOWN).multiply(new BigDecimal(2));
    }

}
