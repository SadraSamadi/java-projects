package order;

import java.util.ArrayList;

public class Order {

	private String id;
	private String user;
	private String costumer;
	private String date;
	private String address;
	private ArrayList<SubOrder> subOrders = new ArrayList<Order.SubOrder>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getCostumer() {
		return costumer;
	}

	public void setCostumer(String costumer) {
		this.costumer = costumer;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ArrayList<SubOrder> getSubOrders() {
		return subOrders;
	}

	public void setSubOrders(ArrayList<SubOrder> subOrders) {
		this.subOrders = subOrders;
	}

	public class SubOrder {

		private String id;
		private String orderpId;
		private String serviceId;
		private String statusId;
		private int per;
		private int amount;
		private String rate;
		private double price;
		private boolean accepted;
		private boolean denied;
		private String serviceName;
		private boolean isNew;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getOrderpId() {
			return orderpId;
		}

		public void setOrderpId(String orderpId) {
			this.orderpId = orderpId;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceName) {
			this.serviceId = serviceName;
		}

		public String getStatusId() {
			return statusId;
		}

		public void setStatusId(String statusId) {
			this.statusId = statusId;
		}

		public int getPer() {
			return per;
		}

		public void setPer(int per) {
			this.per = per;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public String getRate() {
			return rate;
		}

		public void setRate(String rate) {
			this.rate = rate;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public boolean isAccepted() {
			return accepted;
		}

		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}

		public boolean isDenied() {
			return denied;
		}

		public void setDenied(boolean denied) {
			this.denied = denied;
		}

		public String getServiceName() {
			return serviceName;
		}

		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}

		public boolean isNew() {
			return isNew;
		}

		public void setNew(boolean isNew) {
			this.isNew = isNew;
		}

	}

	public boolean hasNew() {
		for (SubOrder subOrder : subOrders) {
			if (subOrder.isNew) {
				return true;
			}
		}
		return false;
	}

}
