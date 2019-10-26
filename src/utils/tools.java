package utils;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class tools {

    //////////////// CONSOLE INTERACTION ////////////////

    /**
     * Wait for the user to give an input.
     * @param message
     * @return User's input.
     */
    public static String input(String message) {
        Scanner input = new Scanner(System.in);
        System.out.println(message);
        return input.nextLine();
    }

    /**
     * Wait for the user to give an input.
     * @param message
     * @param input
     * @return User's input.
     */
    public static String input(String message, Scanner input){
        System.out.println(message);
        return input.nextLine();
    }

    /**
     * Print a value to the console.
     * @param input
     */
    public static void print(Object input){
        if (input instanceof list)
            print(aslist(input));
        else
            System.out.println(input);
    }

    public static void print(Object[] input){
        String[] items = new String[input.length];
        for (int i = 0; i<input.length; i++){
            items[i] = tostring(input[i]);
        }
        print("[" + join(", ", items) + "]");
    }

    public static void print(list l){
        print(tostring(l));
    }

     //////////////// CASTS ////////////////

    /**
     * Cast to string.
     * @param value
     * @return
     */
    public static String tostring(Object value) {
        if (value instanceof list)
            return tostring(aslist(value));
        return value + "";
    }

    /**
     * Cast to string.
     * @param value
     * @return
     */
    public static String tostring(list value) {
        Object[] input = value.toArray();
        String[] items = new String[input.length];
        for (int i = 0; i<input.length; i++){
            if (input[i].getClass() == value.getClass())
                items[i] = tostring((list) input[i]);
            else
                items[i] = tostring(input[i]);
        }
        return "[" + join(", ", items) + "]";
    }

    /**
     * Cast to int.
     * @param value
     * @return
     */
    public static int toint(Object value) {
        return (int) todouble(value);
    }

    /**
     * Cast boolean to int.
     * @param value
     * @return 0 if true, 1 if false.
     */
    public static int toint(boolean value) {
        return value?0:1;
    }

    /**
     * Cast to int.
     * @param value
     * @return
     */
    public static int toint(String value) {
        return Integer.parseInt(value);
    }

    /**
     * Cast to int.
     * @param value
     * @return
     */
    public static int toint(char value) {
        return toint(tostring(value));
    }

    /**
     * Cast to double.
     * @param value
     * @return
     */
    public static double todouble(Object value){
        return todouble(tostring(value));
    }

    /**
     * Cast to double.
     * @param value
     * @return
     */
    public static double todouble(String value){
        return Double.parseDouble(value);
    }

    /**
     * Cast to double.
     * @param value
     * @return
     */
    public static double todouble(char value){
        return todouble(tostring(value));
    }

    /**
     * Cast to float.
     * @param value
     * @return
     */
    public static float tofloat(Object value){
        return tofloat(tostring(value));
    }

    /**
     * Cast to float.
     * @param value
     * @return
     */
    public static float tofloat(String value){
        return Float.parseFloat(value);
    }

    /**
     * Cast to float.
     * @param value
     * @return
     */
    public static float tofloat(char value){
        return tofloat(tostring(value));
    }

    /**
     * Cast int to boolean.
     * @param value
     * @return true if 0, false else.
     */
    public static boolean toboolean(int value){
        return value == 0;
    }

    /**
     * Cast String to boolean.
     * @param value
     * @return
     */
    public static boolean toboolean(String value){
        return value.equals("true");
    }

    /**
     * Cast array to String array.
     * @param values
     * @return
     */
    public static String[] tostring(int[] values){
       String[] items = new String[values.length];
       for (int i = 0; i<values.length; i++){
           items[i] = tostring(values[i]);
       }
       return items;
    }

    /**
     * Cast array to String array.
     * @param values
     * @return
     */
    public static String[] tostring(double[] values){
        String[] items = new String[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tostring(values[i]);
        }
        return items;
    }

    /**
     * Cast array to String array.
     * @param values
     * @return
     */
    public static String[] tostring(float[] values){
        String[] items = new String[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tostring(values[i]);
        }
        return items;
    }

    /**
     * Cast array to String array.
     * @param values
     * @return
     */
    public static String[] tostring(char[] values){
        String[] items = new String[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tostring(values[i]);
        }
        return items;
    }

    /**
     * Cast array to String array.
     * @param values
     * @return
     */
    public static String[] tostring(boolean[] values){
        String[] items = new String[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tostring(values[i]);
        }
        return items;
    }

    /**
     * Cast array to int array.
     * @param values
     * @return
     */
    public static int[] toint(String[] values){
        int[] items = new int[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to int array.
     * @param values
     * @return
     */
    public static int[] toint(Object[] values){
        int[] items = new int[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to int array.
     * @param values
     * @return
     */
    public static int[] toint(char[] values){
        int[] items = new int[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to int array.
     * @param values
     * @return
     */
    public static int[] toint(boolean[] values){
        int[] items = new int[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to double array.
     * @param values
     * @return
     */
    public static double[] todouble(String[] values){
        double[] items = new double[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = todouble(values[i]);
        }
        return items;
    }

    /**
     * Cast array to double array.
     * @param values
     * @return
     */
    public static double[] todouble(Object[] values){
        double[] items = new double[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = todouble(values[i]);
        }
        return items;
    }

    /**
     * Cast array to double array.
     * @param values
     * @return
     */
    public static double[] todouble(char[] values){
        double[] items = new double[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = todouble(values[i]);
        }
        return items;
    }

    /**
     * Cast array to double array.
     * @param values
     * @return
     */
    public static double[] todouble(boolean[] values){
        double[] items = new double[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to float array.
     * @param values
     * @return
     */
    public static float[] tofloat(String[] values){
        float[] items = new float[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tofloat(values[i]);
        }
        return items;
    }

    /**
     * Cast array to float array.
     * @param values
     * @return
     */
    public static float[] tofloat(Object[] values){
        float[] items = new float[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tofloat(values[i]);
        }
        return items;
    }

    /**
     * Cast array to float array.
     * @param values
     * @return
     */
    public static float[] tofloat(char[] values){
        float[] items = new float[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = tofloat(values[i]);
        }
        return items;
    }

    /**
     * Cast array to float array.
     * @param values
     * @return
     */
    public static float[] tofloat(boolean[] values){
        float[] items = new float[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toint(values[i]);
        }
        return items;
    }

    /**
     * Cast array to boolean array.
     * @param values
     * @return
     */
    public static boolean[] toboolean(String[] values){
        boolean[] items = new boolean[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toboolean(values[i]);
        }
        return items;
    }

    /**
     * Cast array to boolean array.
     * @param values
     * @return
     */
    public static boolean[] toboolean(int[] values){
        boolean[] items = new boolean[values.length];
        for (int i = 0; i<values.length; i++){
            items[i] = toboolean(values[i]);
        }
        return items;
    }

    //////////////// FILES ////////////////

    /**
     * Opens a new file.
     * @param path
     * @param mode 'w' for write mode, 'r' for read mode.
     * @return file object.
     */
    public static file open(String path, char mode) {
        return new file(path, mode);
    }

    /**
     * Deletes a file.
     * @param path
     */
    public static void delete(String path){
        new File(path).delete();
    }


    //////////////// STRING FUNCTIONS ////////////////

    /**
     * Joins a list of Strings with another String as a separator.
     * @param separator
     * @param values
     * @return
     */
    public static String join(String separator, String... values){
        String item = "";
        for (int i = 0; i<values.length; i++){
            if (i != 0)
                item += separator;
            item += values[i];
        }
        return item;
    }

    /**
     * Replaces one substring in a String with another.
     * @param str
     * @param substring
     * @param replace_by
     * @return
     */
    public static String replace(String str, String substring, String replace_by){
        return str.replaceAll(substring, replace_by);
    }

    /**
     * Determine if there is a substring at the start of a String.
     * @param str
     * @param value
     * @return
     */
    public static boolean startswith(String str, String value){
        return str.startsWith(value);
    }

    /**
     * Determine if there is a substring at the end of a String.
     * @param str
     * @param value
     * @return
     */
    public static boolean endswith(String str, String value){
        return str.endsWith(value);
    }

    /**
     * Changes the case of a String to Upper Case.
     * @param str
     * @return
     */
    public static String upper(String str){
        return str.toUpperCase();
    }

    /**
     * Changes the case of a String to Lower Case.
     * @param str
     * @return
     */
    public static String lower(String str){
        return str.toLowerCase();
    }

    /**
     * Splits a string with a certain separator into an array.
     * @param str
     * @param regex
     * @return
     */
    public static String[] split(String str, String regex){
        return str.split(regex);
    }

    //////////////// NUMERIC FUNCTIONS ////////////////

    /**
     * Return the minimum value of an array of numbers.
     * @param values
     * @return
     */
    public static double min(double... values){
        double min = values[0];
        for (double v : values){
            if (v < min)
                min = v;
        }
        return min;
    }

    /**
     * Return the minimum value of an array of numbers.
     * @param values
     * @return
     */
    public static double max(double... values){
        double max = values[0];
        for (double v : values){
            if (v > max)
                max = v;
        }
        return max;
    }

    public static int maxindex(double... values){
        int index = 0;
        for (int i = 0; i<values.length; i++){
            if (values[i] > values[index])
                index = i;
        }
        return index;
    }

    public static double[] remove(double[] values, int index){
        double[] item = new double[values.length-1];
        int counter = 0;
        for (int i = 0; i< item.length; i++){
            if (i != index) {
                item[counter] = values[i];
                counter++;
            }
        }
        return item;
    }

    /**
     * Returns the sum of all values in the array.
     * @param values
     * @return
     */
    public static double sum(double... values){
        double sum = 0;
        for (double v : values){
            sum += v;
        }
        return sum;
    }

    //////////////// LIST FUNCTIONS ////////////////

    /**
     * Returns a list object.
     * @param list
     * @return
     */
    public static list list(Object... list){
        return new list(list);
    }

    /**
     * Returns a list object.
     * @return
     */
    public static list list(){
        return new list();
    }

    /**
     * Casts an object to a list.
     * @param o
     * @return
     */
    public static list aslist(Object o){
        return (list) o;
    }

    /**
     * Casts an object to a list.
     * @param elements
     * @return
     */
    public static list aslist(Object... elements){
        list l = new list();
        for (Object o : elements) {
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(int[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(double[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(float[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(boolean[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(String[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Converts an array to a list.
     * @param arr
     * @return
     */
    public static list aslist(char[] arr){
        list l = new list();
        for (Object o : arr){
            l.add(o);
        }
        return l;
    }

    /**
     * Returns the length of an object.
     * @param o
     * @return - 1 if the object is not of the type list.
     */
    public static int len(Object o){
        if (o instanceof list)
            return aslist(o).size();
        else
            return 1;
    }

    /**
     * Checks a condition along a whole list.
     * @param l
     * @param condition
     * @return True if all elements evaluated to true.
     */
    public static boolean all(list l, Condition condition){
        for (Object o : l.toArray()){
            if (!condition.check(o)) return false;
        }
        return true;
    }

    /**
     * Checks a condition along a whole list.
     * @param l
     * @param condition
     * @return True if any (at least one) of the elements evaluated to true.
     */
    public static boolean any(list l, Condition condition){
        for (Object o : l.toArray()){
            if (condition.check(o)) return true;
        }
        return false;
    }

    /**
     * Returns an iterable version of the list.
     * @param l
     * @return
     */
    public static Object[] iterate(list l){
        return l.toArray();
    }

    /**
     * Can be used to iterate through the values and indices of a list simultaneously.
     * @param l
     * @return Two dimensional list containing the elements along with their indices.
     */
    public static list enumerate(list l){
        list item = list();
        for (int i = 0; i<len(l); i++){
            item.add((Object) list(i, l.get(i)));
        }
        return item;
    }

    /**
     * Returns a new list with a function applied to each element.
     * @param performance
     * @param l
     * @return New list.
     */
    public static list map(Performance performance, list l) {
        list item = new list();
        for (int i = 0; i<len(l); i++) {
            item.add(performance.perform(l.get(i)));
        }
        return item;
    }

    /**
     * Filters a list by removing items that don't match a predicate.
     * @param condition
     * @param l
     * @return New list, containing the filtered elements.
     */
    public static list filter(Condition condition, list l) {
        list item = new list();
        for (int i = 0; i<len(l); i++) {
            if (condition.check(l.get(i)))
                item.add(l.get(i));
        }
        return item;
    }

}
