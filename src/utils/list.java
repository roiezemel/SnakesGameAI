package utils;

import java.util.LinkedList;

public class list extends tools{

    protected LinkedList<Object> l = new LinkedList<>();

    public list() {}

    /**
     * Adds a list of objects to the list.
     * @param elements
     */
    public list(Object... elements){
        for (Object o : elements){
            l.add(o);
        }
    }

    /**
     * Returns a value from a specific position in the list.
     * @param indexes
     * @return The element in the given position.
     */
    public Object get(int... indexes){
        Object pos = this;
        int index = indexes[0];
        for (int i : indexes){
            if (pos.getClass() != ((list) pos).l.get(i).getClass()) {
                pos = ((list) pos).l.get(i);
                return pos;
            }
            pos = ((list) pos).l.get(i);
        }
        return pos;
    }

    /**
     * Adds a new element to the list.
     * @param o
     */
    public void add(Object o){
        l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(int... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(double... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(float... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(String... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(char... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds an array of elements to the list.
     * @param elements
     */
    public void add(boolean... elements){
        for (Object o : elements)
            l.add(o);
    }

    /**
     * Adds another list's elements to the list.
     * @param lst
     */
    public void add(list lst){
        for (Object o : lst.toArray())
            l.add(o);
    }

    /**
     * Converts the list into a String array.
     * @return Array of type String.
     */
    public String[] tostring(){
        String[] item = new String[size()];
        for (int i = 0; i<size(); i++){
            item[i] = tostring(l.get(i));
        }
        return item;
    }

    /**
     * Not for outside library use. Use len() from utils.tools to get the list's size.
     * @return The size of the one - dimensional list.
     */
    protected int size(){return l.size();}

    /**
     * Converts the list into an Object array.
     * @return Array of type Object.
     */
    public Object[] toArray(){return l.toArray();}

    public double[] toDoubleArray(){
        double[] item = new double[l.size()];
        for (int i = 0; i<size(); i++){
            item[i] = (double) l.get(i);
        }
        return item;
    }

    /**
     * Removes an object from the list at given index.
     * @param index
     */
    public void remove(int index){
        l.remove(index);
    }

    /**
     * Removes an object from the list.
     * @param o
     */
    public void remove(Object o){
        l.remove(o);
    }

    public int indexof(Object o){
        return l.indexOf(o);
    }

}
