package com.example.deanguo.myapplication.ui;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangzhennan on 14/6/26.
 */
public class SimpleListAdapter extends BaseAdapter {

    private List<Object> dataSource;
    private List<Object> headerDataSource;
    private List<Object> footerDataSource;
    private Map<Class, ItemRenderDelegate> onDataTypeListenerMap;
    private List<Class> dataTypeList;

    public List<Object> getDataSource() {
        if (null == dataSource) {
            dataSource = new ArrayList<Object>();
        }
        return dataSource;
    }

    public void setDataSource(List<Object> dataSource) {
        this.dataSource = dataSource;

        notifyDataSetChanged();
    }

    public void clear() {
        getDataSource().clear();
        getTopDataSource().clear();
        getBottomDataSource().clear();
        notifyDataSetChanged();
    }

    public void addItem(Object item) {
        addItem(item, getDataSource().size());
    }

    public void addItem(Object item, int index) {
        if (null == item)
            return;

        if (-1 < index && getDataSource().size() > index) {
            getDataSource().add(index, item);
        } else {
            getDataSource().add(item);
        }


        notifyDataSetChanged();
    }

    public void addItemAll(List<? extends Object> items) {
        if (null != items) {
            getDataSource().addAll(items);
            notifyDataSetChanged();
        }
    }

    public void addItemAll(List<? extends Object> items, boolean notify) {
        if (null != items) {
            getDataSource().addAll(items);

            if (notify) {
                notifyDataSetChanged();
            }
        }
    }

    public void removeItem(int index) {
        if (-1 < index && getDataSource().size() > index) {
            getDataSource().remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeItem(Object item) {
        int index = getDataSource().indexOf(item);
        removeItem(index);
    }

    public void removeAllItems() {
        removeAllItems(true);
    }

    public void removeAllItems(boolean notify) {
        getDataSource().clear();
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public List<Object> getTopDataSource() {
        if (null == headerDataSource) {
            headerDataSource = new ArrayList<Object>();
        }
        return headerDataSource;
    }

    public boolean topItemContains(Object item) {
        return getTopDataSource().contains(item);
    }

    public void addItemAlwaysTop(Object item) {
        addItemAlwaysTop(item, getTopDataSource().size());
    }

    public void addItemAlwaysTop(Object item, int index) {
        if (null == item)
            return;

        if (-1 < index && getTopDataSource().size() > index) {
            getTopDataSource().add(index, item);
        } else {
            getTopDataSource().add(item);
        }
        notifyDataSetChanged();
    }

    public void addItemAlwaysTopAll(List<? extends Object> items) {
        if (null != items) {
            getTopDataSource().addAll(items);
            notifyDataSetChanged();
        }
    }

    public void removeItemAlwaysTop(Object item) {
        int index = getTopDataSource().indexOf(item);
        removeItemAlwaysTop(index);
    }

    public void removeItemAlwaysTop(int index) {
        if (-1 < index && getTopDataSource().size() > index) {
            getTopDataSource().remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeAlwaysTopItems() {
        getTopDataSource().clear();
        notifyDataSetChanged();
    }

    public List<Object> getBottomDataSource() {
        if (null == footerDataSource) {
            footerDataSource = new ArrayList<Object>();
        }
        return footerDataSource;
    }

    public boolean bottomItemContains(Object item) {
        return getBottomDataSource().contains(item);
    }

    public void addItemAlwaysBottom(Object item) {
        addItemAlwaysBottom(item, getBottomDataSource().size());
    }

    public void addItemAlwaysBottom(Object item, int index) {
        if (null == item)
            return;

        if (-1 < index && getBottomDataSource().size() > index) {
            getBottomDataSource().add(index, item);
        } else {
            getBottomDataSource().add(item);
        }
        notifyDataSetChanged();
    }

    public void addItemAlwaysBottomAll(List<? extends Object> items) {
        if (null != items) {
            getBottomDataSource().addAll(items);
            notifyDataSetChanged();
        }
    }

    public void removeItemAlwaysBottom(Object item) {
        int index = getBottomDataSource().indexOf(item);
        removeItemAlwaysBottom(index);
    }

    public void removeItemAlwaysBottom(int index) {
        if (-1 < index && getBottomDataSource().size() > index) {
            getBottomDataSource().remove(index);
            notifyDataSetChanged();
        }
    }

    public void removeAlwaysBottomItems() {
        getBottomDataSource().clear();
        notifyDataSetChanged();
    }

    protected Map<Class, ItemRenderDelegate> getOnDataTypeListenerMap() {
        if (null == onDataTypeListenerMap) {
            onDataTypeListenerMap = new HashMap<Class, ItemRenderDelegate>();
        }
        return onDataTypeListenerMap;
    }

    protected List<Class> getDataTypeList() {
        if (null == dataTypeList) {
            dataTypeList = new ArrayList<Class>();
        }
        return dataTypeList;
    }


    public void registerItemRenderDelegate(Class type, ItemRenderDelegate listener) {

        getOnDataTypeListenerMap().put(type, listener);
        if (-1 == getDataTypeList().indexOf(type)) {
            getDataTypeList().add(type);
        }

    }

    public void unregisterItemRenderDelegate(Class type) {
        if (getOnDataTypeListenerMap().containsKey(type)) {
            getOnDataTypeListenerMap().remove(type);
        }

        int index = getDataTypeList().indexOf(type);

        if (-1 < index) {
            getDataTypeList().remove(index);
        }
    }

    public void registerEmptyRendererDelegate(ItemRenderDelegate delegate) {
        if (null == delegate) {
            unregisterItemRenderDelegate(EmptyListRenderType.class);
        } else {
            registerItemRenderDelegate(EmptyListRenderType.class, delegate);
        }
    }

    public void unregisterEmptyRenderDelegate() {
        registerEmptyRendererDelegate(null);
    }

    protected int getDataSize() {
        return getTopDataSource().size() + getDataSource().size() + getBottomDataSource().size();
    }

    @Override
    public int getCount() {
        int size = getDataSize();
        if (size == 0 && -1 < getDataTypeList().indexOf(EmptyListRenderType.class)) {
            return 1;
        } else {
            return size;
        }
    }

    public boolean middleItemContains(Object item) {
        return getDataSource().contains(item);
    }

    @Override
    public Object getItem(int position) {
        int topSize = getTopDataSource().size();
        int midSize = getDataSource().size();
        int bottomSize = getBottomDataSource().size();
        int total = topSize + midSize + bottomSize;

        if (0 == total || 0 > position || total <= position) {
            return null;
        } else if (position < topSize) {
            return getTopDataSource().get(position);
        } else if (position < topSize + midSize) {
            return getDataSource().get(position - topSize);
        } else if (position < topSize + midSize + bottomSize) {
            return getBottomDataSource().get(position - midSize - topSize);
        } else {
            return null;
        }
    }

    public int indexOfItem(Object item) {
        int offset = 0;
        int index = getTopDataSource().indexOf(item);
        if (-1 < index) {
            return index;
        }
        offset += getTopDataSource().size();

        index = getDataSource().indexOf(item);
        if (-1 < index) {
            return offset + index;
        }
        offset += getDataSize();

        index = getBottomDataSource().indexOf(item);
        if (-1 < index) {
            return offset + index;
        } else {
            return -1;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        int type = IGNORE_ITEM_VIEW_TYPE;
        if (0 == getDataSize() && -1 < getDataTypeList().indexOf(EmptyListRenderType.class)) {
            type = getDataTypeList().indexOf(EmptyListRenderType.class);
        } else if (null != item) {
            Class typeClass = item.getClass();
            if (-1 < getDataTypeList().indexOf(typeClass)) {
                type = getDataTypeList().indexOf(typeClass);
            }
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return getDataTypeList().size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int typeIndex = getItemViewType(position);
        Object item = getItem(position);
        if (IGNORE_ITEM_VIEW_TYPE != typeIndex) {
            Class type = getDataTypeList().get(typeIndex);
            ItemRenderDelegate listener = getOnDataTypeListenerMap().get(type);

            if (null != listener) {
                convertView = listener.onRenderItem(position, item, convertView, parent);
            }
        } else {
            String message = null != item ? item.getClass().toString() + " is not registered yet." : "item is null";
            Log.e("SimpleListAdapter", message);
        }
        return convertView;
    }

    static public interface ItemRenderDelegate {
        View onRenderItem(int position, Object item, View view, ViewGroup parent);
    }

    protected class EmptyListRenderType {
    }

}
