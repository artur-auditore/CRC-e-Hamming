import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class HammingCode {

    public ArrayList<Integer> palavraCompleta;

    public Boolean potenciaDeDois(double numero){
        double a = numero;
        Boolean potencia = false;
        if(a == 1){
            return true;
        }
        while(a != 1 && a >=2){
            if((a / 2 == 1) && ( a % 2 == 0)){
                potencia = true;
            }
            a =  a / 2;
        }
        return potencia;
    }
    public void montarPalavra(ArrayList<Integer> palavraInicial){
        palavraCompleta = new ArrayList<>();
        int numero = 1;
        int quant = 0;
        while(quant < palavraInicial.size()){

                if (potenciaDeDois(numero) == false) {
                    palavraCompleta.add(palavraInicial.get(quant));
                    quant++;
                }else{
                    palavraCompleta.add(null);
                }
                numero++;
        }
    }

    public int verificacao(int numeroParidade, int posicao, ArrayList<Integer> palavra) {
        int soma = 0;
        for (int proximo = numeroParidade + 1; proximo <= palavra.size(); proximo++) {
            if (potenciaDeDois(proximo) == false) {
                String bit = Integer.toBinaryString(proximo);
                int numero = palavra.get(proximo - 1);
                if (bit.charAt(bit.length() - posicao) == '1' && numero == 1) {
                    soma++;
                }
            }
        }return soma;
    }
    public int corrigir(ArrayList<Integer> palavraRecebida, int p){
        int bitErro = 0;
        int quant = 0;
        int numero = 0;
        for(int i=1;i<palavraRecebida.size();i++){
            if(potenciaDeDois(i) == true){
                quant++;
                numero = verificacao(i,quant,palavraRecebida);
                int valorBit = -1;
                if(p == 0){
                    if(numero % 2 == 0){
                        valorBit = 0;
                    }
                    else{
                        valorBit = 1;
                    }
                }
                else{
                    if(numero % 2 != 0){
                        valorBit = 0;
                    }
                    else{
                        valorBit = 1;
                    }
                }
                if(palavraRecebida.get(i-1) != valorBit){
                    //palavraRecebida.set(i-1,numero);
                    bitErro = bitErro + i;
                }
            }
        }
        if(bitErro != 0){
            if(palavraRecebida.get(bitErro-1) == 0){
                palavraRecebida.set(bitErro-1,1);
            }
            else{
                palavraRecebida.set(bitErro-1,0);
            }
        }
        return bitErro;
    }
    public void calcular(int paridade){
        int p = paridade;
        int quant=0;
        for(int i=1;i<palavraCompleta.size();i++){
            if(potenciaDeDois(i) == true){
                quant++;
                int numero = verificacao(i,quant,palavraCompleta);

                if(p == 0) {
                    if (numero % 2 != 0) {
                        palavraCompleta.set(i - 1, 1);
                    } else {
                        palavraCompleta.set(i - 1, 0);
                    }
                }
                else{
                    if (numero % 2 != 0) {
                        palavraCompleta.set(i - 1, 0);
                    } else {
                        palavraCompleta.set(i - 1, 1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HammingCode hc = new HammingCode();
        ArrayList<Integer> palavra = new ArrayList();
        ArrayList<Integer> palavraEnviada = new ArrayList();

        Scanner ler = new Scanner(System.in);

        int numero = 0;
        do{
            System.out.println("Digite 1 para transmitir uma palavra:");
            System.out.println("Digite 2 para corrigir uma palavra:");
            System.out.println("Digite 0 para finalizar:");
            numero = ler.nextInt();

            switch (numero){
                case 1:
                    System.out.println("Quantos bits deseja transmitir:");
                    int quant = ler.nextInt();
                    System.out.println("Digite 0 para paridade par ou 1 para paridade impar:");
                    int par = ler.nextInt();
                    for (int i = 0; i < quant; i++) {
                        System.out.println("digite um bit");
                        int bit = ler.nextInt();
                        palavra.add(bit);
                    }
                    hc.montarPalavra(palavra);
                    hc.calcular(par);
                    System.out.println("Palavra Transmitida");
                    for (Integer i: hc.palavraCompleta){
                        System.out.print(i);
                    }
                    System.out.println("\n");
                    palavra.clear();
                    break;
                case 2:
                    System.out.println("Digite a Quantidade de bits para a verificação:");
                    int quantidade = ler.nextInt();
                    System.out.println("Digite 0 para paridade par ou 1 para paridade impar:");
                    int pari = ler.nextInt();
                    for (int i = 0; i < quantidade; i++) {
                        System.out.println("digite um bit");
                        int bit = ler.nextInt();
                        palavraEnviada.add(bit);
                    }
                    int numeroErro = hc.corrigir(palavraEnviada, pari);
                    if(numeroErro == 0){
                        System.out.println("palavra enviada corretamente");
                    }
                    else {
                        System.out.println("Erro no bit: "+numeroErro);
                    }
                    System.out.println("Palavra Final");
                    for (Integer i: palavraEnviada){
                        System.out.print(i);
                    }
                    System.out.println("\n");
                    palavraEnviada.clear();
                    break;
            }

        }while(numero != 0);
    }
}

