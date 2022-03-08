
import java.util.ArrayList;

public class IDLList<E> {
	//Properties of IDLList<E>:
	private ArrayList<Node<E>> indices;
	private Node<E> head;
	private Node<E> tail;
	private int size;
	
	//Inner class Node<E>
	@SuppressWarnings("hiding")
	private class Node<E> {
		//Properties of Node<E>:
		private E data;
		private Node<E> prev;
		private Node<E> next;
		
		//Constructor of Node<E>, given 'E' elem
		public Node(E elem) {
			this.data = elem;
			this.prev = null;
			this.next = null;
		}
		
		//Constructor of Node<E>, given 'E' elem, a link to the previous node, and
		//a link to the next node
		public Node(E elem, Node<E> prev, Node<E> next) {
			this.data = elem;
			this.prev = prev;
			this.next = next;
		}
	}
	
	//Constructor of IDLList
	public IDLList() {
		this.indices = new ArrayList<Node<E>>();
		this.size = 0;
		this.head = null;
		this.tail = null;
	}
	
	
	//Constructor of IDLList
	public IDLList(E[] newIndices) {

		this.indices = new ArrayList<Node<E>>();
        if(newIndices.length>0){
            for(int i = 0; i<newIndices.length; i++){
                this.indices.add(new Node<E>(newIndices[i]));  
            }
            this.size = this.indices.size();
		    this.head = this.indices.get(0);
		    this.tail = this.indices.get(this.size - 1);

            for(int i = 0; i<newIndices.length;i++){
                if(i == 0){
                    this.indices.get(i).prev = null;
                    this.indices.get(i).next = this.indices.get(i+1);
                }
                else if(i == newIndices.length-1){
                    this.indices.get(i).prev = this.indices.get(i-1);
                    this.indices.get(i).next = null;
                }
                else{
                    this.indices.get(i).prev = this.indices.get(i-1);
                    this.indices.get(i).next = this.indices.get(i+1);
                }
            }
        }
        else{
            this.size = 0;
		    this.head = null;
		    this.tail = null;
        }
	}
	
	
	//Given an index, the .add() method adds a new node at the given index 
	public boolean add(int index, E elem) throws Exception {
		if(index < 0 || index > size) {
			throw new Exception("IllegalStateException");
		}
		if(index == 0){
		    this.add(elem);
		}
		else if(index == size-1){
		    this.append(elem);
		}
		else{
		Node<E> placeHldr = this.indices.get(index);
		Node<E> node = new Node<E>(elem,placeHldr.prev,placeHldr);
		node.prev.next = node;
		placeHldr.prev = node;
		indices.add(index, node);
        indices.set(index-1, node.prev);
        indices.set(index+1, placeHldr);
        size += 1;
		}
		return true;
	}
	
	//The .add() method adds a new node at the head
	public boolean add(E elem) {
		Node<E> newNode = new Node<E>(elem,null,this.head);
		if(this.head != null) {
			this.head.prev = newNode;
			this.indices.set(0,head);
		}
		else {
			this.tail = newNode;
		}
		this.head = newNode;
		this.indices.add(0,newNode);
		size += 1;
		return true;
	}
	
	//The .append() method adds a new node at the tail
	public boolean append(E elem) {
		Node<E> newNode = new Node<E>(elem,this.tail,null);
		this.tail = newNode;
		this.indices.add(newNode);
		this.size += 1;
		return true;
	}

	//The .get() method returns the data of a node at the given index
	public E get(int index) throws Exception {
		if(index < 0 || index > size) {
			throw new Exception("IllegalStateException");
		}
		else {
			return this.indices.get(index).data;
		}
	}
	
	//The .getHead() method returns the data of the node at the head
	public E getHead() {
		if(this.size == 0) {
			return null;
		}
        return this.head.data;
	}
		
	//The .getLast() method returns the data of the node at the tail
	public E getLast() {
		if(this.size == 0) {
			return null;
		}
        return this.tail.data;
	}

	//The .remove() method removes the node at the head and returns the node's data
	public E remove() throws Exception {
		if(this.size == 0) {
			throw new Exception("IllegalStateException");
		}
		Node<E> temp = this.head;
		this.head = head.next;
		size += -1;
		this.indices.remove(0);
        return temp.data;
	}	
		
	//The .removeLast() method removes the node at the tail and returns the node's data
	public E removeLast() throws Exception {
		if(this.size == 0) {
			throw new Exception("IllegalStateException");
		}
	
		Node<E> node = this.tail; 
		this.tail = this.tail.prev;
		this.tail = null;
		this.indices.remove(size-1);
		size += -1;
        return node.data;
	}
		
	//Given an index, the .removeAt() method removes the node at the index and returns its data
	public E removeAt(int index) throws Exception {
		if(index < 0 || index > size ) {
			throw new Exception("IllegalStateException");
		}
		if(index == 0) {
			this.remove();
		}
		else if(index == size-1) {
			this.removeLast();
		}
		Node<E> node = this.indices.get(index);
		this.indices.remove(index);
		this.indices.get(index).prev = node.prev;
		this.indices.get(index-1).next = node.next;
		size += -1;
		node.next = null;
		node.prev = null;
		return node.data;
	}
	
	//Given an element to remove, the .remove() method removes the node that is the first instance
	//in which the data is found to be the same
	public boolean remove(E elem) throws Exception {
		
		
			Node<E> node = this.head;
			for(int x = 0; x < size ;x++) {
				if(this.indices.get(x).data == elem) {
					this.removeAt(x);
					break;
				}
				node = node.next;
			
		}
		return true;
	}

	//The .size() method returns the size of the ArrayList
	public int size() {
		return this.size;
	}

	//Converts the ArrayList into a readible string of each Node's data
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		if (this.size > 0) {
			for (int i = 0; i < this.size; i++) {
				result.append(this.indices.get(i).data + ", ");
			}
			result.delete(result.length() - 2, result.length());
		}
		result.append("]");
		return result.toString();
	}

	public static void main(String[] args) throws Exception {
    
	}
}