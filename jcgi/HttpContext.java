package jcgi;

import java.io.*;
import java.net.*;
import java.util.*;
import jcgi.*;

public class HttpContext
{	
	private static HttpContext current = null;
	
	public final HttpRequest Request;
	public final HttpResponse Response;
	
	public static HttpContext Current()
	{
		if (current == null)
			current = new HttpContext();
		return current;
	}
	
	private HttpContext()
	{
		HttpRequest request = null;
		HttpResponse response = null;
		try
		{
			request = new HttpRequest();
			response = new HttpResponse();
		}
		catch(Exception e)
		{
			System.exit(-1);
		}
		Request = request;
		Response = response;
	}
		
	public class HttpRequest
	{
		private Map<String, String> env;
		
		public Map<String, String> Query;
		public Map<String, String> Form;
		public Map<String, String> Cookie;
		
		HttpRequest() throws Exception
		{
			env = System.getenv();
			
			String QueryString;
			String FormString;
			String CookieString;
			
			QueryString = URLDecoder.decode(StringTool.safeString(env.get("QUERY_STRING")), "UTF-8");
			Query = new Hashtable<String, String>();
			StringTool.convertRequestStringToMap(Query, QueryString);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			StringBuilder sb = new StringBuilder();
			String tmpString;
			while((tmpString = br.readLine()) != null)
				sb.append(tmpString);
			
			FormString = sb.toString();
			Form = new Hashtable<String, String>();
			StringTool.convertRequestStringToMap(Form, FormString);
			
			CookieString = URLDecoder.decode(StringTool.safeString(env.get("HTTP_COOKIE")), "UTF-8");
			Cookie = new Hashtable<String, String>();
			StringTool.convertRequestStringToMap(Cookie, CookieString);
		}
	}
	
	public class HttpResponse
	{
		public Map<String, String> Cookie;
		
		private int httpStatus;
		private String responseTextType;
		
		private StringBuilder contentString;
		
		void setDefaultHeader()
		{
			httpStatus = 200;
			responseTextType = "text/html";
		}
		
		HttpResponse() throws Exception
		{
			Cookie = new Hashtable<String, String>();
			contentString = new StringBuilder();
			
			setDefaultHeader();
		}
		
		private String generateHeaderString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append("Status: " + httpStatus);
			sb.append("\r\nContent-type: " + responseTextType);
			if (!Cookie.isEmpty())
			{
			}
			sb.append("\r\n\r\n");
			return sb.toString();
		}
		
		public void Write(String outString)
		{
			contentString.append(outString);
		}
		public void WriteLine(String outString)
		{
			contentString.append(outString);
			contentString.append("\n");
		}
		
		public void End()
		{
			String headerString = generateHeaderString();
			System.out.print(headerString);
			System.out.print(contentString.toString());
			System.exit(0);
		}
	}

}