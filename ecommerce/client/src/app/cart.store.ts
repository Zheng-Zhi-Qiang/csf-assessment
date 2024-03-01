
// TODO Task 2

import { Injectable } from "@angular/core";
import { ComponentStore } from "@ngrx/component-store";
import { Cart, LineItem } from "./models";

const INIT_STATE: Cart = {
    lineItems: []
}

// Use the following class to implement your store
@Injectable()
export class CartStore extends ComponentStore<Cart> {
    constructor() {super(INIT_STATE)}

    readonly getAllLineItems = this.select<LineItem[]>(
        (slice: Cart) => {
          return slice.lineItems
        }
      )

    readonly addToCart = this.updater<LineItem>(
        (slice: Cart, value: LineItem) => {
            return {
                lineItems: [ ...slice.lineItems, value ]
            } as Cart
        }
    )

    readonly getNumberOfItems = this.select<number>(
        (slice: Cart) => {
            return slice.lineItems.length
        }
    )

    readonly getCartTotal = this.select<number>(
        (slice: Cart) => {
            var total = 0
            slice.lineItems.forEach((item) => {
                total += item.price * item.quantity
            })
            return total
        }
    )
    
    readonly clearCart = this.updater(
        (slice: Cart) => {
            return {
                lineItems: []
            } as Cart
        }
    )
}
