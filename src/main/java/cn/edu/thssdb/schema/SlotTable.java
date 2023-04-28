package cn.edu.thssdb.schema;

import cn.edu.thssdb.buffer.BufferPoolManager;
import cn.edu.thssdb.storage.Page;
import cn.edu.thssdb.storage.TablePage;
import cn.edu.thssdb.storage.TablePageSlot;
import cn.edu.thssdb.utils.Global;

public class SlotTable extends Table {
  public SlotTable(BufferPoolManager bufferPoolManager, int firstPageId, Table.OpenFlag flag)
      throws Exception {
    super(bufferPoolManager, firstPageId, flag);
    TablePage tablePage = fetchTablePage(bufferPoolManager_.fetchPage(firstPageId));
    slotSize_ = tablePage.getSlotSize();
  }

  public SlotTable(BufferPoolManager bufferPoolManager, int slotSize, Table.NewFlag flag)
      throws Exception {
    super(bufferPoolManager, slotSize, flag);
  }

  // factory
  public static SlotTable newSlotTable(BufferPoolManager bufferPoolManager, int slotSize)
      throws Exception {
    return new SlotTable(bufferPoolManager, slotSize, Table.NewFlag.INSTANCE);
  }

  public static SlotTable openSlotTable(BufferPoolManager bufferPoolManager, int firstPageId)
      throws Exception {
    return new SlotTable(bufferPoolManager, firstPageId, Table.OpenFlag.INSTANCE);
  }

  @Override
  protected TablePage newTablePage(Page p, int slotSize) {
    if (p == null) {
      return null;
    }
    TablePage tablePage = new TablePageSlot(p);
    Object[] data = new Object[1];
    data[0] = slotSize;
    tablePage.init(data);
    return tablePage;
  }

  @Override
  protected TablePage fetchTablePage(Page p) {
    return new TablePageSlot(p);
  }

  @Override
  protected int getPageMaxTupleSize() {
    return Global.PAGE_SIZE - TablePageSlot.PAGE_HEADER_SIZE - 1;
  }
}
