package pojo_Deserialize;

public class AdPojo {
	private String Company;
	private String url;
	private String text;
	
	// generate getter and setter
	public String getCompany() {
		return Company;
	}
	public void setCompany(String company) {
		Company = company;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	// generate toString method
	@Override
	public String toString() {
		return "AdPojo [Company=" + Company + ", url=" + url + ", text=" + text + "]";
	}

}
