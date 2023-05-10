package com.cs673.backend.serviceImp;

import com.cs673.backend.entity.MemberFeeStandard;
import com.cs673.backend.repository.MemberFeeRepo;
import com.cs673.backend.service.MemberFeeService;
import com.cs673.backend.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberFeeServiceImp implements MemberFeeService {
  @Autowired
  MemberFeeRepo memberFeeRepo;
  @Override
  public void save(MemberFeeStandard memberFeeStandard) { memberFeeRepo.save(memberFeeStandard);}

  @Override
  public MemberFeeStandard findMemberFeeStandardById(int id){return memberFeeRepo.findMemberFeeStandardById(id);}
}
