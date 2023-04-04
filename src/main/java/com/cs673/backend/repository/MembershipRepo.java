package com.cs673.backend.repository;

import com.cs673.backend.entity.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface MembershipRepo extends JpaRepository<MemberShip, Integer> {

    MemberShip findMembershipByPlate(@Param("plate") String plate);

    MemberShip findMemberShipByUserID(@Param("UserId")String userId);
}
