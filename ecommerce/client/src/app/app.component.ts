import { Component, OnInit, ViewChild, inject } from '@angular/core';
import {Observable, tap} from 'rxjs';
import {Router} from '@angular/router';
import { CartStore } from './cart.store';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  // NOTE: you are free to modify this component

  private router = inject(Router)
  private cart: CartStore = inject(CartStore)

  itemCount$!: Observable<number>
  canCheckout!: boolean

  ngOnInit(): void {
    this.itemCount$ = this.cart.getNumberOfItems
                        .pipe(
                          tap((value) => {
                            this.canCheckout = value == 0
                          })
                        )
  }

  checkout(): void {
    this.router.navigate([ '/checkout' ])
  }
}
