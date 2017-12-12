package com.nirvana.ezalor.protocol.l2;

import com.nirvana.ezalor.core.message.Card;
import com.nirvana.ezalor.core.message.CardBox;
import com.nirvana.ezalor.core.message.DefaultCardBox;
import com.nirvana.ezalor.core.message.SimpleCard;
import com.nirvana.ezalor.protocol.exception.ProtocolException;

/**
 * DST文本协议由换行符分割的DST元素组成。
 * DST元素是描述DST协议中的基本元素，它有两种形式。
 * <p>
 * ===============================简单形式================================
 * 以'-'开头的值。下面都是合法的格式：
 * [-tom]
 * [-tom-and-jerry]
 * [-]
 * ===============================复合形式================================
 * key-value形式，以kv之间用'-'分隔符隔开的。如果元素中有多个'-'号，视第一个'-'号为分隔符。
 * 下面都是合法的格式：
 * [username-rose]
 * [password-rose-love-jack]
 * [password-]
 * [1-tom]
 * [1::a-tom]
 * [1::b-jack]
 * ===============================特殊字符=================================
 * 特殊字符有'-','~','\r','\n',key值中不允许出现特殊字符。value中如果出现特殊字符，则需要转码处理。
 * -
 * Created by Nirvana on 2017/9/4.
 */
public class DSTPackage implements L2ProtocolObject {

    /*协议包原始文本*/
    private String content;

    private CardBox cardBox;

    /**
     * 从Elements构建一个DST协议包.
     */
    public DSTPackage(CardBox cardBox) {
        this.cardBox = cardBox;
        StringBuilder contentBuilder = new StringBuilder();
        for (Card card : cardBox) {
            String key = card.getName() == null ? "" : card.getName();
            Object cardContent = card.getContent();
            if (!(cardContent instanceof String)) {
                throw new L2ProtocolException("Unsupported element value type: " + content.getClass().getSimpleName());
            }
            String value = (String) cardContent;
            String line = key + DSTDefinition.SEPARATOR + DSTDefinition.encode(value);
            contentBuilder.append(line).append('\n');
        }
        content = contentBuilder.toString();
    }

    /**
     * 由一个协议文本构建协议包，对文本进行解析校验。
     */
    public DSTPackage(String data) {
        this.content = data;
        cardBox = new DefaultCardBox();

        int index = 0;
        boolean crossSeparator = false;
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        while (index < data.length()) {
            char c = data.charAt(index);
            if (c == '\r' || c == '\n') {
                StringBuilder delimiter = new StringBuilder();
                delimiter.append(c);

                while (index + 1 < data.length()) {
                    c = data.charAt(index + 1);
                    if (c == '\n' || c == '\r') {
                        delimiter.append(c);
                        index++;
                    } else {
                        break;
                    }
                }
                if (!DSTDefinition.DELIMITERS.contains(delimiter.toString())) {
                    throw new ProtocolException("未知分隔符:index:" + index);
                }
                if (!crossSeparator) {
                    throw new ProtocolException("格式错误，缺少:\'" + DSTDefinition.SEPARATOR + "\'index:" + index);
                }

                Card card = new SimpleCard(keyBuilder.toString(), DSTDefinition.decode(valueBuilder.toString()));
                cardBox.addCard(card);

                keyBuilder = new StringBuilder();
                valueBuilder = new StringBuilder();
                crossSeparator = false;
            } else {
                if (index == data.length() - 1) {
                    throw new ProtocolException("协议解析错误，必须以分隔符结尾。");
                }
                if (c == DSTDefinition.SEPARATOR && !crossSeparator) {
                    crossSeparator = true;
                } else {
                    if (crossSeparator) {
                        valueBuilder.append(c);
                    } else {
                        keyBuilder.append(c);
                    }
                }
            }
            index++;
        }
    }

    /**
     * 获取此协议包中真实文本。
     */
    public String getContent() {
        return content;
    }

    @Override
    public CardBox getCardBox() {
        return cardBox;
    }


}
