package com.project.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Test {
	
	
	public static void main(String[] args) {
		String key = String.valueOf(System.currentTimeMillis());
		System.out.println(key);
	}
}


