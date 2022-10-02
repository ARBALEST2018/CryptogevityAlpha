package com.example.cryptogevityalpha.python;

import java.io.*;

public class javarun {
	public static void main(String[] args){
		try {
			String s = null;
			Process process = Runtime.getRuntime().exec("python E:\\chatbot\\demo.py");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while (( s = in.readLine()) != null) {
				System.out.println(s);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}