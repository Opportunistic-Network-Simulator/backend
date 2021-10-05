package com.project.test;

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
		DecimalFormat df = new DecimalFormat("0.00");
		System.out.println(Double.parseDouble(df.format("90.3244")));

	}
}


