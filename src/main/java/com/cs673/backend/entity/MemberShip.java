package com.cs673.backend.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name="member")
public class MemberShip implements Serializable {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private int id;

      @Column
      private String plate;

      @Column
      private String permitType;

      @Column
      private Date startTime;

      @Column
      private Date endTime;

      @Column
      private String userId;

      public int getId() {
        return id;
      }

      public void setId(int id) {
        this.id = id;
      }

      public Date getEndTime() {
        return endTime;
      }

      public void setEndTime(Date endTime) {
        this.endTime = endTime;
      }

      public Date getStartTime() {
        return startTime;
      }

      public void setStartTime(Date startTime) {
        this.startTime = startTime;
      }

      public String getPermitType() {
        return permitType;
      }

      public void setPermitType(String permitType) {
        this.permitType = permitType;
      }

      public String getPlate() {
        return plate;
      }

      public void setPlate(String plate) {
        this.plate = plate;
      }

      public String getUserId(){ return userId; }

      public void setUserId(String userId) { this.userId = userId; }
}
