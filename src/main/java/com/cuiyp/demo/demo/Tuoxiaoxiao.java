package com.cuiyp.demo.demo;

public class Tuoxiaoxiao {
    private int age;
    private String name;

    public Tuoxiaoxiao(String name, int age){
        super();
        this.name = name;
        this.age = age;
    }
    private static final Tuoxiaoxiao Intence;
    static {
        Intence = new Tuoxiaoxiao();
    }
    public Tuoxiaoxiao(){

    }

    public static Tuoxiaoxiao getIntence(){
        return Intence;
    }

    public int hashCode(){
        final int prime = 31;
        int result = 1;
        result = prime * result + this.age;
        result = prime * result + ((name == null) ? 0: name.hashCode());
        return result;
    }
    public static void main(String[] args) {

        //breakTest();

        //Tuoxiaoxiao a = new Tuoxiaoxiao("A",11);
        //Tuoxiaoxiao b = new Tuoxiaoxiao("A",11);
        //System.out.println("a.hashcode="  + a.hashCode());
        //System.out.println("b.hashcode="  + b.hashCode());
        Tuoxiaoxiao b = new Tuoxiaoxiao("B", 11);
        System.out.println(b.age);
        editAge(b);
        System.out.println(b.age);

        int num1 = 10;
        int num2 = 20;
        int temp = num1;
        num1 = num2;
        num2 = temp;
        System.out.println(num1 + "---" + num2);
    }

    private static void editAge(Tuoxiaoxiao obj){
        obj.age = 11111;
    }
    @Override
    public boolean equals(Object obj){
        return  true;
    }

    private static void breakTest(){



        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i == 0 && j == 0){
                    System.out.println("当多层循环中嵌套，想要跳出最外层循环，这样做");
                    break;
                }
                System.out.println("内层循环走不走");
            }
            System.out.println("只跳出一层循环");
        }
    }
}
