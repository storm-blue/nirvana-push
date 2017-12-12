package com.nirvana.ezalor.core.message;

import com.nirvana.ezalor.utils.StringUtils;

import java.util.*;

/**
 * Created by Nirvana on 2017/11/15.
 */
public class DefaultCardBox implements CardBox {

    private List<Card> cards = new ArrayList<>();

    private Map<String, Card> cardNameMap = new HashMap<>();

    public DefaultCardBox() {}

    public DefaultCardBox(Object... contents) {
        for (Object content : contents) {
            addCard(new SimpleCard(content));
        }
    }

    @Override
    public void addCard(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Parameter can not be null.");
        }
        if (StringUtils.isNotBlank(card.getName())) {
            if (cardNameMap.containsKey(card.getName())) {
                throw new IllegalArgumentException("There's already has a card names " + card.getName() + " exist in this box.");
            }
            cardNameMap.put(card.getName(), card);
        }
        cards.add(card);
    }

    @Override
    public Card getCard(int index) {
        if (index >= cards.size()) {
            return null;
        }
        return cards.get(index);
    }

    @Override
    public Card getCard(String name) {
        return cardNameMap.get(name);
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public Collection<String> names() {
        return cardNameMap.keySet();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }
}
