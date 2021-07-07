package com.project.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.project.simulator.entity.Node;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Test {
	
	private Node node;
	
	public static void main(String[] args) throws IOException{
		int n = 15;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.printf("%.2f%%%n", (i+1)*(j+1)*100/(double) (n*n) );
			}
		}
	}
}


