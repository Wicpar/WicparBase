package com.wicpar.wicparbase.utils;

import java.util.LinkedList;

/**
 * Created by Frederic on 15/12/2015 at 13:23.
 */
public class WTree<T>
{

	private LinkedList<Node> roots = new LinkedList<>();

	public int getDepth(Node node,T object)
	{
		int i;

		if (node.data == object)
			return 0;
		for (Node n : node.childs)
		{
			if ((i = getDepth(n, object)) >= 0)
				return i + 1;
		}
		return -1;
	}

	private class Node
	{
		private LinkedList<Node> childs = new LinkedList<>();
		private T data;
	}
}
