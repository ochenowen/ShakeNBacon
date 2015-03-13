package ShakeNBacon;

public class HashNode {
	private String key;
	private int count;
	private HashNode next;
	public HashNode(){
		key = "";
		count = 1;
		next = null;	
	}
	public HashNode(String key){
		this.key = key;
		count = 1;
		next = null;
	}
	public String getKey(){
		return key;
	}
	public int getCount(){
		return count;
	}
	public HashNode getNext(){
		return next;
	}
	public void incCount(){
		count++;
	}
	public void setNext(HashNode next){
		this.next = next;
	}
	public void setKey(String key){
		this.key = key;
	}
	
}