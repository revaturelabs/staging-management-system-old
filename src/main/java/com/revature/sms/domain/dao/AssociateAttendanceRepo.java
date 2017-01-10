package com.revature.sms.domain.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateAttendance;
import com.revature.sms.domain.User;

@Repository
public interface AssociateAttendanceRepo extends JpaRepository<AssociateAttendance, Integer> {

	List<AssociateAttendance> findByDate(Date date);
}
