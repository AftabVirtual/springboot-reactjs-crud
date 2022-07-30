package com.kuhnenagel.ewallet.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "wallet")
public class Wallet {

	String _id;
	double balance;
	String walletName;

	@JsonProperty("_id")
	public String get_id() {
		return this._id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@JsonProperty("walletName")
	public String getWalletName() {
		return this.walletName;
	}

	@JsonProperty("Balance")
	public double getBalance() {
		return this.balance;
	}

	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}

	public void setBalance(double newBalance) {
		this.balance = newBalance;
	}

	@Override
	public String toString() {
		return "Wallet [_id=" + _id + ", balance=" + balance + ", walletName=" + walletName + "]";
	}


}
