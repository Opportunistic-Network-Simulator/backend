package com.project.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.Meet;
import com.project.simulator.entity.Pair;

public class Test {
	
	public static void main(String[] args) throws IOException{
		generateSamples(2.5, 10);
	}
	
	public static void generateSamples(double parameter, int numberOfSamples) throws IOException {
		Samples samples = new Samples();
		for(int i = 0; i<numberOfSamples; i++) {
			double u = Math.random();
			double x = -Math.log(1 - u)/parameter;
			samples.add(x, u);
		}
		samples.sort();
		String result = parameter + "\n" + samples.toString();
		System.out.println(result);
		//the path below must be at the same plot code python directory
		FileWriter Writer = new FileWriter("\\\\wsl$\\Ubuntu-20.04\\home\\gabriel\\dev\\PFC-plot-test\\ExperimentalCDF.txt");
	    Writer.write(result);
	    Writer.close();
	}
	
}


