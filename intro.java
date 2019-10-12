import java.util.*;
import java.net.*;
import jcgi.*;

public class intro
{
	public static void main(String[] args)
	{
		HttpContext context = HttpContext.Current();
		HttpContext.HttpRequest request = context.Request;
		HttpContext.HttpResponse response = context.Response;
		try
		{
			response.WriteLine("<p>Query Strings</p>");
			for(Map.Entry<String, String> entry : request.Query.entrySet())
				response.WriteLine(entry.getKey() + "=" + entry.getValue() + "<br>");
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		finally
		{
			response.End();
		}
	}
}
