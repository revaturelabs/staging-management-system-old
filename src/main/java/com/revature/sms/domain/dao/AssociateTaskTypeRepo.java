package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.AssociateTaskType;

@Repository
public interface AssociateTaskTypeRepo extends JpaRepository<AssociateTaskType, Integer>{

	AssociateTaskType findByType(String type);
}
