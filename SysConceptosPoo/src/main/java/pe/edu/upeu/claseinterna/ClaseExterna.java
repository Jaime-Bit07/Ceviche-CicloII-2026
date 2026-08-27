package pe.edu.upeu.claseinterna;

public class ClaseExterna {
    int num1;
    int num2;
    char oper;
    ClaseExterna(int num1, int num2, char oper){
        this.num1=num1;
        this.num2=num2;
        this.oper=oper;
    }
    class ClaseInterna{
        double operacion(int num1, int num2, char oper){
            double resul=0;
            switch (oper){
                case '+': resul=num1+num2; break;
                case '-': resul=num1-num2; break;
                case '/': resul=(double)num1/num2; break;
                case '*': resul=num1*num2; break;
                default: break;
            }
            return resul;
        }
    }

    public static void main(String[] args) {
        char[] oper={'+', '-', '/', '*'};
        ClaseExterna obj=new ClaseExterna(10, 6,'+');
        ClaseInterna objI=obj.new ClaseInterna();
        for (char o:oper) {
            double resul = objI.operacion(obj.num1, obj.num2, o);
            System.out.println("Resultado con "+o+":" +resul);
        }

    }
}
