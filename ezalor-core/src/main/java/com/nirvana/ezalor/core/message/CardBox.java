package com.nirvana.ezalor.core.message;

import java.util.Collection;

/**
 * Created by Nirvana on 2017/11/15.
 */
public interface CardBox extends Iterable<Card> {

    void addCard(Card card);

    Card getCard(int index);

    Card getCard(String name);

    int size();

    Collection<String> names();

}
