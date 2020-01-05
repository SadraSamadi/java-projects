package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

public class CostumerHttpConnector {

//	public static void main(String[] args) {
//		CostumerHttpConnector c = new CostumerHttpConnector("cs", "cs");
//	}

	private boolean connected = false;
	private String username;
	private String password;

	public HashMap<String, String> info;

	public CostumerHttpConnector(String username, String password) {

		// connect here via username and password
		this.username = username;
		this.password = password;
		connected = Connect();
		if (connected) {
			info = getInfo();
		}
	}

	public boolean Connect() {
		boolean result;
		// connect here via username and password in class
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL("http://onchapline.ir/ocl_cli/costumerLogin.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);

			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("username=" + username).getBytes());
			os.write(("&passwd=" + password).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result

				if (response.toString().equals("done"))
					result = true;
				else
					result = false;
			} else {
				result = false;
			}

		} catch (MalformedURLException e) {
			result = false;
		} catch (IOException e) {
			result = false;
		}

		return result;
	}

	public HashMap<String, String> getInfo() {
		HashMap<String, String> result = new HashMap<String, String>();
		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL(
					"http://onchapline.ir/ocl_cli/costumerDisplayByUsername.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("costumer_username=" + username).getBytes());
			os.write(("&costumer_password=" + password).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();
			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}
		if (rawData == "not connected" || rawData == "0# login failed")
			return null;
		JSONObject json = new JSONObject(rawData);
		// key: Id, Costumer_Id
		result.put("Id", String.valueOf(json.get("Id")));

		// key: name, Costumer_name
		result.put("name", String.valueOf(json.get("name")));
		// key: address, Costumer_address
		result.put("address", String.valueOf(json.get("address")));
		// key: email, Costumer_email
		result.put("email", String.valueOf(json.get("email")));
		// key: call, Costumer_call
		result.put("call", String.valueOf(json.get("call")));
		// key: rate, Costumer_rate
		result.put("rate", String.valueOf(json.get("rate")));
		// key: balance, Costumer_balance
		result.put("balance", String.valueOf(json.get("balance")));
		return result;
	}

	public ArrayList<HashMap<String, String>> getServices() {
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL(
					"http://onchapline.ir/ocl_cli/costumerGetServices.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("costumer_Id=" + info.get("Id")).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}
		if (rawData == "not connected" || rawData == "0# login failed"
				|| rawData.equals("0# empty"))
			return null;
		JSONObject json = new JSONObject(rawData);
		for (int i = 0; i < json.length(); i++) {
			HashMap<String, String> tmpService = new HashMap<String, String>();
			// key: Id, Service_Id
			tmpService.put("Id", json.getJSONObject(String.valueOf(i))
					.getString("Id"));
			// key: costumer, Costumer_Id
			tmpService.put("costumer", json.getJSONObject(String.valueOf(i))
					.getString("costumer"));
			// key: name, Service_name
			tmpService.put("name", json.getJSONObject(String.valueOf(i))
					.getString("name"));
			// key: category, Service_Category
			tmpService.put("category", json.getJSONObject(String.valueOf(i))
					.getString("category"));
			// key: explain, Service_explain
			tmpService.put("explain", json.getJSONObject(String.valueOf(i))
					.getString("explain"));
			// key: amount, Serivce_Amount
			tmpService.put("amount", json.getJSONObject(String.valueOf(i))
					.getString("amount"));
			// key: price, Service_Price
			tmpService.put("price", json.getJSONObject(String.valueOf(i))
					.getString("price"));
			result.add(tmpService);

		}

		return result;
	}

	public HashMap<String, String> getServiceById(String ServiceId) {
		HashMap<String, String> result = new HashMap<String, String>();

		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL(
					"http://onchapline.ir/ocl_cli/costumerGetServices.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("costumer_Id=" + info.get("Id")).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null)
					response.append(inputLine);
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}
		if (rawData == "not connected" || rawData == "0# login failed")
			return null;
		JSONObject json = new JSONObject(rawData);
		for (int i = 0; i < json.length(); i++) {
			if (json.getJSONObject(String.valueOf(i)).getString("Id")
					.equals(ServiceId)) {
				// key: Id, Service_Id
				result.put("Id", json.getJSONObject(String.valueOf(i))
						.getString("Id"));
				// key: costumer, Costumer_Id
				result.put("costumer", json.getJSONObject(String.valueOf(i))
						.getString("costumer"));
				// key: name, Service_name
				result.put("name", json.getJSONObject(String.valueOf(i))
						.getString("name"));
				// key: category, Service_Category
				result.put("category", json.getJSONObject(String.valueOf(i))
						.getString("category"));
				// key: explain, Service_explain
				result.put("explain", json.getJSONObject(String.valueOf(i))
						.getString("explain"));
				// key: amount, Serivce_Amount
				result.put("amount", json.getJSONObject(String.valueOf(i))
						.getString("amount"));
				// key: price, Service_Price
				result.put("price", json.getJSONObject(String.valueOf(i))
						.getString("price"));
				return result;
			}
		}
		return result;
	}

	public ArrayList<HashMap<String, Object>> getOrders() {
		ArrayList<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		// get orders Id

		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL("http://onchapline.ir/ocl_cli/costumerGetOrder.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("order_username=" + username).getBytes());
			os.write(("&order_password=" + password).getBytes());

			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}
		if (rawData == null || rawData.equals("0# empty")) {
			return null;
		}
		JSONObject json = new JSONObject(rawData);
		for (int i = 0; i < json.length(); i++) {
			HashMap<String, Object> tmpOrder = new HashMap<String, Object>();

			tmpOrder.put("Id",
					json.getJSONObject(String.valueOf(i)).getString("Id"));
			tmpOrder.put("user", json.getJSONObject(String.valueOf(i))
					.getString("user"));
			tmpOrder.put("costumer", json.getJSONObject(String.valueOf(i))
					.getString("costumer"));
			tmpOrder.put("address", json.getJSONObject(String.valueOf(i))
					.getString("address"));
			tmpOrder.put("date", json.getJSONObject(String.valueOf(i))
					.getString("date"));

			if (json.getJSONObject(String.valueOf(i)).isNull("sub"))
				continue;

			JSONObject subOrderJson = json.getJSONObject(String.valueOf(i))
					.getJSONObject("sub");
			ArrayList<HashMap<String, String>> subOrders = new ArrayList<HashMap<String, String>>();
			for (int j = 0; j < subOrderJson.length(); j++) {
				HashMap<String, String> subOrder = new HashMap<String, String>();
				subOrder.put("Id", subOrderJson
						.getJSONObject(String.valueOf(j)).getString("Id"));
				subOrder.put("orderp_Id",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("orderp_Id"));
				subOrder.put("service",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("service"));
				subOrder.put("amount",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("amount"));
				subOrder.put("per",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("per"));
				subOrder.put("status",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("status"));
				subOrder.put("rate",
						subOrderJson.getJSONObject(String.valueOf(j))
								.getString("rate"));
				subOrders.add(subOrder);
			}
			tmpOrder.put("sub", subOrders);
			result.add(tmpOrder);
		}
		return result;
	}

	public HashMap<String, String> getOrdersById(String orderId) {
		HashMap<String, String> result = new HashMap<String, String>();

		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL("http://onchapline.ir/ocl_cli/costumerGetOrder.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("order_username=" + username).getBytes());
			os.write(("&order_password=" + password).getBytes());

			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null)
					response.append(inputLine);
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}

		if (rawData == "not connected" || rawData == "0# login failed")
			return null;
		JSONObject json = new JSONObject(rawData);
		for (int i = 0; i < json.length(); i++) {
			if (json.getJSONObject(String.valueOf(i)).getString("Id")
					.equals(orderId)) {
				// key: Id, Service_Id
				result.put("Id", json.getJSONObject(String.valueOf(i))
						.getString("Id"));
				// key: costumer, Costumer_Id
				result.put("user", json.getJSONObject(String.valueOf(i))
						.getString("user"));
				// key: name, Service_name
				result.put("service", json.getJSONObject(String.valueOf(i))
						.getString("service"));
				// key: category, Service_Category
				result.put("costumer", json.getJSONObject(String.valueOf(i))
						.getString("costumer"));
				// key: explain, Service_explain
				result.put("date", json.getJSONObject(String.valueOf(i))
						.getString("date"));
				// key: amount, Serivce_Amount
				result.put("status", json.getJSONObject(String.valueOf(i))
						.getString("status"));
				// key: price, Service_amount
				result.put("amount", json.getJSONObject(String.valueOf(i))
						.getString("amount"));
				// key: price, Service_rate
				result.put("rate", json.getJSONObject(String.valueOf(i))
						.getString("rate"));
				// // key: price, Service_per
				result.put("per", json.getJSONObject(String.valueOf(i))
						.getString("per"));
				// key: price, Service_post
				result.put("post", json.getJSONObject(String.valueOf(i))
						.getString("post"));

				return result;
			}
		}
		return result;
	}

	public String setOrderStatus(String OrderId, String Status) {
		String rawData;
		// connecting to the http
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL(
					"http://onchapline.ir/ocl_cli/costumerSetOrderStatus.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("username=" + username).getBytes());
			os.write(("&passwd=" + password).getBytes());
			os.write(("&order_Id=" + OrderId).getBytes());
			os.write(("&order_status=" + Status).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}

		return rawData;
	}

	public String newService(String name, String catagory, String price,
			String amount, String explain) {
		String rawData;
		// connecting to the HTTP
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL("http://onchapline.ir/ocl_cli/costumerNewService.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("costumer_username=" + username).getBytes());
			os.write(("&costumer_passwd=" + password).getBytes());
			os.write(("&service_name=" + name).getBytes());
			os.write(("&service_category=" + catagory).getBytes());
			os.write(("&service_explain=" + explain).getBytes());
			os.write(("&service_amount=" + amount).getBytes());
			os.write(("&service_price=" + price).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}
		return rawData;

	}

	public String deleteService(String serviceId) {
		String rawData;
		// connecting to the HTTP
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL(
					"http://onchapline.ir/ocl_cli/costumerDeleteService.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("username=" + username).getBytes());
			os.write(("&passwd=" + password).getBytes());
			os.write(("&serivce_Id=" + serviceId).getBytes());
			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}

		return rawData;
	}

	public String editInfo(String password, String email, String call,
			String address, String name) {
		String rawData;
		// connecting to the HTTP
		String USER_AGENT = "Mozilla/5.0";
		URL obj;
		try {
			obj = new URL("http://onchapline.ir/ocl_cli/costumerChangeInfo.php");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			// For POST only - START
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(("username=" + username).getBytes());
			os.write(("&old_password=" + this.password).getBytes());
			os.write(("&new_password=" + password).getBytes());
			this.password = password;
			os.write(("&costumer_name=" + name).getBytes());
			os.write(("&costumer_email=" + email).getBytes());
			os.write(("&costumer_address=" + address).getBytes());
			os.write(("&costumer_call=" + call).getBytes());

			os.flush();
			os.close();
			// For POST only - END

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));

				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);

				}
				in.close();

				// print result
				rawData = response.toString();

			} else {
				rawData = "not connected";
			}

		} catch (MalformedURLException e) {
			rawData = "not connected";
		} catch (IOException e) {
			rawData = "not connected";
		}

		return rawData;
	}

	public boolean isConnected() {
		return connected;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
