package com.project.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Samples {
	
	private List<Double> x;
	private List<Double> u;
	
	public Samples() {
		this.x = new ArrayList<Double>();
		this.u = new ArrayList<Double>();
	}
	
	public void add(double x, double u) {
		this.x.add(x);
		this.u.add(u);
	}
	
	public void sort() {
		List<Double> fixedX = new ArrayList<>(this.x); //copy without reference
		List<Double> fixedU = new ArrayList<>(this.u);
		Collections.sort(this.x);
		for(int i = 0; i < this.u.size(); i++) {
			int index = fixedX.indexOf(this.x.get(i));
			Double oldU = fixedU.get(index);
			this.u.set(i, oldU);
		}
	}
	
	public String toString() {
		String line1 = "";
		for(Double x : this.x) {
			line1 += x;
			if(this.x.indexOf(x) != this.x.size()-1) {
				line1+=",";
			}
		}
		String line2 = "";
		for(Double u : this.u) {
			line2 += u;
			if(this.u.indexOf(u) != this.u.size()-1) {
				line2+=",";
			}
		}
		return line1 + "\n" + line2;
		
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
