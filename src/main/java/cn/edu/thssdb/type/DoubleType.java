package cn.edu.thssdb.type;

import cn.edu.thssdb.utils.Global;

import java.nio.ByteBuffer;

public class DoubleType extends Type {
  public static DoubleType INSTANCE = new DoubleType();

  static {
    Type.TYPES.put(INSTANCE.getTypeCode(), INSTANCE);
    Type.TYPEDICT.put("double", INSTANCE);
  }

  @Override
  public byte getTypeCode() {
    return BuiltinTypeCode.DOUBLE.value;
  }

  @Override
  public int getTypeSize() {
    return Global.DOUBLE_SIZE;
  }

  @Override
  public int getTypeCodeSize() {
    return 1;
  }

  @Override
  public String toString() {
    return "double";
  }

  @Override
  public Value<? extends DoubleType, Double> getNullValue() {
    return DoubleValue.NULL_VALUE;
  }

  @Override
  public Value<? extends DoubleType, Double> deserializeValue(ByteBuffer buffer, int offset) {
    return new DoubleValue(buffer.getDouble(offset));
  }

  @Override
  protected Type customDeserialize(ByteBuffer buffer, int offset) {
    return this;
  }
}
