package ping.mc.game.attribute;

import java.util.List;

public class GameAttributeModifier {
    private double value=0d;
    private GameAttributeModifier.Operation operation = Operation.ADD;
    private GameAttribute attribute;

    public GameAttributeModifier(double value, Operation operation,GameAttribute attribute) {
        this.value = value;
        this.operation = operation;
        this.attribute = attribute;
    }

    public GameAttributeModifier(Operation operation, GameAttribute attribute) {
        this.operation = operation;
        this.attribute = attribute;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Operation getOperation() {
        return operation;
    }

    public enum Operation{
        ADD,
        ADD_PERCENT
    }

    public GameAttribute getAttribute() {
        return attribute;
    }

    public static double calculate(List<GameAttributeModifier> gameAttributes){
        double ADD = 0, ADD_MULTIPLY=0;
        for (GameAttributeModifier gameAttribute : gameAttributes) {
            switch (gameAttribute.getOperation()){
                case ADD -> ADD+=gameAttribute.getValue();
                case ADD_PERCENT -> ADD_MULTIPLY+=gameAttribute.getValue()/100;
            }
        }
        ADD_MULTIPLY*=ADD;
        return ADD+ADD_MULTIPLY;
    }
}
