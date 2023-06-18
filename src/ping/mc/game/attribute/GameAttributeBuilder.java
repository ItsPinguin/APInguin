package ping.mc.game.attribute;

import ping.utils.StringUtils;

public interface GameAttributeBuilder {
    default String build(GameAttributeModifier gameAttributeModifier){
        GameAttribute attribute=GameAttributes.attributes.get(gameAttributeModifier.getAttribute());
        String value=StringUtils.enhancedDouble(gameAttributeModifier.getValue());
        switch (gameAttributeModifier.getOperation()){
            case ADD_PERCENT -> {
                return attribute.getEffect()+value+"%"+attribute.getSymbol();
            }
            default -> {
                return attribute.getEffect()+value+attribute.getSymbol();
            }
        }
    }
}
