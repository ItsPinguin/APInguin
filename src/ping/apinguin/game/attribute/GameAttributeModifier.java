package ping.apinguin.game.attribute;

import org.json.simple.JSONObject;

import java.util.List;

public class GameAttributeModifier {
  private double value = 0d;
  private GameAttributeModifier.Operation operation = Operation.ADD;
  private String attribute;

  public GameAttributeModifier(double value, Operation operation, String attribute) {
    this.value = value;
    this.operation = operation;
    this.attribute = attribute;
  }

  public GameAttributeModifier(Operation operation, String attribute) {
    this.operation = operation;
    this.attribute = attribute;
  }

  public GameAttributeModifier(Object object, String gameAttribute) {
    if (!(object instanceof JSONObject)) {
      value = (Double.parseDouble(object.toString()));
    } else {
      JSONObject jsonObject2 = (JSONObject) object;
      value = Double.parseDouble(jsonObject2.getOrDefault("value", "0").toString());
      operation = GameAttributeModifier.Operation.valueOf((String) jsonObject2.getOrDefault("operation", "ADD"));
    }
    attribute = gameAttribute;
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

  public enum Operation {
    ADD,
    ADD_PERCENT
  }

  public String getAttribute() {
    return attribute;
  }

  public static double calculate(List<GameAttributeModifier> gameAttributes) {
    double ADD = 0, ADD_MULTIPLY = 0;
    for (GameAttributeModifier gameAttribute : gameAttributes) {
      switch (gameAttribute.getOperation()) {
        case ADD -> ADD += gameAttribute.getValue();
        case ADD_PERCENT -> ADD_MULTIPLY += gameAttribute.getValue() / 100;
      }
    }
    ADD_MULTIPLY *= ADD;
    return ADD + ADD_MULTIPLY;
  }
}
