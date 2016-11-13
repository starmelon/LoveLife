package com.starmelon.lovelife.bean;

import java.util.ArrayList;
import java.util.List;

public class DataJson<T> {

	public Boolean status;
	public int total;
	public List<T> tngou = new ArrayList<T>();
	public List<T> list = new ArrayList<T>();

	@Override
	public String toString() {
		return "DataJson [status=" + status + ", total=" + total + ", tngou="
				+ tngou + ", list=" + list + "]";
	}

}
