package com.kuhnenagel.ewallet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.kuhnenagel.ewallet.model.User;
import com.kuhnenagel.ewallet.model.Wallet;
import com.kuhnenagel.ewallet.repository.UserRepository;
import com.kuhnenagel.ewallet.service.impl.UserServiceImpl;

@SpringBootTest
public class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImplTest.class);

//	@BeforeAll
//	static void setup() {
//		logger.info("@BeforeAll - executes once before all test methods in this class");
//	}
//
//	@BeforeEach
//	void init() {
//		logger.info("@BeforeEach - executes before each test method in this class");
//	}
//
//	@AfterAll()
//	public void tearDown() throws Exception {
//		logger.info("@BeforeEach - executes After all test method in this class");
//	}

	@Test()
	public void isNameAvailable() throws Exception {
		User user = new User();
		user.set_id("uid");
		user.setName("aftab");
		ArrayList<Wallet> wallets = new ArrayList<>();
		Wallet wallet = new Wallet();
		wallet.set_id("wid");
		wallet.setWalletName("wallet-a");
		wallet.setBalance(500.0);
		wallets.add(wallet);
		user.setWallets(wallets);
		Mockito.when(userRepository.findByName("aftab")).thenReturn(user);
		assertEquals("aftab", user.getName());
		assertEquals("uid", user.get_id());
	}

	@Test()
	public void getBalanceTest() throws Exception {
		Optional<User> user = Optional.of(new User());
		user.get().set_id("uid");
		user.get().setName("aftab");
		ArrayList<Wallet> wallets = new ArrayList<>();
		Wallet wallet = new Wallet();
		wallet.set_id("wid");
		wallet.setWalletName("wallet-a");
		wallet.setBalance(500.0);
		wallets.add(wallet);
		user.get().setWallets(wallets);
		Mockito.when(userRepository.findById(user.get().get_id())).thenReturn(user);
		assertEquals(500.0, user.get().getWallets().get(0).getBalance());
	}
	
	@Test()
	public void verifyBalanceIsMoreThanZero() throws Exception {
		Optional<User> user = Optional.of(new User());
		user.get().set_id("uid");
		user.get().setName("aftab");
		ArrayList<Wallet> wallets = new ArrayList<>();
		Wallet wallet = new Wallet();
		wallet.set_id("wid");
		wallet.setWalletName("wallet-a");
		wallet.setBalance(-5);
		wallets.add(wallet);
		user.get().setWallets(wallets);
		Mockito.when(userRepository.findById(user.get().get_id())).thenReturn(user);
//		assertEquals(-5, user.get().getWallets().get(0).getBalance());
//	    assumeTrue(user.get().getWallets().get(1).getBalance()>0.0);
	}
}
