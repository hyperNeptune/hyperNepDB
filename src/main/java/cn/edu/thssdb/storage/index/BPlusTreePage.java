package cn.edu.thssdb.storage.index;

import cn.edu.thssdb.storage.Page;
import cn.edu.thssdb.utils.Global;

// shared header for B+ tree pages
// | page header (Page ID, LSN)| pageType | currentSize | maxSize | parentPageId|
public class BPlusTreePage extends Page {
  public enum BTNodeType {
    LEAF,
    INTERNAL
  } // 4 bytes

  public static final int PAGE_TYPE_OFFSET = 0;
  public static final int CURRENT_SIZE_OFFSET = 4;
  public static final int MAX_SIZE_OFFSET = 8;
  public static final int PARENT_PAGE_ID_OFFSET = 12;
  public static final int B_PLUS_TREE_PAGE_HEADER_SIZE = 16;

  public BPlusTreePage(int page_id) {
    super(page_id);
  }

  // getter and setters
  public BTNodeType getPageType() {
    return data_.getInt(PAGE_TYPE_OFFSET) == 0 ? BTNodeType.LEAF : BTNodeType.INTERNAL;
  }

  public void setPageType(BTNodeType page_type) {
    data_.putInt(PAGE_TYPE_OFFSET, page_type == BTNodeType.LEAF ? 0 : 1);
  }

  public int getCurrentSize() {
    return data_.getInt(CURRENT_SIZE_OFFSET);
  }

  public void setCurrentSize(int current_size) {
    data_.putInt(CURRENT_SIZE_OFFSET, current_size);
  }

  public int getMaxSize() {
    return data_.getInt(MAX_SIZE_OFFSET);
  }

  public void setMaxSize(int max_size) {
    data_.putInt(MAX_SIZE_OFFSET, max_size);
  }

  public int getParentPageId() {
    return data_.getInt(PARENT_PAGE_ID_OFFSET);
  }

  public void setParentPageId(int parent_page_id) {
    data_.putInt(PARENT_PAGE_ID_OFFSET, parent_page_id);
  }

  public boolean isRootPage() {
    return getParentPageId() == Global.PAGE_ID_INVALID;
  }

  public boolean isFull() {
    return getCurrentSize() == getMaxSize();
  }

  public boolean isUnderflow() {
    return getCurrentSize() < getMaxSize() / 2;
  }

  public boolean increaseSize(int amount) {
    if (getCurrentSize() + amount > getMaxSize()) {
      return false;
    }
    setCurrentSize(getCurrentSize() + amount);
    return true;
  }
}
