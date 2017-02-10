package com.revature.sms.domain.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.Trainer;
/**
 * DAO Repo for Trainer
 *
 */
@Repository
public interface TrainerRepo extends JpaRepository<Trainer, Integer> {


}
