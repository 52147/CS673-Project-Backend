package com.cs673.backend.repository;

import com.cs673.backend.entity.MemberShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembershipRepo extends JpaRepository<MemberShip, Integer> {

    MemberShip findMembershipByPlate(String plate);

    MemberShip findMemberShipByUserId(String userId);

    List<MemberShip> findAllMembershipByPlate(String plate);
    List<MemberShip> findAllMembershipByUserId(String userId);

}
