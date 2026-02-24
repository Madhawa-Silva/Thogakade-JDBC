package model.TM;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class CartTM {

    private String orderId;
    private String itemCode;
    private String description;
    private double unitPrice;
    private Integer qtyOnHand;
    private double total;

    public CartTM(String id, String description , Double unitPrice, Integer qty, Double total , String orderId ) {
        this.itemCode = id;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qty;
        this.total = total;
        this.orderId = orderId;


    }

}
