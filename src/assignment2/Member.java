package assignment2;

import java.util.ArrayList;

public class Member {
	String account;
	String firstName;
	String lastName;
	boolean online;
	ArrayList<Integer> filmsRated;
	
	public Member(String firstName, String lastName, String account)
	{
		this.account = account;
		this.firstName = firstName;
		this.lastName = lastName;
		online = false;
		filmsRated = new ArrayList<Integer>();
	}
	
	public String getName()
	{
		return firstName + " " + lastName;
	}
	
	public void setName(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getAccount()
	{
		return account;
	}
	
	public void setAccount(String account)
	{
		this.account = account;
	}
	
	public void setOnline(boolean status)
	{
		this.online = status;
	}
	
	public boolean getOnline()
	{
		return online;
	}
	
	public void addRatedFilm(int id)
	{
		filmsRated.add(id);
	}
	
	public boolean checkRatedFilms(int id)
	{
		boolean seenFilm = false;
		for(Integer film: filmsRated)
		{
			if(film.equals(id))
			{
				seenFilm = true;
			}
		}
		return seenFilm;
	}

	@Override
	public String toString() {
		return "Name= " + firstName + ", " + lastName + ", Account= " + account + ".. Online: " + online;
	}
	
	public String membersToString()
	{
		return firstName + "," +  lastName + "," +  account;
	}

}
