package me.magandgolden;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DataWorks {

	@Getter
	private List <String> jokes = new ArrayList <>();
	@Getter
	private List <String> quotes = new ArrayList <>();

	public DataWorks () {
	}

}
