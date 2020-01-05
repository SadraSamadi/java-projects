package datastructer;

/**
 * The Class BinarySearchTree.
 */
public class BinarySearchTree {

	/** The root. */
	private Node root;

	/**
	 * Insert.
	 *
	 * @param data
	 *            the data
	 */
	public void insert(int data) {
		this.root = insert(this.root, data);
	}

	/**
	 * Insert.
	 *
	 * @param root
	 *            the root
	 * @param data
	 *            the data
	 * @return the node
	 */
	private Node insert(Node root, int data) {
		if (root == null)
			return new Node(data);
		else if (data < root.data)
			root.leftChild = insert(root.leftChild, data);
		else if (data > root.data)
			root.rightChild = insert(root.rightChild, data);
		return root;
	}

	/**
	 * Inorder.
	 *
	 * @return the string
	 */
	public String inorder() {
		return inorder(this.root, "[") + "]";
	}

	/**
	 * Inorder.
	 *
	 * @param root
	 *            the root
	 * @param str
	 *            the str
	 * @return the string
	 */
	private String inorder(Node root, String str) {
		if (root == null)
			return str;
		str = inorder(root.leftChild, str);
		str += (str.length() == 1 ? "" : ", ") + root.data;
		str = inorder(root.rightChild, str);
		return str;
	}

	/**
	 * The Class Node.
	 */
	private class Node {

		/** The data. */
		private int data;

		/** The left child. */
		private Node leftChild;

		/** The right child. */
		private Node rightChild;

		/**
		 * Instantiates a new node.
		 *
		 * @param data
		 *            the data
		 */
		private Node(int data) {
			this.data = data;
		}

	}

}
