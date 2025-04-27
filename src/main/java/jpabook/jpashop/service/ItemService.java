package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    //itemRepository 를 위임만 해서 사용하고 있음
    //경우에 따라서 service 계층이 필요한지 고민해 볼 필요가 있음(controller 에서 직접 접근해도 되지 않나)
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional // 작업 후에 commit -> JPA flush(영속성 컨텍스트에 변경된 엔티티를 전부 찾아서 DML을 날림)
    public void updateItem(Long itemId, int price, String name, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId); // 영속상태 -> JPA 가 변경감지
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItem(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
