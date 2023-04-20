package cn.edu.thssdb.schema;

import cn.edu.thssdb.utils.Tuple2;

import java.io.Serializable;
import java.nio.ByteBuffer;

// assume all columns are inlined
// schema is a list of columns
// |------|---------|---------|---------|-----|
// | size | column1 | column2 | column3 | ... |
// |------|---------|---------|---------|-----|
// columns are separated by semicolon, so column name can't contain semicolon
public class Schema implements Serializable {
  private final Column[] columns_;
  private int size_;

  public Schema(Column[] columns) {
    columns_ = columns;
    size_ = columns_.length;
    int offset = 0;
    for (Column column : columns) {
      column.offset_ = offset;
      offset += column.getMaxLength();
    }
  }

  // getters
  public Column[] getColumns() {
    return columns_;
  }

  public Column getColumn(String name) {
    for (Column column : columns_) {
      if (column.getName().equals(name)) {
        return column;
      }
    }
    return null;
  }

  public Column getColumn(int index) {
    if (index < 0 || index >= columns_.length) {
      throw new IndexOutOfBoundsException("getcolumn Index out of bound!");
    }
    return columns_[index];
  }

  public int getSize() {
    return size_;
  }

  public int getOffset(String name) {
    for (Column column : columns_) {
      if (column.getName().equals(name)) {
        return column.offset_;
      }
    }
    return -1;
  }

  // difference between toString and serialize:
  // toString is used for printing & debugging, and is human-readable
  // serialize is used for storage, it is compressed and can only decode by machine

  // toString
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Schema:\n");
    for (Column column : columns_) {
      sb.append(column.toString());
      sb.append("'\n ");
    }
    return sb.toString();
  }

  public void serialize(ByteBuffer buffer, int offset) {
    // size
    buffer.putInt(offset, size_);
    offset += 4;
    for (Column column : columns_) {
      offset = column.serialize(buffer, offset);
      buffer.put(offset, (byte)';');
      offset += 1;
    }
  }

  // WARNING: offset is changed after calling this function
  public static Tuple2<Schema, Integer> deserialize(ByteBuffer buffer, Integer offset) {
    // column one by one until end
    // column format: name,type,primary,nullable,maxLength,offset
    // separated by comma
    int size = buffer.getInt(offset);
    offset += 4;
    Column[] columns = new Column[size];
    for (int i = 0; i < size; i++) {
      Tuple2<Column, Integer> dsr_result = Column.deserialize(buffer, offset);
      columns[i] = dsr_result.getFirst();
      offset = dsr_result.getSecond();
    }
    return new Tuple2<>(new Schema(columns), offset);
  }
}
