package vttp.batch4.csf.ecommerce.repositories;

public class Queries {
    public static final String SQL_INSERT_ORDER = """
            INSERT INTO orders(order_id, order_date, name, address, priority, comments)
            VALUE (?, ?, ?, ?, ?, ?);
            """;

    public static final String SQL_INSERT_LINE_ITEM = """
            INSERT INTO line_items(product_id, name, quantity, price, order_id)
            VALUE (?, ?, ?, ?, ?);
            """;
}
