package dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import communication.Communication;

import base.Configuration;

public class dummynode {

	public static void main(String[] args) {
		Configuration.init("dummyNode");
		Communication comm = new Communication();
		comm.connect();
		BufferedReader in
		   = new BufferedReader(new InputStreamReader(System.in));
		try {
			in.readLine();
		} catch (IOException ioe)
		{
			
		}

	}
}
