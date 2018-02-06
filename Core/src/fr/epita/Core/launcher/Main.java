package fr.epita.Core.launcher;

import fr.epita.Core.datamodel.Identity;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Identity id1 = new Identity();
		id1.setDisplayName("vvv");
		id1.setUID("123");
		id1.setEmail("ss@dd.com");
		
		System.out.println(id1);
	}

}
