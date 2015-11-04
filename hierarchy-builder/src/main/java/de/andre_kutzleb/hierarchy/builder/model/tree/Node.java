package de.andre_kutzleb.hierarchy.builder.model.tree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andre Kutzleb
 */
public class Node<T> {

	public static <T> Node<T> createRoot(T val) {
		return new Node<T>(null, val, -1, true);
	}
	public final T data;
	public final Node<T> parent;
	public final int level;
	public final List<Node<T>> children = new ArrayList<>();
	public final boolean isRoot;

	private final Node<T> root;
	
	private Node(Node<T> parent, T val, int layer, boolean isRoot) {
		this.parent = parent;
		data = val;
		this.level = layer;
		this.isRoot = isRoot;
		if(isRoot) {
			this.root = this;			
		} else {
			this.root = parent.root;
		}
	}

	public Node<T> addChild(T val) {
		Node<T> newNode = new Node<T>(this,val,this.level+1,false);
		this.children.add(newNode);
		return newNode;
	}
	
	public Node<T> getRoot() {
		return root;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(data.toString());
		for (Node<T> child : children) {
			builder.append(child);
		}
		return builder.toString();
	}
	
	
}