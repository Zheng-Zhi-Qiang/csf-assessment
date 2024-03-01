package vttp.batch4.csf.ecommerce.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.errors.SQLInsertionError;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;

import static vttp.batch4.csf.ecommerce.repositories.Queries.*;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  public void create(Order order) throws SQLInsertionError {
    // TODO Task 3
    int result = template.update(SQL_INSERT_ORDER, order.getOrderId(), order.getDate(),
                  order.getName(), order.getAddress(), order.getPriority(), order.getComments());
    if (result != 1){
      throw new SQLInsertionError("Failed to create order");
    }

    int count = 0;
    int noOfLineItems = order.getCart().getLineItems().size();
    for (LineItem item: order.getCart().getLineItems()) {
      count += template.update(SQL_INSERT_LINE_ITEM, item.getProductId(), item.getName(), item.getQuantity(), item.getPrice(), order.getOrderId());
    }
    if (count != noOfLineItems){
      throw new SQLInsertionError("Failed to insert line items");
    }
  }
}
