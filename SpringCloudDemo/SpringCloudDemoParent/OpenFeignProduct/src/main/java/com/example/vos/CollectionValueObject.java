package com.example.vos;

import java.util.List;

/**
 * @author chenzufeng
 * @date 2021/6/15
 * 定义用来接收集合类型参数的对象
 */
public class CollectionValueObject {
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
