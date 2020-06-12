package com.zuci.electron.redis;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisUserRepository extends JpaRepository<RedisUser, Long> {
	
}
