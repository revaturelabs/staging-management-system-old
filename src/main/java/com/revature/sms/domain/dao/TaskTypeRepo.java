package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.TaskType;

@Repository
public interface TaskTypeRepo extends JpaRepository<TaskType, Integer>{

}
