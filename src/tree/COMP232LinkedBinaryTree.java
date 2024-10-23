package tree;

import java.util.*;

/**
 * Linked List implementation of a binary tree
 * 
 * @author William Goble
 * @author Dickinson College
 * @version Feb 27, 2024
 */
public class COMP232LinkedBinaryTree<K, V> implements COMP232BinaryTree<K, V> {
    protected BTNode<K, V> root;
    protected int size;

    public COMP232LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    public COMP232LinkedBinaryTree(K key, V value) {
        root = getNewNode(key, value);
        size = 1;
    }

    public COMP232LinkedBinaryTree(K[] keys, V[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("Length of keys and values must be the same.");
        }
        if (keys.length == 0) {
            throw new IllegalArgumentException("keys and values must not be empty");
        }

         Queue<BTNode<K, V>> nodeQueue = new LinkedList<BTNode<K, V>>();
         root = getNewNode(keys[0], values[0]);
         size++;
         nodeQueue.add(root);

         while (size < keys.length - 1) {
            BTNode<K, V> subRoot = nodeQueue.remove();

            BTNode<K, V> leftChild = getNewNode(keys[size], values[size]);
            leftChild.parent = subRoot;
            subRoot.left = leftChild;
            nodeQueue.add(leftChild);
            size++;

            BTNode<K, V> rightChild = getNewNode(keys[size], values[size]);
            rightChild.parent = subRoot;
            subRoot.right = rightChild;
            nodeQueue.add(rightChild);
            size++;
         }

         // If there is one left, assign it as the left child
         if (size < keys.length) {
            BTNode<K, V> subRoot = nodeQueue.remove();
            BTNode<K, V> leftChild = getNewNode(keys[size], values[size]);
            leftChild.parent = subRoot;
            subRoot.left = leftChild;
            nodeQueue.add(leftChild);
            size++;

            subRoot.right = null;
         }
    }

    public COMP232LinkedBinaryTree(COMP232LinkedBinaryTree<K, V> leftSubTree,
                K key, V value, COMP232LinkedBinaryTree<K, V> rightSubTree) {
        // Intentionally not implemented -- see homework assignment
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int size() {
        return size;
    }

    public boolean contains(K key) {
        // Intentionally not implemented -- see homework assignment
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public V get(K key) {
        BTNode<K, V> targetNode = getNodeFromSubTree(root, key);
        if (targetNode != null) {
            return targetNode.value;
        } else {
            return null;
        }
    }

    private BTNode<K, V> getNodeFromSubTree(BTNode<K, V> subTreeRoot, K key) {
        if(subTreeRoot == null) {
            return null;
        } else if (subTreeRoot.key.equals(key)) {
            return subTreeRoot;
        } else {
            BTNode<K, V> node = getNodeFromSubTree(subTreeRoot.left, key);
            if (node != null) {
                return node;
            }

            node = getNodeFromSubTree(subTreeRoot.right, key);
            return node;
        }
    }

    /**
     * {@inheritDoc}
     */
    public void set(K key, V value) {
        BTNode<K, V> node = getNodeFromSubTree(root, key);
        if (node == null) {
            throw new NoSuchElementException("No node with key: " + key);
        } else {
            node.value = value;
        }
    }

    /**
     * Add a new node with the specified <key, value> pair to the tree. The new 
     * node is added at the first empty child location encountered in level order.
     * 
     * @param key
     *      the key
     * @param value
     *      the value
     */
    public void add(K key, V value) {
        // Intentionally not implemented -- set homework assignment
        throw new UnsupportedOperationException("Not yet implemented");

        /*
         * Hint: Use a queue to perform a level order traversal of the tree until 
         * a  node with a missing child is found. At each node (starting with 
         * the root), if it has no left child, add the new node as the left 
         * child and we are done. If the node has no right child, add the new 
         * node as the right child and we are done. If the node has both children, 
         * add them each to the queue and repeat with the node from the head 
         * of the queue.
         */
    }

    public V remove(K key) {
        BTNode<K, V> targetNode = getNodeFromSubTree(root, key);
        if (targetNode == null) {
            return null;
        } else if (size == 1) {
            V rootValue = targetNode.value;
            size = 0;
            root = null;
            return rootValue;
        } else {
            BTNode<K, V> leaf = targetNode;
            if (!leaf.isLeaf()) {
                leaf = leaf.right;
            } else {
                leaf = leaf.left;
            }

            V replacedValue = targetNode.value;
            targetNode.key = leaf.key;
            targetNode.value = leaf.value;

            if (leaf.parent.left == leaf) {
                leaf.parent.left = null;
            } else {
                leaf.parent.right = null;
            }

            size--;
            return replacedValue;
        }

    }

    /**
     * @{inheritDoc}
     */
    public void visitPreOrder(COMP232Visitor<K, V> visitor) {
        subTreeVisitPreOrder(root, visitor);
    }

    private void subTreeVisitPreOrder(BTNode<K, V> subTreeRoot, COMP232Visitor<K, V> visitor) {
        if(subTreeRoot != null) {
            visitor.visit(subTreeRoot.key, subTreeRoot.value);
            subTreeVisitPreOrder(subTreeRoot.left, visitor);
            subTreeVisitPreOrder(subTreeRoot.right, visitor);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void visitInOrder(COMP232Visitor<K, V> visitor) {
        // Intentionally not implemented -- see homework assignment
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * @{inheritDoc}
     */
    public void visitPostOrder(COMP232Visitor<K, V> visitor) {
        subTreeVisitPostOrder(root, visitor);        
    }

    private void subTreeVisitPostOrder(BTNode<K, V> subtreeRoot, COMP232Visitor<K, V> visitor) {
        if(subtreeRoot != null) {
            subTreeVisitPostOrder(subtreeRoot.left, visitor);
            subTreeVisitPostOrder(subtreeRoot.right, visitor);
            visitor.visit(subtreeRoot.key, subtreeRoot.value);
        }
    }

    public void visitLevelOrder(COMP232Visitor<K, V> visitor) {
        Queue<BTNode<K, V>> nodeQ = new LinkedList<BTNode<K, V>>();
        nodeQ.add(root);

        while (nodeQ.size() > 0) {
            BTNode<K, V> current = nodeQ.remove();
            visitor.visit(current.key, current.value);
            if(current.left != null) {
                nodeQ.add(current.left);
            }
            if(current.right != null) {
                nodeQ.add(current.right);
            }
        }
    }

    /**
     * Count the number of leaf nodes in this tree
     * 
     * @return
     */
    public int countLeafNodes() {
        // Internationally not implemented -- see homework assignment
        throw new UnsupportedOperationException("Not yet implemented");
    }

    protected BTNode<K, V> getNewNode(K key, V value) {
        return new BTNode<K, V>(key, value);
    }

    static protected class BTNode<K ,V> {
        public K key;
        public V value;
        public BTNode<K, V> left;
        public BTNode<K, V> right;
        public BTNode<K, V> parent;

        public BTNode(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            parent = null;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }
    }
}
