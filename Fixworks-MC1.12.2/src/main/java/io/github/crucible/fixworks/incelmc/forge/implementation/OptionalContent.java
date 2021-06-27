package io.github.crucible.fixworks.incelmc.forge.implementation;

public class OptionalContent<T> {

    private boolean hasContent = false;
    private T content;

    public void setContent(T content) {
        this.content = content;
        this.hasContent = true;
    }

    public T getContent() {
        return this.content;
    }

    public boolean hasContent() {
        return this.hasContent;
    }
}
