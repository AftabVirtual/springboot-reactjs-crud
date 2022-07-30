package com.kuhnenagel.ewallet.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "user")
public class User {

	String _id;
	String name;
	ArrayList<Wallet> wallets;

	@JsonProperty("_id")
	public String get_id() {
		return this._id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@JsonProperty("name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("wallets")
	public ArrayList<Wallet> getWallets() {
		return this.wallets;
	}

	public void setWallets(ArrayList<Wallet> wallets) {
		this.wallets = wallets;
	}

	@Override
	public String toString() {
		return "User [_id=" + _id + ", name=" + name + ", wallets=" + wallets + "]";
	}

}
