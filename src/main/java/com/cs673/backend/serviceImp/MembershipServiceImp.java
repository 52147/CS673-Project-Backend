package com.cs673.backend.serviceImp;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.service.MembershipService;
import com.cs673.backend.entity.MemberShip;
import com.cs673.backend.repository.MembershipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MembershipServiceImp implements MembershipService{
  @Autowired
  private MembershipRepo membershiprepo;

  @Override
  public List<MemberShip> findAllMemberShip(){return membershiprepo.findAll();}

  @Override
  public void save(MemberShip membership){ membershiprepo.save(membership);}


  public MemberShip findMembershipByPlate(String plate) {return membershiprepo.findMembershipByPlate(plate);}

  public MemberShip findMemberShipByUserId(String userId){return membershiprepo.findMemberShipByUserId(userId);}

}
