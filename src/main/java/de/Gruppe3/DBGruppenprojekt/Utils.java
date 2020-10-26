package de.Gruppe3.DBGruppenprojekt;

import java.util.ArrayList;
import java.util.Random;
public class Utils {
	
	public static ArrayList<Vehicles> generateTestData(){
		
		ArrayList<Vehicles> list= new ArrayList<Vehicles>();
		Random random= new Random();
		
		for (int i=0;i<2000; i++) {
			Vehicles v= new Vehicles();
			v.setPs(random.nextInt(100+random.nextInt(200)));
			v.setColor(random.nextInt(100+random.nextInt(200)));
			v.setBrand("Mercedes");
			v.setPrice(random.nextInt(10000+random.nextInt(200000)));
			list.add(v);
		}
		
		return list;
	}
}
