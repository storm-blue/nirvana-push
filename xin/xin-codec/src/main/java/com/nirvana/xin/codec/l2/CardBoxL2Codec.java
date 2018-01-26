package com.nirvana.xin.codec.l2;

import com.nirvana.xin.codec.L2ProtocolException;
import com.nirvana.xin.codec.ProtocolException;
import com.nirvana.purist.core.message.Card;
import com.nirvana.purist.core.message.CardBox;
import com.nirvana.purist.core.message.DefaultCardBox;
import com.nirvana.purist.core.message.SimpleCard;

/**
 * Created by Nirvana on 2018/1/16.
 */
public class CardBoxL2Codec {

    private static final CardBoxL2Codec INSTANCE = new CardBoxL2Codec();

    private CardBoxL2Codec() {}

    public static CardBoxL2Codec getInstance() {
        return INSTANCE;
    }

    public CardBox decode(String content) {
        CardBox cardBox = new DefaultCardBox();

        int index = 0;
        boolean crossSeparator = false;
        StringBuilder keyBuilder = new StringBuilder();
        StringBuilder valueBuilder = new StringBuilder();

        while (index < content.length()) {
            char c = content.charAt(index);
            if (c == DSTDefinition.DELIMITER) {

                if (!crossSeparator) {
                    throw new ProtocolException("a '" + DSTDefinition.SEPARATOR + "' expected at index:" + index);
                }

                String key = DSTDefinition.decode(keyBuilder.toString());
                String valueString = valueBuilder.toString();
                Object value;
                if (valueString.startsWith("[") && valueString.endsWith("]")) {
                    value = this.decode(valueString.substring(1, valueString.length() - 1));
                } else {
                    value = DSTDefinition.decode(valueString);
                }
                Card card = new SimpleCard(key, value);
                cardBox.addCard(card);

                keyBuilder = new StringBuilder();
                valueBuilder = new StringBuilder();
                crossSeparator = false;
            } else {
                if (index == content.length() - 1) {
                    throw new ProtocolException("no delimiter find at the end of content.");
                }
                if (c == DSTDefinition.SEPARATOR && !crossSeparator) {
                    crossSeparator = true;
                    if (content.charAt(index + 1) == '[') {
                        index++;
                        int left = 0;
                        while (index < content.length()) {
                            char x = content.charAt(index);
                            if (x == DSTDefinition.LEFT_BRACKET) {
                                left++;
                            } else if (x == DSTDefinition.RIGHT_BRACKET) {
                                left--;
                            }
                            valueBuilder.append(x);
                            if (left == 0) {
                                break;
                            }
                            index++;
                        }
                        if (left != 0) {
                            throw new ProtocolException("the number of '" + DSTDefinition.RIGHT_BRACKET + "' not match '" + DSTDefinition.LEFT_BRACKET + "'.");
                        }
                        if (index == content.length() - 1 || content.charAt(index + 1) != DSTDefinition.DELIMITER) {
                            throw new ProtocolException("no delimiter find after '" + DSTDefinition.RIGHT_BRACKET + "'.");
                        }
                    }
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
        return cardBox;
    }

    public String encode(CardBox box) {
        StringBuilder contentBuilder = new StringBuilder();
        for (Card card : box) {
            String key = card.getName() == null ? "" : card.getName();
            Object cardContent = card.getContent();
            if (cardContent instanceof String) {
                String value = (String) cardContent;
                contentBuilder
                        .append(DSTDefinition.encode(key))
                        .append(DSTDefinition.SEPARATOR)
                        .append(DSTDefinition.encode(value))
                        .append('\n');
            } else if (cardContent instanceof CardBox) {
                contentBuilder
                        .append(DSTDefinition.encode(key))
                        .append(DSTDefinition.SEPARATOR)
                        .append(DSTDefinition.LEFT_BRACKET)
                        .append(this.encode((CardBox) cardContent))
                        .append(DSTDefinition.RIGHT_BRACKET)
                        .append('\n');
            } else {
                throw new L2ProtocolException("Unsupported element value type: " + cardContent.getClass().getSimpleName());
            }
        }
        return contentBuilder.toString();
    }

}
