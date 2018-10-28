package com.aminbahrami.abpjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum Type
{
	String
			{
				@Override
				public Object getValue(Object original)
				{
					if(original.toString().equals("null"))
					{
						return null;
					}
					else
					{
						return original.toString();
					}
				}
			},
	Int
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return Integer.parseInt(original+"");
					}
					catch(NumberFormatException e)
					{
						return 0;
					}
				}
				
			},
	Long
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return java.lang.Long.parseLong(original+"");
					}
					catch(NumberFormatException e)
					{
						return 0;
					}
				}
			},
	Double
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return java.lang.Double.parseDouble(original+"");
					}
					catch(NumberFormatException e)
					{
						return 0;
					}
				}
			},
	Float
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return java.lang.Float.parseFloat(original+"");
					}
					catch(NumberFormatException e)
					{
						return 0;
					}
				}
			},
	Boolean
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return java.lang.Boolean.parseBoolean(original+"");
					}
					catch(NumberFormatException e)
					{
						return false;
					}
				}
			},
	JSONObject
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return new JSONObject(""+original);
					}
					catch(JSONException e)
					{
						return new JSONObject();
					}
				}
			},
	JSONArray
			{
				@Override
				public Object getValue(Object original)
				{
					try
					{
						return new JSONArray(""+original);
					}
					catch(JSONException e)
					{
						return new JSONArray();
					}
				}
			};
	
	public static boolean isMember(Type type)
	{
		Type[] Types=Type.values();
		
		for(Type Type : Types)
		{
			if(Type==type)
				return true;
		}
		
		return false;
	}
	
	public abstract Object getValue(Object original);
}
