package com.project.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.project.simulator.entity.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Teste {
	
	
	public static void main(String[] args) throws IOException{
		List<Double> list = new ArrayList<Double>();
		list.add(2.0);
		list.add(3.0);
		list.add(4.0);
		double avg = list.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
		System.out.println(avg);
		
		
	}
}


