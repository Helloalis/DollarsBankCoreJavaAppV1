package com.cognixia.jump;
import java.util.ArrayList;
import java.util.Scanner;

import com.cognixia.jump.controller.UserController;
import com.cognixia.jump.exceptions.AccountNotFoundException;
import com.cognixia.jump.exceptions.TransactionException;
import com.cognixia.jump.model.User;

public class BankDriver {

	public static void main(String[] args) {
		boolean running = true;
		UserController users = new UserController();
		Scanner scanner = new Scanner(System.in);
		
		
		users.add("Rushu Karan", "4524 Guess Dr", "202 542 9996", "password", 300.00);
		users.add("Rohan Kurup", "1234 Example Ave", "202 512 2444", "password", 500.00);
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
				scanner.nextLine();
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
				System.out.println("Your user id is " + (users.getIdCounter() - 1));
				users.printUser((users.getIdCounter() - 1));
				System.out.println("Please Remember this, as you will use it to log in");	
				
			}
			if(input == 2) {
				System.out.println("+----------------------------+");
				System.out.println("|           Log In           |");
				System.out.println("+----------------------------+");
				System.out.println("Please type your id");
				int logInId = scanner.nextInt();
				scanner.nextLine();
				System.out.println("Please type your password");
				String pass = scanner.nextLine();
				boolean loggedIn = false;
				try {
					if(users.authenticate(logInId, pass)) {
						System.out.println("Logged in as " + logInId);
						loggedIn = true;
					}
					else {
						
						while(!loggedIn) {
							System.out.println("Incorrect login information. Please try again");
							System.out.println("Please type your id");
							logInId = scanner.nextInt();
							scanner.nextLine();
							System.out.println("Please type your password");
							pass = scanner.nextLine();
							if(users.authenticate(logInId, pass)) {
								try {
									System.out.println("Logged in as " + users.getUser(logInId).getName());
								}  catch (AccountNotFoundException e) {
									// TODO Auto-generated catch block
									System.out.println(e.getMessage());
								}
								loggedIn = true;
							}
						}
					}
				}  catch (AccountNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
				while(loggedIn) {
					System.out.println("+----------------------------+");
					System.out.println("|      Welcome Customer      |");
					System.out.println("+----------------------------+");
					System.out.println("1. Deposit Amount");
					System.out.println("2. Withdraw Amount");
					System.out.println("3. Transfer Funds");
					System.out.println("4. View most recent Transctions");
					System.out.println("5. Display Info");
					System.out.println("6. Sign out");
					int nextAc = scanner.nextInt();
					scanner.nextLine();
					switch(nextAc) {
					case 1: 
						boolean deposited = false;
						while(!deposited) {
							System.out.println("How much would you like to deposit?");
							double depAmount = scanner.nextDouble();
							try {
								users.deposit(logInId, depAmount);
								deposited = true;
							} catch (TransactionException e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
						}
						break;
					case 2:
						boolean withdrawn = false;
						while(!withdrawn) {
							System.out.println("How much would you like to withdraw?");
							System.out.println("Should you withdraw more than you possess, a $25 overdraft fee will be assessed");
							double withAm = scanner.nextDouble();
							try {
								users.withdraw(logInId, withAm);
								withdrawn = true;
							} catch (TransactionException e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
							
						}
						break;
					case 3:
						boolean transfer = false;
						while(!transfer) {
							System.out.println("How much would you like to transfer?");
							System.out.println("Should you transfer more than you possess, a $25 overdraft fee will be assessed");
							double transAm = scanner.nextDouble();
							System.out.println("Please enter the account id of whom you wish to transfer to:");
							int transId = scanner.nextInt();
							scanner.nextLine();
							try {
								System.out.println("You wish to transfer $" + transAm + " to " + users.getUser(transId).getName() + "?");
							}  catch (AccountNotFoundException e) {
								// TODO Auto-generated catch block
								System.out.println(e.getMessage());
							}
							System.out.println("Please type y or Y to confirm");
							String con = scanner.nextLine();
							if(con.equals("y") || con.equals("Y")) {
								try {
									users.transferFunds(logInId, transId, transAm);
									transfer = true;
								} catch (TransactionException e) {
									// TODO Auto-generated catch block
									System.out.println(e.getMessage());
								} catch (AccountNotFoundException e) {
									// TODO Auto-generated catch block
									System.out.println(e.getMessage());
								}
							}
						}
						break;
					case 4:
						users.printTransactions(logInId);
						break;
					case 5:
						users.printUser(logInId);
						break;
					case 6:
						loggedIn = false;
						break;
					}
				}
				
			}
			if(input == 3) {
				running = false;
			}
		
		}
		scanner.close();
	}

}
