/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cpd4414.assign2;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;

/**
 *
 * @author Len Payne <len.payne@lambtoncollege.ca>
 */
public class OrderQueue {

    Queue<Order> orderQueue = new ArrayDeque<>();

    public void add(Order order) throws Exception {

        if ((order.getCustomerId().isEmpty() && order.getCustomerName().isEmpty())) {
            throw new Exception("Customer ID and Customer Name not Found");
        } else {
            orderQueue.add(order);
            order.setTimeReceived(new Date());
        }
        if (order.getListOfPurchases().isEmpty()) {
            throw new Exception("No list Of Purchase");
        } else {
            orderQueue.add(order);
            order.setTimeReceived(new Date());
        }
    }

    public Order checkNext() {
        return orderQueue.peek();

    }

    public void orderProcess(Order order) throws Exception {
        if (order.getTimeReceived() == null) {
            throw new noRecivedException("Order does not have a time recived");
        }
        for (Purchase product : order.getListOfPurchases()) {
            int inventoryProductQuant = Inventory.getQuantityForId(product.getProductId());
            int orderQuantity = product.getQuantity();
            if (orderQuantity > inventoryProductQuant) {
                throw new Exception("order out of stock");
            }
        }
        order.setTimeProcessed(new Date());
        orderQueue.remove(order);
        orderQueue.add(order);
    }

    public void fullFillOrder(Order order) throws noRecivedException, Exception {


        if (order.getTimeReceived() == null) {
            throw new noRecivedException("Order does not have a time recived");
        }
        if (order.getTimeProcessed() == null) {
            throw new noProcessException("Order does not have a time processed");
        }
        for (Purchase product : order.getListOfPurchases()) {
            int inventoryProductQuant = Inventory.getQuantityForId(product.getProductId());
            int orderQuantity = product.getQuantity();
            if (orderQuantity > inventoryProductQuant) {
                throw new Exception("order out of stock");
            }

        }
        order.setTimeFulfilled(new Date());
        orderQueue.remove(order);
        orderQueue.add(order);
    }
}
