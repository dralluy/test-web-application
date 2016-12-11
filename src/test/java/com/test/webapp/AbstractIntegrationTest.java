package com.test.webapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.test.webapp.core.HttpMethod;

@RunWith(JUnit4.class)
public abstract class AbstractIntegrationTest {

	protected HttpURLConnection httpConnection = null;

	@BeforeClass
	public static void init() throws Exception {
		WebAppStart.initInfraestructure();
	}

	@AfterClass
	public static void stop() throws Exception {
		WebAppStart.stopInfraestructure();
	}
	
	@After
	public void disconnectHttp() {
		httpConnection.disconnect();
	}

	
	
	protected void initConnection(String url, HttpMethod method, String userPassword, boolean output) {
		URL urlPath;
		try {
			urlPath = new URL(url);
			String enconding = Base64.getEncoder().encodeToString(userPassword.getBytes());
			httpConnection = (HttpURLConnection) urlPath.openConnection();
			httpConnection.setRequestMethod(method.toString());
			httpConnection.setDoOutput(output);
			httpConnection.setInstanceFollowRedirects(false);
			httpConnection.setRequestProperty("Authorization", "Basic " + enconding);
			httpConnection.setUseCaches(false);
		} catch (Exception e) {
			// Error
		}

	}

	protected String extractDataFromRequest() {
		try (InputStream content = httpConnection.getInputStream();
				BufferedReader in = new BufferedReader(new InputStreamReader(content))) {
			String auxData = in.readLine();
			String data = "";
			while ((data = in.readLine()) != null) {
				auxData += data;
			}
			return auxData;
		} catch (Exception e) {
			//Error
		}
		return null;
	}

	protected void putDataForRequest(String data) {
		httpConnection.setRequestProperty("Content-Type", "application/json");
		httpConnection.setRequestProperty("Content-Length", String.valueOf(data.length()));
		try (DataOutputStream content = new DataOutputStream(httpConnection.getOutputStream())) {
			content.write(data.getBytes());
			content.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
