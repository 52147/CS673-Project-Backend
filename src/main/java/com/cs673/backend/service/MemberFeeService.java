package com.cs673.backend.service;

import com.cs673.backend.entity.MemberFeeStandard;

public interface MemberFeeService {
  void save(MemberFeeStandard memberFeeStandard);
  public MemberFeeStandard findMemberFeeStandardById(int id);
}
