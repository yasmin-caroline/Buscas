package buscas;

import java.util.Arrays;
import java.util.Random;

public class Buscas_100000_Elementos {
    
    public static NoA raiz;
    public static int nComparativosArvore;
    public static int nComparativosVetor;
    
    public static void main(String[] args){
        
        int tamanho = 100000;
        Random r = new Random();
        int buscar = r.nextInt(tamanho);
        int[] vetorDesordenado = new int[tamanho];
        
        populaExclusivo(vetorDesordenado,vetorDesordenado.length);//popula sem repetir valores
        
        for(int i = 0; i < vetorDesordenado.length; i++)//popula arvore com mesmos valores do vetor de Busca
            inserirNoArvore(vetorDesordenado[i]);
        
        int[] vetorBuscaBinaria=vetorDesordenado.clone();//copia vetor desordenado
        Arrays.sort(vetorBuscaBinaria);//ordena para posterior busca binaria no vetor    
      
        System.out.println("==========================================================");
        System.out.println("Algoritmos de busca, número de elementos: " +tamanho+ ".");
        System.out.println("Valor a ser buscado: " +buscar+ ". \n");
        
//BUSCA EM VETOR DESORDENADO
        System.out.println("Busca Sequencial em vetor desordenado");
        long inicio = System.currentTimeMillis();
        buscaSequencial(vetorDesordenado,buscar);
        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;
        System.out.println("Tempo: " +tempo+ " milissegundos\n");   

//BUSCA BINARIA EM VETOR ORDENADO
        System.out.println("BUSCA Binária em vetor ordenado");
        inicio = System.currentTimeMillis();
        buscaBinariaVetor(vetorBuscaBinaria,buscar);
        fim=System.currentTimeMillis();
        tempo = fim - inicio;
        System.out.println("Comparações: "+nComparativosVetor);
        System.out.println("TEMPO: " +tempo+ " MILISSEGUNDOS\n");   

//BUSCA EM ARVORE BINARIA
        System.out.println("Busca em árvore binária");
        inicio = System.currentTimeMillis();
        buscaBinariaArvore(raiz,buscar);
        fim = System.currentTimeMillis();
        tempo = fim - inicio;
        System.out.println("Comparações: "+nComparativosArvore);
        System.out.println("Tempo: " +tempo+ " milissegundos"); 
        System.out.println("==========================================================");
        
    }
    
//METODOS DE BUSCA
    public static void buscaBinariaVetor(int[]v, int busca){
        buscaBinariaVetor(v, busca, 0, v.length-1);
    }
    
    public static int buscaBinariaVetor(int[]v, int busca, int menor, int maior){
        
        int media = (menor + maior)/2;
        if(menor > maior){
            nComparativosVetor++;
            return -1;
        }
        
        if(v[media] == busca){
            nComparativosVetor++;
            return media;
        }
        
        if(v[media] < busca){
            nComparativosVetor++;
            return buscaBinariaVetor(v, busca ,media+1, maior);
        }else{
            nComparativosVetor++;
            return buscaBinariaVetor(v, busca, menor, media-1);
        }
        
    } 
    
    public static NoA buscaBinariaArvore(NoA temp, int busca){
        
        if(temp.valor == busca){ 
            nComparativosArvore++;
            return temp;
        }else{
            
            if(busca < temp.valor){
                nComparativosArvore++;
                return buscaBinariaArvore(temp.esq, busca);
            }else{
                nComparativosArvore++;
                return buscaBinariaArvore(temp.dir, busca); 
            }
            
        }
        
    }
    
    public static void buscaSequencial(int[] v,int busca){ 
        
        int comparacoes = 0;
        for(int i = 0; i < v.length; i++){
            comparacoes++;
            
            if(v[i] == busca)
                break;
            
        }
        
        System.out.println("Comparacoes: " + comparacoes);
        
    }
    
//POPULA VETOR COM NUMEROS ALEATÓRIOS SEM REPETIÇÃO DE VALOR  
    public static void populaExclusivo(int[] numeros, int qtdnumeros){
        
        Random random = new Random();
        int naleatorio;
        
        for (int i = 0; i < qtdnumeros; i++){
            naleatorio = random.nextInt(qtdnumeros)+1;
            
            while(existe(numeros, qtdnumeros, naleatorio))
               naleatorio=random.nextInt(qtdnumeros)+1;
            
            numeros[i] = naleatorio;
            
        }
        
    }
    
    public static boolean existe(int[] numeros, int qtdnumeros, int naleatorio){
        
        for (int i = 0; i < qtdnumeros; i++) {
            
            if(numeros[i] == naleatorio)
                return true;
            
        }
        
        return false;
    }
    
  
//INSERIR NÓS ARVORE    
    public static void inserirNoArvore(int v){
        NoA novo = new NoA(v);
        
        if(raiz == null){
            raiz = novo;
        }else{
            NoA temp = raiz;
            boolean inseriu = false;
            
            while(!inseriu){
                
                if(novo.valor <= temp.valor){
                    
                    if(temp.esq == null){
                        temp.esq = novo;
                        inseriu = true;
                    }else{
                        temp = temp.esq;
                    }
                    
                }else{
                    
                    if(temp.dir == null){
                       temp.dir = novo;
                       inseriu = true;
                    }else{
                       temp = temp.dir;
                    }
                    
                }
                
            }
            
        }
        
    }

//BALANCEAR ÁRVORE  
    public static void rotacionar_dir(NoA temp){
        NoA prox = temp.esq;
        temp = prox.dir;
        prox.dir = raiz;
        raiz.esq = temp;
        raiz = prox;
    }
    
    public static void rotacionar_esq(NoA temp){
        NoA prox;
        prox = raiz.dir;
        temp = prox.esq;
        prox.esq = raiz;
        raiz.dir = temp;
        raiz = prox;
    }
    
    public static void rotacionar_esq_dir(NoA raiz){
        rotacionar_esq(raiz.esq);
        rotacionar_dir(raiz);
    }

    public static void rotacionar_dir_esq(NoA raiz){
        rotacionar_dir(raiz.dir);
        rotacionar_esq(raiz);
    }
       
    
    public static void exibeArvore(NoA temp){
        
        if(raiz == null){
            System.out.println("Árvore Vazia, impossível exibir");
        }else{
            
            if(temp != null){
                System.out.println(temp.valor);
                exibeArvore(temp.esq);
                exibeArvore(temp.dir);
            }
            
        } 
        
    }
    
}

