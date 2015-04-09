package com.raghunadimpalli.cc.core.components;

import java.util.ArrayList;
import java.util.List;

public class ExtDropDownManager<K,V> {
	
	private List<DropDownHelper<K, V>> result = new ArrayList<DropDownHelper<K,V>>();
	
	public void addTuple(K key, V value){
		result.add(new DropDownHelper<K,V>(key, value));
	}
	
	public List<DropDownHelper<K, V>> flushDropDownData(){
		return result;
	}

}
