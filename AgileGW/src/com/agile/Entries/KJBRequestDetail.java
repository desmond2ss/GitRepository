package com.agile.Entries;

public class KJBRequestDetail {
	private String number;
	private String PONumber;
	private String rowNumber;
	private String itemNumber;
	private String POQty;
	private String changeNumer;
	private String CountryCode;
	private String weight;
	private String price;
	private String currencyCode;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getPONumber() {
		return PONumber;
	}
	public void setPONumber(String pONumber) {
		PONumber = pONumber;
	}
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	public String getItemNumber() {
		return itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	public String getPOQty() {
		return POQty;
	}
	public void setPOQty(String pOQty) {
		POQty = pOQty;
	}
	public String getChangeNumer() {
		return changeNumer;
	}
	public void setChangeNumer(String changeNumer) {
		this.changeNumer = changeNumer;
	}
	public String getCountryCode() {
		return CountryCode;
	}
	public void setCountryCode(String countryCode) {
		CountryCode = countryCode;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	@Override
	public String toString() {
		return "KJBRequestDetail [number=" + number + ", PONumber=" + PONumber + ", rowNumber=" + rowNumber
				+ ", itemNumber=" + itemNumber + ", POQty=" + POQty + ", changeNumer=" + changeNumer + ", CountryCode="
				+ CountryCode + ", weight=" + weight + ", price=" + price + ", currencyCode=" + currencyCode + "]";
	}

	
}
