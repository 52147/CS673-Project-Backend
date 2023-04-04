package com.cs673.backend.service;

import com.cs673.backend.entity.MemberShip;
import org.springframework.data.repository.query.Param;

public interface MembershipService {
  public void save(MemberShip membership);


  public MemberShip findMembershipByPlate(String plate);
  public MemberShip findMemberShipByUserID(@Param("UserId")String userId);
}
