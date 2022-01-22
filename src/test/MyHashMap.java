package test;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author z
 *
 * @param <K>��������
 * @param <V>ֵ������
 * 
 */
public class MyHashMap<K,V>{
    static private int MAXSIZE=10000;//Ԫ���������
    private int tableSize;//��ϣ��Ĵ�С
	class Entry<A,B>{//��ֵ��
        private A key;
        private B value;
        public Entry(A key,B value){
            this.key=key;
            this.value=value;
        }
        public A getKey(){//��ȡ�ü�ֵ�Եļ�
            return this.key;
        }
        public B getValue(){//��ȡ�ü�ֵ�Ե�ֵ
            return this.value;
        }
    } 
	private int size=0;
    private Entry<K,V>[] table;//��ϣ����
    public int getPrime(int tableSize){//���������Ϊ��ϣ��Ĵ�С
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
	public MyHashMap(){//�����ϣ��
        this.tableSize=this.getPrime(1000);//��ʼ����ϣ���ģ
        this.table=new Entry[this.tableSize];//��ʼ����ϣ������
    }
    @SuppressWarnings("unchecked")
	public MyHashMap(int n){//�����ϣ��
        this.tableSize=this.getPrime(n);//��ʼ����ϣ���ģ
        this.table=new Entry[this.tableSize];//��ʼ����ϣ������
    }
    /**
     * �õ�ɢ�е�ַ
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
     * �鿴�Ƿ������
     * @param key
     * @return
     */
    public boolean containsKey(K key){
        if(get(key)==null)return false;
        return true;
    }
    /**
     * ������ͻʱ���õ��µ�ɢ�е�ַ
     * @param key
     * @return
     */
    private int getNewIndex(K key){//�����ͻ���õ��µ�����
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
     * ����Ӧ�ļ����ؼ�ֵ��
     * @param key
     * @return
     */
    public Entry<K,V> get(K key){
        int index=getNewIndex(key);
        return table[index];
    }
    /**
     * ����Ӧ�ļ�����ֵ
     * @param key
     * @return
     */
    public V getValue(K key){
    	try {
    		int index=getNewIndex(key);
            return table[index].getValue();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("\n�ü�ֵ�Բ����ڣ�");
			e.printStackTrace();
		}
    	return null;
    }
    /**
     * ���Ӽ�ֵ��
     * @param key
     * @param value
     * @return
     */
    public boolean put(K key,V value){//���Ӽ�ֵ��
    	if(this.isFull()==true)return false;
        Entry<K,V> pair=new Entry<>(key,value);
        int index=getNewIndex(key);
        if(this.table[index]!=null) {
        	System.out.println("\n�ü�ֵ���Ѵ��ڣ�");
        }
        else{
            this.table[index]=pair;
            size++;
            return true;
        }
        return false;
    }
    /**
     * �������Ϸ�����ΪHashSet
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
     * ɾ����ֵ��
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
     * ���ع�ϣ��Ĵ�С
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
     * ��չ�ϣ��
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
