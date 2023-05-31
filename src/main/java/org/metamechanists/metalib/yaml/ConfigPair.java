package org.metamechanists.metalib.yaml;

public class ConfigPair<T, U> {
    public T key;
    public U traverser;

    public ConfigPair(T key, U traverser) {
        this.key = key;
        this.traverser = traverser;
    }
}
