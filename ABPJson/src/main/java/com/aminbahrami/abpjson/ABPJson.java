package com.aminbahrami.abpjson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ABPJson
{
	/**
	 * Display developer logs
	 */
	public static Boolean debug=true;
	
	/**
	 * Parse json string and convert to ABPJson class
	 *
	 * @param serialized
	 * @return Object
	 */
	public Object parse(String serialized)
	{
		ArrayList<Field> fields=getFields(this.getClass());
		
		for(Field field : fields)
		{
			field.setAccessible(true);
			
			JsonField jsonFieldFound=field.getAnnotation(JsonField.class);
			JsonObject jsonObjectFound=field.getAnnotation(JsonObject.class);
			
			JsonArray jsonArrayFound=field.getAnnotation(JsonArray.class);
			JsonArrayObject jsonArrayObjectFound=field.getAnnotation(JsonArrayObject.class);
			
			String fieldName=field.getName();
			
			if(jsonFieldFound!=null) //OK
			{
				try
				{
					if(serialized.equals("[]"))
					{
						serialized="{}";
					}
					
					JSONObject jsonObject=new JSONObject(serialized);
					
					if(!jsonFieldFound.name().equals(""))
					{
						fieldName=jsonFieldFound.name();
					}
					
					if(jsonObject.has(fieldName))
					{
						Object value=jsonObject.get(fieldName);
						
						field.set(this,jsonFieldFound.type().getValue(value));
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
			
			if(jsonObjectFound!=null) //OK
			{
				try
				{
					JSONObject jsonObject=new JSONObject(serialized);
					
					if(!jsonObjectFound.name().equals(""))
					{
						fieldName=jsonObjectFound.name();
					}
					
					if(jsonObject.has(fieldName))
					{
						String value=jsonObject.getString(fieldName);
						Class<?> _class=Class.forName(jsonObjectFound.type().getName());
						
						Class[] arguments=new Class[1];
						arguments[0]=String.class;
						
						Method mStringToClass=_class.getMethod("parse",arguments);
						
						Object object=_class.newInstance();
						
						if(mStringToClass!=null)
						{
							field.set(this,mStringToClass.invoke(object,value));
						}
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
			
			if(jsonArrayObjectFound!=null) //OK
			{
				try
				{
					JSONObject jsonObject=new JSONObject(serialized);
					
					if(!jsonArrayObjectFound.name().equals(""))
					{
						fieldName=jsonArrayObjectFound.name();
					}
					
					if(jsonObject.has(fieldName))
					{
						JSONArray jsonArray1=jsonObject.getJSONArray(fieldName);
						
						Class<?> _class=Class.forName(jsonArrayObjectFound.type().getName());
						
						Class[] arguments=new Class[1];
						arguments[0]=String.class;
						
						Method mStringToClass=_class.getMethod("parse",arguments);
						
						if(mStringToClass!=null)
						{
							ArrayList<Object> list=new ArrayList<>();
							
							for(int i=0;i<jsonArray1.length();i++)
							{
								Object object=_class.newInstance();
								
								JSONObject item=jsonArray1.getJSONObject(i);
								String value=item.toString();
								
								list.add(mStringToClass.invoke(object,value));
							}
							
							field.set(this,list);
						}
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
			
			if(jsonArrayFound!=null) //OK
			{
				try
				{
					JSONObject jsonObject=new JSONObject(serialized);
					
					if(!jsonArrayFound.name().equals(""))
					{
						fieldName=jsonArrayFound.name();
					}
					
					if(jsonObject.has(fieldName))
					{
						JSONArray jsonArray1=jsonObject.getJSONArray(fieldName);
						
						ArrayList<Object> list=new ArrayList<>();
						
						for(int i=0;i<jsonArray1.length();i++)
						{
							Object value=jsonArray1.get(i);
							
							list.add(jsonArrayFound.type().getValue(value));
						}
						
						field.set(this,list);
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		return this;
	}
	
	/**
	 * Get list of class properties
	 *
	 * @param _class
	 * @return ArrayList
	 */
	private ArrayList<Field> getFields(Class<?> _class)
	{
		ArrayList<Field> output=new ArrayList<>();
		
		Class<?> i=_class;
		
		while(i!=null && i!=Object.class)
		{
			for(Field field : i.getDeclaredFields())
			{
				if(!field.isSynthetic())
				{
					output.add(field);
				}
			}
			
			i=i.getSuperclass();
		}
		
		return output;
	}
	
	/**
	 * Convert an ABPJson class to JSONObject
	 *
	 * @return JSONObject
	 */
	public JSONObject getJSON()
	{
		JSONObject output=new JSONObject();
		
		ArrayList<Field> fields=this.getFields(this.getClass());
		
		for(Field field : fields)
		{
			field.setAccessible(true);
			
			JsonField jsonFieldFound=field.getAnnotation(JsonField.class);
			JsonObject jsonObjectFound=field.getAnnotation(JsonObject.class);
			
			JsonArray jsonArrayFound=field.getAnnotation(JsonArray.class);
			JsonArrayObject jsonArrayObjectFound=field.getAnnotation(JsonArrayObject.class);
			
			String fieldName=field.getName();
			
			if(jsonFieldFound!=null) //OK
			{
				//Log.i("ABPJson","Name: "+field.getName());
				
				try
				{
					Object value=JSONObject.NULL;
					
					if(field.get(this)!=null)
					{
						value=field.get(this);
					}
					
					if(!jsonFieldFound.name().equals(""))
					{
						fieldName=jsonFieldFound.name();
					}
					
					output.put(fieldName,value);
				}
				catch(JSONException e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
				catch(IllegalAccessException e)
				{
					e.printStackTrace();
				}
			}
			
			if(jsonObjectFound!=null) //OK
			{
				try
				{
					Class<?> _class=jsonObjectFound.type();
					
					Method mGetJSON=_class.getMethod("getJSON");
					
					if(mGetJSON!=null)
					{
						Object value=JSONObject.NULL;
						
						if(field.get(this)!=null)
						{
							value=mGetJSON.invoke(field.get(this));
						}
						
						if(!jsonObjectFound.name().equals(""))
						{
							fieldName=jsonObjectFound.name();
						}
						
						output.put(fieldName,value);
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
			
			if(jsonArrayObjectFound!=null) //OK
			{
				try
				{
					Class<?> _class=Class.forName(jsonArrayObjectFound.type().getName());
					
					Method mGetJSON=_class.getMethod("getJSON");
					
					if(mGetJSON!=null)
					{
						JSONArray jsonArray=new JSONArray();
						
						ArrayList<?> tmpItems=(ArrayList<?>) field.get(this);
						
						for(int i=0;i<tmpItems.size();i++)
						{
							Object value=JSONObject.NULL;
							
							if(mGetJSON.invoke(tmpItems.get(i))!=null)
							{
								value=mGetJSON.invoke(tmpItems.get(i));
							}
							
							jsonArray.put(value);
						}
						
						if(!jsonArrayObjectFound.name().equals(""))
						{
							fieldName=jsonArrayObjectFound.name();
						}
						
						output.put(fieldName,jsonArray);
					}
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
			
			if(jsonArrayFound!=null) //OK
			{
				try
				{
					JSONArray items=new JSONArray();
					
					ArrayList<?> fieldItems=(ArrayList<?>) field.get(this);
					
					for(int i=0;i<fieldItems.size();i++)
					{
						Object value=fieldItems.get(i);
						
						items.put(value);
					}
					
					if(!jsonArrayFound.name().equals(""))
					{
						fieldName=jsonArrayFound.name();
					}
					
					output.put(fieldName,items);
				}
				catch(Exception e)
				{
					if(debug)
					{
						e.printStackTrace();
					}
				}
			}
		}
		
		return output;
	}
	
	/**
	 * Convert class to JSON string
	 *
	 * @return String
	 */
	public String stringify()
	{
		return this.getJSON().toString();
	}
}