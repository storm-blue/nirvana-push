package com.nirvana.ezalor.core.message;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Nirvana on 2017/11/15.
 */
public class Package implements CardBox {

    private PackageType type;

    private MessageLevel level;

    private Object id;

    private boolean retain = false;

    private CardBox box;

    public Package(PackageType type) {
        this(type, MessageLevel.NO_CONFIRM, null, false);
    }

    public Package(PackageType type, MessageLevel level) {
        this(type, level, null, false);
    }

    public Package(PackageType type, MessageLevel level, Object id) {
        this(type, level, id, false);
    }

    public Package(PackageType type, MessageLevel level, Object id, boolean retain) {
        this(type, level, id, retain, new DefaultCardBox());
    }

    public Package(PackageType type, MessageLevel level, Object id, boolean retain, CardBox box) {
        this.type = type;
        this.level = level;
        this.id = id;
        this.retain = retain;
        this.box = box;
    }

    @Override
    public void addCard(Card card) {
        box.addCard(card);
    }

    @Override
    public Card getCard(int index) {
        return box.getCard(index);
    }

    @Override
    public Card getCard(String name) {
        return box.getCard(name);
    }

    @Override
    public int size() {
        return box.size();
    }

    @Override
    public Collection<String> names() {
        return box.names();
    }

    public Object getCardContent(int index) {
        Card card = getCard(index);
        if (card == null) {
            return null;
        }
        return card.getContent();
    }

    public Object getCardContent(String name) {
        Card card = getCard(name);
        if (card == null) {
            return null;
        }
        return card.getContent();
    }

    public void addContent(Object content) {
        addCard(new SimpleCard(content));
    }

    public PackageType getType() {
        return type;
    }

    public MessageLevel getLevel() {
        return level;
    }

    public Object getId() {
        return id;
    }

    public void loadBox(CardBox box) {
        for (Card card : box) {
            this.box.addCard(card);
        }
    }

    public boolean isRetain() {
        return retain;
    }

    @Override
    public Iterator<Card> iterator() {
        return box.iterator();
    }
}
