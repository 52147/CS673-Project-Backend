package com.cs673.backend.service;

import com.cs673.backend.entity.MemberShip;

import java.util.List;

public interface MembershipService {
  public void save(MemberShip membership);

  List<MemberShip> findAllMemberShip();

  public MemberShip findMembershipByPlate(String plate);
  public MemberShip findMemberShipByUserId(String userId);

  public List<MemberShip> findAllMembershipByPlate(String plate);
  public List<MemberShip> findAllMembershipByUserId(String UserId);

}
