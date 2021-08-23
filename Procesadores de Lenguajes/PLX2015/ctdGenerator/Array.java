package ctdGenerator;

import java.util.*;

public class Array {

    private List list;
    private String type;
    private Integer size;

    public Array (List list, String size, String type){
        this.list = list;
        this.type = type;
        this.size = Integer.parseInt(size);
    }

    public List getList(){
        return this.list;
    }

    public String getType(){
        return this.type;
    }

    public void setList(List list){
        this.list = list;
    }

    public void setType(String type){
        this.type = type;
    }

    public int size(){
        return this.size;
    }

    public void add (Object o){
        list.add(o);
    }

    public void setValue(Integer i, Object o){
        list.set(i, o);
    }

    public void remove(Integer i){
        list.remove(i);
    }

    public Object get(Integer i){
        return list.get(i);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i<list.size(); i++){
            sb.append(list.get(i).toString());
            if (i<list.size()-1){
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}