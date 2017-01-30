package com.revature.sms.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.sms.domain.MarketingStatus;
/**
 * DAO Repo for UserRole
 *
 */
@Repository
public interface MarketingStatusRepo extends JpaRepository<MarketingStatus, Integer> {

	
	/**
	 * Method to retrieve MarketingStatus by supplied String representing the name.
	 * @param type String value of the JobEventType to retrieve
	 * @return MarketingStatus object matching supplied String.
	 */
		MarketingStatus findByStatus(String status);
}
