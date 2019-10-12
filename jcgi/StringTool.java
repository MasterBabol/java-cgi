package jcgi;

import java.util.*;

public class StringTool
{
	public static String safeString(String src)
	{
		if (src != null)
			return src;
		else
			return "";
	}
		
	public static void convertRequestStringToMap(Map<String, String> containerMap, String requestString)
	{
		String[] queries;
		String[] queryPair;
		String queryValue;
		
		queries = requestString.split("&");
		for(String query : queries)
		{
			queryPair = query.split("=");
			if (queryPair[0].equals(""))
				continue;
			if (queryPair.length > 1)
			{
				queryValue = queryPair[1];
				for(int i = 2; i < queryPair.length; i++)
					queryValue += "=" + queryPair[i];
			}
			else
				queryValue = "";
			containerMap.put(queryPair[0], queryValue);
		}
	}
}
