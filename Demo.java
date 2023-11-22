class Main {
  public static void main(String[] args) {
    RedBlackTree tree = new RedBlackTree();
    tree.insert(10);
    tree.inOrderTraversal();
    testInsertionAndTraversal();
    testSearch();
    testEdgeCases();
}
public static void testSearch() {
    RedBlackTree tree = new RedBlackTree();
    int[] keys = {10, 5, 15, 3, 7, 12, 18};
    for (int key : keys) {
        tree.insert(key);
    }
    int keyToSearch = 7;
    Node foundNode = tree.search(keyToSearch);
    if (foundNode != null) {
        System.out.println("Found: " + foundNode.key);
    } else {
        System.out.println(keyToSearch + " not found.");
    }
}

public static void testEdgeCases() {
    RedBlackTree tree = new RedBlackTree();
    int[] keys = {10, 20, 30, 40, 50}; // Already sorted for unbalanced insertion
    for (int key : keys) {
        tree.insert(key);
    }
    System.out.println("In-Order Traversal:");
    tree.inOrderTraversal();
    System.out.println();
}

    
public static void testInsertionAndTraversal() {
    RedBlackTree tree = new RedBlackTree();
    int[] keys = {10, 5, 15, 3, 7, 12, 18};
    for (int key : keys) {
        tree.insert(key);
    }
    System.out.println("In-Order Traversal:");
    tree.inOrderTraversal();
    System.out.println();
}
}

class Node {
  int key;
  Node parent;
  Node left;
  Node right;
  int color;

  public Node(int key) {
    this.key = key;
    parent = null;
    left = null;
    right = null;
    color = 1;
  }
}

class RedBlackTree {
  Node root;

  public RedBlackTree() {
    root = null;
  }

  public void insert(int key) {
    Node node = root;
    Node parent = null;

    // Traverse the tree to the left or right depending on the key
    while (node != null) {
      parent = node;
      if (key < node.key) {
        node = node.left;
      } else if (key > node.key) {
        node = node.right;
      } else {
        throw new IllegalStateException("BST already contains a node with key " + key);
      }
    }

    // Insert new node
    Node newNode = new Node(key);
    if (parent == null) {
      root = newNode;
    } else if (key < parent.key) {
      parent.left = newNode;
    } else {
      parent.right = newNode;
    }
    newNode.parent = parent;

    fixedRedBlackPropertiesAfterInsert(newNode);
  }

  public void fixedRedBlackPropertiesAfterInsert(Node newNode) {
    while (newNode != root && newNode.parent.color == 1) {
      if (newNode.parent == newNode.parent.parent.left) {
        Node uncle = newNode.parent.parent.right;

        if (uncle != null && uncle.color == 1) {
          // Scenario 2: Recolor the parent, grandparent, and uncle
          newNode.parent.color = 0; // Black
          uncle.color = 0; // Black
          newNode.parent.parent.color = 1; // Red
          newNode = newNode.parent.parent;
        } else {
          if (newNode == newNode.parent.right) {
            // Scenario 3: Node inserted on the right, left rotate
            newNode = newNode.parent;
            rotateLeft(newNode);
          }
          // Scenario 4: Node inserted on the left, right rotate the grandparent
          newNode.parent.color = 0; // Black
          newNode.parent.parent.color = 1; // Red
          rotateRight(newNode.parent.parent);
        }
      } else {
        // Symmetric cases for left and right swapped
        Node uncle = newNode.parent.parent.left;

        if (uncle != null && uncle.color == 1) {
          // Scenario 2: Recolor the parent, grandparent, and uncle
          newNode.parent.color = 0; // Black
          uncle.color = 0; // Black
          newNode.parent.parent.color = 1; // Red
          newNode = newNode.parent.parent;
        } else {
          if (newNode == newNode.parent.left) {
            // Scenario 3: Node inserted on the left, right rotate
            newNode = newNode.parent;
            rotateRight(newNode);
          }
          // Scenario 4: Node inserted on the right, left rotate the grandparent
          newNode.parent.color = 0; // Black
          newNode.parent.parent.color = 1; // Red
          rotateLeft(newNode.parent.parent);
        }
      }
    }
    // Ensure the root is black
    root.color = 0; // Black
  }

  private void rotateRight(Node node) {
    Node leftChild = node.left;
    Node parent = node.parent;
    node.left = leftChild.right;
    if (leftChild.right != null) {
      leftChild.right.parent = node;
    }
    leftChild.right = node;
    node.parent = leftChild;
    replaceParentsChild(parent, node, leftChild);
    leftChild.parent = parent;
  }

  private void rotateLeft(Node node) {
    Node parent = node.parent;
    Node rightChild = node.right;

    node.right = rightChild.left;
    if (rightChild.left != null) {
      rightChild.left.parent = node;
    }

    rightChild.left = node;
    node.parent = rightChild;

    replaceParentsChild(parent, node, rightChild);
  }

  // This is the function to replace the parents of the node and the child.
  public void replaceParentsChild(Node parent, Node oldChild, Node newChild) {
    if (parent == null) {
      root = newChild;
    } else if (parent.left == oldChild) {
      parent.left = newChild;
    } else if (parent.right == oldChild) {
      parent.right = newChild;
    } else {
      throw new IllegalStateException("Node is not a valid child");
    }
  }

  public Node search(int key) {
    Node node = root;
    while (node != null) {
      if (node.key == key) {
        return node;
      } else if (key < node.key) {
        node = node.left;
      } else {
        node = node.right;
      }
    }
    return null;
  }

  public void inOrderTraversal() {
    inOrderTraversal(root);
  }

  private void inOrderTraversal(Node node) {
    if (node != null) {
      inOrderTraversal(node.left);
      System.out.print(node.key + " ");
      inOrderTraversal(node.right);
    }
  }
}