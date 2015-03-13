
/*Name: Chengxin Chen
 * SID: 1440708
 * QP Hash Table, use to store Bacon's article
 * Homework 5: use Chain HashTable and QP HashTable to store Shakespeare and Bacon's article
 */



package ShakeNBacon;


public class QPHash {		//quadratic probing hash table
	private HashNode[] HashTable;
	private int size;
	int bucket;
	
	
	
	
	/**
	 * constructor for the QPHash table
	 */
	public QPHash(){
		size = 13;
		HashTable = new HashNode[size];
		bucket = 0;
	}
	
	
	/**
	 * QPHash constructor, with initial size of startSize;
	 * @param startSize
	 */
	public QPHash(int startSize){
		size = startSize;
		HashTable = new HashNode[size];
		for(int i = 0; i < size; i++)
			HashTable[i] = null;
		bucket = 0;
	}

	/**
	 * This function allows rudimentary iteration through the QPHash.
	 * The ordering is not important so long as all added elements are returned only once.
	 * It should return null once it has gone through all elements
	 * @return Returns the next element of the hash table. Returns null if it is at its end.
	 */
	public String getNextKey(){
		while(HashTable[bucket] == null && bucket < size-1)			//if the table is null and bucket is not at the end
			bucket++;
		if(bucket >= size-1)
			return null;
		return HashTable[bucket].getKey();
	}
	
	
	
	
	/**
	 * Adds the key to the hash table.
	 * If there is a collision, a new location should be found using quadratic probing.
	 * If the key is already in the hash table, it increments that key's counter.
	 * @param keyToAdd : the key which will be added to the hash table
	 */
	public void insert(String keyToAdd){
		int time = 1;									//set the base of the hash location
		while(HashTable[hash(keyToAdd) + (int) Math.pow(time, 2)] != null 			//find the location of the item should be inserted
				&& !HashTable[hash(keyToAdd) + (int) Math.pow(time, 2)].getKey().equals(keyToAdd)){
			time++;
		}
		//if the node is empty, create new node, if not, then add the count
		if(HashTable[hash(keyToAdd) + (int) Math.pow(time, 2)] == null)
			HashTable[hash(keyToAdd) + (int) Math.pow(time, 2)] = new HashNode(keyToAdd);
		else
			HashTable[hash(keyToAdd) + (int) Math.pow(time, 2)].incCount();
	}
	
	
	/**
	 * print out the table with count in there
	 */
	public void printTable(){
		for(int i = 0; i < size; i++){
			if(HashTable[i] != null){
				System.out.println("index " + i + " " + HashTable[i].getKey() + " :::: " + HashTable[i].getCount());
				HashNode node = HashTable[i].getNext();
				while(node != null){
					System.out.print(" " + HashTable[i].getNext().getKey() + " :::: " + HashTable[i].getCount());
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
		return findCount(keyToFind, 0);
	}
	
	/**
	 * helper method of findCount
	 * @param keyToFind
	 * @param time
	 * @return	the number of times taht key has been used;
	 */
	private int findCount(String keyToFind, int time){
		HashNode node = HashTable[(hash(keyToFind) + (int) Math.pow(time, 2)) % 2];
		if(node == null)
			return 0;
		else if(node.getKey().equals(keyToFind))
			return node.getCount();
		else
			return findCount(keyToFind, time++);
		}

	/**
	 * hash value of that key
	 * @param keyToHash
	 * @return the hash value of the key.
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
