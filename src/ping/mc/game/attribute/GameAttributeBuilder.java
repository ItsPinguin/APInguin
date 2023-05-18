package ping.mc.game.attribute;

import ping.utils.StringUtils;

public interface GameAttributeBuilder {
    default String build(GameAttributeModifier gameAttributeModifier){
        GameAttribute attribute=gameAttributeModifier.getAttribute();
        String value=StringUtils.valueToString(gameAttributeModifier.getValue());
        switch (gameAttributeModifier.getOperation()){
            case ADD -> {
                return attribute.getEffect()+value+attribute.getSymbol();
            }
            case ADD_PERCENT -> {
                return attribute.getEffect()+value+"%"+attribute.getSymbol();
            }
            default -> {
                return attribute.getEffect()+value+attribute.getSymbol();
            }
        }
    }
}