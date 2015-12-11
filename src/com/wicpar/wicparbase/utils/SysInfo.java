package com.wicpar.wicparbase.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Frederic on 14/09/2015 at 20:14.
 */
public class SysInfo
{
	public static String getInfo(Map.Entry<String,Object>... entries)
	{
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().serializeNulls();
		Gson gson = builder.create();
		Map<String, Object> data = new HashMap<>();
		data.put("System Properties", System.getProperties());
		data.put("Nb of Cores", Runtime.getRuntime().availableProcessors());
		data.put("Used Ram", Runtime.getRuntime().totalMemory());
		data.put("Free Ram", Runtime.getRuntime().freeMemory());
		data.put("Max ram usable by jvm", Runtime.getRuntime().maxMemory() == Long.MAX_VALUE ? "no limit" : Runtime.getRuntime().maxMemory());
		for (Map.Entry<String,Object> entry : entries)
		{
			data.put(entry.getKey(), entry.getValue());
		}
		return gson.toJson(data);
	}

	public static Map.Entry<String, Object> makeEntry(final String s, final Object o)
	{
		return new Map.Entry<String, Object>()
		{
			private Object object = o;
			@Override
			public String getKey()
			{
				return s;
			}

			@Override
			public Object getValue()
			{
				return object;
			}

			@Override
			public Object setValue(Object value)
			{
				this.object = value;
				return object;
			}
		};
	}
}
