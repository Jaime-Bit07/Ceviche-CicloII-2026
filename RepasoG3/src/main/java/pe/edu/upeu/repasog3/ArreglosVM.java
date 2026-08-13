package pe.edu.upeu.repasog3;

public class ArreglosVM {

    public static void meses(){
        String[] mes={"Enero", "Febrero", "Marzo", "Abril", "Mayo",
                "Junio", "Julio", "Agosto", "Setiembre",
                "Octubre", "Noviembre", "Diciembre"};

        System.out.println("Tamaño:"+mes.length);
        for (String m:mes){
            System.out.println(m);
        }

        mes[11]="";
        System.out.println("");
        for (String m:mes){
            if(!m.equals(""))
                System.out.println(m);
        }
    }

    public static void imprimirMatriz(int [][] m){
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if(m[i][j]!=0){
                    System.out.print(m[i][j]+"\t");
                }else{
                    System.out.print(" \t");
                }
            }
            System.out.println("");
        }

    }


    public static void main(String[] args) {
        meses();
        int [][] m={{0,2}, {3,4}};
        imprimirMatriz(m);
    }
}