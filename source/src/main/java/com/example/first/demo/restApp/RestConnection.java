package com.example.first.demo.restApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;


//Building simple Java EE application that calls Rest services.
//(example API : https://my-json-server.typicode.com/typicode/demo/posts)
public class RestConnection {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		// Creating a HttpClient object
		HttpClient client = HttpClientBuilder.create().build();

		// Creating a HttpGet Object.
		// The HttpGet class represents the HTTPGET request which retrieves the information of the given server using a URI.
		HttpGet request = new HttpGet("https://my-json-server.typicode.com/typicode/demo/posts");

		// Executing the Get request.
		// The execute() method of the HttpClient class accepts an object(HttpGet, HttpPost, HttpPut, HttpHead etc.) and returns a response object.
		HttpResponse response = client.execute(request);

		// Wrap a BufferedReader around the InputStream
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));

		String line = "";
		// Read response until the end
		while ((line = rd.readLine()) != null) {
			System.out.println(line);
		}
	}

}
