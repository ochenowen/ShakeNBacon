
/*Name: Chengxin Chen
 * SID: 1440708
 * Homework 5: use Chain HashTable and QP HashTable to store Shakespeare and Bacon's article
 * Chain Hash, mainly store Shakespeare's article
 */


package ShakeNBacon;

public class ChainingHash {		
	private int size;
	private HashNode[] map;
	private HashNode currentNode;
	private int bucket;
	
	/**
	 * @constructor for Chaining Hash
	 */
	public ChainingHash(){
		size = 13;
		map = new HashNode[size];
		currentNode = null;
		bucket = 0;
	}
	
	/**
	 * constructor for Chaining Hash
	 * @param startSize integer of what size the hash table should start with
	 */
	public ChainingHash(int startSize){
		size = startSize;
		map = new HashNode[size];
		for(int i = 0; i < size; i++){		//set all teh elements in the hashtable to be initial null
			map[i] = null;
		}
		currentNode = null;
		bucket = 0;
	}

	/**
	 * This function allows rudimentary iteration through the ChainingHash.
	 * The ordering is not important so long as all added elements are returned only once.
	 * It should return null once it has gone through all elements
	 * @return Returns the next element of the hash table. Returns null if it is at its end.
	 */
	public String getNextKey(){
		if(currentNode == null){
			while(bucket != size -1 && currentNode == null){ //set the initial node
				currentNode = map[bucket];
				bucket++;
			}	
		}else{
			if(currentNode.getNext() != null){				//if the next node is not null
				currentNode = currentNode.getNext();
			} else {										//if the next node is null
				bucket++;
				while(map[bucket] == null && bucket < size - 2)		//locate where the bucket should be
					bucket++;
				if(map[bucket] == null && bucket >= size - 1) 
					return null;
				currentNode = map[bucket];
			}
		}	
		if(currentNode != null)
			return currentNode.getKey();
		else
			return null;
	}
	/**
	 * Adds the key to the hash table.
	 * If there is a collision, it should be dealt with by chaining the keys together.
	 * If the key is already in the hash table, it increments that key's counter.
	 * @param keyToAdd : the key which will be added to the hash table
	 */
	public void insert(String keyToAdd){
		insert(keyToAdd, map[hash(keyToAdd)]);
	}
	
	/**
	 * helper menthod of insert
	 * @param keyToAdd
	 * @param node
	 */
	private void insert(String keyToAdd, HashNode node){
		//if there is no node exist on that index, then initialize that index
		if(node == null && map[hash(keyToAdd)] == null)
			map[hash(keyToAdd)] = new HashNode(keyToAdd);
		else if(node == null && map[hash(keyToAdd)] != null){		//if the node exist and is the next node, add it to the table
			HashNode prev = getPrevious(keyToAdd);
			prev.setNext(new HashNode(keyToAdd));
		}
		else if(node!= null && node.getKey().equals(keyToAdd))		//if the node exist and is the same key, then add count
			node.incCount();
		else
			insert(keyToAdd, node.getNext());
	}
	
	/**
	 * helper method for insert
	 * @param key
	 * @return the previous node of the current node we were working on
	 */
	private HashNode getPrevious(String key){
		HashNode node = map[hash(key)];
		while(node != null){
			if(node.getNext() == null)
				return node;
			else
				node = node.getNext();
		}
		return null;
	}
	
	/**
	 * print out the table with the key value and count 
	 */
	public void printTable(){
		for(int i = 0; i < size; i++){
			if(map[i] != null){
				System.out.println("index " + i + " " + map[i].getKey() + " :::: " + map[i].getCount());
				HashNode node = map[i].getNext();
				while(node != null){
					System.out.print(" " + map[i].getNext().getKey() + " :::: " + map[i].getCount());
					node = node.getNext();
				}
			}
		}
	}
	
	/**
	 * Returns the number of times a key has been added to the hash table.
	 * @param keyToFind : The key being searched for
	 * @return returns the number of times that key has been added.
	 */
	public int findCount(String keyToFind){
		return findCount(keyToFind, map[hash(keyToFind)]);
	}
	
	/**
	 * helper method of find count
	 * @param keyToFind
	 * @param node
	 * @return the number of times that key has been added
	 */
	private int findCount(String keyToFind, HashNode node){
		if(node == null)
			return 0;
		if(node.getKey() == keyToFind)
			return node.getCount();
		else
		return findCount(keyToFind, node.getNext());
	}
	
	
	/**
	 * hash function
	 * @param keyToHash
	 * @return index of where the item should be place.
	 */
	private int hash(String keyToHash){
		int theBucket = keyToHash.hashCode() % size;
		if (theBucket < 0) {
			theBucket += size;
		}
		return theBucket;
		//EXTRA CREDIT: Implement your own String hash function here.
	}
	
}