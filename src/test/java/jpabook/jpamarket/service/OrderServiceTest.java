package jpabook.jpamarket.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpamarket.*;
import jpabook.jpamarket.exception.NotEnoughStockException;
import jpabook.jpamarket.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration(classes = JpamarketApplication.class)
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        Member member = createMember();
        Item item = createBook("시골 jpa",1000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("상품주문시상태는order", OrderStatus.ORDER,getOrder.getStatus());
        assertEquals("주문한 상품 종류 수가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 1000*2,getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어들어야한다.", 8,item.getStockQuantity());
    }
    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        Member member = createMember();
        Item item = createBook("시골 jpa",1000,10);
        int orderCount = 11;
        orderService.order(member.getId(), item.getId(), orderCount);
        fail("재고수량 부족 예외가 발생해야함.");

    }
    @Test
    public void 주문취소(){
        Member member = createMember();
        Item item = createBook("시골 jpa",1000,10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals("주문 취소시 상태는 cancel이다.", OrderStatus.CANCEL,getOrder.getStatus());
        assertEquals("주문이 취소되면 상품은 그만큼 재고가 증가해야함.", 10,item.getStockQuantity());

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-23"));
        em.persist(member);
        return member;
    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}