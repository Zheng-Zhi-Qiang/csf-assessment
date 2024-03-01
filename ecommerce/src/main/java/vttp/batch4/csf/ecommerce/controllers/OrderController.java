package vttp.batch4.csf.ecommerce.controllers;


import java.io.StringReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.errors.SQLInsertionError;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
@RequestMapping(path = "/api")
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping(path = "/order", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public ResponseEntity<String> postOrder(@RequestBody String payload) {

    // TODO Task 3
    JsonObject data = Json.createReader(new StringReader(payload)).readObject();
    Order order = new Order();
    Cart cart = new Cart();
    JsonArray cartData = data.getJsonObject("cart").getJsonArray("lineItems");
    List<LineItem> items = new LinkedList<>();
    cartData.stream().map(value -> value.asJsonObject()).forEach(item -> {
      LineItem lineItem = new LineItem();
      lineItem.setName(item.getString("name"));
      lineItem.setPrice(Float.parseFloat(item.get("price").toString()));
      lineItem.setProductId(item.getString("prodId"));
      lineItem.setQuantity(item.getInt("quantity"));
      items.add(lineItem);
    });
    cart.setLineItems(items);
    order.setName(data.getString("name"));
    order.setAddress(data.getString("address"));
    order.setComments(data.getString("comments"));
    order.setDate(new Date());
    order.setPriority(data.getBoolean("priority"));
    order.setCart(cart);
    try {
      poSvc.createNewPurchaseOrder(order);
      String resp = Json.createObjectBuilder()
                        .add("orderId", order.getOrderId())
                        .build().toString();
      return ResponseEntity.status(200).body(resp);
    }
    catch (SQLInsertionError e){
      String resp = Json.createObjectBuilder()
                        .add("message", e.getLocalizedMessage())
                        .build().toString();
      return ResponseEntity.status(400).body(resp);
    }
    
  }
}
