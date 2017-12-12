package com.nirvana.ezalor.core.message;

import com.nirvana.ezalor.utils.StringUtils;

/**
 * Created by Nirvana on 2017/11/15.
 */
public abstract class AbstractCard<T> implements Card<T> {

    private String name;

    public AbstractCard() {}

    public AbstractCard(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean anonymous() {
        return StringUtils.isBlank(name);
    }

}
