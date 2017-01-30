package com.revature.sms.domain.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.UserRole;
/**
 * DAO Repo for UserRole
 *
 */
@Repository
public interface MarketingStatusRepo extends JpaRepository<UserRole, Integer> {

}
