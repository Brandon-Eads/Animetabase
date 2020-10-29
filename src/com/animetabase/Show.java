package com.animetabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class Show {
	private String name;
	private boolean isOngoing;
	private int currentEpisode;
	private int totalEpisodes;
	private File theFile;
	private PrintWriter printWriter;
	
	public Show(String name, int currentEpisode, int totalEpisodes, boolean isOngoing) throws FileNotFoundException {
		this.name = name;
		this.currentEpisode = currentEpisode;
		this.totalEpisodes = totalEpisodes;
		this.isOngoing = isOngoing;
		theFile = new File("yourlist.txt");
		printWriter = new PrintWriter(theFile);
	}
	
	public Show() {}

	public File getTheFile() {
		return theFile;
	}

	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public String getName() {
		return name;
	}

	public int getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(int currentEpisode) {
		this.currentEpisode = currentEpisode;
	}

	public int getTotalEpisodes() {
		return totalEpisodes;
	}

	public void setTotalEpisodes(int totalEpisodes) {
		this.totalEpisodes = totalEpisodes;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOngoing() {
		return isOngoing;
	}
	
	public String showIsOngoing() {
		if (isOngoing) {
			return "Is ongoing";
		}else if (!isOngoing) {
			return "Is not ongoing";
		}else {
		return null;
		}
		
	}
	
	public void setOngoing(boolean isOngoing) {
		this.isOngoing = isOngoing;
	}
	
	public void nextEpisode() {
		this.currentEpisode ++;
	}
	
	public String toString() {
		if (this.isOngoing == false) {
			return name + ": Current Episode: " + currentEpisode + " out of " + totalEpisodes;
		}else {
			return name + ": Current Episode: " + currentEpisode;
		}
	}
}
