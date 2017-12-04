package com.nirvana.push.core.message;

/**
 * Created by Nirvana on 2017/11/15.
 */
public class SimpleCard extends AbstractCard<Object> {

    private Object content;

    public SimpleCard(Object content) {
        this.content = content;
    }

    public SimpleCard(String name, Object content) {
        super(name);
        this.content = content;
    }

    @Override
    public Object getContent() {
        return content;
    }
}
