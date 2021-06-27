package io.github.crucible.fixworks.incelmc.excompressum.implementation;

import net.minecraft.item.Item;

import java.util.HashMap;

public class ItemDependentCache<T> {

    private final Item item;
    private final HashMap<Integer, OptionalContent<T>> metamap = new HashMap<>();

    public ItemDependentCache(Item item) {
        this.item = item;
    }

    public OptionalContent<T> addMeta(int meta, T content) {
        OptionalContent<T> optionalContent = new OptionalContent<T>(content);
        this.metamap.put(meta, optionalContent);
        return optionalContent;
    }

    public OptionalContent<T> getFromMeta(int meta) {
        return this.metamap.get(meta);
    }

    public static class OptionalContent<T> {
        T content;

        public OptionalContent(T content) {
            this.content = content;
        }

        public T getContent() {
            return this.content;
        }
    }
}
