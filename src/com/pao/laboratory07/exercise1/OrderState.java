package com.pao.laboratory07.exercise1;

public enum OrderState{
    PLACED,PROCESSED,SHIPPED,DELIVERED,CANCELED;

    public OrderState next(){
        return switch(this){
            case PLACED -> PROCESSED;
            case PROCESSED -> SHIPPED;
            case SHIPPED -> DELIVERED;
            default -> this;
        };
    }

    public boolean finala(){
        if (this == DELIVERED || this == CANCELED)
            return true;
        else
            return false;
    }

}