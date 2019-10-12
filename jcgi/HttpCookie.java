package jcgi;

public class HttpCookie
{
	public final String name;
	public String value;
	public String path;
	public String domain;
	public Date expire;
	
	public HttpCookie(String importFromHttpCookieString)
	{
	}
	
	public HttpCookie(String name)
	{
		this.name = name;
		this.value = "";
	}
	
	public HttpCookie(String name, String value)
	{
		this.name = name;
		this.value = value;
	}
}
