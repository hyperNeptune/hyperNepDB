package cn.edu.thssdb.query;

import cn.edu.thssdb.schema.Row;
import cn.edu.thssdb.schema.TableInfo;
import cn.edu.thssdb.utils.Cell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QueryResult {

  private List<TableInfo> metaInfos;
  private List<Integer> index;
  private List<Cell> attrs;

  public QueryResult(QueryTable[] queryTables) {
    // TODO
    this.index = new ArrayList<>();
    this.attrs = new ArrayList<>();
  }

  public static Row combineRow(LinkedList<Row> rows) {
    // TODO
    return null;
  }

  public Row generateQueryRecord(Row row) {
    // TODO
    return null;
  }
}
