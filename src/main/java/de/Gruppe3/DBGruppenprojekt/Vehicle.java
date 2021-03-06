package de.Gruppe3.DBGruppenprojekt;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "Vehicles")
public class Vehicle {
	@Id
	public String id;
	public int ps;
	public int colorCode;
	public String brand;
	public int price;
	public String extraEquipment;

	public String getExtraEquipment() {
		return extraEquipment;
	}
	public void setExtraEquipment(String extraEquipment) {
		this.extraEquipment = extraEquipment;
	}
	public Vehicle() {
		this.id = UUID.randomUUID().toString();
		this.ps = (int) (Math.random() * 99 + 1);
		this.colorCode = (int) (Math.random() * 9 + 1);
		this.brand = "Mercedes";
		this.price = (int) (Math.random() * 19999 + 1);
		this.extraEquipment= "hi";
	}
	public Vehicle(String id) {
		this.id = id;
	}

	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public int getColorCode() {
		return colorCode;
	}
	public void setColor(int colorCode) {
		this.colorCode = colorCode;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
