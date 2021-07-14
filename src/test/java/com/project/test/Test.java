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
	
	public static void main(String[] args) throws IOException{
		
		File file=new File("D:\\Gabriel\\Documentos\\Matérias\\PFC\\endedThreads.txt");    //creates a new file instance  
		FileReader fr=new FileReader(file);   //reads the file  
		BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
		String line;
		List<Integer> ids = new ArrayList<Integer>();
		while((line=br.readLine())!=null)  
		{ 
		ids.add(Integer.parseInt(line)); 
		}  
		fr.close();    //closes the stream and release the resources
		
		for(int i = 0; i < 400; i++) {
			boolean t = ids.contains(i);
			if(!t)
				System.out.println("não tem " + i);
		}
		
	}
}


