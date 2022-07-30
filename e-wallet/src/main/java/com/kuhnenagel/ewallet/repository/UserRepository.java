package com.kuhnenagel.ewallet.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.kuhnenagel.ewallet.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public User findByName(String userName);
//	public Wallet findWallets();
//	public Wallet findById(long l);
}
