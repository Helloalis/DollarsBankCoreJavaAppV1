package com.cognixia.jump;
import java.util.ArrayList;
import java.util.Scanner;

import com.cognixia.jump.controller.UserController;
import com.cognixia.jump.model.User;

public class BankDriver {

	public static void main(String[] args) {
		boolean running = true;
		UserController users = new UserController();
		Scanner scanner = new Scanner(System.in);
		
		
		users.add("Rohan Kurup", "1234 Example Ave", "202 542 99966", "password", 500.00);
		while(running) {
		
			System.out.println("+----------------------------+");
			System.out.println("|  DOLLARSBANK Welcomes You  |");
			System.out.println("+----------------------------+");
			System.out.println("1. Create New Account");
			System.out.println("2. Login");
			System.out.println("3. Exit");
		
			int input = 0;
			boolean flag = false;
			while(!flag) {
				System.out.println("Please input 1, 2, or 3.");
				input = scanner.nextInt();
				if(input == 1 || input == 2 || input == 3) {
					flag = true;
				}
			}
			if(input == 1) {
				System.out.println("+---------------------------+");
				System.out.println("|   Enter Account Details   |");
				System.out.println("+---------------------------+");
				String nam, add, num, pass;
				Double amou;
				System.out.println("Customer Name");
				nam = scanner.nextLine();
				System.out.println("Customer Address");
				add = scanner.nextLine();
				System.out.println("Customer Contact Number");
				num = scanner.nextLine();
				System.out.println("Customer Password");
				pass = scanner.nextLine();
				System.out.println("Customer Deposit Amount");
				amou = scanner.nextDouble();
				users.add(nam, add, num, pass, amou);
				System.out.println("Your user id is " + users.getIdCounter());
				System.out.println("Please Remember this, as you will use it to log in");	
				
			}
			if(input == 2) {
				System.out.println("+----------------------------+");
				System.out.println("|           Log In           |");
				System.out.println("+----------------------------+");
				int logInId = scanner.nextInt();
				
				
			}
			if(input == 3) {
				running = false;
			}
		
		}
		scanner.close();
	}

}
