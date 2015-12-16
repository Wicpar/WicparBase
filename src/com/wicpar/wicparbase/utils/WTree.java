package com.wicpar.wicparbase.utils;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Frederic on 15/12/2015 at 13:23.
 */
public class WTree<T>
{

	private Node root = new Node();

	public void getRoot()
	{

	}

	private int getDepth(T object)
	{
		int i;

		for (Node n : root.childs)
		{
			if ((i = getDepth(n, object)) >= 0)
				return i + 1;
		}
		return -1;
	}

	private int getDepth(Node node,T object)
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


	private ArrayList<Node> getNodesForObject(T object)
	{
		ArrayList<Node> nodes = null;

		for (Node n : root.childs)
		{
			if (nodes == null)
				nodes = getNodesForObject(n, object);
			else
				nodes.addAll(getNodesForObject(n, object));
		}
		return nodes;
	}

	private ArrayList<Node> getNodesForObject(Node root, T object)
	{
		ArrayList<Node> nodes = null;
		int i;

		if (root.data == object)
		{
			nodes = new ArrayList<>();
			nodes.add(root);
			return nodes;
		}
		for (Node n : root.childs)
		{
			if (nodes == null)
				nodes = getNodesForObject(n, object);
			else
				nodes.addAll(getNodesForObject(n, object));
		}
		return nodes;
	}

	public class Node
	{
		private LinkedList<Node> childs = new LinkedList<>();
		private LinkedList<Node> parents = new LinkedList<>();
		public T data = null;

		public Node()
		{
		}

		public Node(T data)
		{
			this.data = data;
		}

		public void destroy()
		{
			for (Node parent : parents)
			{
				parent.childs.remove(this);
			}
			parents.clear();
		}
		public void unlink(Node parent)
		{
			parent.childs.remove(this);
			parents.remove(parent);
		}

		public void link(Node parent)
		{
			parent.childs.add(this);
			parents.add(parent);
		}
	}
}
