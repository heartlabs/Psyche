package util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by e1027424 on 07.01.16.
 */
public class Bla {
    public static class StringRepresentation<A> {
        public final A original;

        public StringRepresentation(A original) {
            this.original = original;
        }

        @Override
        public String toString(){
            return original.toString();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            if (obj.getClass() != getClass()) {
                return false;
            }
            return new EqualsBuilder()
                    //           .appendSuper(super.equals(obj))
                    .append(toString(), obj.toString())
                    .isEquals();
        }

        @Override
        public int hashCode() {
            // you pick a hard-coded, randomly chosen, non-zero, odd number
            // ideally different for each class
            return new HashCodeBuilder(17, 37).
                    append(toString()).
                    toHashCode();
        }
    }
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
                new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static String format (Double value){
        DecimalFormat myFormatter = new DecimalFormat("0.##% ");

        String result = myFormatter.format(value);

        return StringUtils.rightPad(result, 8, '.');



  /*
        String s = String.format("%.10f%%", value);

        //if after the first non-0-digit after the decimal point there occur more than one 0s in a row, the rest is ignored
        // 0,00004005 => 0,00004
        // 1245,5004 => 1245,5
        // 10005,0504030201 => 10005,0504030201
        Pattern pattern = Pattern.compile("\\d*,0*(0{0,1}[1-9]+)*");
        Matcher matcher = pattern.matcher(s);
        matcher.find();
        return matcher.group(0);*/
    }

    public static  <A> boolean setEquals(Set<A> set1, Set<A> set2){
        return set1.containsAll(set2) && set2.containsAll(set1);
    }

    public static <A> Set<StringRepresentation<A>> stringRepresentationSet(Set<A> set){
        Set<StringRepresentation<A>> stringSet1 = new HashSet<>();

        for (A a: set){
            stringSet1.add(new StringRepresentation<>(a));
        }

        return stringSet1;
    }

    public static <A> boolean stringRepresentationSetEquals(Set<A> set1, Set<A> set2){
        Set<StringRepresentation<A>> stringSet1 = stringRepresentationSet(set1);
        Set<StringRepresentation<A>> stringSet2 = stringRepresentationSet(set2);

        return stringSet1.equals(stringSet2);
    }
}
