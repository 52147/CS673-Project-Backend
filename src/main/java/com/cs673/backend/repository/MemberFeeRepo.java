package com.cs673.backend.repository;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.entity.MemberFeeStandard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFeeRepo extends JpaRepository<MemberFeeStandard, Integer> {
  MemberFeeStandard findMemberFeeStandardById(int id);
}
