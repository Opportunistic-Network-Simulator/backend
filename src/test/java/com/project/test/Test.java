package com.project.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
		Double toBeTruncated = 3.236;
		Double truncatedDouble = BigDecimal.valueOf(toBeTruncated)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		System.out.println(truncatedDouble);
	}
}


