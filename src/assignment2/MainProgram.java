package assignment2;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;

import javax.swing.JOptionPane;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class MainProgram {
	
	In in; 
	Out out; 
	private static ArrayList<Member> members;
	private static ArrayList<Film> films;
	
	public static void main(String[] args)
	{
		MainProgram program = new MainProgram();
		members = new ArrayList<Member>();
		films = new ArrayList<Film>();
		program.startUp();
	}
	
	public void startUp()
	{
		StdOut.println("Loading.... have a bit of patience");
		loadMembers();
		loadFilms();
		startUpSwitchStatement();
	}
	
	
	public void startUpSwitchStatement()
	{
		String choice;
		boolean exit = false;
		while(exit == false)
		{
			StdOut.println("Welcome! So what do you want to do?");
			StdOut.println("");
			StdOut.println("1) Login.");
			StdOut.println("2) Register.");
			choice = StdIn.readString();
			while(!(choice.equals("1")) && !(choice.equals("2")))
			{
				try
				{
					StdOut.println("Invalid input, please choose 1 or 2.");
					choice = StdIn.readString();
				}
				catch(InputMismatchException e)
				{
					JOptionPane.showMessageDialog(null,e.toString(),"ErrorMessage",JOptionPane.ERROR_MESSAGE);
				}
			}
			switch(choice)
			{
			case "1": login();
				break;
			case "2": register();
			break;
			}
			StdOut.println("Would you like to exit the program (y/n)"); //Gives the option to exit the program after registering.
			String yn = StdIn.readString();
			while(!yn.equals("y") && !yn.equals("n"))
			{
				StdOut.println("Invalid input, please choose \"y\" or \"n\"");
				yn = StdIn.readString();
			}
			if(yn.contains("y") || yn.equals("n"))
			{
				if(yn.contains("y"))
				{
					exit();
				}
				if(yn.contains("n"))
				{
					exit = false;
				}
			}
		}
	}

	
	public void login()
	{
		boolean online = false;
		StdOut.println("Please enter a username. Please tell me you remember it...");
		String account = StdIn.readString();
		for(Member member: members)
		{
			if(member.getAccount().equals(account))
			{
				member.setOnline(true);
				online = true;
			}
		}
		if(online == false)
		{
			StdOut.println("USER DOES NOT EXIST!!");
			StdOut.println("NO ADMITTANCE TO UNAUTHORISED PERSONEL!!!");
			StdOut.println("YOU ARE WASTING YOUR TIME!!! GO AWAY!!!!");
			StdOut.println("...or you could try again, or register a new member.");
		}
		if(online == true)
		{
			mainMenu(account);
		}
	}
	
	
	public void logout(String account)
	{
		for(Member member: members)
		{
			if(member.getAccount().equals(account))
			{
				member.setOnline(false);
			}
		}
		exit();
	}
	
	public void register()
	{
		StdOut.println("Please enter your first name.");
		String firstName = StdIn.readString();
		StdOut.println("Please enter your last name.");
		String lastName = StdIn.readString();
		StdOut.println("Please enter a username.");
		String account = StdIn.readString();
		for(int i = 0; i < members.size(); i++)
		{
			while(members.get(i).getAccount().equals(account))
			{
				StdOut.println("Sorry, some a**e has taken that one, please enter a different username.");
				account = StdIn.readString();
			}
		}
		Member member = new Member(firstName, lastName, account);
		members.add(member);
		StdOut.println(member.toString() + "//  has been added");
		StdOut.println("");
	}
	
	public void editMember(String account)
	{
		for(int i = 0; i < members.size(); i++)
		{
			if(members.get(i).getAccount().contains(account))
			{
				StdOut.println("What would you like to edit about your account?");
				StdOut.println("1. Name.");
				StdOut.println("2. Username.");
				int choice = StdIn.readInt();
				while(!(choice == 1) && !(choice == 2))
				{
					StdOut.println("Invalid input, please try again.");
					choice = StdIn.readInt();
				}
				if(choice == 1)
				{
					StdOut.println("Please enter the new first name.");
					String newFirstName = StdIn.readString();
					StdOut.println("Please enter the new last name.");
					String newLastName = StdIn.readString();
					members.get(i).setName(newFirstName, newLastName);
					StdOut.println("Your name has been changed to " + newFirstName + " " + newLastName);
				}
				if(choice == 2)
				{
					StdOut.println("Please enter the new username.");
					String newUsername = StdIn.readString();
					members.get(i).setAccount(newUsername);;
					StdOut.println("Your username has been changed to " + newUsername);
				}
			}
		}
	}
	
	public void deleteMember(String account)
	{
		if(!(account == "admin"))
		{
			for(int i = 0; i < members.size(); i++)
			{
				if(members.get(i).getAccount().contains(account))
				{
					members.remove(i);
					StdOut.println("User removed: " + account);
				}
			}
		}
		else
		{
			StdOut.println("Admin user cannot be removed.");
		}
	}
	
	
	public void viewAllMembers()
	{
		for(Member member: members)
		{
			StdOut.println(member);
		}
	}
	
	
	public void loadMembers()
	{
		try
		{
			in = new In("members_fx.csv");
			String delims = "[,]";
			while (!in.isEmpty()) 
			{
				String[] data = in.readLine().split(delims);
				Member member = new Member(data[0],data[1],data[2]);
				members.add(member);
			}
			in.close();
		}
		catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null,e.toString(),"ErrorMessage",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void saveMembers()
	{
		try
		{
			out = new Out("members_fx.csv");
			for (Member memberEntry: members)
			{ 
				out.println(memberEntry.membersToString());
			}
			out.close();
		}
		catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null,e.toString(),"ErrorMessage",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void mainMenu(String account)
	{
		int choice = 0;
		while(!(choice == 9))
		{
			StdOut.println("");
			StdOut.println("1. Add a film.");
			StdOut.println("2. Delete a film.");
			StdOut.println("3. Edit a film.");
			StdOut.println("4. Rate a film.");
			StdOut.println("5. Sort films.");
			StdOut.println("6. Recommend films.");
			StdOut.println("7. View all films.");
			StdOut.println("8. View all members.");
			StdOut.println("9. Edit account.");
			StdOut.println("10. Delete account.");
			StdOut.println("11. Log out.");
			
			choice = StdIn.readInt();
			while(!(choice > 0) && !(choice < 12))
			{
				StdOut.println("Invalid input, please try again.");
				choice = StdIn.readInt();
			}
			mainMenuSwitch(choice, account);
		}
	}


	public void mainMenuSwitch(int choice, String account)
	{
		switch(choice)
		{
		case 1: addFilm();
				saveFilms();
			break;
		case 2: deleteFilm();
				saveFilms();
			break;
		case 3: editFilm();
				saveFilms();
			break;
		case 4: rateFilm(account);
		        saveFilms();
			break;
		case 5: sortFilms();
			break;
		case 6: recommendFilms();
			break;
		case 7: viewAllFilms();
			break;
		case 8: viewAllMembers();
			break;
		case 9: editMember(account);
				saveMembers();
			break;
		case 10: deleteMember(account);
				exit();
			break;
		case 11: logout(account);
			break;
		
		}
	}

	public void loadFilms()
	{
		try
		{
			in = new In("films_fx.csv");
			String delims = "[,]";
			while (!in.isEmpty()) 
			{
				String[] data = in.readLine().split(delims);
				int id = Integer.valueOf(data[0]);
				String title = String.valueOf(data[1]);
				int year = Integer.valueOf(data[2]);
				String genre = String.valueOf(data[3]);
				int totalRatings = Integer.valueOf(data[4]);
				int numOfRatings = Integer.valueOf(data[5]);
				Film film = new Film(id, title, year, genre, totalRatings, numOfRatings);
				films.add(film);
			}
			in.close();
		}
		catch(IndexOutOfBoundsException e)
		{
			JOptionPane.showMessageDialog(null,e.toString(),"ErrorMessage",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public void saveFilms()
	{
		try
		{
			out = new Out("films_fx.csv");
			for (Film filmEntry: films)
			{ 
				out.println(filmEntry.filmsToString());
			}
			out.close();
		}
		catch(NullPointerException e)
		{
			JOptionPane.showMessageDialog(null,e.toString(),"ErrorMessage",JOptionPane.ERROR_MESSAGE);
		}
	}
	 
	public void addFilm()
	{
		StdOut.println("Please enter a unique ID for this film.");
		int id = StdIn.readInt();
		for(int i = 0; i < films.size(); i++)
		{
			int filmArrayId = films.get(i).getId();
			while(filmArrayId == id)
			{
				StdOut.println("This id is already used, please enter a unique ID for this film.");
				id = StdIn.readInt();
			}
		}
		StdOut.println("Enter name of film.");
		String title = StdIn.readString();
		StdOut.println("Enter year of release.");
		int year = StdIn.readInt();
		StdOut.println("Enter genre.");
		String genre = StdIn.readString();
		Film film = new Film(id, title, year, genre, 0, 0);
		films.add(film);
		StdOut.println("");
		StdOut.println(film.getTitle() + " has been added.");
	
	}
	
	
	public void deleteFilm()
	{
		StdOut.println("Please enter the search parameters for the film you would like to delete.");
		String search = StdIn.readString();
		boolean found = searchFilms(search);
		if(found)
		{
			StdOut.println("Please enter the ID of the film you would like to delete or -1 to cancel.");
			int choice = StdIn.readInt();
			if(!(choice == -1))
			{
				boolean exists = false;
				while(exists == false)
				{
					for(Film film: films)
					{
						if(film.getId() == choice)
						{
							exists = true;
						}
					}
					if(exists == false)
					{
						StdOut.println("Invalid input, please try again.");
						choice = StdIn.readInt();
					}
				}
				for(int i = 0; i < films.size(); i++)
				{
					if(films.get(i).getId() == choice)
					{
						StdOut.println("");
						StdOut.println(films.get(i).getId() + ". " + films.get(i).getTitle() + " removed.");
						films.remove(i);
					}
				}
			}
		}
		else
		{
			StdOut.println("No films match your search parameters.");
		}
		
	}
	
	public void editFilm()
	{
		for(int i = 0; i < films.size(); i++)
		{
			StdOut.println(films.get(i).toString());
		}
		StdOut.println("Please enter the ID of the film you would like to delete or -1 to cancel.");
		int choice = StdIn.readInt();
		if(!(choice == -1))
		{
			boolean exists = false;
			while(exists == false)
			{
				for(Film film: films)
				{
					if(film.getId() == choice)
					{
						exists = true;
						StdOut.println("What would you like to edit about the film?");
						StdOut.println("1. Name.");
						StdOut.println("2. Year.");
						StdOut.println("3. Genre.");
						choice = StdIn.readInt();
						while(!(choice == 1) && !(choice == 2) && !(choice == 3))
						{
							StdOut.println("Invalid input, please try again.");
							choice = StdIn.readInt();
						}
						if(choice == 1)
						{
							StdOut.println("Please enter the new name of the film.");
							String newName = StdIn.readString();
							film.setTitle(newName);
							StdOut.println("The name has been changed to " + newName);
						}
						if(choice == 2)
						{
							StdOut.println("Please enter the new year of the film.");
							int newYear = StdIn.readInt();
							film.setYear(newYear);
							StdOut.println("The year has been changed to " + newYear);
						}
						if(choice == 3)
						{
							StdOut.println("Please enter the new genre of the film.");
							String newGenre = StdIn.readString();
							film.setGenre(newGenre);
							StdOut.println("The genre has been changed to " + newGenre);
						}
					}
				}
				if(exists == false)
				{
					StdOut.println("Invalid input, please try again.");
					choice = StdIn.readInt();
				}
			}
		}
	}
	
	
	public void viewAllFilms()
	{
		for(Film film: films)
		{
			StdOut.println(film);
		}
	}
	

	public void sortFilms()
	{
		ArrayList<Film> sortedFilms = new ArrayList<Film>(films);
		StdOut.println("Which way would you like the films sorted?");
		StdOut.println("1. By name.");
		StdOut.println("2. By year.");
		StdOut.println("3. By rating.");
		StdOut.println("4. By ID.");
		int choice = StdIn.readInt();
		while(!(choice > 0) && !(choice < 5))
		{
			StdOut.println("Invalid input, please choose 1, 2, 3 or 4.");
			choice = StdIn.readInt();
		}
		if(choice == 1)
		{
			Collections.sort(sortedFilms, MainProgram.FilmNameComparator);
		}
		if(choice == 2)
		{
			Collections.sort(sortedFilms, MainProgram.FilmYearComparator);
		}
		if(choice == 3)
		{
			Collections.sort(sortedFilms, MainProgram.FilmRatingComparator);
		}
		if(choice == 4)
		{
			Collections.sort(sortedFilms, MainProgram.FilmIDComparator);
		}
		for(int i = 0; i < sortedFilms.size(); i++)
		{
			StdOut.println(sortedFilms.get(i));
		}
		
	}
	
	
	public static Comparator<Film> FilmNameComparator = new Comparator<Film>() 
	{
		public int compare(Film film1, Film film2) 
		{
		String FilmName1 = film1.getTitle().toUpperCase();
		String FilmName2 = film2.getTitle().toUpperCase();

		return FilmName1.compareTo(FilmName2);
	   }
	};
	
	public static Comparator<Film> FilmYearComparator = new Comparator<Film>()
	{
		public int compare(Film film1, Film film2)
		{
			int FilmYear1 = film1.getYear();
			int FilmYear2 = film2.getYear();
			return FilmYear1 - FilmYear2;
		}
	};
	
	public static Comparator<Film> FilmIDComparator = new Comparator<Film>()
	{
		public int compare(Film film1, Film film2)
		{
			int FilmID1 = film1.getId();
			int FilmID2 = film2.getId();
			return FilmID1 - FilmID2;
		}
	};
	
	public static Comparator<Film> FilmRatingComparator = new Comparator<Film>()
			{
				public int compare(Film film1, Film film2)
				{
					int FilmRating1 = film1.getRating();
					int FilmRating2 = film2.getRating();
					return FilmRating2 - FilmRating1;
				}
			};
	
	public void rateFilm(String account)
	{
		for(Film film: films)
		{
			StdOut.println(film.toString());
		}
		StdOut.println("Please enter the ID of the film you would like to rate or -1 to cancel.");
		int id = StdIn.readInt();
		if(!(id == -1))
		{
			boolean exists = false;
			while(exists == false)
			{
				for(Film film: films)
				{
					if(film.getId() == id)
					{
						exists = true;
						StdOut.println("Please choose from one of the following ratings.");
						StdOut.println("-5 = Terrible!");
						StdOut.println("-3 = Didn't like it.");
						StdOut.println("0 = Haven't seen it.");
						StdOut.println("1 = OK.");
						StdOut.println("3 = Liked it!");
						StdOut.println("5 = Really liked it!");
						int choice = StdIn.readInt();
						while(!(choice == -5) && !(choice == -3) && !(choice == 0) && !(choice == 1) && !(choice == 3) && !(choice == 5))
						{
							StdOut.println("Invalid input, please try again.");
							choice = StdIn.readInt();
						}
						if(!(choice == 0))
						{
							for(Member member: members)
							{
								if(member.getAccount().equals(account))
								{
									member.addRatedFilm(id);
								}
							}
						}
						int newRating = film.updateRating(choice);
						StdOut.println("The film " + film.getId() + ": " + film.getTitle() + " has a new rating of " + newRating);
					}
				}
				if(exists == false)
				{
					StdOut.println("Invalid input, please try again.");
					id = StdIn.readInt();
				}
			}
		}
	}
	
	public boolean searchFilms(String search)
	{
		boolean found = false;
		for(Film film: films)
		{
			if(film.getTitle().contains(search))
			{
				found = true;
				StdOut.println(film.toString());
			}
		}
		return found;
	}
	
	
	public void recommendFilms()
	{
		ArrayList sortedFilms = new ArrayList<Film>(films);
		Collections.sort(sortedFilms, MainProgram.FilmRatingComparator);
		if(!(sortedFilms.isEmpty()))
		{
			for(int i = 0; i < 10; i++)
			{
				StdOut.println(sortedFilms.get(i));
			}
		}
	}
	
	/*
	 * Saves the films and members and then exits the program.
	 */
	public void exit()
	{
		saveMembers();
		saveFilms();
		StdOut.println("");
		StdOut.println("Saving data.... Just have a bit of patience...");
		StdOut.println("Thanks you for using this program. It will now self destruct. You should probably run now...");
		System.exit(0);
	}
}
