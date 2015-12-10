package assignment2;

public class Film {
	
	final int id;
	String title;
	int year;
	String genre;
	int totalRatings;
	int numOfRatings;
	int rating;
	
	public Film(int id, String title, int year, String genre, int totalRatings, int numOfRatings)
	{
		this.id = id;
		this.title = title;
		this.year = year;
		this.genre = genre;
		this.totalRatings = totalRatings;
		this.numOfRatings = numOfRatings;
		this.rating = 0;
	}

	public int getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(int totalRatings) {
		this.totalRatings = totalRatings;
	}

	public int getNumOfRatings() {
		return numOfRatings;
	}

	public void setNumOfRatings(int numOfRatings) {
		this.numOfRatings = numOfRatings;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getId() {
		return id;
	}
	
	public void setRating(int number)
	{
		this.rating = number;
	}
	
	public int getRating()
	{
		if(!(numOfRatings == 0))
		{
			rating = totalRatings / numOfRatings;
		}
		return rating;
	}
	
	public int updateRating(int newRating) 
	{
		numOfRatings++;
		totalRatings = totalRatings + newRating;
		rating = totalRatings / numOfRatings;
		return getRating();
	}

	@Override
	public String toString() {
		return "ID= " + id + ", title= " + title + ", year= " + year
				+ ", genre= " + genre + ", rating= " + getRating();
	}
	
	public String filmsToString()
	{
		return id + "," + title + "," + year + "," + genre + "," + totalRatings + "," + numOfRatings;
	}
 }
