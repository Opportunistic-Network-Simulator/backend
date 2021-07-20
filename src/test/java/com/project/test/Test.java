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
public class Test {
	
	private volatile List<Integer> l1;
	
	public static void main(String[] args) throws IOException{
		
		System.out.println(Thread.currentThread().getName());
	}
}


