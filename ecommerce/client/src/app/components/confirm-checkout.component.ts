import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CartStore } from '../cart.store';
import { Observable, firstValueFrom } from 'rxjs';
import { LineItem, Order } from '../models';
import { ProductService } from '../product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-confirm-checkout',
  templateUrl: './confirm-checkout.component.html',
  styleUrl: './confirm-checkout.component.css'
})
export class ConfirmCheckoutComponent implements OnInit {

  // TODO Task 3
  private fb: FormBuilder = inject(FormBuilder)
  private cart: CartStore = inject(CartStore)
  private router: Router = inject(Router)
  private productSvc: ProductService = inject(ProductService)
  
  cartTotal!: number
  checkoutForm!: FormGroup
  cartItems!: LineItem[]
  
  ngOnInit(): void {
    this.checkoutForm = this.createForm()
    firstValueFrom(this.cart.getCartTotal).then((value) => this.cartTotal = value)
    firstValueFrom(this.cart.getAllLineItems).then((cart) => this.cartItems = cart)
  }

  createForm(): FormGroup {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      address: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      priority: this.fb.control<boolean>(false),
      comments: this.fb.control<string>('')
    })
  }
  
  checkout() {
    let order: Order = this.checkoutForm.value as Order
    order.cart = {lineItems: this.cartItems}
    this.productSvc.checkout(order)
    .then((resp) => {
      alert(`Order ID: ${JSON.parse(JSON.stringify(resp)).orderId}`)
      this.cart.clearCart()
      this.router.navigate(['/'])
    })
    .catch((err) => {
      alert(`Error: ${JSON.parse(JSON.stringify(err)).error.message}`)
    })
  }
}
