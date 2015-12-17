package com.wicpar.wicparbase.oldphysics;

import org.joml.Vector2d;
import org.joml.Vector3d;

/**
 * Created by Frederic on 01/10/2015 at 13:34.
 */
public class AABB
{
	private final Vector3d Min,Max;

	public AABB()
	{
		Min = new Vector3d();
		Max = new Vector3d();
	}

	public AABB(Vector3d... points)
	{
		this();
		Update(points);
	}

	public AABB(double width, double height, double depth, Vector3d center)
	{
		this();
		Min.set(center.x - width / 2, center.y - height / 2, center.z - depth / 2);
		Max.set(center.x + width / 2, center.y + height / 2, center.z + depth / 2);
	}

	public AABB(double width, double height, double depth, double x, double y, double z)
	{
		this();
		Min.set(x - width / 2, y - height / 2, z - depth / 2);
		Max.set(x + width / 2, y + height / 2, z + depth / 2);
	}

	public void Translate(Vector3d translation)
	{
		Min.add(translation);
		Max.add(translation);
	}

	public void Translate(double x, double y, double z)
	{
		Min.add(x,y,z);
		Max.add(x,y,z);
	}

	public void Update(Vector3d... points)
	{
		for (Vector3d point: points)
		{
			if (point.x < Min.x)
				Min.x = point.x;
			else if (point.x > Max.x)
				Max.x = point.x;

			if (point.y < Min.y)
				Min.y = point.y;
			else if (point.y > Max.y)
				Max.y = point.y;

			if (point.z < Min.z)
				Min.z = point.z;
			else if (point.z > Max.z)
				Max.z = point.z;
		}
	}

	public void Update(Vector2d... points)
	{
		for (Vector2d point: points)
		{
			if (point.x < Min.x)
				Min.x = point.x;
			else if (point.x > Max.x)
				Max.x = point.x;

			if (point.y < Min.y)
				Min.y = point.y;
			else if (point.y > Max.y)
				Max.y = point.y;
		}
	}

	public Vector3d getMin()
	{
		return Min;
	}

	public Vector3d getMax()
	{
		return Max;
	}
}
