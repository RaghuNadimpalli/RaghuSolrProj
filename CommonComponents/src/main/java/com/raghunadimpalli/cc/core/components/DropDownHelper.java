package com.raghunadimpalli.cc.core.components;

public class DropDownHelper<K, V> {
	
	K key;
	
	V value;
	
	public DropDownHelper(K key, V value){
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(String V) {
		this.value = value;
	}
	
	

}
