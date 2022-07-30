package com.kuhnenagel.ewallet.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kuhnenagel.ewallet.controller.UserController;
import com.kuhnenagel.ewallet.exception.handler.CustomException;
import com.kuhnenagel.ewallet.exception.handler.ResourceAlreadyExists;
import com.kuhnenagel.ewallet.model.User;
import com.kuhnenagel.ewallet.model.Wallet;
import com.kuhnenagel.ewallet.repository.UserRepository;
import com.kuhnenagel.ewallet.service.UserService;
import org.springframework.data.mongodb.core.MongoTemplate;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Override
	public User createUser(User request) {
		if (!isNameAvailable(request.getName())) {
			throw new ResourceAlreadyExists("User Already Exists! Please recheck user name!");
		}
		User user = new User();
		user.set_id(UUID.randomUUID().toString());
		user.setName(request.getName());
		ArrayList<Wallet> wallets = new ArrayList<Wallet>();
		for (int i = 0; i < request.getWallets().size(); i++) {
			Wallet wallet = new Wallet();
			wallet.set_id(UUID.randomUUID().toString());
			wallet.setWalletName(request.getWallets().get(i).getWalletName());
			wallet.setBalance(request.getWallets().get(i).getBalance());
			wallets.add(wallet);
		}
		user.setWallets(wallets);
		logger.info("######## user.get_id():" + user.get_id());
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(String id) {
		Optional<User> currentUser = userRepository.findById(id);
		if (currentUser.isEmpty() || currentUser == null) {
			throw new CustomException("User not found, Please recheck user id !");
		}
		userRepository.deleteById(id);
	}

	@Override
	public boolean isNameAvailable(String userName) {
		boolean isUserNameAvailable = userRepository.findByName(userName) == null;
		if (isUserNameAvailable == false) {
			throw new ResourceAlreadyExists("User Already Exists! Please recheck user name!");
		}
		return isUserNameAvailable;
	}

	@Override
	public List<User> list() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUser(String id) {
		Optional<User> currentUsers = userRepository.findById(id);
		if (currentUsers.isEmpty() || currentUsers == null) {
			throw new CustomException("User not found, Please recheck user id !");
		}
		return currentUsers;
	}

	@Override
	public double getBalance(String uid, String wid) {
		Optional<User> currentUsers = userRepository.findById(uid);
		if (currentUsers.isEmpty() || currentUsers == null) {
			throw new CustomException("User not found, Please recheck user id !");
		}
		boolean flag = false;
		if (currentUsers.get().get_id().equals(uid)) {
			ArrayList<Wallet> wallets = currentUsers.get().getWallets();
			for (Wallet wallet : wallets) {
				if (wid.equals(wallet.get_id())) {
					flag = true;
					return wallet.getBalance();
				}
			}
			if (!flag) {
				throw new CustomException("Wallet id not found, Please recheck wallet id !");
			}
		} else {
			throw new CustomException("User not found, Please recheck user id !");
		}
		return 0.0;
	}

	@Override
	public double topUp(String uid, String wid, double topup) {
		logger.info(" ###### uid: " + uid + " wid: " + wid + " topup: " + topup);
		double currentBalance = getBalance(uid, wid);
		double newBalance = currentBalance + topup;
		logger.info(" ### currentBalance: " + currentBalance + " topup: " + topup + " newBalance: " + newBalance);
		if (newBalance < 0) {
			throw new CustomException("Balance Can't be less than Zero, Please recheck!");
		}
		Optional<User> currentUser = userRepository.findById(uid);
		if (currentUser.isEmpty() || currentUser == null) {
			throw new CustomException("User not found, Please recheck user id !");
		}

		User user = new User();
		user.set_id(currentUser.get().get_id());
		user.setName(currentUser.get().getName());

		boolean isWalletIdFound = false;
		ArrayList<Wallet> wallets = new ArrayList<Wallet>();
		for (int i = 0; i < currentUser.get().getWallets().size(); i++) {
			Wallet wallet = new Wallet();
			if (currentUser.get().getWallets().get(i).get_id().equals(wid)) {
				logger.info("##### Wallet id matched, Update balance "
						+ currentUser.get().getWallets().get(i).get_id().equals(wid));
				isWalletIdFound = true;

				wallet.set_id(currentUser.get().getWallets().get(i).get_id());
				wallet.setWalletName(currentUser.get().getWallets().get(i).getWalletName());
				wallet.setBalance(newBalance);
			} else {
				wallet.set_id(currentUser.get().getWallets().get(i).get_id());
				wallet.setWalletName(currentUser.get().getWallets().get(i).getWalletName());
				wallet.setBalance(currentUser.get().getWallets().get(i).getBalance());
			}
			wallets.add(wallet);
		}
		if (!isWalletIdFound) {
			throw new CustomException("Wallet id not found, Please recheck Wallet id !");
		}
		user.setWallets(wallets);
		userRepository.save(user);
		return newBalance;
	}

	/*
	 * @Override public double topUp(String uid, String wid, double topup) {
	 * logger.info(" ###### uid: " + uid + " wid: " + wid + " topup: " + topup);
	 * 
	 * double currentBalance = getBalance(uid, wid); double newBalance =
	 * currentBalance + topup; logger.info(" ### currentBalance: " + currentBalance
	 * + " topup: " + topup + " newBalance: " + newBalance); if (newBalance < 0) {
	 * throw new
	 * CustomException("Balance Can't be less than Zero, Please recheck!"); }
	 * Optional<User> currentUser = userRepository.findById(uid); if
	 * (currentUser.isEmpty() || currentUser == null) { throw new
	 * CustomException("User not found, Please recheck user id !"); } User user =
	 * new User(); user.set_id(currentUser.get().get_id());
	 * user.setName(currentUser.get().getName()); ArrayList<Wallet> wallets = new
	 * ArrayList<Wallet>(); for (int i = 0; i <
	 * currentUser.get().getWallets().size(); i++) { Wallet wallet = new Wallet();
	 * wallet.set_id(currentUser.get().getWallets().get(i).get_id());
	 * wallet.setWalletName(currentUser.get().getWallets().get(i).getWalletName());
	 * logger.info("##### equals: "+currentUser.get().getWallets().get(i).get_id().
	 * equals(wid)); //
	 * logger.info("##### ==: "+currentUser.get().getWallets().get(i).get_id()==wid)
	 * ; if (currentUser.get().getWallets().get(i).get_id().equals(wid)) {
	 * logger.info("##### wallet id matched: "+currentUser.get().getWallets().get(i)
	 * .get_id().equals(wid));
	 * 
	 * 
	 * Query query = new Query(new Criteria("_id").is(uid)); Update update = new
	 * Update().set("balance", newBalance);//.set("email", user.getEmail());
	 * userRepository.updateFirst(query, update, COLLECTION);
	 * 
	 * wallet.setBalance(newBalance); } else {
	 * wallet.setBalance(currentUser.get().getWallets().get(i).getBalance()); }
	 * wallets.add(wallet); } user.setWallets(wallets);
	 * logger.info("########## Final User Object: "+user);
	 * userRepository.save(user); return newBalance; }
	 */
}
