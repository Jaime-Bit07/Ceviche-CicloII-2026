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
            for (int j = 0; j < m[1].length; j++) {
                if(m[i][j]!=0){
                    System.out.print(m[i][j]+"\t");
                }else{
                    System.out.print(" \t");
                }
            }
            System.out.println("");
        }
    }

    public static int[][] matrizF5(int tamanho, int numI){
        int[][] matriz=new int[tamanho][tamanho];
        for (int i = 0; i < matriz.length; i++) {
            for (int j = matriz[0].length-1; j >=matriz[0].length-1-i; j--) {
                matriz[i][j]=numI;
                numI++;
            }
        }
        return matriz;
    }


    public static int[][] matrizF11(int tamanho, int numI){
        int[][] matriz=new int[tamanho][tamanho];
        for (int f = 0; f < matriz.length; f++) {
            for (int c = matriz[0].length-1-f; c>=0; c--) {
                matriz[f][c]=numI;
                numI++;
            }
        }
        return matriz;
    }

    public static int[][] matrizF12(int tamanho, int numI){
        int[][] matriz=new int[tamanho][tamanho];
        for (int f = 0; f < matriz.length; f++) {
            for (int c = 0; c<matriz[0].length;c++) {
                matriz[f][c]=numI;
                numI++;
            }
        }
        return matriz;
    }

    

    public static void main(String[] args) {
        // meses();
        // int [][] m={{1,2,6}, {3,4,2}, {5,6,7}};
        // imprimirMatriz(m);
        imprimirMatriz(matrizF5(6,1));
        System.out.println("");
        imprimirMatriz(matrizF11(5,1));
        System.out.println("");
        imprimirMatriz(matrizF12(5,1));
    }
}