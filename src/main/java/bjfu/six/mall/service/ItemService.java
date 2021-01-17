package bjfu.six.mall.service;

import bjfu.six.mall.entity.po.Item;
import bjfu.six.mall.mapper.ItemsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemsMapper itemsMapper;

    public Item[] getItemsByOrderId(int orderId){
        return itemsMapper.getItemsByOrderId(orderId);
    }

    public int addItems(Item item){return itemsMapper.insertItem(item);}

}
