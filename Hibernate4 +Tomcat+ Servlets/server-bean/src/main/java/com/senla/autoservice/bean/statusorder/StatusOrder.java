package com.senla.autoservice.bean.statusorder;

public enum StatusOrder {
	Broned, Opened, Closed, Deleted, Canceled;

	@Override

	public String toString() {
		switch (this) {
		case Broned:
			return "Broned";
		case Opened:
			return "Opened";
		case Closed:
			return "Closed";
		case Deleted:
			return "Deleted";
		case Canceled:
			return "Canceled";
		default:
			throw new IllegalArgumentException();
		}

	}

}
