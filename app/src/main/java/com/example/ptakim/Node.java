package com.example.ptakim;

public class Node {
    private int num;
    private String str;

    public Node(int num,String str){
        this.num = num;
        this.str = str;
    }

    public  Node(String str){
        this.str = str;
        this.num = getRandomIntInclusive(0,10000);
    }
    public  Node(String str,int min){
        this.str = str;
        this.num = getRandomIntInclusive(min,10000);
    }

    public int getNum(){
        return num;
    }

    public String getStr(){
        return str;
    }

    private int getRandomIntInclusive( double min,double  max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return ((int) Math.floor(Math.random() * (max - min + 1) + min)); // The maximum is inclusive and the minimum is inclusive
    }

}
