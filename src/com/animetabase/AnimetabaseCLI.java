package com.animetabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnimetabaseCLI {

	private static final String MAIN_MENU_OPTION_ADD_SHOW = "Add New Show";
	private static final String MAIN_MENU_OPTION_VIEW_SHOWS = "View Current Shows";
	private static final String MAIN_MENU_OPTION_EDIT_SHOW = "Edit a Current Show";
	private static final String MAIN_MENU_OPTION_REMOVE_SHOW = "Remove a Show From Your List";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_ADD_SHOW, MAIN_MENU_OPTION_VIEW_SHOWS,
							MAIN_MENU_OPTION_EDIT_SHOW, MAIN_MENU_OPTION_REMOVE_SHOW, MAIN_MENU_OPTION_EXIT };
	private Menu menu;
	private FileWriter fileWriter;
	private BufferedWriter bufferedWriter;
	private List<Show> theShows;

	public AnimetabaseCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() throws IOException {

		theShows = new ArrayList<>();

		printHeader();
		
		File yourList = new File("yourlist.txt");
		loadYourList(yourList);

		boolean shouldProcess = true;

		while (shouldProcess) {

			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			switch (choice) {

			case MAIN_MENU_OPTION_ADD_SHOW:
				createShow();
				break;

			case MAIN_MENU_OPTION_VIEW_SHOWS:
				displayShows();
				break;
				
			case MAIN_MENU_OPTION_EDIT_SHOW:
				editShow();
				break;
				
			case MAIN_MENU_OPTION_REMOVE_SHOW:
				deleteShow();
				break;

			case MAIN_MENU_OPTION_EXIT:
				System.out.println("\nHave a nice day!");
				endMethod();
				shouldProcess = false;
				break;

			}
		}

	}

	public void printHeader() {
		System.out.println("Welcome to the");
		System.out.println("  ___   _   _ ________  ___ _____ _____ ___  ______  ___   _____ _____ ");
		System.out.println(" / _ \\ | \\ | |_   _|  \\/  ||  ___|_   _/ _ \\ | ___ \\/ _ \\ /  ___|  ___|");
		System.out.println("/ /_\\ \\|  \\| | | | | .  . || |__   | |/ /_\\ \\| |_/ / /_\\ \\\\ `--.| |__");
		System.out.println("|  _  || . ` | | | | |\\/| ||  __|  | ||  _  || ___ \\  _  | `--. \\  __|");
		System.out.println("| | | || |\\  |_| |_| |  | || |___  | || | | || |_/ / | | |/\\__/ / |___");
		System.out.print("\\_| |_/\\_| \\_/\\___/\\_|  |_/\\____/  \\_/\\_| |_/\\____/\\_| |_/\\____/\\____/");
		System.out.println("  Okaeri ヽ(•‿•)ノ");
		System.out.println("\nWhat would you like to do today?");

	}

	public void createShow() throws IOException {
		Scanner input = new Scanner(System.in);

		String name = "";
		boolean isOngoing = false;
		int currentEpisode = 0;
		int totalEpisodes = 0;
		boolean shouldAsk = true;

		while (shouldAsk) {
			System.out.print("What is the name of your new addition? ");
			name = input.nextLine();
			System.out.print("Is this show still ongoing? ");
			String ongoingString = input.nextLine();
			if (ongoingString.equalsIgnoreCase("Y")) {
				isOngoing = true;
				totalEpisodes = 0;
			} else if (ongoingString.equalsIgnoreCase("N")) {
				isOngoing = false;
				System.out.print("How many episodes are there? ");
				try {
					totalEpisodes = Integer.parseInt(input.nextLine());
				} catch (NumberFormatException numFormEx) {
					System.out.println("Uh-oh! Looks like our system didn't agree with whatever you put in there! Try again!");
					return;
				}
			} else {
				System.out.println("Sorry, the system didn't like whatever you inputted. Try again!");
				return;
			}
			System.out.print("What episode are you currently on? ");
			try {
				currentEpisode = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException numFormEx) {
				return;
			}
			System.out.println("Is this correct?");
			if (isOngoing) {
				System.out.println("Name: " + name + " Current Episode: " + currentEpisode + " Is ongoing: " + isOngoing);
			} else if (!isOngoing) {
				System.out.println("Name: " + name + " Current Episode: " + currentEpisode+ " Total Episodes: "
						+ totalEpisodes + " Is ongoing: " + isOngoing);
			}
			String isCorrectResponse = input.nextLine();
			if (isCorrectResponse.equalsIgnoreCase("Y")) {
				shouldAsk = false;
			} else if (isCorrectResponse.equalsIgnoreCase("N")) {
				shouldAsk = true;
			}
		}

		Show show = new Show(name, currentEpisode, totalEpisodes, isOngoing);

		theShows.add(show);
		saveShows();

	}

	public void saveShows() throws IOException {
		
		for (Show s : theShows) {
			try {
				File theFile = new File("yourlist.txt");
				if (!theFile.exists()) {
					theFile.createNewFile();
				}
				fileWriter = new FileWriter(theFile.getName(), true);
				bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.newLine();
				bufferedWriter.write(
						s.getName() + "|" + s.getCurrentEpisode() + "|" + s.isOngoing() + "|" + s.getTotalEpisodes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			bufferedWriter.close();
		}
	}

	public List<Show> loadYourList(File saveFile) throws FileNotFoundException{
		Scanner reader = new Scanner(saveFile);
	
		while (reader.hasNextLine()) {
			String theLine = reader.nextLine();
			String[] theValue = new String[]{};
			theValue = theLine.split("\\|");
			if (theLine.contains("|")) {
			Show show = new Show(theValue[0], Integer.parseInt(theValue[1]), Integer.parseInt(theValue[3]), Boolean.parseBoolean(theValue[2]));
			theShows.add(show);
			} else {
				
			}
		}
		reader.close();
		return theShows;
	}
	
	public void displayShows() {
		System.out.println();
		for (Show s : theShows) {
			System.out.println(s.toString());
		}
	}

	public Show searchFor(String nameToLookFor) throws FileNotFoundException {
		Show theShow = null;
		for (Show s : theShows) {
			if(s.getName().equalsIgnoreCase(nameToLookFor)) {
			return s;
		}else {
			System.out.println("Sorry, but the system did not find the show you were looking for! Try again!");
			return theShow;
		}
		}return theShow;		
	}
	
	public void deleteShow() throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.println();
		System.out.print("That's sad to hear. What show did you want to remove? ");
		String showToDelete = input.nextLine();
		
		if (searchFor(showToDelete) != null) {
		theShows.remove(searchFor(showToDelete));
		}else {
			System.out.println();
			System.out.println("Sorry, the system didn't like whatever you inputted. Try again!");
		}
	}
	
	public void editShow() throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		displayShows();
		System.out.println();
		System.out.print("What is the name of the show would you like to edit?");
		String showToEdit = input.nextLine();
		System.out.println("What would you like to change about the show?");
		System.out.println("[1] The Name");
		System.out.println("[2] My Current Episode");
		System.out.println("[3] The Total Number of Episodes");
		System.out.println("[4] Its Ongoing Status");
		
		String choice = input.nextLine();
		Show showToChange = searchFor(showToEdit);
		if (Integer.valueOf(choice) < 1 || Integer.valueOf(choice) > 4) {
			System.out.println("Sorry, the system didn't like whatever you inputted. Try again!");
			return;
		}else {
		
		switch (choice) {
			
		case "1":
			System.out.println("Current Name: " + showToChange.getName());
			System.out.print("What should the new name be? ");
			showToChange.setName(input.nextLine());
			System.out.println("Gotcha! We'll make that change right now! Go make sure we did it right!");
			break;
		
		case "2":
			System.out.println("Current Episode: " + showToChange.getCurrentEpisode());
			System.out.print("What should your new current episode be? ");
			showToChange.setCurrentEpisode(Integer.parseInt(input.nextLine()));
			System.out.println("Gotcha! We'll make that change right now! Go make sure we did it right!");
			break;
			
		case "3":
			System.out.println("Current Total Episodes: " + showToChange.getTotalEpisodes());
			System.out.print("What should the new total number of episodes? ");
			showToChange.setTotalEpisodes(Integer.parseInt(input.nextLine()));
			System.out.println("Gotcha! We'll make that change right now! Go make sure we did it right!");
			break;
			
		case "4":
			System.out.println("Current Ongoing Status: " + showToChange.showIsOngoing());
			System.out.print("What should the new is ongoing status be? "
					+ "\nIs Ongoing = true  |  Is Not Ongoing = false");
			showToChange.setOngoing(Boolean.parseBoolean(input.nextLine().toLowerCase()));
			System.out.println("Gotcha! We'll make that change right now! Go make sure we did it right!");
			break;
		}
		}
	}
	
	public void endMethod() throws IOException {
		saveShows();
	}
	
}
