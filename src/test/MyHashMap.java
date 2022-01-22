package test;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author z
 *
 * @param <K>键的类型
 * @param <V>值的类型
 * 
 */
public class MyHashMap<K,V>{
    static private int MAXSIZE=10000;//元素最大数量
    private int tableSize;//哈希表的大小
	class Entry<A,B>{//键值对
        private A key;
        private B value;
        public Entry(A key,B value){
            this.key=key;
            this.value=value;
        }
        public A getKey(){//获取该键值对的键
            return this.key;
        }
        public B getValue(){//获取该键值对的值
            return this.value;
        }
    } 
	private int size=0;
    private Entry<K,V>[] table;//哈希数组
    public int getPrime(int tableSize){//获得素数作为哈希表的大小
        int s=tableSize%2==1?tableSize:tableSize+1;
        int i=0;
        while(s<=MAXSIZE){
            for(i=(int)Math.sqrt(s);i>2;i--){
                if(s%i==0)break;
            }
            if(i==2&&(s-3)%4==0)break;
            else{s+=2;}
        }      
        return s;
    }
    @SuppressWarnings("unchecked")
	public MyHashMap(){//构造哈希表
        this.tableSize=this.getPrime(1000);//初始化哈希表规模
        this.table=new Entry[this.tableSize];//初始化哈希表数组
    }
    @SuppressWarnings("unchecked")
	public MyHashMap(int n){//构造哈希表
        this.tableSize=this.getPrime(n);//初始化哈希表规模
        this.table=new Entry[this.tableSize];//初始化哈希表数组
    }
    /**
     * 得到散列地址
     * @param key
     * @return
     */
    private int getAddress(K key){
        String strKey=String.valueOf(key);
        int index=0;
        for(char each:strKey.toCharArray()){
            index=index<<5+each-'0';
        }
        return index%this.tableSize;
    }
    /**
     * 查看是否包含键
     * @param key
     * @return
     */
    public boolean containsKey(K key){
        if(get(key)==null)return false;
        return true;
    }
    /**
     * 发生冲突时，得到新的散列地址
     * @param key
     * @return
     */
    private int getNewIndex(K key){//解决冲突，得到新的索引
        int index=getAddress(key);
        int newIndex=index;
        if(table[index]==null)return index;
        else{
            int cNum=1; 
            while(table[newIndex]!=null&&!table[newIndex].getKey().equals(key)) {
                if(++cNum%2==0){
                    newIndex=(index+(cNum*cNum/4))%this.tableSize;
//                    System.out.println(cNum);
//                    System.out.println(newIndex);
                    while(newIndex<0) {
                    	newIndex+=this.tableSize;
                    }
                }
                else{
                    newIndex=(index-(cNum*cNum/4))%this.tableSize;
                    if(newIndex<0)newIndex+=this.tableSize;
                }
            }
        }
        return newIndex;
    }
    /**
     * 按对应的键返回键值对
     * @param key
     * @return
     */
    public Entry<K,V> get(K key){
        int index=getNewIndex(key);
        return table[index];
    }
    /**
     * 按对应的键返回值
     * @param key
     * @return
     */
    public V getValue(K key){
    	try {
    		int index=getNewIndex(key);
            return table[index].getValue();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("\n该键值对不存在！");
			e.printStackTrace();
		}
    	return null;
    }
    /**
     * 增加键值对
     * @param key
     * @param value
     * @return
     */
    public boolean put(K key,V value){//增加键值对
    	if(this.isFull()==true)return false;
        Entry<K,V> pair=new Entry<>(key,value);
        int index=getNewIndex(key);
        if(this.table[index]!=null) {
        	System.out.println("\n该键值对已存在！");
        }
        else{
            this.table[index]=pair;
            size++;
            return true;
        }
        return false;
    }
    /**
     * 将键集合返回作为HashSet
     * @return
     */
    public List<K> keySet(){
        List<K> set=new LinkedList<>();
        for(Entry<K,V> each:table){
            if(each!=null)
            set.add(each.getKey());
        }
        return set;
    }
    /**
     * 删除键值对
     * @param key
     * @return
     */
    public boolean remove(K key){
        int index=getNewIndex(key);
        if(this.table[index]==null)return false;
        else{
            this.table[index]=null;
            size--;
        }
        return true;
    }
    /**
     * 返回哈希表的大小
     * @return
     */
    public int size() {
    	return size;
	}
    public boolean isFull() {
    	if(size==tableSize)return true;
    	else return false;
    }
    /**
     * 清空哈希表
     */
    public void clear() {
    	for(int i=0;i<tableSize;i++) {
    		table[i]=null;
    	}
    	size=0;
	}
    public void printAll() {
    	for(Entry<K,V> each:this.table) {
    		if(each!=null)System.out.printf("key:%s val:%d\n",String.valueOf(each.getKey()),each.getValue());
    	}
    }
}
