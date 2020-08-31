package pojo;

public class PostAPIPojo {
	
	private int id;
	private String Title;
	private String author;
	
	public PostAPIPojo(int id,String title,String author) {
		this.id = id;
		this.Title = title;
		this.author = author;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		this.Title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "PostAPIPojo [id=" + this.id + ", title=" + this.Title + ", author=" + this.author + "]";
	}
	
}
