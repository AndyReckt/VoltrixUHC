package me.shir.uhcp.util;

import java.util.*;

public class ValueComparator implements Comparator<String>
{
    private Map<String, Integer> base;
    
    public ValueComparator(final Map<String, Integer> base) {
        this.base = base;
    }
    
    @Override
    public int compare(final String a, final String b) {
        if (this.base.get(a) >= this.base.get(b)) {
            return -1;
        }
        return 1;
    }
}
