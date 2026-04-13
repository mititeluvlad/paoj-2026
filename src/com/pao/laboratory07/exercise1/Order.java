package com.pao.laboratory07.exercise1;
import java.util.Stack;

public class Order{
    private OrderState stare;
    private Stack<OrderState> st;

    public Order(OrderState s){
        this.stare = s;
        this.st = new Stack<>();
    }

    public void nextState() throws OrderIsAlreadyFinalException {
        if(stare.finala() == true){ 
            throw new OrderIsAlreadyFinalException();           
        }
        st.push(stare);
        stare = stare.next();
        System.out.println("Order state updated to: " + stare);
    }

    public void cancel() throws CannotCancelFinalOrderException {
        if(stare.finala() == true){
            throw new CannotCancelFinalOrderException();
        }
        st.push(stare);
        stare = OrderState.CANCELED;
        System.out.println("Order has been canceled.");
    }

    public void undoState() throws CannotRevertInitialOrderStateException {
        if(st.isEmpty()){
            throw new CannotRevertInitialOrderStateException();
        }
        else{
            OrderState s = st.pop();
            stare = s;
            System.out.println("Order state reverted to: " + stare);
        }
    }

}